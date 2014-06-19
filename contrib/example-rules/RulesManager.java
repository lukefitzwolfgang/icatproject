//Tom Griffin, STFC ISIS Facility
//17/06/2014

package isisicatclient;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tang76
 */
public class RulesManager {
    
    /*This is an example ICAT rule set for a production facility.
    create and createMany are used interchanable to privide examples of both.
    
    Both JPQL and SimpleQuery formats are used
    
    This script implements a PaNData like policy http://wiki.pan-data.eu/imagesGHD/0/08/PaN-data-D2-1.pdf
    e.g  www.isis.stfc.ac.uk/user-office/data-policy11204.html 
    
    Read carefully and consider if you want all of these rules.
    
    If you spot any mistakes, please contact tom.griffin@stfc.ac.uk
    
    */

    private ICAT port;
    private String sessionId;
    private boolean test;

    public RulesManager(ICAT port, String sessionId, boolean test) {
        this.port = port;
        this.sessionId = sessionId;
        this.test = test;
    }

    public void doEverything() throws isisicatclient.IcatException_Exception {

        //Drop All Existing Rules, Groups, PublicSteps etc
        DropEverything();
        //Create the super admin group - edit the search string.
        CreateFacilityAdmins("User[name='uows/13574']");
        //Create the public tables and steps
        CreatePublicTables();
        CreatePublicSteps();
        
        //Create lower powered data ingestors - (does not add anyone to this group)
        CreateDataIngestors();
        
        //Create rules for instrument scientists
        CreateInstumentScientistRules();
        
        //Create rules for investigators on their own data
        CreateInvestigationUserRules();
        CreateCoInvestigatorRules();
        CreatePIDelegationRules();
        
        //Create public data rules
        CreatePublicUmembargoedDataRules();
        CreatePublicCalibrationRules();
        
        //Create rules to make DOI-ed data readable to DOI-readers (a special web account)
        CreateDOIRules();
        
        //Create special rules for public subsets of data
        CreateSimpleDataBaseRules("Disordered Materials Published Data");
        
        
        System.out.println("Done");

    }

    private void DropEverything() throws isisicatclient.IcatException_Exception {
        //Teardown - drop everything            
        //Drop all groups (some casting required)
        List<Object> allGroupsResults = port.search(sessionId, "Grouping");
        List tempGroups = allGroupsResults;
        List<EntityBaseBean> allGroups = (List<EntityBaseBean>) tempGroups;
        port.deleteMany(sessionId, allGroups);

        //Drop all rules
        List<Object> allRulesResults = port.search(sessionId, "Rule");
        List tempRules = allRulesResults;
        List<EntityBaseBean> allRules = (List<EntityBaseBean>) tempRules;
        port.deleteMany(sessionId, allRules);

        //Drop all public steps
        List<Object> allPublicStepResults = port.search(sessionId, "PublicStep");
        List tempPublicSteps = allPublicStepResults;
        List<EntityBaseBean> allPublicSteps = (List<EntityBaseBean>) tempPublicSteps;
        port.deleteMany(sessionId, allPublicSteps);
    }

    private void CreateFacilityAdmins(String searchString) throws isisicatclient.IcatException_Exception {
        //Get the admin user
        List<Object> adminUsers = port.search(sessionId, searchString);

        //Create the Admin group
        Grouping facilityAdmins = new Grouping();
        facilityAdmins.name = "FacilityAdmins";
        facilityAdmins.id = port.create(sessionId, facilityAdmins);

        for(Object o : adminUsers)
        {
            UserGroup mainAdminToAdmin = new UserGroup();
            mainAdminToAdmin.grouping = facilityAdmins;
            mainAdminToAdmin.user = (User)o;
            port.create(sessionId, mainAdminToAdmin);
        }

        //Grant facilityAdmins access to everything
        facilityAdmins = (Grouping) port.search(sessionId, "Grouping[name='FacilityAdmins']").get(0);

        List<String> allTables = port.getEntityNames();

        for (String table : allTables) {
            Rule rule = new Rule();
            rule.grouping = facilityAdmins;
            rule.crudFlags = "CRUD";
            rule.what = table;
            port.create(sessionId, rule);
            System.out.println("Created " + table + " admin rule");
        }
    }

    private void CreatePublicTables() throws isisicatclient.IcatException_Exception {
        List<String> publicTables = new ArrayList<>();

        publicTables.add("Application");
        publicTables.add("DatafileFormat");
        publicTables.add("DatasetType");
        publicTables.add("Facility");
        publicTables.add("FacilityCycle");
        publicTables.add("Instrument");
        publicTables.add("InstrumentScientist");
        publicTables.add("InvestigationType");
        publicTables.add("ParameterType");
        publicTables.add("PermissibleStringValue");
        publicTables.add("Publication");
        publicTables.add("Shift");
        publicTables.add("User");

        //Link tables that can be public for speed reasons
        publicTables.add("DataCollectionDatafile");
        publicTables.add("DataCollectionDataset");
        publicTables.add("InvestigationUser");
        publicTables.add("StudyInvestigation");

        List<EntityBaseBean> publicRules = new ArrayList<>();
        for (String publicTableName : publicTables) {
            Rule publicRule = new Rule();
            publicRule.what = publicTableName;
            publicRule.crudFlags = "R";
            publicRules.add(publicRule);
        }
        port.createMany(sessionId, publicRules);
    }

