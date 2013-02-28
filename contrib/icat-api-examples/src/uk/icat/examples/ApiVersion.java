/**
 * Example to show getApiVersion without a login to ICAT API
 * 
 * $Id$
 * 
 */
package uk.icat.examples;
 
import org.icatproject.ICAT;

public class ApiVersion extends ExampleBase {

        public static void main(String[] args) throws Exception {
                ICAT icat = getIcat();
                String api = icat.getApiVersion();
                System.out.println("API Version = " + api);
        }
}
