/**
 * Example to show user authentication with ICAT API to get SID
 * 
 * $Id$
 * 
 */
package uk.icat.examples;
 
import org.icatproject.ICAT;

public class Logout extends ExampleBase {

        public static void main(String[] args) throws Exception {
                ICAT icat = getIcat();
                String sid=args[0];
                //System.out.println(sid);
                icat.logout(sid);
        }
}
