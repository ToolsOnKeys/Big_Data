# Redis

## 1、NoSQL简介

* 思考：Session共享问题如何解决？

  > 1、存在Cookie中：存在于客户端，不安全，网络负担效率低
  >
  > 2、存在文件服务器或者数据库中：导致大量的IO操作，效率低
  >
  > 3、Session复制：会导致每个服务器之间必须将Session广播到及群里的每个节点，Session数据冗余，节点越多，浪费越大，存再广播风暴问题。
  >
  > 4、存在Redis中：最好，将Session数据存在内存中，每台服务器都从内存中读取数据，速度快，结构相对简单。

* NoSQL数据库概述

  >1、泛指非关系型数据库。NoSQL不依赖业务逻辑方式存储，而已简单的key-value模式存储。增加了数据库的扩展能力
  >
  >2、NoSQL的特点：不遵循SQL标准；不支持ACID；远超于SQL的性能
  >
  >3、NoSQL的使用场景：对数据的高并发的读写，海量数据的读写。
  >
  >4、NoSQL的不适用场景：需要事务支持；基于sql的结构化查询存储，处理复杂的关系，需要即席查询
  >
  >5、建议：用不着sql的和用了sql也不行的情况下，考虑使用NoSql

* 常用的缓存数据库

  > 1、Memcached：数据在内存中，一般不持久化；简单的kv模式；一般是作为缓存数据库辅助持久化的数据库
  >
  > 2、Redis：数据在内存中，支持持久化，主要用做备份恢复；支持多种数据结构；一般作为缓存数据库辅助持久化的数据库。
  >
  > 3、mongoDB：文档型数据库。
  >
  > 4、HBase：Hadoop项目中的数据库。
  >
  > 5、Cassandra：
  >
  > 6、Neo4j

## 2、Redis 简介 及 安装

### ①、Redis是什么

> redis是一个高性能的key-value数据库，它是完全开源免费的，而且redis是一个NOSQL类型数据库，是为了解决高并发、高扩展，大数据存储等一系列的问题而产生的数据库解决方案，是一个非关系型的数据库。但是，它也是不能替代关系型数据库，只能作为特定环境下的扩充。
>
> redis是一个以key-value存储的数据库结构型服务器，它支持的数据结构类型包括：字符串（String）、链表（lists）、哈希表（hash）、集合（set）、有序集合（Zset）等。为了保证读取的效率，redis把数据对象都存储在内存当中，它可以支持周期性的把更新的数据写入磁盘文件中。而且它还提供了交集和并集，以及一些不同方式排序的操作。

### ②、应用场景

* 配合关系性数据库做高速缓存

* 由于其拥有持久化能力，利用其多样的数据结构存储特定的数据

  > 1、最新N个数据-》通过List实现按字眼时间排序的数据
  >
  > 2、排行榜-》利用zset（有序集合）
  >
  > 3、时效性的数据，比如手机验证码-》Expire过期
  >
  > 4、计数器-》原子性，自增方法INCR、DECR
  >
  > 5、去除大量数据中的重复数据-》利用set集合
  >
  > 6、构建队列-》利用list集合
  >
  > 7、发布订阅消息系统-》pub/sub模式

### ③、安装步骤

* 1)         下载获得redis-3.2.5.tar.gz后将它放入我们的Linux目录/opt

* 2)         解压命令

  ```linux
  tar -zxvf redis-3.2.5.tar.gz
  ```

* 3)         解压完成后进入目录:

  ```linux
  cd redis-3.2.5
  ```

* 4)         安装gcc与g++

  ```linux
  yum install gcc
  
  yum install gcc-c++
  ```

* 6)         重新进入到Redis的目录中执行 make distclean后再执行make 命令.

  ```linux
  make distclean
  make
  ```

* 7)         执行完make后，可跳过Redis test步骤，直接执行 make install

  ```linux
  make install
  ```

* 8)         扩展:  升级glibc库的版本到2.1.7

