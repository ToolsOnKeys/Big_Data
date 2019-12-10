# Hive安装与部署

## 1、Hive安装地址

### ①、Hive官网地址

http://hive.apache.org/

### ②、文档查看地址

https://cwiki.apache.org/confluence/display/Hive/GettingStarted

### ③、下载地址

http://archive.apache.org/dist/hive/

## 2、Hive安装部署

### ①、Hive安装及配置

* Ⅰ、把apache-hive-1.2.1-bin.tar.gz上传到linux的/opt/software目录下

* Ⅱ、解压**apache-hive-1.2.1-bin.tar.gz**到/opt/module/目录下面

  ```linux
  > tar -zxvf apache-hive-1.2.1-bin.tar.gz -C /opt/module/
  ```

* Ⅲ、修改**apache-hive-1.2.1-bin**的名称为**hive**

  ```linux
  > mv apache-hive-1.2.1-bin/ hive
  ```

* Ⅳ、修改**/opt/module/hive/conf**目录下的**hive-env.sh.template**名称为**hive-env.sh**

  ```linux
  > mv hive-env.sh.template hive-env.sh
  ```

* Ⅴ、配置**hive-env.sh**文件

  ```hive-env.sh
  export HADOOP_HOME=/opt/module/hadoop-2.7.2 #配置HADOOP_HOME路径
  export HIVE_CONF_DIR=/opt/module/hive/conf #配置HIVE_CONF_DIR路径
  ```

### ②、Hadoop集群配置

* Ⅰ、必须启动hdfs和yarn

  注：编写一个脚本，一次性将Hive运行时进程全部起来

  ```gethive.sh
  #!/bin/bash
  case $1 in
  "start"){
  echo --------启动hdfs----------
  /opt/module/hadoop-2.7.2/sbin/start-dfs.sh
  echo --------启动yarn----------
  ssh hadoop102 "/opt/module/hadoop-2.7.2/sbin/start-yarn.sh"
  };;
  "stop"){
  echo --------关闭hdfs----------
  /opt/module/hadoop-2.7.2/sbin/stop-dfs.sh
  echo --------关闭yarn----------
  ssh hadoop102 "/opt/module/hadoop-2.7.2/sbin/stop-yarn.sh"
  };;
  esac
  ```

* Ⅱ、在HDFS上创建/tmp和/user/hive/warehouse两个目录并修改他们的同组权限可写(可不操作，系统会自动创建)

  注：以下是手动创建并调整权限：

  ```linux
  > bin/hadoop fs -mkdir /tmp
  > bin/hadoop fs -mkdir -p /user/hive/warehouse
  > bin/hadoop fs -chmod g+w /tmp
  > bin/hadoop fs -chmod g+w /user/hive/warehouse
  ```

### ③、Hive基本操作

```linux
#1、启动hive
> bin/hive
#2、查看数据库
> show databases;
#3、打开默认数据库
> use default;
#4、显示default数据库种的表
> show tables;
#5、创建一张表
> create table student(id int,name string);
#6、显示数据库中有几张表
> show tables;
#7、查看表的结构
> desc student;
#8、向表中插入数据
> insert into student values(1,"dinghao");
#9、查询表中数据
> select * from student;
#10、退出hive
> quit;
```

## 3、将本地文件导入Hive案列

需求：

​         将本地/opt/module/data/student.txt这个目录下的数据导入到hive的student(id int, name string)表中。

### ①、数据准备（可以创建一个目录存储数据）

```linux
# 在/opt/module/目录下创建datas
> mkdir /opt/module/datas
# 在/opt/module/datas/目录下创建students.txt文件并添加数据（数据可以先以“\t”分隔）
> touch students.txt
> vim students.txt
1001	zhangshan
1002	lishi
1003	zhaoliu
```

### ②、Hive实际操作

