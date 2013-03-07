#!/bin/bash
#
# $Id$
#

# load configuration details
. ./properties.sh

sqlplus -s $DB_ICATUSER_USERNAME/$DB_ICATUSER_PASSWORD@localhost <<EOF
set serveroutput on
set heading off
set feedback on

INSERT INTO PASSWD VALUES ('$ICATUSER_USERNAME', '$ICATUSER_PASSWORD');

exit
EOF

#
# - the end -
#