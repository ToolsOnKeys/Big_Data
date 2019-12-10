# Flume企业开发案列

## 1、复制和多路复用

### ①、实例需求

* 使用Flume-1监控文件变动，Flume将变动内容传递给Flume-2，Flume-2负责存储到HDFS。同时Flume-1将变动内容传递给Flume-3，Flume-3负责输出到Local FileSystem。

### ②、需求分析

![](D:\BigData\BigData\20Flume\相关图片\单数据源多出口案列-选择器.png)

### ③、实现步骤

* f5-1.conf

```f5-1.conf
# Name the components on this agent
a1.sources = r1
a1.sinks = k1 k2
a1.channels = c1 c2

#定义source
a1.sources.r1.type = exec
a1.sources.r1.command = tail -F /opt/module/hive/logs/hive.log
# 将数据流复制给所有channel
a1.sources.r1.selector.type = replicating

#定义sinks端
a1.sinks.k1.type = avro
a1.sinks.k1.hostname = 0.0.0.0
a1.sinks.k1.port = 44445
a1.sinks.k2.type = avro
a1.sinks.k2.hostname = 0.0.0.0
a1.sinks.k2.port = 44446

#定义channel端
a1.channels.c1.type = memory
## 总容量为10000个event
a1.channels.c1.capacity = 10000
## 表示传输时收集到10000个event以后才会去提交事务
a1.channels.c1.transactionCapacity = 10000

a1.channels.c2.type = memory
## 总容量为10000个event
a1.channels.c2.capacity = 10000
## 表示传输时收集到10000个event以后才会去提交事务
a1.channels.c2.transactionCapacity = 10000

#配置source、sinks和channel之间的关系
a1.soures.r1.channels = c1 c2
a1.sinks.k1.channel = c1
a1.sinks.k2.channel = c2
```

* f5-2.conf

  ```f5-2.conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  #配置source
  a1.sources.r1.type = avro
  a1.sources.r1.bind = 0.0.0.0
  a1.sources.r1.port = 44445
  
  #配置sinks
  a1.sinks.k1.type = hdfs
  a1.sinks.k1.hdfs.path = hdfs://hadoop101:9000/flumehomework/hivelogs-1/%y-%m-%d/%H
  #上传文件的前缀
  a1.sinks.k1.hdfs.filePrefix = logs-
  #是否按照时间滚动文件夹
  a1.sinks.k1.hdfs.round = true
  #多少时间单位创建一个新的文件夹
  a1.sinks.k1.hdfs.roundValue = 20
  #重新定义时间单位
  a1.sinks.k1.hdfs.roundUnit = sec
  #是否使用本地时间戳
  a1.sinks.k1.hdfs.useLocalTimeStamp = true
  #积攒多少个Event才flush到HDFS一次
  a1.sinks.k1.hdfs.batchSize = 1000
  #设置文件类型，可支持压缩
  a1.sinks.k1.hdfs.fileType = DataStream
  #多久生成一个新的文件
  a1.sinks.k1.hdfs.rollInterval = 30
  #设置每个文件的滚动大小
  a1.sinks.k1.hdfs.rollSize = 134217700
  #文件的滚动与Event数量无关
  a1.sinks.k1.hdfs.rollCount = 0
  
  #配置channels
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  
  ```

* f5-3.conf

  ```f5-3.conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  #配置source
  a1.sources.r1.type = avro
  a1.sources.r1.bind = 0.0.0.0
  a1.sources.r1.port = 44446
  
  #配置sinks
  a1.sinks.k1.type = file_roll
  a1.sinks.k1.sink.directory = /opt/module/flume/homework/datas/f5datas
  
  #配置channels
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```


### ④、验证

``` linux
> bin/flume-ng agent -c conf/ -n a2 -f homework/confs/f5-2.conf
> bin/flume-ng agent -c conf/ -n a2 -f homework/confs/f5-3.conf
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f5-1.conf
```

## 2、负载均衡和故障转移

### ①、案例需求

* 使用Flume1监控一个端口，其sink组中的sink分别对接Flume2和Flume3，采用FailoverSinkProcessor，实现故障转移的功能

  ![](D:\BigData\BigData\20Flume\相关图片\故障转移案例.png)

### ②、需求步骤

