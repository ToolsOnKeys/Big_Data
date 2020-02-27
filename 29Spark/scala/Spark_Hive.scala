import org.apache.spark.sql.SparkSession

/**
  * @author dinghao 
  * @create 2020-02-07 16:15 
  * @message
  */
object Spark_Hive {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").enableHiveSupport().getOrCreate()
    import sparkSession.implicits._

    sparkSession.sql("create table test(id int)").show()

    sparkSession.sql("show tables").show()

    sparkSession.sql("insert into test values(1001)").show()

    sparkSession.sql("select * from test").show()

    sparkSession.close()
  }
}
