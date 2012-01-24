#!/bin/sh
#set -x
#
# $Id: glassfish.sh 1167 2012-01-19 17:37:36Z abm65@FED.CCLRC.AC.UK $
#
opts="create, delete, list, start, stop, restart, deploy, undeploy or redeploy"

if [ $# = 0 ]; then
    echo Must set one parameter from $opts and optionally icat, where relevant
    exit 1
fi

props=./glassfish.props
if [ ! -f $props ]; then
    echo There is no $props file
    exit 1
fi

. $props

for key in url icatName icatPW icatuserName icatuserPW glassfish port adminuser passwordfile; do
    eval val='$'$key
    if [ -z "$val" ]; then
        echo $key must be set in $props file
        exit 1
    fi
done

asadmin="$glassfish/bin/asadmin --port $port --user $adminuser --passwordfile $passwordfile"

if [ $1 = start ]; then
    $asadmin <<EOF
start-database --dbhost 127.0.0.1
start-domain
EOF
    exit 0
elif [ $1 = stop ]; then
    $asadmin <<EOF
stop-domain
stop-database --dbhost 127.0.0.1
EOF
z=$(ps -efl | grep glassfish | grep java)
if [ -n "$z" ]; then
	echo "Glassfish is still there - give it 5 more seconds"
	sleep 5
	set $z
	kill -9 $4
fi
    exit 0
elif [ $1 = restart ]; then
    $asadmin <<EOF
stop-domain
stop-database --dbhost 127.0.0.1
EOF
z=$(ps -efl | grep glassfish | grep java)
if [ -n "$z" ]; then
	echo "Glassfish is still there - give it 5 more seconds"
	sleep 5
	set $z
	kill -9 $4
fi
	$asadmin <<EOF
start-database --dbhost 127.0.0.1
start-domain
EOF
    exit 0
fi

version=$($asadmin version | head -1)
echo $version | grep "Version string could not be obtained from Server" > /dev/null
if [ $? = 0 ]; then
    echo "Server appears not to be running so will attempt to start it"    
    $asadmin <<EOF
start-database --dbhost 127.0.0.1
start-domain
EOF
    version=$($asadmin version | head -1)
    echo $version | grep "Version string could not be obtained from Server" > /dev/null
    if [ $? = 0 ]; then
        echo "Server still not running - so give up."
        exit 1
    fi
fi

v=
for i in 3.0.1 3.1
do
echo $version | grep $i > /dev/null
if [ $? = 0 ]; then
    v=$i
fi
done

if [ -z $v ]; then
    echo version $version is not supported
    exit 1
fi

if [ $1 = create ]; then
    $asadmin <<EOF
create-jdbc-connection-pool \
   --datasourceclassname oracle.jdbc.pool.OracleDataSource --restype javax.sql.DataSource \
   --failconnection=true --steadypoolsize 2 --maxpoolsize 8 \
   --property url="'"${url}"'":password=${icatPW}:user=${icatName}:ImplicitCachingEnabled=true:MaxStatements=200 \
   icat3
create-jdbc-connection-pool \
   --datasourceclassname oracle.jdbc.pool.OracleDataSource --restype javax.sql.DataSource \
   --failconnection=true --steadypoolsize 2 --maxpoolsize 8 \
   --property url="'"${url}"'":password=${icatuserPW}:user=${icatuserName}:ImplicitCachingEnabled=true:MaxStatements=200 \
   icat3-user
create-jdbc-resource --connectionpoolid icat3 jdbc/icat3
create-jdbc-resource --connectionpoolid icat3-user jdbc/icat3-user
EOF
 	echo Ensure that "Thread Pools" below the configurations has an http-thread-pool with a "Max Thread Pool Size" of 128.
 	echo There may be more than one configuration to change. Change them all.

    fname=$glassfish/glassfish/domains/domain1/lib/ojdbc14.jar
    if [ ! -f $fname ]; then
	echo Warning $fname does not exist
    fi

elif [ $1 = delete ]; then
    $asadmin <<EOF
delete-jdbc-resource jdbc/icat3
delete-jdbc-resource jdbc/icat3-user
delete-jdbc-connection-pool icat3
delete-jdbc-connection-pool icat3-user
EOF
elif [ $1 = list ]; then
    $asadmin <<EOF
list-applications
EOF
elif [ $1 = deploy ]; then
    if [ -n "$icat" -a '(' -z "$2" -o "$2" = icat ')' ]; then
        echo "*** Deploying icat"
        echo $0 $1 $icat
        $asadmin --interactive=false <<EOF
deploy $icat
EOF
    fi
    
elif [ $1 = undeploy ]; then
    if [ -n "$icat" -a '(' -z "$2" -o "$2" = icat ')' ]; then
  	echo "*** Undeploying icat"
        icatBase=`basename $icat`
        icatEarStem=${icatBase%%.ear}
        echo $0 $1 $icatEarStem
        $asadmin --interactive=false <<EOF
undeploy $icatEarStem
EOF
    fi

elif [ $1 = redeploy ]; then
    if [ -n "$icat" -a '(' -z "$2" -o "$2" = icat ')' ]; then
       	echo "*** Redeploying icat"
        icatBase=`basename $icat`
        icatEarStem=${icatBase%%.ear}
        echo $0 $1 $icatEarStem
        $asadmin --interactive=false <<EOF
redeploy --name $icatEarStem $icat
EOF
    fi
else
    echo Must set one parameter to $opts
    exit 1
fi
#
# - the end -
#
