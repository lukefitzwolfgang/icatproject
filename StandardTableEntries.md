# Introduction #

In order to facilitate cross facility searching of data catalogues it would be helpful for facilities to use standard terms for certain objects within ICAT.

This is an initial proposal (based on the existing values used at ISIS).

Please add or edit the entries, with comments ideally,or contact the ICAT mailing list with your views

## Dataset\_Type ##

| NAME | DESCRIPTION | Comments |
|:-----|:------------|:---------|
| analyzed | Analyzed data |  |
| experiment\_cal | RAW data collected at the facility during a calibration run. | redundant? (C.Felder) |
| experiment\_eng | RAW data collected at the facility during a engineering test. | redundant? (C.Felder) |
| experiment\_raw | RAW data collected at the facility during an experiment. | redundant? (C.Felder) |
| raw | measured raw data | proposal (C.Felder) |
| reduced | Reduced Data |  |
| simulation | Simulation data | naming: simulated (C.Felder) |
| special\_cal | Calibration data not acquired through the normal Data acquisition system. (single detector calibration, ?)|  |


## Investigation\_Type ##
| NAME | DESCRIPTION | Comments |
|:-----|:------------|:---------|
| calibration | A set of Calibration |  |
| commercial\_experiment | A scientific experiment performed by a commercial company |  |
| engineering | Calibration, first light data, alignment, ? |   |
| experiment | A scientific experiment. |   |
| simulation |  |  |


## Datafile\_Format ##
| NAME	               | VERSION | FORMAT\_TYPE | DESCRIPTION                    | Comments |
|:--------------------|:--------|:-------------|:-------------------------------|:---------|
| isis neutron raw    | 2       | binary      | ISIS RAW Neutron format        |  |
| isis neutron raw    | 8	  | binary      | ISIS RAW Neutron format        |  |
| isis muon raw       | 4       | binary      | ISIS RAW muon format           |  |
| nexus               | 1       | binary      |                                |  |
| nexus               | 2.1.0	  | HDF4        | ISIS Muon format               |  |
| nexus               | 3.0.0	  | HDF5        | Neutron and X-Ray data format. |  |
| nexus               | 4.1.0	  | HDF4        | ISIS Muon format               |  |
| nexus               | 4.3.0	  | HDF4        | ISIS Muon format               |  |