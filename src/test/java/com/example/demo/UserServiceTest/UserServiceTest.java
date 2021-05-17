package com.example.demo.UserServiceTest;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper = new UserMapper();

    @Autowired
    private UserService userService;

    private final UserValidator userValidator = new UserValidator();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        userService = new UserService(userRepository, userMapper, userValidator);

//        User user = new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France");
//        User user1 = new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");
//        userRepository.save(user);
//        userRepository.save(user1);

    }

    @Test
    @Disabled
    void registerUser() throws UserValidationException {
        // given
        User user = new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France");
//        User user1 = new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

//        when(userService.registerUser(user)).thenReturn(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"));

        // when
        userService.registerUser(user);

        // then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userRepository).save(userArgumentCaptor.capture());

        User capturedStudent = userArgumentCaptor.getValue();

        assertThat(capturedStudent).isEqualTo(user);
    }

    @Test
    @Disabled
    void getUser() throws UserNotFoundException {

//        when(userRepository.findAll()).thenReturn(Arrays.asList(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"),
//                new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France")));

        when(userRepository.findById(1)).thenReturn(Optional.of(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France")));

        UserDTO userRes = userService.getUser(1);

        assertEquals("Renos", userRes.getName());
    }

    @Test
//    @Disabled
    void getUserByCountry() {

        when(userRepository.findByCountry("France")).thenReturn(Arrays.asList(new User(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"),
                new User(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France")));

//        when(userRepository.findAll()).thenReturn(res);

        List<UserDTO> res = userService.getUserByCountry("France");
//
        assertEquals(2, res.size());

    }
}