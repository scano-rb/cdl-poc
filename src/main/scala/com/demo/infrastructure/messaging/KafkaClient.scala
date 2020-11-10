package com.demo.infrastructure.messaging

import java.util.Properties
import java.util.concurrent.Future

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord, RecordMetadata}

case class KafkaClient(kafkaProducer: KafkaProducer[String, String], kafkaTopic: String) {

  def publish(message: String): Future[RecordMetadata] = {
    val record = new ProducerRecord[String, String](kafkaTopic, message)
    kafkaProducer.send(record)
  }
}

object KafkaClient {

  def apply(bootstrapServer: String, kafkaTopic: String): KafkaClient = {
    val config        = kafkaConfig(bootstrapServer)
    val kafkaProducer = new KafkaProducer[String, String](config)

    KafkaClient(kafkaProducer, kafkaTopic)
  }

  private def kafkaConfig(bootstrapServers: String): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", bootstrapServers)
    props.put("acks", "all")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props
  }
}
