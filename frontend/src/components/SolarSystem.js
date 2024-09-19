import React, { useEffect, useState } from 'react';
import { Container, Typography } from '@mui/material';
import DeckGL from '@deck.gl/react';
import { OrbitView } from '@deck.gl/core';
import { SimpleMeshLayer } from '@deck.gl/mesh-layers';
import { SphereGeometry } from 'three';  // Import SphereGeometry from Three.js
import { getPlanets } from '../services/api';  // Import the API service

const INITIAL_VIEW_STATE = {
  target: [0, 0, 0],  // Center the view
  zoom: -1,  // Slightly zoom out
  minZoom: -5,
  maxZoom: 5,
  pitch: 45,
  bearing: 0,
};

const ORBIT_SCALE = 1e-6;  // Smaller scale for orbit distances
const PLANET_SCALE = 1e-3;  // Smaller scale for planet sizes

const getPlanetColor = (planetName) => {
  switch (planetName.toLowerCase()) {
    case 'mercury':
      return [169, 169, 169];  // Gray
    case 'venus':
      return [255, 165, 0];    // Orange
    case 'earth':
      return [0, 102, 204];    // Blue
    case 'mars':
      return [255, 69, 0];     // Red
    case 'jupiter':
      return [204, 102, 0];    // Dark orange
    case 'saturn':
      return [255, 223, 0];    // Yellow
    case 'uranus':
      return [173, 216, 230];  // Light blue
    case 'neptune':
      return [0, 0, 255];      // Blue
    default:
      return [255, 255, 255];  // White for any unknown planet
  }
};

const createSphereMeshForPlanet = (radius) => {
  const geometry = new SphereGeometry(radius, 32, 32);
  const { position } = geometry.attributes;
  const positions = new Float32Array(position.array);

  return {
    positions: { value: positions, size: 3 },
  };
};

const SolarSystem = () => {
  const [planets, setPlanets] = useState([]);

  useEffect(() => {
    const fetchPlanets = async () => {
      try {
        const data = await getPlanets();
        console.log("Fetched planets:", data);
        setPlanets(data);
      } catch (error) {
        console.error("Failed to fetch planets", error);
      }
    };

    fetchPlanets();
  }, []);

  const layers = planets.map(planet => (
    new SimpleMeshLayer({
      id: `planet-layer-${planet.name}`,
      data: [planet],
      mesh: createSphereMeshForPlanet(planet.radius * PLANET_SCALE),
      pickable: true,
      getPosition: () => [planet.orbitRadius * ORBIT_SCALE, 0, 0],
      getColor: () => getPlanetColor(planet.name),
      getScale: () => [1, 1, 1],
    })
  ));

  return (
    <Container>
      <Typography variant="h4" gutterBottom>
        Solar System Representation
      </Typography>

      {/* DeckGL component for visualizing the planets */}
      <DeckGL
        initialViewState={INITIAL_VIEW_STATE}
        controller={true}
        views={new OrbitView({})}
        layers={layers}  // Attach layers to Deck.GL
        style={{ height: '1000px', marginTop: '20px' }}
      />
    </Container>
  );
};

export default SolarSystem;
