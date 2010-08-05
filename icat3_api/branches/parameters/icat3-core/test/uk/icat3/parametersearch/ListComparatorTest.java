/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package uk.icat3.parametersearch;

import uk.icat3.parametersearch.comparator.Comparator;
import uk.icat3.parametersearch.exception.NoParameterTypeException;
import uk.icat3.parametersearch.exception.NoParametersException;
import uk.icat3.parametersearch.exception.ParameterSearchException;
import uk.icat3.parametersearch.util.ParameterValued;
import java.util.ArrayList;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.ParameterPK;
import uk.icat3.search.InvestigationSearch;

/**
 *
 * @author cruzcruz
 */
public class ListComparatorTest extends ILLTest {

    @Test
    public void datafileParameterTest () throws NoParameterTypeException, ParameterSearchException {
        
        // ------------- Comparator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("deg", "datafile1"));
        p1.setIsDatafileParameter("Y");
        p1.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p1));
        comp1.setComparator(Comparator.EQUAL);
        comp1.setValue(new Double (3.14));
        // ----------------------------------------------------

        // ------------- Comparator 2 -------------------------
        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("deg", "datafile2"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        ParameterComparator comp2 = new ParameterComparator();
        comp2.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p2));
        comp2.setComparator(Comparator.EQUAL);
        comp2.setValue(new Double (5.2));
        // ----------------------------------------------------

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);
        
       assertTrue("Results of investigations should not be ZERO", (li.size() == 1));
    }

    @Test
    public void datasetParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {

       // ------------- Comparator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("deg", "dataset1"));
        p1.setIsDatasetParameter("Y");
        p1.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATASET, p1));
        comp1.setComparator(Comparator.EQUAL);
        comp1.setValue(new Double (2.1));
        // ----------------------------------------------------

        // ------------- Comparator 2 -------------------------
//        Parameter p2 = new Parameter();
//        p2.setParameterPK(new ParameterPK("deg", "datafile2"));
//        p2.setIsDatafileParameter("Y");
//        p2.setNumeric(true);
//
//        ParameterComparator comp2 = new ParameterComparator();
//        comp2.setParameterValued(new ParameterValued(ParameterType.DATASET, p2));
//        comp2.setComparator(Comparator.EQUAL);
//        comp2.setValue(new Double (5.2));
        // ----------------------------------------------------

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
//        lc.add(comp2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);

       assertTrue("Results of investigations should not be ZERO", (li.size() == 1));
        
    }

    @Test
    public void sampleParameterTest () throws NoParameterTypeException, ParameterSearchException {
         // ------------- Comparator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("deg", "sample1"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p1));
        comp1.setComparator(Comparator.EQUAL);
        comp1.setValue(new Double(2.2));
        // ----------------------------------------------------

        // ------------- Comparator 2 ----------------------
//        Parameter p2 = new Parameter();
//        p2.setParameterPK(new ParameterPK("N/A", "Consistance"));
//        p2.setIsSampleParameter("Y");
//        p2.setNumeric(false);
//
//        ParameterComparator comp2 = new ParameterComparator();
//        comp2.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p2));
//        comp2.setComparator(Comparator.EQUAL);
//        comp2.setValue("solution");
        // ----------------------------------------------------

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
//        lc.add(comp2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, 1, -1, em);

        show(li);

        assertTrue("Results of investigations should not be ZERO", (li.size() == 1));

    }

    @Test
    public void allParameterTest () throws NoParameterTypeException, ParameterSearchException {

        // ------------- Comparator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("deg", "datafile1"));
        p1.setIsDatafileParameter("Y");
        p1.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p1));
        comp1.setComparator(Comparator.EQUAL);
        comp1.setValue(new Double (3.14));
        // ----------------------------------------------------

        // ------------- Comparator 2 ----------------------
        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("deg", "sample1"));
        p2.setIsSampleParameter("Y");
        p2.setNumeric(true);

        ParameterComparator comp2 = new ParameterComparator();
        comp2.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p2));
        comp2.setComparator(Comparator.EQUAL);
        comp2.setValue(new Double(2.2));
        // ----------------------------------------------------

        // ---------------- Comparator 3 ----------------------
        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("deg", "dataset1"));
        p3.setIsDatasetParameter("Y");
        p3.setNumeric(true);

        ParameterComparator comp3 = new ParameterComparator();
        comp3.setParameterValued(new ParameterValued(ParameterType.DATASET, p3));
        comp3.setComparator(Comparator.EQUAL);
        comp3.setValue(new Double (2.1));
        // ----------------------------------------------------
        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);
        lc.add(comp3);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, 1, -1, em);


       assertTrue("Results of investigations should not be ZERO", (li.size() == 1));
    }

    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(ListComparatorTest.class);
    }
}
