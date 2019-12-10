# JDBC

## 1、概述

> 在Java中，数据库存取技术可分为以下几类：
>
> 1、JDBC直接访问数据库
>
> 2、JDO技术
>
> 3、第三方O/R工具，如Hibernate，Mybatis等

### ①、什么是JDBC

* JDBC是一个独立于特定数据库管理系统、通用的SQL数据库存取和操作的公共接口（API），定义了用来访问数据库的标准Java类库，使用这个类库可以以一种表征的方法、方便的访问数据资源。
* JDBC是SUN公司提供一套用于数据库操作的接口API，java程序员至于要面向这套接口编程即可。

### ②、JDBC API

* JDBC API是一系列的接口，它统一和规范了引用程序与数据库的链接、执行SQL语句，并得到返回结果等各类操作。声明在java.sql和javax.sql包中。

  ![](D:\BigData\BigData\03JDBC\JDBC相关图解\JDBC—API.jpg)

### ③、JDBC程序编写步骤

![](D:\BigData\BigData\03JDBC\JDBC相关图解\JDBC程序编写步骤.bmp)

* 导入相应的jar包

  > 驱动程序相应的jar包要到数据库提供商那边下载。

* 驱动注册与加载

  >1、加载驱动，把驱动类加载到内存中
  >
  >2、注册驱动，把驱动类的对象交给DriverManager管理，用于后面创建链接等使用。

  ```java
  //加载并注册驱动
  Class.forName("com.mysql.jdbc.Driver");
  ```

* 创建Connection对象

  > 通过DriverManager类建立到数据库的链接

  ```java
  //获取数据库链接
  //url格式：
  //jdbc:mysql://主机名：端口号/数据库名称？参数=值&参数=值...
  String url = "jdbc:mysql://localhost:3306/test";
  Connection conn = DriverManager.getConnection(rul,"id","passwd");
  ```

* 操作或访问数据库

  > 1、数据库链接被用于向数据库服务器发送命令和sql语句，并接受数据库服务器返回的结果。
  >
  > 2、其实数据库链接就是一个Socket链接

  1、Statement：

  * Statement：用于执行静态SQL语句并返回它所生成结果的对象。

  * PrepatedStatement：SQL语句被预编译并存储在此对象中，然后可以使用此对象多次高效的执行该语句

  * CallableStatement：用于执行SQL的存储过程

    ``` java
    Statement st = conn.createStatement();//获取Statement对象
    int excuteUpdate(String sql):更新
    ResultSet excuteQuery(String sql):查询
    ```

  2、ResultSet：

  * ResultSet对象以逻辑表格的形式封装了执行数据库操作的结果集

  * ResultSet对象维护了一个执行当前数据行的游标。

    ```java
    boolean next();
    getXXX(String columnLabel);
    getXXX(int index);
    ```

* 释放资源

  ```java
  Statement.close();
  Connection.close();
  ```

### ④Statement处理sql，实例代码

