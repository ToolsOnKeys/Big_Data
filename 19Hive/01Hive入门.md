# Hive

## 1、Hive基本概念

### ①、什么是Hive

* 由Facebook开源用于解决海量结构化数据的统计与分析
* Hive是基于Hadoop的一个数据仓库工具，可以将结构化的数据文件映射为一张表，并提供类SQL查询功能
* 本质：将HQL转化为MapReduce程序。
  * Hive处理的数据存储再HDFS。
  * Hive分析数据底层的默认实现是MapReduce。
  * 执行程序运行在Yarn上。

### ②、Hive优缺点

* 优点
  * 操作接口采用类SQL语法，提供快速开发的功能（简单、容易上手）
  * 避免了去写MapReduce，减少了开发人员的学习成本
  * Hive的执行延迟比较高，因此Hive常用于数据分析，对实时性要求不高的场合。
  * Hive优势在于处理大数据，对于处理小数据没有优势，因为Hive的执行延迟比较高
  * Hive支持用户自定义函数，用户可以根据自己的需求来实现自己的函数
* 缺点
  * Hive的HQL表达能力有限：迭代式算法无法表达；数据挖掘方面不擅长
  * Hive的效率比较低：Hive自动生成的MapReduce作业，通常情况下不够智能化；Hive调优比较困难，粒度较粗。

### ③、Hive架构原理

* 用户接口：Client
  * CLI（hive shell）
  * JDBC/ODBC（java访问hive）
  * WEBUI（浏览器访问hive）
* 元数据：Metastore
  * 元数据包括：表名、表所属的数据库（默认实default）、表的拥有者、列/分区字段、表的类型（是否是外部表）、表的数据所在目录等。
  * 默认存储在自带的derby数据库中，推荐使用MySQL存储Metastore
* Hadoop
  * 使用HDFS进行存储，使用MapReduce进行计算
* 驱动器：Driver
  * 解析器（SQL Parser）：将SQL字符串转化成抽象语法树AST，这一步一般都用第三方工具库完成，比如antlr；对AST进行语法分析，比如表是否存在、字段是否存在、SQL是否有误。
  * 编译器（Physical Plan）：将AST编译成逻辑执行计划
  * 优化器（Query Optimizer）：对逻辑执行计划进行优化
  * 执行器（Execution）：把逻辑执行计划转换成可以运行的物理计划。对于Hive来说，就是MR/Spark

* 总结：Hive通过给用户提供的一系列交互接口，接收到用户的指令（SQL），使用自己的Driver，结合元数据（MetaStore），将这些指令翻译成MapReduce，提交给Hadoop中执行，最后，将执行返回的结果输出给用户交互接口。

### ④、Hive和数据库比较

* 由于Hive采用了类似SQL的查询语言HQL，因此很容易将Hive理解为数据库。起始从结构上来看，Hive和数据库除了拥有类似的查询语言，再无类似之处。数据库可以用在Online的应用中，但是Hive是为数据仓库而设计的。

* 1、查询语言

  由于SQL被广泛的应用在数据仓库中，因此，专门针对Hive的特性设计了类SQL的查询语言HQL。熟悉SQL开发的开发者可以很方便的使用Hive进行开发。

* 2、数据存储位置

  Hive是建立在Hadoop之上，所有Hive的数据都是存储在HDFS中的。而数据库则可以将数据保存在块设备或者本地系统中。

* 3、数据更新

  由于Hive是针对数据仓库应用设计的，而数据仓库的内容是读多写少。因此，Hive中不建议对数据的改写，所有的数据都是在加载的时候确定好的。而数据库中的数据通常是需要经常进行修改的，因此可以使用INSERT INTO....VALUES添加数据，使用update更新数据。

* 4、索引

  Hive在加载数据的过程中不会对数据进行任何处理，甚至不会对数据进行扫描，因此也没有对数据中的某些key建立索引。Hive要访问数据中满足条件的特定值时，需要暴力扫描整个数据，因此访问延迟较高。由于MapReduce的引入，Hive可以并行访问数据，因此及时没有索引，对于大数据量的访问，Hive仍然可以体现出优势。数据库中，通常会针对一个或者几个列建立索引，因此对于少量的特定条件的数据的访问，数据库可以有很高的效率，较低的言辞。由于数据的访问延迟较高，决定了Hive不适合做在线数据查询。

