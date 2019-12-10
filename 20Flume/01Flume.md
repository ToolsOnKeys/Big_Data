# Flume

## 1、Flume概述

### ①、Flume 定义

* 提供的一个高可用，高可靠的，分布式的海量日志采集、聚合和传输的系统。Flume基于流式结构，灵活简单。
* Flume最主要的作用就是，实时读取服务器本地磁盘的数据，将数据写入到HDFS。

### ②、Flume基础架构

#### 2.1 Agent（JVM进程）

* Agent 是一个JVM进程，它以时间的形式将数据从源头送至目的。
* Agent 主要有三个部分组成，Source、Chiannel、Sink。

#### 2.2 Source（接收处理数据）

* Source是负责接收数据到Flume Agent的组件。Source组件可以处理各种类型、各种格式的日志数据，包括avro、thrift、exec、jms、spooling directory、netcat、sequence generator、syslog、http、legacy。

#### 2.3 Sink（事件处理）

* Sink 不断地轮询Channel中的事件且批量地移除它们，并将这些事件批量写入到存储或索引系统、或者被发送到另一个Flume Agent。
* Sink组件目的地包括hdfs、logger、avro、thrift、ipc、file、HBase、solr、自定义。

#### 2.4 Channel（缓冲区）

* Channel 是位于Source和Sink之间地缓冲区。因此，Channel允许Source和Sink运作在不同地速率上。Channel是线程安全的，可以同时处理几个Source地写入操作和几个Sink地读取操作。
* Flume自带三种Channel：Memory Channel和File Channel以及Kafka Channel
* Memory Channel是内存中队列。Memory Channel在不需要关心数据丢失地情况下适用。如果需要关心数据丢失，那么Memory Channel就不应该使用，因为程序死亡、机器宕机或者重启都会导致数据丢失。
* File Channel将所有事件写道磁盘。因此在程序关闭或机器宕机情况下不会丢失数据。

#### 2.5 Event

* 传输单元，Flume数据传输地基本单元，以Event地形式将数据从源头送至目的地。Event由**Header和Body**两部分组成，Header用来存放该event地一些属性，为K-V结构，Body用来存放该数据，形式为字节数组。

## 2、Flume 快速入门

### ①、Flume 安装部署

``` linux
> tar -zxvf apache-flume-1.7.0-bin.tar.gz -C /安装地址
# 将flume/conf下的flume-env.sh.template文件修改为flume-env.sh，并配置flume-env.sh文件
> vi flume-env.sh
export JAVA_HOME=/opt/module/jdk1.8.0_144
```

### ②、入门案例

案例：<https://github.com/ToolsOnKeys/BigData/blob/master/20Flume/02%E5%85%A5%E9%97%A8%E6%A1%88%E5%88%97.md>

* 监控端口数据官方案例
* 实时监控单个追加文件
* 实时监控目录下多个新文件
* 实时监控目录下的多个追加文件

## 3、FLume进阶

### ①、Flume事务

![](D:\BigData\BigData\20Flume\相关图片\Flume事务.png)

* 1、首先Web Server端提供数据到Source中，Source接收的事件数据。
* 2、Source将数据推送给Channel之前，会先将推送数据打包成Put推送事件
* =========================Put事务==========================
* 3、Put中首先doPut会先将数据写入到临时缓冲区putList
* 4、推送事件中doCommit会先检查channel内存队列是否足够合并。
* 5、如果channel内存队列空间不足，Put事务流程会先回滚数据
* ==========================Task事务=========================
* 6、Sink会通过doTask从channel中的数据提取到临时缓冲区takeList中，并将数据发送到HDFS中
* 7、takelist中数据通过doCommit发送数据到Sink，如果数据发送成功，则会清空临时缓冲区takelist中的数据
* 8、如果doCommit数据发送过程中出现异常，rollback将临时缓冲区takeList中的数据归还channel内存队列

* 9、最终Sink将数据转发到HDFS中

### ②、Flume Agent内部原理

![](D:\BigData\BigData\20Flume\相关图片\FlumeAgent内部原理.png)

**原理分析**

* source接收数据，之后向Channel Processor发送处理事件
* Channel Processor将处理事件传递给拦截器链，经过拦截器链处理之后，事件重新返回Channel Processor
* Channle Processor 将每个事件交给Channel选择器，Channel Selector将返回写入的Channel列表
* Channel Processor根据Channel选择器的的结果写入到相应的Channel。

**重要组件**

* ChannelSelector

  ChannelSelector的作用时选出Event将要被发完哪个Channel。其共有两种类型，分别是Replicating（复制）和Multiplexing（多路复用）。

  ReplicatingSelector（复制选择器）会将一个Event发完所有的Channel，Multiplexing（多路复用）会根据相应的原则，将不同的Event发完不同的Channel。

* SinkProcessor

  SinkProcessor共有三种类型：

  分别是DefaultSinkProcessor（单个Sink）、

  LoadBalancingSinkProcessor（可以实现负载均衡的功能）、

  FailoverSinkProcessor（对应的时Sink Group，可以实现故障转移的功能）

### ③、Flume 拓扑结构

#### 1、简单串联

![](D:\BigData\BigData\20Flume\相关图片\Flume拓扑结构-简单串联.png)

* 这种模式是将多个flume顺序连接起来了，从最初的source开始到最终sink传送的目的存储系统。此模式不建议桥接过多的flume数量，flume数量过多不仅会影响传输速率，而且一旦传输过程中某个节点flume宕机，会影响整个传输系统。

#### 2、复制和多路复用

![](D:\BigData\BigData\20Flume\相关图片\Flume拓扑结构-复制和多路复用.png)

* Flume 支持将事件流向一个或者多个目的地。这种模式可以将相同数据复制到多个channel中，或者将不同数据分发到不同的channel中，sink可以选择传送到不同的目的地。

#### 3、负载均衡和故障转移

![](D:\BigData\BigData\20Flume\相关图片\Flume拓扑结构-负载均衡和故障转移.png)

* Flume 支持使用将多个sink逻辑上分到一个sink组，sink组配合不同的SinkProcessor可以实现负载均衡和错误恢复的功能。

#### 4、聚合

![](D:\BigData\BigData\20Flume\相关图片\Flume拓扑结构-聚合.png)

* 这种模式时我们最常见的，也是非常使用，日常web应用通常分布在上百个服务器，大着甚至上千，上万个服务器。产生的日志，处理起来也非常麻烦。用flume的这种组合方式能很好的解决这一问题，每台服务器部署一个flume采集日志，传送到一个集中收集日志的flume，在由此flume上传到hdfs、hive、hbase等、进行日志分析。

### ④、Flume企业开发案例

#### 1、复制和多路复用

#### 2、负载均衡和故障转移

#### 3、聚合