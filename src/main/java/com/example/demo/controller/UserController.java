package com.example.demo.controller;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.*;
import com.example.demo.model.User;
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

    private final UserService userService;

    private final UserRepository userRepository;

    /**
     * User Controller Constructor
     * @param userService User Service
     * @param userRepository User Repository
     */
    @Autowired
    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    /**
     * Register new user to the database and the
     * @param user The class object User
     * @return UserDTO class.
     * @throws UserValidationException User Validation Exception
     */
    @PostMapping("/user")
    public UserDTO saveUser(@Valid @RequestBody User user) throws UserValidationException {
        return userService.registerUser(user);
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
     * @throws UserNotFoundException User Not Found Exception
     */
    @GetMapping("/user/{id}")
    public UserDTO getUserId(@PathVariable("id") Integer id) throws UserNotFoundException {
        return userService.getUser(id);
    }
}
