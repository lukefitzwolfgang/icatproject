/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.icat3.sessionbeans.manager;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import org.apache.log4j.Logger;
import uk.icat3.entity.DatafileDoi;
import uk.icat3.entity.DatasetDoi;
import uk.icat3.entity.Doi;
import uk.icat3.entity.InvestigationDoi;
import uk.icat3.exceptions.DoiNotFoundException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.manager.DoiManager;
import uk.icat3.sessionbeans.ArgumentValidator;
import uk.icat3.sessionbeans.EJBObject;

/**
 * This is stateless session bean for managing the Dois. It can create, update and
 * retrieve Digital Object Identifier (DOI) for Investigation, Dataset and Datafile.
 * NOTE: This should only be exposed to Admin service
 * @author Mr. Srikanth Nagella
 */
@Stateless
@Interceptors(ArgumentValidator.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class DoiManagerBean extends EJBObject implements DoiManagerLocal {
    static Logger log = Logger.getLogger(DoiManagerBean.class);

    @Override
    public InvestigationDoi createInvestigationDoi(Long investigationId, String doi, String doiServerName) throws NoSuchObjectFoundException, ValidationException {
        log.info("Creating investigation Doi with doi name "+doi+" and doi server name "+doiServerName+" for Investigation with id "+investigationId);
        return DoiManager.createInvestigationDoi(investigationId, doi, doiServerName, manager);
    }

    @Override
    public DatasetDoi createDatasetDoi(Long datasetId, String doi, String doiServerName) throws NoSuchObjectFoundException, ValidationException {
        log.info("Creating Dataset Doi with doi name "+doi+" and doi server name "+doiServerName+" for Dataset with id "+datasetId);
        return DoiManager.createDatasetDoi(datasetId, doi, doiServerName, manager);
    }

    @Override
    public DatafileDoi createDatafileDoi(Long datafileId, String doi, String doiServerName) throws NoSuchObjectFoundException, ValidationException {
        log.info("Creating Datafile Doi with doi name "+doi+" and doi server name "+doiServerName+" for Datafile with id "+datafileId);
        return DoiManager.createDatafileDoi(datafileId, doi, doiServerName, manager);
    }

    @Override
    public boolean updateDoi(Doi doiInput) throws DoiNotFoundException, ValidationException {
        log.info("Updating doi with doi name "+doiInput.getName());
        return DoiManager.updateDoi(doiInput, manager);
    }

    @Override
    public Doi getDoi(String doi, String serverName) throws DoiNotFoundException {
        log.info("Returning the Doi information for doi name "+doi+" and server name "+serverName);
        return DoiManager.getDoi(doi, serverName, manager);
    }
}
