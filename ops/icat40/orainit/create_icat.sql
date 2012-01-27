-- $Id$
  DROP TABLE "APPLICATIONS" cascade constraints;
  DROP TABLE "DATAFILE" cascade constraints;
  DROP TABLE "DATAFILE_FORMAT" cascade constraints;
  DROP TABLE "DATAFILE_PARAMETER" cascade constraints;
  DROP TABLE "DATASET" cascade constraints;
  DROP TABLE "DATASET_PARAMETER" cascade constraints;
  DROP TABLE "DATASET_STATUS" cascade constraints;
  DROP TABLE "DATASET_TYPE" cascade constraints;
  DROP TABLE "FACILITY_CYCLE" cascade constraints;
  DROP TABLE "FACILITY_INSTRUMENT_SCIENTIST" cascade constraints;
  DROP TABLE "FACILITY_USER" cascade constraints;
  DROP TABLE "INSTRUMENT" cascade constraints;
  DROP TABLE "INVESTIGATION" cascade constraints;
  DROP TABLE "INVESTIGATION_TYPE" cascade constraints;
  DROP TABLE "INVESTIGATOR" cascade constraints;
  DROP TABLE "KEYWORD" cascade constraints;
  DROP TABLE "PARAMETER" cascade constraints;
  DROP TABLE "PUBLICATION" cascade constraints;
  DROP TABLE "RELATED_DATAFILES" cascade constraints;
  DROP TABLE "RULE" cascade constraints;
  DROP TABLE "SAMPLE" cascade constraints;
  DROP TABLE "SAMPLE_PARAMETER" cascade constraints;
  DROP TABLE "SHIFT" cascade constraints;
  DROP TABLE "SOFTWARE_VERSION" cascade constraints;
  DROP TABLE "STUDY" cascade constraints;
  DROP TABLE "STUDY_INVESTIGATION" cascade constraints;
  DROP TABLE "STUDY_STATUS" cascade constraints;
  DROP TABLE "THIS_ICAT" cascade constraints;
  DROP TABLE "TOPIC" cascade constraints;
  DROP TABLE "TOPIC_LIST" cascade constraints;
  DROP TABLE "USERGROUP" cascade constraints;
  DROP TABLE "USER_ROLES" cascade constraints;
  DROP SEQUENCE "DATAFILE_ID_SEQ";
  DROP SEQUENCE "DATASET_ID_SEQ";
  DROP SEQUENCE "INVESTIGATION_ID_SEQ";
  DROP SEQUENCE "PUBLICATION_ID_SEQ";
  DROP SEQUENCE "RULE_ID_SEQ";
  DROP SEQUENCE "SAMPLE_ID_SEQ";
  DROP SEQUENCE "SOFTWARE_VERSION_ID_SEQ";
  DROP SEQUENCE "STUDY_ID_SEQ";
  DROP SEQUENCE "TOPIC_ID_SEQ";

  CREATE SEQUENCE  "DATAFILE_ID_SEQ" NOCACHE;

  CREATE SEQUENCE  "DATASET_ID_SEQ" NOCACHE;

  CREATE SEQUENCE  "INVESTIGATION_ID_SEQ"  NOCACHE;

  CREATE SEQUENCE  "PUBLICATION_ID_SEQ"   NOCACHE;

  CREATE SEQUENCE  "RULE_ID_SEQ"   NOCACHE;

  CREATE SEQUENCE  "SAMPLE_ID_SEQ"  NOCACHE;

  CREATE SEQUENCE  "SOFTWARE_VERSION_ID_SEQ"  NOCACHE;

  CREATE SEQUENCE  "STUDY_ID_SEQ"  NOCACHE;

  CREATE SEQUENCE  "TOPIC_ID_SEQ"  NOCACHE;

  CREATE TABLE "APPLICATIONS" 
   (	"APP_CODE" VARCHAR2(10 BYTE), 
	"APP_NAME" VARCHAR2(255 BYTE), 
	"APP_DESCRIPTION" VARCHAR2(4000 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   ) ;

   COMMENT ON COLUMN "APPLICATIONS"."APP_CODE" IS 'Unique identifier for this application in the system.';
   COMMENT ON COLUMN "APPLICATIONS"."APP_NAME" IS 'Name of the application.';
   COMMENT ON COLUMN "APPLICATIONS"."APP_DESCRIPTION" IS 'Description of the application.';
   COMMENT ON TABLE "APPLICATIONS"  IS 'Stores descriptions of the Oracle Application Express applications which use this schema. The table USER_ROLES is linked to APPLICATIONS. In future version of ICAT these tables will be removed as applications interact with ICAT via the ICAT API.';

  CREATE TABLE "DATAFILE" 
   (	"ID" NUMBER(38,0), 
	"DATASET_ID" NUMBER, 
	"NAME" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(255 BYTE), 
	"DATAFILE_VERSION" VARCHAR2(255 BYTE), 
	"DATAFILE_VERSION_COMMENT" VARCHAR2(4000 BYTE), 
	"LOCATION" VARCHAR2(4000 BYTE), 
	"DATAFILE_FORMAT" VARCHAR2(255 BYTE), 
	"DATAFILE_FORMAT_VERSION" VARCHAR2(255 BYTE), 
	"DATAFILE_CREATE_TIME" TIMESTAMP (1), 
	"DATAFILE_MODIFY_TIME" TIMESTAMP (1), 
	"FILE_SIZE" NUMBER, 
	"COMMAND" VARCHAR2(4000 BYTE), 
	"CHECKSUM" VARCHAR2(255 BYTE), 
	"SIGNATURE" VARCHAR2(4000 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "DATAFILE"."ID" IS 'Key';
   COMMENT ON COLUMN "DATAFILE"."DATASET_ID" IS 'Key of parent dataset.';
   COMMENT ON COLUMN "DATAFILE"."NAME" IS 'Logical name of datafile.';
   COMMENT ON COLUMN "DATAFILE"."DESCRIPTION" IS 'Description of contents, mechanism of capture or other relevant details pertaining to the datafile. It is expected that the topics covered by the description will be consistent across datafiles collected at a particular facility.';
   COMMENT ON COLUMN "DATAFILE"."DATAFILE_VERSION" IS 'There are many situations in which a collected file may need to be replaced by a newer version. For example if the software creating the datafiles is incorrect or being fed incorrect data (incorrect algorithms or stale sample information) then erroneous data could be present in the final data file. Correction of the datafile due to these type of errors could then be recorded and a new version registered.';
   COMMENT ON COLUMN "DATAFILE"."DATAFILE_VERSION_COMMENT" IS 'Describes why there was a new version of the datafile and what has changed. If the change which caused a new version of the file be produced is relevant for user and their data analysis they can then choose to update their files to the newer versions.';
   COMMENT ON COLUMN "DATAFILE"."LOCATION" IS 'The physical location of the file. This will be facility specific e.g. in the case of Diamond Light Source this would be an SRB schemed URL. The locations are expected to be fully qualified but they could be relative if the parent dataset location is specified, again this should be consistent across all the datafiles associated with a facility.';
   COMMENT ON COLUMN "DATAFILE"."DATAFILE_FORMAT" IS 'Format of the file. Permitted values are specified in DATAFILE_FORMAT.';
   COMMENT ON COLUMN "DATAFILE"."DATAFILE_FORMAT_VERSION" IS 'This can be removed in future versions of ICAT as the data already exists in DATAFILE_FORMAT.';
   COMMENT ON COLUMN "DATAFILE"."DATAFILE_CREATE_TIME" IS 'Useful parameter in the datafile which can be used for various things. It allows one to search for all files which were modified after they were created to solve consistency issues. Can be used to calculate the actual extent in of any experiment. ';
   COMMENT ON COLUMN "DATAFILE"."DATAFILE_MODIFY_TIME" IS 'Used with DATAFILE_CREATE_TIME.';
   COMMENT ON COLUMN "DATAFILE"."FILE_SIZE" IS 'This is the actual size in bytes of the file (as oppose to the size on a storage device which maybe greater due to block size issues). This column should be a parameter but it is needed often and thus is present at this level.';
   COMMENT ON COLUMN "DATAFILE"."COMMAND" IS 'This stores the command line used to create this file. This is primarily seen as related to datafiles which have come out of data analysis work. Also it is present here and not in the RELATED_DATAFILES table as the source datafile may not be held in the catalog, but be externally referenced. This could be an area where registration of dependencies in data analysis is seen as best practice.';
   COMMENT ON COLUMN "DATAFILE"."CHECKSUM" IS 'In later releases of ICAT this piece of fixity information should be moved to a DATAFILE_PARAMETER and the checksum_standard should also be captured.';
   COMMENT ON COLUMN "DATAFILE"."SIGNATURE" IS 'In later releases of ICAT this piece of fixity information should be moved to a DATAFILE_PARAMETER and the signature_standard should also be captured.';
   COMMENT ON TABLE "DATAFILE"  IS 'Information about the datafiles associated with this facility. This table stores information about logical names and physical locations. Some parameters (e.g. create time and fixity information) are also stored however the majority of this information is now in the DATAFILE_PARAMETER table e.g. the uA hours value for datafiles which gave the ISIS scientists an idea of how much data was collected has been moved to associated entries in the DATAFILE_PARAMETER table.';

  CREATE TABLE "DATAFILE_FORMAT" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"VERSION" VARCHAR2(255 BYTE), 
	"FORMAT_TYPE" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "DATAFILE_FORMAT"."NAME" IS 'Name of the format.';
   COMMENT ON COLUMN "DATAFILE_FORMAT"."VERSION" IS 'Version of the format.';
   COMMENT ON COLUMN "DATAFILE_FORMAT"."FORMAT_TYPE" IS 'The type of the format e.g. binary, HDF5, XML.';
   COMMENT ON COLUMN "DATAFILE_FORMAT"."DESCRIPTION" IS 'A description of the format relating to the type of data that it holds, this could be a link to more information about the format also.';
   COMMENT ON TABLE "DATAFILE_FORMAT"  IS 'Holds the valid datafile formats that are permitted in DATAFILE.';

  CREATE TABLE "DATAFILE_PARAMETER" 
   (	"DATAFILE_ID" NUMBER, 
	"NAME" VARCHAR2(255 BYTE), 
	"UNITS" VARCHAR2(255 BYTE), 
	"STRING_VALUE" VARCHAR2(4000 BYTE), 
	"NUMERIC_VALUE" FLOAT(126), 
	"DATETIME_VALUE" TIMESTAMP (3), 
	"RANGE_TOP" VARCHAR2(255 BYTE), 
	"RANGE_BOTTOM" VARCHAR2(255 BYTE), 
	"ERROR" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "DATAFILE_PARAMETER"."DESCRIPTION" IS 'from where and how the parameter was recorded or extracted as oppose to a definition of the parameter; the latter being defined in the parameter table';

  CREATE TABLE "DATASET" 
   (	ID NUMBER(38,0), 
	SAMPLE_ID NUMBER, 
	INVESTIGATION_ID NUMBER, 
	NAME VARCHAR2(255 BYTE), 
	DATASET_TYPE VARCHAR2(255 BYTE), 
	DATASET_STATUS VARCHAR2(255 BYTE), 
	LOCATION VARCHAR2(4000 BYTE), 
	DESCRIPTION VARCHAR2(4000 BYTE),
        START_DATE TIMESTAMP(1),
        END_DATE TIMESTAMP(1),
	MOD_TIME TIMESTAMP (1), 
	MOD_ID VARCHAR2(255 BYTE), 
	CREATE_TIME TIMESTAMP (1), 
	CREATE_ID VARCHAR2(255 BYTE)
   );

   COMMENT ON COLUMN "DATASET"."SAMPLE_ID" IS 'Key to the related sample from the SAMPLE table. This can be unset as calibration datasets will not have a sample.  As an aside it should be noted that data in the SAMPLE table is often sourced from the Proposal and that what people propose and what they bring maybe different so some process of manual reconciliation may be needed as this is not always possible to automate.';
   COMMENT ON COLUMN "DATASET"."INVESTIGATION_ID" IS 'Key of the related INVESTIGATION.';
   COMMENT ON COLUMN "DATASET"."NAME" IS 'The name of the dataset. This naming convention depends upon facility specific policies and is a function of how data is registered with the ICAT.';
   COMMENT ON COLUMN "DATASET"."DATASET_STATUS" IS 'This show the status of the data collection. Examples of values are empty, ongoing (e.g. CLF waiting for the glass plate to be analysed) or complete.';
   COMMENT ON COLUMN "DATASET"."LOCATION" IS 'This is the location or root designation of the dataset. This is useful for specifying all the files in a directory, or for giving a physical root designation to nested files locations specified in the flat datafile table to allow applications to display and download the nested structures.';
   COMMENT ON COLUMN "DATASET"."DESCRIPTION" IS 'This give a description of the dataset. This could hold a variety of information or could be empty.
An example of use is storage of a description of the data collected at a facility filled in by data acquisition systems e.g. important parameters (e.g. temperature might change a few degrees, but the angle of the sample might be very important so this is the place such information should be stored) or this could be used to describe how the data was ''cut''; in the case of CLF they cut by shot - i.e. one dataset per shot which is important information for understanding the data organisation. Also when DATASET_TYPE is PRE_EXPERIMENT_DATE this field could be used to describe why certain files have been added and what their purpose is (e.g. simulation files).';
   COMMENT ON TABLE "DATASET"  IS 'Groups a set of files. Illustrative groupings include pre-experimental data including electronic copies of the proposal or simulation data, experimental data, analysed data and final data.';

  CREATE TABLE "DATASET_PARAMETER" 
   (	"DATASET_ID" NUMBER, 
	"NAME" VARCHAR2(255 BYTE), 
	"UNITS" VARCHAR2(255 BYTE), 
	"STRING_VALUE" VARCHAR2(4000 BYTE), 
	"NUMERIC_VALUE" FLOAT(126), 
	"DATETIME_VALUE" TIMESTAMP (3), 
	"RANGE_TOP" VARCHAR2(255 BYTE), 
	"RANGE_BOTTOM" VARCHAR2(255 BYTE), 
	"ERROR" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "DATASET_PARAMETER"."DESCRIPTION" IS 'from where and how the parameter was recorded or extracted as oppose to a definition of the parameter; the latter being defined in the parameter table';

  CREATE TABLE "DATASET_STATUS" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "DATASET_STATUS"."NAME" IS 'A meaningful status name. In future versions of ICAT this could be changed to STATUS.';
   COMMENT ON COLUMN "DATASET_STATUS"."DESCRIPTION" IS 'A description of what the status term means, for example if this is empty does it mean that this is a placeholder for data or that no data will ever be added and this is a tag of some kind.';
   COMMENT ON TABLE "DATASET_STATUS"  IS 'Holds the status of datafiles; for example are they empty and therefore placeholders, being added to or complete.';

  CREATE TABLE "DATASET_TYPE" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "DATASET_TYPE"."NAME" IS 'Designates a particular dataset type e.g. experiment_raw.';
   COMMENT ON COLUMN "DATASET_TYPE"."DESCRIPTION" IS 'A description of the dataset type e.g. raw data collected at the facility during an experiment.';
   COMMENT ON TABLE "DATASET_TYPE"  IS 'Holds the valid set of dataset types.';

  CREATE TABLE "FACILITY_CYCLE" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"START_DATE" TIMESTAMP (1), 
	"FINISH_DATE" TIMESTAMP (1), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "FACILITY_CYCLE"."NAME" IS 'Facility specific designator of a cycle. Facilities have systematic naming formats for this e.g. ISIS use the start and end dates for creating cycle names. In terms of data organisation, facilities have found cycles a useful way of delineating their data.';
   COMMENT ON COLUMN "FACILITY_CYCLE"."START_DATE" IS 'Start date of the cycle.';
   COMMENT ON COLUMN "FACILITY_CYCLE"."FINISH_DATE" IS 'Finish date of the cycle.';
   COMMENT ON COLUMN "FACILITY_CYCLE"."DESCRIPTION" IS 'A description of the cycle including any noteworthy events relevant for understanding the data archive state.';
   COMMENT ON TABLE "FACILITY_CYCLE"  IS 'Designates the time between any two shutdown periods for a facility i.e. active time when calibrations and experiments are being done.';

  CREATE TABLE "FACILITY_INSTRUMENT_SCIENTIST" 
   (	"INSTRUMENT_NAME" VARCHAR2(255 BYTE), 
	"FEDERAL_ID" VARCHAR2(255 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "FACILITY_INSTRUMENT_SCIENTIST"."INSTRUMENT_NAME" IS 'The facility name of the device from which data is being collected e.g. Instrument name, Beamline name or Target area name for the STFC facilities.';
   COMMENT ON COLUMN "FACILITY_INSTRUMENT_SCIENTIST"."FEDERAL_ID" IS 'The organisational identifier associated with the designated authority. For example in the case of STFC this is the Active Directory user name that the individual uses to login to corporate services.';
   COMMENT ON TABLE "FACILITY_INSTRUMENT_SCIENTIST"  IS 'Holds information pertaining to facility designated authorities with regards to particular user scheduled experimental equipment. For example, for ISIS this would be instruments, for Diamond this would be beamlines and for CLF this would be target areas. These designated authorities have privilege in the ICAT system to access to all the data collected at their instruments.';

  CREATE TABLE "FACILITY_USER" 
   (	"FACILITY_USER_ID" VARCHAR2(255 BYTE), 
	"FEDERAL_ID" VARCHAR2(255 BYTE), 
	"TITLE" VARCHAR2(255 BYTE), 
	"INITIALS" VARCHAR2(255 BYTE), 
	"FIRST_NAME" VARCHAR2(255 BYTE), 
	"MIDDLE_NAME" VARCHAR2(255 BYTE), 
	"LAST_NAME" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )   ;

   COMMENT ON COLUMN "FACILITY_USER"."FACILITY_USER_ID" IS 'FACILITY_USER_ID should be self consistent across the database and usually refers to a user numbering system which is valid inside a particular facility in the case of ISIS and DLS this is a number based system but it need not be.';
   COMMENT ON COLUMN "FACILITY_USER"."FEDERAL_ID" IS 'FEDERAL_ID should be self consistent across the database and usually refers to a user numbering system which is valid across a (virtual or real) organisation e.g. the CICT issued Federal Identifiers at STFC. Thus it is generalised as a string as this can accommodate a numbering system also.';
   COMMENT ON COLUMN "FACILITY_USER"."TITLE" IS 'The TITLE of the individual (for example Mr, Mrs, Ms, Miss, Professor, Dr.) This information is often taken from facility user database or business systems.';
   COMMENT ON COLUMN "FACILITY_USER"."INITIALS" IS 'Initials of the user often taken from the facility business system.';
   COMMENT ON COLUMN "FACILITY_USER"."FIRST_NAME" IS 'The first name of the facility user.';
   COMMENT ON COLUMN "FACILITY_USER"."MIDDLE_NAME" IS 'Middle name if any of the facility user. In future versions of ICAT this should probably be changed to MIDDLE_NAMES i.e. plural.';
   COMMENT ON COLUMN "FACILITY_USER"."LAST_NAME" IS 'Last name of the facility user';
   COMMENT ON TABLE "FACILITY_USER"  IS 'Holds information pertaining to people associated with investigations whether proposers or experimenters. It also is associates facility issued user identifiers with corporate identifiers of the organisational context within which the facilities operate. The former is used to register proposals and associated data and the latter used to access services offered by the organisation to access that data for example.';

  CREATE TABLE "INSTRUMENT" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"SHORT_NAME" VARCHAR2(30 BYTE), 
	"TYPE" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "INSTRUMENT"."NAME" IS 'The name of the instrument.';
   COMMENT ON COLUMN "INSTRUMENT"."SHORT_NAME" IS 'Needed as often (e.g. ISIS)  instrument short names cannot be generated automatically from long names e.g. CRISP (CSP), HRPD (HRP), PRISMA (PRS).';
   COMMENT ON COLUMN "INSTRUMENT"."TYPE" IS 'The type or classification of the device used for data collection such that similar facilities could understand what the instrument does from the type. E.g. at ISIS HRPD is a Powder Diffractometer, other Neutron sources also have Powder Diffractometers, but the specific name of their device would be different.';
   COMMENT ON COLUMN "INSTRUMENT"."DESCRIPTION" IS 'A longer description or link to longer description of the device used for data collection.';
   COMMENT ON TABLE "INSTRUMENT"  IS 'The name of this table should be changed in future version of ICAT to something more generic. As this table lists the name for the facility designated device which is normally associated with data collection.';

  CREATE TABLE "INVESTIGATION" 
   (	"ID" NUMBER(38,0), 
	"INV_NUMBER" VARCHAR2(255 BYTE), 
	"VISIT_ID" VARCHAR2(255 BYTE), 
	"FACILITY" VARCHAR2(30 BYTE), 
	"FACILITY_CYCLE" VARCHAR2(255 BYTE), 
	"INSTRUMENT" VARCHAR2(255 BYTE), 
	"TITLE" VARCHAR2(255 BYTE), 
	"INV_TYPE" VARCHAR2(255 BYTE), 
	"INV_ABSTRACT" VARCHAR2(4000 BYTE), 
	"PREV_INV_NUMBER" VARCHAR2(255 BYTE), 
	"BCAT_INV_STR" VARCHAR2(255 BYTE), 
	"GRANT_ID" NUMBER, 
	"INV_PARAM_NAME" VARCHAR2(255 BYTE), 
	"INV_PARAM_VALUE" VARCHAR2(4000 BYTE), 
	"INV_START_DATE" TIMESTAMP (1), 
	"INV_END_DATE" TIMESTAMP (1), 
	"RELEASE_DATE" TIMESTAMP (1), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE), 
	"SRC_HASH" VARCHAR2(32 BYTE)
   )  ;

   COMMENT ON COLUMN "INVESTIGATION"."ID" IS 'Key for the table referred to in other tables as INVESTIGATION_ID.';
   COMMENT ON COLUMN "INVESTIGATION"."INV_NUMBER" IS 'This is the experiment number e.g. the RB number from ISIS. In the case of ISIS, this is usually derived from the proposal number, but proposals can be split into separate experiments each with their own number, in which case it actually maps to the approved proposal number.';
   COMMENT ON COLUMN "INVESTIGATION"."VISIT_ID" IS 'Sometimes (e.g. in the case of DLS) investigations are consortium based, i.e. carried out by a range of people from different institutions who manage their own time slot on the instrument. So in effect the investigation is multi-faceted where different groups should not have access to the data from other groups. Thus a visit identifier is required to differentiate the different groups in the consortium based proposal and the experiments that they perform. In the case of ISIS and CLF this can be set to null or a constant value.';
   COMMENT ON COLUMN "INVESTIGATION"."FACILITY" IS 'Derived from THIS_ICAT.FACILITY_SHORT_NAME often needed in front end applications and for differentiating results in multiple ICAT queries and so is here as an optimisation.';
   COMMENT ON COLUMN "INVESTIGATION"."FACILITY_CYCLE" IS 'Defined as the time between two maintenance periods of the light, neutron or laser sources. this is usually a name - e.g. year and number. Valid values are specificed in the FACILIY_CYCLE.NAME column.';
   COMMENT ON COLUMN "INVESTIGATION"."INSTRUMENT" IS 'Multiple instruments per approved proposal are different investigations with different instrument inside ICAT. Feature requested by DLS and ISIS, the other solutions was to attach the  instrument at the dataset level but this was seen as problematic due in part to common searches being in terms of instrument and experiment number as oppose to title and drilling down further.';
   COMMENT ON COLUMN "INVESTIGATION"."TITLE" IS 'The proposal is usually the source of the INVESTIGATION.TITLE however this could be modified by the facility to reflect more accurately the real experiment being performed as oppose to the one specified in the proposal.';
   COMMENT ON COLUMN "INVESTIGATION"."INV_TYPE" IS 'Valid value will be from amongst the ones available from INVESTIGATION_TYPE.NAME_VALUES e.g. common ones will be experiment or calibration. Note - as calibrations can be ad hoc not just at the beginning of a cycle but also when an instrument is fixed then these are  modelled as separate investigations. The linkage to these by experimental investigations is proposed to done via the range of timestamps on the collected datafiles in that particular experiment.';
   COMMENT ON COLUMN "INVESTIGATION"."INV_ABSTRACT" IS 'Description of the experiment, e.g. based on the proposal.';
   COMMENT ON COLUMN "INVESTIGATION"."PREV_INV_NUMBER" IS 'Experiment number of a preceding and related experiment, e.g. in a chain of such experiments. This has not be used in practise and may be a candidate for removal in future versions of ICAT as aggregation of studies can be done in the STUDY_INVESTIGATION table.';
   COMMENT ON COLUMN "INVESTIGATION"."BCAT_INV_STR" IS 'Short hand for representing a best guess at the Principal Investigator - this is used at ISIS when mining metadata from the back catalog (where no matching proposal information was available) and the column is short for back catalog investigator string. This could be changed to Principal_Investigator_Institution in future versions of ICAT to make it more widely of use. ';
   COMMENT ON COLUMN "INVESTIGATION"."GRANT_ID" IS 'This was meant to hold information about who has funded the experiment. However in practise this was never used by any of the facilities, so should in future versions of ICAT be removed.';
   COMMENT ON COLUMN "INVESTIGATION"."INV_PARAM_NAME" IS 'Holds the defining parameter name for investigation based on the facility, e.g. in ISIS this may be run number range for the experiment. This may later be removed and replaces with a INVESTIGATION_PARAMETER table in versions of ICAT after 3.3. These investigation parameters could also be used to replace keyword, but this needs some discussion. Also if nested parameters were supported then the topic table could be removed also. This would require significant rework to dependent software system but would be an excellent generalisation mechanism for ICAT. ';
   COMMENT ON COLUMN "INVESTIGATION"."INV_PARAM_VALUE" IS 'The facility specific value of the inv_param_name. E.g. in the case of ISIS the actual range of numbers in some specified format for the range of values designating the run numbers.';
   COMMENT ON COLUMN "INVESTIGATION"."INV_START_DATE" IS 'The official start date-time of the experiment. E.g. in ISIS as of the time of writing this could be derived from the date-time that the first raw datafile was read from the instrument.';
   COMMENT ON COLUMN "INVESTIGATION"."INV_END_DATE" IS 'The official end date-time of the experiment. E.g. in ISIS as of the time of writing this could be derived from the date-time that the latest raw datafile was created on the instrument.';
   COMMENT ON COLUMN "INVESTIGATION"."RELEASE_DATE" IS 'This is the date in the future that the raw data will be made available to other users (or publicly available) - this is informed by the data policy of the facility (e.g. 3 years for ISIS).';
   COMMENT ON COLUMN "INVESTIGATION"."SRC_HASH" IS 'This stores a hash key to identify the records in a business system (e.g DUO Desk) which is the source of approved proposals in ICAT. From the Diamond DUO Desk primary key values are hashed together; this is needed as one ICAT record is often sourced from multiple records in the business system.';
   COMMENT ON TABLE "INVESTIGATION"  IS 'Holds information about investigations (i.e. experiments, calibrations etc). How investigations (more specifically experiments) are derived from proposals is facility specific. For example, in ISIS there is mainly a one to one correspondence, whereas in Diamond a proposal often maps onto many investigations.';

  CREATE TABLE "INVESTIGATION_TYPE" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "INVESTIGATION_TYPE"."NAME" IS 'The name given to the INVESTIGATION_TYPE, e.g. experiment. ';
   COMMENT ON COLUMN "INVESTIGATION_TYPE"."DESCRIPTION" IS 'Description of what the type denotes e.g. for commercial_experiment this might be: "A scientific experiment performed by a commercial company".';
   COMMENT ON COLUMN "INVESTIGATION_TYPE"."SEQ_NUMBER" IS 'Many times, you want to display options in a particular order of relevance (which is not likely to be alphabetical).  If you have a - seq_number- column then you can add new options and adjust their order without code changes or having to reorder rows in the database.';
   COMMENT ON COLUMN "INVESTIGATION_TYPE"."MOD_TIME" IS 'The date and time at which the record in the database was last modified.';
   COMMENT ON COLUMN "INVESTIGATION_TYPE"."MOD_ID" IS 'The identifier associated with the resource who last modified the record or if more relevent for whom the record was last modified. This may be a person in which case it would be their federal_id or a process in which case it is often the name of the process.';
   COMMENT ON COLUMN "INVESTIGATION_TYPE"."CREATE_TIME" IS 'The date and time at which the record in the database was created.';
   COMMENT ON COLUMN "INVESTIGATION_TYPE"."CREATE_ID" IS 'The identifier associated with the resource who created the record or if more relevent for whom the record was created. This may be a person in which case it would be their federal_id or a process in which case it is often the name of the process.';
   COMMENT ON TABLE "INVESTIGATION_TYPE"  IS 'Holds the designation of valid investigation types.';

  CREATE TABLE "INVESTIGATOR" 
   (	"INVESTIGATION_ID" NUMBER(38,0), 
	"FACILITY_USER_ID" VARCHAR2(255 BYTE), 
	"ROLE" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "INVESTIGATOR"."INVESTIGATION_ID" IS 'The key of the investigation associated with the investigator.';
   COMMENT ON COLUMN "INVESTIGATOR"."FACILITY_USER_ID" IS 'The key refering to facility user associated with this investigation.';
   COMMENT ON COLUMN "INVESTIGATOR"."ROLE" IS 'The role of the facility user in this investigation, for example Principal Investigator, Co-Investigator, etc.';
   COMMENT ON TABLE "INVESTIGATOR"  IS 'A facility user can be involved with more than one investigation and iinvestigation can have more than one facility user. This is a many-to-many mapping table which models this fact.';

  CREATE TABLE "KEYWORD" 
   (	"INVESTIGATION_ID" NUMBER(38,0), 
	"NAME" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "KEYWORD"."INVESTIGATION_ID" IS 'The key of the investigation associated with this keyword.';
   COMMENT ON COLUMN "KEYWORD"."NAME" IS 'The actual keyword. Note these are stored in a case sensitive way as they could alter the meaning of the keyword. A pertinent example would be chemical formulas.';
   COMMENT ON TABLE "KEYWORD"  IS 'Holds the keywords associated with the investigations. Keywords can comve from a variety of sources, for example using words from the investigation title and/or abstract with the stop words removed, from specified key information in the proposal or user supplied with the proposal. Keywords maybe populated in other ways also. In future versions of ICAT implementing INVESTIGATION_PARAMETERS would do away with the need for a separate keyword table.';

  CREATE TABLE "PARAMETER" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"UNITS" VARCHAR2(255 BYTE), 
	"UNITS_LONG_VERSION" VARCHAR2(4000 BYTE), 
	"NUMERIC_VALUE" VARCHAR2(1 BYTE), 
	"NON_NUMERIC_VALUE_FORMAT" VARCHAR2(255 BYTE), 
	"IS_SAMPLE_PARAMETER" VARCHAR2(1 BYTE), 
	"IS_DATASET_PARAMETER" VARCHAR2(1 BYTE), 
	"IS_DATAFILE_PARAMETER" VARCHAR2(1 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"VERIFIED" VARCHAR2(1 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "PARAMETER"."NAME" IS 'The name of the parameter; following the SI units is recommended where applicable in which case the PARAMETER.NAME maps onto SI base quantity or SI derived quantity e.g. Celsius temperature. If following SI is not possible then following the SI methodology of naming is recommended where applicable. Note SI quantities may not always be applicable as often you are recording the temperature of something not just the temperature in which case the Units should be as close to SI as possible.';
   COMMENT ON COLUMN "PARAMETER"."UNITS" IS 'The unit (preferably SI symbol if applicable) for this parameter. Note any given parameter name can be held at multiple unit levels, this is needed to support data from different sources e.g. user office systems, values collected at proposal time and values collected at data registration.  Use N/A when no Unit applies. An example of a value would be °C.';
   COMMENT ON COLUMN "PARAMETER"."UNITS_LONG_VERSION" IS 'The long version of units as short hand unit is normally used in practice. This should, if applicable be the SI Name, for example degree Celsius.';
   COMMENT ON COLUMN "PARAMETER"."NON_NUMERIC_VALUE_FORMAT" IS 'Where the value is a string, this allows that value to be documented according to the rules or a regular expression.';
   COMMENT ON COLUMN "PARAMETER"."IS_SAMPLE_PARAMETER" IS 'Y or y denote that the parameter is relevant for association with samples.';
   COMMENT ON COLUMN "PARAMETER"."IS_DATASET_PARAMETER" IS 'Y or y denote that the parameter is relevant for association with datasets.';
   COMMENT ON COLUMN "PARAMETER"."IS_DATAFILE_PARAMETER" IS 'Y or y denote that the parameter is relevant for association with datafiles.';
   COMMENT ON COLUMN "PARAMETER"."DESCRIPTION" IS 'This describes the PARAMETER.NAME and is not a definition of the units.';
   COMMENT ON COLUMN "PARAMETER"."VERIFIED" IS 'If Y this means that the parameter was loaded from the facility spreadsheet, list of approved values or that the parameter was unverified but has been checked and is now verified. N means that the parameter is not verified, i.e. it has not been approved by the facility; often this will be the case when datafiles or datasets are registered and the parameter values they have associated with them are new or have not been recognised as valid values before. Rather then not allow registration, it is allowed, but the values are flagged for later checking and verification to keep the mechanism of generality in the ICAT under some control such that random values are minimised.';
   COMMENT ON TABLE "PARAMETER"  IS 'This table contains information about the valid parameters that can be used to describe samples, datasets and datafiles. It is recommended that a single parameter uses a single unit type so that data pertaining to that parameter type is using a uniform unit system through out the catalog aiding selection of data based on values of a particular parameter. The PARAMETER table should hold a description of the types of information that the facility collects. However when data is registered into ICAT parameters that don''t already exist are added but marked as unverified, so that they can be checked later and either accepted as a new valif facility parameter type or reconciled with another type or other relevant changes made.';

  CREATE TABLE "PUBLICATION" 
   (	"ID" NUMBER, 
	"INVESTIGATION_ID" NUMBER(38,0), 
	"FULL_REFERENCE" VARCHAR2(4000 BYTE), 
	"URL" VARCHAR2(255 BYTE), 
	"REPOSITORY_ID" VARCHAR2(4000 BYTE), 
	"REPOSITORY" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "PUBLICATION"."ID" IS 'Key for records in the PUBLICATION table.';
   COMMENT ON COLUMN "PUBLICATION"."INVESTIGATION_ID" IS 'The key of the related investigation.';
   COMMENT ON COLUMN "PUBLICATION"."FULL_REFERENCE" IS 'A citable reference to the work.';
   COMMENT ON COLUMN "PUBLICATION"."URL" IS 'A link to where the publication is available.';
   COMMENT ON COLUMN "PUBLICATION"."REPOSITORY_ID" IS 'Short hand identifier for the repository where the publication is available.';
   COMMENT ON COLUMN "PUBLICATION"."REPOSITORY" IS 'The name of the repository which contains a copy of the publication, e.g. STFC ePubs.';
   COMMENT ON TABLE "PUBLICATION"  IS 'This supports the linking of investigations to publications. This can be both publications that the current investigation is based on and publications created from the investigation.';

  CREATE TABLE "RELATED_DATAFILES" 
   (	"SOURCE_DATAFILE_ID" NUMBER, 
	"DEST_DATAFILE_ID" NUMBER, 
	"RELATION" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )   ;

   COMMENT ON COLUMN "RELATED_DATAFILES"."SOURCE_DATAFILE_ID" IS 'The key of the source datafile in the DATAFILE table.';
   COMMENT ON COLUMN "RELATED_DATAFILES"."DEST_DATAFILE_ID" IS 'The key of the destination datafile in the DATAFILE table.';
   COMMENT ON COLUMN "RELATED_DATAFILES"."RELATION" IS 'Specifies the nature of the relationship - i.e. <dest_file> a <relation> of <source_file>. where relation could be for example: subset, newer version, reduced, used the configuration of - e.g. sample environment (configuration, temperature, pressure). ';
   COMMENT ON TABLE "RELATED_DATAFILES"  IS 'This table show how datafiles are related. It works off the axiom that there can only be one relationship between any two specified files. It would, for example, support the capture of information showing how newer updated versions of captured datafiles were generated. It is to be understood that the source is related to the destination and the RELATION column holds information pertaining to this relationship. The relationship might hold information about a transformation or might be another type of relationship e.g. newer version.';

  CREATE TABLE "RULE" 
   (	"ID" NUMBER, 
	"GROUP_NAME" VARCHAR2(255 BYTE), 
	"WHAT" VARCHAR2(255 BYTE), 
	"C" VARCHAR2(1 BYTE), 
	"R" VARCHAR2(1 BYTE), 
	"U" VARCHAR2(1 BYTE), 
	"D" VARCHAR2(1 BYTE), 
	"RESTRICTION" VARCHAR2(4000 BYTE), 
	"CRUD_JPQL" VARCHAR2(4000 BYTE), 
	"SEARCH_JPQL" VARCHAR2(4000 BYTE), 
	"BEANS" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1)
   )   ;

  CREATE TABLE "SAMPLE" 
   (	"ID" NUMBER(38,0), 
	"INVESTIGATION_ID" NUMBER, 
	"NAME" VARCHAR2(255 BYTE), 
	"INSTANCE" VARCHAR2(255 BYTE), 
	"CHEMICAL_FORMULA" VARCHAR2(255 BYTE), 
	"SAFETY_INFORMATION" VARCHAR2(4000 BYTE), 
	"PROPOSAL_SAMPLE_ID" NUMBER(38,0), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "SAMPLE"."ID" IS 'The key for the sample information in ICAT.';
   COMMENT ON COLUMN "SAMPLE"."INVESTIGATION_ID" IS 'The key of the related investigation.';
   COMMENT ON COLUMN "SAMPLE"."NAME" IS 'Descriptive name of the sample.';
   COMMENT ON COLUMN "SAMPLE"."INSTANCE" IS 'This is a designator representing difference instances of the sample e.g. (1, 2, 3)  brought by the experimenters. This will be NULL in the case where the abstract sample is being described.';
   COMMENT ON COLUMN "SAMPLE"."CHEMICAL_FORMULA" IS 'This is the chemical formula of the sample. Note this can be NULL in cases when there is a complex layered target, where the chemical formula is unknown, or only partially known or when trying to study the interface between two solids; in these cases more support in describing these situations in ICAT may be needed.';
   COMMENT ON COLUMN "SAMPLE"."SAFETY_INFORMATION" IS 'This field holds sample safety or sample hazard information. Where the safety information has numerous aspects, then these can be stored as sample parameters and a note stored here to that effect.';
   COMMENT ON COLUMN "SAMPLE"."PROPOSAL_SAMPLE_ID" IS 'A copy of the sample_id in the database from which this record was imported.  This lets us propagate changes made to a sample''s child tables, such as  sample_parameter.';

  CREATE TABLE "SAMPLE_PARAMETER" 
   (	"SAMPLE_ID" NUMBER(38,0), 
	"NAME" VARCHAR2(255 BYTE), 
	"UNITS" VARCHAR2(255 BYTE), 
	"STRING_VALUE" VARCHAR2(4000 BYTE), 
	"NUMERIC_VALUE" FLOAT(126), 
	"DATETIME_VALUE" TIMESTAMP (3), 
	"ERROR" VARCHAR2(255 BYTE), 
	"RANGE_TOP" VARCHAR2(255 BYTE), 
	"RANGE_BOTTOM" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "SAMPLE_PARAMETER"."SAMPLE_ID" IS 'The key of the related sample in ICAT.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."NAME" IS 'The name of the sample parameter, e.g. disposal method.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."UNITS" IS 'The units of the parameter, SI if applicable.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."STRING_VALUE" IS 'If the field NAME has a value expressed as a string then the data will be present here. Whether a number or value is stored is determined by what is set is the PARAMETER.NUMERIC_VALUE column.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."NUMERIC_VALUE" IS 'If the field NAME has units expressed as a number then the value will be set here. Whether a number or value is stored is determined by what is set is the PARAMETER.NUMERIC_VALUE column.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."DATETIME_VALUE" IS 'Store Date and time value of parameter';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."ERROR" IS 'Holds the error range for the STRING_VALUE, NUMERIC_VALUE or RANGE_TOP and RANGE_BOTTOM combination.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."RANGE_TOP" IS 'If the value is a range this holds the maximum value. This has not been used in practise and might be a candidate for removal in the next version of ICAT.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."RANGE_BOTTOM" IS 'If the value is a range this holds the minimum value. This has not been used in practise and might be a candidate for removal in the next version of ICAT.';
   COMMENT ON COLUMN "SAMPLE_PARAMETER"."DESCRIPTION" IS 'Where and how the parameter was recorded or extracted as oppose to a definition of the parameter; the latter being defined in the PARAMETER table.';
   COMMENT ON TABLE "SAMPLE_PARAMETER"  IS 'Stores name-value pairs of metadata associated with the sample. The type of names are constrained by their entry in the PARAMETER table, allowing for building up controlled vocabularies for a particular facility around pertinent sample information.';

  CREATE TABLE "SHIFT" 
   (	"INVESTIGATION_ID" NUMBER, 
	"START_DATE" TIMESTAMP (1), 
	"END_DATE" TIMESTAMP (1), 
	"SHIFT_COMMENT" VARCHAR2(4000 BYTE), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "SHIFT"."INVESTIGATION_ID" IS 'The key of the related investigation.';
   COMMENT ON COLUMN "SHIFT"."START_DATE" IS 'The start date and time of the shift.';
   COMMENT ON COLUMN "SHIFT"."END_DATE" IS 'The end date and time of the shift.';
   COMMENT ON COLUMN "SHIFT"."SHIFT_COMMENT" IS 'A comment on the shift. This could, for example explain why the data and time of the shift had been changed.';
   COMMENT ON TABLE "SHIFT"  IS 'This stores information pertaining to when experiments are actually performed. This information is needed in scenarios where programs at the data collection device check with ICAT or its proxy IKitten to determine whether experimenters at the device and what experiment they are performing can be accurately ascertained. While this is more of an operational than archive feature, the metadata associated with this situation can be used to help data registration and filling out fields of metadata in the data files themselves.';

  CREATE TABLE "SOFTWARE_VERSION" 
   (	"ID" NUMBER(38,0), 
	"NAME" VARCHAR2(4000 BYTE), 
	"SW_VERSION" VARCHAR2(255 BYTE), 
	"FEATURES" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(255 BYTE), 
	"AUTHORS" VARCHAR2(255 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (6), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "SOFTWARE_VERSION"."ID" IS 'The key of the software version record  in ICAT.';
   COMMENT ON COLUMN "SOFTWARE_VERSION"."NAME" IS 'The name of the program.';
   COMMENT ON COLUMN "SOFTWARE_VERSION"."SW_VERSION" IS 'The version string of the program.';
   COMMENT ON COLUMN "SOFTWARE_VERSION"."FEATURES" IS 'The features of the program.';
   COMMENT ON COLUMN "SOFTWARE_VERSION"."DESCRIPTION" IS 'Description of the program. This could for example include a URL for further information about the software.';
   COMMENT ON COLUMN "SOFTWARE_VERSION"."AUTHORS" IS 'The authors of the program.';
   COMMENT ON TABLE "SOFTWARE_VERSION"  IS 'This hold information about versions of the software used to do data reduction and analysis. This has not been used in practice in ICAT 3.1 to 3.3. It maybe used in the future, but will need re-visiting to see whether or not it meets the need of storing such information.';

  CREATE TABLE "STUDY" 
   (	"ID" NUMBER(38,0), 
	"NAME" VARCHAR2(255 BYTE), 
	"PURPOSE" VARCHAR2(4000 BYTE), 
	"STATUS" VARCHAR2(255 BYTE), 
	"RELATED_MATERIAL" VARCHAR2(4000 BYTE), 
	"STUDY_CREATION_DATE" TIMESTAMP (1), 
	"STUDY_MANAGER" NUMBER, 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )   ;

   COMMENT ON COLUMN "STUDY"."ID" IS 'The key of the study in ICAT.';
   COMMENT ON COLUMN "STUDY"."NAME" IS 'Unique name given to the study.';
   COMMENT ON COLUMN "STUDY"."PURPOSE" IS 'The reason for aggregating this particular set of investigations, i.e. the aggregation criteria used.';
   COMMENT ON COLUMN "STUDY"."STATUS" IS 'Ongoing or complete, as there could be additional investigations planned in the future which could be applicable to this study.';
   COMMENT ON COLUMN "STUDY"."RELATED_MATERIAL" IS 'This field holds information related to this study. For example this could be related studies in different facility ICATs or similar sample investigated at a different facility (e.g. DLS or CLF if work done at ISIS). To allow the connection of different sources of relevant information  at the moment this is unstructured text but this may become more structured in future versions of ICAT.';
   COMMENT ON COLUMN "STUDY"."STUDY_CREATION_DATE" IS 'When the study was created. This could be removed in future version of ICAT as the audit column CREATE_TIME now holds this information.';
   COMMENT ON COLUMN "STUDY"."STUDY_MANAGER" IS 'This should map to the FACILITY_USER.FEDERAL_ID, as the user who creates the study need not be an investigator but should be known to be a registered user of the facility. Authorisation rules would need to be added to ICAT_AUTHORISATION. ';
   COMMENT ON TABLE "STUDY"  IS 'STUDY is used to aggregate investigations. This is not used in ICAT 3.3 but may be a basis to allow facility users to group their experiments or sets of related experiments in the future.';

  CREATE TABLE "STUDY_INVESTIGATION" 
   (	"STUDY_ID" NUMBER, 
	"INVESTIGATION_ID" NUMBER, 
	"INVESTIGATION_VISIT_ID" VARCHAR2(255 BYTE), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "STUDY_INVESTIGATION"."STUDY_ID" IS 'The key of the study in ICAT.';
   COMMENT ON COLUMN "STUDY_INVESTIGATION"."INVESTIGATION_ID" IS 'The key of the investigation in ICAT.';
   COMMENT ON COLUMN "STUDY_INVESTIGATION"."INVESTIGATION_VISIT_ID" IS 'Should be removed in a future version of ICAT.';
   COMMENT ON TABLE "STUDY_INVESTIGATION"  IS 'Mapping table holding the link between studies and investigation.';

  CREATE TABLE "STUDY_STATUS" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"DESCRIPTION" VARCHAR2(4000 BYTE), 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_TIME" TIMESTAMP (6), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "STUDY_STATUS"."NAME" IS 'The name give to the status.';
   COMMENT ON COLUMN "STUDY_STATUS"."DESCRIPTION" IS 'A description of what the study status actually means.';
   COMMENT ON TABLE "STUDY_STATUS"  IS 'A lookup table holding information about the valid status values for studies.';

  CREATE TABLE "THIS_ICAT" 
   (	"FACILITY_SHORT_NAME" VARCHAR2(30 BYTE), 
	"FACILITY_LONG_NAME" VARCHAR2(255 BYTE), 
	"FACILITY_URL" VARCHAR2(255 BYTE), 
	"FACILITY_DESCRIPTION" VARCHAR2(4000 BYTE), 
	"DAYS_UNTIL_PUBLIC_RELEASE" NUMBER(7,0) DEFAULT 1095, 
	"SEQ_NUMBER" NUMBER DEFAULT 999, 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1)
   )  ;

   COMMENT ON COLUMN "THIS_ICAT"."FACILITY_SHORT_NAME" IS 'The short name of the facility e.g. DLS.';
   COMMENT ON COLUMN "THIS_ICAT"."FACILITY_LONG_NAME" IS 'The long or official name of the facility e.g. Diamond Light Source.';
   COMMENT ON COLUMN "THIS_ICAT"."FACILITY_URL" IS 'A link to further information about the facility e.g. the facility homepage.';
   COMMENT ON COLUMN "THIS_ICAT"."FACILITY_DESCRIPTION" IS 'A description of the facility.';
   COMMENT ON COLUMN "THIS_ICAT"."DAYS_UNTIL_PUBLIC_RELEASE" IS 'The number of days until the raw data is made publicaly available for a given experiment. A value of 0 means that the data should be made immediately available.';
   COMMENT ON TABLE "THIS_ICAT"  IS 'Reflective information about the facility served by this instance of ICAT.';

  CREATE TABLE "TOPIC" 
   (	"ID" NUMBER(38,0), 
	"NAME" VARCHAR2(255 BYTE), 
	"PARENT_ID" NUMBER(38,0), 
	"TOPIC_LEVEL" NUMBER(38,0), 
	"MOD_TIME" TIMESTAMP (6), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "TOPIC"."ID" IS 'The key of the record in ICAT.';
   COMMENT ON COLUMN "TOPIC"."NAME" IS 'The name of the classification term.';
   COMMENT ON COLUMN "TOPIC"."PARENT_ID" IS 'The key of the parent record in the TOPIC table. This will be some special value where the topic is at the root of the hierarchy.';
   COMMENT ON COLUMN "TOPIC"."TOPIC_LEVEL" IS 'The level in the hierarchy the term is at.';
   COMMENT ON TABLE "TOPIC"  IS 'Used to build a taxonomy of terms relevent to investigations.';

  CREATE TABLE "TOPIC_LIST" 
   (	"INVESTIGATION_ID" NUMBER(38,0), 
	"TOPIC_ID" NUMBER(38,0), 
	"MOD_TIME" DATE, 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "TOPIC_LIST"."INVESTIGATION_ID" IS 'The key of the linked investigation.';
   COMMENT ON COLUMN "TOPIC_LIST"."TOPIC_ID" IS 'The key of the linked topic (refers to the leaf node).';
   COMMENT ON TABLE "TOPIC_LIST"  IS 'Stores the link between investigations and their topics.';

  CREATE TABLE "USERGROUP" 
   (	"NAME" VARCHAR2(255 BYTE), 
	"MEMBER" VARCHAR2(255 BYTE), 
	"MOD_TIME" TIMESTAMP (1)
   )   ;

  CREATE TABLE "USER_ROLES" 
   (	"APP_CODE" VARCHAR2(10 BYTE), 
	"USERNAME" VARCHAR2(255 BYTE), 
	"ROLE" VARCHAR2(20 BYTE), 
	"MOD_TIME" TIMESTAMP (1), 
	"MOD_ID" VARCHAR2(255 BYTE), 
	"CREATE_TIME" TIMESTAMP (1), 
	"CREATE_ID" VARCHAR2(255 BYTE)
   )  ;

   COMMENT ON COLUMN "USER_ROLES"."APP_CODE" IS 'The application code refers to values in the APPLICATIONS table.';
   COMMENT ON COLUMN "USER_ROLES"."USERNAME" IS 'The user''s corporate username.  Must be upper case. This will be there federal Identifier at STFC.';
   COMMENT ON COLUMN "USER_ROLES"."ROLE" IS 'The role of the user with regards to the application.';
   COMMENT ON TABLE "USER_ROLES"  IS 'Stores special roles, e.g. Administrators, assigned to the users of the Oracle Application Express applications which interact with ICAT. The table APPLICATIONS is linked to USER_ROLES. These users specified will not necessarily be facility users. In future version of ICAT these tables will be removed as applications interact with ICAT via the ICAT API. ';

  CREATE UNIQUE INDEX "PK_DF" ON "DATAFILE" ("ID");
  CREATE UNIQUE INDEX "SAMPLE_UK2" ON "SAMPLE" ("ID", "INVESTIGATION_ID");
  CREATE UNIQUE INDEX "STUD_INVESTIGATION_PK" ON "STUDY_INVESTIGATION" ("STUDY_ID", "INVESTIGATION_ID");
  CREATE UNIQUE INDEX "PK_DSP" ON "DATASET_PARAMETER" ("DATASET_ID", "NAME", "UNITS");
  CREATE UNIQUE INDEX "SYS_C009839" ON "DATASET_TYPE" ("NAME");
  CREATE UNIQUE INDEX "KEYWORD_PK" ON "KEYWORD" ("INVESTIGATION_ID", "NAME");
  CREATE UNIQUE INDEX "THIS_ICAT_PK" ON "THIS_ICAT" ("FACILITY_SHORT_NAME");
  CREATE UNIQUE INDEX "SYS_C009868" ON "STUDY_STATUS" ("NAME");
  CREATE UNIQUE INDEX "PK_SP" ON "SAMPLE_PARAMETER" ("SAMPLE_ID", "NAME", "UNITS");
  CREATE UNIQUE INDEX "SAMPLE_UK1" ON "SAMPLE" ("INVESTIGATION_ID", "NAME", "INSTANCE");
  CREATE UNIQUE INDEX "INVESTIGATION_UK2" ON "INVESTIGATION" ("VISIT_ID", "INV_NUMBER", "FACILITY_CYCLE", "INSTRUMENT");
  CREATE UNIQUE INDEX "APPLICATIONS_PK" ON "APPLICATIONS" ("APP_CODE"); 
  CREATE INDEX "DP_IDX1" ON "DATAFILE_PARAMETER" ("NAME");
  CREATE UNIQUE INDEX "PK_FGDF" ON "RELATED_DATAFILES" ("SOURCE_DATAFILE_ID", "DEST_DATAFILE_ID");
  CREATE UNIQUE INDEX "USER_ROLES_PK" ON "USER_ROLES" ("APP_CODE", "USERNAME");
  CREATE INDEX "DATAFILE_INDEX1" ON "DATAFILE" ("DATASET_ID");
  CREATE UNIQUE INDEX "PK_TL" ON "TOPIC_LIST" ("INVESTIGATION_ID", "TOPIC_ID");
  CREATE UNIQUE INDEX "PK_DP" ON "DATAFILE_PARAMETER" ("DATAFILE_ID", "NAME", "UNITS");
  CREATE UNIQUE INDEX "PK_S" ON "SAMPLE" ("ID");
  CREATE UNIQUE INDEX "PK_DS" ON "DATASET" ("ID"); 
  CREATE UNIQUE INDEX "DATASET_UK1" ON "DATASET" ("INVESTIGATION_ID", "SAMPLE_ID", "DATASET_TYPE", "NAME");
  CREATE UNIQUE INDEX "SYS_C009845" ON "FACILITY_CYCLE" ("NAME");
  CREATE UNIQUE INDEX "INVESTIGATION_UK3" ON "INVESTIGATION" ("SRC_HASH");
  CREATE UNIQUE INDEX "PK_INVR" ON "INVESTIGATOR" ("INVESTIGATION_ID", "FACILITY_USER_ID");
  CREATE UNIQUE INDEX "USERGROUP_PK" ON "USERGROUP" ("NAME", "MEMBER");
  CREATE UNIQUE INDEX "FACILITY_USER_PK" ON "FACILITY_USER" ("FACILITY_USER_ID");
  CREATE UNIQUE INDEX "SYS_C009838" ON "DATASET_STATUS" ("NAME");
  CREATE UNIQUE INDEX "STUDY_UK1" ON "STUDY" ("NAME");
  CREATE INDEX "FACILITY_USER_UK1" ON "FACILITY_USER" (UPPER("FEDERAL_ID"));
  CREATE UNIQUE INDEX "SYS_C009833" ON "DATAFILE_FORMAT" ("NAME", "VERSION"); 
  CREATE UNIQUE INDEX "RULE_PK" ON "RULE" ("ID");
  CREATE INDEX "DATAFILE_INDEX2" ON "DATAFILE" (LOWER("NAME"));
  CREATE INDEX "DP_IDX2" ON "DATASET_PARAMETER" ("NAME");
  CREATE UNIQUE INDEX "PK_I" ON "INVESTIGATION" ("ID");
  CREATE UNIQUE INDEX "FACILITY_INSTRUMENT_SCIEN_PK" ON "FACILITY_INSTRUMENT_SCIENTIST" ("INSTRUMENT_NAME", "FEDERAL_ID");
  CREATE UNIQUE INDEX "SHIFT_PK" ON "SHIFT" ("INVESTIGATION_ID", "START_DATE", "END_DATE");
  CREATE UNIQUE INDEX "SYS_C009866" ON "SOFTWARE_VERSION" ("ID");
  CREATE UNIQUE INDEX "INSTRUMENT_TYPE_PK" ON "INSTRUMENT" ("NAME");
  CREATE INDEX "KEYWORD_INDEX1" ON "KEYWORD" ("INVESTIGATION_ID");
  CREATE UNIQUE INDEX "PARAMETER_PK" ON "PARAMETER" ("NAME", "UNITS");
  CREATE UNIQUE INDEX "DATAFILE_UK1" ON "DATAFILE" ("DATASET_ID", "NAME", "LOCATION");
  CREATE UNIQUE INDEX "PK_STU" ON "STUDY" ("ID");
  CREATE UNIQUE INDEX "SYS_C009843" ON "INVESTIGATION_TYPE" ("NAME");
  CREATE UNIQUE INDEX "PK_P" ON "PUBLICATION" ("ID");
  CREATE UNIQUE INDEX "PK_T" ON "TOPIC" ("ID");
  CREATE INDEX "KEYWORD_INDEX4" ON "KEYWORD" ("INVESTIGATION_ID", LOWER("NAME"));
  CREATE INDEX "INVESTIGATION_INDEX1" ON "INVESTIGATION" ("INV_END_DATE");

  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("DATAFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("UNITS" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_PARAMETER" ADD CONSTRAINT "PK_DP" PRIMARY KEY ("DATAFILE_ID", "NAME", "UNITS") ENABLE;

  ALTER TABLE "INSTRUMENT" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" MODIFY ("SHORT_NAME" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "INSTRUMENT" ADD CONSTRAINT "INSTRUMENT_TYPE_PK" PRIMARY KEY ("NAME") ENABLE;

  ALTER TABLE "PARAMETER" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("UNITS" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("NUMERIC_VALUE" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("IS_SAMPLE_PARAMETER" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("IS_DATASET_PARAMETER" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("IS_DATAFILE_PARAMETER" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("VERIFIED" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "PARAMETER" ADD CONSTRAINT "PARAMETER_PK" PRIMARY KEY ("NAME", "UNITS") ENABLE;

  ALTER TABLE "THIS_ICAT" MODIFY ("FACILITY_SHORT_NAME" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" MODIFY ("DAYS_UNTIL_PUBLIC_RELEASE" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "THIS_ICAT" ADD CONSTRAINT "THIS_ICAT_PK" PRIMARY KEY ("FACILITY_SHORT_NAME")
    ENABLE;

  ALTER TABLE "DATASET" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("DATASET_TYPE" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET" ADD CONSTRAINT "PK_DS" PRIMARY KEY ("ID")
    ENABLE;
  ALTER TABLE "DATASET" ADD CONSTRAINT "DATASET_UK1" UNIQUE ("INVESTIGATION_ID", "SAMPLE_ID", "DATASET_TYPE", "NAME")
    ENABLE;

  ALTER TABLE "STUDY_STATUS" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" MODIFY ("DESCRIPTION" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_STATUS" ADD CONSTRAINT "SYS_C009868" PRIMARY KEY ("NAME")
    ENABLE;

  ALTER TABLE "APPLICATIONS" MODIFY ("APP_CODE" NOT NULL ENABLE);
  ALTER TABLE "APPLICATIONS" MODIFY ("APP_NAME" NOT NULL ENABLE);
  ALTER TABLE "APPLICATIONS" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "APPLICATIONS" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "APPLICATIONS" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "APPLICATIONS" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "APPLICATIONS" ADD CONSTRAINT "APPLICATIONS_PK" PRIMARY KEY ("APP_CODE")
    ENABLE;
  ALTER TABLE "APPLICATIONS" ADD CONSTRAINT "APPLICATIONS_CHK1" CHECK (app_code = upper(app_code)) ENABLE;

  ALTER TABLE "STUDY" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "STUDY" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "STUDY" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "STUDY" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY" ADD CONSTRAINT "PK_STU" PRIMARY KEY ("ID")
    ENABLE;
  ALTER TABLE "STUDY" ADD CONSTRAINT "STUDY_UK1" UNIQUE ("NAME")
    ENABLE;

  ALTER TABLE "FACILITY_USER" MODIFY ("FACILITY_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_USER" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_USER" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_USER" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_USER" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_USER" ADD CONSTRAINT "FACILITY_USER_PK" PRIMARY KEY ("FACILITY_USER_ID")
    ENABLE;

  ALTER TABLE "FACILITY_CYCLE" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_CYCLE" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_CYCLE" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_CYCLE" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_CYCLE" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_CYCLE" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_CYCLE" ADD CONSTRAINT "SYS_C009845" PRIMARY KEY ("NAME")
    ENABLE;

  ALTER TABLE "TOPIC" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "TOPIC" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "TOPIC" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC" ADD CONSTRAINT "PK_T" PRIMARY KEY ("ID")
    ENABLE;




  ALTER TABLE "SHIFT" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" MODIFY ("START_DATE" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" MODIFY ("END_DATE" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "SHIFT" ADD CONSTRAINT "SHIFT_PK" PRIMARY KEY ("INVESTIGATION_ID", "START_DATE", "END_DATE")
    ENABLE;




  ALTER TABLE "SOFTWARE_VERSION" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SOFTWARE_VERSION" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "SOFTWARE_VERSION" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "SOFTWARE_VERSION" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "SOFTWARE_VERSION" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "SOFTWARE_VERSION" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "SOFTWARE_VERSION" ADD CONSTRAINT "SYS_C009866" PRIMARY KEY ("ID")
    ENABLE;




  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("INSTRUMENT_NAME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("FEDERAL_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" ADD CONSTRAINT "FACILITY_INSTRUMENT_SCIEN_PK" PRIMARY KEY ("INSTRUMENT_NAME", "FEDERAL_ID") ENABLE;

  ALTER TABLE "INVESTIGATOR" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATOR" MODIFY ("FACILITY_USER_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATOR" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATOR" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATOR" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATOR" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATOR" ADD CONSTRAINT "PK_INVR" PRIMARY KEY ("INVESTIGATION_ID", "FACILITY_USER_ID") ENABLE;

  ALTER TABLE "DATASET_TYPE" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_TYPE" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "DATASET_TYPE" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_TYPE" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_TYPE" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_TYPE" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_TYPE" ADD CONSTRAINT "SYS_C009839" PRIMARY KEY ("NAME") ENABLE;

  ALTER TABLE "PUBLICATION" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" MODIFY ("FULL_REFERENCE" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "PUBLICATION" ADD CONSTRAINT "PK_P" PRIMARY KEY ("ID") ENABLE;

  ALTER TABLE "INVESTIGATION" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("INV_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("FACILITY" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("INV_TYPE" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "PK_I" PRIMARY KEY ("ID")    ENABLE;
  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "INVESTIGATION_UK2" UNIQUE ("VISIT_ID", "INV_NUMBER", "FACILITY_CYCLE", "INSTRUMENT")     ENABLE;
  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "INVESTIGATION_UK3" UNIQUE ("SRC_HASH")     ENABLE;

  ALTER TABLE "INVESTIGATION_TYPE" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION_TYPE" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION_TYPE" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION_TYPE" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION_TYPE" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION_TYPE" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "INVESTIGATION_TYPE" ADD CONSTRAINT "SYS_C009843" PRIMARY KEY ("NAME") ENABLE;

  ALTER TABLE "USER_ROLES" MODIFY ("APP_CODE" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" MODIFY ("ROLE" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "USER_ROLES" ADD CONSTRAINT "USER_ROLES_PK" PRIMARY KEY ("APP_CODE", "USERNAME")    ENABLE;
  ALTER TABLE "USER_ROLES" ADD CONSTRAINT "USER_ROLES_CHK1" CHECK (username = upper(username)) ENABLE;




  ALTER TABLE "DATAFILE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" MODIFY ("DATASET_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE" ADD CONSTRAINT "PK_DF" PRIMARY KEY ("ID")     ENABLE;
  ALTER TABLE "DATAFILE" ADD CONSTRAINT "DATAFILE_UK1" UNIQUE ("DATASET_ID", "NAME", "LOCATION")     ENABLE;




  ALTER TABLE "RULE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "RULE" MODIFY ("C" NOT NULL ENABLE);
  ALTER TABLE "RULE" MODIFY ("R" NOT NULL ENABLE);
  ALTER TABLE "RULE" MODIFY ("U" NOT NULL ENABLE);
  ALTER TABLE "RULE" MODIFY ("D" NOT NULL ENABLE);
  ALTER TABLE "RULE" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "RULE" ADD CONSTRAINT "RULE_PK" PRIMARY KEY ("ID")
    ENABLE;




  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("SAMPLE_ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("UNITS" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE_PARAMETER" ADD CONSTRAINT "PK_SP" PRIMARY KEY ("SAMPLE_ID", "NAME", "UNITS")
    ENABLE;




  ALTER TABLE "KEYWORD" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "KEYWORD" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "KEYWORD" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "KEYWORD" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "KEYWORD" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "KEYWORD" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "KEYWORD" ADD CONSTRAINT "KEYWORD_PK" PRIMARY KEY ("INVESTIGATION_ID", "NAME")
    ENABLE;




  ALTER TABLE "RELATED_DATAFILES" MODIFY ("SOURCE_DATAFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" MODIFY ("DEST_DATAFILE_ID" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" MODIFY ("RELATION" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "RELATED_DATAFILES" ADD CONSTRAINT "PK_FGDF" PRIMARY KEY ("SOURCE_DATAFILE_ID", "DEST_DATAFILE_ID")
    ENABLE;




  ALTER TABLE "TOPIC_LIST" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC_LIST" MODIFY ("TOPIC_ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC_LIST" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC_LIST" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "TOPIC_LIST" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "TOPIC_LIST" ADD CONSTRAINT "PK_TL" PRIMARY KEY ("INVESTIGATION_ID", "TOPIC_ID")
    ENABLE;




  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("VERSION" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATAFILE_FORMAT" ADD CONSTRAINT "SYS_C009833" PRIMARY KEY ("NAME", "VERSION")
    ENABLE;




  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("STUDY_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("INVESTIGATION_VISIT_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "STUDY_INVESTIGATION" ADD CONSTRAINT "STUD_INVESTIGATION_PK" PRIMARY KEY ("STUDY_ID", "INVESTIGATION_ID")
    ENABLE;




  ALTER TABLE "USERGROUP" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "USERGROUP" MODIFY ("MEMBER" NOT NULL ENABLE);
  ALTER TABLE "USERGROUP" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "USERGROUP" ADD CONSTRAINT "USERGROUP_PK" PRIMARY KEY ("NAME", "MEMBER")
    ENABLE;




  ALTER TABLE "DATASET_PARAMETER" MODIFY ("DATASET_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" MODIFY ("UNITS" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_PARAMETER" ADD CONSTRAINT "PK_DSP" PRIMARY KEY ("DATASET_ID", "NAME", "UNITS")
    ENABLE;

  ALTER TABLE "DATASET_STATUS" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_STATUS" MODIFY ("SEQ_NUMBER" NOT NULL ENABLE);
  ALTER TABLE "DATASET_STATUS" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_STATUS" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_STATUS" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "DATASET_STATUS" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "DATASET_STATUS" ADD CONSTRAINT "SYS_C009838" PRIMARY KEY ("NAME")
    ENABLE;

  ALTER TABLE "SAMPLE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("INVESTIGATION_ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("NAME" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("SAFETY_INFORMATION" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("MOD_TIME" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("MOD_ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("CREATE_TIME" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" MODIFY ("CREATE_ID" NOT NULL ENABLE);
  ALTER TABLE "SAMPLE" ADD CONSTRAINT "PK_S" PRIMARY KEY ("ID")     ENABLE;
  ALTER TABLE "SAMPLE" ADD CONSTRAINT "SAMPLE_UK1" UNIQUE ("INVESTIGATION_ID", "NAME", "INSTANCE")    ENABLE;
  ALTER TABLE "SAMPLE" ADD CONSTRAINT "SAMPLE_UK2" UNIQUE ("ID", "INVESTIGATION_ID")    ENABLE;

  ALTER TABLE "DATAFILE" ADD CONSTRAINT "DATAFILE_DATAFILE_FORMAT_FK1" FOREIGN KEY ("DATAFILE_FORMAT", "DATAFILE_FORMAT_VERSION")
	  REFERENCES "DATAFILE_FORMAT" ("NAME", "VERSION") ENABLE;
  ALTER TABLE "DATAFILE" ADD CONSTRAINT "DATAFILE_DATASET_FK1" FOREIGN KEY ("DATASET_ID")
	  REFERENCES "DATASET" ("ID") ENABLE;

  ALTER TABLE "DATAFILE_PARAMETER" ADD CONSTRAINT "DATAFILE_PARAMETER_PARAME_FK1" FOREIGN KEY ("NAME", "UNITS")
	  REFERENCES "PARAMETER" ("NAME", "UNITS") ENABLE;
  ALTER TABLE "DATAFILE_PARAMETER" ADD CONSTRAINT "FK_DP_DF" FOREIGN KEY ("DATAFILE_ID")
	  REFERENCES "DATAFILE" ("ID") ON DELETE CASCADE ENABLE;

  ALTER TABLE "DATASET" ADD CONSTRAINT "DATASET_DATASET_STATUS_FK1" FOREIGN KEY ("DATASET_STATUS")
	  REFERENCES "DATASET_STATUS" ("NAME") ENABLE;
  ALTER TABLE "DATASET" ADD CONSTRAINT "DATASET_DATASET_TYPE_FK1" FOREIGN KEY ("DATASET_TYPE")
	  REFERENCES "DATASET_TYPE" ("NAME") ENABLE;
  ALTER TABLE "DATASET" ADD CONSTRAINT "DATASET_INVESTIGATION_FK1" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ENABLE;
  ALTER TABLE "DATASET" ADD CONSTRAINT "DATASET_SAMPLE_FK1" FOREIGN KEY ("SAMPLE_ID", "INVESTIGATION_ID")
	  REFERENCES "SAMPLE" ("ID", "INVESTIGATION_ID") ENABLE;

  ALTER TABLE "DATASET_PARAMETER" ADD CONSTRAINT "DATASET_PARAMETER_PARAME_FK1" FOREIGN KEY ("NAME", "UNITS")
	  REFERENCES "PARAMETER" ("NAME", "UNITS") ENABLE;
  ALTER TABLE "DATASET_PARAMETER" ADD CONSTRAINT "FK_DSP_DS" FOREIGN KEY ("DATASET_ID")
	  REFERENCES "DATASET" ("ID") ON DELETE CASCADE ENABLE;

  ALTER TABLE "FACILITY_INSTRUMENT_SCIENTIST" ADD CONSTRAINT "FACILITY_INSTRUMENT_SCIEN_FK1" FOREIGN KEY ("INSTRUMENT_NAME")
	  REFERENCES "INSTRUMENT" ("NAME") ENABLE;

  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "INVESTIGATION_FACILITY_CY_FK1" FOREIGN KEY ("FACILITY_CYCLE")
	  REFERENCES "FACILITY_CYCLE" ("NAME") ENABLE;
  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "INVESTIGATION_INSTRUMENT_FK1" FOREIGN KEY ("INSTRUMENT")
	  REFERENCES "INSTRUMENT" ("NAME") ENABLE;
  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "INVESTIGATION_INVESTIGATI_FK1" FOREIGN KEY ("INV_TYPE")
	  REFERENCES "INVESTIGATION_TYPE" ("NAME") ENABLE;
  ALTER TABLE "INVESTIGATION" ADD CONSTRAINT "INVESTIGATION_THIS_ICAT_FK1" FOREIGN KEY ("FACILITY")
	  REFERENCES "THIS_ICAT" ("FACILITY_SHORT_NAME") ENABLE;

  ALTER TABLE "INVESTIGATOR" ADD CONSTRAINT "FK_I_I" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "INVESTIGATOR" ADD CONSTRAINT "INVESTIGATOR_FACILITY_USER_FK1" FOREIGN KEY ("FACILITY_USER_ID")
	  REFERENCES "FACILITY_USER" ("FACILITY_USER_ID") DEFERRABLE ENABLE;

  ALTER TABLE "KEYWORD" ADD CONSTRAINT "FK_K_STU" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ON DELETE CASCADE ENABLE;

  ALTER TABLE "PUBLICATION" ADD CONSTRAINT "FK_P_I" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ON DELETE CASCADE ENABLE;

  ALTER TABLE "RELATED_DATAFILES" ADD CONSTRAINT "FK_FGDF_DF" FOREIGN KEY ("SOURCE_DATAFILE_ID")
	  REFERENCES "DATAFILE" ("ID") ENABLE;
  ALTER TABLE "RELATED_DATAFILES" ADD CONSTRAINT "RELATED_DATAFILES_DATAFIL_FK1" FOREIGN KEY ("DEST_DATAFILE_ID")
	  REFERENCES "DATAFILE" ("ID") ENABLE;

  ALTER TABLE "SAMPLE" ADD CONSTRAINT "FK_S_I" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ON DELETE CASCADE ENABLE;

  ALTER TABLE "SAMPLE_PARAMETER" ADD CONSTRAINT "FK_S" FOREIGN KEY ("SAMPLE_ID")
	  REFERENCES "SAMPLE" ("ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "SAMPLE_PARAMETER" ADD CONSTRAINT "SAMPLE_PARAMETER_PARAMETE_FK1" FOREIGN KEY ("NAME", "UNITS")
	  REFERENCES "PARAMETER" ("NAME", "UNITS") ENABLE;

  ALTER TABLE "SHIFT" ADD CONSTRAINT "SHIFT_INVESTIGATION_FK1" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ON DELETE CASCADE ENABLE;

  ALTER TABLE "STUDY" ADD CONSTRAINT "STUDY_STUDY_STATUS_FK1" FOREIGN KEY ("STATUS")
	  REFERENCES "STUDY_STATUS" ("NAME") ENABLE;

  ALTER TABLE "STUDY_INVESTIGATION" ADD CONSTRAINT "STUD_INVESTIGATION_INVEST_FK1" FOREIGN KEY ("STUDY_ID")
	  REFERENCES "INVESTIGATION" ("ID") ENABLE;
  ALTER TABLE "STUDY_INVESTIGATION" ADD CONSTRAINT "STUD_INVESTIGATION_STUDY_FK1" FOREIGN KEY ("STUDY_ID")
	  REFERENCES "STUDY" ("ID") ENABLE;

  ALTER TABLE "TOPIC_LIST" ADD CONSTRAINT "FK_TL_STU" FOREIGN KEY ("INVESTIGATION_ID")
	  REFERENCES "INVESTIGATION" ("ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "TOPIC_LIST" ADD CONSTRAINT "FK_TL_T" FOREIGN KEY ("TOPIC_ID")
	  REFERENCES "TOPIC" ("ID") ENABLE;

  ALTER TABLE "USER_ROLES" ADD CONSTRAINT "USER_ROLES_APPLICATIONS_FK1" FOREIGN KEY ("APP_CODE")
	  REFERENCES "APPLICATIONS" ("APP_CODE") ON DELETE CASCADE DISABLE;


  exit
