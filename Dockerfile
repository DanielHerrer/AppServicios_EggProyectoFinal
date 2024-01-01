# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
WORKDIR ./servicios  # Ajusta la ruta al directorio del proyecto
RUN mvn clean package -DskipTests

# Stage 2: Use the built artifact
FROM openjdk:17.0.1-jdk-slim
COPY --from=build ./servicios/target/servicios-1.0.0-SNAPSHOT.jar /app/servicios.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "servicios.jar"]
