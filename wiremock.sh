#!/bin/bash

docker run --name rococo-mock -p 8080:8080 -v ./wiremock/rest:/home/wiremock -d wiremock/wiremock:2.35.0 --global-response-templating --enable-stub-cors
