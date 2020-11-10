# Batch Processing API
A simple API that exposes an endpoint which executes a streaming pipeline. It reads transaction registries from a db in an async way and emit them downstream where transformations are applied to each of them before being dumped to a file. A notifacation to a kafka topic is sent when stream finishes.
## Stack

* akka typed
* akka http
* akka streams
* alpakka-slick (alpakka for Slick)

## Main components
* REST API
* db postgres
* zookeeper y kafka

## Prerequisites

* JDK 8
* Scala
* Sbt
* docker and docker-compose

## Setup

1. Run docker containers:
```
docker-compose up
```
2. Generate registries:
```
./generator.sh
```
By default, it generates 2000 registries. This amount can be configured in [application.conf](src/main/resources/application.conf)

3. Run the app:
```
sbt run
```
It runs the app on http://localhost:8074

4. Request the API endpoint that triggers the batch processing pipeline:
```
curl --header "Content-Type: application/json" \
  --request POST \
  http://localhost:8074/batchclosure
```
## Data Generator

Just to mention, the [DataGenerator.scala](src/main/scala/com/demo/infrastructure/generator/DataGenerator.scala) is also a stream.