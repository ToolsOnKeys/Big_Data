package SparkStreamingTrain.Homework

import kafka.serializer.StringDecoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 20:10 
  * @message
  */
object HDirectAPIAuto2 {


  val getSsc: StreamingContext = {

    //创建StreamingContext对象
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""),Seconds(5))

    //设置checkpoints
    ssc.checkpoint("cc")

    //kafka参数
    val kafkaParams: Map[String, String] = Map[String,String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )

    //获取kafka数据并创建DStream对象
    val kafkaDStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams,Set("atguigu"))

    //对数据逻辑处理并打印
    kafkaDStream.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    //返回StreamingContext对象
    ssc
  }

  def main(args: Array[String]): Unit = {

    //获取ssc
    val ssc = StreamingContext.getActiveOrCreate("cc",()=>getSsc)

    //启动任务
    ssc.start()
    ssc.awaitTermination()
  }
}
