FROM maven:3.6-jdk-12-alpine as build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /build/src/  
RUN mvn clean package


# OpenJDK 7u181-jre-slim  (OpenJDK <1.8.0_131)
FROM openjdk:7u181-jre-slim as jre7slim
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -jar /app/system-info.jar


# OpenJDK 8u181-jre-slim 
FROM openjdk:8u181-jre-slim as jre8slim
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -jar /app/system-info.jar


# OpenJDK 8u181-jre-slim with flags
FROM openjdk:8u181-jre-slim as jre8slimflags
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -jar /app/system-info.jar


# OpenJDK 9-jre-slim 
FROM openjdk:9-jre-slim as jre9slim
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -jar /app/system-info.jar


# OpenJDK 9-jre-slim with flags
FROM openjdk:9-jre-slim as jre9slimflags
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -jar /app/system-info.jar


# OpenJDK 10-jre-slim 
FROM openjdk:10-jre-slim as jre10slim
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -jar /app/system-info.jar


# OpenJDK 11.0.1-jre-slim
FROM openjdk:11.0.1-jre-slim as jre11slim
RUN apt-get -qq update && apt-get -y install curl netcat socat tcpdump iputils-ping iperf strace dstat htop lsof iftop iptraf httpry dnsutils lftp --no-install-recommends && rm -rf /var/lib/apt/lists/*
COPY --from=build /build/target/system-info.jar /app/system-info.jar
EXPOSE 8080
CMD java -jar /app/system-info.jar

