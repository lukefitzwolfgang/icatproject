/**
 * Example to show user authentication with ICAT API to get SID
 * 
 * $Id$
 * 
 */
package uk.icat.examples;
 
import org.icatproject.ICAT;

public class Login extends ExampleBase {

        public static void main(String[] args) throws Exception {
                ICAT icat = getIcat();
                String sid = icat.login(authenticator, credentials);
                System.out.println(sid);
        }
}
