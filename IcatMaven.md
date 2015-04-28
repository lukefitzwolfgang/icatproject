# Maven Build Instructions for ICAT4 #
This guide is specific for Linux distributions.

## Required Software Components ##

  * Java JDK (1.6 or later);
  * Maven;
  * SVN client.

## Instructions ##

1.  Set PATH in ~/.bashrc to include JAVA\_HOME and MAVEN\_HOME, the home directory of Maven;

2.  Checkout a copy of ICAT using the SVN, changing destination directory and user name appropriately;

```
svn checkout https://icatproject.googlecode.com/svn/icat3_api/trunk/ icatproject --username youremail@gmail.com
```

3.  Configure Maven using the settings.xml file;

```
$MAVEN_HOME/conf/settings.xml
```

> Add the local repository as follows:
```
<localRepository>~/.m2/repository/</localRepository>
```

4.  Download ojdbc14.jar from: [http://www.oracle.com/technetwork/database/enterprise-edition/jdbc-10201-088211.html](http://www.oracle.com/technetwork/database/enterprise-edition/jdbc-10201-088211.html)

> Add ojdbc14.jar to the local maven repository;
```
mvn install:install-file -Dfile=/path/to/ojdbc14.jar -DgroupId=oracle -DartifactId=ojdbc -Dpackaging=jar -Dversion=14
```

> Expect the following output:
```
[INFO] Scanning for projects...
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] Building Maven Stub Project (No POM) 1
[INFO] ------------------------------------------------------------------------
[INFO] --- maven-install-plugin:2.3.1:install-file (default-cli) @ standalone-pom ---
[INFO] Installing /home/glassfish3/Downloads/ojdbc14.jar to /home/glassfish3/.m2/repository/oracle/ojdbc/14/ojdbc-14.jar
[INFO] Installing /tmp/mvninstall7231744228400656295.pom to /home/glassfish3/.m2/repository/oracle/ojdbc/14/ojdbc-14.pom
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 1.374s
[INFO] Finished at: Thu Dec 08 10:40:10 GMT 2011
[INFO] Final Memory: 2M/15M
[INFO] ------------------------------------------------------------------------
```


5.Run the Maven command in the top level directorary of ICAT.
```
cd icatproject
mvn package
```

> Expect the following output:
```
.
.
Downloaded: http://repo1.maven.org/maven2/commons-digester/commons-digester/1.7/commons-digester-1.7.jar (137 KB at 78.0 KB/sec)
Downloading: http://repo1.maven.org/maven2/bouncycastle/bcprov-jdk14/138/bcprov-jdk14-138.jar
Downloaded: http://repo1.maven.org/maven2/commons-collections/commons-collections/2.1/commons-collections-2.1.jar (162 KB at 79.6 KB/sec)
Downloading: http://repo1.maven.org/maven2/org/bouncycastle/bctsp-jdk14/1.38/bctsp-jdk14-1.38.jar
Downloaded: http://repo1.maven.org/maven2/org/bouncycastle/bctsp-jdk14/1.38/bctsp-jdk14-1.38.jar (23 KB at 18.0 KB/sec)
Downloading: http://repo1.maven.org/maven2/org/bouncycastle/bcprov-jdk14/1.38/bcprov-jdk14-1.38.jar
Downloaded: http://repo1.maven.org/maven2/bouncycastle/bcmail-jdk14/138/bcmail-jdk14-138.jar (188 KB at 94.0 KB/sec)
.
.
[INFO] Building jar: /home/glassfish3/icat_mvn/trunk/icat3-ear/target/icat3-ear-1.0.0-SNAPSHOT-tests.jar
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] icat3-core ........................................ SUCCESS [2:10.047s]
[INFO] icat3-data ........................................ SUCCESS [3.931s]
[INFO] icat3-jaxb ........................................ SUCCESS [2.957s]
[INFO] icat3-logging ..................................... SUCCESS [12.261s]
[INFO] icat3-user-ldap ................................... SUCCESS [3.123s]
[INFO] icat3-user-ansto .................................. SUCCESS [2.041s]
[INFO] icat3-exposed ..................................... SUCCESS [10.342s]
[INFO] icat3-download .................................... SUCCESS [2.710s]
[INFO] icat3-reporting ................................... SUCCESS [1:57.287s]
[INFO] icat3-ear ......................................... SUCCESS [11.831s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4:57.123s
[INFO] Finished at: Thu Dec 08 10:45:48 GMT 2011
[INFO] Final Memory: 28M/67M
[INFO] ------------------------------------------------------------------------
```

## - the end - ##