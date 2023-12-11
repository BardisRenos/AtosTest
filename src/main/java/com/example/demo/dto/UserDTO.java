package com.example.demo.dto;
import lombok.*;

import java.io.Serializable;

/**
 * User Data Transform Object class.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO implements Serializable {

    private String id;
    private String name;
    private String lastName;
    private int age;
    private String address;
    private String city;
    private String country;
}
