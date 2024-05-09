#!/bin/sh

export POSTGRES_USER=juntemonos
export POSTGRES_PASSWORD=juntemonos
export POSTGRES_DB=juntemonos
export POSTGRES_HOST="$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' juntemonos-postgres)"
export POSTGRES_PORT=5432
export JUNTEMONOS_AI_SERVICE_URL="$(docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' juntemonos-ai-service)"
export OPENAI_API_KEY=<YOUR_OPENAI_API_KEY>