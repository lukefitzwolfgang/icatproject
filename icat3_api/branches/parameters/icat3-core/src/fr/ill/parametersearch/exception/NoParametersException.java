/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 30 juin 2010
 */

package fr.ill.parametersearch.exception;

import fr.ill.parametersearch.exception.ParameterSearchException;

/**
 *
 * @author cruzcruz
 */
public class NoParametersException extends ParameterSearchException {

    private final static String msg = "No parameters defined.";

    public NoParametersException() {
        super (NoParametersException.msg);
    }

    public NoParametersException(String msg) {
        super (NoParametersException.msg + ":" + msg);
    }
}
