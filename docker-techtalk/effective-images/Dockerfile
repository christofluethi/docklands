FROM debian

COPY . /app
RUN apt-get update
RUN apt-get install –y openjdk-8-jdk ssh emacs curl

CMD ["java", "-jar", "/app/target/app.jar"]

