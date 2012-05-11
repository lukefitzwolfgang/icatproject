
package uk.icat3.sessionbeans.manager;

import javax.ejb.Local;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.Investigator;
import uk.icat3.entity.InvestigatorPK;
import uk.icat3.entity.Keyword;
import uk.icat3.entity.KeywordPK;
import uk.icat3.entity.Sample;
import uk.icat3.entity.SampleParameter;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.SessionException;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.util.InvestigationInclude;


/**
 * This is the business interface for InvestigationManager enterprise bean.
 */
@Local
public interface InvestigationManagerLocal {
    
    public Investigation getInvestigation(String sessionId, Long investigationId) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public Investigation getInvestigation(String sessionId, Long investigationId, InvestigationInclude includes) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException ;
    
    public void addKeyword(String sessionId, Keyword keyword, Long investigationId) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void removeKeyword(String sessionId, KeywordPK keywordPK) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void deleteKeyword(String sessionId, KeywordPK keywordPK) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void addInvestigator(String sessionId, Investigator investigator, Long investigationId) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void modifyInvestigator(String sessionId, Investigator investigator) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void addSample(String sessionId, Sample sample, Long investigationId) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void removeSample(String sessionId, Long sampleId) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void deleteSample(String sessionId, Long sampleId) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void modifySample(String sessionId, Sample sample) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void removeSampleParameter(String sessionId, SampleParameter sampleParameterPK) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void deleteSampleParameter(String sessionId, SampleParameter sampleParameterPK) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void modifySampleParameter(String sessionId, SampleParameter sampleParameter) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void deleteInvestigator(String sessionId, InvestigatorPK investigatorPK) throws SessionException, ValidationException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
    public void removeInvestigator(String sessionId, InvestigatorPK investigatorPK) throws SessionException, InsufficientPrivilegesException, NoSuchObjectFoundException;
    
}
