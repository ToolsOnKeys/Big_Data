# HA高可用

## 1、HA概述

### ①、HA，即高可用（7*24小时不中断服务）

### ②、实现高可用最关键的策略是消除单点故障。

* HA严格来说应该分成各个组件的HA机制：HDFS的HA和Yarn的HA。

### ③、在Hadoop2.0之前，在HDFS集群中NameNode存在单点故障问题（SPOF）

### ④、NameNode主要在以下两个方面影响HDFS集群。

* NameNode机器发生意外，如宕机，集群将无法使用，直到管理员重启。
* NameNode机器需要升级，包括软件、硬件升级，此时集群也将无法使用。

### ⑤、总结

* HDFS HA功能通过Active/Standby两个NameNode是实现在集群中对NameNode的热备来解决上述问题。如果出现故障，如机器崩溃或机器需要升级维护，这是可通过此种方式将NameNode很快的切换到另外一台机器。

## 2、工作机制

配置两个NameNode，通过双NameNode消除单点故障。

## 3、HDFS-HA手动故障转移

### ①、工作要点

* 1、元数据管理方式需要改变

  *  内存中各自保存一份元数据；
  * Edits日志只有Active状态的NameNode节点可以做写操作
  * 两个NameNode都可以读写Edits
  * 共享的Edits放在一个共享存储种管理（qjournal和NFS两个主流的实现）

* 2、必须保证两个NameNode之间能够ssh无密码登陆

* 3、隔离（Fence），即同一时刻仅只有一个NameNode对外提供服务

* 4、自动故障转移需要一个状态管理功能模块

  实现了一个ZKfailover，常驻在每一个NameNode所在的节点，每一个ZKfailover负责监控自己所在NameNode节点，利用zk进行状态标识，当需要进行状态切换时，由ZKfailover来负责切换，切换时需要防止brain split现象的发生。

### ②、环境准备

* 1）修改IP

* 2）修改主机名及主机名和IP地址的映射

* 3）关闭防火墙

* 4）ssh免密登录

* 5）安装JDK，配置环境变量等

### ③、规划集群

| hadoop102   | hadoop103       | hadoop104   |
| ----------- | --------------- | ----------- |
| NameNode    | NameNode        |             |
| JournalNode | JournalNode     | JournalNode |
| DataNode    | DataNode        | DataNode    |
| NodeManager | NodeManager     | NodeManager |
|             | ResourceManager |             |

### ④、配置HDFS-HA集群

* 1、官方地址

  <http://hadoop.apache.org/>

* 2、在/opt/module/目录下创建一个HA文件夹

* 3、将/opt/module/下的 hadoop-2.7.2拷贝到/opt/module/HA目录下

* 4、配置core-site.xml

  ```core-site.xml
  <configuration>
  <!-- 把两个NameNode）的地址组装成一个集群mycluster -->
  	<property>
  		<name>fs.defaultFS</name>
          <value>hdfs://mycluster</value>
  	</property>
  	<!-- 声明journalnode服务本地文件系统存储目录-->
  	<property>
  		<name>dfs.journalnode.edits.dir</name>
  		<value>/opt/module/HA/hadoop-2.7.2/data/jn</value>
  	</property>
  	<!-- 指定hadoop运行时产生文件的存储目录 -->
  	<property>
  		<name>hadoop.tmp.dir</name>
  		<value>/opt/module/HA/hadoop-2.7.2/data/tmp</value>
  	</property>
  </configuration>
  ```

