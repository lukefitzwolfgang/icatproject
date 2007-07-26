

Insert into DATAFILE_FORMAT
   (NAME, VERSION, FORMAT_TYPE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('nexus', '3.0.0', 'HDF5', 'Neutron and X-Ray data format.', TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATAFILE_FORMAT
   (NAME, VERSION, FORMAT_TYPE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('isis neutron raw v8', '8', 'binary', 'ISIS Raw File with sample volume added to block 6EF482', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATAFILE_FORMAT
   (NAME, VERSION, FORMAT_TYPE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('isis muon raw v4', '4', 'binary', 'Version 4 of the Muon Raw Binary Format', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATAFILE_FORMAT
   (NAME, VERSION, FORMAT_TYPE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('nexus', '1', 'binary', '1st Nexus Format Based on HDF', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');



Insert into DATASET_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('empty', 'No data available. Only the metadata available.', TO_TIMESTAMP('02/01/2007 11:25:14.0','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_STATUS
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('incomplete', TO_TIMESTAMP('02/01/2007 11:25:14.2','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_STATUS
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('under review', TO_TIMESTAMP('02/01/2007 11:25:14.2','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_STATUS
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('complete', TO_TIMESTAMP('02/01/2007 11:25:14.2','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_STATUS
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('unknown', TO_TIMESTAMP('02/01/2007 11:25:14.2','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');


Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('analyzed', 'Analyzed data', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('experiment_cal', 'RAW data collected at the facility during a calibration run.', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('experiment_eng', 'RAW data collected at the facility during a engineering test.', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('experiment_raw', 'RAW data collected at the facility during an experiment.', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('laser_shot', 'CLF specific data related to the high power end of the Laser monitoring.', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('reduced', 'Reduced Data', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('sample/target', 'Dataset containing information about a sample/target', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('simulation', 'Simulation data', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('source_monitoring', 'Data from the monitoring of the Light Source (neutron/X-ray/Laser)', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('special_cal', 'Calibration data not acquired through the normal Data acquisition system. (single detector calibration, �)', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('special_user', 'User data uploaded into the system', TO_TIMESTAMP('02/01/2007 11:25:14.3','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('etc', TO_TIMESTAMP('02/01/2007 11:25:14.5','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('processed', TO_TIMESTAMP('02/01/2007 11:25:14.5','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('unkown', TO_TIMESTAMP('02/01/2007 11:25:14.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into DATASET_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('raw', TO_TIMESTAMP('02/01/2007 11:25:14.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');


Insert into FACILITY_CYCLE
   (NAME, START_DATE, FINISH_DATE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('cycle_00_1', TO_TIMESTAMP('26/06/2000 00:00:00.0','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('16/08/2000 00:00:00.0','DD/MM/YYYY HH24:MI:SS.FF'), 'ISIS Cycle', TO_TIMESTAMP('02/01/2007 11:25:15.2','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into FACILITY_CYCLE
   (NAME, START_DATE, FINISH_DATE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('cycle_00_2', TO_TIMESTAMP('16/08/2000 00:00:00.0','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('01/11/2000 00:00:00.0','DD/MM/YYYY HH24:MI:SS.FF'), 'ISIS Cycle', TO_TIMESTAMP('02/01/2007 11:25:15.2','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');


Insert into INSTRUMENT
   (NAME, TYPE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('GEM', 'Crystallography', 'GEM', TO_TIMESTAMP('02/01/2007 11:25:13.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INSTRUMENT
   (NAME, TYPE, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('SXD', 'Crystallography', 'SXD', TO_TIMESTAMP('02/01/2007 11:25:13.9','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');



Insert into INVESTIGATION_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('calibration', 'A set of Calibration', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INVESTIGATION_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('engineering', 'Calibration, first light data, alignment, �', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INVESTIGATION_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('experiment', 'A scientific experiment.', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INVESTIGATION_TYPE
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('test', 'Test investigation', TO_TIMESTAMP('02/01/2007 11:25:14.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INVESTIGATION_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('simulation', TO_TIMESTAMP('02/01/2007 11:25:15.1','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INVESTIGATION_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('unknown', TO_TIMESTAMP('02/01/2007 11:25:15.1','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into INVESTIGATION_TYPE
   (NAME,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('measurement', TO_TIMESTAMP('02/01/2007 11:25:15.1','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');



Insert into STUDY_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('closed', 'Selection of Dataset completed.', TO_TIMESTAMP('02/01/2007 11:25:15.303207','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into STUDY_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('ongoing', 'New datasets will be added when available.', TO_TIMESTAMP('02/01/2007 11:25:15.303207','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into STUDY_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('not yet started', 'not yet started', TO_TIMESTAMP('02/01/2007 11:25:15.422059','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into STUDY_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('complete', 'complete', TO_TIMESTAMP('02/01/2007 11:25:15.422059','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into STUDY_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('unknown', 'unknown', TO_TIMESTAMP('02/01/2007 11:25:15.422059','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into STUDY_STATUS
   (NAME, DESCRIPTION,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('in progress', 'in progress', TO_TIMESTAMP('02/01/2007 11:25:15.422059','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');


Insert into TOPIC
   (ID, NAME, PARENT_ID, TOPIC_LEVEL,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   (1, 'ISIS', -1, 0, TO_TIMESTAMP('02/01/2007 11:43:59.413981','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into TOPIC
   (ID, NAME, PARENT_ID, TOPIC_LEVEL,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   (2, 'Experiment', 1, 1, TO_TIMESTAMP('02/01/2007 11:43:59.413981','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
Insert into TOPIC
   (ID, NAME, PARENT_ID, TOPIC_LEVEL,  MOD_TIME, CREATE_TIME, MOD_ID, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   (3, 'Powder Diffraction', 2, 2, TO_TIMESTAMP('02/01/2007 11:43:59.413981','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'FIRST PROPAGATION', 'N', 'Y');
   
   
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('finish_date', 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('good_frames', 'pulses', 'pulses', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('good_proton_charge', 'uAmp hours', 'uAmp hours', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('monitor_sum1', 'neutrons', 'neutrons', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('monitor_sum2', 'neutrons', 'neutrons', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('monitor_sum3', 'neutrons', 'neutrons', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('notes', 'N/A', 'N/A', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('number_of_periods', 'decimal', 'decimal', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('number_of_spectra', 'decimal', 'decimal', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('number_of_time_channels', 'decimal', 'decimal', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('run_duration', 'seconds', 'seconds', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('run_header', 'N/A', 'N/A', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, DESCRIPTION, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('run_number', 'N/A', 'Y', 'Y', 'U', 'U', 'U', 'Other possible name : shot_number', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.6','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('run_number', 'decimal', 'decimal', 'Y', 'Y', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:25:15.9','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('run_title', 'N/A', 'N/A', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('short_title', 'N/A', 'N/A', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('start_date', 'yyyy-MM-dd HH:mm:ss', 'yyyy-MM-dd HH:mm:ss', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('time_channel_parameters', 'N/A', 'N/A', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'),TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'), 'FIRST PROPAGATION', 'N', 'Y');
Insert into PARAMETER
   (NAME, UNITS, UNITS_LONG_VERSION, SEARCHABLE, NUMERIC_VALUE, IS_SAMPLE_PARAMETER, IS_DATASET_PARAMETER, IS_DATAFILE_PARAMETER, MOD_ID,  MOD_TIME, CREATE_TIME, CREATE_ID, DELETED, FACILITY_AQUIRED)
 Values
   ('total_proton_charge', 'uAmp hours', 'uAmp hours', 'Y', 'N', 'U', 'U', 'Y', 'FIRST PROPAGATION', TO_TIMESTAMP('02/01/2007 11:32:57.5','DD/MM/YYYY HH24:MI:SS.FF'), TO_TIMESTAMP('02/01/2007 11:25:14.7','DD/MM/YYYY HH24:MI:SS.FF'),'FIRST PROPAGATION', 'N', 'Y');