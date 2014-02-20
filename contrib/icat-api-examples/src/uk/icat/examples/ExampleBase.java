/**
 * Base class for examples
 * 
 * $Id$
 */
package uk.icat.examples;

import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.xml.namespace.QName;

import org.icatproject.Login;
import org.icatproject.Application;
import org.icatproject.Datafile;
import org.icatproject.DatafileFormat;
import org.icatproject.DatafileParameter;
import org.icatproject.Dataset;
import org.icatproject.DatasetParameter;
import org.icatproject.DatasetType;
import org.icatproject.EntityBaseBean;
import org.icatproject.Facility;
import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.IcatExceptionType;
import org.icatproject.IcatException_Exception;
import org.icatproject.Investigation;
import org.icatproject.InvestigationType;
import org.icatproject.Job;
import org.icatproject.Login;
import org.icatproject.Login.Credentials;
import org.icatproject.Login.Credentials.Entry;
import org.icatproject.ParameterType;
import org.icatproject.ParameterValueType;
import org.icatproject.Rule;
import org.icatproject.User;
import org.icatproject.UserGroup;

public class ExampleBase {

        private static final String IDS_LOCATION_PROPERTY_NAME = "ids.location";
        private static final String WSDL_LOCATION_PROPERTY_NAME = "wsdl.location";
        private static final String USERNAME_PROPERTY_NAME = "username";
        private static final String EXAMPLE_PROPERTIES_FILENAME = "example.properties";
        private static final String PASSWORD_PROPERTY_NAME = "password";
        private static final String AUTHENTICATOR_PROPERTY_NAME = "authenticator";

        protected static String username;
        private static String wsdlLocation;
        protected static String password;
        protected static String authenticator;
        protected static String ids;
        protected static Login.Credentials credentials; 

        public static ICAT getIcat() throws Exception {
                readConfigurationFile();
                URL icatServiceWsdlLocation = getServiceWsdlLocation();
                ICATService service = new ICATService(icatServiceWsdlLocation, new QName("http://icatproject.org", "ICATService"));
                ICAT icat = service.getICATPort();

                credentials = new Login.Credentials(); 
                List<Login.Credentials.Entry> entries = credentials.getEntry(); 
                Entry e;

                e = new Entry(); 
                e.setKey(USERNAME_PROPERTY_NAME); 
                e.setValue(username); 
                entries.add(e); 
                e = new Entry();  
                e.setKey(PASSWORD_PROPERTY_NAME); 
                e.setValue(password); 
                entries.add(e); 

                return service.getICATPort();
        }

        private static void readConfigurationFile() throws Exception {
                Properties properties = new Properties();
                properties.load(new FileInputStream(EXAMPLE_PROPERTIES_FILENAME));
                username = (String) properties.get(USERNAME_PROPERTY_NAME);
                password = (String) properties.get(PASSWORD_PROPERTY_NAME);
                wsdlLocation = (String) properties.get(WSDL_LOCATION_PROPERTY_NAME);
                authenticator = (String) properties.get(AUTHENTICATOR_PROPERTY_NAME);
                ids = (String) properties.get(IDS_LOCATION_PROPERTY_NAME);
        }

        private static URL getServiceWsdlLocation() throws MalformedURLException {
                URL baseUrl = org.icatproject.ICATService.class.getResource(".");
                return new URL(baseUrl, wsdlLocation);
        }

}
