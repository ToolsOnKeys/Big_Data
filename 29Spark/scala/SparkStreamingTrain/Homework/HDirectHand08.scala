package SparkStreamingTrain.Homework

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 20:29 
  * @message
  */
object HDirectHand08 {
  def main(args: Array[String]): Unit = {

    //创建StreamContext对象
    val ssc = new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"), Seconds(5))

    //kafka参数
    val kafkaParams: Map[String, String] = Map[String,String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )

    //获取上一次启动最后保留的Offset【可以通过存储offset到数据库中】
    val fromOffset = Map[TopicAndPartition, Long](TopicAndPartition("atguigu",0) -> 10)

    //读取Kafka数据并创建DStream对象
    val kafkaDStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder,String](
      ssc,
      kafkaParams,
      fromOffset,
      (m:MessageAndMetadata[String,String])=>m.message())

    //创建一个数据用于存放当前消费数据的Offset信息
    var offsetRanges = Array.empty[OffsetRange]

    //获取当前消费者数据的offset信息
    val wordOnCount = kafkaDStream.transform(rdd => {
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    //打印Offset信息
    wordOnCount.foreachRDD(rdd=>{
      offsetRanges.foreach(a=>println(a.topic+","+a.partition+","+a.fromOffset+","+a.untilOffset))
      rdd.foreach(println)
    })

    //开启任务
    ssc.start()
    ssc.awaitTermination()
  }
}
