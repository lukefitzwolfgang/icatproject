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
domain3_properties
icat-client.jar
icat_api_examples.jar
readme.txt
test_all.sh
test_one.sh

Jar files:
One jar file contains the icat-client jar for the version of ICAT.  
The other jar file contains a compiled version of a small set of tests for this version of ICAT.

Properties files:
Each of the properties files contains the connection information for an icat.  The choice of the name of the file is not significant, but its contents are important.

Sh files:
The file change_icat.sh links one of the _properties files to the name example.properties.  
The file test_one.sh runs a test using the file example.properties.
The file test_all.sh runs test_one.sh using each of the properties files.

Usage of test_all.sh:
To run all of the tests do this:
./test_all.sh

The output is in csv form showing date, host, properties, response, wsdl.location and can be imported into a spreadsheet such as Excel and LibreOffice.

Output of test_all.sh:
Expect something of the following form:

31/10/2012 23:53:53, u-8100, test42, domain1, authenticator = db Session ID = 59e2623f-50e0-4784-a175-29067ac3ec55 API Version = 4.2.0, wsdl.location=http://localhost:8080/ICATService/ICAT?wsdl
31/10/2012 23:53:55, u-8100, test42, domain2, authenticator = db Session ID = d5c702e2-27fb-4854-92a7-5512f9e71a54 API Version = 4.2.0, wsdl.location=http://localhost:8080/ICATService/ICAT?wsdl
31/10/2012 23:53:56, u-8100, test42, domain3, authenticator = db Session ID = 499844da-159c-4040-ac64-388f01f9b85e API Version = 4.2.0, wsdl.location=http://localhost:8080/ICATService/ICAT?wsdl

Testing a single icat:
To select a single test do this:

./change_icat.sh domain1
./test_one.sh

Expect something of the following form:

authenticator = db Session ID = 59e2623f-50e0-4784-a175-29067ac3ec55 API Version = 4.2.0

Customisation of the tests:

There is no limit to the number of _properties files.  Three are provided as examples.  The user can create additional _properties files and give them names which indicate their content; for example a file called esrf_properties may contain the properties which are required to connect to esrf.  

Depending on the local firewall, it may be necessary to change the file test_one.sh to pass the address of the local proxy server.  There is a commented out example of this in the file.

If the remote host is using a non standard certificate on a secure protocol, it may be necessary to pass the certificate file to the java virtual machine.  There is a commented out example of this in the test_one.sh for https connection to www.icatproject.org.  It is possible to create the equivalent certificate file for other hosts, but the information about how to do this is not in this note.

- the end - 

