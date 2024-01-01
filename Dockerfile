# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app  # Establece el directorio de trabajo en /app

# Copia el contenido del proyecto Spring Boot a /app
COPY ./servicios /app

# Ejecuta Maven en el directorio correcto
RUN mvn -f /app/pom.xml clean package -DskipTests

# Stage 2: Use the built artifact
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /app/target/servicios-1.0.0-SNAPSHOT.jar /app/servicios.jar
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "servicios.jar"]
