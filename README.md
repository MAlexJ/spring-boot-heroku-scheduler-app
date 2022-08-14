## Application configuration:

### ENV Variables

#### Required ENV Variables

1. MONGODB_DATABASE - MongoDb database name
2. MONGODB_URI - URI to MongoDb

#### Optional ENV Variables

3. PORT - PORT - spring application port, by default - 8081
4. SCHEDULER_CRONE - job time to execute for standalone running (override by CONFIG_SERVER_URI)
5. ZIPKIN_PORT - URL to Zipkin distributed tracing system, by default - http://localhost
6. CONFIG_SERVER_URI - URL to configuration properties to spring configuration server

### Gradle:

* '<b>updateProcfile</b>' task designed to update Procfile file that HEROKU service uses for deployment
* [Detecting build version and time at runtime](https://www.vojtechruzicka.com/spring-boot-version/)

### MongoDb:

* Cloud db: https://cloud.mongodb.com/
* [Working with Spring Data Repositories](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#repositories)

##### How to set up database:

1. Login to Mongo cluster
    * https://account.mongodb.com/account/login


2. Get or set new admin credentials:<br>
    * login
    * password
    * and set permission - Atlas admin


3. Create a Shared Cluster
    * FREE >> Sandbox (Shared RAM, 512 MB Storage)


4. Go to Quick Start Security

    * Set up a username and a new password


5. Set up network access for database:

    * Network Access tab >> Edit IP Access List Entry >>  Allow Access from Anywhere


6. Database Deployments Options <br>
   Go to: Database >> Database Deployments >> Connect button <br>
    * Select your driver and version: Java and latest version of driver <br>
    * Add your connection string into your application code <br>

   example: <br>
   <code>
   mongodb+srv://<username>:<password>@cluster0.8seexos.mongodb.net/?retryWrites=true&w=majority
   </code>


7. Create new DATABASES
    * Go to: DATABASE >> Cluster >> Collections >> Add My Own Data
    * Set up Database name (example: sampleDB ) and collection name (example: collectionDB)


8. Set up required ENV Variables for application:
    * MONGODB_DATABASE - Database name from DATABASE >> Cluster >> Collections  (example: sampleDB )
    * MONGODB_URI - mongodb+srv://<username>:<password>@cluster0.8seexos.mongodb.net/?retryWrites=true&w=majority

### Postman

* postman collection for test API: /info/Scheduler_App_API.postman_collection.json

## Application info/documentation:

#### Spring boot:

* [How to Start and Stop Scheduler in Spring Boot](https://www.yawintutor.com/how-to-start-and-stop-scheduler-in-spring-boot/)
* [Running Scheduled Jobs in Spring Boot](https://reflectoring.io/spring-scheduler/)

#### Spring Zipkin:

* [Spring Cloud Sleuth](https://medium.com/@kirill.sereda/spring-cloud-sleuth-zipkin-%D0%BF%D0%BE-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8-9f8504581dae)

#### Spring paging and sorting:

* [Paging and Sorting](https://docs.spring.io/spring-data/rest/docs/current-SNAPSHOT/reference/html/#paging-and-sorting)
* [Spring @PageableDefault](https://stackoverflow.com/questions/41486047/use-pageabledefault-with-spring-data-rest)
* [Spring spring-data-web @PageableDefault](https://www.baeldung.com/spring-data-web-support)

#### Spring Boot Actuator:

* [How to Enable All Endpoints](https://www.baeldung.com/spring-boot-actuator-enable-endpoints)
* [Web API Documentation](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/)

#### Spring Boot Client Config

1. add new libraries to gradle build file

```
implementation 'org.springframework.cloud:spring-cloud-starter-config'
implementation 'org.springframework.cloud:spring-cloud-starter-bootstrap'
```

2. create new bootstrap.yml file in resource project folder
3. fill bootstrap.yml file with application name and config server URI

```
spring:
  application:
    name: heroku-scheduler-app
  cloud:
    config:
      uri: ${CONFIG_SERVER_URI:http://127.0.0.1:8888}
```

#### Spring mapper:

* [MapStruct documentation](https://mapstruct.org/documentation/stable/reference/html/)

#### Java Script:

* [Server Sent Events documentation](https://learn.javascript.ru/server-sent-events)

#### Java:

* [Java documentation standard](https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html#link)