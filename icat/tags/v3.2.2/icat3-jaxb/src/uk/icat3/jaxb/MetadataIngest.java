package uk.icat3.jaxb;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import org.apache.log4j.Logger;
import uk.icat3.entity.DatafileFormat;
import uk.icat3.exceptions.ICATAPIException;
import uk.icat3.exceptions.InsufficientPrivilegesException;
import uk.icat3.exceptions.NoSuchObjectFoundException;
import uk.icat3.exceptions.ValidationException;
import uk.icat3.jaxb.MetadataParser;
import uk.icat3.jaxb.gen.*;
import uk.icat3.jaxb.helper.StringComparer;
import uk.icat3.manager.DataFileManager;
import uk.icat3.manager.DataSetManager;
import uk.icat3.manager.InvestigationManager;
import uk.icat3.search.AdvancedSearchDetails;
import uk.icat3.search.InvestigationSearch;
import uk.icat3.util.Util;

/**
 * MetadataIngest.java
 *
 * Created on 23-May-2007, 12:15:23
 *
 * This class implements a service that enables a user/facility to submit an xml document that declaratively
 * describes data that is to be ingested into ICAT.  This is designed as an alternative to manipulating api
 * directly which would necessitate familiarity with the ICAT API and would require integration of the ICAT
 * API...
 *
 * uses the icat3-jaxb package to read an icat compliant xml file for ingestion
 * into ICAT.  The database is queried to decipher where
 *
 *
 * ISIS will use this service at the end of each experimental run to submit metadata for a resulting raw / log / nexus file
 * read xml into object tree
 * find related investigation
 * if related investigation found then place datafile within that investigation
 * if no related investigation found then a new one must be created along with all other related artifacts e.g. datasets, samples etc.
 *
 *
 * @author df01
 */
public class MetadataIngest {

    // Global class logger
    static Logger log = Logger.getLogger(MetadataIngest.class);
    static String RUN_NUMBER = "RUN_NUMBER";
    static int RUN_NUMBER_THRESHOLD = 10;
    static long DATE_THRESHOLD = 604800000; //one week in seconds

    /**
     * Method that accepts XML document in the form of a String for ingestion into ICAT
     * Spawns insert off to asynchronous MessageDrivenBean for efficiency
     *
     * @param userId
     * @param xml
     * @param manager
     * @return
     * @throws java.lang.Exception
     *
     */
    public static Long[] ingestMetadata(String userId, String xml, EntityManager manager) throws ICATAPIException {
        Icat icat = null;

        //read xml file
        try {
            icat = MetadataParser.parseMetadata(userId, xml);
            return ingestMetadata(userId, icat, manager);
        } catch (ICATAPIException iae) {
            throw iae;
        } catch (Exception e) {
            throw new ValidationException("An error occurred while trying to parse metadata for ingestion into ICAT ", e);
        } //end try/catch
    }

