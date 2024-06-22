ARG ALLURE_DOCKER_API

FROM eclipse-temurin:21-jdk

ENV ALLURE_DOCKER_API=${ALLURE_DOCKER_API}

WORKDIR /heisenbug-2023-autumn
COPY ./gradle ./gradle
COPY ./gradlew ./
COPY ./rococo-e2e ./rococo-e2e
COPY ./build.gradle ./
COPY ./settings.gradle ./
COPY ./gradle.properties ./

CMD ./gradlew test -Dtest.env=docker
