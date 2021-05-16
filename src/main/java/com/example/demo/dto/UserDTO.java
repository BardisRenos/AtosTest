package com.example.demo.dto;
import java.io.Serializable;

/**
 * User Data Transform Object class.
 */
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
