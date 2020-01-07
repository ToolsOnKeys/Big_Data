# Yarn模式

* Spark客户端直接连接Yarn，不需要额外的构建Spark集群

## 1、安装使用

```linux
# 0）停止Standalone模式下的spark集群
> sbin/stop-all.sh
> zk.sh stop
> sbin/stop-master.sh
# 1）为了防止和Standalone模式冲突，再单独解压一份spark
> tar -zxvf spark-2.1.1-bin-hadoop2.7.tgz -C /opt/module/
# 2）进入到/opt/module目录，修改spark-2.1.1-bin-hadoop2.7名称为spark
> mv spark-2.1.1-bin-hadoop2.7/ spark
# 3）修改hadoop配置文件/opt/module/hadoop-2.7.2/etc/hadoop/yarn-site.xml，添加如下内容
> vi yarn-site.xml
<!--是否启动一个线程检查每个任务正使用的物理内存量，如果任务超出分配值，则直接将其杀掉，默认是true -->
<property>
     <name>yarn.nodemanager.pmem-check-enabled</name>
     <value>false</value>
</property>

<!--是否启动一个线程检查每个任务正使用的虚拟内存量，如果任务超出分配值，则直接将其杀掉，默认是true -->
<property>
     <name>yarn.nodemanager.vmem-check-enabled</name>
     <value>false</value>
</property>
# 4）分发配置文件
> xsync /opt/module/hadoop-2.7.2/etc/hadoop/yarn-site.xml
# 5）修改/opt/module/spark/conf/spark-env.sh，添加YARN_CONF_DIR配置，保证后续运行任务的路径都变成集群路径
> mv spark-env.sh.template spark-env.sh
> vi spark-env.sh

YARN_CONF_DIR=/opt/module/hadoop-2.7.2/etc/hadoop
# 6）启动HDFS以及YARN集群
> sbin/start-dfs.sh
> sbin/start-yarn.sh
# 7）执行一个程序
> bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master yarn \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
#参数：--master yarn，表示Yarn方式运行；--deploy-mod表示客户端方式运行程序
```

## 2、配置历史服务

* 由于是重新解压的Spark压缩文件，所以需要针对Yarn模式，再次配置一下历史服务器

```linux
# 1）修改spark-default.conf.template名称
> mv spark-defaults.conf.template spark-defaults.conf
# 2）修改spark-default.conf文件，配置日志存储路径，并分发
> vi spark-defaults.conf
spark.eventLog.enabled          true
spark.eventLog.dir               hdfs://hadoop201:9000/directory
# 3）修改spark-env.sh文件，添加如下配置：
> vi spark-env.sh
export SPARK_HISTORY_OPTS="
-Dspark.history.ui.port=18080 
-Dspark.history.fs.logDirectory=hdfs://hadoop201:9000/directory 
-Dspark.history.retainedApplications=30"
# 参数1含义：WEBUI访问的端口号为18080
# 参数2含义：指定历史服务器日志存储路径
# 参数3含义：指定保存Application历史记录的个数，如果超过这个值，旧的应用程序信息将被删除，这个是内存中的应用数，而不是页面上显示的应用数。
```

## 3、配置查看历史日志

* 为了从Yarn上关联到Spark历史服务器，需要配置关联路径

```linux
#1）修改配置文件/opt/module/spark/conf/spark-defaults.conf 添加如下内容：
spark.yarn.historyServer.address=hadoop102:18080
spark.history.ui.port=18080
# 2）重启Spark历史服务
> sbin/start-history-server.sh 
# 3）提交任务到Yarn执行
> bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master yarn \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
```

## 4、运行流程

> Spark有yarn-client和yarn-cluster两种模式，主要区别在于：Driver程序的运行节点。

* yarn-client：Driver程序运行在客户端，适用于交互、调试，可以立即看到程序的输出结果。
* yarn-cluster：Driver程序运行在有ResourceManager管理的NodeManager上，再启动的APPMaster，适用于生产环境

### ①、客户端模式（默认）

```linux
bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master yarn \
--deploy-mode client \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
```

### ②、集群模式

```linux
bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master yarn \
--deploy-mode cluster \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
```