* 5、执行

  Hive中大多数查询的执行是通过hadoop提供的MapReduce来实现的。而数据库通常有自己的执行引擎。

* 6、执行延迟

  Hive在查询数据的时候没有索引，需要扫描整个表，因此延迟比较高。另外一个导致Hive执行延迟高的因素时MapReduce框架。由于MapReduce本身具有较高的延迟，因此在利用MapReduce执行Hive查询时，也会有较高的延迟。相对的，数据库的执行延迟较低。当然，这个低时有条件的，即数据规模较小，当数据规模大到超过数据库的处理能力的时候，Hive的并行计算显然能体现出优势。

* 7、可扩展性

  由于Hive是建立在Hadoop之上的，因此Hive的可扩展性和Hadoop的可扩展性是一致的。而数据库由于ACID语义的限制，扩展行非常有限。

* 8、数据规模（重要，且是其他差异点导致的直接原因）

  由于Hive建立在集群上并可以利用MapReduce进行并行计算，因此可以支持大规模的数据；对应的，数据库可以支持的数据规模较小。

## 2、Hive安装

<https://github.com/ToolsOnKeys/BigData/blob/master/19Hive/02Hive%E5%AE%89%E8%A3%85%E9%83%A8%E7%BD%B2.md>

## 3、Hive的数据类型

### ①、基本数据类型

| Hive数据类型 | Java数据类型 | 长度                                                 | 例子                                 |
| ------------ | ------------ | ---------------------------------------------------- | ------------------------------------ |
| TINYINT      | byte         | 1byte有符号整数                                      | 20                                   |
| SMALINT      | short        | 2byte有符号整数                                      | 20                                   |
| **INT**      | int          | 4byte有符号整数                                      | 20                                   |
| **BIGINT**   | long         | 8byte有符号整数                                      | 20                                   |
| BOOLEAN      | boolean      | 布尔类型，true或者false                              | TRUE  FALSE                          |
| FLOAT        | float        | 单精度浮点数                                         | 3.14159                              |
| **DOUBLE**   | double       | 双精度浮点数                                         | 3.14159                              |
| **STRING**   | string       | 字符系列。可以指定字符集。可以使用单引号或者双引号。 | ‘now is the time’ “for all good men” |
| TIMESTAMP    |              | 时间类型                                             |                                      |
| BINARY       |              | 字节数组                                             |                                      |

​        对于Hive的String类型相当于数据库的varchar类型，该类型是一个可变的字符串，不过他不能声明其中最多能存储多少个字符，理论上它可以存储2GB的字符数。

### ②、集合数据类型

| 数据类型 | 描述                                                         | 语法示例 |
| -------- | ------------------------------------------------------------ | -------- |
| STRUCT   | 和c语言中的struct类似，都可以通过“点”符号访问元素内容。例如，如果某个列的数据类型是STRUCT{first STRING, last STRING},那么第1个元素可以通过字段.first来引用。 | struct() |
| MAP      | MAP是一组键-值对元组集合，使用数组表示法可以访问数据。例如，如果某个列的数据类型是MAP，其中键->值对是’first’->’John’和’last’->’Doe’，那么可以通过字段名[‘last’]获取最后一个元素 | map()    |
| ARRAY    | 数组是一组具有相同类型和名称的变量的集合。这些变量称为数组的元素，每个数组元素都有一个编号，编号从零开始。例如，数组值为[‘John’,   ‘Doe’]，那么第2个元素可以通过数组名[1]进行引用。 | Array()  |

​       Hive有三种复杂类型Array、map、struct。array和map同Java中的array和map类似，而struct同C语言中的struct类似，他封装了一个命名字段集合，复杂数据类型允许任意层次的嵌套。

### ③、类型转化

​        Hive的原子数据类型是可以进行隐式转换的，类似于Java的类型转换，例如某表达式使用int类型，tinyint会自动转换为int类型，但是Hive不会进行方向转化；例如：某表达式使用tinyint类型，int不会自动转换为tinyint类型，它会返回错误，除非使用cast操作。

* 1、隐式类型转换规则如下：
  * 任何整数类型都可以隐式的转换为一个范围更广的类型，如tinyint可以转换成int，int可以转换成bigint
  * 所有整数类、float和string类型都可以转换为double
  * tinyint、smallint、int都可以转换为float
  * boolean类型不可以转换为任何其他的类型