```java
package com.atguigu.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.Test;

/*
 * 网络编程：tcp
 * 
 * 服务器端：
 * 1、ServerSocket server = new ServerSocket(3306);
 * 2、Socket socket = server.accept();
 * 3、InputStream input = socket.getInputStream();//接收sql，客户端传过来的
 * 4、在服务器执行sql
 * 5、把结果给客户端
 * 
 * 客户端：
 * 1、Socket socket = new Socket(服务器的IP地址，3306);
 * 2、传sql
 * 3、OutputStream out = socket.getOutputStream();
 * 4、out.write(sql);
 * 5、接收结果
 * 6、断开连接  out.close();socket.close();
 */
public class TestStatement {
	@Test
	public void testAdd()throws Exception{	
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement st = conn.createStatement();
        String sql = "INSERT INTO t_department(dname,description) VALUES('财务部','负责发钱工作')";
		int len = st.executeUpdate(sql);//把insert,update,delete都用这个方法
		if(len>0){
			System.out.println("添加成功");
		}else{
			System.out.println("添加失败");
		}
		st.close();
		conn.close();
	}
	
	@Test
	public void testUpdate()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement st = conn.createStatement();
		
        String sql = "UPDATE t_department SET description = '负责发工资、社保、公积金工作' WHERE dname ='财务部'";
		int len = st.executeUpdate(sql);//把insert,update,delete都用这个方法
		if(len>0){
			System.out.println("修改成功");
		}else{
			System.out.println("修改失败");
		}
		st.close();
		conn.close();
	}
	
	@Test
	public void testDelete()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		Statement st = conn.createStatement();

		String sql = "DELETE FROM t_department WHERE did =6";
		int len = st.executeUpdate(sql);//把insert,update,delete都用这个方法
		
		if(len>0){
			System.out.println("删除成功");
		}else{
			System.out.println("删除失败");
		}
		
		st.close();
		conn.close();
	}
	
	@Test
	public void testSelect()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		Statement st = conn.createStatement();
		
        String sql = "SELECT * FROM t_department";
		ResultSet rs = st.executeQuery(sql);//select语句用query方法
		while(rs.next()){//是否有下一行
			//取这一行的单元格
			int id = rs.getInt(1);
			String name = rs.getString(2);
			String desc = rs.getString(3);
			
			System.out.println(id+"\t" + name + "\t" + desc);
		}
		
		rs.close();
		st.close();
		conn.close();
		
	}
	
	@Test
	public void testSelect2()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		Statement st = conn.createStatement();

		String sql = "SELECT did,dname FROM t_department";
		ResultSet rs = st.executeQuery(sql);//select语句用query方法
		while(rs.next()){//是否有下一行
			//取这一行的单元格
			int id = rs.getInt("did");
			String name = rs.getString("dname");
			System.out.println(id+"\t" + name);
		}
		
		rs.close();
		st.close();
		conn.close();
	}
}

```

### ⑤、PreparedStatement 处理sql，实例代码

```java
package com.atguigu.preparedstatement;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import org.junit.Test;

/*
 * PreparedStatement：是Statement子接口
 * 1、SQL不需要拼接
 * 2、SQL不会出现注入
 * 3、可以处理Blob类型的数据
 * tinyblob：255字节以内
 * blob：65K以内
 * mediumblob:16M以内
 * longblob：4G以内
 * 
 * 如果还是报错：xxx too large，那么在mysql的安装目录下，找my.ini文件加上如下的配置参数：
 * max_allowed_packet=16M
 * 注意：修改了my.ini文件，一定要重新启动服务
 * 
 */
public class TestPreparedStatement {
	@Test
	public void add() throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入姓名：");
		String name = input.nextLine();
		
		System.out.println("请输入手机号码：");
		String tel = input.nextLine();
		
		System.out.println("请输入性别：");
		String gender = input.nextLine();
		
		System.out.println("请输入薪资：");
		double salary = input.nextDouble();
		
		System.out.println("请输入部门编号：");
		int did = input.nextInt();
		
		//1、连接数据库
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);

		//2、编写带？的SQL
		String sql = "INSERT INTO t_employee (ename,tel,gender,salary,did) VALUES(?,?,?,?,?)";
		
		// 3、准备一个PreparedStatement：预编译sql
		PreparedStatement pst = conn.prepareStatement(sql);// 对带？的sql进行预编译

		// 4、把?用具体的值进行代替
		pst.setString(1, name);
		pst.setString(2, tel);
		pst.setString(3, gender);
		pst.setDouble(4, salary);
		pst.setInt(5, did);

		// 5、执行sql
		int len = pst.executeUpdate();
		System.out.println(len>0?"添加成功":"添加失败");

		// 6、释放资源
		pst.close();
		conn.close();
	}

	@Test
	public void select() throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入姓名：");
		String name = input.nextLine();
		
		//1、连接数据库
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);

		//2、编写带?的sql
		//孙红雷  ' or '1' = '1
		String sql = "SELECT eid,ename,tel,gender,salary FROM t_employee WHERE ename = ?";
				
		// 3、把带？的sql语句进行预编译
		PreparedStatement pst = conn.prepareStatement(sql);

		// 4、把？用具体的变量的赋值
		pst.setString(1, name);

		// 5、执行sql
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			int id = rs.getInt("eid");
			String ename = rs.getString("ename");
			String tel = rs.getString("tel");
			String gender = rs.getString("gender");
			double salary = rs.getDouble("salary");

			System.out.println(id + "\t" + ename + "\t" + tel + "\t" + gender + "\t" + salary);
		}

		// 6、释放资源
		rs.close();
		pst.close();
		conn.close();
	}

	@Test
	public void addBlob() throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.println("请输入用户名：");
		String username = input.nextLine();

		System.out.println("请输入密码：");
		String pwd = input.nextLine();

		System.out.println("请指定照片的路径：");
		String photoPath = input.nextLine();

		//1、连接数据库
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
		
		//2、 INSERT INTO `user` VALUES(NULL,用户名,密码,照片)
		String sql = "INSERT INTO `user` (username,`password`,head_picture)VALUES(?,?,?)";

		// 3、准备一个PreparedStatement：预编译sql
		PreparedStatement pst = conn.prepareStatement(sql);// 对带？的sql进行预编译

		// 4、对？进行设置
		pst.setString(1, username);
		pst.setString(2, pwd);
		pst.setBlob(3, new FileInputStream(photoPath));

		// 5、执行sql
		int len = pst.executeUpdate();
		System.out.println(len > 0 ? "添加成功" : "添加失败");

		// 6、释放资源
		pst.close();
		conn.close();
	}
}

```

