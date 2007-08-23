/*
 * SearchKeyword.java
 *
 * Created on 15-Aug-2007, 12:57:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package icat3wstest;

import client.Datafile;
import client.DatafileParameter;
import client.Dataset;
import client.DatasetParameter;
import client.Dataset;
import client.DatasetInclude;
import client.DatasetParameterPK;
import client.DatasetType;
import java.util.ArrayList;
import java.util.List;
import static icat3wstest.Constants.*;

/**
 *
 * @author gjd37
 */
public class DatasetManager {
    
    /** Creates a new instance of SearchKeyword */
    public static void getDataset(String sid, Long id) throws Exception {
        
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            Dataset dataset = port.getDatasetIncludes(sid, id, DatasetInclude.DATASET_FILES_AND_PARAMETERS);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("  ID: "+dataset.getId()+", TITLE: "+dataset.getName());
            System.out.println("     Datasets: "+dataset.getDatafileCollection().size());
            System.out.println("     -------------");
            for (Datafile datafile: dataset.getDatafileCollection()) {
                System.out.println("       "+datafile.getName());
                System.out.println("            Dataset parameters: "+datafile.getDatafileParameterCollection().size());
                System.out.println("            ------------------------");
                for (DatafileParameter datafileParameter : datafile.getDatafileParameterCollection()) {
                    System.out.println("               "+datafileParameter.getDatafileParameterPK().getName());
                }
            }
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to getDataset with SID "+sid);
            System.out.println(ex);
            assert false;
            
            // TODO handle custom exceptions here
        }
    }
    
    public static void getDatasets(String sid, Long id) throws Exception {
        
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            long time = System.currentTimeMillis();
            
            List<Long> ids = new ArrayList<Long>();
            ids.add(id);
            //ids.add(id);
            
            // TODO process result here
            List<Dataset> datasets = port.getDatasets(sid, ids);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Number of getDatasets is "+datasets.size());
            System.out.println("Results:");
            for (Dataset dataset : datasets) {
                System.out.println("  ID: "+dataset.getId()+", TITLE: "+dataset.getName());
                
            }
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to getDatasets with SID "+sid);
            System.out.println(ex);
            assert false;
            
            // TODO handle custom exceptions here
        }
    }
    
    
    public static Dataset createDataset(String sid, String name) throws Exception {
        
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            long time = System.currentTimeMillis();
            
            Dataset ds = new Dataset();
            
            //   Should be done with something like:
            List<DatasetType> types = port.listDatasetTypes(SID);
            ds.setDatasetType(types.iterator().next());
            
            ds.setName(name);
            
            Dataset result = port.createDataSet(sid, ds, INVESTIGATION_ID);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Results: createDataset: ID: "+result.getId());
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
            return result;
        } catch (Exception ex) {
            System.out.println("Unable to createDataset with SID "+sid);
            System.out.println(ex);
            assert false;
            return null;
            
            // TODO handle custom exceptions here
        }
    }
    
    public static void delete_undeleteDataset(String sid, Long id){
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            port.deleteDataSet(sid, id);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Result: delete_undeleteDataset");
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to delete_undeleteDataset with SID "+sid);
            System.out.println(ex);
            assert false;
        }
    }
    
    public static void removeDataset(String sid, Long id){
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            port.removeDataSet(sid, id);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Result: removeDataset");
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to removeDataset with SID "+sid);
            System.out.println(ex);
            assert false;
        }
    }
    
    public static void updateDataset(String sid, Dataset df, String newName) throws Exception {
        
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            long time = System.currentTimeMillis();
            
            df.setName(newName);
            
            port.modifyDataSet(sid, df);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Results: updateDataset");
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to updateDataset with SID "+sid);
            System.out.println(ex);
            assert false;
            
            // TODO handle custom exceptions here
        }
    }
    
    
    
    public static DatasetParameter addParameter(String sid, String name, String units, Long id){
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            DatasetParameterPK PK = new DatasetParameterPK();
            PK.setDatasetId(id);
            PK.setName(name);
            PK.setUnits(units);
            DatasetParameter dfp = new DatasetParameter();
            dfp.setDatasetParameterPK(PK);
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            DatasetParameter dfpRetured = port.addDataSetParameter(sid, dfp, id);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Result: addDatasetParameter: "+dfpRetured);
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
            return dfpRetured;
        } catch (Exception ex) {
            System.out.println("Unable to addDatasetParameter with SID "+sid);
            System.out.println(ex);
            assert false;
            return null;
            // TODO handle custom exceptions here
        }
    }
    
    public static void delete_undeleteParameter(String sid, String name, String units, Long id){
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            DatasetParameterPK PK = new DatasetParameterPK();
            PK.setDatasetId(id);
            PK.setName(name);
            PK.setUnits(units);
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            port.deleteDataSetParameter(sid, PK);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Result: deleteDatasetParameter");
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to deleteDatasetParameter with SID "+sid);
            System.out.println(ex);
            assert false;
        }
    }
    
    public static void removeDatasetParameter(String sid, String name, String units, Long id){
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            DatasetParameterPK PK = new DatasetParameterPK();
            PK.setDatasetId(id);
            PK.setName(name);
            PK.setUnits(units);
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            port.removeDataSetParameter(sid, PK);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Result: removeDatasetParameter");
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to removeDatasetParameter with SID "+sid);
            System.out.println(ex);
            assert false;
        }
    }
    
    public static void updateDatasetParameter(String sid, DatasetParameter dfp, String newDesc){
        try { // Call Web Service Operation
            client.ICATService service = new client.ICATService();
            client.ICAT port = service.getICATPort();
            // TODO initialize WS operation arguments here
            
            dfp.setDescription(newDesc);
            
            long time = System.currentTimeMillis();
            
            // TODO process result here
            port.modifyDataSetParameter(sid, dfp);
            
            float totalTime = (System.currentTimeMillis() - time)/1000f;
            
            System.out.println("Result: updateDatasetParameter");
            
            System.out.println("\nTime taken: "+totalTime+" seconds");
            System.out.println("--------------------------------------------------\n");
            assert true;
        } catch (Exception ex) {
            System.out.println("Unable to updateDatasetParameter with SID "+sid);
            System.out.println(ex);
            assert false;
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        // getDataset(SID, DATASET_ID);
        // getDatasets(SID, DATASET_ID);
        
      /*  Dataset df = createDataset(SID, "name for sid "+SID);
        updateDataset(SID, df, "new name of "+SID);  //this should fail with ICAT_ADMIN user
        delete_undeleteDataset(SID, df.getId());
        delete_undeleteDataset(SID, df.getId());
        removeDataset(SID, df.getId()); //should be false for none ICAT_ADMIN user*/
        
        DatasetParameter dfp = addParameter(SID, PARAMETER_NAME, PARAMETER_UNITS, DATASET_ID);
        if(dfp != null) {
            updateDatasetParameter(SID, dfp, "new description for dfp");
            delete_undeleteParameter(SID, PARAMETER_NAME, PARAMETER_UNITS, DATASET_ID); //delete it
            delete_undeleteParameter(SID, PARAMETER_NAME, PARAMETER_UNITS, DATASET_ID); //un delete it
            removeDatasetParameter(SID, PARAMETER_NAME, PARAMETER_UNITS, DATASET_ID);  //should be false for none ICAT_ADMIN user
        }        
    }    
}
