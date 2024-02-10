package com.parking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.model.Car;
import com.parking.model.ParkingSlot;
import java.util.List;
import java.util.Optional;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long>{
	Optional<ParkingSlot> findByCar_RegistrationNumber(String registrationNumber);
	List<ParkingSlot> findByIsOccupied(boolean isOccupied);
	Optional<ParkingSlot> findBySlotNumber(int slotNumber);
	Optional<ParkingSlot> findByCar(Car car);
	List<ParkingSlot> findAll();
	void deleteAll();
}
