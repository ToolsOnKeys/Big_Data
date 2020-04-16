# TDH服务的角色

## 1、Transwarp_License_Cluster角色

| 角色         | 描述                                        |
| ------------ | ------------------------------------------- |
| License Node | 提供License认证服务，判断许可证是否符合授权 |

## 2、TOS角色

| 角色         | 描述                  |
| ------------ | --------------------- |
| TOS Master   | 提供TOS服务的主节点   |
| TOS Slave    | 提供TOS服务的备用节点 |
| TOS Registry | Image管理工具         |

## 3、Zookeeper角色

| 角色             | 描述                                                         |
| ---------------- | ------------------------------------------------------------ |
| Zookeeper Server | 一个或多个运行Zookeeper服务的节点。Zookeeper服务是指包含一个或多个节点的集群提供服务框架用于集群管理。对于集群，Zookeeper服务提供的功能包括维护配置信息、命名、提供Hyperbase的分布式同步，以及当HMaser停止时触发master选择。Hyperbase需要有一个Zookeeper集群才能工作。推荐在Zookeeper集群中至少有三个节点 |

## 4、HDFS角色

| 角色     | 描述                                                         |
| -------- | ------------------------------------------------------------ |
| Httpfs   | 对HDFS提供Web UI服务。允许用户在可视化界面上对HDFS进行操作和管理。 |
| DataNode | 在HDFS中，DataNode是用来存储数据块的节点                     |

## 5、NameService角色

| 角色        | 描述                                                         |
| ----------- | ------------------------------------------------------------ |
| NameNode    | HDFS系统中的节点用于维护文件系统中所有文件的目录结构并跟踪文件数据存储于哪些数据节点。当客户端需要从HDFS文件系统中获得文件时，它通过和NameNode通讯来知道客户端哪个数据节点上有客户端需要的文件。 一个集群中只能有一个NameNode。NameNode不能被赋予其他角色。 |
| JournalNode | Standby NameNode和Active NameNode通过JournalNode通信，保持信息同步。 |

## 6、Yarn角色

| 角色            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| ResourceManager | Resource Manager负责将各个资源部分（计算、内存、带宽等）安排给基础 NodeManager，并与NodeManager一起启动和监视它们的基础应用程序。 |
| NodeManager     | Node Manager管理一个YARN集群中的每个节点。提供针对集群中每个节点的服务，从监督对一个容器的终生管理到监视资源和跟踪节点的状态。 |
| HistoryServer   | 应用状态监控平台。                                           |
| TimelineServer  | 提供对YARN的作业历史日志信息的展现服务。                     |

## 7、Search角色

| 角色         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| SearchServer | 提供内部搜索引擎服务                                         |
| SearchHead   | Search的网页版本的集群管理工具，支持数据的增删改查，并且可以通过语句进行可视化查询，是ES开发人员的一个非常有用的辅助工具 |

## 8、Hyperbase角色

| 角色            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| HyperbaseMaster | 节点实现Hyperbase数据库以下功能: 配区域给RegionServers，平衡整个集群。1.确认RegionServer在运行中。2.如果没找到参考值，删除parents。3.管理数据库表。4.在RegionServers中分发消息。5.监控RegionServers以决定是否有必要执行恢复，如果是，则执行恢复。可以分配HMaster角色给一个或多个节点以进行备份切换。如果你分配角色给多个节点，这将创建一个active-standby状态的HMaster节点的集群，即一个节点处于active状态而集群中的另一个节点处于standby状态。如果active状态的HMaster停止，Zookeeper集群将选出一个inactive状态的HMaster来作为active状态的HMaster。 |
| RegionServer    | 负责服务和管理Hyperbase区域的节点。                          |
| HyperbaseThrift | Thrift Client API开放的节点，客户端可通过Thrift和Hyperbase通讯。 |

## 9、TxSQL角色

| 角色  | 描述                                                  |
| ----- | ----------------------------------------------------- |
| TxSQL | 高可用的元数据存储，支持数据访问，同Metastore进行通信 |

## 10、Shiva角色

