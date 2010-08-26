/*
 * This code is developed in Institut Laue-Langevin (France).
 * Its goal is the implementation of parameter search into ICAT Web Service
 * 
 * Created on 6 juil. 2010
 */

package uk.icat3.parametersearch;

import uk.icat3.search.parameter.ParameterType;
import uk.icat3.search.parameter.ParameterComparisonCondition;
import uk.icat3.search.parameter.ParameterCondition;
import uk.icat3.search.parameter.ParameterLogicalCondition;
import uk.icat3.search.parameter.ComparisonOperator;
import uk.icat3.exception.CyclicException;
import uk.icat3.exception.EmptyOperatorException;
import uk.icat3.search.parameter.util.ParameterSearchUtil;
import uk.icat3.search.parameter.util.ParameterValued;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.icat3.entity.Datafile;
import uk.icat3.entity.DatafileFormat;
import uk.icat3.entity.DatafileParameter;
import uk.icat3.entity.DatafileParameterPK;
import uk.icat3.entity.Dataset;
import uk.icat3.entity.DatasetParameter;
import uk.icat3.entity.DatasetParameterPK;
import uk.icat3.entity.IcatAuthorisation;
import uk.icat3.entity.IcatRole;
import uk.icat3.entity.Investigation;
import uk.icat3.entity.Parameter;
import uk.icat3.entity.ParameterPK;
import uk.icat3.entity.Sample;
import uk.icat3.entity.SampleParameter;
import uk.icat3.entity.SampleParameterPK;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.manager.DataFileManager;
import uk.icat3.manager.DataSetManager;
import uk.icat3.manager.InvestigationManager;
import uk.icat3.manager.ManagerUtil;
import uk.icat3.search.InvestigationSearch;
import uk.icat3.util.BaseTest;
import uk.icat3.util.BaseTestClassTX;
import uk.icat3.util.ElementType;
import uk.icat3.util.LogicalOperator;

/**
 * THIS ONLY A CLASS TO MAKE TEST IN JPQL STATEMENTS
 * 
 * Datafile_parameter count = 1392669
 * Dataset_parameter count = 0
 * Sample_parameter count = 759
 * @author cruzcruz
 */
public class BaseParameterSearchTest extends BaseTest {
    public static ParameterSearchUtil searchUtil;
    private static  List<Parameter> lp;
    public static String logOp = " AND ";
    private long time;
    private static Random random = new Random();
    private static final String VALID_USER_FOR_INVESTIGATION  = "TEST";
    protected static List<Object> removeEntities;
    protected static ArrayList<ParameterComparisonCondition> pcDatafile;
    protected static ArrayList<ParameterComparisonCondition> pcDataset;
    protected static ArrayList<ParameterComparisonCondition> pcSample;
    protected static Map<String, Parameter> parameter;

    public BaseParameterSearchTest() {
        searchUtil = new ParameterSearchUtil();
    }

    // TODO: create parameter first
    private static Parameter createParameter (String units, String name) {
        Parameter p = new Parameter();

        p.setParameterPK(new ParameterPK (units, name));
        p.setSearchable("Y");
        p.setNumeric(true);
        p.setIsDatafileParameter("Y");
        p.setIsSampleParameter("Y");
        p.setIsDatasetParameter("Y");
        p.setVerified(true);

        Timestamp timeSQL = new Timestamp(new Date().getTime());

        p.setCreateTime(timeSQL);
        p.setModTime(timeSQL);
        p.setCreateId(VALID_USER_FOR_INVESTIGATION);
        p.setModId(VALID_USER_FOR_INVESTIGATION);

        em.persist(p);

        parameter.put(name, p);
        return p;
    }

    private static Dataset createDataset (Investigation inv, String name) throws NoSuchObjectFoundException, ValidationException, InsufficientPrivilegesException {
        int i = random.nextInt();
        Dataset dat = new Dataset();
        dat.setName(name);
        dat.setInvestigation(inv);
        dat.setDatasetType("test");

        return DataSetManager.createDataSet(VALID_USER_FOR_INVESTIGATION, dat, em);
    }

