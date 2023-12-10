package com.api.parkingcontrol.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
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

    public Page<Car> findAll(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    public Optional<Car> findById(UUID id) {
        return carRepository.findById(id);
    }

}
