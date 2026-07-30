FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle settings.gradle ./
COPY src ./src

RUN chmod +x gradlew && ./gradlew bootJar -x test --no-daemon

FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /workspace/build/libs/*.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8090
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]
