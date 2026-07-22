#!/usr/bin/env bash
set -euo pipefail

if [[ $# -ne 2 ]]; then
  echo "usage: $0 <source-env> <target-env>" >&2
  exit 2
fi

SOURCE_ENV=$1
TARGET_ENV=$2

for key in JWT_SECRET_KEY PROD_BASE_URL STAG_BASE_URL VERCEL_URL TEST_URL; do
  value=$(sed -n "s/^${key}=//p" "$SOURCE_ENV" | tail -n 1)
  if [[ -z "$value" ]]; then
    echo "missing required key in source env: $key" >&2
    exit 1
  fi

  temp_file=$(mktemp)
  awk -v key="$key" -v value="$value" '
    BEGIN { updated = 0 }
    index($0, key "=") == 1 {
      print key "=" value
      updated = 1
      next
    }
    { print }
    END {
      if (!updated) {
        print key "=" value
      }
    }
  ' "$TARGET_ENV" > "$temp_file"
  chmod 600 "$temp_file"
  mv "$temp_file" "$TARGET_ENV"
done
