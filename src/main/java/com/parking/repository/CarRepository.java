package com.parking.repository;

import java.util.List;
import java.util.Optional;

import com.parking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import com.parking.model.Car;
import org.springframework.data.jpa.repository.Query;

public interface CarRepository extends JpaRepository<Car, Long> {

	@Query("SELECT c.registrationNumber FROM Car c WHERE c.color = :color")
	List<String> findRegistrationNumbersByColor(String color);
	Optional<Car> findByRegistrationNumber(String registrationNumber);
	List<Car> findByColor(String color);
	List<Car> findAll();
}
