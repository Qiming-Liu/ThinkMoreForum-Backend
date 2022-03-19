FROM openjdk:11

WORKDIR /app

COPY . /app

RUN chmod +x ./gradlew
RUN ./gradlew clean assemble

VOLUME /tmp

EXPOSE 443

ENTRYPOINT ["java", "-jar", "/app/build/libs/forum-0.0.1.jar"]