#!/bin/bash
#
# $Id$
#

# load configuration details
. ./properties.sh

ij <<EOF
connect 'jdbc:derby://$SERVER:1527/icatuser;Password=$DB_USERNAME:User=$DB_PASSWORD';
INSERT INTO PASSWD (USERNAME, ENCODEDPASSWORD) VALUES ('$ICATUSER_USERNAME', '$ICATUSER_PASSWORD');
EOF

#
# - the end -
#
