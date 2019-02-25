# Seccomp

Deny all syscalls which leads to failure on container startup - due to the lack of syscalls.
```
docker container run --rm -it --cap-add ALL --security-opt apparmor=unconfined --security-opt seccomp=deny.json alpine sh
```

Trace syscalls
```
strace chmod 644 README.md 2>&1 | grep chmod
execve("/bin/chmod", ["chmod", "644", "README.md"], [/* 64 vars */]) = 0
fchmodat(AT_FDCWD, "README.md", 0644)   = 0
```

Check default seccomp profile
```
grep chmod default.json
    "chmod",
    "fchmod",
    "fchmodat",
```

Start a new container with the default-no-chmod.json profile and attempt to run the `chmod 777 /` command.
```
docker container run --rm -it --cap-add ALL --security-opt apparmor=unconfined --security-opt seccomp=default-no-chmod.json alpine chmod 777 /
chmod: /: Operation not permitted
```
