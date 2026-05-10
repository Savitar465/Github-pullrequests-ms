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

# Download AWS RDS SSL certificate
RUN apk add --no-cache curl \
  && curl -o /app/global-bundle.pem https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem \
  && apk del curl

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=builder /build/target/github-pullrequest-ms-*.jar app.jar

RUN chown spring:spring /app/global-bundle.pem

USER spring:spring

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
