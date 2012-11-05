#!/bin/bash
#
# $Id$
#
export properties='_properties'
#
if [ ! "$1" = "" ] 
then
#
# The following code is really horrible.  Unless you understand it, do not change it!  It determines the url of the server and the name of the file for the trusted certificate
#
  wsdl=`grep ^wsdl ${1}${properties}`
  front=${wsdl#*=}; back=${front%/*}; url=${back%/*}
  url_0=${url}
  url_1=${url//https:\/\//}
  file=${url/#https:\/\//_}
  file=${file//./_}
  file=${file//:/_}

  if [ ! "${file:0:1}" = "_" ] 
    then 
    echo This url is not https: $url_0
    exit 1
  else
    echo | java -cp InstallCert.jar InstallCert $url_1 > /dev/null
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

