/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package fr.ill.parametersearch.test;

import fr.ill.parametersearch.ParameterType;
import fr.ill.parametersearch.exception.NoParameterTypeException;
import fr.ill.parametersearch.exception.NoParametersException;
import fr.ill.parametersearch.exception.ParameterSearchException;
import fr.ill.parametersearch.util.ParameterSearchUtil;
import fr.ill.parametersearch.util.ParameterValued;
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


    @Test
    public void datafileParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {
        
        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("string", "scanType"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        ParameterValued pv2 = new ParameterValued(ParameterType.DATAFILE, p2);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("deg", "Rotation"));
        p3.setIsDatafileParameter("Y");
        p3.setNumeric(true);

        ParameterValued pv3 = new ParameterValued(ParameterType.DATAFILE, p3);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(true);

        ParameterValued pv4 = new ParameterValued(ParameterType.DATAFILE, p4);

//        lp.add(p2);
        lp.add(pv3);
        lp.add(pv4);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);

        
       assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
    }

    @Test
    public void datasetParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {
        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("string", "scanType"));
        p2.setIsDatasetParameter("Y");
        p2.setNumeric(true);

        ParameterValued pv2 = new ParameterValued(ParameterType.DATASET, p2);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("deg", "Rotation"));
        p3.setIsDatasetParameter("Y");
        p3.setNumeric(true);

        ParameterValued pv3 = new ParameterValued(ParameterType.DATASET, p3);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p4.setIsDatasetParameter("Y");
        p4.setNumeric(true);

        ParameterValued pv4 = new ParameterValued(ParameterType.DATASET, p4);

        lp.add(pv2);
        lp.add(pv3);
        lp.add(pv4);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);
        
        assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
        
    }

    @Test
    public void sampleParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {
        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("N/A", "Size"));
        p2.setIsSampleParameter("Y");
        p2.setNumeric(true);

        ParameterValued pv2 = new ParameterValued(ParameterType.SAMPLE, p2);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(true);

        ParameterValued pv3 = new ParameterValued(ParameterType.SAMPLE, p3);
        
        lp.add(pv2);
        lp.add(pv3);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListParameter("SUPER_USER", lp, 1, -1, em);

        assertFalse("Results of investigations should not be ZERO", (li.size() == 0));

    }

    @Test
    public void allParameterTest () throws NoParameterTypeException, NoParametersException, ParameterSearchException {

        // Only for datafile and sample parameter, cause there is no
        // parameters for Dataset
        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("N/A", "Size"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(true);

        ParameterValued pv1 = new ParameterValued(ParameterType.SAMPLE, p1);

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("deg", "Rotation"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        ParameterValued pv2 = new ParameterValued(ParameterType.DATAFILE, p2);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(true);

        ParameterValued pv3 = new ParameterValued(ParameterType.SAMPLE, p3);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(true);

        ParameterValued pv4 = new ParameterValued(ParameterType.DATAFILE, p4);

        lp.add(pv1);
        lp.add(pv2);
        lp.add(pv3);
        lp.add(pv4);

        List<Investigation> li = (List<Investigation>) InvestigationSearch
                .searchByParameterListParameter("SUPER_USER",lp, 1, -1, em);


       assertFalse("Results of investigations should not be ZERO", (li.size() == 0));
    }

    protected List<ParameterValued> getListParameters () {
        List<ParameterValued> lp = new ArrayList<ParameterValued>();

        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("N/A", "Mass"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(true);

        ParameterValued pv1 = new ParameterValued(ParameterType.SAMPLE, p1);

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("Pa", "Pressure"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        ParameterValued pv2 = new ParameterValued(ParameterType.DATAFILE, p2);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(false);

        ParameterValued pv3 = new ParameterValued(ParameterType.SAMPLE, p3);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("deg", "Rotation"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(false);

        ParameterValued pv4 = new ParameterValued(ParameterType.DATAFILE, p4);

        lp.add(pv1);
        lp.add(pv2);
//        lp.add(p3);
//        lp.add(p4);

        return lp;
    }


}
