echo "Starting Service"

touch "/tmp/parking-service.log"
chmod 777 "/tmp/parking-service.log"

java -jar ./parking-system.jar &

echo "Please wait while service starts"
sleep 6