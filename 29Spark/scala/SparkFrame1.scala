import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * @author dinghao 
  * @create 2020-02-06 14:52 
  * @message
  */
//    dataFrame.filter($"age">20).show()
object SparkFrame1 {
  def main(args: Array[String]): Unit = {
//    val sc = new SparkContext(new SparkConf().setMaster("local[*]"))
    //创建sparkSession
    val sparkSession = SparkSession.builder().master("local[*]").getOrCreate()
        sparkSession.read.textFile("spark1/dh").show()
        sparkSession.read.json("spark1/dh2").createTempView("people")
        sparkSession.sql("select name from people").show()
    //数据加载
    val dataFrame = sparkSession.read.json("spark1/dh2")

    //SQL风格语法
        dataFrame.createTempView("people")
        sparkSession.sql("select age from people").show()
        println("======================global=====================")
        dataFrame.createGlobalTempView("person")
        sparkSession.sql("select name from global_temp.person").show()
        sparkSession.newSession().sql("select * from global_temp.person").show()
    //DSL风格语法

    //RDD 转换为DataFrame
    import sparkSession.implicits._
    dataFrame.filter($"age">20)
    //1、手动转换
    sparkSession.sparkContext.textFile("spark1/dh").map(a=>{
      val strings = a.split(",")
      (strings(0),strings(1).toInt)
    }).toDF("name","age").show()

    //2、样例类的方式转化
    sparkSession.sparkContext.textFile("spark1/dh").map(a=>{
      val strings = a.split(",")
      person(strings(0),strings(1).toInt)}).toDF().show()

    //DataFram转换为RDD
    sparkSession.read.json("spark1/dh2").rdd.collect().foreach(println)

    //创建DataSet
    sparkSession.sparkContext.textFile("spark1/dh").map(a=>{
      val strings = a.split(",")
      person(strings(0),strings(1).toInt)
    }).toDS().rdd.collect().foreach(println)

    //DataFrame和DataSet之间互操作
    sparkSession.read.json("spark1/dh2").as[person].toDF().show()
    //关闭sparkSession
    sparkSession.close()
  }
}
case class person(name:String,age:BigInt)