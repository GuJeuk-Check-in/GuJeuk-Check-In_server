FROM gradle:8.14.3-jdk17 AS build
WORKDIR /app

COPY gradlew ./
COPY gradle/wrapper ./gradle/wrapper
COPY build.gradle settings.gradle ./

RUN chmod +x gradlew
RUN ./gradlew --no-daemon dependencies

COPY src ./src
RUN ./gradlew --no-daemon clean bootJar -x test

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
