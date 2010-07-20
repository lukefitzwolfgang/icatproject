/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 25 juin 2010
 */

package fr.ill.parametersearch;

import fr.ill.parametersearch.comparator.Comparator;
import fr.ill.parametersearch.exception.NullParameterException;
import fr.ill.parametersearch.exception.ParameterSearchException;
import uk.icat3.entity.Parameter;

/**
 * This class contains information about the parameter and the value which is
 * going to be compare with.
 *
 * @author cruzcruz
 */
public class ParameterComparator extends ParameterOperable{

    /** JQPL field name for NUMERIC_VALUE */
    public static String NUMERIC_VALUE = "numericValue";
    /** JQPL field name for STRING_VALUE */
    public static String STRING_VALUE = "stringValue";

    /** Parameter to compare */
    private Parameter param = null;
    /** Type of comparator (greater_than, less_than, equal, ..)*/
    private Comparator comparator = null;
    /** Type of parameter (dataset, datafile, sample, ALL) */
    private ParameterType type;
    /** Value to compare with */
    private Object value = null;
    /** Second value to compare with (only for some comparators like between)*/
    private Object value2 = null;

    /**
     * Empty constructor. Needed for Web Service implementation
     */
    public ParameterComparator() {
    }


    /**
     * Contructor
     *
     * @param param Parameter
     * @param comparator Comparator which compare with
     * @param value Value to compare with parameter 'param'
     */
    public ParameterComparator(Parameter param, Comparator comparator, String value) {
        this.param = param;
        this.comparator = comparator;
        this.value = value;
    }

    /**
     * Check that the compartor is well contructed and the fields are filled.
     *
     * @return False in case of missing a field.
     */
    public void validate () throws ParameterSearchException {
        if (this.param == null)
            throw new NullParameterException(this.getClass().getName() + ".param");
        if (this.comparator == null)
            throw new NullParameterException(this.getClass().getName() + ".comparator");
        if (this.value == null)
            throw new NullParameterException(this.getClass().getName() + ".value");
    }

    /////////////////////////////////////////////////////////////////////////////
    //                   GETTERS and SETTERS
    ///////////////////////////////////////////////////////////////////////////
    
    public Comparator getComparator() {
        return comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public Parameter getParam() {
        return param;
    }

    public void setParam(Parameter param) {
        this.param = param;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getValue2() {
        return value2;
    }

    public void setValue2(Object value2) {
        this.value2 = value2;
    }

    public ParameterType getType() {
        return type;
    }

    public void setType(ParameterType type) {
        this.type = type;
    }    
}
