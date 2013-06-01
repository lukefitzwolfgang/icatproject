/**
 * Example to execute Delete method within the ICAT4
 * 
 *
 * $Id$
 *  
 */
package uk.icat.examples;

import org.icatproject.ICAT;
import org.icatproject.Datafile;
import java.util.List;

public class DeleteDatafiles extends ExampleBase {

        public static void main(String[] args) throws Exception {

                Datafile d;
                d = new Datafile();
                ICAT icat = getIcat();
                String sid = icat.login(authenticator, credentials);
                String query="Dataset.id";
                query = "";
                for (int i=0; i<args.length; i++) {
                   query += args[i] + " ";
                }
                List<Object> result = icat.search(sid, query);
                for (Object obj : result) {
                    System.out.println(obj);
                    d = (Datafile) obj;
                    icat.delete(sid, d);
                }
                icat.logout(sid);
        }
}
