# MapReduce

## 1、MapReduce 概述

### ①、定义

* MapReduce是一个分布式运算程序的编程框架，使用用户开发“基于Hadoop的数据分析应用”的核心框架。
* MapReduce核心功能是用户编写的业务逻辑代码和自带默认组件整合成一个完整的分布式运算程序，并发运行在一个Hadoop集群上。

### ②、优缺点（特点）

* 优点

  ```优点
  1、MapReduce已于编程
  > 他简单的实现一些接口，就可以完成一个分布式程序，这个分布式程序可以分不到大量廉价的PC及其上运行。
  2、良好的扩展性
  > 当你的计算资源不能得到满足的时候，你就可以通过简单的增加机器来扩展你的计算能力
  3、高容错性
  > MapReduce设计的初衷就是使程序能够部署到廉价的PC机器上，这就要求它具有很高的容错性。比如其中一台机器挂了，它可以把上面的计算任务转移到另一台节点上运行，不至于这个任务运行失败，而且这个过程不需要人工参与，而完全由Hadoop内部完成的
  4、适合PB级别以上的海量数据的离线处理
  > 可以实现上千台服务器集群并发工作，提供数据处理能力。
  ```

* 缺点

  ```缺点
  1、不擅长实时计算
  > MapReduce无法想MySQl一样，在毫秒或者秒级内返回结果
  2、不擅长流式计算
  > 流式计算的输入数据是动态，而MapReduce的输入数据集是静态，不能动态变化。这是因为MapReduce自身的设计特点决定了数据资源必须是静态的。
  3、不擅长DAG（有向图）计算
  > 多个应用程序存在依赖关系，后一个应用程序的输入为前一个的输出。在这种情况下，MapReduce并不是不能做，而是使用后，每个MapReduce作业的输出结果都会写入到磁盘，会造成大量的IO操作，导致性能非常低下。
  ```

### ③、核心思想

> 需求：统计其中每个单词出现的次数（查询结果a-p和q-z）

* 1、MapReduce运行程序一般需要分成两个阶段：Map阶段和Reduce阶段
* 2、Map阶段的并发MapTask，完全并行运行，不不相干
  * 读数据，并按行处理
  * 按空格切分行内单词
  * KV键值对（单词，1）
  * 将所有的KV键值对中的单词，按照单词首字母，分成2个分区（a-p，q-z）溢写道磁盘。

* 3、Reduce阶段的并发ReduceTask，完全互不相干，但是他们的数据依赖于上一个阶段的所有MapTask并发实例的输出。
* 4、MapReduce编程模型只能包含一个Map阶段和一个Reduce阶段，如果用户的业务逻辑非常复杂，那么就只能多个MapReduce程序，串行运行。
* 总结：分析WordCount数据流走向深入理解MapReduce核心思想

### ④、MapReduce进程

> 一个完成的MapReduce程序在分布式运行时有三类实例进程

* 1、MrAppMaster：负责整个程序的过程调度及状态协调。
* 2、MapTask：负责Map阶段的整个数据处理流程。
* 3、ReduceTask：负责Reduce阶段的整个数据处理流程。

### ⑤、官方WordCount源码

> 采用反编译工具反编译源码，发现WordCount案列有Map类、Reduce类和驱动类。且数据的类型是Hadoop自身封装的序列化类型。

### ⑥、常用数据序列化类型

| Java类型 | Hadoop Writable类型 |
| -------- | ------------------- |
| Boolean  | BooleanWritable     |
| Byte     | ByteWritable        |
| Int      | IntWritable         |
| Float    | FloatWritable       |
| Long     | LongWritable        |
| Double   | DoubleWritable      |
| String   | Text                |
| Map      | MapWritable         |
| Array    | ArrayWritable       |

### ⑦、MapReduce编程规范

* 1、Mapper阶段

  > 1、用户自定义的Mapper要继承自己的父类
  >
  > 2、Mapper的输入数据是KV对的形式（KV的类型可自定义）
  >
  > 3、Mapper中的业务逻辑写在map（）方法中
  >
  > 4、Mapper的输出数据是KV对的形式（KV的类型可以自定义）
  >
  > 5、map（）方法（MapTask进程）对每一个<K,V>调用一次

