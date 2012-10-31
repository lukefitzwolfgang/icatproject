#!/bin/sh
#
# $Id$
#
export list_of_icats=list_of_icats.txt
export           pwd=`pwd`
export          base=`basename $pwd`
export          host=`hostname`
#
./change_icat.sh > $list_of_icats
while read icat
do
  ./change_icat.sh $icat
  date="`date`"
  test=`./test_one.sh`
  wsdl=`grep ^wsdl example.properties`
  echo $date, $host, $base, $icat, $test, $wsdl
done < $list_of_icats
#
# - the end - 
#

