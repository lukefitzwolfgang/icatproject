/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.icat3.sessionbeans.manager;

import javax.ejb.Local;
import uk.icat3.entity.DatafileDoi;
import uk.icat3.entity.DatasetDoi;
import uk.icat3.entity.Doi;
import uk.icat3.entity.InvestigationDoi;
import uk.icat3.exceptions.DoiNotFoundException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;

/**
 * This is local interface for the Digital Object Identifier (DOI) Manager Services.
 * @author Mr. Srikanth Nagella
 */
@Local
public interface DoiManagerLocal {
    InvestigationDoi createInvestigationDoi(Long investigationId, String doi, String doiServerName) throws NoSuchObjectFoundException, ValidationException;
    DatasetDoi createDatasetDoi(Long datasetId, String doi, String doiServerName) throws NoSuchObjectFoundException, ValidationException;
    DatafileDoi createDatafileDoi(Long datafileId, String doi, String doiServerName) throws NoSuchObjectFoundException, ValidationException;
    boolean updateDoi(Doi doiInput) throws DoiNotFoundException, ValidationException;
    Doi getDoi(String doi,String serverName) throws DoiNotFoundException;
}
