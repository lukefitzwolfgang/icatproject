#!/bin/sh
#
# $Id$
#
export         properties='example.properties'
export        trust_store=jssecacerts-www.icatproject.org
#
wsdl=`grep ^wsdl ${properties}`
file=`./change_wsdl.sh $wsdl`
[ "${file:0:1}" = "_" -a -f $file ] && trust_store=$file

java -Djavax.net.ssl.trustStore=$trust_store                                                          -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#java -Djavax.net.ssl.trustStore=jssecacerts -Dhttp.proxyHost=wwwcache.rl.ac.uk -Dhttp.proxyPort=8080 -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#
# - the end - 
#

