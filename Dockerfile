# Stage 1: Build with Maven
FROM maven:3.8.3-openjdk-17  AS build
COPY . /servicios
WORKDIR /servicios  # Establecer como directorio de trabajo
RUN mvn clean package -DskipTests

# Stage 2: Use the built artifact
FROM openjdk:17.0.1-jdk-slim
COPY --from=build target/servicios-1.0.0-SNAPSHOT.jar servicios.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "servicios.jar"]