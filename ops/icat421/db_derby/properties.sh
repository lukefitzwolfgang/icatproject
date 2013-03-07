#!/bin/bash
#
# $Id$
#

#
# Please check that the following properties are correct 
#

SERVER="localhost"

# database administrator
DB_USERNAME="APP"
DB_PASSWORD="APP"

# Must contain "domains/"
GLASSFISH=/home/glassfish3/glassfish3/glassfish

# Port for glassfish admin calls (normally 4848)
PORT=4848

#
# The following properties can be left unchanged for
# non-production ICAT deployments 
#

# ICAT test user
ICATUSER_USERNAME="root"
ICATUSER_PASSWORD="password"

# ICAT connection driver and connection properties
ICAT_DRIVER=org.apache.derby.jdbc.ClientDataSource
ICAT_PROPERTIES=Password=$DB_PASSWORD:User=$DB_USERNAME:serverName=$SERVER:DatabaseName=icat:connectionAttributes=";"create"'"="'"true

# Authn DB driver and connection properties
AUTHDB_DRIVER=org.apache.derby.jdbc.ClientDataSource
AUTHDB_PROPERTIES=Password=$DB_PASSWORD:User=$DB_USERNAME:serverName=$SERVER:DatabaseName=icatuser:connectionAttributes=";"create"'"="'"true

#
# - the end -
#
