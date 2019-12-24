# Flume相关总结

## 1、Flume相关总结

### 1.1 Flume组成，Put事务，Take事务

* Taridir Source：断电续传、多目录。Flume1.6以前需要自己自定义Source记录每次读取文件位置，实现断电续传
* File Channel：数据存储在磁盘，宕机数据可以保存。但是传输速率慢。适合对数据传输可靠性要求高的场景，比如，金融行业。
* Memory Channel：数据存储在内存中，宕机数据丢失。传输速率快。适合对数据传输可靠性要求不高的场景，比如，普通的日志数据。
* Kafka Channel：减少了Flume的Sink阶段，提高了传输效率。
* Source到Channel是Put事务。
* Channel到Sink是Take事务。

### 1.2 Flume 拦截器

#### ①、拦截器注意事项

* 项目中自定义了：ETL拦截器和区分类型拦截器。
* 采用两个拦截器的优缺点：优点（模块化开发和可移植性）；缺点（性能会低一些）

#### ②、自定义拦截器的步骤

* 实现Interceptor
* 重写四个方法
  * initialize初始化
  * public Event intercept(Event event)处理单个Event
  * public List<Event>intercept(List<Event> events)处理多个Event，在这个方法中调用Event intercept（Event event）
  * close 方法

## 1.3 Flume Channel选择器

* Channel Selectors，可以让不同的项目日志通过不同的Channel到不同的Sink中去。官方文档中Channel Selectors有两种类型：Replicating Channel Selector（default）和Multiplexing Channel Selector
* 这两种Selector的区别是：Replicating会将source过来的events发往所有channel，而Multiplexing可以选择该发往哪些Channel。

## 1.4 Flume 监控器

* Ganglia

## 1.5 Flume 采集数据会丢失吗？（防止数据丢失的机制）

* 不会，Channel可以存储在File中，数据传输自身有事务。

## 1.6 Flume 内存

* 开发中在flume-env.sh中设置JVM heap为4G活更高，部署在单独的服务器上（4核8线程16G内存）
* -Xmx和-Xms最好设置一致，减少内存抖动带来的性能影响，如果设置不一致容易导致频繁fullgc

## 1.7 FileChannel优化

* 通过配置dataDirs执行多个路径，每个路径对应不同的硬盘，增大Flume吞吐量。
* checkpointDir和backupCheckpointDir 也尽量配置在不同硬盘对应的目录中，保证checkpoint坏掉后，可以快速使用backupCheckpointDir恢复数据。

## 1.8 HDFS Sink 小文件处理

### ①、HDFS存入大量小文件，有什么影响

* 元数据层面：每个小文件都有一份元数据，其中包括文件路径，文件名，所有者，所属组，权限，创建时间等，这些信息都保存在Namenode内存中。所以小文件过多，会占用Namenode服务器大量内存，影响Namenode性能和使用寿命。
* 计算层面：默认情况下MR会对每个小文件启用一个Map任务计算，非常影响计算性能。同时也影响磁盘寻址时间。

### ②、HDFS小文件处理

* 官方默认的这三个参数配置写入HDFS后会产生小文件，hdfs.rollInterval=3600、hdfs.rollSize=134217728、hdfs.rollCount=0,hdfs.roundValue=3600、hdfs.roundUnit=second几个参数综合作用，效果如下：
  * tmp文件在达到128M时会滚动生成正式文件。
  * tmp文件创建超3600s时会滚动生成正式文件。