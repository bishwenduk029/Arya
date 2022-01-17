FROM openjdk:11-slim

RUN apt-get update
RUN apt-get install libatomic1

WORKDIR /home/app

COPY build/layers/libs /home/app/libs
COPY build/layers/resources /home/app/resources
COPY build/layers/application.jar /home/app/application.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]