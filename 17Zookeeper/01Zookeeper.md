# Zookeeper

## 1、Zookeeper入门

### ①、概述

* Zookeeper是一个开源的分布式的，为分布式应用提供协调服务的Apache项目
* Zookeeper从设计模式角度来理解：是一个基于观察者模式设计的分布式服务管理框架，它**负责存储和管理大家都关心的数据**，然后**接收观察者的注册**，一旦这些数据的状态发生变化，Zookeeper就将**负责通知已经在Zookeeper上注册的那些观察者**做出相应的反应。
* Zookeeper=文件系统+通知机制。
  * 1、服务端启动时去注册信息（创建的都是临时节点）
  * 2、获取当前在线服务器列表，并且注册监听。
  * 3、服务器节点上下线。
  * 4、服务器节点上下线时间通知。
  * 5、重新获取服务器列表，并重新注册监听。
* 为其他框架提供服务的。

### ②、特点

* 1、Zookeeper：一个领导者（Leader），多个跟随者（Follower）组成的集群。
* 2、**集群中只要有半数以上节点存活，Zookeeper集群就能正常服务**。
* 3、全局数据一致：每个Server保存一份相同的数据副本，Client无论连接到哪个Server，数据都是一致的。
* 4、更新请求顺序进行，来自同一个Client的更新请求按其发送顺序依次执行。
* 5、数据更新原子性，一次数据更新要么成功，要么失败。
* 6、实时性，在一定时间范围内，Client能读到最新数据。

### ③、数据结构

* Zookeeper数据模型的结构与**Unix文件系统很类似**，整体上可以看作是一棵树，每个节点名称做一个ZNode。每一个ZNode默认能够存储**1MB**的数据，每个ZNode都可以**通过其路径唯一标识**。

### ④、应用场景

提供的服务包括：统一命名服务、**统一配置管理**、**统一集群管理**、服务器节点动态上下线、软负载均衡等。

* 1、统一命名服务

  在分布式环境下，经常需要对应用/服务进行统一命名，便于识别。

  eg：IP不容易记住，而域名容易记住

* 2、统一配置管理

  * 分布式环境下，配置文件同步非常常见
    * 一般要求一个集群中，所有节点配置信息是一致的。
    * 对配置文件修改后，希望能够快速同步到各个节点。
  * 配置管理可交由ZooKeeper实现
    * 可将配置信息写入zookeeper上的一个Znode。
    * 各个客户端服务器监听这个Znode。
    * 一旦Znood中的数据被修改，ZooKeeper将通知各个客户端服务器。

* 3、统一集群管理

  * 分布式环境中，实时掌握每个节点的状态是必要的。
    * 可以根据节点实时状态做出一些调整
  * Zookeeper可以实现实时监控节点状态变化。
    * 可将节点信息写入Zookeeper上的一个Znode。
    * 监听这个ZNode可获取他的实时状态变化

* 4、服务器动态上下线

* 5、软负载均衡

  * 在Zookeeper中记录每台服务器的访问数，让访问数最少的服务器去处理最新的客户端请求。

## 2、Zookeeper安装

### ①、本地模式安装部署

* 安装准备
  * 安装jdk
  * 拷贝Zookeeper安装包到Linux系统下
  * 解压Zookeeper

* 配置调整

  * 将zookeeper安装路径下的conf文件夹下的zoo_sample.cfg修改为zoo.cfg

  * 打开zoo.cfg文件，修改dataDir路径（一般数据路径就放在zookeepera安装路径下）

    配置信息解读：

    * tickTime = 2000   #通信心跳数，Zookeeper服务器与客户端心跳时间，单位毫秒。

      Zookeeper使用的是基本时间，服务器之间或客户端与服务器之间维持心跳的时间间隔，也就是每个tickTime时间就会发送一个心跳，时间单位是毫秒。

      它用于心跳机制，并且设置最小的session超时时间为两倍心跳时间（Session的最小超时时间是2*tickTime）

    * initLimit=10  #LF 初始通信时限

      集群中的Follower跟随者服务器和Leader领导者服务器之间初始连接时能容忍的最多心跳数（tickTime的数量），用它来限定集群中的Zookeeper服务器连接到Leader的时限。

    * syncLimit=5  #LF同步通信时限

      集群中Leader和Follower之间的最大响应时间单位，加入响应超过syncLimit*tickTime，Leader认为Follwer死掉，从服务器列表中删除Follwer

    * dataDir  #数据文件目录+数据持久化路径

      主要用于保存Zookeeper中的数据

    * clientPort  #客户端连接端口

      监听客户端连接的端口

  * 在zookeeper安装路径下创建dataDir路径

* 操作Zookeeper

  ```linux
  > bin/zkServer.sh start #启动Zookeeper服务端
  > jps #查看zookeeper服务进程是否启动
  > bin/zkServer.sh status #查看zookeeper状态
  > bin/zkCli.sh #进入zookeeper客户端
  > quit #退出客户端
  > bin/zkServer.sh stop #关闭zookeeper服务端
  ```

  

