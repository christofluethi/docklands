FROM debian:8.11-slim

COPY counter.sh /counter.sh
RUN chmod +x /counter.sh

ENTRYPOINT ["/counter.sh"]
CMD ["0", "5"]
