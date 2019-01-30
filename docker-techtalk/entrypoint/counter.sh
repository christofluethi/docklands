#!/bin/bash

trap 'echo "SIGINT caught! Gracefully exit in 2sec..." && sleep 2 && exit 0' SIGINT
trap 'echo "SIGTERM caught! Gracefully exit in 15sec..." && sleep 15 && exit 0' SIGTERM

COUNTER=0

unset END_AT

if [ -n "$1" ]; then
    COUNTER=$1
fi

if [ -n "$2" ]; then
    END=$2
fi

echo "Start at $(date)"
echo "PID: $$"

while [ 1 ]; do
    echo "Counter ${COUNTER}"
    if [ -n "${END}" ] && [ ${COUNTER} -ge ${END} ]; then
        break
    fi
    COUNTER=$((COUNTER + 1))
    sleep 1
done

echo "End at $(date)"
