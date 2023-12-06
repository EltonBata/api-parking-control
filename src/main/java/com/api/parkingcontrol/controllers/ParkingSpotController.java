package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.dtos.ParkingSpotDto;
import com.api.parkingcontrol.models.Car;
import com.api.parkingcontrol.models.ParkingSpot;
import com.api.parkingcontrol.services.CarService;
import com.api.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/parking-spot")
public class ParkingSpotController {

    // Dependency Injection of service
    @Autowired
    ParkingSpotService parkingSpotService;

    @Autowired
    CarService carService;

    @PostMapping

    public ResponseEntity<ParkingSpot> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        var parkingSpot = new ParkingSpot();
        var car = new Car();

        //Converts validated parkingSpot data to parkingSpot model (bind) 
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot); 

        //Converts validated car data to car model (bind) 
        BeanUtils.copyProperties(parkingSpotDto, car); 

        //Set registrationData
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpot, car));

    }
}
