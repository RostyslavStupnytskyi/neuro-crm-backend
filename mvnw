#!/bin/sh
set -e
MVN_VERSION=3.9.6
WRAPPER_DIR="$(dirname "$0")/.mvn/wrapper"
JAR="$WRAPPER_DIR/maven-wrapper.jar"
if [ ! -f "$JAR" ]; then
  echo "Maven wrapper JAR missing" >&2
  exit 1
fi
exec java -jar "$JAR" "$@"
