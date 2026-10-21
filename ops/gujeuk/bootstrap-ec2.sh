#!/usr/bin/env bash
set -euo pipefail

sudo apt-get update
sudo apt-get install -y ca-certificates curl git unzip jq

sudo install -m 0755 -d /etc/apt/keyrings
curl -fsSL https://download.docker.com/linux/ubuntu/gpg \
  | sudo gpg --dearmor -o /etc/apt/keyrings/docker.gpg
sudo chmod a+r /etc/apt/keyrings/docker.gpg

. /etc/os-release
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.gpg] https://download.docker.com/linux/ubuntu ${VERSION_CODENAME} stable" \
  | sudo tee /etc/apt/sources.list.d/docker.list >/dev/null

sudo apt-get update
sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

TARGET_USER="${SUDO_USER:-ubuntu}"
sudo usermod -aG docker "$TARGET_USER"
sudo systemctl enable --now docker

sudo -u "$TARGET_USER" mkdir -p \
  "/home/$TARGET_USER/gujeuk-aws/prod" \
  "/home/$TARGET_USER/gujeuk-aws/stag" \
  "/home/$TARGET_USER/gujeuk-aws/backups"

echo "Docker installed. Log out and reconnect once so the docker group is applied."
