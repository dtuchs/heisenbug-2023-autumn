#!/bin/bash
cd ./rococo-client/ || exit
source ./docker.properties

echo '### Remove old imags ###'
docker rmi $(docker images --format '{{.Repository}}:{{.Tag}}' | grep ${name})

echo '### Build dev frontend image ###'
docker build -t ${registry}/${name}:${version} -t ${registry}/${name}:latest .

if [ "$1" = "push" ]; then
  echo '### Push dev frontend image ###'
  docker push ${registry}/${name}:${version}
  docker push ${registry}/${name}:latest
fi

cd ../
docker images
echo '### Done! ###'
