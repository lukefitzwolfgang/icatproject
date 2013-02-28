#!/bin/sh
#
# $Id$
#
sid=`java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Login`
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.RemainingMinutes $sid
echo Sleep ...
sleep 6
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.RemainingMinutes $sid
echo Sleep ...
sleep 6
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.RemainingMinutes $sid
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Logout $sid
#
# - the end - 
#

