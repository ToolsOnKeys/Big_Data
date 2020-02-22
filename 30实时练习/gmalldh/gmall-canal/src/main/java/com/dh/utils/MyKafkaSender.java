package com.dh.utils;

import com.dh.constants.GmallConstants;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author dinghao
 * @create 2020-02-21 14:22
 * @message
 */
public class MyKafkaSender {
    public static KafkaProducer<String,String> producer = null;
    public static KafkaProducer<String,String> createKafkaProducer(){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop201:9092,hadoop202:9092,hadoop203:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG,GmallConstants.KAFKA_TOPIC_ORDER_INFO);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);
        return kafkaProducer;
    }
    public static void send(String topic,String msg){
        if(producer==null){
            producer=createKafkaProducer();
        }
        producer.send(new ProducerRecord<String,String>(topic,msg));
    }
}
