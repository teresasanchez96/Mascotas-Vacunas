FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src
COPY datos-prueba ./datos-prueba

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar
COPY --from=build /app/datos-prueba ./datos-prueba

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
