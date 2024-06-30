# Использование минимального образа Maven для сборки проекта
FROM maven:3.8.6-eclipse-temurin-17 AS build

# Установка рабочей директории
WORKDIR /app

# Копирование файла конфигурации Maven и скачивание зависимостей
COPY pom.xml .
RUN mvn dependency:go-offline

# Копирование исходного кода проекта и сборка
COPY src ./src
COPY src/main/resources/application.properties ./src/main/resources/application.properties
RUN mvn clean package

# Использование минимального JDK для запуска приложения
FROM eclipse-temurin:17-jdk-alpine

# Установка рабочей директории
WORKDIR /app

# Копирование собранного jar-файла из предыдущего этапа
COPY --from=build /app/target/NbaPlayerStati-1.0-SNAPSHOT.jar /app/nba-stats.jar

# Открытие порта для приложения
EXPOSE 8080

# Установка команды для запуска приложения
ENTRYPOINT ["java", "-jar", "/app/nba-stats.jar"]
