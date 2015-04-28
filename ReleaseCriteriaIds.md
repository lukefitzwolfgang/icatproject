**Contents**


# Release criteria for the reference implementation of IDS #

This note was written as "Release criteria for IDS download manager technology preview known as IDS 0.0".  We have decided not to have a technology preview of the download manager.  More criteria are relevant, since the scope of the release is much wider than that of the original.  Among the obvious additions are:

  * the requirements for deployments for ISIS, DLS and LSF;
  * the conformance of the reference implementation to the specification.

## Introduction ##

The target data for the release of the reference implementation of IDS is autumn 2013.  There are deployments of limited editions of IDS in use in CLF, DLS and ISIS.  In this document, expressions such as "IDS release", should be understood as "the release of the IDS reference implementation".

This document sets out a set of criteria to be met before the release is made.  This document does not provide detail for the verification of the requirements.

The plan in Pandata for service verifications provides for testing of the IDS in service verifications towards the end of 2013.  The Service Verifications are referred to as SV[n](n.md), for example SV[4](4.md).

  * http://pandatawp4.wordpress.com

## Criteria ##

I propose the set of release conditions for the IDS.  The index of this page provides a list.

### Traceable materials for test ###

The materials which are made available must be a clean, compilable copy which is available in an SVN.  The source of the material must be well defined, so that they can be easily rebuilt.

### Coexistence with icat 4.2 and icat 4.3 ###

The materials for the quick install of ICAT 4.2 are at the following address:

**http://code.google.com/p/icatproject/source/browse/svn/ops/icat42**

On a clean machine, it should be possible to install the IDS so that it coexists with the existing icat 4.2.

### README file is up to date ###

At the moment, there is missing information in the README file.  In particular it does not tell the installer the location of the file downloadmanager.properties.

The format of the README.txt file should be harmonised with that of the readme.txt for the icat42 installation.

### Harmonisation ###

The readme files and scripts for controlling and installing the download manager are quite different from those of ICAT 4.2  We should agree on a common format for ICAT 4.3 and for IDS.

### Satisfactory performance in SV[4](4.md) ###

SV[4](4.md) includes tests for a range of data conditions from local and remote locations and uses both a script interface and the Topcat interface for the download of materials.  The intention is that these tests remain valid and provide a convenient, self verifying quick test.  The test verifies functionality.  It does not verify performance.

### Range of installation conditions ###

The release should operate correctly with:
  * icat 4.2, 4.2.1, 4.2.2, 4.2.3, 4.2.4, 4.2.5;
  * dm database on derby on localhost;
  * dm database on oracle on localhost and remote host;
  * dm database on mysql on localhost and remote host.

### All tests pass in one pass on all configurations ###

If it is necessary to make changes to the software in order to succeed in a configuration, it is necessary to repeat all of the tests to show that one set of materials works for all configurations.

### Reasonable time for response ###

The logs from the tests in SV[4](4.md) reveal the time for the preparation of the download zip file (zip rate) and the time for the transer time (transfer rate); in addition there is a latency which is required for catalogue access and process (latency time).  Until we have more experience of using these, it is not possible to give values, as the performance is strongly influenced by the operating environment.  Initial tests using www.icatproject.org serving to abm65.esc.rl.ac.uk, suggest that the zip rate is around 5MB/second, the transfer rate is around 3 MB/second, and the latency around 5 seconds.

### Release note is up to date ###

The release note should include a list of the test conditions, and the performance.

### Outstanding work list ###

There has to be a list of outstanding work.  This can be a pointer to something else but it must be something static so that the list is static and corresponds to the conditions at the time of the test.

### - the end - ###