* 5、配置hdfs-site.xml

  ```hdfs-site.xml
  <configuration>
  	<!-- 完全分布式集群名称 -->
  	<property>
  		<name>dfs.nameservices</name>
  		<value>mycluster</value>
  	</property>
  
  	<!-- 集群中NameNode节点都有哪些 -->
  	<property>
  		<name>dfs.ha.namenodes.mycluster</name>
  		<value>nn1,nn2</value>
  	</property>
  
  	<!-- nn1的RPC通信地址 -->
  	<property>
  		<name>dfs.namenode.rpc-address.mycluster.nn1</name>
  		<value>hadoop102:8020</value>
  	</property>
  
  	<!-- nn2的RPC通信地址 -->
  	<property>
  		<name>dfs.namenode.rpc-address.mycluster.nn2</name>
  		<value>hadoop103:8020</value>
  	</property>
  
  	<!-- nn1的http通信地址 -->
  	<property>
  		<name>dfs.namenode.http-address.mycluster.nn1</name>
  		<value>hadoop102:50070</value>
  	</property>
  
  	<!-- nn2的http通信地址 -->
  	<property>
  		<name>dfs.namenode.http-address.mycluster.nn2</name>
  		<value>hadoop103:50070</value>
  	</property>
  
  	<!-- 指定NameNode元数据在JournalNode上的存放位置 -->
  	<property>
  		<name>dfs.namenode.shared.edits.dir</name>
  <value>qjournal://hadoop102:8485;hadoop103:8485;hadoop104:8485/mycluster</value>
  	</property>
  
  	<!-- 配置隔离机制，即同一时刻只能有一台服务器对外响应 -->
  	<property>
  		<name>dfs.ha.fencing.methods</name>
  		<value>sshfence</value>
  	</property>
  
  	<!-- 使用隔离机制时需要ssh无秘钥登录-->
  	<property>
  		<name>dfs.ha.fencing.ssh.private-key-files</name>
  		<value>/home/atguigu/.ssh/id_rsa</value>
  	</property>
  
  	<!-- 关闭权限检查-->
  	<property>
  		<name>dfs.permissions.enable</name>
  		<value>false</value>
  	</property>
  
  	<!-- 访问代理类：client，mycluster，active配置失败自动切换实现方式-->
  	<property>
    		<name>dfs.client.failover.proxy.provider.mycluster</name>
  <value>org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider</value>
  	</property>
  </configuration>
  
  ```

* 6、拷贝配置好的hadoop环境到其他节点。

### ⑤、启动HDFS-HA集群

```linux
#1、在所有JournalNode节点上，输入以下命令启动JournalNode服务
> sbin/hadoop-daemon.sh start journalnode
#2、在【nn1】上，对其进行格式化，并启动
> bin/hdfs namenode -format
> sbin/hadoop-daemon.sh start namenode
#3、在【nn2】上，同步nn1的元数据内容
> bin/hdfs namenode -bootstrapStandby
#4、启动【nn2】
> sbin/hadoop-daemon.sh start namenode
#5、在【nn1】上，启动所有的DataNode
> sbin/hadoop-daemons.sh start datanode
#6、将【nn1】切换为active
> bin/hdfs haadmin -transitionToActive nn1
```

## 4、HDFS-HA自动故障转移

### ①、工作要点

* 使用命令hdfs haadmin -failover手动进行故障转移，在该模式下，即使现役NameNode已经失效，系统也不会自动从现役NameNode转移到待机NameNode。
* 自动故障转移为HDFS部署增加了两个新组件：ZooKeeper和ZKFailoverController（ZKFC）进程。
  * 1、ZooKeeper是维护少量协调数据，通知客户端这些数据的改变和监视客户端故障的高可用服务。HA的自动故障转移依赖于ZooKeeper的以下功能：
    * 故障检测：集群中的每个NameNode在ZooKeeper种维护一个持久会话，如果机器崩溃，ZooKeeper中的会话将终止，ZooKeeper通知另一个NameNode需要触发故障转移。
    * 嫌疑NameNode选择：ZooKeeper提供了一个简单的机制用于唯一的选择一个节点为active状态。如果目前嫌疑NameNode崩溃，另一个节点可能从ZooKeeper获得特殊的排外锁以表名它应该称为现役NameNode。
  * 2、ZKFC是自动故障转移种的另一个新组件，是ZooKeeper的客户端，也将是和管理NameNode的状态。每个运行NameNode的主机也运行了一个ZKFC进程，ZKFC负责：
    * 健康检测：ZKFC使用一个健康检查命令定期的ping与之在同一个主机的NameNode，只要该NameNode及时的恢复健康状态，ZKFC就认为该节点是健康的。如果该节点崩溃，冻结或进入不健康状态，健康检查其表示该节点为非健康。
    * ZooKeeper会话管理：当本地NameNode是健康的，ZKFC保持一个ZooKeeper中打开的会话。如果本地NameNode处于active状态，ZKFC也保持一个特殊的znode锁，该锁使用了ZooKeeper对短暂节点的支持，如果会话终止，所节点将自动删除。
    * 基于ZooKeeper的选择：如果本地NameNode是健康的，且ZKFC发现没有其他的节点当前持有znode锁，它件为自己获取该锁。如果成功，则他已经赢得了选择，并负责运行故障转移进程以使他的本地NameNode为active。故障转移进程与前面描述的手动故障转移相似，首先如果必要保护之前的现役NameNode，然后本地NameNode转换为Active状态。

### ②、规划集群

