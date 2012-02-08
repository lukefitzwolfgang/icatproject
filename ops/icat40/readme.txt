README.txt for ICAT 4.0 installation
Alistair Mills - 26 January 2012
$Id$

For more detailed instructions please visit:
http://code.google.com/p/icatproject/wiki/InstallIcat40

In order to make it simple to install ICAT40, the following configuration is assumed:

Properites files:

The .properties files must be in the conf directory of the glassfish server.  You may find it convenient to put a symbolic link from the config directory to the properties files.

File ojdbc14.jar:

This file is distributed by Oracle.  We do not distribute it as this would violate our agreement with Oracle.  YOu must put it into the lib directory of the glassfish server.

Operating system:

operation system: linux
username: glassfish3
home directory: /home/glassfish3
shell: bash
note: the operating system user glassfish3 is running the Glassfish Server and the applications.

Glassfish Server:

directory: /home/glassfish3/glassfish3/glassfish
admin user: admin
admin user password: adminadmin
asadmin command: The operating system user called glassfish3 has asadmin on the PATH 
ojdbc14.jar: The file ojdbc14.jar has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/lib
icat.properties: The file has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/config
log4j.properties: The file has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/config

Oracle tools:
sqlplus: The operating system user glassfish3 has sqlplus on the PATH

Oracle database server: localhost
ICAT username/ password: icat/myicatpasswd
ICATUSER username/password: icatuser/myicatuserpasswd

Java run-time: 
java: The operating system user glassfish3 has java on the PATH

Directories:
icat40 - this contains two scripts called glassfish and glassfish.props which tell the glassfish Server what to do
icat40/orainit - this contains scripts to initialise the database
icat40/usertable_init - this contains a script to add a user to ICAT
icat40/test_icat - this contains a simple test for ICAT

The following files are part of the ICAT 4.0 distribution:
icat-ear-1583-http.ear
icat-ear-1583.ear
icat-client-1583.jar

The two ear files are identical, save that the one with -http deploys icat on port 8080 with http protocol.  The other deploys icat on port 8181 with https.  The use of https leads to complications during deployment and usage.  The preconfigured version of the software in this package deploys to 8080.

Server certificate:
Neither the Server nor a client application require a certificate, as icat is deployed using http protocol.

Ports:
The port 8080 is available for the glassfish Server to deploy icat-http
The port 4848 is available for the the admin console of glassfish

Configuration information:

Configuration is stored in the following files:
icat40/glassfish.props
icat40/passwordfile
icat40/usertable_init/usertable.sh
icat40/test_icat/example.properties

Instructions:

Create a test environment similar to the one assumed.  If it is not possible to be identical, then the four files containing configuration may require changes.  The relationships between the files is described in the file called dependencies.txt.

Ensure the following conditions on the system: 

- Database server running, and the username and passwords installed, but no content;
- Glassfish Server installed, but not running;
- Expand the distribution file into a directory; icat40 is used in the following instructions.

Do the following:

# Initialise the tables in the database - this will remove any existing content.

cd icat40/orainit
sqlplus icat/myicatpasswd@localhost @create_icat.sql | tee create_icat.log
cd ../..

# Start the glassfish Server, create the database pools and deploy icat.
# Deploying icat also creates the schema for icatuser, but does not give it useful content.

cd icat40
./glassfish.sh start
./glassfish.sh create
./glassfish.sh deploy icat
cd ..

# Create a user for use by the test
cd icat40/usertable_init
./usertable.sh
cd ../..

# Run the test which does logon to the icat

cd icat40/test_icat
./test_one.sh Authenticate
cd ..

# If all is well, then ICAT is working

To undeploy icat, delete the connection pools and stop the glassfish Server, do the following:

cd icat40
./glassfish.sh undeploy icat
./glassfish.sh delete
./glassfish.sh stop

# - the end -

