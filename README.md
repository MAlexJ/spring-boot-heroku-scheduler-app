## Project description:

Project based on 'server-sent-events' technology, which will allow real-time tracking of event changes
and displaying them in web UI (angularJS) <br>

* Database - Mongo DB
* Web framework - Spring
* Java - 18
* UI - angularJS

as an additional option, the project can be part of the spring cloud

## Application configuration:

### ENV Variables

#### Required ENV Variables ( file: application.yaml)

1. MONGODB_DATABASE - MongoDb database name
2. MONGODB_URI - URI to MongoDb

#### Optional ENV Variables

3. PORT - PORT - spring application port, by default - 8081
4. SCHEDULER_CRONE - job time to execute for standalone running (override by CONFIG_SERVER_URI)
5. ZIPKIN_ENABLED - enable tracing, by default - disable
    * ZIPKIN_PORT - URL to Zipkin distributed tracing system, by default - http://localhost
    * configuration and Zipkin service is located at the link: https://github.com/MAlexJ/spring-boot-heroku-zipkin-app
6. CONFIG_SERVER_ENABLED - enable spring global configuration, disabled by default
    * CONFIG_SERVER_URI - URL to configuration properties to spring configuration server
7. SPRING_ACTUATOR_ENABLED - enable spring actuator, disabled by default
    * use url - http://application:port/actuator/ for info
8. MAX_QUEUE_SIZE - cache size for SSE, 7 by default

### How to add ENV variable to IDE

1. Got to IDE >> Edit configuration
2. Modify options >> select in list - Environment variables
3. set ENV variables as in the example below

example:
<code>
MONGODB_DATABASE=sampleDB;MONGODB_URI=mongodb+srv://admin:
pWCbN0STJVmDmdDs@cluster0.ckcsd.mongodb.net/myFirstDatabase?retryWrites=true&w=majority
</code>

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

### Swagger UI

* documentation: https://springdoc.org/ <br>
* how to use: The Swagger UI page will then be available at:
  <br>
  <code> http://server:{{port}}/context-path/swagger-ui.html </code>
  <br> or <br>
  <code> http://server:{{port}}/swagger-ui.html </code>

## Application info/documentation:

#### Github Java CI with Gradle

* Issue Error Gradle Script ‘/Home/Runner/Work/*/Gradlew’ Is Not
  Executable  [Error Gradle Script Gradlew Is Not Executable](https://spacetech.dk/error-gradle-script-home-runner-work-gradlew-is-not-executable.html)

#### Spring boot:

* [How to Start and Stop Scheduler in Spring Boot](https://www.yawintutor.com/how-to-start-and-stop-scheduler-in-spring-boot/)
* [Running Scheduled Jobs in Spring Boot](https://reflectoring.io/spring-scheduler/)

### Spring Actuator

* [Spring Boot Actuator: Health check, Auditing, Metrics gathering and Monitoring](https://www.callicoder.com/spring-boot-actuator/#:~:text=You%20can%20enable%20or%20disable,the%20identifier%20for%20the%20endpoint)

Endpoint ID Description

* auditevents - Exposes audit events (e.g. auth_success, order_failed) for your application
* info - Displays information about your application.
* health - Displays your application’s health status.
* metrics - Shows various metrics information of your application.
* loggers - Displays and modifies the configured loggers.
* logfile - Returns the contents of the log file (if logging.file or logging.path properties are set.)
* httptrace - Displays HTTP trace info for the last 100 HTTP request/response.
* env - Displays current environment properties.
* flyway - Shows details of Flyway database migrations.
* liquidbase - Shows details of Liquibase database migrations.
* shutdown - Lets you shut down the application gracefully.
* mappings - Displays a list of all @RequestMapping paths.
* scheduledtasks - Displays the scheduled tasks in your application.
* threaddump - Performs a thread dump.
* heapdump - Returns a GZip compressed JVM heap dump.

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