/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 23 juil. 2010
 */

package uk.icat3.search.parameter.util;

import uk.icat3.search.parameter.ParameterType;
import uk.icat3.entity.Parameter;
import uk.icat3.exception.NoParameterTypeException;
import uk.icat3.exception.NoSearchableParameterException;
import uk.icat3.exception.NullParameterException;

/**
 * This class contains the information about a parameter which is going to
 * be used into a parameter search
 * 
 * @author cruzcruz
 */
public class ParameterValued {
    /** JQPL field name for NUMERIC_VALUE */
    private static String NUMERIC_VALUE = "numericValue";
    /** JQPL field name for STRING_VALUE */
    private static String STRING_VALUE = "stringValue";
    /** Type of parameter (dataset, datafile, sample, ALL) */
    private ParameterType type;
    /** Parameter to compare */
    private Parameter param;


    ///////////////////////////////////////////////////////////////////////////
    //                CONSTRUCTORS                                           //
    ///////////////////////////////////////////////////////////////////////////
    public ParameterValued() {
    }

    public ParameterValued (Parameter param) {
        this.param = param;
    }

    public ParameterValued(ParameterType type) {
        this.type = type;
    }

    public ParameterValued(ParameterType type, Parameter param) {
        this.type = type;
        this.param = param;
    }

    /**
     * Return the JPQL field name of the parameter value.
     *
     * @return Field name
     */
    public String getValueType() {
        if (getParam().isNumeric())
            return NUMERIC_VALUE;
        return STRING_VALUE;
    }

    /**
     * Check the parameter valued is correctly defined
     * 
     * @throws NoSearchableParameterException
     * @throws NullParameterException
     * @throws NoParameterTypeException
     */
    public void validate() throws NoSearchableParameterException, NullParameterException, NoParameterTypeException {
        if (param == null)
            throw new NullParameterException("parameter ");

        if (this.type == null)
            throw new NoParameterTypeException("type is null");

        if (!this.param.getSearchable().toUpperCase().equalsIgnoreCase("Y"))
            throw new NoSearchableParameterException(this.param);

        if (type == ParameterType.DATAFILE && !this.param.isDatafileParameter())
            throw new NoSearchableParameterException(this.param, "Parameter not relevant for Datafile");
        if (type == ParameterType.DATASET && !this.param.isDataSetParameter())
            throw new NoSearchableParameterException(this.param, "Parameter not relevant for Dataset");
        if (type == ParameterType.SAMPLE && !this.param.isSampleParameter())
            throw new NoSearchableParameterException(this.param, "Parameter not relevant for Sample");
    }


    ///////////////////////////////////////////////////////////////////////////
    //                               GETTERS and SETTERS                     //
    ///////////////////////////////////////////////////////////////////////////


    public Parameter getParam() {
        return param;
    }

    public void setParam(Parameter param) {
        this.param = param;
    }
    
    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }
}
