package uk.icat3.io.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.xml.ws.WebServiceRef;

import org.apache.openejb.api.LocalClient;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.util.IDSMock;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.Dataset;
import uk.icat3.entity.EntityBaseBean;
import uk.icat3.entity.Investigation;
import uk.icat3.io.entity.ExtractedDataBean;

@LocalClient
public class ICATIOServiceTest {

	private static final String SELECT_FROM_INVESTIGATION = "SELECT * from Investigation";
	private static final String SELECT_FROM_DATASET = "SELECT * from Dataset";

	private static final String IO_URL = "http://127.0.0.1:4204/ICATIOService?wsdl";
	private static final String ICAT_URL = "http://127.0.0.1:4204/ICATService?wsdl";

	@WebServiceRef(wsdlLocation = IO_URL)
	private ICATIOWs service;
	
	private static InitialContext context;

	@PersistenceContext(unitName = "icat3-io-test")
	EntityManager manager;

	private static boolean initialized = false;
	private Investigation investigation;
	private static IDSMock idsMock;

	@BeforeClass
	public static void beforeClass() throws Exception {
		Properties p = new Properties();
		p.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.openejb.client.LocalInitialContextFactory");

		p.put("database", "new://Resource?type=DataSource");
		p.put("database.JdbcDriver", "org.hsqldb.jdbcDriver");
		p.put("database.JdbcUrl", "jdbc:hsqldb:mem:database");

		p.put("databaseUnmanaged", "new://Resource?type=DataSource");
		p.put("databaseUnmanaged.JdbcDriver", "org.hsqldb.jdbcDriver");
		p.put("databaseUnmanaged.JdbcUrl", "jdbc:hsqldb:mem:database");
		p.put("databaseUnmanaged.JtaManaged", "false");

		p.put("icat3.hibernate.hbm2ddl.auto", "update");
		p.put("icat3.hibernate.dialect", "org.hibernate.dialect.HSQLDialect");

		
		p.put("openejb.embedded.remotable", "true");

		configureLogging(p);

		context = new InitialContext(p);

		idsMock = new IDSMock();
		idsMock.start();
		
	}
	
	@AfterClass
	public static void afterClass() throws Exception {
		idsMock.stop();
	}
	
	private static void setPersistenceProvider(Properties p, String ... args) {
		for (String arg : args) {
			p.put(arg + ".hibernate.hbm2ddl.auto", "create-drop");
			p.put(arg + ".hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
			p.put(arg + ".hibernate.show_sql", "true");
			p.put(arg + ".provider", "org.hibernate.ejb.HibernatePersistence");

		}
	}

	private static void configureLogging(Properties p) {
		p.put("log4j.rootLogger", "INFO,C");
		// p.put("log4j.category.OpenEJB", "warn");
		// p.put("log4j.category.OpenEJB.options", "info");
		// p.put("log4j.category.OpenEJB.server", "info");
		// p.put("log4j.category.OpenEJB.startup", "info");
		// p.put("log4j.category.OpenEJB.startup.service", "warn");
		// p.put("log4j.category.OpenEJB.startup.config", "info");
		// p.put("log4j.category.OpenEJB.hsql", "info");
		// p.put("log4j.category.CORBA-Adapter", "info");
		// p.put("log4j.category.Transaction", "warn");
		// p.put("log4j.category.org.apache.activemq", "error");
		// p.put("log4j.category.org.apache.geronimo", "error");
		// p.put("log4j.category.openjpa", "error");
		p.put("log4j.appender.C", "org.apache.log4j.ConsoleAppender");
		p.put("log4j.appender.C.layout", "org.apache.log4j.SimpleLayout");
	}

	@Before
	public void setUp() throws NamingException {
		context.bind("inject", this);
		insertData();
	}

	private void insertData() {
		if (initialized) {
			return;
		}
		initialized = true;
		try {
			persist(createInvestigation());
			createDataset();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createDataset() throws Exception {
		Dataset dataset = new Dataset();
		dataset.setName("name");
		dataset.setDatasetType("type");
		dataset.setInvestigationId(1L);
		dataset.setInvestigation(investigation);
		persist(dataset);

		Datafile datafile = new Datafile();
		datafile.setName("name");
		datafile.setLocation("20110317/00000001/16ANF_LA1.bmp");
		datafile.setDataset(dataset);
		persist(datafile);

		dataset.setDatafileCollection(Arrays.asList(datafile));
		merge(dataset);
	}

	private void persist(EntityBaseBean object) throws NamingException, NotSupportedException, SystemException, HeuristicMixedException, HeuristicRollbackException, RollbackException {
		UserTransaction tx = (UserTransaction) context.lookup("java:comp/UserTransaction");
		object.setCreateId(String.valueOf(new Random().nextInt()));
		tx.begin();
		manager.persist(object);
		tx.commit();
		Query query = manager.createNativeQuery(SELECT_FROM_INVESTIGATION);
		List resultList = query.getResultList();
		System.err.println(resultList);
	}

	private void merge(EntityBaseBean object) throws NamingException, NotSupportedException, SystemException, HeuristicMixedException, HeuristicRollbackException, RollbackException {
		UserTransaction tx = (UserTransaction) context.lookup("java:comp/UserTransaction");
		object.setCreateId(String.valueOf(new Random().nextInt()));
		tx.begin();
		manager.merge(object);
		tx.commit();
		Query query = manager.createNativeQuery(SELECT_FROM_INVESTIGATION);
		List resultList = query.getResultList();
		System.err.println(resultList);
	}

	private Investigation createInvestigation() {
		investigation = new Investigation();
		investigation.setInvNumber(String.valueOf(new Random().nextInt()));
		investigation.setFacility("CLF");
		investigation.setInvType("experiment");
		investigation.setTitle("title");
		return investigation;
	}

	@Test
	public void shouldStartWebService() throws MalformedURLException {
		assertNotNull(service);
	}

	@Test
	public void shouldExtractListOfInvestigations() throws Exception {
		ExtractedDataBean data = service.downloadData(SELECT_FROM_INVESTIGATION);
		assertNotNull(data);
		assertNotNull(data.getFiles());
		assertNotNull(data.getResultList());
		assertEquals(1, data.getResultList().length);
	}

	@Test
	public void shouldExtractDataset() {
		ExtractedDataBean data = service.downloadData(SELECT_FROM_DATASET);
		assertNotNull(data);
		assertNotNull(data.getFiles());
		assertNotNull(data.getResultList());
		assertEquals(1, data.getResultList().length);
		assertEquals(1, data.getFiles().size());
		byte[] image = data.getFiles().values().iterator().next();
		assertNotNull(image);
	}
	
	@Test
	public void shouldUploadDataset() throws Exception {
		ExtractedDataBean data = service.downloadData(SELECT_FROM_DATASET);

//		String sessionId = icatService.login("CIC", "password");
		service.uploadData("", data);
		
	}

}
