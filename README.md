# Microservice of basic Contact information

The purposes for which this project was carried out are merely educational and to teach those who are starting in the world of web development with the great spring framework.

The following tools were used to build an application with mvc architecture and good programming practices.

* JPA
* Lombok
* MySQL
* Starter y Javax validation
* MapStruct
* Swagger2
* jUnit
* Gson

All dependencies are used very carefully and meticulously.

## Context application
Contacts are the way to represent the association or links of an account or a person, in this particular case, contacts work as the abstraction of a person's information in the real world. The characteristics, properties or attributes on which our contact class will depend will be the following:

* id
* name
* lastName
* phone
* creation
* doctype
* documentNumber

Each variable represents a characteristic or fundamental element in our class or object to persist, in order to represent the aforementioned abstraction.

# App Struct

The structure of the application is based on the mvc architecture, which allows us to separate our business logic, controllers, models, data and configurations of great importance in our application.

We have special folders for our application domain, which is where our POJO and DTO classes are located. In addition, in an essential way there are assemblers, which allow us to map our persisted classes with our DTOs. There is a special packet for configurations and exceptions, where in the configurations we store information such as the swagger configuration and the error traps. In the exceptions, all the custom errors that occur in our application are handled. Finally we have the repository, services and implementations and a controller class to expose the endpoints of our service or microservice.

# Logical business
In the business logic, the use of dependency injections and handling of data by data streams is made with the stream and map operator. It is also taken as a good practice to use Optional when it can be implemented to return null information in our objects.

With the business logic we will be able to list information of all the contacts available in our database, as well as to be able to save, modify, delete and obtain them independently with specific methods that make contact with our service controller or endpoint exhibitor.

# Exceptions

In every program or web application that is exposed as a microservice, there must be consolidation or personal validation, for this, custom exceptions are used, which are called in the business logic or are executed as they may be and are controlled in our class exception handler. For each exception, particular codes are handled, which allow the developer to determine the reason why certain errors are occurring.

`if (contact == null)
throw new ContactsNotFound("Any contact(s) was found");`

In this way, you can see how the exception is triggered at runtime, but if it is not handled, the message that is displayed on the console is not very pleasant to the eye, then this is where the exception or error interceptor takes its role. in this application.

@ExceptionHandler(ContactsNotFound.class)
public ResponseEntity<Object> ContactsNotFounds() {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Any client was found");
        body.put("exception_code", ExceptionsCode.NOT_FOUND.getCode());

        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);


## Application properties

Thanks to this kind of controller, we can estimate or make a connection between our web application and a database, in this case, a relational database, MySQL, is used. Each information that is housed in the properties is necessary for the full execution of the application and the persistence of the data.

## Microservice exposure

To expose the connection points or endpoints, it is done through their controller class, which is the one in charge of manipulating all the http methods that we know best, through this we expose our web service. The connection is established through the following specific routes: 
* http://localhost:8080/api/contacts/
* http://localhost:8080/swagger-ui.html