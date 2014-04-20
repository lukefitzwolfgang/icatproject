#!/bin/bash
#
# $Id$
#
export properties='_properties'
export shost=${https_proxy%:*} 
export sport=${https_proxy#*:}
export  host=${http_proxy%:*} 
export  port=${http_proxy#*:}
export     Dshost=""
export      Dhost=""
export     Dsport=""
export      Dport=""
export    DbyPass=""
[ "$shost"    != "" ] && export  Dshost="-Dhttps.proxyHost=$shost"
[ "$host"     != "" ] && export   Dhost="-Dhttp.proxyHost=$host"
[ "$sport"    != "" ] && export  Dsport="-Dhttps.proxyPort=$sport"
[ "$port"     != "" ] && export   Dport="-Dhttp.proxyPort=$port"
[ "$no_proxy" != "" ] && export DbyPass="-Dhttp.nonProxyHosts=$no_proxy"
#
#set -x
if [ ! "$1" = "" ]
then
#
  wsdl=`grep ^wsdl ${1}${properties}`
  file=`./change_wsdl.sh $wsdl`
  #echo file=$file
  url=`./change_url.sh $wsdl`
  echo url=$url
  if [ ! "${file:0:1}" = "_" ]
    then
    echo This url is not https: $url
    exit 1
  else
    echo | java $DbyPass $Dshost $Dhost $Dsport $Dport -cp InstallCert.jar InstallCert $url > /dev/null
    mv jssecacerts $file
    ls -l $file
  fi
else
  echo $0 name-of-domain-eg-domain1
  exit 1
fi
#
# - the end -
#

