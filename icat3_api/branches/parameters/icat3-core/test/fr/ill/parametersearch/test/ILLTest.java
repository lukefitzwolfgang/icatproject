/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package fr.ill.parametersearch.test;

import fr.ill.parametersearch.ParameterComparator;
import fr.ill.parametersearch.ParameterOperable;
import fr.ill.parametersearch.ParameterOperator;
import fr.ill.parametersearch.ParameterType;
import fr.ill.parametersearch.comparator.Comparator;
import fr.ill.parametersearch.exception.CyclicException;
import fr.ill.parametersearch.util.ParameterSearchUtil;
import fr.ill.parametersearch.util.ParameterValued;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import uk.icat3.entity.Dataset;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.ParameterPK;
import uk.icat3.search.InvestigationSearch;
import uk.icat3.util.BaseTest;
import uk.icat3.util.BaseTestClassTX;
import uk.icat3.util.LogicalOperator;

/**
 * THIS ONLY A CLASS TO MAKE TEST IN JPQL STATEMENTS
 * 
 * Datafile_parameter count = 1392669
 * Dataset_parameter count = 0
 * Sample_parameter count = 759
 * @author cruzcruz
 */
public class ILLTest extends BaseTestClassTX {
    public static ParameterSearchUtil searchUtil;
    private static  List<Parameter> lp;
    public static String logOp = " AND ";
    private long time;

    public ILLTest() {
        searchUtil = new ParameterSearchUtil();
    }

//    @Test
    public void atest () throws Exception {
        startTime();
         lp = em.createQuery("select p from Parameter p" ).getResultList();
         int cont = 0;
         System.out.println("************** Number of paraeters: " + lp.size());
         for (Parameter p : lp) {
             System.out.println(cont++ + " " + p.getParameterPK().getName());
        }
         List<Investigation> li = em.createQuery("select distinct OBJECT (i) from " +
               "Investigation i")
                .setMaxResults(200).getResultList();

         show (li);

         for (int i = 0; i < 4; i++) {
//             System.out.println("NO INVESTIGATION");
//             noInvestigationParmaeter2 ();
//             noInvestigationParmaeter3 ();
//             System.out.println("----------------------------");

             System.out.println("INVESTIGATION");
             investigationParmaeter2 ();
             investigationParmaeter3 ();
             System.out.println("----------------------------");

//             System.out.println("IN");
//             investigationParmaeterIN2 ();
//             investigationParmaeterIN3 ();
//             System.out.println("----------------------------");
        }
    }

//    @Test
    public void existsTest () throws Exception {
        startTime ();
        List<Investigation> li = em.createQuery("select distinct df.dataset.investigation i from " +
                "Datafile df JOIN Sample sample ON (sample.investigationId.id = i.id) WHERE " +
                "EXISTS (" +
                "select d from df.datafileParameterCollection d, sample.sampleParameterCollection s " +
                "WHERE ((d.parameter = :p) or s.parameter = :p2)" +
//                " WHERE ((d.parameter = :p) and (df.dataset.investigation.id in (select distinct s.sample.investigationId.id from SampleParameter s " +
//                " WHERE s.parameter = :p2))) " +
                ")")
                .setParameter("p", lp.get(35))
//                .setParameter("p1", lp.get(36))
                .setParameter("p2", lp.get(17))
                .getResultList();
        show(li);
    }

//    @Test
    public void equalTest () {
        List<Investigation> li = em.createQuery("select distinct d.datafile.dataset.investigation i from " +
                "DatafileParameter d WHERE " +
//                "(d.datafile = d1.datafile and " +
//                "(s.sample.investigationId = d.datafile.dataset.investigation) " +
                "( (d.parameter =  :p) or" +
                " (i.id in (select distinct s.sample.investigationId.id from SampleParameter s " +
                " WHERE s.parameter = :p2)))")
                .setParameter("p", lp.get(35))
//                .setParameter("p1", lp.get(36))
                .setParameter("p2", lp.get(17))
                .getResultList();
        show(li);
    }

    


//       @Test
    public void noInvestigationParmaeter2 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select distinct df.dataset.investigation i from " +
                 "Datafile df WHERE EXISTS (select parameter_0 from " +
                 "df.datafileParameterCollection parameter_0" +
                 ", df.datafileParameterCollection parameter_1" +
                 " WHERE(" +
                 "(parameter_0.parameter = :p AND parameter_0.numericValue > 1.0)) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0))")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

