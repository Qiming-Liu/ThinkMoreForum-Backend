FROM openjdk:11

WORKDIR /app

COPY . /app

ENV SSL_ENABLE "true"

ENV ENABLE_SWAGGER "true"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]