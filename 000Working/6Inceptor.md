# Inceptor常用SQL
* MySQL中的语句都能用，不再一一描述，只记录一些不同  详情见Inceptor 6.0文档 3.4.4查询语句这节
* 查询语句 SELECT开头，可以通过添加多种从句从Inceptor中的表中获得信息。
* 最常使用的数据查询语句的语法如下：
```SQL
SELECT [ALL | DISTINCT] select_expression, select_expression, ...
FROM table_reference
[WHERE where_condition]
[GROUPBY col_list]
[CLUSTERBY col_list 
 | [DISTRIBUTE BY col_list] [SORT BY col_list]
 ]
 [LIMIT (M,)N  
| [OFFSET M ROWSFETCHNEXT | FIRST] N ROWSONLY];
```

* 如果不指定数据库，Inceptor默认从default数据库中寻找用户指定的查询来源。要指定数据库，可以用"."来表示表和数据库的从属关系，比如    
```SQL
SELECT * FROM database_name.table_name;
```
* 或者还可以在查询前使用USE语句来指定当前数据库：
```SQL
USE database_name;
SELECT query_specifications;
USE default;
```
* 1、-- 过滤 
  -- WHERE在SELECT之前过滤，而HAVING在SELECT之后过滤。

* 2、-- ORDER BY, SORT BY, DISTRIBUTE BY和CLUSTER BY 略

