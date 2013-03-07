#!/bin/sh

# load configuration details
. ./properties.sh

asadmin="$GLASSFISH/bin/asadmin --port $PORT"

$asadmin create-jdbc-connection-pool \
   --datasourceclassname ${AUTHDB_DRIVER} --restype javax.sql.DataSource \
   --failconnection=true --steadypoolsize 2 --maxpoolsize 8 --ping \
   --property ${AUTHDB_PROPERTIES} \
   authn_db
$asadmin create-jdbc-resource --connectionpoolid authn_db jdbc/authn_db
