#! /bin/bash
#
# $Id$
#

export         properties='example.properties'
export     CURL_CA_BUNDLE=sig-11.crt
export            std_err=std.err
                     wsdl=`grep ^wsdl ${properties}`
                  IDS_URL=`./change_u.sh $wsdl`

          DownloadManager=ids
      ids_cmd_prepareData=prepareData
       ids_cmd_preparedId=preparedId
        ids_cmd_getStatus=getStatus
          ids_cmd_getData=getData
        ids_status_ONLINE=ONLINE

               sleep_time=1
                curl_post=-d
                     curl="curl "

           icat_cmd_Login="Login"
          icat_cmd_Logout="Logout"
          icat_cmd_Search="Search"
          icat_Search_arg="Dataset.id"

             icat_api_cli="./icat_cli.sh"
         

#set -x
SESSION_ID=`$icat_api_cli $icat_cmd_Login`
echo  SESSION_ID=$SESSION_ID
for DATASET_ID in `$icat_api_cli $icat_cmd_Search $icat_Search_arg`
do
   DATASET_ZIP=$DATASET_ID.zip
   [ -f $DATASET_ZIP ] && rm $DATASET_ZIP
   PREPARED_ID=`$curl $curl_post "sessionId=$SESSION_ID&datasetIds=$DATASET_ID" $IDS_URL/$DownloadManager/$ids_cmd_prepareData 2>>$std_err`
   echo PREPARED_ID=$PREPARED_ID
   GETDATA=`$curl $IDS_URL/$DownloadManager/"$ids_cmd_getData?sessionId=$SESSION_ID&$ids_cmd_preparedId=$PREPARED_ID" > $DATASET_ZIP 2>>$std_err`
   #echo ____ Downloaded file ______
   #ls -l     $DATASET_ZIP
   unzip -o  $DATASET_ZIP
done
$icat_api_cli $icat_cmd_Logout $SESSION_ID

#
# - the end -
#
