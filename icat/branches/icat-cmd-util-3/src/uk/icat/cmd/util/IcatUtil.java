// $Id: IcatUtil.java 934 2011-08-09 13:16:35Z nab24562@FED.CCLRC.AC.UK $
package uk.icat.cmd.util;

import java.net.URL;

import javax.xml.namespace.QName;

import uk.icat.cmd.entity.Configuration;
import uk.icat3.client.ICAT;
import uk.icat3.client.ICATService;

public class IcatUtil {

	private ICAT serviceInstance;
	private String sid;

	public IcatUtil(Configuration config) throws Exception {
		try {
			URL baseUrl = uk.icat3.client.ICATService.class.getResource(".");
			URL serviceWsdlLocation = new URL(baseUrl, config.getUrl());

			serviceInstance = new ICATService(serviceWsdlLocation, new QName("client.icat3.uk", "ICATService")).getICATPort();
			sid = serviceInstance.login(config.getUser(), config.getPassword());
		} catch (Exception e) {
			System.err.println("Error during SID negotiation: " + e.getMessage());
			System.exit(1);
		}
	}

	public String getSid() {
		return sid;
	}

	public Object getService() {
		return serviceInstance;
	}

}
