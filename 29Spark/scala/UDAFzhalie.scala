import org.apache.spark.sql.SparkSession

import scala.collection.mutable.ArrayBuffer

/**
  * @author dinghao 
  * @create 2020-02-07 10:57 
  * @message
  */
object UDAFzhalie {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").getOrCreate()

    import sparkSession.implicits._


    val df = sparkSession.sparkContext.textFile("spark1/dh3").map(a => {
      val strings = a.split(",")
      (strings(0), strings(1))
    }).flatMap(a => {
      val arrayBuffer = ArrayBuffer[(String, Int)]()
      a._2.split("-").foreach(b => arrayBuffer.append((a._1, b.toInt)))
      arrayBuffer
    }
    ).toDF("name", "age")
    //      .flatMapValues(_.split("-")).toDF("name", "age")

    df.createTempView("dd")

    sparkSession.sql("select name,avg(age) from dd group by name").show()

    sparkSession.close()
  }
}