* 2、Reduce阶段

  > 1、用户自定义的Reducer要继承自己的父类
  >
  > 2、Reducer的输入数据类型对应Mapper的输出数据类型，也是 KV
  >
  > 3、Reducer的业务逻辑写在reduce（）方法中
  >
  > 4、ReduceTask进程对每一组相同k的<k,v>组调用一次reduce（）方法

* 3、Driver阶段

  > 相当于Yarn集群的客户端，用于提交我们整个程序到yarn集群，提交的是封装了MapReduce程序相关运行参数的job对象。

## 2、Hadoop序列化

### ①、序列化的概述

* 定义

  > 1、序列化就是把内存中的对象转换成字节序列（或其他数据传输协议）以便于存储到磁盘（持久化）和网络传输。
  >
  > 2、反序列化就是将收到的字节序列（或其他数据传输协议）或则是磁盘的持久化数据，转换成内存中的对象。

* 为什么要序列化

  > 内存数据存到磁盘，传输到其他地方，反序列化将磁盘的数据再注入内存。

* 为什么不用java的序列化

  > Java的序列化是一个重量级序列化框架（Serializable），一个对象被序列化后，会附带很多额外的信息（各种校验信息，header,继承体系等），不便于再网络中高效传输。所以，Hadoop自己开发了一台序列化机制（Writable）

* Hadoop 序列化的特点

  > 1、紧凑：高效使用内存空间。
  >
  > 2、快速：读写数据的额外开销小。
  >
  > 3、可扩展：随着通信协议的升级而可升级。
  >
  > 4、互操作：支持多语言的交互

### ②、自定义bean对象实现序列化接口（Writable）

> 具体实现bean对象的序列化步骤如下7步：

* 1、必须实现Writable接口

* 2、反序列化，需要反射调用空参构造函数，所以必须有空仓构造

  ```java
  public FlowBean() {
  	super();
  }
  ```

* 3、重写序列化方法

  ```java
  @Override
  public void write(DataOutput out) throws IOException {
  	out.writeLong(upFlow);
  	out.writeLong(downFlow);
  	out.writeLong(sumFlow);
  }
  ```

* 4、重写反序列化方法

  ```java
  @Override
  public void readFields(DataInput in) throws IOException {
  	upFlow = in.readLong();
  	downFlow = in.readLong();
  	sumFlow = in.readLong();
  }
  ```

* 5、注意反序列化的顺序和序列化的顺序完全一致

* 6、要先把结果显示再文件中，需要重写toString（），可用”\t“分开，方便后续用。

* 7、如果需要将自定义的bean放在key中传输，则还需要实现Comparable接口，因为MapReduce框中的Shuffle过程要求对key必须能排序。

  ```java
  @Override
  public int compareTo(FlowBean o) {
  	// 倒序排列，从大到小
  	return this.sumFlow > o.getSumFlow() ? -1 : 1;
  }
  ```

  

## 3、MapReduce框架原理

### ①、InputFormat 数据输入

>  Input—InputFormat—》Mapper—Shuffle—》Reducer—OutputFormat—》Output

* 切片与MapTask并行度决定机制

  ```
  > 1、问题引入：
    * MapTask的并行度决定Map阶段的任务处理并发读，进而影响到整个Job的处理进度。
  > 2、MapTask并行度决定机制
    * 数据块：Block是HDFS物理上把数据分成一块一块
    * 数据切片：数据接片只是再逻辑上对输入进行分片，并不会在磁盘上将其切分成片进行存储
  ```

  * 1、一个Job的Map阶段并行度由客户端再提交Job时的切片数决定。
  * 2、每一个Split切片分配一个MapTask并行实例处理。
  * 3、默认情况下，切片大小=BlockSize。
  * 4、切片时不考虑数据集整体，而是逐个针对每一个文件单独切片。

