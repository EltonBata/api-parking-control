package com.api.parkingcontrol.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParkingSpotDto(
                @NotBlank @Size(max = 7) String parkingSpotNumber,
                @NotBlank String responsibleName,
                @NotBlank String apartment,
                @NotBlank String block,
                @NotBlank String licencePlateCar,
                @NotBlank String brandCar,
                @NotBlank String modelCar,
                @NotBlank String colorCar) {
}
