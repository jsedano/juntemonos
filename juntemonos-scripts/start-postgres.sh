#!/bin/bash
source ./set-environment.sh

# Check if the container exists
if [[ "$(docker ps -aq -f name=juntemonos-postgres)" ]]; then
    echo "Container 'juntemonos-postgres' already exists. Starting the container..."
    docker start juntemonos-postgres
else
    echo "Container 'juntemonos-postgres' does not exist. Pulling the image and creating the container..."
    docker pull postgres
    docker run --name juntemonos-postgres -e POSTGRES_PASSWORD=$POSTGRES_PASSWORD -e POSTGRES_USER=$POSTGRES_USER -e POSTGRES_DB=$POSTGRES_DB -d postgres
fi