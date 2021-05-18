package com.example.demo.model;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;
import lombok.Data;

/**
 * This class represents the User model (Database table) and the attributes. Some are mandatory and with
 * a specific size, with the limit of the minimum and the maximum size.
 */
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

    /**
     *  Default constructor
     */
    public User() {
    }

    /**
     * User constructor
     * @param id Unique for each User.
     * @param name The name of a User.
     * @param lastName The last name of a User.
     * @param age The age of a User.
     * @param address The address of a User.
     * @param city The city of a User.
     * @param country The country of a User.
     */
    public User(int id, String name, String lastName, int age, String address, String city, String country) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.city = city;
        this.country = country;
    }
}