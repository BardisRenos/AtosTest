# Atos Assigment Test


### Info

Create 2 REST services: one that allows to register a user and the other one that displays the details of a registered user.

Requirements:
- define a user (what are the fields needed). We should have mandatory and optional fields!
- validate the input and return proper error messages/http status
- log the input and output of each call and the processing time.
- have a request parameter which is not mandatory and which provides a default value in case is not set
- have a path variable
- clear code and javadoc
- unit tests
- only adults ( age > 18 years) and who live in France can create an account!

Bonuses:
- user a non-relational DB in order to save the users!
- use AOP
- documentation/UML/schemas to explain the architecture


### Prerequisites 

- Java v11
- Spring Boot v2.4.5
- Maven Project 
- Maven Build Tool
- Lombok (Additional Library)

### Installation

```git
git clone https://github.com/BardisRenos/AtosTest.git
```

In order to clean and install the Maven repository dependencies. 

```
mvn clean install
```

### Application Properties

Changing the server port from 8080 (Default) to 8081.

```
server.port=8081
```

Setting the H2 in memory database. I do not user a SQL database but a vitual memory which is creted when the application runs.  

```
spring.datasource.url=jdbc:h2:mem:testdb;MODE=MySQL;DB_CLOSE_DELAY=-1;IGNORECASE=TRUE;
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=' '
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.datasource.initialization-mode=always
spring.datasource.platform=h2
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=create
```

### Data Model

The Model part is the representation the database table schema into Java coding. Setting the attributes of the User object witch corrensponds with the Users database table schema attributes.


The `Entity` annotation indicates the variables entitity of the database table. Also, the `@Table` corrensopnd to database table name. Finally the `@Data`  

```java
@Entity
@Table(name = "Users")
@Data

```

By using the annotation `@Id` sets the `primary key` (Id attribute) which will be unique, nullable and not updatable. 

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(unique = true, nullable = false, updatable = false)
private int id;
````

By using the annotation `@NotNUll` and `@Size` you specify that entity should not be null and also the size should be inside of a specific size. 

```java
@NotNull(message = "The name cannot be null")
@Size(min = 5, max = 32, message = "The name cannot be less than 5 and greater than 32 characters")
private String name;
```

In this case when the city could be null
```java
private String city;
```

### DTO's (Data Transfer Object) and DTO Mappers

The conversation of the data base entities into Data tranform objects. We try not to expose the entities from the database. 


Since we are using Maven, just add the `modelmapper` library as a dependency.

```
<dependency>
  <groupId>org.modelmapper</groupId>
  <artifactId>modelmapper</artifactId>
  <version>2.4.2</version>
</dependency>
```
Coding in Java, how to convert an entity to DTO.  
```java
ModelMapper modelMapper = new ModelMapper();
OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
```

<p align="center"> 
<img src="https://github.com/BardisRenos/AtosTest/blob/master/layers.png" width="350" height="450" style=centerme>
</p>

### Validation 

In order to validate that the attributes of a User object, should use `@Valid` as a parameter.

```java
@PostMapping("/user")
public UserDTO saveUser(@Valid @RequestBody User user) throws UserValidationException 
```

### Schema Design

The below sql command that create the database table `Users` 

```sql
CREATE TABLE Users (
    `id`  INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(32) NOT NULL,
    `lastName` VARCHAR(64) NOT NULL,
    `age` INT NOT NULL,
    `address` VARCHAR(64),
    `city` VARCHAR(64),
    `country` VARCHAR(64) NOT NULL);
```

The sql command `INSERT` fill up the table 

```sql
 INSERT INTO Users (id, name, lastName, age, address, city, country)
 VALUES (122, 'Renos', 'Bardis', 20, '78 BD Wilson', 'Antibes', 'France'),
        (222, 'Peter', 'Materdeeer', 21, '6 BD Dugommier', 'Juan Leas Pins', 'France'),
        (322, 'William', 'Seillsss', 25, '89 BD Albert', 'Nice', 'France'),
        (422, 'Nikos', 'Pappas', 35, '101 BD Cannes', 'Cannes', 'France');
```


### User Exception Handling 

```java
@ExceptionHandler({UserValidationException.class})
public ResponseEntity<Object> handleBadRequest(Exception ex) {
    return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
}
```

```java
@ExceptionHandler({UserNotFoundException.class})
public ResponseEntity<Object> handleNotFound(Exception ex) {
    return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND);
}
```

### User Not Found Exception

The 404 which is the NOT_FOUND status
```java
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception

```

### UserValidation Exception

The 400 which is BAD_REQUEST status

```java
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserValidationException extends Exception 
```

### User Validator

The validator class checks if a given User is greater than 18 and if he is from France. Otherwise, the method validate throws a `UserValidationException` type.

```java
@Component
public class UserValidator {

    final String COUNTRY = "France";

    public void validate(User user) throws UserValidationException {
        if (user.getAge() <= 18 || !COUNTRY.equals(user.getCountry())) {
            throw new UserValidationException(String.format("User must be older than 18 years old and live in %s", COUNTRY));
        }
    }
}
```




