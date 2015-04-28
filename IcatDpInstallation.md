# Data Portal Installation #
Author(s): Glen Drinkwater

## Introduction ##

This document is to guide an administrator through the steps needed to install the Data Portal v3.3 onto Glassfish v2 UR2 b4 on Microsoft Windows or Linux.

## Prerequisites ##

The installation needs the following software installed:
  1. Java 6 Update 6 from http://java.sun.com/javase/downloads/index.jsp
  1. Apache Ant v1.7+ from http://ant.apache.org
  1. Glassfish v2 UR2 b4 from https://glassfish.dev.java.net/downloads/v2ur2-b04.html

The installation also needs this database
  1. An installed Data Portal 3.3 schema instance on an Oracle DB


_**Note:**_ From now on 'JAVA\_HOME' and 'GLASSFISH\_HOME' represent the root directory installation of Java and Glassfish respectively.

If you have already installed the ICAT API, please go to section 3 and then onto 7 or 8 depending whether Glassfish is currently running.

## Installation ##

  1. Download and unpack the **installation.zip** file provided by Devigo
    * This contains all the binary jars and ant scripts needed to install Data Portal
    * This will be known as the 'INSTALLATION\_HOME'
  1. Check ant is installed with the correct version by executing this from the command prompt:
    * `$ ant -version  `                Apache Ant version  # 7.0 compiled on December 13 2006
  1. Configure the 'INSTALLATION\_HOME'/nbproject/database.properties file.
    * Configure the Data Portal core schema
> > i. Add your own values to the dataportal.user, dataportal.url and dataportal.password properties
> > i. This is for the Data Portal core schema’s username, password and url
> > i. Note that dataportal.url must be in Oracle JDBC format, i.e. start with jdbc:oracle:thin:@
    * Configure the name of facility you wish to install.
> > i. Change facility.name to either ISIS or DLS
> > i. NB: This will either install the version of Data Portal for ISIS or DLS
  1. Configure the **'INSTALLATION\_HOME'/nbproject/glassfish.properties** file.
    * Configure the application server Glassfish
> > i. If you are using the default Glassfish installation sjsas.admin.port, sjsas.http.port, sjsas.password, sjsas.host, sjsas.username and sjsas.domain can leave alone.
> > i. Change sjsas.root to the 'GLASSFISH\_HOME' remembering to add the escape character \ if you are on windows for :  and \
> > i. Change admin.auth.password to the password you wish to add for the admin ICAT API web services.  The username will be facility.name-admin from 3 c) i) i.e. DLS-admin
    * This step can be skipped if you have already installed the ICAT API
  1. Now the configuration is finished the installation can start with a series of ant commands executed in the 'INSTALLATION\_HOME'
  1. **` $ ant –f dataportal.xml init-config `**
    * Copies the required files onto the Glassfish server i.e. Oracle’s JDBC driver
    * Copies a jar file to fix a bug in the StAX Parser in Java 6.
> > i. NB: If you are using Linux, you will need to manually copy **'INSTALLATION\_HOME'/lib/wstx-asl-3.9.2.jar** into **'JAVA\_HOME'/jre/lib/endorsed** creating the endorsed directory if it does not exist.
  1. **` $ ant –f dataportal.xml start-domain `**
    * Starts the Glassfish server, if it complains of not starting, check _http://localhost:4848_.
    * If it still has not started use the commands `'GLASSFISH_HOME'/bin/asadmin start-domain domain1` and ` 'GLASSFISH_HOME'/bin/asadmin stop-domain domain`1 to start and stop the default domain domain1
  1. **` $ ant –f dataportal.xml install`**
    * Creates all the glassfish data sources and connection pools and deploys the **dataportal.ear** onto Glassfish
  1. **` $ ant –f dataportal.xml configure-dataportal-db`**
    * Configures the dataportal DB
  1. **` $ ant –f dataportal.xml restart-domain `**
    * Allows the changes to 9 to be picked up from the database.
  1. To check if the installation is correct, goto _https://localhost:8181/dataportal_ to view the login page.

## See Also ##

[source:"ICAT/DevigoTutorial/Data Portal Installation.doc"  Data Portal Installation]

[source:"ICAT/DevigoTutorial/DataPortal and ICAT Installation.pdf" Data Portal and ICAT Installation Presentation]