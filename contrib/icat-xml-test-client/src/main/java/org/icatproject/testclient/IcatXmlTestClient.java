package org.icatproject.testclient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.icatproject.EntityBaseBean;
import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.Login.Credentials;
import org.icatproject.Login.Credentials.Entry;
import org.icatproject.testclient.IcatWriter.DataTypeID;
import org.icatproject.testclient.Icatdata.Config;
import org.icatproject.testclient.Icatdata.Config.SearchContainer;

/**
 * A simple client which can import data from an xml file into icat, remove data entities in icat and search for data in
 * icat.
 */
public class IcatXmlTestClient {

	private static final String ICAT_SERVICE_WSDL = "/ICATService/ICAT?wsdl";

	private ICAT icat;

	private String sessionId;

	/**
	 * Login to icat.
	 * 
	 * @param username
	 * @param password
	 * @param authMethod
	 * @throws Exception
	 */
	private final void login(final String username, final String password, final String authMethod) throws Exception {
		Credentials credentials = new Credentials();
		List<Entry> entries = credentials.getEntry();
		Entry e = new Entry();
		e.setKey("username");
		e.setValue(username);
		entries.add(e);
		e = new Entry();
		e.setKey("password");
		e.setValue(password);
		entries.add(e);
		this.sessionId = icat.login(authMethod, credentials);
	}

	/**
	 * Logout from icat.
	 * 
	 * @throws Exception
	 */
	private final void logout() throws Exception {
		icat.logout(sessionId);
		this.sessionId = null;
	}

	/**
	 * Removes all data from ICAT for the datatypes (tables) configured in the input file.
	 * 
	 * @param config
	 * @throws Exception
	 */
	private final void clean(final Config config) throws Exception {
		if (config.getTables2clean() == null || config.getTables2clean().isEmpty())
			return;
		for (String table : config.getTables2clean()) {
			cleanData(table, config.getHaltOnError());
		}
		System.out.println("Cleaned all data!");
	}

	/**
	 * Removes all data for one datatype (table) in ICAT.
	 * 
	 * @param datatype
	 * @param haltOnError
	 * @throws Exception
	 */
	private final void cleanData(final String datatype, final boolean haltOnError) throws Exception {
		try {
			List<Object> results = icat.search(sessionId, datatype);
			if (results == null || results.size() == 0)
				return;
			for (Object object : results) {
				try {
					icat.delete(sessionId, (EntityBaseBean) object);
				} catch (Exception e) {
					if (haltOnError)
						throw e;
					System.err.println(e);
					e.printStackTrace(System.err);
					System.err.println("Continuing cleaning...");
				}
			}
			System.out.println("Cleaned all data for data type: " + datatype);
		} catch (Exception e) {
			if (haltOnError)
				throw e;
			System.err.println(e);
			e.printStackTrace(System.err);
			System.err.println("Continuing cleaning...");
		}
	}

	/**
	 * Search for data using the ICAT query language.
	 * 
	 * @param query
	 * @param outFile
	 * @throws Exception
	 */
	private final void search(final String query, final String outFile) throws Exception {
		List<Object> results = icat.search(sessionId, query);
		if (results == null || results.size() == 0) {
			System.out.println("No result for query: " + query);
		} else {
			Icatdata data = new Icatdata();
			for (Object object : results) {
				data.getBeans().add((EntityBaseBean) object);
			}
			JAXBContext jc = JAXBContext.newInstance(Icatdata.class);
			Marshaller m = jc.createMarshaller();
			OutputStream os = System.out;
			if (outFile != null) {
				os = new FileOutputStream(outFile);
			}
			System.out.println("Found object(s)!");
			data.setConfig(null);
			m.marshal(data, os);
		}
	}

	private final Map<DataTypeID, EntityBaseBean> searchIDs(final Config config) throws Exception {
		if (config.getSearchids() == null || config.getSearchids().isEmpty())
			return null;
		Map<DataTypeID, EntityBaseBean> objMap = new HashMap<DataTypeID, EntityBaseBean>();
		for (SearchContainer search : config.getSearchids()) {
			try {
				List<Object> results = icat.search(sessionId, search.getQuery());
				if (results != null && results.size() > 1)
					throw new ArrayIndexOutOfBoundsException("Found more than one entity bae bean for query="
							+ search.getQuery());
				objMap.put(new DataTypeID(results.get(0).getClass().getSimpleName(), search.getId()),
						(EntityBaseBean) results.get(0));
			} catch (Exception e) {
				if (config.getHaltOnError())
					throw e;
				System.err.println(e);
				e.printStackTrace(System.err);
				System.err.println("Continuing searching for IDs ...");
			}
		}
		System.out.println("Finished searching.");
		return objMap;
	}

	/**
	 * Create the ICAT web service client.
	 * 
	 * @param url
	 * @throws Exception
	 */
	private final void createService(final String url) throws Exception {
		URL icatUrl = new URL(url + ICAT_SERVICE_WSDL);
		ICATService service = new ICATService(icatUrl);
		this.icat = service.getICATPort();
		System.out.println("ICAT Version: " + icat.getApiVersion());
	}

	/**
	 * Import data from xml file into icat.
	 * 
	 * @param fileName
	 * @throws Exception
	 */
	private void read(final String fileName) throws Exception {
		JAXBContext jc = JAXBContext.newInstance(Icatdata.class);
		Unmarshaller um = jc.createUnmarshaller();
		Icatdata data = (Icatdata) um.unmarshal(new File(fileName));
		this.clean(data.getConfig());
		Map<DataTypeID, EntityBaseBean> objMap = this.searchIDs(data.getConfig());
		IcatWriter w = new IcatWriter(data, objMap);
		w.write(this.icat, this.sessionId);
		System.out.println("Data import done!");
	}

	/**
	 * Exceute the user's command (import or search).
	 * 
	 * @param cmd
	 * @param param1
	 * @param param2
	 * @param url
	 * @param username
	 * @param password
	 * @param authMethod
	 * @throws Exception
	 */
	private void run(final String cmd, final String param1, final String param2, final String url,
			final String username, final String password, final String authMethod) throws Exception {
		this.createService(url);
		this.login(username, password, authMethod);
		if (cmd.equalsIgnoreCase("search")) {
			this.search(param1, param2);
		} else if (cmd.equalsIgnoreCase("import")) {
			this.read(param1);
		} else {
			System.err.println("ERROR: command " + cmd + " unknown!");
		}
		this.logout();
	}

	public static void main(String[] args) {
		try {
			IcatXmlTestClient client = new IcatXmlTestClient();
			if (args != null && args.length == 6) {
				client.run(args[0], args[1], null, args[2], args[3], args[4], args[5]);
			} else if (args != null && args.length == 7) {
				client.run(args[0], args[1], args[2], args[3], args[4], args[5], args[6]);
			} else {
				System.out
						.println("Usage: IcatXmlTestClient command param1 [param2] url username password authMethod\n"
								+ "where command can be import or search and the param1 is either the xml file containing icat data (for import) or the search string (for search). And optional param2 is the output file for the search command.\n"
								+ "url, username, password and authMethod (db or ldap) are needed to connect to icat.");
			}
		} catch (Exception e) {
			System.err.println(e);
			e.printStackTrace(System.err);
		}
	}

}
