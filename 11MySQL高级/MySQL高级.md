# MySQL高级

## 1、MySQL逻辑架构图

![](D:\BigData\BigData\11MySQL高级\相关资料\MySQL架构图.png)

* 和其他数据库相比，MySQL有点与众不同，他的架构可以在多种不同场景中应用并发挥良好作用。主要体现在存储引擎的架构上，插件时的存储引擎架构间给查询处理和其他的系统任务以及数据的存储提取相分离。这种架构可以根据业务的需求和实际需要选择合适的存储引擎。

* 1、连接层

  最上层是一些客户端和连接服务，包含本地sock通信和大多数基于和后端/服务端工具实现的类似于tcp/ip的通信。主要完成一些类似于连接处理、授权认证、及相关的安全方案。在该层上引入了线程池的概念，为通过认证安全接入的客户端提供线程。同样在该层上可以实现基于SSL的安全连接。服务器也会为安全接入的每个客户端验证他的所有具有的操作的权限。

* 2、服务层

  | 服务                             | 说明                                                         |
  | :------------------------------- | :----------------------------------------------------------- |
  | Management Serveices & Utilities | 系统管理和控制工具                                           |
  | SQL Interface:                   | SQL接口。接受用户的SQL命令，并且返回用户需要查询的结果。比如select from就是调用SQL   Interface |
  | Parser                           | 解析器。 SQL命令传递到解析器的时候会被解析器验证和解析       |
  | Optimizer                        | 查询优化器。  SQL语句在查询之前会使用查询优化器对查询进行优化，比如有where条件时，优化器来决定先投影还是先过滤。 |
  | Cache和Buffer                    | 查询缓存。如果查询缓存有命中的查询结果，查询语句就可以直接去查询缓存中取数据。这个缓存机制是由一系列小缓存组成的。比如表缓存，记录缓存，key缓存，权限缓存等 |

* 3、引擎层

  存储引擎层，存储引擎真正的负责Mysql中数据的存储和提取，服务器通过api于存储引擎进行通信。不同的存储引擎具有的功能不同，这样我们可以根据自己的实际需要进行选取。

* 4、存储层

  数据存储层，主要是将数据存储在运行于裸设备的文件系统之上，并完成于存储引擎的交互。

  

## 2、Mysql的安装路径和基本常识说明

| 参数         | 路径                            | 解释                         | 备注                       |
| ------------ | ------------------------------- | ---------------------------- | -------------------------- |
| --datadir    | /var/lib/mysql/                 | mysql数据库文件的存放路径    |                            |
| --basedir    | /usr/bin                        | 相关命令目录                 | mysqladmin mysqldump等命令 |
| --plugin-dir | /usr/lib64/mysql/plugin         | mysql插件存放路径            |                            |
| --log-error  | /var/lib/mysql/jack.atguigu.err | mysql错误日志路径            |                            |
| --pid-file   | /var/lib/mysql/jack.atguigu.pid | 进程pid文件                  |                            |
| --socket     | /var/lib/mysql/mysql.sock       | 本地连接时用的unix套接字文件 |                            |
|              | /usr/share/mysql                | 配置文件目录                 | mysql脚本及配置文件        |
|              | /etc/init.d/mysql               | 服务启停相关脚本             |                            |

### ①、sql_mode

sql_mode定义了对Mysql中sql语句语法的校验规则

sql_mode是个容易被忽视的变量，默认值是控制，在这种设置下是可以允许一些非法的操作的，比如允许一些非法数据的插入。在生产环境中必须将这个值设置为严格模式，所以开发、测试环境的数据库也必须要设置，这样在开发测试阶段就可以发现问题。

| 参数                       | 说明                                                         |
| -------------------------- | ------------------------------------------------------------ |
| ONLY_FULL_GROUP_BY         | 对于GROUP BY聚合操作，如果在SELECT中的列，没有在GROUP   BY中出现，那么这个SQL是不合法的，因为列不在GROUP BY从句中 |
| NO_AUTO_VALUE_ON_ZERO      | 该值影响自增长列的插入。默认设置下，插入0或NULL代表生成下一个自增长值。如果用户 希望插入的值为0，而该列又是自增长的，那么这个选项就有用了 |
| STRICT_TRANS_TABLES        | 在该模式下，如果一个值不能插入到一个事务表中，则中断当前的操作，对非事务表不做限制 |
| NO_ZERO_IN_DATE            | 在严格模式下，不允许日期和月份为零                           |
| NO_ZERO_DATE               | 设置该值，mysql数据库不允许插入零日期，插入零日期会抛出错误而不是警告 |
| ERROR_FOR_DIVISION_BY_ZERO | 在INSERT或UPDATE过程中，如果数据被零除，则产生错误而非警告。如 果未给出该模式，那么数据被零除时MySQL返回NULL |
| NO_AUTO_CREATE_USER        | 禁止GRANT创建密码为空的用户                                  |
| NO_ENGINE_SUBSTITUTION     | 如果需要的存储引擎被禁用或未编译，那么抛出错误。不设置此值时，用默认的存储引擎替代，并抛出一个异常 |
| PIPES_AS_CONCAT            | 将"\|\|"视为字符串的连接操作符而非或运算符，这和Oracle数据库是一样的，也和字符串的拼接函数Concat相类似 |
| ANSI_QUOTES                | 启用ANSI_QUOTES后，不能用双引号来引用字符串，因为它被解释为识别符 |
| ORACLE                     | 设置等同于PIPES_AS_CONCAT,   ANSI_QUOTES, IGNORE_SPACE, NO_KEY_OPTIONS, NO_TABLE_OPTIONS,   NO_FIELD_OPTIONS, NO_AUTO_CREATE_USER |

