package com.example.demo.service;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.exception.*;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

/**
 * User Service layer.
 */
@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    /**
     * User Service Constructor
     * @param userRepository User Repository
     * @param userMapper User Mapper
     * @param userValidator User Validator
     */
    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidator = userValidator;
    }

    /**
     * Register a new user. Also, rise a User Validation Exception if the user is not older that 18 years old
     * and not form France.
     * @param user User
     * @return UserDTO class.
     * @throws UserValidationException User Validation Exception
     */
    public UserDTO registerUser(User user) throws UserValidationException {
        userValidator.validate(user);
        user = userRepository.save(user);
        return userMapper.convertAllUserEntityToDTO(user);
    }

    /**
     * Retrieve a User by the unique ID.
     *
     * @param id is unique and not null.
     * @return UserDTO object.
     * @throws UserNotFoundException User Not Found Exception
     */
    public UserDTO getUser(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID: %s not found", id));
        }
        return userMapper.convertAllUserEntityToDTO(user.get());
    }

    /**
     * Retrieve a list of usersDTOs that are from the same country.
     *
     * @param country Country parameter
     * @return a list of UserDTOs.
     */
    public List<UserDTO> getUserByCountry(String country) {
        return userRepository.findByCountry(country).stream().map(userMapper::convertAllUserEntityToDTO).collect(Collectors.toList());
    }

}
