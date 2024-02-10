package com.parking.dto;

public class CarDetailsDTO {
	private String registrationNumber;
	private String color;
	
	public CarDetailsDTO(String registrationNumber, String color) {
		this.registrationNumber= registrationNumber;
		this.color=color;
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
