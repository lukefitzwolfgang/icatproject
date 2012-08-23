#!/bin/sh

props=./glassfish.props
if [ ! -f $props ]; then
    echo There is no $props file
    exit 1
fi

. $props

for key in glassfish port; do
    eval val='$'$key
    if [ -z "$val" ]; then
        echo $key must be set in $props file
        exit 1
    fi
done

asadmin="$glassfish/bin/asadmin --port $port"

$asadmin delete-jdbc-resource jdbc/authn_db

$asadmin delete-jdbc-connection-pool authn_db


