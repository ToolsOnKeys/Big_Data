# HDFS

## 1、HDFS概述

### ①、产生背景和定义

* 背景

分布式文件系统。

* 定义

文件系统，用于存储文件，通过目录树来定位文件；其次，他是分布式的，有很多服务器联合起来实现其功能，集群中的服务器有各自的角色。

* 使用场景

适合一次写入，多次读出的场景，且不支持文件的修改。适合用来做数据分析，并不适合用来做网盘引用

### ② 优缺点

#### 优点

* 高容错性

  > 1、数据自动保存多个副本。它可以通过增加副本的形式，提高容错性
  >
  > 2、某一个副本丢失以后，它可以自动恢复

* 适合处理大数据

  > 1、数据规模：能够处理数据规模达到GB、TB、甚至PB级别的数据
  >
  > 2、文件规模：能够处理百万规模以上的文件数量，数据相当之大

* 可构建在廉价的机器上，通过多副本机制，提高可靠性

#### 缺点

* 不适合低延时数据访问

* 无法高效的对大量小文件进行存储

  > 1、存储大量小文件的话，他会再用NameNode大量的内存来存储文件目录和块信息。其中NameNode的内存是有限的。
  >
  > 2、小文件存储的寻址时间会超过读取时间，它违反了HDFS的设计目标

* 不支持并发写入、文件随机修改

  > 1、一个文件只能有一个写，不允许多个线程同时写
  >
  > 2、仅支持数据append（追加），不支持文件的随机修改

### ③HDFS组成架构

* NameNode（nn）

  > 1、管理HDFS的名称空间
  >
  > 2、配置副本策略
  >
  > 3、管理数据块（Block）映射信息
  >
  > 4、处理客户端读写请求

* DateNode（dn）

  > 1、Slave，NameNode下达命令，DataNode执行实际的操作
  >
  > 2、存储实际的数据块
  >
  > 3、执行数据块的读/写操作。

* Client（客户端）【并非真正属于HDFS】

  > 1、文件切分。文件上传HDFS的时候，Client将文件切分成一个一个的Block，然后进行上传
  >
  > 2、与NameNode交互，获取文件的位置信息
  >
  > 3、与DataNode交互，读取或写入数据
  >
  > 4、Client提供一些命令来管理HDFS，比如NameNode格式化
  >
  > 5、Client可以通过一些命令来访问HDFS，比如HDFS增删改查操作

* Secondary NameNode

  > 1、并非NameNode的热备。当NameNode挂掉，把并不能马上替换NameNode并提供服务
  >
  > 2、辅助NameNode，分担其工作量，比如定期合并Fsimage和Edits，并推送给NameNode
  >
  > 3、在紧急情况下，可辅助恢复NameNode

### ④HDFS文件块大小

* HDFS中的文件在物理上时分块存储（Block），块的大小可以通过配置参数（dfs.blocksize）来规定，默认大小在Hadoop2.x版本中时128M，老版本是64M

  > 1、集群中的block
  >
  > 2、如果寻址时间约为10ms，即查找到目标block时间为10ms
  >
  > 3、寻址时间为传输时间的1%时，则为最佳状态。因此，传输时间=10ms/0.01 = 1000ms = 1s
  >
  > 4、而目前磁盘的传输速率普遍为100MB/s
  >
  > 5、block大小=1s*100MB/s=100MB

* 块的大小设置原则

  > 1、HDFS的块设置太小，会增加寻址时间，程序一致在找块的开始位置
  >
  > 2、如果块设置的太大，从C盘传输数据的时间会明显大于定位这个块开始位置所需的时间。导致程序在处理这块数据时，会非常慢
  >
  > 3、HDFS块的大小设置主要取决于磁盘传输速率

## 2、HDFS的Shell操作

### ①、基本语法

* bin/hadoop fs 具体命令

* bin/hdfs dfs 具体命令

  >dfs时fs的实现类

* bin/hadoop fs

  > 查看所有fs的参数命令

### ②、常用命令实操

* -help：输出这个命令的参数

  ``` linux
  bin/hadoop fs -help rm
  ```

* -ls：显示目录信息

  ``` linux
  bin/hadoop fs -ls hdfs路径
  ```

* -mkdir：在HDFS上创建目录

  ```linux
  bin/hadoop fs -mkdir hdfs路径目录
  ```

