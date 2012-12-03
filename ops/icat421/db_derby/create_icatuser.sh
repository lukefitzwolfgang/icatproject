#!/bin/bash
#
# $Id$
#

# load user details
. ./properties.sh

ij <<EOF
connect 'jdbc:derby://localhost:1527/icatuser;Password=$DB_USERNAME:User=$DB_PASSWORD';
INSERT INTO PASSWD (USERNAME, ENCODEDPASSWORD) VALUES ('$ICATUSER_USERNAME', '$ICATUSER_PASSWORD');
EOF

#
# - the end -
#
