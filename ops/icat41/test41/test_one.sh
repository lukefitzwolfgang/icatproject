#!/bin/sh
#
# $Id$
#
export method=Authenticate
#java -Djavax.net.ssl.trustStore=jssecacerts-www.icatproject.org -Dhttp.proxyHost=wwwcache.rl.ac.uk -Dhttp.proxyPort=8080 -cp icat_api_examples.jar:icat.client-4.1.0.jar uk.icat.examples.$*
java -Djavax.net.ssl.trustStore=jssecacerts-www.icatproject.org -cp icat_api_examples.jar:icat.client-4.1.0.jar uk.icat.examples.$method
#
# - the end - 
#

