# Presto

## 1、Presto简介

### ①、Presto概念

* Presto是一个开源的分布式SQL查询引擎，数据量支持GB到PB字节，主要用于处理妙计查询的场景。
* 注意：虽然Presto可以解析SQL，但他不是一个标准的数据库。不是MySQL、Oracle的代替品，也不能用来处理在线事务（OLTP）

### ②、Presto架构

![](D:\BigData\Big_Data\27Presto\相关图片\Presto架构.png)

* Presto由一个Coordinator和多个Worker组成
* 有客户端提交查询，从Presto命令行CLI提交到Coordinator
* Coordinator解析查询计划，然后把任务分发给Worker执行。
* Worker负责执行任务和处理数据
* Catalog标识数据员。一个Catelog包含Schema和Connector
* Connector是适配器，用于Presto和数据源（Hive、Redis）的连接，类似于JDBC
* Schema类似于Mysql中数据库，Table类似于Mysql中的表
* Coordinator是负责从Worker获取结果并返回最终结果给Client

### ③、Presto优缺点

* 优点：Presto基于内存运算，减少了硬盘IO，计算更快；能够连接多个数据源，跨数据员连表查，如从Hive查询大量网站访问记录，然后从Mysql中匹配出设备信息。
* 缺点：Presto能够处理PB级别的海量数据分析，当Presto并不是把PB级别数据都放在内存中计算的。而是根据场景，如Count，AVG等聚合运算，是边度数据便计算，再清除内存，再读数据再计算，这种方式消耗的内存并不高。但是连表查，就可能产生大量的临时数据，因此速度会变慢，反而Hive此时会更擅长。

### ④、Presto、Impala性能比较

* 他们的共同点就是吃内存，当然再内存充足的情况下，并且有规模恰当的集群，性能应该会更客观。总体来看，Impala性能稍微领先于presto，但是presto再数据源支持上非常丰富，包括hive、图数据库、传统关系型数据库、Redis等
* 缺点：这两种对于hbase支持的都不好，presto不支持，但是对hdfs、hive兼容性很好，起始这也是顺理成章的，所以数据源的处理非常重要，针对hbase的二级索引查询可以用phoenix，效果也不错。

## 2、Presto优化

### ①、数据存储优化

* 合理设置分区：于Hive类似，Presto会根据元数据信息读取分区数据，合理的分区能减少Presto数据读取连，提升查询性能
* 使用列式存储：Presto对ORC文件读取做了特定优化，因此再Hive中创建Presto使用的标识，建议采用ORC格式存储。相对于Parquest，Presto对ORC支持更好。
* 使用压缩：数据压缩可以减少节点间数据传输对IO带宽压力，对于即席查询需要快速解压，建议采用Snappy压缩

### ②、查询SQL优化

* 只选择使用的字段，减少使用*
* 过滤条件尽量加上分区字段
* Group by 语句优化，按照数据量的进行降序排序
* Order by 时可以尽量使用Limit
* 使用Join语句时间大表放在左边

## 3、注意事项

### ①、字段名引用

* 避免与关键字冲突，Presto对关键字段加双引号分隔

### ②、时间函数

* 对于Timestamp，需要进行比较的时候，需要添加Timestamp关键字，而MySQL中对Timestamp可以之间进行比较。

### ③、不支持insert overwrite语法

* Presto中不支持insert overwrite语法，所以只能先delete，然后insert into

### ④、parquet格式

* Presto目前支持Parquet格式，支持查询，但不支持insert