    private void CreatePublicSteps() throws isisicatclient.IcatException_Exception {
        //set public steps - for example, if someone can read a dataset, they can access all files under that
        List<EntityBaseBean> publicSteps = new ArrayList<>();

        String[] parameterBasedPublicSteps = new String[]{"Investigation", "Dataset", "Datafile", "Sample"};
        for (String table : parameterBasedPublicSteps) {
            PublicStep paramPublicStep = new PublicStep();
            paramPublicStep.origin = table;
            paramPublicStep.field = "parameters";
            publicSteps.add(paramPublicStep);
        }

        String[] publicStepsFromInvestigation = new String[]{"samples", "publications", "shifts", "investigationUsers", "keywords", "investigationInstruments"};
        for (String step : publicStepsFromInvestigation) {
            PublicStep invToSomething = new PublicStep();
            invToSomething.origin = "Investigation";
            invToSomething.field = step;
            publicSteps.add(invToSomething);
        }

        String[] publicStepsFromDataset = new String[]{"sample", "datafiles"};
        for (String step : publicStepsFromDataset) {
            PublicStep datasetToSomething = new PublicStep();
            datasetToSomething.origin = "Dataset";
            datasetToSomething.field = step;
            publicSteps.add(datasetToSomething);
        }

        //sample to sampleType    
        PublicStep sampleToSampleType = new PublicStep();
        sampleToSampleType.origin = "Sample";
        sampleToSampleType.field = "type";
        publicSteps.add(sampleToSampleType);

        //User to user group
        PublicStep userToUserGroup = new PublicStep();
        userToUserGroup.origin = "User";
        userToUserGroup.field = "userGroups";
        publicSteps.add(userToUserGroup);

        //User group to grouping
        PublicStep userGroupToGrouping = new PublicStep();
        userGroupToGrouping.origin = "UserGroup";
        userGroupToGrouping.field = "grouping";
        publicSteps.add(userGroupToGrouping);

        port.createMany(sessionId, publicSteps);
    }

    private void CreateDataIngestors() throws isisicatclient.IcatException_Exception {
        //Data (raw) Ingestor group and rules
        Grouping dataIngestors = new Grouping();
        dataIngestors.name = "DataIngestors";
        dataIngestors.id = port.create(sessionId, dataIngestors);
        dataIngestors = (Grouping) port.search(sessionId, "Grouping[name='DataIngestors']").get(0);

        List<String> ingestorTables = port.getEntityNames();

        ingestorTables.remove("Facility");

        List<EntityBaseBean> ingestorRules = new ArrayList<>();
        for (String table : ingestorTables) {
            Rule rule = new Rule();
            rule.grouping = dataIngestors;
            rule.crudFlags = "CRUD"; //no delete permission for ingestors
            rule.what = table;
            ingestorRules.add(rule);
        }
        port.createMany(sessionId, ingestorRules);
    }

