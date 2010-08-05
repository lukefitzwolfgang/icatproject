/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 30 juin 2010
 */

package uk.icat3.parametersearch.exception;

import uk.icat3.parametersearch.ParameterOperator;
import uk.icat3.parametersearch.exception.ParameterSearchException;


/**
 *
 * @author cruzcruz
 */
public class EmptyOperatorException extends ParameterSearchException {

    private final static String msg = "Parameter operator list is empty.";
    

    public EmptyOperatorException() {
        super (EmptyOperatorException.msg);
    }
}
