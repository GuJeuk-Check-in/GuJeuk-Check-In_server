FROM eclipse-temurin:17-jre

WORKDIR /app

RUN addgroup -S spring && adduser -S spring -G spring

COPY build/libs/*.jar app.jar

RUN chown spring:spring app.jar

USER spring

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
