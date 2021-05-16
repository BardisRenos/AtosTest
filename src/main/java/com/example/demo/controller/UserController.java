package com.example.demo.controller;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

/**
 * The Controller layer.
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /**
     * Register new user to the database and the
     * @param userDTO
     * @return UserDTO class.
     * @throws UserValidationException
     */
    @PostMapping("/user")
    public UserDTO saveUser(@Valid @RequestBody UserDTO userDTO) throws UserValidationException {
        return userService.registerUser(userDTO);
    }

    /**
     * Retrieve a list of UserDTO by a given country. Has a default value if anything is given as a Request parameter.
     * @param country not unique.
     * @return List of UserDTOs.
     */
    @GetMapping("/user")
    public List<UserDTO> getUser(@RequestParam(value = "country", defaultValue = "France") String country) {
        return userService.getUserByCountry(country);
    }

    /**
     * Retrieve a UserDTO by a given id attribute.
     * @param id unique for each user.
     * @return UserDTO class.
     * @throws UserNotFoundException
     */
    @GetMapping("/user/{id}")
    public UserDTO getUserId(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.getUser(id);
    }
}
