/**
 * Example to show user authentication with ICAT API to get SID
 * 
 * $Id$
 * 
 */
package uk.icat.examples;
 
import org.icatproject.ICAT;

public class Authenticate extends ExampleBase {

        public static void main(String[] args) throws Exception {
                ICAT icat = getIcat();
                String sid = icat.login(authenticator, credentials);
                String api = icat.getApiVersion();
                System.out.println("authenticator = " + authenticator +
                                   " : Session ID = " + sid +
                                   " : API Version = " + api);
                icat.logout(sid);
        }
}
