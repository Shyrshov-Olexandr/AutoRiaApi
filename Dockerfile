FROM openjdk:17-alpine

MAINTAINER Shyrshov Olexandr

RUN mkdir /autoriaApp
WORKDIR /autoriaApp
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh