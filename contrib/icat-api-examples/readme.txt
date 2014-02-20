$Id$ 

The following list, is the set of examples of how to invoke the API from a simple java program:

Authenticate
Search

1. Check out the following branch from the SVN repository: /contrib/icat-api-examples

2. Build the software using ant with the following command: ant

3. Edit the file example.properties to point to the correct application end point such as: https://astra-gemini.esc.rl.ac.uk:8181/ICATService/ICAT?wsdl

4. Execute the program of choice.  For example to use Authenticate use: 

export CLASSPATH=\
icat_api_examples.jar:\
icat.client-4.3.0.jar:\
ids.client-1.0.0.jar:\
jackson-databind-2.2.0.jar:\
jackson-core-2.2.0.jar:\
jackson-annotations-2.2.0.jar

java uk.icat.examples.Authenticate

Alternatively, the following script hides all of the details:

./icat_cli.sh Authenticate

5. Expect a response such as: Session ID = d7cfaac0-1269-4398-874d-23601ca26b1b

- the end - 

