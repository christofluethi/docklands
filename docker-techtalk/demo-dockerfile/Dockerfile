ARG VERSION=latest
FROM alpine:${VERSION}
ARG VERSION

LABEL version=$VERSION
LABEL maintainer="user@example.com"
LABEL description="simple dockerfile demo"

WORKDIR /opt
WORKDIR demo
WORKDIR container

RUN mkdir added && \
    mkdir copied && \
    mkdir /anonvol && \ 
    mkdir /namedvol && \ 
    mkdir /bindvol && \
    mkdir /localvol

RUN echo $VERSION > image_version
RUN echo $VERSION > /anonvol/volume_image_version
VOLUME ["/anonvol"]

ADD http://www.shaped.ch/index.html /anonvol/index.html
ADD somefiles.tar.gz /opt/demo/container/added/
COPY somefiles.tar.gz /opt/demo/container/copied/

EXPOSE 8080
EXPOSE 8081/tcp
EXPOSE 8082/udp

ENV ENVIRONMENT=development

CMD ["/bin/sh"]
