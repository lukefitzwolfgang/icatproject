import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.icatproject.Application;
import org.icatproject.Constraint;
import org.icatproject.DataCollection;
import org.icatproject.Datafile;
import org.icatproject.DatafileFormat;
import org.icatproject.Dataset;
import org.icatproject.DatasetParameter;
import org.icatproject.DatasetType;
import org.icatproject.EntityBaseBean;
import org.icatproject.EntityField;
import org.icatproject.EntityInfo;
import org.icatproject.Facility;
import org.icatproject.Grouping;
import org.icatproject.IcatException;
import org.icatproject.IcatExceptionType;
import org.icatproject.IcatException_Exception;
import org.icatproject.Investigation;
import org.icatproject.InvestigationParameter;
import org.icatproject.InvestigationType;
import org.icatproject.InvestigationUser;
import org.icatproject.Job;
import org.icatproject.ParameterType;
import org.icatproject.ParameterValueType;
import org.icatproject.PermissibleStringValue;
import org.icatproject.PublicStep;
import org.icatproject.RelType;
import org.icatproject.Rule;
import org.icatproject.Sample;
import org.icatproject.SampleParameter;
import org.icatproject.User;
import org.icatproject.UserGroup;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * These tests are for those aspects that cannot be tested by the core tests. In particular does the
 * INCLUDE mechanism work properly.
 */
public class TestWS {

	private static Random random;
	private static Session session;

	// @AfterClass
	public static void afterClass() throws Exception {
		session.clear();
		session.clearAuthz();
	}

