## 5、Hyperbase表 VS Hyperdrive表

### 5.1、Hyperbase

* 基于开源的Hive HBaseStorageHandler研发，本省设计比较简单，Hyperbase底层不存储表的schema信息，数据的序列化反序列化都依赖于上层Hive。这造成很多功能和性能上的问题。例如：null和空字符串的区别，SQL执行计划的优化等。因此在与Inceptor配合使用的情况下不推荐使用Hyperbase表。只用一种场景比较合适：在Hyperbase中已存在一张表，需要通过Inceptor的外表功能进行对接。这样保证了Inceptor对原生Hyperbase的访问功能。

### 5.2、Hyperdrive表

* 为了解决Hyperbase表的一些设计缺陷，星环科技自主研发了Hyperdrive白哦，去除了Hive HBaseStorageHandler的设计，并增加了新的功能，是其能更高效地通过Inceptor访问存储在HyperBase中的数据。
* 在底层Hyperbase中加入表的Schema信息，数据存储压缩率更高，序列化/反序列化更高效。数据类型支持BOOLEAN、TINYINT、SMALLINT\INTEGER\BIGINT\DATE\TIMESTAMP\DECIMAL\FLOAT\DOUBLE\STRING\VARCHAR\STRUCT\BINARY等数据类型
* 对接Inceptor通用的存储访问层Stargate，可以支持完整的Filter转换下推、Global Lookup Join等特性，显著提升SQL性能。

