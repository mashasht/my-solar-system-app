package com.example.space_app.controller;

import com.example.space_app.model.Planet;
import com.example.space_app.service.PlanetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PlanetController {

    private final PlanetService planetService;

    public PlanetController(PlanetService planetService) {
        this.planetService = planetService;
    }

    @GetMapping("/planets")
    public List<Planet> getAllPlanets() {
        return planetService.getAllPlanets();
    }

    @GetMapping("/planet")
    public ResponseEntity<Planet> getPlanetByName(@RequestParam String name) {
        Optional<Planet> planet = planetService.getPlanetByName(name);
        return planet.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/planet")
    public Planet addPlanet(@RequestBody Planet planet) {
        return planetService.addPlanet(planet);
    }
}
