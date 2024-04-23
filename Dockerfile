FROM openjdk:17-slim as build

WORKDIR /app

COPY . .

RUN mkdir -p /root/.gradle
RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties
RUN chmod +x gradlew



RUN ./gradlew build -x test

RUN ls -la /app/src/main/java/com/mansour/ide/common/security/

FROM openjdk:17-slim
VOLUME /tmp
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080/tcp