    private void CreateInstumentScientistRules() throws isisicatclient.IcatException_Exception {
        //Instrument Scientist Rules            
        String count = "";
        //Investigation
        Rule isInv = new Rule();
        isInv.crudFlags = "R";
        isInv.what = "SELECT i FROM Investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE u.name = :user";
        isInv.grouping = null; //rules applies regardless of group membership (not explicity needed)
        port.create(sessionId, isInv);

        //Test
        if (test) {
            count = port.search(sessionId, "SELECT COUNT(i) FROM Investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst WHERE inst.name='WISH'").get(0).toString();
            System.out.println("Instrument Scientist - Investigation - OK - " + count);
        }

        //Dataset
        Rule isDs = new Rule();
        isDs.crudFlags = "CRU";
        isDs.what = "SELECT d FROM Dataset d JOIN d.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE d.name='Default' AND u.name = :user";
        port.create(sessionId, isDs);

        //Test
        if (test) {
            count = port.search(sessionId, "Count (Dataset) <-> Investigation <-> InvestigationInstrument <-> Instrument[name='WISH']").get(0).toString();
            System.out.println("Instrument Scientist - Dataset OK - " + count);
        }

        test = false;

        //datafile
        Rule isDf = new Rule();
        isDf.crudFlags = "R";
        isDf.what = "SELECT df FROM Datafile df JOIN df.dataset d JOIN d.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE d.name='Default' AND u.name = :user";
        port.create(sessionId, isDf);

        //Test
        if (test) {
            count = port.search(sessionId, "Count (Datafile) <-> Dataset <-> Investigation <-> InvestigationInstrument <-> Instrument[name='WISH']").get(0).toString();
            System.out.println("Instrument Scientist - Datafile OK - " + count);
        }

        //samples - via investigation
        Rule isSampleInv = new Rule();
        isSampleInv.crudFlags = "R";
        isSampleInv.what = "SELECT s FROM Sample s JOIN s.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE u.name = :user";
        port.create(sessionId, isSampleInv);

        //Test
        if (test) {
            count = port.search(sessionId, "Count (Sample) <-> Investigation <-> InvestigationInstrument <-> Instrument[name='WISH']").get(0).toString();
            System.out.println("Instrument Scientist - Sample via Investigation OK - " + count);

            //test with new syntax
            count = port.search(sessionId, "SELECT COUNT(Sample$) FROM Sample AS Sample$ JOIN Sample$.investigation AS Investigation$ JOIN Investigation$.investigationInstruments AS InvestigationInstrument$ JOIN InvestigationInstrument$.instrument AS Instrument$ WHERE (Instrument$.name = 'WISH')").get(0).toString();
            System.out.println("Instrument Scientist - Sample via Investigation OK - " + count + " (new query syntax)");
        }

        //samples - via dataset
        Rule isSampleDs = new Rule();
        isSampleDs.crudFlags = "CR";
        isSampleDs.what = "SELECT s FROM Sample AS s JOIN s.datasets AS ds JOIN ds.investigation AS i JOIN i.investigationInstruments AS ii JOIN ii.instrument AS inst JOIN inst.instrumentScientists AS instSci JOIN instSci.user u WHERE u.name = :user";
        port.create(sessionId, isSampleDs);

        if (test) {
            //Test
            count = port.search(sessionId, "SELECT COUNT(s) FROM Sample AS s JOIN s.datasets as ds JOIN ds.investigation AS i JOIN i.investigationInstruments AS ii JOIN ii.instrument AS inst WHERE (inst.name = 'WISH')").get(0).toString();
            System.out.println("Instrument Scientist - Sample via Dataset OK - " + count);
        }

        //sample type - via investigation
        Rule isSampleTypeInv = new Rule();
        isSampleTypeInv.crudFlags = "CR";
        isSampleTypeInv.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE u.name = :user";
        port.create(sessionId, isSampleTypeInv);

        if (test) {
            //Test
            count = port.search(sessionId, "Count (SampleType) <-> Sample <-> Investigation <-> InvestigationInstrument <-> Instrument[name='WISH']").get(0).toString();
            System.out.println("Instrument Scientist - SampleType via Investigation OK - " + count);
        }

        //sample type - via dataset
        Rule isSampleTypeDs = new Rule();
        isSampleTypeDs.crudFlags = "CR";
        isSampleTypeDs.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.datasets ds JOIN ds.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE u.name = :user";
        port.create(sessionId, isSampleTypeDs);

        if (test) {
            //Test
            count = port.search(sessionId, "SELECT COUNT(SampleType$) FROM SampleType AS SampleType$ JOIN SampleType$.samples AS Sample$ JOIN Sample$.datasets AS Dataset$ JOIN Dataset$.investigation AS Investigation$ JOIN Investigation$.investigationInstruments AS InvestigationInstrument$ JOIN InvestigationInstrument$.instrument AS Instrument$ WHERE (Instrument$.name = 'WISH')").get(0).toString();
            System.out.println("Instrument Scientist - SampleType via Dataset OK - " + count);
        }

        //Sample Parameter - via Investigation
        Rule isSampleParamInv = new Rule();
        isSampleParamInv.crudFlags = "CR";
        isSampleParamInv.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE u.name = :user";
        port.create(sessionId, isSampleParamInv);

        if (test) {
            //Test
            count = port.search(sessionId, "Count (SampleParameter) <-> Sample <-> Investigation <-> InvestigationInstrument <-> Instrument[name='WISH']").get(0).toString();
            System.out.println("Instrument Scientist - SampleParameter via Investigation OK - " + count);
        }

        //Sample Parameter - via Dataset
        Rule isSampleParamDs = new Rule();
        isSampleParamDs.crudFlags = "CR";
        isSampleParamDs.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationInstruments ii JOIN ii.instrument inst JOIN inst.instrumentScientists instSci JOIN instSci.user u WHERE u.name = :user";
        port.create(sessionId, isSampleParamDs);

        if (test) {
            //Test
            count = port.search(sessionId, "SELECT COUNT(SampleParameter$) FROM SampleParameter AS SampleParameter$ JOIN SampleParameter$.sample As Sample$ JOIN Sample$.datasets AS Dataset$ JOIN Dataset$.investigation AS Investigation$ JOIN Investigation$.investigationInstruments AS InvestigationInstrument$ JOIN InvestigationInstrument$.instrument AS Instrument$ WHERE (Instrument$.name = 'WISH')").get(0).toString();
            System.out.println("Instrument Scientist - SampleParameter via Dataset OK - " + count);
        }
        //End Instrument Scientist rules
    }

