/*
 * TestSearch.java
 *
 * Created on 20 February 2007, 12:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.icat3.test;

import java.util.Collection;
import java.util.Random;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.DatafileFormat;
import uk.icat3.entity.DatafileFormatPK;
import uk.icat3.entity.DatafileParameter;
import uk.icat3.entity.Dataset;
import uk.icat3.entity.DatasetType;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.InvestigationType;
import uk.icat3.manager.DataFileManager;
import uk.icat3.manager.DataSetManager;
import uk.icat3.manager.InvestigationManager;
import uk.icat3.util.ElementType;
import uk.icat3.util.Queries;

/**
 *
 * @author gjd37
 */
public class TestJPA {
    
    protected  static Logger log = Logger.getLogger(TestSearch.class);
    
    // TODO code application logic here
    static EntityManagerFactory  emf = null;
    // Create new EntityManager
    static EntityManager  em = null;
    
    /** Creates a new instance of TestSearch */
    public TestJPA() {
    }
    
    protected static void setUp(){
        emf = Persistence.createEntityManagerFactory("icat3-scratch-testing-PU");
        //emf = Persistence.createEntityManagerFactory("icat3-dls_dev_new");
        em = emf.createEntityManager();
        // Begin transaction
        em.getTransaction().begin();
        
    }
    
    protected static void tearDown(){
        // Commit the transaction
        em.getTransaction().commit();
        
        em.close();
    }
    
    
    public void createInv() throws Exception{
        setUp();
        
        Investigation investigation = new Investigation();
        
        investigation.setTitle("investigation "+new Random().nextInt());
        investigation.setInvNumber(""+new Random().nextInt());
        investigation.setInvType(new InvestigationType("experiment"));
        
        InvestigationManager.createInvestigation("test_admin_investigation", investigation, em);
        
        tearDown();
    }
    
    public void createDS() throws Exception{
        setUp();
        
        Dataset ds = new Dataset();
        DatasetType type = new DatasetType();
        type.setName("analyzed");
        type.setDescription("Analyzed data");
        ds.setDatasetType(type);
        ds.setName("unit test create data set");
        
        DataSetManager.createDataSet("test_admin_investigation", ds, 100L, em);
        
        tearDown();
    }
    
    public void createDF() throws Exception{
        setUp();
        
        Datafile df = new Datafile();
        DatafileFormat type = new DatafileFormat();
        DatafileFormatPK pk = new DatafileFormatPK("3.0.0", "nexus");
        type.setDatafileFormatPK(pk);
        
        df.setDatafileFormat(type);
        df.setName("name of df");
        
        DataFileManager.createDataFile("test_admin_investigation", df, 100L, em);
        
        tearDown();
    }
    
    public void changeRole() throws Exception {
        setUp();
        
        InvestigationManager.updateAuthorisation("test_admin_investigation", "CREATOR", 103L, em);
        
        tearDown();
    }
    
