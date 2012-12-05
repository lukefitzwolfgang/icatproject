README.txt for ICAT rapid test
Alistair Mills - October 2012
$Id: readme.txt 2219 2012-11-06 23:10:22Z crcamills@gmail.com $

1. Introduction:

This material is a simple test for icat for use in Service Verification - 1 in December 2012.  It is not intended for production.  It has been tested on linux and MacOs.  The use of this material on other platforms such as windows and cygwin is not supported, but it may work with minor changes.

This directory only contains material for testing icat 4.2.  If connecting to a server running icat 4.1 or 4.0 will not work.

The client computer should have a normal java run time environment, the bash shell, python and svn.

To get a copy of this files and the files which form the test, use the following command

svn co https://icatproject.googlecode.com/svn/ops/icat42/test421

2. The directory contains the following materials.  Their use is described later:

2.1 Bash scripts:
change_icat.sh
change_url.sh
change_wsdl.sh
test_2.sh
test_all.sh
test_two.sh
trust.sh

2.2 Python scripts:
create.py
delete.py

2.3 Txt files:
list_of_examples.txt
readme.txt

2.4 Example properties file:
domain5_properties

2.5 Example output from the test:
test421.csv

2.6 Java jar files for the tests:
icat_api_examples.jar
icat-client.jar
InstallCert.jar

2.7 Trust store files for the icats which are in the test:
_apps_jcns_fz-juelich_de_9111
_icat-elettra_grid_elettra_trieste_it_8181
_icat_synchrotron-soleil_fr
_www_icatproject_org
_www_icatproject_org_8443
_wwws_esrf_fr

2.8 Zip file:
clf-setup-1.0.0-SNAPSHOT-distro.zip

3 Use of the files:

3.1 Bash scripts:
change_icat.sh links one of the _properties files to the name example.properties.  
test_two.sh runs a test using the file example.properties.
test_all.sh runs test_two.sh using each of the properties files.
trust.sh allows the user to tell the tests to trust additional hosts - see Trusting additional hosts.
change_url.sh is a utility for transforming the url from one form to another.
change_wsdl.sh is a utility for transforming the wsdl line in the properties files to a url.

3.2 Python scripts:
One file does a series of create operations to add Investigation and other items to ICAT.
The other file reverses the create operations.

3.3 Properties files:
The properties file contains the connection information for an icat.  The choice of the name of the file is not significant, but its contents are important.  Additional properties files are in the directory called sites.

3.4 Txt files:
One file contains this text.  The other is used by test_two.sh.

3.5 Csv file:
This is an example of the output from running the test on a single ICAT.

3.6 Jar files:
One jar file contains the icat-client jar for the version of ICAT.  
Another jar file contains a compiled version of a small set of tests for this version of ICAT.
Another jar file contains code for creating the certificate trust store.

3.7 Trust store files:
The trust store files are used to tell the Java virtual machine to trust a host.  The url of the host is implied in the name of the file.
The file jssecacerts-www.icatproject.org is the default trust file.  It tells the test script to trust www.icatproject.org.
For example, file file _icat-elettra_grid_elettra_trieste_it_8181 contains the truststore for elettra on https:8181.

3.8 Zip file:
The use of the clf-set-up file is described at the end of this note.  This is only required if installing an ICAT server.  The zip file contains the materials for preparing the environment for the test:
clf-setup-1.0.0-SNAPSHOT-distro.zip

4 Running the tests.

4.1 Usage of test_all.sh:

To run all of the tests do this:
./test_all.sh

The output is in csv form showing date, host, properties, response, wsdl.location and can be imported into a spreadsheet such as Excel and LibreOffice.

4.2 Output of test_all.sh:

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

4.3 Testing a single icat:

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

4.4 Customisation of the tests:

There is no limit to the number of _properties files.  Others are provided in the sites directory.  Simply copy them from the directory.  The user can create additional _properties files and give them names which indicate their content; for example a file called esrf_properties may contain the properties which are required to connect to esrf.  

4.5 Tell the scripts how to deal with the local firewall:

Depending on the local firewall, it may be necessary to change the file test_2.sh to pass the address of the local proxy server.  There is a commented out example of this in test_2.sh.

If the remote host is using a non standard certificate on a secure protocol, it may be necessary to tell the java virtual machine to trust the host.  This is described in the next section.  

4.6 Trusting additional hosts:

To trust an additional host use the script trust.sh.  For example, use the following command:

./trust.sh domain4

In this case this creates a file called _www_icatproject_org_4081.  The script test_2.sh uses this file to trust the host.

This script provides an easy way to collect the certificate when adding a host to the tests and the host does not have a trusted certificate.

5 Preparing the ICAT for the test:

If you wish to install content into an empty, ICAT the .zip file and the .py files facilitate this.  For the moment, we are reusing materials created for a deployment at RAL.  In the future, there will be a more general purpose tool available in the contrib directory.

Note, that you are not obliged to use this method.  If you prefer to use another method, you may do so. 

Do the following:

5.1 Unzip the zip file and this creates a directory called clf-setup with a utility java jar:

unzip clf-setup-1.0.0-SNAPSHOT-distro.zip

5.2 Execute the utility program as follows:

If the ICAT has been deployed on http:8080 on localhost, use the following command:

java -cp "clf-setup/*" Setup create --url http://localhost:8080

This can be reversed with the following command:

java -cp "clf-setup/*" Setup drop --url http://localhost:8080

If the icat is on https:8181, use the following:

java -cp "clf-setup/*" Setup create --url https://localhost:8181

It is necessary to tell the JVM to trust the host.  The trust script is provided to make this easy.  The details of its use are described above.

The program prompts for the password of the "root" user.  The name of the root user is in the icat.properties file.  The username and password of the root user must also be known to the authentication method.  Usually this means that it is in the table PASSWD in the icatuser database.

If the program is silent, all is well.  It has created users called CIC, reader and guest, and given them appropriate authorizations.  If you do not like these names, there is an option to provide alternatives.

5.3 Add the users CIC, reader and guest to the authentication mechanism.  Usually this means that it is in the table PASSWD in the icatuser database.  The detail of how to do this is provided elsewhere.

5.4 Create an additional Facility, Investigation, Dataset and Datafile in the ICAT.

To create additional objects in ICAT, do this:

python create.py http://localhost:8080 db username CIC password password

To delete the additional objects in ICAT, do this:

python delete.py http://localhost:8080 db username CIC password password


- the end - 

