# python-icat #

python-icat is a Python package that provides a collection of modules
for writing programs that access an ICAT service using the SOAP
interface.  It is based on Suds and extends it with ICAT specific
features.

## System Requirements ##

  * Python 2.7 or newer.  Python 2.6 will do but requires patching the sources, see below.
  * Suds, either the [original version](https://fedorahosted.org/suds/) or the [fork by Jurko Gospodnetić](https://bitbucket.org/jurko/suds/).  The latter is recommended as the original version is not maintained any more and contains bugs.  Python 3 requires the jurko fork.
  * [PyYAML](http://pyyaml.org/wiki/PyYAML).  (Only needed to use the YAML backend of icatdump.py and icatrestore.py and to run the example scripts.  Not required to install or use python-icat itself.)
  * [lxml](http://lxml.de/).  (Only needed to use the XML backend of icatdump.py and icatrestore.py.  Not required to install or use python-icat itself.)
  * [argparse](http://code.google.com/p/argparse/). (Only for Python 2.6 and 3.1, argparse became part of the Python standard library in 2.7 and 3.2 respectively.)

## Download ##

Current Release:

  * [python-icat 0.7.0](http://icatproject.googlecode.com/svn/contrib/python-icat/python-icat-0.7.0.tar.gz) ([signature](http://icatproject.googlecode.com/svn/contrib/python-icat/python-icat-0.7.0.tar.gz.asc))

Old versions can also be found [here](http://icatproject.googlecode.com/svn/contrib/python-icat/).

## Installation from Source ##

python-icat follows the standard Python conventions of packaging source distributions.  See the documentation on [Installing Python Modules](http://docs.python.org/2.7/install/) for details or to customize the install process.

  1. Download the sources, unpack, and change into the source directory.
  1. `python setup.py build`
  1. `python setup.py install`

The last step might require admin privileges in order to write into the site-packages directory of your Python installation.

If you are using Python 2.6, apply python2\_6.patch (included in the source distribution, see the README for detailed instructions) after the first step.  It removes the use of dict comprehensions  and of the bytes string prefix 'b' which were introduced in Python 2.7.

## Additional Resources ##

  * I gave a [presentation on python-icat](http://icatproject.org/wp-content/uploads/2014/03/RolfKrahl_python-icat_Dublin_2014.pdf) at the ICAT meeting, March 2014, Dublin.

## Author ##

Rolf Krahl