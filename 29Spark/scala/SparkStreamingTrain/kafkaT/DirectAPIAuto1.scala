package SparkStreamingTrain.kafkaT


import kafka.consumer
import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * @author dinghao 
  * @create 2020-02-10 11:22 
  * @message DirectStream 无法直接消费
  */
object DirectAPIAuto1 {
  def main(args: Array[String]): Unit = {
    //创建StreamingContext
    val streamingContext = new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"), Seconds(2))

    streamingContext.checkpoint("ck")

    //kafka参数
    val kafkaParam = Map[String, String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG->"hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG->"atguigu"
    )
    //读取kafka数据创建DStream
    val kafkaDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](streamingContext, kafkaParam, Set("atguigu"))

    kafkaDStream.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
