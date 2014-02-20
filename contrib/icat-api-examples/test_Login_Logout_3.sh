#!/bin/sh
#
# $Id$
#
sid=`./icat_cli.sh Login`
echo $sid
for i in `seq 10 -1 0`
do
  printf "Remaining minutes %6.1f %s\n" `./icat_cli.sh RemainingMinutes $sid` $sid
  echo Sleep ... $i
  sleep 2
done
echo Done
./icat_cli.sh Logout $sid
#
# - the end - 
#

