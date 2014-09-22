import ConfigParser
import icat_ingestor
import sys

"""
Sample script to create Instruments in an ICAT instance.
It reads the Instrument description from the instruments.cfg
configuration file, and the ICAT access parameters from
icat.cfg
"""


config = ConfigParser.SafeConfigParser()
config.optionxform = str
config.read("instruments.cfg")
ingest = icat_ingestor.ICATIngestor()
ingest.read_icat_config("icat.cfg", "psi")

ingest.new_facility = True
try:
    ingest.connect()
except:
    print sys.exc_info()
    print ingest.options
    sys.exit(-1)

#ingest.check_entry("Instrument", "X02DA")
ingest.schema = {}
for section in config.sections():
    args = dict(config.items(section))
    sect = section.split("/")
    if len(sect) == 2:
        entity_type, entity_name = section.split("/")
        args["name"] = entity_name
    elif len(sect) == 1:
        entity_type = sect[0]

    ingest.schema[entity_type] = args

    entry = ingest.insert_entry(entity_type, ingest.schema)
    if entry is not None:
        print "entry:", entry

ingest.disconnect()
