import java.lang.instrument.*;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

// $Id$

public class premain {
    static {
        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
        new javax.net.ssl.HostnameVerifier () {
            public boolean verify(String hostname, javax.net.ssl.SSLSession sslSession) {
                return true;
            }
        });
    }
    public static void premain(String args, Instrumentation inst) {
        //System.out.println("Ignore Host Certificate Error") ;
    }
}
