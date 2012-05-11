-- Vanilla ISIS ICAT Database scripts
-- these statements may need reordering due to referential integrity etc

-- Applications (empty anyway)

DELETE FROM APPLICATIONS;

-- Datafile

DELETE FROM DATAFILE;

-- Datafile format (will repopulate later)

DELETE FROM DATAFILE_FORMAT;

-- DATASET

DELETE FROM DATASET;

-- DATASET_PARAMETER

DELETE FROM DATASET_PARAMETER;

-- DATASET_STATUS (will repopulate later)

DELETE FROM DATASET_STATUS;

-- DATASET_TYPE (will repopulate later)

DELETE FROM DATASET_TYPE;

-- FACILITY_INSTRUMENT_SCIENTIST (will repopulate later)

DELETE FROM FACILITY_INSTRUMENT_SCIENTIST;

-- FACILITY_USER (will repopulate later)

DELETE FROM FACILITY_USER;

-- ICAT_AUTHORISATION
DELETE FROM ICAT_AUTHORISATION;

-- ICAT_ROLE (will repopulate later)
DELETE FROM ICAT_ROLE;

-- INVESTIGATION
DELETE FROM INVESTIGATION;

-- INVESTIGATION_TYPE (will repopulate later)
DELETE FROM INVESTIGATION_TYPE;

-- KEYWORD
DELETE FROM KEYWORD;

-- LOG_TABLE 
DELETE FROM LOG_TABLE;

-- PARAMETER (will repopulate later)
DELETE FROM PARAMETER;

-- PUBLICATION (empty anyway - no API access)
DELETE FROM PUBLICATION;

-- RELATED_DATAFILES (empty anyway - no API access)
DELETE FROM RELATED_DATAFILES;

-- SAMPLE
DELETE FROM SAMPLE;

-- SAMPLE_PARAMETERS 
DELETE FROM SAMPLE_PARAMETER;

-- SHIFT (empty anyway for ISIS)
DELETE FROM SHIFT;

-- SOFTWARE_VERSION (empty anyway - no API access)
DELETE FROM SOFTWARE_VERSION;

-- STUDY (empty anyway - no API access)
DELETE FROM STUDY;

-- STUDY_INVESTIGATION (empty anyway - no API access)
DELETE FROM STUDY_INVESTIGATION;

-- STUDY_STATUS (empty anyway - no API access)
DELETE FROM STUDY_STATUS;

-- THIS_ICAT (will repopulate later)
DELETE FROM THIS_ICAT;

-- TOPIC (will repopulate later)
DELETE FROM TOPIC;

-- TOPIC_LIST (empty anyway for ISIS)
DELETE FROM TOPIC_LIST;

-- USER_ROLES (empty anyway for ISIS)
DELETE FROM USER_ROLES;

-- FACILITY_CYLE
DELETE FROM FACILITY_CYCLE;

-- INSTRUMENT (will repopulate later)
DELETE FROM INSTRUMENT;


-- Repopulate the tables we need to ingest some basic data

-- DATAFILE_FORMAT

INSERT INTO DATAFILE_FORMAT(NAME,
                            VERSION,
                            FORMAT_TYPE,
                            SEQ_NUMBER,
                            MOD_TIME,
                            MOD_ID,
                            CREATE_TIME,
                            CREATE_ID,
                            FACILITY_ACQUIRED,
                            DELETED)
VALUES ('isis neutron raw',
        '2',
        'binary',
        999,
        '2007-10-17 00:00:00.0',
        'TEST INSERT',
        '2007-10-17 00:00:00.0',
        'TEST INSERT',
        'Y',
        'N');

COMMIT;

-- DATASET_STATUS - there are 5 default entries here in the ISIS version. For now I'll add enough to ingest data

INSERT INTO DATASET_STATUS(NAME,
                           SEQ_NUMBER,
                           MOD_TIME,
                           MOD_ID,
                           CREATE_TIME,
                           CREATE_ID,
                           FACILITY_ACQUIRED,
                           DELETED)
VALUES ('complete',
        999,
        '2007-10-17 00:00:00.0',
        'TEST INSERT',
        '2007-10-17 00:00:00.0',
        'TEST INSERT',
        'Y',
        'N');

COMMIT;

-- DATASET TYPE - again, there are more enties in a live system (15) but for this initial test, one will suffice

INSERT INTO DATASET_TYPE(NAME,
                         DESCRIPTION,
                         SEQ_NUMBER,
                         MOD_TIME,
                         MOD_ID,
                         CREATE_TIME,
                         CREATE_ID,
                         FACILITY_ACQUIRED,
                         DELETED)
VALUES ('experiment_raw',
        'RAW data collected at the facility during an experiment.',
        999,
        '2007-10-17 00:00:00.0',
        'TEST INSERT',
        '2007-10-17 00:00:00.0',
        'TEST INSERT',
        'Y',
        'N');

COMMIT;

-- FACILITY CYCLE - in a real system there are >55, two will do for initial testing

