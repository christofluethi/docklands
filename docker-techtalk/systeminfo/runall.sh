#!/bin/bash

MEM="--memory=1024m"
CPUS="--cpus=6"

if [ ! -z "$1" ]; then
    MEM=$1
fi

if [ ! -z "$2" ]; then
    CPUS=$2
fi

PORT=8180

for STAGE in jre8slim jre8slimflags jre9slim jre9slimflags jre10slim jre11slim
do
    echo "#################################################"
    echo Stage ${STAGE} ${MEM} ${CPUS}
    echo "#################################################"
    docker build --target=${STAGE} . -t ${STAGE} > /dev/null
    echo "docker run -d --name=${STAGE} --rm -p ${PORT}:8080 ${MEM} ${CPUS} ${STAGE}"
    docker run -d --name=${STAGE} --rm -p ${PORT}:8080 ${MEM} ${CPUS} ${STAGE}

    CMD=$(docker inspect ${STAGE} --format '{{ .Config.Cmd }}')

    echo "Waiting for Endpoint localhost:${PORT} to become ready."
    while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' localhost:${PORT})" != "200" ]]; do echo -n "." && sleep 1; done

    echo
    echo "--"
    echo "Start CMD: $CMD"
    curl -s -o - localhost:${PORT}
    echo "--"

	while true; do
		read -p "Continue? " yn
		case $yn in
			[Yy]* ) break;;
			[Nn]* ) exit;;
			* ) echo "Please answer yes or no.";;
		esac
	done

    PORT=$((PORT+1))
    #echo "Killing ${STAGE}"
    #docker kill ${STAGE}
done
