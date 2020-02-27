package SparkStreamingTrain.kafkaT

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 11:37 
  * @message
  */
object DirectAPIAuto2 {


  def getSCC:StreamingContext = {
    var streamingContext = new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"), Seconds(2))

    streamingContext.checkpoint("ckk")

    val Kafkaparam: Map[String, String] = Map[String, String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG->"hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG->"atguigu"
    )

    val kafkaDStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](streamingContext,Kafkaparam,Set("atguigu"))

    kafkaDStream.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print

    streamingContext
  }



  def main(args: Array[String]): Unit = {
    //获取StreamingContext
    val streamingContext = StreamingContext.getActiveOrCreate("ckk",()=>getSCC)
    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
