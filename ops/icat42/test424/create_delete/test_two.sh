#!/bin/bash
#
# $Id$
#
export       total=0
export return_code=0
export        hash='#'
while read line
do
   if [ "${line:0:1}" != $hash -a "${#line}" != 0 ]
   then
      ./test_2.sh $line |\
      while read output
      do
          echo $line : $output
      done
   fi
done < list_of_examples.txt
#
# - the end - 
#

