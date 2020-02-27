package SparkStreamingTrain

import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

/**
  * @author dinghao 
  * @create 2020-02-10 9:05 
  * @message
  */
object Day02 {
  def main(args: Array[String]): Unit = {
    //初始化Spark配置信息
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("day02test")
    //初始化SparkStreamingContext
    val streamingContext = new StreamingContext(sparkConf, Seconds(3))
    //创建RDD队列
    val queue = new mutable.Queue[RDD[Int]]()
    //创建QueueInputDStream
    val inputStream = streamingContext.queueStream(queue,false)
    //处理队列中的RDD数据
    inputStream.map((_,1)).reduceByKey(_+_).print()
    //执行任务
    streamingContext.start()
    //循环创建并向RDD队列中放入RDD
    for(i<- 1 to 5){
      queue+=streamingContext.sparkContext.makeRDD(1 to 10,2)
      Thread.sleep(2000)
    }


    streamingContext.awaitTermination()
  }
}
