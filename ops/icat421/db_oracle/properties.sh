#!/bin/bash
#
# $Id$
#

#
# Please check that the following properties are correct 
#

SERVER="localhost"

# oracle database administrator
DB_USERNAME="sys"
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
ICAT_DRIVER=oracle.jdbc.pool.OracleDataSource 
ICAT_PROPERTIES=url="'"jdbc:oracle:thin:@//$SERVER:1521/XE"'":ImplicitCachingEnabled=true:MaxStatements=200:user=$DB_ICAT_USERNAME:password=$DB_ICAT_PASSWORD

# Authn DB driver and connection properties
AUTHDB_DRIVER=oracle.jdbc.pool.OracleDataSource 
AUTHDB_PROPERTIES=url="'"jdbc:oracle:thin:@//$SERVER:1521/XE"'":ImplicitCachingEnabled=true:MaxStatements=200:user=$DB_ICATUSER_USERNAME:password=$DB_ICATUSER_PASSWORD

#
# - the end -
#