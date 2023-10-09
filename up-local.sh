#!/bin/bash

docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker run --name rococo-db -p 3306:3306 -e MYSQL_ROOT_PASSWORD=secret -d mysql:8.1.0

cd ./rococo-client
npm i
npm run dev