> <http://ftp.gnu.org/gnu/glibc/glibc-2.17.tar.gz>  下载对应的版本

```linux
> [root@hadoop101 software]#  tar -xf glibc-2.17.tar.gz
> [root@hadoop101 glibc-2.1.7]#  cd glibc-2.17
> [root@hadoop101 glibc-2.17]#  mkdir build 
> [root@hadoop101 glibc-2.17]#  cd build
> [root@hadoop101 build]# ../configure --prefix=/usr --disable-profile --enable-add-ons --with-headers=/usr/include --with-binutils=/usr/bin
> [root@hadoop101 build]#  make -j  8
> [root@hadoop101 build]#  make install
```

### ③、查看默认安装目录 /usr/local/bin

* Redis-benchmark：性能测试工具
* Redis-check-aof：修复有问题的AOF文件
* Redis-check-dump：修复有问题的dump.rdb文件
* Redis-sentinel：Redis集群使用
* redis-server：Redis服务器启动命令
* redis-cli：客户端，操作入口

### ④、Redis的启动

* 默认前台方式启动

  ```linux
  redis-server
  >启动后不能操作当前命令窗口，序号再开一个窗口操作
  ```

* 推荐后台启动

  ```linux
  1、拷贝一份redis.conf配置文件到根目录下
  2、修改redis.conf文件中的一项配置daemonize，将no改为yes，代表后台启动
  3、执行配置文件即可
  redis-server /myredis/redis.conf
  ```

### ⑤、客户端访问

* 使用redis-cli 命令访问启动好的Redis

  ```linux
  redis-cli
  > 如果有多个Redis同时启动，则需要指定端口号访问
  redis -cli -p 端口号
  ```

* 测试验证，通过ping命令，如果返回PONG，则OK

### ⑥、关闭Redis服务

* 单实例关闭

  > 1、如果还未通过客户端访问，可直接使用redis-cli shutdown
  >
  > 2、如果已经进入客户端，直接 shutdown即可

* 多实例关闭

  > 指定端口关闭 redis-cli -p 端口号 shutdown

### ⑦、Redis默认16个库

* Redis默认创建16个库，每个库对应一个下标，从0开始。通过客户端链接后默认进入到0号库，推荐只使用0号库

* 使用命令select库的下标 来切换数据库

  ```linux
  select 8
  >切换到8号库
  ```

### ⑧、Redis的单线程+IO多路复用技术

* 多路复用是指使用一个线程来检查多个文件描述符（Socket）的就绪状态，比如调用select和poll函数，传入多个文件描述符，如果有一个文件描述符就绪，则返回，否则阻塞直到超时。得到就绪状态后进行真正的操作可以在同一个线程里执行，也可以启动线程执行（比如使用线程池）。
* Memcached是  多线程 + 锁
* Redis 是 单线程 + IO多路复用

## 3、Redis的五大数据类型

### ①、key

| 指令                   | 功能                                         |
| ---------------------- | -------------------------------------------- |
| keys  *                | 查看当前库的所有键                           |
| exists <key>           | 判断某个键是否存在                           |
| type <key>             | 查看键的类型                                 |
| del <key>              | 删除某个键                                   |
| expire <key> <seconds> | 为键值设置过期时间，单位秒                   |
| ttl <key>              | 查看还有多久过期,-1表示永不过期,-2表示已过期 |
| dbsize                 | 查看当前数据库中key的数量                    |
| flushdb                | 清空当前库                                   |
| Flushall               | 通杀全部库                                   |

### ②、String