* FileInputFormat切片源码解析（input.getSplits(job)）

  ```
  >1、程序先找到你数据存储的目录。
  >2、开始遍历处理（规划切片）目录下的每一个文件
  >3、遍历第一个文件ss.txt
     *获取文件大小：fs.sizeOf(ss.txt)
     *计算切片大小：computeSplitSize（Math.max(minSize,Math.min*(maxSize,blocksize))）=blocksize=128M
     *默认情况下，切片大小=blocksize
     *开始切，形成1个切片：ss.txt-0:128M，第2个切片ss.txt-128:256M,第3个切片ss.txt-256M：300M（每次切片时，都要判断玩剩下的部分是否大于块的1.1倍，不大于1.1倍就划分一块切片）
     *将切片信息写道一个切片规划文件中
     *整个切片的核心过程再getSplit()方法中完成
     *InputSplit只记录了切片的元数据信息，比如起始位置、长度以及所在的节点列表等
  >4、提交切片规划文件到YARN上，YARN上的MrAppMaster就可以根据切片规划文件计算开启MapTask个数
  ```

* Job提交流程

  * 建立连接

    > 1、创建job的代理
    >
    > 2、判断时本地yarn还是远程

  * 提交job

    >1、创建给集群提交数据的Stag路径
    >
    >2、获取jobid，并创建Job路径
    >
    >3、拷贝jar包到集群中
    >
    >4、计算切片，生成切片规划文件
    >
    >5、向Stag路径写XML文件
    >
    >6、提交Job，返回提交状态

    

### ②、FileInputFormat切片机制

* 切片机制

  > 1、简单地按照文件的内容长度进行切片
  >
  > 2、切片的大小，默认等于Block大小
  >
  > 3、切片时不考虑数据集整体，而是逐个针对每一个文件单独切片

* FileInputFormat切片大小的参数配置

  * 1、源码中计算切片大小的公式：

    >Math.max(minSize,Math.min(maxSize,blockSize));
    >
    >mapreduce.input.fileinputformat.split.minsize=1;//默认值就是1
    >
    >mapreduce.input.fileinputformat.split.maxsize=Long.MaxValue;//默认值就是Long.MAXValue
    >
    >因此，默认情况下，切片的大小=blocksize

  * 切片大小的设置

    > 1、maxsize（切片的最大值）：参数如果调用的比blockSize小，则会让切片变小，而且就等于配置的这个参数的值。
    >
    > 2、minsize（切片的最小值）：参数调的比blockSize大，则可以让切片变得比blockSize还大

  * 获取切片信息的API

    > //获取切片的文件名称
    >
    > String name = inputSplit.getPath().getName();
    >
    > //根据文件类型获取切片信息
    >
    > FileSplit inputSplit = (FileSplit) context.getInputSplit();

  

### ③、ConbineTextInputFormat切片机制

> 框架默认的TextInputFormat切片机制是对任务按照文件规划切片，不管文件多小，都会是一个的单独的切片，都会交给一个MapTask，这样如果有大量小文件，就会产生大量的MapTask，处理效率及其低下。

* 应用场景

  > CombineTextInputFormat用于小文件过多的场景，它可以将小文件从逻辑上规划到一个切片中，这个，多个小文件就可以交给一个MapTask处理了。

* 虚拟存储切片最大值设置

  > CombineTextInputFormat.setMaxInputSplitSize(job,4194304);//4M
  >
  > 注意：虚拟存储切片最大值设置最好根据实际的小文件的大小情况来设置具体的值。

* 切片机制

  >生成切片过程包括：虚拟存储过程和切片过程两部分
  >
  >1、虚拟存储过程：将输入目录下的所有文件大小，一次和设置的setMaxInputSplitSize值比较，如果不大于设置的最大值，逻辑上划分一个块。如果输入文件大于设置的最大值且大于两倍，那么一最大值切割一块；但剩下数据大小超过设置的最大值且不大于最大值两倍，此时将文件均分成两个虚拟存储块（防止出现太小切片）。
  >
  >2、切片过程：①、判断虚拟存储的文件大小是否大于setMaxInutSplitSize值，大于等于则单独形成一个切片。②、如果不大于则跟下一个虚拟存储文件进行合并，共同形成一个切片。

