# Dockerfile example

* Whats the base image?
* Whats the working directory?
* Whats the current user?
* Whats the content of `added`?
* Whats the content of `copied`?
* Whats the value of `ENVIRONMENT`?
* What about the data of `/anonvol` from `Dockerfile`?

Run Container with
```
docker run --rm -it --name dftest -v namedvol:/namedvol -v `pwd`/bindvol:/bindvol christofluethi/demo-dockerfile:1.0.0
```

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
