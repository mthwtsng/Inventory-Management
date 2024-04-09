package cmpt276.project.group.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cmpt276.project.group.models.Toy;
import cmpt276.project.group.models.ToyRepository;
import cmpt276.project.group.controllers.ToyController;

import cmpt276.project.group.models.UserRepository;
import cmpt276.project.group.models.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class ToyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToyController toyController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ToyRepository toyRepository;
    @BeforeEach
    void setup() {
        System.out.println("Setting up");
    }

    @Test
    void testContextLoads() {
        assertNotNull(toyController);
    }
    @Test
    void testGetAllToys() throws Exception {
        // Simulate successful login
        MockHttpSession httpSession = new MockHttpSession();

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

        // Attempt to access the showAllToys page
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllToys")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllToys"));
    }
    @Test
    void testAccessAddToys() throws Exception {
        // Simulate successful login
        MockHttpSession httpSession = new MockHttpSession();

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

        // Attempt to access the showAllToys page
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllToys")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllToys"));
        // Attempt to access the addForm page
        mockMvc.perform(MockMvcRequestBuilders.get("/toyAddForm")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/toyAddForm"));
    }



    @Test
    void testAddNewToyCategory1() throws Exception {
        MockHttpSession httpSession = new MockHttpSession();

        Users user = new Users();
        user.setUsername("ben111");
        user.setPassword("Lol123!");

        List<Users> usersList = new ArrayList<>();
        usersList.add(user);

        when(userRepository.findByUsernameAndPassword("ben111", "Lol123!")).thenReturn(usersList);

        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "ben111")
                        .param("password", "Lol123!")
                        .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/protected"))
                .andExpect(model().attributeExists("user"));
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllToys")
                        .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllToys"));
        mockMvc.perform(MockMvcRequestBuilders.get("/toyAddForm")
                        .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/toyAddForm"));

        when(toyRepository.findByNameAndAgeRangeAndQuantity("new toy", "3-5", 5)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/toyAddForm")
                        .param("name", "new toy")
                        .param("ageRange", "3-5")
                        .param("quantity", String.valueOf(5))
                        .session(httpSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/showAllToys"));
    }

        @Test
    void testAddExistingCategory() throws Exception {
        // Simulate successful login
        MockHttpSession httpSession = new MockHttpSession();

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

        // Attempt to access the showAllToys page
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllToys")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllToys"));
        // Attempt to access the addForm page
        mockMvc.perform(MockMvcRequestBuilders.get("/toyAddForm")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/toyAddForm"));

        Toy toy = new Toy();
        toy.setName("Barbies");
        toy.setAgeRange("3-5");
        toy.setQuantity(5);
        List<Toy> toyList = new ArrayList<>();
        toyList.add(toy);
        when(toyRepository.findByNameAndAgeRangeAndQuantity("Barbies", "3-5", 5)).thenReturn(toyList);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/toyAddForm")
                        .param("name", "Barbies")
                        .param("ageRange", "3-5")
                        .param("quantity", String.valueOf(5))
                        .session(httpSession))
                .andExpect(MockMvcResultMatchers.view().name("users/toyAddForm")); // Expecting the addForm view
    }
    }



