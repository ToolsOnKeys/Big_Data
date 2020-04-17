> https://www.cnblogs.com/zhangwuji/p/9156609.html



4种情况，每种情况测试3-5次，取平均值

优化： 保证内存用上，没有性能瓶颈



#### YSCB 使用hbase14



```shell
#解压
tar xvfz ycsb-hbase14-binding-0.17.0.tar.gz

#拷贝lib文件到ycsb中
cd ycsb-hbase14-binding-0.17.0
cp -apR /root/TDH-Client/hyperbase/lib/* lib/

#创建log目录和配置文件目录
mkdir logs
mkdir conf

#拷贝配置文件到conf目录
cp /root/TDH-Client/conf/hyperbase1/hbase-site.xml  ./conf/
cp /root/TDH-Client/conf/hadoop/hdfs-site.xml  ./conf/
cp /root/TDH-Client/conf/hadoop/core-site.xml  ./conf/



#加载数据；指定了记录的个数（1000个）
#-P指明了所用的配置文件的路径；
#-p 可以显示修改YCSB内置的默认配置，例如这里配置recordcount=10000来覆盖之前说过的workloada中默认的recordcount=1000；
#-s一次加载数据或执行测试的过程实践很长，YCSB客户端需要定时(默认10s)发送状态信息；> logs/load.log表示结果日志写入位置
./bin/ycsb load hbase14 -P workloads/workloada  -p threads=10 -p table=usertable -p columnfamily=family -p recordcount=10000 -s > logs/load.log



#测试性能
#-threads 10个线程
#操作次数为10万次 operationcount=1000000
#-p measurementtype=timeseries -p timeseries.granularity=2000指明了YCSB客户端多长时间汇总一次延时，2000表示2000秒记录一次平均延迟
bin/ycsb run hbase14 -P workloads/workloada -threads 10 -p operationcount=1000000 -p table=usertable -p columnfamily=family -p measurementtype=timeseries -p timeseries.granularity=2000 -s > logs/transaction-workloadAA.log
```



### 配置文件说明

