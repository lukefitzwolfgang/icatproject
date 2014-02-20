#!/bin/sh
#
# $Id$
#
sid=`./icat_cli.sh Login`
echo Login $sid rc=$?
./icat_cli.sh Logout $sid
echo Logout $sid rc=$?
#
# - the end - 
#

