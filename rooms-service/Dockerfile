FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/rooms-service-0.0.1-SNAPSHOT.jar /app/rooms-service-0.0.1-SNAPSHOT.jar
EXPOSE 8091
ENTRYPOINT ["java", "-jar", "rooms-service-0.0.1-SNAPSHOT.jar"]
