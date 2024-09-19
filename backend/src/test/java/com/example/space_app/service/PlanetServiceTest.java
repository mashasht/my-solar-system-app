package com.example.space_app.service;

import com.example.space_app.model.Planet;
import com.example.space_app.repository.PlanetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PlanetServiceTest {

    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetService planetService;

    private Planet earth;
    private Planet mars;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        earth = new Planet(1L, "Earth", 6371, 149.6, 1);
        mars = new Planet(2L, "Mars", 3389.5, 227.9, 2);
    }

    @Test
    public void testGetAllPlanets() {
        when(planetRepository.findAll()).thenReturn(Arrays.asList(earth, mars));

        List<Planet> planets = planetService.getAllPlanets();

        assertNotNull(planets);
        assertEquals(2, planets.size());
        verify(planetRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlanetByName() {
        when(planetRepository.findByName("Earth")).thenReturn(Optional.of(earth));

        Optional<Planet> planet = planetService.getPlanetByName("Earth");

        assertTrue(planet.isPresent());
        assertEquals("Earth", planet.get().getName());
        verify(planetRepository, times(1)).findByName("Earth");
    }

    @Test
    public void testAddPlanet() {
        when(planetRepository.save(any(Planet.class))).thenReturn(earth);

        Planet savedPlanet = planetService.addPlanet(earth);

        assertNotNull(savedPlanet);
        assertEquals("Earth", savedPlanet.getName());
        verify(planetRepository, times(1)).save(earth);
    }
}