* 实现方式：

  > 在驱动类中添加代码如下：
  >
  > 1、设置inputFormat，如果不设置，默认用的是TextInputFormat.class
  >
  > job.setInputFormatClass(CombineTextInputFormat.class);
  >
  > 2、虚拟存储切片最大值设置（4m，或更大，根据实际情况设置）
  >
  > CombineTextInputFormat.setMaxInputSplitSize(job,大小)；

### ④、FileInputFormat实现类 

​            FileInputFormat 常见的接口是实现类包括：TextInputFormat、KeyValueTextInputFormat、NLineInputFormat、CombineTextInputFormat和自定义InputFormat等。

* TextInputFormat

  > TextInputFormat 是默认的FileInputFormat实现类。按行读取每条记录。键是存储该行在整个文件中的起始的字节偏移量（LongWritable类型）。只是这行的内容，不包括任何行的终止符（换行符和回车符），Text类型。
  >
  > eg:
  >
  > (0,dinghao dinghao)

* KeyValueTextInputFormat

  > 每一行军事一条记录，被分割符分隔为key，value。可以通过在驱动类中设置conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR，“\t”);来设定分隔符。默认分隔符是tab（\t）。
  >
  > eg:
  >
  > （line1,dinghao dinghao）
  >
  > 此时的key和value都是一个Text

* NLineInputFormat

  > 如果使用NlineInputFormat，代表每个map进程处理的InputSplit不在按Block块去划分，而是按NLineInputFormat指定的行N来划分。即输入文件的额总行数/N=切片数，如果不整除，切片数=商+1.

### ⑤、自定义InputFormat

* 自定义InputFormat的步骤如下：

  ```
  1、自定义个类继承FileImputFormat。
  2、改写RecordReader，实现一次读取一个完整文件并封装成KV。
  3、在输出时使用SequenceFileOutPutFormat输出合并文件。
  ```


## 4、MapReduce工作流程

* Shuffle过程详解：

  >1、MapTask

## 5、Shuffle机制

> Shuffle描述的是数据从Map端到Reduce端的过程，大致分为Map【分片】、Map端Shuffle【排序（sort）、溢写（spill）、合并（merge）】、Reduce端Shuffle【拉取拷贝（Copy）、合并排序（merge sort）】

* Map端的sort

  Map端的输出数据，先写环形缓存区kvbuffer，当环形缓冲区到达一个阈值（可以通过配置文件设置，默认80），便开始溢写，但溢写之前会有一个sort操作，这个sort操作先把Kvbuffer中数据按照partition值和key两个关键值来排序，移动的只是索引数据，排序结果是Kvmeta中数据按照partition为单位聚集在一起，同一个partition内的按照key有序。

* spill（溢写）

  当排序完成，便开始把数据刷到磁盘，刷磁盘的过程以分区为单位，一个分区写完，写下一个分区，分区内数据有序，最终实际上会多次溢写，然后生成多个文件。

* merge（合并）

  spill会生成多个小文件，对于Reduce端拉去数据是相当抵消的额，纳闷这时候就有了merge的过程，合并的过程也是同分片的合并成一个片段（segment），最终所有的segment组装成一个最终文件，那么合并过程就完成了。

### ①、Partition分区

* 问题引出

  要求将统计结果按照条件输出到不同的文件中（分区）。

* 默认Partitioner分区

  ```java
  public class HashPartitioner<K,V> extends Partitioner<K,V>{
      public int getPartition(K key,V value,int numReduceTasks){
          return (key.hashCode() & Integer.MAX_VALUE) % numReduceTasks;
      }
  }
  ```

  默认分区是根据key的hashCode对ReduceTasks个数取模得到的。用户没法控制key存储到那个分区。

* 自定义Partitioner步骤

  1、自定义类继承Partitioner，重写getPartition（）方法

  ```java
  public class CustomPartitioner extends Partitioner<Text,FlowBean>{
      @Override
      public int getPartition(Text key,FlowBean value,int numPartitions){
          //控制分区代码的逻辑部分；
          return partition；
      }
  }
  ```

  2、在Job驱动中，设置自定义的Partitioner

  ```java
  job.setPartitionerClass(CustomPartitioner.class);
  ```

  3、自定义Partition后，要根据自定义的Partitioner的逻辑设置相应数量的ReduceTask

  ```java
  job.setNumReduceTasks(5);
  ```

