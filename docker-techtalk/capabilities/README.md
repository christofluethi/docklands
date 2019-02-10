# Capabilities

Start a new container and prove that the containers root account can change the ownership of files.
```
docker run --rm -it alpine chown nobody /
```

Start another new container and drop all capabilities for the containers root account other than the `CAP_CHOWN` capability. 
```
docker run --rm -it --cap-drop ALL --cap-add CHOWN alpine chown nobody /
```

Start another new container and drop all capability.
```
docker container run --rm -it --cap-drop ALL alpine chown nobody /
```

Start another new container and drop only the `CHOWN` capability form its root account.
```
docker container run --rm -it --cap-drop CHOWN alpine chown nobody /
```

