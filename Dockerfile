# Stage 1: Build the application
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# Install netcat for wait-for-it.sh
RUN apk add --no-cache bash netcat-openbsd

# Copy wait script
COPY wait-for-it.sh .

# Copy built jar
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["./wait-for-it.sh", "mongo", "27017", "--", "./wait-for-it.sh", "service-registry", "8761", "--", "java", "-jar", "app.jar"]