* 分区总结

  1、如果ReduceTask的数量大于getPartition的结果数，则会多产生几个空的输出文件part-r-000xx；

  2、如果1<ReduceTask的数量<getPartition的结果数，则会有一部分分区的数据无处安放，会报Exception；

  3、如果ReduceTask的数量=1，则不管MapTask端输出多少个分区文件，最终结果都交给这一个ReduceTask，最终就只会产生一个结果文件part-r-00000；

  4、分区号必须从零开始，逐一累加。

* 案列分析

```java
public class PhonePatitioner extends Partitioner<Text, PhoneBean> {
//根据手机号分区输出到不同的结果文档中。
	@Override
	public int getPartition(Text key, PhoneBean value, int numPartitions) {
		String phone = key.toString().substring(0, 3);
		switch (phone) {
		case "136":
			return 0;
		case "137":
			return 1;
		case "138":
			return 2;
		case "139":
			return 3;
		default:
			return 4;
		}
	}

}
```

### ②、WritableComparable排序

* 排序概述

  1、排序是MapReduce框架中最重要的操作之一；

  2、MapTask和ReduceTask均会对数据按照key进行排序。该操作属于Hadoop的默认行为。任何应用程序中的数据均会被排序，而不管逻辑是否需要。

  3、默认排序是按照字典排序，且实现该排序的方法是快速排序

  4、对于MapTask，它会将处理的结果暂时放到环形缓冲区中，当环形缓冲区使用率到一定阈值后，再对缓冲区中的数据进行一次快速排序，并将这些有序数据溢出写道磁盘上，而当数据处理完毕后，他会对磁盘上所有文件进行归并排序。

  5、对于ReduceTask，它从每个MapTask上远程拷贝相应的数据文件，如果文件大小超过一定阈值，则溢写到磁盘上，否则存储在内存中。如果磁盘上文件数量达到一定阈值，则进行一次归并排序生成一个更大文件；如果内存中文件大小或者数量超过一定阈值，则进行一次合并后将数据溢写到磁盘上。当所有数据拷贝完毕后，ReduceTask统一对内存和磁盘上的所有数据进行一次归并排序。

* 排序的分类

  1、部分排序：MapReduce根据输入记录的键对数据集排序。保证输出的每个文件内部有序。

  2、全排序：最终输出结果只有一个文件，且文件内部有序。实现方式时只设置一个ReduceTask。但该方法在处理大型文件时效率极低，因为一台机器处理所有文件，完全丧失了MapReduce所提供的并行架构。

  3、辅助排序（GroupingComparator分组）：在Reduce端对key进行分组。应用于：在接收的key为bean对象时，显然一个或几个字段相同（全部字段比较不相同）的key进入到同一个reduce方法时，可以采用分组排序。

  4、二次排序：在自定义排序过程中，如果compareTo中的判断条件为两个即为二次排序。

* 自定义排序WritableComparable

  原理分析：bean对象作为key传输，需要实现WritableComparable接口重写compareTo方法，就可以实现排序。

  ```java
  @Override
  public int compareTo(PhoneBean o) {
  	return (int)(this.getSum() - o.getSum());
  }
  ```

### ③、Combine合并

* 1、Combiner是MR程序中Mapper和Reducer之外的一种组件。

* 2、Combiner组件的父类就是Reducer。

* 3、Combiner和Reducer的区别：Combiner实在每一个MapTask所在的节点运行；Reducer是接收全局所有Mapper的输出结果

* 4、Combiner的意义就是对每一个MapTask的输出进行局部汇总，以减少网络传输量

* 5、Combiner能够应用的前提是不能影响最终的业务逻辑，而且，Combiner的输出kv应该跟Reducer的输入的kv类型要对应起来。

