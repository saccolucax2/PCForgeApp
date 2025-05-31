# Stage 1: Build
FROM maven:3.9-eclipse-temurin-23 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Costruisci l'applicazione
RUN mvn clean package -DskipTests

# Stage 2: Runtime
FROM eclipse-temurin:23-jre

WORKDIR /app

# Copia il jar dall'immagine builder
COPY --from=builder /app/target/*.jar app.jar

# Variabili d'ambiente per MongoDB
ENV MONGO_ADDRESS=mongo
ENV MONGO_PORT=27017

# Esponi la porta dell'applicazione
EXPOSE 8084

# Comando per eseguire l'applicazione
CMD ["java", "-jar", "app.jar"]