INSERT INTO FACILITY_CYCLE(NAME,
                           START_DATE,
                           FINISH_DATE,
                           DESCRIPTION,
                           SEQ_NUMBER,
                           MOD_TIME,
                           MOD_ID,
                           CREATE_TIME,
                           CREATE_ID,
                           FACILITY_ACQUIRED,
                           DELETED)
VALUES ('cycle_09_1',
        '2007-10-17 00:00:00.0',
        '2007-10-17 00:00:00.0',
        'ISIS Cycle',
        999,
        '2007-10-17 00:00:00.0',
        'tang76',
        '2007-10-17 00:00:00.0',
        'tang76',
        'Y',
        'N');

INSERT INTO FACILITY_CYCLE(NAME,
                           START_DATE,
                           FINISH_DATE,
                           DESCRIPTION,
                           SEQ_NUMBER,
                           MOD_TIME,
                           MOD_ID,
                           CREATE_TIME,
                           CREATE_ID,
                           FACILITY_ACQUIRED,
                           DELETED)
VALUES ('cycle_09_2',
        '2007-10-17 00:00:00.0',
        '2007-10-17 00:00:00.0',
        'ISIS Cycle',
        999,
       '2007-10-17 00:00:00.0',
        'tang76',
       '2007-10-17 00:00:00.0',
        'tang76',
        'Y',
        'N');

COMMIT;

-- INSTRUMENT --  47 in ISIS , just one for testing

INSERT INTO INSTRUMENT(NAME,
                       SHORT_NAME,
                       TYPE,
                       DESCRIPTION,
                       SEQ_NUMBER,
                       MOD_TIME,
                       MOD_ID,
                       CREATE_TIME,
                       CREATE_ID,
                       FACILITY_ACQUIRED,
                       DELETED)
VALUES (
          'GEM',
          'GEM',
          'Crystallography',
          '"GEM The General Materials Diffractometer is a new generation neutron diffractometer recently constructed at the ISIS pulsed neutron source. GEM can be used to perform high intensity, high resolution experiments to study the structure of disordered materials and crystalline powders. Click here for a recent paper with a detailed description of GEM."',
          999,
          '2007-10-17 00:00:00.0',
          'ICATISIS_DEV_NEW',
          '2007-10-17 00:00:00.0',
          'FROM SPREADSHEET',
          'Y',
          'N');

COMMIT;


-- FACILITY_INSTRUMENT_SCIENTIST - nearly 200 in the ISIS one, just enough for testing here - this requires a trigger to set permissions in ICAT

INSERT INTO FACILITY_INSTRUMENT_SCIENTIST(INSTRUMENT_NAME,
                                          FEDERAL_ID,
                                          SEQ_NUMBER,
                                          MOD_TIME,
                                          MOD_ID,
                                          CREATE_TIME,
                                          CREATE_ID,
                                          FACILITY_ACQUIRED,
                                          DELETED)
VALUES ('GEM',
        'tang76',
        1,
        '2007-10-17 00:00:00.0',
        'ICATISIS_DEV_NEW',
        '2007-10-17 00:00:00.0',
        'ICATISIS_DEV_NEW',
        'Y',
        'N');

-- Facility user - import from the ISIS user db in the real world. I'll add a Prof Test User and Dr Instrument Scientist for testing

INSERT INTO FACILITY_USER(FACILITY_USER_ID,
                          TITLE,
                          INITIALS,
                          FIRST_NAME,
                          LAST_NAME,
                          MOD_TIME,
                          MOD_ID,
                          CREATE_TIME,
                          CREATE_ID,
                          FACILITY_ACQUIRED,
                          DELETED)
VALUES ('1',
        'Prof',
        'T',
        'Testing',
        'User',
        '2007-10-17 00:00:00.0',
        'TEST DATA',
        '2007-10-17 00:00:00.0',
        'TEST DATA',
        'Y',
        'N');

INSERT INTO FACILITY_USER(FACILITY_USER_ID,
                          TITLE,
                          INITIALS,
                          FIRST_NAME,
                          LAST_NAME,
                          MOD_TIME,
                          MOD_ID,
                          CREATE_TIME,
                          CREATE_ID,
                          FACILITY_ACQUIRED,
                          DELETED)
VALUES ('2',
        'Dr',
        'I',
        'Instrument',
        'Scientist',
        '2007-10-17 00:00:00.0',
        'TEST DATA',
        '2007-10-17 00:00:00.0',
        'TEST DATA',
        'Y',
        'N');

COMMIT;

