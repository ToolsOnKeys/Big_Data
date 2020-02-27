import org.apache.spark.sql.SparkSession

/**
  * @author dinghao 
  * @create 2020-02-05 17:07 
  * @message
  */
object Sparktest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder().master("local[*]").getOrCreate()


    import spark.implicits._
    val dataFrame = spark.sparkContext.textFile("spark1").map(a => {
      var m = a.split(",")
      (m(0), m(1).toInt)
    }).toDF("name","age")

    dataFrame.show()
//    dataFrame.createTempView("people")
//    spark.sql("select * from people").show()
    //关闭资源
    spark.stop()

    case class People(name: String, age: Int)
  }

}
