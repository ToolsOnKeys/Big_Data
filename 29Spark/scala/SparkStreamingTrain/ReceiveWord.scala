package SparkStreamingTrain

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * @author dinghao 
  * @create 2020-02-10 10:22 
  * @message
  */
object ReceiveWord {
  def main(args: Array[String]): Unit = {
    val sc = new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"), Seconds(3))
    val line = sc.receiverStream(new MyReceiver("hadoop201", 9999))
    line.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()
    sc.start()
    sc.awaitTermination()
  }
}
