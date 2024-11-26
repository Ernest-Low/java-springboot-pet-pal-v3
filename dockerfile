# Stage 1: Build the application using Maven
FROM maven:3.8.5-amazoncorretto-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy the application source code and build the application
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Run the application using Amazon Corretto
FROM amazoncorretto:17

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Do this only for local - as it won't be in git
COPY env.properties .

# Expose the port the application runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
