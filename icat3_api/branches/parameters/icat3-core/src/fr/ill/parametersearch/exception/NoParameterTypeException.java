/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 30 juin 2010
 */

package fr.ill.parametersearch.exception;

import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.exception.ParameterSearchException;

/**
 *
 * @author cruzcruz
 */
public class NoParameterTypeException extends ParameterSearchException {

    private final static String msg = "No parameter type (datasetParameter, datafileParameter or sampleParameter) was defined";
    public NoParameterTypeException(String msg) {
        super (NoParameterTypeException.msg + ": " + msg);
    }

    public NoParameterTypeException(ParameterComparator comp) {
        super (NoParameterTypeException.msg + ": Error in comparator '" +
                comp.getParameterValued().getParam().getParameterPK().getName() + " (" +
                comp.getParameterValued().getParam().getParameterPK().getUnits() + ")'");
    }
   
}
