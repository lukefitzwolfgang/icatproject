#!/bin/sh

# load configuration details
. ./properties.sh

asadmin="$GLASSFISH/bin/asadmin --port $PORT"

$asadmin delete-jdbc-resource jdbc/icat

$asadmin delete-jdbc-connection-pool icat

$asadmin delete-jms-resource jms/ICATQueueConnectionFactory
$asadmin delete-jms-resource jms/ICATTopicConnectionFactory
$asadmin delete-jms-resource jms/ICATQueue
$asadmin delete-jms-resource jms/ICATTopic


