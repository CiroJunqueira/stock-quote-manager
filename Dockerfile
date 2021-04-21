FROM openjdk:11
MAINTAINER Ciro Junqueira
COPY . /var/www
WORKDIR /var/www
RUN java -jar target/stock-quote-manager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","app.jar"]