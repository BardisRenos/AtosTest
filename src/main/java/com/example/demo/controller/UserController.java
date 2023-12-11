package com.example.demo.controller;

import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.requestEntity.UserRequest;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The Controller layer.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/user")
public class UserController {

    private final UserService userService;

    /**
     * Register new user to the database and the
     * @param user The class object User
     * @return UserDTO class.
     * @throws UserValidationException User Validation Exception
     */
    @PostMapping(value = "/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO saveUser(@RequestBody @Valid UserRequest user) throws UserValidationException {
        return userService.registerUser(user);
    }

    /**
     * Retrieve a list of UserDTO by a given country. Has a default value if anything is given as a Request parameter.
     * @param country not unique.
     * @return List of UserDTOs.
     */
    @GetMapping(value ="/userByCountry")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDTO> getUser(@RequestParam(value = "country", defaultValue="France") String country) throws UserNotFoundException {
        return userService.getUserByCountry(country);
    }

    /**
     * Retrieve a UserDTO by a given id attribute.
     * @param id unique for each user.
     * @return UserDTO class.
     * @throws UserNotFoundException User Not Found Exception
     */
    @GetMapping(value ="/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserId(@PathVariable("id") String id) throws UserNotFoundException {
        return userService.getUser(id);
    }
}
