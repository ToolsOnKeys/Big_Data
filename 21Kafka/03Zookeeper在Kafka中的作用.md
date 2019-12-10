# Zookeep在Kafka中的作用

## 1、leader选举和follower信息同步

* Kafka集群的broker，和Consumer都需要连接Zookeeper。Producer直接连接Broker

* Producer把数据上传到Brokeer，Producer可以指定数据有几个分区、几个备份。

* leader处理partition的所有读写请求，于此同时，follower会定期的复制leader上地数据。

* 如果leader发生故障或者挂掉，一个新的leader被选举并接收客户端地消息。Kafka确保从同步副本列表中选举一个副本为leader。

  follower的同步机制：（待总结）

* Topic分区被放在不同地Broker中，保证Producer和Consumer错开访问Broker，避免了访问单个Broker造成过度地IO压力，是的负载均衡。

## 2、Zookeeper在Kafka中地作用

### ①、Broker注册

* Ⅰ、Broker是分布式部署并且之间相互独立，但是需要有一个注册系统能够将整个集群中的Broker管理起来，此时就使用到了Zookeeper。在Zookeeper上会又有个专门用来进行Broker服务器列表记录地节点：/brokers/ids
* Ⅱ、每个Broker在启动是，都会在Zookeeper上进行注册，即到/brokers/ids下创建属于自己地节点，如：/brokers/ids/[0....N]
* Ⅲ、Kafka使用了全部唯一地数字来指代每个Broker服务器，不同地Broker必须使用不同地BrokerID进行注册，创建完节点后，每个Broker就会将自己地IP地址和端口信息记录到该节点中去。其中，Broker创建的节点类型是临时节点，一旦Broker宕机，则对应的临时节点也会被自动删除。

### ②、Topic注册

* Ⅰ、在Kafka中，同一个Topic地消息会被分成多个分区，并将其分布到多个Broker上，这些分区信息及与Broker地对应关系也都是有Zookeeper在维护，由专门的节点来记录，如

  /brokers/topics

* Ⅱ、Kafka中每个Topic都会以/brokers/topics/[topic]的形式被记录，如：/brokers/topics/login和/brokers/topics/search等。Broker服务器启动后，会到对应Topic节点（/brokers/topics）上注册自己的Broker ID并写入针对该Topic的分区总数，如/brokers/topics/login/3->2，这个节点表示Broker ID为3的一个Broker服务器，对于“login”这个Topic的消息，提供了两个分区进行消息存储，同样，这个分区节点也是临时节点。

### ③、生产者负载均衡

* Ⅰ、由于同一个Topic消息会被分区并将其分布在多个Broker上，因此，生产者需要将消息合理的发送到这些分布式的Broker上，那么如何实现生产者的负载均衡，Kafka支持传统的负载均衡，也支持Zookeeper方式实现负载均衡。
* Ⅱ、四层负载均衡，根据生产者的IP地址和端口来为其确定一个相关联的Broker。通常，一个生产者只会对应单个Broker，然后该生产者产生的消息都发往该Broker。这种方式逻辑简单，每个生产者不需要同其他系统建立额外的TCP连接，只需要和Broker维护单个TCP连接即可。但是，其无法做到真正的负载均衡，因为实际系统中的每个生产者产生的消息量及每个Broker的消息存储量都是不一样的，如果有些生产者产生的消息远多于其他生产者的话，那么会导致不同的Broker即受到的消息总数差异巨大，同时，生产者也无法实时感知到Broker的新增和删除。
* Ⅲ、使用Zookeeper方式实现负载均衡，由于每个Broker启动时，都会完成Broker注册过程，生产者会通过该节点的变化来动态的感知到Broker服务器列表的变更，这样就可以实现动态的负载均衡机制。

### ④、消费者负载均衡

* Ⅰ、与生产者类似，Kafka中的消费者同样需要进行负载均衡来实现多个消费者合理的从对应的Broker服务器上接收消息，每个消费者分组包含若干消费者，每条消息都只会发送给分组中的一个消费者，不同的消费者分组消费自己特定的Topic下面的消息，互不干扰。

### ⑤、分区 与 消费者的关系

* Ⅰ、消费组（Consumer Group）：消费组下有多个Consumer（消费者）。对于每个消费者组，Kafka都会为其分配一个全局唯一的Group ID，Group内部的所有消费者共享该ID。订阅的Topic下的每个分区只能分配给某个group下的一个consumer（当然该分区还可以被分配给其他的group）。同时，Kafka为每个消费者分配了一个Consumer ID，通常采用“Hostname：UUID”形式来表示。

* Ⅱ、在Kafka中，规定了每个消息分区只能被同组的一个消费者进行消费，因此，需要在Zookeeper上记录消息分区与Consumer之间的关系，每个消费者一旦确定了对一个消息分区的消费权力，需要将其Consumer ID 写入到Zookeeper对应消息分区的临时节点上，例如：

  /consumers/[group_id]/owners/[topic]/[broker_id-partition_id]

  其中，[broker_id-partition_id]就是一个消息分区的表示，节点内容就是该消息分区上消费者的Consumer ID。

### ⑥、消息 消费进度Offset记录

* Ⅰ、在消费者对指定消息分区进行消息消费的过程中，需要定时地建分区消息地消费进度Offset记录到Zookeeper上，以便在该消费者进行重启或者其他消费者重新接管该消息分区的消息后，能够从之前的进度开始继续进行消息消费。Offset在Zookeeper中由一个专门的节点进行记录，其节点路径为：

  /consumers/[group_id]/offsets/[topic]/[broker_id-partition_id]

  节点内容就是Offset的值

### ⑦、消费者注册

* Ⅰ、消费者服务器在初始化启动时加入消费者分组的步骤如下：

  * 注册到消费者分组。每个消费者服务器启动时，都回到Zookeeper的指定节点下创建一个属于自己的消费者节点，例如：/consumers/[group_id]/ids/[consumer_id]，完成节点创建后，消费者就会将自己订阅的Topic信息写入该临时节点。

  * 对消费者分组中的消费者的变化注册监听。每个消费者都需要关注所属消费者分组中其他消费者服务器的变化情况，即对/consumers/[group_id]/ids节点注册子节点变化的Watcher监听，一旦发现消费者新增或减少，就触发消费者的负载均衡。

  * 对Broker服务器变化注册监听。消费者需要对/broker/ids/[0-N]中的节点进行监听，如果发现Broker服务器列表发生变化，那么就根据具体情况来决定是否需要继续宁消费者负载均衡。

  * 进行消费者负载均衡。为了让同一个Topic下不同分区的消息尽量均衡的被多个消费者消费，消费而进行消费者与消息分区分配的过程，通常，对于一个消费者分组，如果组内的消费者服务器发生变更或Broker服务器发生变更，会触发消费者负载均衡。

    

    

### ⑧、Kafka在Zookeeper上的结构

![](D:\BigData\BigData\21Kafka\相关图片\Kafka在zookeeper上的结构.webp)

