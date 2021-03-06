## HBase问题分析手段

* 监控系统：首先用于判断系统各项指标是否正常，明确系统目前状况
* 服务端日志：查看例如region移动轨迹，发生了什么动作，服务端接受处理了那些客户端请求
* gc日志：gc情况是否正常
* 操作系统日志和命令：操作系统层面、硬件是否故障，当前状况如何
* btrace：实时跟踪目前服务端的请求和处理情况
* 运维工具：通过内置于系统中的功能，查看服务器实时处理状况



以上是大部分系统都具备的，不过各有各的用法，以下是通过常见的问题来梳理的六大手段



### 1、个别请求为什么很慢？

个别请求慢是常见的问题，首先需要明确是客户端还是服务端原因，进而分析服务端状况以及捕获这些请求来明确定位

* ①、通过客户端日志来初步分析下慢请求的规律，尝试再客户端确定请求的RowKey和操作类型
* ②、确定是不是一段时间内集中出现的慢请求，如果是那么可以参考问题2来解决
* ③、查看服务端监控，观察响应时间是否平稳，maxResponseTime是否出现峰值。如果可以初步确定是服务端问题
* ④、客户端分析无效，可以通过运维工具再服务端捕获慢请求的RowKey和操作类型
* ⑤、确定RowKey对应的region，初步查看是否存在数据表参数配置不合理（例如version设置过多、blockcache、bloomfilter类型不正确）、storefile过多、命中率过低等问题
* ⑥、尝试重试这些请求或者直接分析hfile来查看返回结果是否过大，请求是否耗费资源过多
* ⑦、查看服务端关于HDFS的监控和日志，以及datanode日志，来分析是否存在hdfs块读取慢或者磁盘故障。

### 2、客户端读写请求为什么大量出错

读写请求大量出错的现象主要有两类：大量出现服务端exception，大量超时。其中第一种有异常信息较好判断问题所在。

* ①、大量服务端exception一般是region不在线导致的，可能是region再split但是时间很长超过预期，或是meta数据错误导致客户端获取region location错误。以上现象均可通过日志来定位。
* ②、遇到大量超时，首先应该排除服务端是否出现fullgc或者ygc时间过长。前者可能由于内存碎片、cms gc速度来不及导致，后者一般是由于系统使用了swap内存。
* ③、通过系统命令和日志