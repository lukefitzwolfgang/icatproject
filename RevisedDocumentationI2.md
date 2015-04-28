#Revised Documentation I-2.



$Id$

# Revised Documentation I-2 #

# I2: API #

ICAT API Installation from Binary

Author(s): Glen Drinkwater

Revised: Alistair Mills

## Prerequisites ##

This document is to guide an administrator through the steps needed to install the ICAT API v3.3 onto Glassfish v2 UR2 b4 on Microsoft Windows or Linux.

The installation needs the following software installed:

A. Java 6 Update 6 from:
  * [http://java.sun.com/javase/downloads/index.jsp](http://java.sun.com/javase/downloads/index.jsp).

B. Apache Ant v1.7+ from:
  * [http://ant.apache.org](http://ant.apache.org).

C. Glassfish v2 UR2 b4 from:
  * [https://glassfish.dev.java.net/downloads/v2ur2-b04.html](https://glassfish.dev.java.net/downloads/v2ur2-b04.html).

D. Installed and configured Java Cog Kit from:
  * [http://wiki.cogkit.org/index.php/Main\_Page](http://wiki.cogkit.org/index.php/Main_Page);
  * [http://www.globus.org/cog/distribution/1.2/FAQ.TXT](http://www.globus.org/cog/distribution/1.2/FAQ.TXT,);
  * This is only needed for running the ICAT API and not the installation.

The installation also needs these databases to be accessible with usernames icat, icatuser and dataportal:

  1. An installed ICAT 3.3 schema instance on an Oracle DB;
> 2. An accessible empty schema for the ICAT API user session tables on an Oracle DB.

Note that JAVA\_HOME and GLASSFISH\_HOME represent the root directories for the installations of Java and Glassfish respectively.

If you have already installed the Data Portal, please go to section 3 and then onto 7 or 8 depending on whether Glassfish is currently running.

## Installation of icat ##

It should not be necessary to be root to install icat.  However, depending on how the prerequisites were installed, this may be necessary.

1. Download the installation directory from SVN:
  * This contains all the binary jars and ant scripts needed to install ICAT API;
  * This will be known as INSTALLATION\_HOME.

2. Check ant is installed with the correct version by executing this from the command prompt:
  * ant -version
  * Expect a response such as:
```
Apache Ant version 1.7.0 compiled on December 13 2006. 
```

3. Configure the file INSTALLATION\_HOME/nbproject/databases.properties:
  * There is an example of the file below;
  * Change facility.name to ISIS, DLS or CLF.  This will install the version of ICAT API for ISIS, DLS or CLF;
  * Configure the ICAT schema:
    * Add the values to the properties for:
      * icat.user;
      * icat.url;
      * icat.password;
      * icat.data.source;
      * icat.driver.
    * This is for the main ICAT schema’s username, password and url;
    * Note that icat.url must be in Oracle JBDC format.
  * Configure the ICATUSER schema;
  * Configure the DATAPORTAL schema.

4. Configure the file INSTALLATION\_HOME/nbproject/glassfish.properties:
  * Configure the application server Glassfish:
    * If you are using the default Glassfish installation, you should not have to alter the following:
      * sjsas.admin.port;
      * sjsas.http.port;
      * sjsas.password;
      * sjsas.host;
      * sjsas.username;
      * sjsas.domain.
    * Change sjsas.root to GLASSFISH\_HOME remembering to add the escape character \ if you are on windows for : and \;
    * Change admin.auth.password to the password you wish to add for the admin ICAT API web services. The username will be facility.name-admin from above, for example ISIS-admin.
  * This step can be skipped if you have already installed the Data Portal:
  * There is an example of this file at the end of this note.

5. Now the configuration is finished, the installation can start with a series of ant commands executed in INSTALLATION\_HOME.  These may have to execute as root.

6. ant –f icat.xml init-config
  * Copies the required files onto the Glassfish server i.e. Oracle's JDBC driver;
  * Copies a jar file to fix a bug in the StAX Parser in Java 6;
  * If using Linux, manually copy:
```
INSTALLATION_HOME/lib/wstx-asl-3.9.2.jar 
```
  * to:
```
JAVA_HOME/jre/lib/endorsed 
```
  * Create the directory JAVA\_HOME/jre/lib/endorsed if it does not exist.

7. ant –f icat.xml start-domain
  * Starts the Glassfish server, if it complains of not starting, check:
    * http://localhost:4848;
  * If it still has not started, use the following commands to start and stop and start the glassfish domain domain1:
```
GLASSFISH_HOME/bin/asadmin start-domain domain1
GLASSFISH_HOME/bin/asadmin stop-domain domain1
GLASSFISH_HOME/bin/asadmin start-domain domain1
```

8. ant –f icat.xml install
  * Creates all the glassfish data sources and connection pools and deploys the icat-api.ws.jar onto Glassfish;
  * If reinstalling, then some errors will occur saying it cannot create some tables; do not worry about this, it just means the tables have already been created;
  * If a failure or an error in the context of database connection that includes: Class not found, check to see if there is a file called ojdbc14.jar in GLASSFISH\_HOME/domains/domain1/lib/. If there is not one present, then copy it there from:

> INSTALLATION\_HOME/lib/ojdbc14.jar.

9. ant –f icat.xml configure-icat-db
  * Configures the MyProxy Servers and enables SUPER and ADMIN for ICAT API admin logins.

10. ant –f icat.xml restart-domain
  * Allows the changes in step 9 to be picked up from the database.

11. To check if the installation is correct, check:
  * https://localhost:8181/ICATService/ICAT?wsdl to view the WSDL file.

## Further information ##

### Example of databases.properties ###

Here is an example of the file INSTALLATION\_HOME/nbproject/databases.properties:
```
# Facility Name, either ISIS or DLS
facility.name=ISIS

# This is the properties for the main ICAT schema
icat.user=icat
icat.url=jdbc:oracle:thin:@localhost:1521:XE
icat.password=myicatpasswd
icat.data.source.classname=oracle.jdbc.pool.OracleDataSource
icat.driver=oracle.jdbc.driver.OracleDriver

# This is the properties for the ICAT user schema
icatuser.user=icatuser
icatuser.url=jdbc:oracle:thin:@localhost:1521:XE
icatuser.password=myicatuserpasswd
icatuser.data.source.classname=oracle.jdbc.pool.OracleDataSource
icatuser.driver=oracle.jdbc.driver.OracleDriver

# This is the properties for the dataportal core schema
dataportal.user=dataportal
dataportal.url=jdbc:oracle:thin:@localhost:1521:XE
dataportal.password=mydataportalpasswd
dataportal.data.source.classname=oracle.jdbc.pool.OracleDataSource
dataportal.driver=oracle.jdbc.driver.OracleDriver

```

### Useful commands for verifying the database ###
```
echo 'SELECT table_name FROM user_tables;' | sqlplus        icat/myicatpasswd@XE
echo 'SELECT table_name FROM user_tables;' | sqlplus  dataportal/mydataportalpasswd@XE
echo 'SELECT table_name FROM user_tables;' | sqlplus    icatuser/myicatuserpasswd@XE
```

Note that although there is a user called something like ISIS-admin with a password like icatadmin, this user is not a username for the database.  This username is contained within icat.

### Example of glassfish.properties ###
```

# Glassfish Application server configuration
sjsas.admin.port=4848
sjsas.http.port=8090
sjsas.password=adminadmin
sjsas.host=localhost
sjsas.username=admin
sjsas.domain=domain1
# The next two are the only ones normally needed to be changed after
# a standard Glassfish configuration
# i.e. C\:\\Program Files\\glassfish-v2ur2 or /opt/glassfish-v2ur2 
sjsas.root=/opt/glassfish

# password for icat api admin
admin.auth.password=icatadmin
```

## - the end - ##