|  hadoop102  |    hadoop103    |  hadoop104  |
| :---------: | :-------------: | :---------: |
|  NameNode   |    NameNode     |             |
| JournalNode |   JournalNode   | JournalNode |
|  DataNode   |    DataNode     |  DataNode   |
|     ZK      |       ZK        |     ZK      |
| NodeManager |   NodeManager   | NodeManager |
|             | ResourceManager |             |

### ③、配置ZooKeeper集群

* 1、解压安装

  ```linux
  #1、解压zookeeper安装包到/opt/module/目录下
  > tar -zxvf zookeeper-3.4.10.tar.gz -C /opt/module/
  #2、在/opt/module/zookeeper-3.4.10/目录创建zkData文件夹
  > mkdir -p zkData
  #3、重命名/opt/module/zookeeper-3.4.10/conf这个目录下的zoo_sample.cfg为zoo.cfg
  > mv zoo_sample.cfg zoo.cfg
  ```

* 2、配置zoo.cfg

  ```linux
  > vim zoo.cfg
  dataDir=/opt/module/zookeeper-3.4.10/zkData
  #######################cluster##########################
  server.2=hadoop102:2888:3888
  server.3=hadoop103:2888:3888
  server.4=hadoop104:2888:3888
  ```

  * 配置详解

    * Server.A=B:C:D。

      A是一个数字，表示这个是第几号服务器；

    ​       B是这个服务器的ip地址；

    ​       C是这个服务器与集群中的Leader服务器交换信息的端口；

    ​       D是万一集群中的Leader服务器挂了，需要一个端口来重新进行选举，选出一个新的Leader，而这个端口就是用来执行选举时服务器相互通信的端口。

    * 集群模式下配置一个文件myid，这个文件在dataDir目录下，这个文件里面有一个数据就是A的值，Zookeeper启动时读取此文件，拿到里面的数据与zoo.cfg里面的配置信息比较从而判断到底是哪个server。

* 3、集群操作

  ```linux
  #1、在/opt/module/zookeeper-3.4.10/zkData目录下创建一个myid的文件
  > touch myid
  #2、编辑myid文件
  > vim myid
  2
  #3、拷贝配置好的zookeeper到其他机器上
  > xsync /opt/module/zookeeper-3.4.10
  #4、编写一个脚本，负责群起ZooKeeper、关闭ZooKeeper和查看ZooKeeper状态
  
  #!/bin/bash
  case $1 in
  "start"){
  for i in hadoop101 hadoop102 hadoop103
  do
   ssh $i /opt/module/zookeeper-3.4.10/bin/zkServer.sh start
  done
  };;
  "stop"){
  for i in hadoop101 hadoop102 hadoop103
  do
   ssh $i /opt/module/zookeeper-3.4.10/bin/zkServer.sh stop
  done
  };;
  "status"){
  for i in hadoop101 hadoop102 hadoop103
  do
   ssh $i /opt/module/zookeeper-3.4.10/bin/zkServer.sh status
  done
  };;
  esac
  
  #5、启动ZooKeeper
  > zk.sh start
  #6、查看状态
  > zk.sh status
  ```

### ④-1、配置HDFS-HA自动故障转移

* 具体配置

  * 1、在hdfs-site.xml中增加

    ```xml
    <property>
    	<name>dfs.ha.automatic-failover.enabled</name>
    	<value>true</value>
    </property>
    ```

  * 2、在core-site.xml中增加

    ```xml
    <property>
    	<name>ha.zookeeper.quorum</name>
    	<value>hadoop102:2181,hadoop103:2181,hadoop104:2181</value>
    </property>
    ```

* 启动（在手动故障转移的基础上修改）

  ```linux
  #1、关闭所有的HDFS服务
  > sbin/stop-dfs.sh
  #2、启动ZooKeeper
  > zk.sh start
  #3、初始化HA在ZooKeeper中状态
  > bin/hdfs zkfc -formatZK
  #4、启动HDFS服务（先启动JN，在启动NN以及DN）
  > sbin/hadoop-daemons.sh start journalnode
  > sbin/hadoop-daemons.sh start namenode
  > sbin/hadoop-daemons.sh start datanode
  #5、启动DFSZKFailoverController，先在那个台机器启动，哪台NameNode就是Active
  > sbin/hadoop-daemons.sh start zkfc
  ```


### ④-2、直接进行自动故障转移

仅仅在第一次启动的时候做如下操作即可

