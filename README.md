# Automated Parking System

## Overview
The Automated Parking System is a Spring Boot project designed to manage a parking lot with linear parking slots. It facilitates the entry and exit of cars, as well as various operations related to parking slot allocation and vacancy.

## Technology Stack
- Spring Boot
- H2 Database

## Controllers

### Create Parking Slots
Endpoint: POST /create/parkingslots/{slotCount}

Creates parking slots in the parking lot.

### Allocate Parking Slot
Endpoint: POST /park

Allocates a parking slot for a car.

### Deallocate Parking Slot
Endpoint: PUT /leave/{slotNumber}

Frees up a parking slot.

### Get Registration Number List by Color
Endpoint: GET /registrationnumbers/{color}

Retrieves a list of car registration numbers based on color.

### Get Slot Number by Registration Number
Endpoint: GET /slotnumber/{registrationNumber}

Retrieves the slot number where a car with the given registration number is parked.

### Get Slot Numbers by Color
Endpoint: GET /parkingslotlist/{color}

Retrieves slot numbers where cars with the given color are parked.

### Get All Parking Slots
Endpoint: GET /parkingstatus

Retrieves information about all parking slots.

### Get All Cars
Endpoint: GET /cars

Retrieves information about all cars parked.

## Note
- Make sure to replace {slotCount}, {color}, and {registrationNumber} with appropriate values when making requests to the endpoints.


## Instructions to Execute the Code

1. Open a terminal window, navigate to folder "Execution-Files"
2. Make sure no process is running on port 8089
3. Now run the command "sh start-service.sh"
4. Now open another terminal window, and navigate to folder "Execution-Files"
5. Run command "sh start-parking-system.sh" to get started with the command line interactive interface to the service

Sample commands supported are following:

1. create_parking_lot 6
2. park KA-01-HH-1234 White
3. leave 4
4. status
5. registration_numbers_for_cars_with_colour White
6. slot_number_for_registration_number KA-01-HH-1234
7. slot_numbers_for_car_color White