### ⑥、PreparedStatement VS Statement

* 代码的可读性和可维护性是Statement的sql拼接的难题
* PreparedStatement可以方式SQL注入
* PreparedStatement可以处理Blob类型的数据
* PreparedStatement能最大可能提高性能。

### ⑦、批处理

> 当需要成批插入或者更新记录时。可以采用Java的批量更新机制，这一机制允许多条语句一次性提交个数据库批量处理。通常情况下批量比单独提交处理更有效率

>JDBC的批量处理提供了两个方法：

* addBatch()：添加需要批量处理的SQL语句或参数
* executeBatch()：执行批量处理的语句

> 案列代码：

```java
package com.atguigu.preparedstatement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.junit.Test;

public class TestBatch {
	@Test
	public void noBatch()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
        
        String sql = "INSERT INTO t_department(dname,description) VALUES(?,?)";
        PreparedStatement st = conn.prepareStatement(sql);
        for(int i=0; i<1000; i++){
        	st.setString(1, "测试部门" + i);
        	st.setString(2, "测试部门描述"  + i);
        	st.executeUpdate();
        }
		st.close();
		conn.close();
	}
	
	@Test
	public void useBatch()throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true";
		String user = "root";
		String password = "123456";
		Connection conn = DriverManager.getConnection(url, user, password);
        String sql = "INSERT INTO t_department(dname,description) VALUES(?,?)";
        PreparedStatement st = conn.prepareStatement(sql);
        
        for(int i=0; i<1000; i++){
        	st.setString(1, "测试部门" + i);
        	st.setString(2, "测试部门描述"  + i);
        	st.addBatch();
        }
        
        st.executeBatch();
		
		st.close();
		conn.close();
	}
}
```

### ⑧、事务

JDBC程序中当一个链接对象被创建时，默认情况下时自动提交事务：每次执行一个SQL语句时，如果执行成功，就会向数据库自动提交，而不能回滚。

JDBC程序中为了让多个SQL语句作为一个事务执行：

* 调用Connection对象的setAutoCommit（false）；//取消自动提交事务
* 在所有的SQL语句都成功执行后，调用commit（）；//方法提交事务
* 在其中某个操作失败或出现异常时，调用rollback（）；//方法回滚事务
* 若此时Connection没有被彻底关闭，还可能被重复使用，则需要恢复其自动提交状态setAutoCommit(true)

