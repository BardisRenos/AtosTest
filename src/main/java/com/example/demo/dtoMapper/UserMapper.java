package com.example.demo.dtoMapper;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * The DTO Mapper class. This class helps into the transformation of the entities by the Service layer.
 */
@Service
public class UserMapper {

    /**
     * The method convert User's  object Entities to UserDto
     * @param userEntity A given User entity
     * @return UserDTO class
     */
    public UserDTO convertAllUserEntityToDTO(User userEntity){

        return new ModelMapper().map(userEntity, UserDTO.class);
    }
}
