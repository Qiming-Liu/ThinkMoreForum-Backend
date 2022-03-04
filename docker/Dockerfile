FROM openjdk:11

WORKDIR /app

COPY . /app

ENV SSL_ENABLE "true"

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]