* 6、自定义Combiner实现步骤：

  * 1、自定义一个Combiner继承Reducer，重写Reduce方法

    ```java
    public class WordcountCombiner extends Reducer<Text, IntWritable, Text,IntWritable>{
    	@Override
    	protected void reduce(Text key, Iterable<IntWritable> values,Context context) throws IOException, InterruptedException {
            // 1 汇总操作
    		int count = 0;
    		for(IntWritable v :values){
    			count += v.get();
    		}
            // 2 写出
    		context.write(key, new IntWritable(count));
    	}
    }
    ```

  * 2、在Job驱动类中设置

    ```java
    job.setCombinerClass(WordcountCombiner.class);
    ```

### ④、GroupingComparator分组（辅助排序）

* 对Reduce阶段的数据根据某一个或几个字段进行分组。

* 分组排序步骤：

  * 1、自定义WritableComparator

    ```java
    public class OrderGroupingComparator extends WritableComparator {
    	public OrderGroupingComparator() {
    		super(OrderBean.class,true);
    	}
    
    	@Override
    	public int compare(WritableComparable a, WritableComparable b) {
    		OrderBean aBean = (OrderBean) a;
    		OrderBean bBean = (OrderBean) b;
    		return bBean.getOrderNo()-aBean.getOrderNo();
    	}
    }
    ```

  * 2、重写compare（）方法

  * 3、创建一个构造将比较对象的类传给父类。

## 6、MapTask工作机制

### ①、Read阶段

​       MapTask通过用户编写的RecordReader，从输入InputSplit中解析出一个一个key/value。

### ②、Map阶段

​       该节点主要是将解析出的key/value交给用户编写map（）函数，并产生一系列的key/value。

### ③、Collect收集阶段

​        在用户编写map（）函数中，当数据处理完成后，一般会调用OutputCollector.collect（）输出结果。在该函数内部，它会将生成的key/value分区（调用Partitioner），并写入一个环形内存缓冲区

### ④、Spill阶段

​       即“溢写”，当环形缓冲区满后，MapReduce会将数据写到本地磁盘上，生成一个临时文件。需要注意的是，将数据写入本地磁盘之前，想要对数据进行一次本地排序，并在必要时对数据进行合并、压缩等操作。

 溢出阶段的详情：

* 1、利用快速排序算法对缓存区数据进行排序，排序方式时，先按照分区编号Partition进行排序，然后按照key进行排序。这样，经过排序后，数据以分区为单位聚集在一起，且同一分区的所有数据按照key有序。
* 2、按照分区编号由小到大依次将每个分区中的数据写入任务工作目录下的临时文件output/spillN.out（N表示当前溢写次数）中。如果用户设置了Combiner，则写入文件前，对每个分区中数据进行一次聚集操作。
* 3、将分区数据的元信息写道内存索引数据结构SpillRecord中，其中每个分区的元信息包括在临时文件中的偏移量、压缩前数据的大小和压缩后的数据大小。如果当前内存索引大小超过1MB，则将内存索引写道文件output/spillN.out.index中。

### ⑤、Combine阶段

​       当所有数据处理完成后，MapTask对所有的临时文件进行一次合并，以确保最终只会生成一个数据文件。

### 最后：

​        当所有数据处理完成后，MapTask会将所有的临时文件合并成一个大文件，并保存到文件output/file.out中，同时生成相应的索引文件output/file.out.index。

​       在进行文件合并过程中，MapTask以分区为单位进行合并。对于某个分区，它将次啊用多轮递归合并的方式。每轮合并io.sort.factor（默认10）个文件，并将产生的文件重新加入待合并的列表中，对文件排序后，重复以上过程，直到最终得到一个大文件。

​       让每个MapTask最终只生成一个数据文件，可以避免同时打开大量文件和同时读取大量小文件产生的随机读取带来的开销。

## 7、ReduceTask工作机制

### ①、工作机制

* Copy阶段：

  ReduceTask从各个MapTask上远程拷贝一片数据，并针对某一片数据，如果其大小超过一定阈值，则写道磁盘上，否则直接放入到 内存中。

* Merge阶段：

  在远程拷贝数据的同时，ReduceTask启动了两个后台线程对内存和磁盘上的文件进行合并，以防止内存使用过多或磁盘上文件过多。