* -moveFromLocal：从本地剪切粘贴到HDFS

  ```linux
  bin/hadoop fs -moveFromLocal 本地路径/文件 hdfs路径
  ```

* -appendToFile：追加一个文件到已经存在的文件末尾

  ```linux
  bin/hadoop fs -appendToFile a.txt hdfs上文件路径
  ```

* -cat：显示文件内容

  ```linux
  bin/hadoop fs -cat hdfs上文件路径
  ```

* -chgrp、-chmod、-chown：修改文件权限

  ```linux
  bin/hadoop fs -chmod 777 hdfs上文件路径
  ```

* -copyFromLocal：从本地文件系统中拷贝文件到HDFS路径上

  ```linux
  bin/hadoop fs -copyFromLocal 本地路径/文件 hdfs路径
  ```

* -copyToLocal：从HDFS拷贝到本地

  ```linux
  bin/hadoop fs -copyToLocal hdfs路径/文件 本地路径
  ```

* -cp：从HDFS的一个路径拷贝到HDFS的另一个路径

  ```linux
  bin/hadoop fs -cp hdfs路径/文件 hdfs路径/文件
  ```

* -mv：从HDFS目录中移动文件

  ```linux
  bin/hadoop fs -mv hdfs路径/文件 hdfs路径
  ```

* -get：等同于copyToLocal，就是从HDFS上下载文件到本地

  ```linux
  bin/hadoop fs -get hdfs路径/文件 本地路径
  ```

* -getmerge：合并下载多个文件

  ```linux
  bin/hadoop fs -getmerge hdfs路径/* 本地路径/文件
  ```

* -put：等同于copyFromLocal

  ```linux
  bin/hadoop fs -put 本地路径/文件 hdfs路径
  ```

* -tail：显示一个文件的末尾

  ```linux
  bin/hadoop fs -tail hdfs路径/文件
  ```

* -rm：删除文件或文件夹

  ```linux
  bin/hadoop fs -rm hdfs路径/文件或文件夹
  ```

* -rmdir：删除空目录

  ```linux
  bin/hadoop fs -rmdir hdfs路径目录
  ```

* -du：统计文件夹的大小

  ```linux
  bin/hadoop fs -du hdfs路径目录 
  >显示hdfs路径下所有文件或文件夹做统计
  bin/hadoop fs -du -s hdfs路径目录
  >显示hdfs路径下总的大小统计
  ```

* -setrep：设置HDFS文件的副本数量

  ```linux
  hadoop fs -setrep 10 hdfs路径/文件
  >这里设置的副本数只是记录在NameNode的元数据中，是否真的会有这么多副本，还得看DataNode的数量。如果目前只有三台设备，最多也就3个副本，只有节点数增加到10台时，副本才会达到10
  ```

## 3、HDFS客户端操作

### ①、HDFS客户端环境准备

* 拷贝对应操作系统的hadoop.jar包到一个非中文路径中

* 配置HADHOOP_HOME的环境变量

* 把HADHOOP_HOME加入到PATH环境变量中

* 创建一个Maven工程

* 在pom.xml中导入相应的依赖坐标+日志 

  ``` xml
  <dependencies>
  		<dependency>
  			<groupId>org.apache.logging.log4j</groupId>
  			<artifactId>log4j-core</artifactId>
  			<version>2.8.2</version>
  		</dependency>
  		<dependency>
  			<groupId>org.apache.hadoop</groupId>
  			<artifactId>hadoop-common</artifactId>
  			<version>2.7.2</version>
  		</dependency>
  		<dependency>
  			<groupId>org.apache.hadoop</groupId>
  			<artifactId>hadoop-client</artifactId>
  			<version>2.7.2</version>
  		</dependency>
  		<dependency>
  			<groupId>org.apache.hadoop</groupId>
  			<artifactId>hadoop-hdfs</artifactId>
  			<version>2.7.2</version>
  		</dependency>
  </dependencies>
  ```

* 在src/main/resources目录下创建文件log4j.properties

  ``` properties
  log4j.rootLogger=INFO, stdout
  log4j.appender.stdout=org.apache.log4j.ConsoleAppender
  log4j.appender.stdout.layout=org.apache.log4j.PatternLayout
  log4j.appender.stdout.layout.ConversionPattern=%d %p [%c] - %m%n
  log4j.appender.logfile=org.apache.log4j.FileAppender
  log4j.appender.logfile.File=target/spring.log
  log4j.appender.logfile.layout=org.apache.log4j.PatternLayout
  log4j.appender.logfile.layout.ConversionPattern=%d %p [%c] - %m%n
  ```

