/*
 * KeywordSearch.java
 *
 * Created on 22 February 2007, 08:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package uk.icat3.search;

import static uk.icat3.util.Queries.ALLKEYWORDS;
import static uk.icat3.util.Queries.ALLKEYWORDS_NATIVE_ALPHA;
import static uk.icat3.util.Queries.ALLKEYWORDS_NATIVE_ALPHA_NUMERIC;
import static uk.icat3.util.Queries.KEYWORDS_FOR_USER;
import static uk.icat3.util.Queries.KEYWORDS_FOR_USER_ALPHA;
import static uk.icat3.util.Queries.KEYWORDS_FOR_USER_ALPHA_NUMERIC;

import java.util.Collection;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

import uk.icat3.search.parameter.util.ElementType;
import uk.icat3.util.KeywordType;
/**
 *
 * @author gjd37
 */
public class KeywordSearch {
    
    // Global class logger
    static Logger log = Logger.getLogger(KeywordSearch.class);
    
    /**
     *  This gets all the keywords avaliable for that user, they can only see keywords associated with their
     * investigations or public investigations
     *
     * @param userId federalId of the user.
     * @param manager manager object that will facilitate interaction with underlying database
     * @return list of keywords
     */
    public static Collection<String> getKeywordsForUser(String userId, EntityManager manager){
        return getKeywordsForUser(userId, KeywordType.ALL, null, -1, manager);
    }
    
    /**
     *  This gets all the keywords avaliable for that user, they can only see keywords associated with their
     * investigations or public investigations
     *
     * @param userId federalId of the user.
     * @param type ALL, ALPHA, ALPHA_NUMERIC, {@link KeywordType}
     * @param manager manager object that will facilitate interaction with underlying database
     * @return list of keywords
     */   
    public static Collection<String> getKeywordsForUser(String userId, KeywordType type, EntityManager manager){
        return getKeywordsForUser(userId, type, null, -1, manager);
    }
    
    /**
     *  This gets all the keywords avaliable for that user, beginning with a keyword, they can only see keywords associated with their
     * investigations or public investigations
     *
     * @param userId federalId of the user.
     * @param manager manager object that will facilitate interaction with underlying database
     * @return list of keywords
     */
    public static Collection<String> getKeywordsForUser(String userId, String startKeyword, EntityManager manager){
        return getKeywordsForUser(userId, KeywordType.ALL, startKeyword, -1, manager);
    }
    
    /**
     * This gets all the keywords avaliable for that user, beginning with a keyword, they can only see keywords associated with their
     * investigations or public investigations
     *
     * @param userId federalId of the user.
     * @param startKeyword start keyword to search
     * @param numberReturned number of results returned
     * @param manager manager object that will facilitate interaction with underlying database
     * @return list of keywords
     */
    //TODO should only show keywords of public accessible investigations
    public static Collection<String> getKeywordsForUser(String userId, KeywordType type, String startKeyword, int numberReturned, EntityManager manager){
        log.trace("getKeywordsForUser("+userId+", "+type+", "+startKeyword+", "+numberReturned+", EntityManager)");
        
        if(startKeyword != null) startKeyword = startKeyword+"%";
        //startKeyword = "%";
        
        if(numberReturned < 0){
            //switch on type
            if(type == null || type == KeywordType.ALL){
                return  (Collection<String>)manager.createNamedQuery(KEYWORDS_FOR_USER).
                        setParameter("startKeyword", startKeyword).getResultList();
            } else if(type == KeywordType.ALPHA){
                return (Collection<String>)manager.createNamedQuery(KEYWORDS_FOR_USER_ALPHA).
                        setParameter("startKeyword", startKeyword).getResultList();
            } else if(type == KeywordType.ALPHA_NUMERIC){
                return (Collection<String>)manager.createNamedQuery(KEYWORDS_FOR_USER_ALPHA_NUMERIC).
                        
                        setParameter("startKeyword", startKeyword).getResultList();
            } else throw new RuntimeException(""); //should never be thrown
        } else {
            if(type == null || type == KeywordType.ALL){
                return  (Collection<String>)manager.createNamedQuery(KEYWORDS_FOR_USER).
                     
                        setParameter("startKeyword", startKeyword).
                        setMaxResults(numberReturned).getResultList();
            } else if(type == KeywordType.ALPHA){
                return (Collection<String>)manager.createNamedQuery(KEYWORDS_FOR_USER_ALPHA).
                      
                        setParameter("startKeyword", startKeyword).
                        setMaxResults(numberReturned).getResultList();
            } else if(type == KeywordType.ALPHA_NUMERIC){
                return (Collection<String>)manager.createNamedQuery(KEYWORDS_FOR_USER_ALPHA_NUMERIC).
                       
                        setParameter("startKeyword", startKeyword).
                        setMaxResults(numberReturned).getResultList();
            } else throw new RuntimeException(""); //should never be thrown
        }
    }
    
    
    /**
     * This gets all the unique keywords in the database
     *
     * Types,  ALPHA, ALPHA_NUMERIC only work with oracle DBs
     *
     * @param userId federalId of the user.
     * @param type ALL, ALPHA, ALPHA_NUMERIC, {@link KeywordType}
     * @param manager manager object that will facilitate interaction with underlying database
     * @return list of keywords
     */
    public static Collection<String> getAllKeywords(String userId, KeywordType type, EntityManager manager){
        log.trace("getAllKeywords("+userId+", EntityManager)");
        Collection<String> keywords  = null;
        
        if(type.toString().equals(KeywordType.ALL.toString())){
            keywords = (Collection<String>)manager.createNamedQuery(ALLKEYWORDS).getResultList();
        } else if(type.toString().equals(KeywordType.ALPHA.toString())){
            keywords = (Collection<String>)manager.createNamedQuery(ALLKEYWORDS_NATIVE_ALPHA).getResultList();
        } else if(type.toString().equals(KeywordType.ALPHA_NUMERIC.toString())){
            keywords = (Collection<String>)manager.createNamedQuery(ALLKEYWORDS_NATIVE_ALPHA_NUMERIC).getResultList();
        } else {
            keywords = (Collection<String>)manager.createNamedQuery(ALLKEYWORDS).getResultList();
        }
        
        return keywords;
    }
    
}
