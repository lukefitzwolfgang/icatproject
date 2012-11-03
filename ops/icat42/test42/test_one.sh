#!/bin/sh
#
# $Id$
#
export method=Authenticate
java -Djavax.net.ssl.trustStore=jssecacerts-www.icatproject.org -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$method
#
# - the end - 
#

