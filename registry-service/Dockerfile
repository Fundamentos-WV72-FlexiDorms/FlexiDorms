FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/registry-service-0.0.1-SNAPSHOT.jar /app/registry-service-0.0.1-SNAPSHOT.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "registry-service-0.0.1-SNAPSHOT.jar"]
