#!/bin/bash

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
    # copy relevant glasshfish.props files for db to icat and authn_db configuration directories
    cp db_$SELECTED_DATABASE/glassfish.props.icat icat.ear.config/glassfish.props
    cp db_$SELECTED_DATABASE/glassfish.props.authn_db authn_db.ear.config/glassfish.props

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
