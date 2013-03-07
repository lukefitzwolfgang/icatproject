#!/bin/sh

# load configuration details
. ./properties.sh

asadmin="$GLASSFISH/bin/asadmin --port $PORT"

$asadmin set server.http-service.access-log.format="common"
$asadmin set server.http-service.access-logging-enabled=true

$asadmin create-jdbc-connection-pool \
   --datasourceclassname ${ICAT_DRIVER} --restype javax.sql.DataSource \
   --failconnection=true --steadypoolsize 2 --maxpoolsize 8 --ping \
   --property ${ICAT_PROPERTIES} \
   icat
$asadmin create-jdbc-resource --connectionpoolid icat jdbc/icat

$asadmin create-jms-resource --restype javax.jms.QueueConnectionFactory jms/ICATQueueConnectionFactory
$asadmin create-jms-resource --restype javax.jms.TopicConnectionFactory jms/ICATTopicConnectionFactory
$asadmin create-jms-resource --restype javax.jms.Queue jms/ICATQueue
$asadmin create-jms-resource --restype javax.jms.Topic jms/ICATTopic

$asadmin set server.thread-pools.thread-pool.http-thread-pool.max-thread-pool-size=128
