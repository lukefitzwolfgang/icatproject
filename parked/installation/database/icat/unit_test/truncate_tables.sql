ALTER TABLE INVESTIGATION DISABLE CONSTRAINT INVESTIGATION_FACILITY_CY_FK1;
ALTER TABLE INVESTIGATION DISABLE CONSTRAINT INVESTIGATION_INSTRUMENT_FK1;
ALTER TABLE INVESTIGATION DISABLE CONSTRAINT INVESTIGATION_INVESTIGATI_FK1;
ALTER TABLE INVESTIGATION DISABLE CONSTRAINT INVESTIGATION_THIS_ICAT_FK1;
ALTER TABLE STUDY         DISABLE CONSTRAINT STUDY_STUDY_STATUS_FK1;
ALTER TABLE DATAFILE      DISABLE CONSTRAINT DATAFILE_DATAFILE_FORMAT_FK1;
ALTER TABLE DATAFILE      DISABLE CONSTRAINT DATAFILE_DATASET_FK1;
ALTER TABLE DATAFILE_PARAMETER      DISABLE CONSTRAINT DATAFILE_PARAMETER_PARAME_FK1;
ALTER TABLE DATAFILE_PARAMETER      DISABLE CONSTRAINT FK_DP_DF;
ALTER TABLE DATASET       DISABLE CONSTRAINT DATASET_DATASET_STATUS_FK1;
ALTER TABLE DATASET       DISABLE CONSTRAINT DATASET_DATASET_TYPE_FK1;
ALTER TABLE DATASET       DISABLE CONSTRAINT DATASET_INVESTIGATION_FK1;
ALTER TABLE DATASET	  DISABLE CONSTRAINT DATASET_SAMPLE_FK1;
ALTER TABLE DATASET_PARAMETER      DISABLE CONSTRAINT DATASET_PARAMETER_PARAME_FK1;
ALTER TABLE DATASET_PARAMETER      DISABLE CONSTRAINT FK_DSP_DS;
ALTER TABLE FACILITY_INSTRUMENT_SCIENTIST DISABLE CONSTRAINT FACILITY_INSTRUMENT_SCIEN_FK1;
ALTER TABLE INVESTIGATOR        DISABLE CONSTRAINT FK_I_I;
ALTER TABLE INVESTIGATOR        DISABLE CONSTRAINT INVESTIGATOR_FACILITY_USER_FK1;
ALTER TABLE KEYWORD 		DISABLE CONSTRAINT FK_K_STU;
ALTER TABLE PUBLICATION		DISABLE CONSTRAINT FK_P_I;
ALTER TABLE RELATED_DATAFILES   DISABLE CONSTRAINT FK_FGDF_DF;
ALTER TABLE RELATED_DATAFILES	DISABLE CONSTRAINT RELATED_DATAFILES_DATAFIL_FK1;
ALTER TABLE SAMPLE              DISABLE CONSTRAINT FK_S_I;
ALTER TABLE SAMPLE_PARAMETER    DISABLE CONSTRAINT FK_S;
ALTER TABLE SAMPLE_PARAMETER    DISABLE CONSTRAINT SAMPLE_PARAMETER_PARAMETE_FK1;
ALTER TABLE SHIFT               DISABLE CONSTRAINT SHIFT_INVESTIGATION_FK1;
ALTER TABLE STUDY_INVESTIGATION DISABLE CONSTRAINT STUD_INVESTIGATION_INVEST_FK1;
ALTER TABLE STUDY_INVESTIGATION DISABLE CONSTRAINT STUD_INVESTIGATION_STUDY_FK1;
ALTER TABLE TOPIC_LIST          DISABLE CONSTRAINT FK_TL_STU;
ALTER TABLE TOPIC_LIST          DISABLE CONSTRAINT FK_TL_T;
ALTER TABLE USER_ROLES          DISABLE CONSTRAINT USER_ROLES_APPLICATIONS_FK1;
TRUNCATE TABLE APPLICATIONS;
TRUNCATE TABLE DATAFILE;
TRUNCATE TABLE DATAFILE_FORMAT;
TRUNCATE TABLE DATAFILE_PARAMETER;
TRUNCATE TABLE DATASET;
TRUNCATE TABLE DATASET_PARAMETER;
TRUNCATE TABLE DATASET_STATUS;
TRUNCATE TABLE DATASET_TYPE;
TRUNCATE TABLE FACILITY_CYCLE;
TRUNCATE TABLE FACILITY_INSTRUMENT_SCIENTIST;
TRUNCATE TABLE FACILITY_USER;
TRUNCATE TABLE INSTRUMENT;
TRUNCATE TABLE INVESTIGATION;
TRUNCATE TABLE INVESTIGATION_TYPE;
TRUNCATE TABLE INVESTIGATOR;
TRUNCATE TABLE KEYWORD;
TRUNCATE TABLE PARAMETER;
TRUNCATE TABLE PUBLICATION;
TRUNCATE TABLE RELATED_DATAFILES;
TRUNCATE TABLE SAMPLE;
TRUNCATE TABLE SAMPLE_PARAMETER;
TRUNCATE TABLE SHIFT;
TRUNCATE TABLE SOFTWARE_VERSION;
TRUNCATE TABLE STUDY;
TRUNCATE TABLE STUDY_INVESTIGATION;
TRUNCATE TABLE STUDY_STATUS;
TRUNCATE TABLE THIS_ICAT;
TRUNCATE TABLE TOPIC;
TRUNCATE TABLE TOPIC_LIST;
TRUNCATE TABLE USER_ROLES;
TRUNCATE TABLE RULE;
TRUNCATE TABLE USERGROUP;
DROP SEQUENCE datafile_id_seq;
DROP SEQUENCE dataset_id_seq;
DROP SEQUENCE investigation_id_seq;
DROP SEQUENCE publication_id_seq;
DROP SEQUENCE sample_id_seq;
DROP SEQUENCE software_version_id_seq;
DROP SEQUENCE study_id_seq;
DROP SEQUENCE topic_id_seq;
CREATE SEQUENCE datafile_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE dataset_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE investigation_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE publication_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE sample_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE software_version_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE study_id_seq START WITH 5000 NOCACHE;
CREATE SEQUENCE topic_id_seq START WITH 5000 NOCACHE;
ALTER TABLE INVESTIGATION ENABLE CONSTRAINT INVESTIGATION_FACILITY_CY_FK1;
ALTER TABLE INVESTIGATION ENABLE CONSTRAINT INVESTIGATION_INSTRUMENT_FK1;
ALTER TABLE INVESTIGATION ENABLE CONSTRAINT INVESTIGATION_INVESTIGATI_FK1;
ALTER TABLE INVESTIGATION ENABLE CONSTRAINT INVESTIGATION_THIS_ICAT_FK1;
ALTER TABLE STUDY         ENABLE CONSTRAINT STUDY_STUDY_STATUS_FK1;
ALTER TABLE DATAFILE      ENABLE CONSTRAINT DATAFILE_DATAFILE_FORMAT_FK1;
ALTER TABLE DATAFILE      ENABLE CONSTRAINT DATAFILE_DATASET_FK1;
ALTER TABLE DATAFILE_PARAMETER      ENABLE CONSTRAINT DATAFILE_PARAMETER_PARAME_FK1;
ALTER TABLE DATAFILE_PARAMETER      ENABLE CONSTRAINT FK_DP_DF;
ALTER TABLE DATASET       ENABLE CONSTRAINT DATASET_DATASET_STATUS_FK1;
ALTER TABLE DATASET       ENABLE CONSTRAINT DATASET_DATASET_TYPE_FK1;
ALTER TABLE DATASET       ENABLE CONSTRAINT DATASET_INVESTIGATION_FK1;
ALTER TABLE DATASET	  ENABLE CONSTRAINT DATASET_SAMPLE_FK1;
ALTER TABLE DATASET_PARAMETER      ENABLE CONSTRAINT DATASET_PARAMETER_PARAME_FK1;
ALTER TABLE DATASET_PARAMETER      ENABLE CONSTRAINT FK_DSP_DS;
ALTER TABLE FACILITY_INSTRUMENT_SCIENTIST ENABLE CONSTRAINT FACILITY_INSTRUMENT_SCIEN_FK1;
ALTER TABLE INVESTIGATOR        ENABLE CONSTRAINT FK_I_I;
ALTER TABLE INVESTIGATOR        ENABLE CONSTRAINT INVESTIGATOR_FACILITY_USER_FK1;
ALTER TABLE KEYWORD 		ENABLE CONSTRAINT FK_K_STU;
ALTER TABLE PUBLICATION		ENABLE CONSTRAINT FK_P_I;
ALTER TABLE RELATED_DATAFILES   ENABLE CONSTRAINT FK_FGDF_DF;
ALTER TABLE RELATED_DATAFILES	ENABLE CONSTRAINT RELATED_DATAFILES_DATAFIL_FK1;
ALTER TABLE SAMPLE              ENABLE CONSTRAINT FK_S_I;
ALTER TABLE SAMPLE_PARAMETER    ENABLE CONSTRAINT FK_S;
ALTER TABLE SAMPLE_PARAMETER    ENABLE CONSTRAINT SAMPLE_PARAMETER_PARAMETE_FK1;
ALTER TABLE SHIFT               ENABLE CONSTRAINT SHIFT_INVESTIGATION_FK1;
ALTER TABLE STUDY_INVESTIGATION ENABLE CONSTRAINT STUD_INVESTIGATION_INVEST_FK1;
ALTER TABLE STUDY_INVESTIGATION ENABLE CONSTRAINT STUD_INVESTIGATION_STUDY_FK1;
ALTER TABLE TOPIC_LIST          ENABLE CONSTRAINT FK_TL_STU;
ALTER TABLE TOPIC_LIST          ENABLE CONSTRAINT FK_TL_T;
ALTER TABLE USER_ROLES          DISABLE CONSTRAINT USER_ROLES_APPLICATIONS_FK1;
commit;

