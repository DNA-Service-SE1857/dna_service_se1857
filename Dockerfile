# Giai đoạn 1: build bằng Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Giai đoạn 2: chạy app bằng JRE
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/dna_service-0.0.1-SNAPSHOT.jar ./dna_service.jar

EXPOSE 8080
CMD ["java", "-jar", "dna_service.jar"]
