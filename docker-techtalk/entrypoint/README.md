# Entrypoint Demo

Simple counting container

```
Start at Wed Jan 30 18:52:53 CET 2019
PID: 17996
Counter 0
Counter 1
Counter 2
Counter 3
Counter 4
Counter 5
End at Wed Jan 30 18:52:58 CET 2019
```

Allows override start and end of counter with
```
docker run image 0 10
```

Handles `SIGINT` and `SIGTERM` for graceful exit
