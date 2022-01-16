FROM adoptopenjdk/openjdk11:latest
LABEL maintainer = 'lccyrillo - investpess ativo '
LABEL data_release = '01/06/2021'

ENV DB_HOST=localhost
ENV DB_PORT=5433


RUN mkdir -p /usr/server
RUN mkdir /usr/server/config

COPY artifacts/investpess_ativo_jar/investpess-ativo.jar /usr/server/investpess-ativo.jar
COPY /config/config.properties /usr/server/config/config.properties


WORKDIR /usr/server
CMD ["java","-jar", "/usr/server/investpess-ativo.jar"]
EXPOSE 50051
