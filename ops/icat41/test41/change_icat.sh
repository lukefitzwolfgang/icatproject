#! /bin/bash
#
export properties=example.properties

if [ "$1" = "" ]  
then
  # [ -h $properties ] && link=`ls -l example.properties` echo ${link#*.}
  for i in *_properties
  do 
    export j=${i%%_properties}
    echo ${j}
  done
  exit 1
fi

export file=${1}_properties
if [ ! -f "$file" ]  
then
  echo File not found - $file
  echo 'Please give an argument to identify the icat'
  echo For example one of the following:
  for i in *_properties
  do 
    export j=${i##_properties}
    echo ${j}
  done
  exit 2
fi

[ -h $properties ] && rm $properties
ln -s $file $properties
#
# - the end -
#

