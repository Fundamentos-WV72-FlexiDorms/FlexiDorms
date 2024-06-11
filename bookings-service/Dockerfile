FROM openjdk:21-jdk-slim
WORKDIR /app
COPY /target/bookings-service-0.0.1-SNAPSHOT.jar /app/bookings-service-0.0.1-SNAPSHOT.jar
EXPOSE 8092
ENTRYPOINT ["java", "-jar", "bookings-service-0.0.1-SNAPSHOT.jar"]
