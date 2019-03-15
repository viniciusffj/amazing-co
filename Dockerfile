FROM openjdk:8-jdk-alpine

ADD . /service
WORKDIR /service

CMD ["./gradlew", "bootRun"]
