package cmpt276.project.group.controllers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import cmpt276.project.group.models.ClothingRepository;

import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AutoConfigureMockMvc
public class clothingControllerTest {

    @Autowired
    private ClothingController clothingController;



    @MockBean
    private ClothingRepository clothingRepository;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private HttpSession httpSession;

    @Test
    void testGetAccessWithoutLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showAllClothes"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/login"));
    }
};
   // void setup() {
     //   httpSession = new MockHttpSession();
    //}
