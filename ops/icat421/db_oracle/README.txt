# $Id$

Oracle Instructions
===================

Before you deploy ICAT make sure you have followed the steps below.

1) The Glassfish server requires the Oracle JDBC driver. This jar will need to 
   be put in $GLASSFISH_HOME/glassfish/domains/domain1/lib before starting the
   domain. It can be obtained from the Oracle website.

2) ./vars contains the usernames and passwords for various accounts that need
   to be created. If you make any changes please make sure these are reflected
   in both of the glassfish.props files in this directory.

3) This installation also requires SQLPlus which can be obtained from the
   Oracle website. If you have trouble running SQLPlus after installing it
   from an rpm, check you PATH and LD_LIBRARY_PATH.

- the end -