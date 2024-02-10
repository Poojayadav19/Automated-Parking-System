package com.parking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.parking.dto.CarDetailsDTO;
import com.parking.exception.BadRequestException;
import com.parking.exception.ElementNotFoundException;
import com.parking.model.Car;
import com.parking.model.ParkingSlot;
import com.parking.repository.CarRepository;
import com.parking.repository.ParkingSlotRepository;

@Service
public class ParkingServiceImpl implements ParkingService{

	@Autowired
	ParkingSlotRepository parkingSlotRepository;
	
	@Autowired
	CarRepository carRepository;
	
	@Override
	public void createParkingSlots(Integer parkingSlotSize) {
		if(parkingSlotRepository.count() == 0) {
			ArrayList<ParkingSlot> parkingSlotList = new ArrayList<>();
			for(Integer slotIndex = 1 ; slotIndex <= parkingSlotSize ; slotIndex++) {
				ParkingSlot parkingSlot = new ParkingSlot(slotIndex, null);
				parkingSlotList.add(parkingSlot);
			}
			System.out.println(parkingSlotList);
			parkingSlotRepository.saveAll(parkingSlotList);
		} else {
			throw new BadRequestException("Parking System Already Exists");
		}
	}
	
	@Override
	public ParkingSlot allocateParkingSlot(CarDetailsDTO carDetailsDTO) {
		ParkingSlot availableParkingSlot = findEmptyParkingSlot();
		if(availableParkingSlot != null) {
			Car car = new Car(
					carDetailsDTO.getRegistrationNumber(),
					carDetailsDTO.getColor().toLowerCase()
			);
			availableParkingSlot.setCar(car);
			availableParkingSlot.setOccupied(true);
			parkingSlotRepository.save(availableParkingSlot);
		} else throw new ElementNotFoundException("No Parking Slot Available");
		return availableParkingSlot;
	}

	@Override
	public void deallocateParkingSlot(CarDetailsDTO carDetailsDTO) throws Exception {
		Optional<Car> car = carRepository.findByRegistrationNumber(carDetailsDTO.getRegistrationNumber());
		if(!car.isEmpty()) {
			Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findByCar(car.get());
			if(!parkingSlot.isEmpty()) {
				parkingSlot.get().setCar(null);
				carRepository.delete(car.get());
				parkingSlotRepository.save(parkingSlot.get());
			} else throw new ElementNotFoundException("This car is missing");
		} else throw new ElementNotFoundException("This car is not parked here");
	}

	@Override
	public void deallotcateParkingSlotBySlotNumber(Integer slotNumber) {
		Optional<ParkingSlot> parkingSlotOptional = parkingSlotRepository.findBySlotNumber(slotNumber);
		if(!parkingSlotOptional.isEmpty()) {
			ParkingSlot parkingSlot = parkingSlotOptional.get();
			Car car = parkingSlot.getCar();
			if(car != null) {
				parkingSlot.setCar(null);
				parkingSlot.setOccupied(false);
				parkingSlotRepository.save(parkingSlot);
				carRepository.delete(car);
			} else throw new BadRequestException("No car is parked at slot " + slotNumber);
		} else throw new ElementNotFoundException("Slot number " + slotNumber + " does not exist");
	}

	@Override
	public List<ParkingSlot> getAllParkingSlots() {
		return parkingSlotRepository.findAll();
	}

	@Override
	public List<Car> getAllCars() {
		return carRepository.findAll();
	}

	@Override
	public List<String> getRegistrationNumberListByColor(String color) {
		List<String> registrationNumberList = carRepository.findRegistrationNumbersByColor(color.toLowerCase());
		if(registrationNumberList.size() > 0) {
			return registrationNumberList;
		} else throw new ElementNotFoundException("No car found with color " + color);
	}

	@Override
	public Integer getParkingSlotByRegisterationNumber(String registrationNumber) {
		Optional<ParkingSlot> parkingSlot = parkingSlotRepository.findByCar_RegistrationNumber(registrationNumber);
		if(!parkingSlot.isEmpty()) return parkingSlot.get().getSlotNumber();
		else throw new ElementNotFoundException("This car is not parked here");
	}

	@Override
	public List<Integer> getParkingSlotListByColor(String color) {
		List<Integer> slotNumbers = new ArrayList<>();
		List<Car> cars = carRepository.findByColor(color.toLowerCase());
		if(cars.size() > 0) {
			for (Car car : cars) {
				Optional<ParkingSlot> parkingSlotOptional = parkingSlotRepository.findByCar(car);
				parkingSlotOptional.ifPresent(parkingSlot -> slotNumbers.add(parkingSlot.getSlotNumber()));
			}
			return slotNumbers;
		} else throw new ElementNotFoundException("No car found with color " + color);
	}

	public List<ParkingSlot> getAllAvailableParkingSlots() {
		return parkingSlotRepository.findByIsOccupied(false);
	}

	private ParkingSlot findEmptyParkingSlot() {
		List<ParkingSlot> availableSlots = getAllAvailableParkingSlots();
		ParkingSlot availableParkingSlot = null;
		if (availableSlots.size() > 0) {
			for(ParkingSlot parkingSlot : availableSlots) {
				if(availableParkingSlot == null || availableParkingSlot.getSlotNumber() > parkingSlot.getSlotNumber()) {
					availableParkingSlot = parkingSlot;
				}
			}
		}
		return availableParkingSlot;
	}

}
