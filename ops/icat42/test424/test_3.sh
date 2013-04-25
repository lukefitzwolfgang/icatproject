#! /bin/bash
#
# $Id$
#

#set -x

export         properties='example.properties'
                     wsdl=`grep ^wsdl ${properties}`
                  IDS_URL=`./change_u.sh $wsdl`

          DownloadManager=DownloadManager
      ids_cmd_prepareData=prepareData
       ids_cmd_preparedId=preparedId
        ids_cmd_getStatus=getStatus
          ids_cmd_getData=getData
        ids_status_ONLINE=ONLINE

               sleep_time=1
                curl_post=-d
                     curl="curl --silent"

           icat_cmd_Login="Login"
          icat_cmd_Logout="Logout"
          icat_cmd_Search="Search"
          icat_Search_arg="Dataset.id"
                 SOMEFILE=somefile
             SOMEFILE_ZIP=$SOMEFILE.zip

             icat_api_cli="./test_2.sh"

[ -f $SOMEFILE_ZIP ] && rm $SOMEFILE_ZIP
STATUS=
SESSION_ID=`$icat_api_cli $icat_cmd_Login`
DATASET_ID=`$icat_api_cli $icat_cmd_Search $icat_Search_arg`
PREPARED_ID=`$curl $curl_post "sessionId=$SESSION_ID&datasetIds=$DATASET_ID" $IDS_URL/$DownloadManager/$ids_cmd_prepareData`
export k=0
export mk=100

while [     \( "$STATUS" != "$ids_status_ONLINE" \) \
         -a \( $k -lt $mk \) ] 
do
    STATUS=`$curl $IDS_URL/$DownloadManager/$ids_cmd_getStatus?$ids_cmd_preparedId=$PREPARED_ID`
    printf .
    sleep $sleep_time
    export k=`expr $k + 1` 
done
if [ "$STATUS" = "$ids_status_ONLINE" ]
then
    echo ready! '(' $k ')' ... starting download
    $curl $IDS_URL/$DownloadManager/$ids_cmd_getData?$ids_cmd_preparedId=$PREPARED_ID > $SOMEFILE_ZIP
    $icat_api_cli $icat_cmd_Logout $SESSION_ID
    ls -l $SOMEFILE_ZIP
    unzip -vf $SOMEFILE
    unzip -o  $SOMEFILE
else
    echo timeout! '(' $k ')' ... terminating
    exit 1
fi

#
# - the end -
#
