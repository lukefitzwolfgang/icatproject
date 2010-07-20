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
public class NullParameterException extends ParameterSearchException {

    private static String msg = NullParameterException.class.getName();

    public NullParameterException(String fieldName) {
        super (msg + ": field " + fieldName + " cannot be null");
    }


}
