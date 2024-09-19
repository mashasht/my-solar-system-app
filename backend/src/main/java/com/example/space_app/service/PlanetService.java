package com.example.space_app.service;

import com.example.space_app.model.Planet;
import com.example.space_app.repository.PlanetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.List;

@Service
public class PlanetService {

    private final PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository) {
        this.planetRepository = planetRepository;
    }

    public List<Planet> getAllPlanets() {
        return planetRepository.findAll();
    }

    public Optional<Planet> getPlanetByName(String name) {
        return planetRepository.findByName(name);
    }

    public Planet addPlanet(Planet planet) {
        return planetRepository.save(planet);
    }
}
