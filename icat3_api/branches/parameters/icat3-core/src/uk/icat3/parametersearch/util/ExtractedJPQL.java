/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 9 juil. 2010
 */

package uk.icat3.parametersearch.util;

import uk.icat3.parametersearch.exception.NoParametersException;
import java.util.HashMap;
import java.util.Map;
import uk.icat3.entity.Parameter;

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
    /** List of JPQL parameters */
    protected Map<String, Object> jpqlParameter;

    /**
     * Constructor
     */
    public ExtractedJPQL() {
        condition = new StringBuffer();
        datafileParameter = new HashMap<String, Parameter> ();
        datasetParameter = new HashMap<String, Parameter> ();
        sampleParameter = new HashMap<String, Parameter> ();
        jpqlParameter = new HashMap<String, Object> ();
    }

    /**
     * Return JPQL statement relative to the JPQL parameter declaration
     *
     * @return JPQL parameters declaration
     * @throws NoParametersException
     */
    public String getParametersJPQL () throws NoParametersException  {
        String ret = "";
        // FIXME: IN is a INNER JOIN, so null column values are not permitted. This
        // means that if a investigation has one type of parameter but no other
        // wouldn't be included in the result search. This could be replaced by LEFT JOIN,
        // but then execution time will increase.        
        // This only matters for statements where and OR operation is present.
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


    ////////////////////////////////////////////////////////////////////////
    //                       GETTERS                                      //
    ////////////////////////////////////////////////////////////////////////
   
    public String getCondition() {
        return condition.toString();
    }

    

    public Map<String, Parameter> getDatafileParameter () {
        return datafileParameter;
    }

    public Map<String, Object> getAllParameter () {
        HashMap<String, Object> ret = new HashMap<String, Object> ();
        
        ret.putAll(datafileParameter);
        ret.putAll(datasetParameter);
        ret.putAll(sampleParameter);
        ret.putAll(jpqlParameter);
        
        return ret;
    }

    public Map<String, Parameter> getDatasetParameter() {
        return datasetParameter;
    }

    public Map<String, Parameter> getSampleParameter() {
        return sampleParameter;
    }
}
