# Kafka快速安装

## 1、安装配置

### ①、集群规划

| hadoop102 | hadoop103 | hadoop104 |
| :-------: | :-------: | :-------: |
|    zk     |    zk     |    zk     |
|   kafka   |   kafka   |   kafka   |

### ②、集群部署

```linux
# 1、解压安装包
> tar -zxvf kafka_2.11-0.11.0.0.tgz -C /opt/module/
# 2、修改解压后的文件名称
> mv kafka_2.11-0.11.0.0/ kafka
# 3、在/opt/module/kafka目录下创建logs文件
> mkdir logs
# 4、修改配置文件
> vi config/server.properties


#broker的全局唯一编号，不能重复
broker.id=0
#删除topic功能使能
delete.topic.enable=true
#处理网络请求的线程数量
num.network.threads=3
#用来处理磁盘IO的现成数量
num.io.threads=8
#发送套接字的缓冲区大小
socket.send.buffer.bytes=102400
#接收套接字的缓冲区大小
socket.receive.buffer.bytes=102400
#请求套接字的缓冲区大小
socket.request.max.bytes=104857600
#kafka数据存放的路径
log.dirs=/opt/module/kafka/data
#topic在当前broker上的分区个数
num.partitions=1
#用来恢复和清理data下数据的线程数量
num.recovery.threads.per.data.dir=1
#segment文件保留的最长时间，超时将被删除
log.retention.hours=168
#配置连接Zookeeper集群地址
zookeeper.connect=hadoop102:2181,hadoop103:2181,hadoop104:2181


# 5、配置环境变量（非必须）
> sudo vi /etc/profile

#KAFKA_HOME
export KAFKA_HOME=/opt/module/kafka
export PATH=$PATH:$KAFKA_HOME/bin

> source /etc/profile
# 6、分发安装包
> xsync kafka/
# 7、分别在hadoop103、hadoop104上修改/opt/module/kafka/config/server.properties中的broker.id=1、broker.id=2 【注：broker.id不得重复】
# 8、编写kafka集群群起群关脚本 kafka.sh

#!/bin/bash
case $1 in
"start"){
for i in hadoop101 hadoop102 hadoop103
do
 echo ==============$i================
 ssh $i /opt/module/kafka/bin/kafka-server-start.sh -daemon /opt/module/kafka/config/server.properties
done
};;
"stop"){
for i in hadoop101 hadoop102 hadoop103
do
 echo ==============$i=================
 ssh $i /opt/module/kafka/bin/kafka-server-stop.sh stop
done
};;
esac

# 9、开启集群
> bin/kafka-server-start.sh -daemo config/server.properties #单起
> kafka.sh start  群起
# 10、关闭集群
> bin/kafka-server-stop.sh stop #单关
> kafka.sh stop #群关
```

