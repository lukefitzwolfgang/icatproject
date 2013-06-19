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
      label=`echo $line | sed 's/ .*//'`
      label=${label##./}
      bash -c "$line" |\
      while read output
      do
          echo ${label%%.py} : $output
      done
   fi
done < list_of_py_examples.txt
#
# - the end - 
#

