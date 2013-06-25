#!/bin/bash
#
# $Id$
#
export         properties='example.properties'
export        trust_store=
#
wsdl=`grep ^wsdl ${properties}`
file=`./change_wsdl.sh $wsdl`
[ "${file:0:1}" = "_" -a -f $file ] && trust_store="-Djavax.net.ssl.trustStore=$file"
#set -x
java $trust_store                                                          -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#java $trust_store  -Dhttp.proxyHost=wwwcache.rl.ac.uk  -Dhttp.proxyPort=8080 \
                   -Dhttps.proxyHost=wwwcache.rl.ac.uk -Dhttps.proxyPort=8080 -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#
# - the end - 
#