* Sort阶段：

  按照MapReduce语义，用户编写reduce（）函数输入数据时按key进行聚集的一组数据。为了将key相同的数据聚在一起，Hadoop采用了基于排序的策略。由于各个MapTask已经实现了自己的处理结果进行了局部排序，因此，ReduceTask只需对所有数据进行一次归并排序即可。

* Reduce阶段：

  reduce（）函数将计算结果写到HDFS上

### ②、设置ReduceTask并行度（个数）

ReduceTask的并行度同样影响整个Job的执行并发度和执行效率，但与MapTask的并发数由切片数决定不同，ReduceTask数量的决定是可以直接手动设置：

```java
//默认值是1，手动设置4
job.setNumReduceTasks(4);
```

### ③、注意事项

* ReduceTask=0，表示没有Reduce阶段，输出文件个数和Map个数一致。
* ReduceTask默认值就是1，所以输出文件个数为一个。
* 如果数据分布不均匀，就有可能在Reduce阶段产生数据倾斜
* ReduceTask数量并不是任意设置，还要考虑业务逻辑需求，有些情况下，需要计算全局汇总结果，就只能有一个ReduceTask
* 具体多少个ReduceTask，需要根据集群性能而定。
* 如果分区数不是1，但是ReduceTask为1，是否执行分区过程。答案是：不执行分区过程。因为在MapTask的源码中，执行分区的前提是先判断ReduceNum的个数是否大于1。不大于1肯定不执行。

## 8、OutputFormat数据输出

### ①、OutputFormat接口实现类

OutputFormat时MapReduce输出的基类，所有实现MapReduce输出都实现了OutFormat接口：

* 文本输出TextOutputFormat

  ​         默认的输出格式是TextOutputFormat，他把每条记录写为文本行。他的键和值可以是任意类型，因为TextOutputFormat调用toString（）方法把他们转换为字符串。

* SequenceFileOutputFormat

  ​          将SequenceFileOutputFormat输出作为后续MapReduce任务的输入，这便是一种好的输出格式，因为它的格式紧凑，很容易被压缩

* 自定义OutputFormat

  * 1、使用场景：为了实现控制最终文本的输出路径和输出格式，可以自定义OutputFormat。
  * 2、自定义OutputFormat步骤：自定义一个类继承FileOutputFormat；改写RecordWriter，具体改写输出数据的方法write（）；

## 9、Join 多种应用

### ①、Reduce Join工作原理

* Map端的主要工作：为来自不同表或文件的key/value对，打标签以区别不同来源的记录。然后用链接字段作为key，其余部分和新加的表只作为value，最后进行输出。
* Reduce端主要工作：在Reduce端以链接字段作为key的分组已经完成，我们只需要在每个分组当中将那些来源不同文件的记录（在Map阶段已经打标志）分开，最后进行合并就OK了。

### ②、Map Join工作原理

* 1、使用场景：适用于一张小表，一张很大的表。

* 2、优点：在Reduce端处理过多的表，非常容易产生数据倾斜。采用Map端缓存多张表，提前处理与吴逻辑，这样增加了Map端业务，减少了Reduce短的数据的压力，尽可能的减少了数据倾斜。

  ```java
  public class MyMapJoinMapper extends Mapper<LongWritable, Text, Text, NullWritable>{
  	HashMap<String, String> map = new HashMap<>();
  	@Override
  	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context)
  			throws IOException, InterruptedException {
  		String[] fields = value.toString().split("\t");
  		String second = map.get(fields[1]);
  		context.write(new Text(fields[0]+"\t"+second+"\t"+fields[2]), NullWritable.get());
  	}
  
  	@Override
  	protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context)
  			throws IOException, InterruptedException {
  		URI[] cacheFiles = context.getCacheFiles();
  		Path path = new Path(cacheFiles[0]);
  		FileSystem fSystem = FileSystem.get(context.getConfiguration());
  		FSDataInputStream inputStream = fSystem.open(path);
  		BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream));
  		String line;
  		while((line = bReader.readLine())!=null) {
  			String[] fields = line.split("\t");
  			map.put(fields[0], fields[1]);
  		}
  		IOUtils.closeStream(bReader);
  	}
  }
  ```

