package org.icatproject.editor.server;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.namespace.QName;

import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.IcatExceptionType;
import org.icatproject.IcatException_Exception;
import org.icatproject.Login.Credentials;
import org.icatproject.editor.client.IcatGwtService;
import org.icatproject.editor.shared.CredType;
import org.icatproject.editor.shared.CredType.Type;
import org.icatproject.editor.shared.CredType.Visibility;
import org.icatproject.editor.shared.EditorException;
import org.icatproject.editor.shared.LoginResult;
import org.icatproject.editor.shared.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.rl.esc.catutils.CheckedProperties;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class IcatGwtServiceImpl extends RemoteServiceServlet implements IcatGwtService {

	private DatatypeFactory datatypeFactory;

	private DateFormat dfout;

	private static final String formatString = "yyyy-MM-dd' 'HH:mm:ss";

	private static final Logger logger = LoggerFactory.getLogger(IcatGwtServiceImpl.class);

	private Map<String, IceEntityInfo> eiMap = new HashMap<String, IceEntityInfo>();

	private ICAT icatEP;

	private List<String> beanNames;

	private Map<String, List<CredType>> credentialList = new LinkedHashMap<String, List<CredType>>();

	@Override
	public void init(ServletConfig conf) throws ServletException {
		super.init(conf);

		try {
			CheckedProperties props = new CheckedProperties();
			props.loadFromFile("ice.properties");

			URL icatUrl = new URL(props.getURL("icat.url") + "/ICATService/ICAT?wsdl");

			ICATService icatService = new ICATService(icatUrl, new QName("http://icatproject.org",
					"ICATService"));
			icatEP = icatService.getICATPort();

			datatypeFactory = DatatypeFactory.newInstance();
			dfout = new SimpleDateFormat(formatString);
			dfout.setLenient(false);

			beanNames = new ArrayList<String>(Arrays.asList(props.getString("bean.list").split(
					"\\s+")));
			for (String beanName : beanNames) {
				/* This will detect any invalid names */
				icatEP.getEntityInfo(beanName);
			}

			ArrayList<String> authnNames = new ArrayList<String>(Arrays.asList(props.getString(
					"authn.list").split("\\s+")));
			for (String authnName : authnNames) {
				List<CredType> credTypes = new ArrayList<CredType>();
				credentialList.put(authnName, credTypes);
				ArrayList<String> authnOneList = new ArrayList<String>(Arrays.asList(props
						.getString("authn." + authnName + ".list").split("\\s+")));
				for (String authnOne : authnOneList) {
					if (!authnOne.isEmpty()) {
						Visibility visibility = Visibility.EXPOSED;
						String key = "authn." + authnName + "." + authnOne + ".visible";
						if (props.has(key)) {
							if (props.getString(key).equals("false")) {
								visibility = Visibility.HIDDEN;
							}
						}
						CredType credType = new CredType(authnOne, visibility, Type.STRING);
						credTypes.add(credType);
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getClass() + " " + e.getMessage());
			throw new ServletException(e.getClass() + " " + e.getMessage());
		}

		logger.info("Init of IcatGwtServiceImpl succesful");
	}

	



	
	@Override
	public LoginResult login(String plugin, Map<String, String> map) throws EditorException,
			SessionException {
		logger.debug("Plugin is " + plugin);
		try {
			Credentials credentials = new Credentials();
			for (Entry<String, String> e : map.entrySet()) {
				Credentials.Entry cred = new Credentials.Entry();
				cred.setKey(e.getKey());
				cred.setValue(e.getValue());
				credentials.getEntry().add(cred);
			}
			String sessionId = icatEP.login(plugin, credentials);
			String userName = icatEP.getUserName(sessionId);
			logger.debug("Logged in as " + userName);
			return new LoginResult(sessionId, userName);
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() == IcatExceptionType.SESSION) {
				throw new SessionException(e.getMessage());
			} else {
				throw new EditorException("IcatInternalException " + e.getMessage());
			}
		}
	}

	@Override
	public void logout(String sessionId) {
		try {
			icatEP.logout(sessionId);
		} catch (IcatException_Exception e) {
			// Ignore it
		}
		logger.debug("User with sessionId " + sessionId + " logged out");
	}



	







	@Override
	public Map<String, List<CredType>> getCredentialList() {
		return credentialList;
	}

}