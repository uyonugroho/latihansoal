FROM adoptopenjdk/openjdk11:jdk-11.0.6_10-alpine-slim
WORKDIR /app
COPY target/customer-service-1.0.0.jar /app/service.jar
CMD java $JAVA_OPTIONS -jar service.jar
