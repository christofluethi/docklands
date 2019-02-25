# Change dir

Dockerfile
```
FROM alpine:latest

RUN mkdir -p /some/directory
RUN echo "test" > /some/directory/somefile
RUN cd /some/directory/ 
RUN cat somefile
```

## Whats to output of the docker build process?
<details>
    <summary>Solution</summary>

* Fails due to wrong path for the last line
* Change directory on previous lines is not persisted
</details>



## How can you fix it this?
<details>
    <summary>Solution</summary>

Use the following:

```
FROM alpine:latest

RUN mkdir -p /some/directory
RUN echo "test" > /some/directory/somefile
RUN cd /some/directory/ && cat somefile
```

Or

```
FROM alpine:latest

RUN mkdir -p /some/directory
RUN echo "test" > /some/directory/somefile
WORKDIR /some/directory
RUN cat somefile
```
</details>