* 1、查看当前的sql_mode

  ```linux
  select @@sql_mode
  ```

* 2、临时修改sql_mode

  ```linux
  > set @@sql_mode='';
  ```

* 3、永久修改，需要在配置文件my.cnf中修改

  ```linux
  > vim /etc/my.cnf
  [mysqld]
  sql_mode=''
  ```

### ②、Mysql的用户管理

| 命令                                                         | 描述                                     | 备注                                                         |
| ------------------------------------------------------------ | ---------------------------------------- | ------------------------------------------------------------ |
| create user zhang3 identified by '123123';                   | 创建名称为zhang3的用户，密码设为123123； |                                                              |
| select host,user,password,select_priv,insert_priv,drop_priv from mysql.user; | 查看用户和权限的相关信息                 |                                                              |
| set   password =password('123456')                           | 修改当前用户的密码                       |                                                              |
| update   mysql.user set password=password('123456') where user='li4'; | 修改其他用户的密码                       | 所有通过user表的修改，必须用flush privileges;命令才能生效    |
| update   mysql.user set user='li4' where user='wang5';       | 修改用户名                               | 所有通过user表的修改，必须用flush privileges;命令才能生效    |
| drop   user li4                                              | 删除用户                                 | 不要通过delete from  user   u where user='li4' 进行删除，系统会有残留信息保留。 |

* Mysql的权限管理

  | 命令                                                         | 描述                                                         |
  | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | grant 权限1,权限2,…权限n   on 数据库名称.表名称 to 用户名@用户地址   identified by ‘连接口令’ | 该权限如果发现没有该用户，则会直接新建一个用户。   示例：   grant select,insert,delete,drop on atguigudb.* to   li4@localhost  ;   给li4用户用本地命令行方式下，授予atguigudb这个库下的所有表的插删改查的权限。 |
  | grant all privileges on *.* to joe@'%'  identified by '123'; | 授予通过网络方式登录的的joe用户 ，对所有库所有表的全部权限，密码设为123. |

* 收回权限

  | 命令                                                         | 描述                                |
  | ------------------------------------------------------------ | ----------------------------------- |
  | show grants                                                  | 查看当前用户权限                    |
  | revoke  [权限1,权限2,…权限n]   on    库名.表名    from  用户名@用户地址 ; | 收回权限命令                        |
  | REVOKE ALL PRIVILEGES ON mysql.* FROM joe@localhost;         | 收回全库全表的所有权限              |
  | REVOKE select,insert,update,delete ON mysql.* FROM   joe@localhost; | 收回mysql库下的所有表的插删改查权限 |

### ③、查看sql的执行周期

```linux
#1、查看profile是否开启
> show variables like '%profiling%';
#2、开启profile
> set profiling=1
#3、使用profile，可以查看最近的几次查询
> show profiles;
#4、根据query_id，查看sql的具体执行步骤
> show profiles cpu,block io for query 2;
```

* mysql语句的大致执行流程

  ​        mysql客户端通过协议与mysql服务器连接，发送查询语句，先检查查询缓存，如果命中，直接返回结果，否则进行语句解析，也就是说，在解析查询之前，服务器会先访问查询缓存（query cache）--它存储select语句以及相应的插叙你结果集。如果某个查询结果已经位于缓存中，服务器就不会在对查询进行解析、优化、以及执行。它仅仅将缓存中的结果返回给用户即可，这将大大提高系统的性能。

  ​         语法解析器和预处理：首先mysql通过关键字将sql语句进行解析，并生成一棵对应的解析树。MySQL解析器将使用mysql语法规则验证和解析查询；预处理器则根据一些mysql规则进一步检查解析数是否合法。

  ​         查询优化器当解析树被认为是合法的了，并且有优化器将其转化成explian。一条查询可以有很多种执行方式，最后都返回相同的结果。优化器的作用就是找到这其中最好的explian。

  ​        然而，mysql默认使用的是Btree索引，并且一个大致方向就是：无论这么折腾sql，至少目前来说，mysql最多只用到表中一个索引。

