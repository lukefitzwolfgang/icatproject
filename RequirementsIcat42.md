

# Requirements for ICAT 4.2 #

The purpose of this page is to provide a place where people can write their shopping lists of requirements for ICAT 4.2.  As we translate these requirements into actions, we shall probably use a ticket to track the work.

## General ##

The focus of this release is to support the work of the existing facilities using ICAT ie ISIS, SNS, DLS and CLF.

  * Include licence in downloads and maven builds

## ISIS ##

(E = essential, D= Desirable)
  * (E) A **fully supported** upgrade path from ICAT 3.3 to 4.2;
  * (E) Maintain a legacy icat3-exposed web service API to allow gradual migration. This is essential for established users to realistically migrate;
  * (E) Maintain the ability to create many datafiles in an existing dataset in one web service call - ideally some kind of ingestXml call;
  * (E) Usage reports (how many people use ICAT, how many people downloaded dataset x, how many times has anyone asked for data >3 years old);
  * (E) TopCAT - add ability for data owners to grant permission on their datasets to others;
  * (E) Upload of derived data through TopCAT;
  * (D) A way of grouping parameter names - to solve the 86 variations of 'temperature' problem (temp, temp1, t1 sampletemp etc). Currently this makes searching nearly impossible;
  * (D) TopCAT - expose information about the investigators;
  * (D) TopCAT - download all metadata for an investigation or dataset;
  * (D) Basic visualize  or view data in TopCAT, possibly using jpowder or similar;
  * (D) Interface between SDB and ICAT.  The details of this requirement has to elaborated with Chris Kruk in eScience.

## SNS ##

To provide better system performance and user experience, we wanted to offer live view and auto data reduction to SNS users in the future. SNS has two major requirements:

1. Provide a new API method to allow a quick metadata search without a user login. The metadata request seems to vary from instrument to instrument. For example our eqsans instrument scientist asked for the following metadata: start time, run title, run notes, total run time, total accelerator current, sample to detector distance, wave length band used for a given run number or a range of run numbers. They wanted these metadata to be available in mantid via icat without having to login into mantid/icat. Today, these metadata are obtained by calling a script that harvests pieces from different metadata files on the instrument analysis machine.

2. Another request is to enable cross references among the runs. How to best guess the background runs like empty run, transmission run, vanadium run etc that are needed for data reduction? Can this be done without a user login?

## DLS ##

Please meet the following requirements:

## CLF ##

Please meet the following requirements:

## CRISP ##

  * (D) Provide support for data mining

## ESRF ##

  * Visualization of data in TopCAT

## Eudat ##

Please meet the following requirements:

## ILL ##

Please meeting the following requirements:


## Pandata ##

  * REST web service (PSI request at several meetings;
  * A Topcat server for Pandata must be accessible over the internet on standard ports and have a standard certificate and be visible from within an institutional firewall without modifications to the firewall;
  * Any ICAT service deployed for federation by a remote Topcat which is within an institutional firewall must offer its data in a manner that makes it visible to the remote Topcat without a change to the firewall of the institutional firewall of the Topcat.


  * Clean plugin APIs

> In order to facilitate the adaption of ICAT to the different users' computing environments, ICAT should have two clean and well-documented plugin APIs for:

> - Authentication / Authorization

> - Interface (what is now the WSDL connector)

A note: In Trieste, I have heard of a pluggable authentication module for Umbrella. I would be very interested in details about this. If ICAT already has such an interface perhaps I am misunderstanding the authentication API.

  * Developers' documentation

It would be interesting to know something about the status of the different branches in the repository, as well as setting up a development environment. The current release is, however, already providing very good documentation.

  * Migration assistance

The users require a well-documented and easy migration path between ICAT releases in order to support the idea of long-term data storage.

  * Alternatives to Glassfish

As some sites do not use Glassfish for the JEE applications, it should be possible to deploy ICAT on other application servers such as Web Logic and Jboss.


## Others ##

  * Integration of a work flow management system such as Taverna



## - the end - ##