    private static DatasetParameter createDatasetParameter (Dataset dat, Parameter p, Number numb) {
        DatasetParameter datParam = new DatasetParameter ();

        datParam.setDataset(dat);
        datParam.setDatasetParameterPK(new DatasetParameterPK(p.getParameterPK().getUnits(),
                        p.getParameterPK().getName(), dat.getId()));
        
        datParam.setNumericValue(numb.doubleValue());

        Timestamp timeSQL = new Timestamp(new Date().getTime());

        datParam.setCreateTime(timeSQL);
        datParam.setModTime(timeSQL);
        datParam.setCreateId(VALID_USER_FOR_INVESTIGATION);
        datParam.setModId(VALID_USER_FOR_INVESTIGATION);

        em.persist(datParam);

        return datParam;
    }

    private static Datafile createDatafile (Dataset dat, String name) throws InsufficientPrivilegesException, NoSuchObjectFoundException, ValidationException {
        Datafile file = new Datafile();
        Collection<DatafileFormat> datafileFormat = (Collection<DatafileFormat>)executeListResultCmd("select d from DatafileFormat d");
        file.setDatafileFormat(datafileFormat.iterator().next());
        file.setName(name);
        file.setDataset(dat);

        return DataFileManager.createDataFile(VALID_USER_FOR_INVESTIGATION, file, em);
    }

    private static DatafileParameter createDatafileParameter (Datafile dataFile, Parameter p, Number numb) {
        DatafileParameter dfParam = new DatafileParameter ();
        dfParam.setDatafile(dataFile);
        dfParam.setDatafileParameterPK(new DatafileParameterPK(p.getParameterPK().getUnits(),
                            p.getParameterPK().getName(), dataFile.getId()));
        dfParam.setNumericValue(numb.doubleValue());

        Timestamp timeSQL = new Timestamp(new Date().getTime());

        dfParam.setCreateTime(timeSQL);
        dfParam.setModTime(timeSQL);
        dfParam.setCreateId(VALID_USER_FOR_INVESTIGATION);
        dfParam.setModId(VALID_USER_FOR_INVESTIGATION);

        em.persist(dfParam);

        return dfParam;
    }

    private static Sample createSample (Investigation inv, String name) {
        Sample samp = new Sample();

        samp.setInvestigationId(inv);

        samp.setName(name);
        samp.setSafetyInformation("algo");

        Timestamp timeSQL = new Timestamp(new Date().getTime());

        samp.setCreateTime(timeSQL);
        samp.setModTime(timeSQL);
        samp.setCreateId(VALID_USER_FOR_INVESTIGATION);
        samp.setModId(VALID_USER_FOR_INVESTIGATION);
        em.persist(samp);

        return samp;
    }

    private static SampleParameter createSampleParameter (Sample samp, Parameter p, Number numb) {
        SampleParameter sampParam = new SampleParameter();

        sampParam.setSample(samp);
        sampParam.setSampleParameterPK(new SampleParameterPK(p.getParameterPK().getUnits(),
                        p.getParameterPK().getName(), samp.getId()));

        Timestamp timeSQL = new Timestamp(new Date().getTime());

        sampParam.setNumericValue(numb.doubleValue());

        sampParam.setCreateTime(timeSQL);
        sampParam.setModTime(timeSQL);
        sampParam.setCreateId(VALID_USER_FOR_INVESTIGATION);
        sampParam.setModId(VALID_USER_FOR_INVESTIGATION);

        em.persist(sampParam);

        return sampParam;
    }

    private static IcatAuthorisation createTestAutho () {
        IcatAuthorisation autho = new IcatAuthorisation();
        Timestamp timeSQL = new Timestamp(new Date().getTime());
        autho.setUserId(VALID_USER_FOR_INVESTIGATION);

        autho.setRole(em.find(IcatRole.class, "SUPER"));
        autho.setElementType(ElementType.INVESTIGATION);
        autho.setCreateTime(timeSQL);
        autho.setModTime(timeSQL);
        autho.setCreateId(VALID_USER_FOR_INVESTIGATION);
        autho.setModId(VALID_USER_FOR_INVESTIGATION);
        em.persist(autho);
        return autho;
    }

