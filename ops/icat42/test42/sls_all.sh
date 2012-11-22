#!/bin/sh
#
# $Id$
#
# The purpose of this script is to provide the test time information in a format for the SLS monitor
#
export                pwd=`pwd`
export               base=`basename $pwd`
export               host=`hostname`
export example_properties=example.properties
export            sls_xml='sls.xml'
touch            $sls_xml
export          timestamp=`date -u -r $sls_xml +'%Y-%m-%dT%H:%M:%S+00:00'` # this means get the time of the file in UT and present it in the format shown
export       time_command="/usr/bin/time -f %e"
export        dc_commands='1000*dpq'  # this means multiply by 1000, and quit 
export        time_format="%4.0f"     # this means only show the integer part
export     responseformat='      <numericvalue name="%s" desc="Response time (m-sec)" timestamp="%s">%s</numericvalue>\n'
export        test_failed="failed"
export        time_failed=10000       # this means report 10000 milliseconds if there is a failure


cat > $sls_xml <</eof
<?xml version="1.0" encoding="utf-8"?>
<serviceupdate xmlns="http://sls.cern.ch/SLS/XML/update">
  <id>Pandata</id>
  <availability>100</availability>
  <timestamp>$timestamp</timestamp>
  <data>
/eof

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
  else
      test=$test_failed
      time=$time_failed
  fi
  printf "$responseformat" $icat $timestamp ${time} >> $sls_xml
  rm time
  rm $example_properties
done

cat >> $sls_xml <</eof
   </data>
</serviceupdate>
/eof
#
# - the end -
#

