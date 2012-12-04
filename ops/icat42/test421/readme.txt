README.txt for ICAT rapid test
Alistair Mills - October 2012
$Id: readme.txt 2219 2012-11-06 23:10:22Z crcamills@gmail.com $

svn co https://icatproject.googlecode.com/svn/ops/icat42/test421

This material is a simple test for icat for use in Service Verification - 1 in December 2012.  It is not intended for production.  It has been tested on linux and MacOs.  The use of this material on other platforms such as windows and cygwin is not supported, but it may work with minor changes.

There is only one directory containing the material for testing icat 4.2.  Using materials for icat 4.2 when connecting to a server running icat 4.1 or 4.0 will not work.

The client computer should have a normal java run time environment, the bash shell, and svn.

The directory contains the following materials:

change_icat.sh
domain5_properties
icat_api_examples.jar
icat.client-4.2.0.jar
InstallCert.jar
jssecacerts-www.icatproject.org
readme.txt
test421.csv
test_all.sh
test_two.sh
test_2.sh
trust.sh

Jar files:
One jar file contains the icat-client jar for the version of ICAT.  
Another jar file contains a compiled version of a small set of tests for this version of ICAT.
Another jar file contains code for creating the certificate trust store.

Properties files:
Each of the properties files contains the connection information for an icat.  The choice of the name of the file is not significant, but its contents are important.

Sh files:
The file change_icat.sh links one of the _properties files to the name example.properties.  
The file test_one.sh runs a test using the file example.properties.
The file test_all.sh runs test_one.sh using each of the properties files.
The file trust.sh allows the user to tell the tests to trust additional hosts - see Trusting additional hosts.

Jssecacerts file:
This is the default trust file.  It tells the test scripts to trust www.icatproject.org.

Certificate files:
_icat-elettra_grid_elettra_trieste_it_8181 contains the truststore for elettra.

Usage of test_all.sh:
To run all of the tests do this:
./test_all.sh

The output is in csv form showing date, host, properties, response, wsdl.location and can be imported into a spreadsheet such as Excel and LibreOffice.

Output of test_all.sh:
Expect something of the following form:

04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Authenticate : authenticator = db : Session ID = 2aa7adb9-0380-4065-a6cf-33f281e07aed : API Version = 4.2.0, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search Facility.name : CLF, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search Facility.name : My Facility, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search Investigation.name : My Investigation, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search Investigation.name : None, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search Investigation.name : Temp, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search Dataset.name : My Dataset, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search DISTINCT Dataset.name : My Dataset, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search DISTINCT Datafile.name : My Datafile 2, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, Search DISTINCT Datafile.name : My Datafile 1, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, GetUserName : Username = CIC, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
04/12/2012 17:10:24, abm65.esc.rl.ac.uk, test421, domain5, RemainingMinutes : Remaining Minutes:119.99963333333334, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl


Testing a single icat:

To select a single test do this:

./change_icat.sh domain5
./test_two.sh

Expect something of the following form:

Authenticate : authenticator = db : Session ID = 38d0de50-882d-4ecb-aea1-63eed7fe428e : API Version = 4.2.0
Search Facility.name : CLF
Search Facility.name : My Facility
Search Investigation.name : My Investigation
Search Investigation.name : None
Search Investigation.name : Temp
Search Dataset.name : My Dataset
Search DISTINCT Dataset.name : My Dataset
Search DISTINCT Datafile.name : My Datafile 2
Search DISTINCT Datafile.name : My Datafile 1
GetUserName : Username = CIC
RemainingMinutes : Remaining Minutes:119.9997

Customisation of the tests:

There is no limit to the number of _properties files.  Three are provided as examples.  The user can create additional _properties files and give them names which indicate their content; for example a file called esrf_properties may contain the properties which are required to connect to esrf.  

Depending on the local firewall, it may be necessary to change the file test_2.sh to pass the address of the local proxy server.  There is a commented out example of this in test_2.sh.

If the remote host is using a non standard certificate on a secure protocol, it may be necessary to tell the java virtual machine to trust the host.  This is described in the next section.  

Trusting additional hosts:

To trust an additional host use the script trust.sh.  For example, use the following command:

./trust.sh domain4

In this case this creates a file called _www_icatproject_org_4081.  The script test_2.sh uses this file to trust the host.

This script provides an easy way to collect the certificate when adding a host to the tests and the host does not have a trusted certificate.

- the end - 

