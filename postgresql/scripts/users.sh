#!/bin/bash

_psql () { psql -v ON_ERROR_STOP=0 "$@" ; }

nr=1
seek=true

_psql \
<<'EOF'
REVOKE ALL ON schema public FROM public;
EOF


while [ "$seek" = true ]; do
    VAR_USER="POSTGRESQL_USER${nr}_USERNAME"
    VAR_PWD="POSTGRESQL_USER${nr}_PASSWORD"
    if [[ -z "${!VAR_USER+x}" ]] || [[ -z "${!VAR_PWD+x}" ]]; then
    	echo "Stopping on user $nr"
        seek=false
    else
        echo "found user $nr ${!VAR_USER}"
        _psql --set=user_name="${!VAR_USER}" \
              --set=user_pwd="${!VAR_PWD}" \
<<'EOF'
CREATE USER :"user_name" WITH PASSWORD :'user_pwd';
CREATE SCHEMA AUTHORIZATION :"user_name";
ALTER USER :"user_name" SET search_path = :'user_name';
EOF
        nr=$((nr+1))
    fi
done

