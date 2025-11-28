# docker/api.Dockerfile
FROM eclipse-temurin:25-jre AS base

WORKDIR /app

# Copia o jar já construído
COPY build/libs/genai-etl-api.jar app.jar

# Healthcheck simples
HEALTHCHECK --interval=30s --timeout=5s --start-period=30s \
  CMD wget -qO- http://localhost:8080/actuator/health || exit 1

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