```java
package com.atguigu.transaction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestTransaction {
	public static void main(String[] args){
		Connection conn = null;
		try {
			//1、连接数据库
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost:3306/test";
			String user = "root";
			String password = "123456";
			conn = DriverManager.getConnection(url, user, password);
			//设置手动提交
			conn.setAutoCommit(false);

			String sql1 = "update t_department set description = ? where did = ?";
			PreparedStatement pst1 = conn.prepareStatement(sql1);
			pst1.setObject(1, "挣大钱的");
			pst1.setObject(2, 4);
			int len1 = pst1.executeUpdate();
			System.out.println(len1>0?"更新部门信息成功":"更新部门信息失败");
			pst1.close();
			
			String sql2 = "update t_employee set salary = salary + ? where did = ?";
			PreparedStatement pst2 = conn.prepareStatement(sql2);
			pst2.setObject(1, 20000);
			pst2.setObject(2, 4);
			int len2 = pst2.executeUpdate();
			System.out.println(len2>0?"更新部门信息成功":"更新部门信息失败");
			pst2.close();
			//事务整体提交
			conn.commit();
		}catch (Exception e) {
			try {
				if(conn!=null){
                    //中间存在异常，回滚事务
					conn.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally{
			try {
				if(conn!=null){
					//恢复自动提交
					conn.setAutoCommit(true);
					//释放连接
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
```

## 2、数据库链接池

### ①、数据库链接池

#### Ⅰ 数据库连接池的必要性

* 没有数据库连接池存在的问题

  ```
  1、传统通过DriverManager获取Connection链接，对于这个链接在使用后会释放关闭，数据库连接获取的资源并没有得到很好的重复利用。当并发量大时，资源开销无法满足。
  2、对于每次Connection连接对象释放时，如果由于程序异常未成功释放关闭，将会导致数据库系统中的内存泄露，内存泄漏的最终结果是要重启数据库。
  3、传统的Connection的数量无法控制，资源分配后回收也难以保证，内存泄漏严重，服务器易崩溃。
  ```

* 链接池技术的创建原因和思想

  ```分析
  1、解决传统数据库链接导致的资源分配问题和内存泄漏问题。
  2、基本思想：为数据库链接建立一个“缓冲池”。预先在缓冲池中放入一定数量的链接，但需要建立数据库连接时，只需要在“缓冲池”中取出一个，使用完毕后，在将链接资源放回去。数据库链接池负责分配、管理和释放数据库链接，它允许应用程序重复使用一个现有的数据库链接，而不是重新建立一个。链接池的最大数据库链接数量限定了这个连接池能占用的最大连接数，当应用程序向连接池请求的连接数超过最大连接数量时，这些请求将被加入到等待的队列中。
  ```

* 数据库连接池技术的优点

  ```分析
  1、资源重用
  2、更快的系统反应速度
  3、新的资源分配方式
  4、统一的链接管理，避免了数据库链接泄漏
  ```

#### Ⅱ 常见的数据库连接池

> JDBC的数据库连接池使用javax.sql.DataSource 接口来表示，该接口通常由服务器提供实现，也有一些开源组织提供实现：

* DBCP：速度较C3P0快，但存在BUG，Hibernate3已不在提供支持
* C3P0：稳定但速度较慢
* Proxool：有监控连接池状态的功能，稳定性较C3P0差一点
* BoneCP：速度快
* Druid：阿里提供，推荐

> DataSource 通常被称为数据源，它包含连接池和连接池管理两个部分，但习惯上把DataSouce称为连接池。
>
> 注意：
>
> 1、数据源和数据库链接时不同的，数据源无需创建多个，他是产生数据库连接的工厂，因此整个应用只需要一个数据源即可。
>
> 2、当数据库访问结束后，程序还是向以前一样关闭数据库链接：conn.close()；但是conn.close() 并没有关闭数据库的物理链接，他仅仅把数据库链接释放，归还给了数据库链接池。

