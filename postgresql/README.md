# PostgreSQL 10 Container 

Base Image: https://hub.docker.com/r/centos/postgresql-10-centos7

## Required Environment variables
`POSTGRESQL_ADMIN_PASSWORD`
Password for the postgres admin account (optional)

`POSTGRESQL_USER1_USERNAME`
Username of the user

`POSTGRESQL_USER1_PASSWORD`
Password for this user

## User creation
Specify users with 

`POSTGRESQL_USER1_USERNAME`: Username of the user
`POSTGRESQL_USER1_PASSWORD`: Password for this user

you can use `POSTGRESQL_USER2_USERNAME` for futher users.

This will create a role with its own schema and set the `search_path` accordingly.
