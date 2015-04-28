# Record of actions from ICAT collaboration meetings up to meeting 74 #

Later meetings are recorded at http://icatproject.org/collaboration/communication/monthly-meetings/

**Contents**



## Meeting 74 - 29 May 2014 ##

Agenda

  * Actions from meeting 73 (Steve)
  * Steering Group (Tom or any other member)
  * ICAT development (Steve)
  * IDS development (Steve)
  * TopCAT development (Wayne)
  * Issues from ICAT production deployments (Any)
  * AOB (Any)

### Notes ###

Present: Kunal, Alistair, Wayne, Kevin, Tom, Milan, Steve

Apologies: Anton, Rolf, Shelly

Action 73.0 - Tom has sent a note around about DOIs and has already received one reply.

Action 73.1 - Kunal reported his Wildfly findings - primarily the need for a java:/ in front of a field in the persistence.xml.  Steve then reported that he had managed to install an ICAT with one authenticator but that testicat would not run properly because the published wsdl was a little different and meant that it was not possible to use SUDS to create a credential object - so logging in was not possible. Steve commented that in may respects Wildfly is more regular than Glassfish and that the support on the forum is excellent. Having demonstrated that there appear to be no major problems he will take this work further at some later date with the intention of updating the setup script to modify the glassfish version of the .war file and to deploy it on wildfly. Kunal has done some work preparing scripts but does not have the time currently. Steve has also tried TomEE+ but found that this was harder to get started with. Probably work on TomEE+ will be delayed for much longer as Wildfly appears to be a viable alternative to Glassfish. Tom asked for JDBC connector resilience with Wildfly to be checked.

Action 73.2 - Tom has sent around details of Balsamiq.

Action 72.5 - Tom is tidying up the rules and will then share them

Action 71.3 - Alistair said that he did not intend to carry out this action. Milan suggested that a round table at the forthcoming PanData workshop could address this. The workshop is before the next meeting so the action will be removed.

Tom reported on the ICAT Steering Group which had its first meeting last week.The main items of discussion were the ToR and the choice of chairman. The meeting has not yet chosen a chairman. TopCAT was also discussed. The consensus was that TopCAT would be needed but that two labs still needed to have their own bespoke interface as well.The work of the group will be public and the ToR (once finalised) and the minutes of the meetings will appear on icatproject.org.

Steve reported on ICAT development. The 4.3.3-SNAPSHOT was made available around the end of April for both server and client. Note that the updated documentation for the client is with the client as the server and client are now released separately. No feedback has been received yet. Work on producing a RESTful interface covering at least import and export has started. As a consequence of a chnage made to facilitiate deployment on Wildlfy icat will now be packaged as a .war file instead of a .ear file. This has fixed the problem reported earlier on deploying a RESTful interface side by side with a SOAP interface.

Steve also reported on the IDS development for which a snapshot is also available. Rolf has deployed in but found that had disabled the background data checking. Steve hopes that this can be restored but at a level where it does not have too big an impact. A change is being made to support file uploads from TopCAT using POST.

Wayne then reported on TopCAT. The remaining work preventing the next release is the file upload. The file can be uploaded but he is not able to see whether or not the operation was successful because of the same-origin policy enforced by the browser. He is lookign for a work-around. Next week he plans to use Balsamiq to mock up a new TopCAT interface with the hope that it can be shown at the forthcoming PanData workshop to attract feedback.

Tom reported that he had been working with Kevin to fix a problem when the Oracle RAC nodes go down and no failover takes place. The solution should appear in the installation scripts or at least be documented. Tom also reported that as a test he had tried deleting all the investigations for an instrument and that it took about a second for each investigation (which is the top of a tree of course) so he resorted to a direct operation on the database.

There were no AOB items.


### Actions ###

| Item | Description                                                                       | Assigned       |
|:-----|:----------------------------------------------------------------------------------|:---------------|
| 74.0 | Look into JDBC connector resilience with Wildfly                                  | Steve    |
| 74.1 | Document or implement in setup script the JDBC connector fix for Glassfish/Oracle | Kevin or Steve |
| 72.5 | Add authz rules to contrib directory                                              | Tom      |
| 61.1 | Put the simple ICAT browser into contrib when it is ready                         | Tom      |
| 60.3 | Finalise a release of IJP and send a note to the mailing list                     | Steve    |

## Meeting 73 - 24 April 2014 ##

Agenda

  * Actions from meeting 72 (Steve)
  * ICAT development (Steve)
  * TopCAT development (Wayne)
  * Alternatives to Glassfish (Any)
  * Issuing DOIs for ICAT (Tom)
  * ICAT roadmap update (Steve)
  * Issues from ICAT production deployments (Any)
  * AOB (Any)

### Notes 73 ###
Present: Steve, Wayne, Kunal, Tom, Shelly

Apologies: Milan, Anton, Rolf

The meeting startup was delayed by about 10 minutes because of mistakes in setting up a laptop at RAL.

Action 72.0 - Circulate likely time-scales for the items on the current road map is no longer relevant as a new roadmap - with dates - will be discussed in this meeting - Closed

Action 72.1 - Kunal had organised a Hangout familiarisation meeting last week - Closed

Action 72.2 - The IDS plugin interface was discussed in Dub with SSIlin and the views of Christophe accepted - Closed

Action 72.3 - Tom explained that Alistair had had some misconfiguration - Closed

Action 72.4 - Fixed in 4.3.3 - Closed

Steve reported on the 4.3.3 SNAPSHOTs of the server and client. Shelly asked about the need to use Glassfish 4. Steve explained that Glassfish 4 was used for testing and that it allowed the deployment order of components to be defined but that version 3 of Glassfish should still work.

Wayne reported that the next TopCAT release was delayed by about a month. Various bug fixes had been made but the upload facility was not yet complete.

Kunal has started looking at WildFly as an alternative container. He will report next month.

Tom explained his ideas about issuing DOIs for the ICAT software. He will put this into a posting in order to get feedback on the desired granularity - Action

Steve explained the updated roadmap. A couple of small changes were suggested and having reassured Tom that nothing had been removed besides giving consideration to integration of ICE into TopCAT and the construction of a federation service there was agreement that the updated roadmap - with the two extra changes from the meeting should replace the existing roadmap, but that the existing one be preserved on the web site to show progress. Changes to multi-ICAT searching were discussed, including providing mockups of the proposed designs.

Tom reported that Kevin had been updating their ICAT 4.3.2 to use Glassfish 4. He had had some problems with certificates.

Shelly asked about the date of the next meeting. It is normally held on the last Thursday of the month - so in this case the 29th May.

### Actions 73 ###

| Item | Description                                                                     | Assigned |
|:-----|:--------------------------------------------------------------------------------|:---------|
| 73.0 | Send round a proposal for ICAT DOIs                                             | Tom      |
| 73.1 | Report on issues porting to WildFly                                            | Kunal    |
| 73.2 | Send around details of Balsamiq mockups                                         | Tom      |
| 72.5 | Add authz rules to contrib directory                                            | Tom      |
| 71.3 | Invite WP4 of Pandata to provide feedback on their experience of ICAT products  | Alistair |
| 61.1 | Put the simple ICAT browser into contrib when it is ready                       | Tom      |
| 60.3 | Finalise a release of IJP and send a note to the mailing list                   | Steve    |


## Meeting 72 - 27 February 2014 ##

Agenda

  * Actions from meeting 71 (Steve)
  * Meeting technology (Kunal)
  * Meeting in Dublin in March (Alistair)
  * ICAT roadmap (Steve)
  * ICAT development (Steve)
  * TopCAT development (Wayne)
  * Coordination of IDS plugins (Andy/Christophe)
  * Release and SV for Pandata (Alistair)
  * Issues from ICAT production deployments (Any)
  * AOB (Any)


### Notes 72 ###
Present: Milan, Shelly, Ivan, Alistair, Christophe, Anton, Kunal, Tom, Wayne and Steve

Apologies: Rolf

Action 71.0 - there has not been much progress yet with the preparation of the Dublin meeting besides essentials such as a room to meet in. This will be rectified over the next few weeks. It will not appear on the actions list as there will be no further meeting before Dublin. Tom volunteered Jay to talk about integration of ICAT and Mantid and also to talk about his experience (as someone new to ICAT) with the ICAT API.

Action 71.1 - this has now been completed and the first version finalised. Steve will circulate a note with probable time scales for the items on the road map. The time scales may become part of the map when it is next revised.

Action 71.2 - a request has been sent in to the icat-support list Wayne is in discussion with Milan about it. This will be closed.

Action 70.6 - PanData release 003 has exhibited no problems. This action will be closed.

Action 70.9 - Kunal has been investigation alternative meeting technologies. He has tried a meeting using Google Hangouts with Shelley and Milan and all were favourably impressed. It was noted that it did take a little while to get used to but the functionality was excellent. Kunal will register people as required and will set up a test meeting for anyone who wants to familiarise themselves with the technology so that the next ICAT collaboration meeting progresses smoothly. This will be in a around two weeks time.

Agenda items except where covered by actions:

Steve reported on ICAT progress and the problems he has been having trying to add a servlet to the icat ear file. He will work around the problem for now with a SOAP interface for the lucene communication.

Wayne reported that he had been discussing the behaviour of TopCAT with Milan. The next release of TopCAT will include data upload.

Christophe explained that the IDS plugin mechanism did not give him the flexibility he needed. He will send a note to Steve explaining what he needs.

The PanData SV has shown up a problem accessing the ISIS ICAT. This may be a firewall problem somewhere as it used to work. Alistair will follow up on any possible firewall issues. In about 3 week's time the SV will concentrate on Umbrella.

Tom asked if anybody had seen any odd behaviour with case sensitivity of searches.

Tom will submit his authz rules to the contrib directory.

Alistair has discovered proxyvole to search for and use existing proxy configurations. This will be used in the next SV.

Christophe asked how to record which instrument a dataset is related to now that we have a many to many relation between investigation and instrument. Besides using a DatasetParameter no other suggestion was offered.

### Actions 72 ###

| Item | Description                                                                     | Assigned      | Status            |
|:-----|:--------------------------------------------------------------------------------|:--------------|:------------------|
| 72.0 | Circulate likely time-scales for the items on the current road map              | Steve         | New               |
| 72.1 | Organise a Hangout familiarisation meeting                                      | Kunal         | New               |
| 72.2 | Send note to Steve about the shortcomings of the IDS plugin interface           | Christophe    | New               |
| 72.3 | Understand why SV's fail when ISIS ICAT is contacted                            | Alistair      | New               |
| 72.4 | Raise an issue on case sensitivity of searches                                  | Tom           | New               |
| 72.5 | Add authz rules to contrib directory                                            | Tom           | New               |
| 71.3 | Invite WP4 of Pandata to provide feedback on their experience of ICAT products  | Alistair      | Pending           |
| 61.1 | Put the simple ICAT browser into contrib when it is ready                       | Tom           | Pending           |
| 60.3 | Finalise a release of IJP and send a note to the mailing list                   | Steve         | Pending           |

## Meeting 71 - 31 January 2014 ##

  * Actions from meeting 70 (Steve)
  * Meeting technology (Alistair)
  * Meeting in Dublin in March (Alistair)
  * ICAT roadmap (Steve)
  * Status of recent releases (Steve)
  * Coordination of IDS plugins (Andy/Christophe)
  * Release and SV for Pandata (Alistair)
  * Issues from ICAT production deployments (Any)
  * AOB (Any)

### Notes 71 ###
Present: Rolf, Shelley, Milan, Alistair, Kunal and Steve

Apologies: Tom

Kunal had looked at a number of choices including GoToMeeting and Teamviewer. Evo was also suggested as another possibility. He will try out one or more products with Milan and Shelley and report back.

Alastair explained that the current Dublin meeting agenda was currently a clone of that for the the last meeting. Steve requested that any site reports should highlight any problems.

Steve went through the draft Road Map. A lot of useful ideas were put forward which will be reflected in the map:
  * Documentation improvements are needed for search and authorization (doing it efficiently)
  * Rolf has data export/import for his python code but it is quite slow. He uses yaml syntax with ids replaced by the uniqueness constraint fields which is similar to what Steve had in mind.
  * It was suggested again that it would be good to isolate the federation part of Topcat. This might be a service in its own right.
  * There was a suggestion that ICE should be incorporated into TopCAT.

PaNData service verification will start tomorrow.

Alistair said that he planned to produce a report on ICAT from PaNData. Steve asked him to make sure that problems were promptly reported in the usual way (the appropriate googlecode issue list) so that any issues could be addressed as soon as possible.

Milan had found a problem with TopCAT for which he will submit a bug report

### Actions 71 ###

| Item | Description                                                                     | Assigned      | Status            |
|:-----|:--------------------------------------------------------------------------------|:--------------|:------------------|
| 71.0 | Organise the Dublin meeting                                                     | Alistair      | In progress       |
| 71.1 | Update road map with ideas from meeting                                         | Steve         | New               |
| 71.2 | Submit TopCAT bug report                                                        | Milan         | New               |
| 71.3 | Invite WP4 of Pandata to provide feedback on their experience of ICAT products  | Alistair      | New               |
| 70.6 | Test a collection of ICAT parts as Pandata release 003                          | Alistair      | In progress       |
| 70.9 | Investigate alternative meeting technology                                      | Kunal         | In progress       |
| 61.1 | Put the simple ICAT browser into contrib when it is ready                       | Tom           | Pending           |
| 60.3 | Finalise a release of IJP and send a note to the mailing list                   | Steve         | Pending           |

## Meeting 70 - 12 December 2013 ##

  * Actions from meeting 69 (Alistair)
  * Status of ICAT 4.3 and related parts such as authn (Steve)
  * Status of IDS (Steve)
  * Status of TopCAT (Wayne)
  * Training and SV for Panadata (Alistair)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 70 ###

Present: Steve, Wayne, Alistair, Shelly, Tom

The actions were reviewed and their stati updated/

The face to face meeting for 2014 has been set for 24-25 March.  The venue is the Department of Computer Science at Trinity College, Dublin.

There will be releases of ICAT, Topcat, Authn\_shib and IDS around the end of 2013, or the beginning of 2014.

A collection of ICAT parts will be tested as a group and adopted by Pandata as part of the Pandata software stack release in January 2014.

There is a need to investigate the use of ICATs sharing a single lucene index.

There was discussion about the ICAT collaboration meetings.  It was agreed to reduce the frequency of meetings from fortnightly to monthly.  It was agreed to investigate the use of alternative technology for the meetings.  The present arrangements with voice only connections in a telephone conference does not support visual means of communication.

### Actions 70 ###

| Item | Description                                                                     | Res           | Status                    |
|:-----|:--------------------------------------------------------------------------------|:--------------|:--------------------------|
| 70.0 | Document the meeting and update the actions                                     | AM            | Done                      |
| 70.1 | See Juan about the ICAT meeting in Dublin                                       | TG            | Done                      |
| 70.2 | Close actions 66.04 and 64.02                                                   | AM            | Done                      |
| 70.3 | Release ICAT 4.3.1                                                              | SF            | Done                      |
| 70.4 | Release update to the SNAPSHOT IDS and the python api                           | SF            | Done                      |
| 70.5 | Release Topcat 1.11                                                             | SF            | Done                      |
| 70.6 | Test a collection of ICAT parts as Pandata release 003                          | AM            | In progress                      |
| 70.7 | Investigate 2 icats sharing a lucene index                                      | SF            | To be considered as part of the Road Map|
| 70.8 | Release the shibboleth authenticator for use by Umbrella                        | SF            | Done                      |
| 70.9 | Schedule a set of meetings for ICAT in 2014 and investigate alternative technology | Kunal         | In progress - transferred to Kunal |
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |                  .|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |                  .|

## Meeting 69 - 31 October 2013 ##

  * Actions from meeting 68 (Alistair)
  * Status of ICAT 4.3 (Steve)
  * Status of IDS (Steve)
  * Status of TopCAT (Steve)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 69 ###

Present: Steve, Frazer, Alistair, Kunal, Milan

Apologies: Shelly, Jamie, Christophe, Tom

Steve reported that he had talked to Bill and Stefan in ISIS about Umbrella authentication.  He also reported that he had exchanged emails with Bjorn at PSI.  He said that he was making progress, but it was not possible to say when it would work, as completion is dependent on the collaboration of the group.

Steve reported that ICAT 4.3.0 was now available and he had only on reported fault.

Steve reported that he was now working on IDS, and that he was confident that the IDS release will be ready in November 2013.

Steve reported that Wayne is making good progress with Topcat.  It is expected that Topcat 2.0 will be available in November 2013 and include support for IDS.

Frazer reported that ISIS now has ICAT 4.3 available with Topcat 1.9.

Steve reported that ISIS now has ICAT 4.3 available with Topcat 1.9.

Milan reported that SV6 is due on 1 November and that the tests are running correctly at Elettra.

Alistair reported that the materials for the training course on in the installation of the Pandata software stack are almost ready and that the course will take place next week.

Alistair reported that good progress was being made with the ingester for ESRF and that ingestion into the production ICAT at ESRF will start during November.

