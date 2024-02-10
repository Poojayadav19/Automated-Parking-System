package com.parking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name="CAR", uniqueConstraints = @UniqueConstraint(name = "UNIQUE_CAR",columnNames= {"registrationNumber"}))
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private long id;
	
	@Column(name="REGISTRATION_NUMBER", nullable = false)
	private String registrationNumber;
	
	@Column(name="COLOR", nullable = false)
	private String color;

	public Car() {}

    public Car(String registrationNumber, String colour) {
        this.registrationNumber = registrationNumber;
        this.color = colour;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

