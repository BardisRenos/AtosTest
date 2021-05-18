package com.example.demo.UserControllerTest;
import com.example.demo.controller.UserController;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.dtoMapper.UserMapper;
import com.example.demo.model.User;
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

@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
//@Import(SpringSecurityConfig.class)
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
        userController = new UserController(userService, userRepository);
    }


    @Test
    public void getUserByCountry() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        userList.add(new UserDTO(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"));
        userList.add(new UserDTO(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"));

        UserDTO userDTO = new UserDTO(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        String url = "/user?country=France";
        when(userService.getUserByCountry("France")).thenReturn(userList);
        mockMvc.perform(get(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(userList.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(userList.get(0).getName())))
                .andExpect(jsonPath("$[0].lastName", is(userList.get(0).getLastName())))
                .andExpect(jsonPath("$[1].id", is(userList.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(userList.get(1).getName())))
                .andExpect(jsonPath("$[1].lastName", is(userList.get(1).getLastName())))
                .andReturn();
    }


    @Test
    public void getUserById() throws Exception {
        final int id = 1;
        UserDTO userDTO = new UserDTO(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        when(userService.getUser(id)).thenReturn(userDTO);

        //response is retrieved as MvcResult
        MvcResult mvcResult = mockMvc.perform(get("/user/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(userDTO.getId())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserDTO userCreated = new ObjectMapper().readValue(jsonResponse, UserDTO.class);

        Assertions.assertNotNull(userCreated);
        assertEquals(userCreated.getId(), userDTO.getId());
        assertEquals(userCreated.getName(), userDTO.getName());
        assertEquals(userCreated.getAge(), userDTO.getAge());

    }

    @Test
    public void insertUser() throws Exception {
        User user = new User(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");
        UserDTO userDTO = new UserDTO(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        //mocking the bean for any object of AddUserRequest.class
        when(userService.registerUser(any(User.class))).thenReturn(userDTO);

        //response is retrieved as MvcResult
        MvcResult mvcResult = mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(userDTO.getId())))
                .andExpect(jsonPath("$.name", is(userDTO.getName())))
                .andExpect(jsonPath("$.lastName", is(userDTO.getLastName())))
                .andReturn();

        String jsonResponse = mvcResult.getResponse().getContentAsString();
        UserDTO userCreated = new ObjectMapper().readValue(jsonResponse, UserDTO.class);

        Assertions.assertNotNull(userCreated);
        assertEquals(userCreated.getId(), userDTO.getId());
        assertEquals(userCreated.getName(), userDTO.getName());
        assertEquals(userCreated.getAge(), userDTO.getAge());
    }
}
