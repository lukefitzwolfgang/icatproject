The initial service verification was carried out on Friday 9th November 2012.  The participation in the service verification was excellent with 11 of the 15 partners providing client tests, and four of the partners providing services for test.  Both of our associates from outside Europe participated.

The following graphic provides a summary of the results:

![http://icatproject.googlecode.com/svn/ops/miscellany/png/sv0-2.png](http://icatproject.googlecode.com/svn/ops/miscellany/png/sv0-2.png)

Figure 1: Service Verification Summary

The following can be observed in the graphic:

> •Most of the connections were successful (85%);

> •Some partners had failures connecting to the services at JCNS and ELETTRA.

The following partners provided servers (4/15):

> •Diamond – 4.2;

> •Elettra – 4.2;

> •JCNS – 4.2;

> •STFC x 4 – 4.0 x 2, 4.1, 4.2;

The following partners provided clients (11/15):

> •Alba – Spain;

> •DESY – Germany;

> •DLS – UK;

> •Elettra – Italy;

> •ESRF – France;

> •ILL – France;

> •ISIS – UK;

> •JCNS – Germany;

> •PSI – Switzerland;

> •Soleil – France;

> •STFC – UK.

The following associates provided clients (2/2):

> •ANS – Australia;

> •SNS/ORNL – USA.

The test involved each client logging on to each of the available servers in turn and logging the session identifier from the server.

The test provided a reasonably large and diverse set of conditions for the tests and included:

> •ICAT 4.0, 4.1, 4.2;

> •both http and https protocols;

> •the db authentication mechanism using oracle, derby and mysql databases;

> •11 different firewalls for outbound connections;

> •4 firewalls for inbound connections;

> •3 http connections on ports 8080, 2080, 5080;

> •3 https connections on ports 4081, 8181, 9111;

> •connections from 11 countries;

> •connections from three continents.

Several of the collaborators ran the tests from home where their connection regime is simpler than within their institution and confirmed that their difficulties in connection were due to their institutions.

In addition to point-to-point tests, STFC ran 10 continuous instances of the tests for 10 days.  This ensured that each of the servers had a base load of 1 connection per second for 10 days.  This test passed with 100% success rate.

We had participation rates as follows:

> •servers: 27%;

> •clients: 76%;

> •coverage: 22%.

The lessons learned were the following:

> •server services should use standard ports, preferably https:443, alternatively https:8443;

> •server services should use certificates from a recognised certificate authority;

> •server services should authenticate using their site preferred authentication mechanism.

Many of the clients had difficulties connecting to servers outside their firewall.  These difficulties can be eliminated with the use of standard deployment of the services.  When the server is appropriately configured, access to the server is handled by the firewall.  For example, it is generally not necessary from within a firewall to inform the firewall administrator in order to access secure services such as internet banks.

For future service verifications, we intend to do the following:

> •increase participation rates each month with the aim of achieving 100% coverage in March 2013;

> •maintain the variety of test conditions;

> •ensure that genuine authentication mechanisms are in use;

> •ensure that the servers are configured as production services.

Alistair Mills, STFC, UK