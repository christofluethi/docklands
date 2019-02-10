#!/usr/bin/bash

COUNTER=0;
while [ 1 ]
do
    if [ -f /tmp/unhealthy ]; then
        echo -n "!"
    else
        echo -n "."
    fi
    COUNTER=$((COUNTER+1))

    if [ $((${COUNTER} % 10)) == 0 ]; then
        echo " | $COUNTER"
    fi 

    sleep 1
done
