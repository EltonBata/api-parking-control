package com.api.parkingcontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.Car;
import com.api.parkingcontrol.models.ParkingSpot;
import com.api.parkingcontrol.repositories.CarRepository;
import com.api.parkingcontrol.repositories.ParkingSpotRepository;

import jakarta.transaction.Transactional;

@Service
public class ParkingSpotService {

    // Injecting Dependency of repository
    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    @Autowired
    CarRepository carRepository;

    @Transactional
    public ParkingSpot save(ParkingSpot parkingSpot, Car car) {

        Car savedCar = carRepository.save(car);

        parkingSpot.setCar(savedCar);

        ParkingSpot savedParkingSpot = parkingSpotRepository.save(parkingSpot);

        return savedParkingSpot;
    }
}