```shell
# 使用负载类
workload=site.ycsb.workloads.CoreWorkload

#【没有默认值，必须配置】
#1. 配置为load阶段的需要insert的数量
#2. 配置为run阶段之前已经插入的记录数量
recordcount=1000000

# 【没有默认值，必须配置】
# Run阶段的操作数量
operationcount=3000000

# The number of insertions to do, if different from recordcount.
# Used with insertstart to grow an existing table.
#insertcount=

# The offset of the first insertion
insertstart=0

# 记录的列字段数量
fieldcount=10

# 每一列的值大小
fieldlength=100

# 是否读所有的列
readallfields=true

# Update的时候是否写所有列
writeallfields=false

#列字段的value长度分布
fieldlengthdistribution=constant
#fieldlengthdistribution=uniform
#fieldlengthdistribution=zipfian

# 读操作的比例
readproportion=0.95

# 更新操作比例
updateproportion=0.05

#插入操作比例
insertproportion=0

# 读后改的操作比例
readmodifywriteproportion=0

# scan操作比例
scanproportion=0

#单次scan的最大读取的条目数量
maxscanlength=1000

# 一次scan的数量的分布
scanlengthdistribution=uniform
#scanlengthdistribution=zipfian


#顺序插入还是伪随机插入？
insertorder=hashed
#insertorder=ordered

# The distribution of requests across the keyspace
#key空间的分布？
requestdistribution=zipfian
#requestdistribution=uniform
#requestdistribution=latest

# Percentage of data items that constitute the hot set
# 热数据的比例
hotspotdatafraction=0.2

# Percentage of operations that access the hot set
# 访问热数据集的比例？
hotspotopnfraction=0.8

# Maximum execution time in seconds
#maxexecutiontime= 

# The name of the database table to run queries against
#table 名称
table=usertable

# The column family of fields (required by some databases)
#列族名称
#columnfamily=

# How the latency measurements are presented
#统计方式
measurementtype=histogram
#measurementtype=timeseries
#measurementtype=raw
# 当统计方式设置为raw，输出是如下CSV 格式的数据点

# "operation, timestamp of the measurement, latency in us"

# Raw datapoints are collected in-memory while the test is running. Each
# data point consumes about 50 bytes (including java object overhead).
# For a typical run of 1 million to 10 million operations, this should
# fit into memory most of the time. If you plan to do 100s of millions of
# operations per run, consider provisioning a machine with larger RAM when using
# the RAW measurement type, or split the run into multiple runs.
#测试过程中 数据点采集在内存中，每个点消耗50B内存；100万-1000万的操作，一般内存满足需求
#如果 准备做100s的百万级别操作？ 需要考虑更大的内存
#
# Optionally, you can specify an output file to save raw datapoints.
# Otherwise, raw datapoints will be written to stdout.
# The output file will be appended to if it already exists, otherwise
# a new output file will be created.
#配置存储数据点的文件，不配置则打印到stdout
#measurement.raw.output_file = /tmp/your_output_file_for_this_run

#直方图是否弄桶？
# Whether or not to emit individual histogram buckets when measuring
# using histograms.
# measurement.histogram.verbose = false

# JVM Reporting.
#
# Measure JVM information over time including GC counts, max and min memory
# used, max and min thread counts, max and min system load and others. This
# setting must be enabled in conjunction with the "-s" flag to run the status
# thread. Every "status.interval", the status thread will capture JVM 
# statistics and record the results. At the end of the run, max and mins will
# be recorded.
#是否统计JVM信息，包括GC 数量，max和min内存使用，线程数量，系统负载等等
# measurement.trackjvm = false

# The range of latencies to track in the histogram (milliseconds)
#直方图跟踪的延迟范围
histogram.buckets=1000

# Granularity for time series (in milliseconds)
# 时间序列的粒度
timeseries.granularity=1000


#延迟报告
# Latency reporting.
#
# YCSB records latency of failed operations separately from successful ones.
# Latency of all OK operations will be reported under their operation name,
# such as [READ], [UPDATE], etc.
#
# For failed operations:
# By default we don't track latency numbers of specific error status.
# We just report latency of all failed operation under one measurement name
# such as [READ-FAILED]. But optionally, user can configure to have either:
# 1. Record and report latency for each and every error status code by
#    setting reportLatencyForEachError to true, or
# 2. Record and report latency for a select set of error status codes by
#    providing a CSV list of Status codes via the "latencytrackederrors"
#    property.
# reportlatencyforeacherror=false
# latencytrackederrors="<comma separated strings of error codes>"

#插入错误的处理
# Insertion error retry for the core workload.
# By default, the YCSB core workload does not retry any operations.
# However, during the load process, if any insertion fails, the entire
# load process is terminated.
# If a user desires to have more robust behavior during this phase, they can
# enable retry for insertion by setting the following property to a positive
# number.
# core_workload_insertion_retry_limit = 0
#
# the following number controls the interval between retries (in seconds):
# core_workload_insertion_retry_interval = 3

#
# Distributed Tracing via Apache HTrace (http://htrace.incubator.apache.org/)
#
# Defaults to blank / no tracing
# Below sends to a local file, sampling at 0.1%
#
# htrace.sampler.classes=ProbabilitySampler
# htrace.sampler.fraction=0.001
# htrace.span.receiver.classes=org.apache.htrace.core.LocalFileSpanReceiver
# htrace.local.file.span.receiver.path=/some/path/to/local/file
#
# To capture all spans, use the AlwaysSampler
#
# htrace.sampler.classes=AlwaysSampler
#
# To send spans to an HTraced receiver, use the below and ensure
# your classpath contains the htrace-htraced jar (i.e. when invoking the ycsb
# command add -cp /path/to/htrace-htraced.jar)
#
# htrace.span.receiver.classes=org.apache.htrace.impl.HTracedSpanReceiver
# htrace.htraced.receiver.address=example.com:9075
# htrace.htraced.error.log.period.ms=10000

```





### 性能测试

#### 1. 只写，10并发，10万条

```
workload=site.ycsb.workloads.CoreWorkload

readallfields=true

readproportion=0
updateproportion=0
scanproportion=0
insertproportion=1

requestdistribution=zipfian
```



```shell
bin/ycsb run hbase14 -P workloads/workload_writeonly -threads 10 -p operationcount=100000 -p recordcount=10000 -p table=usertable -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output -p timeseries.granularity=2000 -s > logs/transaction-workload_writeonly.log
```



性能测试结果：

[OVERALL], RunTime(ms), 25764
[OVERALL], Throughput(ops/sec), 3881.3848781245147

...

[INSERT], Average, 2417.65418
[INSERT], Min, 844
[INSERT], Max, 716401

| 测试次数     | 1     | 2     | 3     | 4     | 平均 |
| ------------ | ----- | ----- | ----- | ----- | ---- |
| 吞吐量       | 3881  | 4265  | 4516  | 4192  | 4213 |
| 平均延迟(ms) | 2.417 | 2.093 | 2.056 | 2.228 | 2.19 |
| region 数量  | 2     | 2     | 2     | 2     | 2    |

主要使用一个region server  hadoop3里面写, region数量为2个，分布较为平均，写完后各为一半

heap Mem： 2G

Max： 23G

Memstore Size 55MB



#### 2. 只写，20并发，10万条

```shell
bin/ycsb run hbase14 -P workloads/workload_writeonly -threads 20 -p operationcount=100000 -p recordcount=710000 -p table=usertable -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output -p timeseries.granularity=2000 -s > logs/transaction-workload_writeonly.log
```



