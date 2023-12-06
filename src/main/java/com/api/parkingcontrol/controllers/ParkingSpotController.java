package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        // Verify some conflits with stored data that must be unique
        if (carService.existsByLicencePlateCar(parkingSpotDto.licencePlateCar())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Licence Plate Car is already in use");
        }

        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.parkingSpotNumber())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Parking Spot Number is already in use");
        }

        if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.apartment(), parkingSpotDto.block())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Parking Spot registered for this apartment or block");
        }

        var parkingSpot = new ParkingSpot();
        var car = new Car();

        // Converts validated parkingSpot data to parkingSpot model (bind)
        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);

        // Converts validated car data to car model (bind)
        BeanUtils.copyProperties(parkingSpotDto, car);

        // Set registrationData
        parkingSpot.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC")));

        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpot,
                car));

    }

    @GetMapping
    public ResponseEntity<List<ParkingSpot>> getAllParkingSpot() {
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable("id") UUID id) {

        Optional<ParkingSpot> optionalParkingSpot = parkingSpotService.findById(id);

        if (optionalParkingSpot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(optionalParkingSpot.get());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable("id") UUID id) {

        if (!parkingSpotService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }

        parkingSpotService.delete(id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Parking Spot deleted");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateParkingSpot(@PathVariable("id") UUID id,
            @RequestBody @Valid ParkingSpotDto parkingSpotDto) {

        Optional<ParkingSpot> optionalParkingSpot = parkingSpotService.findById(id);

        if (optionalParkingSpot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }

        var parkingSpot = optionalParkingSpot.get();
        var car = optionalParkingSpot.get().getCar();

        BeanUtils.copyProperties(parkingSpotDto, parkingSpot);
        BeanUtils.copyProperties(parkingSpotDto, car);

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpot, car));
    }
}
