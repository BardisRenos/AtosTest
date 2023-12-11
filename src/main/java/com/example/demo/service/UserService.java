package com.example.demo.service;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.exception.*;
import com.example.demo.model.User;
import com.example.demo.requestEntity.UserRequest;
import com.example.demo.utils.UserValidator;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * User Service layer.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    /**
     * Register a new user. Also, rise a User Validation Exception if the user is not older that 18 years old
     * and not form France.
     * @param user User
     * @return UserDTO class.
     * @throws UserValidationException User Validation Exception
     */
    public UserDTO registerUser(UserRequest user) throws UserValidationException {
        userValidator.validate(user);

        User userEntity = new User(UUID.randomUUID().toString().split("-")[0], user.getName(),
                user.getLastName(), user.getAge(), user.getAddress(), user.getCity(), user.getCountry());

        User userEntityRes = userRepository.save(userEntity);
        return userMapper.convertAllUserEntityToDTO(userEntityRes);
    }

    /**
     * Retrieve a User by the unique ID.
     *
     * @param userId is unique and not null.
     * @return UserDTO object.
     * @throws UserNotFoundException User Not Found Exception
     */
    public UserDTO getUser(String userId) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID: %s not found", userId));
        }
        return userMapper.convertAllUserEntityToDTO(user.get());
    }

    /**
     * Retrieve a list of usersDTOs that are from the same country.
     *
     * @param country Country parameter
     * @return a list of UserDTOs.
     */
    public List<UserDTO> getUserByCountry(String country) throws UserNotFoundException {
        List<User> listOfUsers = userRepository.findByCountry(country);
        if(listOfUsers.isEmpty()) {
            throw new UserNotFoundException(String.format("User with country: %s not found or the country", country));
        }
        return userRepository.findByCountry(country).stream().map(userMapper::convertAllUserEntityToDTO).collect(Collectors.toList());
    }

}
