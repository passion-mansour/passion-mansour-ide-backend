# _server.Dockerfile
FROM openjdk:17-slim as build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN ./gradlew build -x test

FROM openjdk:17-slim
VOLUME /tmp
COPY --from=build /workspace/app/build/libs/*.jar app.jar


ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 7382/tcp