#!/bin/bash
#
# $Id$
#
#set -x
java                                                           -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#java -Dhttp.proxyHost=proxy.esrf.fr -Dhttp.proxyPort=3128 -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.$*
#
# - the end - 
#

