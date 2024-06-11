FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service-0.0.1-SNAPSHOT.jar
EXPOSE 8093
ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]
