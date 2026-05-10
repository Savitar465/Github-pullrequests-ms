# syntax=docker/dockerfile:1

# Build Spring Boot fat JAR
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /build

COPY mvnw pom.xml ./
COPY .mvn .mvn
COPY src src

RUN sed -i 's/\r$//' mvnw && chmod +x mvnw \
  && ./mvnw -B -DskipTests clean package

# Runtime
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

COPY --from=builder /build/target/github-pullrequest-ms-*.jar app.jar

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
