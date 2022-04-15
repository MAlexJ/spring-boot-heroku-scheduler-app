## Application configuration:

### ENV Variables

1. SCHEDULER_ENABLED - activate scheduling, by default - true
2. SCHEDULER_CRONE - job time to execute
3. ZIPKIN_PORT - URL to Zipkin distributed tracing system, by default - http://localhost

##### gradle:

* '<b>updateProcfile</b>' task designed to update Procfile file that HEROKU service uses for deployment

##### MongoDb:

* Cloud db: https://cloud.mongodb.com/

## Application info/documentation:

#### Spring boot:

* [How to Start and Stop Scheduler in Spring Boot](https://www.yawintutor.com/how-to-start-and-stop-scheduler-in-spring-boot/)
* [Running Scheduled Jobs in Spring Boot](https://reflectoring.io/spring-scheduler/)
* [Spring Cloud Sleuth ](https://medium.com/@kirill.sereda/spring-cloud-sleuth-zipkin-%D0%BF%D0%BE-%D1%80%D1%83%D1%81%D1%81%D0%BA%D0%B8-9f8504581dae)

#### Spring paging and sorting:

* [Paging and Sorting](https://docs.spring.io/spring-data/rest/docs/current-SNAPSHOT/reference/html/#paging-and-sorting)
* [Spring @PageableDefault](https://stackoverflow.com/questions/41486047/use-pageabledefault-with-spring-data-rest)
* [Spring spring-data-web @PageableDefault](https://www.baeldung.com/spring-data-web-support)

#### Spring Boot Actuator:

* [How to Enable All Endpoints](https://www.baeldung.com/spring-boot-actuator-enable-endpoints)
* [Web API Documentation](https://docs.spring.io/spring-boot/docs/current/actuator-api/htmlsingle/)

#### Spring mapper:

* [MapStruct documentation](https://mapstruct.org/documentation/stable/reference/html/)

#### Java Script:

* [Server Sent Events documentation](https://learn.javascript.ru/server-sent-events)

#### Java:

* [Java documentation standard](https://docs.oracle.com/en/java/javase/17/docs/specs/javadoc/doc-comment-spec.html#link)

#### Gradle

* [Detecting build version and time at runtime](https://www.vojtechruzicka.com/spring-boot-version/)