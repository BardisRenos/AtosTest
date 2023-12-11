package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * This class represents the User model (Database table) and the attributes.
 */
@Data
@Document(collection = "user")
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Adding the entities of the database table Users. Those attributes are representing the database table "User".
     */
    @Id
    private String id;
    private String name;
    private String lastName;
    private int age;
    private String address;
    private String city;
    private String country;

}