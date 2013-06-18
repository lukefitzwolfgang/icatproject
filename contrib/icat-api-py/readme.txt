# README.txt

# $Id$

# This set of scripts provides a simple way to connect to an icat and do simple operations


# define the icat connection information

. ./domain5.icat

# Check that you can connect

./authenticate.py $icat $authenticator $credentials

# Check that you can search

./search.py $icat $authenticator $credentials Datafile

# Check that you can create and use a sessionId

SessionId=`./login.py $icat $authenticator $credentials`
./search.py $icat $SessionId Datafile
./getUserName.py $icat $SessionId
./getRemainingMinutes.py $icat $SessionId
sleep 10
./getRemainingMinutes.py $icat $SessionId
./logout.py $icat $SessionId
./getApiVersion.py $icat



# ++ . ./domain5.icat
# +++ export 'user=username alistair.mills@btinternet.com'
# +++ user='username alistair.mills@btinternet.com'
# +++ export 'pass=password 711978200'
# +++ pass='password 711978200'
# +++ export authenticator=db
# +++ authenticator=db
# +++ credentials='username alistair.mills@btinternet.com password 711978200'
# +++ export icat=http://www.icatproject.org:5080
# +++ icat=http://www.icatproject.org:5080
# ++ ./authenticate.py http://www.icatproject.org:5080 db username alistair.mills@btinternet.com password 711978200
# authenticator = db : Session ID = 4814e551-bbaf-4a61-9de3-e65637c29648 : API Version = 4.2.5
# ++ ./search.py http://www.icatproject.org:5080 db username alistair.mills@btinternet.com password 711978200 Datafile
# [(datafile){
#    createId = "alistair.mills@btinternet.com"
#    createTime = 2013-04-25 15:50:30
#    modTime = 2013-04-25 15:50:30
#    id = 195
#    modId = "alistair.mills@btinternet.com"
#    description = "This is a description of data file 1"
#    location = "datafile1.txt"
#    name = "My Datafile 1"
#  }, (datafile){
#    createId = "alistair.mills@btinternet.com"
#    createTime = 2013-04-25 15:50:30
#    modTime = 2013-04-25 15:50:30
#    id = 196
#    modId = "alistair.mills@btinternet.com"
#    description = "This is a description of data file 2"
#    location = "datafile2.txt"
#    name = "My Datafile 2"
#  }]
# +++ ./login.py http://www.icatproject.org:5080 db username alistair.mills@btinternet.com password 711978200
# ++ SessionId=aff8d1d2-ee3d-4904-81b1-135331960c5d
# ++ ./search.py http://www.icatproject.org:5080 aff8d1d2-ee3d-4904-81b1-135331960c5d Datafile
# [(datafile){
#    createId = "alistair.mills@btinternet.com"
#    createTime = 2013-04-25 15:50:30
#    modTime = 2013-04-25 15:50:30
#    id = 195
#    modId = "alistair.mills@btinternet.com"
#    description = "This is a description of data file 1"
#    location = "datafile1.txt"
#    name = "My Datafile 1"
#  }, (datafile){
#    createId = "alistair.mills@btinternet.com"
#    createTime = 2013-04-25 15:50:30
#    modTime = 2013-04-25 15:50:30
#    id = 196
#    modId = "alistair.mills@btinternet.com"
#    description = "This is a description of data file 2"
#    location = "datafile2.txt"
#    name = "My Datafile 2"
#  }]
# ++ ./getUserName.py http://www.icatproject.org:5080 aff8d1d2-ee3d-4904-81b1-135331960c5d
# alistair.mills@btinternet.com
# ++ ./getRemainingMinutes.py http://www.icatproject.org:5080 aff8d1d2-ee3d-4904-81b1-135331960c5d
# 119.977816667
# ++ sleep 10
# ++ ./getRemainingMinutes.py http://www.icatproject.org:5080 aff8d1d2-ee3d-4904-81b1-135331960c5d
# 119.806316667
# ++ ./logout.py http://www.icatproject.org:5080 aff8d1d2-ee3d-4904-81b1-135331960c5d
# ++ ./getApiVersion.py http://www.icatproject.org:5080
# 4.2.5

#
# - the end -
#
