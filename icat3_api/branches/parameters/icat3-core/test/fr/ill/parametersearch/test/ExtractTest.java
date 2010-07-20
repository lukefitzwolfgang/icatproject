/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 8 juil. 2010
 */

package fr.ill.parametersearch.test;

import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.ParameterType;
import fr.ill.parametersearch.comparator.Comparator;
import fr.ill.parametersearch.exception.ParameterSearchException;
import fr.ill.parametersearch.util.ParameterSearchUtil;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.ParameterPK;

/**
 *
 * @author cruzcruz
 */
public class ExtractTest {

    /**
     * Test the JPQL generated for parameter search when a list
     * of parameter comparator are passed to the function web service.
     * 
     * @throws ParameterSearchException
     */
    @Test
    public void listComparators () throws ParameterSearchException {
        new ParameterSearchUtil().extractJPQLComparators(getListComparators());
    }

    /**
     * Return a list of comparator examples.
     * 
     * @return
     */
    private List<ParameterComparator> getListComparators () {
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("string", "scanType"));
        p1.setIsDatafileParameter("Y");
        p1.setNumeric(false);


        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("string", "scanType"));
        p3.setIsDatafileParameter("Y");
        p3.setNumeric(false);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("string", "scanType"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(false);

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("string", "scanType"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        ParameterComparator comp1 = new ParameterComparator();
        comp1.setParam(p1);
        comp1.setComparator(Comparator.START_WITH);
        comp1.setValue("comp1");
        comp1.setType(ParameterType.DATAFILE);

        ParameterComparator comp2 = new ParameterComparator();
        comp2.setParam(p2);
        comp2.setComparator(Comparator.LESS_EQUAL);
        comp2.setValue(new Float("12.23423"));
        comp2.setType(ParameterType.DATAFILE);

        ParameterComparator comp3 = new ParameterComparator();
        comp3.setParam(p3);
        comp3.setComparator(Comparator.START_WITH);
        comp3.setValue("comp3");
        comp3.setType(ParameterType.DATAFILE);

        ParameterComparator comp4 = new ParameterComparator();
        comp4.setParam(p4);
        comp4.setComparator(Comparator.START_WITH);
        comp4.setValue("comp4");
        comp4.setType(ParameterType.DATAFILE);

        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);
        lc.add(comp3);
        lc.add(comp4);

        return lc;
    }
}
