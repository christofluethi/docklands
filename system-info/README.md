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


