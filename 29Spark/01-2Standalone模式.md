# Standalone模式

* Standalone模式是Spark自带的资源调度引擎，构建一个由Master+Slave构成的Spark集群，Spark运行在集群中。
* 这个要和Hadoop中的Standalone区别开。这里的Standalone是指只用Spark来搭建一个集群，不需要借助其他的框架。是相对于Yarn和Mesos来说的。

## 1、安装

### ①、集群规划

|       | hadoop201 | hadoop202 | hadoop203 |
| :---: | :-------: | :-------: | :-------: |
| Spark |  Master   |  Worker   |  Worker   |
|       |  Worker   |           |           |

### ②、安装

```linux
> tar -zxvf spark-2.1.1-bin-hadoop2.7.tgz -C /opt/module/
> mv spark-2.1.1-bin-hadoop2.7 spark-standalone
> cd conf
conf > mv slaves.template slaves
conf > vim slaves
hadoop201
hadoop202
hadoop203
conf > mv spark-env.sh.template spark-env.sh
conf > vim spark-env.sh
SPARK_MASTER_HOST=hadoop102
SPARK_MASTER_PORT=7077
```

### ③、使用

```linux
> sbin/start-all.sh
> bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master spark://hadoop201:7077 \
--executor-memory 2G \
--total-executor-cores 2 \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
# 配置Executor可用内存为2G，使用CPU核数为2个
```

| 参数                       | 解释                                                         | 可选值举例                                          |
| -------------------------- | ------------------------------------------------------------ | --------------------------------------------------- |
| --class                    | Spark程序中包含主函数的类                                    |                                                     |
| --master                   | Spark程序运行的模式                                          | 本地模式：local[*]、spark://hadoop102:7077、   Yarn |
| --executor-memory   1G     | 指定每个executor可用内存为1G                                 | 符合集群内存配置即可，具体情况具体分析。            |
| --total-executor-cores   2 | 指定所有executor使用的cpu核数为2个                           | 同上                                                |
| --executor-cores           | 指定每个executor使用的cpu核数                                | 同上                                                |
| application-jar            | 打包好的应用jar，包含依赖。这个URL在集群中全局可见。 比如hdfs:// 共享存储系统，如果是file://   path，那么所有的节点的path都包含同样的jar | 同上                                                |
| application-arguments      | 传给main()方法的参数                                         | 同上                                                |

## 2、配置历史服务器

* 由于spark-shell停止掉后，hadoop102:4040页面就看不到历史任务的运行情况，所以开发时都配置历史服务器记录任务运行情况

```linux 
#修改spark-default.conf.template名称
#修改spark-default.conf文件，配置日志存储路径，并分发
conf > mv spark-defaults.conf.template spark-defaults.conf
conf > vim spark-defaults.conf
spark.eventLog.enabled          true
spark.eventLog.dir              hdfs://hadoop201:9000/directory
conf > xsync spark-defaults.conf

#启动Hadoop集群，HDFS上的目录提前存在。
hadoop > sbin/start-dfs.sh
> hadoop fs -mkdir /directory

#修改spark-env.sh文件，添加如下配置
conf > vim spark-env.sh
export SPARK_HISTORY_OPTS="
-Dspark.history.ui.port=18080 
-Dspark.history.fs.logDirectory=hdfs://hadoop201:9000/directory 
-Dspark.history.retainedApplications=30"
# 参数1含义：WEBUI访问的端口号为18080
# 参数2含义：指定历史服务器日志存储路径
# 参数3含义：指定保存Application历史记录的个数，如果超过这个值，旧的应用程序信息将被删除，这个是内存中的应用数，而不是页面上显示的应用数
```

## 3、配置高可用（HA）

```linux
#（0）停止集群
> sbin/stop-all.sh
#（1）Zookeeper正常安装并启动（基于以前讲的数仓项目脚本）
> zk.sh start
#（2）修改spark-env.sh文件添加如下配置：
> vi spark-env.sh
注释掉如下内容：
#SPARK_MASTER_HOST=hadoop201
#SPARK_MASTER_PORT=7077
添加上如下内容。配置由Zookeeper管理Master，在Zookeeper节点中自动创建/spark目录，用于管理：
export SPARK_DAEMON_JAVA_OPTS="
-Dspark.deploy.recoveryMode=ZOOKEEPER 
-Dspark.deploy.zookeeper.url=hadoop201,hadoop202,hadoop203 
-Dspark.deploy.zookeeper.dir=/spark"
#（3）分发配置文件
> xsync spark-env.sh
#（4）在hadoop201上启动全部节点
> sbin/start-all.sh
#（5）在hadoop202上单独启动master节点
> sbin/start-master.sh
#（6）在启动一个hadoop202窗口，将/opt/module/spark-local/input数据上传到hadoop集群的/input目录
> hadoop fs -put /opt/module/spark-local/input/ /input
#（7）Spark HA集群访问
> bin/spark-shell \
--master spark://hadoop201:7077,hadoop202:7077 
#参数：--master spark://hadoop102:7077指定要连接的集群master
#（8）执行WordCount程序
scala>sc.textFile("hdfs://hadoop201:9000/input").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect
```

## 4、运行流程

> Spark有standalone-client和standalone-cluster两种模式，主要区别在于：Driver程序的运行节点

* standalone-client：Driver程序运行在客户端，适用于交互、调试，可以立即看到程序的输出结果
* standalone-cluster：Driver程序运行在由Master启动的Worker节点，适用于生产环境。

### ①、客户端模式

```linux
bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master spark://hadoop201:7077,hadoop202:7077 \
--deploy-mode client \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
```

### ②、集群模式

```linux
bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master spark://hadoop201:7077,hadoop202:7077 \
--deploy-mode cluster \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
```