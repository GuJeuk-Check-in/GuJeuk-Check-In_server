#!/usr/bin/env bash
set -euo pipefail

BASE=/home/ubuntu/.cloudflared
SUPERVISOR=/home/ubuntu/bin/cloudflared-supervisor.sh
LOCK="$BASE/start.lock"
OUT="$BASE/supervisor.out"

mkdir -p "$BASE"
exec 9>"$LOCK"
if ! flock -w 20 9; then
  echo "cloudflared start lock is still held after 20 seconds" >&2
  exit 1
fi

supervisor_count="$(pgrep -u ubuntu -f '/home/ubuntu/bin/cloudflared-supervisor.sh' | wc -l || true)"
tunnel_count="$(pgrep -u ubuntu -f '/usr/local/bin/cloudflared --config /home/ubuntu/.cloudflared/config.yml tunnel run' | wc -l || true)"

if [ "$supervisor_count" -gt 0 ] && [ "$tunnel_count" -gt 0 ]; then
  echo "cloudflared already running"
  exit 0
fi

pkill -u ubuntu -f '/usr/local/bin/cloudflared --config /home/ubuntu/.cloudflared/config.yml tunnel run' >/dev/null 2>&1 || true
pkill -u ubuntu -f '/home/ubuntu/bin/cloudflared-supervisor.sh' >/dev/null 2>&1 || true
sleep 1

# Close the lock descriptor in the background process. Otherwise the
# supervisor keeps the lock forever and future recovery attempts are skipped.
nohup "$SUPERVISOR" 9>&- >"$OUT" 2>&1 &
echo "started cloudflared supervisor pid=$!"