| 指令                                   | 功能                                                         |
| -------------------------------------- | :----------------------------------------------------------- |
| get <key>                              | 查询对应键值                                                 |
| set <key> <value>                      | 添加键值对                                                   |
| append <key> <value>                   | 将给定的<value>追加到原值的末尾                              |
| strlen <key>                           | 获取值的长度                                                 |
| setnx <key> <value>                    | 只有在key 不存在时设置key的值                                |
| incr <key>                             | 将key中存储的数字值增1   只能对数字值操作，如果为空，新增值为1 |
| decr <key>                             | 将key中存储的数字值减1   只能对数字之操作，如果为空,新增值为-1 |
| incrby /decrby <key> 步长              | 将key中存储的数字值增减，自定义步长                          |
| mset <key1> <value1> <key2>   <value2> | 同时设置一个或多个key-value对                                |
| mget <key1> <key2>  <key3>             | 同时获取一个或多个value                                      |
| msetnx <key1> <value1> <key2> <value2> | 同时设置一个或多个key-value对，当且仅当所有给定的key都不存在 |
| getrange <key> <起始位置> <结束位置>   | 获得值的范围,类似java中的substring                           |
| setrange <key> <起始位置> <value>      | 用<value>覆盖<key>所存储的字符串值，从<起始位置>开始         |
| setex <key> <过期时间> <value>         | 设置键值的同时，设置过去时间，单位秒                         |
| getset <key> <value>                   | 以新换旧,设置了新值的同时获取旧值                            |

* 1、String是Redis最基本的类型，一个key对应一个value

* 2、String类型是二进制安全的。意味着Redis的string可以包含任何数据。

* 3、String类型中value最多可以是512M

* 4、incr key 操作的原子性

  ```分析
  >所谓原子操作指不会被线程调度机制打断的操作；这个操作一旦开始，就一直运行到结束，中间不会有任何context switch（切换到另一个线程）
  >在单线程中，能够在单挑指令中完成的操作都可以认为是“原子操作”，因为中断只能发生于指令之间。
  >在多线程中，不能被其他进程（线程）打断的操作就叫原子操作
  >Redis单命令的原子性主要得益于Redis的单线程
  ```

### ③、List

| 指令                                          | 功能                                           |
| --------------------------------------------- | ---------------------------------------------- |
| lpush/rpush    <key>  <value1>  <value2>      | 从左边/右边插入一个或多个值。                  |
| lpop/rpop    <key>                            | 从左边/右边吐出一个值。   值在键在，值光键亡。 |
| rpoplpush    <key1>  <key2>                   | 从<key1>列表右边吐出一个值，插到<key2>列表左边 |
| lrange <key> <start> <stop>                   | 按照索引下标获得元素(从左到右)                 |
| lindex <key> <index>                          | 按照索引下标获得元素(从左到右)                 |
| llen <key>                                    | 获得列表长度                                   |
| linsert <key>    before <value>    <newvalue> | 在<value>的后面插入<newvalue> 插入值           |
| lrem <key> <n>  <value>                       | 从左边删除n个value(从左到右)                   |

* 1、单键多值
* 2、Redis列表是简单的字符串列表，按照插入顺序排序。你可以添加一个元素到列表的头部或尾部
* 3、他的底层是双向链表，对两端的操作性能很高，通过索引下标的操作中间的节点性能不高。

### ④、Set

| 指令                                    | 功能                                                         |
| --------------------------------------- | ------------------------------------------------------------ |
| sadd <key>    <value1>  <value2>   .... | 将一个或多个 member 元素加入到集合 key 当中，已经存在于集合的 member 元素将被忽略。 |
| smembers <key>                          | 取出该集合的所有值。                                         |
| sismember <key>    <value>              | 判断集合<key>是否为含有该<value>值，有返回1，没有返回0       |
| scard     <key>                         | 返回该集合的元素个数。                                       |
| srem <key> <value1> <value2> ....       | 删除集合中的某个元素。                                       |
| spop <key>                              | 随机从该集合中吐出一个值。                                   |
| srandmember <key> <n>                   | 随机从该集合中取出n个值。   不会从集合中删除                 |
| sinter <key1> <key2>                    | 返回两个集合的交集元素。                                     |
| sunion <key1> <key2>                    | 返回两个集合的并集元素。                                     |
| sdiff <key1> <key2>                     | 返回两个集合的差集元素。                                     |

