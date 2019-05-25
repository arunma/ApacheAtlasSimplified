package com.thoughtworks.atlas.receiver

import java.util
import java.util.{List, Properties}

import akka.actor.ActorSystem
import akka.kafka.{ConsumerSettings, Subscriptions}
import akka.kafka.scaladsl.Consumer
import akka.stream.scaladsl.Sink
import akka.stream.{ActorMaterializer, Materializer}
import org.apache.atlas.`type`.AtlasType
import org.apache.atlas.kafka.{AtlasKafkaConsumer, AtlasKafkaMessage}
import org.apache.atlas.model.notification.HookNotification.EntityCreateRequestV2
import org.apache.atlas.model.notification.{AtlasNotificationBaseMessage, AtlasNotificationStringMessage, EntityNotification, HookNotification}
import org.apache.atlas.notification.NotificationInterface.NotificationType
import org.apache.atlas.utils.AtlasJson
import org.apache.atlas.v1.model.instance.Referenceable
import org.apache.atlas.v1.model.notification.HookNotificationV1.EntityCreateRequest
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer

import scala.collection.JavaConverters._

object AtlasKafkaConsumer {

  val AtlasEntitiesTopic = "ATLAS_ENTITIES"
  val AtlasHooksTopic = "ATLAS_HOOK"

  implicit val system = ActorSystem("atlas-kafka-consumer-system")
  implicit val materializer = ActorMaterializer()

  def consumeAtlasEntities(bootStrapServers: String, topics: Set[String]): Unit = {
    val properties = consumerProperties(bootStrapServers)
    val kafkaConsumer = new KafkaConsumer[String, String](properties)
    kafkaConsumer.subscribe(topics.asJava)
    kafkaConsumer.seekToBeginning(kafkaConsumer.assignment())

    while (true) {
      val consumerRecords = kafkaConsumer.poll(2000).asScala
      consumerRecords.foreach { consumerRecord =>
        println("Value : " + consumerRecord.value())
      }
    }

  }

  def main(args: Array[String]): Unit = {
    consumeAtlasEntities("localhost:9027", Set(AtlasHooksTopic))
  }


  def consumerProperties(bootstrapServers: String) = {
    val props = new Properties()
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers)
    props.put(ConsumerConfig.GROUP_ID_CONFIG, "AtlasDemoGroupId11111")
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, classOf[StringDeserializer])
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
    props
  }
}































































/*
  def consumeAtlasEntities(bootStrapServers: String, topics: Set[String]): Unit = {
    val properties = consumerProperties(bootStrapServers)
    val kafkaConsumer = new KafkaConsumer[String, String](properties)
    kafkaConsumer.seekToBeginning(kafkaConsumer.assignment())
    kafkaConsumer.subscribe(topics.asJava)
    val atlasKafkaConsumer = new AtlasKafkaConsumer[HookNotification](NotificationType.HOOK, kafkaConsumer, false, 2000)
    val messageList = atlasKafkaConsumer.receive()

    messageList.asScala.foreach{kafkaMessage =>
      val hookNotification = kafkaMessage.getMessage
      println (hookNotification.toString)

    }

    /*while (true) {
      val consumerRecords = kafkaConsumer.poll(2000).asScala
      consumerRecords.foreach { consumerRecord =>
        println("Value : " + consumerRecord.value())
        val msg = AtlasJson.fromV1Json(consumerRecord.value(), classOf[HookNotification])
        println(msg.toString())
        println(msg.getType())
      }
    }*/

  }*/

/*
  def consumeAtlasEntities(bootStrapServers: String, topics: Set[String]): Unit = {
    val consumerSettings = ConsumerSettings(system, new StringDeserializer, new StringDeserializer)
      .withBootstrapServers(bootStrapServers)
      .withGroupId("AtlasKafkaClientGroupId")
      .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")


    Consumer
      .committableSource(consumerSettings, Subscriptions.topics(topics))
      .runWith(Sink.foreach { record =>
        println(record.record.value())
        val msg = AtlasType.fromV1Json(record.record.value(), classOf[AtlasNotificationStringMessage])
        println(msg.getMessage())
      })
  }*/
