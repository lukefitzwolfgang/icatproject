/**
 * Example to execute getRemainingMinutes method within the ICAT4
 * 
 * $Id$
 *  
 */
package uk.icat.examples;

import org.icatproject.ICAT;
import java.util.List;

public class RemainingMinutes extends ExampleBase {

        public static void main(String[] args) throws Exception {

                ICAT icat = getIcat();
                String sid;
                if (args.length == 0) {
                    sid = icat.login(authenticator, credentials);
                    System.out.println(icat.getRemainingMinutes(sid));
                    icat.logout(sid);
                } else {
                    sid = args[0];
                    System.out.println(icat.getRemainingMinutes(sid));
                }
        }
}
