# SparkSQL

## 1、概述

### ①、什么是Spark SQL

* Spark SQL是Spark用来处理结构化数据的一个模块，他提供了2个编程抽象：DataFrame和DataSet，并且作为分布式SQL查询引擎的作用。
* Spark SQL是将SparkSQL转换成RDD，然后提交到集群执行，执行效率快

### ②、Spark SQL的特点

* 易整合
* 统一的数据访问方式
* 兼容Hive
* 标准的数据连接

### ③、什么是DataFrame

* 在Spark中，DataFrame是一种以RDD为基础的分布式数据集，类似于传统数据库的二维表格。DataFrame与RDD的主要区别在于，前者带有schema元信息，即DataFrame所表示的二维表数据集的每一列都带有名称和类型。
* 这使得Spark SQL得以洞察更多的结构信息，从而对藏于DataFrame背后的数据源以及作用与DataFrame之上的变换进行了针对性的优化，最终达到大幅提升运行时效率的目标。
* 反观RDD，由于无从得知所存数据的具体内部结构，SparkCore只能在stage层面进行简单、通用的流水优化。
* DataFrame也是懒执行的，但性能上比RDD要高，主要原因：优化的执行计划，即查询计划通过Spark catalyst optimiser 进行了优化

### ④、什么是DataSet

* DataSet是分布式数据集合。DataSet是Spark 1.6中添加的新的抽象，是DataFrame的一个扩展。他提供了RDD的优势（强类型，使用强大的lambda函数的能力）以及Spark SQL优化执行引擎的优点。DataSet也可以使用功能性的转换（如map，flatMap，filter等等）
  * 1、是DataFrame API的一个扩展，是SparkSQL最新的数据抽象
  * 2、用户友好的API风格，即具有类型安全检查也具有DataFrame的查询优化特性
  * 3、用样例类来对DataSet中定义数据的结构信息，样例类中每个属性的名称直接映射到DataSet中的字段名称
  * 4、DataSet是强类型的。如可以有DataSet[Person]

## 2、Spark SQL编程

### ①、SparkSession 新的起始点

* 在老的版本中，SparkSQL提供两种SQL查询起始点：一个叫SQLContext，用于Spark自己提供的SQL查询；一个叫HiveContext，用于连接Hive的查询
* SparkSession是Spark最新的SQL查询起始点，实质上是SQLContext和HiveContext的组合，所以在SQLContext和HiveContext上可用的APISparkSession上同样可以使用的。SparkSession内部封装了sparkContext，所以计算实际上是由sparkContext完成的

```scala
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
```

### ②、RDD、DataFrame和DataSet

* 在SparkSQL中Spark为我们提供了两个新的抽象，分别是DataFrame和DataSet。他们和RDD有什么区别呢？首先从版本的产生来看：

  RDD(spark1.0)->DataFrame(spark1.3)->DataSet(spark1.6)

  如果同样的数据都给到这三个数据结构，他们分别计算之后，都会给出相同的结果。不同的是他们的执行效率和执行方式。在后期的Spark版本中，DataSet有可能会逐步取代RDD和DataFrame称为唯一的API接口

### ③、三者的共性

* RDD、DataFrame、DataSet全都是Spark平台下的分布式弹性数据集，为处理超大型数据提供便利
* 三者都有惰性机制，在进行创建、转换，只有在遇到Action时三者才会开始遍历运算。
* 三者有许多共通的函数
* 在对DataFrame和DataSet进行操作许多操作都需要这个包：import spark.implicits._ （创建好SparkSession对象后尽量直接导入）