* 2、可以使用cast操作显示进行数据类型转换
  * 例如cast（‘1’ as int）将字符串‘1’转换为整数1；如果强制类型转换失败，如执行cast（‘x’ as int），表达式返回空值NULL。

## 4、DDL数据定义

### ①、创建数据库

```
> create database db_hive; #创建一个数据库，数据库在HDFS上的默认路径是/user/hive/warehouse/*.db
> create database if not exists db_hive; #避免要创建的数据库已经存在错误，增加if not exits判断。
> create database db_hive2 location '/db_hive2.db' #创建一个数据库，同时指定数据库在HDFS上存放的位置。
```

### ②、查询数据库

```linux
#1、显示数据库
> show databases;
#2、过滤显示查询的数据库
> show databases like 'db_*';
#3、显示数据库信息
> desc database xxx;
#4、显示数据库详细信息，extended
> desc database extended xxx;
#5、切换当前数据库
> use xxx;
```

### ③、修改数据库

​       用户可以使用ALTER DATABASE命令为某个数据库的DBPROPERTIES设置键-值对属性值，来描述这个数据库的属性信息。**数据库的其他元数据信息都是不可更改的，包括数据库名和数据库所在的目录位置**。

```linux
#eg:
> alter database hive set dbproperties('createtime'='20190826');
```

### ④、删除数据库

```linux
#1、删除空数据库
> drop database xxx;
#2、如果删除的数据库不存在，最好采用if exists判断数据库是否存在
> drop database if exists xxx;
#3、如果数据库不为空，可以采用cascade命令，强制删除
> drop database xxx cascade;
```

### ⑤、创建表

* 建表语法

  ``` 建表语法
  CREATE [EXTERNAL] TABLE [IF NOT EXISTS] table_name
  [(col_name data_type [COMMENT col_comment], ...)]
  [COMMENT table_comment]     #当前表的描述信息
  [PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]  #创建分区表
  [CLUSTERED BY (col_name, col_name, ...) #创建分桶表
  [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]
  [ROW FORMAT row_format]
  [STORED AS file_format] #指定文件格式
  [LOCATION hdfs_path] #指定表结构位置
  ```

* 字段说明

  * （1）CREATE TABLE 创建一个指定名字的表。如果相同名字的表已经存在，则抛出异常；用户可以用 IF NOT EXISTS 选项来忽略这个异常。

  * （2）EXTERNAL关键字可以让用户创建一个外部表，在建表的同时指定一个指向实际数据的路径（LOCATION），Hive创建内部表时，会将数据移动到数据仓库指向的路径；若创建外部表，仅记录数据所在的路径，不对数据的位置做任何改变。在删除表的时候，内部表的元数据和数据会被一起删除，而外部表只删除元数据，不删除数据。

  * （3）COMMENT：为表和列添加注释。

  * （4）**PARTITIONED BY创建分区表**

  * （5）CLUSTERED BY创建分桶表

  * （6）SORTED BY不常用

  * （7）ROW FORMAT

  DELIMITED [FIELDS TERMINATED BY char] [COLLECTION ITEMS TERMINATED BY char]

  ​        [MAP KEYS TERMINATED BY char] [LINES TERMINATED BY char]

     | SERDE serde_name [WITH SERDEPROPERTIES (property_name=property_value, property_name=property_value, ...)]

  用户在建表的时候可以自定义SerDe或者使用自带的SerDe。如果没有指定ROW FORMAT 或者ROW FORMAT DELIMITED，将会使用自带的SerDe。在建表的时候，用户还需要为表指定列，用户在指定表的列的同时也会指定自定义的SerDe，Hive通过SerDe确定表的具体的列的数据。

  SerDe是Serialize/Deserilize的简称，目的是用于序列化和反序列化。

  * （8）STORED AS指定存储文件类型
    * Ⅰ、常用的存储文件类型：SEQUENCEFILE（二进制序列文件）、TEXTFILE（文本）、RCFILE（列式存储格式文件）
    * Ⅱ、如果文件数据是纯文本，可以使用STORED AS TEXTFILE。
    * Ⅲ、如果数据需要压缩，使用 STORED AS SEQUENCEFILE。

  * （9）LOCATION ：指定表在HDFS上的存储位置。

  * （10）LIKE允许用户复制现有的表结构，但是不复制数据。

