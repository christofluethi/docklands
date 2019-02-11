# Docker Networking

## Default bridge docker0
Run two containers
```
docker run -it --rm --name debianone debian bash
docker run -it --rm --name debiantwo debian bash
```

Whats the output of
```
root@debianone $ ping debiantwo
```

## Custom Bridge Network

Create custom network
```
docker network create --driver bridge --subnet 192.168.221.0/24 debiannet
```

Run two containers
```
docker run -it --rm --name debianone --network debiannet debian bash
docker run -it --rm --name debiantwo --network debiannet debian bash
```

Whats the output of
```
root@debianone $ ping debiantwo
```

