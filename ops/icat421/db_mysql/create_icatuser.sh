#!/bin/bash
#
# $Id$
#

# load user details
. ./properties.sh

mysql -u$DB_ICATUSER_USERNAME -p$DB_ICATUSER_PASSWORD <<EOF
use icatuser;
INSERT INTO PASSWD values ('$ICATUSER_USERNAME', '$ICATUSER_PASSWORD');
EOF

#
# - the end -
#