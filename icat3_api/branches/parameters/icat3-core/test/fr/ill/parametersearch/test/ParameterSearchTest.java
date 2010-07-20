package fr.ill.parametersearch.test;

/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 29 juin 2010
 */



import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.ParameterOperator;
import fr.ill.parametersearch.util.ParameterSearchUtil;
import fr.ill.parametersearch.comparator.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import uk.icat3.entity.Parameter;
import uk.icat3.util.LogicalOperator;

/**
 *
 * @author cruzcruz
 */
public class ParameterSearchTest {

    public static void main(String[] args) {
        try {
            Parameter p1 = new Parameter();
            p1.setIsDatafileParameter("Y");
            Parameter p2 = new Parameter();
            p1.setIsDatafileParameter("Y");
            p2.setNumeric(true);
            ParameterComparator comp1 = new ParameterComparator();
            comp1.setParam(p1);
            comp1.setComparator(Comparator.START_WITH);
            comp1.setValue("cosa");
            ParameterComparator comp2 = new ParameterComparator();
            comp2.setParam(p2);
            comp2.setComparator(Comparator.LESS_EQUAL);
            comp2.setValue(new Float("12.23423"));
           ParameterOperator op1 = new ParameterOperator();
            ParameterOperator op2 = new ParameterOperator();
            ParameterOperator op3 = new ParameterOperator();
            ParameterOperator op4 = new ParameterOperator();

            op1.setOperator(LogicalOperator.AND);
            op2.setOperator(LogicalOperator.AND);
            op3.setOperator(LogicalOperator.OR);
            op4.setOperator(LogicalOperator.AND);
           
            op2.add(comp1);
            op3.add(comp2);
            op1.add(comp2);
            op4.add(comp1);
            op2.add(op3);
            op1.add(op2);
            op1.add(op3);
            op4.add(op1);
           
            ParameterSearchUtil psu = new ParameterSearchUtil();

//            String[] res = psu.extractJPQLComparators(op4);
            
//            System.out.println(res[0]);
//            System.out.println(res[1]);
        } catch (Exception ex) {
            Logger.getLogger(ParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