| 角色              | 描述                                                    |
| ----------------- | ------------------------------------------------------- |
| ShivaMaster       | 管理所有的Tablets、Tablet服务器以及其他集群相关的元数据 |
| ShivaWebserver    | web服务器                                               |
| ShivaTabletServer | 用于存储和管理Tablet                                    |

## 11、Inceptor角色

| 角色              | 描述                                                         |
| ----------------- | ------------------------------------------------------------ |
| InceptorServer    | Inceptor运行的节点，帮助查询和管理HDFS的大型数据集。InceptorServer提供工具从文件系统中提取、转换和装载数据，实施数据格式结构，以及提供HDFS或Hyperbase的支持文件访问 |
| InceptorMetaStore | 管理表结构及其元数据信息，从TxSQL中获取目标元数据。          |
| InceptorExecutor  | 批处理任务的执行节点                                         |

## 12、Pilot角色

| 角色            | 描述                                                         |
| --------------- | ------------------------------------------------------------ |
| PilotServer     | 轻量的在线报表工具、支持多种报表样式，提供丰富美观的报表展现 |
| FilerobotServer | 用于对接Pilot上对HDFS的操作                                  |

## 13、Kafka角色

| 角色        | 描述                |
| ----------- | ------------------- |
| KafkaServer | Kafka的任务管理工具 |

## 14、Codis角色

| 角色           | 描述                                                         |
| -------------- | ------------------------------------------------------------ |
| CodisDashboard | 集群管理工具，支持Codis Proxy和Codis Server进行添加、删除、同步等操作。当集群状态变化时，Codis Dashboard维护该集群里的所有Codis Proxy状态的一致性。 |
| Codis Proxy    | 客户端连接的Redis代理服务。                                  |
| Codis FE       | 集群管理的网络界面。                                         |
| Codis Sever    | Codis项目的Redis分支，添加了额外的数据结构，以支持slot相关的操作和数据迁移指令。 |

## 15、Notification角色	

| 角色               | 描述                                                         |
| ------------------ | ------------------------------------------------------------ |
| NotificationServer | 提供消息通知服务，此服务覆盖TDH中所有具有通知功能的工具，如Governor |

## 16、Governor角色

| 角色          | 描述                                                         |
| ------------- | ------------------------------------------------------------ |
| CovemorServer | 负责提供对元数据的访问、历史追踪、状态统计等功能，为用户提供数据治理的服务 |

### 17、KMS角色

| 角色      | 描述                 |
| --------- | -------------------- |
| KMSServer | Hadoop密钥管理服务器 |

## 18、Transporter角色

| 角色              | 描述                                        |
| ----------------- | ------------------------------------------- |
| TransporterServer | 提供Transporter ETL服务。实现数据的实时入库 |

## 19、Workflow角色

| 角色           | 描述                                                         |
| -------------- | ------------------------------------------------------------ |
| WorkflowServer | 提供可视化的工作流设计，以及对工作流的调制、调度，对工作流任务具有丰富的分析功能 |

## 20、Rubik

| 角色        | 描述                                    |
| ----------- | --------------------------------------- |
| RubikServer | 提供对OLAP Cube的设计、创建、实例化服务 |

## 21、HBase角色

| 角色         | 描述                                                         |
| ------------ | ------------------------------------------------------------ |
| HBaseMaster  | 节点实现HBase数据库以下功能: 配区域给RegionServers，平衡整个集群。1.确认RegionServer在运行中。2.如果没找到参考值，删除parents。3.管理数据库表。4.在RegionServers中分发消息。5.监控RegionServers以决定是否有必要执行恢复，如果是，则执行恢复。可以分配HMaster角色给一个或多个节点以进行备份切换。如果你分配角色给多个节点，这将创建一个active-standby状态的HMaster节点的集群，即一个节点处于active状态而集群中的另一个节点处于standby状态。如果active状态的HMaster停止，Zookeeper集群将选出一个inactive状态的HMaster来作为active状态的HMaster。 |
| RegionServer | 负责服务和管理HBase区域的节点。                              |
| HBaseThrift  | Thrift Client API开放的节点，客户端可通过Thrift和HBase通讯。 |

