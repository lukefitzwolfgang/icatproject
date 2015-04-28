**Contents**



# Release criteria for ICAT 4.3 #

## Introduction ##

The target data for the release of ICAT 4.3 is the second half of August 2013.

This document sets out a set of criteria to be met before the release is made.  This document does not provide detail for the verification of the requirements.

The plan for the service verifications provides for testing of the ICAT 4.3 in October 2013.

## Criteria ##

I propose the following release conditions for ICAT 4.3:

### Traceable materials for test ###

The materials which are made available must check out from SVN easily and compile without user intervention to form a set of usable materials such as .ear files.  The materials will correspond to the documentation.

### Coexistence with icat42 ###

An icat 4.3 which is part of the federation of icats must interoperate with a collection of icat 4.3 and icat 4.2.  In particular a single Topcat should be support a mixture of ICATs.  The credentials which are presented to icat 4.3 should be identical to those presented to an icat 4.2.

### README file up to date ###

Any readme files should contain up to date information.  Where possible, essential information should not be contained uniquely in a readme file.  The information should be in the standard documentation also.

### Harmonisation of ICAT with other members of the ICAT family ###

An icat 4.3 should operate correctly all other current members of the ICAT family of compenents, eg:

  * Topcat 1.8 and 1.9
  * authn\_db 1.0.0
  * authn\_ldap 1.0.0
  * authn\_anon 1.0.1
  * download\_manager 1.0.0

The installation materials should contain a set of installation and operational scripts which are well harmonised.  Topcat 1.9 and IDS 1.0 have scripts which differ from those of ICAT 4.2.

### Satisfactory performance in SV[6](6.md) on October 2013 ###

There is a Service Verification of Pandata planned for October 2013.  This will include a mixture of ICATs using ICAT 4.2 and ICAT 4.3.  Any anomolies detected will be addressed.  Performance includes execution time, reliability and other factors.

### Realistic range of installation conditions ###

The release should operate correctly with:

  * icat database on derby on localhost;
  * icat database on oracle on localhost and remote host;
  * icat database on mysql on localhost and remote host.

The release materials should operate correctly in at least the following conditions:

  * all of the services ie icat, glassfish, database, authn, ids on a single host;
  * all of the services ie icat, glassfish, database, authn, ids on a different host for each; icat and glassfish may be on a single host.

### All tests pass in one pass on all configurations ###

The tests which are provided as part of the service verification are simple and should pass easily and quickly on all configurations.

### Reasonable time for response ###

There are now at least two monitors in common use at RAL, ie SLS and

### Release note is up to date ###

There will be release notes in the distribution and they should contain information on late items, such as a list of deployment examples.

### All open tickets for ICAT reviewed ###

All open tickets on ICAT should be reviewed before the release.  If appropriate, the release should close a ticket or the ticket should be updated.

### Outstanding work list ###

There has to be a list of outstanding work. This can be a pointer to something else but it must be something static so that the list is static and corresponds to the conditions at the time of the test.

## - the end - ##