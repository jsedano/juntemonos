#!/bin/bash
source ./set-environment.sh
# Build the juntemonos-api jar using Maven
mvn clean verify -f ../juntemonos-api/pom.xml
# Check if the container exists
if [[ "$(docker ps -aq -f name=juntemonos-api)" ]]; then
    echo "Container 'juntemonos-api' already exists. Starting the container..."
    docker start juntemonos-api
else
    echo "Container 'juntemonos-api' does not exist. Building the image and creating the container..."
    docker build -t juntemonos-api ../juntemonos-api
    docker run --name juntemonos-api -p 8080:8080 -e POSTGRES_USER=$POSTGRES_USER -e POSTGRES_DB=$POSTGRES_DB -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD -e POSTGRES_HOST=$POSTGRES_HOST -e POSTGRES_PORT=$POSTGRES_PORT -d juntemonos-api 
fi