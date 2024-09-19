package com.example.space_app.service;

import com.example.space_app.model.Planet;
import com.example.space_app.repository.PlanetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.within;

@SpringBootTest
public class PlanetServiceTest {

    @Autowired
    private PlanetService planetService;

    @MockBean
    private PlanetRepository planetRepository;

    private Planet earth;

    @BeforeEach
    public void setUp() {
        earth = new Planet();
        earth.setId(1L);
        earth.setName("Earth");
        earth.setRadius(6371);
        earth.setOrbitRadius(149.6);
        earth.setMoons(1);
    }

    @Test
    public void testGetPlanetByName() {
        Mockito.when(planetRepository.findByName("Earth")).thenReturn(Optional.of(earth));

        Optional<Planet> result = planetService.getPlanetByName("Earth");

        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Earth");
        assertThat(result.get().getRadius()).isEqualTo(6371);
        assertThat(result.get().getOrbitRadius()).isCloseTo(149.6, within(0.1));
    }

    @Test
    public void testGetNonExistentPlanet() {
        Mockito.when(planetRepository.findByName("Pluto")).thenReturn(Optional.empty());

        Optional<Planet> result = planetService.getPlanetByName("Pluto");

        assertThat(result).isNotPresent();
    }
}
