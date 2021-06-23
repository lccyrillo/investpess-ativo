FROM adoptopenjdk/openjdk8:latest
LABEL maintainer = 'lccyrillo - investpess ativo '
LABEL data_release = '01/06/2021'

ENV DB_HOST=localhost
ENV DB_PORT=5433


RUN mkdir -p /usr/server

COPY artifacts/investpess_ativo_jar/investpess-ativo.jar /usr/server/investpess-ativo.jar


WORKDIR /usr/server
CMD ["java","-jar", "/usr/server/investpess-ativo.jar"]
EXPOSE 50051
