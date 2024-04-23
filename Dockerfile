# Use OpenJDK 17 Slim as the base image
FROM openjdk:17-slim as build
WORKDIR /workspace/app

# Copy necessary files for building the application
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

# Build the application, skipping tests
RUN ./gradlew build -x test --info

# Use OpenJDK 17 Slim for the runtime image
FROM openjdk:17-slim
VOLUME /tmp
# Copy the built JAR from the build stage
COPY --from=build /workspace/app/build/libs/*.jar app.jar

# Set the entrypoint
ENTRYPOINT ["java","-jar","/app.jar"]

# Expose the application's port
EXPOSE 7382/tcp
