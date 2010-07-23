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

    /**
     * Constructor
     */
    public ExtractedJPQL() {
        condition = new StringBuffer();
        datafileParameter = new HashMap<String, Parameter> ();
        datasetParameter = new HashMap<String, Parameter> ();
        sampleParameter = new HashMap<String, Parameter> ();
    }

    /**
     * Return JPQL statement relative to the JPQL parameter declaration
     *
     * @return JPQL parameters declaration
     * @throws NoParametersException
     */
    public String getParametersJPQL () throws NoParametersException  {
        String ret = "";
        for (Map.Entry<String, Parameter> e : datafileParameter.entrySet())
            ret += ", IN(df.datafileParameterCollection) " + e.getKey();

        for (Map.Entry<String, Parameter> e : datasetParameter.entrySet())
             ret += ", IN(ds.datasetParameterCollection) " + e.getKey();

        for (Map.Entry<String, Parameter> e : sampleParameter.entrySet())
             ret += ", IN(sample.sampleParameterCollection) " + e.getKey();

        if (ret.isEmpty())
            throw new NoParametersException();

        String parameter = "";
        if (!datafileParameter.isEmpty())
            parameter += ", IN(i.datasetCollection) ds, IN(ds.datafileCollection) df";
        if (datafileParameter.isEmpty() && !datasetParameter.isEmpty())
            parameter += ", IN(i.datasetCollection) ds";
        if (!sampleParameter.isEmpty())
            parameter += ", IN(i.sampleCollection) sample";

        return parameter.substring(2) + ret;
    }


    ///////////////////////////////////////////////////////////////////////////////////
    //                       GETTERS
    ////////////////////////////////////////////////////////////////////////
   
    public String getCondition() {
        return condition.toString();
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

    public Map<String, Parameter> getDatasetParameter() {
        return datasetParameter;
    }

    public Map<String, Parameter> getSampleParameter() {
        return sampleParameter;
    }
}
