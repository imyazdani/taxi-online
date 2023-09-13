FROM openjdk:11-jre-slim
RUN mkdir -p /app
COPY target/*.jar /app/taxi-online.jar
EXPOSE 8080
WORKDIR /app/
CMD ["java","-jar","taxi-online.jar"]