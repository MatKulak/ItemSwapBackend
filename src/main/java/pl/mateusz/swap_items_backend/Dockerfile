FROM openjdk:17-jdk-slim
WORKDIR /app
COPY app.jar app.jar
RUN mkdir /app/storage
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
