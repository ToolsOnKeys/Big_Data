package SparkStreamingTrain.kafkaT

import kafka.common.TopicAndPartition
import kafka.message.MessageAndMetadata
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.{HasOffsetRanges, KafkaUtils, OffsetRange}


/**
  * @author dinghao 
  * @create 2020-02-10 14:00 
  * @message
  */
object DirectAPIHand {
  def main(args: Array[String]): Unit = {
    val streamingContext = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""), Seconds(3))

    //kafka参数
    val Kafkaparam: Map[String, String] = Map[String, String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )

    //获取上次启动保留的offset
    val fromOffsets = Map[TopicAndPartition, Long](TopicAndPartition("atguigu", 0) -> 10)


    //读取kafka数据创建DStream
    val kafkaDStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder,String](
      streamingContext,
      Kafkaparam,
      fromOffsets,
      (m: MessageAndMetadata[String, String]) => m.message())

    //创建一个数组，用于存放当前消费数据的offset信息
    var offsetRanges = Array.empty[OffsetRange]

    //获取当前消费数据的offset信息
    val wordToCount = kafkaDStream.transform { rdd =>
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    //打印offset信息
    wordToCount.foreachRDD(rdd => {
      for (o <- offsetRanges) {
        println(o.topic + "," + o.partition + "," + o.fromOffset + "," + o.untilOffset)

      }
      rdd.foreach(println)
    }
    )

    streamingContext.start()
    streamingContext.awaitTermination()

  }
}
