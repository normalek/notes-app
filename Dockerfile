#
# Build stage
#
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:21-jdk-slim
COPY --from=build /home/app/target/notes-app-0.0.1-SNAPSHOT.jar /usr/local/lib/notes-app-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/notes-app-0.0.1-SNAPSHOT.jar"]