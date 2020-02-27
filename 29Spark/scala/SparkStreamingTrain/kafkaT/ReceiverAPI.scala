package SparkStreamingTrain.kafkaT

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 10:40 
  * @message
  */
object ReceiverAPI {
  def main(args: Array[String]): Unit = {
    val streamingContext = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""), Seconds(3))

    val KafkaDStream = KafkaUtils.createStream(streamingContext,
      "hadoop201:2181,hadoop202:2181,hadoop203:2181",
      "atguigu",
      Map[String, Int]("atguigu" -> 1)
    )

    KafkaDStream.map{case(_,value)=>{(value,1)}}.reduceByKey(_+_).print

    streamingContext.start()
    streamingContext.awaitTermination()
  }
}
