FROM maven:3.6.0-jdk-8-slim AS build
COPY src /HomeFit/backend/src
COPY pom.xml /HomeFit/backend/
USER root
RUN --mount=type=cache,target=/root/.m2 mvn -DskipTests=true -f /HomeFit/backend/pom.xml clean package

FROM openjdk:8-jre-alpine
COPY --from=build /HomeFit/backend/target/app.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/usr/local/lib/app.jar"]

