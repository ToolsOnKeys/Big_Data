# Pheonix

## 1、Pheonix简介

### ①、Pheonix定义

* Phoenix是HBase的开源SQL皮肤。可以使用标准JDBC API代替HBase客户端API来创建表，插入数据和查询HBase数据。

### ②、Pheonix特点

* 1）容易集成：如Spark，Hive，Pig，Flume和Map Reduce；

* 2）操作简单：DML命令以及通过DDL命令创建和操作表和版本化增量更改；

* 3）支持HBase二级索引创建。

## 2、Pheonix 快速入门

### ①、安装

#### Ⅰ、官网地址

<http://phoenix.apache.org/>

#### Ⅱ、Pheonix部署

```
1）上传并解压tar包
[atguigu@hadoop101 module]$ tar -zxvf /opt/software/apache-phoenix-4.14.2-HBase-1.3-bin.tar.gz -C /opt/module

[atguigu@hadoop101 module]$ mv apache-phoenix-4.14.2-HBase-1.3-bin phoenix
2）复制server包并拷贝到各个节点的hbase/lib
[atguigu@hadoop102 module]$ cd /opt/module/phoenix/

[atguigu@hadoop102 phoenix]$ cp phoenix-4.14.2-HBase-1.3-server.jar /opt/module/hbase/lib/
[atguigu@hadoop102 phoenix]$ scp phoenix-4.14.2-HBase-1.3-server.jar hadoop103:/opt/module/hbase/lib/
[atguigu@hadoop102 phoenix]$ scp phoenix-4.14.2-HBase-1.3-server.jar hadoop104:/opt/module/hbase/lib/
3）复制client包并拷贝到各个节点的hbase/lib
[atguigu@hadoop102 module]$ cd /opt/module/phoenix/
[atguigu@hadoop102 phoenix]$ cp phoenix-4.14.2-HBase-1.3-client.jar /opt/module/hbase/lib/
[atguigu@hadoop102 phoenix]$ scp phoenix-4.14.2-HBase-1.3-client.jar hadoop103:/opt/module/hbase/lib/
[atguigu@hadoop102 phoenix]$ scp phoenix-4.14.2-HBase-1.3-client.jar hadoop104:/opt/module/hbase/lib/
4）配置环境变量
#phoenix
export PHOENIX_HOME=/opt/module/phoenix
export PHOENIX_CLASSPATH=$PHOENIX_HOME
export PATH=$PATH:$PHOENIX_HOME/bin
5）启动Pheonix
[atguigu@hadoop101 phoenix]$ /opt/module/phoenix/bin/sqlline.py hadoop102,hadoop103,hadoop104:2181
```

### ②、Pheonix Shell 操作

#### Ⅰ、表的操作

```sql
#显示所有表
> !table 或 !tables
#创建表(在phoenix中，表名等会自动转换为大写，若要小写，使用双引号，如"us_population"。
指定多个列的联合作为RowKey)
> create table if not exists dh(id varchar primary key,name varchar)
> CREATE TABLE IF NOT EXISTS us_population (
State CHAR(2) NOT NULL,
City VARCHAR NOT NULL,
Population BIGINT
CONSTRAINT my_pk PRIMARY KEY (state, city));
#插入数据
> upsert into table_name values('value1','value2');
#查询记录
> select * from table_name where column_name = 'value1';
#删除记录
> delete from table_name where column_name = 'value1';
#删除表
> drop table table_name;
#退出命令行
> !quit
```

#### Ⅱ、表的映射

##### 1、表的映射

* 表的关系：默认情况下，直接在HBase中创建的表，通过Phoenix是查看不到的。如果要在Phoenix中操作直接在HBase中创建的表，则需要在Phoenix中进行表的映射。映射方式有两种：视图映射和表映射。

* 在命令行中创建表test

  ```sql
  > create 'test','info1','info2'
  ```

* 视图映射

  ```
  > create view "test"(id varchar primary key, "info1"."name" varchar,"info2"."addr" varchar);
  > drop view "test";
  ```

* 表映射

  ```
  create table "test"(id varchar primary key, "info1"."name" varchar,"info2"."addr" varchar) column_encoded_bytes=0;
  ```

### ③、Pheonix JDBC操作

* 创建项目依赖

* 案例代码

  ```java
  public class PhoenixTest1 {
      public static void main(String[] args) throws ClassNotFoundException, SQLException {
          //1、定义参数
          String driver = PhoenixDriver.class.getName();
          String url = "jdbc:phoenix:hadoop101,hadoop102,hadoop103:2181";
          //2、加载驱动
          Class.forName(driver);
          //3、建立连接
          Connection connection = DriverManager.getConnection(url);
          //4、预编译sql
          PreparedStatement preparedStatement = connection.prepareStatement("select * from \"test\"");
          //5、查询返回值
          ResultSet resultSet = preparedStatement.executeQuery();
          //6、打印结果
          while(resultSet.next()){
              System.out.println(resultSet.getString(1)+"-"+resultSet.getString(2));
          }
          //7、关闭资源
          connection.close();
      }
  }
  ```

  

