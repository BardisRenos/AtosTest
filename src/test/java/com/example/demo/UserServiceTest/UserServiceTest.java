package com.example.demo.UserServiceTest;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.model.User;
import com.example.demo.requestEntity.UserRequest;
import com.example.demo.service.UserService;
import com.example.demo.utils.UserValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * JUnit testing for the Service layer
 */
@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapper();

    @InjectMocks
    private UserService userService;

    private final UserValidator userValidator = new UserValidator();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userMapper, userRepository, userValidator);
    }

    /**
     * Testing if the user is registered and check if the method retrieve the same user object.
     * @throws UserValidationException A User Validation Exception.
     */
    @Test
    void registerUser() throws UserValidationException {
        UserRequest user = new UserRequest("Renos", "Bardis", 20, "78 BD DD", "Antes", "France");

        when(userRepository.save(any(User.class))).thenAnswer((Answer<User>) invocation -> {
            User user1 = (User) invocation.getArguments()[0];
            user1.setId("1");
            return user1;
        });

        UserDTO userDTO = userService.registerUser(user);
        assertNotNull(userDTO);
        assertEquals("1", userDTO.getId());
        assertEquals(20, user.getAge());
        assertEquals("Bardis", user.getLastName());
    }

    /**
     * Testing if the correct user is retrieved. By checking the name and the last name.
     * @throws UserNotFoundException A User Not Found Exception will be risen.
     */
    @Test
    void getUser() throws UserNotFoundException {
        when(userRepository.findById("1")).thenReturn(Optional.of(new User("1", "Renos", "Bardis", 20, "78 BD DD", "Antes", "France")));
        UserDTO userRes = userService.getUser("1");
        assertEquals("Renos", userRes.getName());
        assertEquals("Bardis", userRes.getLastName());
    }

    /**
     * Testing if the correct user is retrieved. By checking the name and the last name of both User class.
     */
    @Test
    void getUserByCountry() throws UserNotFoundException {
        when(userRepository.findByCountry("France")).thenReturn(Arrays.asList(new User("1", "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"),
                new User("2", "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France")));
        List<UserDTO> res = userService.getUserByCountry("France");
        assertEquals(2, res.size());
        assertEquals("Renos", res.get(0).getName());
        assertEquals("Nikos", res.get(1).getName());
    }
}