* 创建一个HDFS的API测试类（指定相应的包）

  ``` java
  public class Hdfstest {
  	// 1获取文件的配置信息
  	Configuration configuration = new Configuration();
  	// 配置在集群上运行
  	FileSystem fs;
  	@Before // 配置在集群上运行
  	public void testbegin() {
  		try {
  			fs = FileSystem.get(new URI("hdfs://hadoop101:9000"), configuration, "dinghao");
  		} catch (Exception e) {
  			e.getMessage();
  		}
  	}
  
  	@After // 关闭FileSystem
  	public void testlast() {
  		try {
  			fs.close();
  		} catch (IOException e) {
  			e.printStackTrace();
  		}
  	}
  }
  ```

### ②、HDFS的API操作

* 在hdfs上创建文件目录

  ``` java
  @Test // 在hdfs上创建文件目录
  public void testMakdir() throws Exception {// 创建目录
  	fs.mkdirs(new Path("/dinghao/dingding"));
  }
  ```

* 将hdfs上的文件（或目录）拷贝到本地指定路径

  ``` java
  @Test // 拷贝文件到本地
  public void testCopyFromLocalFile() throws Exception {
  	fs.copyToLocalFile(new Path("/"), new Path("D:/source"));
  }
  ```

* 将本地文件或目录复制到hdfs上指定的目录下

  ``` java
  @Test // 将本地文件或目录复制到hdfs上的指定目录
  public void testCopyFromLocal() throws Exception {
  	fs.copyFromLocalFile(new Path("D:/source/bigdata"), new Path("/dinghao"));
  	fs.copyFromLocalFile(new Path("d:/source/bigdata/banzhang"), new Path("/dinghao"));
  }
  ```

* 删除hdfs上的文件夹或文件

  ``` java
  @Test // hdfs文件夹或文件删除
  public void testDelete() throws Exception {
  	// 针对目录=》true:递归删除；false：非递归删除
  	// 针对文件=》true和false都可以删除
  	fs.delete(new Path("/dinghao/bigdata"), true);
  	fs.delete(new Path("/dinghao/banzhang"), true);
  }
  ```

* hdfs上文件或文件夹的重命名

  ``` java
  @Test // 文件或文件夹重命名
  public void testrename() throws Exception {
  	fs.rename(new Path("/dinghao/banzhang"), new Path("/dinghao/bangzhang1"));
  	fs.rename(new Path("/dinghao/bigdata"), new Path("/dinghao/big"));
  }
  ```

* hdfs文件夹下所有文件的遍历即文件信息的显示

  ``` java
  @Test // hdfs文件详情查看
  public void testListFiles() throws Exception {
  // 1、递归获取所有的文件 true
  RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
  int i = 0;
  while (listFiles.hasNext()) {
  	System.out.println("=================" + i++ + "================");
  	LocatedFileStatus fileStatus = listFiles.next();
  		// 获取文件名称
  		System.out.println("文件名称：" + fileStatus.getPath().getName());
  		// 获取长度
  		System.out.println("文件大小：" + fileStatus.getLen());
  		// 获取权限
  		System.out.println("获取权限" + fileStatus.getPermission());
          // 获取分组
  		System.out.println("获取分组" + fileStatus.getGroup());
  		// 获取块信息
  		BlockLocation[] blockLocations = fileStatus.getBlockLocations();
  		for (BlockLocation blockLocation : blockLocations) {
  			// 获取块存储的主机节点
             System.out.println("存储节点：" +Arrays.toString(blockLocation.getHosts()));
  		}
      }
  }
  ```

* 递归获取指定目录下的文件或目录结构

  ``` java
  	//递归获取至今文件夹下文件目录的树结构
  	public void getFileMessage(Path path,int i) throws Exception {
  		FileStatus[] fileStatus = fs.listStatus(path);
  		if (fileStatus.length != 0) {
  			i++;
  			for (FileStatus fileStatus2 : fileStatus) {
  				for(int t=0;t<i-1;t++) {
  					System.out.print("\t");
  				}
  				if (fileStatus2.isFile()) {
  					System.out.println("|-文件：" + fileStatus2.getPath().getName());
  				} else {
  					System.out.println("|-目录：" + fileStatus2.getPath().getName());
  					getFileMessage(fileStatus2.getPath(),i);
  				}
  			}
  		}
  	}
  
  	@Test//测试递归获取文件树结构
  	public void testgetmessage()throws Exception{
  		getFileMessage(new Path("/"),0);
  	}
  ```