* 1、Redis set 对外提供的功能于list类似是一个列表的功能，特殊支持在于set是可以自动排重的，当你需要存储一个列表数据，又不希望出现重复的数据时，set是一个很好的选择，并且set提供了判断某个成员是否在一个set集合内的重要接口，这个也是list所不能提供的。
* 2、Redis的Set时string类型的无序集合。他的底层其实是一个value为null的hash表，所以添加，上传，查早的复杂度都是O(1)。

### ⑤、Hash

| 指令                                                   | 功能                                                         |
| ------------------------------------------------------ | ------------------------------------------------------------ |
| hset <key>    <field>  <value>                         | 给<key>集合中的  <field>键赋值<value>                        |
| hget <key1>    <field>                                 | 从<key1>集合<field> 取出 value                               |
| hmset <key1>    <field1> <value1> <field2> <value2>... | 批量设置hash的值                                             |
| hexists key    <field>                                 | 查看哈希表 key 中，给定域 field 是否存在。                   |
| hkeys <key>                                            | 列出该hash集合的所有field                                    |
| hvals <key>                                            | 列出该hash集合的所有value                                    |
| hincrby <key> <field>  <increment>                     | 为哈希表 key 中的域 field 的值加上增量 increment             |
| hsetnx <key>    <field> <value>                        | 将哈希表 key 中的域 field 的值设置为 value ，当且仅当域 field 不存在 |

* 1、Redis hash是一个键值对集合。
* 2、Redis hash是一个String类型的field和value的映射表，hash特别适合存储对象
* 3、类似于Java里面的Map<String,Object>

### ⑥、zset

| 指令                                                         | 功能                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| zadd  <key> <br />  <score1> <value1>    <score2> <value2>... | 将一个或多个 member 元素及其 score 值加入到有序集 key 当中   |
| zrange <key>    <start> <stop>    [WITHSCORES]               | 返回有序集 key 中，下标在<start>   <stop>之间的元素   带WITHSCORES，可以让分数一起和值返回到结果集。 |
| zrangebyscore key min max [withscores] [limit offset   count] | 返回有序集 key 中，所有 score 值介于 min 和 max 之间(包括等于 min 或 max )的成员。有序集成员按 score 值递增(从小到大)次序排列。 |
| zrevrangebyscore key max min [withscores] [limit offset   count] | 同上，改为从大到小排列。                                     |
| zincrby <key> <increment> <value>                            | 为元素的score加上增量                                        |
| zrem    <key>  <value>                                       | 删除该集合下，指定值的元素                                   |
| zcount <key>    <min>  <max>                                 | 统计该集合，分数区间内的元素个数                             |
| zrank <key>    <value>                                       | 返回该值在集合中的排名，从0开始。                            |

* 1、Redis有序集合zset与普通集合set非常相似，是一个没有重复元素的字符串集合。不同之处是有序集合的每个成员都关联了一个评分，这个评分被用来按照从最低分到最高分的方式排序集合中的成员。集合的成员是唯一的，但是评分可以是重复的。
* 2、因为元素是有序的，所以你也可以很快的根据评分或者次序来获取一个范围的元素。访问有序集合的中间元素也是非常快的。

## 4、Redis 的相关配置

* 1、计量单位说明，对于大小写不敏感

* 2、include

  > 类似于jsp中的include，多实例的情况可以把公用的配置文件提取出来。

* 3、ip地址绑定bind

  >* 1、默认情况bind=127.0.0.1 只能接受本机的访问请求
  >* 2、不写的情况下，无限制的接受任何ip地址的访问
  >* 3、生产环境肯定要写你应用服务器的地址
  >* 4、如果开启了protected-mode，那么在没有设定bind ip且没有设密码的情况下，Redis值允许接受本机的响应

* 4、tcp-backlog

  >* 1、可以理解是一个请求到达后至到接受进程处理前的队列。
  >* 2、backlog队列总和=未完成三次握手队列+已经完成三次握手队列。
  >* 3、高并发环境tcp-backlog设置值跟超时时限内的Redis吞吐量决定。

