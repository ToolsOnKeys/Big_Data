# Kafka

## 1、Kafka概述

### ①、定义

* Kafka是一个分布式的基于发布/订阅模式的消息队列（Message Queue），主要应用于大数据实时处理领域。

### ②、消息队列

#### 1、使用消息队列的好处

* **解耦**：允许你独立的扩展或修改两边的处理过程，只要确保他们遵守同样的接口约束。
* **可恢复性**：系统的一部分组件失效时，不会影响整个系统。消息队列降低了进程间的耦合度，所以及时一个处理消息的进程挂掉，加入队列中的消息仍然可以在系统恢复后被处理
* **缓冲**：有助于控制和优化数据流经过系统的速度，解决生产消息和消费消息的处理速度不一致的情况
* **灵活性&峰值处理能力**：在访问量剧增的情况下，应用仍然需要继续发挥作用，但是这样的突发流量并不常见。如果为以能处理这类分支访问为标准来投入资源随时待命无疑是巨大的浪费。使用消息队列能够时关键组件顶住顶住的访问压力，而不会因为突发的超负荷的请求而完全崩溃
* **异步通信**：很多时候，用户不想也不需要立即处理消息。消息队列提供了异步处理机制，允许用户把一个消息放入队列，但不立即处理它。想向队列中放入多少消息就放多少，然后在需要的时候再去处理他们。

#### 2、消息队列的两种模式

* **点对点模式**（一对一，消费者主动拉取数据，消息收到后消息清除）

  消费生产者生产消息发送到Queue中，然后消息消费者从Queue中取出并且消费消息。消息被消费以后，queue中不再有存储，所以消息消费者不可能消费到已经被消费的消息。Queue支持存在多个消费者，但是对一个消息而言，只会有一个消费者可以消费。

* **发布/订阅模式**（一对多，消费者消费数据之后不会清除消息）

  消息生产者（发布）将消息发布到topic中，同时有多个消息消费者（订阅）消费该消息。和点对点方式不同，发布到topic的消息会被所有订阅者消费。

### 3、Kafka基础架构

![](D:\BigData\BigData\21Kafka\相关图片\Kafka架构.png)

* 1、Producer：消息生产者，就是向kafka broker发消息的客户端
* 2、Consumer：消息消费者，向kafka broker取消息的客户端
* 3、Consumer Group（CG）：消费者组，有多个consumer组成。消费者组内每个消费者负责不同分区的数据，一个分区只能由一个组内消费者消费；消费者之间互不影响。所有的消费者都属于某个消费者组，即消费者组是逻辑上的一个订阅者。
* 4、Broker：一台kafka服务器就是一个broker。一个集群由多个broker组成。一个broker可以容纳多个topic
* 5、Topic：可以理解为一个队列，生产者和消费者面向的都是一个topic；
* 6、Partition：为了实现一个扩展性，一个非常大的topic可以分布到多个broker（即服务器）上，一个topic可以分为多个partition，每个partition是一个有序的队列；
* 7、Replica：副本，为保证集群中的某个节点发生故障时，该节点上的partition数据不丢失，且kafka仍然能够继续工作，kafka提供了副本机制，一个topic的每个分区都有若干个副本，一个leader和若干个follower。
* 8、leader：每个分区多个副本的“主”，生产者发送数据的对象，以及消费者消费数据的对象都是leader。
* 9、follower：每个分区多个副本中的“从”，实时从leader中同步数据，保持和leader数据的同步。leader发生故障时，某个follower会成为新的follower。

## 2、Kafka 快速入门

### ①、安装配置

<https://github.com/ToolsOnKeys/BigData/blob/master/21Kafka/02Kafka%E5%BF%AB%E9%80%9F%E5%AE%89%E8%A3%85.md>

### ②、Kafka命令行操作

```linux
# 1、查看当前服务器中的所有topic
> bin/kafka-topics.sh --zookeeper hadoop102:2181 --list
# 2、创建topic
> bin/kafka-topics.sh --zookeeper hadoop102:2181 --create --replication-factor 3 --partitions 1 --topic first
# 3、删除topic
> bin/kafka-topics.sh --zookeeper hadoop101:2181 --delete --topic first
# 4、发送消息
> bin/kafka-console-producer.sh --broker-list hadoop102:9092 --topic first
# 5、消费消息
> bin/kafka-console-consumer.sh  --zookeeper hadoop102:2181 --topic first
> bin/kafka-console-consumer.sh  --bootstrap-server hadoop102:9092 --topic first
> bin/kafka-console-consumer.sh  --bootstrap-server hadoop102:9092 --from-beginning --topic first
# 6、查看某个Topic的详情
> bin/kafka-topics.sh --zookeeper hadoop102:2181 --describe --topic first
# 7、修改分区数
> bin/kafka-topics.sh --zookeeper hadoop102:2181 --alter --topic first --partitions 6
```

## 3、Kafka 架构深入

### ①、Kafka工作流程及文件存储机制

