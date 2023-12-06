package com.api.parkingcontrol.dtos;

import org.hibernate.validator.constraints.UniqueElements;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParkingSpotDto(
        @NotBlank @UniqueElements String parkingSpotNumber,
        @NotBlank @Size(max = 7) String responsibleName,
        @NotBlank String apartment,
        @NotBlank String block) {
}
