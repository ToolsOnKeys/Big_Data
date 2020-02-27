package SparkStreamingTrain

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-08 15:52 
  * @message
  */
object WordCount {
  def main(args: Array[String]): Unit = {

    //初始化Spark配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("wordcount")
    //初始化Spark Streaming
    val sc = new StreamingContext(sparkConf, Seconds(3))

    val lineStreams = sc.socketTextStream("hadoop201", 9999)

    lineStreams.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print

    //开启流式任务
    sc.start()
    sc.awaitTermination()
  }
}
