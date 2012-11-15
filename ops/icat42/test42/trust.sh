#!/bin/bash
#
# $Id$
#
export properties='_properties'
#
if [ ! "$1" = "" ] 
then
#
  wsdl=`grep ^wsdl ${1}${properties}`
  file=`./change_wsdl.sh $wsdl`
  #echo file=$file
  url=`./change_url.sh $wsdl`
  #echo url=$url
  if [ ! "${file:0:1}" = "_" ] 
    then 
    echo This url is not https: $url
    exit 1
  else
    echo | java -cp InstallCert.jar InstallCert $url > /dev/null
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

