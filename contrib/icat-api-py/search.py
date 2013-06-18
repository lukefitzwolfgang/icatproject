#!/usr/bin/env python
#
# $Id$
#
# The purpose of this script is to Search the ICAT
#
# python search.py http://localhost:8080 db username CIC password password "Dataset"
# python search.py http://localhost:8080 $sessionId                        "Dataset"
#

from suds.client import Client

import logging
import sys

logging.basicConfig(level=logging.CRITICAL)

args = sys.argv
protocolHostAndPort = args[1]
client = Client(protocolHostAndPort + "/ICATService/ICAT?wsdl")
service = client.service
factory = client.factory

if len(args) == 4:
	sessionId = args[2]

elif len(args)-1 < 3 or len(args)-1 % 2 == 0:
	print >> sys.stderr, "This must have two fixed arguments: protocol-hostname-port authenticator-mnemonic \nfollowed by pairs of arguments to represent the credentials. For example:\n", args[0], "https://example.com:8181 db \\\n username root \\\n password guess Search-argument\n"
	sys.exit(1)
else:
#
# create credentials for User
#
	authenticator = args[2]
	credentials = factory.create("credentials")
	for i in range (3, len(args)-1, 2): 
		entry = factory.create("credentials.entry")
		entry.key = args[i]
		entry.value = args[i + 1]
		credentials.entry.append(entry)
	sessionId = service.login(authenticator, credentials)

# application 
print service.search(sessionId, args[len(args)-1])

if len(args) != 4:
	service.logout(sessionId)

#
# - the end -
#
