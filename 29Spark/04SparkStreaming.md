# SparkStreaming

## 1、Spark Streaming概述

### ①、Spark Streaming是什么

* Spark Streaming用于流式数据的处理。Spark Streaming支持的数据输入源很多，例如：Kafka、Flume、Twitter、ZeroMQ和简单的TCP套接字等等。
* 数据输入后可以用Spark的高度抽象原语如：map、reduce、join、window等进行运算。而结果也能保存在很多地方，如HDFS、数据库等。
* 和Spark基于RDD的概念很相似，Spark Streaming使用离散化流（discretized stream）作为抽象表示，叫做DStream。DStream是随着时间推移而收到的数据的序列。在内部，每个时间区间收到的数据都作为RDD存在，而DStream是由这些RDD做组成的序列（因此得名“离散化”）

### ②、Spark Streaming特点

* 易用
* 容错
* 易整合到Spark体系

### ③、SparkStreaming 架构

* 数据接收器和任务执行器不在一个工作节点上

### ④、背压机制

* Spark1.5之前的版本，用户如果要限制Receiver的数据接收速率，可以通过设置静态配置参数“spark.streming.receiver.maxRate”的值来实现，此举虽然可以通过限制接收速率，来适配当前的处理能力，防止内存溢出，当也会引入其他问题。比如：producer数据生产高于maxRate，当前集群处理能力也高于maxRate，这就会造成资源利用率下降等问题。
* 为了更好的协调数据接收速率与资源处理，1.5版本开始Spark Streaming可以动态控制数据接收速率来适配集群数据处理能力。
* 背压机制（Spark Streaming Backpressure）：根据JobScheduler反馈作业的执行信息来动态调整Receiver数据接收率。
* 通过属性“spark.streaming.backpressure.enabled”来控制是否启动backpressure机制，默认值时false，既不开启

## 2、DStream入门

### 版本选型

* ReceiverAPI：需要一个专门的Executor去接收数据，然后发送给其他的Executor做计算。存在的问题你，接收数据的Executor和计算的Executor速度会有所不同，特别在接收数据的Executor速度大于计算的Executor速度，会导致计算数据的节点内存溢出

* DirectAPI：是由计算的Executor来主动消费Kafka的数据，速度又自身控制。

  * 0.8 ReceiverAPI
    * 1、专门的Executor读取数据，速度不统一
    * 2、跨机器传输数据，WAL
    * 3、Executor读取数据通过多个线程的方式，想要增加并行度，则需要多个流
    * 4、offset存储再Zookeeper中

  * 0.8 DirectAPI

    * 1、Executor读取数据并计算

    * 2、增加Executor个数来增加消费的并行度

    * 3、offset存储

      a.CheckPoint（getActiveOrCreate方式创建的StreamingContext）；

      b.手动维护（有事物的存储系统）获取offset必须再第一个调用的算子中

      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges

  * 0.10 DirectAPI

    * 1、Executor读取数据并计算

    * 2、增加Executor个数来增加消费的并行度

    * 3、offset存储

      a.__consumer_offsets系统主题中

      b.手动维护（有事务的存储系统中）

### ①、WordCount案例

```scala
object HReceiveWord {
  def main(args: Array[String]): Unit = {
    //创建StreamingContext对象
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""),Seconds(3))

    //使用自定义的Receiver读取数据创建DStream
    val rDStream = ssc.receiverStream(new HMyReceiver("hadoop201",9999))

    //计算wordCount
    rDStream.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()
    
    //开启任务
    ssc.start()
    ssc.awaitTermination()
  }
}
```

* DStream是Spark Streaming的基础抽象，代表持续性的数据流和经过各种Spark原语操作后的结果数据流。再内部实现上，DStream是一系列的RDD来表示。每个RDD含有一段时间间隔内的数据

## 3、DStream创建

### ①、RDD队列

```scala
object RDDStream {

  def main(args: Array[String]) {

    //1.初始化Spark配置信息
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDDStream")

    //2.初始化SparkStreamingContext
    val ssc = new StreamingContext(conf, Seconds(4))

    //3.创建RDD队列
    val rddQueue = new mutable.Queue[RDD[Int]]()

    //4.创建QueueInputDStream
    val inputStream = ssc.queueStream(rddQueue,oneAtATime = false)

    //5.处理队列中的RDD数据
    val mappedStream = inputStream.map((_,1))
    val reducedStream = mappedStream.reduceByKey(_ + _)

    //6.打印结果
    reducedStream.print()

    //7.启动任务
    ssc.start()

//8.循环创建并向RDD队列中放入RDD
    for (i <- 1 to 5) {
      rddQueue += ssc.sparkContext.makeRDD(1 to 300, 10)
      Thread.sleep(2000)
    }

    ssc.awaitTermination()

  }
}
```

### ②、自定义数据源

