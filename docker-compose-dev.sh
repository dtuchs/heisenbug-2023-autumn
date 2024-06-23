#!/bin/bash

echo "### Build backend images ###"
bash ./gradlew jibDockerBuild -x :rococo-e2e:test
cd rococo-client || exit 1
echo "### Build frontend image ###"
docker build --build-arg PROFILE=docker -t dtuchs/rococo-client-docker:0.0.1-SNAPSHOT -t dtuchs/rococo-client-docker:latest .

cd ../
docker images
PROFILE=docker docker compose up -d
docker ps -a