* sql的书写顺序

  select distinct from join on where group by having order by limit

* sql的执行顺序

  from on join where group by having select distinct order by limit

### ④、MyISAM和InnoDByingqing

| 对比项         | MyISAM                                                   | InnoDB                                                       |
| -------------- | -------------------------------------------------------- | ------------------------------------------------------------ |
| 外键           | 不支持                                                   | 支持                                                         |
| 事务           | 不支持                                                   | 支持                                                         |
| 行表锁         | 表锁，即使操作一条记录也会锁住整个表，不适合高并发的操作 | 行锁,操作时只锁某一行，不对其它行有影响，   适合高并发的操作 |
| 缓存           | 只缓存索引，不缓存真实数据                               | 不仅缓存索引还要缓存真实数据，对内存要求较高，而且内存大小对性能有决定性的影响 |
| 关注点         | 读性能                                                   | 并发写、事务、资源                                           |
| 默认安装       | Y                                                        | Y                                                            |
| 默认使用       | N                                                        | Y                                                            |
| 自带系统表使用 | Y                                                        | N                                                            |

```linux
#1、查看所有的数据库引擎
> show engines;
#2、查看默认的数据库引擎
> show variables like '%storage_engine%';
```

### ⑤、主从复制

* 不管使用何种存储引擎，在server层都可以开启binlog日志功能。binlog会记录所有的逻辑操作，并且是采用追加写的形式，将写操作命令，记录在一个二进制文件中。因此**binlog日志通常用于恢复数据**，或者是**主从复制**。
* 默认binlog文件会保存在/var/lib/mysql目录中，以mysql-bin.xxxx开头。可以使用mysqlbinlog进行查看。
* mysql的主从复制为设置一台mysql服务实例为主机，其他一台为从机。主机的数据可以实时同步到从机。

#### 配置主从

```linux
#1、确保主机和从机都开启了二进制日志，编辑/etc/my.cnf文件
> vim /etc/my.cnf;
log-bin=mysql-bin
server-id=x  #注：x为一个int值，每个server实例都有有个自己的id
binlog-format=mixed
#2、在主机上授权从机，使用root用户登陆主机，在命令行执行如下命令
>grant replication slave on *.* to 'slave'@'%' identfied by '123456';
#3、在主机执行show master status 查看主机的binlog日志信息，并记录下binlog的file和position属性
> show master status;
#4、配置从机
> change master to
  master_user='slave',
  master_password='123456',
  master_host='192.168.xxx.xxx',
  master_log_file='mysql-bin.000028',
  master_log_pos=250
  ## 注： 使用mysql客户端连接从机服务，如果之前配置锅主从，需要先执行stop slave 停止主从关系。配置完成后，在从机的/var/lib/mysql目录下会产生一个名为master.info的主机配置信息文件。
#5、启动从机
> start slave;
#6、查看从机是否正常启动
> show slave status \G;
```

## 3、索引优化分析

### ①、索引的概念

![](D:\BigData\BigData\11MySQL高级\相关资料\索引图例.png)

* 索引（index）是帮助mysql高效获取数据的数据结构。可以得到索引的本质：索引是数据结构。可以简单的理解为排好序的快速查找数据结构。
* 在数据之外，数据库系统还维护着满足特定查找算法的数据结构，这些数据结构以某种方式应用数据，这样就可以在这些数据结构上实现高级查找算法。这种数据结构就是索引。
* 一般来说，索引很大，不可能全部存储在内存中，因此索引往往以索引文件的形式存储在磁盘上。
* 优势：提高了数据检索的效率，降低数据库的IO成本；其次，通过索引列对数据进行排序，降低数据排序的成本，降低了cpu的消耗。
* 劣势：虽然索引大大提高了查询速度，同时却会降低更新表的速度，如对表进行insert、update和delete。因为更新表时，mysql不仅要保存数据，还要保存以下索引文件每次更新添加了索引列的字段，都会调整因为更新锁带来的键值变化后的索引信息。其次，实际上索引也是一张表，该表保存了主键与索引字段，并指向实体表的记录，所以索引列也是要占用空间的。

### ②、BTree和B+Tree

![](D:\BigData\BigData\11MySQL高级\相关资料\BTree.png)

![](D:\BigData\BigData\11MySQL高级\相关资料\B+Tree.png)

