package com.api.parkingcontrol.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.parkingcontrol.models.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    boolean existsByLicencePlateCar(String lincencePlateCar);
} 