//        @Test
    public void noInvestigationParmaeter3 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select distinct df.dataset.investigation i from " +
                 "Datafile df WHERE EXISTS (select parameter_0 from " +
                 "df.datafileParameterCollection parameter_0 " +
                 ",df.datafileParameterCollection parameter_1" +
                 " WHERE " +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue > 1.0) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0)))")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }
   

//    @Test
    public void investigationParmaeter2 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select i from " +
                 "Investigation i WHERE EXISTS (select parameter_0 from " +
                 "DatafileParameter parameter_0" +
                 ",DatafileParameter parameter_1" +
                 " WHERE" +
                 "(parameter_0.datafile.dataset.investigation = i AND " +
                 "parameter_0.datafile = parameter_1.datafile AND" +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue > 1.0)) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0)) )")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

    

//     @Test
    public void investigationParmaeter3 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select distinct i from " +
                 "Investigation i WHERE EXISTS (select parameter_0 from " +
                 "DatafileParameter parameter_0" +
                 ",DatafileParameter parameter_1" +
                 " WHERE" +
                  "(parameter_0.datafile.dataset.investigation = i AND " +
                  "parameter_0.datafile = parameter_1.datafile AND " +
                  "((parameter_0.parameter = :p AND parameter_0.numericValue > 1.0)) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0) ))")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

//      @Test
    public void investigationParmaeterIN2 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select distinct i from " +
                 "Investigation i WHERE EXISTS (select parameter_0 from IN(i.datasetCollection) ds, " +
                 "IN (ds.datafileCollection) df, " +
                 " df.datafileParameterCollection parameter_0" +
                 ",df.datafileParameterCollection parameter_1" +
                 " WHERE" +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue > 1.0)) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0) )")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

//     @Test
    public void investigationParmaeterIN3 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select distinct i from " +
                 "Investigation i WHERE EXISTS (select parameter_0 from IN(i.datasetCollection) ds, " +
                 "IN (ds.datafileCollection) df, " +
                 " df.datafileParameterCollection parameter_0" +
                 ",df.datafileParameterCollection parameter_1" +
                 " WHERE" +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue > 1.0)) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0) )")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }
      
    

   
   
    

//    @Test
    public void existsParmaeterIN2 () throws Exception {
        startTime ();
        List<Investigation> li = em.createQuery("select distinct i from " +
                 "Investigation i WHERE EXISTS (select df from IN(i.datasetCollection) ds, " +
                 "IN (ds.datafileCollection) df, " +
                 " IN(df.datafileParameterCollection) parameter_0, IN(df.datafileParameterCollection) parameter_1 WHERE" +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue < 8.0))) " //+ logOp +
//                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue >= 1.0) ))")
                ).setParameter("p", lp.get(35))
//                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

//    @Test
    public void equalParmaeter2 () throws Exception {
        startTime ();
         List<Investigation> li = em.createQuery("select distinct i from " +
                 "Investigation i WHERE EXISTS (select distinct parameter_0 " +
                 " from DatafileParameter parameter_0, DatafileParameter parameter_1 WHERE" +
                 " parameter_0.datafile.dataset.investigation = i AND" +
                 " parameter_0.datafile = parameter_1.datafile AND " +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue < 8.0) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue > 1.0) ))")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

//     @Test
    public void existsParmaeter2 () throws Exception {
        List<Investigation> li = em.createQuery("select distinct i from " +
                 "Investigation i WHERE EXISTS (select df from IN(i.datasetCollection) ds, " +
                 "IN (ds.datafileCollection) df, " +
                 " IN(df.datafileParameterCollection) parameter_0, IN(df.datafileParameterCollection) parameter_1 WHERE" +
                 "((parameter_0.parameter = :p AND parameter_0.numericValue < 8.0) " + logOp +
                 "(parameter_1.parameter = :p1 AND parameter_1.numericValue > 1.0) ))")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .setMaxResults(200).getResultList();
        show(li);
    }

   
    
    

   


