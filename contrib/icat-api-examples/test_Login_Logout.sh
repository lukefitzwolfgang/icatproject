#!/bin/sh
#
# $Id$
#
 java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Logout \
`java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Login`
#
# - the end - 
#

