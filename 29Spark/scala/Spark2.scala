import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @author dinghao 
  * @create 2020-02-07 14:08 
  * @message
  */
object Spark2 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").getOrCreate()
    import sparkSession.implicits._
    //    sparkSession.read.orc("")
    //等价于
    //    sparkSession.read.format("orc").load("")

    //    sparkSession.read.format("json").load("spark1/dh2").show()
    //等价于
    val df = sparkSession.read.json("spark1/dh2")

//    df.write.format("json").mode(SaveMode.Append).save("dd")
    //等价于
    //    df.write.mode(SaveMode.Append).json("dd")

    df.write.mode(SaveMode.Overwrite).json("dd")

    sparkSession.close()
  }
}
