FROM eclipse-temurin:17-jre

WORKDIR /app

COPY apps/beopdong-api/build/libs/beopdong-api.jar app.jar

ENV JAVA_OPTS=""

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar app.jar"]