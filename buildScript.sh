#!/bin/bash

# Check if Java 21 is installed
JAVA_VERSION=$(java -version 2>&1 | head -n 1)
if [[ "$JAVA_VERSION" == *"21"* ]]; then
  echo "Java 21 is installed."
else
  echo "Java 21 is not installed. Please install Java 21."
  exit 1
fi

# Navigate to transaction-service directory and run mvn clean install
cd transaction-service || { echo "Failed to enter transaction-service directory"; exit 1; }
echo "Running mvn clean install in transaction-service directory..."
./mvnw clean install || { echo "mvn clean install failed in transaction-service"; exit 1; }

# Move one directory up and then into notification directory, and run mvn clean install
cd .. || { echo "Failed to go up one directory"; exit 1; }
cd notification || { echo "Failed to enter notification directory"; exit 1; }
echo "Running mvn clean install in notification directory..."
./mvnw clean install || { echo "mvn clean install failed in notification"; exit 1; }

echo "Builds completed successfully!"