# CDL POC
CDL POC usando tecnologías de streaming. Se expone una API REST que ejecuta un proceso batch basado en Streaming. Dicho pipeline lee registros de una db de manera asíncrona y los emite a lo largo del flujo, donde se realizan transformaciones sobre los mismos y se los vuelca en un archivo. Al finalizar el pipeline, se envía una notificación a un tópico de `Kafka`.

## Stack

* akka typed
* akka http
* akka streams
* alpakka-slick (alpakka con conector para Slick)

## Principales componentes
* microservicio
* db mysql
* zookeeper y kafka

## Prerequisitos

* JDK 8
* Scala
* Sbt
* docker y docker-compose

## Setup

1. Levantar dockers:
```
docker-compose up
```
2. Generar registros:
```
./generator.sh
```
Por defecto inserta 100 registros, puede configurarse la cantidad de registros en [application.conf](src/main/resources/application.conf)

3. Ejecutar la app:
```
sbt run
```
Levanta la app en http://localhost:8074

4. Ejecutar el pipeline haciendo un request a la API:
```
curl --header "Content-Type: application/json" \
  --request POST \
  --data '{"idSite":3,"paymentMethod":65}' \
  http://localhost:8074/batchclosure
```
## Data Generator

Como dato extra, solo mencionar que [DataGenerator.scala](src/main/scala/com/closure/infrastructure/generator/DataGenerator.scala) tambien es un stream.