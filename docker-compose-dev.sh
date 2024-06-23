#!/bin/bash
source ./docker.properties
export PROFILE="${PROFILE:=docker}"

PROFILE="${PROFILE}" docker compose down

docker_containers="$(docker ps -a -q)"
docker_images="$(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rococo')"

if [ ! -z "$docker_containers" ]; then
  echo "### Stop containers: $docker_containers ###"
  docker stop $(docker ps -a -q)
  docker rm $(docker ps -a -q)
fi
if [ ! -z "$docker_images" ]; then
  echo "### Remove images: $docker_images ###"
  docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep 'rococo')
fi

echo "### Build backend images ###"
bash ./gradlew jibDockerBuild -x :rococo-e2e:test
cd rococo-client || exit 1
echo "### Build frontend image ###"
docker build --build-arg PROFILE=${PROFILE} -t dtuchs/rococo-client-${PROFILE}:${FRONT_VERSION} -t dtuchs/rococo-client-${PROFILE}:latest .

cd ../
docker images
PROFILE="${PROFILE}" docker compose up -d
docker ps -a
