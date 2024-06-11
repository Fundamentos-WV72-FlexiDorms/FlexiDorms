FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/config-service-0.0.1-SNAPSHOT.jar /app/config-service-0.0.1-SNAPSHOT.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "config-service-0.0.1-SNAPSHOT.jar"]