* 文件上传

  ``` java
  	@Test//hdfs文件上传
  	public void putFileToHdfs()throws Exception{
  		//1、创建输入流
  		FileInputStream input = new FileInputStream(new File("D:/source/hadoop.md"));
  		//2、获取输出流
  		FSDataOutputStream output = fs.create(new Path("/hadoop.md"));
  		//3、流对拷
  		IOUtils.copyBytes(input, output, configuration);
  		//4、关闭资源
  		IOUtils.closeStream(input);
  		IOUtils.closeStream(output);
  	}
  ```

* 文件下载

  ``` java
  	@Test//下载文件
  	public void getFileHdfs()throws Exception{
  		//1、创建输入流
  		FSDataInputStream inputStream = fs.open(new Path("/hadoop.md"));
  		//2、获取输出流
  		FileOutputStream outputStream = new FileOutputStream(new File("D:/source/test.md"));
  		//3、流对拷
  		IOUtils.copyBytes(inputStream, outputStream, configuration);
  		//4、关闭资源
  		IOUtils.closeStream(inputStream);
  		IOUtils.closeStream(outputStream);
  	}
  ```

* 定位文件读取

  ``` java
  	@Test//定位文件读取-获取第1块文件
  	public void getfile1()throws Exception{
  		//1、获取输入流
  		FSDataInputStream inputStream = fs.open(new Path("/hadoop.md"));
  		//2、获取输出流
  		FileOutputStream outputStream = new FileOutputStream(new File("d:/source/test1.md"));
  		//3、流拷贝
  		byte[] bs = new byte[1024];
  		for(int i=0;i<5;i++) {
  			inputStream.read(bs);
  			outputStream.write(bs);
  		}
  		//4、关闭资源
  		IOUtils.closeStream(inputStream);
  		IOUtils.closeStream(outputStream);
  	}
  	@Test//定位文件读取-获取第2块文件
  	public void getfile2()throws Exception{
  		//1、获取输入流
  		FSDataInputStream inputStream = fs.open(new Path("/hadoop.md"));
  		//2、获取输出流
  		FileOutputStream outputStream = new FileOutputStream(new File("d:/source/test2.md"));
  		inputStream.seek(1024*5);
  		//3、流拷贝
  		IOUtils.copyBytes(inputStream, outputStream, configuration);
  		//4、关闭资源
  		IOUtils.closeStream(inputStream);
  		IOUtils.closeStream(outputStream);
  	}
  ```

## 4、HDFS的数据流

### ①、HDFS写数据流程

![](D:\BigData\BigData\16大数据入门\相关说明图片\DN的写过程.png)

* 剖析文件写入

  > 1、客户端通过Distributed FileSystem 模块向NameNode请求上传文件，NameNode检查目标文件是否存在，父目录是否存在。
  >
  > 2、NameNode返回是否可以上传
  >
  > 3、客户端请求第一个Block上传到哪几个DataNode服务器。
  >
  > 4、NameNode返回3个DataNode节点，分别为dn1、dn2、dn3。
  >
  > 5、客户端通过FSDataOutputStream模块请求dn1上传数据，dn1收到请求会继续调用dn2，然后dn2会调用dn3，将这个通信管道建立完成。
  >
  > 6、d1、d2、d3逐级应答客户端。
  >
  > 7、客户端开始往dn1上传第一个Block，以Packet为单位（64KB左右）【n个chunk数据单元】，dn1收到一个Packet就会传给dn2，dn2传给dn3；dn1每传一个packet会放入一个应答队列等待应答。
  >
  > 8、当一个Block传输完毕之后，客户端再次请求NameNode上传第二个Block的服务器

* 网络拓扑-节点距离计算

* 机架感知（副本存储节点选择）

  > 假如有三个副本，可以将两个副本放在一个机架的不同节点上，同时将第三个副本放到另外一台机架上。

### ②、HDFS读数据