#### Ⅲ Druid（德鲁伊）数据源（封装JDBCTools类）

* 德鲁伊配置文件: druid.properties

  ```druid.properties
  url=jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true
  username=root
  password=123456
  driverClassName=com.mysql.jdbc.Driver
  initialSize=10
  maxActive=20
  maxWait=1000
  ```

* JDBCTools类实现

```java
package com.atguigu.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/*
 * 获取连接或释放连接的工具类
 */
public class JDBCTools {
	// 1、数据源,即连接池
	private static DataSource dataSource;
	
	// 2、ThreadLocal对象
	private static ThreadLocal<Connection> threadLocal;

	static {
		try {
			//1、读取druip.properties文件
			Properties pro = new Properties();	
            pro.load(JDBCTools.class.getClassLoader().getResourceAsStream("druid.properties"));
			
			//2、连接连接池
			dataSource = DruidDataSourceFactory.createDataSource(pro);

			//3、创建线程池
			threadLocal = new ThreadLocal<>();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取连接的方法
	 * 
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() {
		// 从当前线程中获取连接
		Connection connection = threadLocal.get();
		if (connection == null) {
			// 从连接池中获取一个连接
			try {
				connection = dataSource.getConnection();
				// 将连接与当前线程绑定
				threadLocal.set(connection);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return connection;
	}

	/**
	 * 释放连接的方法
	 * 
	 * @param connection
	 */
	public static void releaseConnection() {
		// 获取当前线程中的连接
		Connection connection = threadLocal.get();
		if (connection != null) {
			try {
				connection.close();
				// 将已经关闭的连接从当前线程中移除
				threadLocal.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
```

## 3、Apache—DBUtils简介

> commons-dbutils是Apache组织提供的一个开源JDBC工具类库，它是对JDBC的简单封装，学习成本低，并且dbutils能极大简化jdbc编码的工作量，同时也不会影响程序的性能。

### ①、DbUtils类

>提供了如关闭链接、转载JDBC驱动程序等常规工作的工具类，里面的所有方法都是静态的。主要方法如下：

* public static void close(......) throws java.sql.SQLExecption：DbUtils类提供了三个重载的关闭方法。这些方法检查所提供的参数是不是NULL，如果不是的话，它们就关闭Connection、Statement和ResultSet。
* public static void closeQuietly(......)：这一类方法不仅能在Connection、Statement和ResultSet为Null情况下避免关闭，还能隐藏一些在程序中抛出的SQLExecption。
* public static void commitAndClose(Connection conn)throws SQLExecption：用来提交连接的事务，然后关闭链接。
* public static void commitAndCloseQuietly(Connection conn)：用来提交链接的事务，然后关闭链接，并且在关闭连接时不抛出SQL异常
* public static void rollback(Connection conn)throws SQLExecption：允许conn为null，因为方法内部有判断
* public static void rollbackAndClose(Connection conn)throws SQLExecption
* public static void rollbackAndCloseQuietly(Connection conn)
* public static boolean loadDriver(java.lang.String driverClassName)：这一方法转载并注册JDBC驱动程序，如果成功就返回true。使用该方法，不需要捕捉这个异常（ClassNotFoundExecption）

### ②、QueryRunner类

> 该类封装了SQL的执行，是线程安全的。

* 可以实现增、删、改、查、批处理

* 考虑了事务处理的Connection

* 该类最主要就是简化了SQL查询，它与ResultSetHandler组合在一起使用可以完成大部分的数据库操作，能够大大减少代码量。

  ```
  1、更新
  public int update(Connection conn,String sql,Object... params)throws SQLExecption;
  2、插入
  public<T> insert(Connection conn,String sql,ResultSetHandler<T> rsh,Object... params)throws SQLExecption;
  3、批处理
  public int[] batch(Connection conn,String sql,Object[][] params)throws SQLExecption;
  4、使用QueryRunner类实现查询
  public Object query(Connection conn,String sql,ResultSetHandler rsh,Object... params)throws SQLExecption;
  ```

