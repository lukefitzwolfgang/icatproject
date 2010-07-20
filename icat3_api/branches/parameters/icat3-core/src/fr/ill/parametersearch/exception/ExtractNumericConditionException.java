/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 30 juin 2010
 */

package fr.ill.parametersearch.exception;

import fr.ill.parametersearch.comparator.Comparator;
import fr.ill.parametersearch.exception.ParameterSearchException;
import uk.icat3.entity.Parameter;

/**
 * This exception is executed when a numeric value is compared using a string comparator
 * 
 * @author cruzcruz
 */
public class ExtractNumericConditionException extends ParameterSearchException {

    private static String msg = "Extracting JPQL condition exception:";

    public ExtractNumericConditionException (Parameter param, Comparator comp) {
        super (ExtractNumericConditionException.msg +
                "Parameter '" +
                param.getParameterPK().getName() +
                "(" + param.getParameterPK().getUnits() +
                ")' contains numeric value " +
                "but comparator '" + comp.name() + "' is for string values");
    }
}
