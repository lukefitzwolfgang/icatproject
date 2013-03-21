import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.xml.namespace.QName;

import org.apache.log4j.Logger;
import org.icatproject.Datafile;
import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.IcatException_Exception;
import org.icatproject.Login.Credentials;
import org.icatproject.Login.Credentials.Entry;

/** A singleton to deal with the flow of messages */
class DelayedAction {

	static private final Logger logger = Logger.getLogger(DelayedAction.class);

	/** Simplest implementation of a singleton */
	public static final DelayedAction INSTANCE = new DelayedAction();

	/** Flag to indicate that the constructor has worked */
	private static boolean ready;

	private ICAT icat;
	private String sessionId;
	private Timer timer = new Timer();

	/** The list of requests - all access to this is synchronised on the list itself */
	private List<Long> requests = new ArrayList<Long>();

	/** Deal with the list of requests */
	private class Look extends TimerTask {

		@Override
		public void run() {
			List<Long> clone;
			synchronized (requests) {
				/* Keep the lock for as short a time as possible */
				clone = new ArrayList<Long>(requests);
				requests.clear();
			}
			/*
			 * Construct ICAT query that will only consider the data file IDs we have been given. If
			 * the query can be made more restrictive this will save time. As an example this code
			 * will only include data files with a name starting with 'w'
			 */
			StringBuilder sb = new StringBuilder();
			for (long id : clone) {
				if (sb.length() == 0) {
					sb.append("Datafile INCLUDE DatafileFormat [name LIKE 'w%' AND id IN (");
				} else {
					sb.append(",");
				}
				sb.append(id);
			}
			if (sb.length() != 0) {
				sb.append(")]");
				logger.info(sb.toString());
				List<Object> os = null;
				try {
					/* Get the data we are allowed to see */
					os = icat.search(sessionId, sb.toString());
				} catch (IcatException_Exception e) {
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					e.printStackTrace(new PrintStream(baos));
					logger.error(baos);
					return;
				}
				for (Object o : os) {
					Datafile df = (Datafile) o;
					logger.info("Datafile " + df.getId() + " with name " + df.getName() + " has "
							+ df.getDatafileFormat().getName() + " format");
				}
			}
		}
	}

	/** Read in the properties and get an ICAT session */
	private DelayedAction() {
		try {
			File f = new File("mdb_example.properties");
			Properties props = new Properties();
			try {
				props.load(new FileInputStream(f));
				logger.info("Property file " + f + " loaded");
			} catch (Exception e) {
				logger.fatal("Problem with " + f.getAbsolutePath() + "  " + e.getMessage());
				return;
			}
			String url = props.getProperty("serverUrl");
			logger.info("Using ICAT service at " + url);
			final URL icatUrl = new URL(url + "/ICATService/ICAT?wsdl");
			final ICATService icatService = new ICATService(icatUrl, new QName(
					"http://icatproject.org", "ICATService"));
			icat = icatService.getICATPort();
			String credentials = props.getProperty("credentials");
			if (credentials == null) {
				logger.fatal("Property 'credentials' is not defined");
				return;
			}
			String[] creds = credentials.trim().split("\\s+");

			sessionId = login(creds);
			double remaining = icat.getRemainingMinutes(sessionId) - 10;
			if (remaining < 0) {
				logger.fatal("Your icat has given an unreasonably short session lifetime");
				return;
			}
			long millis = (long) (remaining * 60 * 1000);

			/* Schedule a timer task to run ten minutes before the session times out */
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					try {
						icat.refresh(sessionId);
					} catch (IcatException_Exception e) {
						/* For real code this probably wants to do more to recover */
						ByteArrayOutputStream baos = new ByteArrayOutputStream();
						e.printStackTrace(new PrintStream(baos));
						logger.fatal(baos);
						ready = false;
					}
				}
			}, millis, millis);
			logger.debug("Logged in");
		} catch (Exception e) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			e.printStackTrace(new PrintStream(baos));
			logger.fatal(baos);
			return;
		}
		ready = true;
	}

	private String login(String[] creds) throws IcatException_Exception {
		Credentials credentials = new Credentials();
		List<Entry> entries = credentials.getEntry();
		int i = 1;
		while (i < creds.length) {
			Entry e = new Entry();
			e.setKey(creds[i]);
			e.setValue(creds[i + 1]);
			entries.add(e);
			i += 2;
		}
		return icat.login(creds[0], credentials);
	}

	public void note(String operation, String entity, long id) {
		if (ready) {
			logger.debug(operation + " " + entity + " " + id);
			synchronized (requests) {
				/* Make sure that requests are dealt with within five seconds */
				if (requests.isEmpty()) {
					timer.schedule(new Look(), 5000L);
				}
				requests.add(id);
			}
		}
	}

}