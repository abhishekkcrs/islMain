# -------- Build stage --------
FROM eclipse-temurin:23-jdk-jammy AS builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY .mvn .mvn
COPY mvnw pom.xml ./
COPY src ./src

# Give executable permission to mvnw if needed (Linux/Mac)
RUN chmod +x mvnw

# Build the application (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# -------- Runtime stage --------
FROM eclipse-temurin:23-jre-jammy

# Set working directory
WORKDIR /app

# Copy the built jar from builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 (Spring Boot default)
EXPOSE 8080

# Run the application
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