    public void testJPA() throws Exception {
        setUp();
        
        long time = System.currentTimeMillis();
        String QUERY = Queries.LIST_ALL_USERS_INVESTIGATIONS_JPQL + " AND (i.keywordCollection.keywordPK.name LIKE '%ccw%' OR i.keywordCollection.keywordPK.name LIKE '%orbita%') AND i.keywordCollection.markedDeleted = 'N'";
        
        QUERY = "SELECT i FROM Datafile i, IcatAuthorisation ia WHERE i.id = ia.elementId AND ia.elementType = :dataFileType AND i.markedDeleted = 'N' " +
                " AND (ia.userId = :userId OR ia.userId = 'ANY')" +
                " AND ia.markedDeleted = 'N' AND ia.role.actionSelect = 'Y' AND "+
                " i.datafileParameterCollection.markedDeleted = 'N' AND i.datafileParameterCollection.datafileParameterPK.name = 'run_number' AND" +
                " i.datafileParameterCollection.numericValue BETWEEN :lower AND :upper AND i.dataset.investigation.instrument.name = 'SXD'";
        
        
        Query nullQuery = em.createQuery(QUERY);
        
        //  QUERY = "SELECT DISTINCT t0.ID, t0.GRANT_ID, t0.MOD_TIME, t0.RELEASE_DATE, t0.CREATE_ID, t0.TITLE, t0.MOD_ID, t0.INV_ABSTRACT, t0.PREV_INV_NUMBER, t0.VISIT_ID, t0.BCAT_INV_STR, t0.INV_NUMBER, t0.CREATE_TIME, t0.FACILITY_ACQUIRED, t0.DELETED, t0.FACILITY_CYCLE, t0.INSTRUMENT, t0.INV_TYPE, t0.FACILITY FROM KEYWORD t5, KEYWORD t4, KEYWORD t3, ICAT_ROLE t2, ICAT_AUTHORISATION t1, INVESTIGATION t0 WHERE (((((((((t0.ID = t1.ELEMENT_ID) AND (t1.ELEMENT_TYPE = 'INVESTIGATION')) AND (t0.DELETED = 'N')) AND ((t1.USER_ID = ?userId) OR (t1.USER_ID = 'ANY'))) AND (t1.DELETED = 'N')) AND (t2.ACTION_SELECT = 'Y')) AND ((t3.NAME LIKE '%ccw%') OR (t4.NAME LIKE '%shu%'))) AND (t5.DELETED = 'N')) AND ((((t2.ROLE = t1.ROLE) AND (t3.INVESTIGATION_ID = t0.ID)) AND (t4.INVESTIGATION_ID = t0.ID)) AND (t5.INVESTIGATION_ID = t0.ID)))";
        
        // Query nullQuery = em.createNativeQuery(QUERY);
        
        
        //try and find user with null as investigation
        //nullQuery.setParameter("keyword1", "%shull%");
        //nullQuery.setParameter("keyword2", "%ral%");
        //  nullQuery.setParameter("keyword3", "%ccwi%");
        nullQuery.setParameter("upper", 1250f);
        nullQuery.setParameter("lower", 100f);
        nullQuery.setParameter("userId", "test");
        nullQuery.setParameter("dataFileType", ElementType.DATAFILE);
        
        //  nullQuery.setParameter("parentElementType", ElementType.DATASET);
        // nullQuery.setParameter("parentElementId", null);
        
        
        Collection<Datafile> dfs = nullQuery.getResultList();
        for (Datafile object : dfs) {
            for(DatafileParameter dfp : object.getDatafileParameterCollection()){
               if(dfp.getDatafileParameterPK().getName().equals("run_number")) System.out.println(dfp.getNumericValue());
            }
        }

        
        System.out.println((System.currentTimeMillis() - time)/6000f+" seconds");
        //System.out.println(em.createQuery("SELECT i FROM IcatAuthorisation i WHERE i.elementType = :type1  AND (i.parentElementType = :type OR :type IS NULL)").setParameter("type1", ElementType.INVESTIGATION).setParameter("type", null).getResultList());
        tearDown();
    }
    
    public void getRoles() throws Exception {
        setUp();
        
        System.out.println(InvestigationManager.getAuthorisations("test_admin_investigation", 100L, em));
        
        tearDown();
    }
    
    public void addRole() throws Exception {
        setUp();
        
        System.out.println(DataFileManager.addAuthorisation("test_admin_investigation","addedDatafileUser", "CREATOR", 100L, em));
        
        tearDown();
    }
    
      public void testP() throws Exception {
        setUp();
        
          String QUERY = Queries.LIST_ALL_USERS_INVESTIGATIONS_JPQL + " AND (i.keywordCollection.keywordPK.name LIKE '%ccw%' OR i.keywordCollection.keywordPK.name LIKE '%orbita%') AND i.keywordCollection.markedDeleted = 'N'";
        
        QUERY = "SELECT i FROM Datafile i WHERE i.datafileParameterCollection.numericValue BETWEEN :lower AND :upper";
        
        
        Query nullQuery = em.createQuery(QUERY);
              
        nullQuery.setParameter("upper", 1250f);
        nullQuery.setParameter("lower", 100f);
        
        
        Collection<Datafile> dfs = nullQuery.getResultList();
        for (Datafile object : dfs) {
            for(DatafileParameter dfp : object.getDatafileParameterCollection()){
               if(dfp.getDatafileParameterPK().getName().equals("run_number")) System.out.println(object+ " " +dfp.getNumericValue());
               
            }
        } 
        tearDown();
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        
        TestJPA ts = new TestJPA();
        
        // ts.createDF();
        // ts.createDS();
        //ts.addRole();
        // ts.getRoles();
        //  ts.createInv();
        //ts.testJPA();
        //  ts.changeRole();
        
        ts.testP();
    }
    
    
    
}
