# $Id$

MySQL Instructions
==================

Before you deploy ICAT make sure you have followed the steps below.

1) The Glassfish server requires the MySQL JDBC driver. This jar will need to
   be put in $GLASSFISH_HOME/glassfish/domains/domain1/lib before starting the
   domain. It can be obtained from the MySQL website (Connector/J).

2) ./properties.sh contains the usernames and passwords for the MySQL database
   admin, the two database users that need to be created for ICAT and the test
   ICAT user. If you make any changes please make sure these are reflected in
   both of the glassfish.props files in this directory.

- the end -