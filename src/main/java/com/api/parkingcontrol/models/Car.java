package com.api.parkingcontrol.models;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAR")
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

    /** Relationships */

    @OneToOne
    private ParkingSpot parkingSpot;

    /** Getters and Setters */

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getLicencePlateCar() {
        return licencePlateCar;
    }

    public void setLicencePlateCar(String licencePlateCar) {
        this.licencePlateCar = licencePlateCar;
    }

    public String getBrandCar() {
        return brandCar;
    }

    public void setBrandCar(String brandCar) {
        this.brandCar = brandCar;
    }

    public String getModelCar() {
        return modelCar;
    }

    public void setModelCar(String modelCar) {
        this.modelCar = modelCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public void setParkingSpot(ParkingSpot parkingSpot_id) {
        this.parkingSpot = parkingSpot_id;
    }

}