	@BeforeClass
	public static void beforeClass() throws Exception {
		try {
			random = new Random();
			session = new Session();
			session.setAuthz();
			session.clearAuthz();
			session.setAuthz();
			session.clear();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	private static void create() throws Exception {

		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		DatasetType dst = session.createDatasetType(facility, "GQ");

		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		ParameterType p = new ParameterType();
		p.setName("TIMESTAMP");
		p.setUnits("TIMESTAMP");
		p.setDescription("F is not a wibble");
		p.setApplicableToDataset(true);
		p.setValueType(ParameterValueType.DATE_AND_TIME);
		p.setFacility(facility);
		p.setId((Long) session.create(p));

		Dataset wibble = session.createDataset("Wibble", dst, inv);

		DatafileFormat dft1 = session.createDatafileFormat(facility, "png", "binary");
		DatafileFormat dft2 = session.createDatafileFormat(facility, "bmp", "binary");

		session.createDatafile("wib1", dft1, wibble);
		session.createDatafile("wib2", dft2, wibble);

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
		session.createDatasetParameter(date, p, wibble);

		Dataset wobble = session.createDataset("Wobble", dst, inv);
		session.createDatafile("wob1", dft1, wobble);

		Dataset dfsin = session.createDataset("dfsin", dst, inv);
		Datafile fred = session.createDatafile("fred", dft1, dfsin);
		Datafile bill = session.createDatafile("bill", dft1, dfsin);

		Dataset dfsout = session.createDataset("dfsout", dst, inv);
		Datafile mog = session.createDatafile("mog", dft1, dfsout);

		Application application = session.createApplication(facility, "The one", "1.0");

		DataCollection input = session.createDataCollection(wibble, fred, bill);
		DataCollection output = session.createDataCollection(wobble, mog);

		session.createJob(application, input, output);
	}

	private Datafile addDatafile(Dataset dataset, String name, DatafileFormat format) {
		Datafile datafile = new Datafile();
		datafile.setDatafileFormat(format);
		datafile.setName(name);
		dataset.getDatafiles().add(datafile);
		return datafile;
	}

	private Dataset addDataset(Investigation inv, String name, DatasetType type) {
		Dataset dataset = new Dataset();
		dataset.setName(name);
		dataset.setType(type);
		inv.getDatasets().add(dataset);
		return dataset;
	}

	private DatasetParameter addDatasetParameter(Dataset dataset, Object o, ParameterType p) {
		DatasetParameter dsp = new DatasetParameter();
		if (p.getValueType() == ParameterValueType.DATE_AND_TIME) {
			dsp.setDateTimeValue((XMLGregorianCalendar) o);
		}
		dsp.setType(p);
		dataset.getParameters().add(dsp);
		return dsp;
	}

	private Sample addSample(Investigation inv, String sampleName) {
		Sample sample = new Sample();
		sample.setName(sampleName);
		inv.getSamples().add(sample);
		return sample;
	}

	private SampleParameter addSampleParameter(Sample sample, Object o, ParameterType p) {
		SampleParameter sp = new SampleParameter();
		if (p.getValueType() == ParameterValueType.DATE_AND_TIME) {
			sp.setDateTimeValue((XMLGregorianCalendar) o);
		}
		sp.setType(p);
		sample.getParameters().add(sp);
		return sp;
	}

	@Test
	public void publicTable() throws Exception {
		session.addRule(null, "DatafileFormat", "R");
		session.addRule(null, "DatasetType", "R");
		session.delRule(null, "DatafileFormat", "R");
		session.delRule(null, "DatasetType", "R");
	}

	@Test
	public void publicStep() throws Exception {
		PublicStep ps = new PublicStep();
		ps.setOrigin("Application");
		ps.setField("jobs");
		session.create(ps);

		ps = new PublicStep();
		ps.setOrigin("Applicatio");
		ps.setField("jobs");
		try {
			session.create(ps);
			fail("Should have thrown exception");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
			assertEquals("Applicatio is not an EntityBaseBean", e.getMessage());
		}

		ps = new PublicStep();
		ps.setOrigin("Application");
		ps.setField("Jobs");
		try {
			session.create(ps);
			fail("Should have thrown exception");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
			assertEquals("Field value Jobs does not implement a relationship from Application",
					e.getMessage());
		}
	}

	@Test
	public void entities() throws Exception {
		List<String> entities = session.getIcat().getEntityNames();
		assertEquals(38, entities.size());
		assertTrue(entities.contains("Application"));
	}

	@Test
	public void authz() throws Exception {
		session.clear();

		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		DatasetType dstPB = session.createDatasetType(facility, "PB");
		DatasetType dstQQ = session.createDatasetType(facility, "QQ");

		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		Dataset dsPB = session.createDataset("PB", dstPB, inv);
		Dataset dsQQ = session.createDataset("QQ", dstQQ, inv);

		DatafileFormat dfmt = session.createDatafileFormat(facility, "png", "binary");

		session.createDatafile("PB", dfmt, dsPB);
		session.createDatafile("QQ", dfmt, dsQQ);

		String q1 = "COUNT(Dataset.name) [type.name = 'PB'] <-> Datafile[name = 'PB']";
		String q2 = "COUNT(Dataset.name)";
		String q3 = "COUNT(Dataset)";

		assertEquals(1L, session.search(q1).get(0));
		assertEquals(2L, session.search(q2).get(0));
		assertEquals(2L, session.search(q3).get(0));

		try {
			session.delRule("root", "Dataset", "CRUD");
			// The space between the single quotes is necessary - I suspect a bug in eclipselink
			session.addRule("root", "Dataset [type.name = ' ']", "R");

			assertEquals(0L, session.search(q1).get(0));
			assertEquals(0L, session.search(q2).get(0));
			assertEquals(0L, session.search(q3).get(0));

			session.delRule("root", "Dataset [type.name = ' ']", "R");
			session.addRule("root", "Dataset [type.name = 'PB']", "R");

			assertEquals(1L, session.search(q1).get(0));
			assertEquals(1L, session.search(q2).get(0));
			assertEquals(1L, session.search(q3).get(0));

			session.delRule("root", "Dataset [type.name = 'PB']", "R");
		} finally {
			session.addRule("root", "Dataset", "CRUD");
		}
	}

	@Test
	public void authz2() throws Exception {
		session.clear();

		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		Rule rule = new Rule();
		rule.setCrudFlags("R");
		rule.setWhat("Investigation <-> InvestigationUser <-> User [name = :user]");
		session.create(rule);

		User piOne = new User();
		piOne.setName("piOne");
		piOne.setId(session.create(piOne));

		User piTwo = new User();
		piTwo.setName("piTwo");
		piTwo.setId(session.create(piTwo));

		Investigation invOne = session.createInvestigation(facility, "InvestigationOne",
				"Investigation one", investigationType);

		Investigation invTwo = session.createInvestigation(facility, "InvestigationTwo",
				"Investigation two", investigationType);

		InvestigationUser iuOne = new InvestigationUser();
		iuOne.setInvestigation(invOne);
		iuOne.setUser(piOne);
		iuOne.setRole("Principal Investigator");
		iuOne.setId(session.create(iuOne));

		InvestigationUser iuTwo = new InvestigationUser();
		iuTwo.setInvestigation(invTwo);
		iuTwo.setUser(piTwo);
		iuTwo.setRole("Principal Investigator");
		iuTwo.setId(session.create(iuTwo));

		String piOneSessionId = session.login("db", "username", "piOne", "password", "piOne");
		String piTwoSessionId = session.login("db", "username", "piTwo", "password", "piTwo");

		List<Object> invsOne = session.getIcat().search(piOneSessionId, "Investigation");
		assertEquals(1, invsOne.size());
		assertEquals("InvestigationOne", ((Investigation) invsOne.get(0)).getName());

		List<Object> invsTwo = session.getIcat().search(piTwoSessionId, "Investigation");
		assertEquals(1, invsTwo.size());
		assertEquals("InvestigationTwo", ((Investigation) invsTwo.get(0)).getName());

		// Create new user for PIs to relate to his investigation
		User aone = new User();
		aone.setName("aone");
		aone.setId(session.create(aone));

		Grouping oneControllers = new Grouping();
		oneControllers.setName("OneControllers");
		oneControllers.setId(session.create(oneControllers));

		UserGroup userGroup = new UserGroup();
		userGroup.setUser(piOne);
		userGroup.setGrouping(oneControllers);
		session.create(userGroup);

		rule = new Rule();
		rule.setCrudFlags("CRUD");
		rule.setGrouping(oneControllers);
		rule.setWhat("InvestigationUser <-> Investigation [name = 'InvestigationOne']");
		session.create(rule);

		try {
			InvestigationUser iuaone = new InvestigationUser();
			iuaone.setInvestigation(invTwo);
			iuaone.setUser(aone);
			iuaone.setId(session.getIcat().create(piOneSessionId, iuaone));
			fail("Should not get here as can't grant access to inv two");
		} catch (Exception e) {
		}

		{
			InvestigationUser iuaone = new InvestigationUser();
			iuaone.setInvestigation(invOne);
			iuaone.setUser(aone);
			iuaone.setId(session.getIcat().create(piOneSessionId, iuaone));
		}

		assertEquals(2, (session.getIcat().search(piOneSessionId, "InvestigationUser")).size());
		assertEquals(0, (session.getIcat().search(piTwoSessionId, "InvestigationUser")).size());

		// Create a simple rule allowing oneControllers full access to InvestigationUser and ensure
		// that reading works.

		rule = new Rule();
		rule.setCrudFlags("CRUD");
		rule.setGrouping(oneControllers);
		rule.setWhat("InvestigationUser");
		session.create(rule);

		assertEquals(3, (session.getIcat().search(piOneSessionId, "InvestigationUser")).size());

		try {
			session.getIcat().search(piTwoSessionId, "InvestigationUser");
		} catch (Exception e) {
		}

		session.clearAuthz();
		session.setAuthz();

	}

	/**
	 * To test with the case when an operation is allowed by a general rule but an unrestricted rule
	 * also exists. This used to give a problem if the unrestricted rule was not processed first.
	 */
	@Test
	public void authz3() throws Exception {
		session.clear();

		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		List<Object> objects = session
				.search("Rule [bean='Investigation']<-> Grouping[name='root']");
		for (Object o : objects) {
			session.delete((Rule) o);
		}

		Grouping rootG = (Grouping) session.search("Grouping [name=:user]").get(0);

		Rule rule = new Rule();
		rule.setCrudFlags("C");
		rule.setGrouping(rootG);
		rule.setWhat("Investigation");
		session.create(rule);

		rule = new Rule();
		rule.setCrudFlags("R");
		rule.setWhat("Investigation <-> InvestigationUser <-> User [name = :user]");
		session.create(rule);

		session.createInvestigation(facility, "InvestigationOne", "Investigation one",
				investigationType);

		session.createInvestigation(facility, "InvestigationTwo", "Investigation two",
				investigationType);

		rule = new Rule();
		rule.setCrudFlags("CRUD");
		rule.setGrouping(rootG);
		rule.setWhat("Investigation");
		session.create(rule);

		assertEquals(2, (session.search("Investigation")).size());

		session.clearAuthz();
		session.setAuthz();

	}

	@Test
	public void bigCreate() throws Exception {
		session.clear();

		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		DatasetType dst = session.createDatasetType(facility, "GQ");

		ParameterType p = new ParameterType();
		p.setName("TIMESTAMP");
		p.setUnits("TIMESTAMP");
		p.setDescription("F is not a wibble");
		p.setApplicableToSample(true);
		p.setApplicableToDataset(true);
		p.setValueType(ParameterValueType.DATE_AND_TIME);
		p.setFacility(facility);
		p.setId((Long) session.create(p));

		DatafileFormat dft1 = session.createDatafileFormat(facility, "png", "binary");
		DatafileFormat dft2 = session.createDatafileFormat(facility, "bmp", "binary");

		Investigation inv = new Investigation();
		inv.setId(42L);
		inv.setFacility(facility);
		inv.setName("A");
		inv.setTitle("Not null");
		inv.setType(investigationType);
		inv.setVisitId("57");

		final Dataset wibble = addDataset(inv, "Wibble", dst);

		addDatafile(wibble, "wib1", dft1);

		addDatafile(wibble, "wib2", dft2);

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		addDatasetParameter(wibble, date, p);

		Dataset wobble = addDataset(inv, "Wobble", dst);
		addDatafile(wobble, "wob1", dft1);

		Sample sample = addSample(inv, "S1");
		addSample(inv, "S2");

		addSampleParameter(sample, date, p);

		session.registerInvestigation(inv);

		inv = (Investigation) session.get("Investigation INCLUDE  Sample, SampleParameter",
				inv.getId());
		assertEquals(0, inv.getSamples().size());

		session.addRule("root", "Sample", "R");

		inv = (Investigation) session.get("Investigation INCLUDE  Sample, SampleParameter",
				inv.getId());
		assertEquals(2, inv.getSamples().size());
		for (Sample s : inv.getSamples()) {
			assertEquals(0, s.getParameters().size());
		}

		session.addRule("root", "SampleParameter", "R");

		inv = (Investigation) session.get("Investigation INCLUDE  Sample, SampleParameter",
				inv.getId());
		assertEquals(2, inv.getSamples().size());
		for (Sample s : inv.getSamples()) {
			if (s.getName().equals("S1")) {
				assertEquals(1, s.getParameters().size());
			} else if (s.getName().equals("S2")) {
				assertEquals(0, s.getParameters().size());
			} else {
				fail("Neither S1 nor S2");
			}
		}
	}

	@Test
	public void createCascade() throws Exception {
		session.clear();
		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		List<Object> objects = session.search("User [name = 'root']");
		User u = (User) objects.get(0);
		InvestigationUser iu = new InvestigationUser();
		iu.setUser(u);
		Investigation i = new Investigation();
		i.setFacility(facility);
		i.setName("Frederick");
		i.setTitle("the Great");
		i.setVisitId("42");
		i.setType(investigationType);
		i.getInvestigationUsers().add(iu);
		Long invid = session.create(i);
		objects = session
				.search("Investigation INCLUDE InvestigationUser, User [name='Frederick']");
		assertEquals(1, objects.size());
		i = (Investigation) objects.get(0);
		assertEquals(1, i.getInvestigationUsers().size());
		assertEquals("root", i.getInvestigationUsers().get(0).getUser().getName());
		session.synchLucene();

		List<Object> results = session.searchText("frederick AND great", 10, null);
		assertEquals(1, results.size());
		EntityBaseBean result = (EntityBaseBean) results.get(0);
		assertEquals("Investigation", result.getClass().getSimpleName());
		assertEquals(invid, result.getId());

		results = session.searchText("frederick AND great", 10, "Investigation");
		assertEquals(1, results.size());
		result = (EntityBaseBean) results.get(0);
		assertEquals("Investigation", result.getClass().getSimpleName());
		assertEquals(invid, result.getId());

		results = session.searchText("frederick AND wimp", 10, null);
		assertEquals(0, results.size());

		i = (Investigation) session.get("Investigation INCLUDE 1", invid);
		i.setTitle("the Wimp");
		session.update(i);
		session.synchLucene();

		results = session.searchText("frederick AND wimp", 10, null);
		assertEquals(1, results.size());
		result = (EntityBaseBean) results.get(0);
		assertEquals("Investigation", result.getClass().getSimpleName());
		assertEquals(invid, result.getId());

		results = session.searchText("frederick AND wimp", 10, "Investigation");
		assertEquals(1, results.size());
		result = (EntityBaseBean) results.get(0);
		assertEquals("Investigation", result.getClass().getSimpleName());
		assertEquals(invid, result.getId());

		results = session.searchText("frederick AND great", 10, null);
		assertEquals(0, results.size());

		session.delete(i);
		session.synchLucene();

		results = session.searchText("frederick AND wimp", 10, null);
		assertEquals(0, results.size());

		results = session.searchText("frederick AND wimp", 10, "Investigation");
		assertEquals(0, results.size());

		results = session.searchText("frederick AND great", 10, null);
		assertEquals(0, results.size());
	}

	@Test
	public void duplicates() throws Exception {
		session.clear();

		Facility f = new Facility();
		f.setName("TestDuplicates");
		session.create(f);
		try {
			session.create(f);
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(IcatExceptionType.OBJECT_ALREADY_EXISTS, ue.getType());
			assertEquals("Facility exists with name = 'TestDuplicates'", ue.getMessage());
			assertEquals(-1, ue.getOffset());
		}
	}

	@Test
	public void duplicatesMany() throws Exception {
		session.clear();

		List<EntityBaseBean> beans = new ArrayList<EntityBaseBean>();
		Facility f = new Facility();
		f.setName("One");
		beans.add(f);
		f = new Facility();
		f.setName("Two");
		beans.add(f);
		f = new Facility();
		f.setName("Three");
		beans.add(f);
		f = new Facility();
		f.setName("Two");
		beans.add(f);
		f = new Facility();
		f.setName("One");
		beans.add(f);
		try {
			session.createMany(beans);
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(IcatExceptionType.OBJECT_ALREADY_EXISTS, ue.getType());
			assertEquals("Facility exists with name = 'Two'", ue.getMessage());
			assertEquals(3, ue.getOffset());
		}
	}

	@Test
	public void duplicatesMany2() throws Exception {
		session.clear();

		List<EntityBaseBean> beans = new ArrayList<EntityBaseBean>();
		Facility f = new Facility();
		f.setName("One");
		beans.add(f);
		f = new Facility();
		f.setName("Two");
		beans.add(f);
		f = new Facility();
		f.setName("Three");
		beans.add(f);
		f = new Facility();
		f.setName("Two");
		beans.add(f);
		f = new Facility();
		f.setName("One");
		beans.add(f);
		f = new Facility();
		f.setName("Two");
		session.create(f);
		try {
			session.createMany(beans);
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(IcatExceptionType.OBJECT_ALREADY_EXISTS, ue.getType());
			assertEquals("Facility exists with name = 'Two'", ue.getMessage());
			assertEquals(1, ue.getOffset());
		}
	}

	@Test
	public void duplicatesMany3() throws Exception {
		session.clear();
		Facility f = new Facility();
		f.setName("Two");
		f.setId(session.create(f));
		List<EntityBaseBean> beans = new ArrayList<EntityBaseBean>();
		InvestigationType i = new InvestigationType();
		i.setName("One");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("Two");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("Three");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("Two");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("One");
		i.setFacility(f);
		beans.add(i);
		try {
			session.createMany(beans);
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(IcatExceptionType.OBJECT_ALREADY_EXISTS, ue.getType());
			assertTrue(ue.getMessage().startsWith(
					"InvestigationType exists with name = 'Two', facility = 'id:"));
			assertEquals(3, ue.getOffset());
		}
	}

	@Test
	public void gets() throws Exception {
		session.clear();
		create();
		Long dsId = (Long) session.search("Dataset.id [name = 'Wibble']").get(0);

		assertEquals("Wibble", ((Dataset) session.get("Dataset", dsId)).getName());

		Dataset ds = (Dataset) session.get("Dataset ds INCLUDE ds.datafiles df, df.datafileFormat",
				dsId);
		assertEquals("Wibble", ds.getName());
		assertEquals(2, ds.getDatafiles().size());
		for (Datafile df : ds.getDatafiles()) {
			assertTrue(df.getName().equals("wib1") || df.getName().equals("wib2"));
			String tn = df.getDatafileFormat().getName();
			if (df.getName().equals("wib1")) {
				assertEquals(tn, "png");
			} else {
				assertEquals(tn, "bmp");
			}
		}

		try {
			session.get("Dataset", random.nextLong());
			fail("No throw");
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() != IcatExceptionType.NO_SUCH_OBJECT_FOUND) {
				throw e;
			}
		}

		try {
			session.get("Dataset INCLUDE Investigator", dsId);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
			assertEquals("Investigator is not an EntityBaseBean", e.getMessage());
		}
		try {
			session.get("Dataset INCLUDE Investigation, Facility, Instrument", dsId);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
		}
		try {
			session.get("Dataset INCLUDE User", dsId);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
			assertEquals("Unable to reach User in list of INCLUDES.", e.getMessage());

		}
	}

	@Test
	public void inapplicableParameterType() throws Exception {
		session.clear();
		Facility facility = session.createFacility("Test Facility", 90);
		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");
		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		ParameterType pts = new ParameterType();
		pts.setName("UselessString");
		pts.setValueType(ParameterValueType.STRING);
		pts.setFacility(facility);
		pts.setUnits("peck");
		pts.setId((Long) session.create(pts));

		InvestigationParameter ip = new InvestigationParameter();

		ip.setType(pts);
		ip.setInvestigation(inv);

		try {
			ip.setStringValue("bad");
			session.create(ip);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.VALIDATION, e.getFaultInfo().getType());
			assertEquals("Parameter of type UselessString is not applicable to an Investigation",
					e.getMessage());
		}
	}

