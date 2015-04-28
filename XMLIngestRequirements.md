#Requirements for the XML Ingest in ICAT.

# Introduction #

(Please use your initials to note the source of each requirement, also please add yours to the listed initials if a requirement also applies to you/ your facility)

XMLIngest is a module in ICAT that allows users to ingest their metadata inform of XML file into iCAT. This will avoid the overhead of calling several webservices calls to ingest metadata.

# Use Cases #
  1. Simple Case: DAQ Machine creates XML file with metadata of datafiles and datafile parameters and calls the XMLIngest module. if any error occurs in ingestion, DAQ client sends a email to the administrator. (SN)
  1. Two styles of use: 1) Ingesting permissions and metadata before an experiment (from the proposal database) 2) Ingesting data as it is collected into these investigations (TG)

# Requirements #
  * Simple XML Schema (SN, TG)
  * Shouldn't exit by rolling back on problem ingesting few of the elements in XML. (SN)
  * Controllable list of calibration runs (inv\_numbers) (TG)

Add your content here.  Format your content with:
  * Text in **bold** or _italic_
  * Headings, paragraphs, and lists
  * Automatic links to other wiki pages