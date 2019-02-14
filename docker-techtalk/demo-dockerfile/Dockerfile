FROM alpine

LABEL maintainer="user@example.com"
LABEL description="simple dockerfile demo"

WORKDIR /opt/demo/container

RUN mkdir added && \
    mkdir copied && \
    mkdir /anonvol && \ 
    mkdir /namedvol && \ 
    mkdir /bindvol && \
    mkdir /localvol

RUN echo "hello world" > /anonvol/from_dockerfile
VOLUME ["/anonvol"]

ADD http://www.shaped.ch/index.html /anonvol/index.html
ADD somefiles.tar.gz /opt/demo/container/added/
COPY somefiles.tar.gz /opt/demo/container/copied/

EXPOSE 8080
EXPOSE 8081/tcp
EXPOSE 8082/udp

ENV ENVIRONMENT=development

CMD ["/bin/sh"]
