import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;

import org.icatproject.Datafile;
import org.icatproject.Dataset;
import org.icatproject.DatasetParameter;
import org.icatproject.ICAT;
import org.icatproject.ICATService;
import org.icatproject.IcatException_Exception;
import org.icatproject.Investigation;
import org.icatproject.Login.Credentials;
import org.icatproject.Login.Credentials.Entry;

public class Top {

	private static final String tsb = "{ts ";
	private static final String tse = "}";

	public static void main(String[] args) throws MalformedURLException, IcatException_Exception {
		if (args.length != 4) {
			System.err.println("Must have four args: hosturl, \"db\"|\"ldap\", username, password");
			System.exit(1);
		}
		String host = args[0];
		String plugin = args[1];
		String userName = args[2];
		String password = args[3];
		URL icatUrl = null;
		icatUrl = new URL(host + "/ICATService/ICAT?wsdl");
		QName qName = new QName("http://icatproject.org", "ICATService");
		ICATService service = new ICATService(icatUrl, qName);
		ICAT icat = service.getICATPort();

		Credentials credentials = new Credentials();
		List<Entry> entries = credentials.getEntry();
		Entry e;

		e = new Entry();
		e.setKey("username");
		e.setValue(userName);
		entries.add(e);

		e = new Entry();
		e.setKey("password");
		e.setValue(password);
		entries.add(e);

		String sessionId = icat.login(plugin, credentials);
		System.out.println("Session id: " + sessionId);

		// First 10 GS datasets ordered by name
		{
			// JPQL
			List<?> ids = icat
					.search(sessionId,
							"SELECT ds.name FROM Dataset ds WHERE ds.type.name = 'GS' ORDER BY ds.name LIMIT 0, 10");
			System.out.print("\nFirst 10 dataset names order by name");
			for (Object id : ids) {
				System.out.print(" " + id);
			}
			System.out.println();
		}

		{
			// Concise
			List<?> ids = icat.search(sessionId,
					",10 Dataset.name ORDER BY name [type.name = 'GS']");
			System.out.print("First 10 dataset names order by name");
			for (Object id : ids) {
				System.out.print(" " + id);
			}
			System.out.println();
		}

		// As before but skip the first 8 and then take the next two
		String ds1, ds2;

		{
			// JPQL
			List<?> ids = icat
					.search(sessionId,
							"SELECT ds.name FROM Dataset ds WHERE ds.type.name = 'GS' ORDER BY ds.name LIMIT 8, 2");
			System.out.print("\n9th and 10th dataset names order by name");
			for (Object id : ids) {
				System.out.print(" " + id);
			}
			System.out.println();
			if (ids.size() >= 2) {
				ds1 = (String) ids.get(0);
				ds2 = (String) ids.get(1);
			} else {
				ds1 = "";
				ds2 = "";
			}
		}

		{
			// Concise
			List<?> ids = icat.search(sessionId,
					"8,2 Dataset.name ORDER BY name [type.name = 'GS']");
			System.out.print("9th and 10th dataset names order by name");
			for (Object id : ids) {
				System.out.print(" " + id);
			}
			System.out.println();
			if (ids.size() >= 2) {
				ds1 = (String) ids.get(0);
				ds2 = (String) ids.get(1);
			} else {
				ds1 = "";
				ds2 = "";
			}
		}

		// Parameters and files of datasets - note that we only print one
		// parameter and one file for each dataset - see break statements
		{ // JPQL
			String query = "SELECT ds FROM Dataset ds WHERE ds.name BETWEEN ':ds1' AND ':ds2' AND ds.type.name = 'GS' ORDER BY ds.name INCLUDE ds.parameters.type, ds.datafiles";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out.println("\nParameters and files of datasets with names between " + ds1
					+ " and " + ds2);
			System.out.println("Query is " + query);
			List<?> dss = icat.search(sessionId, query);
			for (Object o : dss) {
				Dataset ds = (Dataset) o;
				System.out.println(ds.getName() + " " + ds.getCreateTime() + " "
						+ ds.getParameters().size() + " " + ds.getDatafiles().size());
				for (DatasetParameter dp : ds.getParameters()) {
					System.out
							.println("   " + dp.getType().getName() + ": " + dp.getNumericValue());
					break;
				}
				for (Datafile dp : ds.getDatafiles()) {
					final StringBuilder sb = new StringBuilder(host + "/ids/Data/" + "getThumbnail");
					sb.append("?sessionid=" + sessionId);
					sb.append("&datafileid=" + dp.getId());
					System.out.println("   " + dp.getName() + " at " + sb);
					break;
				}
			}
		}

		{ // Concise
			String query = "Dataset ORDER BY name INCLUDE DatasetParameter, Datafile, ParameterType  [name >= ':ds1' AND name <= ':ds2' AND type.name = 'GS']";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out.println("Parameters and files of datasets with names between " + ds1
					+ " and " + ds2);
			System.out.println("Query is " + query);
			List<?> dss = icat.search(sessionId, query);
			for (Object o : dss) {
				Dataset ds = (Dataset) o;
				System.out.println(ds.getName() + " " + ds.getCreateTime() + " "
						+ ds.getParameters().size() + " " + ds.getDatafiles().size());
				for (DatasetParameter dp : ds.getParameters()) {
					System.out
							.println("   " + dp.getType().getName() + ": " + dp.getNumericValue());
					break;
				}
				for (Datafile dp : ds.getDatafiles()) {
					final StringBuilder sb = new StringBuilder(host + "/ids/Data/" + "getThumbnail");
					sb.append("?sessionid=" + sessionId);
					sb.append("&datafileid=" + dp.getId());
					System.out.println("   " + dp.getName() + " at " + sb);
					break;
				}
			}
		}

		// Specific dataset parameters only - selected by name
		{ // JPQL
			String query = "SELECT dp FROM DatasetParameter dp, dp.dataset ds WHERE (dp.type.name = 'SAD_SPEC_B_FWHM' OR dp.type.name = 'GEM_SHOT_NUM_VALUE') AND  ds.name BETWEEN ':ds1' AND ':ds2' AND ds.type.name = 'GS' INCLUDE dp.dataset.type , dp.type";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out.println("\nSpecific dataset parameters only - selected by name");
			System.out.println("Query is " + query);

			List<?> dsps = icat.search(sessionId, query);
			for (Object o : dsps) {
				DatasetParameter dsp = (DatasetParameter) o;
				System.out.println(dsp.getType().getName() + " " + dsp.getDataset().getId() + ": "
						+ dsp.getNumericValue());
			}
		}
		{ // Concise
			String query = "DatasetParameter  INCLUDE Dataset, DatasetType, ParameterType [type.name = 'SAD_SPEC_B_FWHM' OR type.name = 'GEM_SHOT_NUM_VALUE'] <-> Dataset [name >= ':ds1' AND name <= ':ds2' AND type.name = 'GS']";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out.println("Specific dataset parameters only - selected by name");
			System.out.println("Query is " + query);

			List<?> dsps = icat.search(sessionId, query);
			for (Object o : dsps) {
				DatasetParameter dsp = (DatasetParameter) o;
				System.out.println(dsp.getType().getName() + " " + dsp.getDataset().getId() + ": "
						+ dsp.getNumericValue());
			}
		}

		// Datasets selected by presence of a parameter with a specific
		// value or a file being present. For the concise query this requires multiple queries -
		// get the two sets and combine them.

		{ // JPQL

			String query = "SELECT ds.id FROM Dataset ds, ds.parameters dp, ds.datafiles df WHERE ds.name BETWEEN ':ds1' AND ':ds2' AND ds.type.name = 'GS' AND ((dp.type.name = 'N_FROG_AUTOWIDTH_VALUE' AND dp.numericValue >= 8) OR df.name = 'LASER_BAY_SCINTILLATOR_TRACE') ";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out
					.println("\nDatasets selected by presence of a parameter with a specific value or a file being present");
			System.out.println("Query is " + query);

			List<Object> dss = icat.search(sessionId, query);

			System.out.println("dataset ids are: " + dss);
		}

		Set<Object> dssab;
		{ // Concise

			String query = "Dataset.id [name >= ':ds1' AND name <= ':ds2' AND type.name = 'GS'] <-> DatasetParameter [(type.name = 'N_FROG_AUTOWIDTH_VALUE' AND numericValue >= 8)]";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out
					.println("\nDatasets selected by presence of a parameter with a specific value or a file being present");
			System.out.println("Query is " + query);

			List<Object> dssa = icat.search(sessionId, query);

			query = "Dataset.id [name >= ':ds1' AND name <= ':ds2' AND type.name = 'GS'] <-> Datafile [name = 'LASER_BAY_SCINTILLATOR_TRACE']";
			query = query.replace(":ds1", ds1).replace(":ds2", ds2);
			System.out.println("Query is " + query);

			List<Object> dssb = icat.search(sessionId, query);

			dssab = new HashSet<Object>(dssa);
			dssab.addAll(dssb);

			System.out.println("dataset ids are: " + dssab);
		}

		// Go from a list of dataset ids and access parameters and
		// investigations. This time we have all parameters of each dataset
		// and need to select by Java code. This is rather inefficient the
		// investigations information should be cached rather than looking
		// it up every time.		
		{ // JPQL
			System.out.println("\nGo from a list of dataset ids " + dssab
					+ "  and access parameters and investigations.");
			for (Object o : dssab) {
				Long dsid = (Long) o;
				Dataset ds = (Dataset) icat.get(sessionId,
						"Dataset ds INCLUDE ds.parameters.type, ds.datafiles, ds.investigation",
						dsid);
				Investigation inv = (Investigation) icat.get(sessionId, "Investigation", ds
						.getInvestigation().getId());
				System.out.println(ds.getName() + " with inv " + inv.getName() + " and "
						+ +ds.getParameters().size() + " parameters and "
						+ ds.getDatafiles().size() + " files");
				for (DatasetParameter dp : ds.getParameters()) {
					String name = dp.getType().getName();
					if (Arrays.asList("N_FROG_AUTOWIDTH_VALUE", "S_PUMP_TIMING_XPOS")
							.contains(name)) {
						System.out.println("   " + name + ": " + dp.getNumericValue());
					}
				}
			}

		}
		{ // Concise
			System.out.println("\nGo from a list of dataset ids " + dssab
					+ "  and access parameters and investigations.");
			for (Object o : dssab) {
				Long dsid = (Long) o;
				Dataset ds = (Dataset) icat.get(sessionId,
						"Dataset INCLUDE DatasetParameter, Datafile, Investigation, ParameterType",
						dsid);
				Investigation inv = (Investigation) icat.get(sessionId, "Investigation", ds
						.getInvestigation().getId());
				System.out.println(ds.getName() + " with inv " + inv.getName() + " and "
						+ +ds.getParameters().size() + " parameters and "
						+ ds.getDatafiles().size() + " files");
				for (DatasetParameter dp : ds.getParameters()) {
					String name = dp.getType().getName();
					if (Arrays.asList("N_FROG_AUTOWIDTH_VALUE", "S_PUMP_TIMING_XPOS")
							.contains(name)) {
						System.out.println("   " + name + ": " + dp.getNumericValue());
					}
				}
			}

		}

		{
			// Selection by time

			String query = ",1000 Dataset  ORDER BY startDate [type.name = 'GD' AND startDate BETWEEN :lower AND :upper]";
			query = query.replace(":lower", tsb + "2011-12-15 00:00:00" + tse).replace(":upper",
					tsb + "2012-08-01 00:00:00" + tse);

			System.out.println("\nLook for daily data between dates");
			System.out.println("Query is " + query);

			List<Object> dss = icat.search(sessionId, query);

			System.out.println(dss.size() + " are returned");
			if (dss.size() > 0) {
				System.out.println("1st has startdate of " + ((Dataset) dss.get(0)).getStartDate());
			}
		}

		{
			// Use of functions
			System.out.println("\nCount of datasets is "
					+ icat.search(sessionId, "COUNT(Dataset)").get(0));
		}

		// Datasets without a specific file
		{ 
			String query = ",1000 Dataset.id  [type.name = 'GS' AND startDate BETWEEN :lower AND :upper]";
			query = query.replace(":lower", tsb + "2011-06-15 00:00:00" + tse).replace(":upper",
					tsb + "2012-09-01 00:00:00" + tse);
			List<Object> dssa = icat.search(sessionId, query);

			query = ",1000 Dataset.id  [type.name = 'GS' AND startDate BETWEEN :lower AND :upper] <-> Datafile [name = 'N_PUMP_TIMING_TRACE']";
			query = query.replace(":lower", tsb + "2011-06-15 00:00:00" + tse).replace(":upper",
					tsb + "2012-09-01 00:00:00" + tse);

			List<Object> dssb = icat.search(sessionId, query);
			dssab = new HashSet<Object>(dssa);
			dssab.removeAll(dssb);
			System.out.println("dataset ids size a: " + dssa.size());
			System.out.println("dataset ids size b: " + dssb.size());
			System.out.println("dataset ids size a-b: " + dssab.size());
		}

		{
			// Average value of a datasetparameter
			String query = "AVG(DatasetParameter.numericValue) [type.name = 'GEM_SHOT_NUM_VALUE']";
			List<Object> dss = icat.search(sessionId, query);
			if (dss.size() > 0) {
				System.out.println("\nAvg is " + dss.get(0));
			}
		}

		{
			// Average value of a datasetparameter for a range of shot dates
			String query = "AVG(DatasetParameter.numericValue) [type.name = 'GEM_SHOT_NUM_VALUE'] <-> Dataset [startDate BETWEEN :lower AND :upper]";
			query = query.replace(":lower", tsb + "2011-06-15 00:00:00" + tse).replace(":upper",
					tsb + "2012-04-01 00:00:00" + tse);
			List<Object> dss = icat.search(sessionId, query);
			if (dss.size() > 0) {
				System.out.println("\nAvg is " + dss.get(0));
			}
		}

	}
}
