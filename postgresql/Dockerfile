FROM centos/postgresql-10-centos7

USER 0
RUN ln -sf /usr/share/zoneinfo/Europe/Zurich /etc/localtime && echo "Europe/Zurich" > /etc/timezone
COPY scripts/* /usr/share/container-scripts/postgresql/start/
RUN chown 26:26 /usr/share/container-scripts/postgresql/start/* && \
    chmod +x /usr/share/container-scripts/postgresql/start/*.sh
USER 26

ENV TZ "Europe/Zurich"

ENTRYPOINT ["container-entrypoint"]
CMD ["run-postgresql"]

