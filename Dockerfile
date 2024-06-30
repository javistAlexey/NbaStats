
FROM maven:3.8.6-openjdk-17 AS build
COPY pom.xml /app/
COPY src /app/src/
WORKDIR /app
RUN mvn clean package

FROM openjdk:17-jdk-slim
COPY --from=build /app/target/nba-stats-1.0-SNAPSHOT.jar /nba-stats.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "/nba-stats.jar"]