* 管理表（Table Type：MANAGED_TABLE）
  * 1、理论

    * 默认创建的表都是所谓的管理表，优势也被称为内部表。因为这种表，Hive会控制数据的声明周期。Hive默认情况会将这些表的数据存储在由配置项hive.metastore.warehouse.dir所定义的目录的子目录下。当我们删除一个管理表时，Hive也会删除这个表中数据。管理表不适合和其他工具共享数据。

  * 2、实例

    ```linux
    # 普通创建表
    create table if not exists student(
    id int, name string
    )
    row format delimited fields terminated by '\t'
    stored as textfile
    location '/user/hive/warehouse/student'
    # 根据查询结果创建表（查询的结果会添加到新创建的表中）
    create table if not exists student1 as select id,name from student;
    # 根据已经存在的表结构创建表
    create table if not exists student2 like student;
    # 查询表的类型
    desc formatted student2;
    ```

* 外部表（Table Type：EXTERNAL_TABLE）
  * 1、理论

    * 因为表时外部表，所以Hive并非认为其完全拥有这份数据。删除该表并不会删除掉这份数据，不过描述表的元数据信息会被删除掉。

  * 2、管理表和外部表的使用场景

    * 每天将收集到的网站日志定期流入HDFS文本文件。在外部表（原始日志表）的基础上做大量的统计分析，用到的中间表、结果表使用内部表存储，数据通过select+insert进入内部表。

  * 3、实例

    ```linux
    create external table if not exists default.dept(
    deptno int,
    dname string,
    loc int
    )
    row format delimited fields terminated by '\t';
    ```

* 管理表和外部表的互相转换

  ```linux
  #1、查询表的类型
  > desc formatted student;
  #2、修改内部表student 为外部表
  > alter table student set tblproperties('EXTERNAL'='TRUE');
  #3、查询表的类型
  > desc formatted student;
  #4、修改外部表student 为内部表
  > alter table student set tblproperties('EXTERNAL'='FALSE');
  #5、查询表的类型。
  > desc formatted student;
  ```

### ⑥、分区表

​        分区表实际上就是对应一个HDFS文件系统上的独立的文件夹，改文件夹下是改分区所有的数据文件。Hive中的分区就是分目录，把一个大的数据集根据业务需要分割成下的数据集根据业务需要分割小的数据集。在查询是通过where子句中的表达式选择查询所需要的指定的分区，这样的查询效率会提高很多。

* 1、分区表的基本操作

  ```linux
  #创建分区表的语法
  > create table dept_partition(deptno int,dname string,loc string)
    partitioned by(month string)
    row format delimited fields terminated by '\t';
  #加载数据到分区表
  > load data local inpath '/opt/module/xxx.txt' into table default. partition(month='xxx');
  #查询分区里面的内容
  > select * from dept_partition where month = 'xxxx';
  #增加分区
  > alter table dept_partition add partition(mm string);
  #增加多个分区
  > alter table dept_partition add partition(mm string) partition(nn string);
  #删除单个分区
  > alter table dept_partition drop partition(mm string);
  #删除多个分区
  > alter table dept_partition drop partition(mm string),partition(nn string);
  #查看分区表有多少个分区
  > show partitions dept_partition;
  #查看分区表结构
  > desc formatted dept_partition;
  ```

* 2、分区表注意事项

  ```linux
  #1、创建二级分区表
  > create table tbl(a string,b int)
    partitioned(c string,d int)
    row format delimited fields terminated by '\t';
  #2、正常的加载数据
  > load data local inpath 'xxx路径/文件' into table tbl partition(c='xxx',d='yyy');
  #3、查询分区数据
  > select * from tbl where c='xxx' adn d='yyy';
  ```

  * 把数据直接上传到分区目录上，让分区表和数据产生关联的三种方式。

    * 上传数据后修复

      ```linux
      # 上传数据
      > dfs -mkdir -p 路径；
      > dfs -put 路径/文件 路径；
      # 执行修复命令
      > msck repair table tbl;
      # 查询数据
      > select * from tbl where a='xxx' and b='yyy';
      ```

    * 上传数据后添加分区

      ```linux
      # 上传数据
      > dfs -mkdir -p 路径;
      > dfs -put 路径/文件 路径;
      # 执行添加分区
      > alter table tbl add partition(cc='xxx');
      # 查询数据
      > select * from tbl where cc = 'xxx';
      ```

    * 创建文件夹后load数据到分区

      ```linux
      # 创建目录
      > dfs -mkdir -p 路径;
      # 上传数据
      > load data local inpath '路径/文件' into table tbl partition(cc = 'xxx');
      # 查询数据
      > select * from tbl where cc = 'xxx';
      ```

