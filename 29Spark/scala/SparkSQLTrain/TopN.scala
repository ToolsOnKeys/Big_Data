package SparkSQLTrain

import java.text.{DecimalFormat, NumberFormat}

import org.apache.spark.sql.{SaveMode, SparkSession}

/**
  * @author dinghao 
  * @create 2020-02-08 9:15 
  * @message
  */
object TopN {
  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().enableHiveSupport().master("local[*]").getOrCreate()
    import sparkSession.implicits._

    sparkSession.sql("use sparkpractice").show()

    val dataFrame = sparkSession
      .sql("select a.click_product_id,b.product_name,c.city_name,c.area from user_visit_action a left join product_info b on a.click_product_id = b.product_id left join city_info c on a.city_id = c.city_id where a.click_product_id != '-1'")
      .toDF("pid", "pname", "city", "area")
    dataFrame.createTempView("message")

    sparkSession.sql("select b.area, b.pname, b.num, b.rak from (select a.area,a.pname,a.num,row_number() over(partition by area order by num desc) rak from (select area,pname,count(*) num from message group by area,pname) a)b where b.rak<=3")
      .toDF("area", "pname", "num", "rak").createTempView("df1")

    sparkSession.sql("select d.area,d.pname,d.city,d.num,d.rak from (select c.area, c.pname, c.city, c.num, row_number() over(partition by c.area,c.pname order by c.num desc) rak from (select a.area,a.pname,a.city,count(*) num from message a join df1 b on a.area = b.area and a.pname = b.pname group by a.area,a.pname,a.city)c)d where d.rak <=2")
      .toDF("area", "pname", "city", "num", "rak").createTempView("df2")

    sparkSession.sql("select a.area,a.pname,a.num,b.city,b.num/a.num bfs from df1 a left join df2 b on a.area = b.area and a.pname = b.pname")
      .rdd.map(a => ((a(0), a(1), a(2)), (a(3), a(4)))).groupByKey().map(a => {
      var middle = ""
      var ss = 0.0
      val mm = new DecimalFormat("0.00%")
      a._2.foreach(elem => {
        middle = middle + elem._1 + mm.format(elem._2.toString.toDouble) + ","
        ss = ss + elem._2.toString.toDouble
      })
      if (1 - ss != 0) {
        middle = middle + "其他" + mm.format(1 - ss)
      } else {
        middle = middle.substring(0, middle.length - 1)
      }
      (a._1._1.toString, a._1._2.toString, a._1._3.toString.toInt, middle)
    }).toDF("area", "pname", "num", "rates").createTempView("df3")

    sparkSession.sql("select * from df3 order by area,num desc").show(40,false)
//      .write.format("jdbc")
//      .mode(SaveMode.Overwrite)
//      .option("url", "jdbc:mysql://hadoop201:3306/gmall?useUnicode=true&characterEncoding=utf8")
//      .option("dbtable", "message")
//      .option("user", "root")
//      .option("password", "ding087417")
//      .save()

    sparkSession.close()
  }
}
