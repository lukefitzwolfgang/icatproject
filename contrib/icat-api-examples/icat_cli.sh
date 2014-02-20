#!/bin/bash
#
# $Id$
#
export properties='example.properties'
export      shost=${https_proxy%:*}
export      sport=${https_proxy#*:}
export       host=${http_proxy%:*}
export       port=${http_proxy#*:}
export     Dshost=""
export      Dhost=""
export     Dsport=""
export      Dport=""
export    DbyPass=""
[ "$shost"      != "" ] && export  Dshost="-Dhttps.proxyHost=$shost"
[ "$host"       != "" ] && export   Dhost="-Dhttp.proxyHost=$host"
[ "$sport"      != "" ] && export  Dsport="-Dhttps.proxyPort=$sport"
[ "$port"       != "" ] && export   Dport="-Dhttp.proxyPort=$port"
[ "$no_proxy"   != "" ] && export DbyPass="-Dhttp.nonProxyHosts=$no_proxy"
export CLASSPATH=\
icat_api_examples.jar:\
icat.client-4.3.0.jar:\
ids.client-1.0.0.jar:\
jackson-databind-2.2.0.jar:\
jackson-core-2.2.0.jar:\
jackson-annotations-2.2.0.jar

trust_store=
wsdl=`grep ^wsdl ${properties}`
file=`./change_wsdl.sh $wsdl`
[ "${file:0:1}" = "_" -a -f $file ] && trust_store="-Djavax.net.ssl.trustStore=$file"

#set -x
java $trust_store $DbyPass $Dshost $Dhost $Dsport $Dport  uk.icat.examples.$*

#
# - the end -
#