### ⑦、修改表

* 重命名表

  ```linux
  > alter table table_old_name rename to table_new_name
  ```

* 增加/修改/替换列信息

  ```linux
  # 更新列
  > alter table table_name change column field_old_name field_new_name 新类型；
  # 增加列或替换列
  > alter table table_name add columns(new_field_name 类型);
  > alter table table_name replace columns(field1 string,field2 int,....);
  ```

* 删除表

  ```linux
  > drop table table_name;
  ```

## 5、DML数据操作

### ①、数据导入

* 向表中装载数据（Load）

  ```linux
  # 基本语法
  > load data [local] inpath 'address/file'
    [overwrite] into table table_name [partition(field = 'data')];

  #实操
  ##1、创建一张表
  > create table table_name(field1 string,field2 int)
    row format delimited fields terminated by '\t';
  ##2、加载本地文件到hive
  > load data local inpath 'localpath/file' into table table_name;
  ##3、加载HDFS上文件到hive
  > dfs -put localpath/filename hdfspath
  > load data hdfspath into table table_name;
  ##4、加载数据覆盖表中已有的数据
  > load data local inpath 'localpath/filename' overwrite into table tabel_name;
  ```

* 通过查询语句向表中插入数据（Insert）

  ```linux
  # 创建一个分区表
  > create table table_name(field1 string ,field2 int)
    row format delimited fields terminated by '\t';
  # 基本插入数据
  > insert into table table_name values('xxx',10);
  # 基本模式插入（根据单表查询结果）
  > insert overwrite table table_name
    select * from other_table_name;
  # 多插入模式（根据多张表查询结果）
  > insert overwrite table table_name
    select * from one_table_name
    insert overwrite table table_name
    select * from two_table_name;
  ```

* 查询语句中创建表并加载数据（As Select）【根据查询结果来创建表】

  ```linux
  > create table if not exists new_table_name
    as select field1,field2 from old_table_name;
  ```

* 创建表时通过Location指定加载数据路径

  ```linux
  # 创建表的时候，指定数据来自于hdfs上的那个位置
  > create table table_name(field1 string,field2 int)
    [partitioned by (field3 string,field4 int)]
    row format delimited fields terminated by '\t'
    location 'hdfspath/filename';
  # 向hdfs指定路径上上传文件
  > dfs -put localpath/filename hdfspath;
  ```

* Import数据到指定Hive表中

  **注意**：只有用export到处的数据，才可以用import导入

  ```linux
  > import table table_name [partition(field3='xxx')]
    from 'hdfspath/exportfile'
  ```

### ②、数据导出

* Insert导出

  ```linux
  # 将查询的结果导出到本地
  > insert overwrite local directory 'localpath'
    select * from table_name;
  # 将查询的结果格式化导出到本地
  > insert overwrite local directory 'localpath'
    select * from table_name
    row format delimited fields terminated by '\t';
  # 将查询的结果导出到HDFS上
  > insert overwrite directory 'hdfspath'
    select * from table_name
    row format delimited fields terminated by '\t';
  ```

* Hadoop命令导出到本地

  ```linux
  > dfs -get hdfspath/filename localpath;
  ```

* Hive Shell 命令导出

  ```linux
  > bin/hive -e 'select * from table_name > localpath/filename
  ```

* Export导出到HDFS上

  ```linux
  > export table table_name to 'hdfspath';
  ```

* Sqoop导出

  后面会另开章节

### ③、清除表中数据（Truncate）

```linux
> truncate table table_name;
```

## 6、查询

* 重要网址：<https://cwiki.apache.org/confluence/display/Hive/LanguageManual+Select>

* 查询基本语法：

  ```sql
  select [ALL | DISTINCT] field1,field2...
  from table_name
  [where condition]
  [group by field_list]
  [having condition]
  [order by field_list]
  [
      cluster by field_list | [distribute by field_list] [sort by field_list]
  ]
  [desc | asc]
  [limit num]
  ```

