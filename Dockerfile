# Use a specific version of the OpenJDK 21 image
FROM openjdk:21-jdk-slim AS build

# Set the working directory for the build stage
WORKDIR /app

# Copy the Maven wrapper and pom.xml
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this step leverages Docker cache to speed up future builds)
RUN ./mvnw dependency:go-offline -B

# Copy the source code
COPY src src

# Package the application (skip tests to speed up the build, can be adjusted)
RUN ./mvnw clean package -DskipTests

# Create a minimal runtime image
FROM openjdk:21-jdk-slim AS runtime

# Set the working directory for the runtime stage
WORKDIR /app

# Copy the JAR file from the build stage
COPY --from=build /app/target/fap-system-0.0.1-SNAPSHOT.jar /app/fap-system-0.0.1-SNAPSHOT.jar

# Expose the application port
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "/app/fap-system-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

