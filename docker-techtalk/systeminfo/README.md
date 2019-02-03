# System Info

Simple container to print system and jvm data to test use with docker.

```
JAVA   SYSTEM       192.168.252.2   IP
JAVA   SYSTEM       2176401c931f    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          48.4 MiB        (       50724864 bytes) Max Heap (-Xmx)
JAVA   JVM          33.6 MiB        (       35258368 bytes) Current Heap
JAVA   JVM          15.4 MiB        (       16189976 bytes) Free Heap
JAVA   JVM          4                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          10.0.2          Java Version
JAVA   JVM          10.0.2+13-Debian-2 VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 10.0.2+13-Debian-2
JAVA   JVM          JVM Args: 
ENV    JVM          JAVA_OPTS: -
```

## Before Java 1.8.0_131
Set -Xmx and -Xms to maintain memory bounds

## Java 1.8.0_131 and after
Set `-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap` to be able to run docker containers limited with `--memory 100m` and `--cpuset-cpus 0,2`. Use of `--cpus` will not work with 8 and 9.

## Java 10
Defaults to `-XX:+UseContainerSupport` which leads to `--memory`, `--cpus` and `--cpuset-cpus` working without any other changes.

# RunAll Script

OpenJDK 8-jre-slim u181
```
#################################################
Stage jre8slim --memory=2048m --cpus=6
#################################################
docker run -d --name=jre8slim --rm --memory=2048m --cpus=6 jre8slim
Waiting for Endpoint 192.168.252.2:8080 to become ready.
...
--
Start CMD: [/bin/sh -c java -jar /app/system-info.jar]
JAVA   SYSTEM       192.168.252.2   IP
JAVA   SYSTEM       1d61ec227860    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          7.0 GiB         (     7478968320 bytes) Max Heap (-Xmx)
JAVA   JVM          499.5 MiB       (      523763712 bytes) Current Heap
JAVA   JVM          477.9 MiB       (      501083624 bytes) Free Heap
JAVA   JVM          8                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          1.8.0_181       Java Version
JAVA   JVM          25.181-b13      VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 25.181-b13
JAVA   JVM          JVM Args: 
ENV    JVM          JAVA_OPTS: -
--
```

OpenJDK 8-jre-slim u181 with `-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap` flags
```
#################################################
Stage jre8slimflags --memory=2048m --cpus=6
#################################################
docker run -d --name=jre8slimflags --rm --memory=2048m --cpus=6 jre8slimflags
Waiting for Endpoint 192.168.252.3:8080 to become ready.
...
--
Start CMD: [/bin/sh -c java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -jar /app/system-info.jar]
JAVA   SYSTEM       192.168.252.3   IP
JAVA   SYSTEM       1f8f31d13e9e    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          455.5 MiB       (      477626368 bytes) Max Heap (-Xmx)
JAVA   JVM          202.5 MiB       (      212336640 bytes) Current Heap
JAVA   JVM          181.4 MiB       (      190237240 bytes) Free Heap
JAVA   JVM          8                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          1.8.0_181       Java Version
JAVA   JVM          25.181-b13      VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 25.181-b13
JAVA   JVM          JVM Args: -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap
ENV    JVM          JAVA_OPTS: -
--
```

OpenJDK 9-jre-slim u181
```
#################################################
Stage jre9slim --memory=2048m --cpus=6
#################################################
docker run -d --name=jre9slim --rm --memory=2048m --cpus=6 jre9slim
Waiting for Endpoint 192.168.252.4:8080 to become ready.
...
--
Start CMD: [/bin/sh -c java -jar /app/system-info.jar]
JAVA   SYSTEM       192.168.252.4   IP
JAVA   SYSTEM       78179ccccfbe    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          7.8 GiB         (     8413773824 bytes) Max Heap (-Xmx)
JAVA   JVM          502.0 MiB       (      526385152 bytes) Current Heap
JAVA   JVM          420.5 MiB       (      440943472 bytes) Free Heap
JAVA   JVM          8                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          9.0.4           Java Version
JAVA   JVM          9.0.4+12-Debian-4 VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 9.0.4+12-Debian-4
JAVA   JVM          JVM Args: 
ENV    JVM          JAVA_OPTS: -
--
```