	@Test
	public void includes() throws Exception {
		session.clear();
		create();

		List<?> results = session.search("Dataset.id order by id");

		assertEquals("Count", 4, results.size());
		Long dsid = (Long) results.get(0);

		results = session.search("Dataset [id = " + dsid + "]");
		assertEquals("Count", 1, results.size());
		Dataset ds = (Dataset) results.get(0);
		assertEquals("Value", dsid, ds.getId());
		assertEquals("No files", 0, ds.getDatafiles().size());
		assertEquals("No params", 0, ds.getParameters().size());
		assertNull("No inv", ds.getInvestigation());

		results = session.search("Dataset INCLUDE Datafile [id = " + dsid + "]");
		assertEquals("Count", 1, results.size());
		ds = (Dataset) results.get(0);
		assertEquals("Value", dsid, ds.getId());
		assertEquals("Files", 2, ds.getDatafiles().size());
		assertEquals("No params", 0, ds.getParameters().size());
		assertNull("No inv", ds.getInvestigation());

		try {
			results = session.search("Dataset INCLUDE 1, Datafile [id = " + dsid + "]");
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(-1, ue.getOffset());
			assertEquals(IcatExceptionType.BAD_PARAMETER, ue.getType());
			assertEquals(
					"Expected token from types [ENTSEP] at token , in INCLUDE 1 < , > Datafile [ ",
					ue.getMessage());
		}

		try {
			results = session.search("Dataset INCLUDE Datafile, 1 [id = " + dsid + "]");
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(-1, ue.getOffset());
			assertEquals(IcatExceptionType.BAD_PARAMETER, ue.getType());
			assertEquals("Expected token from types [NAME] at token 1 in Datafile , < 1 > [ id ",
					ue.getMessage());
		}

		results = session.search("Dataset INCLUDE 1 [id = " + dsid + "]");
		assertEquals("Count", 1, results.size());
		ds = (Dataset) results.get(0);
		assertEquals("Value", dsid, ds.getId());
		assertEquals("No files", 0, ds.getDatafiles().size());
		assertEquals("No params", 0, ds.getParameters().size());
		assertNotNull("Inv", ds.getInvestigation());

		results = session.search("Dataset INCLUDE DatasetParameter [id = " + dsid + "]");
		assertEquals("Count", 1, results.size());
		ds = (Dataset) results.get(0);
		assertEquals("Value", dsid, ds.getId());
		assertEquals("No Files", 0, ds.getDatafiles().size());
		assertEquals("Params", 1, ds.getParameters().size());
		assertNull("No inv", ds.getInvestigation());

		results = session.search("Dataset INCLUDE Datafile, DatasetParameter [id = " + dsid + "]");
		assertEquals("Count", 1, results.size());
		ds = (Dataset) results.get(0);
		assertEquals("Value", dsid, ds.getId());
		assertEquals("Files", 2, ds.getDatafiles().size());
		assertEquals("Params", 1, ds.getParameters().size());
		assertNull("No inv", ds.getInvestigation());

		results = session.search("Dataset INCLUDE Datafile, DatasetParameter, Investigation [id = "
				+ dsid + "]");
		assertEquals("Count", 1, results.size());
		ds = (Dataset) results.get(0);
		assertEquals("Value", dsid, ds.getId());
		assertEquals("Files", 2, ds.getDatafiles().size());
		assertEquals("Params", 1, ds.getParameters().size());
		assertNotNull("Inv", ds.getInvestigation());

		results = session.search("Job");
		assertEquals("Count", 1, results.size());
		Job job = (Job) results.get(0);
		assertNull("InputDataset", job.getInputDataCollection());
		assertNull("OutputDataset", job.getOutputDataCollection());

		results = session.search("Job");
		assertEquals("Count", 1, results.size());
		job = (Job) results.get(0);
		assertNull("Application", job.getApplication());

		results = session.search("Job INCLUDE Application");
		assertEquals("Count", 1, results.size());
		job = (Job) results.get(0);
		assertNotNull("Application", job.getApplication());

		results = session.search("Application");
		assertEquals("Count", 1, results.size());
		Application application = (Application) results.get(0);
		assertEquals("InputDataset", 0, application.getJobs().size());

		results = session.search("Application INCLUDE Job");
		assertEquals("Count", 1, results.size());
		application = (Application) results.get(0);
		assertEquals("InputDataset", 1, application.getJobs().size());

		try {
			results = session.search("Job INCLUDE InputDataset, InputDatafile, Dataset, Datafile");
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(-1, ue.getOffset());
			assertEquals(IcatExceptionType.BAD_PARAMETER, ue.getType());
		}

		try {
			results = session
					.search("Job INCLUDE InputDataset, InputDatafile, OutputDataset, OutputDatafile, Dataset, Datafile");
			fail("Exception not thrown");
		} catch (IcatException_Exception e) {
			IcatException ue = e.getFaultInfo();
			assertEquals(-1, ue.getOffset());
			assertEquals(IcatExceptionType.BAD_PARAMETER, ue.getType());
		}

	}

