FROM openjdk:21-jdk-slim
LABEL authors="DELL"
WORKDIR /app
COPY target/chatbot-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]