#!/usr/bin/env python

"""
Creates a simple test HDF5 file, containing random data

original code: Scientific Computing Team - ELETTRA Sincrotrone Trieste SCpA
"""

import sys
import ConfigParser
import random
import h5py
import numpy as np

__copyright__ = "Copyright 2014, Paul Scherrer Institute"
__credits__ = ["original code: Scientific Computing Team - ELETTRA Sincrotrone Trieste SCpA"]
__license__ = "LGPL"
__version__ = "3"


def generate_random_hdf5(fname, templatefn):
    """
    sample HDF5 file generation
    Generates sample HDF5 files with random data and metadata

    Parameters
    ----------
    fname: str
        Name of the HDF5 to be generated
    templatefn : str
        Name of the configuration file describing HDF5 structure

    Examples
    --------
    >>> fname = generate_random_hdf5('fname.h5', 'template.cfg')
    """

    # read configuration file
    config = ConfigParser.SafeConfigParser()
    config.read(templatefn)

    ini = config._sections

    # open hdf5 file
    f = h5py.File(fname, 'w')

    # fill data and metadata
    meta = config.items("h5meta")
    strdt = h5py.new_vlen(str)
    for val in meta:
        # If it is data, generate a random dataset
        if val[0][-5:] == "/data":
            shape_tmp = map(int, val[1].strip("(").strip(")").split(","))
            shape = [i for i in shape_tmp]
            data = np.random.rand(*shape)
            group = f.require_group(val[0].rsplit('/', 1)[0])
            dset = group.require_dataset(val[0].rsplit('/', 1)[1], shape, dtype=float)
            dset[:] = data
            continue

        if len(val[0].split('/')) > 1:
            groupname, dsetname = val[0].rsplit('/', 1)
            group = f.require_group(groupname)
            dset = group.require_dataset(dsetname, (1, ), strdt)
        else:
            dset = f.require_dataset(val[0], (1, ), strdt)
        rndval = val[1].split(',')[random.randint(0, len(val[1].split(',')) - 1)].strip()
        print dset, rndval
        dset[...] = rndval
        ini['h5meta'][val[0]] = rndval
    f.close()

    # what could I return?
    print "Done"
    return 0


if __name__ == "__main__":
    args = sys.argv
    if len(args) < 3:
        print "[USAGE] ", args[0], "configuration_file fname.h5"
        sys.exit(1)

    result = generate_random_hdf5(args[2], args[1])