	@Test
	public void login() throws Exception {
		double rm = session.getRemainingMinutes();
		assertTrue(rm > 0);
		assertEquals(System.getProperty("projectVersion").replace("-SNAPSHOT", ""),
				session.getApiVersion());
		assertEquals("root", session.getUserName());
		Thread.sleep(10);
		rm = session.getRemainingMinutes();
		session.refresh();
		assertTrue(session.getRemainingMinutes() > rm);

		String piOneSessionId = session.login("db", "username", "piOne", "password", "piOne");
		session.logout(piOneSessionId);
	}

	@Test
	public void noDuplicates() throws Exception {
		session.clear();
		Facility f = new Facility();
		f.setName("Two");
		f.setId(session.create(f));
		Facility f2 = new Facility();
		f2.setName("Three");
		f2.setId(session.create(f2));
		List<EntityBaseBean> beans = new ArrayList<EntityBaseBean>();
		InvestigationType i = new InvestigationType();
		i.setName("One");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("Two");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("Three");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("Four");
		i.setFacility(f);
		beans.add(i);
		i = new InvestigationType();
		i.setName("One");
		i.setFacility(f2);
		beans.add(i);

		session.createMany(beans);

	}

	@Ignore("Need to include gf-client.jar from glassfish3/glassfish/lib/ - no good maven solution found yet")
	// Need to have notification.list = Dataset Datafile
	// notification.Dataset = CUD
	// notification.Datafile = CUD
	@Test
	public void notifications() throws Exception {
		session.clear();

		Context context = new InitialContext();
		TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context
				.lookup("jms/ICATTopicConnectionFactory");

		Topic topic = (Topic) context.lookup("jms/ICATTopic");

		TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();
		javax.jms.Session jsession = topicConnection.createSession(false,
				javax.jms.Session.AUTO_ACKNOWLEDGE);
		MessageConsumer consumer = jsession.createConsumer(topic);
		Listener topicListener = new Listener();
		consumer.setMessageListener(topicListener);
		topicConnection.start();

		create();

		Long dfid = ((EntityBaseBean) session.search("Datafile ORDER BY id").get(0)).getId();
		Datafile datafile = (Datafile) session.get("Datafile INCLUDE 1", dfid);
		datafile.setDescription("Junk");
		session.update(datafile);
		session.delete(datafile);

		Long dsid = ((EntityBaseBean) session.search("Dataset ORDER BY id").get(0)).getId();
		Dataset dataset = (Dataset) session.get("Dataset INCLUDE 1", dsid);
		dataset.setDescription("Obscure junk");
		session.update(dataset);
		session.delete(dataset);

		// Wait for a second - though it does not appear to be needed
		Thread.sleep(1000);

		int ncreate = 0;
		int nupdate = 0;
		int ndelete = 0;
		int ndataset = 0;
		int ndatafile = 0;

		while (true) {
			ObjectMessage msg = topicListener.getMessage();
			if (msg == null) {
				break;
			}
			String operation = msg.getStringProperty("operation");
			String entity = msg.getStringProperty("entity");
			Long id = (Long) msg.getObject();

			System.out.println(operation + " " + entity + " " + id);
			if (operation.equals("C")) {
				ncreate++;
			} else if (operation.equals("U")) {
				nupdate++;
			} else if (operation.equals("D")) {
				ndelete++;
			}
			if (entity.equals("Datafile")) {
				ndatafile++;
			} else if (entity.equals("Dataset")) {
				ndataset++;
			}

		}
		assertEquals(10, ncreate);
		assertEquals(2, nupdate);
		assertEquals(2, ndelete);
		assertEquals(8, ndatafile);
		assertEquals(6, ndataset);
	}

