# A note on anonymous / open access to certain metadata #

## Use Case/Requirements ##

Certain metadata has no intrinsic value by itself, so does not need protecting, but is of use for finding a file where the user has access to the file by virtue of thier physical location

Examples include: Run Number, Instrument, Run Start datetime, Run End date/time

## ICAT3 ##
ICAT3 does not allow permissions to be set at any level below dataset, so such fine-grained permissions are not possible (without significant API changes)

## ICAT4 ##
The rules based authorisation and querying in ICAT4 does make this possible
An example rule would be

"anon", "DataFileParameter", "R", "[datasetParameterPK.Name = 'RunNumber']"

## Anonymous Access ##

This can be achieved in a couple of ways: <br><br>

1) The no change way<br>
By creating an account 'anon' with password 'open' (for example) and associating this account with<br>
<br>
2) The minimal change way<br>
Create an icat4-user-component (or modify an existing such as icat3-user-ansto) to have an additional logon method ("logonAnon") which simply returns a session Id associated with the 'anon' account described in the rule. This could also log the IP address of the client calling that method, and associated that with the session Id. The IP address could also be used as part of the authentication e.g. only allow access if in the range 130.246.x.x<br>
<br>
3) The third way.<br>
Modify the other (query) methods to work without a session ID argument (or null argument). The interceptors would need adapting to cope with this as the first thing they do is check for a valid sessionId