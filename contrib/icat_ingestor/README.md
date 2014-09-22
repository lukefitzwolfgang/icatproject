# Description

  The ICAT Ingestor package is a python module which allows easy metadata insertion ("ingestion") inside the ICAT metadata catalogue [www.icatproject.org], in use at Photon and Neutron facilities in Europe. The main idea behind this module is to use a standard python dictionary as input, leaving to third-party scripts the task to translate their metadata in this format. With the package it is provided a script which performs such a translation on HDF5 files: nevertheless, the method is generic enough that translators for other data formats (e.g. ASCII) can be easily written.


# Requirements
  + python 2.7
  + suds module
  + python-icat module: [https://code.google.com/p/icatproject/wiki/PythonIcat]
  + for the example script: h5py

# Running the example
## Step 0-a: create a test HDF5 file
   In case you do not have a ready-to-go HDF5 file, ```create_test_hdf5.py``` can help you creating a simple HDF5 file, containing some random data (for people interested, it can create NeXus compliant HDF5 files). The structure of the wannabe HDF5 file is described in ```hdf5_skel.cfg```. To create a new file, just do:

```
./create_test_hdf5.py hdf5_skel.cfg test.h5
```

## Step 0-b: fill basic structure in ICAT
   If you do have an empty ICAT, you can fill it with a Facility and some Instruments using ```create_instruments.py```: this uses the configuration file ```instruments.cfg``` to create this basic structure into ICAT.

## Step 1: configure the translator
   The script actually doing the translation is ```ingest_hdf5.py```. It reads two configuration files:
   + ```icat.cfg``: this file contains the needed parameters to connect to your ICAT service
   + ```hdf5_to_icat.cfg```, which contains the translation table from your HDF5 fields into ICAT entities. 

   The translator script reads the HDF5 fields described into the configuration file, and assign them to ICAT Entity attributes: each section represents an ICAT attribute. If the dataset is not found in the HDF5 file, it is assumed that the provided value is an hardcoded one, and used accordingly. The script creates an ```icat_args``` dictionary, where keys are ICAT entities (or field names: the distinction is not pretty clear at this point of time). Each value is then another dictionary, where keys are attribute names, and values attribute values. An example is:

```
{'sample': {'molecularFormula': 'Mg-11Li-B-M', 'name': 'type A'}, ...}
```
   After you've setup the translator, you can run the ingestor


## Step 2: run the ingestor
   This is as easy as doing:
   ```
   python ingest_hdf5.py -c hdf5_to_icat.cfg -i icat_psi.cfg -s psi test.h5
   ```
   The script will read information inside HDF5, fill the python dictionary, and the call 
   
   ```
   entry = ingest.insert_entry(section, icat_args)   
   ```
   This will try to insert one entry in ICAT: it nevertheless needs the complete information contained in ```icat_args```, as ICAT schema can be quite complex, and despite the great work done in creating python-icat, there are some subtle things that must be managed carefully, e.g.: there is no way to predict which attributes are required, or not. Also, Entities attributes can be references to other attributes: so, it is necessary to use an iterrative ingestion process.

   After all the *normal* entries are inserted, you still need to call ```ingest.fill_reference_tables(icat_args)```: this because there are cross-reference tables as *InvestigationInstrument* that are **NOT FILLED** during normal ICAT ingestion process. This process should be taken care by ICAT server, but it is not.