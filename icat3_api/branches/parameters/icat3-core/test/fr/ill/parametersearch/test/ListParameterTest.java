/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package fr.ill.parametersearch.test;

import fr.ill.parametersearch.exception.NoParameterTypeException;
import fr.ill.parametersearch.exception.NoParametersException;
import fr.ill.parametersearch.util.ParameterSearchUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ListParameterTest extends ILLTest {

//    @Test
    public void test22 () throws NoParametersException {
        try {
            List<Investigation> li = (List<Investigation>) InvestigationSearch.searchByParameter("SUPER_USER", new ParameterSearchUtil().extractJPQLParameters(getListParameters()), 1, -1, em);
            if (li.size() == 0) {
                assertFalse("Results of investigations should not be ZERO", true);
            }
            show(li);
        } catch (NoParameterTypeException ex) {
            Logger.getLogger(ListParameterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void datafileParameterTest () throws NoParameterTypeException, NoParametersException {
        
        List<Parameter> lp = new ArrayList<Parameter>();

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("string", "scanType"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("deg", "Rotation"));
        p3.setIsDatafileParameter("Y");
        p3.setNumeric(true);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(true);

//        lp.add(p2);
        lp.add(p3);
        lp.add(p4);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameter("SUPER_USER", searchUtil.extractJPQLParameters(lp), 1, -1, em);

        
       assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
    }

    @Test
    public void datasetParameterTest () throws NoParameterTypeException, NoParametersException {
        List<Parameter> lp = new ArrayList<Parameter>();

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

        lp.add(p2);
        lp.add(p3);
        lp.add(p4);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameter("SUPER_USER", searchUtil.extractJPQLParameters(lp), 1, -1, em);
        
        assertFalse("Results of investigations should be ZERO. (Not datasetParameter for ILL)", (boolean)(li.size() > 0));
        
    }

    @Test
    public void sampleParameterTest () throws NoParameterTypeException, NoParametersException {
        List<Parameter> lp = new ArrayList<Parameter>();

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("N/A", "Size"));
        p2.setIsSampleParameter("Y");
        p2.setNumeric(true);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(true);
        
        lp.add(p2);
        lp.add(p3);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameter("SUPER_USER", searchUtil.extractJPQLParameters(lp), 1, -1, em);

        assertFalse("Results of investigations should not be ZERO", (li.size() == 0));

    }

    @Test
    public void allParameterTest () throws NoParameterTypeException, NoParametersException {

        // Only for datafile and sample parameter, cause there is no
        // parameters for Dataset
        List<Parameter> lp = new ArrayList<Parameter>();

        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("N/A", "Size"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(true);

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("deg", "Rotation"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(true);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(true);

        lp.add(p1);
        lp.add(p2);
        lp.add(p3);
        lp.add(p4);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameter("SUPER_USER", searchUtil.extractJPQLParameters(lp), 1, -1, em);


       assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
    }

    @Override
    protected List<Parameter> getListParameters () {
        List<Parameter> lp = new ArrayList<Parameter>();

        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("N/A", "Mass"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(true);

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(false);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("deg", "Rotation"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(false);

        lp.add(p1);
        lp.add(p2);
//        lp.add(p3);
//        lp.add(p4);

        return lp;
    }


}