    private static Investigation createInvestigation(String title) throws NoSuchObjectFoundException, InsufficientPrivilegesException, ValidationException {
        int i = random.nextInt();
        Investigation investigation = new Investigation();
        investigation.setId((long)i);
        investigation.setTitle(title + " "+ i);
        investigation.setInvNumber("9-10-" + i);
        investigation.setInvType("experiment");
        investigation.setFacility(ManagerUtil.getFacility(em).getFacilityShortName());

        return InvestigationManager.createInvestigation(VALID_USER_FOR_INVESTIGATION, investigation, em);
    }

    @BeforeClass
    public static void create ()  {
        setUp();
        parameter = new HashMap<String, Parameter>();
        removeEntities = new ArrayList<Object>();
        try {
            IcatAuthorisation autho = createTestAutho();
            Investigation inv = createInvestigation("Investigation 1");
            Investigation inv2 = createInvestigation("Investigation 2");
            Sample samp = createSample(inv, "Sample_1");
            Sample samp2 = createSample(inv2, "Sample_2");
            Dataset dat = createDataset(inv, "dataset_1");
            Dataset dat2 = createDataset(inv2, "dataset_2");
            Datafile datFile = createDatafile(dat, "datafile_1");
            Datafile datFile2 = createDatafile(dat2, "datafile_2");

            Parameter sp1_1 = createParameter("deg", "sample1");
            Parameter ds1_1 = createParameter("deg", "dataset1");
            Parameter df1_1 = createParameter("deg", "datafile1");
            Parameter df1_2 = createParameter("deg", "datafile2");
            removeEntities.add(sp1_1);
            removeEntities.add(ds1_1);
            removeEntities.add(df1_1);
            removeEntities.add(df1_2);

            Parameter df2_1 = createParameter("deg", "datafile2_1");
            Parameter sp2_1 = createParameter("deg", "sample2_1");
            Parameter ds2_1 = createParameter("deg", "dataset2_1");
            removeEntities.add(df2_1);
            removeEntities.add(sp2_1);
            removeEntities.add(ds2_1);

            removeEntities.add(createSampleParameter(samp, sp1_1, new Double(2.2)));
            removeEntities.add(createSampleParameter(samp2, sp2_1, new Double(21.2)));
            removeEntities.add(createDatasetParameter(dat, ds1_1, new Double(2.1)));
            removeEntities.add(createDatasetParameter(dat2, ds2_1, new Double(21.1)));
            removeEntities.add(createDatafileParameter(datFile, df1_1, new Double (3.14)));
            removeEntities.add(createDatafileParameter(datFile, df1_2, new Double (5.2)));
            removeEntities.add(createDatafileParameter(datFile2, df2_1, new Double(21.0000002)));

            removeEntities.add(datFile);
            removeEntities.add(dat);
            removeEntities.add(samp);
            removeEntities.add(inv);

            removeEntities.add(datFile2);
            removeEntities.add(dat2);
            removeEntities.add(samp2);
            removeEntities.add(inv2);
            // Be sure that autho for TEST doesn't exists
            if (autho != null)
                removeEntities.add(autho);

//            removeEntities();
            //        createDatafile(createDataset(createInvestigation()));
        } catch (NoSuchObjectFoundException ex) {
            Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
            removeEntities();
        } catch (InsufficientPrivilegesException ex) {
            Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
            removeEntities();
        } catch (ValidationException ex) {
            Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
            removeEntities();
        }

        createListComparators();
    }

