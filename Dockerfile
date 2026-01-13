
# Build stage
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /build
COPY . .
RUN if [ -f mvnw ]; then ./mvnw clean package -DskipTests -q; else mvn clean package -DskipTests -q; fi

# Runtime stage
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
EXPOSE 8080
WORKDIR /app
COPY --from=builder /build/target/*-exec.jar app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","app.jar"]