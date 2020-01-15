# System Info

A simple debug container to print system, jvm and client information. Supports various debug features as 
* Force system exit with errorcode 85 (/die)
* Ram consumptions (/memory)
* Readiness check (/ready)
* Liveness check (/live)
* Control readiness and liveness (/set/unhealthy, /set/failing, /clear/unhealthy, /clear/failing)



## Sample output 

```
REQ    CLIENT       User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/72.0.3626.81 Safari/537.36
REQ    CLIENT       192.168.1.100   Client-IP
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
APP    APP          Is ready (/ready): true
APP    APP          Is alive (/live): true
```
