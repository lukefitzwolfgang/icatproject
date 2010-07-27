/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 30 juin 2010
 */

package fr.ill.paramsearch;

import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.ParameterOperator;
import fr.ill.parametersearch.ParameterType;
import fr.ill.parametersearch.comparator.Comparator;
import java.util.Collection;
import org.junit.Test;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.ParameterPK;
import uk.icat3.exposed.util.BaseTestClassTX;
import uk.icat3.exposed.util.TestUserLocal;
import uk.icat3.sessionbeans.search.InvestigationSearchBean;
import uk.icat3.sessionbeans.user.UserSessionLocal;
import uk.icat3.util.LogicalOperator;

/**
 *
 * @author cruzcruz
 */
public class ParameterSearchTest extends BaseTestClassTX{

    private static InvestigationSearchBean icat = new InvestigationSearchBean();
     private static UserSessionLocal tul = new TestUserLocal();

    @Test
    public void search () throws Exception {
      
            Parameter p1 = new Parameter();
            p1.setIsDatafileParameter("Y");
            p1.setNumeric(false);


            Parameter p3 = new Parameter();
           p1.setIsDatafileParameter("Y");
            p1.setNumeric(false);

            Parameter p4 = new Parameter();
            p1.setIsDatafileParameter("Y");
            p1.setNumeric(false);
            
            Parameter p2 = new Parameter();
            p1.setIsDatafileParameter("Y");
            p2.setNumeric(true);

//            ParameterComparator comp1 = new ParameterComparator();
//            comp1.setParam(p1);
//            comp1.setComparator(Comparator.START_WITH);
//            comp1.setValue("comp1");
//
//            ParameterComparator comp2 = new ParameterComparator();
//            comp2.setParam(p2);
//            comp2.setComparator(Comparator.LESS_EQUAL);
//            comp2.setValue(new Float("12.23423"));
//            comp2.setType(ParameterType.DATAFILE);
//
//            ParameterComparator comp3 = new ParameterComparator();
//            comp3.setParam(p3);
//            comp3.setComparator(Comparator.START_WITH);
//            comp3.setValue("comp3");
//            comp3.setType(ParameterType.DATAFILE);
//
//            ParameterComparator comp4 = new ParameterComparator();
//            comp4.setParam(p4);
//            comp4.setComparator(Comparator.START_WITH);
//            comp4.setValue("comp4");
//            comp4.setType(ParameterType.DATAFILE);
//
//
//            ParameterOperator op1 = new ParameterOperator();
//            ParameterOperator op2 = new ParameterOperator();
//            ParameterOperator op3 = new ParameterOperator();
//            ParameterOperator op4 = new ParameterOperator();
//
//            op1.setOperator(LogicalOperator.AND);
//            op2.setOperator(LogicalOperator.AND);
//            op3.setOperator(LogicalOperator.OR);
//            op4.setOperator(LogicalOperator.AND);
//
//            op2.add(comp2);
//            op3.add(comp3);
//            op1.add(comp1);
//            op4.add(comp4);
//
//            op1.add(op2);
//            op2.add(op3);
//            op3.add(op4);
//
//
//
//            icat.setEntityManager(em);
////            icat.setUserSession(tul);
//
//            ParameterComparator commp5 = new ParameterComparator();
//
//            Parameter scanType = new Parameter();
//            scanType.setParameterPK(new ParameterPK("string", "scanType"));
//
//            commp5.setParam(scanType);
//            commp5.setComparator(Comparator.START_WITH);
//            commp5.setValue("2");
//            commp5.setType(ParameterType.DATAFILE);
//
//            Collection<Investigation> li = icat.searchByParameter("", commp5);
//
//            int cont = 1;
//            for (Investigation i : li) {
//                System.out.println (cont++ + "**" + i.getInvNumber() + "***" + i.getTitle());
//            }

//            icat.searchByParameter(null, op2);
//            icat.searchByParameter(null, op3);
//            icat.searchByParameter(null, op4);
            
        
    }
}