    private void CreateInvestigationUserRules() throws isisicatclient.IcatException_Exception {
        String count;
        //InvestigationUser Rules
        Rule invUserinv = new Rule();
        invUserinv.crudFlags = "R";
        invUserinv.what = "Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserinv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Investigation) <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Investigation OK - " + count);
        }

        Rule invUserInvParam = new Rule();
        invUserInvParam.crudFlags = "R";
        invUserInvParam.what = "InvestigationParameter <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserInvParam);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(InvestigationParameter) <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Investigation Parameter OK - " + count);
        }

        //DS
        Rule invUserDs = new Rule();
        invUserDs.crudFlags = "R";
        invUserDs.what = "Dataset <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDs);

        if (test) {
            count = port.search(sessionId, "COUNT(Dataset) <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Dataset OK - " + count);
        }

        Rule invUserDsParam = new Rule();
        invUserDsParam.crudFlags = "R";
        invUserDsParam.what = "DatasetParameter <-> Dataset <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDsParam);

        if (test) {
            count = port.search(sessionId, "COUNT(DatasetParameter) <-> Dataset <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Dataset Parameter OK - " + count);
        }

        //DF
        Rule invUserDf = new Rule();
        invUserDf.crudFlags = "R";
        invUserDf.what = "Datafile <-> Dataset <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDf);

        if (test) {
            count = port.search(sessionId, "COUNT(Datafile) <-> Dataset <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Datafile OK - " + count);
        }

        Rule invUserDfParam = new Rule();
        invUserDfParam.crudFlags = "R";
        invUserDfParam.what = "DatafileParameter <-> Datafile <-> Dataset <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDfParam);

        if (test) {
            count = port.search(sessionId, "COUNT(DatafileParameter) <-> Datafile <-> Dataset <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Datafile Parameter OK - " + count);
        }

        //Sample - via investigation
        Rule invUserSampleInv = new Rule();
        invUserSampleInv.crudFlags = "R";
        invUserSampleInv.what = "SELECT s FROM Sample s JOIN s.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE user.name = :user";
        port.create(sessionId, invUserSampleInv);

        if (test) {
            count = port.search(sessionId, "COUNT(Sample) <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Sample via Investigation OK - " + count);
        }

        //Sample - via Dataset
        Rule invUserSampleDs = new Rule();
        invUserSampleDs.crudFlags = "R";
        invUserSampleDs.what = "Sample <-> Dataset <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        invUserSampleDs.what = "SELECT s FROM Sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE user.name = :user";
        port.create(sessionId, invUserSampleDs);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(s) FROM Sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE user.name = :user").get(0).toString();
            System.out.println("InvUser - Sample via Dataset OK - " + count);
        }

        //SampleParameter - via investigation
        Rule invUserSampleParamInv = new Rule();
        invUserSampleParamInv.crudFlags = "R";
        invUserSampleParamInv.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE user.name = :user";
        port.create(sessionId, invUserSampleParamInv);

        if (test) {
            count = port.search(sessionId, "COUNT(SampleParameter) <-> Sample <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - SampleParameter via Investigation OK - " + count);
        }

        //Sample - via Dataset
        Rule invUserSampleParamDs = new Rule();
        invUserSampleParamDs.crudFlags = "R";
        invUserSampleParamDs.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE user.name = :user";
        port.create(sessionId, invUserSampleParamDs);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(sp) FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE user.name = :user").get(0).toString();
            System.out.println("InvUser - SampleParameter via Dataset OK - " + count);
        }
            //test= true;
        //Investigatio user - write access to non 'Default' datasets
        //DS
        Rule invUserDsUpload = new Rule();
        invUserDsUpload.crudFlags = "CRUD";
        invUserDsUpload.what = "Dataset [name <> 'Default'] <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDsUpload);

        if (test) {
            count = port.search(sessionId, "COUNT(Dataset) <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUserUpload - Dataset OK - " + count);
        }

        Rule invUserDsParamUpload = new Rule();
        invUserDsParamUpload.crudFlags = "CRUD";
        invUserDsParamUpload.what = "DatasetParameter <-> Dataset [name <> 'Default'] <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDsParamUpload);

        if (test) {
            count = port.search(sessionId, "COUNT(DatasetParameter) <-> Dataset [name <> 'Default'] <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUserUpload - Dataset Parameter OK - " + count);
        }

        //DF
        Rule invUserDfUpload = new Rule();
        invUserDfUpload.crudFlags = "CRUD";
        invUserDfUpload.what = "Datafile <-> Dataset [name <> 'Default'] <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDfUpload);

        if (test) {
            count = port.search(sessionId, "COUNT(Datafile) <-> Dataset [name <> 'Default'] <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Datafile OK - " + count);
        }

        Rule invUserDfParamUpload = new Rule();
        invUserDfParamUpload.crudFlags = "CRUD";
        invUserDfParamUpload.what = "DatafileParameter <-> Datafile <-> Dataset [name <> 'Default'] <-> Investigation <-> InvestigationUser <-> User [name = :user]";
        port.create(sessionId, invUserDfParamUpload);

        if (test) {
            count = port.search(sessionId, "COUNT(DatafileParameter) <-> Datafile <-> Dataset <-> Investigation <-> InvestigationUser <-> User[name=:user]").get(0).toString();
            System.out.println("InvUser - Datafile Parameter OK - " + count);
        }

        //Sample - via Dataset
        Rule invUserSampleDsUpload = new Rule();
        invUserSampleDsUpload.crudFlags = "CRUD";
        invUserSampleDsUpload.what = "SELECT s FROM Sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE d.name <> 'Default' AND user.name = :user";
        port.create(sessionId, invUserSampleDsUpload);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(s) FROM Sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE d.name <> 'Default' AND user.name = :user").get(0).toString();
            System.out.println("InvUser - Sample via Dataset OK - " + count);
        }

        //Sample - via Dataset
        Rule invUserSampleParamDsUpload = new Rule();
        invUserSampleParamDsUpload.crudFlags = "CRUD";
        invUserSampleParamDsUpload.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE d.name <> 'Default' AND user.name = :user";
        port.create(sessionId, invUserSampleParamDsUpload);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(sp) FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i JOIN i.investigationUsers iu JOIN iu.user user WHERE  d.name <> 'Default' AND user.name = :user").get(0).toString();
            System.out.println("InvUser - SampleParameter via Dataset OK - " + count);
        }
        //End InvestigationUser Rules
    }

    private void CreatePublicUmembargoedDataRules() throws isisicatclient.IcatException_Exception {
        String count;
        //Public Access to older data
        //Unembargoed Data Rules
        //Unembargoed Investigation
        Rule ueInvestigation = new Rule();
        ueInvestigation.crudFlags = "R";
        //unembargoInvestigation.what = "Investigation[releaseDate < CURRENT_TIMESTAMP]"; //Note - this doesn't work
        ueInvestigation.what = "SELECT i FROM Investigation i WHERE i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueInvestigation);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Investigation)").get(0).toString();
            System.out.println("Unembargoed data - Investigation - OK - " + count);
        }

        //Unembargoed InvestigationParameters
        Rule ueInvParam = new Rule();
        ueInvParam.crudFlags = "R";
        ueInvParam.what = "SELECT ip FROM InvestigationParameter ip JOIN ip.investigation i WHERE i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueInvParam);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(InvestigationParameter)").get(0).toString();
            System.out.println("Unembargoed data - Investigation Parameter - OK - " + count);
        }

        //Unembargoed Dataset
        Rule ueDataset = new Rule();
        ueDataset.crudFlags = "R";
        ueDataset.what = "SELECT d FROM Dataset d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueDataset);

        //Test           
        if (test) {
            count = port.search(sessionId, "COUNT(Dataset)").get(0).toString();
            System.out.println("Unembargoed data - Dataset - OK - " + count);
        }
        //Unembargoed Dataset Parameters
        Rule ueDatasetParam = new Rule();
        ueDatasetParam.crudFlags = "R";
        ueDatasetParam.what = "SELECT dp FROM DatasetParameter dp JOIN dp.dataset d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueDatasetParam);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(DatasetParameter)").get(0).toString();
            System.out.println("Unembargoed data - Dataset Parameter - OK - " + count);
        }

        //Unembargoed Datafile
        Rule ueDatafile = new Rule();
        ueDatafile.crudFlags = "R";
        ueDatafile.what = "SELECT df FROM Datafile df JOIN df.dataset d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueDatafile);
        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Datafile)").get(0).toString();
            System.out.println("Unembargoed data - Datafile - OK - " + count);
        }

        //Unembargoed Datafile Parameters
        Rule ueDatafileParams = new Rule();
        ueDatafileParams.crudFlags = "R";
        ueDatafileParams.what = "SELECT dfp FROM DatafileParameter dfp JOIN dfp.datafile df JOIN df.dataset d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueDatafileParams);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(DatafileParameter)").get(0).toString();
            System.out.println("Unembargoed data - Datafile Parameters - OK - " + count);
        }
        //Unembargoed Sample via Investigation
        Rule ueSampleViaInv = new Rule();
        ueSampleViaInv.crudFlags = "R";
        ueSampleViaInv.what = "SELECT s FROM Sample s JOIN s.investigation i WHERE i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueSampleViaInv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Sample)").get(0).toString();
            System.out.println("Unembargoed data - Sample via Investigatio - OK - " + count);
        }

        //Unembargoed Sample via Dataset
        Rule ueSampleViaDs = new Rule();
        ueSampleViaDs.crudFlags = "R";
        ueSampleViaDs.what = "SELECT s FROM Sample s JOIN s.datasets d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueSampleViaDs);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Sample)").get(0).toString();
            System.out.println("Unembargoed data - Sample via Dataset - OK - " + count);
        }

        //Unembargoed Sample Parameter via Investigation
        Rule ueSampleParamViaInv = new Rule();
        ueSampleParamViaInv.crudFlags = "R";
        ueSampleParamViaInv.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.investigation i WHERE i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueSampleParamViaInv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleParameter)").get(0).toString();
            System.out.println("Unembargoed data - SampleParameter via Investigation - OK - " + count);
        }

        //Unembargoed Sample Parameter via Dataset
        Rule ueSampleParamViaDs = new Rule();
        ueSampleParamViaDs.crudFlags = "R";
        ueSampleParamViaDs.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueSampleParamViaDs);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleParameter)").get(0).toString();
            System.out.println("Unembargoed data - SampleParameter via Dataset - OK - " + count);
        }

        //Unembargoed SampleType via Investigation
        Rule ueSampleTypeViaInv = new Rule();
        ueSampleTypeViaInv.crudFlags = "R";
        ueSampleTypeViaInv.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.investigation i WHERE i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueSampleParamViaInv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleType)").get(0).toString();
            System.out.println("Unembargoed data - SampleType via Investigation - OK - " + count);
        }
        //Unembargoed SampleType via Dataset
        Rule ueSampleTypeViaDs = new Rule();
        ueSampleTypeViaDs.crudFlags = "R";
        ueSampleTypeViaDs.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.datasets d JOIN d.investigation i WHERE d.name = 'Default' AND i.releaseDate < CURRENT_TIMESTAMP";
        port.create(sessionId, ueSampleTypeViaDs);

            //test = true;
        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleType)").get(0).toString();
            System.out.println("Unembargoed data - SampleType via Dataset - OK - " + count);
        }
        //End Unembargoed Data Rules
    }

    private void CreatePublicCalibrationRules() throws isisicatclient.IcatException_Exception {
        String count;
        //Public Access to calibration data
        //Unembargoed Investigation
        Rule calibInvestigation = new Rule();
        calibInvestigation.crudFlags = "R";
        //unembargoInvestigation.what = "Investigation[releaseDate < CURRENT_TIMESTAMP]"; //Note - this doesn't work
        calibInvestigation.what = "SELECT i FROM Investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibInvestigation);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Investigation)").get(0).toString();
            System.out.println("Calibration data - Investigation - OK - " + count);
        }

        //Unembargoed InvestigationParameters
        Rule calibInvParam = new Rule();
        calibInvParam.crudFlags = "R";
        calibInvParam.what = "SELECT ip FROM InvestigationParameter ip JOIN ip.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibInvParam);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(InvestigationParameter)").get(0).toString();
            System.out.println("Calibration data - Investigation Parameter - OK - " + count);
        }

        //Unembargoed Dataset
        Rule calibDataset = new Rule();
        calibDataset.crudFlags = "R";
        calibDataset.what = "SELECT d FROM Dataset d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibDataset);

        //Test           
        if (test) {
            count = port.search(sessionId, "COUNT(Dataset)").get(0).toString();
            System.out.println("Calibration data - Dataset - OK - " + count);
        }
        //Unembargoed Dataset Parameters
        Rule calibDatasetParam = new Rule();
        calibDatasetParam.crudFlags = "R";
        calibDatasetParam.what = "SELECT dp FROM DatasetParameter dp JOIN dp.dataset d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibDatasetParam);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(DatasetParameter)").get(0).toString();
            System.out.println("Calibration data - Dataset Parameter - OK - " + count);
        }

        //Unembargoed Datafile
        Rule calibDatafile = new Rule();
        calibDatafile.crudFlags = "R";
        calibDatafile.what = "SELECT df FROM Datafile df JOIN df.dataset d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibDatafile);
        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Datafile)").get(0).toString();
            System.out.println("Calibration data - Datafile - OK - " + count);
        }

        //Unembargoed Datafile Parameters
        Rule calibDatafileParams = new Rule();
        calibDatafileParams.crudFlags = "R";
        calibDatafileParams.what = "SELECT dfp FROM DatafileParameter dfp JOIN dfp.datafile df JOIN df.dataset d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibDatafileParams);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(DatafileParameter)").get(0).toString();
            System.out.println("Calibration data - Datafile Parameters - OK - " + count);
        }
        //Unembargoed Sample via Investigation
        Rule calibSampleViaInv = new Rule();
        calibSampleViaInv.crudFlags = "R";
        calibSampleViaInv.what = "SELECT s FROM Sample s JOIN s.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibSampleViaInv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Sample)").get(0).toString();
            System.out.println("Calibration data - Sample via Investigatio - OK - " + count);
        }

        //Unembargoed Sample via Dataset
        Rule calibSampleViaDs = new Rule();
        calibSampleViaDs.crudFlags = "R";
        calibSampleViaDs.what = "SELECT s FROM Sample s JOIN s.datasets d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibSampleViaDs);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(Sample)").get(0).toString();
            System.out.println("Calibration data - Sample via Dataset - OK - " + count);
        }

        //Unembargoed Sample Parameter via Investigation
        Rule calibSampleParamViaInv = new Rule();
        calibSampleParamViaInv.crudFlags = "R";
        calibSampleParamViaInv.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibSampleParamViaInv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleParameter)").get(0).toString();
            System.out.println("Calibration data - SampleParameter via Investigation - OK - " + count);
        }

        //Unembargoed Sample Parameter via Dataset
        Rule calibSampleParamViaDs = new Rule();
        calibSampleParamViaDs.crudFlags = "R";
        calibSampleParamViaDs.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibSampleParamViaDs);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleParameter)").get(0).toString();
            System.out.println("Calibration data - SampleParameter via Dataset - OK - " + count);
        }

        //Unembargoed SampleType via Investigation
        Rule calibSampleTypeViaInv = new Rule();
        calibSampleTypeViaInv.crudFlags = "R";
        calibSampleTypeViaInv.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibSampleTypeViaInv);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleType)").get(0).toString();
            System.out.println("Calibration data - SampleType via Investigation - OK - " + count);
        }
        //Unembargoed SampleType via Dataset
        Rule calibSampleTypeViaDs = new Rule();
        calibSampleTypeViaDs.crudFlags = "R";
        calibSampleTypeViaDs.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.datasets d JOIN d.investigation i JOIN i.type t WHERE t.name = 'calibration'";
        port.create(sessionId, calibSampleTypeViaDs);

        if (test) {
            //Test           
            count = port.search(sessionId, "COUNT(SampleType)").get(0).toString();
            System.out.println("Calibration data - SampleType via Dataset - OK - " + count);
        }

    }

    private void CreateSimpleDataBaseRules(String database) throws isisicatclient.IcatException_Exception {
        //Rules for Disordered Materials Published Data
        Grouping publishedDataAdmins = new Grouping();
        publishedDataAdmins.name = "Disordered Materials Published Data Admins";
        publishedDataAdmins.id = port.create(sessionId, publishedDataAdmins);

            //Add Frazer to that group
            /*UserGroup frazerToPdas = new UserGroup();
         frazerToPdas.grouping = publishedDataAdmins;
         frazerToPdas.user = frazer;
         frazerToPdas.id = port.create(sessionId, frazerToPdas);*/
        String databaseName = " = '" + database + "'";

        //Create on ParameterType
        Rule pdaCreateParamType = new Rule();
        pdaCreateParamType.grouping = publishedDataAdmins;
        pdaCreateParamType.crudFlags = "CU";
        pdaCreateParamType.what = "ParameterType";
        port.create(sessionId, pdaCreateParamType);
        String count;
        if (test) {
            count = port.search(sessionId, "SELECT COUNT(i) FROM Investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - Investigation - OK - " + count);
        }

        //Rules for Investigation
        Rule pdaInvestigation = new Rule();
        pdaInvestigation.grouping = publishedDataAdmins;
        pdaInvestigation.crudFlags = "CRUD";
        pdaInvestigation.what = "SELECT i FROM Investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaInvestigation);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(i) FROM Investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - Investigation - OK - " + count);
        }

        //Rules for InvestigationParameter
        Rule pdaInvParam = new Rule();
        pdaInvParam.grouping = publishedDataAdmins;
        pdaInvParam.crudFlags = "CRUD";
        pdaInvParam.what = "SELECT ip FROM InvestigationParameter ip JOIN ip.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaInvParam);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(ip) FROM InvestigationParameter ip JOIN ip.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - InvestigationParameter - OK - " + count);
        }

        //Rules for Dataset
        Rule pdaDs = new Rule();
        pdaDs.grouping = publishedDataAdmins;
        pdaDs.crudFlags = "CRUD";
        pdaDs.what = "SELECT ds FROM Dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaDs);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(ds) FROM Dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - Dataset - OK - " + count);
        }

        //Rules for DatasetParameter
        Rule pdaDsParam = new Rule();
        pdaDsParam.grouping = publishedDataAdmins;
        pdaDsParam.crudFlags = "CRUD";
        pdaDsParam.what = "SELECT dsp FROM DatasetParameter dsp JOIN dsp.dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaDsParam);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(dsp) FROM DatasetParameter dsp JOIN dsp.dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - DatasetParameter - OK - " + count);
        }

        //Rules for Datafile
        Rule pdaDf = new Rule();
        pdaDf.grouping = publishedDataAdmins;
        pdaDf.crudFlags = "CRUD";
        pdaDf.what = "SELECT df FROM Datafile df JOIN df.dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaDf);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(df) FROM Datafile df JOIN df.dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - Datafile - OK - " + count);
        }

        //Rules for DatasetParameter
        Rule pdaDfParam = new Rule();
        pdaDfParam.grouping = publishedDataAdmins;
        pdaDfParam.crudFlags = "CRUD";
        pdaDfParam.what = "SELECT dfp FROM DatafileParameter dfp JOIN dfp.datafile df JOIN df.dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaDfParam);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(dfp) FROM DatafileParameter dfp JOIN dfp.datafile df JOIN df.dataset ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - DatafileParameter - OK - " + count);
        }
        //Rules for Sample via Investigation
        Rule pdaSampleViaInv = new Rule();
        pdaSampleViaInv.grouping = publishedDataAdmins;
        pdaSampleViaInv.crudFlags = "CRUD";
        pdaSampleViaInv.what = "SELECT s FROM Sample s JOIN s.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaSampleViaInv);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(s) FROM Sample s JOIN s.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - Sample via Investigation - OK - " + count);
        }

        //Rules for Sample Via Dataset
        Rule pdaSampleViaDs = new Rule();
        pdaSampleViaDs.grouping = publishedDataAdmins;
        pdaSampleViaDs.crudFlags = "CRUD";
        pdaSampleViaDs.what = "SELECT s FROM Sample s JOIN s.datasets ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaSampleViaDs);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(s) FROM Sample s JOIN s.datasets ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - Sample via Dataset - OK - " + count);
        }

        //Rules for SampleParameter via Investigation
        Rule pdaSampleParamViaInv = new Rule();
        pdaSampleParamViaInv.grouping = publishedDataAdmins;
        pdaSampleParamViaInv.crudFlags = "CRUD";
        pdaSampleParamViaInv.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaSampleParamViaInv);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(sp) FROM SampleParameter sp JOIN sp.sample s JOIN s.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - SampleParameter via Investigation - OK - " + count);
        }

        //Rules for SampleParameter Via Dataset
        Rule pdaSampleParamViaDs = new Rule();
        pdaSampleParamViaDs.grouping = publishedDataAdmins;
        pdaSampleParamViaDs.crudFlags = "CRUD";
        pdaSampleParamViaDs.what = "SELECT sp FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaSampleParamViaDs);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(sp) FROM SampleParameter sp JOIN sp.sample s JOIN s.datasets ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - SampleParameter via Dataset - OK - " + count);
        }

        //SampleType - create is independant, so need 'direct' 'C' access, and then to read their own?
        Rule pdaCreateSampleType = new Rule();
        pdaCreateSampleType.grouping = publishedDataAdmins;
        pdaCreateSampleType.crudFlags = "CR";
        pdaCreateSampleType.what = "SampleType";
        port.create(sessionId, pdaCreateSampleType);

        //Rules for SampleType via Investigation
        Rule pdaSampleTypeViaInv = new Rule();
        pdaSampleTypeViaInv.grouping = publishedDataAdmins;
        pdaSampleTypeViaInv.crudFlags = "CRUD";
        pdaSampleTypeViaInv.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaSampleTypeViaInv);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(st) FROM SampleType st JOIN st.samples s JOIN s.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - SampleType via Investigation - OK - " + count);
        }

        //Rules for SampleType Via Dataset
        Rule pdaSampleTypeViaDs = new Rule();
        pdaSampleTypeViaDs.grouping = publishedDataAdmins;
        pdaSampleTypeViaDs.crudFlags = "CRUD";
        pdaSampleTypeViaDs.what = "SELECT st FROM SampleType st JOIN st.samples s JOIN s.datasets ds JOIN ds.investigation i JOIN i.type it WHERE it.name " + databaseName;
        port.create(sessionId, pdaSampleTypeViaDs);

        if (test) {
            count = port.search(sessionId, "SELECT COUNT(st) FROM SampleType st JOIN st.samples s JOIN s.datasets ds JOIN ds.investigation i JOIN i.type it WHERE it.name" + databaseName).get(0).toString();
            System.out.println("DMPD - SampleType via Dataset - OK - " + count);
        }
    }

    private void CreateDOIRules() throws isisicatclient.IcatException_Exception {
        //DOI reader group, account and rules
        //Investigation, InvestigationUser, InvestigationInstrument, Instrument, User, Dataset, Datafile
        Grouping doiReaders = new Grouping();
        doiReaders.name = "DOI Readers";
        doiReaders.id = port.create(sessionId, doiReaders);

        User doiUser = (User) port.search(sessionId, "User[name='uows/1049734']").get(0);
        UserGroup doiUserToGroup = new UserGroup();
        doiUserToGroup.user = doiUser;
        doiUserToGroup.grouping = doiReaders;
        doiUserToGroup.id = port.create(sessionId, doiUserToGroup);

        Rule doiInvestigation = new Rule();
        doiInvestigation.grouping = doiReaders;
        doiInvestigation.crudFlags = "R";
        doiInvestigation.what = "SELECT i FROM Investigation i WHERE i.doi IS NOT NULL";
        port.create(sessionId, doiInvestigation);

            //count = port.search(sessionId, "SELECT COUNT(i) FROM Investigation i WHERE i.doi IS NOT NULL").get(0).toString();
        //count = port.search(sessionId, "SELECT COUNT(i) FROM Investigation i WHERE i.doi<> ''").get(0).toString();
        Rule doiDataset = new Rule();
        doiDataset.grouping = doiReaders;
        doiDataset.crudFlags = "R";
        doiDataset.what = "SELECT ds FROM Dataset ds WHERE ds.doi IS NOT NULL";
        port.create(sessionId, doiDataset);

        Rule doiDatasetInv = new Rule();
        doiDatasetInv.grouping = doiReaders;
        doiDatasetInv.crudFlags = "R";
        doiDatasetInv.what = "SELECT i FROM Investigation i JOIN i.datasets ds WHERE ds.doi IS NOT NULL";
        port.create(sessionId, doiDatasetInv);

        Rule doiDatafile = new Rule();
        doiDatafile.grouping = doiReaders;
        doiDatafile.crudFlags = "R";
        doiDatafile.what = "SELECT df FROM Datafile df WHERE df.doi IS NOT NULL";
        port.create(sessionId, doiDatafile);

        Rule doiDatafileDataset = new Rule();
        doiDatafileDataset.grouping = doiReaders;
        doiDatafileDataset.crudFlags = "R";
        doiDatafileDataset.what = "SELECT ds FROM Dataset ds JOIN ds.datafiles df WHERE df.doi IS NOT NULL";
        port.create(sessionId, doiDatafileDataset);

        Rule doiDatafileInv = new Rule();
        doiDatafileInv.grouping = doiReaders;
        doiDatafileInv.crudFlags = "R";
        doiDatafileInv.what = "SELECT i FROM Investigation i JOIN i.datasets ds JOIN ds.datafiles df WHERE df.doi IS NOT NULL";
        port.create(sessionId, doiDatafileInv);

    }

    private void CreateCoInvestigatorRules() throws isisicatclient.IcatException_Exception {
        String count;
        //Co-Investigator - for now they can read everything they are associated with - Investigation + P ; DS + P ; DF + P; Sample + P
        Rule coiInv = new Rule();
        coiInv.crudFlags = "R";
        coiInv.what = "Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiInv);

        Rule coiInvParam = new Rule();
        coiInvParam.crudFlags = "R";
        coiInvParam.what = "InvestigationParameter <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiInvParam);

        //DS
        Rule coiDs = new Rule();
        coiDs.crudFlags = "R";
        coiDs.what = "Dataset <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiDs);

        Rule coiDsParam = new Rule();
        coiDsParam.crudFlags = "R";
        coiDsParam.what = "DatasetParameter <-> Dataset <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiDsParam);

        //DF
        Rule coiDf = new Rule();
        coiDf.crudFlags = "R";
        coiDf.what = "Datafile <-> Dataset <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiDf);

        Rule coiDfParam = new Rule();
        coiDfParam.crudFlags = "R";
        coiDfParam.what = "DatafileParameter <-> Datafile <-> Dataset <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiDfParam);

        //Sample - via investigation
        Rule coiSampleInv = new Rule();
        coiSampleInv.crudFlags = "R";
        coiSampleInv.what = "Sample <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiSampleInv);

        Rule coiSampleParamInv = new Rule();
        coiSampleParamInv.crudFlags = "R";
        coiSampleParamInv.what = "SampleParameter <-> Sample <-> Investigation <-> InvestigationUser  <-> User <-> User [name = :user]";
        port.create(sessionId, coiSampleParamInv);
    }

    private void CreatePIDelegationRules() throws isisicatclient.IcatException_Exception {

        //Allow PI to delegate permissions, by adding people to the InvestigationUser table
        Rule piAddExperimenters = new Rule();
        piAddExperimenters.crudFlags = "C";
        piAddExperimenters.what = "InvestigationUser <-> Investigation <-> InvestigationUser [role = 'principal_experimenter'] <-> User [name = :user]";
        port.create(sessionId, piAddExperimenters);
    }
}
