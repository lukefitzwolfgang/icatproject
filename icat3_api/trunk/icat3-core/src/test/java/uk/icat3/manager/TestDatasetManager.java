package uk.icat3.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import uk.icat3.entity.Dataset;
import uk.icat3.entity.DatasetType;
import uk.icat3.entity.Facility;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.InvestigationType;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ObjectAlreadyExistsException;
import uk.icat3.util.BaseTestTransaction;
import uk.icat3.util.RuleManager;

public class TestDatasetManager extends BaseTestTransaction {

	private Investigation inv;
	private DatasetType dst;

	@Before
	public void setUpManager() throws Exception {
		RuleManager.oldAddUserGroupMember("Group", "P1", em);
		RuleManager.oldAddUserGroupMember("Group", "P2", em);
		RuleManager.oldAddRule("Group", "Dataset", "CRUD", null, em);
		RuleManager.oldAddRule("Group", "Datafile", "CRUD", null, em);
		RuleManager.oldAddRule("Group", "Investigation", "CRUD", null, em);
		RuleManager.oldAddRule("Group", "DatasetType", "CRUD", null, em);
		RuleManager.oldAddRule("Group", "InvestigationType", "CRUD", null, em);
		RuleManager.oldAddRule("Group", "Facility", "CRUD", null, em);

		Facility f = new Facility();
		f.setName("ISIS");
		f.setDaysUntilRelease(90);
		BeanManager.create("P1", f, em);

		InvestigationType type = new InvestigationType();
		type.setName("experiment");
		BeanManager.create("P1", type, em);

		inv = createInvestigation("42", "Fred", type, f);
		inv.setId((Long) BeanManager.create("P1", inv, em).getPk());

		dst = createDatasetType("dstype", "wibble");
		BeanManager.create("P1", dst, em);
	}

	@Test
	public void testCreateDataset() throws Exception {
		int nDatasets = BeanManager.search("P1", "Dataset", em).getList().size();
		int nDatafiles = BeanManager.search("P1", "Datafile", em).getList().size();
		Dataset ds = makeDs();
		assertEquals("Size", nDatasets + 1, BeanManager.search("P1", "Dataset", em).getList().size());
		assertEquals("Size", nDatafiles + 2, BeanManager.search("P1", "Datafile", em).getList().size());

		assertEquals("dsname", ds.getName());
		assertEquals("dfname1", ds.getDatafiles().iterator().next().getName());
		assertEquals("createId", "P1", ds.getCreateId());
		assertEquals("modId", "P1", ds.getModId());
	}

	@Test(expected = ObjectAlreadyExistsException.class)
	public void testCreateDatasetClash() throws Exception {
		makeDs();
		makeDs();

	}

	private Dataset makeDs() throws Exception {
		Dataset ds = createDataset(inv, "dsname", dst);
		ds.getDatafiles().add(createDatafile(null, "dfname1"));
		ds.getDatafiles().add(createDatafile(null, "dfname2"));
		ds.setId((Long) BeanManager.create("P1", ds, em).getPk());
		ds = (Dataset) BeanManager.get("P1", "Dataset", ds.getId(), em).getBean();
		return ds;
	}

	/**
	 * Test of updateInvestigator method, of class InvestigatorManager.
	 */
	@Test
	public void testUpdateDataset() throws Exception {

		int nDatasets = BeanManager.search("P1", "Dataset", em).getList().size();
		int nDatafiles = BeanManager.search("P1", "Datafile", em).getList().size();
		Dataset ds = makeDs();
		assertEquals("Size", nDatasets + 1, BeanManager.search("P1", "Dataset", em).getList().size());
		assertEquals("Size", nDatafiles + 2, BeanManager.search("P1", "Datafile", em).getList().size());

		assertEquals("dsname", ds.getName());
		assertEquals("dfname1", ds.getDatafiles().iterator().next().getName());
		assertEquals("createId", "P1", ds.getCreateId());
		assertEquals("modId", "P1", ds.getModId());

		Long dsid = ds.getId();
		ds = createDataset(inv, "dsname", dst);
		ds.setId(dsid);
		ds.setLocation("guess");

		BeanManager.update("P2", ds, em);
		ds = (Dataset) BeanManager.get("P2", "Dataset", ds.getId(), em).getBean();

		assertEquals("Size", nDatasets + 1, BeanManager.search("P1", "Dataset", em).getList().size());
		assertEquals("Size", nDatafiles + 2, BeanManager.search("P1", "Datafile", em).getList().size());

		assertEquals("guess", ds.getLocation());

		assertEquals("dsname", ds.getName());
		assertEquals("dfname1", ds.getDatafiles().iterator().next().getName());
		assertEquals("createId", "P1", ds.getCreateId());
		assertEquals("modId", "P2", ds.getModId());
	}

	@Test
	public void testDeleteDataset() throws Exception {
		int nDatasets = BeanManager.search("P1", "Dataset", em).getList().size();
		int nDatafiles = BeanManager.search("P1", "Datafile", em).getList().size();
		Dataset ds = makeDs();
		Long dsid = ds.getId();

		ds = createDataset(inv, "dsname", dst);
		ds.setId(dsid);

		BeanManager.delete("P2", ds, em);
		assertEquals("Size", nDatasets, BeanManager.search("P1", "Dataset", em).getList().size());
		assertEquals("Size", nDatafiles, BeanManager.search("P1", "Datafile", em).getList().size());
	}

	@Test(expected = InsufficientPrivilegesException.class)
	public void testDeleteBadUser() throws Exception {
		Dataset ds = makeDs();
		Long dsid = ds.getId();

		ds = createDataset(inv, "dsname", dst);
		ds.setId(dsid);

		BeanManager.delete("P3", ds, em);
	}

	@Test(expected = NoSuchObjectFoundException.class)
	public void testUpdateNotExists() throws Exception {
		Dataset ds = makeDs();

		ds = createDataset(inv, "dsname", dst);
		ds.setId(890325842309L);

		BeanManager.delete("P1", ds, em);
	}

}