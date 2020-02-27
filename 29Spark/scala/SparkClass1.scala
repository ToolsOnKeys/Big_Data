import org.apache.spark.sql.SparkSession

/**
  * @author dinghao 
  * @create 2020-02-07 9:12 
  * @message
  */
object SparkClass1 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").getOrCreate()

    import sparkSession.implicits._

    val df = sparkSession.read.json("spark1/dh2")
    val ds = df.as[people]

//    df.map(_.getString(1)).show()
//    ds.map(_.name).show()
    df.createTempView("Person")

//    sparkSession.sql("select name from Person").show()

    //UDF
    sparkSession.udf.register("addName",(a:String)=>"Name:"+a)
//    sparkSession.sql("select addName(name),age from Person").show()
    sparkSession.udf.register("myAvg",MyUDAF)
    sparkSession.sql("select myAvg(age) from Person").show()


    sparkSession.close()
  }
}
case class people(name:String,age:Long)
