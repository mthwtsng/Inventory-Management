package cmpt276.project.group.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cmpt276.project.group.models.*;
import cmpt276.project.group.controllers.ToyController;

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
public class ClothingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToyController clothingController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ClothingRepository clothingRepository;
    @BeforeEach
    void setup() {
        System.out.println("Setting up");
    }

    @Test
    void testContextLoads() {
        assertNotNull(clothingController);
    }
    @Test
    void testGetAllClothing() throws Exception {
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

        // Attempt to access the showAllClothes page
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllClothes")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllClothes"));
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
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllClothes")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllClothes"));
        // Attempt to access the addForm page
        mockMvc.perform(MockMvcRequestBuilders.get("/clothingAddForm")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/clothingAddForm"));
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
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllClothes")
                        .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllClothes"));
        mockMvc.perform(MockMvcRequestBuilders.get("/clothingAddForm")
                        .session(httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/clothingAddForm"));
        when(clothingRepository.findByTypeAndGenderAndAgeRangeAndQuantity("new clothing item", "boys","3-5", 5)).thenReturn(new ArrayList<>());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/clothingAddForm")
                        .param("type", "new clothing item")
                        .param("gender","boys")
                        .param("ageRange", "3-5")
                        .param("quantity", String.valueOf(5))
                        .session(httpSession))
                .andExpect(status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/showAllClothes"));
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
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllClothes")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/showAllClothes"));
        // Attempt to access the addForm page
        mockMvc.perform(MockMvcRequestBuilders.get("/clothingAddForm")
                        .session((MockHttpSession) httpSession))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("users/clothingAddForm"));

        Clothing clothing = new Clothing();
        clothing.setType("Long sleeve shirts");
        clothing.setGender("girls");
        clothing.setAgeRange("3-5");
        clothing.setQuantity(7);
        List<Clothing> clothingList = new ArrayList<>();
        clothingList.add(clothing);
        when(clothingRepository.findByTypeAndGenderAndAgeRangeAndQuantity("Long sleeve shirts", "girls","3-5", 7)).thenReturn(clothingList);

        mockMvc.perform(MockMvcRequestBuilders.post("/users/clothingAddForm")
                        .param("type", "Long sleeve shirts")
                        .param("gender","girls")
                        .param("ageRange", "3-5")
                        .param("quantity", String.valueOf(7))
                        .session(httpSession))
                .andExpect(MockMvcResultMatchers.view().name("users/clothingAddForm")); // Expecting the addForm view
    }
}



