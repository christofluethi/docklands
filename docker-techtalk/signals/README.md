# Signals

Demonstrate docker signal handling

## Issue
* Shell form of Dockerfile CMD wraps java process with a bash process
* Bash process does not forward SIGTERM signal to java process
* Convertion to json array form `["x","y"]` does not allow `$JAVA_OPTS`

## Goal
* Container stops correctly
* Java process reads JAVA_OPTS
* JAVA_OPTS can be overridden with environment variable

## Solution
<details>
    <summary>Show</summary>

* Use custom entrypoint
* Execute java with `exec` in bash entrypoint (replaces PID 1 of bash with the java process)
* Specify $JAVA_OPTS in entrypoint exec line
* See `solution/Dockerfile`
</details>
