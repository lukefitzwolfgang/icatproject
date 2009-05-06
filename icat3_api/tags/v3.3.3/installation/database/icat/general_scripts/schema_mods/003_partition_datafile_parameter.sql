

DROP TABLE datafile_parameter;

CREATE TABLE DATAFILE_PARAMETER
(
DATAFILE_ID NUMBER NOT NULL,
NAME VARCHAR2(255) NOT NULL,
UNITS VARCHAR2(255) NOT NULL,
STRING_VALUE VARCHAR2(4000),
NUMERIC_VALUE DOUBLE PRECISION,
RANGE_TOP VARCHAR2(255),
RANGE_BOTTOM VARCHAR2(255),
ERROR VARCHAR2(255),
DESCRIPTION VARCHAR2(4000),
MOD_TIME TIMESTAMP(1) NOT NULL,
MOD_ID VARCHAR2(255) NOT NULL,
CREATE_TIME TIMESTAMP(1) NOT NULL,
CREATE_ID VARCHAR2(255) NOT NULL,
FACILITY_ACQUIRED VARCHAR2(1) NOT NULL,
DELETED VARCHAR2(1) DEFAULT 'N' NOT NULL
);

CREATE INDEX dp_idx1 ON datafile_parameter(name);


ALTER TABLE DATAFILE_PARAMETER
ADD CONSTRAINT PK_DP PRIMARY KEY
(
DATAFILE_ID,
NAME,
UNITS
)
 ENABLE
;

ALTER TABLE DATAFILE_PARAMETER
ADD CONSTRAINT FK_DP_DF FOREIGN KEY
(
DATAFILE_ID
)
REFERENCES DATAFILE
(
ID
)
ON DELETE CASCADE ENABLE
;





ALTER TABLE DATAFILE_PARAMETER
ADD CONSTRAINT DATAFILE_PARAMETER_PARAME_FK1 FOREIGN KEY
(
NAME,
UNITS
)
REFERENCES PARAMETER
(
NAME,
UNITS
) ENABLE
;



ALTER TABLE DATAFILE_PARAMETER
ADD CONSTRAINT DATAFILE_PARAMETER_CHK1 CHECK
(deleted in('Y','N'))
 ENABLE
;

ALTER TABLE DATAFILE_PARAMETER
ADD CONSTRAINT DATAFILE_PARAMETER_CHK2 CHECK
(Facility_Acquired in('Y','N'))
 ENABLE
;


COMMENT ON COLUMN DATAFILE_PARAMETER.DESCRIPTION IS 'from where and how the parameter was recorded or extracted as oppose to a definition of the parameter; the latter being defined in the parameter table'
;
