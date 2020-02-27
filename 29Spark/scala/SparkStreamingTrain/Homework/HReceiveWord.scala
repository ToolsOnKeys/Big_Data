package SparkStreamingTrain.Homework

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 18:37 
  * @message
  */
object HReceiveWord {
  def main(args: Array[String]): Unit = {
    //创建StreamingContext对象
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""),Seconds(3))

    //使用自定义的Receiver读取数据创建DStream
    val rDStream = ssc.receiverStream(new HMyReceiver("hadoop201",9999))

    //计算wordCount
    rDStream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    //开启任务
    ssc.start()
    ssc.awaitTermination()
  }
}
