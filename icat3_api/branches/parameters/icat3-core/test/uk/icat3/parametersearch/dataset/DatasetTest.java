/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package uk.icat3.parametersearch.dataset;

import uk.icat3.parametersearch.exception.NoParameterTypeException;
import uk.icat3.parametersearch.exception.NoParametersException;
import uk.icat3.parametersearch.exception.ParameterSearchException;
import uk.icat3.parametersearch.util.ParameterValued;
import java.util.ArrayList;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import uk.icat3.entity.Dataset;
import static org.junit.Assert.*;
import uk.icat3.parametersearch.BaseParameterSearchTest;
import uk.icat3.parametersearch.ParameterComparator;
import uk.icat3.parametersearch.ParameterOperator;
import uk.icat3.parametersearch.ParameterType;
import uk.icat3.search.DatasetSearch;
import uk.icat3.util.LogicalOperator;

/**
 *
 * @author cruzcruz
 */
public class DatasetTest extends BaseParameterSearchTest {
    

    @Test
    public void listParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {
        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        ParameterValued pv2 = new ParameterValued(ParameterType.DATASET, parameter.get("dataset1"));
        ParameterValued pv3 = new ParameterValued(ParameterType.DATASET, parameter.get("dataset2"));

        lp.add(pv2);
//        lp.add(pv3);
//        lp.add(pv4);

        List<Dataset> li = (List<Dataset>) DatasetSearch
                .searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);

        showDatasets(li);
        assertTrue("Results of investigations should not be ZERO", (li.size() == 1));
        
    }

    /**
     * List comparators Test
     *
     * @throws NoParameterTypeException
     * @throws ParameterSearchException
     */
    @Test
    public void listComparatorTest () throws NoParameterTypeException, ParameterSearchException {

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(pcDataset.get(1));

        List<Dataset> ld = (List<Dataset>) DatasetSearch
                .searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);

       showDatasets(ld);
       assertTrue("Results of investigations should not be ZERO", (ld.size() == 1));
    }


    /**
     * Operable test
     *
     * @throws NoParameterTypeException
     * @throws ParameterSearchException
     */
    @Test
    public void operableTest () throws NoParameterTypeException, ParameterSearchException {
        ParameterOperator op1 = new ParameterOperator(LogicalOperator.OR);
        ParameterOperator op2 = new ParameterOperator(LogicalOperator.OR);

        op2.add(pcDataset.get(0));
        op2.add(pcDataset.get(1));
        op1.add(op2);
//        op1.add(pcDataset.get(2));

        List<Dataset> li = (List<Dataset>) DatasetSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);

        showDatasets(li);
       assertTrue("Results of investigations should be 2 not " + li.size(), (li.size() == 2));
    }
    

    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(DatasetTest.class);
    }

}
