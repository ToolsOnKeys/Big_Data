# SparkCore

> sc.flatMap(_.split(" ")).map((\_,1)).reduceByKey(\_+\_).collection

## 1、RDD概述

### ①、什么是RDD

* RDD叫做弹性分布式数据集，是Spark中最基本的数据抽象。代码中一个抽象类，代表一个弹性的、不可变、可分区、里面的元素可并行计算的集合
* 1、弹性
  * 存储的弹性：内存与磁盘的自动切换
  * 容错的弹性：数据丢失可以自动恢复
  * 计算的弹性：计算出错重试机制
  * 分片的弹性：可根据需要重新分片

* 2、分布式
  * 数据存储在大数据集群不同节点上
* 3、数据集
  * RDD封装了计算逻辑，并不保存数据
* 4、数据抽象
  * RDD是一个抽象类，需要子类具体实现
* 5、不可变
  * RDD封装了计算逻辑，是不可以改变的想要改变，只能产生新的RDD，在新的RDD里面封装计算逻辑
* 6、可分区、并行计算

## 2、分区规则

### ①、默认分区源码（RDD数据从集合中创建）

* 默认分区数=总的CPU核数

### ②、分区源码（RDD数据从集合中创建）

* 分区的开始位置=分区号*数据总长度/分区总数
* 分区的结束位置=（分区号+1）*数据总长度/分区总数

### ③、默认分区源码（RDD数据从文件中读取）

* 文件从创建的RDD，默认分区数为：defaultParallelism和2的最小值即CPU核数和2的最小值

### ④、分区源码（RDD数据从文件中读取后创建）

* 步长=文件大小/分区数

## 3、转换算子

### ①、map（）映射

```scala
//参数f是一个函数，它可以接收一个参数。当某个RDD执行map方法时，会遍历该RDD中的每一个数据项，并依次应用f函数，从而产生一个新的RDD。即，这个新RDD中的每一个元素都是原来RDD中每一个元素依次应用f函数而得到的
sc.makeRDD((1 to 20 )).map(_*2)
```

### ②、mapPartitions() 以分区为单位执行map

```scala
// Map是一次处理一个元素，而mapPartitions一次处理一个分区的数据
sc.makeRDD(1 to 20,2).mapPartitions(_.map(_*2))
```

### ③、mapPartitionsWithIndex()带分区号

```scala
sc.makeRDD(1 to 10,3).mapPartitionsWithIndex((index,items)=>items.map((index,_)))
```

### ④、flatMap（）压平

```scala
sc.makeRDD(List(List(1,2),List(2,3),List(3,4,23,2))).flatMap(a=>a)
```

### ⑤、glom（）分区转换为数组

```scala
sc.makeRDD(1 to 15,3).glom
```

### ⑥、groupBy（）分组

```scala
sc.makeRDD(1 to 15).groupBy(_%2)
```

### ⑦、filter（）过滤

```scala
sc.makeRDD(1 to 10).filter(_%3==2)
```

### ⑧、sample（）采样

```scala

```

