/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 22 nov. 2010
 */

package uk.icat3.exceptions;

/**
 *
 * @author cruzcruz
 */
public class RestrictionException extends Throwable {

    public RestrictionException() {
        super ();
    }

    public RestrictionException(String msg) {
        super (msg);
    }
}