* 5、timeout

  一个空闲的客户端维持多少秒会关闭，0代表永不关闭

* 6、tcp keepalive

  对访问客户端的一种心跳检测，每个n秒检测一次，官方推荐设置为60秒。

* 7、daemonize

  是否为后台进程

* 8、pidfile

  存放pid文件的位置，每个实例都会产生一个不同的pid文件

* 9、log level

  四个级别根据使用阶段来判断，生产环境选择notice或者warning

* 10、syslog

  是否将Redis日志传输到linux系统日志服务中

* 11、sylog-ident

  日志的标记

* 12、syslog-facility

  输出日志的设备

* 13、database

  设定库的数量，默认是16

* 14、security

  在命令行中设置密码

* 15、maxclient

  最大客户端连接数

* 16、maxmemory

  设置Redis可以使用的内存量。一旦到达内存使用上限，Redis将会试图移除内部数据，移除规则可以通过maxmemory-policy来指定。如果Redis无法根据移除规则来移除内存中的数据，或者设置了“不允许移除”，

  那么Redis则会针对那些需要申请内存的指令返回错误信息，比如SET、LPUSH等。

* 17、Maxmemory-policy

  l  volatile-lru：使用LRU算法移除key，只对设置了过期时间的键

  l  allkeys-lru：使用LRU算法移除key

  l  volatile-random：在过期集合中移除随机的key，只对设置了过期时间的键

  l  allkeys-random：移除随机的key

  l  volatile-ttl：移除那些TTL值最小的key，即那些最近要过期的key

  l  noeviction：不进行移除。针对写操作，只是返回错误信息

* 18、Maxmemory-samples

  设置样本数量，LRU算法和最小TTL算法都并非是精确的算法，而是估算值，所以你可以设置样本的大小。

  一般设置3到7的数字，数值越小样本越不准确，但是性能消耗也越小。

## 5、Redis 的 Java 客户端 Jedis

### ①、Jedis所需要的jar包，可通过Maven的依赖引入

* Commons-pool-1.6.jar
* Jedis-2.1.0.jar

### ②、使用Windows环境下Eclipse链接虚拟机中的Redis注意事项：

* 禁用Linux的防火墙
* redis.conf中注释掉bind 127.0.0.1，然后protect-mode no

### ③、手机验证码案列

* 发送验证码：

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		String tel = request.getParameter("telno");
		System.out.println("telno:"+tel);
		//判断手机号是否为空
		if(tel==null) {
			printWriter.print("1");//1代表手机号为空
			return;
		}
		//获取Jedis对象，链接Redis数据库
		Jedis jedis = new Jedis("hadoop103", 6379);
		//从Redis中获取当前获取密钥的次数
		String string = tel + ":count";
		String string2 = tel + ":no";
		String count = jedis.get(string);
		String code = getCode();
		if(count==null) {
			System.out.println(count);
			jedis.setex(string,24*60*60,"1");
			jedis.setex(string2,120,code);
			printWriter.print("2");//2代表发送成功
			printWriter.close();
			jedis.close();
			return;
		}
		int countint = Integer.parseInt(count);
		if(countint>=3) {
			System.out.println(countint);
			printWriter.print("3");//3代表次数大于三次
			printWriter.close();
			jedis.close();
			return;
		}
		jedis.incr(string);
		jedis.setex(string2,120,code);
		printWriter.print("2");
		printWriter.close();
		jedis.close();
	}
```

* 验证码确认

```java
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter printWriter = response.getWriter();
		String tel = request.getParameter("telno");
		String code = request.getParameter("code");
		Jedis jedis = new Jedis("hadoop103",6379);
		String string = tel + ":count";
		String string2 = tel + ":no";
		String code1 = jedis.get(string2);
		System.out.println("code:"+code);
		System.out.println("code1:"+code1);
		if(code!=null&&code.equals(code1)) {
			printWriter.print("success");
		}else {
			printWriter.print("err");
		}
	}
