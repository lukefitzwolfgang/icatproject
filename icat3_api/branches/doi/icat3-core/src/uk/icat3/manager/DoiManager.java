/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.icat3.manager;

import java.util.logging.Level;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import org.apache.log4j.Logger;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.DatafileDoi;
import uk.icat3.entity.Dataset;
import uk.icat3.entity.DatasetDoi;
import uk.icat3.entity.Doi;
import uk.icat3.entity.DoiServer;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.InvestigationDoi;
import uk.icat3.exceptions.DoiNotFoundException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;

/**
 * This class is implementation of the Manager methods for Digital Object Identifier
 * (DOI). The manager methods include, Adding, Updating, retrieving DOI objects for Datasets,
 * Datafiles & Investigations.
 * NOTE: Only the administrators can do the above operations.
 * @author Mr. Srikanth Nagella
 */
public class DoiManager extends ManagerUtil {
    // Global class logger

    static final Logger log = Logger.getLogger(DoiManager.class);

    /**
     * This method creates a Investigation Doi takes in investigation id, doi name
     * doi server name.
     * @param investigationId
     * @param doi doi name
     * @param doiServerName doi server name
     * @param manager
     * @return The created InvestigationDoi object. it no match for investigation
     * or doi server occurs then this method throws NoObjectFoundException exception.
     * @throws NoSuchObjectFoundException
     */
    public static InvestigationDoi createInvestigationDoi(Long investigationId, String doi, String doiServerName, EntityManager manager) throws NoSuchObjectFoundException, ValidationException {
        try {
            //check investigation
            Investigation investigation = find(Investigation.class, investigationId, manager);
            //get doi Server
            DoiServer doiServer = (DoiServer) manager.createNamedQuery("DoiServer.findByServerName").setParameter("serverName", doiServerName).getSingleResult();
            try {
                //Check if the doi already exists
                getDoi(doi, doiServerName, manager);
                throw new ValidationException("DOI Already exists");
            } catch (DoiNotFoundException ex) {
            }
            //create Investigation Doi
            InvestigationDoi invDoi = new InvestigationDoi();
            invDoi.setInvestigation(investigation);
            invDoi.setDoiServer(doiServer);
            invDoi.setName(doi);
            manager.persist(invDoi);
            return invDoi;
        } catch (NoResultException ex){
            throw new NoSuchObjectFoundException("Server not found");
        }
    }

    /**
     * This method creates DatasetDoi using the input dataset id, doi name and
     * doi server.
     * @param datasetId
     * @param doi name of doi
     * @param doiServerName doi server name
     * @param manager
     * @return created DatasetDoi. if dataset id or doi server is not found then
     * throws exception.
     * @throws NoSuchObjectFoundException
     */
    public static DatasetDoi createDatasetDoi(Long datasetId, String doi, String doiServerName, EntityManager manager) throws NoSuchObjectFoundException, ValidationException{
        try {
            //check investigation
            Dataset dataset = find(Dataset.class, datasetId, manager);
            //get doi Server
            DoiServer doiServer = (DoiServer) manager.createNamedQuery("DoiServer.findByServerName").setParameter("serverName", doiServerName).getSingleResult();
            try {
                //Check if the doi already exists
                getDoi(doi, doiServerName, manager);
                throw new ValidationException("DOI Already exists");
            } catch (DoiNotFoundException ex) {
            }
            //create Investigation Doi
            DatasetDoi datasetDoi = new DatasetDoi();
            datasetDoi.setDataset(dataset);
            datasetDoi.setDoiServer(doiServer);
            datasetDoi.setName(doi);
            manager.persist(datasetDoi);
            return datasetDoi;
        }  catch (NoResultException ex){
            throw new NoSuchObjectFoundException("Server not found");
        }
    }

