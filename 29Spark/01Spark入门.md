# Spark入门

## 1、Spark概述

### ①、什么是Spark

* Hadoop主要解决，海量数据的存储和海量数据的分析计算
* Spark是一种基于内存的快速、通用、可扩展的大数据分析计算引擎

### ②、Hadoop和Spark框架对比

* Hadoop MR框架：从数据源获取数据，经过分析计算后，将结果输出到指定的位置，核心是一次计算，不适合迭代式计算
* Spark框架：支持迭代式计算，图形计算
* Spark框架计算比MR快的原因是：中间过程只要无Shuffle过程，就不需要落盘，基本过程都在内存中完成
* 算子：就是姜文的初始化状态通过操作进行转换为中间状态，然后通过操作转换为完成状态
* 中间转换：operator

### ③、Spark内置模块

* Spark Core：实现了spark的基本功能，包含任务调度、内存管理、错误恢复、与存储系统交互等模块。Spark Core中还包含了对弹性分布式数据集（RDD）的API定义
* Spark SQL：机构化数据，是Spark用来操作数据结构话数据的程序包。通过Spark SQL，我们可以使用SQL或者Apache Hive版本的HQL来查询数据。Spark SQL支持多种数据源，比如Hive表、Parquet以及JSON
* Spark Streaming：实时计算，是Spark提供的对事实数据进行流式计算的组件。提供关了用来操作数据流的API，并且与Spark Core的RDD API高度对应。
* Spark Mlib：机器学习，提供拆跟你讲的机器学习功能的次序库。包括分类、回归、聚类、协同过滤等，还提供了模型评估、数据导入等额外的支持功能
* Spark GraghX：图计算，主要用于图形并行计算和图挖掘系统的组件
* 集群管理器：Spark设计为可以高效的再一个计算节点到数千个计算节点之间伸缩计算。为了实现这样的要求，同时获得最大的灵活性，Spark支持再各种集群管理器上运行，包括Hadoop Yarn、Apache Mesos，以及Spark自带的一个建议调度器、叫做独立调度器。

### ④、Spark特点

* 快：与Hadoop 的 MR程序相比，Spark基于内存的运算要快100倍以上，基于硬盘的运算也要快10倍以上。Spark实现了高效的DAG执行引擎，可以同故宫基于内存来高效处理数据流。计算的中间结果是存在与内存中的。
* 易用：Spark支持Java、Python和Scala的API，还支持超过80中高级算法，使用户可以快速的构建不同的应用。而且Spark支持交互式的Python和Scala的Shell，可以非常方便的在这些Shell中使用Spark集群来验证解决问题的方法
* 通用：Spark提供了统一的解决方案。Spark可以用于 交互式查询（Spark SQL）、实时流处理（Spark Streaming）、机器学习（Spark MLib）和图计算（GraphX）。这些不同类型的处理都可以在同一个应用中无缝使用。减少了开发和维护的人力成本和部署平台的物力成本
* 兼容性：Spark可以非常方便的与其他的开源产品进行融合。比如，Spark可以使用Hadoop的YARN和Apache Mesos作为他的资源管理和调度器，并且可以处理所有Hadoop支持的数据，包括HDFS、HBase等。这对于已经部署Hadoop集群的用户特别重要，因为不需要做任何数据迁移就可以使用Spark的强大处理能力。

## 2、Spark的运行模式

> 部署Spark集群答题上分为两种模式：单机模式与集群模式

> 大多数分布式框架都支持单机模式，方便开发者调试框架的运行环境。但是在生产环境中，并不会使用单机模式。

### ①Spark目前支持的部署模式

* Local模式：在本地部署单个Spark服务，调度器默认为FIFO
* Standalone模式：Spark自带的任务调度模式（国内常用）
  * 配置历史服务器
* Yarn模式：Spark使用Hadoop的Yarn组件进行资源与人的调度（国内常用）
* Mesos模式：Spark使用Mesos平台进行资源与任务的调度

### ②、集群角色

#### Ⅰ、Master和Worker集群资源管理

* Master：Spark特有的资源调度系统的Leader。掌管着整个集群的资源信息，类似于Yarn框架中的ResourceManager
* Worker：Spark特有的资源调度系统的slave，有多个。每个Slave掌握着所在节点的所有资源信息

