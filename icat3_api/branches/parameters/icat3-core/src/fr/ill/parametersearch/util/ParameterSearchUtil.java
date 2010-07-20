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
import fr.ill.parametersearch.exception.ParameterSearchException;
import java.util.List;
import java.util.Map;
import uk.icat3.entity.DatafileParameter;
import uk.icat3.entity.DatasetParameter;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.SampleParameter;
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
    public final static String PARAM_NAME = "parameter";

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

        Parameter param = paramComp.getParam();
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
     * Extract a JPQL statement from a parameter search structure defined in a
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

    private void extractJPQL(ParameterOperable parameterOperable, ExtractedJPQLPriva ejpql) throws ParameterSearchException {

        if (parameterOperable.getClass() == ParameterComparator.class) {
            ParameterComparator comp = (ParameterComparator) parameterOperable;
            comp.validate();

            String paramName = this.getNextParamName();
            
            ejpql.addParameter(paramName, comp.getParam(), comp.getType());

            if (comp.getValue().getClass() == Parameter.class) {
                String paramNameVal = this.getNextParamName();
                Parameter p = (Parameter)comp.getValue();
                ejpql.addParameter(paramNameVal, p, getParameterType(p));
                
                comp.setValue(paramNameVal + "." + getValueType((Parameter) comp.getValue()));
            }

            String condition = this.getParamterCondition (paramName);
            condition += " AND " + this.getCondition(paramName + "." + getValueType(comp.getParam()), comp);
            ejpql.addCondition(condition);
        }
        
        else if (parameterOperable.getClass() == ParameterOperator.class) {
            ParameterOperator op = (ParameterOperator) parameterOperable;

            if (op.getListComparable().isEmpty())
                return;

            String paramNameOR = null;
            for (ParameterOperable po : op.getListComparable().subList(0, op.getListComparable().size() - 1)) {
                if (op.getOperator() == LogicalOperator.OR)
                    paramNameOR = extractJPQLOR(po, ejpql, paramNameOR);
                else
                    extractJPQL(po, ejpql);
                
                ejpql.addCondition(op.getOperator());
            }

            ParameterOperable po = op.getListComparable().get(op.getListComparable().size() - 1);

            // Extract last ParameterOperable
            if (op.getOperator() == LogicalOperator.OR)
                paramNameOR = extractJPQLOR(po, ejpql, paramNameOR);
            else
                extractJPQL(po, ejpql);
        }
    }

     private String extractJPQLOR(ParameterOperable parameterOperable, ExtractedJPQLPriva ejpql, String paramName) throws ParameterSearchException {

         if (parameterOperable.getClass() == ParameterComparator.class) {
            ParameterComparator comp = (ParameterComparator) parameterOperable;
            comp.validate();

            // Check if the parameter to compare with it's a parameter
            if (comp.getValue().getClass() == Parameter.class) {
                String paramNameVal = this.getNextParamName();
                Parameter p = (Parameter)comp.getValue();
                ejpql.addParameter(paramNameVal, p, getParameterType(p));

                comp.setValue(paramNameVal + "." + getValueType((Parameter) comp.getValue()));
            }

            String condition = "";
            if (paramName == null) {
                paramName = this.getNextParamName();
                ejpql.addParameter(paramName, comp.getParam(), comp.getType());

                condition = this.getParamterCondition (paramName);
                condition += " AND " + this.getCondition(paramName + "." + getValueType(comp.getParam()), comp);
            }
            else {
                Parameter p = ejpql.getAllParameter().get(paramName);
                
                if (comp.getType() != getParameterType(p)) {
                    paramName = this.getNextParamName();
                    ejpql.addParameter(paramName, comp.getParam(), comp.getType());

                    condition = this.getParamterCondition (paramName);
                    condition += " AND " + this.getCondition(paramName + "." + getValueType(comp.getParam()), comp);
                }
                else {
                    condition += this.getCondition(paramName + "." + getValueType(comp.getParam()), comp);
                }
            }
            
            ejpql.addCondition(condition);
         }
         else
             extractJPQL(parameterOperable, ejpql);

         return paramName;
    }

    public static String getTableName(ParameterType type) throws NoParameterTypeException {
        if (type == ParameterType.DATASET) {
            return DatasetParameter.class.getSimpleName();
        }
        else if (type == ParameterType.DATAFILE) {
            return DatafileParameter.class.getSimpleName();
        }
        else if (type == ParameterType.SAMPLE) {
            return SampleParameter.class.getSimpleName();
        }

        throw new NoParameterTypeException("function getTableName (ParameterType type)");
    }

    private ParameterType getParameterType (Parameter p) throws NoParameterTypeException {
         if (p.isDatafileParameter())
            return ParameterType.DATAFILE;
        if (p.isSampleParameter())
            return ParameterType.SAMPLE;
        if (p.isDataSetParameter())
            return ParameterType.DATASET;
         
        throw new NoParameterTypeException(p.getParameterPK().getName() + " " + p.getParameterPK().getUnits());
    }

    private String getValueType(Parameter param) {
        if (param.isNumeric())
            return ParameterComparator.NUMERIC_VALUE;
        return ParameterComparator.STRING_VALUE;
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
    public ExtractedJPQL extractJPQLParameters(List<Parameter> listParameters) throws NoParameterTypeException {
        ExtractedJPQLPriva ejpql = new ExtractedJPQLPriva();

        String condition = "";
        String paramName;
        ParameterType parameterType;
       
        for (Parameter p : listParameters) {
            paramName = getNextParamName();
            parameterType = getParameterType(p);

            condition = getParamterCondition(paramName);

            ejpql.addParameter(paramName, p, parameterType);
            ejpql.addCondition(condition);
            ejpql.addCondition(LogicalOperator.AND);
        }
        
        // Remove last AND operator
        ejpql.removeLastCondition (LogicalOperator.AND);

        return ejpql;
    }

    public ExtractedJPQL extractJPQLComparators(List<ParameterComparator> listComparators) throws ParameterSearchException {

        ExtractedJPQLPriva ejpql = new ExtractedJPQLPriva();

        for (ParameterComparator p : listComparators.subList(0, listComparators.size() - 1)) {
            extractJPQL(p, ejpql);
            ejpql.addCondition(LogicalOperator.AND);
        }
        
        extractJPQL(listComparators.get(listComparators.size() - 1), ejpql);
        System.out.println("+++ " + ejpql.getCondition());

        for (Map.Entry<String, ?> e : ejpql.getAllParameter().entrySet()) {
            System.out.println("--- " + e.getValue() + " " + e.getKey());
        }

        return ejpql;
    }

    private String getParamterCondition(String paramName) {
        return paramName + ".parameter = " + ":" + paramName;
    }

   

    
    private class ExtractedJPQLPriva extends ExtractedJPQL {
        private void addCondition (String condition) {
            this.condition.append("(" + condition + ") ");
        }

        private void addParameter (String paramName, Parameter param, ParameterType paramType) {
            if (paramType == ParameterType.DATAFILE) {
                datafileParameter.put(paramName, param);
                if (datafileParameter.size() > 1) {
                    this.addStartCondition (datafileFirstParameter + ".datafile = " +
                            paramName + ".datafile", LogicalOperator.AND);
                }
                else
                    datafileFirstParameter = paramName;
            }
            else if (paramType == ParameterType.DATASET) {
                datasetParameter.put(paramName, param);
                if (datasetParameter.size() > 1) {
                    this.addStartCondition (datasetFirstParameter + ".dataset = " +
                            paramName + ".dataset", LogicalOperator.AND);
                }
                else
                    datasetFirstParameter = paramName;
            }
            else if (paramType == ParameterType.SAMPLE) {
                sampleParameter.put(paramName, param);
                if (sampleParameter.size() > 1) {
                    this.addStartCondition (sampleFirstParameter + ".sample = " +
                            paramName + ".sample", LogicalOperator.AND);
                }
                else
                    sampleFirstParameter = paramName;
            }
        }

        private void addStartCondition (String condition, LogicalOperator op) {
            this.condition.insert(0, condition + " " + op.name() + " ");
        }

        private void addCondition(LogicalOperator logicalOperator) {
            this.condition.append(logicalOperator + " ");
        }

        private void removeLastCondition(LogicalOperator op) {
            int length = this.condition.length();
            this.condition.delete(length - op.toString().length() - 1, length);
        }

        
    }
}
