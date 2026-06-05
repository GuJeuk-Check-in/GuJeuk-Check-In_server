#!/usr/bin/env bash
set -euo pipefail

SOURCE_DIR="${SOURCE_DIR:-/home/ubuntu/bin}"
TARGET_USER="${TARGET_USER:-ubuntu}"
SUDOERS_FILE=/etc/sudoers.d/gujeuk-wifi

if [ "$EUID" -ne 0 ]; then
  echo "Run with sudo:"
  echo "sudo /home/ubuntu/bin/install-wifi-command.sh"
  exit 1
fi

for file in server-wifi gujeuk-wifi-admin; do
  if [ ! -f "$SOURCE_DIR/$file" ]; then
    echo "Missing file: $SOURCE_DIR/$file" >&2
    exit 1
  fi
done

install -o root -g root -m 0755 \
  "$SOURCE_DIR/gujeuk-wifi-admin" \
  /usr/local/sbin/gujeuk-wifi-admin

SERVER_WIFI_TARGET="/home/$TARGET_USER/bin/server-wifi"
if [ "$(readlink -f "$SOURCE_DIR/server-wifi")" = "$(readlink -f "$SERVER_WIFI_TARGET")" ]; then
  chown "$TARGET_USER:$TARGET_USER" "$SERVER_WIFI_TARGET"
  chmod 0755 "$SERVER_WIFI_TARGET"
else
  install -o "$TARGET_USER" -g "$TARGET_USER" -m 0755 \
    "$SOURCE_DIR/server-wifi" \
    "$SERVER_WIFI_TARGET"
fi

ln -sf "$SERVER_WIFI_TARGET" "/home/$TARGET_USER/bin/wifi"
ln -sf "$SERVER_WIFI_TARGET" /wifi

cat >"$SUDOERS_FILE" <<EOF
$TARGET_USER ALL=(root) NOPASSWD: /usr/local/sbin/gujeuk-wifi-admin *
EOF
chmod 0440 "$SUDOERS_FILE"
visudo -cf "$SUDOERS_FILE"

echo "Wi-Fi command installed."
echo "Use: wifi"
echo "Use: /wifi"
