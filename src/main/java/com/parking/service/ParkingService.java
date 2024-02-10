package com.parking.service;

import java.util.List;

import com.parking.dto.CarDetailsDTO;
import com.parking.model.Car;
import com.parking.model.ParkingSlot;

public interface ParkingService {
	void createParkingSlots(Integer number);
	ParkingSlot allocateParkingSlot(CarDetailsDTO carDetailsDTO) ;
	void deallocateParkingSlot(CarDetailsDTO carDetailsDTO) throws Exception;
	List<String> getRegistrationNumberListByColor(String colour);
	Integer getParkingSlotByRegisterationNumber(String registrationNumber);
	List<Integer> getParkingSlotListByColor(String colour);
	void deallotcateParkingSlotBySlotNumber(Integer slotNumber);
	List<ParkingSlot> getAllParkingSlots();
	List<Car> getAllCars();
}
