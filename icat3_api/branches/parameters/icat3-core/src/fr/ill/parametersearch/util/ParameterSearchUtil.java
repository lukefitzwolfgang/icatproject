/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 29 juin 2010
 */

package fr.ill.parametersearch.util;

import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.ParameterOperable;
import fr.ill.parametersearch.ParameterOperator;
import fr.ill.parametersearch.ParameterType;
import fr.ill.parametersearch.comparator.Comparator;
import fr.ill.parametersearch.exception.ExtractNumericConditionException;
import fr.ill.parametersearch.exception.ExtractStringConditionException;
import fr.ill.parametersearch.exception.NoParameterTypeException;
import fr.ill.parametersearch.exception.NoParametersException;
import fr.ill.parametersearch.exception.ParameterSearchException;
import java.util.List;
import uk.icat3.entity.Parameter;
import uk.icat3.util.LogicalOperator;

/**
 * This class contains util functions that extract from parameter, comparators
 * and operator JPQL information.
 * 
 * @author cruzcruz
 */
public class ParameterSearchUtil {

    /** This counter contains the number of JPQL parameters created (param_0, param_1)*/
    private int contParameter;
    /** Root name of the parameters used in the JPQL statment ('PARAM_NAME'_'contParameter')*/
    private final static String PARAM_NAME = "parameter";

    /**
     * Constructor
     */
    public ParameterSearchUtil() {
        contParameter = 0;
    }


    /**
     * Return next the parameter name not used yet.
     *
     * @return Next parameter name
     */
    public String getNextParamName () {
        return PARAM_NAME + "_" + contParameter++;
    }


    /**
     * Extract condition from one parameter comparator. 
     * 
     * Return example: param_0.numeric_value > 1.0
     *
     * @param paramName Parameter name in the JPQL statement
     * @param paramComp ParameterComparator
     * @return JPQL condition
     * @throws ExtractStringConditionException
     * @throws ExtractNumericConditionException
     */
    public String getCondition (String paramName, ParameterComparator paramComp) throws ExtractStringConditionException, ExtractNumericConditionException {

        Parameter param = paramComp.getParameterValued().getParam();
        Comparator comparator = paramComp.getComparator();
        Object value = paramComp.getValue();

        // Numeric comparators
        if (param.isNumeric()) {
            if (comparator == Comparator.GREATER_THAN)
                return paramName + " > " + value.toString();
            if (comparator == Comparator.GREATER_EQUAL)
                return paramName + " >= " + value.toString();
            if (comparator == Comparator.LESS_THAN)
                return paramName + " < " + value.toString();
            if (comparator == Comparator.LESS_EQUAL)
                return paramName + " <= " + value.toString();
            if (comparator == Comparator.EQUAL)
                return paramName + " = " + value.toString();

            throw new ExtractNumericConditionException(param, comparator);
        }
        // String comparators
        else {
            if (comparator == Comparator.CONTAIN)
                return paramName + " like '%" + value.toString() + "%'";
            if (comparator == Comparator.START_WITH)
                return paramName + " like '" + value.toString() + "%'";
            if (comparator == Comparator.END_WITH)
                return paramName + " like '%" + value.toString() + "'";
            if (comparator == Comparator.EQUAL)
                return paramName + " = '" + value.toString() + "'";
            
            throw new ExtractStringConditionException(param, comparator);
        }
    }

    /**
     * Extract JPQL statement from a parameter search structure defined inside
     * the ParameterOperable object called 'paramOperable'
     * 
     * @param paramOperable Contains the parameter search structure
     * @return JPQL statement
     * @throws ParameterSearchException
     */
    public ExtractedJPQL extractJPQLOperable (ParameterOperable paramOperable) throws ParameterSearchException {
        ExtractedJPQLPriva ejpql = new ExtractedJPQLPriva();

        extractJPQL(paramOperable, ejpql);

        return ejpql;
    }

    /**
     * Extract JPQL statement condition from a Comparator and store in the ejpql
     * object.
     * 
     * @param comp Comparator
     * @param ejpql Object where the information is stored
     * @throws NoParameterTypeException
     * @throws ExtractStringConditionException
     * @throws ExtractNumericConditionException
     * @throws ParameterSearchException
     */
    private void extractJPQLComparator (ParameterComparator comp, ExtractedJPQLPriva ejpql) throws NoParameterTypeException, ExtractStringConditionException, ExtractNumericConditionException, ParameterSearchException {
        // Check that the comparator is well contructed.
        comp.validate();

        // Check if the parameter to compare with it's a parameter
        if (comp.getValue().getClass() == ParameterValued.class) {
            String paramNameVal = this.getNextParamName();
            ParameterValued p = (ParameterValued)comp.getValue();
            ejpql.addParameter(paramNameVal, p);
            comp.setValue(paramNameVal + "." + ((ParameterValued) comp.getValue()).getValueType());
        }
        addParameterCondition(this.getNextParamName(), comp, ejpql);
    }