    @AfterClass
    public static void removeEntities () {
//        setUp();
        for (Object obj : removeEntities) {
            if (obj.getClass() == Dataset.class) {
                try {
                    DataSetManager.removeDataSet(VALID_USER_FOR_INVESTIGATION, (Dataset) obj, em);
                } catch (NoSuchObjectFoundException ex) {
                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InsufficientPrivilegesException ex) {
                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (obj.getClass() == Investigation.class) {
                try {
                    InvestigationManager.removeInvestigation(VALID_USER_FOR_INVESTIGATION, (Investigation) obj, em);
                } catch (NoSuchObjectFoundException ex) {
                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InsufficientPrivilegesException ex) {
                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (obj.getClass() == Datafile.class) {
                try {
                    DataFileManager.removeDataFile(VALID_USER_FOR_INVESTIGATION, (Datafile) obj, em);
                } catch (NoSuchObjectFoundException ex) {
                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InsufficientPrivilegesException ex) {
                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            else if (obj.getClass() == DatafileParameter.class)
//                try {
//                DataFileManager.removeDatafileParameter(VALID_USER_FOR_INVESTIGATION, (DatafileParameter) obj, em);
//            } catch (InsufficientPrivilegesException ex) {
//                Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
//            } catch (NoSuchObjectFoundException ex) {
//                Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            else if (obj.getClass() == DatasetParameter.class) {
//                try {
//                    DataSetManager.removeDataSetParameter(VALID_USER_FOR_INVESTIGATION, (DatasetParameter) obj, em);
//                } catch (InsufficientPrivilegesException ex) {
//                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
//                } catch (NoSuchObjectFoundException ex) {
//                    Logger.getLogger(BaseParameterSearchTest.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
            else
                em.remove(obj);
        }

        tearDown();
        removeEntities = null;
    }


    

    

    protected void startTime () {
        time = new Date().getTime();
    }

    protected void showFiles (List<Datafile> ld) {
        System.out.println("******* Number of results: " + ld.size());
        System.out.println("*******                    " + (new Date().getTime() - time)/1000 + " sec");
        int cont = 1;
        for (Datafile d : ld) {
            System.out.println (cont++ + " " + d.getName());
        }
    }

    protected void showDatasets (List<Dataset> ld) {
        System.out.println("******* Number of results: " + ld.size());
        System.out.println("*******                    " + (new Date().getTime() - time)/1000 + " sec");
        int cont = 1;
        for (Dataset d : ld) {
            System.out.println (cont++ + " " + d.getName());
        }
    }

    protected void showSamples (List<Sample> ld) {
        System.out.println("******* Number of results: " + ld.size());
        System.out.println("*******                    " + (new Date().getTime() - time)/1000 + " sec");
        int cont = 1;
        for (Sample d : ld) {
            System.out.println (cont++ + " " + d.getName());
        }
    }

    protected void showInv(List<Investigation> li) {
        System.out.println("******* Number of results: " + li.size());
        System.out.println("*******                    " + (new Date().getTime() - time)/1000 + " sec");
        int cont = 1;
        for (Investigation i : li) {
            System.out.println (cont++ + " " + i.getInvNumber() + " " + i.getTitle());
        }
    }

    protected static ParameterCondition getOperable () throws CyclicException, EmptyOperatorException  {
       // ------------- ComparisonOperator 1 ----------------------
        Parameter p1 = new Parameter();
        p1.setParameterPK(new ParameterPK("s", "Time duration"));
        p1.setIsDatafileParameter("Y");
        p1.setNumeric(true);

        ParameterComparisonCondition comp1 = new ParameterComparisonCondition();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p1));
        comp1.setComparator(ComparisonOperator.GREATER_EQUAL);
        comp1.setValue(new Float (1));
        // ----------------------------------------------------

        // ------------- ComparisonOperator 2 ----------------------
        Parameter p2 = new Parameter();
        p2.setParameterPK(new ParameterPK("N/A", "Mass"));
        p2.setIsSampleParameter("Y");
        p2.setNumeric(false);

        ParameterComparisonCondition comp2 = new ParameterComparisonCondition();
        comp2.setParameterValued(new ParameterValued(ParameterType.SAMPLE, p2));
        comp2.setComparator(ComparisonOperator.START_WITH);
        comp2.setValue("10");
        // ----------------------------------------------------

        // ------------- ComparisonOperator 3 ----------------------
        Parameter p3 = new Parameter();
        p3.setParameterPK(new ParameterPK("string", "scanType"));
        p3.setIsDatafileParameter("Y");
        p3.setNumeric(false);

        ParameterComparisonCondition comp3 = new ParameterComparisonCondition();
        comp3.setParameterValued(new ParameterValued(ParameterType.DATAFILE, p3));
        comp3.setComparator(ComparisonOperator.START_WITH);
        comp3.setValue("");
        // ----------------------------------------------------
        
        ParameterLogicalCondition op1 = new ParameterLogicalCondition();
        ParameterLogicalCondition op2 = new ParameterLogicalCondition();
        ParameterLogicalCondition op3 = new ParameterLogicalCondition();
        ParameterLogicalCondition op4 = new ParameterLogicalCondition();

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
    private static void createListComparators () {

         // ------------- ComparisonOperator 1 ----------------------
        ParameterComparisonCondition comp1 = new ParameterComparisonCondition();
        comp1.setParameterValued(new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile1")));
        comp1.setComparator(ComparisonOperator.EQUAL);
        comp1.setValue(new Double (3.14));
        // ----------------------------------------------------

        // ------------- ComparisonOperator 2 ----------------------
        ParameterComparisonCondition comp2 = new ParameterComparisonCondition();
        comp2.setParameterValued(new ParameterValued(ParameterType.SAMPLE, parameter.get("sample1")));
        comp2.setComparator(ComparisonOperator.EQUAL);
        comp2.setValue(new Double(2.2));
        // ----------------------------------------------------

        // ------------- ComparisonOperator 3 ----------------------
        ParameterComparisonCondition comp3 = new ParameterComparisonCondition();
        comp3.setParameterValued(new ParameterValued(ParameterType.DATASET, parameter.get("dataset1")));
        comp3.setComparator(ComparisonOperator.EQUAL);
        comp3.setValue(new Double (2.1));
        // ----------------------------------------------------

        // ------------- ComparisonOperator 4 ----------------------
        ParameterComparisonCondition comp4 = new ParameterComparisonCondition();
        comp4.setParameterValued(new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile2_1")));
        comp4.setComparator(ComparisonOperator.EQUAL);
        comp4.setValue(new Double (21.0000002));
        // ----------------------------------------------------

        // ------------- ComparisonOperator 5 -------------------------
        ParameterComparisonCondition comp5 = new ParameterComparisonCondition();
        comp5.setParameterValued(new ParameterValued(ParameterType.DATAFILE, parameter.get("datafile2")));
        comp5.setComparator(ComparisonOperator.EQUAL);
        comp5.setValue(new Double (5.2));
        // ----------------------------------------------------

         // ------------- ComparisonOperator 6 -------------------------
        ParameterComparisonCondition comp6 = new ParameterComparisonCondition();
        comp6.setParameterValued(new ParameterValued(ParameterType.DATASET, parameter.get("dataset2_1")));
        comp6.setComparator(ComparisonOperator.EQUAL);
        comp6.setValue(new Double(21.1));
        // ----------------------------------------------------

        // ------------- ComparisonOperator 7 -------------------------
        ParameterComparisonCondition comp7 = new ParameterComparisonCondition();
        comp7.setParameterValued(new ParameterValued(ParameterType.SAMPLE, parameter.get("sample2_1")));
        comp7.setComparator(ComparisonOperator.EQUAL);
        comp7.setValue(new Double(21.2));
        // ----------------------------------------------------
        
        pcDatafile = new ArrayList<ParameterComparisonCondition>();
        pcDatafile.add(comp1); // 0 datafile1
        pcDatafile.add(comp5); // 1 datafile2
        pcDatafile.add(comp4); // 2 datafile2_1

        pcDataset = new ArrayList<ParameterComparisonCondition>();
        pcDataset.add(comp3);  // 0 dataset1
        pcDataset.add(comp6);  // 1 dataset2_1

        pcSample = new ArrayList<ParameterComparisonCondition>();
        pcSample.add(comp2);   // 0 sample1
        pcSample.add(comp7);
    }
}
