
package uk.icat3.sessionbeans.search;

import java.util.Collection;
import javax.ejb.Local;
import uk.icat3.exceptions.SessionException;
import uk.icat3.util.KeywordType;

/**
 * This is the business interface for KeywordSearch enterprise bean.
 */
@Local
public interface KeywordSearchLocal {
    
    public Collection<String> getKeywordsForUser(String sessionId) throws SessionException;
    
    public Collection<String> getKeywordsForUser(String sessionId, int numberReturned) throws SessionException;
    
    public Collection<String> getKeywordsForUser(String sessionId, KeywordType type) throws SessionException;
    
    public Collection<String> getKeywordsForUser(String sessionId, String startKeyword, int numberReturned) throws SessionException;
    
    public Collection<String> getAllKeywords(String sessionId, KeywordType type) throws SessionException;
}
