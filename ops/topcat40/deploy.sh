#!/bin/sh

# $Id: deploy.sh 1210 2012-04-06 17:01:15Z abm65@FED.CCLRC.AC.UK $

opts="setupDB, deleteDB, setupICAT, deleteICAT, create, delete, deploy, undeploy"

if [ $# = 0 ]; then
    echo Must set one parameter from $opts and optionally topcat or downloadManager where relevant
    echo See glassfish.sh for additional options
    exit 1
fi

props=./deploy.props 
if [ ! -f $props ]; then
    echo There is no $props file
    exit 1
fi
. $props

icatlist=./icat.list
if [ ! -f $icatlist ]; then
    echo There is no $icatlist file
exit 1
fi

fname=$glassfish/glassfish/domains/domain1/lib/ojdbc14.jar
TopCATDB=TopCATDB
downloadpmDB=downloadmanager__pm
downloadNontxDB=downloadmanager__nontx
topcatBase=`basename $topcat`
topcatWarStem=${topcatBase%%.war}
downloadManagerBase=`basename $downloadManager`
downloadManagerWarStem=${downloadManagerBase%%.war}
asadmin="$glassfish/bin/asadmin --port $port --user $adminuser --passwordfile $passwordfile"

for key in glassfishprops sysPW databaseServer topcatName topcatPW downloadName downloadPW topcat downloadManager; do
    eval val='$'$key
    if [ -z "$val" ]; then
        echo $key must be set in $props file
        exit 1
    fi
done

if [ $1 = setupDB ]; then
sqlplus sys/$sysPW@$databaseServer AS SYSDBA <<EOF
CREATE USER $topcatName PROFILE "DEFAULT" IDENTIFIED BY "$topcatPW" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP" QUOTA UNLIMITED ON "USERS" ACCOUNT UNLOCK; 
CREATE USER $downloadName PROFILE "DEFAULT" IDENTIFIED BY "$downloadPW" DEFAULT TABLESPACE "USERS" TEMPORARY TABLESPACE "TEMP" QUOTA UNLIMITED ON "USERS" ACCOUNT UNLOCK;
@createuser_topcat_db.sql
EOF
sqlplus $topcatName/$topcatPW@$databaseServer @generate_topcat_db.sql | tee generate_topcat_db.log
sqlplus $topcatName/$topcatPW@$databaseServer @initialise_topcat_db.sql | tee initialise_topcat_db.log

elif [ $1 = deleteDB ]; then
sqlplus sys/$sysPW@$databaseServer AS SYSDBA <<EOF
DROP USER $topcatName CASCADE;
DROP USER $downloadName CASCADE;
EOF
echo "Dropped users $topcatName and $downloadName"

elif [ $1 = setupICAT ]; then
ID=0
while read icat_descripter_file
do
if [ "${icat_descripter_file:0:1}" != "#" ]; then
. $icat_descripter_file
ID=`expr $ID + 1`
sqlplus $topcatName/$topcatPW@$databaseServer <<EOF
INSERT INTO TOPCAT_ICAT_SERVER (ID, NAME, SERVER_URL, DEFAULT_USER, DEFAULT_PASSWORD, PLUGIN_NAME, DOWNLOAD_PLUGIN_NAME, VERSION) VALUES ('$ID', '$FacilityName', '$ICAT_url', '$ICAT_username', '$ICAT_password', '', '', '$ICAT_version');
EOF
fi
done < $icatlist


elif [ $1 = deleteICAT ]; then
sqlplus $topcatName/$topcatPW@$databaseServer <<EOF
DELETE FROM TOPCAT_ICAT_SERVER; 
EOF
echo "ICAT connection information removed"



elif [ $1 = enable ]; then
    $asadmin <<EOF
    enable $topcatWarStem
EOF

elif [ $1 = disable ]; then
    $asadmin <<EOF
    disable $topcatWarStem
EOF

elif [ $1 = create ]; then
    $asadmin <<EOF
    create-jdbc-connection-pool \
        --allownoncomponentcallers false \
        --associatewiththread false \
        --creationretryattempts  0 \
        --creationretryinterval 10 \
        --leakreclaim false \
        --leaktimeout 0 \
        --validationmethod auto-commit \
        --datasourceclassname oracle.jdbc.pool.OracleDataSource \
        --failconnection false \
        --idletimeout 300 \
        --isconnectvalidatereq false \
        --isisolationguaranteed true \
        --lazyconnectionassociation false \
        --lazyconnectionenlistment false \
        --matchconnections false \
        --maxconnectionusagecount 0 \
        --maxpoolsize 32 \
        --maxwait 60000 \
        --nontransactionalconnections false \
        --poolresize 2 \
        --restype javax.sql.DataSource \
        --statementtimeout -1 \
        --steadypoolsize 8 \
        --validateatmostonceperiod 0 \
        --wrapjdbcobjects false \
        --property User=${topcatName}:Password=${topcatPW}:URL="'"${url}"'" $TopCATDB

    create-jdbc-resource  \
        --connectionpoolid $TopCATDB jdbc/$TopCATDB

    create-jdbc-connection-pool \
        --allownoncomponentcallers false \
        --associatewiththread false \
        --creationretryattempts  0 \
        --creationretryinterval 10 \
        --leakreclaim false \
        --leaktimeout 0 \
        --validationmethod auto-commit \
        --datasourceclassname oracle.jdbc.pool.OracleDataSource \
        --failconnection false \
                 --idletimeout 300 \
                 --isconnectvalidatereq false \
                 --isisolationguaranteed true \
                 --lazyconnectionassociation false \
                 --lazyconnectionenlistment false \
                 --matchconnections false \
                 --maxconnectionusagecount 0 \
                 --maxpoolsize 32 \
                 --maxwait 60000 \
                 --nontransactionalconnections false \
                 --poolresize 2 \
                 --restype javax.sql.DataSource \
                 --statementtimeout -1 \
                 --steadypoolsize 8 \
                 --validateatmostonceperiod 0 \
                 --wrapjdbcobjects false \
                 --property User=${downloadName}:Password=${downloadPW}:URL="'"${url}"'" $downloadpmDB

    create-jdbc-resource  \
                 --connectionpoolid $downloadpmDB jdbc/$downloadpmDB

    create-jdbc-connection-pool \
                 --allownoncomponentcallers false \
                 --associatewiththread false \
                 --creationretryattempts  0 \
                 --creationretryinterval 10 \
                 --leakreclaim false \
                 --leaktimeout 0 \
                 --validationmethod auto-commit \
                 --datasourceclassname oracle.jdbc.pool.OracleDataSource \
                 --failconnection false \
                 --idletimeout 300 \
                 --isconnectvalidatereq false \
                 --isisolationguaranteed true \
                 --lazyconnectionassociation false \
                 --lazyconnectionenlistment false \
                 --matchconnections false \
                 --maxconnectionusagecount 0 \
                 --maxpoolsize 32 \
                 --maxwait 60000 \
                 --nontransactionalconnections false \
                 --poolresize 2 \
                 --restype javax.sql.DataSource \
                 --statementtimeout -1 \
                 --steadypoolsize 8 \
                 --validateatmostonceperiod 0 \
                 --wrapjdbcobjects false \
                 --property User=${downloadName}:Password=${downloadPW}:URL="'"${url}"'" $downloadNontxDB

    create-jdbc-resource  \
                 --connectionpoolid $downloadNontxDB jdbc/$downloadNontxDB
EOF
     fname=$glassfish/glassfish/domains/domain1/lib/ojdbc14.jar
    if [ ! -f $fname ]; then
        echo Warning $fname does not exist
    fi


elif [ $1 = delete ]; then
    $asadmin <<EOF
        delete-jdbc-resource jdbc/$TopCATDB
        delete-jdbc-connection-pool $TopCATDB 
        delete-jdbc-resource jdbc/$downloadpmDB
        delete-jdbc-connection-pool $downloadpmDB 
        delete-jdbc-resource jdbc/$downloadNontxDB 
        delete-jdbc-connection-pool $downloadNontxDB 
EOF

elif [ $1 = deploy ]; then
    if [ -n "$topcat" -a '(' -z "$2" -o "$2" = topcat ')' ]; then
        echo "*** Deploying topcat"
        echo $0 $1 $topcat
        $asadmin --interactive=false <<EOF
        deploy $topcat
EOF
    fi
    if [ -n "$downloadManager" -a '(' -z "$2" -o "$2" = downloadManager ')' ]; then
        echo "*** Deploying downloadManager"
        echo $0 $1 $downloadManager
        $asadmin --interactive=false <<EOF
        deploy $downloadManager
EOF
    fi

elif [ $1 = undeploy ]; then
    if [ -n "$topcat" -a '(' -z "$2" -o "$2" = topcat ')' ]; then
        echo "*** Undeploying topcat"
        echo $0 $1 $topcatWarStem
        $asadmin --interactive=false <<EOF
        undeploy $topcatWarStem
EOF
    fi  
    if [ -n "$downloadManager" -a '(' -z "$2" -o "$2" = downloadManager ')' ]; then
        echo "*** Undeploying downloadManager"
        echo $0 $1 $downloadManagerWarStem
        $asadmin --interactive=false <<EOF
        undeploy $downloadManagerWarStem
EOF
    fi

else
    echo Must set one parameter to $opts
    exit 1
fi
#
# - the end -
#

