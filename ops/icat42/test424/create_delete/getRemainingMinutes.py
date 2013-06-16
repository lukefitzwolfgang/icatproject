#!/usr/bin/env python
#
# $Id$
#
# The purpose of this script is to retrieve the remaining minutes from the ICAT
#
# python Search.py http://localhost:8080 db username CIC password password "Dataset"
#

from suds.client import Client

import logging
import sys

logging.basicConfig(level=logging.CRITICAL)

args = sys.argv
if len(args) <> 3:
    print >> sys.stderr, "This must have two arguments: icat SessionId"
    sys.exit(1)

protocolHostAndPort = args[1]

client = Client(protocolHostAndPort + "/ICATService/ICAT?wsdl")
service = client.service
factory = client.factory
print service.getRemainingMinutes(args[2])

#
# - the end -
#