    private void addParameterCondition (String paramName, ParameterComparator comp, ExtractedJPQLPriva ejpql) throws ExtractStringConditionException, ExtractNumericConditionException {
        ejpql.addParameter(paramName, comp.getParameterValued());
        // Add condition for parameter
        ejpql.addStartCondition(this.getParameterCondition (paramName), LogicalOperator.AND);
        // Add condition for comparator
        ejpql.addCondition(this.getCondition(paramName + "." + comp.getParameterValued().getValueType(), comp));
    }

    /**
     * Extract JPQL statement condition from a Comparator and store in the ejpql
     * object. To optimize the ejecution of SQL queries where the OR condition
     * is present, the code try to reuse declared paremeters to use them
     * inside OR condition. The last parameter name using in a OR condition is
     * 'paramName'
     *
     * DatafileParameter p0 where
     *              p0.parameter = scanType  OR p0.parameter = wavelength
     * 
     * DatafileParameter p0, DatafileParameter p1 where
     *              p0.parameter = scanType AND p1.parameter = wavelength
     *
     * The difference between the sentences above, is that the first use only
     * one parameter to make the OR condition, and the other has to use 2
     * parameters.
     * 
     * @param comp Comparator
     * @param ejpql Object where the information is stored
     * @param paramName Last parameter name used in OR condition
     * @return
     * @throws ParameterSearchException
     */
    private String extractJQPL (ParameterComparator comp, ExtractedJPQLPriva ejpql, String paramName) throws ParameterSearchException {
        comp.validate();

        // Check if the parameter to compare with, it's a parameter
        // TODO: make some test with this.
        if (comp.getValue().getClass() == ParameterValued.class) {
            String paramNameVal = this.getNextParamName();
            ParameterValued p = (ParameterValued)comp.getValue();
            ejpql.addParameter(paramNameVal, p);
            comp.setValue(paramNameVal + "." + ((ParameterValued) comp.getValue()).getValueType());
        }

        // No parameter name has been defined in OR condition
        if (paramName == null) {
            paramName = this.getNextParamName();
            addParameterCondition(paramName, comp, ejpql);
        }
        // OR condition has been defined
        else {
            if (comp.getParameterValued().getType() != ejpql.getParameterType(paramName)) {
                paramName = this.getNextParamName();
                addParameterCondition(paramName, comp, ejpql);
            }
            // User a parameter which has been declared before
            else {
                ejpql.addCondition(this.getCondition(paramName + "." + comp.getParameterValued().getValueType(), comp));
            }
        }
        return paramName;
    }

    /**
     * Extract JPQL statement condition from a Operable parameter and store it in the ejpql
     * object.
     * 
     * @param paramOperable Operable parameter
     * @param ejpql Object where the information is stored
     * @throws ParameterSearchException
     */
    private void extractJPQL(ParameterOperable paramOperable, ExtractedJPQLPriva ejpql) throws ParameterSearchException {
        extractJPQL(paramOperable, ejpql, null);
    }

    /**
     * Extract JPQL statement condition from a Operable parameter and store in the ejpql
     * object. To optimize the ejecution of SQL queries where the OR condition
     * is present, the code try to reuse declared paremeters to use them
     * inside OR condition. The last parameter name using in a OR condition is
     * 'paramName'
     *
     * DatafileParameter p0 where
     *              p0.parameter = scanType  OR p0.parameter = wavelength
     *
     * DatafileParameter p0, DatafileParameter p1 where
     *              p0.parameter = scanType AND p1.parameter = wavelength
     *
     * The difference between the sentences above, is that the first use only
     * one parameter to make the OR condition, and the other has to use 2
     * parameters.
     * 
     * @param parameterOperable Operable parameter
     * @param ejpql Object where the information is stored
     * @param paramName Last parameter name used in OR condition
     * @return
     * @throws ParameterSearchException
     */
    private String extractJPQL(ParameterOperable parameterOperable, ExtractedJPQLPriva ejpql, String paramName) throws ParameterSearchException {

        // If it's a parameterComparator
        if (parameterOperable.getClass() == ParameterComparator.class)
            return extractJQPL((ParameterComparator) parameterOperable, ejpql, paramName);
        
        // If it's a ParameterOperator
        else if (parameterOperable.getClass() == ParameterOperator.class) {
            ParameterOperator op = (ParameterOperator) parameterOperable;

            // If the list is empty then exit
            if (op.getListComparable().isEmpty())
                return paramName;

            // Open parenthesis for the list of comparators
            ejpql.openParenthesis();

            int size = op.getListComparable().size();
            // Iterates over the list except the first parameter
            for (int i = 1; i < size; i++) {
                if (op.getOperator() == LogicalOperator.OR)
                    paramName = extractJPQL(op.getListComparable().get(i), ejpql, paramName);
                else
                    extractJPQL(op.getListComparable().get(i), ejpql);
                
                ejpql.addCondition(op.getOperator());
            }

            // Extract first parameter (Then it's not necessary to delete last
            //  addCondition(Operator)).
            ParameterOperable po = op.getListComparable().get(0);

            // Extract last ParameterOperable
            if (op.getOperator() == LogicalOperator.OR)
                paramName = extractJPQL(po, ejpql, paramName);
            else
                extractJPQL(po, ejpql);

            // Close the parenthesis for the comparators
            ejpql.closeParenthesis();
        }
        return paramName;
    }

