
FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=./target/*-exec.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/app.jar"]