* f6-1.conf

  ```conf
  # Name the components on this agent
  a1.sources = r1
  a1.sinks = k1 k2
  a1.channels = c1
  
  # Describe/configure the source
  a1.sources.r1.type = netcat
  a1.sources.r1.bind = localhost
  a1.sources.r1.port = 44444
  #
  
  #配置负载均衡
  a1.sinkgroups = g1
  a1.sinkgroups.g1.sinks = k1 k2
  a1.sinkgroups.g1.processor.type = load_balance
  a1.sinkgroups.g1.processor.backoff = true
  a1.sinkgroups.g1.processor.selector = random
  #配置故障转移
  #a1.sinkgroups = g1
  #a1.sinkgroups.g1.sinks = k1 k2
  #a1.sinkgroups.g1.processor.type = failover
  #a1.sinkgroups.g1.processor.priority.k1 = 5
  #a1.sinkgroups.g1.processor.priority.k2 = 10
  #a1.sinkgroups.g1.processor.maxpenalty = 10000
  
  # 
  # 将数据流复制给所有channel
  #a1.sources.r1.selector.type = replicating
  
  #定义channel端
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  
  #定义sinks端
  a1.sinks.k1.type = avro
  a1.sinks.k1.hostname = hadoop102
  a1.sinks.k1.port = 44445
  a1.sinks.k2.type = avro
  a1.sinks.k2.hostname = hadoop103
  a1.sinks.k2.port = 44446
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  a1.sinks.k2.channel = c1
  ```

* f6-2.conf

  ```conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  #配置source
  a1.sources.r1.type = avro
  a1.sources.r1.bind = hadoop102
  a1.sources.r1.port = 44445
  
  #配置sinks
  a1.sinks.k1.type = logger
  
  #配置channels
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

* f6-3.conf

  ```conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  #配置source
  a1.sources.r1.type = avro
  a1.sources.r1.bind = hadoop103
  a1.sources.r1.port = 44446
  
  #配置sinks
  a1.sinks.k1.type = logger
  
  #配置channels
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

### ③、验证

```linux
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f6-2.conf
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f6-3.conf
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f6-1.conf
```

## 3、聚合

### ①、案列需求

* hadoop102上的Flume-1监控文件/opt/module/data/group.log，

* hadoop103上的Flume-2监控某一个端口的数据流，

* Flume-1与Flume-2将数据发送给hadoop101上的Flume-3，Flume-3将最终数据打印到控制台。

  ![](D:\BigData\BigData\20Flume\相关图片\多数据源汇总.png)

### ②、配置文件

* f7-1.conf

  ```conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  #配置source
  a1.sources.r1.type = avro
  a1.sources.r1.bind = hadoop101
  a1.sources.r1.port = 44445
  
  
  #定义channel端
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sinks.k1.type = logger
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

* f7-2.conf

  ```conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  
  a1.sources.r1.type = exec
  a1.sources.r1.command = tail -F /opt/module/data/f7.txt
  
  #定义channel端
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sinks.k1.type = avro
  a1.sinks.k1.hostname = hadoop101
  a1.sinks.k1.port = 44445
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

* f7-3.conf

  ```conf
  a1.sources = r1
  a1.sinks = k1
  a1.channels = c1
  
  a1.sources.r1.type = netcat
  a1.sources.r1.bind = localhost
  a1.sources.r1.port = 44444
  
  #定义channel端
  a1.channels.c1.type = memory
  ## 总容量为10000个event
  a1.channels.c1.capacity = 10000
  ## 表示传输时收集到10000个event以后才会去提交事务
  a1.channels.c1.transactionCapacity = 10000
  
  a1.sinks.k1.type = avro
  a1.sinks.k1.hostname = hadoop101
  a1.sinks.k1.port = 44445
  
  a1.sources.r1.channels = c1
  a1.sinks.k1.channel = c1
  ```

### ③、验证

```linux
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f7-1.conf -Dflume.root.logger=INFO,console
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f7-2.conf
> bin/flume-ng agent -c conf/ -n a1 -f homework/confs/f7-3.conf
```

## 4、自定义Interceptor

### ①、案例需求

* 使用Flume采集服务器本地日志，需要按照日志类型的不同，将不同的日志发完不同的分析系统。

### ②、需求分析

* 在实际开发中，一台服务器产生的日志类型可能有横幅哦中，不同类型的日志可能需要发送到不同的分析系统。此时会用到Flume拓扑结构中的Multiplexing结构，Multiplexing（多路复用）的原理是，根据event中Header的某个key的值，将不同的event发送到不同的Channel中，所以我们需要自定义一个Interceptor，为不同类型的event的Header中的key赋予不同的值。
* 在该案例中，我们一端口数据模拟日志，以数字（单个）和字母（单个）模拟不同类型的日志，我们需要自定义interceptor区分数字和字母，将其分别发完不同的分析系统（channel）

![](D:\BigData\BigData\20Flume\相关图片\选择器案列.png)