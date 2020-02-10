# RDD序列化

* 初始化工作实在Driver端进行的，而时机运行的程序实在Executor端进行的，设计跨进程通信，如果设计到内存对象的数据，需要对内存对象进行序列化，

## 1、闭包

### ①、闭包检查

* 主要是Executor阶段对算子过程进行校验，首先校验是否存在闭包，如果存在闭包，则会坚持是否序列化，如果没有序列化，会执行序列化【序列化是否允许，需要看对象是否具有某些特性：①、是否继承序列类②、是否是样例类（样例类默认实现序列化方法）】

### ②、解决算子过程中存在对象的问题

> Driver：算子意外的代码都是在Driver端执行
>
> Executor：算子里面的代码都是在Executor端执行

* 类继承scala.Serializable
* 将类变量（不包括对象赋值）赋值给基本变量
* 把对象类变成样例类

### ③、Kryo序列化框架

* Java序列化能够序列化任何的类，但是序列化后的对象比较繁重，机器传输过程中比较消耗性能
* Spark处于性能考虑，提供了Kryo序列化框架（RDD在Shuffle数据的是后，简单的数据类型、数组和字符串类型已经在Spark内部使用了kryo来序列化了）

* 即使使用kryo序列化，对象类的定义也要继承Serializable接口，或者使用样例类

* 同时SparkConf对象建立的时候也要设置相应的参数

  ```scala
  val conf: SparkConf = new SparkConf()
                  .setAppName("SerDemo")
                  .setMaster("local[*]")
                  // 替换默认的序列化机制
                  .set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
                  // 注册需要使用 kryo 序列化的自定义类
                  .registerKryoClasses(Array(classOf[Searcher]))
  ```

  