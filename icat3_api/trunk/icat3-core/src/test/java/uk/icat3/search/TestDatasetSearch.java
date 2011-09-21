/*
 * TestInvalidUser.java
 *
 * Created on 07 March 2007, 12:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.icat3.search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static uk.icat3.util.TestConstants.ICAT_ADMIN_USER;
import static uk.icat3.util.TestConstants.INVALID_USER;
import static uk.icat3.util.TestConstants.VALID_DATASET_NAME;
import static uk.icat3.util.TestConstants.VALID_DATA_SET_ID;
import static uk.icat3.util.TestConstants.VALID_SAMPLE_ID_FOR_INVESTIGATION_ID;
import static uk.icat3.util.TestConstants.VALID_SAMPLE_NAME;
import static uk.icat3.util.TestConstants.VALID_USER_FOR_INVESTIGATION;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.icat3.entity.Dataset;
import uk.icat3.entity.Sample;
import uk.icat3.exceptions.ICATAPIException;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.manager.ManagerUtil;
import uk.icat3.manager.RuleManager;
import uk.icat3.util.BaseClassTransaction;

/**
 * 
 * @author gjd37
 */
public class TestDatasetSearch extends BaseClassTransaction {

	@BeforeClass
	public static void authz() throws Exception {
		RuleManager.addUserGroupMember(VALID_USER_FOR_INVESTIGATION, VALID_USER_FOR_INVESTIGATION, em);
		RuleManager.addUserGroupMember(VALID_USER_FOR_INVESTIGATION, ICAT_ADMIN_USER, em);
		RuleManager.addRule(VALID_USER_FOR_INVESTIGATION, "Dataset", "CRUD", null, em);
		RuleManager.addRule(VALID_USER_FOR_INVESTIGATION, "Sample", "CRUD", null, em);
	}

	private static Logger log = Logger.getLogger(TestDatasetSearch.class);

	/**
	 * Tests dataset getSamplesBySampleName
	 */
	@Test
	public void testgetSamplesBySampleName() {
		log.info("Testing valid user for get sample by sample name: " + VALID_USER_FOR_INVESTIGATION);

		Collection<Sample> samples = DatasetSearch.getSamplesBySampleName(VALID_USER_FOR_INVESTIGATION,
				VALID_SAMPLE_NAME, em);

		// TODO dynamic find this, at the moment its one dataset
		assertNotNull("Must not be an null collection of samples ", samples);
		assertEquals("Number of samples searched for " + VALID_SAMPLE_NAME, 0, samples.size());
		// assertEquals("Sample id should be 3",samples.iterator().next().getId(),
		// VALID_SAMPLE_ID_FOR_INVESTIGATION_ID);
	}

	/**
	 * Tests dataset getSamplesBySampleName
	 */
	@Test
	public void testgetSamplesBySampleNameInvalidUser() {
		log.info("Testing invalid user for get sample by sample name: " + INVALID_USER);

		Collection<Sample> samples = DatasetSearch.getSamplesBySampleName(INVALID_USER, VALID_SAMPLE_NAME, em);

		// TODO dynamic find this, at the moment its one dataset
		assertNotNull("Must not be an null collection of samples ", samples);
		assertEquals("Number of samples searched for " + VALID_SAMPLE_NAME + " should be 0", 0, samples.size());

	}

	/**
	 * Tests dataset getDatasetsBySample
	 */
	@Test (expected = NoSuchObjectFoundException.class)
	public void testgetDatasetsBySample() throws ICATAPIException {
		log.info("Testing valid user for get datasets by sample : " + VALID_USER_FOR_INVESTIGATION);

		Sample sample = ManagerUtil.find(Sample.class, VALID_SAMPLE_ID_FOR_INVESTIGATION_ID, em);

		Collection<Dataset> datasets = DatasetSearch.getDatasetsBySample(VALID_USER_FOR_INVESTIGATION, sample, em);

		// TODO dynamic find this, at the moment its one dataset
		assertNotNull("Must not be an null collection of Datasets ", datasets);
		assertEquals("Number of Datasets", 3, datasets.size());
		assertEquals("Datasets id should be 2", datasets.iterator().next().getId(), VALID_DATA_SET_ID);
	}

	/**
	 * Tests dataset getDatasetsBySample
	 */
	@Test (expected = NoSuchObjectFoundException.class)
	public void testgetDatasetsBySampleInvalidUser() throws ICATAPIException {
		log.info("Testing invalid user for get datasets by sample : " + INVALID_USER);

		Sample sample = ManagerUtil.find(Sample.class, VALID_SAMPLE_ID_FOR_INVESTIGATION_ID, em);

		 DatasetSearch.getDatasetsBySample(INVALID_USER, sample, em);

	}

	/**
	 * Tests dataset getSamplesBySampleName
	 */
	@Test
	public void testgetDatasetsByName() {
		log.info("Testing valid user for get Datasets by dataset name: " + VALID_USER_FOR_INVESTIGATION);

		Collection<Dataset> datasets = DatasetSearch.getDatasetsByName(VALID_USER_FOR_INVESTIGATION,
				VALID_DATASET_NAME, em);

		assertNotNull("Must not be an null collection of datasets ", datasets);
		assertEquals("Number of datasets searched for " + VALID_DATASET_NAME , 0, datasets.size());
	}

	/**
	 * Tests dataset getSamplesBySampleName
	 */
	@Test
	public void testgetDatasetsByNameInvalidUser() {
		log.info("Testing invalid user for get Datasets by dataset name: " + INVALID_USER);

		Collection<Dataset> datasets = DatasetSearch.getDatasetsByName(INVALID_USER, VALID_DATASET_NAME, em);

		assertNotNull("Must not be an null collection of datasets ", datasets);
		assertEquals("Number of datasets searched for " + VALID_DATASET_NAME + " should be 0", 0, datasets.size());

	}

}