    /**
     * Create JPQL statement for a list of parameters. The JQPL statement created
     * will return all the investigations which have some datafile that includes all the parameters
     * defined in 'listParameter'.
     *
     * @param listParameters
     * @return
     * @throws NoParameterTypeException
     */
    public ExtractedJPQL extractJPQLParameters(List<ParameterValued> listParameters) throws NoParameterTypeException {
        ExtractedJPQLPriva ejpql = new ExtractedJPQLPriva();

        String paramName;

        int size = listParameters.size();
        for (int i = 1; i < size; i++) {
            paramName = getNextParamName();
            ejpql.addParameter(paramName, listParameters.get(i));
            ejpql.addCondition(getParameterCondition(paramName));
            ejpql.addCondition(LogicalOperator.AND);
        }

        // Add last parameter
        paramName = getNextParamName();
        ejpql.addCondition (getParameterCondition(paramName));
        ejpql.addParameter(paramName, listParameters.get(0));

        return ejpql;
    }

    /**
     * Extract JPQL statement from a list of comparators and store it in the ejpql
     * object.
     * 
     * @param listComparators
     * @return
     * @throws ParameterSearchException
     */
    public ExtractedJPQL extractJPQLComparators(List<ParameterComparator> listComparators) throws ParameterSearchException {

        ExtractedJPQLPriva ejpql = new ExtractedJPQLPriva();

        int size = listComparators.size();
        // Extract all the comparators except the first one.
        for (int i = 1; i < size; i++) {
            extractJPQLComparator(listComparators.get(i), ejpql);
            ejpql.addCondition(LogicalOperator.AND);
        }

        // Extract first comparator
        extractJPQLComparator(listComparators.get(0), ejpql);

        return ejpql;
    }

    /**
     * Return the JPQL condition for a parameter
     * 
     * @param paramName Name or the parameter
     * @return JPQL condition
     */
    private String getParameterCondition(String paramName) {
        return paramName + ".parameter = " + ":" + paramName;
    }


    /**
     * Private class that extends ExtractedJPQL for adding information, only
     * used inside this class
     *
     * @see ExtractedJPQL
     */
    private class ExtractedJPQLPriva extends ExtractedJPQL {
        /**
         * Add a condition between parenthesis
         * 
         * @param condition Condition to add
         */
        private void addCondition (String condition) {
            this.condition.append("(" + condition + ") ");
        }

        /**
         * Open a parenthesis in the condition string.
         */
        private void openParenthesis () {
            this.condition.append("(");
        }

        /**
         * Close a parenthesis in the condition string.
         */
        private void closeParenthesis () {
            this.condition.append(")");
        }

        /**
         * Add a parameter
         *
         * @param paramName JQPL parameter name for the ParameterValued
         * @param p ParameterValued to be added
         */
        private void addParameter (String paramName, ParameterValued p) {
            if (p.getType() == ParameterType.DATAFILE)
                datafileParameter.put(paramName, p.getParam());
            else if (p.getType() == ParameterType.DATASET)
                datasetParameter.put(paramName, p.getParam());
            else if (p.getType() == ParameterType.SAMPLE)
                sampleParameter.put(paramName, p.getParam());
        }

        /**
         * Add a condition in the begining of the string
         * 
         * @param condition Condition to be added
         * @param op Operator to added
         */
        private void addStartCondition (String condition, LogicalOperator op) {
            this.condition.insert(0, "(" + condition + ") " + op.name() + " ");
        }

        /**
         * Add an operator to the condition
         *
         * @param logicalOperator Operator to be added.
         */
        private void addCondition(LogicalOperator logicalOperator) {
            this.condition.append(logicalOperator + " ");
        }

        /**
         * Return the parameter type of a JPQL parameter name
         * 
         * @param paramName JPQL parameter name
         * @return Parameter type
         * @throws NoParametersException
         */
        private ParameterType getParameterType(String paramName) throws NoParametersException {
            if (datafileParameter.containsKey(paramName))
                return ParameterType.DATAFILE;
            if (datasetParameter.containsKey(paramName))
                return ParameterType.DATASET;
            if (sampleParameter.containsKey(paramName))
                return ParameterType.SAMPLE;

            throw new NoParametersException("No parameter name '" + paramName + "' defined");
        }
    }
}
