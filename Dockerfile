# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17 AS build
COPY . /app_Servicios_MendoReparo
WORKDIR /app_Servicios_MendoReparo/servicios  # Ajusta la ruta al directorio del proyecto
RUN mvn clean package -DskipTests

# Stage 2: Use the built artifact
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app_Servicios_MendoReparo/servicios/target/servicios-0.0.1-SNAPSHOT.jar /app/servicios.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "servicios.jar"]