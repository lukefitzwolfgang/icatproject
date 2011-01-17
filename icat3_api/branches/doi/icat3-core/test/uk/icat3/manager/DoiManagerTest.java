/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.icat3.manager;

import java.util.logging.Level;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.DoiNotFoundException;
import uk.icat3.entity.Doi;
import uk.icat3.entity.DatafileDoi;
import uk.icat3.entity.DatasetDoi;
import uk.icat3.entity.DatasetType;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.Dataset;
import uk.icat3.entity.InvestigationType;
import uk.icat3.entity.Facility;
import uk.icat3.entity.InvestigationDoi;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.DoiServer;
import org.apache.log4j.Logger;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.util.BaseTestClassTX;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author icat
 */
public class DoiManagerTest extends BaseTestClassTX {

    private DoiServer server;
    private Facility facility;
    private InvestigationType invType;
    private Investigation investigation;
    private Investigation investigation2;
    private DatasetType datasetType;
    private Dataset dataset;
    private Datafile datafile;
    private static Logger log = Logger.getLogger(DoiManagerTest.class);

    public DoiManagerTest() {
    }

    @Before
    public void setUpManager() {
        //create doi server
        server = new DoiServer();
        server.setServerName("BritishLibrary");
        server.setServerUrl("https://dx.doi.org/");
        em.persist(server);
        //create facility
        facility = new Facility();
        facility.setFacilityShortName("ICAT");
        facility.setFacilityLongName("ICAT");
        facility.setFacilityDescription("Information Catalog");
        facility.setFacilityUrl("http://icatproject.googlecode.com/");
        facility.setDaysUntilRelease(new Long(365));
        facility.setModId("test");
        em.persist(facility);
        //create Investigation Type
        invType = new InvestigationType();
        invType.setName("testexperiment");
        invType.setDescription("Investigation type is experiment");
        invType.setModId("test");
        em.persist(invType);
        em.flush();
        //create investigation
        investigation = new Investigation();
        investigation.setInvNumber("8080");
        investigation.setFacility("ICAT");
        investigation.setTitle("Test Investigation");
        investigation.setInvType("testexperiment");
        investigation.setModId("test");
        em.persist(investigation);
        em.flush();
        //create investigation
        investigation2 = new Investigation();
        investigation2.setInvNumber("8181");
        investigation2.setFacility("ICAT");
        investigation2.setTitle("Test Investigation 2");
        investigation2.setInvType("testexperiment");
        investigation2.setModId("test");
        em.persist(investigation2);
        em.flush();
        //createe dataset type
        datasetType = new DatasetType();
        datasetType.setName("testdatasettype");
        datasetType.setDescription("Test Dataset Type");
        datasetType.setModId("test");
        em.persist(datasetType);
        em.flush();
        //create dataset
        dataset = new Dataset();
        dataset.setInvestigation(investigation);
        dataset.setDescription("Test Dataset");
        dataset.setName("testdataset");
        dataset.setDatasetType("testdatasettype");
        dataset.setModId("test");
        em.persist(dataset);
        //create datafile
        datafile = new Datafile();
        datafile.setDataset(dataset);
        datafile.setName("testdatafile");
        datafile.setModId("test");
        em.persist(datafile);
    }

    @After
    public void tearDownManager() {
        //remove datafile
        em.remove(datafile);
        em.flush();
        //remove dataset
        em.remove(dataset);
        em.flush();
        //remove investigation
        em.remove(investigation);
        em.flush();
        //remove investigation2
        em.remove(investigation2);
        em.flush();
        //remove doi server
        em.remove(server);
        //remove facility
        em.remove(facility);
        //remove investigation type
        em.remove(invType);
        //remove dataset type
        em.remove(datasetType);

    }