* 1、B-树的关键字和记录时放在一起的，叶子节点可以看作是外部节点，不包含任何信息；B+树的非叶子节点中只有关键字和指向下一个节点的索引，记录只放在叶子节点中。
* 2、在B-树中，也靠近根节点的记录查找事件也快，只要找到关键字即可确定记录的存在；而在B+树种每个记录的查找时间基本时一样的，都需要从根节点走到叶子节点，而且在叶子节点中还要比较关键字。从这个角度看B-树的性能好像关闭B+树号，而在实际应用中却是B+树的性能要好些。因为B+树的非叶子节点不存放实际的数据，这样每个节点可容纳的元素个数比B-树多，树高比B-树小，这样带来的好处时减少磁盘的访问次数。尽管B+树找到一个记录所需的比较次数要比B-树多，但是一次磁盘访问的时间相当于成百上千次内存比较的时间，因此实际中B+树的性能可能还会好些，而且B+树的叶子节点使用指针连接在一起，方便顺序遍历（例如查看一个目录下的所有文件，一个表中的所有记录等），这也是很多数据库和文件系统使用B+树的缘故。
* 3、B+树的磁盘读写代价相对B-树更低
* 4、B+树的查询效率更加稳定。

### ③、聚簇索引和非聚簇索引

![](D:\BigData\BigData\11MySQL高级\相关资料\聚簇索引和非聚簇.png)

* 聚簇索引并不是一种单独的索引类型，而是一种数据存储方式。
* 聚簇索引的好处：按照聚簇索引排列顺序，查询显示一定范围数据的时候，由于数据都是紧密相连，数据库不用从多个数据块中提取数据，所以节省了大量的io的操作。
* 对于mysql数据库目前只用innodb数据引擎支持聚簇索引，而Myisam并不支持聚簇索引。
* 由于数据物理存储排序方式只能有一种，所以 每个mysql表中只能有一个聚簇索引。一般情况下就是该表的主键。
* 为了充分利用聚簇索引的聚簇的特性，所以innodb表的主键列尽量选用有序的顺序id，而不建议用无序的id比如uuid这种。

## 3、Mysql索引分类

### ①、单值索引

* 即一个索引只包含单个列，一个表可以有多个单值索引

```sql
#1、随表一起创建
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name) #单值索引列
);
#2、单独建立单值索引
> create index idx_customer_name on customer(customer_name)
```

### ②、唯一索引

* 索引列的值必须唯一，但允许空值

```sql
#1、随表一起建立
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  UNIQUE (customer_no) # 唯一索引列
);
#2、单独建唯一索引
> create unique index idx_xxx_name on table_name(fields)
```

### ③、主键索引

* 设定为主键后数据库会自动建立索引，innodb为聚簇索引

```sql
#1、随表一起建立
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id)  #主键索引
);
#2、单独建立主键索引
> alter table table_name add primary key table_name(fields);
#3、删除主键索引
> alter table table_name drop primary key;
#4、修改主键索引
> alter table table_name drop primary key;
> alter table table_name add primary key table_name(fields);
```

### ④、复合索引

* 一个索引包含多个列

```sql
#1、随表一起建立
CREATE TABLE customer (id INT(10) UNSIGNED  AUTO_INCREMENT ,customer_no VARCHAR(200),customer_name VARCHAR(200),
  PRIMARY KEY(id),
  KEY (customer_name),
  UNIQUE (customer_name),
  KEY (customer_no,customer_name)
);
#2、单独建立
> create index idx_xxx_name on table_name(field1,field2...);
```

### ⑤、基本语法

| 功能          | 实现                                                         |
| ------------- | ------------------------------------------------------------ |
| 创建          | CREATE  [UNIQUE   ]  INDEX [indexName] ON   table_name(column)) |
| 删除          | DROP INDEX [indexName] ON mytable;                           |
| 查看          | SHOW INDEX FROM table_name\G                                 |
| 使用Alter命令 | ALTER TABLE tbl_name ADD   PRIMARY KEY (column_list) : 该语句添加一个主键，这意味着索引值必须是唯一的，且不能为NULL。 |
|               | ALTER   TABLE tbl_name ADD PRIMARY KEY (column_list)         |
|               | ALTER TABLE tbl_name ADD INDEX index_name   (column_list): 添加普通索引，索引值可出现多次。 |
|               | ALTER   TABLE tbl_name ADD FULLTEXT index_name (column_list):该语句指定了索引为 FULLTEXT ，用于全文索引。 |

## 4、索引创建的时机选择

### ①、适合创建索引的情况

1） 主键自动建立唯一索引；

2） 频繁作为查询条件的字段应该创建索引

3） 查询中与其它表关联的字段，外键关系建立索引

4） 单键/组合索引的选择问题， 组合索引性价比更高

5） 查询中排序的字段，排序字段若通过索引去访问将大大提高排序速度

6） 查询中统计或者分组字段

### ②、不适合创建索引的情况

1） 表记录太少

2） 经常增删改的表或者字段

3） Where条件里用不到的字段不创建索引

4） 过滤性不好的不适合建索引