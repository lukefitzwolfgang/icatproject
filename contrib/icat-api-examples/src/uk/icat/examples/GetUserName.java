/**
 * Example to show getUserName with ICAT API
 * 
 * $Id$
 * 
 */
package uk.icat.examples;
 
import org.icatproject.ICAT;

public class GetUserName extends ExampleBase {

        public static void main(String[] args) throws Exception {
                ICAT icat = getIcat();
                String sid = icat.login(authenticator, credentials);
                String username = icat.getUserName(sid);
                System.out.println("Username = " + username);
                icat.logout(sid);
        }
}
