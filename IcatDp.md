# Depreciation notice #

Support for Data Portal was dropped in ICAT 4.  [Topcat](InstallTopcat40.md) is the replacement.

# Data Portal #
Author(s): Glen Drinkwater and Richard Tyer

## Architecture ##
The Data Portal was written using the new Java Enterprise Edition 5 technology stack.  The Data Portal is a customisable web interface for secure data search and retrieval that consists of two main parts:

  1. the core module which provides all the requisite functionality and interacts with the ICAT API.

> 2. a customisable Web Interface (WI) layer.

The Data Portal schema holds a range of information that is required for the management and administration of the Data Portal and its users. The Data Portal can be accessed by users via their web browser over a secure HTTPS connection.

The core module has been written using Enterprise JavaBean (EJB) 3 and is hosted using the open source Glassfish Application server.  The WI communicates via the EJB's remote interfaces rather than use the local EJB's interfaces. This approach was taken in order to allow multiple front ends to be designed if ever the current JSF implementation were no longer sufficient (e.g. a JavaFX, Portlet or Struts 2 web or desktop application could be implemented to communicate to the core EJBs) and to allow rapid application development, since the core EJBs do not need to be reloaded every time a new WI is deployed.  It should be noted that performance is not hindered by using the remote interfaces since all communication is performed inside the same JVM exploiting Glassfish’s feature of treating all collocated remote interfaces as if they were local interfaces.

The core EJBs could be exposed to other applications not wishing to communicate using EJBs via web services and JSR 181.  It would be a simple task to expose the EJBs as web services and for other applications to interface to these.  A prototype Data Portlet has been developed to demonstrate that exposing the Data Portal core EJBs as web services is achievable.  This used the web services to deploy a portlet application that mimicked the functionality of the Data Portal.

## Core Modules (EJB Session Beans) ##

The core modules are written as Stateless Session Beans (EJB 3).  This means that after each invocation of a method, no state is kept by the bean, similar to web services.  The advantage is gained by better performance and much simpler design and implementation of the core modules. This also creates a clean separation of client UI and business logic.

