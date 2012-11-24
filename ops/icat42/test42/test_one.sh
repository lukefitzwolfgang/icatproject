#!/bin/bash
#
# $Id$
#
export             method=Authenticate
export         properties='example.properties'
export        trust_store=jssecacerts-www.icatproject.org
#
wsdl=`grep ^wsdl ${properties}`
file=`./change_wsdl.sh $wsdl`
[ "${file:0:1}" = "_" -a -f $file ] && trust_store=$file
java -Djavax.net.ssl.trustStore=$trust_store -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$method 

#java -Djavax.net.ssl.trustStore=$trust_store -Dhttp.proxyHost=wwwcache.rl.ac.uk -Dhttp.proxyPort=8080 -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$method 2> /dev/null
#
# - the end - 
#