### ①、基本查询

* 1、全表和特定列查询

  ```sql
  > select * from table_name;
  > select field1,field2... from table_name;
  ```

  **注意**：

  * sql语言对大小写不敏感
  * sql可以写在一行也可以写在多行
  * 关键字不能被缩写，也不能分行
  * 各子句一般要分行写
  * 使用缩进提高语句的可读性

* 2、列别名

  ```sql
  > select field1 [as] field_other_name from table_name;
  ```

  **注意**：重命名一个列，可以有助于方便使用这个列数据，同时as可以省略。

* 3、算数运算符

  | 运算符 |      描述      |
  | :----: | :------------: |
  |  A+B   |    A和B相加    |
  |  A-B   |     A减去B     |
  |  A*B   |    A和B相乘    |
  |  A/B   |     A除以B     |
  |  A%B   |    A对B取余    |
  |  A&B   |  A和B按位取与  |
  |  A\|B  |  A和B按位取或  |
  |  A^B   | A和B按位取异或 |
  |   ~A   |   A按位取反    |

  ```sql
  > select field1 + 1 from table_name;
  ```

* 4、常用函数

  | 方法  |  说明  |
  | :---: | :----: |
  | count | 总行数 |
  |  max  | 最大值 |
  |  min  | 最小值 |
  |  sum  |  总和  |
  |  avg  | 平均值 |

* 5、Limit关键字

  典型的查询会返回多行数据。Limit子句用于限制返回的行数

  ```sql
  > select * from table_name limit num;
  ```

### ②、Where语句

* 1、比较运算符

  | 操作符                  | 支持的数据类型 | 描述                                                         |
  | ----------------------- | -------------------- | ------------------------------------------------------------ |
  | A=B                     | 基本数据类型   | 如果A等于B则返回TRUE，反之返回FALSE                          |
  | A<=>B                   | 基本数据类型   | 如果A和B都为NULL，则返回TRUE，其他的和等号（=）操作符的结果一致，如果任一为NULL则结果为NULL |
  | A<>B, A!=B              | 基本数据类型   | A或者B为NULL则返回NULL；如果A不等于B，则返回TRUE，反之返回FALSE |
  | A<B                     | 基本数据类型   | A或者B为NULL，则返回NULL；如果A小于B，则返回TRUE，反之返回FALSE |
  | A<=B                    | 基本数据类型   | A或者B为NULL，则返回NULL；如果A小于等于B，则返回TRUE，反之返回FALSE |
  | A>B                     | 基本数据类型   | A或者B为NULL，则返回NULL；如果A大于B，则返回TRUE，反之返回FALSE |
  | A>=B                    | 基本数据类型   | A或者B为NULL，则返回NULL；如果A大于等于B，则返回TRUE，反之返回FALSE |
  | A [NOT] BETWEEN B AND C | 基本数据类型   | 如果A，B或者C任一为NULL，则结果为NULL。如果A的值大于等于B而且小于或等于C，则结果为TRUE，反之为FALSE。如果使用NOT关键字则可达到相反的效果。 |
  | A IS NULL               | 所有数据类型   | 如果A等于NULL，则返回TRUE，反之返回FALSE                     |

* 2、like和Rlike

  * 使用like运算选择类似的值
  * 选择条件可以包含字符或数字
    * % ：代表0个或多个字符（任意字符）
    * _ ：代表一个任意字符
  * Rlike子句时hive中这个功能的一个扩展，其可以通过java的正则表达式这个更强大的语言来指定匹配条件。
    * [字符] ：代表含有这个字符的信息

  ```sql
  > select * from table_name where field like '2%'
  > select * from table_name where field like '_2';
  > select * from table_name where field Rlike '[2]';
  ```

* 3、逻辑运算符（and/or/not）

  | 操作符 |  含义  |
  | :----: | :----: |
  |  AND   | 逻辑并 |
  |   OR   | 逻辑或 |
  |  NOT   | 逻辑否 |

  ```sql
  > select * from table_name where field and | or | not in condition;
  ```

### ③、分组（group by）

