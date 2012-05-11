/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.icat3.exceptions;

/**
 * This expection is thrown when Doi are not found when queried.
 * @author Mr. Srikanth Nagella
 */
public class DoiNotFoundException extends ICATAPIException {

    /**
     * Creates a new instance of <code>DoiNotFoundException</code> without detail message.
     */
    public DoiNotFoundException() {
    }


    /**
     * Constructs an instance of <code>DoiNotFoundException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public DoiNotFoundException(String msg) {
        super(msg);
    }
}
