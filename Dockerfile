FROM openjdk:8

RUN mkdir /fetcher

WORKDIR /fetcher

ADD ./fetcher-api/target/fetcher-api-1.0.0.jar /fetcher

EXPOSE 8083

CMD ["java", "-jar", "fetcher-api-1.0.0.jar"]