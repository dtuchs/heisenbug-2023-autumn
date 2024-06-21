#!/bin/bash
source ./docker.properties
export PROFILE="${PROFILE:=docker}"

PREFIX="${IMAGE_PREFIX}" PROFILE="${PROFILE}" docker compose down

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
docker build -t ${IMAGE_PREFIX}/rococo-client-${PROFILE}:${FRONT_VERSION} -t ${IMAGE_PREFIX}/rococo-client-${PROFILE}:latest .

cd ../
docker images
PREFIX="${IMAGE_PREFIX}" PROFILE="${PROFILE}" docker compose up -d
docker ps -a
