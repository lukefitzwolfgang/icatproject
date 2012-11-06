README.txt for ICAT rapid test
Alistair Mills - October 2012
$Id$

svn co https://icatproject.googlecode.com/svn/ops/icat40/test40
svn co https://icatproject.googlecode.com/svn/ops/icat41/test41
svn co https://icatproject.googlecode.com/svn/ops/icat42/test42

This material is a simple test for icat for use in Service Verification - 0 in November 2012.  It is not intended for production.  It has been tested on linux and MacOs.  The use of this material on other platforms such as windows and cygwin is not supported, but it may work with minor changes.

There are three directories, each containing similar materials.  The jar files in each directory are different, as they have been built for only one version of ICAT.  Using materials for icat 4.2 when connecting to a server running icat 4.1 or 4.0 will not work.

The client computer should have a normal java run time environment, the bash shell, and svn.

The directory contains the following materials:

change_icat.sh
domain1_properties
domain2_properties
domain4_properties
icat_api_examples.jar
icat.client-4.1.0.jar
InstallCert.jar
jssecacerts-www.icatproject.org
readme.txt
test41.csv
test_all.sh
test_one.sh
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

06/11/2012 23:03:37, u-8100, test42, dls, authenticator = db Session ID = 8f9f1fe8-6df4-427b-bfb4-602f2ea3c471 API Version = 4.2.0, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
06/11/2012 23:03:38, u-8100, test42, domain5, authenticator = db Session ID = 79d61063-f8df-4d18-888b-29aee579b19d API Version = 4.2.0, wsdl.location=http://www.icatproject.org:5080/ICATService/ICAT?wsdl
06/11/2012 23:03:39, u-8100, test42, elettra, authenticator = db Session ID = ec5ab2af-67d6-41c7-a0cb-f782b4d67f3c API Version = 4.2.1, wsdl.location=https://icat-elettra.grid.elettra.trieste.it:8181/ICATService/ICAT?wsdl

Testing a single icat:

To select a single test do this:

./change_icat.sh domain1
./test_one.sh

Expect something of the following form:

authenticator = db Session ID = 59e2623f-50e0-4784-a175-29067ac3ec55 API Version = 4.2.0

Customisation of the tests:

There is no limit to the number of _properties files.  Three are provided as examples.  The user can create additional _properties files and give them names which indicate their content; for example a file called esrf_properties may contain the properties which are required to connect to esrf.  

Depending on the local firewall, it may be necessary to change the file test_one.sh to pass the address of the local proxy server.  There is a commented out example of this in test_one.sh.

If the remote host is using a non standard certificate on a secure protocol, it may be necessary to tell the java virtual machine to trust the host.  This is described in the next section.  

Trusting additional hosts:

To trust an additional host use the script trust.sh.  For example, use the following command:

./trust.sh domain4

In this case this creates a file called _www_icatproject_org_4081.  The script test_one.sh uses this file to trust the host.

This script provides an easy way to collect the certificate when adding a host to the tests and the host does not have a trusted certificate.

- the end - 

