#!/usr/bin/env python
#
# $Id$
#
# The purpose of this obtain the API version from the ICAT
#
# python getApiVersion.py http://localhost:8080
#

from suds.client import Client

import logging
import sys

logging.basicConfig(level=logging.CRITICAL)

args = sys.argv
if len(args) != 2:
    print >> sys.stderr, "This must have one fixed argument: protocol-hostname-port. For example:\n", args[0], "https://example.com:8181 \n"
    sys.exit(1)

protocolHostAndPort = args[1]
client = Client(protocolHostAndPort + "/ICATService/ICAT?wsdl")
service = client.service
print service.getApiVersion (service)

#
# - the end -
#