    public static Long[] ingestMetadata(String userId, Icat icat, EntityManager manager) throws ICATAPIException {
        Long investigationId = null;
        uk.icat3.entity.Investigation investigation = null;
        ArrayList invIds = new ArrayList();
        Long returnIds = null;

        try {

            List<Study> _studies = icat.getStudy();
            for (Study _study : _studies) {
                List<Investigation> _investigations = _study.getInvestigation();

                for (Investigation _inv : _investigations) {
                    //After successful parsing of xml, check to see if datafile belongs to existing investigation
                    investigationId = findMatchingInvestigation(userId, _inv, manager);

                    //If datafile belongs to existing investigation then ingest just the datafile
                    if (investigationId != null) {
                        //ingest datafile
                        investigation = InvestigationManager.getInvestigation(userId, investigationId, manager);

                        //may want to do extra checks here to make sure metadata is correct
                        //..
                        //.
                    } //end if
                    //If datafile does not belong to existing investigation then ingest everything
                    if (investigationId == null) {
                        investigation = getInvestigation(userId, _inv, manager);
                        investigation = InvestigationManager.createInvestigation(userId, investigation, manager);
                    } //end if
                    invIds.add(investigation.getId());

                    //add keywords
                    List<uk.icat3.jaxb.gen.Keyword> _keywords = _inv.getKeyword();
                    ArrayList<uk.icat3.entity.Keyword> keywords = getKeywords(_keywords, investigation.getId());
                    for (uk.icat3.entity.Keyword keyword : keywords) {
                        try {
                            InvestigationManager.addInvestigationObject(userId, keyword, investigation.getId(), manager);
                        } catch (Exception ve) {
                            //catch validation exception i.e. if duplicate keyword exists, allow ingestion of other keywords to continue
                        } //end try/catch
                    } //end
                    //add publications
                    List<uk.icat3.jaxb.gen.Publication> _publications = _inv.getPublication();
                    ArrayList<uk.icat3.entity.Publication> publications = getPublications(_publications, investigation.getId());
                    for (uk.icat3.entity.Publication publication : publications) {
                        try {
                            InvestigationManager.addInvestigationObject(userId, publication, investigation.getId(), manager);
                        } catch (ValidationException ve) {
                            //catch validation exception i.e. if duplicate publication exists, allow ingestion of other publications to continue
                        } //end try/catch
                    } //end
                    //add investigators
                    List<uk.icat3.jaxb.gen.Investigator> _investigators = _inv.getInvestigator();
                    ArrayList<uk.icat3.entity.Investigator> investigators = getInvestigators(_investigators, investigation.getId());
                    for (uk.icat3.entity.Investigator investigator : investigators) {
                        try {
                            InvestigationManager.addInvestigationObject(userId, investigator, investigation.getId(), manager);
                        } catch (ValidationException ve) {
                            ve.printStackTrace();
                            //catch validation exception i.e. if duplicate investigator exists, allow ingestion of other investigators to continue
                        } //end try/catch
                    } //end
                    //add permissions for investigators to investigation
                    for (uk.icat3.jaxb.gen.Investigator _investigator : _investigators) {
                        if (!userId.equals(_investigator.getUserId())) {
                            InvestigationManager.addAuthorisation(userId, _investigator.getUserId(), _investigator.getPrivilege(), investigation.getId(), manager);
                        }
                    } //end for
                    //add Investigation Samples (used in experiment pre-population)
                    List<uk.icat3.jaxb.gen.Sample> _samples = _inv.getSample();
                    for (uk.icat3.jaxb.gen.Sample _sample : _samples) {
                        uk.icat3.entity.Sample sample = getSample(userId, _sample, investigation, manager);
                    } //end for
                    List<Dataset> _datasets = _inv.getDataset();
                    for (Dataset _dataset : _datasets) {
                        uk.icat3.entity.Dataset dataset = getDataset(userId, _dataset, investigation, manager);

                        //add permssions for investigators to dataset
                        for (uk.icat3.jaxb.gen.Investigator _investigator : _investigators) {
                            if (!userId.equals(_investigator.getUserId())) {
                                DataSetManager.addAuthorisation(userId, _investigator.getUserId(), _investigator.getPrivilege(), dataset.getId(), manager);
                            }
                        } //end for
                        List<Datafile> _datafiles = _dataset.getDatafile();
                        for (Datafile _datafile : _datafiles) {
                            uk.icat3.entity.Datafile datafile = getDatafile(userId, _datafile, dataset, manager);

                            //add permssions for investigators to datafile
                            for (uk.icat3.jaxb.gen.Investigator _investigator : _investigators) {
                                if (!userId.equals(_investigator.getUserId())) {
                                    DataFileManager.addAuthorisation(userId, _investigator.getUserId(), _investigator.getPrivilege(), datafile.getId(), manager);
                                }
                            } //end for
                        } //end for datafile
                    } //end for
                } //end for
            } //end for
            //return invIds
        } catch (NoSuchObjectFoundException ex) {
            //java.util.logging.Logger.getLogger("global").log(Level.SEVERE, null, ex);
            throw(ex);
        } catch (InsufficientPrivilegesException ex) {
            //java.util.logging.Logger.getLogger("global").log(Level.SEVERE, null, ex);
            throw(ex);
        } //end try / catch#
        return (Long[]) invIds.toArray(new Long[0]);
    }

