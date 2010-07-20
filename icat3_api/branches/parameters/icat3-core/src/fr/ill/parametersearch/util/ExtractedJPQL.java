/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 9 juil. 2010
 */

package fr.ill.parametersearch.util;

import fr.ill.parametersearch.exception.NoParametersException;
import java.util.HashMap;
import java.util.Map;
import uk.icat3.entity.DatafileParameter;
import uk.icat3.entity.DatasetParameter;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.SampleParameter;

/**
 * This class define all the parameter and condition extracted from a parameter
 * search structure.
 * 
 * @author cruzcruz
 */
public class ExtractedJPQL {

    /** Condition of the parameter search */
    protected StringBuffer condition;
    /** List of datafile parameter */
    protected Map<String, Parameter> datafileParameter;
    /** List of dataset parameter */
    protected Map<String, Parameter> datasetParameter;
    /** List of sample parameter */
    protected Map<String, Parameter> sampleParameter;
    /** First datafile parameter that was created */
    protected String datafileFirstParameter;
    /** First dataset parameter that was created */
    protected String datasetFirstParameter;
    /** First sample parameter that was created */
    protected String sampleFirstParameter;

    /**
     * Constructor
     */
    public ExtractedJPQL() {
        // This paranthesis separated the conditions from
        // other comparasions.
        condition = new StringBuffer("(");
        datafileFirstParameter = datasetFirstParameter = sampleFirstParameter = null;
        datafileParameter = new HashMap<String, Parameter> ();
        datasetParameter = new HashMap<String, Parameter> ();
        sampleParameter = new HashMap<String, Parameter> ();
    }

    /**
     * Return the first parameter that has to be defined. This is used when the
     * JQPL statement is created (select firstparameter.investigation i from ....)
     *
     * @return First parameter which select investigations.
     */
    public String getFirstParameter() {
        String firstParameter = "";
        if (getDatafileParameter().size() > 0) {
            firstParameter = getDatafileFirstParameter() + ".datafile.dataset.investigation";
            addStartCondition(firstParameter, sampleFirstParameter,  ".sample.investigationId");
            addStartCondition(firstParameter, datasetFirstParameter,  ".dataset.investigationId");
        }
        else if (getDatasetParameter().size() > 0) {
            firstParameter = getDatasetFirstParameter() + ".dataset.investigation";
            addStartCondition(firstParameter, sampleFirstParameter,  ".sample.investigationId");
            addStartCondition(firstParameter, datafileFirstParameter,  ".datafile.dataset.investigationId");
        }
        else if (getSampleParameter().size() > 0) {
            firstParameter = getSampleFirstParameter() + ".sample.investigationId";
            addStartCondition(firstParameter, datasetFirstParameter,  ".dataset.investigationId");
            addStartCondition(firstParameter, datafileFirstParameter,  ".datafile.dataset.investigationId");
        }

        return firstParameter;
    }

    /**
     * Add a new condition at the begining of the statement
     * 
     * @param paramName Name of the JQPL parameter
     * @param param Parameter
     * @param investigationPath Where is defined the investigation in the parameter Name
     * ( Ex: for datafileParrameter is '.datafile.dataset.investigation')
     */
    private void addStartCondition (String paramName, String param, String investigationPath) {
        if (param != null)
            this.condition.insert(0, paramName + " = " + param + investigationPath + " AND ");
    }


    ///////////////////////////////////////////////////////////////////////////////////
    //                       GETTERS
    ////////////////////////////////////////////////////////////////////////
   
    public String getCondition() {
        // Add the parenthesis create in the constructor
        return condition.toString() + ")";
    }

    public String getParameters () throws NoParametersException  {
        String ret = "";
        for (Map.Entry<String, Parameter> e : datafileParameter.entrySet())
            ret += ", " + DatafileParameter.class.getSimpleName() + " " + e.getKey();
        
        for (Map.Entry<String, Parameter> e : datasetParameter.entrySet())
            ret += ", " + DatasetParameter.class.getSimpleName() + " " + e.getKey();

        for (Map.Entry<String, Parameter> e : sampleParameter.entrySet())
            ret += ", " + SampleParameter.class.getSimpleName() + " " + e.getKey();

        if (ret.isEmpty())
            throw new NoParametersException();
        
        return ret.substring(2);
    }

    public Map<String, Parameter> getDatafileParameter () {
        return datafileParameter;
    }

    public Map<String, Parameter> getAllParameter () {
        HashMap<String, Parameter> ret = new HashMap<String, Parameter> ();
        
        ret.putAll(datafileParameter);
        ret.putAll(datasetParameter);
        ret.putAll(sampleParameter);
        
        return ret;
    }

    public String getDatafileFirstParameter() {
        return datafileFirstParameter;
    }

    public String getDatasetFirstParameter() {
        return datasetFirstParameter;
    }

    public Map<String, Parameter> getDatasetParameter() {
        return datasetParameter;
    }

    public String getSampleFirstParameter() {
        return sampleFirstParameter;
    }

    public Map<String, Parameter> getSampleParameter() {
        return sampleParameter;
    }

    

    
}
