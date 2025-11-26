# Этап сборки
FROM openjdk:17-jdk-slim AS builder
WORKDIR /app
COPY . .
RUN ./gradlew :file-api:bootJar --no-daemon

# Этап запуска
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /app/file-api/build/libs/file-api-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]