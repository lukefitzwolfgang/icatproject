#!/bin/sh
#
# $Id$
#
sid=`java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Login`
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Logout $sid
#
# - the end - 
#

