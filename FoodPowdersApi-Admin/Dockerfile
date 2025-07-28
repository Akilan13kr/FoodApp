# Stage 1: Build with Maven and cache dependencies
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copy only pom.xml and download dependencies first
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw dependency:go-offline -B

# Copy the rest of the code
COPY . .

# Build the app
RUN ./mvnw clean package -DskipTests

# Stage 2: Final image with only the JAR
FROM eclipse-temurin:21
WORKDIR /app
COPY --from=build /app/target/FoodPowdersApi-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 10000
ENTRYPOINT ["java", "-jar", "app.jar"]