```scala
class HMyReceiver(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){

  def receive(): Unit = {

    try {
      //创建socket流对象
      val socket = new Socket(host, port)

      //创建流
      val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))

      //读取数据
      var str = reader.readLine()

      if (str != null && !isStopped()) {
        //写入spark内存
        store(str)
        //读取新的数据
        str = reader.readLine()
      }

      //关闭流
      reader.close()
      socket.close()
      //重启
      restart("restart")
    } catch {
      //出现异常，重启
      case e:Exception => restart("restart")
    }
  }

  //启动时调用的方法
  override def onStart(): Unit = {
    //开启新的线程读取数据
    new Thread(){
      override def run(): Unit = {
        receive()
      }
    }.start()
  }

  override def onStop(): Unit = {}
}
```

```scala
object HReceiverAPI {
  def main(args: Array[String]): Unit = {
    val ssc =
      new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"),Seconds(3))

    val lineDStream =
      KafkaUtils.createStream(
        ssc,
        "hadoop201:2181,hadoop202:2081,hadoop203:2181",
        "atguigu",
        Map[String,Int]("atguigu"->1))

    lineDStream.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
```

### ③、自动Kafka数据源（无法断点续传）【KafkaUtils 0.8版本】

```scala
object HDirectAPIAuto1 {
  def main(args: Array[String]): Unit = {
    //获取StreamingContext对象
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""), Seconds(2))
    //kafka参数
    val kafkaParams = Map[String, String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )
    //获取kafka数据并创建DStream对象
    val kafkaDStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, Set("atguigu"))
    //数据计算并打印
    kafkaDStream.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()
    //开启任务
    ssc.start()
    ssc.awaitTermination()
  }
}
```

### ④、自动Kafka数据源（断点续传）【KafkaUtils 0.8版本】

```scala
object HDirectAPIAuto2 {


  val getSsc: StreamingContext = {

    //创建StreamingContext对象
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName(""),Seconds(5))

    //设置checkpoints
    ssc.checkpoint("cc")

    //kafka参数
    val kafkaParams: Map[String, String] = Map[String,String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )

    //获取kafka数据并创建DStream对象
    val kafkaDStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder](ssc,kafkaParams,Set("atguigu"))

    //对数据逻辑处理并打印
    kafkaDStream.map(_._2).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    //返回StreamingContext对象
    ssc
  }

  def main(args: Array[String]): Unit = {

    //获取ssc
    val ssc = StreamingContext.getActiveOrCreate("cc",()=>getSsc)

    //启动任务
    ssc.start()
    ssc.awaitTermination()
  }
}
```

### ⑤、手动Kafka数据源（断点续传）【KafkaUtils 0.8版本】

```scala
object HDirectHand08 {
  def main(args: Array[String]): Unit = {

    //创建StreamContext对象
    val ssc = new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"), Seconds(5))

    //kafka参数
    val kafkaParams: Map[String, String] = Map[String,String](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu"
    )

    //获取上一次启动最后保留的Offset【可以通过存储offset到数据库中】
    val fromOffset = Map[TopicAndPartition, Long](TopicAndPartition("atguigu",0) -> 10)

    //读取Kafka数据并创建DStream对象
    val kafkaDStream = KafkaUtils.createDirectStream[String,String,StringDecoder,StringDecoder,String](
      ssc,
      kafkaParams,
      fromOffset,
      (m:MessageAndMetadata[String,String])=>m.message())

    //创建一个数据用于存放当前消费数据的Offset信息
    var offsetRanges = Array.empty[OffsetRange]

    //获取当前消费者数据的offset信息
    val wordOnCount = kafkaDStream.transform(rdd => {
      offsetRanges = rdd.asInstanceOf[HasOffsetRanges].offsetRanges
      rdd
    }).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)

    //打印Offset信息
    wordOnCount.foreachRDD(rdd=>{
      offsetRanges.foreach(a=>println(a.topic+","+a.partition+","+a.fromOffset+","+a.untilOffset))
      rdd.foreach(println)
    })

    //开启任务
    ssc.start()
    ssc.awaitTermination()
  }
}
```

### ⑥、自动Kafka数据源（断点续传）【KafkaUtils 0.10版本】

```scala
object DirectAPI {
  def main(args: Array[String]): Unit = {
    //
    val ssc = new StreamingContext(new SparkConf().setAppName("").setMaster("local[*]"), Seconds(5))

    var kafkaParams = Map[String, Object](
      ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG -> "hadoop201:9092,hadoop202:9092,hadoop203:9092",
      ConsumerConfig.GROUP_ID_CONFIG -> "atguigu",
      "key.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer",
      "value.deserializer" -> "org.apache.kafka.common.serialization.StringDeserializer"
    )

    val kafkaDStream = KafkaUtils.createDirectStream[String,String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String,String](Set("atguigu"), kafkaParams)
    )

    kafkaDStream.map(_.value()).flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
```

