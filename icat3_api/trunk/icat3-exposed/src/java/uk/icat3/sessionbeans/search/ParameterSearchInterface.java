/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 25 nov. 2010
 */

package uk.icat3.sessionbeans.search;

import java.util.Collection;
import uk.icat3.exceptions.ParameterSearchException;
import uk.icat3.exceptions.RestrictionException;
import uk.icat3.exceptions.SessionException;
import uk.icat3.restriction.RestrictionCondition;
import uk.icat3.search.parameter.ParameterComparisonCondition;
import uk.icat3.search.parameter.ParameterCondition;
import uk.icat3.search.parameter.util.ParameterSearch;

/**
 * This interface defines functions for parameter search.
 * T class is a Enum Object and should be one of the type of search
 * we are goint to make (DatasetInclude, DatafileInclude, InvestigationInclude,
 * SampleInclude)
 * 
 * @author cruzcruz
 */
public interface ParameterSearchInterface<T extends Enum> {

    /**
     * Return datasets matched by a logical condition.
     *
     * @param sessionId Session identification
     * @param logicalCondition Logial condition
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterCondition(String sessionId, ParameterCondition logicalCondition) throws SessionException, ParameterSearchException, RestrictionException;

    /**
     * Return datasets matched by comparison(s).
     *
     * @param sessionId Session identification
     * @param comparison Comparison
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterComparison(String sessionId, ParameterComparisonCondition... comparison) throws SessionException, ParameterSearchException, RestrictionException;

    /**
     * Return datasets matched by parameter(s).
     *
     * @param sessionId Session identification
     * @param parameters Parameters
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameter(String sessionId, ParameterSearch... parameters) throws SessionException, ParameterSearchException, RestrictionException;

    /**
     * Return datasets matched by a logical condition, include and restrictions.
     *
     * @param sessionId Session identification
     * @param logicalCondition Logial condition
     * @param include Include options
     * @param restriction Restrictions
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterCondition(String sessionId, ParameterCondition logicalCondition, T include, RestrictionCondition... restriction) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by comparison(s), include and restrictions.
     *
     * @param sessionId Session identification
     * @param comparison Comparison
     * @param include Include options
     * @param restriction Restrictions
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterComparison(String sessionId, ParameterComparisonCondition[] comparison, T include, RestrictionCondition... restriction) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by parameter(s), include and restrictions.
     *
     * @param sessionId Session identification
     * @param parameters Parameters
     * @param include Include options
     * @param restriction Restrictions
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameter(String sessionId, ParameterSearch[] parameters, T include, RestrictionCondition... restriction) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by a logical condition and restriction.
     *
     * @param sessionId Session identification
     * @param logicalCondition Logial condition
     * @param restriction Restrictions
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterCondition(String sessionId, ParameterCondition logicalCondition, RestrictionCondition... restriction) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by comparison(s) and restrictions.
     *
     * @param sessionId Session identification
     * @param comparison Comparison
     * @param restriction Restrictions
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterComparison(String sessionId, ParameterComparisonCondition[] comparison, RestrictionCondition... restriction) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by parameter(s) and restrictions.
     *
     * @param sessionId Session identification
     * @param parameters Parameters
     * @param restriction Restrictions
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameter(String sessionId, ParameterSearch[] parameters, RestrictionCondition... restriction) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by a logical condition and include
     *
     * @param sessionId Session identification
     * @param logicalCondition Logial condition
     * @param include Include options
     * @return Collection of datasets
     *
     * @throws SessionException
     * @throws ParameterSearchException
     */
    public Collection searchByParameterCondition(String sessionId, ParameterCondition logicalCondition, T include) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by comparison and include.
     *
     * @param sessionId Session identification
     * @param comparison Comparison condition
     * @param include Include options
     * @return
     * @throws SessionException
     * @throws ParameterSearchException
     * @throws RestrictionException
     */
    public Collection searchByParameterComparison(String sessionId, ParameterComparisonCondition[] comparison, T include) throws SessionException, ParameterSearchException, RestrictionException;
    /**
     * Return datasets matched by parameter(s) and include.
     *
     * @param sessionId Session identification
     * @param parameters Parameters
     * @param include Include options
     * @throws SessionException
     * @throws ParameterSearchException
     * @throws RestrictionException
     */
    public Collection searchByParameter(String sessionId, ParameterSearch[] parameters, T include) throws SessionException, ParameterSearchException, RestrictionException;
}