OpenJDK 9-jre-slim u181 with `-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap` flags
```
#################################################
Stage jre9slimflags --memory=2048m --cpus=6
#################################################
docker run -d --name=jre9slimflags --rm --memory=2048m --cpus=6 jre9slimflags
Waiting for Endpoint 192.168.252.5:8080 to become ready.
...
--
Start CMD: [/bin/sh -c java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -jar /app/system-info.jar]
JAVA   SYSTEM       192.168.252.5   IP
JAVA   SYSTEM       dc2c18b68bf9    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          512.0 MiB       (      536870912 bytes) Max Heap (-Xmx)
JAVA   JVM          82.0 MiB        (       85983232 bytes) Current Heap
JAVA   JVM          51.9 MiB        (       54425072 bytes) Free Heap
JAVA   JVM          8                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          9.0.4           Java Version
JAVA   JVM          9.0.4+12-Debian-4 VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 9.0.4+12-Debian-4
JAVA   JVM          JVM Args: -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap
ENV    JVM          JAVA_OPTS: -
--
```

OpenJDK 10-jre-slim 
```
#################################################
Stage jre10slim --memory=2048m --cpus=6
#################################################
docker run -d --name=jre10slim --rm --memory=2048m --cpus=6 jre10slim
Waiting for Endpoint 192.168.252.6:8080 to become ready.
...
--
Start CMD: [/bin/sh -c java -jar /app/system-info.jar]
JAVA   SYSTEM       192.168.252.6   IP
JAVA   SYSTEM       0de020031db3    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          512.0 MiB       (      536870912 bytes) Max Heap (-Xmx)
JAVA   JVM          61.0 MiB        (       63963136 bytes) Current Heap
JAVA   JVM          42.0 MiB        (       44034528 bytes) Free Heap
JAVA   JVM          6                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          10.0.2          Java Version
JAVA   JVM          10.0.2+13-Debian-2 VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 10.0.2+13-Debian-2
JAVA   JVM          JVM Args: 
ENV    JVM          JAVA_OPTS: -
--
```

OpenJDK 11-jre-slim 
``` 
#################################################
Stage jre11slim --memory=2048m --cpus=6
#################################################
docker run -d --name=jre11slim --rm --memory=2048m --cpus=6 jre11slim
Waiting for Endpoint 192.168.252.7:8080 to become ready.
...
--
Start CMD: [/bin/sh -c java -jar /app/system-info.jar]
JAVA   SYSTEM       192.168.252.7   IP
JAVA   SYSTEM       23f1bcb8047c    Hostname
JAVA   SYSTEM       31.3 GiB        (    33654829056 bytes) Physical memory
JAVA   JVM          512.0 MiB       (      536870912 bytes) Max Heap (-Xmx)
JAVA   JVM          67.0 MiB        (       70254592 bytes) Current Heap
JAVA   JVM          30.5 MiB        (       32027008 bytes) Free Heap
JAVA   JVM          6                                       Available processors (cores)
JAVA   SYSTEM       Linux           OS Name
JAVA   SYSTEM       4.4.0-141-generic Kernel Version
JAVA   JVM          11.0.1          Java Version
JAVA   JVM          11.0.1+13-Debian-2bpo91 VM Version
JAVA   JVM          VM Vendor: Oracle Corporation
JAVA   JVM          VM Name: OpenJDK 64-Bit Server VM
JAVA   JVM          VM Version: 11.0.1+13-Debian-2bpo91
JAVA   JVM          JVM Args: 
ENV    JVM          JAVA_OPTS: -
--
```
