#!/usr/bin/env python
#
# $Id$
#
# The purpose of this script is to insert the content inserted by insert.py from the ICAT
#
# python remove.py http://localhost:8080 db username CIC password password
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

# datafile1
datafile1 = service.search(sessionId, "Datafile[name='My Datafile 1']")
if len(datafile1): 
    print "Datafile: 'My Datafile 1' already exists ... so delete it"
    service.delete(sessionId, datafile1)
else:
    print "Datafile: 'My Datafile 1' does not exist"

# datafile2
datafile2 = service.search(sessionId, "Datafile[name='My Datafile 2']")
if len(datafile2): 
    print "Datafile: 'My Datafile 2' already exists ... so delete it"
    service.delete(sessionId, datafile2)
else:
    print "Datafile: 'My Datafile 2' does not exist"

# dataset
datasets = service.search(sessionId, "Dataset[name='My Dataset']")
if len(datasets): 
    print "Dataset: 'My Dataset' already exists ... so delete it"
    dataset = datasets[0]
    service.delete(sessionId, dataset)
else:
    print "Dataset: 'My Dataset' does not exist"

# dataset type
dataset_types = service.search(sessionId, "DatasetType[name='My Dataset Type']")
if len(dataset_types): 
    print "DatasetType: 'My Dataset Type' already exists ... so delete it"
    datasetType = dataset_types[0]
    service.delete(sessionId, datasetType)
else:
    print "DatasetType: 'My Dataset Type' does not exist"

# investigation
investigations = service.search(sessionId, "Investigation[name='My Investigation']")
if len(investigations): 
    print "Investigation: 'My Investigation' already exists ... so delete it"
    investigation = investigations[0]
    service.delete(sessionId, investigation)
else:
    print "Investigation: 'My Investigation' does not exist"

# investigation type
investigation_types = service.search(sessionId, "InvestigationType[name='My Investigation Type']")
if len(investigation_types): 
    print "InvestigationType: 'My Investigation Type' already exists ... so delete it"
    investigation_type = investigation_types[0]
    service.delete(sessionId, investigation_type)
else:
    print "InvestigationType: 'My Investigation Type' does not exist"

# instrument
instrument = service.search(sessionId, "Instrument[name='My Instrument']")
if len(instrument): 
    print "Instrument: 'My Instrument' already exists ... so delete it"
    service.delete(sessionId, instrument)
else:
    print "Instrument: 'My Instrument' does not exist"

# facility
facilities = service.search(sessionId, "Facility[name='My Facility']")
if len(facilities): 
    print "Facility: 'My Facility' already exists ... so delete it"
    facility = facilities[0]
    service.delete(sessionId, facility)
else:
    print "Facility: 'My Facility' does not exist"

service.logout(sessionId)

#
# - the end -
#