    /**
     * Method searches database in order to find matching investigation.
     * If a match is found then the Experiment Number is returned otherwise null is returned.
     */
    private static Long findMatchingInvestigation(String userId, uk.icat3.jaxb.gen.Investigation _investigation, EntityManager manager) {
        String experimentNumber = null;
        boolean trusted = false;
        Long investigationId = null;
        AdvancedSearchDetails advanDTO = new AdvancedSearchDetails();

        experimentNumber = _investigation.getInvNumber();
        trusted = _investigation.isTrusted();

        //if experiment number found, try to find a match in the database
        if ((!Util.isEmpty(experimentNumber)) && (trusted)) {
            advanDTO.setExperimentNumber(experimentNumber);
            
            if ((_investigation.getVisitId() != null) && (_investigation.getVisitId().length() >0))
                advanDTO.setVisitId(_investigation.getVisitId());
            
            Collection<uk.icat3.entity.Investigation> investigations = InvestigationSearch.searchByAdvanced(userId, advanDTO, manager);

            //if there is no match return null
            if ((investigations == null) || (investigations.size() == 0)) {
                return null;
            }
            //if there is a match then return investigationId
            for (uk.icat3.entity.Investigation inv : investigations) {
                investigationId = inv.getId();
            } //end for
            return investigationId;
        } //end if
        //if experiment number not found or not trusted then we need to do some detective work
        //...
        //.
        //add instrument to search criteria
        if (!Util.isEmpty(_investigation.getInstrument())) {
            Collection<String> instruments = new ArrayList<String>();
            instruments.add(_investigation.getInstrument());
            advanDTO.setInstruments(instruments);
        } //end if
        /* ISIS Specific - Have instead replaced with Date Range Search below
        //get run number
        Double runNumber = null;
        Collection<Parameter> params = _investigation.getDataset().get(0).getDatafile().get(0).getParameter();
        if ((params != null) && (params.size() >0)) {
        for (Parameter param : params) {
        if (param.getName().equalsIgnoreCase(RUN_NUMBER))
        runNumber = param.getNumericValue();
        }//end for
        }//end if
        if (runNumber != null) {
        advanDTO.setRunStart(new Double(runNumber.longValue() - RUN_NUMBER_THRESHOLD));
        advanDTO.setRunEnd(new Double(runNumber.longValue() + RUN_NUMBER_THRESHOLD));
        }//end if
         */

        //get create date of file
        Date date = null;
        String createTime = _investigation.getDataset().get(0).getDatafile().get(0).getDatafileCreateTime().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            date = sdf.parse(createTime);
            advanDTO.setDateRangeStart(new Date(date.getTime() - DATE_THRESHOLD));
            advanDTO.setDateRangeEnd(new Date(date.getTime() + DATE_THRESHOLD));
        } catch (Exception e) {
            e.printStackTrace();
        } //end try/catch
        //String investigators = null;
        //Collection<Investigator> investigators = _investigation.getInvestigator();
        //advanDTO.setInvestigators(investigators);
        advanDTO.setBackCatalogueInvestigatorString(_investigation.getBcatInvStr());

        Collection<uk.icat3.entity.Investigation> investigations = InvestigationSearch.searchByAdvanced(userId, advanDTO, manager);

        //do soundex on results
        /*
        Soundex soundex = new Soundex();
        if ((investigations != null) && (investigations.size() > 0)) {
            Iterator it = investigations.iterator();
            while (it.hasNext()) {
                try {
                    uk.icat3.entity.Investigation inv = (uk.icat3.entity.Investigation) it.next();
                    //if we get a match of 4 (highest value) see apache codec package, then return match
                    if (soundex.difference(_investigation.getTitle(), inv.getTitle()) == 4) {
                        log.debug("___****___ found match, title: " + inv.getTitle() + ", RB: " + inv.getInvNumber());
                        return new Long(inv.getId());
                    } //end if
                } catch (Exception e) {
                    e.printStackTrace();
                } //end try/catch
            } //end while
        } //end if
        */
        
        if ((investigations != null) && (investigations.size() > 0)) {
            Iterator it = investigations.iterator();
            while (it.hasNext()) {
                try {
                    uk.icat3.entity.Investigation inv = (uk.icat3.entity.Investigation) it.next();
                    //if we get a match of 4 (highest value) see apache codec package, then return match
                    if (StringComparer.compareStrings(_investigation.getTitle(), inv.getTitle()) > .4) {                    
                        log.debug("___****___ found match, title: " + inv.getTitle() + ", RB: " + inv.getInvNumber());
                        return new Long(inv.getId());
                    } //end if
                } catch (Exception e) {
                    e.printStackTrace();
                } //end try/catch
            } //end while
        } //end if 
        