![](D:\BigData\BigData\16大数据入门\相关说明图片\DN的读流程.png)

> 1、客户端通过Distributed FileSystem向NameNode请求下载文件，NameNode通过查询元数据，找到文件块所在的DataNode地址。
>
> 2、挑选一台DataNode（就近原则，然后随机服务器，请求读取数据）
>
> 3、DataNode开始传输数据给客户端（从磁盘里面读取数据输入流，以Packet为单位来做校验）
>
> 4、客户端以Packet为单位接收，先在本地缓存，然后写入目标文件

## 5、NameNode和SecondaryNameNode

### ①、NN和2NN的工作机制

![](D:\BigData\BigData\16大数据入门\相关说明图片\NN和2NN配合机制.png)

* NameNode中元数据是存储在哪里，2NN和NN如何协调数据？

  > 1、存储在内存中
  >
  > 2、磁盘中有一个镜像文件和一个编辑日志，只支持在编辑日志追加内存的操作日志
  >
  > 3、2NN定期（条件：定时向NN发送合并请求通知或NN有100万次操作）将NN中的日志中文件信息和镜像文件复制到2NN中，后将复制版本内容写入内存，NN同时会在磁盘中产生另一个日志（记录之后NN的操作），2NN会将镜像和日志重新生成镜像写入磁盘。之后2NN会将磁盘镜像文件写入NN中，替换掉NN中的镜像文件。
  >
  > 4、内存中数据恢复，可以将镜像文件和编辑日志加载到内存中就可以恢复NN的数据。

### ②、Fsimage和Edits解析

> NameNode被格式化后，将在/opt/module/hadoop-2.7.2/data/tmp/dfs/name/current目录中产生如下文件：
>
> fsimage_0000000000000
>
> fsimage_0000000000000.md5
>
> seen_txid
>
> VERSION：集群id等信息

* 概念

  > 1、Fsimage文件：HDFS文件系统元数据的一个永久性的检查点，其中包含HDFS文件系统的所有目录和文件innode的序列化
  >
  > 2、Edits文件：存放HDFS文件系统的所有更新操作的路径，文件系统客户端执行的所有写操作首先会被记录到Edits文件中。
  >
  > 3、seen_txid文件保存的是一个数字，就是最后一个edits_的数字
  >
  > 4、每次NameNode启动的时候都会将Fsimage文件读入内存，加载Edits里面的更新操作，保证内存中的元数据信息是最新的、同步的，可以看成NameNode启动的时候就将Fsimage和Edits文件进行合并。

* oiv 查看Fsimage文件

  > 1、查看oiv和oev的命令

  ```linux
  hdfs
  ```

  > 2、基本语法

  ``` linux
  hdfs oiv -p 文件类型 -i 镜像文件 -o 转换后文件输出路径/文件名
  ```

  > 3、实例

  ```linux
  hdfs oiv -p XML -i fsimage_xxxxxxxxxxx -o ./fsimage.xml
  ```

  > 4、注意：在集群启动后，要求DN上报数据块信息，并间隔一段时间后再次上报

* oev查看Edits文件

  > 1、基本语法

  ```linux
  hdfs oev -p 文件类型 -i 编辑日志 -o 转换后的文件输出路径/文件名
  ```

  > 2、实例

  ```linux
  hdfs oev -p XML -i edits_xxxxxxxxxx -o ./edits.xml
  ```

### ③、CheckPoint时间设置

* 通常情况一下SecondaryNameNode每隔1h执行一次。调整hdfs-default.xml

  ``` xml
  <property>
    <name>dfs.namenode.checkpoint.period</name>
    <value>3600</value>
  </property>
  ```

* NN默认1min检查一次操作数量，当操作数达到100W次时，2NN执行一次。调整hdfs-dafault.xml

  ```xml
  <property>
    <name>dfs.namenode.checkpoint.txns</name>
    <value>1000000</value>
  <description>操作动作次数</description>
  </property>
  <property>
    <name>dfs.namenode.checkpoint.check.period</name>
    <value>60</value>
  <description> 1分钟检查一次操作次数</description>
  </property >
  ```

### ④、NameNode故障处理 ( 数据恢复 ) 