	@Test
	public void numericParameterRanges() throws Exception {
		session.clear();
		Facility facility = session.createFacility("Test Facility", 90);
		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");
		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		ParameterType ptn = new ParameterType();
		ptn.setName("TestNumeric");
		ptn.setApplicableToInvestigation(true);
		ptn.setValueType(ParameterValueType.NUMERIC);
		ptn.setFacility(facility);
		ptn.setEnforced(true);
		ptn.setMinimumNumericValue(50.);
		ptn.setUnits("bushel");
		ptn.setId((Long) session.create(ptn));

		InvestigationParameter ip = new InvestigationParameter();

		ip.setType(ptn);
		ip.setInvestigation(inv);

		try {
			ip.setNumericValue(40.);
			session.create(ip);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.VALIDATION, e.getFaultInfo().getType());
		}

		ip.setNumericValue(60.);
		ip.setId((Long) session.create(ip));

		try {
			ip.setNumericValue(30.);
			session.update(ip);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.VALIDATION, e.getFaultInfo().getType());
		}

		ip.setNumericValue(70.);
		session.update(ip);

	}

	@Test
	public void oldGets() throws Exception {
		session.clear();
		create();
		Long dsId = (Long) session.search("Dataset.id [name = 'Wibble']").get(0);

		assertEquals("Wibble", ((Dataset) session.get("Dataset", dsId)).getName());

		try {
			session.get("Dataset", random.nextLong());
			fail("No throw");
		} catch (IcatException_Exception e) {
			if (e.getFaultInfo().getType() != IcatExceptionType.NO_SUCH_OBJECT_FOUND) {
				throw e;
			}
		}

		try {
			session.get("Dataset INCLUDE Investigator", dsId);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
			assertEquals("Investigator is not an EntityBaseBean", e.getMessage());
		}
		try {
			session.get("Dataset INCLUDE Investigation, Facility, Instrument", dsId);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
		}
		try {
			session.get("Dataset INCLUDE User", dsId);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.BAD_PARAMETER, e.getFaultInfo().getType());
			assertEquals("Unable to reach User in list of INCLUDES.", e.getMessage());

		}
	}

	@Test
	public void oldSearches() throws Exception {
		session.clear();
		create();

		assertEquals(4L, session.search("COUNT(Dataset)").get(0));

		long max = -999999999999999L;
		long min = 999999999999999L;
		for (Object result : session.search("Dataset")) {
			Dataset ds = (Dataset) result;
			max = Math.max(ds.getId(), max);
			min = Math.min(ds.getId(), min);
		}

		assertEquals(min, session.search("MIN(Dataset.id) [id > 0]").get(0));
		assertEquals(max, session.search("MAX(Dataset.id) [id > 0]").get(0));

		Long invId = (Long) session.search("Investigation.id").get(0);

		List<?> results = session.search("Dataset.id "
				+ "<-> DatasetParameter[type.name = 'TIMESTAMP'] "
				+ "<-> Investigation[name <> 12]");
		assertEquals("Count", 1, results.size());

		results = session.search("Datafile [name = 'fred'] <-> Dataset[id <> 42]");
		assertEquals("Count", 1, results.size());

		String query = "Dataset.id  ORDER BY id [type.name IN :types] <-> Investigation[id BETWEEN :lower AND :upper]";

		query = query.replace(":lower", Long.toString(invId))
				.replace(":upper", Long.toString(invId)).replace(":types", "('GS', 'GQ')");
		results = session.search(query);
		assertEquals("Count", 4, results.size());

		query = "Dataset.id ORDER BY startDate [type.name IN :types AND name >= :lower AND name <= :upper]";
		query = query.replace(":lower", "'Wabble'").replace(":upper", "'Wobble'")
				.replace(":types", "('GS', 'GQ')");
		results = session.search(query);
		assertEquals("Count", 2, results.size());

		query = "ParameterType.name [description LIKE 'F%']";
		results = session.search(query);
		assertEquals("Count", 1, results.size());
		assertEquals("TIMESTAMP", results.get(0));

		results = session.search("Datafile.name ORDER BY id");
		assertEquals("Count", 6, results.size());
		assertEquals("Result", "wib1", results.get(0));
		assertEquals("Result", "wib2", results.get(1));

		results = session.search(",1 Datafile.name ORDER BY id");
		assertEquals("Count", 1, results.size());
		assertEquals("Result", "wib1", results.get(0));

		results = session.search("1, Datafile.name ORDER BY id");
		assertEquals("Count", 5, results.size());
		assertEquals("Result", "wib2", results.get(0));

		results = session.search("1,1 Datafile.name ORDER BY id");
		assertEquals("Count", 1, results.size());
		assertEquals("Result", "wib2", results.get(0));

		results = session.search("100,1 Datafile.name ORDER BY id");
		assertEquals("Count", 0, results.size());

		results = session.search("0,100 Datafile.name ORDER BY id");
		assertEquals("Count", 6, results.size());
		assertEquals("Result", "wib1", results.get(0));
		assertEquals("Result", "wib2", results.get(1));

		results = session.search("Facility");
		Facility facility = (Facility) results.get(0);

		results = session.search("DISTINCT ParameterType.valueType");
		ParameterValueType pvt = (ParameterValueType) results.get(0);

		results = session.search("ParameterType [facility.id=" + facility.getId()
				+ " AND valueType=" + pvt + "]");
		assertEquals(1, results.size());

		results = session.search("ParameterType [facility.id=" + facility.getId() + " AND " + pvt
				+ "= valueType]");
		assertEquals(1, results.size());

		results = session.search("Dataset [complete = TRUE]");
		assertEquals(0, results.size());
		results = session.search("Dataset [complete = FALSE]");
		assertEquals(4, results.size());
	}

	@Test
	public void performance() throws Exception {
		session.clear();
		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		DatasetType dst = session.createDatasetType(facility, "GQ");

		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		Dataset wibble = session.createDataset("Wibble", dst, inv);

		DatafileFormat dfmt = session.createDatafileFormat(facility, "png", "binary");

		long start = System.currentTimeMillis();
		int n = 100;
		for (int i = 0; i < n; i++) {
			session.createDatafile("fred" + i, dfmt, wibble);
		}
		System.out.println("Time per datafile to write: " + (System.currentTimeMillis() - start)
				/ (n + 0.) + "ms");
		List<EntityBaseBean> dfs = new ArrayList<EntityBaseBean>();
		for (int i = 0; i < n; i++) {
			final Datafile datafile = new Datafile();
			datafile.setDatafileFormat(dfmt);
			datafile.setName("bill" + i);
			datafile.setDataset(wibble);
			dfs.add(datafile);
		}
		start = System.currentTimeMillis();
		session.createMany(dfs);
		System.out.println("Time per datafile using createMany: "
				+ (System.currentTimeMillis() - start) / (n + 0.) + "ms");

		start = System.currentTimeMillis();
		List<Object> results = null;
		;
		// int m = 10000000;
		int m = 1;
		for (int i = 0; i < m; i++) {
			results = session
					.search("SELECT df FROM Datafile df INCLUDE df.datafileFormat, df.dataset");
		}

		System.out.println("Time per datafile to retrieve: " + results.size() + " datafiles "
				+ (System.currentTimeMillis() - start) / (results.size() + 0.) + "ms");

		start = System.currentTimeMillis();
		results = session.search("SELECT df FROM Datafile df");
		System.out.println("Time per datafile to retrieve no includes: " + results.size()
				+ " datafiles " + (System.currentTimeMillis() - start) / (results.size() + 0.)
				+ "ms");

		start = System.currentTimeMillis();
		@SuppressWarnings("unused")
		List<Object> ids = session.search("SELECT df.id FROM Datafile df");
		System.out.println("Time per datafile to retrieve ids: " + results.size() + " datafiles "
				+ (System.currentTimeMillis() - start) / (results.size() + 0.) + "ms");

		dfs.clear();
		for (Object odf : results) {
			Datafile df = (Datafile) odf;
			df.setDataset(null);
			df.setDatafileFormat(null);
			dfs.add(df);
		}

		start = System.currentTimeMillis();
		session.deleteMany(dfs);
		System.out.println("Time per datafile to delete: " + results.size()
				+ " datafiles with deleteMany: " + (System.currentTimeMillis() - start)
				/ (results.size() + 0.) + "ms");
	}

	@Test
	public void searches() throws Exception {
		session.clear();
		create();

		Date now = new Date();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss");
		String nowString = "{ts " + df.format(now) + "}";
		String baseq = "SELECT p FROM DatasetParameter p WHERE p.dateTimeValue";
		assertEquals(0, session.search(baseq + " > " + nowString).size());
		assertEquals(1, session.search(baseq + "<= " + nowString).size());

		assertEquals(4L, session.search("SELECT COUNT(ds) FROM Dataset ds").get(0));

		long max = -999999999999999L;
		long min = 999999999999999L;
		for (Object result : session.search("SELECT ds FROM Dataset ds")) {
			Dataset ds = (Dataset) result;
			max = Math.max(ds.getId(), max);
			min = Math.min(ds.getId(), min);
		}

		assertEquals(min,
				session.search("SELECT MIN(ds.id) FROM  Dataset ds WHERE ds.id > 0").get(0));
		assertEquals(max,
				session.search("SELECT MAX(ds.id) FROM  Dataset ds WHERE ds.id > 0").get(0));

		Long invId = (Long) session.search("SELECT inv.id FROM Investigation inv").get(0);

		List<?> results = session
				.search("SELECT ds.id FROM Dataset ds JOIN ds.parameters dsp JOIN ds.investigation inv"
						+ " WHERE dsp.type.name = 'TIMESTAMP' AND inv.name <> 12");
		assertEquals("Count", 1, results.size());

		results = session
				.search("SELECT df FROM Datafile df JOIN df.dataset ds WHERE df.name = 'fred' AND ds.id <> 42");
		assertEquals("Count", 1, results.size());

		String query = "SELECT ds.id FROM Dataset ds JOIN ds.investigation inv "
				+ "WHERE ds.type.name IN :types AND inv.id BETWEEN :lower AND :upper "
				+ "ORDER BY ds.id";
		query = query.replace(":lower", Long.toString(invId))
				.replace(":upper", Long.toString(invId)).replace(":types", "('GS', 'GQ')");
		results = session.search(query);
		assertEquals("Count", 4, results.size());

		query = "SELECT ds.id FROM Dataset ds WHERE ds.type.name IN :types AND ds.name >= :lower AND ds.name <= :upper ORDER BY ds.startDate";
		query = query.replace(":lower", "'Wabble'").replace(":upper", "'Wobble'")
				.replace(":types", "('GS', 'GQ')");
		results = session.search(query);
		assertEquals("Count", 2, results.size());

		query = "SELECT pt.name FROM ParameterType pt WHERE pt.description LIKE 'F%'";
		results = session.search(query);
		assertEquals("Count", 1, results.size());
		assertEquals("TIMESTAMP", results.get(0));

		results = session.search("SELECT df.name FROM Datafile df ORDER BY df.id");
		assertEquals("Count", 6, results.size());
		assertEquals("Result", "wib1", results.get(0));
		assertEquals("Result", "wib2", results.get(1));

		results = session.search("SELECT df.name FROM Datafile df ORDER BY df.id LIMIT 0,1");
		assertEquals("Count", 1, results.size());
		assertEquals("Result", "wib1", results.get(0));

		results = session.search("SELECT df.name FROM Datafile df ORDER BY df.id LIMIT 1,100");
		assertEquals("Count", 5, results.size());
		assertEquals("Result", "wib2", results.get(0));

		results = session.search("SELECT df.name FROM Datafile df ORDER BY df.id LIMIT 1,1");
		assertEquals("Count", 1, results.size());
		assertEquals("Result", "wib2", results.get(0));

		results = session.search("SELECT df.name FROM Datafile df ORDER BY df.id LIMIT 100,1");
		assertEquals("Count", 0, results.size());

		results = session.search("SELECT df.name FROM Datafile df ORDER BY df.id LIMIT 0,100");
		assertEquals("Count", 6, results.size());
		assertEquals("Result", "wib1", results.get(0));
		assertEquals("Result", "wib2", results.get(1));

		results = session
				.search("SELECT ds.name from Dataset ds JOIN ds.datafiles df1 JOIN ds.datafiles df2 "
						+ "WHERE df1.name = 'fred' AND df2.name = 'bill'");
		assertEquals("Count", 1, results.size());
		assertEquals("Result", "dfsin", results.get(0));

		results = session.search("SELECT f FROM Facility f");
		Facility facility = (Facility) results.get(0);

		results = session.search("SELECT DISTINCT pt.valueType FROM ParameterType pt");
		ParameterValueType pvt = (ParameterValueType) results.get(0);

		results = session.search("SELECT pt FROM ParameterType pt WHERE pt.facility.id="
				+ facility.getId() + " AND pt.valueType=" + pvt.getClass().getName() + "." + pvt);
		assertEquals(1, results.size());

		results = session.search("SELECT pt FROM ParameterType pt WHERE pt.facility.id="
				+ facility.getId() + " AND " + pvt.getClass().getName() + "." + pvt
				+ "= pt.valueType");
		assertEquals(1, results.size());

		results = session.search("SELECT ds FROM Dataset ds WHERE ds.complete = TRUE");
		assertEquals(0, results.size());
		results = session.search("SELECT ds FROM Dataset ds WHERE ds.complete = FALSE");
		assertEquals(4, results.size());
	}

	@Test
	public void stringParameterRanges() throws Exception {
		session.clear();
		Facility facility = session.createFacility("Test Facility", 90);
		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");
		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		ParameterType pts = new ParameterType();
		pts.setName("TestString");
		pts.setApplicableToInvestigation(true);
		pts.setValueType(ParameterValueType.STRING);
		pts.setFacility(facility);
		pts.setEnforced(true);
		PermissibleStringValue psv = new PermissibleStringValue();
		psv.setValue("good1");
		pts.getPermissibleStringValues().add(psv);
		psv = new PermissibleStringValue();
		psv.setValue("good2");
		pts.getPermissibleStringValues().add(psv);
		pts.setUnits("chain");
		pts.setId((Long) session.create(pts));

		InvestigationParameter ip = new InvestigationParameter();

		ip.setType(pts);
		ip.setInvestigation(inv);

		try {
			ip.setStringValue("bad");
			session.create(ip);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.VALIDATION, e.getFaultInfo().getType());
		}

		ip.setStringValue("good1");
		ip.setId((Long) session.create(ip));

		try {
			ip.setStringValue("worse");
			session.update(ip);
			fail("No throw");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.VALIDATION, e.getFaultInfo().getType());
		}

		ip.setStringValue("good2");
		session.update(ip);
	}

	@Test
	public void testInvestigation() throws Exception {
		EntityInfo ei = session.getEntityInfo("Investigation");
		assertEquals("An investigation or experiment", ei.getClassComment());
		for (Constraint constraint : ei.getConstraints()) {
			assertEquals(Arrays.asList("facility", "name", "visitId"), constraint.getFieldNames());
		}
		assertEquals(20, ei.getFields().size());
		int n = 0;
		for (EntityField field : ei.getFields()) {
			if (field.getName().equals("id")) {
				assertEquals("Long", field.getType());
				assertEquals(false, field.isNotNullable());
				assertEquals(null, field.getComment());
				assertEquals(RelType.ATTRIBUTE, field.getRelType());
				assertEquals(null, field.getStringLength());
			} else if (field.getName().equals("facilityCycle")) {
				assertEquals("FacilityCycle", field.getType());
				assertEquals(false, field.isNotNullable());
				assertEquals(null, field.getComment());
				assertEquals(RelType.ONE, field.getRelType());
				assertEquals(null, field.getStringLength());
			} else if (field.getName().equals("title")) {
				assertEquals("String", field.getType());
				assertEquals(true, field.isNotNullable());
				assertEquals("Full title of the investigation", field.getComment());
				assertEquals(RelType.ATTRIBUTE, field.getRelType());
				assertEquals((Integer) 255, field.getStringLength());
			} else if (field.getName().equals("investigationUsers")) {
				assertEquals("InvestigationUser", field.getType());
				assertEquals(false, field.isNotNullable());
				assertEquals(null, field.getComment());
				assertEquals(RelType.MANY, field.getRelType());
				assertEquals(null, field.getStringLength());
			} else {
				n++;
			}
		}
		assertEquals(17, n);
	}

	@Test
	public void testTestCalls() throws Exception {
		session.clear();

		/* Create user and rules */
		User piOne = new User();
		piOne.setName("piOne");
		piOne.setId(session.create(piOne));

		User piTwo = new User();
		piTwo.setName("piTwo");
		piTwo.setId(session.create(piTwo));

		Grouping ones = new Grouping();
		ones.setName("Ones");
		ones.setId(session.create(ones));

		UserGroup userGroup = new UserGroup();
		userGroup.setUser(piOne);
		userGroup.setGrouping(ones);
		session.create(userGroup);

		Rule rule = new Rule();
		rule.setGrouping(ones);
		rule.setCrudFlags("CRUD");
		rule.setWhat("Facility");
		session.create(rule);

		String piOneSessionId = session.login("db", "username", "piOne", "password", "piOne");
		String piTwoSessionId = session.login("db", "username", "piTwo", "password", "piTwo");

		Facility facility = new Facility();
		facility.setName("A Facility");

		/* testCreate */
		session.getIcat().testCreate(piOneSessionId, facility);

		try {
			session.getIcat().testCreate(piTwoSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.INSUFFICIENT_PRIVILEGES, e.getFaultInfo().getType());
			assertEquals("CREATE access to this Facility is not allowed.", e.getMessage());
		}

		facility.setId(session.getIcat().create(piOneSessionId, facility));

		try {
			session.getIcat().testCreate(piOneSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.OBJECT_ALREADY_EXISTS, e.getFaultInfo().getType());
			assertEquals("Facility exists with name = 'A Facility'", e.getMessage());
		}

		try {
			session.getIcat().testCreate(piTwoSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.OBJECT_ALREADY_EXISTS, e.getFaultInfo().getType());
			assertEquals("Facility exists with name = 'A Facility'", e.getMessage());
		}

		/* testUpdate */
		facility.setName("Banana");
		session.getIcat().testUpdate(piOneSessionId, facility);

		try {
			session.getIcat().testUpdate(piTwoSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.INSUFFICIENT_PRIVILEGES, e.getFaultInfo().getType());
			assertEquals("UPDATE access to this Facility is not allowed.", e.getMessage());
		}

		session.getIcat().update(piOneSessionId, facility);

		session.getIcat().testUpdate(piOneSessionId, facility);

		try {
			session.getIcat().testUpdate(piTwoSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.INSUFFICIENT_PRIVILEGES, e.getFaultInfo().getType());
			assertEquals("UPDATE access to this Facility is not allowed.", e.getMessage());
		}

		/* testDelete */
		session.getIcat().testDelete(piOneSessionId, facility);

		try {
			session.getIcat().testDelete(piTwoSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.INSUFFICIENT_PRIVILEGES, e.getFaultInfo().getType());
			assertEquals("DELETE access to this Facility is not allowed.", e.getMessage());
		}

		session.getIcat().delete(piOneSessionId, facility);

		try {
			session.getIcat().testDelete(piOneSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.NO_SUCH_OBJECT_FOUND, e.getFaultInfo().getType());
			assertEquals("Facility[id:" + facility.getId() + "] not found.", e.getMessage());
		}

		try {
			session.getIcat().testDelete(piTwoSessionId, facility);
			fail("No exception thrown");
		} catch (IcatException_Exception e) {
			assertEquals(IcatExceptionType.NO_SUCH_OBJECT_FOUND, e.getFaultInfo().getType());
			assertEquals("Facility[id:" + facility.getId() + "] not found.", e.getMessage());
		}

		session.clearAuthz();
		session.setAuthz();
	}

	@Test
	public void updates() throws Exception {
		session.clear();

		Facility facility = session.createFacility("Test Facility", 90);

		InvestigationType investigationType = session.createInvestigationType(facility,
				"TestExperiment");

		DatasetType dst = session.createDatasetType(facility, "GQ");

		Investigation inv = session.createInvestigation(facility, "A", "Not null",
				investigationType);

		Dataset wibble = session.createDataset("Wibble", dst, inv);
		Dataset wobble = session.createDataset("Wobble", dst, inv);

		DatafileFormat dfmt = session.createDatafileFormat(facility, "png", "binary");

		Datafile df = session.createDatafile("fred", dfmt, wibble);
		df = (Datafile) session.get("Datafile INCLUDE Dataset, DatafileFormat", df.getId());
		assertEquals("Wibble", df.getDataset().getName());

		df.setDataset(wobble);
		df.setLocation("guess");
		df.setDatafileFormat(session.createDatafileFormat(facility, "notpng", "notbinary"));
		df.setDatafileFormat(null);
		df.setFileSize(-1L);
		session.update(df);
		df = (Datafile) session.get("Datafile INCLUDE Dataset,DatafileFormat", df.getId());
		assertEquals("Wobble", df.getDataset().getName());
	}

}
