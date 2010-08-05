/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package uk.icat3.parametersearch;

import uk.icat3.parametersearch.exception.NoParameterTypeException;
import uk.icat3.parametersearch.exception.NoParametersException;
import uk.icat3.parametersearch.exception.ParameterSearchException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.icat3.entity.Investigation;
import uk.icat3.search.InvestigationSearch;
import uk.icat3.util.LogicalOperator;

/**
 *
 * @author cruzcruz
 */
public class OperableTest extends ILLTest {

    @Test
    public void datafileParameterTest () throws NoParameterTypeException, ParameterSearchException {


        ParameterOperator op1 = new ParameterOperator(LogicalOperator.OR);
        ParameterOperator op2 = new ParameterOperator(LogicalOperator.AND);

        op2.add(pcDatafile.get(0));
        op2.add(pcDatafile.get(1));
        op1.add(op2);
        op1.add(pcDatafile.get(2));

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);
        

       assertTrue("Results of investigations should be 2 not " + li.size(), (li.size() == 2));
    }

    @Test
    public void datasetParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(pcDataset.get(0));

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);

       assertTrue("Results of investigations should not be ZERO", (li.size() == 1));

    }

    @Test
    public void sampleParameterTest () throws NoParameterTypeException, ParameterSearchException {

        ParameterOperator op1 = new ParameterOperator();
        ParameterOperator op2 = new ParameterOperator();
        ParameterOperator op3 = new ParameterOperator();
        ParameterOperator op4 = new ParameterOperator();

        op1.setOperator(LogicalOperator.AND);
        op2.setOperator(LogicalOperator.AND);
        op3.setOperator(LogicalOperator.OR);
        op4.setOperator(LogicalOperator.AND);

        op1.add(pcSample.get(0));

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);


        assertTrue("Results of investigations should be 1 not " + li.size(), (li.size() == 1));
    }

    @Test
    public void allParameterTest () throws NoParameterTypeException, ParameterSearchException {

        ParameterOperator op1 = new ParameterOperator();
        ParameterOperator op2 = new ParameterOperator();

        op1.setOperator(LogicalOperator.OR);
        op2.setOperator(LogicalOperator.AND);

        // (comp4 OR (comp1 AND comp2))
        op2.add(pcDatafile.get(0));
        op2.add(pcDataset.get(0));

        op1.add(pcDatafile.get(2));
        op1.add(op2);
        

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);


       assertTrue("Results of investigations should be 2 not " + li.size(), (li.size() == 2));
    }

    @Test
    public void allParameterTest2 () throws NoParameterTypeException, ParameterSearchException {

        ParameterOperator op1 = new ParameterOperator();
        ParameterOperator op2 = new ParameterOperator();

        op1.setOperator(LogicalOperator.AND);
        op2.setOperator(LogicalOperator.AND);

        // (comp4 OR (comp1 AND comp2))
        op2.add(pcDatafile.get(0));
        op2.add(pcDataset.get(0));

        op1.add(pcDatafile.get(2));
        op1.add(op2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);


       assertTrue("Results of investigations should be ZERO not " + li.size(), (li.size() == 0));
    }

    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(OperableTest.class);
    }
}
