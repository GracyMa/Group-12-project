# Use the official OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy project files into the container
COPY . .

# Build the project
RUN ./mvnw clean package -DskipTests

# Set the startup command
CMD ["java", "-jar", "target/OnlineShoppingProject-0.0.1-SNAPSHOT.jar"]
