# Use a JDK base image
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /app
COPY ../src .
RUN chmod +x ./mvnw

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]