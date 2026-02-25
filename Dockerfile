# Build stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy only backend pom first for dependency caching
COPY backend/pom.xml backend/pom.xml
RUN mvn -f backend/pom.xml dependency:go-offline -B

# Copy backend source
COPY backend/src backend/src

# Build
RUN mvn -f backend/pom.xml clean package -DskipTests -B

# Run stage
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the specific repackaged jar (not the .original)
COPY --from=build /app/backend/target/unigov-0.0.1-SNAPSHOT.jar app.jar

# Create uploads directory
RUN mkdir -p uploads

# Expose port
EXPOSE 8081

# Start - use shell form so env vars expand properly
CMD java -Xmx512m \
    -Dserver.port=${PORT:-8081} \
    -jar app.jar
