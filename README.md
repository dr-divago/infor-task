# infor-task

There are 2 module in the project task1 and task2

### TASK1
The task doesn't quite define what order mean, the customerId is in the format C{number} for most of the customerID where number is from 1 to 24. I decided to clean the input (remove " and modify every customerID not in the format C{number}. I created a example.txt where the client are not in order according to the format of clientID defined. 

String Output  
`java -jar task1-1.0-SNAPSHOT-fat.jar "path/to/example.txt"`
 

HTML Output  
`java -jar task1-1.0-SNAPSHOT-fat.jar "path/to/example.txt" true`


### TASK2
I used vert.x library for creating the server and connecting to the remove server that provie XML.  
Reason is because vert.x is a library based on non-blocking IO, asyncronous. That allow better performance and easy 
to develop async system. 

#### Start Server  
`java -jar task2-1.0-SNAPSHOT-fat.jar`

Server will listen to port 8080. 
#### Endpoint
/{title}  
Example use:  
`curl localhost:8080/lost`  
 
{title} is the title of the movie to get the json from

#### Configuration :
`java -jar task2-1.0-SNAPSHOT-fat.jar -conf /path/to/config.json`

Format configuration:  
`
{  
  "http.port" : "8080",  
  "apikey": "",  
  "baseUrl": "www.omdbapi.com"  
}
`

apikey is required to query www.omdbapi.com
