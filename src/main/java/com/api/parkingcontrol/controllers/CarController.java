package com.api.parkingcontrol.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.parkingcontrol.models.Car;
import com.api.parkingcontrol.services.CarService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/car")
public class CarController {

    @Autowired
    CarService carService;

    @GetMapping
    public ResponseEntity<Page<Car>> getAllCars(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {

        Page<Car> cars = carService.findAll(pageable);

        if (!cars.isEmpty()) {
            for (Car car : cars) {
                UUID id = car.getId();

                car.add(linkTo(methodOn(CarController.class).getOneCar(id)).withSelfRel());
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(cars);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneCar(@PathVariable("id") UUID id){
        
        Optional<Car> car = carService.findById(id);

        if(car.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found");
 
        }

        car.get().add(linkTo(CarController.class).withSelfRel());

        return ResponseEntity.status(HttpStatus.OK).body(car);
    }

}
