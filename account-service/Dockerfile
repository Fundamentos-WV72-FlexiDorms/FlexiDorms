FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/account-service-0.0.1-SNAPSHOT.jar /app/account-service-0.0.1-SNAPSHOT.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "account-service-0.0.1-SNAPSHOT.jar"]
