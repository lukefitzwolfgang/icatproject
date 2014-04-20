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

facilities = service.search(sessionId, "Facility [name = 'IdsPythonClientTest']")
if facilities:
    service.deleteMany(sessionId, facilities)
facility = factory.create("facility")
facility.name = "IdsPythonClientTest"
facility.id = service.create(sessionId, facility)
itype = factory.create("investigationType")
itype.facility = facility
itype.name = "fred"
itype.id = service.create(sessionId, itype)
inv = factory.create("investigation")
inv.facility = facility
inv.name = "fred"
inv.visitId = "42"
inv.title = "Dr Frederick Fred"
inv.type = itype
inv.id = service.create(sessionId, inv)
dtype = factory.create("datasetType")
dtype.facility = facility
dtype.name = "fred"
dtype.id = service.create(sessionId, dtype)
dataset = factory.create("dataset")
dataset.investigation = inv
dataset.name = "fred"
dataset.type = dtype
dataset.id = service.create(sessionId, dataset)
dformat = factory.create("datafileFormat")
dformat.facility = facility
dformat.name = "fred"
dformat.version = "4.2.0"
dformat.id = service.create(sessionId, dformat)

f = open("a.b", "w")
wibbles = ""
for i in range(1000): wibbles += (str(i) + " wibble ")
f.write(wibbles)
f.close()
f = open("a.b")
print "Freds before", service.search(sessionId, "Datafile [name = 'Fred']")
dfid = client.put(sessionId, f, "fred", dataset.id, dformat.id, "Description")
f.close()
print "Freds after", service.search(sessionId, "Datafile [name = 'Fred']")
