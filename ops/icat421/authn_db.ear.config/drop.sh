#!/bin/sh

# load configuration details
. ./properties.sh

asadmin="$GLASSFISH/bin/asadmin --port $PORT"

$asadmin delete-jdbc-resource jdbc/authn_db

$asadmin delete-jdbc-connection-pool authn_db