```linux
#1、启动hive
> bin/hive
#2、显示数据库
> show databases;
#3、使用default数据库
> use default;
#4、显示default数据库中的表
> show tables;
#5、删除已创建的student表
> drop table student;
#6、创建student表，并声明文件分隔符“\t”
> create table student(id int,name string) row format delimited fields terminated by '\t';
#7、加载/opt/module/datas/students.txt文件到student数据库表中
> load data local inpath '/opt/module/datas/students.txt' into table student;
#8、Hive查询结果
> select * from student;
```

### ③、问题总结

* 再打开一个客户端窗口启动hive，会产生java.sql.SQLException

  原因是，Metastore默认存储在自带的derby数据库中，推荐使用MySQL存储Metastore；

## 4、MySql 安装

### ①、安装包准备

* 查看mysql是否安装，如果安装了，卸载mysql

  ```linux
  > rpm -qa|grep mysql #查看mysql的版本
  > rpm -e --nodeps mysql-libs-5.1.73-7.el6.x86_64 #卸载
  > unzip mysql-libs.zip #详见相关资料里面的资料包
  ```

### ②、安装MySql服务器

```linux
> rpm -ivh MySQL-server-5.6.24-1.el6.x86_64.rpm #安装mysql服务端
> cat/root/.mysql_secret #查看随机生成的mysql密码
> service mysql start #启动mysql服务
> service mysql status #查看MySQL服务状态
```

### ③、安装MySql客户端

```linux
#1、安装mysql客户端
> rpm -ivh MySQL-client-5.6.24-1.el6.x86_64.rpm
#2、连接mysql
> mysql -uroot -pxxxxxx
#3、修改密码
> set password=password('xxxxxx');
#4、退出MySQL
> exit;
```

### ④、MySql中user表中主机配置

配置只要是root用户+密码，在任意主机上都能登陆Mysql数据库

```linux
#1、进入mysql
> mysql -uroot -pxxxx
#2、显示数据库
> show databases;
#3、使用mysql数据库
> use default;
#4、展示mysql数据库中的所有表
> show tables
#5、展示user表的结构
> desc user;
#6、查询user表
> select User, Host, Password from user;
#7、修改user表，把Host列内容修改为“%”
> update user set host='%' where host='localhost';
#8、删除root用户的其他host
> delete from user where host ='hadoop102';
.......
#9、刷新
> flush privileges;
#10、退出
> quit;
```

## 5、Hive元数据配置到MySql

### ①、驱动拷贝

```linux
#1、在/opt/software/mysql-libs目录下解压mysql-connector-java-5.1.27.tar.gz驱动包
> tar -zxvf mysql-connector-java-5.1.27.tar.gz
#2、拷贝mysql-connector-java-5.1.27-bin.jar到/opt/module/hive/lib/
> cp /opt/software/mysql-libs/mysql-connector-java-5.1.27/mysql-connector-java-5.1.27-bin.jar/opt/module/hive/lib/
```

### ②、配置Metastore到MySql

```linux
#1、在/opt/module/hive/conf目录下创建一个hive-site.xml
> touch hive-site.xml
> vi hive-site.xml
#2、根据官方文档配置参数，拷贝数据到hive-site.xml文件中
https://cwiki.apache.org/confluence/display/Hive/AdminManual+MetastoreAdmin
```

```hive-site.xml
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
# 数据库url
	<property>
	  <name>javax.jdo.option.ConnectionURL</name>
	  <value>jdbc:mysql://hadoop102:3306/metastore?createDatabaseIfNotExist=true</value>
	  <description>JDBC connect string for a JDBC metastore</description>
	</property>
# mysql驱动
	<property>
	  <name>javax.jdo.option.ConnectionDriverName</name>
	  <value>com.mysql.jdbc.Driver</value>
	  <description>Driver class name for a JDBC metastore</description>
	</property>
# 账号
	<property>
	  <name>javax.jdo.option.ConnectionUserName</name>
	  <value>root</value>
	  <description>username to use against metastore database</description>
	</property>
# 密码
	<property>
	  <name>javax.jdo.option.ConnectionPassword</name>
	  <value>000000</value>
	  <description>password to use against metastore database</description>
	</property>
</configuration>
```

