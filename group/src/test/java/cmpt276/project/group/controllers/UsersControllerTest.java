
package cmpt276.project.group.controllers;

import cmpt276.project.group.models.UserRepository;
import cmpt276.project.group.models.Users;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.mock.web.MockHttpSession; // Import MockHttpSession from Spring

@WebMvcTest(UsersController.class)
public class UsersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HttpSession httpSession;

    @Test
    void testGetLoginPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/login"));
    }
    void setup() {
        httpSession = new MockHttpSession();
    }

    @Test
    void testSuccessfulLogin() throws Exception {
        httpSession = new MockHttpSession();

        Users user = new Users();
        user.setUsername("ben111");
        user.setPassword("Lol123!");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user);

        when(userRepository.findByUsernameAndPassword("ben111", "Lol123!")).thenReturn(usersList);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "ben111")
                        .param("password", "Lol123!")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/protected"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void testFailedLogin() throws Exception {


// Update your test methods to use MockHttpSession
        httpSession = new MockHttpSession();
        when(userRepository.findByUsernameAndPassword("alice567", "123!password")).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "alice567")
                        .param("password", "123!password")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/login"))
                .andExpect(model().attributeExists("error"));
    }


    @Test
    void testSuccessfulSignUp() throws Exception {
        httpSession = new MockHttpSession();

        Users user = new Users();
        user.setName("bob");
        user.setUsername("bob234");
        user.setPassword("Bob123!");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user);

        when(userRepository.findByNameAndUsernameAndPassword("bob","bob234", "Bob123!")).thenReturn(usersList);

        mockMvc.perform(MockMvcRequestBuilders.post("/signup")
                        .param("name","bob")
                        .param("username", "bob234")
                        .param("password", "Bob123!")
                        .param("empCode","childhelp2020")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/protected"));
    }




};


