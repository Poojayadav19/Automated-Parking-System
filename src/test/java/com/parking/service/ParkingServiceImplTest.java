package com.parking.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.parking.dto.CarDetailsDTO;
import com.parking.exception.BadRequestException;
import com.parking.model.ParkingSlot;
import com.parking.repository.CarRepository;
import com.parking.repository.ParkingSlotRepository;

public class ParkingServiceImplTest {

	@InjectMocks
	ParkingServiceImpl parkingService;
	@Mock
	CarRepository carRepository;
	@Mock
	ParkingSlotRepository parkingSlotRepository;
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
	void createParkingSlots_givenSlotCount_throwException() {
		 when(parkingSlotRepository.count()).thenReturn(1L);
		 assertThrows(BadRequestException.class, () -> parkingService.createParkingSlots(SLOT_COUNT));
	}
}
