FROM openjdk:17-ea-slim
EXPOSE 8080
ADD ./target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]