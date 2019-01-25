FROM openjdk:8-jdk-alpine

MAINTAINER Nathan, Jimmy and Mika

COPY /target/swagger-spring-1.0.0.jar /opt/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar","/opt/swagger-spring-1.0.0.jar"]