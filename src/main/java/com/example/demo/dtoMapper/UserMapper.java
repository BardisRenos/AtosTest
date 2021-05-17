package com.example.demo.dtoMapper;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.springframework.stereotype.Service;

/**
 * The DTO Mapper class. This class helps into the transformation of the entities by the Service layer.
 */
@Service
public class UserMapper {

    /**
     * The method convert User's Entities to UserDto
     * @param userEntity A given User entity
     * @return UserDTO class
     */
    public UserDTO convertAllUserEntityToDTO(User userEntity){
        return new UserDTO(userEntity.getId(), userEntity.getName(), userEntity.getLastName(), userEntity.getAge(),
                userEntity.getAddress(), userEntity.getCity(), userEntity.getCountry());
    }

    /**
     * The method convert UserDto to User's Entities
     * @param userDTO A given User DTO
     * @return User class
     */
    public User convertAllUserDTOToEntity(UserDTO userDTO){
        return new User(userDTO.getId(), userDTO.getName(), userDTO.getLastName(), userDTO.getAge(),
                userDTO.getAddress(), userDTO.getCity(), userDTO.getCountry());
    }
}
