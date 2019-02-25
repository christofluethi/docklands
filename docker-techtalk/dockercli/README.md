# Docker CLI

## Short

Run container attached
```
docker run --rm --name testweb httpd
```

Run container detached
```
docker run --rm -d --name testweb httpd
```

Logs
```
docker logs testweb
docker logs testweb --tail 100 -f
```

Logs
```
docker inspect testweb
docker inspect testweb --format '{{ .NetworkSettings.IPAddress }}'
```

Enter detached container
```
docker exec -it testweb bash
```

Stop/Remove container
```
docker stop testweb
docker rm testweb
```

Show/create/remove networks
```
docker network ls
docker network create --subnet 192.168.x.0/24 testnet
docker network rm testnet
```

Show volumes
```
docker volume ls
docker volume create testvol
docker volume rm testvol
```

Clean System
```
docker system df
docker container prune
docker image prune
docker volume prune
docker system prune
```

## Detailed

### Image

Search remote image (no support for tags no search in private repos)
```
docker search christofluethi
```

Download image (needs tag). default registry is docker.io (docker hub).
```
docker pull christofluethi/systeminfo:1.0.0
docker pull docker.io/christofluethi/systeminfo:1.0.0
docker pull httpd
```

Show local images
```
docker images 
docker image ls
```

Inspect Image
```
docker image inspect httpd
```

History of image (How image was built)
```
docker history httpd
```

Remove Images
```
docker rmi docker.io/christofluethi/systeminfo:1.0.0
docker rmi 33b13ed6b80a
docker image rm httpd
```

Remove unused images (all with --all)
```
docker image prune
```


### Container
Show containers
```
docker ps
docker ps -a
docker container ps
```

Run Container
* `-d` detached (background)
* `--name` name the container
* `--rm` remove container after stopping
* `-p` portmapping host:container
```
docker run httpd
docker container run httpd
docker run -d --name testweb -p 8080:80 httpd
```

Jump into container
* `-i` interactive (stdin)
* `-t` bind to tty (stdout)
* You have to specify `CMD` (bash) to execute
```
docker exec -it testweb bash
```

Show changes in actual container
```
docker diff testweb
```

Inspect container
```
docker inspect testweb
docker container inspect testweb --format '{{ .NetworkSettings.IPAddress }}'
```

Pause and unpause container (does not receive cpu schedules)
```
docker pause testweb
docker container unpause testweb
```

Restart, Stop/Kill, start container
```
docker restart testweb
docker stop testweb
docker kill testweb
docker start testweb
```

Remove container 
```
docker rm testweb
```

Remove all stopped containers
```
docker container prune
```

Stop all running containers
```
docker stop $(docker ps -q)
```



### Build Container
Build Image from Dockerfile
```
docker build .
docker build -t myhttpd:latest 
```

Tag Image
```
docker tag myhttpd:latest christofluethi/myhttpd:latest
docker tag 49c8331df8ef repository.address.example.com/myhttpd:latest
```

Push image to repository
```
docker push christofluethi/myhttpd:latest
```

Needs repository login
```
docker login docker.io
```


### System
Get system informations
```
docker info
docker system info
```

Get Events
```
docker system events --since 24h
```

Show docker disk space usage
```
docker system df
```

Clean system
```
docker system prune
```
