# $Id$

Oracle Instructions
===================

Before you deploy ICAT make sure you have followed the steps below.

1) The Glassfish server requires the Oracle JDBC driver jar file, ojdbc6.jar. This jar
   will need to be put in $GLASSFISH_HOME/glassfish/domains/domain1/lib before
   starting the domain. It can be obtained from the Oracle website.

2) This installation also requires SQLPlus which can be obtained from the
   Oracle website. Make sure the glassfish3 user has SQLPlus on the PATH.

3) ./properties.sh contains the usernames and passwords for various accounts
   that need to be created. If you make any changes please make sure these are 
   reflected in both of the properties.sh file in this directory.

- the end -
