package com.parking.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.parking.dto.CarDetailsDTO;
import com.parking.exception.BadRequestException;
import com.parking.exception.ElementNotFoundException;
import com.parking.model.ParkingSlot;
import com.parking.service.ParkingService;

public class ParkingSlotControllerTest {
	@InjectMocks
	ParkingSlotController parkingSlotController;
	@Mock
	ParkingService parkingService;
	@Mock
	CarDetailsDTO carDetailsDTO;
	@Mock 
	ParkingSlot parkingSlot;
	List<String> registrationNumberList;
	List<Integer> slotNumbers;
	private final static Integer SLOT_COUNT= 10;
	private final static String REGISTRATION_NUMBER = "DL-02-BS-6070";
	private final static String COLOR = "White";
	private static final Integer SLOT_NUMBER = 3;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		carDetailsDTO = new CarDetailsDTO(REGISTRATION_NUMBER,COLOR);
		parkingSlot = new ParkingSlot();
		parkingSlot.setOccupied(true);
		parkingSlot.setSlotNumber(SLOT_NUMBER);
		registrationNumberList = new ArrayList<>();
		registrationNumberList.add(REGISTRATION_NUMBER);
		slotNumbers = new ArrayList<>();
		slotNumbers.add(SLOT_NUMBER);
	}
	
	@Test
	void parkingSlots_givenSlotCount_executeSuccessfully() {
		doNothing().when(parkingService).createParkingSlots(SLOT_COUNT);
		var response = parkingSlotController.parkingSlots(SLOT_COUNT);
		var expectedResponseMsg = "Created a parking lot with " + String.valueOf(SLOT_COUNT) + " slots";
		assertEquals(expectedResponseMsg, response.getBody());
	}
	
	@Test
	void parkingSlots_givenSlotNumber_throwException() {
		doThrow(new BadRequestException("Parking System Already Exists")).when(parkingService).createParkingSlots(SLOT_COUNT);
		var response  = parkingSlotController.parkingSlots(SLOT_COUNT);
		var expectedResponseMsg = "Parking System Already Exists";
		assertEquals(expectedResponseMsg, response.getBody());
	}
	
	@Test
	void allocateParkingSlot_givenCarDetails_executeSuccessfully() {
		Mockito.when(parkingService.allocateParkingSlot(carDetailsDTO)).thenReturn(parkingSlot);
		var response = parkingSlotController.allocateParkingSlot(carDetailsDTO);
		var expectedResponseMsg = "Allocated slot number: " + parkingSlot.getSlotNumber();
		assertEquals(expectedResponseMsg, response.getBody());
	}
	
	@Test
	void allocateParkingSlot_givenCarDetails_throwException() {
		Mockito.when(parkingService.allocateParkingSlot(carDetailsDTO)).thenThrow(new ElementNotFoundException("No Parking Slot Available"));
		var response = parkingSlotController.allocateParkingSlot(carDetailsDTO);
		var expectedResponseMsg = "No Parking Slot Available";
		assertEquals(expectedResponseMsg, response.getBody());
	}
	
	@Test
	void deallocateParkingSlot_givenSlotNumber_executeSuccessfully() {
		doNothing().when(parkingService).deallotcateParkingSlotBySlotNumber(SLOT_NUMBER);
		var response = parkingSlotController.deallocateParkingSlot(SLOT_NUMBER);
		var expectedResponseMsg = "Slot number " + SLOT_NUMBER +" is free";
		assertEquals(expectedResponseMsg, response.getBody());
	}
	
	@Test
	void deallocateParkingSlot_givenSlotNumber_throwException() {
		doThrow(new BadRequestException("No car is parked at slot " + SLOT_NUMBER)).when(parkingService).deallotcateParkingSlotBySlotNumber(SLOT_NUMBER);
		var response = parkingSlotController.deallocateParkingSlot(SLOT_NUMBER);
		var expectedResponseMsg = "No car is parked at slot " + SLOT_NUMBER;
		assertEquals(expectedResponseMsg, response.getBody());
	}
	
	@Test
	void getRegistrationNumberListByColor_givenColor_executeSuccessfully() {
		Mockito.when(parkingService.getRegistrationNumberListByColor(COLOR)).thenReturn(registrationNumberList);
		var response = parkingSlotController.getRegistrationNumberListByColor(COLOR);
		assertEquals(registrationNumberList, response.getBody());
	}
	
	@Test
	void getRegistrationNumberListByColor_givenColor_throwException() {
		Mockito.when(parkingService.getRegistrationNumberListByColor(COLOR)).thenThrow(new ElementNotFoundException("No car found with color " + COLOR));
		var response = parkingSlotController.getRegistrationNumberListByColor(COLOR);
		var expectedResponseMsg = "No car found with color " + COLOR;
		assertEquals(expectedResponseMsg, response.getBody().get(0));
	}
	
	@Test
	void slotNumberByRegistrationNumber_givenRegistrationNumber_executeSuccessfully() {
		Mockito.when(parkingService.getParkingSlotByRegisterationNumber(REGISTRATION_NUMBER)).thenReturn(SLOT_NUMBER);
		var response = parkingSlotController.slotNumberByRegistrationNumber(REGISTRATION_NUMBER);
		assertEquals(SLOT_NUMBER, response.getBody());
	}
	
	@Test
	void slotNumbersByColour_givenColor_executeSuccessfully() {
		Mockito.when(parkingService.getParkingSlotListByColor(COLOR)).thenReturn(slotNumbers);
		var response = parkingSlotController.slotNumbersByColour(COLOR);
		assertEquals(slotNumbers, response.getBody());
	}
}
