# _server.Dockerfile
FROM openjdk:17-slim as build
WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src




RUN ./gradlew build -x test && ls /workspace/app/build/libs/

FROM openjdk:17-slim
VOLUME /tmp


COPY --from=build /workspace/app/build/libs/app.jar /app.jar




ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 7382/tcp