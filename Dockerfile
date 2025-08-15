# --- Stage 1: Build the application with Maven ---
# Use a Maven image that includes Java 24
FROM maven:3-eclipse-temurin-24 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the Maven command to build the project and create the JAR file
# -DskipTests skips running unit tests during this build
RUN mvn clean package -DskipTests


# --- Stage 2: Create the final, lightweight image ---
# Use a slim Java 24 runtime image
FROM eclipse-temurin:24-jre-jammy

# Set the working directory
WORKDIR /app

# Copy ONLY the built JAR file from the 'build' stage into this final image
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 to allow traffic to the application
EXPOSE 8080

# The command to run when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]