* 方法一：将2NN中数据拷贝到NN存储数据的目录中

  > 1、模拟NN宕机（ 杀进程; 删除NN的数据【/opt/module/hadoop-2.7.2/data/tmp/dfs/name】）

  ```linux
  > kill -9 NN的进程号
  > rm -rf /opt/module/hadoop-2.7.2/data/tmp/dfs/name/*
  ```

  > 2、拷贝2NN中current文件夹到NN存储数据（【/opt/module/hadoop-2.7.2/data/tmp/dfs/name】）

  ```linux
  > scp -r dinghao@hadoop103:2NN读current路径/文件 ./name/
  ```

  > 3、重启NN

  ```linux
  sbin/hadoop-daemon.sh start namenode
  ```

* 方法二：使用-importCheckpoint选项启动NN守护进程，从而将2NN中数据拷贝到NN数据存储的目录中

  > 1、修改hdfs-site.xml

  ```linux
  <property>
    <name>dfs.namenode.checkpoint.period</name>
    <value>120</value>
  </property>
  <property>
    <name>dfs.namenode.name.dir</name>
    <value>/opt/module/hadoop-2.7.2/data/tmp/dfs/name</value>
  </property>
  ```

  > 2、模拟NN宕机

  ```linux
  > kill -9 NN的进程号
  > rm -rf /opt/module/hadoop-2.7.2/data/tmp/dfs/name/*
  ```

  > 3如果2NN和NN不在一个主机节点上，需要将2NN存储数据的目录拷贝到NN存储数据的平级目录上（不包含in_use.lock文件）

  ```linux
  > scp -r dinghao@hadoop103:/opt/module/hadoop-2.7.2/data/tmp/dfs/namesecondary ./
  > rm -rf in_use.lock
  ```

  > 4、导入检查点数据（完成时通过ctrl+c结束掉）

  ```linux
  > bin/hdfs namenode -inportCheckpoint
  ```

  > 5、重启NN

  ```linux
  > sbin/hadoop-daemon.sh start namenode
  ```

### ⑤、集群的安全模式

* 概述

  * 1、NN启动

    >NN启动时，首先将镜像文件（Fsimage）载入内存，并执行编辑日志（Edits）中各项操作。一旦在内存中成功建立文件系统元数据的映像，则创建一个新的Fsimage文件和一个空的编辑日志。此时，NN开始监听DN请求。这个期间，NN一直处于安全模式，即NN的文件系统对于客户端来说只读。

  * 2、DN启动

    > 系统中的数据块的位置并不是由NN维护的，而是以块列表的形式存储在DN中。在系统正常操作期间，NN会在内存中保留所有块位置的映射信息。在安全模式下，各个DN都会向NN发送最新的块列表信息，NN了解到足够多的块位置信息之后，即可高效的运行文件系统。

  * 3、安全模式退出判断

    > 如果满足“**最小副本条件**”，NN会在30s后退出安全模式。所谓最小副本条件指的是在整个文件系统中99.9%的块满足最小副本级别（默认值：dfs.replication.min=1)。在启动一个刚刚格式化的HDFS集群时，因为系统中还没有任何块，所以NN不会进入到安全模式。

* 基本语法

  > 集群处于安全模式，不能执行写操作。集群启动完成后，自动退出安全模式

  ```shell
  1、bin/hdfs dfsadmin -safemode get
  >查看安全模式状态
  2、bin/hdfs dfsadmin -safemode enter
  >进入安全模式状态
  3、bin/hdfs dfsadmin -safemode leave
  >离开安全模式状态
  4、bin/hdfs dfsadmin -safemode wait
  >等待安全模式状态
  ```

* 案例（模拟【等待安全模式状态】）

  > 1、常看当前状况

  ```linux
  > hdfs dfsadmin -safemode get
  ```

  > 2、进入安全模式

  ```linux
  > bin/hdfs dfsadmin -safemode enter
  ```

  > 3、创建如下脚本，在/opt/module/hadoop-2.7.2路径上，编辑一个脚本safemode.sh

  ```linux
  > vim safemode.sh
  #!/bin/bash
  hdfs dfsadmin -safemode wait
  hdfs dfs -put /opt/module/hadoop-2.7.2/README.txt /
  > chmod 777 safemode.sh
  > ./safemode.sh
  ```

  > 4、再打开一个窗口终端，关闭安全模式

  ```linux
  > bin/hdfs dfsadmin -safemode leave
  ```

  > 5、观察现象(切换为原来终端窗口)

  ```现象观察
  （a）再观察上一个窗口
     Safe mode is OFF
  （b）HDFS集群上已经有上传的数据了。
  ```