* Kafka中消息是以topic进行分类的，生产者生产消息，消费者消费消息，都是面向topic的。
* topic（消息队列）是逻辑的概念，而partition是物理的上的概念，每个partition对应于一个log文件，该log文件中存储的就是producer生产的数据。Producer生产的数据会被不断追加到该log文件末端，且每条数据都有自己的offset。消费者组中的每个消费者，都会实时记录自己消费到了哪个offset，一边出错恢复时，从上次的位置继续消费。
* 一个topic分为多个partition，一个partition可以分为多个segment，一个segment对应两个文件（log文件和index文件）
* 由于生产者生产的消息会不断追加到log文件末尾，为防止log文件过大导致数据定位效率低下，Kafka采取了分片和索引机制，将每个partition分为多个segment。每个segment对应两个文件—index文件和log文件。这些文件位于一个文件夹下，该文件夹的命名规则为：topic名称+分区序号。
* index文件存储大量的索引信息，log文件存储大量的数据，索引文件中的元数据指向对应数据文件中message的物理偏移地址。

### ②、Kafka生产者

* Ⅰ、分区策略

  * 分区的原因：

    * ①、方便在集群中扩展，每个Partition可以通过调整以适应它所在的机器，而一个topic又可以由多个Partition组成，因此整个集群就可以适应任意大小的数据了；
    * ②、可以提高并发，因为可以以Partition为单位读写了。

  * 分区的原则：

    我们需要将producer发送的数据封装成一个ProducerRecord对象。

    * ①、指明partition的情况下，直接将指明的值直接作为partition值
    * ②、没有知名partition值，但是有key的情况下，将key的hash值与topic的partition数进行取余得到partition值；
    * ③、既没有partition值有没有key值的情况下，第一次调用随机生成的一个整数（后面每次调用再这个整数上自增），将这个值与topic可以用的partition总数区域得到partition值，也就是常说的round-robin算法。

* Ⅱ、数据可靠性保证

  为保证producer发送的数据，能可靠的发送到指定的topic，topic的每个partition收到produucer发送的数据后，都需要向producer发送ack（acknoledgerment确认收到），如果producer收到ack，就会进行下一轮的发送，否则重新发送数据。

  * ISR

    Leader维护了一个动态的同步副本队列（ISR），意为和leader保持同步的follower集合。当ISR中的follower完成数据的同步之后，leader就会给follower发送ack。如果follower长时间未向leader同步数据，则该follower将被踢出ISR，该时间阈值由replica.time.max.ms参数设定.Leader发生故障之后，就会从ISR中选举新的leader。

  * ack应答机制

    * 对于某些不太重要的数据，对数据的可靠性要求不是很高，能够容忍数据的少量丢失，所以没必要等ISR中的follower全部接受成功。
    * 所以Kafka向用户提供了三种可靠性级别，用户根据对可靠性和延迟的要求进行权衡，选择一下的配置。
      * 0：producer不等待broker的ack，这一操作提供了一个最低的延迟，broker已接受到还没有写入磁盘就已经返回，当broker故障时有可能丢失数据；
      * 1：producer等待broker的ack，partition的leader落盘成功后返回ack，如果在follower同步之前leader故障，那么将会丢失数据；
      * -1：all，producer等待broker的ack，partition的leader和follower全部落盘成功后才返回ack。但是如果在follower同步完成后，broker发送ack之前，leader发生故障，那么会造成数据重复。

  * 故障处理细节

    LEO：指的是每个副本最大的offset;

    HW：值得是消费者能见到的最大的offset，ISR队列中最小的LEO。

    * follower故障：follower发生故障后，会被临时提出ISR，待改follower恢复后，follower会读取本地磁盘记录的上次的HW，并将log文件高于HW的部分截取掉，从HW开始向leader进行同步。等待follower的LEO大于等于partition的HW，即follower追上leader之后，就可以重新加入ISR了。
    * leader故障：leader发生故障之后，会从ISR中选出一个新的leader，之后，为保证多个副本之间的数据一致性，其余的follower会先将各自的log文件中高于HW的部分戒掉，然后从新的leader同步数据。
    * 注意：这只能保证副本之间的数据一致性，并不能保证数据不丢失或者不重复。

* Ⅲ、Exactly Once语义

  * At Least Once语义：将服务器的ACK设置为-1，可以保证Peoducer到Server之间不会丢失数据。
  * At Most Once语义：将服务器的ACK设置为0，可以保证生产者每条消息只会被发送一次。
  * Exactly Once语义：在At Least Once的基础上加上幂等性特性，即将Produce的参数中的enable.idompotence设置为true即可。可以保证数据完整且不重复
  * 幂等性无法保证跨分区会话的Exactly Once。

### ③、Kafka消费者

* Ⅰ、消费方式

  * consumer采用pull（拉）模式从broker中读取数据。
  * push（推）模式很难适应消费速率不同的消费者，因为消息发送速率是由broker决定的。他的目标是尽可能以最快速度传递消息，但是这样很容易造成consumer来不及处理消息，典型的表现就是拒绝服务以及网络拥塞。而pull模式则可以根据consumer的消费能力以适当的速率消费消息。
  * pull模式不足之处是，如果kafka没有数据，消费者可能陷入循环中，一致返回空数据。针对这一点，Kafka的消费者在消费数据时会传入一个时长参数timeout，如果当前没有数据可供消费，consumer会等待一段时间之后再返回，这段市场即为timeout。

