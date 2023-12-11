package com.example.demo.requestEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

/**
 * This class represents the Request of the User model. Between the fields some are mandatory and with
 * a specific size, with the limit of the minimum and the maximum size.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {

    @NotEmpty(message = "The name cannot be null")
    @Size(min = 5, max = 32, message = "The name cannot be less than 5 and greater than 32 characters")
    private String name;
    @NotEmpty(message = "The last name cannot be null")
    @Size(min = 5, max = 64, message = "The name cannot be less than 5 and greater than 64 characters")
    private String lastName;
    @NotNull(message = "The age cannot be null")
    @Min(18)
    @Max(65)
    private int age;
    private String address;
    private String city;
    @NotEmpty(message = "The country cannot be null")
    @Size(min = 2, max = 64, message = "The country cannot be less than 2 and greater than 25 characters")
    private String country;

}