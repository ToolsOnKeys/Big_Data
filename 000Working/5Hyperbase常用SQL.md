# Hyperbase常用SQL

## 1、创建表
### 1.1 建HBase内表 
* 创建了一张名为 hbase_inner_table 的内表，存储格式为 HBaseStorageHandler 。
```SQL
CREATE TABLE hbase_inner_table(
  key1 string,
  bi bigint,
  dc decimal(10,2),
  ch varchar(10),
  ts timestamp,
  en string
)STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler';
```
### 1.2 建HBase外表 
```SQL
CREATE EXTERNAL TABLE hbase_external_table(
  key1 string,
  ex1 double,
  ex3 date,
  ex5 string
)
STORED BY 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
WITH SERDEPROPERTIES ("hbase.columns.mapping"=":key,f:q1,f:q4,f:q5") ① 
TBLPROPERTIES ("hbase.table.name"="test.hbase_inner_table");② 
```
* ① 指定外表 hbase_external_table 和已存在的 hbase_inner_table 表的列映射关系。
* ② 指定外表 hbase_external_table 中的列与源表的映射关系。映射时数据类型强行转换，转换失败则 为NULL。
## 2、为内表 hbase_inner_table 添加列
* 对表hbase_inner_table添加了一个数据类型为 boolean 列bl。
```SQL
ALTER TABLE hbase_inner_table ADD COLUMNS (bl boolean);
```
## 3、清空内表 hbase_inner_table
* 表中数据被清空，但表的元数据信息仍存在，可通过 DESCRIBE FORMATTED 查看。
```SQL
  TRUNCATE TABLE hbase_inner_table;
```
## 4、删除表
```SQL
DROP TABLE <tableName>;
```
## 5、创建索引
### 5.1 为内表 hbase_inner_table 创建全局索引
*  根据列ch创建一个名为ch_global的全局索引，并指定该索引字段的长度为10.
```SQL
CREATE GLOBAL INDEX ch_global ON hbase_inner_table(ch(10));
```
### 5.2 为内表 hbase_inner_table 创建全文索引
* 为内表hbase_inner_table根据列en、cn来创建全文索引
```SQL
CREATE FULLTEXT INDEX ON hbase_inner_table(bi,ch,en) SHARD NUM 1;
```
## 6、删除索引
### 6.1 删除全局索引：ch_global
```SQL
DROP INDEX ch_global ON hbase_inner_table;
```
### 6.2 删除内表 hbase_inner_table 的全文索引
* 目前HBase不支持使用SQL生成索引，您可以从hbase shell中执行 rebuild 指令来生成索引
```SQL
DROP FULLTEXT INDEX ON hbase_inner_table;
```
## 7、插入数据
### 7.1 向hbase_inner_table表中单条插入数据
```SQL
INSERT INTO hbase_inner_table
VALUES('001',1,1.01,'Hyperbase','2017-01-08 20:31:46','sunday',true);
INSERT INTO hbase_inner_table
VALUES('002',2,2.01,'transwarp hbase','2017-01-09 10:25:45','monday',true);
INSERT INTO hbase_inner_table
VALUES('003',3,3.01,'hbase','2017-01-10 15:05:20','tuesday',false);
```
### 7.2 向hbase_inner_table表中批量插入数据
```SQL
BATCHINSERT INTO hbase_inner_table BATCHVALUES (
VALUES('004',4,4.01,'esdrive','2017-01-11 15:05:20','wednesday',false),
VALUES('005',5,5.01,'transwarp es','2017-01-12 15:18:18','thursday',false),
VALUES('006',6,6.01,'hyperdrive','2017-01-13 05:13:13','friday',false),
VALUES('007',7,7.01,'inceptor','2017-01-14 10:55:20','saturday',false),
VALUES('008',8,8.01,'fulltext','2017-01-15 17:23:40','tuestuesday',false)
);
```
## 8、更新数据
```SQL
update hbase_inner_table set bl=false where key1='001';
```
## 9、删除记录
```SQL
DELETE FROM hbase_inner_table WHERE key1='001';
```
## 10、利用索引查询
* 该参数设置完成后才能利用索引进行有效的查询
```SQL
set ngmr.exec.mode=local;
```
## 10.1 全局索引
### 10.1.1 用全局索引ch_global查询
* 利用全局索引‘ch_global’查询列ch中值为‘hbase’，且列bl值为false的记录。
```SQL
select/*+USE_INDEX(t1 USING ch_global)*/ * from hbase_inner_table t1 where ch='hbase'and bl=false;
```
### 10.1.2 不使用全局索引进行查询
* 不利用索引查询列ch值为‘hbase’，且列bl值为false的记录。
```SQL
select/*+USE_INDEX(t1 USING NOT_USE_INDEX)*/ key1, bi, dc, ch, ts, en, bl from hbase_inner_table t1 where ch='hbase'and bl=false;
```
## 10.2 全文索引
### 10.2.1 精确匹配(term)
```SQL
select * from hbase_inner_table where contains(en, "term 'tuesday'");
```
### 10.2.2 前缀匹配(prefix)
```SQL
select * from hbase_inner_table where contains(en, "prefix 'tues'");
```
### 10.2.3 模糊查询(wildcard)
* 查询 \* 前的字符 tues出现任意次，且以 day结尾记录。
```SQL
select * from hbase_inner_table where contains(en, "wildcard 'tues*day'");
```
### 10.2.4 多个操作符查询
```SQL
select * from hbase_inner_table where contains(en, "wildcard 'tues*day'") and contains(bi,"term '3'");
```
### 10.2.5 in表达式(in)//枚举
```SQL
select * from hbase_inner_table where contains(en, "in 'sunday,monday'");
```
### 10.2.6 正则表达式(regexp)
* 查询en列满足正则表达式为 's.*y' 的记录，.* 表示 s和 y间可出现任意个字符。
```SQL
select * from hbase_inner_table where contains(en, "regexp 's.*y'");
```
### 10.2.7 全文检索（match）
* 不要对非 STRING 类型进行模糊、前缀、正则等查询。如果是对数字类型的进行范围查询，则需保证该列的数据类型为 #b 才可以
```SQL
select * from hbase_inner_table where contains(en, "match 'tuesday'");
```
### 10.2.8 范围查询
```SQL
select * from hbase_inner_table where contains(bi, "> '6'");
```
### 10.2.9 范围表达式(range)
```SQL
select * from hbase_inner_table where contains(bi, "range '[1,3)'");
```