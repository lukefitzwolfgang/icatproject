#!/bin/bash
#
# $Id$
#
# This procedure accepts an argument with the url for a web service and transforms it
#
# For example:
# ./change_url.sh wsdl.location=https://wwws.esrf.fr/icat/ICATService/ICAT?wsdl
#
# returns:
#
# wwws.esrf.fr
#
#
# The following code is really horrible.  Unless you understand it, do not change it!  It determines the name of the url for the server providing the ICAT
#
wsdl=$1
front=${wsdl#*=}
back=${front%/*} 
url=${back%/*}
url_1=${url/#https:\/\// }
echo ${url_1%/*}
#
# - the end - 
#

