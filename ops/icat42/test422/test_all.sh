#!/bin/sh
#
# $Id$
#
# The purpose of this script is to provide the test information in csv format
#
export                pwd=`pwd`
export               base=`basename $pwd`
export               host=`hostname`
export example_properties=example.properties
export        date_format='-u +%d/%m/%Y%t%T'
#
./change_icat.sh |\
while read icat
do
    ./change_icat.sh $icat
    wsdl=`grep ^wsdl $example_properties`
    date="`date $date_format`"
    ./test_two.sh $line |\
    while read output
    do
        echo $date, $host, $base, $icat, $output, $wsdl
    done
    rm $example_properties
done
#
# - the end -
#

