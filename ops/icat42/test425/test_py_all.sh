#!/bin/sh
#
# $Id$
#
# The purpose of this script is to provide the test information in csv format
#
#set -x
export                pwd=`pwd`
export               base=`basename $pwd`
export               host=`hostname`
export example_properties=example.properties
export        date_format='-u +%d/%m/%Y%t%T'
#
for i in *.ict
do
    . ./$i
    ./test_py_two.sh |\
    while read output
    do
        date="`date $date_format`"
        echo $date, $host, $base, ${i%%.ict}, $output
    done
done
#
# - the end -
#

