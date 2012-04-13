README.txt for TOPCAT 4.0 installation
Harjinder Chahal - 03 April 2012
$Id: readme.txt 1208 2012-04-05 13:38:19Z ssx11488@FED.CCLRC.AC.UK $

For more detailed instructions please visit:
http://code.google.com/p/icatproject/wiki/InstallTopcat40
http://code.google.com/p/icatproject/wiki/InstallIcat40

TOPCAT is an companion product for ICAT.  It provides a user friendly way to browse the contents of an ICAT.  

These installation instructions are intended for installing Topcat as a companion to ICAT40.

In order to make it simple to install TOPCAT, the following configuration is assumed:

ICAT40: the location of an installation of ICAT40.
../icat40 - installed and accessible in the specified directory. The location of icat40 can be changed in the deploy.props.

Ports:
The port 8181 is available for the Glassfish Server to deploy Topcat.

Glassfish Server:
topcat.properties - has been added to /home/glassfish3/glassfish3/glassfish/domains/domain1/lib/classes

Configuration is stored in the following files:
topcat40/deploy.props
topcat40/topcat.properties
topcat40/icat.list
topcat40/localhost.icat
topcat40/icata.icat
topcat40/icatb.icat

Instructions:

Create a test environment similar to the one assumed in these instructions in addition to the one for ICAT40. If it is not possible to be identical, then deploy.props will require changes. 

Ensure the following conditions on the system: 

- Database server running;
- Glassfish server running;
- the file topcat.properties is available in the appropriate directory for the glassfish server (see above).

Do the following:

# Create schemas and initialise the tables  
cd topcat40
./deploy.sh setupDB
cd ..

# Create the database pools
cd topcat40
./deploy.sh create
cd ..

# Add ICAT connection information
cd topcat40
./deploy.sh setupICAT
cd ..

# Deploy topcat
cd topcat40
./deploy.sh deploy topcat
cd ..

# Logon to topcat 
https://localhost:8181/TOPCATWeb.jsp 


To stop, undeploy topcat and delete the connection pools, do the following:
cd topcat40
./deploy.sh undeploy topcat
./deploy.sh delete
cd ..

To delete the ICAT connection information and the topcat schemas, do the following:
cd topcat40
./deploy.sh deleteICAT
./deploy.sh deleteDB



The script deploy.sh suppports the following arguments:
----------------

setupDB			Creates and populates the database schemas for Topcat
setupICAT		Populates the database schemas for Topcat with information about the available ICATs 
deleteDB		Complement of setupDB 
deleteICAT		Complement of setupICAT 
create			Creates the jdbc connection pools between the database and glassfish. Please ensure that the parameters in deploy.props are correctly defined.
delete			Deletes the jdbc connection pools between the database and glassfish.
deploy			Use with topcat to deploy the specified application.
undeploy 		Use with topcat to undeploy the specified application.

# - the end - 
