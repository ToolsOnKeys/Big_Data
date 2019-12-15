# Kylin

## 1、Kylin概述

### ①、Kylin定义

* Kylin是一个开源的分布式分析引擎，提供Hadoop/Spark之上的SQL查询接口及多维分析（OLAP）能力以支持超大规模数据，他能以亚秒内查询巨大的Hive表

### ②、Kylin特点

> Kylin的主要特点包括支持SQL接口、支持超大规模数据集、亚秒级响应、可伸缩性、高吞吐率、BI工具集成等。

* 标准SQL接口：Kylin是以标准的SQL作为对外服务的接口。

* 支持超大数据集：Kylin对于大数据的支撑能力可以是目前所有技术中最为领先的。

* 亚秒级响应：Kylin拥有有意的查询响应速度，这点得益于与计算，很多复杂的计算，比如连接、聚合，在离线的预计算过程中就已经完成，这大大降低了查询时刻所需的计算量，提高了响应速度。

* 可伸缩型和高吞吐率：单节点Kylin可实现每秒70个查询，还可以搭建Kyline的集群

* BI工具集成

  Kylin可以于现有的BI工具集成，具体包括如下内容。

  ODBC：与Tableau、Excel、PowerBI等工具集成

  JDBC：与Saiku、BIRT等java工具集成

  RestAPI：与JavaScript、Web网页集成

  Kylin开发团队还共享了Zepplin的插件，也可以使用Zepplin来访问Kylin服务。

### ③、Kylin架构

