#!/bin/bash
#
# $Id$
#

usage() {
    echo "Usage:"
    echo "$0 init-database"
    echo "$0 create-icatuser"
    echo "$0 stop-database"
    exit 1
}

[[ $# -eq 0 ]] && usage


init-database()
{
    # copy properties.sh to icat and authn_db configuration directories
    cp db_$SELECTED_DATABASE/properties.sh icat.ear.config/properties.sh
    cp db_$SELECTED_DATABASE/properties.sh authn_db.ear.config/properties.sh

    cd db_$SELECTED_DATABASE
    ./init_database.sh
    cd ..
}

create-icatuser() {
    cd db_$SELECTED_DATABASE
    ./create_icatuser.sh
    cd ..
}

stop-database() {
    if [[ "$SELECTED_DATABASE" == "derby" ]]
    then
        cd db_$SELECTED_DATABASE
        ./stop_database.sh
        cd ..
    fi
}


if [[ $SELECTED_DATABASE != "derby" ]] && 
   [[ $SELECTED_DATABASE != "mysql" ]] &&
   [[ $SELECTED_DATABASE != "oracle" ]]
then
    echo "Please select a database"
    exit
fi

case "$1" in
    "init-database")
        init-database
        ;;
    "create-icatuser")
        create-icatuser
        ;;
    "stop-database")
        stop-database
        ;;
    *)
        echo "Unrecognised option \`$1\`"
        usage
        ;;
esac

# - the end -
