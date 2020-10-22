# Network_Programming
Laboratory work for Network Programming university course

## Task
1. Pull a docker container (alexburlacu/pr-server) from the registry;
2. Run it (port 5000);
3. Access the root route of the server and find your way to register (The access token that you get after accessing the /register route must be put in a HTTP header of the subsequent requests under the key X-Access-Token key);
4. Extract data from data key and get next links from link key (hardcoding the routes is strictly forbidden. You need to "traverse" the API)
6. Access token has a timeout of 20 seconds, and you are not allowed to get another token every time you access a different route. So, one register per program run
7. Once you fetch all the data, convert it to a common representation, doesn't matter what this representation is
8. The final part of the lab is to make a concurrent TCP server, serving the fetched content, that will respond to (mandatory) a column selector message, like `SelectColumn column_name`, and (optional) `SelectFromColumn column_name glob_pattern`

## Implementation
### Requirements
* **Java Version** : 15
* **Docker Container**: alexburlacu/pr-server

### Run
1. Pull docker image: ```` docker pull alexburlacu/pr-server ````
2. Run Image: ```` docker run -p 5000:5000 <imageId>````
3. Run project


   






