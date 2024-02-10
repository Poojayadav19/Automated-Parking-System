package com.parking.controller;

import java.util.Collections;
import java.util.List;

import com.parking.exception.BadRequestException;
import com.parking.exception.ElementNotFoundException;
import com.parking.model.ParkingSlot;
import lombok.extern.slf4j.XSlf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.dto.CarDetailsDTO;
import com.parking.model.Car;
import com.parking.service.ParkingService;

@RestController
@RequestMapping("/v1")
@XSlf4j
public class ParkingSlotController {
	
	@Autowired
	ParkingService parkingService;

	/**
	 * Create empty parking slots
	 *
 	 * @param slotCount represent total number of parking slots
	 * @return String contains status whether or no parking slots created
	 */
	@PostMapping("/create/parkingslots/{slotCount}")
	public ResponseEntity<String> parkingSlots(@PathVariable Integer slotCount) {
		try {
			parkingService.createParkingSlots(slotCount);
			return new ResponseEntity<>("Created a parking lot with " + String.valueOf(slotCount) + " slots", HttpStatus.CREATED);
		} catch (BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	/**
	 * Allocate parking space based on availability of slots
	 *
	 * @param carDetailsDTO contains car registration number and color
	 * @return String contains the slot number where car is parked
	 */
	@PostMapping("/park")
	public ResponseEntity<String> allocateParkingSlot(@RequestBody CarDetailsDTO carDetailsDTO) {
		try {
			ParkingSlot parkingSlot = parkingService.allocateParkingSlot(carDetailsDTO);
			return new ResponseEntity<>("Allocated slot number: " + parkingSlot.getSlotNumber(), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}

	}

	/**
	 * Vacate the parking slot
	 *
	 * @param slotNumber represent slot number where car is parked
	 * @return String contains availability status of slot number
	 * */
	@PutMapping("/leave/{slotNumber}")
	public ResponseEntity<String> deallocateParkingSlot(@PathVariable Integer slotNumber) throws Exception {
		try {
			parkingService.deallotcateParkingSlotBySlotNumber(slotNumber);
			return new ResponseEntity<>("Slot number " + slotNumber +" is free", HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		} catch (BadRequestException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	/**
	 * Find all cars registration number which are of given color
	 *
	 * @param color represent car color
	 * @return List<String> contains list of car registration number
	 */
	@GetMapping("/registrationnumbers/{color}")
	public ResponseEntity<List<String>> getRegistrationNumberListByColor(@PathVariable String color) {
		try {
			return new ResponseEntity<>(parkingService.getRegistrationNumberListByColor(color), HttpStatus.OK);
		} catch (ElementNotFoundException e) {
			return new ResponseEntity<>(Collections.singletonList(e.getMessage()), HttpStatus.CONFLICT);
		}
	}

	/**
	 * Find parking slot where car with given registration number parked
	 *
	 * @param registrationNumber represent car registration number
	 * @return Integer contain slot number
	 */
	@GetMapping("/slotnumber/{registrationNumber}")
	public ResponseEntity<Integer> slotNumberByRegistrationNumber(@PathVariable String registrationNumber) {
		return new ResponseEntity<>(parkingService.getParkingSlotByRegisterationNumber(registrationNumber), HttpStatus.OK);
	}

	/**
	 * Find slot numbers where cars with given color parked
	 *
	 * @param color represent car color
	 * @return List<Integer> contains list of slot numbers
	 */
	@GetMapping("/parkingslotlist/{color}")
	public ResponseEntity<List<Integer>> SlotNumbersByColour(@PathVariable String color) {
	    return new ResponseEntity<List<Integer>>(parkingService.getParkingSlotListByColor(color), HttpStatus.OK);
	}

	@GetMapping("/parking")
	public ResponseEntity<List<ParkingSlot>> getAllParkingSlots() {
		return new ResponseEntity<>(parkingService.getAllParkingSlots(), HttpStatus.OK);
	}

	@GetMapping("/cars")
	public ResponseEntity<List<Car>> getAllCars() {
		return new ResponseEntity<>(parkingService.getAllCars(), HttpStatus.OK);
	}
}
