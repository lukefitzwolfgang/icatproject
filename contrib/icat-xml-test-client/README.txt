Xml test client for ICAT
************************

This readme explains briefly how to use the IcatXmlTestClient. The client can
be used to import data into icat and to search data in icat.

Data to be imported must be in xml format. The format is based on the wsdl of
icat's web service. Data retrieved from icat (search) will also be saved in
xml format.

Build the client
----------------

You need maven installed and properly configured on your machine.
Run 'mvn package' in the icatXmlTestClient directory to create the
icatXmlTestClient-0.2.jar in the target sub-directory.

Using the client
----------------

You need a java VM of version 1.6 or higher. Open a command line change into the
directory which contains the icatXmlTestClient-0.1.jar and call

java -cp icatXmlTestClient-0.2.jar [proxy_params] org.icatproject.testclient.IcatXmlTestClient command [query] [file] icat_base_URL user password auth_mechanism

proxy_params: standard java properties e.g. to configure your proxy requirements:
   -Dhttp.nonProxyHosts=myicat.xxx.yy to avoid passing through the proxy for
   your server or -Dhttp.proxyHost=proxy.xxx.yy -Dhttp.proxyPort=80 to use
   the proxy
   
command: can be import or search

query: the icat query string (see http://www.icatproject.org/mvn/site/icat/4.2.1/icat.client/manual.html);
   can only be used with the search command

file: the file that you want to import or the file which should contain the search result.

icat_base_URL: the first part of the URL to call the icat web service; e.g. if
   your icat wsdl can be found under http://myicat.xxx.yy:8080/ICATService/ICAT?wsdl
   you need to specify http://myicat.xxx.yy:8080
   
user: the user who authenticates to icat (for the first import into an empty
   icat this must be one of the root users who has been defined in the
   icat.properties file)
   
password: the password of the authenticating user

auth_mechanism: the authentication mechanism that you are using (db or ldap)

Example:
java -cp icatXmlTestClient-0.2.jar -Dhttp.nonProxyHosts=myserver.xxx.yy org.icatproject.testclient.IcatXmlTestClient import icatdata.xml http://myserver.xxx.yy:8080 root secret db
   

Search command
--------------

The search command executes a query and saves all results to a file.

Example:
java -cp icatXmlTestClient-0.2.jar org.icatproject.testclient.IcatXmlTestClient search "Facility [name = 'ESRF']" search.xml http://myserver.xxx.yy:8080 root secret db

This will fetch the facility named 'ESRF' and save it to the search.xml file.


Import command
--------------

The import command imports icat data from an xml file.

Example:
java -cp icatXmlTestClient-0.2.jar org.icatproject.testclient.IcatXmlTestClient import import-icat.xml http://myserver.xxx.yy:8080 root secret db

This will import all data from import-icat.xml.

The xml file structure has to be:

<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<icatdata xmlns:ns2="http://icatproject.org">
	<config>
		<haltOnError></haltOnError>
		<entities2clean>
			<query></query>
			...
		</entities2clean>
		<localIdRange>
			<min></min>
			<max></max>
		</localIdRange>
		<searchids>
			<search>
				<id></id>
				<query></query>
			</search>
		</searchids>
	</config>
	<!-- icat data part (e.g. instrument) --> 
	<instrument>
		<id></id>
		<searchid></searchid>
		<name></name>
		...
	</instrument>
</icatdata>

The file contains a configuration part (<config> element) and a data part
(all the rest).

The CONFIGURATION part allows to define error handling, to clean data and to
resolve references between icat objects:

haltOnError: if true any error stops the execution of the client, if false
   (default) the client ignores errors and tries to continue
   
entities2clean: all data which is returned by the queries listed here will be
   removed before importing. You can have one or more query elements inside to
   reference all data to be deleted. Allows to start from a clean DB for each
   import run. The syntax is the icat query syntax (see search command above).
   If you specify only the name of the EntityBaseBean xml element (e.g.
   instrument, facility, dataset ...) all data of this entity base bean will
   be deleted (empty table)
   
localIdRange: min (default MIN_INTEGER) and max (default MAX_INTEGER) define a
   range of ids used in the xml file to reference other icat objects in the
   same file. Allows to create e.g. a facility object in the file and re-use
   it later on in the same file by referencing its id; all ids that are outside
   this range will be treated as existing icat IDs.
   
searchids: allows to search an existing icat object in the DB and to reference
   it through a local id; can be used to query for an existing facility to
   reference it further down in the file.
   
The DATA part uses the entity base bean xml name (see icat wsdl) as the element
name and all properties of the object can be defined as sub-elements. Contained
objects can be defined inside (they will be created) or referenced.

To reference an object you place only the <id> element with the id inside the
objects element. You can also add an <id> element to an object that you are
creating (a local id). This local id can then be referenced further down in
the file.

You can also reference existing icat objects through the <searchid> element
which does a search on icat for the object. If you additionally specify a local
id the searched element can be referenced further down with the local id.
This behavior is similar to searching for an object in the searchid part of the
config element but it can be more convenient to do it in the data part right in
the object that you are creating.

The client caches ids per entity base bean type (i.e. you can use the same id
for different types). The first time it comes across an id it assumes that you
want to create the object and adds it to the cache after creation. Afterwards
each time it comes across the same id for the same data type it fetches the
object from the cache and does not create it again. 

Please see the examples under src/test/resources for more information
(especially the import-icat.xml file).

Potential problems
------------------

Don't forget to pass the proxy configuration to the java vm when calling the
test client (see examples above)! Typically if you cannot connect to icat's
webservice or if you get an HTTP 403 error check your proxy configuration.

If your web service uses https and a self generated certificate you have to
tell the java vm to accept this certificate. In general you need to add the
certificate to your trust store. See
http://code.google.com/p/java-use-examples/source/browse/trunk/src/com/aw/ad/util/InstallCert.java
for the InstallSert utility which creates a trust store called jssecacerts
with your certificate. You then have to pass this trust store as the default
trust store to your vm:
java -cp icatXmlTestClient-0.2.jar -Djavax.net.ssl.trustStore=jssecacerts -Dhttp.nonProxyHosts=myserver.xxx.yy org.icatproject.testclient.IcatXmlTestClient ...

Development details
-------------------

The web service client is generated using wsimport tool from jdk. The
ICATService class has been modified manually to add a convenience constructor.
Call wsimport with the following parameters to generate the code in a
directory called 'src':
wsimport -s src http://myserver.xxx.yy:8080/icat/ICATService/ICAT?wsdl

The @XmlElement(namespace = "http://icatproject.org") annotation was generated
by wsimport for several properties. These properties could not be retrieved
later on by the xml unmarshaller. The annotations had to be removed to be able
to marshall/unmarshall these properties correctly. The origin of this issue
seems to be that the datafile and dataset class are annotated as xml root elements.
Therefore in the xsd referenced by the wsdl of icat these elements are all
declared as references
(e.g. <xs:element ref="tns:dataset" .../>)
instead of simple elements
(e.g. <xs:element name="..." type="tns:dataset" .../>).
Normally by specifying the org.icatproject namespace in the JAXB context
used for marshalling/unmarshalling xml the references should be found.
Unfortunately this does not work so far.

To fix the problem the annotations had to be removed from the following
properties:

Datafile.dataset
DatafileParameter.datafile
DatasetParameter.dataset
InputDatafile.datafile
InputDataset.dataset
OutputDatafile.datafile
OutputDataset.dataset

An addtional member called searchId has been added to the EnityBaseBean class
to handle reference resolving over icat queries. 

The marshalling and un-marshalling is handled by JAXB and has its root element
in the Icatdata container class.

TODOs
-----
+ add general update flag which updates each element which has an icat
  reference id or a searchid element (or use update attribute instead?)
+ perhaps use XML ID and XML IDREF instead of current <id> elements for
  references (requires strings instead of longs)
  
Contact
-------
stefan.schulze@esrf.fr