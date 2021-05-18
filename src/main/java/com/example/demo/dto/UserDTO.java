package com.example.demo.dto;
import lombok.Data;
import java.io.Serializable;

/**
 * User Data Transform Object class.
 */
@Data
public class UserDTO implements Serializable {

    private int id;
    private String name;
    private String lastName;
    private int age;
    private String address;
    private String city;
    private String country;

    /**
     *  Default DTO constructor
     */
    public UserDTO() {
    }

    /**
     * User DTO constructor
     * @param id The unique ID number of each User.
     * @param name The name of a User.
     * @param lastName The last name of a User.
     * @param age The age of a User.
     * @param address The address of a User.
     * @param city The city of a User.
     * @param country The country of a User.
     */
    public UserDTO(int id, String name, String lastName, int age, String address, String city, String country) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.city = city;
        this.country = country;
    }
}
