# ---------- Build Stage ----------
FROM bellsoft/liberica-openjdk-debian:23 as builder

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and POM files first for layer caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Ensure mvnw is executable (important on Windows)
RUN chmod +x mvnw

# Download dependencies (improves Docker layer caching)
RUN ./mvnw dependency:go-offline

# Now copy the rest of the application code
COPY src ./src

# Package the Spring Boot application (skip tests to speed up)
RUN ./mvnw clean package -DskipTests

# ---------- Runtime Stage ----------
FROM bellsoft/liberica-openjdk-debian:23

# Set working directory
WORKDIR /app

# Copy only the final jar from builder image
COPY --from=builder /app/target/*.jar app.jar

# Expose the Spring Boot default port
EXPOSE 8080

# Set entrypoint
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
