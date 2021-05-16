package com.example.demo.service;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * User Service layer.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserValidator userValidator;


    /**
     * Register a new user. Also, rise a User Validation Exception if the user is not older that 18 years old
     * and not form France.
     * @param userDTO
     * @return UserDTO class.
     * @throws UserValidationException
     */
    public UserDTO registerUser(UserDTO userDTO) throws UserValidationException {
        User user = userMapper.convertAllUserDTOToEntity(userDTO);
        userValidator.validate(user);
        user = userRepository.save(user);
        return userMapper.convertAllUserEntityToDTO(user);
    }

    /**
     * Retrieve a User by the unique ID.
     *
     * @param id is unique and not null.
     * @return UserDTO object.
     * @throws UserNotFoundException
     */
    public UserDTO getUser(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(String.format("User with ID: %s not found", id));
        }
        return userMapper.convertAllUserEntityToDTO(user.get());
    }

    /**
     * Retrieve a list of users that are from the same country.
     *
     * @param country
     * @return a list of UserDTOs.
     */
    public List<UserDTO> getUserByCountry(String country) {
        return userRepository.findByCountry(country).stream().map(userMapper::convertAllUserEntityToDTO).collect(Collectors.toList());
    }

}