| 测试次数     | 1     | 2     | 3     | 4     | 平均 |
| ------------ | ----- | ----- | ----- | ----- | ---- |
| 吞吐量       | 6950  | 6743  | 7055  | 6968  | 6929 |
| 平均延迟(ms) | 2.559 | 2.658 | 2.533 | 2.563 | 2.58 |
| region 数量  | 2     | 2     | 2     | 2     | 2    |



#### 3. 读0.17，写0.83，10并发，6万条

```
workload=site.ycsb.workloads.CoreWorkload

readallfields=true

readproportion=0.17
updateproportion=0
scanproportion=0
insertproportion=0.83

requestdistribution=zipfian
```



执行命令：

```shell
bin/ycsb run hbase14 -P workloads/workload_mix -threads 10 -p operationcount=60000 -p recordcount=1120000 -p table=usertable -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_mix -p timeseries.granularity=2000 -s > logs/transaction-workload_mix.log
```



| 测试次数         | 1    | 2    | 3    | 平均 |
| ---------------- | ---- | ---- | ---- | ---- |
| 吞吐量           | 5453 | 5047 | 5427 | 5309 |
| 读平均延迟(ms)   | 0.69 | 0.56 | 0.52 | 0.59 |
| 写平均延迟（ms） | 1.62 | 1.94 | 1.80 | 1.79 |
| region 数量      | 2    | 2    | 2    |      |



#### 4. 读0.17，写0.83，20并发，6万条

分裂成5个region

hadoop2:2个

hadoop3 1个

hadoop4 2个



```
bin/ycsb run hbase14 -P workloads/workload_mix -threads 20 -p operationcount=60000 -p recordcount=1120000 -p table=usertable -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_mix -p timeseries.granularity=2000 -s > logs/transaction-workload_mix.log
```



| 测试次数         | 1    | 2    | 3    | 平均 |
| ---------------- | ---- | ---- | ---- | ---- |
| 吞吐量           | 8581 | 8811 | 8944 | 8778 |
| 读平均延迟(ms)   | 1.57 | 0.92 | 1.08 | 1.19 |
| 写平均延迟（ms） | 1.84 | 1.91 | 1.78 | 1.84 |
| region 数量      | 5    | 5    | 5    |      |









## 重新测试

原先测试的内容由于没有进行预分片，是region server 自动分片

下面是

```
hbase(main):001:0> n_splits = 200 # HBase recommends (10 * number of regionservers)
hbase(main):002:0> create 'usertableSplit', 'family', {SPLITS => (1..n_splits).map {|i| "user#{1000+i*(9999-1000)/n_splits}"}}

```



#### 1. 只写，10万条



```shell
bin/ycsb run hbase14 -P workloads/workload_writeonly -threads 10 -p operationcount=100000 -p recordcount=200000 -p table=usertableSplit -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_writeonly -p timeseries.granularity=2000 -s > logs/transaction-workload_writeonly.log
```



- 10线程

| 测试次数         | 1    | 2     | 3     | 平均    |
| ---------------- | ---- | ----- | ----- | ------- |
| 吞吐量           | 5831 | 6429  | 6455  | 6238.33 |
| 写平均延迟（ms） | 1.45 | 1.398 | 1.388 | 1.412   |
| region 数量      | 4*10 | 4*10  | 4*10  |         |

- 20线程

```
bin/ycsb run hbase14 -P workloads/workload_writeonly -threads 20 -p operationcount=100000 -p recordcount=500000 -p table=usertableSplit -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_writeonly -p timeseries.granularity=2000 -s > logs/transaction-workload_writeonly.log
```

| 测试次数         | 1     | 2     | 3     | 平均  |
| ---------------- | ----- | ----- | ----- | ----- |
| 吞吐量           | 10037 | 10489 | 10452 | 10326 |
| 写平均延迟（ms） | 1.682 | 1.595 | 1.616 | 1.631 |
| region 数量      | 4*10  | 4*10  | 4*10  |       |



#### 2. 读写混合，6万条

- 10线程

```
bin/ycsb run hbase14 -P workloads/workload_mix -threads 10 -p operationcount=60000 -p recordcount=660000 -p table=usertableSplit -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_mix -p timeseries.granularity=2000 -s > logs/transaction-workload_mix.log
```



| 测试次数         | 1     | 2     | 3    | 平均     |
| ---------------- | ----- | ----- | ---- | -------- |
| 吞吐量           | 6149  | 6695  | 6427 | 6423.667 |
| 读平均延迟(ms)   | 0.564 | 0.378 | 0.39 | 0.444    |
| 写平均延迟（ms） | 1.533 | 1.409 | 1.47 | 1.470667 |
| region 数量      | 4*10  | 4*10  | 4*10 |          |



- 20线程

