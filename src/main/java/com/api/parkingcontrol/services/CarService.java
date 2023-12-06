package com.api.parkingcontrol.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.parkingcontrol.models.Car;
import com.api.parkingcontrol.repositories.CarRepository;

@Service
public class CarService {

    // Injecting Dependency of repository
    @Autowired
    CarRepository carRepository;

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public boolean existsByLicencePlateCar(String licencePlateCar) {
        return carRepository.existsByLicencePlateCar(licencePlateCar);
    }

}
