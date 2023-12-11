# Assignment Test


### Info

Create 2 REST services: one that allows to registration of a user and the other one that displays the details of a registered user.

Requirements:
- define a user (what are the fields needed). We should have mandatory and optional fields!
- validate the input and return proper error messages/http status
- log the input and output of each call and the processing time.
- have a request parameter that is not mandatory and which provides a default value in case is not set
- have a path variable
- clear code and Javadoc
- unit tests
- only adults ( age > 18 years) and those who live in France can create an account!

Bonuses:
- user a non-relational DB to save the users!
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

To clean and install the Maven repository dependencies. 

```
mvn clean install
```

### Application Properties

Changing the server port from 8080 (Default) to 8081.

```
server.port=8081
```
### Setting the non-relational database 
Setting the MongoDB in-memory database.  

```
#Mongo Configuration
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=mongodb
spring.data.mongodb.repositories.enable=true
```

### Data Model

The Model part is the representation of the database table schema in Java coding. Setting the attributes of the User object which corresponds with the Users database table schema attributes.


The `@Document` corresponds to the database table name. Also, the `@Data` is the annotation that helps a developer to interact with the database's entities. 

```java
@Data
@Document
```

### Setting the mongodb service

If you work on linux operation system. The command to install mongodb servet is :

```script
sudo apt install mongodb-server-core
```
Have to create a folder in order to store some files there

```script
mkdir data-mongodb
cd data-mongodb
```

Enable the mongodb server

```script
mongod --dbpath=.
```

By using the annotation `@Id` sets the `primary key` (Id attribute) which will be unique, nullable and not updatable. 

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(unique = true, nullable = false, updatable = false)
private int id;
````

By using the annotations `@NotNUll` and `@Size` you specify that the entity should not be null and also the size should be inside of a specific size. 

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

The conversation of the database entities into Data transform objects. We try not to expose the entities from the database. 


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

### The two different structures

How architecture should be, with DTO and without. 

3 Layers

With only Entity:

    Presentation => REST CONTROLLER
            ENTITY
            |
    BUSINESS => SERVICES
            |
            ENTITY
    DAL (Data Access Layer) => Repositories

    Issues:
        - infinite recursion loop in JSON deserialisation
        - Business Logic is tied to the definition of the model
            (EVERYTHING HAS TO BE IN DATABASE, which is not always the case)


With DTO:

    Presentation => REST CONTROLLER------------------
        DTO                                 /\
        |                                   |
        v                                    DTO
    BUSINESS => SERVICES---------------------------
        DTO transform to ENTITY             ENTITY trasnform to DTO
        |                                   /\
        v                                   |
        Save ENTITY                        GET ENTITY
    DAL (Data Access Layer) => Repositories--------

    Non Issue:
        A bit more code to do (sometimes it can be just a copy of an entity)
    ++:
        More flexible in the Businness Lyaer to add data
        Separate
        
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

### Insert values into the database

When the application starts, the database has already 4 records, in order to check if the application works.

```java
@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDB(UserRepository userRepository){
		return (args) ->{
			List<User> users = new ArrayList<>(Arrays.asList(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"),
					new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"),
					new User(3, "Aggelos", "MPalallis", 30, "90 BVD MM", "Paris", "France"),
					new User(4, "Vaggelis", "Papagkikas", 50, "90 RUE LL", "Bordeaux", "France")));

			userRepository.saveAll(users);
		};
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

### JUnit Testing

It is best to test all the layers of this application. 

- Repository layer
- Service layer
- Controll layer

For the `repository` layer:

```java
  @BeforeEach
  public void init(){
      User user = new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France");
      User user1 = new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");
      userRepository.save(user);
      userRepository.save(user1);
    }
```
With the annotation `@BeforeEach` before each `@Test` part, the method insert two user object.  

```java
  @AfterEach
  public void delete(){
      userRepository.deleteAll();
    }
```

With the annotation `@AfterEach` after each `@Test` part, the method delete all the records.


In this case the method `testData()` retrieve all the records and checks if the total number
of records are 2 and the first record the name of the user is `Renos`.

```java
@Test
public void testData(){
    List<User> userRes = (List<User>) userRepository.findAll();
    assertEquals(2, userRes.size());
    assertEquals("Renos", userRes.get(0).getName());
  }
```

In the other case is the riverce side of the first unit test. Retrieve all the records and checks if the
name is not we gave and the total size of the list not 1.

```java
@Test
public void testDataNot(){
    List<User> userRes = (List<User>) userRepository.findAll();
    assertNotEquals("George", userRes.get(0).getName());
    assertNotEquals(1, userRes.size());
}
```

For the `service` layer:

In this layer, `Mockito` testing will be used. In this case `mock` the `findById()` method which returns an Optional `User`. Followed by
the `service` method  `getUser()`. Checks the `name` and the `last name`.

```java
@Test
void getUser() throws UserNotFoundException {
    when(userRepository.findById(1)).thenReturn(Optional.of(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France")));
    UserDTO userRes = userService.getUser(1);
    assertEquals("Renos", userRes.getName());
    assertEquals("Bardis", userRes.getLastName());
}

```


For the `controller` layer :


In this test case the method `getUser()` return a UserDTO type. Then `mockMvc` apply the path of the controller `/user/{id}` and chekcs if
the `HttpStatus` is ok, namely `200`. Also, checks the id, name and the last name. 

```java
@Test
  public void getUserById() throws Exception {
      final int id = 1;
      UserDTO userDTO = new UserDTO(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

      when(userService.getUser(id)).thenReturn(userDTO);

      MvcResult mvcResult = mockMvc.perform(get("/user/{id}", id)
              .contentType(MediaType.APPLICATION_JSON)
              .content(om.writeValueAsString(userDTO)))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$.id", is(userDTO.getId())))
              .andExpect(jsonPath("$.name", is(userDTO.getName())))
              .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
              .andReturn();

      String jsonResponse = mvcResult.getResponse().getContentAsString();
      UserDTO userCreated = new ObjectMapper().readValue(jsonResponse, UserDTO.class);

      Assertions.assertNotNull(userCreated);
      assertEquals(userCreated.getId(), userDTO.getId());
      assertEquals(userCreated.getName(), userDTO.getName());
      assertEquals(userCreated.getAge(), userDTO.getAge());
  }
```

### Testing screenshot

From this screen shot it is obvious that all the test are passed. 


<p align="center"> 
<img src="https://github.com/BardisRenos/AtosTest/blob/master/Images/test-screenshot.png" width="550" height="550" style=centerme>
</p>


Run All test with Coverage

<p align="center"> 
<img src="https://github.com/BardisRenos/AtosTest/blob/master/Images/CoverageTest.png" width="550" height="550" style=centerme>
</p>


### UML Diagram 

In this section will demonstrate the UML diagram. 

<p align="center"> 
<img src="https://github.com/BardisRenos/AtosTest/blob/master/Images/uml.png" width="450" height="550" style=centerme>
</p>


### Flow diagram of the structure

This structure depicts the flow of each component until the database.  

<p align="center"> 
<img src="https://github.com/BardisRenos/AtosTest/blob/master/Images/UMLDiagram.png" width="450" height="550" style=centerme>
</p>



### UML Sequence Diagram


<p align="center"> 
<img src="https://github.com/BardisRenos/AtosTest/blob/master/Images/umlDB.png" width="450" height="550" style=centerme>
</p>