        //if we get here then no match was found, so return null
        return null;
    }

    private static uk.icat3.entity.Investigation getInvestigation(String userId, uk.icat3.jaxb.gen.Investigation investigation, EntityManager manager) {
        uk.icat3.entity.Investigation inv = new uk.icat3.entity.Investigation();
        inv.setBcatInvStr(investigation.getBcatInvStr());
        //inv.setFacilityCycle(investigation.getFacilityCycle());        
        
        inv.setInvAbstract(investigation.getInvAbstract());
        inv.setInvNumber(investigation.getInvNumber());
        inv.setPrevInvNumber(investigation.getPrevInvNumber());
        inv.setTitle(investigation.getTitle());
        inv.setInvType(investigation.getInvType().toLowerCase());                
        
        //check to see if visit id exists
        //...
        AdvancedSearchDetails advanDTO = new AdvancedSearchDetails();
        advanDTO.setExperimentNumber(investigation.getInvNumber());
        //advanDTO.setVisitId(investigation.getVisitId());
        Collection<uk.icat3.entity.Investigation> investigations = InvestigationSearch.searchByAdvanced(userId, advanDTO, manager);
          
        int high = 0;
        boolean exists = false;
        if ((investigations != null) && (investigations.size() > 0)) {
            Iterator it = investigations.iterator();
            while (it.hasNext()) {                                    
                    uk.icat3.entity.Investigation _inv = (uk.icat3.entity.Investigation) it.next();                    
                    if (_inv.getVisitId().equalsIgnoreCase(investigation.getVisitId())) exists = true;
                    
                    try {
                        int vis = Integer.valueOf(_inv.getVisitId());
                        if (vis > high) high = vis;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }//end try/catch
            }//end while                             
        }//end if 
                
        //if does NOT exist then set normally else find highest visit_id and increment by one
        if (!exists) {
            inv.setVisitId(investigation.getVisitId());            
        } else {
            inv.setVisitId(new Integer(high+1).toString());            
        }//end if                                
        
        if (investigation.getInstrument() != null)
            inv.setInstrument(investigation.getInstrument().toLowerCase());

        return inv;
    }

    private static ArrayList<uk.icat3.entity.Keyword> getKeywords(List<uk.icat3.jaxb.gen.Keyword> _keywords, Long investigationId) {
        ArrayList<uk.icat3.entity.Keyword> keywords = new ArrayList<uk.icat3.entity.Keyword>();
        for (uk.icat3.jaxb.gen.Keyword _keyword : _keywords) {
            uk.icat3.entity.KeywordPK pk = new uk.icat3.entity.KeywordPK(_keyword.getName(), investigationId);
            uk.icat3.entity.Keyword keyword = new uk.icat3.entity.Keyword(pk);
            keywords.add(keyword);
        } //end for
        return keywords;
    }

    private static ArrayList<uk.icat3.entity.Publication> getPublications(List<uk.icat3.jaxb.gen.Publication> _publications, Long investigationId) {
        ArrayList<uk.icat3.entity.Publication> publications = new ArrayList<uk.icat3.entity.Publication>();
        for (uk.icat3.jaxb.gen.Publication _publication : _publications) {
            uk.icat3.entity.Publication publication = new uk.icat3.entity.Publication();
            publication.setFullReference(_publication.getFullReference());
            publication.setRepository(_publication.getRepository());
            publication.setRepositoryId(_publication.getRepositoryId());
            publication.setUrl(_publication.getUrl());
            publications.add(publication);
        } //end for
        return publications;
    }

    private static ArrayList<uk.icat3.entity.Investigator> getInvestigators(List<uk.icat3.jaxb.gen.Investigator> _investigators, Long investigationId) {
        ArrayList<uk.icat3.entity.Investigator> investigators = new ArrayList<uk.icat3.entity.Investigator>();
        for (uk.icat3.jaxb.gen.Investigator _investigator : _investigators) {
            uk.icat3.entity.InvestigatorPK pk = new uk.icat3.entity.InvestigatorPK(_investigator.getAffiliationId(), investigationId);
            uk.icat3.entity.Investigator investigator = new uk.icat3.entity.Investigator(pk);
            investigator.setRole(_investigator.getRole());
            investigators.add(investigator);
        } //end for
        return investigators;
    }

    private static uk.icat3.entity.Dataset getDataset(String userId, uk.icat3.jaxb.gen.Dataset _dataset, uk.icat3.entity.Investigation investigation, EntityManager manager) throws ICATAPIException {

        //check to see if dataset exists in database
        uk.icat3.entity.Dataset dataset = null;
        Collection<uk.icat3.entity.Dataset> datasets = investigation.getDatasetCollection();
        for (uk.icat3.entity.Dataset storedDataset : datasets) {
            if (_dataset.getName().equalsIgnoreCase(storedDataset.getName())) {
                dataset = storedDataset;
            } //end if
        } //end for
        //if dataset does not exist in database then create it
        if (dataset == null) {
            dataset = new uk.icat3.entity.Dataset();

            //check to see if sample in database
            if (_dataset.getSample() != null) {
                uk.icat3.jaxb.gen.Sample _sample = _dataset.getSample();
                uk.icat3.entity.Sample sample = getSample(userId, _sample, investigation, manager);
                dataset.setSampleId(sample.getId());
            } //end if
            if (_dataset.getDatasetStatus() != null) {
                dataset.setDatasetStatus(_dataset.getDatasetStatus().toLowerCase());
            } //end if
            if (_dataset.getDatasetType() != null) {
                dataset.setDatasetType(_dataset.getDatasetType().toLowerCase());
            } //end if
            dataset.setDescription(_dataset.getDescription());
            //dataset.setInvestigationId(investigation);
            dataset.setInvestigation(investigation);
            dataset.setName(_dataset.getName());



            //store in database
            DataSetManager.createDataSet(userId, dataset, manager);

            //store dataset parameters
            getDatasetParameters(userId, _dataset.getParameter(), dataset.getId(), manager);
        } //end if
        return dataset;
    }

    private static uk.icat3.entity.Sample getSample(String userId, uk.icat3.jaxb.gen.Sample _sample, uk.icat3.entity.Investigation investigation, EntityManager manager) throws ICATAPIException {

        //check to see if sample exists in investigation
        uk.icat3.entity.Sample sample = null;
        Collection<uk.icat3.entity.Sample> samples = investigation.getSampleCollection();
        for (uk.icat3.entity.Sample storedSample : samples) {
            if (_sample.getName().equalsIgnoreCase(storedSample.getName())) {
                sample = storedSample;
            } //end if
        } //end for
        //if sample is null then create a new one
        if (sample == null) {
            sample = new uk.icat3.entity.Sample();
            sample.setInvestigationId(investigation);
            sample.setName(_sample.getName());
            sample.setInstance(_sample.getInstance());
            //sample.setProposalSampleId(_sample.get);
            sample.setSafetyInformation(_sample.getSafetyInformation());

            //store in database
            sample = (uk.icat3.entity.Sample) uk.icat3.manager.InvestigationManager.addInvestigationObject(userId, sample, investigation.getId(), manager);

            //also store sample parameters (if there are any)
            getSampleParameters(userId, _sample.getParameter(), sample.getId(), investigation, manager);
        } //end if
        return sample;
    }

    private static ArrayList<uk.icat3.entity.DatafileParameter> getDatafileParameters(String userId, List<uk.icat3.jaxb.gen.Parameter> _parameters, Long datafileId, EntityManager manager) throws ICATAPIException {
        ArrayList<uk.icat3.entity.DatafileParameter> parameters = new ArrayList<uk.icat3.entity.DatafileParameter>();
        for (uk.icat3.jaxb.gen.Parameter _parameter : _parameters) {
            uk.icat3.entity.DatafileParameterPK pk = new uk.icat3.entity.DatafileParameterPK();
            pk.setDatafileId(datafileId);
            pk.setName(_parameter.getName());
            pk.setUnits(_parameter.getUnits());

            uk.icat3.entity.DatafileParameter parameter = new uk.icat3.entity.DatafileParameter(pk);
            parameter.setDescription(_parameter.getDescription());
            parameter.setError(_parameter.getError());
            parameter.setNumericValue(_parameter.getNumericValue());
            parameter.setRangeBottom(_parameter.getRangeBottom());
            parameter.setRangeTop(_parameter.getRangeTop());
            parameter.setStringValue(_parameter.getStringValue());

            parameter = DataFileManager.addDataFileParameter(userId, parameter, datafileId, manager);

            parameters.add(parameter);
        } //end for
        return parameters;
    }

    private static uk.icat3.entity.Datafile getDatafile(String userId, uk.icat3.jaxb.gen.Datafile _datafile, uk.icat3.entity.Dataset dataset, EntityManager manager) throws ICATAPIException {

        uk.icat3.entity.Datafile datafile = new uk.icat3.entity.Datafile();
        //datafile.setDatasetId(dataset);
        datafile.setDataset(dataset);
        if (_datafile.getChecksum() != null) {
            datafile.setChecksum(_datafile.getChecksum());
        }
        if (_datafile.getCommand() != null) {
            datafile.setCommand(_datafile.getCommand());
        }
        if (_datafile.getDatafileFormat() != null) {
            DatafileFormat format = new DatafileFormat(_datafile.getDatafileFormatVersion(), _datafile.getDatafileFormat().toLowerCase());
            datafile.setDatafileFormat(format);
        } //end if
        if (_datafile.getDatafileModifyTime() != null) {
            datafile.setDatafileModifyTime(Util.parseDate(_datafile.getDatafileModifyTime().toXMLFormat()));
        }
        if (_datafile.getDatafileCreateTime() != null) {
            datafile.setDatafileCreateTime(Util.parseDate(_datafile.getDatafileCreateTime().toXMLFormat()));
        }
        datafile.setDatafileVersion(_datafile.getDatafileVersion());
        datafile.setDatafileVersionComment(_datafile.getDatafileVersionComment());
        datafile.setDescription(_datafile.getDescription());

        if (_datafile.getFileSize() != null) {
            datafile.setFileSize(_datafile.getFileSize().intValue());
        }
        datafile.setLocation(_datafile.getLocation());
        datafile.setName(_datafile.getName());
        datafile.setSignature(_datafile.getSignature());

        //store in database
        datafile = DataFileManager.createDataFile(userId, datafile, dataset.getId(), manager);

        //store datafile parameters
        List<Parameter> _dfParams = _datafile.getParameter();
        getDatafileParameters(userId, _dfParams, datafile.getId(), manager);

        return datafile;
    }

    private static ArrayList<uk.icat3.entity.DatasetParameter> getDatasetParameters(String userId, List<uk.icat3.jaxb.gen.Parameter> _parameters, Long datasetId, EntityManager manager) throws ICATAPIException {
        ArrayList<uk.icat3.entity.DatasetParameter> parameters = new ArrayList<uk.icat3.entity.DatasetParameter>();
        for (uk.icat3.jaxb.gen.Parameter _parameter : _parameters) {
            uk.icat3.entity.DatasetParameterPK pk = new uk.icat3.entity.DatasetParameterPK();
            pk.setDatasetId(datasetId);
            pk.setName(_parameter.getName());
            pk.setUnits(_parameter.getUnits());

            uk.icat3.entity.DatasetParameter parameter = new uk.icat3.entity.DatasetParameter(pk);
            parameter.setDescription(_parameter.getDescription());
            parameter.setError(_parameter.getError());
            parameter.setNumericValue(_parameter.getNumericValue());
            parameter.setRangeBottom(_parameter.getRangeBottom());
            parameter.setRangeTop(_parameter.getRangeTop());
            parameter.setStringValue(_parameter.getStringValue());

            parameter = DataSetManager.addDataSetParameter(userId, parameter, datasetId, manager);

            parameters.add(parameter);
        } //end for
        return parameters;
    }

    private static ArrayList<uk.icat3.entity.SampleParameter> getSampleParameters(String userId, List<uk.icat3.jaxb.gen.Parameter> _parameters, Long sampleId, uk.icat3.entity.Investigation investigation, EntityManager manager) throws ICATAPIException {
        ArrayList<uk.icat3.entity.SampleParameter> parameters = new ArrayList<uk.icat3.entity.SampleParameter>();
        for (uk.icat3.jaxb.gen.Parameter _parameter : _parameters) {
            uk.icat3.entity.SampleParameterPK pk = new uk.icat3.entity.SampleParameterPK();
            pk.setSampleId(sampleId);
            pk.setName(_parameter.getName());
            pk.setUnits(_parameter.getUnits());

            uk.icat3.entity.SampleParameter parameter = new uk.icat3.entity.SampleParameter(pk);
            //parameter.setDescription(_parameter.getDescription());
            parameter.setError(_parameter.getError());
            parameter.setNumericValue(_parameter.getNumericValue());
            parameter.setRangeBottom(_parameter.getRangeBottom());
            parameter.setRangeTop(_parameter.getRangeTop());
            parameter.setStringValue(_parameter.getStringValue());

            //store in database
            parameter = (uk.icat3.entity.SampleParameter) uk.icat3.manager.InvestigationManager.addInvestigationObject(userId, parameter, investigation.getId(), manager);

            parameters.add(parameter);
        } //end for
        return parameters;
    }
}