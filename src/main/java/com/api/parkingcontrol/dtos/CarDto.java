package com.api.parkingcontrol.dtos;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.validation.constraints.NotBlank;

public record CarDto(
        @NotBlank @UniqueElements String licencePlateCar,
        @NotBlank String brandCar,
        @NotBlank String modelCar,
        @NotBlank String colorCar) {
}
