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
