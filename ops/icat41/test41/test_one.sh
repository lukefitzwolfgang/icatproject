#!/bin/bash
#
# $Id$
#
export             method=Authenticate
export         properties='example.properties'
export        trust_store=jssecacerts-www.icatproject.org
#
# The following code is really horrible.  Unless you understand it, do not change it!  It determines the name of the file for the trusted certificate
#
wsdl=`grep ^wsdl ${properties}`
front=${wsdl#*=};  back=${front%/*}; url=${back%/*}
file=${url/#https:\/\//_};  
file=${file//./_}
file=${file//:/_}
[ "${file:0:1}" = "_" -a -f $file ] && trust_store=$file
java -Djavax.net.ssl.trustStore=$trust_store -cp icat_api_examples.jar:icat.client-4.1.0.jar uk.icat.examples.$method

#java -Djavax.net.ssl.trustStore=$trust_store -Dhttp.proxyHost=wwwcache.rl.ac.uk -Dhttp.proxyPort=8080 -cp icat_api_examples.jar:icat.client-4.1.0.jar uk.icat.examples.$method
#
# - the end - 
#

