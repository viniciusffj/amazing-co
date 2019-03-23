FROM openjdk:8-jdk-alpine

RUN mkdir -p /root/.gradle
ENV HOME /root
VOLUME /root/.gradle

ADD . /service
WORKDIR /service

CMD ["./gradlew", "bootRun"]
