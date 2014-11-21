#!/bin/bash
#
#
# Authors: 
#  - initial:Leonardo Sala (Leonardo.sala@psi.ch) 
#  - modified : Jan Solca (jan.solca@psi.ch) 
# 
# Script to install Glassfish 4 as current user. Please use chmod on Glassfish 4 'GLASSDIR' to change owner. 
#
# TODO Idea is to provide a parameter asking for extended setup which will change default password of keystore and certificate 
#


## Globals Noteworthy: 

declare CHOICE 



## SHoW Help 
function show_help(){

## printf "Usage: %s [-x] [-f cfg_file]\n\t [-x] : extended Setup\n\t [-f cfg_file] : /path/to/config/file\n " $(basename $0) >&2

cat << EOF

Usage: ${0##*/} [-x] [-f CFGFILE] [-d Dir] [-i Dir] [-n] [-h] [-y] [-c] [-t]

       Install Glassfish 4 J2EE Server as current user under '$GLASSFISH_DIR' 

          -x            Extended Setup  --  change store password and certificate NOT YET IMPLEMENTED !!!
          -f CFGFILE    Config File     --  absolute path to the config file containing parameters to be overwritten (partial overwrite possible). 
          -d INSTDIR    Setup Dir.      --  absolute path to directory from which the installation is made. Default is current directory.
                                        If no download ( no -n flag ) then all downloaded software should be copied there. 
          -i GLASSDIR   GlassFish Dir.  --  absolute path to directory where GlassFish should be installed 
          -n            Download        --  allows the script to download all the needed software. Otherwise it is assumed software is in the current directory
                                        i.e. same directory as '$(basename $0)'
          -h            print this help message
          -c GLASSDIR   configure-only an existing Glassfish installation
          -y            Assume yes to all questions
          -t            Use it only for a test installation. It will use default passwords etc. NOT SAFE FOR A REAL INSTALLATION.
EOF
}    


## Show Extended: User chose the -x and for the moment it is not implemented 

function show_extended {

cat << EOF

Dear gentle User, 

as claimed in the usage of this program the option '-x' is not yet implemented. The program will stop now !!! 

In the case of uncertainty please use the '-h' option to get the programs's usage. 
EOF
}

 


# Minimalist function for prompting the user for an answer (yes or no). The Text to prompt must be passed as argument to the function
function choice { 

    CHOICE=

    # user prompt must be provided as function's arg 
    local prompt="$*"
    local answer 
 
    read -p "$prompt" answer 
   
    case "$answer" in 
	[yY1]) CHOICE='y' ;;
	[nN0]) CHOICE='n';;
	*    ) CHOICE="$answer";;
    esac 
} 

#-Old declare CURRENTSTEP

## Function to format Step text used to inform user about current Step. Description of current Step must be provided as function's argument
function format_step_text {

    # description of current step. Text to echoed given as function's arg
    local prompt="$*"

    # format with color fore- and background 
    local start=$(tput setaf 1) 
    local mid=$(tput setab 7) 
    local end=$(tput sgr 0)


    local CURRENTSTEP=$(printf "${start} Inst. Step ${mid} %s ${end}" "$prompt") 
    printf "\n$CURRENTSTEP\n\n"
  }  


##################################################################################
#
#  Variables Setup 
#      can be overwritten individually in the configuration file 
#      Please do not overwrite XX_CONN_URL in Config file since the version matching must also be considered..  
# 

format_step_text  "Variables Initialisation" 

#-Old echo "$CURRENTSTEP"

# currently most settings here taken from Leo's original work. The usage of a CFG File is recommended to overwrite with local settings..  
GLASSFISH_DIR=${HOME}/Programs/glassfish4
GLASSFISH_CONN_URL=http://download.java.net/glassfish/4.0/release/glassfish-4.0.zip  

# MYSQL settings 
JAVA_CONN=mysql-connector-java-5.1.30
JAVA_CONN_URL=http://dev.mysql.com/get/Downloads/Connector-J/${JAVA_CONN}.tar.gz

# Default Directory from where the setup is started. Can be overwriten 
SETUP_DIR=$(pwd)


