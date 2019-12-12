# HBase

## 1、HBase 简介

### ①、HBase 定义

* HBase是一种分布式、可扩展、支持海量数据存储的NoSQL数据库

### ②、HBase数据模型

* 逻辑上，HBase的数据模型同关系数据库类似，数据存储在一张表中，有行有列。但从HBase的底层物理存储结构（K-V）来看，HBase更像一个multi-dimensional map。
* Name Space：命名空间，类似于关系型数据库的DatabBase概念，每个命名空间下有多个表。HBase有两个自带的命名空间，分别是“hbase”和“default”，“hbase”中存放的是HBase内置的表，“default”表是用户默认使用的命名空间。
* Region：类似于关系性数据库的表概念。不同的是，HBase定义表示只需要声明列族即可，不需要声明具体的列。这意味着，玩HBase写入数据时，字段可以动态、按需指定。因此，和关系性数据库相比，HBase能够轻松应对字段变更的场景。
* Row：HBase表中的每行数据都有一个RowKey和多个Column（列）组成，数据是按照RowKey的字典顺序存储的，并且查询数据时只能根据RowKey进行检索，所以RowKey的设计十分重要。
* Column：HBase中的每个列都由Column Family（列族）和Column Qualifier（列限定符）进行限定。坚表示，只需要指明列族，而列限定符无序预先定义。
* Time Stamp：用于表示数据的不同版本，每条数据写入时，如果不指定时间戳，系统会自动为其加上该字段，其值为写入HBase的时间。
* Cell：由{rowkey,column Family:coolumn Qualifier,time Stamp}唯一确定的单元。cell中的数据是没有类型的，全部是字节码形式存储。

### ③、HBase基本架构

![](D:\BigData\BigData\23HBase\相关图片\HBase架构(不完整版).png)

* Region Server

  Region的管理者，其实想类为HRegionServer。

  主要作用如下：

  对于数据的操作（get、put、delete）；

  对Region的操作（splitRegion、compactRegion）。

* Master：

  指所有Region Server的管理者，其实现类为HMaster。

  主要作用如下：

  对于表的操作（create、delete、alter），

  对于RegionServer的操作（分配regions到每个RegionServer，监控每个RegionServer的状态，负载均衡和故障转移）

* Zookeeper：

  HBase通过Zookeeper来做Master的高可用、

  RegionServer的监控、元数据的入口以及集群配置的维护等工作。

* HDFS：

  为HBase提供最终的一个底层数据存储服务，同时为HBase提供高可用的支持。

## 2、HBase的安装部署

