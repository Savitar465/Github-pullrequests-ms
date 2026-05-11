# syntax=docker/dockerfile:1

FROM eclipse-temurin:25-jre

WORKDIR /app

# Download AWS RDS SSL certificate
RUN apt-get update && apt-get install -y --no-install-recommends curl \
  && curl -o /app/global-bundle.pem https://truststore.pki.rds.amazonaws.com/global/global-bundle.pem \
  && apt-get purge -y curl && apt-get autoremove -y && rm -rf /var/lib/apt/lists/*

RUN groupadd -r spring && useradd -r -g spring spring

# Copy pre-built JAR from CI
COPY target/*.jar app.jar

RUN chown spring:spring /app/global-bundle.pem

USER spring:spring

ENV SERVER_PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