## define flags to identify cmdline options set by user. 
IS_EXTENDED_SETUP=false
IS_CFG_FILE=false
IS_DOWNLOAD=false
IS_SETUP_DIR=false 
IS_GLASS_DIR=false
IS_ALWAYS_YES=false
CONFIG_ONLY=false

# TODO > osx has no wget per default so check first and exit if needed 
#WGET="wget -q -c"
WGET="wget -nv -c"

# Check for empty parameter. No param exits the script and print usage message

if [ ! $#  -gt 0 ] ; then 
    show_help 
    exit 2 
fi


# Here 'getopts' is used in favor of 'getopt' I had lot of trouble with OSX.'getopt' enhanced GNU version is good but not portable.
while getopts 'tyc:xd:ni:f:h' opts
do 
  case $opts in 
#     x) IS_EXTENDED_SETUP=true
#	  #echo "Dbg: Extended Setup was specified. Default pasword and Certificate will be overwritten"
#        ;;  

     x) show_extended
        exit 2
	;;
     f) IS_CFG_FILE=true
        CFG_FILE="$OPTARG"
	  #echo "Dbg: config is : " "$CFG_FILE"
	  #echo "Dbg: Path to Configuration File was specified. Some of the default settings will be overwritten" 
        ;;
     n) IS_DOWNLOAD=true
	  #echo "Dbg: Download the software flag was specified."
	  ;; 
     d) IS_SETUP_DIR=true 
	SETUP_DIR="$OPTARG"
	;;
     i) IS_GLASS_DIR=true
        GLASSFISH_DIR="$OPTARG"
        ;;
     y) IS_ALWAYS_YES=true
	;;
     c) CONFIG_ONLY=true
	GLASSFISH_DIR="$OPTARG"
	;;
     t) TEST_INSTALLATION=true
	;;
     ?) show_help 
        exit 2
        ;;
     h) show_help
	exit 2
	;;
  esac
done

 
shift $(($OPTIND -1))

# source config file to set parameters (will overwrite defaults) 
if $IS_CFG_FILE ; then    
    if [ -f "$CFG_FILE" ] ; then   
	. "$CFG_FILE"
  else 
     printf  "The specified file '%s' was not found !!\n\nAborting the installation ..." "$CFG_FILE"
     exit 2
  fi
fi


# CHECK JAVA_HOME. Do not check this env. var. before CFG File is sourced. Passing a CFG file is still optional. 
if [ ! $JAVA_HOME ]; then
    if [ -d "/usr/java/latest/" ]; then
	JAVA_HOME=/usr/java/latest/;
    elif [ -d "/usr/lib/jvm/java-7-oracle/" ]; then
	JAVA_HOME=/usr/lib/jvm/java-7-oracle/;
    elif [ -d "/usr/lib/jvm/default-java/" ]; then
	JAVA_HOME=/usr/lib/jvm/default-java/;
    else
	echo "Cannot guess JAVA_HOME, please export it. Aborting Installation.."
	exit 2
    fi
fi

echo "Using JAVA_HOME=${JAVA_HOME}"
echo " "
# /usr/lib/jvm/java-7-oracle/
# /usr/java/latest/



## WARN user about exclusive usage CLI / CFG 
declare SUMMARY= 
SUMMARY=$(printf " $(tput setaf 1 setab 7) -- PLEASE DO NOT DEFINE VARIABLES as CLI OPTION and in CONFIG file at the same time. Config file settings  will overwrite command line options !! -- $(tput sgr 0)" "$SUMMARY") 
SUMMARY=$(printf "%s\n\n - The installation directory of Glassfish 4 will be in $GLASSFISH_DIR with the following settings:"  "$SUMMARY")


# Idea: SUMMARY string should be adaptive and summarize the user's choices. Thus string concat with IF was chosen to build the string. I (sj95) tried '[' or '[[' 
# with '&&' notation (instead of the if notation) but somehow on OSX not ok. Linux not tested 
# So as "work around" I used a printf method var=$(printf "%s Newtext" "$var")  where $var is the text to concatenate with. 

if $IS_EXTENDED_SETUP ; then 
   SUMMARY=$(printf "%s\n\n - Extended Setup was specified. Default pasword and Certificate will be overwritten" "$SUMMARY")
