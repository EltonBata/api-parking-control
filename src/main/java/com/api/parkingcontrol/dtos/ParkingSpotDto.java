package com.api.parkingcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParkingSpotDto(
        @NotBlank(message = "{empty}") @Size(max = 7, message = "Parking Spot Number must have only 7 chars") String parkingSpotNumber,
        @NotBlank(message = "Responsible Name can not be blank") String responsibleName,
        @NotBlank(message = "Apartment can not be blank") String apartment,
        @NotBlank(message = "Block can not be blank") String block,
        @NotBlank(message = "Licence Plate Car can not be blank") String licencePlateCar,
        @NotBlank(message = "Brand Car can not be blank") String brandCar,
        @NotBlank(message = "Model Car can not be blank") String modelCar,
        @NotBlank(message = "Color car can not be blank") String colorCar) {
}
