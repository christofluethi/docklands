#!/bin/bash

MEM="--memory=1024m"
CPUS="--cpus=6"

if [ ! -z "$1" ]; then
    MEM=$1
fi

if [ ! -z "$2" ]; then
    CPUS=$2
fi

for STAGE in jre8slim jre8slimflags jre9slim jre9slimflags jre10slim jre11slim
do
    echo "#################################################"
    echo Stage ${STAGE} ${MEM} ${CPUS}
    echo "#################################################"
    docker build --target=${STAGE} . -t ${STAGE} > /dev/null
    echo "docker run -d --name=${STAGE} --rm ${MEM} ${CPUS} ${STAGE}"
    docker run -d --name=${STAGE} --rm ${MEM} ${CPUS} ${STAGE}

    IP=$(docker inspect ${STAGE} --format '{{ .NetworkSettings.IPAddress }}')
    CMD=$(docker inspect ${STAGE} --format '{{ .Config.Cmd }}')

    echo "Waiting for Endpoint ${IP}:8080 to become ready."
    while [[ "$(curl -s -o /dev/null -w ''%{http_code}'' ${IP}:8080)" != "200" ]]; do echo -n "." && sleep 1; done

    echo
    echo "--"
    echo "Start CMD: $CMD"
    curl -s -o - ${IP}:8080
    echo "--"

	while true; do
		read -p "Continue? " yn
		case $yn in
			[Yy]* ) break;;
			[Nn]* ) exit;;
			* ) echo "Please answer yes or no.";;
		esac
	done

    #echo "Killing ${STAGE}"
    #docker kill ${STAGE}
done
