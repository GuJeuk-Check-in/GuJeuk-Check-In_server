#!/usr/bin/env bash
set -euo pipefail

BASE=/home/ubuntu/.cloudflared
LOCK="$BASE/watchdog.lock"
LOG="$BASE/watchdog.log"
FAILS_FILE="$BASE/watchdog.failures"
NETWORK_FILE="$BASE/watchdog.network"
BODY_FILE="$BASE/watchdog.response"
START=/home/ubuntu/bin/start-cloudflared.sh
LOCAL_URL=http://localhost:8080/purpose/all
PUBLIC_URL=https://api.taisu.site/purpose/all

mkdir -p "$BASE"
exec 8>"$LOCK"
flock -n 8 || exit 0

log() {
  echo "$(date -u '+%Y-%m-%dT%H:%M:%SZ') watchdog: $*" >>"$LOG"
}

restart_cloudflared() {
  local reason="$1"
  local attempt

  log "restarting cloudflared: $reason"
  pkill -u ubuntu -f '/usr/local/bin/cloudflared --config /home/ubuntu/.cloudflared/config.yml tunnel run' >/dev/null 2>&1 || true
  pkill -u ubuntu -f '/home/ubuntu/bin/cloudflared-supervisor.sh' >/dev/null 2>&1 || true

  for attempt in {1..15}; do
    if ! pgrep -u ubuntu -f '/usr/local/bin/cloudflared --config /home/ubuntu/.cloudflared/config.yml tunnel run' >/dev/null &&
      ! pgrep -u ubuntu -f '/home/ubuntu/bin/cloudflared-supervisor.sh' >/dev/null; then
      break
    fi
    sleep 1
  done

  pkill -KILL -u ubuntu -f '/usr/local/bin/cloudflared --config /home/ubuntu/.cloudflared/config.yml tunnel run' >/dev/null 2>&1 || true
  pkill -KILL -u ubuntu -f '/home/ubuntu/bin/cloudflared-supervisor.sh' >/dev/null 2>&1 || true
  sleep 1

  "$START" >>"$LOG" 2>&1 || true
  echo 0 >"$FAILS_FILE"
}

network_fingerprint() {
  local ssid
  local route

  ssid="$(iw dev wlo1 link 2>/dev/null |
    awk -F': ' '/^[[:space:]]*SSID:/ { print $2; exit }')"
  route="$(ip -4 route get 1.1.1.1 2>/dev/null |
    awk 'NR == 1 {
      for (index = 1; index <= NF; index++) {
        if ($index == "via") gateway = $(index + 1)
        if ($index == "src") source = $(index + 1)
      }
      print gateway "|" source
    }')"

  if [ -n "$ssid" ] && [ -n "$route" ]; then
    printf '%s|%s\n' "$ssid" "$route"
  fi
}

supervisor_count="$(pgrep -u ubuntu -f '/home/ubuntu/bin/cloudflared-supervisor.sh' | wc -l || true)"
tunnel_count="$(pgrep -u ubuntu -f '/usr/local/bin/cloudflared --config /home/ubuntu/.cloudflared/config.yml tunnel run' | wc -l || true)"

if [ "$supervisor_count" -eq 0 ] || [ "$tunnel_count" -eq 0 ]; then
  restart_cloudflared "process missing supervisor=$supervisor_count tunnel=$tunnel_count"
  exit 0
fi

current_network="$(network_fingerprint)"
previous_network="$(cat "$NETWORK_FILE" 2>/dev/null || true)"

if [ -n "$current_network" ]; then
  printf '%s\n' "$current_network" >"$NETWORK_FILE"
fi

if [ -n "$previous_network" ] &&
  [ -n "$current_network" ] &&
  [ "$current_network" != "$previous_network" ]; then
  restart_cloudflared "network changed from [$previous_network] to [$current_network]"
  exit 0
fi

# Restarting the tunnel cannot fix an unavailable local application.
if ! curl -fsS --max-time 5 "$LOCAL_URL" >/dev/null 2>&1; then
  log "local app check failed; keeping tunnel process untouched"
  exit 0
fi

http_code="$(curl -sS --max-time 10 -o "$BODY_FILE" -w '%{http_code}' "$PUBLIC_URL" 2>/dev/null || true)"

if [ "$http_code" = "200" ]; then
  echo 0 >"$FAILS_FILE"
  rm -f "$BODY_FILE"
  exit 0
fi

if [ "$http_code" = "530" ] &&
  grep -q 'error code: 1033' "$BODY_FILE" 2>/dev/null; then
  restart_cloudflared "Cloudflare returned HTTP 530 / tunnel error 1033"
  rm -f "$BODY_FILE"
  exit 0
fi

fail_count=0
if [ -f "$FAILS_FILE" ]; then
  fail_count="$(cat "$FAILS_FILE" 2>/dev/null || echo 0)"
fi
case "$fail_count" in
  '' | *[!0-9]*) fail_count=0 ;;
esac

fail_count=$((fail_count + 1))
echo "$fail_count" >"$FAILS_FILE"
log "public check failed code=${http_code:-none}; consecutive_failures=$fail_count"

if [ "$fail_count" -ge 2 ]; then
  restart_cloudflared "$fail_count consecutive public API failures"
fi

rm -f "$BODY_FILE"