```
bin/ycsb run hbase14 -P workloads/workload_mix -threads 20 -p operationcount=60000 -p recordcount=700000 -p table=usertableSplit -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_mix -p timeseries.granularity=2000 -s > logs/transaction-workload_mix.log
```

| 测试次数         | 1     | 2     | 3    | 平均     |
| ---------------- | ----- | ----- | ---- | -------- |
| 吞吐量           | 10159 | 10048 | 9996 | 10067.67 |
| 读平均延迟(ms)   | 0.538 | 0.416 | 0.39 | 0.448    |
| 写平均延迟（ms） | 1.646 | 1.699 | 1.66 | 1.668    |
| region 数量      | 4*10  | 4*10  | 4*10 |          |



#### 3. 只读  (结果：1000W数据量下，线程数31，32的情况下，吞吐量最大)

- 10线程 100W

```
bin/ycsb run hbase14 -P workloads/workload_readonly -threads 10 -p operationcount=1000000 -p table=usertable -p columnfamily=family -p measurementtype=timeseries -p
timeseries.granularity=2000 -s > logs/transaction-workloadread.log
```



| 测试次数       | 1        | 2        | 3        | 平均 |
| -------------- | -------- | -------- | -------- | ---- |
| 吞吐量         | 21230.52 | 21343.81 | 21464.75 |      |
| 读平均延迟(ms) | 0.448    | 0.447    | 0.446    |      |
| region 数量    | 201      | 201      | 201      |      |



- 20线程 100W

```
bin/ycsb run hbase14 -P workloads/workload_readonly -threads 20 -p operationcount=1000000 -p table=usertable -p columnfamily=family -p measurementtype=timeseries -p
timeseries.granularity=2000 -s > logs/transaction-workloadread.log
```

| 测试次数       | 1        | 2        | 3        | 平均 |
| -------------- | -------- | -------- | -------- | ---- |
| 吞吐量         | 32263.27 | 33337.77 | 33466.08 |      |
| 读平均延迟(ms) | 0.578    | 0.559    | 0.560    |      |
| region 数量    | 201      | 201      | 201      |      |



- 20线程

```
bin/ycsb run hbase14 -P workloads/workload_readonly -threads 20 -p operationcount=5000000 -p table=usertable -p columnfamily=family -p measurementtype=timeseries -p
timeseries.granularity=2000 -s > logs/transaction-workloadread.log
```

| 数据量         | 500W     | 800W     | 1000W    | 平均 |
| -------------- | -------- | -------- | -------- | ---- |
| 吞吐量         | 68848.71 | 69876.32 | 67884.05 |      |
| 读平均延迟(ms) | 0.731    | 0.262    | 0.289    |      |
| region 数量    | 201      | 201      | 201      |      |



- 1000W数据量

```
bin/ycsb run hbase14 -P workloads/workload_readonly -threads 20 -p operationcount=10000000 -p table=usertable -p columnfamily=family -p measurementtype=timeseries -p
timeseries.granularity=2000 -s > logs/transaction-workloadread.log
```

| 线程数 | 吞吐量            | 读平均延迟(ms)    | region 数量 |
| ------ | ----------------- | ----------------- | ----------- |
| 10     | 42752.40          | 0.427             | 201         |
| 20     | 67884/70293       | 0.289/0.262       | 201         |
| 25     | 77900/76069/76420 | 0.314/0.299/0.623 | 201         |
| 28     | 81779/78563/81625 | 0.472/0.385/0.278 | 201         |
| 30     | 83915/78397/81796 | 0.182/0.208/0.274 | 201         |
| 31     | 81611/83030/82546 | 0.489/0.519/0.273 | 201         |
| 32     | 82200/82940/85458 | 0.517/0.152/0.181 | 201         |
| 33     | 81620/84615/82957 | 0.465/0.170/0.182 | 201         |
| 35     | 81620/81136       | 0.182/0.177       | 201         |
| 40     | 83798/81771       | 0.263/0.113       | 201         |



region server  Heap Size: 20G

CPU: Intel(R) Xeon(R) CPU E5-2620 v4 @ 2.10GHz（2 CPU, 32 processors）

内存：128G

磁盘：2TB  

网卡：1000M





## 测试更多region的情况

n_splits = 200

create 'usertableSplitTest', 'family', {SPLITS => (1..n_splits).map {|i| "user#{1000+i*(9999-1000)/n_splits}"}}



```
bin/ycsb run hbase14 -P workloads/workload_writeonly -threads 20 -p operationcount=100000 -p recordcount=300000 -p table=usertableSplitTest -p columnfamily=family -p measurementtype=raw -p measurement.raw.output_file=./pointdata/run_output_writeonly -p timeseries.granularity=2000 -s > logs/transaction-workload_writeonly.log
```

10线程

5882 1.539

5833 1.55



20线程

9141 1.87

9248 1.85