FROM maven:3.8.3-openjdk-17 AS build
COPY ./servicios ./servicios
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build ./servicios/target/servicios-1.0.0-SNAPSHOT.jar servicios.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "servicios.jar"]
