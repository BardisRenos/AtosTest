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

By using the annotation `@Id` sets the `primary key` (Id attribute) which will be unique and nullable and not updatable. 

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(unique = true, nullable = false, updatable = false)
private int id;
````

By using the annotation `@NotNUll` and `@Size` you specify 

```java
@NotNull(message = "The name cannot be null")
@Size(min = 5, max = 32, message = "The name cannot be less than 5 and greater than 32 characters")
private String name;
```

In this case when the city has 
```java
private String city;
```


### Schema Design

The below sql command that create the database table `Users` 

```sql
CREATE TABLE Users (
    `id`  INTEGER PRIMARY KEY AUTO_INCREMENT NOT NULL,
    `name` VARCHAR(32),
    `lastName` VARCHAR(64),
    `age` INT,
    `address` VARCHAR(64),
    `city` VARCHAR(64),
    `country` VARCHAR(64));
```

The other sql command fill up the table 

```sql
 INSERT INTO Users (id, name, lastName, age, address, city, country)
 VALUES (122, 'Renos', 'Bardis', 20, '78 BD Wilson', 'Antibes', 'France'),
        (222, 'Omar', 'Mater', 21, '6 BD Dugommier', 'Juan Leas Pins', 'France'),
        (322, 'Jacques', 'Seilier', 25, '89 BD Albert', 'Nice', 'France'),
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

```java
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    /**
     * This method return an exception message when the user not found.
     * @param message
     */
    public UserNotFoundException(String message){
        super(message);
    }
}

```

### UserValidation Exception


```java
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UserValidationException extends Exception {

    /**
     * This method return an exception message when the condition is not satisfied.
     * @param message
     */
    public UserValidationException(String message) {
        super(message);
    }
}
```

### User Validator

The validator class checks if a given User is greater than 18 and if he is from France. Otherwise, the method validate throws a `UserValidationException` type.

```java
@Component
public class UserValidator {

    /**
     * The validate method checks if the user is greater than 18 years old and if is from France. Otherwise,
     * rise a UserValidationException.
     * @param user Class.
     * @throws UserValidationException
     */
    public void validate(User user) throws UserValidationException {
        if (user.getAge() <= 18 || !"France".equals(user.getCountry())) {
            throw new UserValidationException("User must be older than 18 years old and live in France");
        }
    }
}
```
