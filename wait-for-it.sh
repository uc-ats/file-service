#!/usr/bin/env bash
# wait-for-it.sh — wait for a host:port to be available
set -e

host="$1"
shift
port="$1"
shift

until nc -z "$host" "$port"; do
  echo "Waiting for $host:$port..."
  sleep 2
done

exec "$@"