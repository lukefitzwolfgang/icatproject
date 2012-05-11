
package uk.icat3.sessionbeans.search;

import java.util.Collection;
import javax.ejb.Local;
import uk.icat3.entity.IcatRole;
import uk.icat3.entity.Instrument;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.InvestigationType;
import uk.icat3.entity.Parameter;
import uk.icat3.exceptions.SessionException;
import uk.icat3.search.AdvancedSearchDetails;
import uk.icat3.util.InvestigationInclude;
import uk.icat3.util.LogicalOperator;


/**
 * This is the business interface for InvestigationSearch enterprise bean.
 */
@Local
public interface InvestigationSearchLocal {
    
    public Collection<Investigation> searchByAdvanced(String sessionId, AdvancedSearchDetails advancedSearch) throws SessionException ;
    
    public Collection<Investigation> searchByAdvanced(String sessionId, AdvancedSearchDetails advancedSearch, int startIndex, int numberOfResults) throws SessionException ;
        
    public Collection<Investigation> searchByKeywords(String sessionId, Collection<String> keywords, InvestigationInclude include, boolean fuzzy) throws SessionException ;
    
    public Collection<Investigation> searchByKeywords(String sessionId, Collection<String> keywords) throws SessionException ;
    
    public Collection<Investigation> searchByKeywords(String sessionId, Collection<String> keywords, int startIndex, int numberOfResults) throws SessionException ;
    
    public Collection<Investigation> searchByKeywords(String sessionId, Collection<String> keywords, InvestigationInclude include, boolean fuzzy, int startIndex, int numberOfResults) throws SessionException ;
    
    public Collection<Investigation> searchByKeywords(String sessionId, Collection<String> keywords, InvestigationInclude include,  int startIndex, int numberOfResults) throws SessionException ;
    
    public Collection<Investigation> searchByKeywords(String sessionId, Collection<String> keywords, LogicalOperator operator, InvestigationInclude include, boolean fuzzy, int startIndex, int numberOfResults) throws SessionException ;
    
    public Collection<Investigation> getMyInvestigations(String sessionId) throws SessionException ;
    
    public Collection<Investigation> getMyInvestigations(String sessionId, InvestigationInclude include) throws SessionException ;
    
    public Collection<Investigation> getMyInvestigations(String sessionId, InvestigationInclude include, int startIndex, int number_results) throws SessionException ;
    
    public Collection<Investigation> searchByUserID(String sessionId, String userSearch) throws SessionException ;
    
    public Collection<Investigation> searchByUserID(String sessionId, String userSearch, int startIndex, int number_results) throws SessionException ;
    
    public Collection<Investigation> searchByUserSurname(String sessionId, String surname) throws SessionException ;
    
    public Collection<Investigation> searchByUserSurname(String sessionId, String surname, int startIndex, int number_results) throws SessionException ;
    
    public Collection<String> listInstruments(String sessionId) throws SessionException ;
    
    public Collection<IcatRole> listRoles(String sessionId) throws SessionException ;
    
    public Collection<Parameter> listParameters(String sessionId) throws SessionException ;
    
    public Collection<String> listInvestigationTypes(String sessionId) throws SessionException ;
        
    }