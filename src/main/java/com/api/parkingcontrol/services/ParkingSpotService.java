package com.api.parkingcontrol.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.Car;
import com.api.parkingcontrol.models.ParkingSpot;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;

import jakarta.transaction.Transactional;

@Service
public class ParkingSpotService {

    // Injecting Dependency of repository
    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    // Dependency Injection of car service
    @Autowired
    CarService carService;

    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot, Car car) {

        Car savedCar = carService.save(car);

        parkingSpot.setCar(savedCar);

        ParkingSpot savedParkingSpot = parkingSpotRepository.save(parkingSpot);

        return savedParkingSpot;
    }

    public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
        return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
    }

    public boolean existsByApartmentAndBlock(String apartment, String block) {
        return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
    }

    public List<ParkingSpot> findAll() {
        return parkingSpotRepository.findAll();
    }

    public Optional<ParkingSpot> findById(UUID id) {
        return parkingSpotRepository.findById(id);
    }

    public boolean existsById(UUID id) {
        return parkingSpotRepository.existsById(id);
    }

    @Transactional
    public void delete(UUID id) {
        parkingSpotRepository.deleteById(id);
    }
}
