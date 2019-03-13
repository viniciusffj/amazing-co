FROM openjdk:9-jdk

ADD . /service
WORKDIR /service

CMD ["./gradlew", "bootRun"]
