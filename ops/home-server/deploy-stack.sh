#!/usr/bin/env bash
set -euo pipefail

if [ "$#" -ne 4 ]; then
  echo "usage: $0 <deploy-dir> <env-file> <image-ref> <public-health-url>" >&2
  exit 1
fi

DEPLOY_DIR="$1"
ENV_FILE="$2"
IMAGE_REF="$3"
PUBLIC_HEALTH_URL="$4"

read_env_value() {
  local key="$1"
  sed -n "s/^${key}=//p" "$ENV_FILE" | tail -n 1
}

APP_PORT="$(read_env_value APP_PORT)"
APP_CONTAINER_NAME="$(read_env_value APP_CONTAINER_NAME)"
MYSQL_CONTAINER_NAME="$(read_env_value MYSQL_CONTAINER_NAME)"
REDIS_CONTAINER_NAME="$(read_env_value REDIS_CONTAINER_NAME)"
APP_PORT="${APP_PORT:-8080}"
APP_CONTAINER_NAME="${APP_CONTAINER_NAME:-gujeuk-app}"
MYSQL_CONTAINER_NAME="${MYSQL_CONTAINER_NAME:-gujeuk-mysql}"
REDIS_CONTAINER_NAME="${REDIS_CONTAINER_NAME:-gujeuk-redis}"
LOCAL_HEALTH_URL="http://localhost:${APP_PORT}/public/organs"

wait_for_health() {
  local container_name="$1"

  for attempt in $(seq 1 45); do
    local state
    state="$(docker inspect -f '{{if .State.Health}}{{.State.Health.Status}}{{else}}{{.State.Status}}{{end}}' "$container_name" 2>/dev/null || true)"

    if [ "$state" = "healthy" ] || [ "$state" = "running" ]; then
      echo "dependency ready: ${container_name} (${state})"
      return 0
    fi

    if [ "$attempt" -eq 45 ]; then
      echo "dependency not ready: ${container_name}" >&2
      docker inspect "$container_name" >&2 || true
      return 1
    fi

    sleep 2
  done
}

cd "$DEPLOY_DIR"

test -f "$ENV_FILE"
docker image inspect "$IMAGE_REF" >/dev/null

APP_IMAGE="$IMAGE_REF" docker compose --env-file "$ENV_FILE" up -d mysql redis
wait_for_health "$MYSQL_CONTAINER_NAME"
wait_for_health "$REDIS_CONTAINER_NAME"
APP_IMAGE="$IMAGE_REF" docker compose --env-file "$ENV_FILE" up -d --no-deps app

for attempt in $(seq 1 45); do
  if curl -fsS "$LOCAL_HEALTH_URL" >/dev/null; then
    echo "local health check passed: $LOCAL_HEALTH_URL"
    break
  fi

  if [ "$attempt" -eq 45 ]; then
    echo "local health check failed: $LOCAL_HEALTH_URL" >&2
    APP_IMAGE="$IMAGE_REF" docker compose --env-file "$ENV_FILE" ps >&2 || true
    APP_IMAGE="$IMAGE_REF" docker compose --env-file "$ENV_FILE" logs --tail=200 app >&2 || true
    exit 1
  fi

  sleep 2
done

curl -fsS "$PUBLIC_HEALTH_URL" >/dev/null
echo "public health check passed: $PUBLIC_HEALTH_URL"
echo "deployed container: $APP_CONTAINER_NAME"