fi 

if  $IS_CFG_FILE ; then 
    SUMMARY=$(printf "%s\n\n - Path to Configuration File was specified. Some of the default settings will be overwritten" "$SUMMARY")
    SUMMARY=$(printf "%s\n\t - Path to config file : $CFG_FILE" "$SUMMARY" )
fi

if $IS_DOWNLOAD ; then 
   SUMMARY=$(printf "%s\n\n - The software (Glassfish, MySQL Java Connector BUT not Oracle JDBC Driver) needed by the intaller script will be downloaded from the Internet. Be sure network is correctly set up" "$SUMMARY")
fi 

if $IS_SETUP_DIR; then 
    SUMMARY=$(printf "%s\n\n - The Setup Directory from which the installation is started is '$SETUP_DIR'" "$SUMMARY")
fi 

if $IS_GLASS_DIR; then 
    SUMMARY=$(printf "%s\n\n - The Installation Directory where Glassfish will be installed is '$GLASSFISH_DIR'. Write access is needed!! Otherwise use 'sudo'" "$SUMMARY")
fi 

SUMMARY=$(printf "%s \n\nAre You sure to proceed with these seetings (y/N)? >" "$SUMMARY")

## Asked user if he really wants to install.
if [ $IS_ALWAYS_YES == true ]; then
    CHOICE='y'
else
    choice "$SUMMARY"
fi

if [ $CHOICE == 'y' ] ; then 
      
      #echo "Processing............."
      # need current dir for comparison and new PATH for asadmin CLI 
      MYPWD=`pwd`
      export PATH=${GLASSFISH_DIR}/bin:${PATH}
    
elif [ $CHOICE == 'n' ] ; then 
      
      echo "Installation aborted by user ......"
      exit 2
else 
      ### WRONG INPUT !!!
      echo "Wrong CHOICE, could generate an internal error thus aborting the installation ..."
      exit 3 
fi 



# goto SETUP_DIR or create it 

if [ "$SETUP_DIR" != "$MYPWD" ] ; then 

    [ -d "$SETUP_DIR" ] || mkdir "$SETUP_DIR" 

    # Primarily for permission issues 
    if test $? -gt 0; then
	echo "The directory $SETUP_DIR could not be created......"
	echo "Aborting Installation..." 
	exit 4
    fi
fi 


## START INSTALLATION in the SETUP DIR. ##

# CHECk if user as right to create GLASSDIR  i.e.  write permission on parent dir of GLASSDIR




# SETUP_DIR is either pwd or can be overwritten cmdline '-d' or in CFG FILE 
cd $SETUP_DIR 

# Download Glassfish software and MySQL Java Connector. For Oracle manual download and setup because of OTN download.. 
if $IS_DOWNLOAD ; then  
       
    # test if unzip,wget are installed (do not use java jar as alternative as unzip) 
    if ! type "$(which unzip)" > /dev/null ; then 
	echo "UNZIP executable not found on path ..." 
	echo "Aborting Installation .... "
	exit 4
    fi
    
    if ! [ -x "$(command -v wget)" ]; then

	echo "WGET executable not found on path ..."  >&2
	echo "Aborting Installation .... "
	exit 4
    fi

    format_step_text " Process Downloading software....."      

    $WGET $GLASSFISH_CONN_URL  
    $WGET $JAVA_CONN_URL  
fi


# Unzip / UnTar 
# Remember the Glassfish ZIP file contains glassfish's root directory (glassfih4), don't know (under OSX default unzip) how to unpack without glassfish4 root directory. So must be removed afterwards  

