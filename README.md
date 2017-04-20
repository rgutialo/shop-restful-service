## Shop Restful Service

This is a RESTful API for a Shop entity.
 

### Prerequisites
This projects is under Spring Boot. So we need Java 8 and gradle in order to be build.

### Usage

You can use this service in order to learn how to create a rest(ful) service under Spring Boot using Google API.

#### Running the Service

```sh
'./gradlew bootRun' under Linux OR 'gradlew.bat bootRun' under Windows
```
#### Distributing the Service

```sh
'./gradlew build' under Linux OR 'gradlew.bat build' under Windows
```
That generates the binary `./build/libs/shop-rest-service-0.1.0.jar`

#### Testing the Service

We have made some simple serialization json-tests. To execute them, just run:  
```sh
'./gradlew test' # under Linux OR 'gradlew.bat test' under windows
```

### Decisions made

#### Persistence

We decided to use the ConcurrentHashMap class in order to allow persistence and concurrent data access. We thought it was a good decision forr this test. Obviously, in production we should think other ways to  ensure good connections and good results for final users.


#### Geolocation

We decided to use an asynchronous connection to Google API because restful services are mainly for mobile devices (smartphones, tablets). These devices depends on phone coverage and we have to use simple ways to avoid deadlocks.

#### Rest

In order to respect the REST constraints, we decided to implement this service using Spring HATEOAS. In all cases, this service returns an HTTEntity with HttpStatus (OK, NOT FOUND).
Furthermore, this service returns not only the JSON object, but also the location of the resource returned.

### Comments

This exercise helps me to know more about Spring and Spring Boot. I worked using other frameworks as Struts or architectures as SOAP Services.

Moreover, we decided to define entities thinking to be reusable in future projects. We have to implement new code not only thinking to solve present problems, but also contemplating new posibilities in next future.

We also thought to define jsonviews to display more or less data in json objects, but maybe this was out of this exercise.

I hope you like this solution and i hope we would be working together soon.