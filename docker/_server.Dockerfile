# _server.Dockerfile
FROM openjdk:17-slim as build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src



# Build the application, skipping tests, and list JAR files
RUN ./gradlew build -x test && ls /workspace/app/build/libs/

FROM openjdk:17-slim
VOLUME /tmp

# Copy the specific JAR file to the root directory and name it app.jar
COPY --from=build /workspace/app/build/libs/*-SNAPSHOT.jar /app.jar




ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 7382/tcp