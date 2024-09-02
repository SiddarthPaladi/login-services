# Use an ARM64-compatible OpenJDK image
FROM arm64v8/openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/movecloud-login-service-0.0.1-SNAPSHOT.jar /app/movecloud-login-service-0.0.1-SNAPSHOT.jar

# Expose the port that the application runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/movecloud-login-service-0.0.1-SNAPSHOT.jar"]
