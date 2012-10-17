README.txt for ICAT 4.2.0 rapid installation
Alistair Mills - 13 October 2012
$Id$

For more detailed instructions please visit:
http://code.google.com/p/icatproject/wiki/InstallIcat42

This material is not the complete set of materials for icat 4.2.0.  It is a selected set of materials which are packaged to make it easy to get ICAT 4.2.0 to run.  It is not intended for production.

In order to make it simple to install ICAT42, the following configuration is assumed:

operation system:       linux
username:               glassfish3
home directory:         /home/glassfish3
shell:                  bash
additional software:    java, jee, glassfish, svn, python, python-suds

svn client to obtain the software (optional if obtaining the materials another way)
python with the python-suds plugin for running the test program (optional if skipping the test)

It is usual to add content to the ./bashrc such as the following:

export      JAVA_HOME=/usr/lib/jvm/java-6-openjdk
export GLASSFISH_HOME=/home/glassfish3/glassfish3
export        DB_HOME=$GLASSFISH_HOME/javadb
export PATH=\
$JAVA_HOME/bin:\
$GLASSFISH_HOME/bin:\
$DB_HOME/bin:\
$PATH

Glassfish Server:

Note that the operating system user glassfish3 is running the Glassfish Server and the applications.  The installer of ICAT has to insert properties files into the Glassfish server, so it is best if Glassfish is installed by the user glassfish3 and not by root.  The Glassfish server can run as a user process run the user glassfish3.

directory:                               /home/glassfish3/glassfish3/
admin user:                              admin
admin user password:                     adminadmin
asadmin command:                         The operating system user called glassfish3 has asadmin on the PATH 
ij command:                              The operating system user called glassfish3 has ij on the PATH 

icat.ear.config/icat.properties:         The file has been added to $GLASSFISH_HOME/glassfish/domains/domain1/config
icat.ear.config/log4j.properties:        The file has been added to $GLASSFISH_HOME/glassfish/domains/domain1/config
authn_db.ear.config/authn_db.properties: The file has been added to $GLASSFISH_HOME/glassfish/domains/domain1/config

Derby database server:                   localhost
ICAT schema username/password:           APP/APP
ICATUSER schema username/password:       APP/APP

ICAT username/password:                  root/password

Obtaining the materials:

svn co https://icatproject.googlecode.com/svn/ops/icat42

Directories:
icat42/                                  contains the ear files taken from the icat 4.2 distribution materials 
icat42/usertable_init/                   contains a script to add a user to ICATUSER database
icat42/test_icat/                        contains a simple test for ICAT

The following files are part of the ICAT 4.2.0 distribution:
authn_db.ear-1.0.0.ear                   contains the compiled ear file for the db authenticator
authn_ldap.ear-1.0.0.ear                 contains the compiled ear file for the ldap authenticator
icat.ear-4.2.0.ear                       contains the compiled ear file for icat for deployment on https
icat.ear-4.2.0-http.ear                  contains the compiled ear file for icat for deployment on http

The file icat.ear-4.2.0-http.ear is only distributed for the purpose of testing the deployment process without the need for a certificate.  Its content is the same as the distribution, save that it deploys icat on port 8080 with http protocol.  The other deploys icat on port 8181 with https.  The use of https leads to complications during deployment and usage.  The preconfigured version of the software in this package deploys to 8080.

Server certificate:
Neither the Server nor a client application require a certificate, as icat is deployed using http protocol.

Ports:
The port 8080 is available for the glassfish Server to deploy icat
The port 4848 is available for the the admin console of glassfish

Configuration information:

Configuration is stored in the following files:
authn_db.ear.config/glassfish.props      parameters which are used when creating connection pools for authentication
icat.ear.config/glassfish.props          parameters which are used when creating connection pools for icat
usertable_init/usertable.sh              parameters which are used to create a username/password for the ICAT API

Instructions:
Create a test environment similar to the one assumed.  If it is not possible to be identical, then the files containing configuration may require changes.

Ensure that the Glassfish Server is installed, but not running.  Make sure that the properties files are installed in the glassfish server as described above.

Go to the directory called icat42 containing the material from the svn command and do the following:

# 1. Start the glassfish Server, and its database.

asadmin start-domain domain1
asadmin start-database --dbhost 127.0.0.1

# 2. Create the database pools and deploy the authenticator and icat.

cd icat.ear.config     ; ./create.sh;   cd ..
cd authn_db.ear.config ; ./create.sh;   cd ..

# 3. Deploy the authenticator and icat.

asadmin deploy authn_db.ear-1.0.0.ear
asadmin deploy icat.ear-4.2.0-http.ear

# 4. Create the user for use by the test.
# Deploying icat creates the schema for icatuser, but does not give it useful content.
# It may be simpler to perform this step using a database administration tool such as sqldeveloper.

cd usertable_init ;     ./usertable.sh; cd ..

# 5. Run the test which does a logon to icat and exercises the API.

cd icat.ear.config; python test.py localhost 8080 db username root password password; cd ..

# If all is well, then ICAT is working.  To reverse all of this, do the following ...

# 6. Undeploy the authenticator and icat

asadmin undeploy authn_db.ear-1.0.0
asadmin undeploy icat.ear-4.2.0-http

# 7. Delete the connection pools 

cd icat.ear.config     ; ./drop.sh;   cd ..
cd authn_db.ear.config ; ./drop.sh;   cd ..

# 8. Stop the database and the domain

asadmin stop-database --dbhost 127.0.0.1
asadmin stop-domain domain1

# - the end -

