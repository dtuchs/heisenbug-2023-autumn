FROM node:20.5.0-alpine AS build
ARG PROFILE
WORKDIR /app

COPY package.json ./
COPY package-lock.json ./
RUN npm install
COPY . ./
RUN npm run build:${PROFILE}

FROM nginx:1.23.3-alpine as release
COPY nginx.conf /etc/nginx/conf.d/default.conf
COPY --from=build /app/build /usr/share/nginx/html/
EXPOSE 81
CMD ["nginx", "-g", "daemon off;"]
