FROM alpine

COPY status.sh /status.sh
RUN chmod +x status.sh

HEALTHCHECK --interval=5s --timeout=1s --start-period=1s --retries=2 \
  CMD test ! -e /tmp/unhealthy

CMD ["/bin/sh", "status.sh"]
