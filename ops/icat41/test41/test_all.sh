#!/bin/bash
#
# $Id$
#
export                pwd=`pwd`
export               base=`basename $pwd`
export               host=`hostname`
export        date_format='-u +%d/%m/%Y%t%T'
export example_properties='example.properties'
#
./change_icat.sh |\
while read icat
do
  ./change_icat.sh $icat
  date="`date $date_format`"
  test=`./test_one.sh`
  wsdl=`grep ^wsdl $example_properties`
  echo $date, $host, $base, $icat, $test, $wsdl
  rm $example_properties
done
#
# - the end - 
#

