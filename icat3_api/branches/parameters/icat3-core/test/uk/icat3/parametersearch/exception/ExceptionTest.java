/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package uk.icat3.parametersearch.exception;

import uk.icat3.exceptions.NoSearchableParameterException;
import uk.icat3.exceptions.NoParameterTypeException;
import uk.icat3.exceptions.NoStringComparatorException;
import uk.icat3.exceptions.EmptyOperatorException;
import uk.icat3.exceptions.EmptyListParameterException;
import uk.icat3.exceptions.NullParameterException;
import uk.icat3.exceptions.NoElementTypeException;
import uk.icat3.exceptions.CyclicException;
import uk.icat3.exceptions.NoParametersException;
import uk.icat3.exceptions.NoNumericComparatorException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import junit.framework.JUnit4TestAdapter;
import org.junit.Test;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.Parameter;
import static org.junit.Assert.*;
import uk.icat3.parametersearch.BaseParameterSearchTest;
import uk.icat3.search.parameter.ParameterComparisonCondition;
import uk.icat3.search.parameter.ParameterLogicalCondition;
import uk.icat3.search.parameter.ParameterType;
import uk.icat3.search.parameter.ComparisonOperator;
import uk.icat3.search.parameter.util.ParameterValued;
import uk.icat3.search.DatafileSearch;
import uk.icat3.search.InvestigationSearch;
import uk.icat3.search.SampleSearch;
import uk.icat3.util.LogicalOperator;

/**
 *
 * @author cruzcruz
 */
public class ExceptionTest extends BaseParameterSearchTest {

    /**
     * List parameter error test. Test the exceptions work fine.
     */
    @Test
    public void noSearchableExceptionTest () {
        boolean exception = false;
        ParameterValued pv3 = new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile1"));
        try {
            List<ParameterValued> lp = new ArrayList<ParameterValued>();
            ParameterValued pv4 = new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile2"));
            pv3.getParam().setSearchable("N");
            lp.add(pv3);
            lp.add(pv4);
            SampleSearch.searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyListParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            exception = true;
        } catch (NullParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParametersException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            assertTrue("Should be a NoSearchableException", exception);
            pv3.getParam().setSearchable("Y");
        }
    }

    /**
     * The parameter contains a numberic value but the comparator is for a string.
     */
    @Test
    public void comparatorExceptionTest () {
        boolean exception = false;
        try {
            List<ParameterComparisonCondition> lc = new ArrayList<ParameterComparisonCondition>();
             // ------------- ComparisonOperator 1 ----------------------
            ParameterComparisonCondition comp1 = new ParameterComparisonCondition();
            comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile1")));
            comp1.setComparator(ComparisonOperator.START_WITH);
            comp1.setValue(new Double (3.14));
            lc.add(comp1);
            DatafileSearch.searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);

        } catch (EmptyListParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoNumericComparatorException ex) {
            exception = true;
        } catch (NoStringComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            assertTrue("Should be a NoNumericComparatorException", exception);
        }
    }

    @Test
    public void noParameterTypeExceptionTest () {
        boolean exception = false;
        try {
            List<ParameterValued> lp = new ArrayList<ParameterValued>();
            ParameterValued pv4 = new ParameterValued(null, new Parameter());
            lp.add(pv4);
            InvestigationSearch.searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);
        } catch (NoParameterTypeException ex) {
            exception = true;
        } catch (EmptyListParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            assertTrue("Should be a NoParameterTypeException", exception);
        }
    }

    /**
     * Add the operator itself produces an cyclic execption.
     */
    @Test
    public void cyclicExceptionTest () {
        boolean exception = false;
        try {
            ParameterLogicalCondition op1 = new ParameterLogicalCondition(LogicalOperator.OR);
            ParameterLogicalCondition op2 = new ParameterLogicalCondition(LogicalOperator.AND);
            op2.add(pcDatafile.get(0));
            op2.add(pcDatafile.get(1));
            op1.add(op2);
            op1.add(op1);
            List<Datafile> li = (List<Datafile>) DatafileSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParametersException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoElementTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyOperatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoStringComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoNumericComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CyclicException ex) {
            exception = true;
        }
        finally {
            assertTrue("Should be a CyclicException", exception);
        }
    }

    @Test
    public void nullParameterExceptionTest () {
        boolean exception = false;
        try {
            List<ParameterComparisonCondition> lc = new ArrayList<ParameterComparisonCondition>();
             // ------------- ComparisonOperator 1 ----------------------
            ParameterComparisonCondition comp1 = new ParameterComparisonCondition();
            comp1.setParameterValued(null);
            comp1.setComparator(ComparisonOperator.EQUAL);
            comp1.setValue(new Double (3.14));
            lc.add(comp1);
            DatafileSearch.searchByParameterListComparators("SUPER_USER", lc, -1, -1, em);

        } catch (EmptyListParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoNumericComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoStringComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullParameterException ex) {
            exception = true;
        }
        finally {
            assertTrue("Should be a NullParameterException", exception);
        }
    }

    /**
     * Operator is empty
     */
    @Test
    public void emptyListExceptionTest () {
        boolean exception = false;
        try {
            ParameterLogicalCondition op1 = new ParameterLogicalCondition(LogicalOperator.OR);
            ParameterLogicalCondition op2 = new ParameterLogicalCondition(LogicalOperator.AND);
//            op2.add(pcDatafile.get(0));
//            op2.add(pcDatafile.get(1));
//            opop2.add(pcDatafile.get(0));
//            o1.add(op2);
//            op1.add(pcSample.get(0));
            List<Datafile> li = (List<Datafile>) DatafileSearch
                .searchByParameterOperable("SUPER_USER", op1, 1, -1, em);
            assertTrue("Results of investigations should be 2 not " + li.size(), li.size() == 2);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParametersException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoElementTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyOperatorException ex) {
            exception = true;
        } catch (NullParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoStringComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoNumericComparatorException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            assertTrue("Should be a EmptyOperatorException", exception);
        }
    }


     /**
     * No searchable exception occurs when try to search by a datafile parameter
     * but this parameter is not relevant to datafile.
     */
    @Test
    public void noSearchableParameter () {
        boolean exception = false;
        ParameterValued pv3 = new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile1"));
        try {
            List<ParameterValued> lp = new ArrayList<ParameterValued>();
            ParameterValued pv4 = new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile2"));
            pv3.getParam().setDatafileParameter(false);
            pv3.getParam().setDatasetParameter(true);
            lp.add(pv3);
            lp.add(pv4);
            SampleSearch.searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (EmptyListParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSearchableParameterException ex) {
            exception = true;
        } catch (NullParameterException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoParametersException ex) {
            Logger.getLogger(ExceptionTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            assertTrue("Should be a NoSearchableException", exception);
            pv3.getParam().setSearchable("Y");
        }
    }

    public static junit.framework.Test suite(){
        return new JUnit4TestAdapter(ExceptionTest.class);
    }
}
