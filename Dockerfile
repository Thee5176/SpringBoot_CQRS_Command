
FROM openjdk:21-jdk-slim
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=./target/*-exec.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=dev","-jar","/app.jar"]