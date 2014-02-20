#!/bin/bash
#
# $Id$
#
export total=0
export return_code=0
while read line
do
   if [ "${line:0:1}" != "#" -a ${#line} != 0 ]
   then
      result="`./icat_cli.sh $line`"
      return_code=$?
      total=`expr $total + $return_code`
      echo $line , $result , $return_code 
   fi
done < list_of_examples.txt
[ $total = 0 ] && echo Result: success || echo Result: failure
exit $total
#
# - the end - 
#

