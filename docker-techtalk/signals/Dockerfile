FROM maven:3.6-jdk-12-alpine as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/  
RUN mvn clean package

# OpenJDK 11.0.1-jre-slim
FROM openjdk:11.0.1-jre-slim as jre11slim
COPY --from=build /build/target/signals.jar /app/signals.jar
EXPOSE 8080
CMD java $JAVA_OPTS -jar /app/signals.jar