//     @Test
    public void exstsTest () throws Exception {
        List<Investigation> li = em.createQuery("select distinct df.dataset.investigation i from Datafile df WHERE " +
                "EXISTS (" +
                "select d from df.datafileParameterCollection d, df.datafileParameterCollection d1 WHERE " +
                "d.parameter = :p or d1.parameter = :p1" +
                ")")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .getResultList();
        show(li);
    }


    


//    public static void main (String[] args) throws ParameterSearchException {
//        try {
//            emf = Persistence.createEntityManagerFactory(TestConstants.PERSISTENCE_UNIT);
//            em = emf.createEntityManager();
//            searchUtil = new ParameterSearchUtil();
//            List<Investigation> li = (List<Investigation>) InvestigationSearch
//                    .searchByParameter("SUPER_USER"
//                        ,searchUtil.extractJPQLOperable(getOperable())
//                        , -1, -1, em);
//        } catch (NoParametersException ex) {
//            Logger.getLogger(ILLTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    

//    @Test
    public void test2 () throws Exception {
//            List<Investigation> li = (List<Investigation>) InvestigationSearch
//                    .searchByParameter("SUPER_USER"
//                        ,searchUtil.extractJPQLOperable(getOperable())
//                        , -1, -1, em);
//                    List<Parameter> lp = em.createQuery("select p from Parameter p" ).getResultList();
                    List<Investigation> li = em.createQuery("select distinct df.dataset.investigation i from Datafile df WHERE " +
                            "EXISTS (" +
                            "select d from df.datafileParameterCollection d, df.datafileParameterCollection d1 WHERE " +
                            "d.parameter = :p and d1.parameter = :p1" +
                            ")")
//                            "d.datafile = d1.datafile " +
            //                "and (d.parameter.parameterPK.name =  :parameter " +
//                            "and (d.parameter =  :parameter " +
                            // Too slow when you compare including the 'units'
            //                "and d.datafileParameterPK.units = 'V'" +
//                            "and d1.parameter = :parameter1 " +
            //                "and d1.datafileParameterPK.units = 'Å' " +
            //                "and d2.parameter = :parameter3 " +
//                            " )")
                            .setParameter("p", lp.get(35))
                            .setParameter("p1", lp.get(36))
                            .getResultList();
            //
            //        if (li.size() == 0) {
            //            assertFalse("Results of investigations should not be ZERO", true);
            //        }
            //        List<Investigation> li = em.createQuery("SELECT DISTINCT i FROM Investigation i WHERE " +
            //                "EXISTS (SELECT DISTINCT ds FROM i.datasetCollection ds)").getResultList();
            show(li);
    }


//    @Test
    public void test222 () {
//        List<Parameter> lp = em.createQuery("select p from Parameter p" ).getResultList();
        List<Investigation> li = em.createQuery("select distinct d.datafile.dataset.investigation i from " +
                "DatafileParameter d, DatafileParameter d1 WHERE " +
                "d.datafile = d1.datafile " +
                "and (d.parameter =  :p " +
                "and d1.parameter = :p1 )")
                .setParameter("p", lp.get(35))
                .setParameter("p1", lp.get(36))
                .getResultList();
    //
    //        if (li.size() == 0) {
    //            assertFalse("Results of investigations should not be ZERO", true);
    //        }
    //        List<Investigation> li = em.createQuery("SELECT DISTINCT i FROM Investigation i WHERE " +
    //                "EXISTS (SELECT DISTINCT ds FROM i.datasetCollection ds)").getResultList();
        show(li);
    }
    

//    @Test
//    public void test3 () {
//        try {
//            List<Investigation> li = (List<Investigation>) InvestigationSearch.searchByParameter("ADMIN", new ParameterSearchUtil().extractJPQLComparators(getListComparators()), 1, -1, em);
//            show(li);
//        } catch (ParameterSearchException ex) {
//            Logger.getLogger(ILLTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

//    @Test
//    public void test4 () {
//        try {
//            List<Investigation> li = (List<Investigation>) InvestigationSearch.searchByParameter("SUPER_USER", new ParameterSearchUtil().extractJPQLParameters(getListParameters()), 1, -1, em);
//            show(li);
//        } catch (NoParameterTypeExecption ex) {
//            Logger.getLogger(ILLTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//    }

