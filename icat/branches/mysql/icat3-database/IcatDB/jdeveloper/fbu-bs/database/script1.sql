ALTER TABLE EXPERIMENTER DROP CONSTRAINT "EXPERIMENTER_PROPOSAL_FK1"
;

ALTER TABLE EXPERIMENTER DROP CONSTRAINT "EXPERIMENTER_FACILITY_USE_FK1"
;

ALTER TABLE INSTRUMENT_SCIENTIST DROP CONSTRAINT "CON_INST_SCI_INST"
;

ALTER TABLE INSTRUMENT_SCIENTIST DROP CONSTRAINT "INSTRUMENT_SCIENTIST_FACI_FK1"
;

ALTER TABLE PROPOSAL DROP CONSTRAINT "PROPOSAL_FACILITY_CYCLE_FK1"
;

ALTER TABLE SAMPLE DROP CONSTRAINT "SAMPLE_PROPOSAL_FK1"
;

ALTER TABLE SAMPLE_PARAMETERS DROP CONSTRAINT "SAMPLE_PARAMETERS_SAMPLE_FK1"
;

DROP TABLE EXPERIMENTER CASCADE CONSTRAINTS;

DROP TABLE FACILITY_CYCLE CASCADE CONSTRAINTS;

DROP TABLE FACILITY_USER CASCADE CONSTRAINTS;

DROP TABLE INSTRUMENT CASCADE CONSTRAINTS;

DROP TABLE INSTRUMENT_SCIENTIST CASCADE CONSTRAINTS;

DROP TABLE PROPOSAL CASCADE CONSTRAINTS;

DROP TABLE SAMPLE CASCADE CONSTRAINTS;

DROP TABLE SAMPLE_PARAMETERS CASCADE CONSTRAINTS;

CREATE TABLE EXPERIMENTER
(
EXPERIMENT_NUMBER NUMBER NOT NULL,
FACILITY_USER_NUMBER NUMBER NOT NULL,
PROPOSAL_SYSTEM_PERMISSION VARCHAR2(50) NOT NULL,
ROLE VARCHAR2(100) NOT NULL,
MODIFICATION_TIMESTAMP TIMESTAMP(1) NOT NULL
)
;

CREATE TABLE FACILITY_CYCLE
(
NAME VARCHAR2(255) NOT NULL,
START_DATE TIMESTAMP(1) NOT NULL,
END_DATE TIMESTAMP(1),
MODIFICATION_TIMESTAMP TIMESTAMP(1) NOT NULL
)
;

CREATE TABLE FACILITY_USER
(
FACILITY_USER_NUMBER NUMBER NOT NULL,
FEDERAL_ID VARCHAR2(255) NOT NULL,
TITLE VARCHAR2(20) NOT NULL,
INITIALS VARCHAR2(20) NOT NULL,
FIRST_NAME VARCHAR2(100) NOT NULL,
MIDDLE_NAME VARCHAR2(100),
LAST_NAME VARCHAR2(100) NOT NULL,
MODIFICATION_TIMESTAMP DATE NOT NULL
)
;

CREATE TABLE INSTRUMENT
(
NAME VARCHAR2(100) NOT NULL,
SHORT_NAME VARCHAR2(100) NOT NULL,
TYPE VARCHAR2(100) NOT NULL,
DESCRIPTION VARCHAR2(3000) NOT NULL,
MODIFICATION_TIMESTAMP DATE NOT NULL
)
;

CREATE TABLE INSTRUMENT_SCIENTIST
(
INSTRUMENT_NAME VARCHAR2(100) NOT NULL,
FACILITY_INST_SCIENTIST_FED_ID VARCHAR2(255) NOT NULL,
MODIFICATION_TIMESTAMP DATE NOT NULL
)
;

CREATE TABLE PROPOSAL
(
EXPERIMENT_NUMBER NUMBER NOT NULL,
PREVIOUS_EXPERIMENT_NUMBER NUMBER,
EXPERIMENT_TYPE VARCHAR2(50) NOT NULL,
EXPERIMENT_TITLE VARCHAR2(100) NOT NULL,
EXPERIMENT_ABSTRACT VARCHAR2(100) NOT NULL,
ASSIGNED_INSTRUMENT VARCHAR2(100) NOT NULL,
ROUND VARCHAR2(255) NOT NULL,
DELETED VARCHAR2(1) DEFAULT 'N',
MODIFICATION_TIMESTAMP DATE NOT NULL
)
;

