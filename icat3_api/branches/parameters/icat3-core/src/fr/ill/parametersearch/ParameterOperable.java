/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 25 juin 2010
 */

package fr.ill.parametersearch;

/**
 * This is the parent class for ParameterOperator and ParameterComparator, and
 * it's used to create the parameter search structure.
 *
 * This is an example of how it works:
 *
 * ParameterComparator p1 = new ParameterComparator (PRESS, EQUAL,
 *       new Float(21.00), SAMPLE);
 * ParameterComparator p2 = new ParameterComparator (TEMP, LESS_THAN,
 *       new Float(2.10), DATAFILE);
 * ParameterComparator p3 = new ParameterComparator (TEMP, LESS_THAN,
 *       new Float(2.10), DATAFILE);

 * ParameterOperator paramOp = new ParameterOperator (AND);

 * paramOp.add(new ParameterComparator (PRESS, EQUAL,new Float(21.00),
 *             SAMPLE));
 * paramOp.add(new ParameterOperator
 *             (OR,new ParameterOperable[]{p1, p2, p3})
 *            )
 * paramOp.add(new ParameterComparator (VOLT, EQUAL, new Float(15.10),
 *             DATASET))
 *
 * 
 * @author cruzcruz
 * @see ParameterComparator
 * @see ParameterOperator
 */
public abstract class ParameterOperable {
    
}
