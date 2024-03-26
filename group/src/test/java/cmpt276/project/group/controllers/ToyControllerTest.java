package cmpt276.project.group.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cmpt276.project.group.models.ToyRepository;
import cmpt276.project.group.controllers.ToyController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class ToyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToyController toyController;

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
        this.mockMvc.perform(get("/showAllToys"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].name", is("Books")))
                .andExpect(jsonPath("$[0].ageRange", is("9-13")))
                .andExpect(jsonPath("$[0].quantity", is(7)))
                .andExpect(jsonPath("$[1].name", is("Video games")))
                .andExpect(jsonPath("$[1].ageRange", is("9-12")))
                .andExpect(jsonPath("$[1].quantity", is(4)))
                .andExpect(jsonPath("$[2].name", is("Doll houses")))
                .andExpect(jsonPath("$[2].ageRange", is("5-8")))
                .andExpect(jsonPath("$[2].quantity", is(3)))
                .andExpect(jsonPath("$[3].name", is("Games")))
                .andExpect(jsonPath("$[3].ageRange", is("10-12")))
                .andExpect(jsonPath("$[3].quantity", is(3)));

    }

}