## 3、分布式安装部署

### ①、集群规划

* 规划部署Zookeeper到三个节点

### ②、安装Zookeeper，配置zoo.cfg文件

```linux
> tar -zxvf zookeeper-3.4.10.tar.gz -C /opt/module/ #解压Zookeeper，安装Zookeeper
> mv zoo_sample.cfg zoo.cfg #获取zoo.cfg文件
> vim zoo.cfg
dataDir=/opt/module/zookeeper-3.4.10/zkData #配置数据路径
#######################cluster##########################
server.2=hadoop102:2888:3888
server.3=hadoop103:2888:3888
server.4=hadoop104:2888:3888

##配置信息解读：
server.A=B:C:D
A:表示第几号服务器
集群模式下配置一个文件myid，这个文件在dataDir目录下，这个文件里面有一个数据就是A的值，Zookeeper启动时读取此文件，拿到里面的数据与zoo.cfg里面的配置信息比较从而判断到底是哪个server
B：服务器的地址
C：服务器的Follower和集群中Leader服务器交换信息的端口
D：是万一集群中的Leader服务器关了，需要一个端口来重新进行选举，选出一个新的Leader，而这个端口就是用来执行选举是服务器相互通信的端口。
```

### ③、配置服务器编号

```linux
> mkdir -p zkData #创建数据路径，要求和zoo.cfg中指定的路径
> touch myid #创建server对应的id
> vim myid #编辑myid，注意三台主机上的id要和zoo.cfg中配置的2，3，4分别对应。
```

### ④、分发Zookeeper

```linux
> xsync zookeeper-3.4.10
```

### ⑤、群起Zookeeper服务相关的脚本

```Shell
#!/bin/bash

case $1 in
"start"){
	for i in hadoop102 hadoop103 hadoop104
	do
		ssh $i "/opt/module/zookeeper-3.4.10/bin/zkServer.sh start"
	done
};;
"stop"){
	for i in hadoop102 hadoop103 hadoop104
	do
		ssh $i "/opt/module/zookeeper-3.4.10/bin/zkServer.sh stop"
	done
};;
"status"){
	for i in hadoop102 hadoop103 hadoop104
	do
		ssh $i "/opt/module/zookeeper-3.4.10/bin/zkServer.sh status"
	done
};;
esac
```

### ⑥、客户端命令操作

| 命令基本语法       | 功能描述                                               |
| ------------------ | ------------------------------------------------------ |
| help               | 显示所有操作命令                                       |
| ls path [watch]    | 使用 ls 命令来查看当前znode中所包含的内容              |
| ls2 path   [watch] | 查看当前节点数据并能看到更新次数等数据                 |
| create             | 普通创建   -s  含有序列   -e  临时（重启或者超时消失） |
| get path   [watch] | 获得节点的值                                           |
| set                | 设置节点的具体值                                       |
| stat               | 查看节点状态                                           |
| delete             | 删除节点                                               |
| rmr                | 递归删除节点                                           |

```linux
> bin/zkCli.sh #启动客户端
> help #显示所有操作命令
> ls / #查看znode中所包含的内容
> ls2 / #查看当前节点详细数据
> create /节点名 节点内容 #创建一个普通节点
> get /节点名 #获得节点的值
> create -e /节点名 #创建临时节点，即在当前客户端可见，推出客户端，再进入客户端就不可见了
> create -s /节点名 #创建待序号的节点。如果原来没有序号节点，序号从0开始一次递增。如果源节点下已有两个节点，则再排序时从2开始。
> set /节点名 节点内容 #修改节点的数据值
> get /节点名 watch #注册监听某个节点的数据变化。
> delete /节点名 #删除节点
> rmr /节点名 #递归删除节点。
> stat /节点名 #查看节点状态。
```

### ⑦、API调用操作

* 1、pom.xml

  ```pom.xml
  <dependencies>
  		<dependency>
  			<groupId>junit</groupId>
  			<artifactId>junit</artifactId>
  			<version>RELEASE</version>
  		</dependency>
  		<dependency>
  			<groupId>org.apache.logging.log4j</groupId>
  			<artifactId>log4j-core</artifactId>
  			<version>2.8.2</version>
  		</dependency>
  		<!-- https://mvnrepository.com/artifact/org.apache.zookeeper/zookeeper -->
  		<dependency>
  			<groupId>org.apache.zookeeper</groupId>
  			<artifactId>zookeeper</artifactId>
  			<version>3.4.10</version>
  		</dependency>
  </dependencies>
  ```

* 2、log4j.properties

  ```log4j.properties
  log4j.rootLogger=INFO, stdout  
  log4j.appender.stdout=org.apache.log4j.ConsoleAppender  
  log4j.appender.stdout.layout=org.apache.log4j.PatternLayout  
  log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n  
  log4j.appender.logfile=org.apache.log4j.FileAppender  
  log4j.appender.logfile.File=target/spring.log  
  log4j.appender.logfile.layout=org.apache.log4j.PatternLayout  
  log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
  ```

