import org.apache.spark.sql.SparkSession

/**
  * @author dinghao 
  * @create 2020-02-07 16:22 
  * @message
  */
object Spark_Hive2 {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().enableHiveSupport().master("local[*]").getOrCreate()
    import sparkSession.implicits._


    //    sparkSession.sql("select * from gmall.ads_uv_count").show()
    sparkSession.sql("use gmall").show()
    sparkSession.sql("show tables").show()
    sparkSession.sql("select * from ads_uv_count").show()

    sparkSession.close()
  }
}
