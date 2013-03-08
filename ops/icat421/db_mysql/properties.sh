#!/bin/bash
#
# $Id$
#

#
# Please check that the following properties are correct 
#

SERVER="localhost"

# mysql database administrator
DB_USERNAME="root"
DB_PASSWORD="password"

# Must contain "domains/"
GLASSFISH=/home/glassfish3/glassfish3/glassfish

# Port for glassfish admin calls (normally 4848)
PORT=4848

#
# The following properties can be left unchanged for
# non-production ICAT deployments 
#

# ICAT database credentials
DB_ICAT_USERNAME="icat"
DB_ICAT_PASSWORD="myicatpasswd"

# AUTHN_DB database credentials
DB_ICATUSER_USERNAME="icatuser"
DB_ICATUSER_PASSWORD="myicatuserpasswd"

# ICAT test user
ICATUSER_USERNAME="root"
ICATUSER_PASSWORD="password"

# ICAT connection driver and connection properties
ICAT_DRIVER=com.mysql.jdbc.jdbc2.optional.MysqlDataSource
ICAT_PROPERTIES=url="'"jdbc:mysql://$SERVER:3306/icat"'":user=$DB_ICAT_USERNAME:password=$DB_ICAT_PASSWORD:databaseName=icat

# Authn DB driver and connection properties
AUTHDB_DRIVER=com.mysql.jdbc.jdbc2.optional.MysqlDataSource
AUTHDB_PROPERTIES=url="'"jdbc:mysql://$SERVER:3306/icatuser"'":user=$DB_ICATUSER_USERNAME:password=$DB_ICATUSER_PASSWORD:databaseName=icatuser

#
# - the end -
#