* 查看HBase页面

  [http://hadoop102:16010](http://linux01:16010)

## 3、HBash Shell 操作

### ①、基础操作

```linux
# 1、进入HBase客户端命令
> bin/hbase shell
# 2、查看帮助命令
> help
# 3、查看当前数据库中的表
> list
```

### ②、表的操作

```linux
# 1、创建表
> create 'table_name','clumn_info'
eg: create 'dh','info'
# 2、插入数据到表
> put 'table_name','key','clumn_info:clumn','clumn_value'
eg: put 'dh','001','info:name','dinghao'
# 3、扫描查看表的数据
> scan 'dh'
> scan 'dh',{STARTROW => '001',STOPROW => '003'}
> scan 'dh',{STARTROW => '001'}
# 4、查看表结构
> describle 'dh'
# 5、更新指定字段的数据
> put 'dh','001','info:name','zyh'
# 6、查看“指定行”或“指定列族：列”的数据
> get 'dh','001'
> get 'dh','001','info:name'
# 7、统计表数据行数
> count 'dh'
# 8、删除数据
> deleteall 'dh','001'
> delete 'dh','001','info:name'
# 9、清空表数据
> truncate 'dh'
# 10、删除表(需要向disable才能drop)
> disable 'dh'
> drop 'dh'
# 11、变更表信息
> alter 'student',{NAME=>'info',VERSIONS=>3}  #将info列族中的数据存放三个版本
```

## 4、HBase 进阶

### ①、架构原理

![](D:\BigData\BigData\23HBase\相关图片\HBase详细架构图.png)

>个人理解：HMaster类实现管理HRegion中的元信息，HRegion对应一个表数据，HRegion中的Store对应每一个列族即一个列。当表数据达到一定程度，会横向切分成两个HRegionServer单元

* StoreFile

  ​       保存实际数据的物理文件，StoreFile以HFile的形式存储在HDFS上。每个Store会有一个或多个StoreFile（HFile），数据在每个StoreFile中都是有序的。

* MemStore

  ​        写缓存，由于HFile中的数据要求是有序的，所以数据是先存储在MemStore中，排好序后，等到达刷写实际才会刷鞋到HFile，每次刷鞋都会形成一个新的HFile。

* WAL

  由于数据要经过MemStore排序后才能刷写到HFile，但把数据保存在内存中会有很高的概率导致数据丢失，为了解决这个问题，数据会先写到一个叫做Write-Ahead logfile的文件中，然后再写入MemStore中。所以在系统出现故障的时候，数据可以通过这个日志文件重建。

### ②、写流程

![](D:\BigData\BigData\23HBase\相关图片\HBase写流程.png)

* 1、Client先访问zookeeper，获取hbase：meta表位于哪个RegionServer。
* 2、访问对应的RegionServer,获取hbase：meta表，根据读请求的namespace:table/rowkey，查询出目标数据位于哪个RegionServer中的哪个Region中。并将该table的region信息以及meta表的位置信息缓存在客户端的meta cache，方便下次访问。
* 3、与目标RegionServer进行通讯
* 4、将数据顺序写入（追加）到WAL
* 5、将数据写入队形的MemStore，数据会在MemStore进行排序
* 6、向客户端发送ack
* 7、等达到MemStore的刷鞋时机后，将数据刷鞋到HFile

### ③、MemStore Flush

![](D:\BigData\BigData\23HBase\相关图片\MemStoreFlush.png)

* MemStore刷写时机

  * 1、当某个memstore（列族）的大小达到 **hbase.hregion.memstore.flush.size（默认值128M）**，其所在region的所有memstore都会刷写。

    当memstore的大小达到了

    **hbase.hregion.memstore.flush.size**（默认值128M）

    ***hbase.hregion.memstore.block.multiplier**（默认值4）

    时，会阻止继续往该memstore写数据。

    > 即当单个memstore大小达到128M会触发刷写，同时如果memstore的总大小达到128M*4会先阻止写数据

  * 2、当region server中memstore的总大小达到

    **java_heapsize***

    ***hbase.regionserver.global.memstore.size**（默认值0.4）

    ***hbase.regionserver.global.memstore.size.lower.limit**（默认值0.95），

    region会按照其所有memstore的大小顺序（由大到小）依次进行刷写。直到region server中所有memstore的总大小减小到上述值以下。

    当region server中memstore的总大小达到**java_heapsize\*hbase.regionserver.global.memstore.size**（默认值0.4）

    时，会阻止继续往所有的memstore写数据。

    > 即memstore的总大小达到虚拟机内存的38%时会触发刷写，同时如果memstore的总大小为虚拟机内存的40%时，阻止往memstore中写数据

  * 3、到达自动刷写的时间，也会触发memstore flush。自动刷新的时间间隔由该属性进行配置**hbase.regionserver.optionalcacheflushinterval（默认1小时）**

    > 即默认一个小时刷写一次

  * 4、当WAL文件的数量超过**hbase.regionserver.max.logs**，region会按照时间顺序依次进行刷写，直到WAL文件数量减小到**hbase.regionserver.max.log**以下（该属性名已经废弃，现无需手动设置，最大值为32）

    > 该属性已经去除。

### ④、读流程

![](D:\BigData\BigData\23HBase\相关图片\HBase读流程.png)

* 1、Client先访问zookeeper，获取hbase：meta表位于哪个Region Server
* 2、访问对应的Region Server，获取hbase：meta表，根据读请求namespace：table/rowkey，查询出目标数据位于哪个Region Server中的哪个Region中。并将该table的region信息以及meta表的位置信息缓存在客户端的meta cache，方便下次访问。
* 3、与目标Region Server进行通讯
* 4、分别在Block Cache（读缓存），MemStore和Store File（HFile）中查询目标数据，并将查到的所有数据进行合并。此处所有数据是指同一条数据的不同版本（time stamp）或者不同的类型（Put/Delete）
* 5、将从文件查询到的数据块（Block，HFile数据存储单元，默认大小为64KB）缓存到Block Cache。
* 6、将合并的最终结果返回给客户端。

### ⑤、StoreFile Compaction

* 由于memstore每次刷写都会生成一个新的HFile，且同一个字段的不同版本和不同类型有可能会分布在不同的HFile中，因此查询时需要遍历所有的HFile。为了减少HFile的个数，以及清理掉过期和删除的数据，会进行StoreFile Compaction。
* Compaction分为两种，分别是Minor Compaction和Major Compaction。Minor Compaction会将临近的若干个较小的HFile合并成一个较大的HFile，但不会清理过期和删除的数据。Major Compaction会将一个Store下的所有的HFile合并成一个大的HFile，并且会清理掉过期和删除数据。

### ⑥、Region Split

* 默认情况下，每个Table起初只要有一个Region，随着数据的不断写入，Region会自动进行拆分。刚拆分时，两个子Region都位于当前的Region Server，但出于负载均衡的考虑，HMaster有可能会将给某个Region转移给其他的Region Server。
* Region Split时机：
  * 1.当1个region中的某个Store下所有StoreFile的总大小超过hbase.hregion.max.filesize，该Region就会进行拆分（0.94版本之前）。
  * 2.当1个region中的某个Store下所有StoreFile的总大小超过Min(R^2 * "hbase.hregion.memstore.flush.size",hbase.hregion.max.filesize")，该Region就会进行拆分，其中R为当前Region Server中属于该Table的个数（0.94版本之后）。

## 5、HBase API 增删改查 代码案例

## 6、HBase-MapReduce 代码案例

