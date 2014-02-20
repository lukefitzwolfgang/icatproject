/**
 * Example to show user authentication with ICAT API to get SID
 * 
 * $Id$
 * 
 */
package uk.icat.examples;
 
import org.icatproject.ICAT;
import org.icatproject.Investigation;
import org.icatproject.Dataset;
import org.icatproject.Datafile;
import java.util.List;
import java.util.ArrayList;
import java.net.URL;
import java.io.OutputStream;
import java.io.FileOutputStream;

public class TestIds extends ExampleBase {

        public static void main(String[] args) throws Exception {
                ICAT icat = getIcat();
                String sid = icat.login(authenticator, credentials);
                String api = icat.getApiVersion();
                System.out.println("authenticator = " + authenticator +
                                   " : Session ID = " + sid +
                                   " : API Version = " + api);
            String sessionId=sid;
            ICAT port = icat;
///////////////PANDATA SV7 TESTS//////////////////
            
            //Assumed logged in with sessionId set either as arguments or from properties file
            //Uses System.out.print to fit with simple .sh redirect logging of progress.
            List<Object> investigations = new ArrayList<Object>();
            try
            {
                long searchStartTime = System.currentTimeMillis();
                investigations = port.search(sessionId, "Investigation INCLUDE Dataset [title = 'empty instrument (no candle stick)']");
                long searchElapsedTime = System.currentTimeMillis() - searchStartTime;
                System.out.println("Investigation name search took " + searchElapsedTime + "ms");
                System.out.println("Investigation search returned: " + investigations.size() + " results");
                if(investigations.size() != 1)
                {
                    System.err.println("Did not find exactly one investigation");
                }
            }
            catch(Exception ex)
            {
                System.err.println("Problem searching investigaion");
                ex.printStackTrace();
            }
            
            Investigation inv = (Investigation)investigations.get(0);
            
            List<Object> datafiles = new ArrayList<Object>();
            
            try
            {
                //get the datafiles
                long filesStartTime = System.currentTimeMillis();


                datafiles = port.search(sessionId, "Datafile <-> Dataset [id=" + inv.getDatasets().get(0).getId() + "]");
                long filesElapsedTime = System.currentTimeMillis() - filesStartTime;
                System.out.println("Datafiles get took " + filesElapsedTime + "ms");
                System.out.println("Found " + datafiles.size() + " datafiles");
                //Expect 16 datafiles
                if(datafiles.size() != 16)
                {
                    System.err.println("Found " + datafiles.size() + " datafiles - expected 16");
                }
            }
            catch(Exception ex)
            {
                System.err.println("Problem getting datafiles");
                ex.printStackTrace();
            }
            
            
            //Download the nexus files
            //time the execution
            URL idsurl = new URL(ids);
            org.icatproject.ids.client.IdsClient idsClient = new org.icatproject.ids.client.IdsClient(idsurl);
            try
            {
                long totalDownloadSize = 0;
                long downloadStartTime = System.currentTimeMillis();

                for(Object dfo : datafiles)
                {
                    Datafile df = (Datafile)dfo;

                    //download
                    totalDownloadSize += df.getFileSize();
                    System.out.println("Downloading " + df.getName());

                    org.icatproject.ids.client.DataSelection dataSel = new org.icatproject.ids.client.DataSelection();
                    dataSel.addDatafile(df.getId());
                    java.io.InputStream dataReturned = idsClient.getData(sessionId, dataSel, org.icatproject.ids.client.IdsClient.Flag.NONE, "nexusfile", 0);

                    String base = "./";
		    String path = base + df.getName();
                    OutputStream outfile = new FileOutputStream(path);

                    int read = 0;
                    byte[] bytes = new byte[1024];

                    while ((read = dataReturned.read(bytes)) != -1) {
                        outfile.write(bytes, 0, read);
                    }
                    System.out.println("Downloaded file to " + path);
                }
                long downloadStopTime = System.currentTimeMillis();
                long downloadElapsedTime = downloadStopTime - downloadStartTime;
                System.out.println("Downloaded and saved " + totalDownloadSize + "bytes in " + downloadElapsedTime + "ms");
            }
            catch(Exception ex)
            {
                System.err.println("Problem downloading or saving datafiles");
                ex.printStackTrace();
            }
            
            //Try some operations that should fail

            
            try
            {
                org.icatproject.ids.client.DataSelection dataSel = new org.icatproject.ids.client.DataSelection();
                dataSel.addDatafile(46642894); //a private datafile id
                java.io.InputStream dataReturned = idsClient.getData(sessionId, dataSel, org.icatproject.ids.client.IdsClient.Flag.NONE, "nexusfile", 0);
                System.err.println("Error - was able to access private file");
            }
            catch (Exception ex)
            {
                //Expected
                System.out.println("Success - was not able to access private file");
            }

            /////////////END PANDATA SV7 TESTS////////////////

                icat.logout(sid);
        }
}
