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

# facility
facilities = service.search(sessionId, "Facility[name='My Facility']")
if len(facilities): 
    print "Facility: 'My Facility' already exists ..."
    facility = facilities[0]
else:
    print "Facility: creating 'My Facility' ..."
    facility = factory.create("facility")
    facility.name = "My Facility"
    facility.fullName = "This is a test facility"
    facility.description = "This is a test facility"
    facility.id = service.create(sessionId, facility)

# instrument
instrument = service.search(sessionId, "Instrument[name='My Instrument']")
if len(instrument): 
    print "Instrument: 'My Instrument' already exists ..."
else:
    print "instrument: creating 'My Instrument' ..."
    instrument = factory.create("instrument")
    instrument.name = "My Instrument"
    instrument.description = "This is a test instrument"
    instrument.fullName = "This is the full name of the test instrument"
    instrument.type = "This is the type of the test instrument"
    instrument.facility = facility
    instrument.id = service.create(sessionId, instrument)

# investigationType
investigation_types = service.search(sessionId, "InvestigationType[name='My Investigation Type']")
if len(investigation_types): 
    print "InvestigationType: 'My Investigation Type' already exists ..."
    investigation_type = investigation_types[0]
else:
    print "InvestigationType: creating 'My Investigation Type' ..."
    investigation_type = factory.create("investigationType")
    investigation_type.name = "My Investigation Type"
    investigation_type.description = "This is a test investigation type"
    investigation_type.facility = facility
    #investigation_type.instrument = instrument
    investigation_type.id = service.create(sessionId, investigation_type)

# investigation
investigations = service.search(sessionId, "Investigation[name='My Investigation']")
if len(investigations): 
    print "Investigation: 'My Investigation' already exists ..."
    investigation = investigations[0]
else:
    print "Investigation: creating 'My Investigation' ..."
    investigation = factory.create("investigation")
    investigation.name = "My Investigation"
    investigation.title = "This is a test investigation"
    investigation.facility = facility
    investigation.instrument = instrument
    investigation.type = investigation_type
    investigation.id = service.create(sessionId, investigation)


# datasetType
dataset_types = service.search(sessionId, "DatasetType[name='My Dataset Type']")
if len(dataset_types): 
    print "DatasetType: 'My Dataset Type' already exists ..."
    dataset_type = dataset_types[0]
else:
    print "DatasetType: creating 'My Dataset Type' ..."
    dataset_type = factory.create("datasetType")
    dataset_type.name = "My Dataset Type"
    dataset_type.description = "This is an example of a dataset type"
    dataset_type.facility = facility
    dataset_type.id = service.create(sessionId, dataset_type)

# dataset
datasets = service.search(sessionId, "Dataset[name='My Dataset']")
if len(datasets): 
    print "Dataset: 'My Dataset' already exists ..."
    dataset = datasets[0]
else:
    print "Dataset: creating 'My Dataset' ..."
    dataset = factory.create("dataset")
    dataset.name = "My Dataset"
    dataset.investigation = investigation
    dataset.type = dataset_type
    dataset.id = service.create(sessionId, dataset)

# datafile1
datafile1 = service.search(sessionId, "Datafile[name='My Datafile 1']")
if len(datafile1): 
    print "Datafile: 'My Datafile 1' already exists ..."
else:
    print "Datafile: creating 'My Datafile 1' ..."
    datafile1 = factory.create("datafile")
    datafile1.name = "My Datafile 1"
    datafile1.description = "This is a description of data file 1"
    datafile1.dataset = dataset
    datafile1.location = "datafile1.txt"
    datafile1.id = service.create(sessionId, datafile1)

# datafile2
datafile2 = service.search(sessionId, "Datafile[name='My Datafile 2']")
if len(datafile2): 
    print "Datafile: 'My Datafile 2' already exists ..."
else:
    print "Datafile: creating 'My Datafile 2' ..."
    datafile2 = factory.create("datafile")
    datafile2.name = "My Datafile 2"
    datafile2.description = "This is a description of data file 2"
    datafile2.dataset = dataset
    datafile2.location = "datafile2.txt"
    datafile2.id = service.create(sessionId, datafile2)

service.logout(sessionId)

#
# - the end -
#
