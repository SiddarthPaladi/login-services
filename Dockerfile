# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/movecloud-login-service-0.0.1-SNAPSHOT.jar /app/movecloud-login-service-0.0.1-SNAPSHOT.jar

# Expose the port that the applications runs on
EXPOSE 8086

# Command to run the application
ENTRYPOINT ["java", "-jar", "/app/movecloud-login-service-0.0.1-SNAPSHOT.jar"]