### ③、ResultSetHandler接口

> 该接口用于处理java.sql.ResultSet，将数据按要求转换为另一种形式。ResultSetHandler接口提供了一个单独的方法：Object handle(java.sql.ResultSet rs)该方法的返回值将作为QueryRunner类的query()方法的返回值。
>
> 该接口有如下实现类可以使用：

* BeanHandler：将结果集中到的第一行数据封装到一个对应的JavaBean实例中。
* BeanListHander：将结果集中的第一行数据封装到一个对应的javaBean实例中，存放在List里。
* ScalarHandler：查询单个值的对象
* MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value对应值
* MapListHandler：将结果集中的每一行数据都封装到Map中，然后再存放到List
* ColumnListHandler：将结果集中某一列的数据存放到List中。
* KeyedHandler（name）：将结果集中的每一行数据都封装到一个Map里，再把这些map存放到一个map中，key为指定的key
* ArrayHandler：把结果集中的每一行数据转换成对象数组。
* ArrayListHandler：把结果集中的每一行数据转成一个数组，再存放到List中。

### ④、BasicDaoImpl代码实例

```java
package com.atguigu.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.atguigu.util.JDBCTools;

/**
 * 定义一个用来被继承的对数据库进行基本操作的Dao
 */
public class BasicDAOImpl {
	private QueryRunner queryRunner = new QueryRunner();

	/**
	 * 通用的增删改操作
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public int update(String sql, Object... params) {
		// 获取连接
		Connection connection = JDBCTools.getConnection();
		int count = 0;
		try {
			count = queryRunner.update(connection, sql, params);
		} catch (SQLException e) {
			//将编译时异常转换为运行时异常向上抛
			throw new RuntimeException(e);
		}
		return count;
	}

	/**
	 * 获取一个对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> T getBean(Class<T> type,String sql, Object... params) {
		// 获取连接
		Connection connection = JDBCTools.getConnection();
		T t = null;
		try {
			t = queryRunner.query(connection, sql, new BeanHandler<T>(type), params);
		} catch (SQLException e) {
			//将编译时异常转换为运行时异常向上抛
			throw new RuntimeException(e);
		}
		return t;
	}

	/**
	 * 获取所有对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public <T> List<T> getBeanList(Class<T> type,String sql, Object... params) {
		// 获取连接
		Connection connection = JDBCTools.getConnection();
		List<T> list = null;
		try {
			list = queryRunner.query(connection, sql, new BeanListHandler<T>(type), params);
		} catch (SQLException e) {
			//将编译时异常转换为运行时异常向上抛
			throw new RuntimeException(e);
		} 
		return list;
	}

	/**
	 * 获取一个单一值的方法，专门用来执行像select count(*)... 这样的sql语句
	 * 
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object getSingleValue(String sql, Object... params) {
		// 获取连接
		Connection connection = JDBCTools.getConnection();
		Object value = null;
		try {
			value = queryRunner.query(connection, sql, new ScalarHandler(), params);
		} catch (SQLException e) {
			//将编译时异常转换为运行时异常向上抛
			throw new RuntimeException(e);
		}
		return value;
	}
	/**
	 * 进行批处理的方法
	 * 关于二维数组Object[][] params
	 * 		二维数组的第一维是sql语句要执行的次数
	 * 		二维数组的第二维就是每条sql语句中要填充的占位符
	 * 
	 * @param sql
	 * @param params
	 */
	public void batchUpdate(String sql , Object[][] params){
		//获取连接
		Connection connection = JDBCTools.getConnection();
		try {
			queryRunner.batch(connection ,sql, params);
		} catch (SQLException e) {
			//将编译时异常转换为运行时异常向上抛
			throw new RuntimeException(e);
		}
	}

```

