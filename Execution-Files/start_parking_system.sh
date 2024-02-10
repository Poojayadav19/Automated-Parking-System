#!/bin/bash

BASE_URL="http://localhost:8089/parkingservice/v1"

# Function to call API based on input
call_api() {
    input=$1
    # Extracting words from input
    words=($input)
    
    case ${words[0]} in
        "create_parking_lot")
            size=${words[1]}
            response=$(curl -X POST -s -w "%{http_code}" "$BASE_URL/create/parkingslots/$size")
            ;;
        "park")
            reg_num=${words[1]}
            color=${words[2]}
            response=$(curl -X POST -s -w "%{http_code}" -H "Content-Type: application/json" -d "{\"registrationNumber\": \"$reg_num\", \"color\": \"$color\"}" "$BASE_URL/park")
            ;;
        "leave")
            slot_num=${words[1]}
            response=$(curl -X PUT -s -w "%{http_code}" "$BASE_URL/leave/$slot_num")
            ;;
        "status")
            response=$(curl -s "$BASE_URL/parkingstatus")
            ;;
        "registration_numbers_for_cars_with_colour")
            color=${words[1]}
            response=$(curl -s "$BASE_URL/registrationnumbers/$color")
            ;;
        "slot_number_for_registration_number")
            reg_num=${words[1]}
            response=$(curl -s "$BASE_URL/slotnumber/$reg_num")
            ;;
        "slot_numbers_for_car_color")
            color=${words[1]}
            response=$(curl -s "$BASE_URL/parkingslotlist/$color")
            ;;
        *)
            echo "Invalid input"
            return
            ;;
    esac

    if [[ "$response" =~ "Not Found" ]]; then
        echo "Not Found"

    else
        # Extract response status code
        http_code=${response:(-3)}
        # Remove the last line (status code) from the response
        echo "Response: $response"
        length_of_body=${#response}
        if [ $length_of_body -gt 3 ]; then
            body=${response:0:$((length_of_body-3))}
        else
            body=response
        fi
        
        if [[ "$http_code" != *[* && "$http_code" != *]* ]] && [[ "$http_code" -eq 200 || "$http_code" -eq 201 || "$http_code" -eq 409  || "$http_code" -eq 404 ]]; then
            if [ "${words[0]}" == "status" ]; then
                # Format and print status response
                echo "$body" | jq -r '.[] | "\(.slotNumber) \(.car.registrationNumber) \(.car.color)"' | column -t
            else
                echo "$body"
            fi
        else
            echo "$response"
        fi
    fi
}

# Main script
while true; do
    read -p "Enter input (type 'exit' to quit): " userInput
    
    # Check if user wants to exit
    if [ "$userInput" == "exit" ]; then
        echo "Exiting..."
        break
    fi
    
    # Call function to process input
    call_api "$userInput"
done