FROM eclipse-temurin:21-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the application JAR file
COPY notification/target/notification-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port (adjust as needed)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "app.jar"]