if [ $CONFIG_ONLY != true ]; then 
    # remove OLD GLASSFISH if it exists. Prompt user to do it because using 'rm -rf *' as sudo is fatal 
    if [ -d "$GLASSFISH_DIR" ]; then
	if [ "$(ls -A $GLASSFISH_DIR)" ]; then
	    echo "Old Glassfish Directory $GLASSFISH_DIR must be removed first !!! Be careful and BACKUP any config file first. Aborting Installation...." ; 
	    exit 2;
	fi
    else
	echo "$DIR is empty, good enough..."
    fi

    #[ -d "$GLASSFISH_DIR/" ] && { echo "Old Glassfish Directory $GLASSFISH_DIR must be removed first !!! Be careful and BACKUP any config file first. Aborting Installation...." ; exit 2; } 

    format_step_text  "Process unpacking software" 

    # Obsolete : using the overwrite mode of unzip (-o). 
    # Obsolete; Remark : do not use -fo since will perform a kind of update thus if directory not present no error displayed but also no unpacking made   

    unzip -q -d "$GLASSFISH_DIR" gl*.zip
    
    if test $? -gt 0; then
	echo "The glassfish ZIP file not found in $SETUP_DIR directory or could not be unzipped ......"
	echo "Aborting Installation..." 
	exit 4
    fi 
 
    cd "$GLASSFISH_DIR/glassfish4" 
    mv * ..
    cd ..
    # due to the hidden directory ".org.opensolaris,pkg/"  forced recursive remove is used  
    rm -rf glassfish4
fi

cd $SETUP_DIR
tar xzf ${JAVA_CONN}.tar.gz

cp ${JAVA_CONN}/${JAVA_CONN}-bin.jar ${GLASSFISH_DIR}/glassfish/lib/      # Global 
#cp ${JAVA_CONN}/${JAVA_CONN}-bin.jar ${GLASSFISH_DIR}/glassfish/domains/domain1/lib/   # Domain specific 
 

# Prepare Glassfish for ICAT 
format_step_text  "Post-processing Glassfish Configuration" 

# configure JDK to use inside Glassfish
[ ! -z "$JAVA_HOME" ] &&  echo "AS_JAVA=$JAVA_HOME" 

asadmin stop-domain domain1
asadmin delete-domain domain1

if [ $TEST_INSTALLATION == true ]; then
    echo "AS_ADMIN_PASSWORD=password" > pwdfile.txt
    ASADM_OPTS="-u admin -W pwdfile.txt"
else
    ASADM_OPTS=""
fi

asadmin  ${ASADM_OPTS} create-domain domain1
asadmin start-domain
if [ $TEST_INSTALLATION == false ]; then
    asadmin $ASADM_OPTS login
fi

asadmin $ASADM_OPTS enable-secure-admin
asadmin restart-domain

#asadmin start-domain 

format_step_text  "Security setup" 

asadmin $ASADM_OPTS set server.http-service.access-log.format="common"
asadmin $ASADM_OPTS set server.http-service.access-logging-enabled=true
asadmin $ASADM_OPTS set server.thread-pools.thread-pool.http-thread-pool.max-thread-pool-size=128
# Mandatory otherwise icat will not work on glasshfish 4
asadmin $ASADM_OPTS set configs.config.server-config.cdi-service.enable-implicit-cdi=false
asadmin $ASADM_OPTS set server.ejb-container.property.disable-nonportable-jndi-names="true"
asadmin $ASADM_OPTS delete-ssl --type http-listener http-listener-2
asadmin $ASADM_OPTS delete-network-listener http-listener-2
asadmin $ASADM_OPTS create-network-listener --listenerport 8181 --protocol http-listener-2 http-listener-2
asadmin $ASADM_OPTS create-ssl --type http-listener --certname s1as --ssl3enabled=false --ssl3tlsciphers +SSL_RSA_WITH_RC4_128_MD5,+SSL_RSA_WITH_RC4_128_SHA http-listener-2

format_step_text  "Restarting Glassfish" 
asadmin stop-domain
asadmin start-domain

format_step_text  "Post-processing Glassfish Certificates by still defaults"
cd ${GLASSFISH_DIR}/glassfish/domains/domain1/config/
sudo ${JAVA_HOME}/bin/keytool -export -keystore keystore.jks -file cert -storepass changeit -alias s1as
cd ${JAVA_HOME}/jre/lib/security/
sudo cp cacerts jssecacerts
# default pwd is "changeit"
sudo ${JAVA_HOME}/bin/keytool -import -keystore jssecacerts -file ${GLASSFISH_DIR}/glassfish/domains/domain1/config/cert   -storepass changeit -alias `hostname -f` -noprompt 

format_step_text  "Restarting Glassfish" 
asadmin stop-domain
asadmin start-domain


cd $MYPWD
echo "Done"
