package com.api.parkingcontrol.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CAR")
@Getter
@Setter

public class Car implements Serializable {
    private static final long serialVersionUID = 1L; // control version number when serialized

    // change the default id(BigInt) to a id(UUID)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // others fields
    @Column(nullable = false, unique = true, length = 10)
    private String licencePlateCar;

    @Column(nullable = false, length = 100)
    private String brandCar;

    @Column(nullable = false, length = 100)
    private String modelCar;

    @Column(nullable = false, length = 50)
    private String colorCar;

}