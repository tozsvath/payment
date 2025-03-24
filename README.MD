### Payments


## Requirements
Java 21
Docker version 2 4.0.5, build ced0996

## build
If you have shell you can use the buildscript.sh to build the project. "sh buildscript.sh"

## run
From docker directory run the following command to run the project (verify docker compose path)
Then run the apps separately - they don't need any parameters, everything is in the application.properties file

## Docker
/usr/local/bin/docker compose -f ./docker-compose.yml -p docker up -d


### - IMPORTANT - Dockerization 
Notification service and transaction-service haven't been completely dockerized yet, but you can run it locally if needed.
They couldn't refer to docker's internal network, so they are running on localhost:8080 and localhost:8081 respectively.