* 3、创建ZooKeep客户端

  ```java
  private static String connectString =
   "hadoop102:2181,hadoop103:2181,hadoop104:2181";
  	private static int sessionTimeout = 2000;
  	private ZooKeeper zkClient = null;
  	@Before
  	public void init() throws Exception {
  	zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
  			@Override
  			public void process(WatchedEvent event) {
  				// 收到事件通知后的回调函数（用户的业务逻辑）
  				System.out.println(event.getType() + "--" + event.getPath());
  				// 再次启动监听
  				try {
  					zkClient.getChildren("/", true);
  				} catch (Exception e) {
  					e.printStackTrace();
  				}
  			}
  		});
  	}
  ```

* 4、创建子节点

  ```java
  // 创建子节点
  @Test
  public void create() throws Exception {
  		// 参数1：要创建的节点的路径； 参数2：节点数据 ； 参数3：节点权限 ；参数4：节点的类型
  		String nodeCreated = zkClient.create("/atguigu", "jinlian".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
  }
  ```

* 5、获取子节点并监听节点变化

  ```java
  // 获取子节点
  @Test
  public void getChildren() throws Exception {
  		List<String> children = zkClient.getChildren("/", true);
  		for (String child : children) {
  			System.out.println(child);
  		}
  		// 延时阻塞
  		Thread.sleep(Long.MAX_VALUE);
  }
  ```

* 6、判断Znode是否存在

  ```java
  // 判断znode是否存在
  @Test
  public void exist() throws Exception {
  	Stat stat = zkClient.exists("/eclipse", false);
  	System.out.println(stat == null ? "not exist" : "exist");
  }
  ```

## 4、Zookeeper内部原理

### ①、节点设置

* 1、持久：客户端和服务器端断开连接后，创建的节点不删除
* 2、短暂：客户端和服务器端断开连接后，创建的节点自己删除
* 3、说明：创建znode时设置顺序标识，znode名称后会附加一个值，顺序号是一个单调递增的计数器，由父节点维护。
* 4、注意：在分布式系统中，顺序号可以被用于为所有的事件进行全局排序，这样客户端可以通过顺序号推断事件的顺序。

### ②、Stat结构体

* 1、czxid-创建的节点的事务zxid：每次修改ZooKeeper状态都会收到一个zxid形式的事件戳，也就是Zookeeper事务ID。事务ID是Zookeeper中所有修改总的次序。每个修改都有唯一的zxid，如果zxid小于zxid2，那么zxid1在zxid2之前发生。
* 2、ctime-znode被创建的毫秒数。
* 3、mzxid-znode最后更新的事务zxid。
* 4、mtime-znode最后修改的毫秒数。
* 5、pZxid-znode最后更新的子节点。
* 6、cversion-znode子节点变化号，znode子节点修改次数。
* 7、dataversion-znode数据变化号。
* 8、aclVersion-znode访问控制列表的变化号。
* 9、ephemeralOwner-如果是临时节点，这个是znode拥有者的session id。如果不是临时节点则是0.
* 10、dataLength-znode的数据长度。
* 11、numChildren-znode子节点数量。

### ③、监听器原理

* 1、监听器原理详解：
  * 首先要有一个main（）线程
  * 在main线程中创建ZooKeeper客户端，这是就会创建两个线程，一个负责网络连接通信，一份负责监听
  * 通过connect网络通信线程将注册的监听事件发送给Zookeeper。
  * 在Zookeeper的注册监听器列表中将注册监听事件添加到列表中
  * Zookeeper监听到有数据或路径变化，就会将这个消息发送给listener监听线程
  * listener线程内部调用process方法
* 2、创建的监听
  * 监听节点数据的变化
  * 监听子节点增减的变化

### ④、选举机制

* 1、半数机制：集群中半数以上机器存货，集群可用。所以Zookeeper适合安装奇数台服务器
* 2、Zookeeper虽然在配置文件中并没有指定Master和Slave。但是，Zookeeper工作时，是有一个节点为Leader，其他则为Follower，Leader是通过内部的选举机制临时产生的。

### ⑤、写数据流程

* 1、Client向Zookeeper的Server1上写数据，发送一个写数据请求。
* 2、如果Server1不是Leader，那么Server1会把接收到的请求进一步转发给Leader，因为每个Zookeeper的server里面有一个是Leader。这个Leader会将写请求光波导每个Server，比如Server1和Server2，各个Server会将该写请求加入待写队列，并向Leader发送成功信息。
* 3、当Leader收到半数以上server的成功信息，说明该写操作可以执行。Leader会向各个Server发送提交信息，各个Server收到信息后会落实队列中的写请求，此时写成功。
* 4、Server1会进一步通知Client数据写成功了，这时就认为整个写操作成功了。