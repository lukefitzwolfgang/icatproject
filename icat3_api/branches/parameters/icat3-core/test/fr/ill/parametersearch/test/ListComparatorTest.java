/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package fr.ill.parametersearch.test;

import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.ParameterType;
import fr.ill.parametersearch.comparator.Comparator;
import fr.ill.parametersearch.exception.NoParameterTypeException;
import fr.ill.parametersearch.exception.NoParametersException;
import fr.ill.parametersearch.exception.ParameterSearchException;
import fr.ill.parametersearch.util.ParameterValued;
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
        p1.setParameterPK(new ParameterPK("s", "Time duration"));
        p1.setIsDatafileParameter("Y");
        p1.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p1));
        comp1.setComparator(Comparator.GREATER_EQUAL);
        comp1.setValue(new Float (1));
        // ----------------------------------------------------

        // ------------- Comparator 2 -------------------------
        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("Å", "Wavelength"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        ParameterComparator comp2 = new ParameterComparator();
        comp2.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p2));
        comp2.setComparator(Comparator.LESS_EQUAL);
        comp2.setValue(new Float (8.0));
        // ----------------------------------------------------

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);
        
       assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
    }

    @Test
    public void datasetParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {

        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("string", "scanType"));
        p2.setIsDatasetParameter("Y");
        p2.setNumeric(true);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("deg", "Rotation"));
        p3.setIsDatasetParameter("Y");
        p3.setNumeric(true);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p4.setIsDatasetParameter("Y");
        p4.setNumeric(true);

        lp.add(new ParameterValued(ParameterType.DATASET, p2));
        lp.add(new ParameterValued(ParameterType.DATASET, p3));
        lp.add(new ParameterValued(ParameterType.DATASET, p4));

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);
        
        assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
        
    }

    @Test
    public void sampleParameterTest () throws NoParameterTypeException, ParameterSearchException {
         // ------------- Comparator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("N/A", "Mass"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(false);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p1));
        comp1.setComparator(Comparator.START_WITH);
        comp1.setValue("10");
        // ----------------------------------------------------

        // ------------- Comparator 2 ----------------------
        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("N/A", "Consistance"));
        p2.setIsSampleParameter("Y");
        p2.setNumeric(false);

        ParameterComparator comp2 = new ParameterComparator();
        comp2.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p2));
        comp2.setComparator(Comparator.EQUAL);
        comp2.setValue("solution");
        // ----------------------------------------------------

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, 1, -1, em);

        show(li);

        assertFalse("Results of investigations should not be ZERO", (li.size() == 0));

    }

    @Test
    public void allParameterTest () throws NoParameterTypeException, ParameterSearchException {

        // ------------- Comparator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("s", "Time duration"));
        p1.setIsDatafileParameter("Y");
        p1.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p1));
        comp1.setComparator(Comparator.GREATER_EQUAL);
        comp1.setValue(new Float (1));
        // ----------------------------------------------------

        // ------------- Comparator 2 ----------------------
        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("N/A", "Mass"));
        p2.setIsSampleParameter("Y");
        p2.setNumeric(false);

        ParameterComparator comp2 = new ParameterComparator();
        comp2.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p2));
        comp2.setComparator(Comparator.START_WITH);
        comp2.setValue("10");
        // ----------------------------------------------------

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListComparators("SUPER_USER", lc, 1, -1, em);


       assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
    }

    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(ListComparatorTest.class);
    }
}