* Ⅱ、分区分配策略

  * RoundRobin：即针对topic的所有消费者，进行轮转分配
  * Range：即针对将一个组，所有的topic，然后“分片”，分给所有的消费者，有错读的风险

* Ⅲ、offset维护

  * 由于consumer在消费过程中可能会出现断电宕机等故障，consumer恢复后，需要从故障前的位置继续消费，所以consumer需要实时记录自己消费到了哪个offset，以便故障恢复以后继续消费。

  * Kafka 0.9版本之前，consumer默认将offset保存在Zookeeper中，从0.9版本开始，consumer默认将offset保存在Kafka一个内置的topic中，该topic为_consumer_offsets.

  * 需要修改配置文件consumer.properties

    ```properties
    exclude.internal.topics=false
    ```

* Ⅳ、消费者组案列

  * 1）需求：测试同一个消费者组中的消费者，同一时刻只能有一个消费者消费。
  * 2）案例实操

  ```linux
  # （1）在hadoop102、hadoop103上修改/opt/module/kafka/config/consumer.properties配置文件中的group.id属性为任意组名。
  [atguigu@hadoop103 config]$ vi consumer.properties
  group.id=atguigu
  #（2）在hadoop102、hadoop103上分别启动消费者
  [atguigu@hadoop102 kafka]$ bin/kafka-console-consumer.sh --zookeeper hadoop102:2181 --topic first --consumer.config config/consumer.properties
  [atguigu@hadoop103 kafka]$ bin/kafka-console-consumer.sh -- zookeeper hadoop102:2181 --topic first --consumer.config config/consumer.properties
  #（3）在hadoop104上启动生产者
  [atguigu@hadoop104 kafka]$ bin/kafka-console-producer.sh --broker-list hadoop102:9092 --topic first
  >hello world
  #（4）查看hadoop102和hadoop103的接收者。 同一时刻只有一个消费者接收到消息。
  ```

### ④、Kafka高效读写数据

* 顺序写磁盘

  Kafka的producer生产数据，要写到log文件中，写的过程一直是追加到文件末尾，为顺序写。官网有数据表明，同样的磁盘，顺序写能到600M/s，而随机写只有100K/s。这与磁盘的机械结构有关，顺序写之所以快，是因为其省去了大量磁头寻址的时间。

* 零复制技术

  零拷贝

### ⑤、Zookeeper在Kafka中的作用

* Kafka集群中有一个broker会被选举为Controller，负责管理集群broker的上下线，所有topic的分区副本分配和leader选举等工作。
* Controller的管理工作都是依赖于Zookeeper的。

### ⑥、Kafka事务

* Kafka从0.11版本开始引入了事务支持。事务可以保证Kafka在Exactly Once语义的基础上，生产和消费可以跨分区和会话，要么全部成功，要么全部失败。

### ⑦、Producer事务

* 为了实现跨分区跨会话的事务，需要引入一个全局唯一的Transaction ID，并将Producer获得的PID和Transaction ID绑定。这样当Producer重启后就可以通过正在进行的TransactionID获得原来的PID。
* 为了管理Transaction，Kafka引入了一个新的组件Transaction Coordinator。Producer就是通过和Transaction Coordinator交互获得TransactionID对应的任务状态。Transaction Coordinator还负责将事务所有写入Kafka的一个内部Topic，这样即使整个服务重启，由于事务状态得到保存，进行中的事务状态可以得到恢复，从而继续进行。

### ⑧、Consumer事务

* 上述事务机制主要是由Producer方面考虑，对于Consumer而言，事务的保证就会相对较弱，尤其是无法保证Commit的信息被精确消费。这是由于Consumer可以通过offset访问任意信息，而且不同的Segment File生命周期不同，同一事物的消息可能会出现重启后被删除的情况。

## 4、Kafka API

### ①、Producer API

#### Ⅰ、消息发送流程

* Kafka的Producer发送消息采用的是异步发送的方式。在消息发送的过程中，设计到了两个线程——main线程和Sender线程，以及一个线程共享变量——RecordAccumulator。main线程将消息发送给RecordAccumulator，Sender线程不断从RecordAccumulator。main线程将消息发送给RecordAccumulator，Sender线程不断从RecordAccumulator中拉取消息发送到Kafka broker。
* 相关参数：
  * batch.size：只有数据积累到batch.size之后，sender才会发送数据。
  * linger.ms：如果数据迟迟未达到batch.size，sender等待linger.time之后就会发送数据。

#### Ⅱ、异步发送API

* 导入依赖

  ```xml
  <dependency>
  <groupId>org.apache.kafka</groupId>
  <artifactId>kafka-clients</artifactId>
  <version>0.11.0.0</version>
  </dependency>
  ```

* 编写代码


#### Ⅲ、同步发送API

### ②、Consumer API

#### Ⅰ、自动提交offset

#### Ⅱ、手动提交offset

#### Ⅲ、自定义存储offset

### ③、自定义Interceptor

#### Ⅰ、拦截器原理

#### Ⅱ、拦截器案例

