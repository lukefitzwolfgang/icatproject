# Database Installation #

The ICAT/Data Portal installation notes assume that you already have a working oracle database with the follwoing database schemas/users installed

  * 1) icat
  * 2) icatuser
  * 3) dataportal

These instructions assume that you have installed Oracle XE 10G.
Please download and extract the latest ICAT/Data Portal [download bundle](http://code.google.com/p/icatproject/downloads/). This will be known as `<INSTALLATION_HOME>`.

To install the schemas necessary for ICAT & the Data Portal (icat, icatuser, dataportal):

1. CD to the directory containing the file install\_icatisis.sql i.e. `<INSTALLATION_HOME>/database/icat`

2. Run sqlplus without connecting to the database (sqlplus /nolog)

3. Run the script install\_icatisis.sql (@install\_icatisis.sql).

Note: Running sqlplus from a directory other than `<INSTALLATION_HOME>/database/icat` may cause the installation script to fail.

The installation script will request the sys password so that it can connect to the database and create the schemas (icat, icatuser, dataportal) and the

directory needed for the creation of the external tables.  The installation script will ask for a password to be assigned to each of the newly created schema

users (icat, icatuser and dataportal)

e.g.

/tmp/icat\_download\_bundle/database/icat>sqlplus /nolog

SQL\*Plus: Release 10.2.0.1.0 - Production on Fri Oct 10 12:10:51 2008

Copyright (c) 1982, 2005, Oracle.  All rights reserved.

SQL> @install\_icatisis.sql

I C A T I S I S   I N S T A L L

This script will create ICATISIS schema objects in a named schema in a
specified database.

Enter Database Name             : XE

Enter SYS password              : mysyspasswd

Enter icat password       	: myicatpasswd

Enter dataportal password       : mydataportalpasswd

Enter icatuser   password       : myicatuserpasswd

Enter External tables location 	: /tmp/extloc



creating user icat

...
...