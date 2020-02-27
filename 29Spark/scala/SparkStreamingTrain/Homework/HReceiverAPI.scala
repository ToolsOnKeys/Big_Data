package SparkStreamingTrain.Homework

import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 19:21 
  * @message
  */
object HReceiverAPI {
  def main(args: Array[String]): Unit = {
    val ssc =
      new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"),Seconds(3))

    val lineDStream =
      KafkaUtils.createStream(
        ssc,
        "hadoop201:2181,hadoop202:2081,hadoop203:2181",
        "atguigu",
        Map[String,Int]("atguigu"->1))

    lineDStream.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