CREATE TABLE SAMPLE
(
SAMPLE_IDENTIFIER NUMBER NOT NULL,
EXPERIMENT_NUMBER NUMBER NOT NULL,
NAME VARCHAR2(100),
CHEMICAL_FORMULA VARCHAR2(100),
MODIFICATION_TIMESTAMP DATE
)
;

CREATE TABLE SAMPLE_PARAMETERS
(
SAMPLE_IDENTIFIER NUMBER NOT NULL,
NAME VARCHAR2(100),
VALUE VARCHAR2(100),
UNITS VARCHAR2(100),
MODIFICATION_TIMESTAMP DATE
)
;

ALTER TABLE EXPERIMENTER
ADD CONSTRAINT SYS_C00186742 PRIMARY KEY
(
EXPERIMENT_NUMBER,
FACILITY_USER_NUMBER
)
 ENABLE
;

ALTER TABLE FACILITY_CYCLE
ADD CONSTRAINT FACILITY_CYCLE_PK PRIMARY KEY
(
NAME
)
 ENABLE
;

ALTER TABLE FACILITY_USER
ADD CONSTRAINT SYS_C00186745 PRIMARY KEY
(
FACILITY_USER_NUMBER
)
 ENABLE
;

ALTER TABLE FACILITY_USER
ADD CONSTRAINT CON_FAC_USR UNIQUE
(
FEDERAL_ID
)
 ENABLE
;

ALTER TABLE INSTRUMENT
ADD CONSTRAINT SYS_C00186746 PRIMARY KEY
(
NAME
)
 ENABLE
;

ALTER TABLE INSTRUMENT_SCIENTIST
ADD CONSTRAINT SYS_C00186747 PRIMARY KEY
(
INSTRUMENT_NAME,
FACILITY_INST_SCIENTIST_FED_ID
)
 ENABLE
;

ALTER TABLE PROPOSAL
ADD CONSTRAINT SYS_C00186741 PRIMARY KEY
(
EXPERIMENT_NUMBER
)
 ENABLE
;

ALTER TABLE SAMPLE
ADD CONSTRAINT SAMPLE_PK PRIMARY KEY
(
SAMPLE_IDENTIFIER
)
 ENABLE
;

ALTER TABLE SAMPLE_PARAMETERS
ADD CONSTRAINT SYS_C00186744 PRIMARY KEY
(
SAMPLE_IDENTIFIER
)
 ENABLE
;

ALTER TABLE EXPERIMENTER
ADD CONSTRAINT EXPERIMENTER_PROPOSAL_FK1 FOREIGN KEY
(
EXPERIMENT_NUMBER
)
REFERENCES USEROFFICE_ISIS.PROPOSAL
(
EXPERIMENT_NUMBER
) ENABLE
;

ALTER TABLE EXPERIMENTER
ADD CONSTRAINT EXPERIMENTER_FACILITY_USE_FK1 FOREIGN KEY
(
FACILITY_USER_NUMBER
)
REFERENCES USEROFFICE_ISIS.FACILITY_USER
(
FACILITY_USER_NUMBER
) ENABLE
;

ALTER TABLE INSTRUMENT_SCIENTIST
ADD CONSTRAINT CON_INST_SCI_INST FOREIGN KEY
(
INSTRUMENT_NAME
)
REFERENCES USEROFFICE_ISIS.INSTRUMENT
(
NAME
) ENABLE
;

ALTER TABLE INSTRUMENT_SCIENTIST
ADD CONSTRAINT INSTRUMENT_SCIENTIST_FACI_FK1 FOREIGN KEY
(
FACILITY_INST_SCIENTIST_FED_ID
)
REFERENCES USEROFFICE_ISIS.FACILITY_USER
(
FEDERAL_ID
) ENABLE
;

ALTER TABLE PROPOSAL
ADD CONSTRAINT PROPOSAL_FACILITY_CYCLE_FK1 FOREIGN KEY
(
ROUND
)
REFERENCES FACILITY_CYCLE
(
NAME
) ENABLE
;

ALTER TABLE SAMPLE
ADD CONSTRAINT SAMPLE_PROPOSAL_FK1 FOREIGN KEY
(
EXPERIMENT_NUMBER
)
REFERENCES USEROFFICE_ISIS.PROPOSAL
(
EXPERIMENT_NUMBER
) ENABLE
;

ALTER TABLE SAMPLE_PARAMETERS
ADD CONSTRAINT SAMPLE_PARAMETERS_SAMPLE_FK1 FOREIGN KEY
(
SAMPLE_IDENTIFIER
)
REFERENCES SAMPLE
(
SAMPLE_IDENTIFIER
) ENABLE
;