* 注：如果配置完成后，如果启动hive异常，可以重新启动虚拟机（重启后，别忘了启动hadoop集群）

### ③、多窗口启动Hive测试

* 启动hive后，回到MySQL窗口查看数据库，显示增加了metastore数据库。

## 6、HiveJDBC访问

### ①、启动hiveserver2服务

``` linux
> bin/hiveserver2 #启动后，页面保持，重新开启一个窗口执行下面的操作
```

### ②、启动beeline

```linux
> bin/beeline
```

### ③、连接hiveserver2

```linux
> !connect jdbc:hive2://hadoop102:10000 #之后输入账号密码（密码为空，直接回车即可）
```

## 7、Hive常用交互命令

```linux
#1、查看hive指令的相关参数
> bin/hive help
#2、“-e”不进入hive的交互窗口执行sql语句
> bin/hive -e "select * from student;"
#3、“-f”执行脚本中的sql语句
> bin/hive -f xxxx.sql 
```

## 8、Hive其他命令操作

```linux
#1、在hive客户端下，如何查看hdfs文件系统
> dfs -ls /;
#2、在hive客户端下，如何查看本地文件系统
> ! ls /opt/module/datas;
#3、查看hive中输入的所有的历史命令
> cat /home/atguigu/.hivehistory
```

## 9、Hive常见属性配置

### ①、Hive数据仓库位置配置(正常不需要调整)

* 1、Default数据仓库的最原始位置在hdfs上的：/user/hive/warehouse路径下。

* 2、在仓库目录下，没有对默认的数据库default创建文件夹。如果某张表属于default数据库，直接在数据仓库目录下创建一个文件夹。

* 3、修改default数据仓库原始位置（将hive-default.xml.template如下的配置信息拷贝到hive-site.xml文件中）

  ```xml
  <property>
  <name>hive.metastore.warehouse.dir</name>
  <value>/user/hive/warehouse</value>
  <description>location of default database for the warehouse</description>
  </property>
  ```

  ```linux
  # 配置同组用户又执行权限
  > bin/hdfs dfs -chmod g+w /user/hive/warehouse
  ```

### ②、查询后信息显示配置

* 在hive-site.xml文件中添加如下配置信息，就可以实现显示当前数据库，以及查询表的头信息配置。

  ```xml
  <property>
  	<name>hive.cli.print.header</name>
  	<value>true</value>
  </property>
  <property>
  	<name>hive.cli.print.current.db</name>
  	<value>true</value>
  </property>
  ```

### ③、Hive运行日志信息配置

* 1、Hive的log默认存放在/tmp/atguigu/hive.log目录下（当前用户下）

* 2、修改hive的log存放日志到/opt/module/hive/logs

  ```linux
  > cd /opt/module/hive/conf
  > mv hive-log4j.properties.template hive-log4j.properties
  > vim hive-log4j.properties
  hive.log.dir=/opt/module/hive/logs
  ```

### ④、参数配置方式

* 1、hive客户端下，查看当前所有的配置信息

  ```linux
  > set;
  ```

* 2、参数的配置的三种方式

  * Ⅰ、配置文件方式

    * 默认配置文件：hive-default.xml
    * 用户自定义配置文件：hive-site.xml
    * 注意：用户自定义配置会覆盖默认配置。另外，Hive也会读入hadoop的配置，因为Hive是作为Hadoop客户端启动的，Hive的配置会覆盖Hadoop的配置。配置文件的设定对本机启动的所有Hive进程都有效。

  * Ⅱ、命令行方式

    ```linxu
    > bin/hive -hiveconf mapred.reduce.tasks=10
    > set mapred.reduce.tasks;
    ```

  * Ⅲ、参数声明方式

    ```linux
    # 可以在HQL中使用SET关键字设定参数，但仅对本次hive启动有效。
    ```