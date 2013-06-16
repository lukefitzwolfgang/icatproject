#!/usr/bin/env python
#
# $Id$
#
# The purpose of this script is to authenticate to the ICAT
#
# python authenticate.py http://localhost:8080 db username CIC password password "Dataset"
#

from suds.client import Client

import logging
import sys

logging.basicConfig(level=logging.CRITICAL)

args = sys.argv
if len(args)-1 < 3 or len(args)-1 % 2 == 0:
    print >> sys.stderr, "This must have two fixed arguments: protocol-hostname-port plugin-mnemonic \nfollowed by pairs of arguments to represent the credentials. For example:\n", args[0], "https://example.com:8181 db \\\n username root \\\n password guess\n"
    sys.exit(1)

protocolHostAndPort = args[1]
plugin = args[2]

client = Client(protocolHostAndPort + "/ICATService/ICAT?wsdl")
service = client.service
factory = client.factory

#
# create credentials for User
#
credentials = factory.create("credentials")

for i in range (3, len(args)-1, 2): 
	entry = factory.create("credentials.entry")
	entry.key = args[i]
	entry.value = args[i + 1]
	credentials.entry.append(entry)
sessionId = service.login(plugin, credentials)

# application 
print  "authenticator = " + plugin + " : Session ID = " + sessionId + " : API Version = " + service.getApiVersion()

print "authenticator = db : Session ID = de351f3f-f029-4d41-84e9-7365aa6d181b : API Version = 4.2.0"

service.logout(sessionId)

#
# - the end -
#
