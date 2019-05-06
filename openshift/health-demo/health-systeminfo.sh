#!/bin/bash
URL=$1

while true
do
    DATA="`curl -s $URL`"
    HN="`echo "$DATA" | grep Hostname | awk '{ print $3 }'`"
    ID="`echo $HN | awk -F"-" '{ print $3 }'`"
    READY="`echo "$DATA" | grep "/ready" | awk '{ print $6 }'`"
    LIVE="`echo "$DATA" | grep "/live" | awk '{ print $6 }'`"
    HEXID="`echo -n $HN | md5sum | cut -c1-6`"
    DEC=$((16#${HEXID}))
    C=$(($DEC % 229))

    echo -e "\e[48;5;${C}m ${HN} \e[0m /ready=$READY /live=$LIVE"

    #echo "$HN $ID $HEXID $DEC $NORMALIZED"

    sleep 2
done