#### Ⅱ、Driver和Executor任务的管理者

* Spark Shell：执行程序发起

* Driver：

  ```
  Spark Shell中预加载的一个叫做sc的Spark Context对象
  1、把用户程序转为作业（Job）
  2、跟踪Executor的任务运行状况
  3、为执行器节点调度任务
  4、UI展示应用的运行状况
  ```

* Executor

  ```
  负责执行spark的具体任务
  ```

#### Ⅲ、总结：

* Master和Worker是Spark集群的资源管理者。Driver和Executor是某一个程序执行的管理者

### ③、Spark的三种模式的详细运行过程

#### Ⅰ、Standalone模式

* 1、使用SparkSubmit提交任务的时候（包括IDE工具中使用new SparkConf()来运行任务的时候），Drive运行在Client；使用SparkShell提交的任务的时候，Driver是运行在Master上
* 2、使用SparkSubmit提交任务的时候，使用给本地的Client的main函数来创建sparkcontext并初始化它；
* 3、SparkContext连接到Master，注册并申请资源（内核和内存）
* 4、Master根据SC提出的申请，根据worker的心跳报告，来决定到底在哪个worker上启动executor
* 5、executor向SC注册
* 6、SC将应用分配给executor
* 7、SC解析应用，创建DAG图，提交给DAGScheduler进行分解成stage（当触发action操作的时候，就会产生job，每个job中包含一个或者多个stage，stage一般在获取外部数据或者shuffle之前产生）。然后stag（又称为Task Set）倍发送到TaskScheduler。TaskScheduler负责将stage中的stage中的task分配到相应的worker上，并由executor来执行
* 8、executor创建Executor线程池，开始执行task，并向SC汇报
* 9、所有的task执行完成之后，SC向Master注销

#### Ⅱ、yarn client

* 1、spark-submit脚本提交，Driver在客户端本地运行
* 2、Client向RM申请启动AM，同时在SC（client上）中创建DAGScheduler和TaskScheduler。
* 3、RM收到请求之后，查询NM并选择其中一个，分配container，并在container中开启AM
* 4、client的SC初始化完成之后，与AM进行通信，向RM注册，根据任务信息向RM申请资源
* 5、AM申请资源后，与AM进行通信，要求在它申请的container中开启executor，Executor在启动之后会向SC注册并申请task
* 6、SC分配task给executor，executor执行任务并向Driver（运行在client之上）汇报，以便客户端可以随时监控任务的运行状态
* 7、任务运行完成之后，client的SC向RM注销自己并关闭自己

#### Ⅲ、yarn cluster

* 1、spark-submit脚本提交，向yarn（RM）中提交ApplicationMaster程序、AM启动命令和需要在Executor中运行的程序等
* 2、RM收到请求之后，选择一个NM，在其上启动过一个container，在container中开启AM，并在AM中完成SC的初始化
* 3、SC向RM注册并请求资源，这样用户可以在RM中查看任务的运行情况。RM根据请求采用轮询的方式和RPC协议向个NM申请资源并监控任务的运行状况知道结束
* 4、AM申请到资源之后，与对应的NM进行通信，要求在其上获取到的Container中开启executor，executor开启之后，向AM中的SC注册并申请task
* 5、AM中的SC分配task给executor，executor运行task并向AM中的SC汇报自己的状态和进度
* 6、应用程序完成之后（各个task完成之后）AM向RM申请注销自己并关闭自己

### ④、几种模式的对比

| 模式       | Spark安装机器数 | 需启动的进程   | 所属者 | 应用场景 |
| ---------- | --------------- | -------------- | ------ | -------- |
| Local      | 1               | 无             | Spark  | 测试     |
| Standalone | 3               | Master及Worker | Spark  | 单独部署 |
| Yarn       | 1               | Yarn及HDFS     | Hadoop | 混合部署 |

### ⑤、端口号总结

1）Spark历史服务器端口号：18080         （类比于Hadoop历史服务器端口号：19888）

2）Spark Master Web端口号：8080（资源）（类比于Hadoop的NameNode Web端口号：50070）

3）Spark Master内部通信服务端口号：7077    （类比于Hadoop的9000端口）

4）Spark查看当前Spark-shell运行任务情况端口号：4040

5）Hadoop YARN任务运行情况查看端口号：8088