    /**
     * Test of createInvestigationDoi method, of class DoiManager.
     */
    @Test
    public void testCreateInvestigationDoi() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createInvestigationDoi");
        InvestigationDoi result = createInvestigationDoi();
        assertNotNull("Failed to create Investigation Doi", result);
        assertEquals("Expected same doi server", server, result.getDoiServer());
        assertEquals("Expected same investigation", investigation, result.getInvestigation());
        em.remove(result);
    }

    /**
     * Test of createInvestigationDoi method, of class DoiManager.
     */
    @Test(expected = NoSuchObjectFoundException.class)
    public void testCreateInvestigationDoiWithInvalidInvestigation() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createInvestigationDoi");
        Long investigationId = investigation.getId()+100L;
        String doi = "10.5286/ISIS.E." + investigationId;
        InvestigationDoi result = DoiManager.createInvestigationDoi(investigationId, doi, server.getServerName(), em);
        assertNull(result);
    }

    /**
     * Test of createInvestigationDoi method with a duplicate, of class DoiManager.
     */
    @Test
    public void testCreateInvestigationDoiDuplicate() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createInvestigationDoi With duplicate");
        InvestigationDoi result = createInvestigationDoi();
        try{
            InvestigationDoi duplicateResult = createInvestigationDoi();
            assertNull("Created duplicate investigation, Excepted to throw validation exception",duplicateResult);
        }catch (ValidationException ex){
        }
        em.remove(result);
    }

    /**
     * Test of createDatasetDoi method, of class DoiManager.
     */
    @Test
    public void testCreateDatasetDoi() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createDatasetDoi");
        DatasetDoi result = createDatasetDoi();
        assertNotNull("Failed to create Dataset Doi", result);
        assertEquals("Expected same doi server", server, result.getDoiServer());
        assertEquals("Expected same dataset", dataset, result.getDataset());
        em.remove(result);
    }

    /**
     * Test of createDatasetDoi method, of class DoiManager.
     */
    @Test(expected = NoSuchObjectFoundException.class)
    public void testCreateDatasetDoiWithInvalidDataset() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createDatasetDoi With Invalid Dataset");
        Long datasetId = dataset.getId()+100L;
        String doi = "10.5286/ISIS.D." + datasetId;
        DatasetDoi result = DoiManager.createDatasetDoi(datasetId, doi, server.getServerName(), em);
        assertNull(result);
    }

    /**
     * Test of createDatasetDoi method with duplicate dataset doi, of class DoiManager.
     */
    @Test
    public void testCreateDatasetDoiWithDuplicate() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createDatasetDoi With duplicate dataset");
        DatasetDoi result = createDatasetDoi();
        try{
            DatasetDoi duplicateResult = createDatasetDoi();
            assertNull("Created duplicate dataset, Excepted to throw validation exception",duplicateResult);
        }catch (ValidationException ex){
        }
        em.remove(result);
    }
    /**
     * Test of createDatasetDoi method, of class DoiManager.
     */
    @Test
    public void testCreateDatafileDoi() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createDatafileDoi");
        DatafileDoi result = createDatafileDoi();
        assertNotNull("Failed to create Datafile Doi", result);
        assertEquals("Expected same doi server", server, result.getDoiServer());
        assertEquals("Expected same datafile", datafile, result.getDatafile());
        em.remove(result);
    }

    /**
     * Test of createDatasetDoi method, of class DoiManager.
     */
    @Test(expected = NoSuchObjectFoundException.class)
    public void testCreateDatafileDoiWithInvalidDatafile() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createDatafileDoi With Invalid Datafile");
        Long datafileId = datafile.getId() + 100L;
        String doi = "10.5286/ISIS.F." + datafileId;
        DatafileDoi result = DoiManager.createDatafileDoi(datafileId, doi, server.getServerName(), em);
        assertNull("Expected null datafiledoi", result);
    }

    /**
     * Test of createDatafileDoi method with duplicate datafile doi, of class DoiManager.
     */
    @Test
    public void testCreateDatafileDoiWithDuplicate() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("createDatafileDoi With duplicate datafile");
        DatafileDoi result = createDatafileDoi();
        try{
            DatafileDoi duplicateResult = createDatafileDoi();
            assertNull("Created duplicate datafile, Excepted to throw validation exception",duplicateResult);
        }catch (ValidationException ex){
        }
        em.remove(result);
    }

    /**
     * Test for retrieving InvestigationDoi
     */
    @Test
    public void testGetInvestigationDoi() throws DoiNotFoundException, NoSuchObjectFoundException, ValidationException {
        System.out.println("getInvestigaitonDoi");
        InvestigationDoi invDoi = createInvestigationDoi();
        InvestigationDoi result = DoiManager.getInvestigationDoi(invDoi.getName(), invDoi.getDoiServer().getServerName(), em);
        assertEquals("Expected same InvestigationDoi", invDoi, result);
        em.remove(invDoi);
    }

    /**
     * Test for retriving InvestigationDoi with Wrong Type
     */
    @Test
    public void testGetInvestigationDoiWithInvalidType() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("getInvestigaitonDoi With Invalid type");
        DatasetDoi invDoi = createDatasetDoi();
        try {
            InvestigationDoi result = DoiManager.getInvestigationDoi(invDoi.getName(), invDoi.getDoiServer().getServerName(), em);
            fail("Should throw a DoiNotFoundException");
        } catch (DoiNotFoundException ex) {
        }
        em.remove(invDoi);
    }

    /**
     * Test for retriving DatasetDoi
     */
    @Test
    public void testGetDatasetDoi() throws DoiNotFoundException, NoSuchObjectFoundException, ValidationException {
        System.out.println("getDatasetDoi");
        DatasetDoi datasetDoi = createDatasetDoi();
        DatasetDoi result = DoiManager.getDatasetDoi(datasetDoi.getName(), datasetDoi.getDoiServer().getServerName(), em);
        assertEquals("Expected same InvestigationDoi", datasetDoi, result);
        em.remove(datasetDoi);
    }

    /**
     * Test for retriving DatasetDoi with Wrong Type
     */
    @Test
    public void testGetDatasetDoiWithInvalidType() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("getDatasetDoi With Invalid type");
        InvestigationDoi invDoi = createInvestigationDoi();
        try {
            DatasetDoi result = DoiManager.getDatasetDoi(invDoi.getName(), invDoi.getDoiServer().getServerName(), em);
            fail("Should throw a DoiNotFoundException");
        } catch (DoiNotFoundException ex) {
        }
        em.remove(invDoi);
    }

    /**
     * Test for retriving DatafileDoi
     */
    @Test
    public void testGetDatafileDoi() throws DoiNotFoundException, NoSuchObjectFoundException, ValidationException {
        System.out.println("getDatafileDoi");
        DatafileDoi datafileDoi = createDatafileDoi();
        DatafileDoi result = DoiManager.getDatafileDoi(datafileDoi.getName(), datafileDoi.getDoiServer().getServerName(), em);
        assertEquals("Expected same InvestigationDoi", datafileDoi, result);
        em.remove(datafileDoi);
    }

    /**
     * Test for retriving DatafileDoi with Wrong Type
     */
    @Test
    public void testGetDatafileDoiWithInvalidType() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("getDatasetDoi With Invalid type");
        InvestigationDoi invDoi = createInvestigationDoi();
        try {
            DatafileDoi result = DoiManager.getDatafileDoi(invDoi.getName(), invDoi.getDoiServer().getServerName(), em);
            fail("Should throw a DoiNotFoundException");
        } catch (DoiNotFoundException ex) {
        }
        em.remove(invDoi);
    }

    /**
     * Test for retriving Generic Doi
     */
    @Test
    public void testGetDoi() throws DoiNotFoundException, NoSuchObjectFoundException, ValidationException {
        System.out.println("getDoi");
        InvestigationDoi invDoi = createInvestigationDoi();
        Doi result = DoiManager.getDoi(invDoi.getName(), invDoi.getDoiServer().getServerName(), em);
        assertEquals("Expected same InvestigationDoi", invDoi, result);
        em.remove(invDoi);
    }

    /**
     * Test for retriving Generic Doi with wrong doi name
     */
    @Test
    public void testGetDoiWrongName() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("getDoi With Wrong Doi Name");
        InvestigationDoi invDoi = createInvestigationDoi();
        try {
            Doi result = DoiManager.getDoi(invDoi.getName() + "Error", invDoi.getDoiServer().getServerName(), em);
            assertNull("Expecting a null Doi", result);
        } catch (DoiNotFoundException ex) {
        }
        em.remove(invDoi);
    }

    /**
     * Test for retriving Generic Doi with wrong doi name
     */
    @Test
    public void testGetDoiWrongServerName() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("getDoi With Wrong Server Name");
        InvestigationDoi invDoi = createInvestigationDoi();
        try {
            Doi result = DoiManager.getDoi(invDoi.getName(), invDoi.getDoiServer().getServerName() + "Error", em);
            assertNull("Expecting a null Doi", result);
        } catch (DoiNotFoundException ex) {
        }
        em.remove(invDoi);
    }

    /**
     * Test of updateDoi method, of class DoiManager.
     */
    @Test
    public void testUpdateDoi() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("updateDoi");
        InvestigationDoi result = createInvestigationDoi();
        InvestigationDoi dupResult = new InvestigationDoi();
        dupResult.setDoiServer(server);
        dupResult.setName(result.getName());
        dupResult.setInvestigation(investigation2);
        try {
            boolean boolResult = DoiManager.updateDoi(dupResult, em);
            assertTrue("Couldn't update the doi",boolResult);
        } catch (DoiNotFoundException ex) {
            fail("Cannot find the Doi");
        }
        em.remove(result);
    }

    /**
     * Test of updateDoi method with non existing investigation, of class DoiManager.
     */
    @Test
    public void testUpdateDoiWithInvalidInvestigation() throws NoSuchObjectFoundException, ValidationException {
        System.out.println("updateDoi with Non existing investigation");
        InvestigationDoi result = createInvestigationDoi();
        //Create a new non existing investigation
        Investigation dupInv = new Investigation();
        InvestigationDoi dupResult = new InvestigationDoi();
        dupResult.setDoiServer(server);
        dupResult.setName(result.getName());
        dupResult.setInvestigation(dupInv);
        try {
            boolean boolResult = DoiManager.updateDoi(dupResult, em);
            assertTrue("Couldn't update the doi",boolResult);
            assertEquals("Same investigaiton expected",dupInv,result.getInvestigation());
        } catch (DoiNotFoundException ex) {
            fail("Cannot find the Doi");
        } catch (ValidationException ex){
        }
        em.remove(result);
    }

    private InvestigationDoi createInvestigationDoi() throws NoSuchObjectFoundException, ValidationException {

        Long investigationId = investigation.getId();
        String doi = "10.5286/ISIS.E." + investigationId;
        InvestigationDoi result = DoiManager.createInvestigationDoi(investigationId, doi, server.getServerName(), em);
        return result;

    }

    private DatasetDoi createDatasetDoi() throws NoSuchObjectFoundException, ValidationException {

        Long datasetId = dataset.getId();
        String doi = "10.5286/ISIS.D." + datasetId;
        DatasetDoi result = DoiManager.createDatasetDoi(datasetId, doi, server.getServerName(), em);
        return result;

    }

    private DatafileDoi createDatafileDoi() throws NoSuchObjectFoundException, ValidationException {
        Long datafileId = datafile.getId();
        String doi = "10.5286/ISIS.F." + datafileId;
        DatafileDoi result = DoiManager.createDatafileDoi(datafileId, doi, server.getServerName(), em);
        return result;
    }

}