//    @Test
//    public void test1 () {
////        InvestigationSearch.searchByParameter("", "", 1, -1, em);
//
////        List<Investigation> li = em.createQuery("select distinct d.datafile.dataset.investigation from DatafileParameter d " +
////                "where d.datafileParameterPK.name = 'scanType'").getResultList();
//
////        List<Investigation> li = em.createQuery("SELECT DISTINCT i FROM Investigation i WHERE " +
////                "EXISTS (SELECT DISTINCT ds FROM i.datasetCollection ds)").getResultList();
//
//        List<Investigation> li = em.createQuery("SELECT DISTINCT i FROM Investigation i WHERE EXISTS " +
//                "(SELECT DISTINCT ds FROM i.datasetCollection ds WHERE EXISTS " +
//                "(SELECT df FROM ds.datafileCollection df WHERE EXISTS " +
//                "(SELECT dfp FROM df.datafileParameterCollection dfp " +
//                "WHERE dfp.datafileParameterPK.name = 'scanType')))").getResultList();
//
//        show (li);
//    }

    protected void startTime () {
        time = new Date().getTime();
    }

    protected void show(List<Investigation> li) {
        System.out.println("******* Number of results: " + li.size());
        System.out.println("*******                    " + (new Date().getTime() - time)/1000 + " sec");
//        int cont = 1;
//        for (Investigation i : li) {
//            System.out.println (cont++ + " " + i.getInvNumber() + " " + i.getTitle());
//        }
    }

    protected static ParameterOperable getOperable () throws CyclicException  {
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

        // ------------- Comparator 3 ----------------------
        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("string", "scanType"));
        p3.setIsDatafileParameter("Y");
        p3.setNumeric(false);

        ParameterComparator comp3 = new ParameterComparator();
        comp3.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p3));
        comp3.setComparator(Comparator.START_WITH);
        comp3.setValue("");
        // ----------------------------------------------------
        
        ParameterOperator op1 = new ParameterOperator();
        ParameterOperator op2 = new ParameterOperator();
        ParameterOperator op3 = new ParameterOperator();
        ParameterOperator op4 = new ParameterOperator();

        op1.setOperator(LogicalOperator.OR);
        // With logical operator OR is extremely slower.
        //op1.setOperator(LogicalOperator.OR);
        op2.setOperator(LogicalOperator.AND);
        op3.setOperator(LogicalOperator.OR);
        op4.setOperator(LogicalOperator.AND);

        op1.add(comp1);
        op1.add(comp2);

//        op2.add(comp1);
//        op3.add(comp2);
//        op1.add(comp2);
//        op4.add(comp1);
//        op2.add(op3);
//        op1.add(op2);
//        op1.add(op3);
//        op4.add(op1);

        return op1;
    }

    private List<Parameter> getListParameters () {
        List<Parameter> lp = new ArrayList<Parameter>();

        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("N/A", "Size"));
        p1.setIsSampleParameter("Y");
        p1.setNumeric(true);

        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("string", "scanType"));
        p2.setIsDatafileParameter("Y");
        p2.setNumeric(true);

        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("N/A", "Mass"));
        p3.setIsSampleParameter("Y");
        p3.setNumeric(false);

        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("string", "scanType"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(false);

        lp.add(p1);
        lp.add(p2);
        lp.add(p3);
//        lp.add(p4);

        return lp;
    }

    /**
     * Return a list of comparator examples.
     *
     * @return
     */
    protected List<ParameterComparator> getListComparators () {

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

        // ------------- Comparator 3 ----------------------
        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("string", "scanType"));
        p3.setIsDatafileParameter("Y");
        p3.setNumeric(false);

        ParameterComparator comp3 = new ParameterComparator();
        comp3.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p3));
        comp3.setComparator(Comparator.START_WITH);
        comp3.setValue("");
        // ----------------------------------------------------

        // ------------- Comparator 4 ----------------------
        Parameter p4 = new Parameter();
        p4.setParameterPK(new ParameterPK("string", "scanType"));
        p4.setIsDatafileParameter("Y");
        p4.setNumeric(false);

        ParameterComparator comp4 = new ParameterComparator();
        comp4.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p4));
        comp4.setComparator(Comparator.START_WITH);
        comp4.setValue("comp4");
        // ----------------------------------------------------
        
        List<ParameterComparator> lc = new ArrayList<ParameterComparator>();
        lc.add(comp1);
        lc.add(comp2);
//        lc.add(comp3);
//        lc.add(comp4);

        return lc;
    }

}
