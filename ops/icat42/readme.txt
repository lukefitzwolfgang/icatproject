README.txt for ICAT 4.2 installation
Alistair Mills - 13 October 2012
$Id$

For more detailed instructions please visit:
http://code.google.com/p/icatproject/wiki/InstallIcat42

Note that there is discussion of Oracle and sqlplus in this copy of the note.  This will be removed when its use has been eliminted.

In order to make it simple to install ICAT42, the following configuration is assumed:

Operating system:

operation system: linux
username: glassfish3
home directory: /home/glassfish3
shell: bash
additional software: 

svn client to obtain the software (optional if you obtain the materials another way)
python with the python-suds plugin for running the test program (optional if skipping the test)

Note that the operating system user glassfish3 is running the Glassfish Server and the applications.  The installer has to insert properties files into the Glassfish server, so it is best if Glassfish is installed by the user glassfish3 and not by root.

Glassfish Server:

directory: /home/glassfish3/glassfish3/glassfish
admin user: admin
admin user password: adminadmin
asadmin command: The operating system user called glassfish3 has asadmin on the PATH 
ojdbc14.jar: The file ojdbc14.jar has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/lib
icat.properties: The file has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/config
log4j.properties: The file has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/config
authn_db.ear.config/authn_db.properties: The file has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/config


Oracle tools:
sqlplus: The operating system user glassfish3 has sqlplus on the PATH

Oracle database server: localhost
ICAT username/ password: icat42/myicatpasswd
ICATUSER username/password: icatuser42/myicatuserpasswd

Obtaining the materials:

svn co https://code.google.com/p/icatproject/svn/ops/icat42


Java run-time: 
java: The operating system user glassfish3 has java on the PATH

Directories:
icat42 - this contains two scripts called glassfish and glassfish.props which tell the glassfish Server what to do
icat42/orainit - this contains scripts to initialise the database
icat42/usertable_init - this contains a script to add a user to ICAT
icat42/test_icat - this contains a simple test for ICAT

The following files are part of the ICAT 4.2.1 distribution:
authn_db.ear-1.0.0.ear
authn_ldap.ear-1.0.0.ear
icat.client-4.2.1.jar
icat.ear-4.2.0.ear

The following is only distributed for the purpose of testing the deployment process without the need for a certificate.  Its content is the same as the distribution, save that it deploys icat on port 8080 with http protocol.  The one from  deploys icat on port 8181 with https.  The use of https leads to complications during deployment and usage.  The preconfigured version of the software in this package deploys to 8080.

icat.ear-4.2.0-http.ear

Server certificate:
Neither the Server nor a client application require a certificate, as icat is deployed using http protocol.

Ports:
The port 8080 is available for the glassfish Server to deploy icat
The port 4848 is available for the the admin console of glassfish

Configuration information:

Configuration is stored in the following files:
authn_db.ear.config/glassfish.props
icat.ear.config/glassfish.props
usertable_init/usertable.sh

Instructions:

Create a test environment similar to the one assumed.  If it is not possible to be identical, then the four files containing configuration may require changes.  The relationships between the files is described in the file called dependencies.txt.

Ensure the following conditions on the system: 

- Database server running, and the username and passwords installed, but no content;
- Glassfish Server installed, but not running;
- Expand the distribution file into a directory.

Do the following:

# Start the glassfish Server, create the database pools and deploy the authenticator and icat.
# Deploying icat also creates the schema for icatuser, but does not give it useful content.

asadmin start-domain domain1
cd icat.ear.config     ; ./create.sh;   cd ..
cd authn_db.ear.config ; ./create.sh;   cd ..

asadmin deploy authn_db.ear-1.0.0.ear
asadmin deploy icat.ear-4.2.0-http.ear

[

It may be simpler to perform this step using a database administration tool such as sqldeveloper.
When this is changed from Oracle to Derby, the same applies.

# Create a user for use by the test
cd usertable_init ;     ./usertable.sh; cd ..

]

# Run the test which does a logon to icat and exercises the API

cd icat.ear.config; python test.py localhost 8080 db username icat42 password icat42passwd; cd ..

# If all is well, then ICAT is working

To undeploy icat, delete the connection pools and stop the glassfish Server, do the following:

asadmin undeploy authn_db.ear-1.0.0
asadmin undeploy icat.ear-4.2.0-http

cd icat.ear.config     ; ./drop.sh;   cd ..
cd authn_db.ear.config ; ./drop.sh;   cd ..

asadmin stop-domain domain1

# - the end -

