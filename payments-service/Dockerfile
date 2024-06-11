FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/payments-service-0.0.1-SNAPSHOT.jar /app/payments-service-0.0.1-SNAPSHOT.jar
EXPOSE 8094
ENTRYPOINT ["java", "-jar", "payments-service-0.0.1-SNAPSHOT.jar"]
