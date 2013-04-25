#!/bin/bash
#
# $Id$
#
# This procedure accepts an argument with the url for a web service and transforms it
#
# For example:
# ./change_wsdl.sh wsdl.location=https://wwws.esrf.fr/icat/ICATService/ICAT?wsdl
#
# returns:
#
# _wwws_esrf_fr
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

