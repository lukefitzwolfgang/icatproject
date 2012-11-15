#!/bin/bash
#
# $Id$
#
# The following code is really horrible.  Unless you understand it, do not change it!  It determines the name of the file for the trusted certificate
#
wsdl=$1
front=${wsdl#*=}
back=${front%/*} 
url=${back%/*}
file=${url/#https:\/\//_}  
file=${file//./_}
file=${file//:/_}
echo ${file%/*}
#
# - the end - 
#

