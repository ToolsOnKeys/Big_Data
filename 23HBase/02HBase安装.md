# HBase安装部署

## 1、Zookeeper正常部署

## 2、Hadoop正常部署

## 3、HBase安装

### ①、 HBase的解压

```linux
> tar -zxvf hbase-1.3.1-bin.tar.gz -C /opt/module
```

### ②、 HBase的配置文件

* 1）hbase-env.sh修改内容：

  ```sh
  export JAVA_HOME=/opt/module/jdk1.8.0_144
  export HBASE_MANAGES_ZK=false
  ```

* 2）hbase-site.xml修改内容：

  ```xml
  <configuration>
  	<property>
  		<name>hbase.rootdir</name>
  		<value>hdfs://hadoop102:9000/HBase</value>
  	</property>
  
  	<property>
  		<name>hbase.cluster.distributed</name>
  		<value>true</value>
  	</property>
  
     <!-- 0.98后的新变动，之前版本没有.port,默认端口为60000 -->
  	<property>
  		<name>hbase.master.port</name>
  		<value>16000</value>
  	</property>
  
  	<property>   
  		<name>hbase.zookeeper.quorum</name>
  	    <value>hadoop102,hadoop103,hadoop104</value>
  	</property>
  
  	<property>   
  		<name>hbase.zookeeper.property.dataDir</name>
  	    <value>/opt/module/zookeeper-3.4.10/zkData</value>
  	</property>
  </configuration>
  ```

* 3）regionservers

  ```regionservers
  hadoop102
  hadoop103
  hadoop104
  ```

* 4）软连接hadoop配置文件到HBase：

  ```linux
  > ln -s /opt/module/hadoop-2.7.2/etc/hadoop/core-site.xml /opt/module/hbase/conf/core-site.xml
  > ln -s /opt/module/hadoop-2.7.2/etc/hadoop/hdfs-site.xml /opt/module/hbase/conf/hdfs-site.xml
  ```

### ③、 HBase远程发送到其他集群

``` linux
 > xsync hbase/
```

## 4、HBase服务的启动

### ①、启动方式一：单起

```linux
> bin/hbase-daemon.sh start master
> bin/hbase-daemon.sh start regionserver
```

* 注意：如果集群之间的节点的时间不同步，会导致regionserver无法启动，抛出ClockOutOfSyncException异常。

* 修复提示：

  * 1、同步时间服务：date -s “1996-01-08 12：12：12”

  * 2、属性：hbase.master.maxclockskew设置更大的值

    ```xml
    <property>
            <name>hbase.master.maxclockskew</name>
            <value>180000</value>
            <description>Time difference of regionserver from master</description>
    </property>
    ```

### ②、启动方式二：群起

```linux
> bin/start-hbase.sh
> bin/stop-hbase.sh
```

