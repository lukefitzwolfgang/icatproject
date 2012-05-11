
package uk.icat3.sessionbeans.manager;

import java.util.Collection;
import javax.ejb.Local;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.DatafileParameter;
import uk.icat3.entity.DatafileParameterPK;
import uk.icat3.entity.DatasetParameterPK;
import uk.icat3.entity.IcatAuthorisation;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.SessionException;
import uk.icat3.exceptions.ValidationException;

/**
 * This is the business interface for DatasetManager enterprise bean.
 */
@Local
public interface DatafileManagerLocal {
    
    
    public Datafile getDatafile(String sessionId, Long datafileId)  throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public Collection<Datafile> getDatafiles(String sessionId, Collection<Long> datafileIds)  throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public Datafile createDataFile(String sessionId, Datafile dataFile, Long datasetId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException ;
    
    public Collection<Datafile> createDataFiles(String sessionId, Collection<Datafile> dataFiles, Long datasetId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException ;
    
    public void modifyDataFile(String sessionId, Datafile dataFile) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException ;
   
    public void deleteDataFile(String sessionId, Long datafileId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public void deleteDataFile(String sessionId, Datafile dataFile) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public void removeDataFile(String sessionId, Long datafileId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public void removeDataFile(String sessionId, Datafile dataFile) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
        
    public DatafileParameter addDataFileParameter(String sessionId, DatafileParameter dataFileParameter, Long datafileId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException ;
    
    public Collection<DatafileParameter> addDataFileParameters(String sessionId, Collection<DatafileParameter> dataFileParameters, Long datafileId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException;
   
    public void modifyDataFileParameter(String sessionId, DatafileParameter dataFileParameter) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException ;
    
    public void removeDataFileParameter(String sessionId, DatafileParameterPK datafileParameterPK) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public void deleteDataFileParameter(String sessionId, DatafileParameterPK datafileParameterPK) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
       //auth bit
    public Collection<IcatAuthorisation> getAuthorisations(String sessionId, Long datafileId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public IcatAuthorisation addAuthorisation(String sessionId, String toAddUserId, String toAddRole, Long datafileId) throws SessionException, NoSuchObjectFoundException, InsufficientPrivilegesException, ValidationException;
    
    public void deleteAuthorisation(String sessionId, Long authorisationId) throws SessionException, NoSuchObjectFoundException, InsufficientPrivilegesException;
    
    public void removeAuthorisation(String sessionId, Long authorisationId) throws SessionException, NoSuchObjectFoundException, InsufficientPrivilegesException;
    
    public void updateAuthorisation(String sessionId, String toChangetoRole, Long authorisationId) throws SessionException, NoSuchObjectFoundException, InsufficientPrivilegesException, ValidationException;

}
