/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 25 juin 2010
 */

package uk.icat3.parametersearch;

import uk.icat3.parametersearch.comparator.Comparator;
import uk.icat3.parametersearch.exception.NullParameterException;
import uk.icat3.parametersearch.exception.ParameterSearchException;
import uk.icat3.parametersearch.util.ParameterValued;
import uk.icat3.entity.Parameter;

/**
 * This class contains information about the parameter and the value which is
 * going to be compare with.
 *
 * @author cruzcruz
 * @see ParameterOperable
 */
public final class ParameterComparator implements ParameterOperable{

    /** Parameter valued which contains parameter and its type */
    private ParameterValued param;
    /** Type of comparator (greater_than, less_than, equal, ..)*/
    private Comparator comparator = null;
    
    /** Value to compare with */
    private Object value = null;
    /** Second value to compare with (only for some comparators like between)*/
    private Object value2 = null;

    /**
     * Empty constructor. Needed for Web Service implementation
     */
    public ParameterComparator() {
        this.param = new ParameterValued ();
    }


    /**
     * Contructor
     *
     * @param param Parameter
     * @param comparator Comparator which compare with
     * @param value Value to compare with parameter 'param'
     */
    public ParameterComparator(Parameter param, Comparator comparator, String value) {
        this.param = new ParameterValued(param);
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

    public ParameterValued getParameterValued() {
        return param;
    }

    public void setParameterValued(ParameterValued param) {
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
}
