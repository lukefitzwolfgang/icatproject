#!/usr/bin/env python

"""
ICAT HDF5 ingestor example. It reads an HDF5-to-ICAT translation
configuration file, reads information from HDF5, and inject data
into the provided ICAT service.
"""

import ConfigParser
from optparse import OptionParser
import sys
import h5py
from os import stat
from datetime import datetime
import logging

import icat_ingestor

__copyright__ = "Copyright 2014, Paul Scherrer Institute"
__credits__ = [""]
__license__ = "LGPL"
__version__ = "3"


### Logger configuration
logger = logging.getLogger("icat_ingestor")
#formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logging.basicConfig(level=logging.INFO, format='[%(levelname)s] %(name)s - %(message)s')


if __name__ == "__main__":
    usage = "usage: %prog [options] file.h5"
    parser = OptionParser(usage=usage)
    parser.add_option("-c", "--cfg", dest="cfg", default=None,
                      help="Configuration file used to translate HDF5 to ICAT.")
    parser.add_option("-i", "--icat_cfg", dest="icat_cfg", default="icat.cfg",
                      help="Configuration file to connect to ICAT. Default=icat.cfg")
    parser.add_option("-s", "--server_name", dest="icat_server", default="default",
                      help="Section name in the icat configuration file, describing the needed ICAT server. Default=default")

    (opts, args) = parser.parse_args()

    if len(args) != 1 or opts.cfg is None:
        parser.print_help()
        sys.exit(-1)

    # read HDF5 to ICAT configuration file
    config = ConfigParser.SafeConfigParser()
    config.optionxform = str
    config.read(opts.cfg)

    # Create an ingestor instance
    ingest = icat_ingestor.ICATIngestor(loglevel=logging.DEBUG)

    # Connect to the ICAT service
    logger.info("Ready to ingest %s", args[0])
    ingest.read_icat_config(opts.icat_cfg, opts.icat_server)
    ingest.connect()

    # Reading the HDF5 file
    infile = h5py.File(args[0])

    # Filling the dictionary
    icat_args = {}
    # Filling info about this injected file
    # this can be seen as an example how to add custom metadata
    icat_args['datafile'] = {}
    icat_args['datafile']['name'] = args[0]
    stats = stat(args[0])
    icat_args['datafile']['fileSize'] = stats.st_size
    icat_args['datafile']['datafileCreateTime'] = datetime.fromtimestamp(stats.st_ctime)
    icat_args['datafile']['datafileModTime'] = datetime.fromtimestamp(stats.st_ctime)

    # creating the dict: each section is an ICAT field name (usually, an Entity name with lowercase first letter)
    for section in config.sections():
        if section == "override":
            continue
        cfg = dict(config.items(section))
        icat_args[section] = {}
        for k, v in cfg.iteritems():
            try:
                icat_args[section][k] = infile[v][:][0]
            except KeyError:
                # if the path is not found in the HDF5 file, fill the metadata with default value found in translation cfg
                logger.warn("[INFO] Default value " + k + v)
                icat_args[section][k] = v

    # looping over the dictionary: each step inserts an entry
    for section in icat_args.keys():
        if section == "override":
            continue
        entry = ingest.insert_entry(section, icat_args)
        if section == "datafile":
            logger.debug(entry)
        if entry is not None:
            logger.info(section + " DONE")

    # post-processing: filling reference tables
    ingest.fill_reference_tables(icat_args)

    # closing the connection
    ingest.disconnect()
