FROM openjdk:17-slim

WORKDIR /app

COPY . .

RUN mkdir -p /root/.gradle

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

RUN chmod +x gradlew

RUN ./gradlew clean build

CMD ["java", "-jar", "build/libs/artx.jar"]