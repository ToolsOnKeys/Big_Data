package com.dinghao.homework;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author dinghao
 * @create 2019-12-06 19:04
 * @message Producer异步发送API
 */
public class CustomProducer1 {
    public static void main(String[] args) throws Exception{
        //定义配置参数对象
        Properties properties  = new Properties();
        //链接Kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"hadoop102:9092");
        //配置KV序列化类型
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        // 以上是必要的，以下是选择性参数

        //配置ack应答机制
        properties.put(ProducerConfig.ACKS_CONFIG,"all");
        //配置重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG,2);
        //配置批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG,16384);//16M
        //配置等待时间
        properties.put(ProducerConfig.LINGER_MS_CONFIG,1000);
        //配置缓冲区大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG,33554432);

        //定义
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        //发布消息到消息队列中
        for (int i = 0; i < 10; i++) {
            //含回调函数 Callback()
            kafkaProducer.send(new ProducerRecord("first", "atguigu" + i), (a,b)->{
                if(b==null){
                    System.out.println(a.partition() + "-" + a.offset() + "-" + a.topic());
                }
            });//同步发送加上get()方法
        }
        kafkaProducer.close();
    }
}
