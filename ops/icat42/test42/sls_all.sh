#!/bin/sh
#
# $Id$
#
# The purpose of this script is to provide the test time information in a format for the SLS monitor
#
export                pwd=`pwd`
export               base=`basename $pwd`
export               host=`hostname`
export example_properties='example.properties'
export            sls_xml='sls.xml'
touch            $sls_xml
export          timestamp=`date -u -r $sls_xml +'%Y-%m-%dT%H:%M:%S+00:00'`
export     responseformat='      <numericvalue name="%s" desc="Response time (m-sec)" timestamp="%s">%s</numericvalue>\n'

cat > $sls_xml <</eof
<?xml version="1.0" encoding="utf-8"?>
<serviceupdate xmlns="http://sls.cern.ch/SLS/XML/update">
  <id>Pandata - SCD</id>
  <availability>100</availability>
  <timestamp>$timestamp</timestamp>
  <data>
/eof

./change_icat.sh |\
while read icat
do
  ./change_icat.sh $icat
  date="`date $date_format`"
  test=`/usr/bin/time -f %e ./test_one.sh 2>time`
  time=`cat time`
  rm time
  time=`echo $time 1000*dpq |dc`
  time=`printf "%4.0f" $time`
  printf "$responseformat" $icat $timestamp ${time} >> $sls_xml
  rm $example_properties
done

cat >> $sls_xml <</eof
   </data>
</serviceupdate>
/eof
#
# - the end - 
#

