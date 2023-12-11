package com.example.demo.UserControllerTest;
import com.example.demo.controller.UserController;
import com.example.demo.dao.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.requestEntity.UserRequest;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * JUnit testing for the Controller layer
 */
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserMapper userMapper;

    ObjectMapper om = new ObjectMapper();


    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService);
    }


    /**
     * Insert two records and then check if the two items are retrieved. Checking the size of the list and the
     * attributes of the two user records.
     * @throws Exception Throws an Exception
     */
    @Test
    public void getUserByCountry() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO("1","Renos", "Bardis", 20, "78 BD DD", "Antes", "France"));
        userList.add(new UserDTO("2","Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"));

        UserDTO userDTO = new UserDTO("2","Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        String url = "/api/v1/user/userByCountry?country=France";
        when(userService.getUserByCountry("France")).thenReturn(userList);
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(userList.get(0).getName())))
                .andExpect(jsonPath("$[0].lastName", is(userList.get(0).getLastName())))
                .andExpect(jsonPath("$[1].name", is(userList.get(1).getName())))
                .andExpect(jsonPath("$[1].lastName", is(userList.get(1).getLastName())))
                .andReturn();
    }

    /**
     * This test checks if a user is retrieved correctly. By checking the id, name and the last name.
     * @throws Exception Throws an Exception.
     */
    @Test
    public void getUserById() throws Exception {
        final String id = "1";
        UserDTO userDTO = new UserDTO("1", "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        when(userService.getUser(id)).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserDTO userCreated = new ObjectMapper().readValue(jsonResponse, UserDTO.class);

        Assertions.assertNotNull(userCreated);
        assertEquals(userCreated.getName(), userDTO.getName());
        assertEquals(userCreated.getAge(), userDTO.getAge());

    }

    /**
     * This test checks that a record is inserted and retrieved properly.
     * @throws Exception Throws an Exception.
     */
    @Test
    public void insertUser() throws Exception {
        User user = new User("1", "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");
        UserDTO userDTO = new UserDTO("1", "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        when(userService.registerUser(any(UserRequest.class))).thenReturn(userDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/user/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserDTO userCreated = new ObjectMapper().readValue(jsonResponse, UserDTO.class);

        Assertions.assertNotNull(userCreated);
        assertEquals(userCreated.getName(), userDTO.getName());
        assertEquals(userCreated.getAge(), userDTO.getAge());
    }
}
