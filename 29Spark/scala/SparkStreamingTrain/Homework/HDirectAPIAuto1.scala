package SparkStreamingTrain.Homework


import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 19:17 
  * @message
  */
object HDirectAPIAuto1 {
  def main(args: Array[String]): Unit = {
    //获取StreamingContext对象
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""), Seconds(2))
    //kafka参数
    val kafkaParams = Map[String, String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )
    //获取kafka数据并创建DStream对象
    val kafkaDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set("atguigu"))
    //数据计算并打印
    kafkaDStream.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()
    //开启任务
    ssc.start()
    ssc.awaitTermination()
  }
}
