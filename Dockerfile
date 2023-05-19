FROM gradle:8.1.0-jdk17 AS build
MAINTAINER Alexander <BigTows> Chapchuk
COPY --chown=gradle:gradle . /home/gradle/project
WORKDIR /home/gradle/project

RUN gradle clean build

FROM openjdk:17

EXPOSE 8080

RUN mkdir /app
COPY --from=build /home/gradle/project/build/libs/*.jar /app/application.jar
CMD ["java", "-jar","/app/application.jar"]
