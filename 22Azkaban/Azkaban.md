# Azkaban

## 1、概述

### ①、什么是Azkaban

* Azkaban是由Linkedin公司推出的一个批量工作流任务调度器，主要用于在一个工作流内以一个特定的顺序运行一组工作和流程，它的配置是通过简单的key:value对的方式，通过配置中的dependencies 来设置依赖关系。Azkaban使用job配置文件建立任务之间的依赖关系，并提供一个易于使用的web用户界面维护和跟踪你的工作流

### ②、为什么需要工作流调度系统

* 1）一个完整的数据分析系统通常都是由大量任务单元组成：shell脚本程序，java程序，mapreduce程序、hive脚本等

* 2）各任务单元之间存在时间先后及前后依赖关系

* 3）为了很好地组织起这样的复杂执行计划，需要一个工作流调度系统来调度执行；

### ③、Azkaban的特点

* 1)        易于使用的Web用户界面

* 2)        简单的工作流的上传

* 3)        方便设置任务之间的关系

* 4)        调度工作流

* 5)        模块化和可插拔的插件机制

* 6)        认证/授权(权限的工作)

* 7)        能够杀死并重新启动工作流

* 8)        有关失败和成功的电子邮件提醒

### ④、常见的工作流调度系统

* 1）简单的任务调度：直接使用crontab实现；

* 2）复杂的任务调度：开发调度平台或使用现成的开源调度系统，比如oozie、azkaban等

### ⑤、Azkaban的架构

* 1) AzkabanWebServer：AzkabanWebServer是整个Azkaban工作流系统的主要管理者，它用户登录认证、负责project管理、定时执行工作流、跟踪工作流执行进度等一系列任务。

* 2) AzkabanExecutorServer：负责具体的工作流的提交、执行，它们通过mysql数据库来协调任务的执行。

* 3) 关系型数据库（MySQL）：存储大部分执行流状态，AzkabanWebServer和AzkabanExecutorServer都需要访问数据库。

## 2、Azkaban安装

待补充

## 3、Azkaban实战

### ①、单一job案列

```job
#first.job
type=command
command=echo 'this is my first job'
```

### ②、java操作任务

```job
#azkabanJava.job
type=javaprocess
java.class=com.atguigu.azkaban.AzkabanTest
classpath=/opt/module/azkaban/lib/*
```

### ③、多job工作流案例

```job
#start.job
type=command
command=touch /opt/module/kangkang.txt
```

```job
#step1.job
type=command
dependencies=start
command=echo "this is step1 job"
```

```job
#step2.job
type=command
dependencies=start
command=echo "this is step2 job"
```

```job
#finish.job
type=command
dependencies=step1,step2
command=echo "this is finish job"
```

### ④、HDFS操作任务

```job
#hdfs job
type=command
command=/opt/module/hadoop-2.7.2/bin/hadoop fs -mkdir /azkaban
```

### ⑤、mapreduce任务

```job
#mapreduce job
type=command
command=/opt/module/hadoop-2.7.2/bin/hadoop jar /opt/module/hadoop-2.7.2/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.7.2.jar wordcount /wordcount/input /wordcount/output
```

### ⑥、Hive脚本

```job
#hive job
type=command
command=/opt/module/hive/bin/hive -f /opt/module/azkaban/jobs/student.sql
```

