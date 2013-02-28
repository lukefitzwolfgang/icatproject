#!/bin/sh
#
# $Id$
#
java -Djavax.net.ssl.trustStore=jssecacerts -Dhttp.proxyHost=wwwcache.rl.ac.uk -Dhttp.proxyPort=8080 -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#
# - the end - 
#