* 1、group by 语句

  group by 语句通常会和聚合行数一起使用，按照一个或者多个列结果进行分组，然后对每个组执行聚合操作。

  ```sql
  > select field1,sum(field2) from table_name group by field1;
  ```

* 2、having 语句

  having 和 where 不同点

  * where针对表的列发挥作用，查询数据；having针对查询结果中的列发挥作用，筛选数据。
  * where后面不能写聚合函数，而having后面可以使用聚合函数
  * having只用于gruop by分组统计语句

  ```sql
  > select field1,sum(field2) sums from table_name group by field1 having sums > 200;
  ```

### ④、Join语句

* 1、等值join

  Hive支持通常的SQL JOIN语句，但是只支持等值连接，不支持非等值连接。

* 2、表的别名

  使用别名可以讲话查询语句，同时使用表名前缀可以提高执行效率

* 3、内连接

  只有进行连接的两个表中都存在于连接条件相匹配的数据才会被保留下来。

* 4、left join 左外连接

  join操作符左边表中符合where子句的所有记录将会被返回

* 5、right join 右外连接

  join操作符右边表中符合where子句的所有记录将会被返回

* 6、full join 满外连接

  将会返回所有表中符合where语句条件的所有记录。如果任一表的指定字段没有符合条件的值的化，那么能使用null值替代。

* 7、多表连接

  **注意**：连接n个表，至少需要n-1个连接条件。

  大多数情况下，hive会对每对join连接对象启动一个MapReduce任务。

* 8、笛卡尔积

  * 笛卡尔集会在下面的条件下产生

    1、省略连接条件

    2、连接条件无效

    3、所有表中的所有行互相连接

* 9、连接为此中不支持or

### ⑤、排序

* 1、全部排序 order by

  全局排序，一个Reducer

  * 使用order by子句排序：asc（默认升序）、desc（降序）

  ```sql
  > select * from table_name order by field1 asc;
  ```

* 2、按照别名排序

  ```sql
  > select field1 as other_name from table_name order by other_name;
  ```

* 3、多个列排序

  ```sql
  > select * from table_name order by field1,field2;
  ```

* 4、每个MapReduce内部排序（Sort by）

  sort by：每个Reducer内部进行排序，对全局结果集来说不是排序。

  ```linux
  #设置reduce个数
  > set mapreduce.job.reducers=3;
  #查看设置的reducer的个数
  > set mapreduce.job.reducers;
  #根据部门编号降序查看员工信息
  > select * from table_name sort by field1 desc;
  #将查询结果导入到文件中
  > insert overwrite local directory 'localpath'
    select * from table_name sort by field1 desc;
  ```

* 5、分区排序（Distribute by）

  distribute by：类似MR中的partition，进行分区，结合sort by使用

  **注意**：hive要求distribute by 语句要写在sort by语句前面

  ```linux
  > set mapreduce.job.reduces=3;
  > insert overwrite local directory 'localpath'
    select * from table_name distribute by field1 sort by field1 desc;
  ```

* 6、Cluster by

  当distribute by 和 sort by字段相同时，可以使用cluster by 方式

  cluster by 除了具有distribute by 的功能外，还具有sort by的功能，但是排序只能时升序排序，不能指定排序规则时asc还是desc。

### ⑥、分桶及抽样查询

* 1、分桶表数据存储

  ​       分区针对的时数据的存储路径；分桶针对的是数据文件。

  ​       分区提供一个**隔离数据和优化查询**的便利方式。不过，并非所有的数据集都可以形成合理的分区，特别是之前锁提到过的要确定合适的划分大小这个疑虑。

  ​        分桶是将数据集分解成更容易管理的若干部分的另一技术。

  ```linux
  > set hive.enforce.bucketing=true;
  > set mapreduce.job.reduces=-1;
  > insert into table table_name
    select * from stu;
  ```

* 2、分桶抽样查询

  对于非常大的数据集，又是用户需要使用的是一个具有代表性的查询结果而不是全部结果。Hive可以通过对表进行抽样来满足这个需求。

  ```sql
  > select * from table_name tablesample(bucket 1 out of 4 on field);
  ```

  **注**：tablesample是抽样语句，语法 ： tablesample(bucket x out of y on field);

  x表示从哪个bucket开始抽取，如果需要去多个分区，以后的分区号为当前分区好加上y。

  x的值必须小于等于y。

### ⑦、其他常用的查询函数
