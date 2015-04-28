

# Introduction #

Instructions for participation in the service verification for Pandata WP4 on 1 February 2013.

In order to participate in the service verification, you should do the following things:

  * inform Alistair Mills that you intend to participate;

  * download the materials and try the tests now;

  * inform Alistair if you have a service to offer;

  * attend the telco on Thursday 31 January at 15h UT;

  * inform Alistair of any technical difficulties.

If you have a service, send the connection inform to Alistair and he will test it, and publish the connection information into the SVN.  If you do not wish to publish the password, then tell Alistair.  As this is a very simple test to test ICATs, revealing a password to a test account on a test icat is very unlikely to weaken the security of your Institute.  Be careful, your security officer may have another opinion!

Please read the following:

  * http://pandatawp4.wordpress.com/

  * http://code.google.com/p/icatproject/wiki/ServiceVerification

  * http://code.google.com/p/icatproject/source/browse/ops/icat42/test422/readme.t2t

Please check out the materials using the following commands:
```
svn co https://icatproject.googlecode.com/svn/ops/icat42/test422
```

## Status ##

The connection information in this table is illustrative only.  Downloading the test materials from the SVN, provides the information so that there is no need when testing a client to type any of this information.

| Partner     | Contact                      | Client | Url                                                         | Server-connection | Icat  | Username | Password       | Authn | Directory | Trust-store file                              |
|:------------|:-----------------------------|:-------|:------------------------------------------------------------|:------------------|:------|:---------|:---------------|:------|:----------|:----------------------------------------------|
| ALBA        | Daniel Salvat                | yes    | https://icattest.cells.es:2081                              | cells             | 4.2.2 | .        | .              | .     | .         | .                                             |
| DESY        | Jürgen Starek                | yes    | (https://icat.desy.de:8181 if security approves)            | desy              | 4.2.2 | icat42   | secret         | db    | test42    | .                                             |
| DLS         | Ghita Kouadri Mostefaoui     | yes    | http://www.icatproject.org:5080                             | dls               | 4.2.2 | icat42   | secret         | db    | test42    | not required                                  |
| ELETTRA     | Milan Prica                  | yes    | https://icat-elettra.grid.elettra.trieste.it:8181           | elettra           | 4.2.2 | test     | secret         | db    | .         | _icat-elettra\_grid\_elettra\_trieste\_it\_8181_|
| ESRF        | Nicola Bessone               | yes    | ?                                                           | esrf              | 4.2.2 | .        | .              | .     | .         | .                                             |
| FRM         | .                            | ?      | ?                                                           | frm               | .     | .        | .              | .     | .         | .                                             |
| HZB         | .                            | ?      | ?                                                           | hzb               | .     | .        | .              | .     | .         | .                                             |
| ILL         | Jamie Hall                   | yes    | https://icat.ill.rf                                         | ill               | 4.2.2 | .        | .              | .     | .         | .                                             |
| ISIS        | Tom Griffin                  | yes    | ?                                                           | isis              | .     | .        | .              | .     | .         | .                                             |
| JCNS        | Christian Felder             | yes    | http://apps.jcns.fz-juelich.de:5080/ICATService/ICAT?wsdl   | jcns              | 4.2.2 | .        | .              | .     | .         | not required                                  |
| LLB         | Stephane Longeville          | yes    | ?                                                           | llb               | .     | .        | .              | .     | .         | .                                             |
| MAXLab      | Krister                      | yes    | ?                                                           | max               | .     | .        | .              | .     | .         | .                                             |
| PSI         | Leonardo                     | yes    | ?                                                           | psi               | .     | .        | .              | .     | .         | .                                             |
| SOLEIL      | Idrissou Chado               | yes    | https://icat.synchrotron-soleil.fr                          | soleil            | 4.2.2 | .        | .              | .     | .         | _icat\_synchrotron-soleil\_fr_|
| STFC        | Alistair Mills               | yes    | http://www.icatproject.org:5080                             | domain5           | 4.2.2 | icat42   | icat42passwd   | db    | test42    | not required                                  |
| .           | .                            | .      | .                                                           | .                 | .     | .        | .              | .     | .         | .                                             |
| ANS         | Steve Androulakis            | yes    | .                                                           | .                 | .     | .        | .              | .     | .         | .                                             |
| SNS         | Shelly Ren                   | yes    | .                                                           | .                 | .     | .        | .              | .     | .         | .                                             |

## - the end - ##