* 3、具体办法：采用DistrubutedCache：在Mapper的setup阶段，将文件读取到缓存集合中；在驱动函数中加载缓存。

  ```java
  public class MyMapJoinDriver {
  	public static void main(String[] args) throws Exception {
  		Configuration configuration = new Configuration();
  		Job job = Job.getInstance(configuration);
  		job.setJarByClass(MyMapJoinDriver.class);
  		job.setMapperClass(MyMapJoinMapper.class);
  		job.setOutputKeyClass(Text.class);
  		job.setOutputValueClass(NullWritable.class);
  		FileInputFormat.setInputPaths(job, new Path("D:/input/order.txt"));
  		FileOutputFormat.setOutputPath(job, new Path("D:/joinoutput"));
  		job.addCacheFile(new URI("file:///D:/input/pd.txt"));
  		job.setNumReduceTasks(0);
  		job.waitForCompletion(true);
  	}
  }
  ```

## 10、计数器的应用

Hadoop为每个作业维护若干内置计数器，以描述多项指标。列入，某些计数器记录衣橱里的字节数和记录数，使用户可监控以处理的输入数据量和已产生的输出数据量。

### ①、计数器API

* 采用枚举的方式统计计数。

  enum MyCounter{MALFORORMED,NORMAL}

  //对美剧定义的自定义计数器+1

  context.getCounter(MyCounter.MALFORORMED).increment(1);

* 采用计数器数组、计数器名称的方式统计。

  context.getCounter("counterGroup","counter").increment(1);

  组名和计数器名称可以随便起，但最好是有意义的。

* 技术结果在程序运行后的控制台上查看。

## 11、MapReduce 开发总结

### ①、在编写MapReduce程序时需要考虑一下几个方面：

* 输入数据的接口：InputFormat

  * 1、默认使用的实现类是：TextInputFormat。
  * 2、TextInputFormat的功能逻辑是：一次读一行文本，然后改行的起始偏移量作为key，行内容作为value返回。
  * 3、KeyValueTextInputFormat每一行均为一条记录，被分割符分隔为key和value。默认分隔符是“\t”。
  * 4、NlineInputFormat按照指定的函数N来划分切片。
  * 5、CombineTextInputFormat可以把多个小文件合并成一个切片处理，提高处理效率。
  * 6、用户还可以自定义InputFormat。

* 逻辑处理接口：Mapper

  用户根据业务需求实现其中的三个方法：map() setup() cleanup()

* Partitioner分区：

  * 1、有默认实现HashPartitioner，逻辑是根据key的哈希值和numReduces来返回一个分区号；key.hashCode()&Integer.MAXVALUE%numReduces
  * 2、如果业务上有特别的需求，可以自定义分区

* Comparable排序：

  * 1、当我们用自定义的对象作为key来输出时，就必须要实现WritableComparable接口，重写其中的compareTo（）方法。
  * 2、部分排序：队最终输出的每一个文件进行内部排序。
  * 3、全排序：对所有的数据进行排序，通常只有一个Reduce。
  * 4、二次排序：排序的条件有两个。

* Combiner合并：

  Combiner合并可以提高程序执行效率，减少IO传输。但是使用时必须不能影响原有的业务处理结果。

* Reducer端分组：GroupingComparator

  在Reduce端对key进行分组。应用于：在接收的key为bean对象时，想让一个或几个字段相同（全部字段比较不相同）的key进入到一个reduce方法时，可以采用分组排序。

* 逻辑处理接口：Reducer

  用户根据业务需求实现其中三个方法：reduce() setup()  cleanup()

* 输出数据接口：OutputFormat

  * 默认实现类是TextOutputFormat，功能逻辑是：将每一个KV对，向目标文本文件输出一行。
  * 将SequenceFileOutputFormat输出作为后续MapReduce任务的输入，这便是一种好的输出格式，因为他的格式，因为他的格式比较紧凑，很容易被压缩。
  * 用户还可以自定义OutputFormat