package com.api.parkingcontrol.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PARKING_SPOT")
@Getter
@Setter

public class ParkingSpot extends RepresentationModel<ParkingSpot> implements Serializable {
    private static final long serialVersionUID = 1L; // control version number when serialized

    // change the default id(BigInt) to a id(UUID)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    // others fields
    @Column(nullable = false, unique = true, length = 10)
    private String parkingSpotNumber;

    @Column(nullable = false)
    private LocalDateTime registrationDate;

    @Column(nullable = false, length = 100)
    private String responsibleName;

    @Column(nullable = false, length = 100)
    private String apartment;

    @Column(nullable = false, length = 30)
    private String block;

    /** Relationships */

    @OneToOne
    private Car car;

   
}