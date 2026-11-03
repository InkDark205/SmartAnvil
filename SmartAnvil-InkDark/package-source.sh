#!/usr/bin/env sh
set -eu
cd "$(dirname "$0")"
cd ..
zip -r SmartAnvil-InkDark-source.zip SmartAnvil-InkDark -x '*/build/*' '*/.gradle/*'
echo "Created ../SmartAnvil-InkDark-source.zip"
