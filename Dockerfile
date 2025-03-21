## Build Angular
#FROM node:20 AS ng-build
#
#WORKDIR /src
#
#RUN npm i -g @angular/cli
#
##COPY news-client/public public
#COPY client/src src
#COPY client/*.json .
#
#RUN npm ci && ng build
#
## Build Spring Boot
##FROM openjdk:21-jdk AS j-build
#FROM maven:3-eclipse-temurin-23 AS sb-builder
#
#
#WORKDIR /src
#
##COPY news-server/.mvn .mvn
##COPY news-server/src src
##COPY news-server/mvnw .
##COPY news-server/pom.xml .
#COPY server/mvnw .
#COPY server/mvnw.cmd .
#COPY server/pom.xml .
#COPY server/.mvn .mvn
#COPY server/src src
#
## Copy angular files over to static
#COPY --from=ng-build /src/dist/client/ src/main/resources/static
#
#RUN chmod +x mvnw && ./mvnw package -Dmaven.test.skip=true
##chmod a+x mvnw && ./
#
## Copy the JAR file over to the final container
#FROM openjdk:23-jdk-bullseye
#
#WORKDIR /app
#
#COPY --from=sb-builder /src/target/server-0.0.1-SNAPSHOT.jar app.jar
#
#ENV PORT=3000
#
#ENV SPRING_DATA_MONGODB_URI=""
#ENV SERVER_PORT=3000
#
#ENV SPRING_DATASOURCE_URL=""
#ENV SPRING_DATASOURCE_USERNAME=""
#ENV SPRING_DATASOURCE_PASSWORD=""
#
##ENV S3_KEY_ACCESS=placeholder
##ENV S3_KEY_SECRET=placeholder
##ENV S3_BUCKET_ENDPOINT=placeholder
##ENV S3_BUCKET_REGION=placeholder
#
#EXPOSE ${PORT}
#
##SHELL [ "/bin/sh", "-c" ]
#ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar


# Build Angular
FROM node:22 AS ng-build

WORKDIR /client

RUN npm i -g @angular/cli@19.2.1

#COPY ./client/public public
COPY ./client/src src
COPY ./client/*.json .

# Install dependencies and build
RUN npm install
RUN ng build


# Build Spring Boot
FROM maven:3-eclipse-temurin-23 AS sb-builder


WORKDIR /src


COPY ./server/mvnw .
COPY ./server/mvnw.cmd .
COPY ./server/pom.xml .
COPY ./server/.mvn .mvn
COPY ./server/src src

# Copy angular files over to static
COPY --from=ng-build /client/dist/client/browser src/main/resources/static

RUN chmod +x mvnw && ./mvnw package -Dmaven.test.skip=true
#chmod a+x mvnw && ./

# Copy the JAR file over to the final container
FROM openjdk:23-jdk-bullseye

WORKDIR /app

COPY --from=sb-builder /src/target/server-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=3000
EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar app.jar