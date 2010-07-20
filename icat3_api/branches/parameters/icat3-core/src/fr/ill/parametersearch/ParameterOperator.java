/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 25 juin 2010
 */

package fr.ill.parametersearch;

import fr.ill.parametersearch.exception.CyclicException;
import java.util.ArrayList;
import java.util.List;
import uk.icat3.util.LogicalOperator;

/**
 * This class contains a list of ParameterOperable that defines a parameter
 * search structure.
 * 
 * @author cruzcruz
 * @see ParameterOperable
 */
public class ParameterOperator extends ParameterOperable {

    /** List of ParameterOperable objects */
    private List<ParameterOperable> listComparable;
    /** Operator which joins the comparators */
    private LogicalOperator operator;


    /**
     *  Constructor
     */
    public ParameterOperator() {
        listComparable = new ArrayList<ParameterOperable> ();
    }

    /**
     * Contructor
     * 
     * @param op Logical Operator to compare with
     */
    public ParameterOperator (LogicalOperator op) {
        this.operator = op;
    }
   

    /**
     * Add a new Parameter Operable into the list
     *
     * @param param Parmareter Operable to add
     * @throws CyclicException In case a cyclic structure had been build.
     */
    public void add (ParameterOperable param) throws CyclicException  {
        if (param == this)
            throw new CyclicException("It's the same object");

        if (listComparable.contains(param))
            throw new CyclicException("This ParameterOperator has already been inserted");
        
        if (param.getClass() == ParameterOperator.class) {
            ParameterOperator op = (ParameterOperator)param;
            if (op.listComparable.contains(this))
                throw new CyclicException("Cyclic structure. " + this.toString());
            for (ParameterOperable p : listComparable)
                if (p.getClass() == ParameterOperator.class && op.listComparable.contains(p))
                    throw new CyclicException("Cyclic structure. " + this.toString());
        }
        listComparable.add(param);
    }


    ////////////////////////////////////////////////////////////////////////////
    //                               GETTERS and SETTERS
    //////////////////////////////////////////////////////////////////////
    public List<ParameterOperable> getListComparable() {
        return listComparable;
    }

    public LogicalOperator getOperator() {
        return operator;
    }

    public void setOperator(LogicalOperator operator) {
        this.operator = operator;
    }
}
