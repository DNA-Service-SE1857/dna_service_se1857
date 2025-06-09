# Dùng image OpenJDK làm base
FROM eclipse-temurin:21-jre


# Thư mục làm việc trong container
WORKDIR /app

# Copy file jar vào container
COPY target/dna_service-0.0.1-SNAPSHOT.jar ./dna_service.jar

# Expose port 8080 (cổng ứng dụng chạy)
EXPOSE 8080

# Lệnh chạy app
ENTRYPOINT ["java", "-jar", "dna_service.jar"]