-- ICAT_ROLE - best to put them all in

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('ADMIN',
        180,
        'Y',
        15,
        'Y',
        1,
        'Y',
        5,
        'Y',
        10,
        'Y',
        20,
        'Y',
        25,
        'Y',
        50,
        'N',
        50,
        'N',
        50,
        'Y',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('CREATOR',
        180,
        'Y',
        15,
        'Y',
        1,
        'Y',
        5,
        'Y',
        10,
        'Y',
        20,
        'N',
        25,
        'Y',
        50,
        'N',
        50,
        'N',
        50,
        'Y',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('DELETER',
        180,
        'N',
        15,
        'Y',
        1,
        'Y',
        5,
        'Y',
        10,
        'Y',
        20,
        'N',
        25,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('DOWNLOADER',
        180,
        'N',
        15,
        'Y',
        1,
        'Y',
        5,
        'N',
        10,
        'N',
        20,
        'N',
        25,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('ICAT_ADMIN',
        180,
        'Y',
        15,
        'Y',
        1,
        'Y',
        5,
        'Y',
        10,
        'Y',
        20,
        'Y',
        25,
        'Y',
        50,
        'Y',
        50,
        'Y',
        50,
        'Y',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('READER',
        180,
        'N',
        15,
        'Y',
        1,
        'N',
        5,
        'N',
        10,
        'N',
        20,
        'N',
        25,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('UPDATER',
        180,
        'N',
        15,
        'Y',
        1,
        'Y',
        5,
        'Y',
        10,
        'N',
        20,
        'N',
        25,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        50,
        'N',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

INSERT INTO ICAT_ROLE(ROLE,
                      ROLE_WEIGHT,
                      ACTION_INSERT,
                      ACTION_INSERT_WEIGHT,
                      ACTION_SELECT,
                      ACTION_SELECT_WEIGHT,
                      ACTION_DOWNLOAD,
                      ACTION_DOWNLOAD_WEIGHT,
                      ACTION_UPDATE,
                      ACTION_UPDATE_WEIGHT,
                      ACTION_DELETE,
                      ACTION_DELETE_WEIGHT,
                      ACTION_REMOVE,
                      ACTION_REMOVE_WEIGHT,
                      ACTION_ROOT_INSERT,
                      ACTION_ROOT_INSERT_WEIGHT,
                      ACTION_ROOT_REMOVE,
                      ACTION_ROOT_REMOVE_WEIGHT,
                      ACTION_SET_FA,
                      ACTION_SET_FA_WEIGHT,
                      ACTION_MANAGE_USERS,
                      ACTION_MANAGE_USERS_WEIGHT,
                      ACTION_SUPER,
                      ACTION_SUPER_WEIGHT,
                      SEQ_NUMBER,
                      MOD_TIME,
                      MOD_ID,
                      CREATE_TIME,
                      CREATE_ID,
                      FACILITY_ACQUIRED,
                      DELETED)
VALUES ('SUPER',
        180,
        'Y',
        15,
        'Y',
        1,
        'Y',
        5,
        'Y',
        10,
        'Y',
        20,
        'Y',
        25,
        'Y',
        50,
        'Y',
        50,
        'Y',
        50,
        'Y',
        50,
        'Y',
        500,
        999,
        '2007-10-17 00:00:00.0',
        'From Spreadsheet',
        '2007-10-17 00:00:00.0',
        'FromSpreadsheet',
        'Y',
        'N');

COMMIT;


-- INVESTIGATION_TYPE - 8 in the real one, just a test one for now. May need to add calibration as a test too, and commercial

INSERT INTO INVESTIGATION_TYPE(NAME,
                               DESCRIPTION,
                               SEQ_NUMBER,
                               MOD_TIME,
                               MOD_ID,
                               CREATE_TIME,
                               CREATE_ID,
                               FACILITY_ACQUIRED,
                               DELETED)
VALUES ('experiment',
        'A scientific experiment.',
        999,
        '2007-10-17 00:00:00.0',
        'ICATISIS_DEV_NEW',
        '2007-10-17 00:00:00.0',
        'FROM SPREADSHEET',
        'Y',
        'N');

COMMIT;

-- Parameter - issues regarding unverified params - needs a bit more investigation

-- THIS_ICAT

INSERT INTO THIS_ICAT(FACILITY_SHORT_NAME,
                      FACILITY_LONG_NAME,
                      FACILITY_URL,
                      FACILITY_DESCRIPTION,
                      DAYS_UNTIL_PUBLIC_RELEASE,
                      SEQ_NUMBER,
                      MOD_ID,
                      MOD_TIME,
                      CREATE_ID,
                      CREATE_TIME,
                      DELETED,
                      FACILITY_ACQUIRED)
VALUES (
          'ISIS',
          'ISIS Pulsed Neutron & Muon Source',
          'http://www.isis.rl.ac.uk',
          '"ISIS supports an international community of around 1600 scientists who use neutrons and muons for research in physics, chemistry, materials science, geology, engineering and biology"',
          1096,
          999,
          'From Spreadsheet',
          '2007-10-17 00:00:00.0',
          'FromSpreadsheet',
          '2007-10-17 00:00:00.0',
          'N',
          'Y');

COMMIT;

Insert into ICAT_AUTHORISATION
   (ID, USER_ID, ROLE, ELEMENT_TYPE, MOD_TIME, MOD_ID, CREATE_TIME, CREATE_ID, FACILITY_ACQUIRED, DELETED)
 Values
   (1, 'ISIS_GUARDIAN', 'ICAT_ADMIN', 'INVESTIGATION', '2007-10-17 00:00:00.0', 'dwf64', '2007-10-17 00:00:00.0', 'dwf64', 'Y', 'N');
COMMIT;
