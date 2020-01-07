# Local模式

* Local模式就是运行在一台计算机上的模式，通常就是用于在本机上练手和测试

## 1、安装

```linux
> tar -zxvf spark-2.1.1-bin-hadoop2.7.tgz -C /opt/module/
> mv spark-2.1.1-bin-hadoop2.7 spark-local
```

## 2、官方求PI案例

```linux
bin/spark-submit \
--class org.apache.spark.examples.SparkPi \
--master local[2] \
./examples/jars/spark-examples_2.11-2.1.1.jar \
10
```

* --class：表示要执行程序的主类
* --master local：没有指定线程数，则所有的计算都运行在一个线程当中，没有任何并行计算
* --master local[n]：指定使用n个Core来运行计算，比如local[2]就是运行在2个Core来执行
* --master lcoal[*]：默认模式。自动帮你按照CPU最多核来设置线程数。比如CPU有8核，Spark帮你自动设置8个线程计算。
* ./examples/jars/spark-examples_2.11-2.1.1.jar：运行的程序jar
* 10：运行程序的输入参数。

## 3、官方WordCount案例

```linux
> bin/spark-shell
> sc.textFile("/opt/module/spark-local/input").flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_).collect
```

