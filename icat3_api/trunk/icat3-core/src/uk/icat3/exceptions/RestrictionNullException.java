/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 23 nov. 2010
 */

package uk.icat3.exceptions;

/**
 *
 * @author cruzcruz
 */
public class RestrictionNullException extends RestrictionException {

    private static final String msg = "Type no recognized. Only Collection and String.";
    public RestrictionNullException() {
        super (msg);
    }
}
