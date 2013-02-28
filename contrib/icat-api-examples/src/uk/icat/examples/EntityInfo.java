/**
 * Example to execute getEntityInfo method within the ICAT4
 * 
 * $Id$
 *  
 */
package uk.icat.examples;

import org.icatproject.ICAT;
import org.icatproject.GetEntityInfo;
import java.util.List;

public class EntityInfo extends ExampleBase {

        public static void main(String[] args) throws Exception {
            ICAT icat = getIcat();
            for (int i=0; i<args.length; i++) {
                Object obj = icat.getEntityInfo(args[i]); 
                System.out.println(args[i] + " : " +obj); 
            }
        }
}
