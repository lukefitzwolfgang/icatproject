$Id$ 

The following list, is the set of examples of how to invoke the API from a simple java program:

Authenticate
Search

1. Check out the following branch from the SVN repository: /sw/icat_api_examples/trunk

2. Build the software using ant with the following command: ant

3. Edit the file example.properties to point to the correct application end point such as: https://astra-gemini.esc.rl.ac.uk:8181/ICATService/ICAT?wsdl

4. Execute the program of choice.  For example to use Authenticate use: 
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Authenticate


5. Expect a response such as: Session ID = d7cfaac0-1269-4398-874d-23601ca26b1b

6. For tests on the Search method, use the following:
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Search Dataset.id
java -cp icat_api_examples.jar:icat-client.jar uk.icat.examples.Search "Dataset.id [datasetType IN ('GS', 'GQ')]"
./test_one.sh                                                  "Search  Dataset.id [datasetType IN ('GS', 'GQ')]"

- the end - 

