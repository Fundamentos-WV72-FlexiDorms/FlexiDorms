FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/admin-service-0.0.1-SNAPSHOT.jar /app/admin-service-0.0.1-SNAPSHOT.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "admin-service-0.0.1-SNAPSHOT.jar"]
