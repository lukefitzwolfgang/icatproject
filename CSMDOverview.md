# A Metadata Model for Facilities Science #

The model is organised around a notion of Studies, a study being a body of scientific work on a particular subject of investigation.  During a study, a scientist would perform a number of investigations e.g. experiments, observations, measurements and simulations. Results from these investigations usually run through different stages: raw data, analysed or derived data and end results suitable for publication. The model has a hierarchical model of the structure of scientific research programmes, projects and studies, and also a generic model of the organisation of data sets into collections and files.

For specific data sets the model provides links to the files holding the actual data. Users can then use these links to access the data with their own applications for analysis as required. Data should be grouped accordingly, and associated with the appropriate experimental parameters. Not all information captured in specific metadata schemas would be used to search for this data or distinguish one data set from another, give possibility to select special parameter.

  * **Investigation**: forms the fundamental unit of the model, with a title, abstract, dates, and unique identifiers referencing the particular study.   Also associated with the investigation are the facility and instrument used to collect data.
  * **Investigator**: describes the people involved in the study, together with their institution and role in the study (e.g. principle investigator, research student).
  * **Topic and Keyword**: provide controlled and uncontrolled vocabulary to annotate and index the investigation.
  * **Publication**: provides links to publications associated with (motivating or derived from) the investigation.
  * **Sample**: information on the material sample under investigation within the study.  The model has fields for a sample’s name, chemical formula and any associated special information about it, such as specific safety information on a toxic material.
  * **Dataset**: one or more datasets can be associated with an investigation, representing different runs or analyses on the sample.  Initially a raw data set can be attached to the investigation, but subsequently, analysed datasets can also be associated.
  * **Datafile**: the CSMD takes a hierarchical view of data holdings, as data sets may contain other dataset as well as units of storage, typically datafiles.  Each datafile has more detailed information, including its name, version, location, data format, creation and modification time, and fixity information such as a Checksum.
  * **Parameter**: parameters describe physical entities associated with the investigation, such as temperature, pressure, or scattering angle, describing either the parameters of the sample, the environment the data was collected in, or the parameters being measured.  Thus parameters are associated with the sample, dataset or the datafile, and have names, units, values, and allowable data ranges.
  * **Authorisation**: the CSMD can associate conditions on investigations and data sets, so that user specified access conditions can be specified.  Thus the authorisation entity can record which user in which role can access data on specific investigations.

The core entities of the CSMD are given in Figure 1 (courtesy of Peter Turner).

![http://icatproject.googlecode.com/svn/Images/icat_v3_core_PeterTurner.png](http://icatproject.googlecode.com/svn/Images/icat_v3_core_PeterTurner.png)