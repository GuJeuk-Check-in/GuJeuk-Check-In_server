#!/usr/bin/env bash
set -euo pipefail

SOURCE_DIR="${SOURCE_DIR:-/home/ubuntu/bin}"
TARGET_USER="${TARGET_USER:-ubuntu}"
SUDOERS_FILE=/etc/sudoers.d/gujeuk-shutdown

if [ "$EUID" -ne 0 ]; then
  echo 'Run with sudo:'
  echo 'sudo /home/ubuntu/bin/install-shutdown-nopasswd.sh'
  exit 1
fi

for file in server-shutdown gujeuk-rtcwake; do
  if [ ! -f "$SOURCE_DIR/$file" ]; then
    echo "Missing file: $SOURCE_DIR/$file" >&2
    exit 1
  fi
done

install -o root -g root -m 0755 \
  "$SOURCE_DIR/gujeuk-rtcwake" \
  /usr/local/sbin/gujeuk-rtcwake

SERVER_SHUTDOWN_TARGET="/home/$TARGET_USER/bin/server-shutdown"
if [ "$(readlink -f "$SOURCE_DIR/server-shutdown")" = "$(readlink -f "$SERVER_SHUTDOWN_TARGET")" ]; then
  chown "$TARGET_USER:$TARGET_USER" "$SERVER_SHUTDOWN_TARGET"
  chmod 0755 "$SERVER_SHUTDOWN_TARGET"
else
  install -o "$TARGET_USER" -g "$TARGET_USER" -m 0755 \
    "$SOURCE_DIR/server-shutdown" \
    "$SERVER_SHUTDOWN_TARGET"
fi

ln -sf "$SERVER_SHUTDOWN_TARGET" "/home/$TARGET_USER/bin/shutdown"
ln -sf "$SERVER_SHUTDOWN_TARGET" /shutdown

cat >"$SUDOERS_FILE" <<EOF
$TARGET_USER ALL=(root) NOPASSWD: /usr/local/sbin/gujeuk-rtcwake *
$TARGET_USER ALL=(root) NOPASSWD: /usr/sbin/shutdown -h now
EOF
chmod 0440 "$SUDOERS_FILE"
visudo -cf "$SUDOERS_FILE"

echo 'Scheduled shutdown command installed.'
echo 'Testing RTC wake scheduling without powering off...'
sudo -u "$TARGET_USER" sudo -n /usr/local/sbin/gujeuk-rtcwake check 120
echo 'RTC dry-run passed.'
echo 'Use: shutdown'
