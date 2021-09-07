FROM openjdk:8-jdk-alpine
MAINTAINER code4copy.com
EXPOSE 80
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]