(Ed's note - Jamie sent a report that ILL has recruited an additional engineer to work on the ILL catalogue.  The engineer will start work in November).

### Actions 69 ###

| Item  | Description                                                                     | Res           | Status            |
|:------|:--------------------------------------------------------------------------------|:--------------|:------------------|
| 69.00 | Document the meeting and update the actions                                     | AM            | Done              |
| 69.01 | Make use of the new ISIS ICAT in SV6 on 1 November                              | AM            | Done              |
| 69.02 | Make use of the new DLS ICAT in SV6 on 1 November                               | AM            | Done              |
| 69.03 | Discuss that training course for Pandata and make changes to the materials      | AM/SF         | Done              |
| 66.01 | Fix the dates of the ICAT and Pandata meetings in Spring 2014                   | TG/AM         | Done              |
| 66.02 | Send a note to TG about Nxingest                                                | AM            | Done              |
| 66.04 | Discuss with Erica Yang issues of mutual concern                                | Shelly        | Done              |
| 64.02 | Schedule a web cast on the contents of ICAT 4.3                                 | Steve         | Cancelled         |
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |                  .|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |                  .|



## Meeting 68 - 17 October 2013 ##

  * Actions from meeting 67 (Alistair)
  * Status of ICAT 4.3 (Steve)
  * Status of IDS (Steve)
  * Status of TopCAT (Wayne)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 68 ###

Present: Steve, Tom, Wayne, Frazer, Alistair, Kunal

Tom reported that the date of the Spring meeting was still being discussed.

As discussed at the previous meeting actions 61.01 and 60.03 have been dropped - they will be done at some stage.

There had been a lot of useful feedback on the ICAT 4.3.0 snapshots resulting in a number of improvements. The only remaining issue was the inability to install it on Glassfish 4.

There has been some progress with the IDS but not much as Steve has been working mostly on ICAT. He plans to make some changes to the API to make it more convenient to use.

Wayne reported that there had been a review of TopCAT issues out of which the items that should be fixed for the next release were identified. He is working on interfacing to ICAT 4.3.0 but does not have one installed. Tom suggested he should use the ISIS one. Tom will also check to see if he has anything else to add to the issue list.

### Actions 68 ###

| Item  | Description                                                                     | Res           | Status   |
|:------|:--------------------------------------------------------------------------------|:--------------|:---------|
| 68.00 | Document the meeting and update the actions                                     | AM            | Done     |
| 68.01 | Check that the list of requirements for Topcat                                  | TG            | Done     |
| 68.02 | Check that the topcat43 plugins works with ICAT 4.3.0                           | WC            | Done     |
| 68.03 | Get a credential from ISIS to access the ISIS Topcat                            | WC            | Done     |
| 68.04 | Talk to Bill and Stefan about Umbrella authentication plugin for ICAT           | SMF           | Done     |
| 66.01 | Fix the dates of the ICAT and Pandata meetings in Spring 2014                   | TG/AM         |         .|
| 66.02 | Send a note to TG about Nxingest                                                | AM            | Done     |
| 66.04 | Discuss with Erica Yang issues of mutual concern                                | Shelly        |         .|
| 64.02 | Schedule a web cast on the contents of ICAT 4.3                                 | Steve         |         .|



## Meeting 67 - 3 October 2013 ##

  * Actions from meeting 66 (Steve)
  * Status of ICAT 4.3 (Steve)
  * Status of IDS (Steve)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 67 ###

Present: Milan, George, Shelly, Steve, Tom, Wayne and Frazer

It was noted that the date of the ICAT meeting in Spring next year is not yet fixed. It should be done soon.

It was suggested that actions 61.01 and 60.03 should be removed as they will be done when they are ready and are not urgent.

Tom reported on his work testing the ICAT4.3.0-SNAPSHOTs. There were still some problems to be addressed with multiple rules. He suggested
that the installation script might check for the EJBTimer. This is present when Glassfish is installed but might have been deleted. He had been unable to use the installation scripts on Windows.

### Actions 67 ###

| Item  | Description                                                                     | Res           |      Status|
|:------|:--------------------------------------------------------------------------------|:--------------|:-----------|
| 67.00 | Document the meeting and update the actions                                     | SMF           |        Done|
| 66.01 | Fix the dates of the ICAT and Pandata meetings in Spring 2014                   | TG/AM         |           .|
| 66.02 | Send a note to TG about Nxingest                                                | AM            |           .|
| 66.04 | Discuss with Erica Yang issues of mutual concern                                | Shelly        |           .|
| 65.02 | Provide feedback on ICAT 4.3 and its related parts                              | All           |        Done|
| 64.02 | Schedule a web cast on the contents of ICAT 4.3                                 | Steve         |           .|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |        Drop|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |        Drop|


## Meeting 66 - 19 September 2013 ##

  * Actions from the meeting 65 (Alistair)
  * Status of ICAT 4.3 (Steve)
  * Update from Pandata (Alistair)
  * Status of IDS (Steve)
  * AOB (All)

### Notes 66 ###

Present: Alistair (STFC/chair), Frazer (ISIS), Tom (ISIS), George (Elettra), Milan (Elettra), Julien (ESRF), Shelly (SNS) and Steve (STFC)

Apologies: Jamie (ILL)

The date of the ICAT face to face meeting in 2014 was discussed.  The final Pandata meeting is scheduled for 29 March 2014 in Dublin to meet in the same week as the RDA meeting in Dublin.  The last three face to face meetings in 2011 (Paris), 2012 (Grenoble) and 2013 (Lund) were schedule to meet in the same week as a Pandata meeting.  It was agreed that we would defer a decision on the date until more information is available about a successor project to Pandata.  It is likely that ICAT-2014 will be in May 2014.  We will try to settle the data by December 2013.

Tom reported that a bug in NxIngest had been detected and corrected.

Steve reported that the snapshots for ICAT 4.3 and related components are available and he would like more feedback.

Steve reported that progress on IDS 1.0 was good, and that it should be available in October/November.

Tom reported that Mantid now supports ICAT 4.2.

Shelly reported that SNS is now investigating the use of ICAT with autoreduction.

Alistair reported that he is arranging a two day training course for technical people in Pandata.  The training will include the installation, deployment and operations of the latest ICAT materials including their integration with Apache.  The training will also cover Topcat, IDS, Umbrella.

The meeting concluded at 15h30.


### Actions 66 ###

| Item  | Description                                                                     | Res           |      Status|
|:------|:--------------------------------------------------------------------------------|:--------------|:-----------|
| 66.00 | Document the meeting and update the actions                                     | AM            |        Done|
| 66.01 | Fix the dates of the ICAT and Pandata meetings in Spring 2014                   | TG/AM         |           .|
| 66.02 | Send a note to TG about Nxingest                                                | AM            |           .|
| 66.03 | Send a note to the icat collaboration about the training for Pandata            | AM            | https://code.google.com/p/pandata/wiki/TrainingSwInstallation |
| 66.04 | Discuss autoreduction with Erica Yang and Shelly Ren                            | AM            |           .|
| 65.02 | Provide feedback on ICAT 4.3 and its related parts                              | All           |    On-going|
| 64.02 | Schedule a web cast on the contents of ICAT 4.3 for September                   | Steve         |           .|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |           .|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |           .|


## Meeting 65 - 5 September 2013 ##

  * Actions from the meeting 64 (Alistair)
  * Status of ICAT 4.3 (Steve)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 65 ###

Present: Alistair (STFC/chair), Kevin (STFC), Tom (ISIS), Idrissou (Soleil) and Steve (STFC)

There has been an offer from ISIS to host the face to face meeting in March 2014.  It was agreed to accept this offer and the chairman thanked Tom for the offer.  The meeting is likely to be held in collaboration with the final meeting of Pandata.

Steve reported that progress on the collection of parts associated with release of ICAT 4.3 was going well.  There are a small number of interdependencies.  For example ICAT 4.3 requires a new version of Icat-setup.  The authn\_db has also changed.  There was a discussion of detection of compatibilities.  It was agreed to review these, following experience of using the new versions of the parts.

There were no issues with any production deployment.  However Tom reported that he was very keen to move to ICAT 4.3 as it solves some of his difficulties.

There was a question about the status of IDS.  Steve reported the technical work was going well, and that a version for comment would be available in October.  Tom asked that this version be available soon, even if some advanced features for remote file systems are not supported.

The meeting concluded at 15h30.

### Actions 65 ###

| Item  | Description                                                                     | Res           |      Status|
|:------|:--------------------------------------------------------------------------------|:--------------|:-----------|
| 65.00 | Document the meeting and update the actions                                     | AM            |        Done|
| 65.01 | Discuss the arrangements for ICAT 2014 with Pandata                             | TG/AM         |        Done|
| 65.02 | Provide feedback on ICAT 4.3 and its related parts                              | All           |    On-going|
| 65.03 | Meet again on 19 September                                                      | All           |        Done|
| 64.02 | Schedule a web cast on the contents of ICAT 4.3 for September                   | Steve         |           .|
| 64.03 | Make snapshots of ICAT 4.3.rc available for comment in August                   | Steve         |        Done|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |           .|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |           .|

## Meeting 64 - 8 Aug 2013 ##

  * Actions from the meeting 63 (Alistair)
  * Status of ICAT 4.3 (Steve)
  * ICAT F2F Meeting (Steve and Alistair)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 64 ###

Present: Alistair (STFC/chair), Kevin (STFC), Tom (ISIS), Ivan (CCFE) and Steve (STFC)

As it is August, the meeting was quiet.

Ivan from CCFE joined the conference for the first time.  CCFE is the Centre for Fusion Energy.  We welcome CCFE to the collaboration.

The Pandata meeting in Barcelona in September was discussed.  It was noted that there would NOT be a meeting about ICAT.  However Steve offered to host a web cast session in September where he would discuss the contents of ICAT 4.3.

It was agreed to invite members of the collaboration to offer to host a face to face meeting of ICAT in March 2014.

Steve discussed the status of ICAT 4.3 and reported that progress was good.  He will make a snapshot of the software and the documentation available to interested parties later in August.  Following that, he will release ICAT 4.3.  During September, he will host a webcast session to discuss ICAT 4.3.

There are no known problems with the production deployments.

Ivan reported that he was making progress with ICAT.  At the moment he is investigating how to integrate ICAT with other meta data systems at CCFE.

Steve reported that he intends to move www.icatproject.org to a new host during August.  The new host will be located within STFC and have a new IP address.  This change should not affect anyone using the resources such as the maven repository on www.icatproject.org.

The meeting concluded at 15h30.

### Actions 64 ###

| Item  | Description                                                                     | Res           |  Status|
|:------|:--------------------------------------------------------------------------------|:--------------|:-------|
| 64.00 | Document the meeting and update the actions                                     | Alistair      |    Done|
| 64.01 | Invite invitations to host the ICAT collaboration meeting in March '14          | Alistair      |    Done|
| 64.02 | Schedule a web cast on the contents of ICAT 4.3 for September                   | Steve         |       .|
| 64.03 | Make snapshots of ICAT 4.3.rc available for comment in August                   | Steve         |       .|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |       .|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |       .|



## Meeting 63 - 25 July 2013 ##

  * Actions from the meeting 62 (Chair)
  * Status of ICAT 4.3 (Steve)
  * Feedback on new components (authn\_simple, topcat 1.9, ice and icat-setup (All)
  * Feedback on changes to query language, authz rules etc (All)
  * Feedback on guidelines for developers (All)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 63 ###

Present: Juergen, Milan, George, Shelley, Tom, Brian, Kevin and Steve (Chair)

There was no progress on any of the actions.

Steve: ICAT 4.3 is still progressing well. There is a problem in the MySQL migration script. It is hoped that when this is converted to Oracle the error may become apparent. It currently looks like a MySQL bug. Besides this the only work left to do is the new query language and related authz changes. Old style queries are being parsed and converted into the new JPQL style. They will then be subject to the new authorization code. This means that because the authz code is changing the data that comes back from INCLUDE may change in 4.3. Some modifications to authz rules may be needed to restore the old behaviour if so desired.

Tom: A couple of feature requests for TopCAT have been submitted, ice and icat-setup seem to work as advertised.

Steve: Feedback on the query language changes had been received saying that we should keep the old language for an extended period. This is to avoid forcing people to change and because its simplicity is appreciated. There seems to be no problem with this. Following a question from Tom, it was explained that authz rules would be applied to included entities but that a mechanism would be provided to say that it was always allowed to go from one specific entity type to another: for example from Dataset to Datafile.

The developer guidelines were discussed briefly. Antony had requested by e-mail the inclusion of something about logging. Tom questioned the need to provide a python install script for the trivial installations, but agreed that, if it were provided, then it should be in python and follow the guidelines. Tom would also like to see some documentation on what the scripts were doing. He was happy to have a verbose option to provide this.

AOB: Earlier in the meeting Shelley had asked about the IDS - Steve emphasised that though it had been made available to parts of PanData it was not yet a supported ICAT component. Juergen expressed an interest in using OpenId with ICAT however he has no spare effort at the moment to do anything about it. Brian asked what was being done about organising the ICAT part of the Barcelona meeting. Steve suggested that it should have two parts: one relating to PanData and a more general part. An action has been added for Steve and Alistair.

### Actions 63 ###

| Item  | Description                                                                     | Res           |             Status|
|:------|:--------------------------------------------------------------------------------|:--------------|:------------------|
| 63.00 | Document the meeting and update the actions                                     | Steve         |               Done|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom           |                  .|
| 61.02 | Write a note about glassfish configuration to reduce https overhead             | Steve         |               Done|
| 61.03 | Write a note about GF 4 and AJP                                                 | Alistair      |ApacheAndGlassfish |
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve         |                  .|
| 63.01 | Plan the ICAT meeting in Barcelona                                              | Steve/Alistair|               Done|

## Meeting 62 - 11 July 2013 ##

  * Actions from the meeting 61 (Chair)
  * Status of ICAT 4.3 (Steve)
  * New components (Steve)
  * Feedback on Topcat 1.9 Snapshot (All)
  * Issues from ICAT production deployments (All)
  * AOB (All)

### Notes 62 ###

Present: Daniel Salvat, Pablo Garcia, David Lopez, George Kourousias, Tom Griffin and Steve Fisher (Chair)

SMF: ICAT4.3 now needs two more things: schema changes with associated scripts and enhanced query language. End of August still looks possible.

Rolf Krahl's simple authenticator has been released and at some point a class he has written to handle hashed passwords will also be used by the db authenticator.

A SNAPSHOT of TopCAT 1.9 has been made available - no feedback has been received so far.

Releases of ice (an ICAT Editor and icat-setup for editing authz related tables) are imminent. Kevin will test them both before release.

TG: ISIS is still using ICAT3.3 and is waiting for ICAT4.3 with the enhanced query language to support more specific rules.

GK: Umbrella will be part of the next PaNData verifications so will probably need an umbrella authenticator.

DS: Reminder about the Barcelona workshop which is expected to have two satellite meetings: one on icat and and one on umbrella.

### Actions 62 ###

| Item  | Description                                                                     | Res       |           Status|
|:------|:--------------------------------------------------------------------------------|:----------|:----------------|
| 62.00 | Document the meeting and update the actions                                     | Steve     |             Done|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | Tom       |                .|
| 61.02 | Write a note about glassfish configuration to reduce https overhead             | Steve     |                .|
| 61.03 | Write a note about GF 4 and AJP                                                 | Alistair  |             Done|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | Steve     |                .|

## Meeting 61 - 13 June 2013 ##

  * Actions from the meeting 60 (Alistair)
  * Release conditions for products (Alistair)
  * Status of contribs  (Alistair)
  * AOB (Any)

### Notes 61 ###

We were pleased to have Milan join us again.  He has been away from work for a month following an operation on his wrist.

Nicola informed us that he is going to leave ESRF in June to work in a commercial organisation in Zurich.  The chairman thanked Nicola for his work with ICAT and wished him well in his new position.

There was discussion about the appropriateness of the release conditions which AM had added to the wiki.  It was agreed that this would be discussed again, for example when ICAT 4.3 is close to release.

SF reported that progress with ICAT 4.3 is good; that free text search is working and is very efficient; that consolidation of code is reducing the volume of code, and making things simpler in the longer term.

TG reported that he is working on a simple browser for ICAT.  He will put it into contrib when it is ready.

SF reported that he has done some timing tests on the download component of the IDS.  This has led to changes in the implementation which ensures that the cypher in SSL mode is set dynamically and this generally ensure less time for the cypher.

AM reported that GF 4 has been released and that he has deployed ICAT, Authdb and Topcat on a GF 4 and that they appear to work correctly.  There was a problem trying to deploy the download component of IDS; as this is not yet a supported product, this is a matter for development.

AM reported that he had deployed ICAT with GF4 with an Apache front-end.  This work has been done at ESRF and will be used for the ESRF production ICAT.  The GF4 and the Apache communicate with one another using AJP.  There was a bug in GF 3 which meant that this did not work when using https, due to an error in the GF which continued to use http, when it should have used https.  The bug has been fixed in GF 4.  To verify that the bug was due to GF3, having got ICAT to work with GF4, AM then tried changing back to GF3, and the ICAT did not work.

### Actions 61 ###

| Item  | Description                                                                     | Res   |           Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:------|:----------------|:---------------------|
| 61.00 | Document the meeting and update the actions                                     | AM    |             Done|                     -|
| 61.01 | Put the simple ICAT browser into contrib when it is ready                       | TG    |                .|                     -|
| 61.02 | Write a note about the performance on the download component of IDS             | SF    |                .|                     -|
| 61.03 | Write a note about GF 4 and AJP                                                 | AM    |             Done|                     -|

### Attendance 61 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SMF  |Steve Fisher                 | STFC SCD                   |     +        |           |
|NB   |Nicola Bessone               | ESRF                       |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|JH   |Jamie Hall                   | ILL                        |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |              |     +     |
|IC   |Idrissou Chado               | Soleil                     |              |     +     |

## Meeting 60 - 30 May 2013 ##

  * Actions from the meeting 59 (Alistair)
  * Roadmap for ICAT 4.3 (Alistair)
  * Release conditions for ICAT 4.3 (Alistair)
  * Status of IJP (Steve)
  * Status of IDS (Alistair)
  * Status of Topcat (Alistair)
  * AOB (Any)

### Notes 60 ###

We were pleased to have Milan join us again.  He has been away from work for a month following an operation on his wrist.

Ghita informed us that she is going to leave Diamond in June to take up an academic position teaching Computer Science in a University.  The chairman thanked Ghita for her work with ICAT and wished her well in her new position.

Steve said that work on ICAT 4.3 was progressing well, with the development of Notifications and Logging completed.  He will deal with Free text next.  He said that when he completes the development of a feature, he changes the status of the corresponding ticket to started.  The ticket will be closed when the feature is included in a release.  If further work is required following release, a new ticket will be created.

Alistair reported that he had written notes about the release conditions for ICAT and IDS.  He will send an email to the mailing list inviting comments.

Steve reported that the IJP is now ready for deployment in LSF at STFC.  A paper has been written and will be presented at a conference in Zurich new week.  He agreed to finalise a release of this product and to make an announcement.

Steve reported that as Antony has left the project, we are short of resources for the development and support of Topcat.  We would very much appreciate it, if one of our partners were to volunteer to support or develop Topcat.

Alistair reported that as Frazer has left the project, we have recruited a student who will join for three months from the beginning of September.  His main task will be to prepare the IDS for release.  We are expecting to have Frazer working with the project team from September again.  This will provide continuity in the work with IDS.

Alistair and Steve reported that STFC is going to recruit at least one contractor for several months to support the ICAT related work until a permanent replacement can be found for Antony.

The meeting finished at 15:25 BST.

### Actions 60 ###

| Item  | Description                                                                     | Res   |           Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:------|:----------------|:---------------------|
| 60.00 | Document the meeting and update the actions                                     | AM    |             Done|                     -|
| 60.01 | Add a page to the wiki about ICAT 4.3 and IDS 1.0 release conditions            | AM    |             Done|                     -|
| 60.02 | Send a note to the mailing list about the notes on release conditions           | AM    |             Done|                     -|
| 60.03 | Finalise a release of IJP and send a note to the mailing list                   | SF    |                .|                     -|
| 60.04 | Consider offering help to support or develop Topcat                             | All   |                .|                     -|
| 60.05 | Make changes to the room booking at RAL for the ICAT meetings                   | AM    |             Done|                     -|


### Attendance 60 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SMF  |Steve Fisher                 | STFC SCD                   |     +        |           |
|PG   |Pablo Garcia                 | Alba                       |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|NB   |Nicola Bessone               | ESRF                       |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George K                     | ELETTRA                    |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |              |     +     |
|TG   |Tom Griffin                  | STFC ISIS Facility         |              |     +     |
|IC   |Idrissou Chado               | Soleil                     |              |     +     |

## Meeting 59 - 16 May 2013 ##

  * Actions from the meetings 58 (Alistair)
  * Status of ICAT 4.3 (Steve)
  * Status of anonymous authenticator (Steve)
  * Status of IDS (Antony)
  * AOB (Any)

### Notes 59 ###

Steve reported that Antony has now left the collaboration as he has a new job within STFC SCD.  Alistair was asked to make an announcement about the availability of the technology preview of the IDS.  Alistair reported that the downloader part of the IDS had performed well during the service verification on 10 May 2013.

(Steve and Alistair reviewed the status of the materials for the IDS on 22 May 2013 and decided that the IDS is not ready for a technology preview.  There will be a student working on this from July, and we expect to have it ready for preview by September).

Steve reported that the anonymous authenticator is now available.  Alistair reported that he has successfully deployed it on one of the test icats for Pandata.

Kevin reported that he has deployed ICAT 4.2.5 in production for CLF at RAL.  This appears to have cured the problem of memory leak and improved the reliability of ICAT.

Kevin and Tom both reported that adding additional indexes to some of the tables in the database has made a large difference to the time required for queries.  The response time is much lower, and it is much more consistent.

There was discussion of how to implement index generation in ICAT 4.3.  There is not a standard way in SQL to tell a database to create indexes.  Furthermore, the tables which requires the indexes can vary from one installation to another.  There is a way to tell EclipseLink to generate indexes in tables, but this does not work if a different persistence provider is used.  It was agreed to provide documentation on index generation.

Steve reported that adding the extensions to the query language for ICAT to support the changes to the schema to be introduced in icat 4.3 is rather complicated and would delay the availability of ICAT 4.3.  It was agreed that Steve should take the time to get it correct, and if it delays ICAT 4.3 until after the summer recess, this would be satisfactory.  (There is a requirement of Pandata that the changes to the schema of ICAT are required by October 2013 - Ed).

### Actions 59 ###

| Item  | Description                                                                     | Res   |           Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:------|:----------------|:---------------------|
| 59.00 | Document the meeting and update the actions                                     | AM    |             Done|                     -|
| 58.01 | Make an announcement about the technology preview of the IDS                    | AM    | Will not be done| The IDS is not ready |

### Attendance 59 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SMF  |Steve Fisher                 | STFC SCD                   |     +        |           |
|DG   |David Garcia                 | Alba                       |     +        |           |
|DG   |Pablo                        | Alba                       |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|NB   |Nicola Bessone               | ESRF                       |     +        |           |
|IC   |Idrissou Chado               | Soleil                     |     +        |           |
|JH   |Jamie Hall                   | IIL                        |     +        |           |

## Meeting 58 - 2 May 2013 ##

  * Actions from the meetings 57 (SMF)
  * Status of ICAT 4.3 (SMF)
  * Status of SV4 of Pandata (AM)
  * Status of CCFE (AM)
  * AOB (Any)

### Notes 58 ###

The meeting discussed actions and updated the status of actions.

SMF stated that a 4.2.5 version of ICAT would be released to fix a thread/memory leak related to the initialisation of log4j. He was reminded that there may be a problem with the frequency of access to the sequence table and that this might also go into 4.2.5.

SMF stated that ICAT 4.3.0 would include the changes required by ISIS for a more powerful query language supporting more complex authz rules.

In AM's absence GK reported on PaNdata SV4 which will start next week and be based on "Big V".

AOB:

SR reported that she was working with APS on a new pilot project

SMF asked if there was any interest in a "File Authenticator". There wasn't so it will not happen. The question then arose can we find out what authenticators are present and what credentials they require. TopCAT can manage without but at the expense of having to configure a plugin correctly. A new requirement will be added.

AW reported that there was good progress on Noris's admin interface to TopCAT.

It was reported that the Chinese Neutron Source have successfully deployed an ICAT and TopCAT.

Finally GKM reported that "Actors" had been added to DAWN.

### Actions 58 ###

| Item  | Description                                                                     | Res   |       Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:------|:------------|:---------------------|
| 57.02 | Prepare a distribution of a technology preview of the Download Manager          | AW    |         Done|                     -|
| 58.00 | Document the meeting and update the actions                                     | SMF   |         Done|                     -|
| 58.01 | Produce 4.2.5 release of ICAT                                                   | SMF   |         Done|                    - |
| 58.02 | Add a requirement to be able to discover what authenticators are present and what credentials they require.       | SMF        |         Done| [Issue 122](https://code.google.com/p/icatproject/issues/detail?id=122) |


### Attendance 58 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |              |       +   |
|SMF  |Steve Fisher                 | STFC SCD                   |     +        |           |
|DG   |David Garcia                 | Alba                       |              |       +   |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|NB   |Nicola Bessone               | ESRF                       |     +        |           |
|IC   |Idrissou Chado               | Soleil                     |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |



## Meeting 57 - 18 April 2013 ##

  * Actions from the meetings 56 (Alistair)
  * Icat Data Server (IDS) development (Antony)
  * AOB (Any)

### Notes 57 ###

The meeting discussed actions from earlier meetings and updated the status of actions.

The meeting discussed the arrival of CCFE as a collaborator.  Alistair agreed to send a note to the collaboration about this.  There is now a note called InductionProcess on the wiki.  The intention is to follow this process and to enhance the note.  The purpose of this is to ensure that new partners get off to a good start.  We look forward to having a representative of CCFE attend one of these meetings.

The meeting discussed the status of the reference implementation of the IDS.  One part of this is called the Download Manager.  This is now in pre-production with both the ISIS and DLS facilities.  It will also be used as part of the service verification which will be conducted by Pandata.  It was agreed to make a distribution of the download manager.  This is a technology preview, and should only be used for testing.

As Steve Fisher is on vacation, the meeting did not discuss the status of ICAT 4.3.

Tom has created a google group called icat-issues@googlegroups.com.  Alistair agreed to add a link to this group on http://www.icatproject.org.

The meeting concluded after 20 minutes.  A new record for brevity!

### Actions 57 ###

| Item  | Description                                                                     | Res**(1)**|       Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:------------|:---------------------|
| 57.00 | Document the meeting and update the actions                                     | AM        |         Done|                     -|
| 57.01 | Inform the collaboration of the arrival of CCFE                                 | AM        |         Done| http://www.ccfe.ac.uk|
| 57.02 | Prepare a distribution of a technology preview of the Download Manager          | AW        |            .|                     -|
| 57.03 | Add a link to icat-issues on http://www.icatproject.org                         | AM        |         Done|                     -|

### Attendance 57 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SMF  |Steve Fisher                 | STFC SCD                   |              |    +      |
|DG   |David Garcia                | Alba                       |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|FB   |Frazer Barnsley              | STFC SCD                   |     +        |           |

## Meeting 56 - 4 April 2013 ##

  * Actions from the meetings 55 (Alistair)
  * Review of requirements for ICAT (Steve)
  * Review of requirements for ICAT related parts such as Topcat, IDS, IJP (Steve)
  * AOB (Any)

### Actions 56 ###

| Item  | Description                                                                     | Res**(1)**|  Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-------|:---------------------|
| 56.00 | Document the meeting and update the actions                                     | AM        |    Done|                     -|
| 56.01 | Arrange for copies of Pandata presentations to be available                     | AM        |    Done| https://code.google.com/p/icatproject/source/browse/#svn%2Fmeetings%2FPandataLund, https://code.google.com/p/icatproject/source/browse/#svn%2Fmeetings%2FLund2013Talks|
| 56.02 | Add a paragraph to the requirements page about general requirements             | AM        |    Done| Deployment, Ingestion, Publishing, User office, Setup, Operations, Common standards|
|   .   |   - PMH interface                                                               | SMF       |    Done|                     -|
|   .   |   - Revision of XML schema                                                      | SMF       |    Done|                     -|
|   .   |   - Additional indices in some tables                                           | SMF       |    Done|                     -|
| 56.03 | Make the priority of the requirement for filesystem interface to ICAT, high     | SMF       |    Done|                     -|
| 56.04 | Create tickets for each of the ICAT  requirements and link to index page        | SMF       |    Done|                     -|
| 56.05 | Provide information about the memory leak in the logging to SN et alia          | TG        |    Done|                     -|

### Attendance 56 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SMF  |Steve Fisher                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|NV   |Nicola Bessone               | ESRF                       |     +        |           |
|PG   |Pablo Garcia                 | Alba                       |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|BM   |Brian Matthews               | STFC SCD                   |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|IC   |Idrissou Chado               | Soleil                     |              |    +      |

## Meeting 55 - 21 March 2013 ##

  * Actions from the meetings 52-54 (Alistair)
  * Discussion of the outcomes from Lund
  * Discussion of the content of ICAT 4.3
  * Discussion of the agenda for the collaboration meetings Spring 2013
  * Update from PaN-data (Milan/George)
  * AOB (Any)

### Notes 55 ###

The meeting discussed actions from earlier meetings and updated the status.

The meeting discussed the conference in Lund.  It was generally agreed that it was successful.  A number of items are worthy of note:

  * Sites such as Alba may have to enhance the reference implementation of IDS when it is available, to meet their requirements for data management;
  * There were presentations at the Pandata meeeting (such as Tom's on ICAT and Umbrella integration) which should be made available to the ICAT collaboration;
  * There should be a half day ICAT workshop added to the Pandata meeting in Barcelona in September;
  * There will be a Pandata WP4 telco next week to discuss matters arising from the Pandata meeting in Lund.

The meeting discussed topics to be addressed in the next three months of collaboration meetings - see the list above.

The meeting finished at 15h35.

### Actions 55 ###

| Item  | Description                                                                     | Res**(1)**|            Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:---------------------|
| 55.00 | Document the meeting and update the actions                                     | AM        |              Done|                     -|
| 55.01 | Arrange for copies of Pandata presentations to be available                     | AM        |              Done|                     -|
| 55.02 | Arrange an ICAT workshop with the Pandata meeting at Alba in September          | AM/DS     |          On-going|                     -|

### Attendance 55 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|IC   |Idrissou Chado               | Soleil                     |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|NV   |Nicola Bessone               | ESRF                       |     +        |           |
|DS   |Daniel Salvat                | Alba                       |     +        |           |
|DL   |David Lopez                  | Alba                       |     +        |           |
|PG   |Pablo Garcia                 | Alba                       |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |              |    +      |
|KP   |Kevin Phipps                 | STFC SCD                   |              |    +      |
|AW   |Antony Wilson                | STFC SCD                   |              |    +      |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |              |    +      |
|TG   |Tom Griffin                  | STFC ISIS Facility         |              |    +      |

## Meeting 54 - 7 March 2013 ##

  * Actions from the meetings 52-53 (Alistair)
  * Plans for Sweden2013 (Krister)
  * Update from PaN-data (Milan/George)
  * AOB (Any)

### Actions 54 ###

| Item  | Description                                                                     | Res**(1)**|            Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:---------------------|
| 54.00 | Document the meeting                                                            | AM        |              Done|                     -|
| 54.01 | Update the timetable for Sweden2013                                             | AM        |              Done|                     -|
| 54.02 | Send information about the conference venue to SR                               | AM        |              Done|                     -|
| 54.03 | Send Elettra status summary for presentation in Lund to AM                      | MP        |              Done|                     -|
| 54.04 | Send one page template for status of a contrib to SR, TG, SR                    | AM        |              Done|                     -|
| 54.05 | Distribute the latest notes on the contents of icat 4.3                         | SF        |              Done|                     -|


### Attendance 54 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|DS   |Daniel Salvat                | Alba                       |     +        |           |
|DL   |David Lopez                  | Alba                       |     +        |           |
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |

## Meeting 53 - 21 February 2013 ##

  * Actions from the meetings 52 (Alistair)
  * Plans for Sweden2013 (Krister)
  * Update from PaN-data (Milan/George)
  * AOB (Any)

### Notes 53 ###

Actions 52.02, 52.06 and 52.10 have been completed

Nothing else to report and no AOB.

### Attendance 53 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|KL   |Krister Larsson              | MAX                        |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|DL   |David Lopez                  | Alba                       |     +        |           |

## Meeting 52 - 7 February 2013 ##

  * Actions from the meetings 51 (Alistair)
  * Plans for Sweden2013 (Krister)
  * Status of IDS reference implementation (Antony)
  * Update from PaN-data (Milan/George)
  * AOB (Any)

### Attendance 52 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|KL   |Krister Larsson              | MAX                        |     +        |           |
|JH   |Jamie Hall                   | ILL                        |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |

### Notes 52 ###

Steve reported that he had only a small amount of feedback about the content of ICAT 4.3 and so he would post to the mailing list again.

Shelly asked if people with experience of calling the ICAT API in C++ could provide advice on which package to use.  Gsoap is a candidate.

Krister asked that we add a note to the web site about lunch being available before the ICAT meeting in Lund.  He also said that he intends to send out another announcement about the meeting in Lund.  Krister said that a representative of ESS would attend the ICAT workshop in Lund; he said that ESS is a potential big user of ICAT.

Jamie asked that changes be included in ICAT 4.3 to support ILL.  He will send a note to the mailing list about this.

Antony and Frazer lead a discussion of the plans for IDS.

Milan reported that he was working on a deliverable for WP4 of Pandata.  He reported that SV[2](2.md) was held on 1st February, but it had not been possible to include meta-data ingestion in the verification.

### Actions 52 ###

| Item  | Description                                                                     | Res**(1)**|            Status|                   Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:-------------------------|
| 52.00 | Document the meeting                                                            | AM        |              Done|                         -|
| 52.01 | Ask again for feedback on ICAT 4.3                                              | SF        |              Done|                         -|
| 52.02 | Send comments to Shelly about the use of C++ for ICAT API                       | All       |              Done|                         -|
| 52.03 | Add a note to the wiki about Antony's talk                                      | AM        |              Done|  http://code.google.com/p/icat-data-service/wiki/07_02_13|
| 52.04 | Send an announcement about the meeting in Lund with clarification of venue      | KL        |              Done|                         -|
| 52.05 | Provide a copy of the draft of the pandata deliverable WP4.D.2 to AM            | MP        |              Done|                         -|
| 52.06 | Send a copy of the mail about the changes to ICAT proposed by ILL               | JH        |              Done|                         -|
| 52.07 | Send a note to AM about installing a certificate in GF                          | JH        |                 .|                         -|
| 52.08 | Investigate the effect on GF of the loss of the database connection             | AM        |                 .|                         -|
| 52.09 | Provide a "dry-run" option on API calls to support greyed-out buttons in GUIs   | SF        |              Done|  This will be in ICAT 4.3|
| 52.10 | Fix for bug in createMany to be released in icat 4.2.3                          | SF        |              Done|                         -|
| 52.11 | Chair the next meeting on 21 February 2013 as Alistair will be on leave         | SF        |              Done|                         -|


## Meeting 51 - 24 January 2013 ##

  * Actions from the meetings 50 (Alistair)
  * Contents of ICAT 4.3
  * Plans for Sweden2013 (Tom)
  * Update from PaN-data (Milan/George)
  * Proposals for ICAT 4.3 release
  * AOB (Any)

### Actions 51 ###

| Item  | Description                                                                     | Res**(1)**|            Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:---------------------|
| 51.00 | Document the meeting                                                            | AM        |              Done|                     -|
| 51.01 | Find out about visa requirements and notify GKM                                 | KL        |              Done|                     -|
| 51.02 | Review the wiki page about the meeting in Sweden regularly                      | AM        |          On-going|                     -|
| 51.03 | Update the description of ICAT 4.3 based on the discussion in the meeting       | SF        |              Done|                     -|
| 51.04 | Have one to one discussion of ICAT 4.3 on the telephone                         | SF/SR     |              Done|                     -|
| 51.05 | Call the next meeting on 7 February; Antony to lead the discussion of IDS       | AM/AW     |                 .|                     -|

### Attendance 51 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|JS   |Juergen Starek               | DESY                       |     +        |           |
|KL   |Kristen Larsson              | MAX                        |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |              |      +    |
|GK   |George Kourousias            | ELETTRA                    |              |      +    |

## Meeting 50 - 10 January 2013 ##

  * Actions from the meetings 49 (Alistair)
  * Plans for Sweden2013 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.2 for RAL - DLS, ISIS and CLF (Ghita, Tom, Kevin)
  * Status of contribs (ContribIndex) (Alistair)
  * AOB (Any)

### Notes 50 ###

Shelly asked that additional information such as venue, contact names and hotel details be added to the wiki for the conference in Sweden.  See actions 50.01 and 50.02.

Milan reported that he was working on a deliverable for WP4 of Pandata, but requires data from Thorsten in DESY.  Thorsten is still on vacation and so the deliverable will be delayed until the data is available.  He is expecting to use the data for the next service verification on 1 February.  When this has occurred, the deliverable can be completed.

Steve reported  that he was considering including some speculative features in ICAT 4.3.  These speculative features are being introduced to support data mining and they may change or be removed from future ICATs.  There was discussion about how to achieve this.  The meeting did not agree any particular mechanism.  It was proposed that odd numbered minor revisions such as 4.3 may contain such features, but even numbered revisions such as 4.2 and 4.4 do not.  A facility using ICAT would normally go from even to even.  However where a Facility requires a speculative feature, then it may deploy an odd version on the understanding that the interface to the speculative feature may change.  See action 50.03.

The meeting finished at 15:30.

### Attendance 50 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |

### Actions 50 ###

| Item  | Description                                                                     | Res**(1)**|            Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:---------------------|
| 50.00 | Document the meeting                                                            | AM        |              Done|                     -|
| 50.01 | Provide additional information about venue, hotel, contacts for Sweden          | AM        |                 .|                     -|
| 50.02 | Update the page on the wiki with information about Sweden                       | AM        |          on-going|                     -|
| 50.03 | Consider a mechanism for introducing speculative features to ICAT               | SF        |              Done|                     -|
| 50.04 | Call next meeting for 24 January 2013                                           | AM        |              Done|                     -|


## Meeting 49 - 13 December 2012 ##

  * Actions from the meetings 48 (Alistair)
  * Plans for ICAT 2013 (Tom)
  * Open ticket review (Steve)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.2 for RAL - DLS, ISIS and CLF (Ghita, Tom, Kevin)
  * Status of contribs (ContribIndex) (Alistair)
  * AOB (Any)

### Actions 49 ###

| Item  | Description                                                                     | Res**(1)**|            Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:---------------------|
| 49.00 | Document the meeting                                                            | AM        |              Done|                     -|
| 49.01 | Forward the email from Juan about Sweden to Shelly                              | AM        |              Done|                     -|
| 49.02 | Update the page on the wiki with information about Sweden                       | AM        |          on-going|                     -|
| 49.03 | Update the status of the tickets on google code                                 | SF        |              Done|                     -|
| 49.04 | Call next meeting for 10 January 2013                                           | AM        |              Done|                     -|

### Attendance 48 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |              |     +     |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|Px   |Pablo Garcia                 | Alba                       |     +        |           |
|DL   |David Lopez                  | Alba                       |     +        |           |
|JS   |Julian Savoyet               | ESRF                       |     +        |           |


## Meeting 48 - 29 November 2012 ##

  * Actions from the meetings 47 (Alistair)
  * Icat legacy policy (IcatLegacyPolicy) (Alistair)
  * Requirements for ICAT 4.3 and beyond (IcatRequirements)(Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.2 for DLS (Ghita)
  * Deployment of ICAT 4.2 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * Status of contribs (ContribIndex) - icat\_setup, artefects from pandata/crisp (Alistair)
  * AOB (Any)

### Actions 48 ###

| Item  | Description                                                                     | Res**(1)**|            Status|               Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:---------------------|
| 48.00 | Document the meeting                                                            | AM        |              Done|                     -|
| 48.01 | Ask Juan to clarify the dates for the conference in Lund in March (ICAT 2013)   | AM        |     Email to Juan|                     -|
| 48.02 | Email Shelly about the conference in Lund                                       | AM        |     Email to Juan|                     -|
| 48.03 | Add licence information to all source code parts in ICAT 4.3 (ticket)           | AM        |              Done|            Ticket #85|
| 48.04 | Remind Jamie Hall about clarifications of requirements for data mining          | AM        |              Done|                     -|
| 48.05 | Create tickets for action 47.05, 47.07, 47.09                                   | AM        |              Done| Tickets #86, #87 #88 |
| 48.06 | Add a note to the wiki about Nexus Archive and ICAT                             | GK        |                 .|                     -|
| 48.07 | Contact Mark Koenig about Nexus standards and ICAT                              | GK        |         Cancelled|                     -|
| 48.08 | Talk to Bill Pulford about Nexus, DLS and ICAT                                  | AM        |              Done|                     -|
| 48.09 | Create wiki page for the agenda for ICAT 2013 and add item on Nexus             | AM        |              Done|            Sweden2013|
| 48.10 | Enhance the ICAT Legacy Policy with a clause for support of ICAT after 6 months | AM        |       Done by Tom|                     -|
| 48.11 | Start a discussion on the mailing lists about changes to uniqueness constraints | SF        |              Done|                     -|
| 48.12 | Call next meetings on 13 December 2012, 10 January 2013; cancel 27 December 2012| AM        |              Done|                     -|


### Attendance 48 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |

## Meeting 47 - 15 November 2012 ##

  * Actions from the meetings 46 (Alistair/Steve)
  * Plans for ICAT 4.3 and beyond (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.2 for DLS (Ghita)
  * Deployment of ICAT 4.2 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * AOB (Any)

### Attendance 47 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|JH   |Jamie Hall                   | ILL                        |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|BM   |Brian Matthews               | STFC SCD                   |     +        |           |
|FB   |Frazer Barnsely              | STFC SCD                   |     +        |           |

### Actions 47 ###

| Item  | Description                                                                     | Res**(1)**|            Status|             Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:-------------------|
| 47.00 | Document the meeting                                                            | AM        |              Done|                   -|
| 47.01 | Send Shelly an email about the meeting in March                                 | AM        |              Done|                   -|
| 47.02 | Clarify ICAT licence                                                            | AM        |              Done| http://opensource.org/licenses/BSD-3-Clause|
| 47.03 | Notify people of IcatLegacyPolicy                                               | AM        |              Done|                   -|
| 47.04 | Clarify the data mining requirements                                            | JH        |                 .|                   -|
| 47.05 | Add more bootstrap information for developers to wikis and documentation        | SF        |      Ticketed #86|                   -|
| 47.06 | Ask Juergen to investigate deploying ICAT using Jboss                           | AM        |              Done|                   -|
| 47.07 | Remove ICATCompat from icat 4.3                                                 | SF        |      Ticketed #87|                   -|
| 47.08 | Update IcatRequirements based on the discussions in this meeting                | TG        |              Done|                   -|
| 47.09 | Do a prototype of machine generated documentation of the WS End Point           | AW        |      Ticketed #88|                   -|
| 47.10 | Investigate the ICAT class in Nexus                                             | GK        |              Done|                   -|
| 47.01 | Call next meeting on Thursday 29th November (NOT a public holiday in USA)       | AM        |              Done|                   -|

### Attendance 47 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC SCD                   |     +        |           |
|SF   |Steve Fisher                 | STFC SCD                   |     +        |           |
|KP   |Kevin Phipps                 | STFC SCD                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|JH   |Jamie Hall                   | ILL                        |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|AW   |Antony Wilson                | STFC SCD                   |     +        |           |
|BM   |Brian Matthews               | STFC SCD                   |     +        |           |
|FB   |Frazer Barnsely              | STFC SCD                   |     +        |           |

## Meeting 46 - 1 November 2012 ##

  * Actions from the meetings 45 (Alistair/Steve)
  * Plan for the face to face meeting in March 2013
  * Plans for ICAT 4.3 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.2 for DLS (Ghita)
  * Deployment of ICAT 4.2 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * AOB (Any)

### Actions 46 ###

| Item  | Description                                                                     | Res**(1)**|            Status|             Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:-------------------|
| 46.00 | Document the meeting                                                            | AM        |              Done|                   -|
| 46.01 | Find the data for the ICAT/Pandata meeting in Sweden in 2013                    | AM        | 11-14/March/2013 |                 TBC|
| 46.02 | Review the ICAT requirements page                                               | TG        |              Done|   IcatRequirements |
| 46.03 | Update the SNS requirements for ICAT                                            | SR        |                 .|                   -|
| 46.04 | Schedule RAL meeting about the ISIS migration                                   | AM        |              Done|           15/11/12 |
| 46.05 | Arrange next meeting for 15 November 2012                                       | AM        |              Done|                   -|

## Meeting 45 - 18 October 2012 ##

### Agenda 45 ###

  * Actions from the meetings 44 (Alistair/Steve)
  * Plans for ICAT 4.3 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.3 for DLS (Ghita)
  * Deployment of ICAT 4.3 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * AOB (Any)

### Attendance 45 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|NB   |Nicola Bessone               | ESRF                       |     +        |           |
|JS   |Julian Savoyet               | ESRF                       |     +        |           |
|JH   |Jamie Hall                   | ILL                        |     +        |           |
|JD   |Juergen Stark                | DESY                       |     +        |           |

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS Facility         |              |      +    |
|AW   |Antony Wilson                | STFC eSc                   |              |      +    |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |              |      +    |
|SR   |Shelly Ren                   | ORNL SNS                   |              |      +    |

### Notes 45 ###

There was was an excellent attendance at the meeting with Juergen, Jamie and Julian attending for the first time.

The CRISP project has written a deliverable comparing meta data catalogs and selected ICAT as the preferred catalog.

Tom was absent, so discussion of icat 4.3 was deferred.

Pandata is planning a series of monthly service verifications, with sv-0 planned for 9 November 2012.

ESRF proposes the following changes for icat 4.3:

  * Add a property to icat.properties to deploy to http rather than http;

  * Add additional properties to auth\_ldap.properties to support deployment at ESRF;

  * Change the installation scripts such as create.sh and glassfish.props to enhance support for domains other than domain1;

  * Eliminate the need for the user to supply the authentication mechanism (ie db or ldap).

The DLS deployment of 4.2 is making good progress and full deployment is planned for the close-down period in January 2013.

The ISIS deployment of 4.2 is making good progress and full deployment is planned for the close-down period in December 2012.

The CLF deployment of 4.2 is making good progress and full deployment is planned for November 2012.

It was noted that the 1st of November is a public holiday in both France and Italy so attendance will be reduced for the meeting on that day.


### Actions 45 ###

| Item  | Description                                                                     | Res**(1)**|            Status|     Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:-----------|
| 45.00 | Document the meeting                                                            | AM        |              Done|           -|
| 45.01 | Add a page to the wiki with an index to contrib                                 | AM        |              Done|           -|
| 45.02 | Publish the ER diagrams for icat 4.1 and 4.2 to contrib                         | NB        |              Done|           -|
| 45.03 | Send an email to Christian about his web service web site                       | AM        |                 .|           -|
| 45.04 | Put a link into the wiki to the site of Christian                               | AM        |              Done|           -|
| 45.05 | Discuss the changes proposed by ESRF                                            | SF/AM     |              Done|           -|
| 45.06 | Arrange next meeting on 1 November, but it is a holiday in France and Italy     | AM        |              Done|           -|


## Meeting 44 - 4 October 2012 ##

### Agenda 44 ###

  * Actions from the meetings 42 and 43 (Alistair/Steve)
  * Report on the ICAT meeting at Nobugs2012 (Kevin)
  * ICAT 4.2.1 - Status  (Steve)
  * Plans for ICAT 4.3 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.3 for DLS (Ghita)
  * Deployment of ICAT 4.3 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * Report on visit to Australia Synchrotron (Alistair)
  * AOB - Nicola and Shelly

### Attendance 44 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|NB   |Nicola Bessone               | ESRF                       |     +        |           |
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|MG   |Mark Green                   | Tech-X                     |              |      +    |
|TG   |Tom Griffin                  | STFC ISIS Facility         |              |      +    |

### Notes 44 ###

Kevin reported that the meeting at NOBUGS2012 had gone well and that the materials from the presentations are on the wiki for download.

Steve reported that ICAT 4.2.1 has been available for two weeks, and that it has been installed on pre-production service for CLF at RAL.

As Tom was not present, discussion of ICAT 4.3 is deferred to the next meeting.

Milan reported that the PanData review is in Brussels on 26-27/11.  He would like to report that the Service Verifications have started and that there are six organisations participating.   He also reported that there is a deliverable due in December on the progress of the deployments.  Alistair reported that we would visit ILL/ESRF on 18-19/10 and that the first Service Verification should take place around 3 November 2012.

Ghita reported that there is a meeting scheduled for 11/10/12 about the migration to ICAT 4.3.  There is now a test server in DLS running 4.2, but there is no data available.  DLS is planning to migrate to ICAT 4.3 during the next shutdown period at the beginning of December 2012.

As Tom was not present, there was no discussion about the deployment for ISIS.

Alistair reported that a pre-production server is running ICAT 4.2 for CLF and that migration to ICAT 4.3 will happen in December 2012.

Alistair reported that he had visited the Australian Synchrotron in Melbourne.  the data catalog uses Tardis and the development work is done by a group in Monash University.  They are interested in integrating Tardis with Pandata using Topcat.  During 2013, the lead software engineer for this work will visit RAL to work on a prototype implementation.

Nicola reported that work on installing ICAT 4.2.1 was in progress at ILL and ESRF.

Shelly reported that she added the following two modules to the icat project contrib repository:

1.	icat-rest-ws is a java based restful web service that retrieves certain metadata with open access such as runs proposals for a given instruments, runs for a given proposal, and file locations of a given run etc.. Tom documented a use case note at: http://code.google.com/p/icatproject/wiki/AnonAccessToMetadata

2.	ingestNexus python script would read a Nexus file, parse metadata, create ICAT client objects, and calling ICAT server for cataloging. I plan to add more comments to the readme file of this module to explain what it does and how it works.

### Actions 44 ###

| Item  | Description                                                                     | Res**(1)**|            Status|     Comment|
|:------|:--------------------------------------------------------------------------------|:---|:-----------------|:-----------|
| 44.00 | Document the meeting                                                            | AM        |              Done|           -|
| 44.01 | Add notes to the this page about action 42.2                                    | SR/AM     |              Done|           -|
| 44.02 | Ask Christian to put his python work into the contrib directory                 | AM        |  See action 45.03|           -|
| 44.03 | Distribute note about trouble shooting ICAT 4.2.1                               | SF        |              Done|           -|
| 44.04 | Put a link into this page about the Nobugs materials NOBUGS2012Workshop         | AM        |              Done|           -|
| 44.05 | Provide a copy of the entity diagram for comment                                | NB/SF     |  See action 45.02|           -|
| 44.06 | Send an email to SF about the difficulties of building ICAT with Eclipse        | NB        |              Done|           -|
## Meeting 43 - 6 September 2012 ##

### Agenda 43 ###

  * Actions from the meeting 42 (Steve)
  * Ingest client for ICAT (Tom/ Shelly)
  * ICAT 4.2 - Status  (Steve)
  * Plans for ICAT 4.3 (Tom)
  * Update on plans for the ICAT meeting at Nobugs2012 (Kevin)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.3 for DLS (Ghita)
  * Deployment of ICAT 4.3 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * AOB (Any)

## Meeting 42 - 23 August 2012 ##

### Agenda 42 ###

  * Actions from the meeting 41 (Alistair)
  * Ingest client for ICAT (Tom/ Shelly)
  * ICAT 4.2 - Status  (Steve)
  * Plans for ICAT 4.3 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.3 for DLS (Ghita)
  * Deployment of ICAT 4.3 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * AOB (Any)

### Actions 42 ###

| Item  | Description                                                                     | Res**(1)**|           Status|     Comment|
|:------|:--------------------------------------------------------------------------------|:---|:----------------|:-----------|
| 42.00 | Document the meeting                                                            | AM        |             Done|           -|
| 42.01 | Make an announcement of the availability of ICAT 4.2.0                          | SF        |             Done|           -|
| 42.02 | Discuss anonymous login and NeXus ingest client                                 | TG/SR     | See action 44.02|           -|
| 42.03 | Steve to chair the meeting on 6 September, Kevin the meeting on 20/09           | SF/KP     |             Done|           -|
| 42.04 | Call the next meeting on 6 September 2012 - to be chaired by Steve              | AM        |             Done|           -|

### Attendance 42 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SMF  |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |              |      +    |
|HC   |Harjinder Chahal             | STFC eSc                   |              |      +    |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |              |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|MG   |Mark Green                   | Tech-X                     |     +        |           |

## Meeting 41 - 9 August 2012 ##

### Agenda 41 ###

  * Actions from the meeting 40 (Alistair)
  * ICAT 4.1 - Status  (Alistair)
  * Plans for ICAT 4.3 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.3 for DLS (Ghita)
  * Deployment of ICAT 4.3 for ISIS (Tom)
  * Deployment of ICAT 4.2 for CLF (Alistair)
  * AOB (Any)

### Actions 41 ###

| Item  | Description                                                                     | Res**(1)**|           Status|     Comment|
|:------|:--------------------------------------------------------------------------------|:---|:----------------|:-----------|
| 41.00 | Document the meeting                                                            | AM        |             Done|           -|
| 41.01 | Publish the Nexus ingest to Google/contrib                                      | SR        |             Done|          - |
| 41.02 | Call the next meeting on 23 August 2012                                         | AM        |             Done|        -   |

### Attendance 41 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SMF  |Steve Fisher                 | STFC eSc                   |              |     +     |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |              |      +    |
|AW   |Antony Wilson                | STFC eSc                   |              |      +    |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |              |      +    |
|GK   |George Kourousias            | ELETTRA                    |              |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |

## Meeting 40 - 26 July 2012 ##

### Agenda 40 ###

  * Actions from the meetings of 14 June (Steve)
  * ICAT 4.1 - Status  (Steve)
  * Plans for ICAT 4.2 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4.2 for DLS (Ghita)
  * Deployment of ICAT 4.2 for ISIS (Tom)
  * Deployment of ICAT 4.1 for CLF (Steve)
  * AOB (Any)

### Actions 40 ###

|Item   |Description                                                                      | Res**(1)**|           Status|     Comment|
|:------|:--------------------------------------------------------------------------------|:---|:----------------|:-----------|
|40.00  |Document the meeting                                                             | SMF       |             Done|           -|
| 40.01 | Investigate XMLIngest with new API - can it be generic?                         | TG        |         On-going|          - |
| 40.02 | Organise ICAT presence at NOBUGS and its ICAT workshop                          | TG        |         On-going|        -   |
| 40.03 | Provide data to Ghita in ICAT 4.1/4.2 format                                    | GKM       |               - | Assigned to Ghita for the moment to make sure it is not forgotten. |

### Attendance 40 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SMF  |Steve Fisher                 | STFC eSc                   |              |       +   |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |              |        +  |
|AW   |Antony Wilson                | STFC eSc                   |              |       +   |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |              |           |
|GK   |George Kourousias            | ELETTRA                    |              |           |
|SR   |Shelly Ren                   | ORNL SNS                   |              |           |

### Notes 40 ###
Steve mentioned the timing issue in 4.1

Tom indicated that ISIS would like XMLIngest back - but they will modify it to use the new API efficiently and attempt to make it generic.

The purpose and format of the ICAT workshop at NOBUGS was discussed. It was thought that a tutorial might be useful. Neither Alistair nor Steve will be present.

Ghita would like some data in an ICAT 4.1 to test her eclipse plugin.

## Meeting 39 - 14 June 2012 ##

### Agenda 39 ###

  * Actions from the meetings of 31 May (Alistair)
  * ICAT 4.1 - Status  (Steve)
  * Plans for ICAT 4.2 (Tom)
  * Update from PaN-data (Milan/George)
  * Deployment of ICAT 4 for DLS (Ghita)
  * Deployment of ICAT 4 for ISIS (Tom)
  * AOB (Any)

### Actions 39 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                                                   Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:---------------------------------------------------------|
|39.00  |Document the meeting                                                              |AM        |             Done|                                                         -|
|39.01  |Distribute TOC/Plan for the Pandata Deliverable WP4.1                             |MP        |             Done|                                                         -|
|39.02  |Make an announcement about the availability of ICAT 4.1                           |AM        |             Done|                                                         -|

### Attendance 39 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SF   |Steve Fisher                 | STFC eSc                   |              |       +   |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |              |       +   |
|TG   |Tom Griffin                  | STFC ISIS Facility         |              |       +   |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |              |       +   |

## Meeting 38 - 31 May 2012 ##

### Agenda 38 ###

  * Actions from the meetings of 19 May (Alistair)
  * ICAT 4.1 - Status  (Steve)
  * Availability of release candidate for ICAT41 including Mysql (Alistair)
  * Plans for ICAT 4.2 (Tom)
  * Deployment of ICAT 4 for DLS (Ghita)
  * Deployment of ICAT 4 for ISIS (Tom)
  * Update from PaN-data (Milan/George)
  * AOB (Any)

### Actions 38 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                                                   Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:---------------------------------------------------------|
|38.00  |Document the meeting                                                              |AM        |             Done|                                                         -|
|38.01  |Send a message to Barcelona asking for help testing ICAT 4.1 RC                   |AM        |             Done|                                                         -|
|38.02  |Include Topcat in the responsibilities of the IEB                                 |AM        |             Done|                                                         -|
|38.03  |ICAT Collaboration is to include discussion of requirements and changes to Topcat |All       |             Done|                                                         -|
|38.04  |Send changes to ICAT 4.0 to support SNS to SF                                     |SR        |             Done|                                                         -|

## Meeting 37 - 17 May 2012 ##

### Agenda 37 ###

  * Actions from the meetings of 3 May (Alistair)
  * ICAT Executive Board (IEB) [Proposed Terms for the Board](IcatExecutiveBoard.md)
  * Changes to the SVN on GoogleCode   (Steve)
  * Update from PaN-data (Milan/George)
  * ICAT 4.1 - Status  (Steve)
  * Availability of release candidate for ICAT41 (Alistair)efficientyly
  * Availability of Mysql version of ICAT
  * AOB (Any)

### Actions 37 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                                                   Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:---------------------------------------------------------|
|37.00  |Document the meeting                                                              |AM        |             Done|                                                         -|
|37.01  |Test deployment of ICAT 4.1 with MySql                                            |AM        |             Done|                                                         -|
|37.02  |Add documentation on addMany method to ICAT 4.1 API documentation                 |SF        |             Done|                                                         -|
|37.03  |Prepare a topcat41 which is compatible with ICAT 4.1                              |AW        |     Awaiting 4.1|                                                         -|
|37.04  |Add pages to the wiki for planning ICAT 4.2                                       |AM        |             Done| Icat42Roadmap, RequirementsIcat42, Icat42ReleaseCriteria |
|37.05  |Comment on Terms for the Executive Board                                          |All       |             Done|                                       IcatExecutiveBoard |
|37.06  |Communicate with Shelly about the downloader                                      |AW/SR     |      In progress|                                                         -|
|37.07  |Do initial definition of ICAT 4.2                                                 |TG/AM     |      In progress| Icat42Roadmap, RequirementsIcat42, Icat42ReleaseCriteria |

### Attendance 37 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |

## Meeting 36 - 3 May 2012 ##

### Agenda 36 ###

  * Actions from the meetings of 19 April (Alistair)
  * Update from PaN-data (Milan/George)
  * New participants joining in Grenoble (Tom)
  * Availability of Mysql support for Topcat40 (Alistair)
  * ICAT 4.1 - Status  (Steve)
  * Python Client API (Steve)
  * Availability of release candidate for ICAT41 (Alistair)
  * Structure of the 'work packages' (Tom)
  * AOB (Any)

> ### Actions 36 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                               Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:-------------------------------------|
|36.00  |Document the meeting                                                              |AM        |             Done|                                     -|
|36.01  |Send comments to Milan/George re Deliverable 5.1 of Pandata                       |AM        |             Done|                                     -|
|36.02  |Test deployment of ICAT 4.1 with MySql                                            |AM        |        see 37.01|                                     -|
|36.03  |Ask ALBA for help with testing ICAT 4.1 using MySql                               |AM        |             Done|                                     -|
|36.04  |Plan for Pandata meeting in Trieste 27-28/6          |All       | https://sites.google.com/site/pandatatrieste |                                     -|
|36.05  |Include Nicolas at ESRF on invitation to collaboration meetings                   |AM        |             Done|                                     -|
|36.06  |Include email list members in invitation to collaboration meetings                |AM        |             Done|                                     -|
|36.07  |Verify that Topcat40 deploys on icat40 as documented                              |AW        |             Done|                                     -|
|36.08  |Set up a working group to deal with migration of ISIS ICAT to ICAT 4.1            |AM/TG     |             Done|                                     -|
|36.09  |Email Christian at Julich re Python i/f to icat 4.1                               |AM/SF     |             Done|                                     -|
|36.10  |Add documentation on addMany method to ICAT 4.1 API documentation                 |SF        |        see 37.02|                                     -|
|36.11  |Add Python interface to the roadmap for ICAT 4.1                                  |AM        |             Done|                                     -|
|36.12  |Add new technology eg EVO to the agenda for next meeting                          |AM        |         Deferred|                                     -|
|36.13  |Prepare a topcat41 which is compatible with ICAT 4.1                              |AW        |        see 37.03|                                     -|
|36.14  |Fix what went wrong with the phone in CR02 at RAL for this meeting                |AM        |             Done|                                     -|


### Attendance 36 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |              |     +     |
|HC   |Harjinder Chahal             | STFC eSc                   |              |     +     |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |
|TG   |Tom Griffin                  | STFC ISIS Facility         |     +        |           |


## Meeting 35 - 19 April 2012 ##

### Agenda 35 ###
  * Actions from the meetings of 5 April (Alistair)
  * Demonstration of Eclipse Plugin (Ghita)
  * Presentation about Python work on ICAT 4 at Julich (Christian)
  * Availability of Topcat40 (Alistair)
  * Availability of Jenkins service on http://www.icatproject.org:6080/jenkins (Alistair)
  * Update from PaN-data meeting (Milan/George)
  * ICAT 4.1 - Schema  (Steve)
  * Availability of ICAT41 (Alistair)
  * AOB (All)

### Actions 35 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                               Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:-------------------------------------|
|35.00  |Document the meeting                                                              |AM        |             Done|                                     -|
|35.01  |Ask Juan to add Alistair to Pandata mailing lists                                 |AM        |             Done|                                     -|
|35.02  |Send comments to Milan/George re Deliverable 5.1 of Pandata                       |AM        |         See 36.1|                                     -|
|35.03  |Release Topcat 4.1                                                                |AM        |             Done|                  wiki:InstallTopcat40|
|35.04  |Consider proposed change to ICAT API to support Python                            |SF        |             Done|                                     -|
|35.05  |Test deployment of ICAT 4.1 with MySql                                            |AM        |         See 36.2|                                     -|
|35.06  |Ask ALBA for help with testing ICAT 4.1 using MySql                               |AM        |         See 36.3|                                     -|

### Attendance 35 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |



## Meeting 34 - 5 April 2012 ##

### Agenda 34 ###
  * Actions from the meetings of 20 March (Alistair)
  * Availability of Topcat40 (Alistair)
  * Update from PaN-data meeting (Milan/George)
  * Schema changes - the end is in sight? (Steve)
  * Mailing lists (Alistair)
  * Open ticket review (Alistair)
  * Work packages (Alistair)
  * Digital certificate on www.icatproject.org and other common ICAT deployments (Alistair)
  * Priorities
  * AOB

### Actions 34 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                               Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:-------------------------------------|
|34.00  |Document the meeting                                                              |AM        |             Done|                                     -|
|34.01  |Try Topcat40 and provide feedback to Harjinder                                    |MP        |                .|                                     -|
|34.02  |Contact Christian Felder about his work on web services python interface          |AM        |             Done|                                     -|
|34.04  |Try Ghita's plugin for Eclipse                                                    |HC        |                .|                                     -|
|34.05  |Chase people for feedback on sample type and sample                               |SF        |                .|                                     -|
|34.06  |Try to get a RC for ICAT 4.1 available by 19/4/2012                               |AM/HC     |                .|                                     -|
|34.07  |Add discussion of Ghita's plugin to next meeting                                  |AM/GDA    |             Done|                                     -|
|34.08  |.                                                                                 |..        |                .|                                     -|






### Attendance 34 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS                  |              |     +     |
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|BM   |Brian Matthews               | STFC eSc                   |              |     +     |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |     +     |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |              |     +     |
|MP   |Milan Prica                  | ELETTRA                    |     +        |           |
|GK   |George Kourousias            | ELETTRA                    |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |


## Meeting 33 - 20 March 2012 ##

This was the face to face meeting in Grenoble.  For details, other than the actions, see WorkshopGrenoble2012.

### Actions 33 ###

|Item   |Description                                                                       |Res**(1)**|           Status|                               Comment|
|:------|:---------------------------------------------------------------------------------|:--|:----------------|:-------------------------------------|
|33.00  |Document the meeting                                                              |AM        |             Done|                                     -|
|33.01  |Remove all of the uses of "3" in the API                                          |SF        |             Done|                                     -|
|33.02  |Consider the possibility of creating a list of objects in one call                |SF        |       Ticket #73|                                     -|
|33.03  |Consider how to provide a free text search in ICAT 4                              |SF        |       Ticket #74|                                     -|
|33.04  |Put Topcat on the client user list for JMS notifications                          |AW        |       Ticket #75|                                     -|
|33.05  |Test that ICAT 4 provides support for ISIS reporting using JMS notification       |AM        |       Ticket #75|                                     -|
|33.06  |Remove shift user from ICAT 4                                                     |SF        |        No action|                                     -|
|33.07  |Provide an early view of innovative features in early releases such as 4.0.1      |AM        |         On-going|                                     -|
|33.08  |Data mining tools are required to support CRISP                                   |AM        |    Added to reqs| [ReqsIcat41](http://code.google.com/p/icatproject/wiki/RequirementsIcat41#CRISP) |
|33.09  |Make presentations from this meeting available from the Google Code site          |AM        |             Done|                                     -|
|33.10  |Write an email to the participants with the call to action                        |AM        |             Done|                                     -|

## Meeting 32 - 8 March 2012 ##

### Actions 32 ###

|Item   |Description                                                                       |Res**(1)**|      Status|                               Comment|
|:------|:---------------------------------------------------------------------------------|:--|:-----------|:-------------------------------------|
|32.00  |Document the meeting                                                              |TG        |        Done|                                     -|
|32.01  |Review and update the install documentation using feedback from Frank and Milan   |HJ/AM     |        Done|                                     -|
|32.02  |Consult main ICAT mailing list on proposed schema changes                         |SF        |        Done|                                     -|
|32.03  |Send Ghita link to documents about ICAT changes                                   |TG        |        Done| http://www.icatproject.org/icat.html |


### Agenda 32 ###
  * Actions from the meetings of 23 February
  * Update from PaN-data meeting (Milan/George)
  * Face to Face Meeting in Grenoble in 2012 (WorkshopGrenoble2012)
  * Revised ICAT schema proposal for 4.1 (Steve's emails)
  * Constraints on parameter values (Steve's email)
  * AOB

### Attendance 32 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS                  |     +        |           |
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |              |     +     |
|BM   |Brian Matthews               | STFC eSc                   |              |     +     |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |              |     +     |
|SR   |Shelly Ren                   | ORNL SNS                   |              |     +     |
|KP   |Kevin Phipps                 | STFC eSc                   |              |     +     |
|MP   |Milan Prica                  | ELLETRA                    |     +        |           |
|GK   |George Kourousias            | ELLETRA                    |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |              |     +     |



## Meeting 31 - 23 February 2012 ##

### Actions 31 ###

|Item   |Description                                                                       |Res**(1)**|      Status|        Comment|
|:------|:---------------------------------------------------------------------------------|:--|:-----------|:--------------|
|31.00  |Document the meeting                                                              |TG        |        Done|              -|
|31.01  |Remind PaN-data meeting about meeting in Grenoble                                 |MP/GK/BM  |        Done|               |
|31.02  |Respond to Valerie Duchasténier with your details for Grenoble meeting            |All       | In progress|               |
|31.03  |Contact Frank about his problems installing ICAT                                  |HC        |        Done| [Issue 72](https://code.google.com/p/icatproject/issues/detail?id=72)      |

### Agenda 31 ###

  * Actions from the meetings of 9 February
  * Road map for ICAT 4.1 (Icat41Roadmap)
  * Face to Face Meeting in Grenoble in 2012 (WorkshopGrenoble2012)
  * Review of ICAT Collaboration by Software Sustainability Institute (SsiExecutiveSummary)
  * AOB

### Attendance 31 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS                  |     +        |           |
|SF   |Steve Fisher                 | STFC eSc                   |              |     +     |
|AM   |Alistair Mills               | STFC eSc                   |              |     +     |
|BM   |Brian Matthews               | STFC eSc                   |              |     +     |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |              |     +     |
|KP   |Kevin Phipps                 | STFC eSc                   |              |     +     |
|SW   |Shaun de Witt                | STFC EUDAT                 |              |     +     |
|MP   |Milan Prica                  | ELLETRA                    |     +        |           |
|GK   |George Kourousias            | ELLETRA                    |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |     +        |           |

## Meeting 30 - 9 February 2012 ##

### Actions 30 ###

|Item   |Description                                                                       |Res**(1)**|      Status|                       Comment|
|:------|:---------------------------------------------------------------------------------|:--|:-----------|:-----------------------------|
|30.00  |Document the meeting                                                              |AM        |        Done|                             -|
|30.01  |Make an announcement about the meeting in Grenoble                                |AM        |        Done| WorkshopGrenoble2012         |
|30.02  |Contact CRISP project about Grenoble                                              |AM        |        Done| Spoke to Jean-Francois       |
|30.03  |Remind SN about OIIMPH                                                            |AM        |        Done| SN has nothing to add!       |
|30.04  |Remind SdeW about Eudat requirements for ICAT 4.1                                 |AM        |        Done| RequirementsIcat41#Eudat     |
|30.05  |Remind SN about DLS login information                                             |AM        |        Done| Completed                    |
|30.06  |Distribute Topcat40 for installation with ICAT40                                  |AM        | In progress| Ask Harjinder                |
|30.07  |Ask for requirements for ICAT 4.1 from CLF, SNS, DLS                              |AM        | In progress| RequirementsIcat41           |
|30.08  |Add requirements for ICAT 4.1 for PANDATA                                         |AM        |        Done| RequirementsIcat41#Pandata   |
|30.09  |Email collaboration about sample data                                             |All       |           .|                             -|
|30.10  |Email TG about requirements for the meeting in Grenoble                           |All       |        Done|                             -|
|30.11  |Coordinate arrangements with the contact person at ILL for the workshop           |TG        | In progress|                             -|


### Agenda 30 ###

  * Actions from the meetings of 26 January
  * Status of ICAT 4.0 (Icat40Announcement)
  * Publicly available ICAT 4.0 demonstration system  (Icat40Demonstrator)
  * Road map for ICAT 4.1 (Icat41Roadmap)
  * Collaboration agreement of PanData and ICAT (IcatPandataAgreement)
  * Proposal to reduce the number of fields for an Investigation and to regularise the field and entity names. (Steve)
  * The Sample, Dataset, Investigation triangle (Steve)
  * Face to Face Meeting in Grenoble in 2012 (WorkshopGrenoble2012)
  * Review of ICAT Collaboration by Software Sustainability Institute (SsiExecutiveSummary)
  * AOB

### Attendance 30 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS                  |     +        |           |
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|BM   |Brian Matthews               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |              |     +     |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|SW   |Shaun de Witt                | STFC EUDAT                 |              |     +     |
|MP   |Milan Prica                  | ELLETRA                    |     +        |           |
|GK   |George Kourousias            | ELLETRA                    |     +        |           |
|AW   |Antony Wilson                | STFC eSc                   |              |     +     |

## Meeting 29 - 26 January 2012 ##

### Actions 29 ###

|Item   |Description                                                                       |Res**(1)**|              Status|               Comment|
|:------|:---------------------------------------------------------------------------------|:--|:-------------------|:---------------------|
|29.00  |Document the meeting                                                              |AM        |                Done|                     -|
|29.01  |Make the release of ICAT 4.0                                                      |AM        |                Done| Icat40Announcement   |
|29.02  |Email Shelly about possible visit to SNS                                          |AM        |                Done|                     -|
|29.03  |Email all about the proposed arrangements for Grenoble on 20/Mar/12               |TG        |                Done| WorkshopGrenoble2012 |
|29.04  |Provide a note on the wiki about OIIMPH and ICAT                                  |SN        |                   .|                     -|
|29.05  |Comment on Steve's paper on ideas for ICAT                                        |All       |                Done|                     -|
|29.06  |Provide a copy of the report of Steve Crouch when available                       |AM        |                   .| SsiExecutiveSummary  |
|29.07  |Test ICAT 4.0 and provide feedback                                                |All       |                Done|                     -|
|29.08  |Create a wiki page for the ICAT 4.1 requirements                                  |AM        |                Done| RequirementsIcat41   |
|29.09  |Add requirements for ICAT 4.1 for discussion at the next meeting                  |SW/TG/SR  |                   .| RequirementsIcat41   |
|29.10  |Put a note on the wiki about the use of the ICAT on the Internet                  |HC/AM     |                   .| Icat40Demonstrator   |
|29.11  |Send note to Ghita about the use of ICAT admin for ICAT for DLS                   |SN        |                   .|                     -|
|29.12  |Follow up with George about this meeting                                          |AM        |                Done|                     -|

We could not contact George for the meeting, but he send this report:

1. The installation and testing will happen the following weeks as some key persons were not available these days.

2. The date for WP4 PaNdata telco will be announced next week.

3. During the WP4 telco we will identify the ICAT responsible for most facilities. (but I expect to be slow procedure as the WP has just started).

### Attendance 29 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS                  |     +        |           |
|SF   |Steve Fisher                 | STFC eSc                   |     +        |           |
|AM   |Alistair Mills               | STFC eSc                   |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC eSc                   |     +        |           |
|SR   |Shelly Ren                   | ORNL SNS                   |     +        |           |
|KP   |Kevin Phipps                 | STFC eSc                   |     +        |           |
|SN   |Sri Nagella                  | STFC eSc                   |     +        |           |
|SW   |Shaun de Witt                | STFC eSc (EUDAT)           |     +        |           |
|MP   |Milan Prica                  | ELLETRA                    |              |     +     |
|GK   |George Kourousias            | ELLETRA                    |              |     +     |

### Agenda 29 ###

  * Actions from the meetings of 12 January
  * Status of ICAT 4.0
  * Road map for ICAT 4.1
  * Collaboration agreement of PanData and ICAT
  * Meeting in Hamburg
  * Review of ICAT Collaboration by Software Sustainability Institute
  * AOB

## Meeting 28 - 12 January 2012 ##

### Actions 28 ###

|Item   |Description                                                                       |Res**(1)**|      Status|      Comment|
|:------|:---------------------------------------------------------------------------------|:--|:-----------|:------------|
|28.00  |Document the meeting                                                              |AM        |        Done|            -|
|28.01  |Agree a description of the PanData/ ICAT collaboration                            |AM/GK     |        Done|            -|
|28.02  |Provide a list of requirement for ICAT from PanData                               |GK        |           .|            -|
|28.03  |Prepare a list of PanData contacts for the ICAT collaboration (1 per partner)     |GK        |           .|            -|
|28.04  |Put discussion of related projects such as Eudat on the agenda for future meetings|AM        |        Done|            -|
|28.05  |Comment on Steve's paper on notification                                          |All       |        Done|            -|
|28.06  |Prepare ICAT 4.0.RC.0 as soon as possible and inform collaboration                |AM        |        Done|            -|
|28.07  |Test ICAT RCs and provide feedback                                                |All       |        Done|            -|
|28.08  |Prepare a road map for ICAT 4.1 for discussion at the next meeting                |AM        |           .|            -|
|28.09  |Send email to Shelly about the meeting in Hamburg                                 |AM        |        Done|            -|

### Agenda 28 ###

  * Actions for the meetings of 1 December
  * Working with PanData project
  * Working with Eudat project
  * Working with European Meta Data Projects
  * Notification proposal
    * https://docs.google.com/document/d/1COpWdSBoChVQKkFINIqACRhdlcdcxD1S2tzBDtw8WKY/edit?hl=en_US&pli=1
  * Plan for ICAT 4.0 - the ICAT4 technology preview release
  * Review of ICAT Collaboration by Software Sustainability Institute
    * http://www.software.ac.uk/news/2011-11-29-software-sustainability-institute-review-google-code-project
  * Visit of David Webster of the University of Leeds to work on API migration

### Attendance 28 ###

|Init |Name                         | Institute                  |     Present  | Apologies |
|:----|:----------------------------|:---------------------------|:-------------|:----------|
|TG   |Tom Griffin                  | STFC ISIS                  |     +        |           |
|SF   |Steve Fisher                 | STFC                       |     +        |           |
|AM   |Alistair Mills               | STFC                       |     +        |           |
|GKM  |Ghita Kouadri Mostefaoui     | Diamond                    |     +        |           |
|HC   |Harjinder Chahal             | STFC                       |     +        |           |
|SR   |Shelly Ren                   | ORNL                       |     +        |           |
|MP   |Milan Prica                  | ELLETRA                    |     +        |           |
|GK   |George Kourousias            | ELLETRA                    |     +        |           |
|KP   |Kevin Phipps                 | STFC                       |              |      +    |
|SW   |Shaun de Witt                | STFC                       |              |      +    |

## Meeting 27 - 1 December 2011 ##

### Actions 27 ###

Actions from ICAT collaboration telephone conference on 1 December 2011<br>
27.1 - Alistair Mills - 1 December 2011<br>
<br>
<table><thead><th>Item   </th><th>Description                                                                       </th><th>Res<b>(1)</b></th><th>      Status</th><th>      Comment</th></thead><tbody>
<tr><td>27.00  </td><td>Document the meeting                                                              </td><td>AM        </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.01  </td><td>Comment on Tom's note on adding IP to session id     wiki:AnonAccessToMetadata    </td><td>All       </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.02  </td><td>Investigate the packaging of the authentication plugin and a properties file      </td><td>SF        </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.03  </td><td>Co-operate with the visitor from Software Sustainability Institute (Steve Crouch) </td><td>All/AM    </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.04  </td><td>Prepare the materials for the release of ICAT 4.0                                 </td><td>All/AM    </td><td> In progress</td><td>            -</td></tr>
<tr><td>27.05  </td><td>Send a note to GKM about the ICAT4 API changes and meet with Ghita and DW         </td><td>GKM/AM/DW </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.06  </td><td>Provide release note for ICAT 4.0                                                 </td><td>AM        </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.07  </td><td>Remove the My Proxy option from ICAT 4.0                                          </td><td>SF        </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.08  </td><td>Change get user details method to work in ICAT 4.0                                </td><td>SF        </td><td>        Done</td><td>            -</td></tr>
<tr><td>27.09  </td><td>Alistair and DW to visit Nick Draper re ICAT 4.0 API                              </td><td>AM        </td><td>        Done</td><td>            -</td></tr></tbody></table>

<h3>Attendance 27</h3>

<table><thead><th>Init </th><th>Name                         </th><th> Institute                  </th><th>     Present  </th><th> Apologies </th></thead><tbody>
<tr><td>TG   </td><td>Tom Griffin                  </td><td> STFC ISIS                  </td><td>     +        </td><td>           </td></tr>
<tr><td>SF   </td><td>Steve Fisher                 </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>AM   </td><td>Alistair Mills               </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>GKM  </td><td>Ghita Kouadri Mostefaoui     </td><td> Diamond                    </td><td>     +        </td><td>           </td></tr>
<tr><td>HC   </td><td>Harjinder Chahal             </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>SR   </td><td>Shelly Ren                   </td><td> ORNL                       </td><td>     +        </td><td>           </td></tr>
<tr><td>MP   </td><td>Milan Prica                  </td><td> ELLETRA                    </td><td>     +        </td><td>           </td></tr>
<tr><td>SC   </td><td>Steve Crouch                 </td><td> Univ of Southampton /SSI   </td><td>              </td><td>           </td></tr>
<tr><td>DW   </td><td>David Webster                </td><td> Univ of Leeds              </td><td>              </td><td>           </td></tr>
<tr><td>KP   </td><td>Kevin Phipps                 </td><td> STFC                       </td><td>              </td><td>      +    </td></tr></tbody></table>

<h3>Agenda 27</h3>

<ul><li>Actions for the meetings of 3 and 17 November<br>
</li><li>Review of ICAT Collaboration by Software Sustainability Institute<br>
<ul><li><a href='http://www.software.ac.uk/news/2011-11-29-software-sustainability-institute-review-google-code-project'>http://www.software.ac.uk/news/2011-11-29-software-sustainability-institute-review-google-code-project</a>
</li></ul></li><li>Plan for ICAT 4.0 - the ICAT4 technology preview release<br>
</li><li>Visit of David Webster of the University of Leeds to work on API migration</li></ul>

<h2>Meeting 26 - 17 November 2011</h2>

<h3>Actions 26</h3>

26.1 - Alistair Mills - 28 November 2011<br>
<br>
<table><thead><th>Item   </th><th>Description                                                            </th><th>Res<b>(1)</b></th><th>      Status</th><th>      Comment</th></thead><tbody>
<tr><td>26.00  </td><td>Document the meeting                                                   </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.01  </td><td>Provide more information on the usage of ICAT4                         </td><td>SF/AM   </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.02  </td><td>Write a note describing controlling metadata based on IP of client     </td><td>TG      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.03  </td><td>Send pom files for building ICAT4 to SF                                </td><td>SR      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.04  </td><td>Try maven build with SF's maven scripts - see 26.03                    </td><td>HC      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.05  </td><td>Investigate the Facility Unit tests in ICAT4                           </td><td>SF/HC   </td><td>           .</td><td>            -</td></tr>
<tr><td>26.06  </td><td>Add upgrade of logging to icat4 roadmap                                </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.07  </td><td>Add IP address to session id in the roadmap                            </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.08  </td><td>Provide date for collaboration meeting in Grenoble to SR and others    </td><td>AM      </td><td>     Waiting</td><td>            -</td></tr>
<tr><td>26.09  </td><td>Add Milan to the invitation list for the meetings                      </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>26.10  </td><td>Chase Holger about attending the collaboration meetings                </td><td>AM      </td><td>        Done</td><td>            -</td></tr></tbody></table>

<h3>Agenda 26</h3>

<ul><li>Actions since the meeting on 3 November<br>
</li><li>Status of ICAT work at ORNL (SR)<br>
<ul><li>Removal of local maven repository<br>
</li><li>Facility based unit test: looks like test input data are hard coded<br>
</li><li>Reporting and event logging in icat4<br>
</li><li>IP address gets logged with sessionId? (requested by SNS IT)</li></ul></li></ul>

<h2>Meeting 25 - 3 November 2011</h2>

Agenda for ICAT collaboration telephone conference on 3 November 2011<br>

<ul><li>Actions since the meeting on 22 September<br>
</li><li>Status of ICAT 4 (Alistair)<br>
</li><li>Representation of time (Steve)<br>
</li><li>Status of port to MySQL (Alistair)<br>
</li><li>Resources available in 2012 (All)<br>
</li><li>Progress on evaluation of ICAT4 (Steve)<br>
</li><li>Possibility of adding a .props file for configuration<br>
</li><li>Brief thoughts on adding notification<br>
</li><li>BMT rather than CMT (Steve)<br>
</li><li>Status of ICAT work at ILL (J-F)<br>
</li><li>Status of ICAT work at ORNL (SR)</li></ul>

<h3>Actions 25</h3>

Actions from ICAT collaboration telephone conference on 3 November 2011<br>
25.1 - Alistair Mills - 3 November 2011<br>
<br>
<table><thead><th>Item   </th><th>Description                                                            </th><th>Res<b>(1)</b></th><th>      Status</th><th>      Comment</th></thead><tbody>
<tr><td>25.00  </td><td>Document the meeting                                                   </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.01  </td><td>Follow up on actions 24.04 and 24.06                                   </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.02  </td><td>Follow up on action 24.03 (Use of Maven for building ICAT4)            </td><td>SF      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.03  </td><td>Attempt to do ICAT 3 to ICAT 4 upgrade using an ISIS testbed           </td><td>TG      </td><td>           .</td><td>            -</td></tr>
<tr><td>25.04  </td><td>Attempt to do ICAT 3 to ICAT 4 upgrade using an ILL testbed            </td><td>J-F     </td><td>           .</td><td>            -</td></tr>
<tr><td>25.05  </td><td>Discuss the GF 3.1.1 problem                                           </td><td>AM/SR   </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.06  </td><td>Investigate ways to represent time to suit the data                    </td><td>SF/RS   </td><td>           .</td><td>            -</td></tr>
<tr><td>25.07  </td><td>Add a properties file to ICAT4                                         </td><td>SF      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.08  </td><td>Experiment with Bean Managed Transactions for ICAT 4.1                 </td><td>SF      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.09  </td><td>Send comments on the changes to the ICAT API to Steve                  </td><td>All     </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.10  </td><td>Provide a document about notification                                  </td><td>SF      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.11  </td><td>Add fields for STARTTIME and ENDTIME to DATASET for ICAT 4.0           </td><td>SF      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.12  </td><td>Defer further implementation of MySQL with ICAT until ICAT 4.1         </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.13  </td><td>Follow up with Shelly on ORNL requirements                             </td><td>SR      </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.14  </td><td>Investigate hosting a collaboration meeting at ILL in 1Q2012           </td><td>J-F     </td><td>        Done</td><td>            -</td></tr>
<tr><td>25.15  </td><td>Schedule next collaboration meeting for 17 November 2011               </td><td>AM      </td><td>        Done</td><td>            -</td></tr></tbody></table>

<b>(1) Collaborators</b>

<table><thead><th>init </th><th>Name                         </th><th> Institute                  </th><th>     Present  </th><th> Apologies </th></thead><tbody>
<tr><td>TG   </td><td>Tom Griffin                  </td><td> STFC ISIS                  </td><td>     +        </td><td>           </td></tr>
<tr><td>SF   </td><td>Steve Fisher                 </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>AM   </td><td>Alistair Mills               </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>HC   </td><td>Harjinder Chahal             </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>KP   </td><td>Kevin Phipps                 </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>SR   </td><td>Shelly Ren                   </td><td> ORNL                       </td><td>     +        </td><td>           </td></tr>
<tr><td>J-F  </td><td>Jean-Francois                </td><td> ILL                        </td><td>     +        </td><td>           </td></tr>
<tr><td>RS   </td><td>Richard Sinclair             </td><td> STFC                       </td><td>              </td><td>           </td></tr></tbody></table>

<h2>Meeting 24 - 22 September 2011</h2>

<h3>Actions 24</h3>

Actions from ICAT collaboration telephone conference on 22 September 2011<br>
24.1 - Alistair Mills - 22 September 2011<br>
<br>
<table><thead><th>Item   </th><th>Description                                                            </th><th>Res<b>(1)</b></th><th>      Status</th><th>      Comment</th></thead><tbody>
<tr><td>24.00  </td><td>Document the meeting                                                   </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>24.01  </td><td>Add Ghita, Holger, Shelly and Harjinder to the meeting list            </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>24.02  </td><td>Write a note for the wiki about the Release criteria for ICAT4         </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>24.03  </td><td>Write a note for the wiki about the use of Maven with ICAT4            </td><td>AM      </td><td>        Done</td><td>             </td></tr>
<tr><td>24.04  </td><td>Test Glassfish 3.11 with ICAT4 and document any alerts                 </td><td>AM      </td><td>        Done</td><td>             </td></tr>
<tr><td>24.05  </td><td>Update the road map for ICAT 4 and link it to the Release criteria     </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>24.06  </td><td>Write a note with estimates of the effort required to release ICAT4    </td><td>AM      </td><td>   Cancelled</td><td>             </td></tr>
<tr><td>24.07  </td><td>Remind people of the next meeting on 6 October                         </td><td>AM      </td><td>        Done</td><td>            -</td></tr>
<tr><td>24.08  </td><td>Update the ICAT4 roadmap                                               </td><td>AM      </td><td>        Done</td><td>            -</td></tr></tbody></table>

<b>(1) Collaborators</b>

<table><thead><th>Init </th><th>Name                         </th><th> Institute                  </th><th>     Present  </th><th> Apologies </th></thead><tbody>
<tr><td>TG   </td><td>Tom Griffin                  </td><td> STFC ISIS                  </td><td>     +        </td><td>           </td></tr>
<tr><td>HG   </td><td>Holger Gebhard               </td><td> ILL                        </td><td>              </td><td>           </td></tr>
<tr><td>SF   </td><td>Steve Fisher                 </td><td> STFC                       </td><td>              </td><td> +         </td></tr>
<tr><td>AM   </td><td>Alistair Mills               </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>GKM  </td><td>Ghita Kouadri Mostefaoui     </td><td> Diamond                    </td><td>              </td><td>           </td></tr>
<tr><td>SN   </td><td>Sri Nagella                  </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>RS   </td><td>Richard Sinclair             </td><td> STFC                       </td><td>              </td><td>           </td></tr>
<tr><td>RF   </td><td>Ron Fowler                   </td><td> STFC                       </td><td>              </td><td>           </td></tr>
<tr><td>HC   </td><td>Harjinder Chahal             </td><td> STFC                       </td><td>     +        </td><td>           </td></tr>
<tr><td>KP   </td><td>Kevin Phipps                 </td><td> STFC                       </td><td>              </td><td> +         </td></tr>
<tr><td>SR   </td><td>Shelly Ren                   </td><td> ORNL                       </td><td>     +        </td><td>           </td></tr>
<tr><td>GK   </td><td>George Kourousias            </td><td> ELLETRA                    </td><td>              </td><td>           </td></tr>
<tr><td>MP   </td><td>Milan Prica                  </td><td> ELLETRA                    </td><td>              </td><td>           </td></tr>
<tr><td>BM   </td><td>Brian Matthews               </td><td> STFC                       </td><td>              </td><td>           </td></tr></tbody></table>


<h2>Meeting 23 - 8 September 2011</h2>

<h3>Actions 23</h3>

Actions from ICAT collaboration telephone conference on 8 September 2011<br>
23.1 - Alistair Mills - 8 September 2011<br>
<br>
<table><thead><th>Item   </th><th>See also  </th><th>Description                                                     </th><th>Res<b>(1)</b></th><th>           Status</th><th>      Comment</th></thead><tbody>
<tr><td>23.00  </td><td>     -    </td><td>Document the meeting                                            </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.01  </td><td> 22.02    </td><td>Documentation on API for ICAT3 and ICAT4                        </td><td>KP      </td><td>     ICAT3 - Done</td><td>            -</td></tr>
<tr><td>23.02  </td><td> 22.03    </td><td>Create location for Jenkins, Sonar and Nexus on icatproject.org </td><td>AM      </td><td>         Deferred</td><td>            -</td></tr>
<tr><td>23.03  </td><td> 22.04    </td><td>Performance tests for icat - Discuss with Tom                   </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.04  </td><td> 22.05    </td><td>Discuss the content of ICAT4 - Do this at mtg 24                </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.05  </td><td> 21.06    </td><td>Improvements to xmlingest - Discuss with Sri                    </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.06  </td><td> 21.01    </td><td>Rule based authentication - Discuss with Steve                  </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.07  </td><td> 21.02    </td><td>Deploy a vanilla ICAT4 in a VM, eg projectscott-ppe             </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.08  </td><td> 21.03    </td><td>Continuous build - Hudson - projectscott-tst and esctest        </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.09  </td><td> 21.04    </td><td>Copy of the documentation of the API of ICAT3 (icatproject.org) </td><td>AM      </td><td>             Done</td><td>            -</td></tr>
<tr><td>23.10  </td><td> 21.05    </td><td>Update/demo of BIRT - Remind HG                                 </td><td>AM      </td><td>             Done</td><td>            -</td></tr></tbody></table>

<h2>Meeting 22 - 22 July 2011</h2>

Actions from ICAT collaboration telephone conference on 22 July 2011<br>
22.1 - Alistair Mills - 22 July 2011<br>
<br>
<table><thead><th>Item   </th><th>Description                                                  </th><th>Res<b>(1)</b></th><th>  Status</th><th>      Comment</th></thead><tbody>
<tr><td>22.00 </td><td>Document the meeting                                          </td><td>AM      </td><td>    Done</td><td>            -</td></tr>
<tr><td>22.01 </td><td>Add information about Mariusz work on the examples to wiki    </td><td>AM      </td><td> Started</td><td>            -</td></tr>
<tr><td>22.02 </td><td>Inform AM about the status of the documentation on the API    </td><td>KP      </td><td>   Done </td><td>            -</td></tr>
<tr><td>22.03 </td><td>Publish information on the continuous build web site          </td><td>AM      </td><td>      - </td><td>            -</td></tr>
<tr><td>22.04 </td><td>Provide material for icat3 performance tests                  </td><td>TG      </td><td>      - </td><td>            -</td></tr>
<tr><td>22.05 </td><td>Update the roadmap for ICAT4                                  </td><td>AM      </td><td>      - </td><td>            -</td></tr>
<tr><td>22.06 </td><td>Update the plan for the improvements for XML Ingest for DLS   </td><td>AM      </td><td>      - </td><td>            -</td></tr></tbody></table>

<h2>Meeting 21 - 7 July 2011</h2>

Actions from ICAT collaboration telephone conference on 7 July 2011<br>
21.1 - Alistair Mills - 7 July 2011<br>
<br>
<table><thead><th>Item   </th><th>Description                                                  </th><th>Res<b>(1)</b></th><th> Status</th><th>      Comment</th></thead><tbody>
<tr><td>21.00 </td><td>Document the meeting                                          </td><td>AM      </td><td>   Done</td><td>            -</td></tr>
<tr><td>21.01 </td><td>Add a note about rule based authentication to googlecode      </td><td>SF      </td><td>     - </td><td>            -</td></tr>
<tr><td>21.02 </td><td>Review the status of the MySQL version of ICAT                </td><td>AW      </td><td>     - </td><td>            -</td></tr>
<tr><td>21.03 </td><td>Review the status of the continuous build of ICAT             </td><td>SN      </td><td>     - </td><td>            -</td></tr>
<tr><td>21.04 </td><td>Provide a copy of the documentation on the API to googlecode  </td><td>KP      </td><td>     - </td><td>            -</td></tr>
<tr><td>21.05 </td><td>Provide an update/demo of BERT                                </td><td>HG      </td><td>     - </td><td>            -</td></tr>
<tr><td>21.06 </td><td>Try using the ICAT from the head of the SVN on Googlecode     </td><td>SO      </td><td>     - </td><td>            -</td></tr>
<tr><td>21.07 </td><td>Arrange the next meeting for 8 September 2011                 </td><td>AM/TG   </td><td>     - </td><td>            -</td></tr>
<tr><td>21.08 </td><td>Arrange a meeting of ICAT users at RAL on 22/7 about ICAT4    </td><td>AM/TG   </td><td>   Done</td><td>            -</td></tr></tbody></table>

<h2>Meeting 20 - 12 May 2011</h2>

Actions from ICAT collaboration telephone conference on 12 May 2011<br>
20.1 - Alistair Mills - 12 May 2011<br>
<br>
<table><thead><th>Item  </th><th>     Description                                             </th><th>Res<b>(1)</b></th><th> Status</th><th>      Comment</th></thead><tbody>
<tr><td>20.00 </td><td>Document the meeting                                         </td><td>AM     </td><td>   Done</td><td>            -</td></tr>
<tr><td>20.01 </td><td>Provide feedback to Steve on his authorisation ideas         </td><td>All    </td><td>   Done</td><td>            -</td></tr>
<tr><td>20.02 </td><td>Provide feedback to Jessica on her download stats component  </td><td>All    </td><td>   Done</td><td>            -</td></tr>
<tr><td>20.03 </td><td>Arrange the next meeting for 7 July 2011                     </td><td>AM/TG  </td><td>   Done</td><td>            -</td></tr></tbody></table>


<h2>Meeting 19 - 31 March 2011</h2>

Actions from ICAT collaboration telephone conference on 03 March 2011<br>
19.1 - Alistair Mills - 31 March 2011<br>
<br>
<table><thead><th>New</th><th>     Description</th><th>                                             Res<b>(1)</b></th><th> Status</th><th>  Comment<b>(2)</b></th></thead><tbody>
<tr><td>19.00 </td><td>Document the meeting                                      </td><td>TG/AM  </td><td>   Done</td><td>            -</td></tr>
<tr><td>19.01 </td><td>Add REST web service interface to roadmap                 </td><td>AM     </td><td>     Ok</td><td>            -</td></tr>
<tr><td>19.02 </td><td>Provide the ISIS TopCat reqs to Sri for TopCat roadmap    </td><td>TG     </td><td>      .</td><td>            -</td></tr>
<tr><td>19.03 </td><td>Update the ICAT roadmap and distribute                    </td><td>AM     </td><td>     Ok</td><td>            -</td></tr>
<tr><td>19.04 </td><td>Ticket review at next meeting                             </td><td>TG     </td><td>      .</td><td>            -</td></tr>
<tr><td>19.05 </td><td>Schedule next meeting for WEDNESDAY 13 April 2011         </td><td>TG     </td><td>     Ok</td><td>            -</td></tr></tbody></table>

<h2>Meeting 18 - 3 March 2011</h2>

Actions from ICAT collaboration telephone conference on 03 March 2011<br>
18.1 - Alistair Mills - 3 March 2011<br>
<br>
<table><thead><th>New</th><th>     Description</th><th>                                             Res<b>(1)</b></th><th> Status</th><th>  Comment<b>(2)</b></th></thead><tbody>
<tr><td>18.00 </td><td>Document the meeting                                      </td><td>TG/AM</td><td>   Done</td><td>     -</td></tr>
<tr><td>18.01 </td><td>Be aware that from 01/2012 ILL may have another ICAT dev  </td><td>All</td><td>     Ok</td><td>      -</td></tr>
<tr><td>18.02 </td><td>Inform Roger Downing about the CIC ingest performance     </td><td>AM</td><td>      .</td><td>       -</td></tr>
<tr><td>18.03 </td><td>Create a road map for TopCat 1.1 or TopCat 2              </td><td>SN</td><td>      .</td><td>       -</td></tr>
<tr><td>18.04 </td><td>Provide a paper on rule based authentication              </td><td>TG</td><td>      .</td><td>       -</td></tr>
<tr><td>18.05 </td><td>Update the road map for icat4 and put on GoogleCode Wiki  </td><td>AM</td><td>      .</td><td>       -</td></tr>
<tr><td>18.06 </td><td>Update the workpackage description and put on GoogleCode  </td><td>AM</td><td>      .</td><td>       -</td></tr></tbody></table>

<b>(1) Collaborators<br></b><table><thead><th>init</th><th> Name                        </th><th> Institute                  </th><th>     Present  ||   Apologies</th></thead><tbody>
<tr><td>TG  </td><td>Tom Griffin                  </td><td> STFC ISIS                  </td><td>+ </td><td>           </td></tr>
<tr><td>HG  </td><td>Holger Gebhard               </td><td> ILL                        </td><td>  </td><td>           </td></tr>
<tr><td>SF  </td><td>Steve Fisher                 </td><td> STFC                       </td><td>  </td><td>           || +</td></tr>
<tr><td>AM  </td><td>Alistair Mills               </td><td> STFC                       </td><td>+ </td><td>           </td></tr>
<tr><td>GKM </td><td>Ghita Kouadri Mostefaoui     </td><td> Diamond                    </td><td>  </td><td>           </td></tr>
<tr><td>SN  </td><td>Sri Nagella                  </td><td> STFC                       </td><td>+ </td><td>           </td></tr>
<tr><td>RS  </td><td>Richard Sinclair             </td><td> STFC                       </td><td>  </td><td>           </td></tr>
<tr><td>RF  </td><td>Ron Fowler                   </td><td> STFC                       </td><td>  </td><td>           </td></tr>
<tr><td>HC  </td><td>Harjinder Chahal             </td><td> STFC                       </td><td>+ </td><td>           </td></tr>
<tr><td>KP  </td><td>Kevin Phipps                 </td><td> STFC                       </td><td>  </td><td>           || +</td></tr></tbody></table>

<h2>Meeting 17 - 17 May 2011</h2>

Actions from ICAT collaboration meeting on 17 February 2011 <br>
17.0 - AM, TG - 17 February 2011<br>
<br>
<table><thead><th> Id </th><th> Description </th><th> Res<b>(1)</b></th><th> Tgt<b>(2)</b></th><th> Comment </th></thead><tbody>
<tr><td>17.00 </td><td> Document the actions  </td><td> AM </td><td> Done </td><td> - </td></tr>
<tr><td>17.01 </td><td> Document the road map for icat4 and distribute for comm. </td><td> AM </td><td> Done </td><td> - </td></tr>
<tr><td>17.02 </td><td> Document the collaboration structure and distribute </td><td> AM </td><td> Done </td><td> - </td></tr>
<tr><td>17.03 </td><td> Consolidate open actions into tickets </td><td> TG </td><td> 03/03 </td><td> - </td></tr>
<tr><td>17.04 </td><td> Invite Fabio to give a talk on 24/2 and invite collab. </td><td> TG </td><td> Done </td><td> - </td></tr></tbody></table>

<b>(1) Collaborators:<br></b><table><thead><th>     </th><th> Name                    </th><th> Institute  </th><th> Present </th><th> Apologies </th></thead><tbody>
<tr><td> TG  </td><td> Tom Griffin             </td><td> ISIS </td><td> + </td><td>   </td></tr>
<tr><td> AM  </td><td> Alistair Mills          </td><td> STFC </td><td> +  </td><td>    </td></tr>
<tr><td> GKM </td><td>Ghita Kouadri Mostefaoui </td><td> Diamond</td><td>  + </td><td>   </td></tr>
<tr><td>FB   </td><td>Fabio Bonaccorso         </td><td> ELETTRA </td><td>  </td><td> + </td></tr>
<tr><td>HG   </td><td>Holger Gebhard           </td><td> ILL </td><td>   </td><td> + </td></tr>
<tr><td>SF   </td><td>Steve Fisher             </td><td> STFC </td><td>   </td><td> + </td></tr>
<tr><td>SN   </td><td>Sri Nagella              </td><td> STFC </td><td>   </td><td> + </td></tr>
<tr><td>RS   </td><td>Richard Sinclair         </td><td> STFC </td><td>   </td><td> + </td></tr>
<tr><td>RD   </td><td>Roger Downing            </td><td> STFC </td><td>   </td><td> + </td></tr>
<tr><td>RF   </td><td>Ron Fowler               </td><td> STFC </td><td>   </td><td> + </td></tr></tbody></table>


<h2>Meeting 14 - 7 January 2011</h2>

Actions from ICAT collaboration meeting on 6 January 2011<br>
14.1 - Tom Griffin - 7 January 2011<br>
<br>
Brief summary of discussions<br>
- SF suggestion for refactoring of ParameterSearching<br>
- SN fixed test cases for ParameterSearching<br>
- SF asked what was special about CLF XML ingest. TG and SN gave history of ISIS XML ingest<br>

<table><thead><th> New </th><th> Description </th><th> Res<code>*</code>(1) </th><th> Status </th><th> Comment<code>*</code>(2) </th></thead><tbody>
<tr><td> 14.00 </td><td> Document this meeting </td><td> TG </td><td> Done </td><td> . </td></tr>
<tr><td> 14.01 </td><td> Meetings now to be on alternate Thursdays at 1300GMT, starting 20th Jan 2011 </td><td> All </td><td> - </td><td> - </td></tr>
<tr><td> 14.02 </td><td> Refactoring of Parameter Methods - SF to circulate changes and reasoning </td><td> SF </td><td> - </td><td> - </td></tr>
<tr><td> 14.03 </td><td> All to feedback on SF's suggestions </td><td> ALL </td><td> - </td><td> - </td></tr>
<tr><td> 14.04 </td><td> Document difference between CLF and ISIS XML Ingest </td><td> SN </td><td> Next meeting </td><td> - </td></tr>
<br><br></tbody></table>

<table><thead><th> Updates </th><th> Description </th><th> Res<code>*</code>(1) </th><th> Status </th><th> Comment<code>*</code>(2) </th></thead><tbody>
<tr><td> 12.01 </td><td> Take responsibility for Code documentation </td><td> AM </td><td> Write a test for the API which documents the API </td><td> End Jan 2011 </td></tr>
<tr><td> 12.02 </td><td> Take responsibility for Installation documentation </td><td> SN </td><td> Ron still working on this </td><td> End Jan 2011 </td></tr>
<tr><td> 12.03 </td><td> Take responsibility for My SQL version of icat </td><td> RS/AM </td><td> TG has completed code/app server work. Waiting for RS to resolve SEQUENCE issue  </td><td> End Jan 2011 </td></tr>
<tr><td> 12.04 </td><td> Take responsibility for User Authentication </td><td> TG </td><td> No progress </td><td> End Jan 2011 </td></tr>
<tr><td> 12.05 </td><td> Take responsibility for Reporting </td><td> TG </td><td> Looking at JASPER, BIRT and scientific reports. HG to add to wiki page </td><td> End Mar 2011 </td></tr>
<tr><td> 12.06 </td><td> Take responsibility for Test suite </td><td> AM </td><td> There is a test for xmlingest in progress </td><td> End Jan 2011 </td></tr>
<tr><td> 12.07 </td><td> Take responsibility for Application server independence </td><td> . </td><td> No progress </td><td> . </td></tr>
<tr><td> 12.08 </td><td> Take responsibility for Authorisation/ Permissions </td><td> SN/AM </td><td> No progress </td><td> End Mar 2011 </td></tr>
<tr><td> 12.09 </td><td> Take responsibility for XML Ingest </td><td> AM/SN </td><td> Multiple threading causes 4% failure.  Try single threading. Failure rate possibly worse than though </td><td> End Jan 2011 </td></tr>
<tr><td> 12.10 </td><td> Take responsibility for documenting Data loader (DLS database load) </td><td> RD/AM </td><td> Chase Roger about this </td><td> End Jan 2011 </td></tr>
<tr><td> 12.11 </td><td> Take responsibility for Logging </td><td> SF </td><td> Steve has documented and made minor code change. SF to investigate SN reported unit test issue</td><td> End Jan 2011 </td></tr>
<tr><td> 12.12 </td><td> Take responsibility for Admin documentation </td><td> TG </td><td> AM to provide information about the CLF stored procedures </td><td> End Jan 2011 </td></tr>
<tr><td> 12.13 </td><td> Take responsibility for direct build and test </td><td> SN </td><td> This is being tested in the CLF project </td><td> End Jan  2011 </td></tr>
<tr><td> 12.14 </td><td> Take responsibility for surrogate ids </td><td> TG </td><td> On going </td><td> End Jan 2011 </td></tr>
<tr><td> 12.15 </td><td> Take responsibility for document the parameter search </td><td> NC </td><td> Document emailed. All to review (esp. SF) </td><td> End Jan 2011 </td></tr>
<tr><td> 12.16 </td><td> Take responsibility for API change management </td><td> SN </td><td>  No progress </td><td> End Jan 2011 </td></tr></tbody></table>


<table><thead><th> Completed </th><th> Description </th><th> Res<code>*</code>(1) </th><th> Status </th><th> Comment<code>*</code>(2) </th></thead><tbody>
<tr><td> 12.17 </td><td> Provide spec of new method for search </td><td> NC/HG </td><td> Complete </td><td> - </td></tr>
<tr><td> 13.00 </td><td> Document this meeting </td><td> AM </td><td> Complete </td><td> . </td></tr>
<tr><td> 13.01 </td><td> Meet again on 6 January 2011 at 1300 GMT </td><td> Complete </td><td> - </td><td> - </td></tr></tbody></table>

<h2>Meeting 13 - 17 December 2010</h2>

Actions from ICAT collaboration meeting on 16 December 2010<br>
13.1 - Alistair Mills - 17 December 2010<br>
<table><thead><th> New </th><th> Description </th><th> Res<code>*</code>(1) </th><th> Status </th><th> Comment<code>*</code>(2) </th></thead><tbody>
<tr><td> 13.00 </td><td> Document this meeting </td><td> AM </td><td> Ok </td><td> . </td></tr>
<tr><td> 13.01 </td><td> Meet again on 6 January 2011 at 1300 GMT </td><td> All </td><td> - </td><td> - </td></tr>
<br><br></tbody></table>

<table><thead><th> Updates </th><th> Description </th><th> Res<code>*</code>(1) </th><th> Status </th><th> Comment<code>*</code>(2) </th></thead><tbody>
<tr><td> 12.01 </td><td> Take responsibility for Code documentation </td><td> AM </td><td> Write a test for the API which documents the API </td><td> End Jan 2011 </td></tr>
<tr><td> 12.02 </td><td> Take responsibility for Installation documentation </td><td> SN </td><td> Work has been done on this by Ron - distribute it </td><td> End Jan 2011 </td></tr>
<tr><td> 12.03 </td><td> Take responsibility for My SQL version of icat </td><td> RS/AM </td><td> Work has been done on this by Richard </td><td> End Jan 2011 </td></tr>
<tr><td> 12.04 </td><td> Take responsibility for User Authentication </td><td> TG </td><td> No progress </td><td> End Jan 2011 </td></tr>
<tr><td> 12.05 </td><td> Take responsibility for Reporting </td><td> TG </td><td> Looking at JASPER, BIRT and scientific reports. HG to add to wiki page </td><td> End Mar 2011 </td></tr>
<tr><td> 12.06 </td><td> Take responsibility for Test suite </td><td> AM </td><td> There is a test for xmlingest in progress </td><td> End Jan 2011 </td></tr>
<tr><td> 12.07 </td><td> Take responsibility for Application server independence </td><td> . </td><td> No progress </td><td> . </td></tr>
<tr><td> 12.08 </td><td> Take responsibility for Authorisation/ Permissions </td><td> SN/AM </td><td> No progress </td><td> End Mar 2011 </td></tr>
<tr><td> 12.09 </td><td> Take responsibility for XML Ingest </td><td> AM/SN </td><td> Multiple threading causes 4% failure.  Try single threading </td><td> End Jan 2011 </td></tr>
<tr><td> 12.10 </td><td> Take responsibility for documenting Data loader (DLS database load) </td><td> RD/AM </td><td> Chase Roger about this </td><td> End Jan 2011 </td></tr>
<tr><td> 12.11 </td><td> Take responsibility for Logging </td><td> SF </td><td> Steve has done some work to consolidate in log4j </td><td> End Jan 2011 </td></tr>
<tr><td> 12.12 </td><td> Take responsibility for Admin documentation </td><td> TG </td><td> AM to provide information about the CLF stored procedures </td><td> End Jan 2011 </td></tr>
<tr><td> 12.13 </td><td> Take responsibility for direct build and test </td><td> SN </td><td> This is being tested in the CLF project </td><td> End Jan  2011 </td></tr>
<tr><td> 12.14 </td><td> Take responsibility for surrogate ids </td><td> TG </td><td> On going </td><td> End Jan 2011 </td></tr>
<tr><td> 12.15 </td><td> Take responsibility for document the parameter search </td><td> NC </td><td> Add the documentation to the Google SVN and Wiki </td><td>  End Jan 2011 </td></tr>
<tr><td> 12.16 </td><td> Take responsibility for API change management </td><td> SN </td><td>  No progress </td><td> End Jan 2011 </td></tr>
<tr><td> 12.17 </td><td> Provide spec of new method for search </td><td> NC/HG </td><td> Complete. Sri to verify </td><td> Soon </td></tr></tbody></table>


<code>*</code>(1) Collaborators:<br>
<table><thead><th>   </th><th> Present </th><th> Apologies </th></thead><tbody>
<tr><td> TG Tom Griffin </td><td> + </td><td>   </td></tr>
<tr><td> SN Sri Nagella </td><td> + </td><td>   </td></tr>
<tr><td> AM Alistair Mills </td><td> + </td><td>   </td></tr>
<tr><td> RS Richard Sinclair </td><td>   </td><td> + </td></tr>
<tr><td> RD Roger Downing </td><td>   </td><td>   </td></tr>
<tr><td> HG Holger Gebhard </td><td> + </td><td>   </td></tr>
<tr><td> NC Najor Cruzcruz </td><td> + </td><td>  </td></tr>
<tr><td> SF Steve Fisher </td><td>   </td><td> + </td></tr>
<tr><td> RF Ron Fowler </td><td>   </td><td> + </td></tr>
<tr><td> GKM Ghita Kouadri Mostefaoui </td><td> + </td><td>   </td></tr></tbody></table>

<code>*</code>(2) Comments<br>
The dates shown here are the earliest dates when we can expect to see real progress with the work.<br>
The persons responsible will be expected to provide status on their work at each meeting.<br>
<br>
<hr />
<h2>Meeting 12 - 1 December 2010</h2>

Actions from ICAT collaboration meeting on 01 December 2010<br>
12.1 - Alistair Mills - 01 December 2010<br>
<br>
<table><thead><th> New </th><th> Description </th><th> Res<code>*</code>(1) </th><th> Status </th><th> Comment<code>*</code>(2) </th></thead><tbody>
<tr><td>12.00 </td><td> Document this meeting </td><td> AM </td><td> Ok </td><td> . </td></tr>
<tr><td>12.01 </td><td> Take responsibility for Code documentation </td><td> AM </td><td>- </td><td> End Jan 2011 </td></tr>
<tr><td>12.02 </td><td> Take responsibility for Installation documentation </td><td> SN </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.03 </td><td> Take responsibility for My SQL version of icat </td><td> RS/AM </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.04 </td><td> Take responsibility for User Authentication </td><td> TG </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.05 </td><td> Take responsibility for Reporting </td><td> TG </td><td> - </td><td> End Mar 2011 </td></tr>
<tr><td> 12.06 </td><td> Take responsibility for Test suite </td><td> AM </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.07 </td><td> Take responsibility for Application server independence </td><td> . </td><td> - </td><td> . </td></tr>
<tr><td> 12.08 </td><td> Take responsibility for Authorisation/ Permissions </td><td> SN/AM </td><td> - </td><td> End Mar 2011 </td></tr>
<tr><td> 12.09 </td><td> Take responsibility for XML Ingest </td><td> AM/SN </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.10 </td><td> Take responsibility for Data loader </td><td> RD/AM </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.11 </td><td> Take responsibility for Logging </td><td> SF </td><td> - </td><td> End Jan 2011 </td><td> Note: Transferred to SF </td></tr>
<tr><td> 12.12 </td><td> Take responsibility for Admin documentation </td><td> TG </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.13 </td><td> Take responsibility for direct build and test </td><td> SN </td><td> - </td><td> End Jan  2011 </td></tr>
<tr><td> 12.14 </td><td> Take responsibility for surrogate ids </td><td> TG </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.15 </td><td> Take responsibility for document the parameter search </td><td> NC </td><td> - </td><td>  End Jan 2011 </td></tr>
<tr><td> 12.16 </td><td> Take responsibility for API change management </td><td> SN </td><td> - </td><td> End Jan 2011 </td></tr>
<tr><td> 12.17 </td><td> Provide spec of new method for search </td><td> NC/HG </td><td> - </td><td> Soon </td></tr>
<tr><td> 12.18 </td><td> Meet again on 16 December 2010 at 1300 GMT </td><td> All </td><td> - </td><td> - </td></tr></tbody></table>

<code>*</code>(1) Responsibilities of participants:<br>
<table><thead><th>   </th><th>   </th><th> Primary </th><th> Secondary </th></thead><tbody>
<tr><td> TG </td><td> Tom Griffin </td><td> 4 </td><td>   </td></tr>
<tr><td> SN </td><td> Sri Nagella </td><td> 4 </td><td> 1 </td></tr>
<tr><td> AM </td><td> Alistair Mills </td><td> 3 </td><td> 2 </td></tr>
<tr><td> RS </td><td> Richard Sinclair </td><td> 1 </td><td>   </td></tr>
<tr><td> HG </td><td> Holger Gebhard </td><td> 1 </td><td>   </td></tr>
<tr><td> NC </td><td>  Najor Cruz-cruz </td><td> 2 </td><td>   </td></tr>
<tr><td> RD </td><td> Roger Downing </td><td> 1 </td><td>   </td></tr>
<tr><td> SF </td><td> Steve Fisher </td><td> 1 </td><td>   </td></tr>
<tr><td> RF </td><td> Ron Fowler </td><td>   </td><td>   </td></tr></tbody></table>

<code>*</code>(2) Comments<br>
The dates shown here are the earliest dates when we can expect to see real progress with the work.<br>
The persons responsible will be expected to provide status on their work at each meeting.<br>
<br>
<h2>Meeting 11 - 19 November 2010</h2>

Actions from ICAT collaboration meeting on 18 November 2010<br>
11.1 - Alistair Mills - 19 November 2010<br>
<br>
<table><thead><th> New   </th><th> Description                                                                                        </th><th> Resp </th><th> Status      </th><th> Comment </th></thead><tbody>
<tr><td> 11.01 </td><td> Improve way of capturing documentation/service requests; copy existing requests into Google groups </td><td> TG   </td><td> In progress </td><td> Soon </td></tr>
<tr><td> 11.02 </td><td> Provide requirements for logging/reporting in ICAT                                                 </td><td> ALL  </td><td> See 12.05   </td><td> Subsumed by 12.05 </td></tr>
<tr><td> 11.03 </td><td> Provide ideas for test suite                                                                       </td><td> ALL  </td><td> See 12.06   </td><td> Subsumed  by 12.06 </td></tr>
<tr><td> 11.04 </td><td> Meet again 2nd Dec                                                                                 </td><td> ALL  </td><td> -           </td><td> - </td></tr>