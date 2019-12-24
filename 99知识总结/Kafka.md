# Kafka相关总结

## 1、Kafka压测

* Kafka官方自带压力测试脚本（kafka-consumer-perf-test.sh、kafka-producer-perf-test.sh）。Kafka压测时，可以查看到哪个地方出现瓶颈（CPU、内存、网络IO）。一般都是网络IO达到瓶颈。

## 2、Kafka的机器数量

* Kafka 机器数量=2✖（峰值生产速度 ✖副本数/100）+1

## 3、Kafka的日志保存时间

* 7天

## 4、Kafka的硬盘大小

* 每天的数据量*7天/70%

## 5、Kafka监控

* 公司自己开发的控制器
* 开源的监控器：KafkaManager、KafkaMonitor、Kafkaeagle

## 6、Kafka分区数

* 分区数并不是越多越好，一般分区数不要超过集群机器数量。分区数越多占用内存越大（ISR等），一个节点集中的分区也就越多，当他宕机的时候，对系统的影响也就越大。
* 分区数一般设置为3-10个。

## 7、Kafka副本数设定

* 一般我们设置成2个或3个，很多企业设置为2个。

## 8、多少个Topic

* 通常情况下：多少日志类型就多少个Topic。也有对日志类型进行合并的。

## 9、Kafka丢不丢数据

* Ack=0，相当于异步发送，消息发送完毕即offset增加，继续生产。
* Ack=1，lead收到leader replica对一个消息的接收ack才增加offset，然后继续生产。
* Ack=-1，lead收到所有replica对一个消息的接受ack才增加offset，然后继续生产。

## 10、Kafka的ISR副本同步队列

* ISR（In-Sync Replicas），副本同步队列。ISR中包括Leader和Follower。如果Leader进程挂掉，会在ISR队列中选择一个服务作为新的Leader。有replic.lag.max.messages(延迟条数)和replica.lang.time.max.ms（延迟时间）两个参数决定一台服务是否可以加入ISR副本队列，在0.10版本移除了replica.lag.max.messages参数，防止服务频繁的进去队列。
* 任意一个维度超过阈值都会把Follower提出出ISR，存入OSR列表，新加入的Follower也会像存放在OSR中。

## 11、Kafka分区分配策略

* 在Kafka内部存在两种默认的分区分配策略：Range和RoundRobin。
* Range时默认策略。Range是对每个Topic而言的（即一个Topic一个Topic分），首先对同一个Topic里面的分区按照序号进行排序，并对消费者按照字母顺序进行排序。然后用Partitions分区的个数除以消费者线程的总数来决定每个消费者线程消费几个分区。如果除不尽，呢们前面几个消费之线程将会多消费一个分区。

## 12、Kafka中数据量计算

* 每天总数据量100G，每天产生1亿条日志，10000W/24/60/60=1150条/秒
* 平均每秒：1150
* 低谷每秒：50
* 高峰每秒：1150*（2倍至20倍）=2300条~23000条
* 每条日志大小：0.5k~2k
* 每秒多少数据量：2.5M~20M

## 13、Kafka挂掉

* Flume记录
* 日志有记录
* 短期没事

## 14、Kafka消息数据积压，Kafka数据能力不足怎么处理？

* 如果时Kafka消费能力不足，则可以考虑增加Topic的分区数，并且同时提升消费组的消费者数量，消费者数=分区数。（两者缺一不可）
* 如果时下游的数据处理不及时：提高每批次来去的数量。批次拉取数据过少（拉取数据/处理时间<生产速度），是处理的数据小于生产的数据，也会造成数据挤压。