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
export       time_command="/usr/bin/time -f %e"
export        dc_commands='1000*dpq'  # this means multiply by 1000, and quit 
export        time_format="%4.0f"     # this means only show the integer part
export     responseformat='      <numericvalue name="%s" desc="Response time (m-sec)" timestamp="%s">%s</numericvalue>\n'
export        test_failed="failed"
export        time_failed=10000       # this means report 10000 milliseconds if there is a failure
#
./change_icat.sh |\
while read icat
do
  ./change_icat.sh $icat
  date="`date $date_format`"
  test=`$time_command ./test_one.sh 2>time`
  if [ $? -eq 0 ]
  then
      time=`cat time`
      time=`echo $time $dc_commands | dc`
      time=`printf $time_format $time`
      rm time
  else
      test=$test_failed
      time=$time_failed
  fi
  wsdl=`grep ^wsdl $example_properties`
  echo $date, $host, $base, $icat, $test, $wsdl, $time
  rm $example_properties
done
#
# - the end -
#

