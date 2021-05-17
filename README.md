# AtosTest


### Documentation

Create 2 REST services: one that allows to register a user and the other one that displays the details of a registered user.

Requirements:
- define a user (what are the fields needed). We should have mandatory and optional fields!
- validate the input and return proper error messages/http status
- log the input and output of each call and the processing time.
- have a request parameter which is not mandatory and which provides a default value in case is not set
- have a path variable
- clear code and javadoc
- unit tests
- only adults ( age > 18 years)  and that live in France can create an account!

### Data Model

The Model part is the representation the data base table schema into java coding. Setting the 

```java
@Entity
@Table(name = "Users")
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Adding the entities of the database table Users. Those attributes are representing the database table "Users".
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, updatable = false)
    private int id;
    @NotNull(message = "The name cannot be null")
    @Size(min = 5, max = 32, message = "The name cannot be less than 5 and greater than 32 characters")
    private String name;
    @NotNull(message = "The last name cannot be null")
    @Size(min = 5, max = 64, message = "The name cannot be less than 5 and greater than 64 characters")
    private String lastName;
    @NotNull(message = "The age cannot be null")
    private int age;
    private String address;
    private String city;
    @NotNull(message = "The country cannot be null")
    @Size(min = 2, max = 64, message = "The country cannot be less than 2 and greater than 25 characters")
    private String country;
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


