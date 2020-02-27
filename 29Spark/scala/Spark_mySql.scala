import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @author dinghao 
  * @create 2020-02-07 14:49 
  * @message
  */
object Spark_mySql {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().master("local[*]").getOrCreate()
  import sparkSession.implicits._

    //加载Mysql参数
//    val properties = new Properties()
//    properties.put("user","root")
//    properties.put("password","ding087417")
    //加载Mysql数据
//    val df = sparkSession.read.jdbc("jdbc:mysql://localhost:3306/test50","student",properties)

//等价于
      //加载数据
    val df = sparkSession.read.format("jdbc")
      .option("url", "jdbc:mysql://localhost:3306/test50")
      .option("dbtable", "student")
      .option("user", "root")
      .option("password", "ding087417")
      .load()

    df.createTempView("student")

    //保存数据
    sparkSession.sql("select sid id,sname name,ssex sex from student").write
      .format("jdbc")
      .mode(SaveMode.Overwrite)
      .option("url", "jdbc:mysql://localhost:3306/test50")
      .option("dbtable", "stu")
      .option("user", "root")
      .option("password", "ding087417")
      .save()


  }
}