### ⑥、NameNode多目录配置

* NN的本地目录可以配置成多个，且每个目录存放相同的内容，增加了可靠性（其实同意主机多副本意义不大）

* 具体配置如下

  > 1、在hdfs-site.xml文件中增加如下内容

```xml
<property>
    <name>dfs.namenode.name.dir</name>
<value>file:///${hadoop.tmp.dir}/dfs/name1,file:///${hadoop.tmp.dir}/dfs/name2</value>
</property>
```

> 2、格式化集群

```linux
> rm -rf data/ logs/
三个环境的都要删除
> bin/hdfs namenode -format
> sbin/start-dfs.sh
```

> 3、查看结果

```linux
> ll
```

## 6、DataNode

### ①、DN的工作机制

* 一个数据块再DN上以文件形式存储再磁盘上，包括两个文件，一个是数据本身，一个是元数据包括数据块的长度，块数据的校验和，以及时间戳。
* DN启动后向NN注册，通过后，周期性（1小时）的向NN上报所有的块信息。
* 心跳是每3s一次，心跳返回结果带有NN给该DN的命令，如复制块数据到另一台机器，或删除某个数据块。如果超过10min30s没有收到某个DN的心跳，则认为该节点不可用。
* 集群运行中可以安全加入和退出一些机器。

### ②、数据完整性

> DN节点上的数据损坏了，如何解决，以下是保证DN数据节点完整性的方法

* 当DN读取Block的时候，他会计算CheckSum。
* 如果计算后的CheckSum，与Block创建的值不一样，说明Block已经损坏。
* Client读取其他DN上的Block。
* DN在其文件创建后周期验证CheckSum。

### ③、掉线时限参数设置

* 判断掉线原理分析

  ```分析
  1、DN进程死亡或者网络故障造成DN无法与NN通信。
  2、NN不会立即把该节点判定为死亡，要经过一段时间，这段时间暂称为超时时长。
  3、HDFS默认的超时时长是10min+30s
  4、TimeOut=2*dfs.namenode.hearbeat.recheck-interval+10*dfs.heartbeat.interval。
  >hdfs-site.xml中配置信息如下：
  <property>
      <name>dfs.namenode.heartbeat.recheck-interval</name>
      <value>300000</value>
  </property>
  <property>
      <name>dfs.heartbeat.interval</name>
      <value>3</value>
  </property>
  ```

### ④、服役新数据节点

* 需求

  > 随着公司的业务的增长，数据量越来越大，原有的数据节点的容量已经不能满足存储数据的需求，需要再原有集群的基础上动态添加新的数据节点

* 环境准备

  * 1、准备一台hadoop设置同hadoop103一致的主机（建议克隆）

  * 2、修改ip和主机名

  * 3、删除原有的HDFS文件系统保留的文件（data和logs）

  * 4、source以下配置文件

    ```linux
    > source /etc/profile
    ```

* 服役新节点实现的具体步骤

  * 1、直接启动新增节点的DN，即可关联到集群

    ```linux
    > sbin/hadoop-daemon.sh start datanode
    ```

  * 2、在hadoop104上传文件

    ```linux
    > hadoop fs -put 本地文件路径/文件 hdfs路径
    ```

  * 3、如果数据不均衡，可以用命令实现集群的再平衡

    ```linux
    > sbin/start-balancer.sh
    ```

### ⑤、退役旧数据节点

#### Ⅰ 添加白名单退役

> 添加到白名单中的主机节点，都允许访问NN，不在白名单的主机节点，都会被退出。

> 配置白名单的具体步骤如下：

* 1、在NN的/opt/module/hadoop-2.7.2/etc/hadoop 目录下创建dfs.hosts文件

  ``` linux
  > vim dfs.hosts
  hadoop101
  hadoop102
  hadoop103
  ```

* 2、在NN的hdfs-site.xml配置文件中增加dfs.hosts属性

  ```xml
  <property>
  <name>dfs.hosts</name>
  <value>/opt/module/hadoop-2.7.2/etc/hadoop/dfs.hosts</value>
  </property>
  ```

* 3、配置文件分发

  ```linux
  > xsync hdfs-site.xml
  ```

