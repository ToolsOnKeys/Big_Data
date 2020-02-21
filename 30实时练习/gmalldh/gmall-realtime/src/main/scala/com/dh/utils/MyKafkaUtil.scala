package com.dh.utils

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies, LocationStrategy}

/**
  * @author dinghao 
  * @create 2020-02-18 15:20 
  * @message
  */
object MyKafkaUtil {

  def getKafkaStream(ssc: StreamingContext, topics: Set[String]) = {
    val properties = PropertiesUtil.load("config.properties")
    val kafkaParams = Map(
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> properties.getProperty("kafka.broker.list"),
      ConsumerConfig.GROUP_ID_CONFIG -> "dh",
      ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer],
      ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG -> classOf[StringDeserializer]
    )
    val locationStrategy = LocationStrategies.PreferConsistent
    val consumerStrategy = ConsumerStrategies.Subscribe[String, String](topics, kafkaParams)

    val kafkaDStream = KafkaUtils.createDirectStream[String, String](ssc, locationStrategy, consumerStrategy)
    kafkaDStream
  }
}
