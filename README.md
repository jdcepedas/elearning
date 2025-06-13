# Getting Started

## Running the project

Run the ElearningApplication class as Java Application. 
This will launch the Spring Boot application with the embedded Tomcat server.


### Running the Postgres DB

For the application to come up we need to have a postgres db running.
To set up a local postgres DB with docker run the following commands:

```
docker run --name elearning \
-e POSTGRES_DB=eldb \
-e POSTGRES_USER=eldb \
-e POSTGRES_PASSWORD=eldb \
-p 5432:5432 \
-d postgres
```

Now set the corresponding values in the application-local.properties file

### Guides

