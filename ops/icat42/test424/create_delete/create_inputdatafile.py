#!/usr/bin/env python
#
# $Id$
#
# The purpose of this script is to insert some content into the ICAT
#
# python insert.py http://localhost:8080 db username CIC password password
#

from suds.client import Client

import logging
import sys

logging.basicConfig(level=logging.CRITICAL)

args = sys.argv
if len(args) < 3 or len(args) % 2 == 0:
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

for i in range (3, len(args), 2): 
	entry = factory.create("credentials.entry")
	entry.key = args[i]
	entry.value = args[i + 1]
	credentials.entry.append(entry)
sessionId = service.login(plugin, credentials)

# datafile 
ds = service.search(sessionId, "Dataset[name='My Dataset']")
df1 = service.search(sessionId, "Datafile[name='My Datafile 1']")
df2 = service.search(sessionId, "Datafile[name='My Datafile 2']")
app = service.search(sessionId, "Application[name='My App']")

#  job
if ( 1 == 1 ):
    print "Job: creating job ..."
    job = factory.create("job")
    job.application = app
    job.id = service.create(sessionId, job)

# inputDataset
if ( 1 == 1 ):
    print "creating inputDataset ..."
    ids = factory.create("inputDataset")
    ids.dataset = ds
    ids.job = job
    ids.id = service.create(sessionId, ids)

    print "creating outputDataset ..."
    ods = factory.create("outputDataset")
    ods.dataset = ds
    ods.job = job
    ods.id = service.create(sessionId, ods)

if ( 1 == 1 ):
    print "creating inputDatafile ..."
    idf = factory.create("inputDatafile")
    idf.datafile = df1
    idf.job = job
    idf.id = service.create(sessionId, idf)
    print "creating outputDatafile ..."
    odf = factory.create("outputDatafile")
    odf.datafile = df2
    odf.job = job
    odf.id = service.create(sessionId, odf)

service.logout(sessionId)

#
# - the end -
#