```

## 6、Redis事务

### ①、Redis中事务的定义

* Redis事务是一个单独的隔离操作：食物中的所有命令都是序列化、按顺序的执行。事务在执行的过程中，不会被其他客户端发送来的命令请求打断。

* Redis事务的主要作用就是串联多个命令防止别的命令插队

### ②、nulti、exec、discard

* 从输入Multi 命令开始，输入的命令都会依次进入命令队列中，但不会执行，知道输入Exec后，Redis会将之前的命令队列中的命令依次执行。
* 组队的过程中可以通过discard来放弃组队

### ③、事务中错误的处理

* 组队中，某个命令出现报告错误，执行时整个的所有队列都会被取消
* 如果执行阶段某个命令报出了错误，则只有报错的命令不会执行，而其他的命令都会执行，不会回滚

### ④、为什么要做成事务

* 悲观锁：行锁、表锁、读锁、写锁等
* 乐观锁：定义一个变量，控制版本，异动数据都会先校验版本字段变量

### ⑤、Redis事务的使用

* WATCH key[key .......]

  在执行multi之前，先执行watch key1 【key2】，可以监视一个或多个key，如果事务执行前这个key被其他命令所改变，那么这个事务将被打断。

* unwatch

  取消watch命令对所有的key的监视

  如果在执行watch命令之后，exec命令或discard命令被执行了的话，那么就不需要再执行unwatch了

* 三特性

  * 单独的隔离操作

    食物中的所有命令都会序列化、按顺序的执行。食物在执行的过程中，不会被其他客户端发送来的命令请求所打断

  * 没有隔离级别的概念

    队列中的命令没有提交之前都不会实际的被执行，因为事务提交前任何指令都不会被实际执行，也就不存在“事务内的查询要看到事务里的更新，在事务外查询不能看到”这个让人万分头疼的问题

  * 不保证原子性

    Redis同一个事务中如果又一条命令执行失败，气候的命令仍然会被执行，没有回滚。

### ⑥、Redis事务案列 秒杀

## 7、Redis持久化

### ①RDB

* 在指定的时间间隔内将内存中的数据集快照写入磁盘，也就是汉化说的Snapshot快照，它恢复时是将快照文件直接督导内存中。
* 备份执行方式：Redis会单独创建（fork）一个子进程来进行持久化，会先将数据写入到一个临时文件中，待持久化过程都结束了，再用这个临时文件替换上次持久化好的文件。整个过程中，主进程是不进行任何IO操作的，这就确保了极高的性能，如果需要进行大规模数据的恢复，且对于数据恢复的完整性不是非常敏感，那RDB方式比AOF方式更加高效。RDB的缺点是最后一次持久化后的数据可能丢失。

* 关于fork：在Linux程序中，fork（）会产生一个和父进程完全相同的子进程，但子进程在此后多会被exec系统调用，处于效率的考虑，Linux中引入了“写时复制技术”，一般情况父进程和子进程会共用同一段物理内存，只有进程空间的隔断的内容要发生变化时，才会将父进程的内容复制一份给子进程。

* RDB保存的文件：在redis.conf中配置文件名称，默认是dump.rdb

* RDB文件的保存路径：默认为Redis启动时命令行所在的目录下，也可以修改：dir ./

* RDB的默认保存策略：

  > save 900 1
  >
  > save 300 10
  >
  > save 60 10000

* 手动保存快照

  > 1、save：只管保存，其他不管，全部阻塞
  >
  > 2、bgsave：按照保存策略自动保存

* RDB的相关配置

  * stop-writes-on-bgsave-error yes

    当Redis无法写入磁盘的话，直接关掉Redis的写操作

  * rdbcompression yes

    进行rdb保存时，将文件压缩

  * rdbchecksum yes

    在存储快照后，还可以让Redis使用CRC64算法进行数据校验，但是这样做会增加大约10%的性能消耗，如果希望获取最大性能的提升，可以关闭此功能

* RDB的备份和恢复
  * 备份：先通过config get dir 查询rdb文件的目录，将*.rdb文件拷贝到别的地方
  * 恢复：关闭Redis，把备份的文件拷贝到工作目录，启动redis，备份数据会直接加载。

* RDB的优缺点：
  * 优点：节省磁盘的空间，恢复速度快
  * 缺点：虽然Redis在fork时使用了写时复制技术，但是如果数据庞大时，还是会比较消耗性能。当备份周期在一定间隔时间做一次备份，所以如果Redis意外宕机的话，就会丢失最后一次快照后的所有修改。

### ②、AOF

* 以日志的形式来记录每个写操作，将Redis执行的过的所有写指令记录下来（读操作不记录），只许追加文件但不可以修改文件，Redis启动之初会读取该文件重新构建数据，换言之，Redis重启的话就根据日志文件的内容将写指令从前到后执行一次以完成数据的恢复工作。

* AOF默认不开启，需要手动到配置文件里面开启配置

  > appendonly no

* 可以在redis.conf中配置文件名称，默认为appendonly.aof

  > appendfilename "appendonly.aof"
  >
  > AOF文件的保存目录和RDB的文件路径一致

* AOF和RDB同时开启，redis听AOF的

* AOF文件故障备份：AOF的备份机制和性能虽然和RDB不同，但是备份和恢复的操作同RDB一样，都是拷贝备份文件，需要恢复时再拷贝到Redis工作目录下，启动系统即加载。

* AOF文件故障恢复：如遇到AOF文件损坏，可通过

  > redis-check-aof --fix appendonly.aof

* AOF同步频率设置 appendfsync always/everysec/no

  * 始终同步
  * 每秒同步
  * 把不主动进行同步，把同步时机交给操作系统。

* Rewrite
  * AOF采用文件追加方式，文件会越来越大，为避免出现这种情况，新增了重写机制，当AOF文件的大小超过了设定的阈值时，Redis就会启动AOF文件压缩，只保留可以恢复数据的最小指令集。可以用bgrewriteaof
  * Redis如何实现重写：AOF文件持续增长而过大时，会fork出一条新进程来讲文件重写（也是险些临时文件最后再rename），遍历新进程的内存中数据，每条记录有一条的Set语句。重写aof文件操作，并没有读取旧的aof文件，而是将整个内存中的数据库内容用命令的方式重写了一个新的aof文件，这点和快照优点类似。

* 何时重写：

  * 重写虽然可以节约大量磁盘的空间，减少恢复时间。但是每次重写还是有一定的负担的，因此设定Redis要满足一定条件才会进行重写。

  * 系统载入时或者上次重写完毕时，Redis会记录此时AOF大小，设为base_size，如果Redis的AOF当前大小>=base_size+base_size*100%(默认)且当前大小>64MB(默认)的情况下，Redis会对AOF重写

    ```
    auto-aof-rewrite-percentage 100
    auto-aof-rewrite-min-size 64mb
    ```

* AOF的优缺点
  * 优点：备份机制更稳健，丢失数据的概率更低；可读的日志文本，通过操作AOF，通过操作AOF，可以处理误操作。
  * 缺点：比起RDB占用更多的磁盘空间，恢复备份速度更慢，每次读写都同步的话，有一定的性能压力。

### ③、RDB和AOF那个更好

* 官方推荐都启用
* 如果数据不敏感，可以选单独用RDB
* 不建议单独用AOF，因为可能会出现BUG
* 如果只是做纯内存的缓存，可以都不用。

## 8、Redis主从复制

### ①、什么是主从复制

主从复制，就是主机数据更新后根据配置和策略，自动同步到备机的master/slaver机制，Master以写为主，Slave以读为主。

### ②、主从复制的目的

* 读写分离，性能扩展
* 容灾快速恢复