* 4、刷新NN

  ```linux
  > hdfs dfsadmin -refreshNodes
  ```

* 5、刷新ResourceManager节点

  ```linux
  > yarn rmadmin -refreshNodes
  ```

* 6、数据均衡处理，实现集群的在平衡

  ```linux
  > ./start-balancer.sh
  ```

#### Ⅱ 添加黑名单退役

> 在黑名单上的主机都会被强制退出；注意：不允许白名单和黑名单中出现同一个主机名

* 1、在NN的/opt/module/hadoop-2.7.2/etc/hadoop 目录下创建dfs.hosts.exclude文件

  ```linux
  > vim dfs.hosts.exclude
  hadoop104
  ```

* 2、在NN的hdfs-site.xml配置文件中增加dfs.hosts.exclude属性

  ```linux
  <property>
  <name>dfs.hosts.exclude</name>
        <value>/opt/module/hadoop-2.7.2/etc/hadoop/dfs.hosts.exclude</value>
  </property>
  ```

* 3、刷新NN、ResourceManager

  ```linux
  > hdfs dfsadmin -refreshNodes
  > yarn rmadmin -refreshNodes
  ```

* 4、检查web浏览器，退役节点的状态是decommission in progress（退役中），说明数据节点正在复制块到其他节点。

* 5、等待退役节点状态为 decommissioned （所有块已经复制完成），停止该节点及节点资源管理器。注意：如果副本数为3，服役节点小于等于3，是不能退役成功的，需要修改副本数后才能退役。

  ```linux
  > sbin/hadoop-daemon.sh stop datanode
  > sbin/yarn-daemon.sh stop nodemanager
  ```

* 6、如果数据不均衡，可以用命令实现集群的再平衡

  ```linux
  > sbin/start-balancer.sh
  ```

### ⑥、DN多目录设置

* 1、DN也可以配置成多目录，每个目录存储的数据不一样。即：数据不是副本

* 2、具体配置如下：hdfs-site.xml

  ```linux
  <property>
          <name>dfs.datanode.data.dir</name>
  <value>file:///${hadoop.tmp.dir}/dfs/data1,file:///${hadoop.tmp.dir}/dfs/data2</value>
  </property>
  ```

## 7、HDFS 2.X新特性

### ①、集群之间的数据拷贝

* scp 实现两个远程主机之间的文件拷贝

  ```linux
  > scp -r root@hadoop102:路径文件 root@hadoop104:路径
  不管两个远程主机之间是否有配置ssh，都可以使用这个方法
  ```

* 采用distcp命令实现两个hadoop集群之间的递归数据的复制

  ```linux
  > bin/hadoop distcp hdfs://hadoop102:9000/user/dinghao/hello.txt hdfs://hadoop103:9000/user/dinghao/hello.txt
  ```

### ②、小文件存档

* HDFS存储小文件的弊端

  > 每个文件均按块存储，每个块元数据存储在NN的内存中，因此HDFS存储小文件会非常低效。因为大量的小文件会耗尽NN中的大部分内存。但注意，存储小文件所需要的磁盘容量和数据块的大小无关。例如，一个1MB的文件设置为128MB的块存储，实际使用的是1MB的磁盘空间，而不是128MB。

* 解决存储小文件办法之一

  > HDFS存档文件或HAR文件，是一个更高效的文件存档工具，他将文件存入HDFS块中，再减少NN内存使用的同时，允许对文件进行透明的访问。具体来说，HDFS存档文件对内还是一个一个独立文件，对NN而言却是一个整体，减少了NN的内存。

* 实例操作

  * 1、需要启动YARN

    ```linux
    > sbin/start-yarn.sh
    群起集群中yarn配置进程
    ```

  * 2、归档文件

    > 把hdfs路径目录下所有的文件归档成一个叫input.har的归档文件，并把归档后文件存储到hdfs中另一个路径目录下

    ```linux
    > bin/hadoop archive -archiveName input.har -p hdfs中来源目录 hdfs中目标目录
    ```

  * 3、查看归档文件

    ```linux
    > hadoop fs -lsr hdfs目标路径/input.har
    > hadoop fs -lsr har://hdfs目标路径/input.har
    ```

  * 4、解归档文件

    ```linux
    > hadoop fs -cp har://hdfs目标路径/input.har/* 解归档文件存放路径
    ```

    # 
