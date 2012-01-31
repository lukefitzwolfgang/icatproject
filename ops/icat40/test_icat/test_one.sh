#!/bin/sh
#
# $Id$
#
#java -Dhttp.proxyHost=wwwcache.rl.ac.uk -Dhttp.proxyPort=8080 -cp icat_api_examples.jar:../icat-client-1583.jar uk.icat.examples.$*
java -cp icat_api_examples.jar:../icat-client-1583.jar uk.icat.examples.$*
#
# - the end - 
#