```linux
#1、在各个JournalNode节点上，输入以下命令启动JournalNode服务
> sbin/hadoop-daemons.sh start JournalNode
#2、在【nn1】上，对其进行格式化，并启动
> sbin/hdfs namenode -format
> sbin/hadoop-daemon.sh start namenode
#3、在【nn2】上，同步nn1的元数据信息
> bin/hdfs namenode -bootstrapStandby
#4、启动【nn2】
> sbin/hadoop-daemon.sh start namenode
#5、启动ZooKeeper集群：
> zk.sh start
#6、初始化ZooKeeper中状态
> sbin/hdfs zkfc -formatZK
#7、启动DFSZKFailoverController，先在哪台机器上启动，哪台机器的NameNode就是Active
> sbin/hadoop-daemons.sh start zkfc
#8、启动DataNode
> sbin/hadoop-daemons.sh start datanode
```

## 5、YARN-HA配置

### ①、YARN-HA工作机制

* 官方文档：

  <http://hadoop.apache.org/docs/r2.7.2/hadoop-yarn/hadoop-yarn-site/ResourceManagerHA.html>

### ②、配置YARN-HA集群

* 1、环境准备

  （1）修改IP

  （2）修改主机名及主机名和IP地址的映射

  （3）关闭防火墙

  （4）ssh免密登录

  （5）安装JDK，配置环境变量等

  （6）配置Zookeeper集群

* 2、规划集群

  |  hadoop102  |    hadoop103    |    hadoop104    |
  | :---------: | :-------------: | :-------------: |
  |  NameNode   |    NameNode     |                 |
  | JournalNode |   JournalNode   |   JournalNode   |
  |  DataNode   |    DataNode     |    DataNode     |
  |     ZK      |       ZK        |       ZK        |
  |             | ResourceManager | ResourceManager |
  | NodeManager |   NodeManager   |   NodeManager   |

* 3、具体配置

  * yarn-site.xml

    ```xml
    <configuration>
        <property>
            <name>yarn.nodemanager.aux-services</name>
            <value>mapreduce_shuffle</value>
        </property>
    
        <!--启用resourcemanager ha-->
        <property>
            <name>yarn.resourcemanager.ha.enabled</name>
            <value>true</value>
        </property>
     
        <!--声明两台resourcemanager的地址-->
        <property>
            <name>yarn.resourcemanager.cluster-id</name>
            <value>cluster-yarn1</value>
        </property>
    
        <property>
            <name>yarn.resourcemanager.ha.rm-ids</name>
            <value>rm1,rm2</value>
        </property>
    
        <property>
            <name>yarn.resourcemanager.hostname.rm1</name>
            <value>hadoop103</value>
        </property>
    
        <property>
            <name>yarn.resourcemanager.hostname.rm2</name>
            <value>hadoop104</value>
        </property>
     
        <!--指定zookeeper集群的地址--> 
        <property>
            <name>yarn.resourcemanager.zk-address</name>
            <value>hadoop102:2181,hadoop103:2181,hadoop104:2181</value>
        </property>
    
        <!--启用自动恢复--> 
        <property>
            <name>yarn.resourcemanager.recovery.enabled</name>
            <value>true</value>
        </property>
     
        <!--指定resourcemanager的状态信息存储在zookeeper集群--> 
        <property>
            <name>yarn.resourcemanager.store.class</name>
    <value>org.apache.hadoop.yarn.server.resourcemanager.recovery.ZKRMStateStore</value>
        </property>
    </configuration>
    ```

* 4、启动Yarn

  ```linux
  #1、在hadoop103中启动yarn
  > sbin/start-yarn.sh
  #2、在hadoop104中启动yarn
  > sbin/yarn-daemon.sh start resourcemanager
  #2、查看服务状态
  > bin/yarn rmadmin -getServiceStaterm1
  ```

## 6、HDFS Federation架构设计

### ①、Name Node架构的局限性

* Namespace（命名空间）的限制：由于NameNode在内存中存储所有的元数据（metadata），因此单个namenode所能存储的对象（文件+块）数目收到namenode所在JVM的heap size限制。
* 隔离问题：由于HDFS仅有一个NameNode，无法隔离各个程序，因此HDFS上的一个实验程序就很有可能影响整个HDFS上运行的程序
* 性能瓶颈：由于是单个NameNode的HDFS架构，因此整个HDFS文件系统的吞吐量受限于单个NameNode的吞吐量。

### ②、HDFS Federation架构设计

* 多个NameNode行使不同的责任，比如一台处理日志，一台处理数据，一台处理文件

### ③、HDFS Federation 应用思考

* 不同应用可以使用不同的NameNode进行数据管理。