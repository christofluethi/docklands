FROM maven:3.6-jdk-12-alpine as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/  
RUN mvn clean package


FROM openjdk:8-jre-slim
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java $JAVA_OPTS -jar /app/system-info.jar

