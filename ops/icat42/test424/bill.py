#!/usr/bin/env python

import ids
from suds.client import Client

idsUrl = "https://sig-11.esc.rl.ac.uk:8181"
icatUrl = "https://sig-11.esc.rl.ac.uk:8181"
plugin = "db"
creds = "username root password password"

      
client = Client(icatUrl + "/ICATService/ICAT?wsdl")
service = client.service
factory = client.factory
credentials = factory.create("credentials")
creds = creds.split()
for i in range (0, len(creds) - 1, 2):
    entry = factory.create("credentials.entry")
    entry.key = creds[i]   
    entry.value = creds[i + 1]
    credentials.entry.append(entry)
sessionId = service.login(plugin, credentials)
client = ids.IdsClient(idsUrl)
idsUrl = idsUrl

facilities = service.search(sessionId, "Facility [name = 'billFacility']")
if facilities:
    service.deleteMany(sessionId, facilities)
facility = factory.create("facility")
facility.name = "billFacility"
facility.id = service.create(sessionId, facility)
itype = factory.create("investigationType")
itype.facility = facility
itype.name = "billInvestigationType"
itype.id = service.create(sessionId, itype)
inv = factory.create("investigation")
inv.facility = facility
inv.name = "billInvestigation"
inv.visitId = "43"
inv.title = "billInvestigationtitle"
inv.type = itype
inv.id = service.create(sessionId, inv)
dtype = factory.create("datasetType")
dtype.facility = facility
dtype.name = "billDatasetType"
dtype.id = service.create(sessionId, dtype)
dataset = factory.create("dataset")
dataset.investigation = inv
dataset.name = "billDatasetname"
dataset.type = dtype
dataset.id = service.create(sessionId, dataset)
dformat = factory.create("datafileFormat")
dformat.facility = facility
dformat.name = "billDatafileformat"
dformat.version = "4.3.0"
dformat.id = service.create(sessionId, dformat)

f = open("a.b", "w")
wibbles = ""
for i in range(100000): wibbles += (str(i) + " wibble ")
f.write(wibbles)
f.close()
f = open("a.b")
print "file bill before", service.search(sessionId, "Datafile [name = 'bill']")
dfid = client.put(sessionId, f, "bill", dataset.id, dformat.id, "Description")
f.close()
print "file bill after", service.search(sessionId, "Datafile [name = 'bill']")

