package com.example.space_app.controller;

import com.example.space_app.model.Planet;
import com.example.space_app.repository.PlanetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PlanetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanetRepository planetRepository;

    @BeforeEach
    public void setUp() {
        // Clean up and insert a known planet for testing
        planetRepository.deleteAll();
        planetRepository.save(new Planet(null, "Earth", 6371, 149.6, 1));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void shouldReturnPlanetByName() throws Exception {
        mockMvc.perform(get("/planet?name=Earth")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Earth"))
                .andExpect(jsonPath("$.radius").value(6371));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void shouldReturnNotFoundForUnknownPlanet() throws Exception {
        mockMvc.perform(get("/planet?name=Pluto")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