    /**
     * This method creates Datafile Doi using datafile id, doi name and doi server
     * name.
     * @param datafileId
     * @param doi
     * @param doiServerName
     * @param manager
     * @return created DatafileDoi. if matching datafile or doi server is not found
     * then throws exception.
     * @throws NoSuchObjectFoundException
     */
    public static DatafileDoi createDatafileDoi(Long datafileId, String doi, String doiServerName, EntityManager manager) throws NoSuchObjectFoundException, ValidationException {
        try {
            //check investigation
            Datafile datafile = find(Datafile.class, datafileId, manager);
            //get doi Server
            DoiServer doiServer = (DoiServer) manager.createNamedQuery("DoiServer.findByServerName").setParameter("serverName", doiServerName).getSingleResult();
            try {
                //Check if the doi already exists
                getDoi(doi, doiServerName, manager);
                throw new ValidationException("DOI Already exists");
            } catch (DoiNotFoundException ex) {
            }
            //create Investigation Doi
            DatafileDoi datafileDoi = new DatafileDoi();
            datafileDoi.setDatafile(datafile);
            datafileDoi.setDoiServer(doiServer);
            datafileDoi.setName(doi);
            manager.persist(datafileDoi);
            return datafileDoi;
         } catch (NoResultException ex){
            throw new NoSuchObjectFoundException("Server not found");
        }
    }

    /**
     * This method returns the matching InvestigationDoi object.
     * @param doi
     * @param doiServerName
     * @param manager
     * @return matching InvestigationDoi. throws an exception if there are no matches.
     * @throws DoiNotFoundException
     */
    public static InvestigationDoi getInvestigationDoi(String doi, String doiServerName, EntityManager manager) throws DoiNotFoundException {
        try {
            //Get the InvestigationDoi
            InvestigationDoi investigationDoi = (InvestigationDoi) getDoi(doi, doiServerName, manager);
            return investigationDoi;
        } catch (ClassCastException ex) {
            throw new DoiNotFoundException("The found Doi doesn't match to the requested type");
        }
    }

    /**
     * This method returns the matching DatasetDoi object
     * @param doi
     * @param doiServerName
     * @param manager
     * @return matching DatasetDoi. throws a exception if there are no matches
     * @throws DoiNotFoundException
     */
    public static DatasetDoi getDatasetDoi(String doi, String doiServerName, EntityManager manager) throws DoiNotFoundException {
        try {
            //Get the DatasetDoi
            DatasetDoi datasetDoi = (DatasetDoi) getDoi(doi, doiServerName, manager);
            return datasetDoi;
        } catch (ClassCastException ex) {
            throw new DoiNotFoundException("The found Doi doesn't match to the requested type");
        }
    }

    /**
     * This method returns the matching DatafileDoi object
     * @param doi
     * @param doiServerName
     * @param manager
     * @return matching DatafileDoi object. throws a excpetion if there are no matches.
     * @throws DoiNotFoundException
     */
    public static DatafileDoi getDatafileDoi(String doi, String doiServerName, EntityManager manager) throws DoiNotFoundException {
        try {
            //Get the DatafileDoi
            DatafileDoi datafileDoi = (DatafileDoi) getDoi(doi, doiServerName, manager);
            return datafileDoi;
        } catch (ClassCastException ex) {
            throw new DoiNotFoundException("The found Doi doesn't match to the requested type");
        }
    }

    /**
     * Its a generic method to return Doi. Need to be casted based on the type of Doi
     * @param doi
     * @param doiServerName
     * @param manager
     * @return Doi object. throws exception if there are no matches.
     * @throws DoiNotFoundException
     */
    public static Doi getDoi(String doi, String doiServerName, EntityManager manager) throws DoiNotFoundException {
        Doi result = null;
        try {
            result = (Doi) manager.createNamedQuery("Doi.findByNameAndServer").setParameter("name", doi).setParameter("serverName", doiServerName).getSingleResult();
        } catch (NoResultException ex) {
            throw new DoiNotFoundException("Provided Doi Name and/or Doi Server exists");
        }
        return result;
    }

    /**
     * This method will update the doi information. This will not change the name of doi
     * and doi server information.
     * @param doiInput
     * @param manager
     * @return whether successful operation or throw an doi not found exception.
     * @throws DoiNotFoundException
     */
    public static boolean updateDoi(Doi doiInput, EntityManager manager) throws DoiNotFoundException, ValidationException{
        Doi doiFound = getDoi(doiInput.getName(), doiInput.getDoiServer().getServerName(),manager);
        doiInput.setDoiServer(doiFound.getDoiServer());
        doiInput.setId(doiFound.getId());
        //Check if doiFound is valid
        doiInput.isValid(manager);
        manager.merge(doiInput);
        return true;
    }
}
