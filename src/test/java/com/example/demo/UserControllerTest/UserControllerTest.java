package com.example.demo.UserControllerTest;
import com.example.demo.controller.UserController;
import com.example.demo.dal.UserRepository;
import com.example.demo.dto.UserDTO;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.exception.UserValidationException;
import com.example.demo.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
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
import com.example.demo.model.*;


import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.List;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;

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

    ObjectMapper om = new ObjectMapper();

    @MockBean
    private List<UserDTO> userList;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        MockitoAnnotations.initMocks(this);
        userController = new UserController(userService, userRepository);
//        this.userList = new ArrayList<>();
//        this.userList.add(new UserDTO(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"));
//        this.userList.add(new UserDTO(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"));
    }


    @Test
    @Disabled
    public void getUserByCountry() {

        this.userList.add(new UserDTO(1, "Renos", "Bardis", 20, "78 BD DD", "Antes", "France"));
        this.userList.add(new UserDTO(2, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France"));

//        UserDTO userDTO = new UserDTO(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        given(userService.getUserByCountry("France")).willReturn(userList);

        String url = "/user?country=France";

//        this.mockMvc.perform(get(url)).andExpect(status.isOk());
//        when(userService.getUserByCountry("France")).thenReturn(userList);
//
//        String url = "/user?country=France";
//
//        MvcResult result = mockMvc.perform(get("/user").content(MediaType.APPLICATION_JSON_VALUE))
//                .andExpect(status().isOk()).andReturn();
//
//        String resultContent = result.getResponse().getContentAsString();
//        Response response = om.readValue(resultContent, Response.class);
//        Assert.assertTrue(response == Boolean.TRUE);

//        given(userService.getUserByCountry("France")).willReturn(userList);

    }


    @Test
    @Disabled
    public void getUserById() throws Exception {
        UserDTO userDTO = new UserDTO(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        final int id = 1;
        given(userService.getUser(id)).willReturn(userDTO);

        mockMvc.perform(get("/user/{id}", id).content(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Disabled
    public void insertUser() throws Exception {
        given(userService.registerUser(any(User.class))).willAnswer((invocation) -> invocation.getArgument(0));

        User user = new User(1, "Nikos", "Papas", 40, "10 BVD LL", "Lyon", "France");

        this.mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(om.writeValueAsString(user)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.Nikos", is(user.getName())))
                .andExpect(jsonPath("$.Papas", is(user.getLastName())))
                .andExpect(jsonPath("$.age", is(user.getAge())));
    }
}
