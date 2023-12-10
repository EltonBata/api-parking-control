package com.api.parkingcontrol.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
import com.api.parkingcontrol.helpers.ValidationErrorMessages;
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

    @Autowired
    ValidationErrorMessages messages;

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto,
            BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = messages.errorMessages(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

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
    public ResponseEntity<Page<ParkingSpot>> getAllParkingSpot(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
        Page<ParkingSpot> parkingSpotList = parkingSpotService.findAll(pageable);

        if (!parkingSpotList.isEmpty()) {
            for (ParkingSpot ps : parkingSpotList) {
                UUID id = ps.getId();
                ps.add(linkTo(methodOn(ParkingSpotController.class).getOneParkingSpot(id)).withSelfRel());

                Car car = ps.getCar();

                UUID car_id = car.getId();

                car.add(linkTo(methodOn(CarController.class).getOneCar(car_id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpot(@PathVariable("id") UUID id) {

        Optional<ParkingSpot> optionalParkingSpot = parkingSpotService.findById(id);

        if (optionalParkingSpot.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found");
        }

        optionalParkingSpot.get().add(linkTo(ParkingSpotController.class).withSelfRel());

        Car car = optionalParkingSpot.get().getCar();

        UUID car_id = car.getId();

        car.add(linkTo(methodOn(CarController.class).getOneCar(car_id)).withSelfRel());

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
            @RequestBody @Valid ParkingSpotDto parkingSpotDto, BindingResult result) {

        if (result.hasErrors()) {
            List<String> errors = messages.errorMessages(result);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

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
