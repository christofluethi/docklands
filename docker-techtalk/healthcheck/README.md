# Healthcheck

Simple Container using a `HEALTHCHECK`.

* Container HEALTHCHECK tests for presence of /tmp/unhealthy
* Container is considered unhealthy if file exists
* Docker shows health of container using `docker ps`
* Automatic restarts for unhealthy containers is currently not supported
* Change of container health generates a `health_status` event shown with `docker system events`

Docker container outputs health status every 1 second.
```
docker run -it --rm --name health christofluethi/health:1.0.0
.......... | 10
.......... | 20
.......... | 30
.......... | 40
.......... | 50
.......... | 60
.......... | 70
.......... | 80
.......... | 90
.........! | 100
!!!!!!!!!! | 110
!!!!!!!!!! | 120
!!!!!!!!!! | 130
!!!!!!!!.. | 140
.......... | 150
.......... | 160
.......... | 170
.....
```

Force container unhealthy
```
docker exec health touch /tmp/unhealthy
```

Clear health status
```
docker exec health rm /tmp/unhealthy
```
