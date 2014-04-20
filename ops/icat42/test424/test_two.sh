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
      #echo ./icat_cli.sh $line
      ./icat_cli.sh $line |\
      while read output
      do
          echo $line : $output
      done
   fi
done < list_of_examples.txt
#
# - the end - 
#

