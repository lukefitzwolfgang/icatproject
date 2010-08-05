/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 5 août 2010
 */

package uk.icat3.parametersearch;

import java.util.Date;
import java.util.List;
import org.junit.Test;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.Parameter;
import uk.icat3.util.BaseTestClassTX;

/**
 * This class if for test differents implementation of JPQL statements.
 * Here we probe the different time executions using different tatics
 * (INNER join, without any JOIN, with EXISTS).
 *
 * Results.
 *
 * When the database is big, use EXISTS and IN give best results. ('existsInTest' method)
 * When the database is not big, use only IN give best results. ('inTest' method)
 *
 * @author cruzcruz
 */
public class MyTestProbes extends BaseTestClassTX {
    private long time;
    private List<Parameter> lp;
    private String logOp = " AND ";

    @Test
    public void atest () throws Exception {
        startTime();
         lp = em.createQuery("select p from Parameter p" ).getResultList();
         int cont = 0;
         System.out.println("************** Number of parameters: " + lp.size());
         for (Parameter p : lp) {
             System.out.println(cont++ + " " + p.getParameterPK().getName());
        }
         
//         List<Investigation> li = em.createQuery("select distinct OBJECT (i) from " +
//               "Investigation i")
//                .setMaxResults(200).getResultList();
//
//         show (li);

         existsINTest();
         inTest();
         existsINTest();
         inTest();
         
         

//         for (int i = 0; i < 4; i++) {
////             System.out.println("NO INVESTIGATION");
////             noInvestigationParmaeter2 ();
////             noInvestigationParmaeter3 ();
////             System.out.println("----------------------------");
//
//             System.out.println("INVESTIGATION");
//             investigationParmaeter2 ();
//             investigationParmaeter3 ();
//             System.out.println("----------------------------");
//
////             System.out.println("IN");
////             investigationParmaeterIN2 ();
////             investigationParmaeterIN3 ();
////             System.out.println("----------------------------");
//        }
    }

//    @Test
    public void existsINTest () throws Exception {
        startTime ();
        List<Investigation> li = em.createQuery("select distinct i from " +
                "Investigation i WHERE " +
                "EXISTS (" +
                "select d from IN(i.datasetCollection) ds, IN(ds.datafileCollection)df, " +
                "IN(df.datafileParameterCollection) d, " +
                "IN(i.sampleCollection ) samp, IN (samp.sampleParameterCollection) s " +
                "WHERE ((d.parameter = :p) or s.parameter = :p2)" +
//                " WHERE ((d.parameter = :p) and (df.dataset.investigation.id in (select distinct s.sample.investigationId.id from SampleParameter s " +
//                " WHERE s.parameter = :p2))) " +
                ")")
                .setParameter("p", lp.get(31))
//                .setParameter("p1", lp.get(36))
                .setParameter("p2", lp.get(17))
                .setMaxResults(200)
                .getResultList();

        System.out.println("------------- existsTest");
        show(li);
    }

    public void inTest () throws Exception {
        startTime ();
        List<Investigation> li = em.createQuery("select distinct i from " +
                "Investigation i, " +
                "IN(i.datasetCollection) ds, IN(ds.datafileCollection)df, " +
                "IN(df.datafileParameterCollection) d, " +
                "IN(i.sampleCollection ) samp, IN (samp.sampleParameterCollection) s " +
                "WHERE ((d.parameter = :p) or s.parameter = :p2)"
//                " WHERE ((d.parameter = :p) and (df.dataset.investigation.id in (select distinct s.sample.investigationId.id from SampleParameter s " +
//                " WHERE s.parameter = :p2))) " +
                )
                .setParameter("p", lp.get(31))
//                .setParameter("p1", lp.get(36))
                .setParameter("p2", lp.get(17))
                .setMaxResults(200)
                .getResultList();

        System.out.println("------------- notExistsTest");
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
                .setParameter("p", lp.get(32))
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
}