![http://lh4.ggpht.com/_NE2QiMZ05d0/STK_glZLqsI/AAAAAAAAAGY/i4FtObR2dvs/s800/DPdesign.jpg](http://lh4.ggpht.com/_NE2QiMZ05d0/STK_glZLqsI/AAAAAAAAAGY/i4FtObR2dvs/s800/DPdesign.jpg)

The Previous Figure shows the main components within the core module, the session, query, lookup and the JMS messages and queues components.

The JMS Messages components do all the concurrent processing within the application.  For logging onto and searching multiple facilities a JMS queue is used to process multiple requests and depending on the request it is severing caches the information in either the LogonCache or QueryCahe for retrieval later.  Every thing from logging on, searching, downloading etc is logged by the core module.  This is just a simple message that is sent to the EventQueue for processing asynchronously to be stored in the event table.

The Lookup component underpins all the other components allowing them to lookup information within the Data Portal schema (i.e. facility names, active facilities, facility WSDL locations etc).  For example, the Query MDBs will receive a query to search ISIS, so it uses the lookup component to find the location of the ISIS ICAT API to send the query to.

The session component logs users on and off the Data Portal as well as setting and getting the users session and preference information.  It uses the LogonCache to cache the information when a user would be logging onto more than one facility.

The query component facilitates all the queries, it receives and creates the query and sends them off to the query MDBs and then waits for the returned query to be put into the query cache.

### Entity Beans (Schema) ###

This is a schema design of the Data Portal core schema.

![http://lh4.ggpht.com/_NE2QiMZ05d0/STK_g7vWfqI/AAAAAAAAAGg/yVcRX3jrVNc/s800/DPcoreSchema.png](http://lh4.ggpht.com/_NE2QiMZ05d0/STK_g7vWfqI/AAAAAAAAAGg/yVcRX3jrVNc/s800/DPcoreSchema.png)

There are 3 parts to the schema:
  * User and session information:
    * DP\_USER\_ROLE – Mapping table between DP\_USER and DP\_ROLE
    * DP\_ROLE – Static lookup table with Data Portal roles
    * DP\_USER – Users who have logged in
    * DP\_SESSION – User’s sessions on Data Portal
    * DP\_FACILITY\_SESSION – For all of the ICAT API sessions associated with DP\_SESSION
    * DP\_USER\_PREFERENCE – User’s preferences for Data Portal

  * Event log information:
    * DP\_EVENT – Static lookup table with all available event types
    * DP\_EVENT\_LOG – Log of event and some details
    * DP\_EVENT\_LOG\_DETAILS – Many to 1 mapping for all details of the DP\_EVENT\_LOG

  * Lookup information:
    * DP\_MODULE\_LOOKUP – Facility information including WSDL location
    * DP\_FACILITY – Static lookup of all the facilities associated with Data Portal

There will be an Entity Bean for each table. For example, the Entity Bean corresponding to DP\_SESSION is shown in Figure 7.  This is a simple POJO that is turned into an Entity Bean by the container in EJB 3.  Session Beans can then use this bean to search for data using the NamedQuery tag within the code.  This query is specified using EJB QL, which is similar to SQL, but specified using classes rather than tables.  Hence simple queries would return a List< Session> of all of the rows in this table matching various predicates.


## User Interface (Web Module) ##
### Web Interface ###

The Web Interface was built using JSF 1.2 based in Glassfish. In addition, the added functionality of the Apache Tomahawk components is used for tables and trees, while Ajax4JSF is used to 'Ajaxify' JSF components that are not already Ajax enabled.  The web interface is a thin layer built on top of the Data Portal core, which is in turn built on top of the ICAT API.  This facilitates seamless integration with other commercial JSF component providers (e.g. RichFaces, ICEFaces, Woodstock etc.) or, as explained previously, would allow either a redesign or reimplementation of a web interface that would interact directly with the core EJBs.

The model that the Web Interface uses with JSF is for one backing bean per page.  Each page actions and binding variables are present in the request scoped backing beans class.  This allows new pages to be added simply.  There are a few session scoped backing beans that the whole of the WI uses:

  * Visit – This holds all the information regarding the users session (preferences, session id etc.);
  * VisitData – This holds all of the user's search results so they can be accessed by each page;
  * SessionHistory – This holds all the information about the previous search criteria.

Below is a screenshot from the Data Portal showing the Data page, which is where the user can view an investigation’s datasets and the dataset’s datafiles.  In the screenshot, the user is viewing the datafiles belonging to the dataset ‘Default’.

![http://lh3.ggpht.com/_NE2QiMZ05d0/STK_hJCg9fI/AAAAAAAAAGo/wa_6H--aVIg/s800/DPinterface.png](http://lh3.ggpht.com/_NE2QiMZ05d0/STK_hJCg9fI/AAAAAAAAAGo/wa_6H--aVIg/s800/DPinterface.png)

### User Interface Pages ###

The WI has the following views:

  * Login
  * Keyword Search
  * Advanced Search
  * Facility Search
  * Results
    * Investigations
    * Datasets and Datafiles
  * Preferences

When a user logs in, they are taken to the 'My Data Search' page by default, which lists all the investigations that they are an investigator of.  This default location can be changed in the preferences page.  A list of all of the functions and corresponding resulting pages are show in th efollowing Table.

| Function		      | JSF function (F)  or link (L)| 	Display page	| Comments|
|:----------------|:-----------------------------|:--------------|:--------|
| Login	              | F	| Keyword search	| User logins in and goes to keyword search page by default.  Depending on configuration that could go to Advanced, MyData (Investigations) or Facility Search pages |
| Logout	              | F	| Login 	| From all pages except login |
| Do Keyword Search	      | F	| Investigations 	|  |
| Do Advanced Search	      | F	| Investigations 	|  |
| Do Facility Search	      | F	| Investigations	|  |
| Do Run Number Search	      | F	| Datasets and Datafiles 	| ISIS Search page only |
| Do Navigation Keyword Search	        | F	| Investigations 	| Searches keyword from the navigation bar from all pages |
| Do Navigation  Advanced Search       | F	| Investigations 	| Searches advanced from the navigation bar from all pages |
| Do Navigation Facility Search	| F	| Investigations	| Searches facility from the navigation bar from all pages |
| Do Navigation Run Number Search	| F	| Datasets and Datafiles 	| ISIS Search page only |
| Do MyData Search	      | F	| Investigations	  | Gets all users own investigations. From all pages except login |
| View Data	              | F	| Datasets and Datafiles |  |
| DownloadDatafile(s)	      |  F	| Datasets and Datafiles |  |
| DownloadDataset	      | F	| Datasets and Datafiles |  |
| View Prefs	              | L	| Preferences	|  |
| Update Prefs	              | F	| Preferences	|  |
| Goto Advanced Search	      | L	| Advanced Search	| From all pages except login |
| Goto Facility Search	      | L	| Facility Search	| From all pages except login |
| Goto Keyword Search	      | L	| Keyword Search	| From all pages except login |
| Goto Results	              | L	| Investigations	| Goes to last search results  |
| Goto Data	              | L	| Datasets and Datafiles	| Goes to last data results |

### Workflow ###

> [[Image(DPdataflow.png, 50%, middle)]]
The previous figure shows the basic workflow within the Data Portal. The basic workflow of the system is that the user logs in and is authenticated to the ICAT API and Data Portal.  The user would then search for data through one of the available searches, which would present a list of returned investigations that matches their search criteria and that the user has access to view.  The user would then drill down into an investigation and look at the datasets and datafiles of an investigation.  At this point the user can choose to download the individual files or the whole dataset.

Every page, except the Login page, is accessible from all other page, using the navigation and tabs.  These represent links and are not function calls.

A JSF link would be a static action link and a JSF function would be a dynamic action link.  For example, link to view the Preferences would be:

<h:commandButton value=”View Preterences” action=”viewPreferences” />
but a dynamic action link to add bookmarks would be:

<h:commandButton value=”Add Preference” action=”#{prefsBean.addPref}” />
where prefsBean would be an instance of a backing bean and addPref would be a method call to dynamically call the prefsBean bean to add the data. If the data was added without an exception being thrown, then the JSF action result would be ‘viewPreferences’, i.e. the same as the static link.

### Error Handling ###

In the event of an error, an error page within the WI should be displayed with a message that an error has occurred and that the user should try again.  At no point should the user see any HTTP error code pages.  This behaviour can be configured within the deployment descriptor file.  Invalid URLs also should be handled similarly with the user being offered the choice to go to either the login or the previous page as appropriate

### Screens ###
  * Login
| Screen name	 | Login |
|:-------------|:------|
| Purpose	 | Authenticates the user to the system, |
| URL	 | /index.jsp |
| Data Displayed	 | None |
| Session state	 | No |
| Effect on refresh	 | None |
| Effect on back	 | None |

  * Keyword Search
| Screen name	 | Keyword search |
|:-------------|:---------------|
| Purpose	 | Gives user input for keyword search |
| URL	 | /protected/basic\_search.jsp |
| Data Displayed	 | None |
| Session state	 | Yes |
| Effect on refresh	 | None, screen showed again |
| Effect on back	 | None, back to last screen |

  * Advanced Search
| Screen name  | Advanced Search |
|:-------------|:----------------|
| Purpose	 | Gives user input for advanced search etc |
| URL	 | /protected/advanced\_search.jsp |
| Data Displayed	 | None |
| Session state	 | Yes |
| Effect on refresh	 | None, screen showed again |
| Effect on back	 | None, goes back to last screen |

  * Facility Search
| Screen name	 | Facility Search (Diamond or ISIS) |
|:-------------|:----------------------------------|
| Purpose	 | Gives user input for isis or dls search etc |
| URL	 | /protected/isis\_search.jsp, /protected/dls\_search.jsp |
| Data Displayed	 | None |
| Session state	 | Yes |
| Effect on refresh	 | None, screen showed again |
| Effect on back	 | None, goes back to last screen |

  * Investigations
| Screen name	 | Investigations |
|:-------------|:---------------|
| Purpose	 | Shows all the investigation results in a table |
| URL	 | /protected/investigations.jsp |
| Data Displayed	 | Collection of Investigations |
| Session state	 | Yes |
| Effect on refresh	 | None, screen showed again |
| Effect on back	 | None, goes back to last screen |

  * Datasets and Datafiles
| Screen name	 | Datasets and Datafiles |
|:-------------|:-----------------------|
| Purpose	 | Shows all the datasets and datafiles in a table |
| URL	 | /protected/datasets.jsp |
| Data Displayed	 | Collection of datasets and datafiles |
| Session state	 | Yes |
| Effect on refresh	 | None, screen showed again |
| Effect on back	 | None, goes back to last screen |

  * Preferences
| Screen name	 | Preferences |
|:-------------|:------------|
| Purpose	 | Shows all user preferences |
| URL	 | /protected/preferences.jsp |
| Data Displayed	 | Users preferences |
| Session state	 | Yes |
| Effect on refresh	 | None, screen showed again |
| Effect on back	 | None, goes back to last screen |


### Internationalization and Locale ###
Internationalisation can be readily implemented when using JSF. Currently only the locale 'EN' is supported.

### Security ###
The application uses filters to ensure that the user is logged in and that their role is allowed to view the requested pages and directories.  JSF PhaseEvents could also be used to check roles and logged in status.

### Authorisation and Authentication ###
The Data Portal passes the username and password sent by the user to each of the active ICAT API instances.  The ICAT API then uses these to retrieve a proxy from the MyProxy Server, which logs the user onto the ICAT API and sends the Data Portal the ICAT API’s user’s session id.  This then authenticates the user when they are searching the ICAT.  If a user logs on successfully to at least one ICAT API instance then they are logged onto the Data Portal.