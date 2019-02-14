# Dockerfile example

* Whats the base image?
* Whats the working directory?
* Whats the content of `added`?
* Whats the content of `copied`?
* Whats the value of `ENVIRONMENT`?
* What about the data of `/anonvol` from `Dockerfile`?

Create volume
```
docker volume create namedvol
```

Run Container with
* For windows use windows command prompt for bind mounts and specify with `c:/<dir>/`...
```
docker run --rm -it --name dftest -v namedvol:/namedvol -v `pwd`/bindvol:/bindvol christofluethi/demo-dockerfile:1.0.0
```

The following Directories exist:
* `/anonvol` docker anonymous volume (Specified in Dockerfile)
* `/bindvol` docker bind mounted volume from docker host
* `/namedvol` docker volume 
* `/localvol` directory in container

Add files to volumes
```
touch /anonvol/anonfile
touch /bindvol/bindfile
touch /namedvol/namedfile
touch /localvol/localfile
```

Which files/volumes survive a rebuild?
* /anonvol
* /bindvol
* /namedvol
* /localvol


Does the following startup change the persistence in any way?
```
docker run --rm -it --name dftest -v namedvol:/namedvol -v `pwd`/bindvol:/bindvol -v anonvol:/anonvol christofluethi/demo-dockerfile:1.0.0
```
