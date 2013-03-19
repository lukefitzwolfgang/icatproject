ICAT 4.2.1 Rapid Installation
# $Id$

This material is not the complete set of materials for ICAT 4.2.1. It is a selected set of materials which are packaged to make it easy to get ICAT to run and is not intended for production.

In addition to this README file, there is a README file in each of the directories associated with the database.

For more detailed instructions please visit: http://code.google.com/p/icatproject/wiki/InstallIcat42

Pre-requisites

In order to make it simple to install ICAT, the following configuration is assumed:

operation system    : linux
username            : glassfish3
home directory      : /home/glassfish3
shell               : bash
additional software : java se, java ee (glassfish), svn, python, python-suds
database            : either MySQL, Oracle or Derby

* Java Standard Edition with jdk can be downloaded from Oracle/Java
* Java Enterprise Edition with Glassfish 3.1.2 or later can be downloaded from Oracle/Java
* SVN client is required to obtain the software (optional if obtaining the materials another way)
* Python with the python-suds plugin for running the test program (optional if skipping the test)
* For information about the databases, please see Database Configuration section below

It is usual to add the following content to the ./bashrc:

export      JAVA_HOME=/usr/lib/jvm/java-6-openjdk
export GLASSFISH_HOME=/home/glassfish3/glassfish3
export        DB_HOME=$GLASSFISH_HOME/javadb
export      ICAT_HOME=/home/glassfish3/icat421
export PATH=\
$JAVA_HOME/bin:\
$GLASSFISH_HOME/bin:\
$DB_HOME/bin:\
$PATH

The installation of the software is not dependant on setting the environmental variables. However this README makes use of them. Please check the paths are correct.

It is necessary for the application property files to be installed for the glassfish.  To do this, use the following commands:

cd $GLASSFISH_HOME/glassfish/domains/domain1
ln -s $ICAT_HOME/icat.ear.config/icat.properties .
ln -s $ICAT_HOME/icat.ear.config/log4j.properties .
ln -s $ICAT_HOME/authn_db.ear.config/authn_db.properties .

Glassfish Server

Note that the operating system user glassfish3 will be running the Glassfish Server and the applications. The installer of ICAT has to insert properties files into the Glassfish server, so it is best if Glassfish is installed by the user glassfish3 and not by root.

directory           : /home/glassfish3/glassfish3/
admin user          : admin
admin user password : adminadmin
asadmin command     : The operating system user called glassfish3 has asadmin on the PATH 

The Glasssfish server requires a database driver for the MySQL and Oracle databases. See Database configuration section below.

database driver : The appropriate database driver has been added to $GLASSFISH_HOME/glassfish/domains/domain1/lib

Ports

The port 8080 is available for the glassfish Server to deploy icat
The port 4848 is available for the the admin console of glassfish

Obtaining the materials for the Installation

svn co https://icatproject.googlecode.com/svn/ops/icat421

Directories 

icat421/                      contains the ear files taken from the ICAT 4.2.1 distribution materials 
icat421/db_derby              contains the glassfish properties files and setup scripts for the derby database
icat421/db_mysql              contains the glassfish properties files and setup scripts for the mysql database
icat421/db_oracle             contains the glassfish properties files and setup scripts for the oracle database

The following files are part of the ICAT 4.2.1 distribution

authn_db.ear-1.0.0.ear       contains the compiled ear file for the db authenticator
authn_ldap.ear-1.0.0.ear     contains the compiled ear file for the ldap authenticator
icat.ear-4.2.1.ear           contains the compiled ear file for icat for deployment on https
icat.ear-4.2.1-http.ear      contains the compiled ear file for icat for deployment on http

The file icat.ear-4.2.1-http.ear is only distributed for the purpose of testing the deployment process without the need for a certificate. Its content is the same as the distribution apart from it deploys ICAT on port 8080 with http protocol. The other deploys icat on port 8181 with https. The use of https leads to complications during deployment and usage. The preconfigured version of the software in this package deploys to 8080.

Database Configuration

ICAT 4.2.1 has been tested against MySQL, Oracle and Derby databases. Further information and configuration settings for each database can be found in their respective directories: db_derby, db_mysql, db_oracle. You should view their README file before attempting to deploy ICAT.

Server certificate

Neither the server nor a client application require a certificate as ICAT is deployed using http protocol.

Instructions

Create a test environment similar to the one assumed. If it is not possible to be identical, then the files containing configuration may require changes.

Ensure that the Glassfish Server is installed, but not running. Make sure that the properties files are installed in the glassfish server as described above.

Go to the directory called icat421 containing the material from the svn command and do the following:

# 0. Select database type (derby, mysql, oracle)

export SELECTED_DATABASE=derby

# 1. Start the glassfish server and the database.

asadmin start-domain domain1
./icat_setup.sh init-database

# 2. Create the jdbc connections for the authenticator and icat.

cd icat.ear.config     ; ./create.sh;   cd ..
cd authn_db.ear.config ; ./create.sh;   cd ..

# 3. Deploy the authenticator and icat.
# If you have previously done a deployment you may receive SQL errors, ignore these.

asadmin deploy authn_db.ear-1.0.0.ear
asadmin deploy icat.ear-4.2.1-http.ear

# 4. Create the user for use by the test.
# Deploying icat creates the schema for icatuser, but does not give it useful content.

./icat_setup.sh create-icatuser

# 5. Run the test which does a logon to icat and exercises the API.

cd icat.ear.config; python test.py localhost 8080 db username root password password; cd ..

# If all is well, then ICAT is working.  To reverse all of this, do the following ...

# 6. Undeploy the authenticator and icat.

asadmin undeploy authn_db.ear-1.0.0
asadmin undeploy icat.ear-4.2.1-http

# 7. Delete the connection pools.

cd icat.ear.config     ; ./drop.sh;   cd ..
cd authn_db.ear.config ; ./drop.sh;   cd ..

# 8. Stop the database and the domain.

./icat_setup.sh stop-database
asadmin stop-domain domain1

# 9. Error conditions.
#
# If things do not work, read the Server log:
# $GLASSFISH_HOME/glassfish/domains/domain1/logs/server.log
#
# If there is no internet condition, the Glassfish Console may hang after a login to:
# http://localhost:4848
#
# Remove the update jar file from the Glassfish and restart the domain: 
#
# asadmin stop-domain domain1
# rm $GLASSFISH_HOME/glassfish/modules/consoleupdatecenterplugin.jar
# asadmin start-domain domain1
#
# see post: http://www.java.net/node/706564
#

# - the end -