* 3、-- GROUP BY
  * 3.1 --单列GROUP BY
    -- 单列GROUP BY就是GROUP BY 子句中只有一列。
    -- 举例我们可以用对transactions表用GROUP BY查看各个账户进行交易的次数：
    SELECT acc_num, COUNT(trans_id) AS cnt FROM transactions GROUP BY acc_num;
    -- GROUP BY前面的列只能包含聚合函数和被分组列 
    -- 注意：一次查询可以使用多个聚合函数，但是聚合函数的参数中的DISTINCT列必须相同。
  * 3.2 -- GROUP BY (number)
    SELECT select_expression1, select_expression2, ...
    GROUP BY groupby_expression [,groupby_expression, ...]
    -- Inceptor中支持group by 1 (第一列），group by 2（第二列）这类用法，其中数字1是指select_expression的位置。

3.3 --多列GROUP BY
-- 多列GROUP BY就是GROUP BY子句中有不止一列。
SELECT acc_num, trans_type, COUNT(trans_id) AS cnt FROM transactions
GROUP BY acc_num, trans_type;

3.4 --用表达式GROUP BY
-- tdh_todate是Inceptor自带的函数，可以用来提取trans_time 中的月份：
SELECT tdh_todate(trans_time, 'yyyyMMddHHmmss', 'MM') AS date,COUNT(trans_id) AS cnt FROM transactions
GROUP BY tdh_todate(trans_time, 'yyyyMMddHHmmss', 'MM');

3.5 --在GROUP BY子句中过滤：HAVING 子句
-- 如果过滤条件受带GROUP BY的查询结果影响，那么就不能用WHERE子句来过滤，而要用HAVING子句
SELECT acc_num, MAX(price*amount) AS max_value FROM transactions WHERE trans_time<'20140630235959'
GROUP BY acc_num 
HAVING MAX(price*amount)>5000;
--有两个过滤条件：WHERE子句中的过滤条件和查询结果无关，而HAVING子句中的过滤要在查询结束后才执行。
--注意：在WHERE子句中不能有聚合函数，因为Inceptor在执行GROUP BY子句之前就会执行WHERE子句。HAVING子句中可以含有聚合函数：

3.6 -- SELECT DISTINCT + GROUP BY
--当执行形如SELECT DISTINCT <key> +　GROUP BY的语句之前必须先打开开关inceptor.select.distinct.group.by.enabled。该开关默认处于关闭状态，如果不启动则出现语法报错。
SET inceptor.select.distinct.group.by.enabled = TRUE;
SELECT DISTINCT acc_num, COUNT(1)FROM transactions
GROUP BY acc_num;

3.7 -- GROUP BY 扩展：ROLLUP/CUBE/GROUPING SETS
-- ROLLUP
-- 生成聚合行、超聚合行和总计行。举例来说，下面的代码
SELECT a, b, c, SUM(expression) FROM table GROUP BY ROLLUP(a, b, c);
-- 会为 (a, b, c)、(a, b) 和 (a) 值的每个唯一组合生成一个带有小计的行。还将计算一个总计行。 
-- 详细地说，以上代码会计算以下四条查询并将结果一并输出：
SELECT a, b, c, sum(expression) FROM table GROUP BY a, b, c;   -- (a, b, c)组合小计 
SELECT a, b, NULL, sum(expression) FROM table GROUP BY a, b;   -- (a, b)组合小计
SELECT a, NULL, NULL, sum(expression) FROM table GROUP BY a;   -- (a)组合小计
SELECT NULL, NULL, NULL, sum(expression) FROM table;           -- 总计

-- CUBE
-- 生成聚合行、超聚合行、交叉表格行和总计行
SELECT a, b, c, SUM (expression) FROM table GROUP BY CUBE (a,b,c);
-- 会为 (a, b, c)、(a, b)、(a, c)、(b, c)、(a)、(b) 和 (c) 值的每个唯一组合生成一个带 有小计的行，还会生成一个总计行。
-- 详细来说，以上代码会进行以下八条计算，并将结果一并输出：
SELECT a, b, c, sum(expression) FROM table GROUP BY a, b, c;  -- (a, b, c)组合小计
SELECT a, b, NULL, sum(expression) FROM table GROUP BY a, b;  -- (a, b)组合小计
SELECT a, NULL, c, sum(expression) FROM table GROUP BY a, c;  -- (a, c)组合小计
SELECT NULL, b, c, sum(expression) FROM table GROUP BY b, c;  -- (b, c)组合小计
SELECT a, NULL, NULL, sum(expression) FROM table GROUP BY a;  -- (a)组合小计
SELECT NULL, b, NULL, sum(expression) FROM table GROUP BY b;  -- (b)组合小计
SELECT NULL, NULL, c, sum(expression) FROM table GROUP BY c;  -- (c)组合小计
SELECT NULL, NULL, NULL, sum(expression) FROM table;          -- 总计

-- GROUPING SETS 
-- GROUPING SETS生成交叉表格行。
-- 举例来说，
SELECT a, b, c, sum(expression) FROM table GROUP BY GROUPING SETS(a,b,c);
-- 会为 (a)、(b) 和 (c) 值的每个唯一组合生成一个带有小计的行。
-- 详细地说，以上代码会进行以下三条计算，并将结果一并输出：
SELECT a, NULL, NULL, sum(expression) FROM table GROUP BY a;  -- (a)组合小计
SELECT NULL, b, NULL, sum(expression) FROM table GROUP BY b;  -- (b)组合小计
SELECT NULL, NULL, c, sum(expression) FROM table GROUP BY c;  -- (c)组合小计

--FUNC(DISTINCT KEY)+GROUP BY 扩展
--支持形如UDAF(DISTINCT)+GROUP BY [ROLLUP|CUBE|GROUPING SET]的语句。
SELECT COUNT(store), COUNT(type), COUNT(DISTINCT grade)FROM inventory
GROUP BY ROLLUP(store, type, grade);

4、-- 多表查询：JOIN 
4.1 -- 笛卡尔连接：Cartesian Join
SELECT * FROM join_demo1 JOIN join_demo2 ON 1=1;
-- 这样的表连接会产生大量的数据，而且并不经常具有意义，所以很少出现在实际应用中。
-- 大多数这样的表连接产生于没有加JOIN条件的错误。
-- 所以为了代码的清晰，如果真的需要进行笛卡尔积连接，最好使用专门的关键词CROSS JOIN:
SELECT table1.col1, table2.col2, ...FROM table1 CROSS JOIN table2

4.2 -- 内连接：INNER JOIN
-- 内连接只显示参与连接的表中有匹配的记录。JOIN和INNER JOIN在这里用法一样。 在一次查询中可以连接两个以上的表：
SELECT a.val, b.val, c.val FROM a JOIN b ON (a.key = b.key1) JOIN c ON (c.key = b.key2)

4.3 -- 外连接：OUTER JOIN
-- 内连接会将被连接的两张表中互相没有匹配值的纪录忽略。
-- 如果想要在连接结果中看到没有匹配值的记录，则应该使用外连接。
-- 外连接又分为左外连接(left outer join)、右外连接(right outer join)和全外连接(full outer join)。
SELECT student_info.stu_name, course_info.course_name FROM student_info LEFT OUTER JOIN course_info ON (student_info.course_id = course_info.course_id);
SELECT student_info.stu_name, course_info.course_name FROM student_info RIGHT OUTER JOIN course_info ON (student_info.course_id = course_info.course_id);
SELECT student_info.stu_name, course_info.course_name FROM student_info FULL OUTER JOIN course_info ON (student_info.course_id = course_info.course_id);

4.4 --  隐式连接：Implicit JOIN 
-- 隐式JOIN的命令中不含有JOIN…ON…关键词，而是通过WHERE子句作为连接条件将两张表连接。
SELECT student_info.stu_name, course_info.course_name FROM student_info, course_info 
WHERE student_info.course_id = course_info.course_id;

4.5 -- 自然连接：NATURAL JOIN 
-- 使用NATURAL JOIN，用户可以不需要明确写出JOIN条件。
-- Inceptor会自动在被连接的两张表中寻找名字相同的列。如果两张表table1和table2中存在同名列col1，Inceptor会自动生成JOIN条件table1.col1=table2.col1。
-- 如果Inceptor在被连接的两张表中找不到同名列，Inceptor会将指令作为无条件的连接，也就是一个笛卡尔积。
SELECT name, trans_idFROM user_info NATURAL JOIN transactions;

4.6 -- 多表连接
-- 可以在一次查询中用多个JOIN子句连接多张表。
SELECT select_expression, select_expression, ...
FROM table_reference [(RIGHT|LEFT|FULL) OUTER] JOIN table_reference ON (join_condition) [(RIGHT|LEFT|FULL) OUTER] JOIN table_reference ON (join_condition) ...
-- 每一个JOIN子句都可以是不同的连接（内连接，左/右/全外连接，左半连接等等）。

4.7 -- 重复连接
-- 有时候同一张表在一次查询中需要和多张其他表连接。假设我们有三张表table1, table2和table3。其中table1要和table2和table3各连接一次。
-- 这种情况下，用户需要给table1在两次连接中起两个不同的化名来让Inceptor能够分辨在各子句中table1的角色：
SELECT t1.col1, tb1.col2, t2.col1, t3.col,...
FROM table1 t1 JOIN table2 t2 ON t1.col1 = t2.col1    
INNER JOIN table3 t3  ON t2.col2 = t3.col1    
INNER JOIN table1 tb1 ON t3.col = tb1.col2;

4.8 -- 表的自连接
-- 一张表也可以和自己连接，此时需要给表取两个不同的化名来让Inceptor能够分辨在各子句中表的角色。
-- 我们用一个包含了员工信息的表来作为例子。表中含有员工工号，员工姓名，员工的上级工号和入职时间：
-- 我们用这张表和自己连接来查询员工和他们上级的姓名：
SELECT e.employee_name, sup.employee_name AS manager_name 
FROM employee_info e
LEFT OUTER JOIN employee_info sup 
ON (e.sup_id = sup.employee_id);

4.9 -- 左半连接和左半反连接
-- 左半连接用来查看左表中符合和JOIN条件的记录。左半反连接用来查看左表不符合JOIN条件的记录。
-- 左半连接和左半反连接都只显示左表中的记录。
-- 左半连接可以通过LEFT SEMI JOIN, WHERE… IN 和WHERE EXISTS 中嵌套子查询来实现。而左半反连接可以通过在LEFT …NOT IN/EXISTS中嵌套子查询来实现。
4.9.1 -- 左半连接
SELECT t1.num, t1.letter FROM test11 t1 LEFT SEMI JOIN test2 t2 ON t1.num = t2.num;
SELECT t1.num, t1.letter FROM test11 t1 WHERE t1.num IN (SELECT t2.num FROM test2 t2 );
SELECT t1.num, t1.letter FROM test11 t1 WHERE EXISTS ( SELECT 1 FROM test2 t2  WHERE t2.num = t1.num);

4.9.2 --左半反连接 
SELECT t1.num, t1.letter FROM test11 t1 WHERE t1.num NOT IN (SELECT t2.num FROM test2 t2 );
SELECT t1.num, t1.letter FROM test11 t1 WHERE NOT EXISTS (SELECT 1 FROM test2 t2 WHERE t1.num = t2.num);

4.10 --不等价连接
-- 要执行不等价连接，ON子句中的连接条件必须是等价条件，不等价条件体现在WHERE子句中的过滤条件中。
-- 不等价连接和笛卡尔积相像，很容易返回大量结果（在两表行数乘积的级别），ON子句中要求有等价条件可以限制结果的数量。
-- 如果确定不需要限制结果数量，可以在ON子句中的等价条件里放一个永远成立的等式，比如1=1。
-- 执行这样的操作必须格外小心。
-- 假设公司想要在员工中间组织一次下棋比赛，每对员工之间都要进行一场比赛，我们想要生成包含所有员工对的数据：
SELECT e1.employee_name, e2.employee_name
FROM employee_info e1
JOIN employee_info e2 ON 1 = 1
WHERE e1.employee_name <> e2.employee_name;

-- 这里join条件是e1.employee_name<> e2.employee_name，因为一个人不能和自己比赛。
-- 这条指令会导致每个 员工对都会重复一次，所以我们可以修改连接条件为e1.employee_name > e2.employee_name：
SELECT e1.employee_name, e2.employee_name
FROM employee_info e1
JOIN employee_info e2 ON 1 = 1
WHERE e1.employee_name > e2.employee_name;

4.11 -- MAP JOIN
-- 如果两张被连接的表中有一张比较小（100MB以下），那么可以通过MAP JOIN来提高执行速度。
--MAP JOIN会将小表放入内存中，在map阶段直接拿另一张表的数据和内存中表数据做匹配，由于省去了shuffle，速度会比较快。
SELECT /*+ MAPJOIN(b) */ select_expression, select_expression, ...
FROM table_reference JOIN table_reference ON join_condition
-- Inceptor已经有了自动MAP JOIN的功能，就是在有一张表在100MB一下时，Inceptor会自动执行MAP JOIN。
--所以用户可以无需特别指明使用MAP JOIN。如果在参与JOIN的表都较大时却指明使用MAP JOIN，可能会导致内存溢出。

5 -- 子查询
-- 子查询是嵌套在查询语句中的查询语句。子查询根据是否和包含它的父查询的结果相关分为非关联子查询和关联子查询。
-- Inceptor高度支持子查询的各种嵌套：非关联子查询可以在FROM,WHERE,SELECT和HAVING子句中嵌套。
-- 关联子查询可以在WHERE和SELECT中嵌套，而不能在HAVING和FROM子句中嵌套。
5.1 -- 非关联子查询
-- 非关联子查询内容和包含它的父查询结果不相关。当子查询和父查询不相关，Inceptor会在执行父查询之前先执行完成子查询。
5.1.1 -- 在WHERE子句中嵌套
-- 单行单列的子查询结果
SELECT name, acc_num, citizen_id, acc_level, reg_date
FROM user_info
WHERE reg_date = (
  SELECT MIN(reg_date) FROM user_info
);

-- 单行多列的子查询结果
-- IN运算符 当子查询结果有不止一条记录，要用IN来表示查询结果须是子查询结果集合中的元素：
SELECT employee_name
FROM employee_info
WHERE employee_id IN (
  SELECT sup_id FROM employee_info
);

SELECT employee_name
FROM employee_info
WHERE employee_id NOT IN (
  SELECT sup_id FROM employee_info
);

5.1.2 --在FROM子句中嵌套
-- 下例查询所有进行过交易的账户持有人名字：
SELECT DISTINCT name
FROM (
  SELECT name FROM user_info
  JOIN transactions
  ON user_info.acc_num = transactions.acc_num
);

-- 下例查询所有个人平均交易额大于所有平均交易额的用户名字
SELECT name
FROM user_info JOIN (
  SELECT transactions.acc_num, AVG(price*amount) avg_trans
  FROM transactions
  GROUP BY transactions.acc_num
) temp
  ON user_info.acc_num = temp.acc_num
  WHERE avg_trans > (SELECT AVG(price*amount) FROM transactions);

5.1.3 --在SELECT子句中嵌套
-- 下例查看各用户的个人平均交易额和所有交易的平均交易额的差：
SELECT acc_num, AVG(price*amount) - (SELECT AVG(price*amount) FROM transactions) AS avg_gap
FROM transactions
GROUP BY acc_num;

5.1.4 --在HAVING子句中嵌套
-- 下例查询最大一笔交易的执行账户和交易额
SELECT acc_num, MAX(price*amount) AS max_value
FROM transactions
GROUP BY acc_num
HAVING MAX(price*amount) = (
  SELECT max(price*amount) FROM transactions
);

5.2 -- 关联子查询
-- 关联子查询的内容和父查询相关。Inceptor会对每条在父查询中出现的记录执行一次子查询
-- 注意:关联子查询中的关联条件不支持OR,也不支持仅包含非等值比较。
5.2.1 -- 在WHERE子句中嵌套
-- Inceptor支持的WHERE子句嵌套需要满足以下要求：
-- 1. WHERE子句中必须包含至少一条等值关系，如果执行业务没有客观要求等值关联，请用户手动添加条件“1=1”。这是为了避免资源被贪婪占用导致枯竭，以保证系统的稳定性。
-- 2. 主查询和子查询之间必须用标量比较运算符连接（包括‘>’、‘<’、‘=’、‘<>’、‘>=’、‘<=’）。
-- 3. 要求子查询的结果必须是一行一列的返回，即标量。
-- 4. 子查询中允许有等值与非等值条件
-- 下例查询了总共进行过3笔交易的账户持有人姓名和账户号码。 注意，当关联子查询中有COUNT函数时，必须打开开关set hive.support.subquery.join.conversion.count=true。
set hive.support.subquery.join.conversion.count=true;
SELECT user_info.name, user_info.acc_num
FROM user_info
WHERE 3=(
  SELECT COUNT(*) FROM transactions
  WHERE user_info.acc_num = transactions.acc_num
);

SELECT COUNT(*) AS cnt FROM TABLEA A, TABLEB B
WHERE ((1=1)) AND ((A.salary > (SELECT (SUM(csA.age) - 114)
  FROM TABLEA csA, TABLEB csB
  WHERE (A.age = csA.age) AND
  ((csB.salary / -9) = (A.salary * -79)))));

5.2.2 -- EXISTS和NOT EXISTS
-- 在WHERE中嵌套子查询时经常会用到EXISTS和NOT EXISTS。当我们只关心子查询有记录返回，而不关心子查询返回的记录内容和记录条数时，我们就可以用WHERE EXISTS。
-- WHERE EXISTS用来查看子查询中的关系是否成立并且返回使得子查询中关系成立的记录（也就是过滤掉使得子查询中的关系不成立的记录）。
-- 比如，假设查询买过单股价格在100元以内的股票的用户，语句应为
SELECT user_info.name, user_info.acc_num
FROM user_info WHERE EXISTS (
  SELECT 1 FROM transactions
  WHERE user_info.acc_num = transactions.acc_num
  AND price < 100
);
-- 事实上，我们建议如果WHERE子句需要满足某种关系（大于、等于、小于、不等于，等等），尽量使用WHERE EXISTS并在子查询中表达关系，而不是通过比较子查询的结果和别的量来表达关系。

-- WHERE NOT EXISTS则用来查看子查询中的关系是否成立并且返回使得子查询中关系不成立的记录（也就是过滤掉使得子查询中的关系成立的记录）。
-- 下例查询了所有没有进行交易的账户持有人姓名和账户号码
SELECT user_info.name, user_info.acc_num
FROM user_info WHERE NOT EXISTS (
  SELECT 1 FROM transactions
  WHERE user_info.acc_num = transactions.acc_num
);
-- 这里，WHERE NOT EXISTS子句中嵌套的子查询返回的是一个常数，这充分体现了EXISTS和NOT EXISTS仅关心子查询是否返回结果，而不关心返回的结果是什么 。

5.2.3 -- 在SELECT子句中嵌套
-- 下例返回所有账户的持有人姓名，账户号码和账户平均交易额：
SELECT user_info.name, user_info.acc_num, (
  SELECT AVG(price*amount) FROM transactions
  WHERE user_info.acc_num = transactions.acc_num
)
FROM user_info;

5.3 -- 子查询的多层嵌套
SELECT name FROM (
  SELECT name, acc_num FROM (
  SELECT name, acc_num, password FROM (
  SELECT name, acc_num, password, bank_acc FROM user_info)
  )
  );

6 --  集合运算：UNION/INTERSECT/EXCEPT
-- Inceptor提供三种方法来对SELECT语句结果进行集合运算：并集(UNION)、交集(INTERSECT)和减去(EXCEPT)。而每个集合运算都有两种选择， 带有ALL和不带有ALL
-- UNION和INTERSECT带上ALL不去重

-- EXCEPT和EXCEPT ALL做集合减法。
-- A EXCEPT B 将A中所有和B重合的记录除去，然后返回去重后的A中剩下的记录。
-- A EXCEPT ALL B 将A中所有和B重合的记录除去，然后不去重的A中剩下的记录。 