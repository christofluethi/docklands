# Docker Networking

## Default bridge docker0 (no ports)
Run two containers
```
docker run -d --rm --name testweb1 centos/httpd-24-centos7
docker run -d --rm --name testweb2 centos/httpd-24-centos7
```

## Default bridge docker0 (with ports)
Run two containers
```
docker run -d --rm --name testweb1 -p 8080:8080 centos/httpd-24-centos7
docker run -d --rm --name testweb2 -p 8081:8080 centos/httpd-24-centos7
```

Whats the output of?
```
docker exec -it testweb1 ping testweb1
docker exec -it testweb1 ping testweb2
```

## Custom Bridge Network

Create custom network
```
docker network create --driver bridge --subnet 192.168.221.0/24 testnet
```

Run two containers
```
docker run -d --rm --name testweb1 -p 8080:8080 --network testnet centos/httpd-24-centos7
docker run -d --rm --name testweb2 -p 8081:8080 --network testnet centos/httpd-24-centos7
```

Whats the output of
```
docker exec -it testweb1 ping testweb2
```

Delete network
```
docker network rm testnet
```
