# ---------- Build Stage ----------
FROM bellsoft/liberica-openjdk-debian:23 as builder

WORKDIR /app

# Copy Maven wrapper and build files
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Download dependencies for caching
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy the rest of the code
COPY src ./src

# Build the app (skip tests for speed)
RUN ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM bellsoft/liberica-openjdk-debian:23

WORKDIR /app

# Copy the built JAR from builder
COPY --from=builder /app/target/*.jar app.jar

# Expose the port (Spring Boot default)
EXPOSE 8080

# Run the app
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
