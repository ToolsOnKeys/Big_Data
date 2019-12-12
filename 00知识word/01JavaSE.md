# 复习

# 第一章 Java概述

## 1.1 Java历史

Java诞生于SUN（Stanford University Network），09年SUN被Oracle（甲骨文）收购。

Java之父是詹姆斯.高斯林(James Gosling)。

1996年发布JDK1.0版。

目前最新的版本是Java12。我们学习的Java8。



## 1.2 Java语言最主要的特点

* 特点一：面向对象

两个基本概念：类、对象

三大特性：封装、继承、多态

* 特点二：健壮性

;吸收了C/C++语言的优点，但去掉了其影响程序健壮性的部分（如指针、内存的申请与释放等），提供了一个相对安全的内存管理和访问机制，异常处理等

* 特点三：跨平台性

跨平台性：通过Java语言编写的应用程序在不同的系统平台上都可以运行。“Write once , Run Anywhere”一次编写，处处运行。

原理：只要在需要运行 java 应用程序的操作系统上，先安装一个Java虚拟机 (JVM Java Virtual Machine) 即可。由JVM来负责Java程序在该系统中的运行。因为有了JVM，同一个Java 程序在三个不同的操作系统中都可以执行。这样就实现了Java 程序的跨平台性。

![1557828366412](D:/atguigu/javaee/JavaSE20190826/note/imgs/1557828366412.png)

## 1.3 Java环境搭建

### 1.3.1 JDK、JRE、JVM

Java开发人员需要安装JDK。

JDK（Java Development kits）：Java开发工具包。

JRE（Java Runtime Environment）：Java运行环境。

JVM（Java Virtual Machine）：Java虚拟机。

JDK = JRE + 开发工具

JRE = JVM + 核心类库

![](D:/atguigu/javaee/JavaSE20190826/note/imgs/1553593811117.png)

### 1.3.2  Java环境搭建

1、安装JDK

避免中文目录等

![](D:/atguigu/javaee/JavaSE20190826/note/imgs/1553644600381.png)

2、配置JDK的开发工具目录到path环境变量中

​	例如：D:\ProgramFiles\Java\jdk1.8.0_51\bin;

​	注意：这个安装目录以你自己的安装目录为准

![1553644724825](D:/atguigu/javaee/JavaSE20190826/note/imgs/1553644724825.png)

（1）为什么配置path？

​	希望在命令行使用javac.exe等工具时，任意目录下都可以找到这个工具所在的目录。

（2）如何配置环境变量？

​	【计算机】右键【属性】，选择【高级系统设置】，选择【高级】，选择【环境变量】，选择【系统环境变量】，编辑path，在【**path原有值**】的前面加入D:\ProgramFiles\Java\jdk1.8.0_51\bin;

## 1.4 第一个Java应用程序

```java
class HelloWorld{
    public static void main(String[] args){
        System.out.print("Hello Java!");
    }
}
```

### 1.4.1 Java程序的开发步骤

三步：

1、编写

必须保存为.java的源文件

2、编译

使用的工具：javac.exe

目的：把.java源文件中的类编译为一个一个的.class字节码文件。

格式：

```java
javac 源文件名.java
```

3、运行

使用的工具：java.exe

格式：

```java
java 类名
java 字节码文件名
```

> 要能够直接运行的类必须包含main方法，否则是无法运行

### 1.4.2 Java程序的结构与格式

```
类{
	方法{
		语句；
	}
}
```

### 1.4.3 Java程序的入口

main方法

```java
public static void main(String[] args){
    
}
```

### 1.4.4 Java注释

1、单行注释

//注释内容

2、多行注释

/*

注释内容

*/

## 1.5 编写Java程序时应该注意的问题

1、字符编码问题

当cmd命令行窗口的字符编码与.java源文件的字符编码不一致，如何解决？

![1566953565672](imgs/1566953565672.png)

![1566953547812](imgs/1566953547812.png)

2、大小写问题

（1）源文件名：不区分

（2）字节码文件名与类名：区分

（3）代码中：区分

建议大家都区分



3、源文件名与类名一致问题？

（1）源文件名是否必须与类名一致？public呢？

如果不是public的类，可以不一致，如果是public的类，必须一致。



（2）一个源文件中是否可以有多个类？public呢？

一个源文件中可以有多个类，但是只能有一个public的类。



（3）main必须在public的类中吗？

不一定。

建议是。



# 第二章 Java的基础语法

## 2.1 标识符

标识符：

​	给变量、方法、类等命名的字符序列称为标识符。

​	通俗的讲：在代码中需要程序员自己命名的部分都成为标识符。



1、标识符的命名规则

（1）只能使用26个英文字母大小写，数字0-9，下划线，美元符号

（2）数字不能开头

（3）不能包含空格

（4）不能直接使用Java的关键字（50个）和3个特殊值

（5）严格区分大小写



2、标识符的命名规范

（1）见名知意

（2）类名等：每一个单词的首字母大写，形式：XxxYyyZzz

（3）变量名等：从第二个单词开始首字母大写，形式：xxxYyyZzz

（4）包名：所有单词都小写，形式：xxx.yyy.zzz

（5）常量名：所有单词都大写，形式：XXX_YYY_ZZZ



## 2.2 变量

### 2.2.1 变量的概念

变量的作用：用来存储数据，代表内存的一块存储区域

### 2.2.2 变量的三要素

1、数据类型

2、变量名

3、变量值

### 2.2.3 变量的使用应该注意什么？

1、先声明后使用

2、必须初始化（即赋值）

3、有作用域

4、在同一个作用域中不能重名

### 2.2.4 变量的声明和赋值、使用的语法格式？

1、变量的声明的语法格式：

```java
数据类型  变量名;
例如：
int age;
String name;
double weight;
char gender;
boolean isMarry;
```

2、变量的赋值的语法格式：

```java
变量名 = 值;
例如：
age = 18;
name = "柴林燕";//只有String类型需要加""
weight = 44.4;
gender = '女';//只有char类型需要加''
isMarry = true;
```

3、变量的使用的语法格式：

```java
通过变量名直接引用

例如：
(1)输出变量的值
System.out.print(name);
System.out.print("姓名：" + name);
System.out.print("name = " + name);
(2)计算
age = age + 1;
```

# 2.3 数据类型

### 2.3.1 Java数据类型的分类

1、基本数据类型

​	8种：整型系列（byte,short,int,long）、浮点型(float,double)、单字符型（char）、布尔型（boolean）

2、引用数据类型

​	类、接口、数组、枚举.....

​	String类型属于类

### 2.3.2 Java的基本数据类型

四类八种基本数据类型：

![](day01_note/imgs/%E5%9F%BA%E6%9C%AC%E6%95%B0%E6%8D%AE%E7%B1%BB%E5%9E%8B%E8%8C%83%E5%9B%B4.jpg)

> Java中的默认类型：整数类型是`int` 、浮点类型是`double` 。

1、整型系列

（1）byte：字节类型

占内存：1个字节

存储范围：-128~127

（2）short：短整型类型

占内存：2个字节

存储范围：-32768~32767

（3）int：整型

占内存：4个字节

存储范围：-2147483648~2147483647

（4）long：整型

占内存：8个字节

数据范围：-9223372036854775808~9223372036854775807

> 注意：在数字后面需要加L或l（小写L）



2、浮点型系列（小数）

（1）float：单精度浮点型

占内存：4个字节

精度：大概科学记数法的小数点后6~7位

> 注意：在数字后面需要加F或f

（2）double：双精度浮点型

占内存：8个字节

精度：大概科学记数法的小数点后15~16位



3、单字符类型

char：字符类型

占用内存：2个字节

数据范围：0~65535

Java中使用的字符集：Unicode字符集

字符的三种表示方式：

（1）'单个字符'

（2）'转义字符'

\t：制表位

\n：换行

\r：回车

\b：删除键

`\\`：\

`\'`：'

`\"`：“

（3）'\u字符的编码值的十六进制形式'

例如：尚：\u5c1a

4、布尔类型

boolean：只能存储true和false

### 2.3.3 进制（了解，可以暂时忽略）

1、进制的分类：

（1）十进制

​	数字组成：0-9

​	进位规则：逢十进一

（2）二进制

​	数字组成：0-1

​	进位规则：逢二进一

（3）八进制

​	数字组成：0-7

​	进位规则：逢八进一

（4）十六进制

​	数字组成：0-9，a~f（或A~F）

​	进位规则：逢十六进一



2、请分别用四种类型的进制来表示10，并输出它的结果：（了解）

（1）十进制：正常表示

System.out.println(10);

（2）二进制：0b或0B开头

System.out.println(0B10);

（3）八进制：0开头

System.out.println(010);

（4）十六进制：0x或0X开头

System.out.println(0X10);



3、为什么byte是-128~127？（理解）

1个字节：8位

0000 0001  ~  0111 111 ==> 1~127

1000 0001 ~ 1111 1111 ==> -127 ~ -1

0000 0000 ==>0

1000 0000 ==> -128（特殊规定）

> *解释：*计算机数据的存储（了解）
>
> *计算机数据的存储使用二进制补码形式存储，并且最高位是符号位，1是负数，0是正数。*
>
> *规定：正数的补码与反码、原码一样，称为三码合一；*
>
> ​	    *负数的补码与反码、原码不一样：*
>
> ​	   *负数的原码：把十进制转为二进制，然后最高位设置为1*
>
> ​	   *负数的反码：在原码的基础上，最高位不变，其余位取反（0变1,1变0）*
>
> ​	   *负数的补码：反码+1*
>
> *例如：byte类型（1个字节，8位）*
>
> *25 ==> 原码  0001 1001 ==> 反码  0001 1001 -->补码  0001 1001*
>
> *-25 ==>原码  1001 1001 ==> 反码1110 0110 ==>补码 1110 0111*



4、学生疑惑解答？

（1）为什么float（4个字节）比long（8个字节）的存储范围大？

（2）为什么double（8个字节）比float（4个字节）精度范围大？

因为float、double底层也是二进制，先把小数转为二进制，然后把二进制表示为科学记数法，然后只保存：

（1）符号位（2）指数位（float类型是8个，double类型是11个）（3）尾数位（float类型是23位，double类型是52位）

> *详见《float型和double型数据的存储方式.docx》*

### 2.3.4 基本数据类型的转换

1、自动类型转换

（1）当把存储范围小的数据赋值给存储范围大的变量时，那么会发生自动类型转换

byte->short->int->long->float->double

​            char->

（2）当byte与byte，short与short，char与char，或者他们三个之间混合运算，会以int处理。

（3）当多种数据类型混合运算，升级为他们当中最大的

（4）boolean类型不参与



2、强制类型转换

强制类型转换的格式：

```
(存储范围小的数据类型)需要强制的数据

例如：
int a = (int)1.2;

char c = (char)97;
```



（1）当把存储范围大的数据赋值给存储范围小的变量时，那么需要进行强制类型转换

double->float->long->int->short->byte

​					   ->char

强制类型有风险：可能会溢出或损失精度

（2）当需要提示某个数据的类型，也可以使用强制类型转换的格式

```
int a = 1;
int b = 2;
System.out.println((double)a/b);
```

（3）boolean类型不参与

### 2.3.5 特殊的数据类型转换

（1）所有的数据类型一旦与字符串进行“+”拼接，结果一定是字符串

（2）字符串不能通过强制类型转换为其他的类型



## 2.4 运算符

1、按照操作数个数的分类：

（1）一元运算符：操作数只有一个

例如：正号（+），负号（-），自增（++），自减（--），逻辑非（！），按位取反（~）

（2）二元运算符：操作数有两个

例如：加（+），减（-），乘（*），除（/），模（%）

​          大于（>），小于（<），大于等于（>=），小于等于（<=），等于（==），不等于（!=）

​	赋值（=，+=，-=，*=，/=，%=，>>=，<<=。。。）

​	 逻辑与（&），逻辑或（|），逻辑异或（^），短路与（&&），短路或（||）

​	左移（<<），右移（>>），无符号右移（>>>），按位与（&），按位或（|），按位异或（^）

（3）三元运算符：操作数三个

例如： ？ ：



2、Java基本数据类型的运算符：

（1）算术运算符

（2）赋值运算符

（3）比较运算符

（4）逻辑运算符

（5）条件运算符

（6）位运算符（难）

### 2.4.1 算术运算符

加法：+

减法：-

乘法：*

除法：/

> *注意：整数与整数相除，只保留整数部分*

取模：%   取余

> 注意：取模结果的正负号只看被模数

正号：+

负号：-

自增：++

自减：--

> **原则：自增与自减**
>
> 如果是单独计算，++/--在前或在后没区别；例如：a++; 或  ++a;
>
> 如果是混合运算，++/--在前或在后有区别：
>
> **++/--在前的，就先自增/自减，后取值**
>
> **++/--在后的，就先取值，后自增/自减**



### 2.4.2 赋值运算符

基本赋值运算符：=

扩展赋值运算符：+=，-=，*=，/=，%=...

> 注意：所有的赋值运算符的=左边一定是一个变量
>
> 作用一定是把=右边计算的结果赋值给左边的变量
>
> 扩展赋值运算符=右边的计算结果的类型如果比左边的大的话会强制类型转换，所以结果可能有风险。



### 2.4.3 比较运算符

大于：>

小于：<

大于等于：>=

小于等于：<=

等于：==

不等于：!=

> 注意：比较表达式的运算结果一定只有true/false
>
> 比较表达式可以作为（1）条件（2）逻辑运算符的操作数



### 2.4.4 逻辑运算符

> 逻辑运算符的操作数必须是布尔值

逻辑与：&
	运算规则：只有左右两边都为true，结果才为true。
	例如：true & true 结果为true
		   false & true 结果为false
		    true & false 结果为false
		    false & false 结果为false
逻辑或：|
	运算规则：只要左右两边有一个为true，结果就为true。
	例如：true | true 结果为true
		   false | true 结果为true
		   true | false 结果为true
		    false | false 结果为false	
逻辑异或：^
	运算规则：只有左右两边不同，结果才为true。
	例如：true ^ true 结果为false
		    false ^ true 结果为true
		    true ^ false 结果为true
		    false ^ false 结果为false			

逻辑非：!
	运算规则：布尔值取反
	例如：!true 为false
		    !false 为true

短路与：&&
	运算规则：只有左右两边都为true，结果才为true。
	例如：true & true 结果为true
		    true & false 结果为false
		    false & ?  结果就为false
	它和逻辑与不同的是当&&左边为false时，右边就不看了。
	
短路或：||	
	运算规则：只要左右两边有一个为true，结果就为true。
	例如：true | ? 结果为treu
		   false | true 结果为true
		    false | false 结果为false	
	它和逻辑或不同的是当||左边为true时，右边就不看了。



### 2.4.5 条件运算符

 ? : 

语法格式：

```java
条件表达式 ? 结果表达式1 : 结果表达式2
```

运算规则：

当条件表达式为true，最终的结果取“结果表达式1的值”，否则就取“结果表达式2”的值

代码示例：

```
（1）boolean类型
boolean isMarry = false;

System.out.println(isMarry ? "已婚" : "未婚");

（2）求最值
int a = 1;
int b = 2;
System.out.println(a > b ? a : b);
```



### 2.4.6 位运算符

左移：<<

​	快速计算：左移几位就是乘以2的几次方

​	实际运算：二进制补码往左边移动，左边移除后，右边缺的都补0

右移：>>

​	快速计算：右移几位就是除以2的几次方

​	实际运算：二进制补码往右边移动，左边补什么看最高位，最高位是1就补1，是0就补0

无符号右移：>>>

​	快速计算：右移几位就是除以2的几次方

​	实际运算：二进制补码往右边移动，左边直接补0，不看最高位

按位与：&

​	1 & 1 是1

​	1 & 0 是 0

​	0 & 1 是 0

​	0 & 0 是 0

按位或：|

​	1 | 1 是1

​	1 | 0 是 1

​	0 | 1 是 1

​	0 | 0 是 0

按位异或：^

​	1 ^ 1 是 0

​	1 ^ 0 是 1

​	0 ^ 1 是 1

​	0 ^ 0 是 0

按位取反：~

​	~1 为 0

​	~0 为1



> 如何区分&,|,^是逻辑运算符还是位运算符？
>
> 看操作数，如果操作数是boolean值，就是逻辑运算符，否则就是位运算符



### 2.4.7 运算符优先级

![1553858424335](imgs/1553858424335.png)

提示说明：

（1）表达式不要太复杂

（2）先算的使用()

# 第三章 流程控制语句结构

流程控制语句结构分为：

1、顺序结构

2、分支结构

3、循环结构

## 3.1 顺序结构

执行过程：从上到下顺序执行

### 3.1.1 输出语句

1、System.out.print(输出内容); #输出内容后不换行

2、System.out.println(输出内容);  #输出内容后换行

```java
#输出常量值
System.out.print(1);
System.out.print('尚');
System.out.print(44.4);
System.out.print(true);
System.out.print("尚硅谷");

#输出变量
int a = 1;
char c = '尚';
double d = 44.4;
boolean b = true;
String school = "尚硅谷";
System.out.print(a);
System.out.print(c);
System.out.print(d);
System.out.print(b);
System.out.print(school);

#输出拼接结果
System.out.print("a = " + a);
System.out.print("c = " + c);
System.out.print("d = " + d);
System.out.print("b = " + b);
System.out.print("school = " + school);
```

### 3.1.2 输入语句

键盘输入代码的三个步骤：

1、准备Scanner类型的变量

2、提示输入xx

3、接收输入内容

示例代码：

```java
//1、准备Scanner类型的变量
java.util.Scanner input = new java.util.Scanner(System.in);

//2、提示输入xx
System.out.print("请输入姓名：");

//3、接收输入内容
String name = input.next();

//其他数据类型的输入和接收
System.out.print("请输入年龄：");
int age = input.nextInt();

System.out.print("请输入体重：");
double weight = input.nextDouble();

System.out.print("请输入性别：");
char gender = input.next().charAt(0);

System.out.print("是否已婚：");
boolean isMarry = input.nextBoolean();
```

关于两种输入字符串的方式：

（1）input.next()：遇到空白符认为输入结束

（2）input.nextLine()：遇到回车换行就认为输入结束



## 3.2 分支结构

分支结构：根据条件选择性的执行某些代码

分为：

1、条件判断：if...else系列

2、选择结构：switch...case系列

### 3.2.1 条件判断

#### 1、单分支结构

语法格式：

```java
if(条件表达式){
    当条件表达式成立时，执行的语句块;
}
```

执行过程：

当条件表达式成立就执行{}中的语句块，否则就不执行；



> 注意：
>
> （1）if(条件表达式)的条件表达式必须是boolean类型
>
> （2）当{}中的语句块只有一个语句时，可以省略{}



示例代码：

```java
int age = 19;
if(age >= 18){
    System.out.println("欢迎来玩");
}

或
if(age >= 18)
    System.out.println("欢迎来玩");

```



#### 2、双分支结构

语法格式：

```java
if(条件表达式){
    当条件表达式成立时，执行的语句块1;
}else{
    当条件表达式不成立时，执行的语句块2;
}
```

执行过程：

当条件表达式成立时，执行的语句块1；否则就执行语句块2；

> 注意：
>
> （1）if(条件表达式)的条件表达式必须是boolean类型
>
> （2）当{}中的语句块只有一个语句时，可以省略{}
>
> （3）else不能单独使用，必须有if和它配对



示例代码：

```java
int num = 5;
if(num % 2 ==0){
    System.out.println("偶数");
}else{
    System.out.println("奇数");
}
```



#### 3、多分支结构

语法格式：

```java
if(条件表达式1){
    当条件表达式1成立时，执行的语句块1;
}else if(条件表达式2){
    当条件表达式2成立时，执行的语句块2;
}else if(条件表达式3){
    当条件表达式3成立时，执行的语句块3;
}。。。
【else{
	当以上所有条件都不成立时，需要执行的语句块n+1;
}
】
```

执行过程：

（1）按照顺序判断条件，如果上面有条件成立了，后面的条件就不看了

（2）多个分支也只会执行其中的一个，看满足哪一个条件。如果所有的条件都不满足，如果存在单独的else，那么执行else中语句块，如果不存在单独的else，那么就一个分支都不执行。

> 注意：
>
> （1）if(条件表达式)的条件表达式必须是boolean类型
>
> （2）当{}中的语句块只有一个语句时，可以省略{}
>
> （3）如果多个条件的范围没有“包含、重叠”，顺序是随意；
>
> ​          如果多个条件的范围有“包含、重叠”，顺序有要求，小的在上面，大的在下面。



示例代码：

```java
int score = 89;
if(score<0 || score > 100){
    //...
}else if(score == 100){
    //...
}else if(score>=80 && score<=99){
    //...
}else if(score>=70 && score<=79){
    //...
}else if(score>=60 && score<=69){
    //...
}else{
    //...
}

或
int score = 89;
if(score<0 || score > 100){
    //...
}else if(score == 100){
    //...
}else if(score>=80){
    //...
}else if(score>=70){
    //...
}else if(score>=60){
    //...
}else{
    //...
}
```



#### 4、嵌套

执行过程：

​	当嵌套在if中，当外面的if条件满足，才会判断里面的条件。

​	当嵌套在else中，当外面的if条件不满足，进入到else中，才会判断里面的条件。

### 3.2.2 选择结构

语法格式：

```java
switch(表达式){
    case 常量值1:
        语句块1;
        【break;】
    case 常量值2:
        语句块2;
        【break;】 
    。。。。
   【default：
       	语句块n+1; 
        【break;】 
    】
}
```

执行过程：

（1）入口

A：当switch(表达式)中的表达式的值与某个case后面的常量值匹配了，就这个case进入；

B：当switch(表达式)中的表达式的值与所有的case都不匹配，就从default进入；

（2）出口

A：自然出口：switch的结束}

B：中断：break;

（3）一旦找到入口，那么就会一直往下执行，甚至贯穿其他的case等，直到遇到“出口”

> 注意：
>
> switch(表达式)中的表达式的类型只能使用byte,short,int,char，枚举，String
>
> case后面必须是常量值，而且不能重复



示例代码：

```java
int month = 4;
switch(month){
    case 3:
    case 4:
    case 5:
        System.out.println("春季");
        break;
    case 6:
    case 7:
    case 8:
        System.out.println("夏季");
        break;   
    case 9:
    case 10:
    case 11:
        System.out.println("秋季");
        break;
    case 12:
    case 1:
    case 2:
        System.out.println("冬季");
        break;
}
```

## 3.3 循环结构

循环结构：

​	“重复”执行某些代码

循环结构的分类：

1、for循环

2、while循环

3、do...while循环



循环都有四个要素：

（1）循环变量的初始化：

​	循环变量：即由它的值来控制循环开始与结束的一个变量

​	循环变量的第一次赋值，称为循环变量初始化

（2）循环条件

​	循环条件成立，循环继续，循环条件不成立，结束循环过程。

​	for(;;)两个分号中间省略循环条件的话，表示循环条件永远成立，那么就需要用break结束。

​	while和do...while的while(true)时表示循环条件永远成立，那么就需要用break结束。

（3）循环体语句块

​	需要重复执行的代码块。

（4）迭代表达式

​	修改循环变量的值



### 3.3.1 for循环

语法格式：

```java
for(循环变量的初始化; 循环条件; 迭代表达式){
    循环体语句块;
}

//特殊情况
for(;;){
    循环体语句块;
}
```

执行过程：

（1）循环变量的初始化;

（2）循环条件，如果成立，继续（3），否则就结束

（3）执行循环体语句块

（4）迭代表达式

（5）回到（2）



> 注意：
>
> （1）循环条件必须是boolean类型
>
> （2）两个;不能多也不能少
>
> （3）当for的{}中的循环体语句只有一个语句时，可以省略{}，但是不建议大家省略
>
> （4）避免这种错误   for(循环变量的初始化; 循环条件; 迭代表达式) ;  {...}



示例代码：

```java
//随机产生10个1-10的整数，统计其中偶数和奇数的个数
int even = 0;
int odd = 0;
for(int i=1; i<=10; i++){
    int num = (int)(Math.random()*10+1);
    if(num % 2==0){
        even++;
    }else{
        odd++;
    }
}
```

```java
//随机产生10个1-10的整数，然后找出其中最大值和最小值
int max = 0;//这里初始化为0，就可以保证当我们for循环执行完第一次时，max中一定是第一个num的值
			//这里不是非得是0，小于等于1即可
int min = 11;//这里初始化为11，就可以保证当我们for循环执行完第一次时，min中一定是第一个num的值
			//这里不是非得是11，大于等于10即可
for(int i=1; i<=10; i++){
    //num在[1,10]
    int num = (int)(Math.random()*10+1);
    if(max < num){
        max = num;
    }
    if(min > num){
        min = num;
    }
}
```

### 3.3.2 while循环

语法格式：

```java
while(循环条件){
    循环体语句块
}

//特殊的形式
while(true){
    //....
    if(条件){
        break;
    }
    //...
}
```

执行过程：

（1）先判断循环条件，如果成立，继续（2），否则就结束while循环

（2）执行循环体语句块



> 注意：
>
> （1）循环条件也必须是boolean类型
>
> （2）当while的循环体语句只有一个语句时，也是可以省略{}，但是我们不建议大家省略



示例代码：

```java
//累加1-100之间3的倍数的和
int sum = 0;
int num = 1;
while(num<=100){
    if(num%3==0){
        sum += num;
    }
    num++;
}
```

```java
//从键盘输入整数，0表示结束，统计正、负的个数
java.util.Scanner input = new java.util.Scanner(System.in);

int positive = 0;
int negative = 0;
while(true){
    System.out.print("请输入整数：");
    int num = input.nextInt();
    
    if(num == 0){
        break;
    }else if(num>0){
        positive++;
    }else{
        negative++;
    }
}
```



### 3.3.3 do...while循环

语法格式：

```java
do{
    循环体语句块
}while(循环条件);
```

执行过程：

（1）先执行一次循环体语句块

（2）判断循环条件，如果成立，继续（3），否则结束do...while

（3）再次执行循环体语句块，然后回到（2）



> 注意：
>
> （1）do{...}while(循环条件);  后面的;不能省略
>
> （2）循环条件必须是boolean类型



示例代码：

```java
//从键盘输入整数，0表示结束，统计正、负的个数
java.util.Scanner input = new java.util.Scanner(System.in);

int positive = 0;
int negative = 0;
int num;

do{
    System.out.print("请输入整数：");
    num = input.nextInt();
    
    if(num>0){
        positive++;
    }else if(num<0){
        negative++;
    }
}while(num!=0);
```

### 3.3.4 三种循环的选择

原则上：三种循环的作用是相同的，都可以实现重复执行某些代码的功能，即它们之间是可以互换的

习惯上：

（1）如果循环体语句块，有必须，至少执行一次的需求，那么选择do....while比较多

（2）如果循环的次数比较明显（从几循环到几），那么首先考虑使用for比较多

（3）如果次数不明显，循环体也不是至少执行一次，只是循环的条件比较清晰，那么可以考虑使用while



### 3.3.5 跳转语句

1、break：

用法：（1）switch，表示结束switch

​	    （2）循环中，表示结束当层循环，看你这个break属于那一层

2、continue 

用法：循环中，表示跳过本次循环剩下的语句块，提前进入下一次循环

```java
java.util.Scanner input = new java.util.Scanner(System.in);
while(true){
    System.out.println(----ATM-----);
    System.out.println(1、存款);
    System.out.println(2、取款);
    System.out.println(3、查询);
    System.out.println(4、退出);
    System.out.print("请选择：");
    int select = input.nextInt();
    switch(select){
        case 1:
            //...
            break;
        case 2:
            //...
            break;  
        case 3:
            //...
            break;
        case 4:
           // break;//无法结束while,只能结束switch
           System.exit(0);
    }
}
```

# 第四章 数组

## 4.1 数组的相关概念和名词（了解）

1、数组(array)：

​	一组具有相同数据类型的数据的按照一定顺序排列的集合。

​       把有限的几个相同类型的变量使用一个名称来进行统一管理。

2、数组名：

​	（1）这个数组名中存储的是：整个数组的“首地址”

​	（2）打印数组名显示的的是：数组的标记[ + 元素的类型 + @ + 数组的hashCode值的十六进制形式

3、下标(index)：

​	我们使用编号、索引、下标来区别/表示一组数当中某一个。

​	范围：[0,数组长度-1]     

​	例如：for(int i = 0;  i<arr.length; i++){}

4、元素(element)：

​	这一组中的每一个数据都是元素。

​	如何表示数组元素？  数组名[下标]

5、数组的长度(length)

​	数组中元素的总个数。

​	如何获取数组长度？  数组名.length



## 4.2 数组的相关语法

### 4.2.1 数组的声明

解决的问题：

（1）这个数组是用来存储什么数据？==>数组的元素的数据类型

（2）这个数组使用什么名字？==>数组名

（3）需要几个维度？==>一维、二维....

语法格式：

```java
  //推荐
//一维数组
元素的数据类型[] 数组名;

 //也对，但是不推荐
元素的数据类型  数组名[];
```

示例：

```java
//要存储一组整数
int[] arr;

//要存储一组单字符
char[] arr;

//要存储一组字符串
String[] arr;
```

### 4.2.2 数组的初始化

初始化的目的：（1）确定数组的长度（2）为元素赋值

两种初始化方式：

1、动态初始化

语法格式：

```java
//指定数组长度
数组名 = new 元素的数据类型[长度];

//为元素赋值
数组名[下标] = 值; //右边的值可以是一个常量值，表达式、随机值、键盘输入
```

> 问：如果只指定数组长度，没有为元素手动赋值，那么元素有值吗？
>
> 有默认值



数组的元素的默认值：

（1）基本数据类型

​	byte,short,int,long：0

​	float,double：0.0

​	char：\u0000

​	boolean：false

（2）引用数据类型

​	都是null



2、静态初始化

在创建数组的同时，既确定了元素的值，也确定了数组的长度。

语法格式：

```java
数组名 = new 元素的数据类型[]{值列表};//右边的[]中不能再写长度了，此时数组的长度已经由{}中值的个数来决定

//当数组的声明与静态初始化在一句完成时，可以写的更简洁
元素的数据类型[] 数组名 = {值列表};
```

适用场合：数组的元素是已知的，并且没有什么规律

​	

示例代码：

```java
String[] weeks = {"monday","tuesday","wednesday","thursday","friday","saturday","sunday"};

int[] daysOfMonths = {31,28,31,30,31,30,31,31,30,31,30,31};

char[] letters = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};//虽然这个用静态初始化也可以，但是其实它这种有规律的，元素个数比较多的，使用动态初始化反而更简洁
```



### 4.2.3 数组的遍历

for循环遍历数组：

```java
for(int i=0; i<数组名.length; i++){
    //或赋值
    //或显示
    //或其他操作
}
```



### 4.2.4 数组的内存分析

元素是基本数据类型的一维数组内存分析：

```
int[] arr = {1,2,3,4,5};
```

![1567471989853](imgs/1567471989853.png)

```java
int[] arr = new int[5];
for(int i=0; i<arr.length; i++){
    arr[i] = i+1;
}
```

![1567472103908](imgs/1567472103908.png)

### 4.2.4 数组中常见的异常

1、ArrayIndexOutOfBoundsException：数组下标越界异常

注意下标的范围：[0, 数组的长度-1]

2、NullPointerException：空指针异常

（1）数组本身来没有创建

（2）数组的元素是引用数据类型，例如：String，当你还没有为元素赋值，元素的默认值是null，如果此时对元素进行.操作时，会发生空指针异常

## 4.3 数组的相关算法

### 4.3.1 数组找最值

1、数组中找最值

思路：

（1）先假设第一个元素是最大/最小

（2）然后用max或min的值与后面的元素一一比较

示例代码：

```java
int[] arr = {4,5,6,1,9};

//找最大值
//假设第一个元素是最大
int max = arr[0];

//用max的值与后面的元素一一比较
for(int i=1; i<arr.length; i++){
    if(max < arr[i]){
        max = arr[i];
    }
}
```

2、数组中找最值及其下标

情况一：找最值及其第一次出现的下标

思路：

（1）先假设第一个元素是最大/最小

（2）然后用max或min的值与后面的元素一一比较

示例代码：

```java
int[] arr = {4,5,6,1,9};
//找最大值
//假设第一个元素是最大
int max = arr[0];
int index = 0;

//用max的值与后面的元素一一比较
for(int i=1; i<arr.length; i++){
    if(max < arr[i]){
        max = arr[i];
        index = i;
    }
}
```

情况二：找最值及其所有最值的下标

思路：

（1）先假设第一个元素是最大/最小

（2）然后用arr[maxIndex]或arr[minIndex]与后面的元素一一比较

示例代码：

```java
int[] arr = {4,5,6,1,9};

int maxIndex = 0;
for(int i=1; i<arr.length; i++){
    if(arr[i] > arr[maxIndex]){
        maxIndex = i;
    }
}
/*
(1)int maxIndex = 0;    即此时假设的最大值是arr[maxIndex]，arr[0]是最大
(2)int i=1;i<arr.length;
(3)if(arr[i] > arr[maxIndex])  if(arr[1] > arr[0])  if(5 > 4 )成立
(4)maxIndex = i; ==> maxIndex = 1;
(5)i++  ==>i=2
(6)i<arr.length;
(7)if(arr[i] > arr[maxIndex])  if(arr[2] > arr[1])  if(6>5) 成立
(8)maxIndex = i;  ==> maxIndex =2
(9)i++  ==> i=3
(10)i<arr.length;
(11)if(arr[i] > arr[maxIndex])   if(arr[3] > arr[2])  if(1>6)不成立
（12）i++ ==>i=4
（13）i<arr.length;
（14）if(arr[i] > arr[maxIndex]) if(arr[4] > arr[2])  if(9>6)成立
(15)maxIndex = i;  ==> maxIndex =4
(16)i++   i=5
(17)i<arr.length;不成立结束
*/
```



### 4.3.2 数组统计：求总和、均值、统计偶数个数等

思路：遍历数组，挨个的累加或判断每一个元素。。。

示例代码：

```java
int[] arr = {4,5,6,1,9};
//求总和、均值
int sum = 0;
for(int i = 0; i<arr.length; i++){
    sum += arr[i];
}
System.out.println("总和：" + sum);
System.out.println("均值（不保留小数部分）：" + sum/arr.length);
System.out.println("均值（保留小数部分）：" + (double)sum/arr.length);
```

示例代码2：

```java
int[] arr = {4,5,6,1,9};
//求总乘积
int mult = 1;
for(int i=0; i<arr.length; i++){
    mult *= arr[i];
}
```

示例代码3：

```java
int[] arr = {4,5,6,1,9};

//统计偶数个数
int even = 0;
for(int i=0; i<arr.length; i++){
    if(arr[i]%2==0){
        even++;
    }
}
```

### 4.3.3 复制

应用场景：

1、扩容

2、备份

3、截取

4、反转

示例代码：扩容

```java
String[] arr = {"hello","java"};

//需要在arr数组中，增加一个元素，"atguigu"
//(1)先创建一个新数组
String[] newArr = new String[arr.length+1];

//(2)把arr中原来的元素复制到新数组newArr中
for(int i=0; i<arr.length; i++){
    newArr[i] = arr[i];
}

//(3)把新元素放到newArr中，假设放在最后一个元素
newArr[newArr.length-1] = "atguigu";

//(4)让arr指向新数组，或者说，把新数组的首地址赋值给arr
arr = newArr;

//(5)遍历arr
for(int i=0; i<arr.length; i++){
    System.out.println(arr[i]);
}
```

示例代码：备份

```java
int[] arr = {1,2,3,4,5};

//需求，备份一份arr
//(1)创建一个新数组，长度和arr一样
int[] newArr = new int[arr.length];

//(2)复制元素
for(int i=0; i<arr.length; i++){
    newArr[i] = arr[i];
}
```

示例代码：截取

```java
int[] arr = {1,2,3,4,5,6,7,8,9,10};

//截取arr数组的[start,end)范围的元素，构成新数组
java.util.Scanner input = new java.util.Scanner(System.in);
System.out.print("start:");
int start = input.nextInt();//3

System.out.print("end:");
int end = input.nextInt();//7

//(1)计算新数组的长度
int len = end-start;//4

//(2)创建新数组
int[] newArr = new int[len];

//(3)复制元素
/*
i=0,newArr[0] = arr[3+0];
i=1,newArr[1] = arr[3+1];
i=2,newArr[2] = arr[3+2];
i=3,newArr[3] = arr[3+3];
*/
for(int i=0; i<len; i++){
    newArr[i] = arr[start + i];
}

//(4)显示
for(int i=0; i<newArr.length; i++){
    System.out.println(newArr[i]);
}
```



### 4.3.4 反转

方法有两种：

1、借助一个新数组

2、首尾对称位置交换

第一种方式示例代码：

```
int[] arr = {1,2,3,4,5,6,7,8,9};

//(1)先创建一个新数组
int[] newArr = new int[arr.length];

//(2)复制元素
for(int i=0; i<arr.length; i++){
    newArr[i] = arr[arr.length-1-i];
}

//(3)舍弃旧的，让arr指向新数组，把新数组的首地址赋值给arr
arr = newArr;

//(4)遍历显示
for(int i=0; i<arr.length; i++){
    System.out.println(arr[i]);
}
```

第二种方式示例代码：

```java
int[] arr = {1,2,3,4,5,6,7,8,9};

//(1)计算要交换的次数：  次数 = arr.length/2
//(2)首尾交换
for(int i=0; i<arr.length/2; i++){//循环的次数 = 交换的次数
    int temp = arr[i];
    arr[i] = arr[arr.length-1-i];
    arr[arr.length-1-i] = temp;
}

//（3）遍历显示
for(int i=0; i<arr.length; i++){
    System.out.println(arr[i]);
}
```



### 4.3.5 查找

查找分为两种：

1、顺序查找：挨个看

​	对数组没要求

2、二分查找：对折对折再对折

​	对数组有要求，元素必须有大小顺序的

顺序查找示例代码：

```java
int[] arr = {4,5,6,1,9};
int value = 1;//查找目标
int index = -1;//因为正常的下标都不会是负数

for(int i=0; i<arr.length; i++){
    if(arr[i] == value){
        index = i;
        break;
    }
}
if(index==-1){
    System.out.println(value + "在数组中不存在");
}else{
    System.out.println(value + "在数组中的下标是" + index);
}

```

二分查找示例代码：

```java
/*
2、编写代码，使用二分查找法在数组中查找 int value = 2;是否存在，如果存在显示下标，不存在显示不存在。
已知数组：int[] arr = {1,2,3,4,5,6,7,8,9,10};
*/
class Exam2{
	public static void main(String[] args){
		int[] arr = {1,2,3,4,5,6,7,8,9};
		
        int value = 2;
        int index = -1;
		
		int left = 0;
        int right = arr.length-1;
        int mid = (left+right)/2;
        
        while(left <= right){
            if(arr[mid] == value){
                index = mid;
                break;
            }else if(arr[mid] < value){
                //往右查找，修改left
                left = mid + 1;
            }else{
                //往左查找，修改right
                right = mid - 1;
            }
            //重新计算
            mid = (left + right)/2;
        }
	}
    if(index==-1){
        System.out.println(value + "在数组中不存在");
    }else{
        System.out.println(value + "在数组中的下标是" + index);
    }
}
```



### 4.3.6 排序

数组的排序算法有千万种，我们只讲了两种：

1、冒泡排序

2、简单的直接排序

示例代码：从小到大，从左到右

```java
int[] arr = {5,4,6,3,1};

for(int i=1; i<arr.length; i++){//轮数 = 数组的长度-1
    //从小到大，从左到右
    //从左到右==>每一轮的起点都是arr[0]
    /*
    i = 1, j=0,1,2,3  j<arr.length-i
    i = 2, j=0,1,2    j<arr.length-i
    ...
    */
    for(int j=0; j<arr.length-i; j++){
        //从小到大==>  左边的元素 > 右边的元素，交换
        if(arr[j] > arr[j+1]){
            int temp = arr[j];
            arr[j] = arr[j+1];
            arr[j+1] = temp;
        }
    }
}

```

示例代码：从大到小，从右到左

```java
char[] arr = {'h','e','l','l','o','j','a','v','a'};

for(int i=1; i<arr.length; i++){//轮数 = 数组的长度-1
    //从大到小，从右到左
    //从右到左==>每一轮的起点都是arr[arr.length-1]
    /*
    i = 1, j=8,7,6,5,4,3,2,1	j>=i
    i = 2, j=8,7,6,5,4,3,2
    ...
    */
    for(int j=arr.length-1; j>=i; j--){
        //从大到小==>  左边的元素 > 右边的元素，交换
        if(arr[j] > arr[j-1]){
            int temp = arr[j];
            arr[j] = arr[j-1];
            arr[j-1] = temp;
        }
    }
}		
```

示例代码：简单的直接选择排序

需求：从小到大

```java
int[] arr = {3,2,6,1,8};

for(int i=1; i<arr.length; i++){//轮数 = 数组的长度 -1
    //找出本轮未排序部分的最小值及其下标
    /*
    i=1，未排序部分  arr[0]~arr[4]
    i=2，未排序部分  arr[1]~arr[4]
    ...
    */
    //假设每一轮未排序部分的第一个元素最小
    int min = arr[i-1];
    int index = i-1;
    
    //用min中的值与arr[i-1]后面的元素一一比较
    for(int j=i; j<arr.length; j++){
        if(arr[j] < min){
            min = arr[j];
            index = j;
        }
    }
    //看它是否在正确的位置，如果不是，就与它应该在的位置的元素交换
    /*
    i=1,本轮最小值应该在arr[0]
    i=2,本轮最小值应该在arr[1]
    ...
    */
    if(index != i-1){
        //交换arr[index]与arr[i-1]
        int temp = arr[index];
        arr[index] = arr[i-1];
        arr[i-1] = temp;
    }
}


//显示结果
for(int i=0; i<arr.length; i++){
	System.out.print(arr[i]);
}
```



## 4.4 二维数组

二维数组的标记：\[\]\[\]

## 4.4.1 相关的表示方式

（1）二维数组的长度/行数： 

​	二维数组名.length

（2）二维数组的其中一行：

​	二维数组名[行下标]

​	行下标的范围：[0, 二维数组名.length-1]

（3）每一行的列数：

​	二维数组名[行下标].length

（4）每一个元素

​	二维数组名\[行下标\]\[列下标\]

### 4.4.2 二维数组的声明和初始化

1、二维数组的声明

```
  //推荐
  元素的数据类型[][] 数组名;

 //不推荐
 元素的数据类型 数组名[][];
 
 //不推荐
 元素的数据类型[] 数组名[];
```

2、二维数组的初始化

（1）静态初始化

```
数组名 = new 元素的数据类型[][]{{第一行值列表},{第二行值列表}。。。};

//当声明与静态初始化在一句完成，可以省略右边的 new 元素的数据类型[][]
元素的数据类型[][] 数组名 = {{第一行值列表},{第二行值列表}。。。};
```

（2）动态初始化（不规则：每一行的列数可能不一样）

```
//（1）先确定总行数
数组名 = new 元素的数据类型[总行数][];

//（2）再确定每一行的列数
数组名[行下标] = new 元素的数据类型[该行的总列数];


//(3)再为元素赋值
数组名[行下标][列下标] = 值;
```

（3）动态初始化（规则：每一行的列数是相同的）

```java
//（1）确定行数和列数
数组名 = new 元素的数据类型[总行数][每一行的列数]; 

//（2）再为元素赋值
数组名[行下标][列下标] = 值;
```



### 4.4.3 二维数组的遍历

```
for(int i=0; i<二维数组名.length; i++){
    for(int j=0; j<二维数组名[i].length; j++){
        //...
    }
}
```

# 第五章 面向对象基础（上）

## 5.1 类与对象

1、类：一类具有相同特性的事物的“抽象”描述。类是创建对象的模板，设计图。

​      对象：是一类事物中一个“具体”的个体，实例。

2、如何声明类？

```java
【修饰符】 class 类名{
    类的成员列表
}
```

3、如何创建对象？

```java
//匿名对象
new 类名()

//有名对象
类名 对象名 = new 类名();
```

## 5.2 类的成员之一：属性

1、如何声明属性？

```
【修饰符】 class 类名{
	【修饰符】 数据类型 属性名;//创建对象后，这个属性是默认值
	【修饰符】 数据类型 属性名 = 值;//创建对象后，这个属性初始化值就是你在这里赋的值
}
```

> 说明：
>
> 属性的类型可以是Java的任意类型，包括基本数据类型、引用数据类型（类、接口、数组等）
>
> 属性的声明位置必须是类中方法外。



总结：Java的数据类型

（1）基本数据类型

byte,short,int,long,float,double,char,boolean

（2）引用数据类型

①类：

​	例如：String、Student、Circle、System、Scanner、Math...

②接口：后面讲

③数组：

​	例如：int[]，String[]，char[]，int\[\]\[\]

```java
int[] arr = new int[5];
这里把int[]看成数组类型，是一种引用数据类型，右边赋值的是一个数组的对象

元素的数据类型：int
数组的数据类型：int[]
```



2、如何为属性赋值？

（1）在声明属性时显式赋值

```
【修饰符】 class 类名{
	【修饰符】 数据类型 属性名 = 值;//创建对象后，这个属性初始化值就是你在这里赋的值
}
```

（2）创建对象之后赋值

```
//创建对象
类名 对象名 = new 类名（）；

//为对象的属性赋值
对象名.属性值 = 值;
```

3、如何访问属性的值？

（1）在本类的方法中访问

示例代码：

```
class Circle{
    double radius;
    
    double area(){
        return 3.14 * radius * radius;
    }
}
```



（2）在其他类的方法中访问

```
class Circle{
    double radius;
}
```

代码示例：

```java
class TestCircle{
    public static void main(String[] args){
        //例如：打印属性值
        Cirlce yuan = new Circle();

        //例如：比较两个对象的属性
        Cirlce c1 = new Circle();
        c1.radius = 1.2;

        Cirlce c2 = new Circle();
        c2.radius = 1.2;
        
        System.out.println("c1的半径：" + c1.radius);        
    }
}
```

4、属性的特点

（1）属性有默认值

A：基本数据类型：

​	byte,short,long,int：0

​	float,double：0.0

​	char：\u0000

​	boolean：false

B：引用数据类型：null



（2）每一个对象的属性是独立的



5、对象属性的内存图

![1567730844367](imgs/1567730844367.png)



## 5.4 类的成员之二：方法

### 5.4.1 方法的概念

方法（method），一个独立可复用的功能，在其他语言中也称为函数。

目的/好处：可复用，简化代码

### 5.4.2 方法的语法

1、方法的声明格式：

```java
【修饰符】 class 类名{
    【修饰符】 返回值类型  方法名(){
        方法体
    }
    【修饰符】 返回值类型  方法名(形参列表){
        方法体
    }
}
```

说明：

（1）【修饰符】：待讲

（2）返回值类型：

​	A：void，表示该方法不返回结果

​	B：非void的其他类型，表示该方法需要返回结果，要求在方法体中，必须最后有"return 返回值;"的语句。

（3）方法名：

​	A：见名知意

​	B：从第二个单词开始首字母大写

（4）【形参列表】：

​	当我们在设计这个方法时，在完成这个方法的功能编写时，需要“调用者”在调用时给我们传递辅助的数据，那么就表示这方法需要参数。因为在设计这个方法时，这个参数的值是不确定的，因此成为“形参参数”，简称形参。

​	形参的声明的语法格式：(数据类型 形参名1，数据类型 形参名2 。。。)

（5）方法体：实现方法的功能，最好一个方法就完成一个独立的功能。

​	一个方法的功能太复杂太多，会降低它的复用性。

2、方法的调用格式：

```java
//本类同级别方法调用
直接调用
```

```java
//在其他类的方法中调用
对象名.方法名(【实参列表】);
变量 = 对象名.方法名(【实参列表】); //这种适用于返回值类型不是void的方法
```

（1）是否传参

调用时是否需要传实参，看被调用的方法是否有形参

要求：实参的个数、类型、顺序与形参一一对应

（2）是否接收返回值

调用时是否需要接受返回值，看被调用的方法的返回值类型是否是void，如果是void，不能接收；如果不是void，可以接收也可以不接收。



3、方法的声明与调用的代码示例

（1）无参无返回值方法

```java
//本类
class TestTools{
    void printHelloWorld(){
        System.out.println("hello world");
    }
    
    void printTenTimeHelloWorld(){
        for(int i=0;i<10;i++){
            printHelloWorld();
            //本类中调用，不需要对象.，因为无参，也不用传参，因为无返回值，也不用接收
        }
    }
}
```

```java
//其他类
class Tools{
    void printHelloWorld(){
        System.out.println("hello world");
    }
}
class TestTools{
    public static void main(String[] args){
        Tools t = new Tools();
        t.printHelloWorld();
        //在其他类中，需要用“对象.”
        //因为无参，也不用传参，因为无返回值，也不用接收
    }
}
```



（2）无参有返回值方法

```java
//本类
class Circle{
    double radius;
    double area(){//无参有返回值
        return 3.14 * radius * radius;
    }
    String getInfo(){
        return "半径：" + radius + "，面积：" + area();
        //本类中调用，不需要对象.，因为无参，也不用传参，因为有返回值，可以直接拼接返回值
    }
}
```

```java
//其他类
class Circle{
    double radius;
    double area(){//无参有返回值
        return 3.14 * radius * radius;
    }
}
class TestCircle{
    public static void main(String[] args){
        Circle c = new Circle();
        System.out.println("面积：" + c.area());
        //其他类中调用，需要对象.，因为无参，也不用传参，因为有返回值，可以直接拼接返回值并打印
    }
}
```



（3）有参无返回值方法

```java
//本类
class Tools{
    void printRectangle(int line, int column ,char sign){
        for(int i=1; i<=line; i++){
            for(int j=1; j<=column; j++){
                System.out.print(sign);
            }
            System.out.println();
        }
    }
    
    void test(){
        printRectangle(5,10,'$');
        //本类中调用，不需要对象.
        //因为有参，必须传参
        //因为没有返回值，那么不用接收
    }
}
```

```java
//其他类
class Tools{
    void printRectangle(int line, int column ,char sign){
        for(int i=1; i<=line; i++){
            for(int j=1; j<=column; j++){
                System.out.print(sign);
            }
            System.out.println();
        }
    }
}
class TestTools{
    public static void main(String[] args){
        Tools t = new Tools();
        t.printRectangle(5,10,'$');
        //其他类中调用，需要对象.
        //因为有参，必须传参
        //因为没有返回值，那么不用接收
    }
}
```



（4）有参有返回值方法

```java
//本类
class Tools{
    int max2(int a, int b){
        return a > b ? a : b;
    }
    
    /*
    int max3(int a, int b, int c){
        int m = max2(a,b);//返回a和b中的最大值
        m = max2(m,c);//返回m和c中的最大值
        //在本类中调用，不需要对象.
        //有参，传参
        //有返回值，接收返回值
        return m;
    }
    */
    int max3(int a, int b, int c){
        return max2(max2(a,b) , c);//把max2（a,b）的返回的结果作为下一次max2方法调用的实参
    }
    
}
```

```java
//其他类
class Tools{
    int max2(int a, int b){
        return a > b ? a : b;
    }
}
class Test{
    public static void main(String[] args){
        Tools t = new Tools();
        int m =  t.max2(4,5);
        System.out.println("最大值：" + m);
    }
}
```



4、方法声明与调用的原则

（1）方法必须先声明后调用

> 如果调用方法时，如果方法名写错或调用一个不存在的方法，编译会报错



（2）方法声明的位置必须在类中方法外

正确示例：

```java
类{
    方法1(){
        
    }
    方法2(){
        
    }
}
```

错误示例：

```java
类{
    方法1(){
        方法2(){  //错误
        
   		}
    }
}
```

（3）方法的调用的位置有要求

正确示例：

```java
类{
    方法1(){
        调用方法
    }
}
```

错误示例：

```java
类{
    方法1(){
        
    }
    
    调用方法  //错误位置
}
```

（4）方法的调用格式要与方法的声明格式对应

①是否要加“对象.”

②是否要接收返回值

③是否要传参

### 5.4.3 方法调用内存分析

方法不调用不执行，调用一次执行一次，每次调用会在栈中有一个入栈动作，即给当前方法开辟一块独立的内存区域，用于存储当前方法的局部变量的值，当方法执行结束后，会释放该内存，称为出栈，如果方法有返回值，就会把结果返回调用处，如果没有返回值，就直接结束，回到调用处继续执行下一条指令。

栈结构：先进后出，后进先出。

```java
class Test01Invoke{
	public static void main(String[] args){
		Tools t = new Tools();
		int x = 5;
		int y = 3;
		int z = 8;
		
		int m = t.max3(x,y,z);
		System.out.println("最大值：" + m);
	}
}
class Tools{
	int max2(int a, int b){
        return a > b ? a : b;
    }
	int max3(int a, int b, int c){
        return max2(max2(a,b) , c);
    }
}
```

![1567816698309](imgs/1567816698309.png)

```java
class Test01InvokeExer{
	public static void main(String[] args){
		Tools t = new Tools();
		int x = 5;
		int y = 3;
		
		int m = 4;
		int n = 4;
		
		int sum1 = t.sum(x,y);
		int sum2 = t.sum(m,n);
		int m = t.max(sum1,sum2);
	}
}
class Tools{
	int sum(int a, int b){
		return a + b;
	}
	int max(int a, int b){
		return a > b ? a : b;
	}
}
```

![1567816781528](imgs/1567816781528.png)

### 5.4.4 可变参数

1、什么情况下需要使用可变参数

当我们在声明一个方法，需要额外的数据来辅助完成方法的功能，但这个时候数据类型是确定的，但是个数不确定，那么这个时候就可以考虑使用可变参数。

2、形式

```
数据类型... 可变参数名
```

3、如何声明可变参数

```
【修饰符】 返回值类型 方法名(数据类型... 可变参数名){
    
}
【修饰符】 返回值类型 方法名(其他非可变参数形参列表 , 数据类型... 可变参数名){
    
}
```

>注意：
>
>（1）一个方法最多只有一个可变参数
>
>（2）这个可变参数必须在形参列表的最后一个
>
>（3）可变参数的数据类型可以是Java任意类型

4、如何使用可变参数

（1）在声明它的方法中，把可变参数当做数组使用

（2）调用时

原则：方法调用时，非可变参数部分，实参的个数、类型、顺序与形参一一对应，有一个传一个。

​             对于可变参数部分，我们可以传入：（1）传入0~n个对应类型的实参（2）传入对应类型的数组对象



### 5.4.5 方法的重载

概念：在同一个类中，当方法名称相同，形参列表不同的两个或多个方法称为方法的重载。和返回值类型无关。

示例代码：

```java
	//求两个整数的最大值
int max(int a, int b){
    return a>b?a:b;
}
	
	//求三个整数的最大值
int max(int a, int b,int c){
    return max(max(a,b),c);
}
	
	//求两个小数的最大值
double max(double a, double b){
    return a>b?a:b;
}
```

> 形参列表的不同：可以是类型、个数、顺序不同

陷阱1：编译器会认为如下两个方法是一样的

```
int sum(int... args){
    //...
}
int sum(int[] args){
    //...
}
```

陷阱2：当调用时，会出现同时匹配两个方法时，会有问题，例如：sum(1,2,3)

```
int sum(int a, int... args){
    //...
}
int sum(int... args){
    
}
```



### 5.4.6 方法的参数传递机制

Java中方法的参数传递机制：值传递

（1）形参是基本数据类型时，实参给形参赋“数据值”，形参的修改和实参完全无关。
（2）形参是引用数据类型时，实参给形参赋"地址值"，如果直接通过形参去修改对象的属性或元素，那么会影响实参，因为此时实参和形参指向“同一个对象”；但是如果把形参“重新“指向”新对象“时，那么形参的修改就和实参无关了。
示例代码：

```java
class Tools{
    void swap(int a, int b){
        int temp = a;
        a = b;
        b = temp;
    }
}
class Test{
    public static void main(String[] args){
        Tools t = new Tools();
        int x = 1;
        int y = 2;
        t.swap(x,y);
        System.out.println("x = " + x);//1
        System.out.println("y = " + y);//2
    }
}
```

示例代码：

```java
class MyData{
    int num;
}
class Tools{
    void change(MyData my){
        my.num *= 2;
    }
}
class Test{
    public static void main(String[] args){
        MyData d = new MyData();
        d.num = 1;
        
        Tools t =new Tools();
        t.change(d);
        
        System.out.println(d.num);//2
    }
}
```

陷阱1：

```java
/*
陷阱1：在方法中，形参 = 新new对象，那么就和实参无关了
*/
class MyData{
    int num;
}
class Tools{
    void change(MyData my){
        my = new MyData();
        my.num *= 2;
    }
}
class Test{
    public static void main(String[] args){
        MyData d = new MyData();
        d.num = 1;
        
        Tools t =new Tools();
        t.change(d);
        
        System.out.println(d.num);//1
    }
}
```

陷阱2：见字符串和包装类部分



### 5.4.7 命令行参数

1、命令行参数：给main传的实参，因为是在运行java命令时，传入的参数，所以叫做命令行参数

2、格式

```java
java 类名 参数1 参数2  参数3 。。。
```

> 每一个参数值之间使用空格分隔

### 5.4.8 递归调用

1、递归：一个方法直接或间接自己调用自己，就称为递归

2、注意：

（1）必须有终止条件，否则会出现“StackOverflowError"栈内存溢出

（2）递归调用不宜太深，否则会效率很低，或者内存溢出

```java
//n!
int jieCheng(int n){
	if(n<0){
        throw new RuntimeException("负数没有阶乘");
	}
	if(n==0 || n==1){
        return 1;
	}
    return n * jieCheng(n-1);
}
```



## 5.3 对象数组

元素是引用数据类型，也称为对象数组，即数组的元素是对象

1、一维对象数组：

> 注意：一维对象数组，首先要创建数组对象本身，即确定数组的长度，然后再创建每一个元素对象，如果不创建，数组的元素的默认值就是null，所以很容易出现空指针异常NullPointerException。

示例代码：

```java
class MyDate{
	int year;
	int month;
	int day;
}
class Test{
    public static void main(String[] args){
        MyDate[] arr = new MyDate[3];//创建数组对象本身，指定数组的长度
        
        for(int i=0; i<arr.length; i++){
            arr[i] = new MyDate();//每一个元素要创建对象
            arr[i].year = 1990 + i;
            arr[i].month = 1 + i;
            arr[i].day = 1 + i;
        }
    }
}
```

一维对象数组的内存图：

![1567990081471](imgs/1567990081471.png)

2、二维对象数组

> 注意：二维对象数组，首先要创建数组对象本身，即确定数组的行数，然后再确定每一行的列数，然后再创建每一个元素对象，如果不创建，数组的元素的默认值就是null，所以很容易出现空指针异常NullPointerException。
>
> 即要创建三种对象：二维数组对象，行对象，元素对象

示例代码：

```java
class Student{
    String name;
    int score;
}
class Test{
    public static void main(String[] args){
        java.util.Scanner input = new java.util.Scanner(System.in);
        
        Student[][] all = new Student[3][];//创建二维数组对象，表示有3组
        
        //创建的是行对象，即指定每一行的列数
        all[0] = new Student[2];
        all[1] = new Student[2];
        all[2] = new Student[3];
        
        //创建元素对象，并为元素对象的属性赋值
        for(int i=0; i<all.length; i++){
            for(int j=0; j<all[i].length; j++){
                all[i][j] = new Student();
                System.out.print("请输入第"+(i+1)+"组的第"+(j+1)+"个学员的姓名和成绩：");
                all[i][j].name = input.next();
                all[i][j].score = input.nextInt();
            }
        }
    }
}
```

二维对象数组的内存图：

![1567990416077](imgs/1567990416077.png)

# 第六章 面向对象基础（中）

面向对象的基本特征：

1、封装

2、继承

3、多态



## 6.1 封装

1、好处：（1）设计者更安全、更可控（2）使用者更方便

2、如何实现封装？

通过对类、成员等设置不同的可见性范围。

原则：暴露该暴露的，隐藏该隐藏。暴露的程度依赖于权限修饰符。

> 面试题：请按照可见范围从小到大（从大到小）列出权限修饰符？

​			本类	本包	其他包的子类	其他包的非子类

private	  yes	   no		no			no

缺省		yes	  yes

protected	yes 	yes	  yes

public		yes 	yes	  yes			yes



权限修饰符可以修饰什么？

（1）外部类：public或缺省

（2）成员：成员变量（属性）、成员方法、构造器、成员内部类

​	四种都可以



3、通常属性的封装是什么样的？

当然属性的权限修饰符可以是private、缺省、protected、public。但是我们大多数时候，见到的都是private，然后给它们配上公共的get/set方法。

示例代码：标准Javabean的写法

```java
public class Student{
    //属性私有化
    private String name;
    private int score;
    
    //保留默认的无参构造
    public Student(){//必选
        
    }
    public Student(String name,int score){//可选
        this.name = name;
        this.score = score;
    }
    
    //提供公共的get/set
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
}
```

## 6.2 构造器

1、构造器的作用：
（1）和new一起使用，创建实例对象

```java
//调用无参构造创建对象
类名 对象名 = new 类名();

//调用有参构造创建对象
类名 对象名 = new 类名(实参列表);
```

（2）在创建对象的同时为属性初始化

如果是无参构造，那么属性是默认值，或者属性的显式初始化值；

如果是有参构造，那么属性就是你在构造器中指定的实参的值



2、声明构造器的语法格式：

```
【修饰符】 class 类名{
	【修饰符】 类名(){
        //...
	}
	【修饰符】 类名(【形参列表】){
        //...
	}
}
```

3、构造器的特点：

（1）构造器名称必须与类名相同

（2）构造器没有返回值类型

（3）每一个类都应该有构造器，如果程序员没有手动编写，那么编译器会自动生成一个默认的无参构造；如果程序员编写了构造器，那么无参构造也需要手动编写，否则不会自动增加。

（4）构造器可以重载

（5）默认生成的无参构造的权限修饰符保持和类的权限修饰符一致。



示例代码：

```java
public class Circle{
    private double radius;
    public Circle(){
        
    }
    public Circle(double radius){
        this.radius  = radius;
    }
}
```



## 6.3 关键字this

1、this关键字：当前对象

（1）在构造器中，this代表的是你正在new的实例对象

（2）在成员方法中，this代表的是你调用当前方法的对象

2、this的用法：

（1）this.属性

当局部变量与成员变量同名时，在成员变量的前面加this.用于区别

（2）this.方法

完全可以省略this.

（3）this()或this(实参列表)

必须在构造器的首行。

this()：表示调用本类的无参构造

this(实参列表)：表示调用本类的有参构造



示例代码：

```java
public class Student{
    //属性私有化
    private String name;
    private int score;
    
    //保留默认的无参构造
    public Student(){//必选
        
    }
    public Student(String name){
        this();//表示调用本类的无参构造
        this.name = name;
    }
    public Student(String name,int score){//可选
        this(name);//表示调用本类的有参构造
        this.score = score;
    }
    
    //提供公共的get/set
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public void setScore(int score){
        this.score = score;
    }
    public int getScore(){
        return score;
    }
}
```



3、成员变量与局部变量的区别？

这里只讨论实例变量（关于类变量见static部分）

（1）声明的位置不同

成员变量：类中方法外

局部变量：在方法中：A：方法的()中的形参，B：方法体{}中，C：代码块{}中

（2）在内存中的存储位置不同

成员变量：在堆中

局部变量：栈

（3）作用域

成员变量：本类中直接使用，其他类中通过“对象.”

局部变量：有作用域  （超出作用域就不能使用，同一个作用域中不能同名）

​	作用域的范围：从声明它的位置开始，到它所属的{}结束

（4）生命周期

成员变量：与它所属的对象同生共死。每一个对象是独立的。

局部变量：与方法的调用同生共死，每次方法调用都是独立。

（5）修饰符

成员变量：可以使用权限修饰符等

局部变量：不能使用权限修饰符。



## 6.4 包

1、包的作用：

（1）避免类的同名

（2）控制某些类或成员的可见性范围

（3）组织管理不同的类



2、声明包的语法格式：

```java
package 包名;
```

> 注意：
>
> 必须在.java的源文件的代码首行
>
> 注意命名规范和习惯



3、包的命名规范和习惯：
（1）包名：全部都小写，所有单词之间使用.分割
（2）习惯用公司域名倒置 + 模块名称



4、使用其他包的类：
（1）使用全名称：java.util.Scanner input = new java.util.Scanner(System.in);
（2）使用import导包，在代码中使用简名称



5、import语句

```java
import 包名.类名;
import 包名.*;
```

> 注意：当使用两个不同包的同名类时，例如：java.util.Date和java.sql.Date，。最多只能有一个使用导包语句+简名称，其他的使用全名称
>
> java.lang包下的类型不需要导包，就可以直接使用简名称

说明：关于静态导入，到static部分再说

示例代码：

```java
package com.atguigu.test;

import java.util.Scanner;

public class Test{
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
    }
}
```



## 6.5 eclipse的使用

1、eclipse管理项目和代码的结构

workspace --> project --> 包-->类...

一个工作空间可以有多个项目。



2、快捷键

常规快捷键：

Ctrl + S：保存

Ctrl + C：复制

Ctrl + V：粘贴

Ctrl + X：剪切

Ctrl + Y：反撤销

Ctrl + Z：撤销

Ctrl + A：全选

Ctrl + F：查找与替换



eclipse中默认的快捷键：

Ctrl + 1：快速修复

Alt + /：代码提示

Alt + ?：  Alt + Shift + /   方法的形参列表提示

Ctrl + D：删除选中行

Ctrl + Alt + ↓：向下复制行

Ctrl + Alt + ↑：向上复制行

Alt + ↓：与下面的行交换位置

Alt + ↑：与下面的行交换位置

Ctrl + Shift + F：快速格式

Ctrl + /：单行注释，再按一次取消

Ctrl + Shift + /：多行注释

Ctrl + Shift +\：取消多行注释

Shift + 回车：在光标下一行插入新行开始编辑

Ctrl + Shift + 回车：在光标上一行插入新行开始编辑

Alt + Shift + A：多行编辑     再按一次退出多行编辑模式

Alt + Shift + S：弹出自动生成代码的菜单选择，包括自动生成构造器、get/set、equals......

Ctrl + Shift + O：快速导包

Ctrl + Shift + T：打开某个类的源文件

Ctrl + O：打开某个类型的摘要outline



3、快速开发的代码模板

代码模板 + Alt + /

（1）main

public static void main(String[] args){

}

（2）sysout

System.out.println();



（3）for

for(int i=0; i<数组名.lenght; i++){

}



其他详细使用见《JavaSE_柴林燕_相关工具.docx》



## 6.6 关键字static

1、static：静态的

2、static是修饰符，它可以修饰什么？

（1）成员变量

（2）方法

（3）代码块

（4）成员内部类

3、static修饰成员变量，称为类变量或静态变量

```java
【修饰符】 class 类{
    【其他修饰符】 static 数据类型 成员变量名;
    【其他修饰符】 static 数据类型 成员变量名 = 值;
}
```

特点：

（1）在内存中存储在方法区

（2）静态变量不单独属于某个对象，是所有对象共享的

（3）它的get/set也是静态的

（4）在静态的set方法等静态方法中，出现了局部变量与静态变量同名时，那么就用“类名."进行区分

（5）静态变量一般不在构造器中为它初始化，一般在静态代码块中或显式赋值初始化。

4、static修饰方法，称为类方法或静态方法

```java
【修饰符】 class 类{
    【其他修饰符】 static 返回值类型 方法名(【形参列表】){
    }
}
```

特点：

（1）静态方法，我们可以使用“类名."进行调用。虽然也可以使用”对象."进行调用，但是不推荐。

（2）静态方法中不允许出现：

A：this

B：对本类非静态成员（属性、方法）的引用

C：super等......



6、成员变量与局部变量的区别？

（1）声明的位置不同

成员变量：类中方法外

​	成员变量又为静态变量（有static）与实例变量（没有static）。

局部变量：在方法中：A：方法的()中的形参，B：方法体{}中，C：代码块{}中

（2）在内存中的存储位置不同

静态变量：在方法区

实例变量：在堆中

局部变量：栈

（3）作用域

静态变量：在本类中直接使用，其他类中通过“类名.”

实例变量：本类的非静态方法中直接使用，其他类中通过“对象.”

局部变量：有作用域  （超出作用域就不能使用，同一个作用域中不能同名）

​	作用域的范围：从声明它的位置开始，到它所属的{}结束

（4）生命周期

静态变量：与所属的类同生共死。每一个对象是共享的。

实例变量：与它所属的对象同生共死。每一个对象是独立的。

局部变量：与方法的调用同生共死，每次方法调用都是独立。

（5）修饰符

成员变量：可以使用权限修饰符等

局部变量：不能使用权限修饰符。



## 6.7 常用的工具类

### 6.7.1 java.lang.Math类

这个类中的方法、常量都是静态的，都是和数学计算相关的

（1）double Math.PI：圆周率

（2）double Math.sqrt(x)：求x平方根

（3）double Math.random()：返回[0,1)范围的随机值

（4）double Math.pow(x,y)：求x的y次方

（5）double Math.ceil(x)：往上取整

（6）double Math.floor(x)：往下取整

（7）long Math.round(x)：四舍五入

（8）double Math.max(x,y)：最大值

（9）double Math.min(x,y)：最小值

。。。



### 6.7.2 java.util.Arrays类

这个类中的方法也都静态的，都是和数组操作相关的

（1）int binarySearche(数组，要查找的值)：使用二分查找法在数组中查找某个的下标，如果存在返回下标，如果不存在返回负数（负数= -插入点-1），要求这个数组必须是有序。

（2）新数组 copyOf(原数组， 新数组的长度)：从原数组的[0]开始复制，得到一个新数组返回

（3）新数组 copyOfRange(原数组， 起始下标， 终止下标)：从原数组的[起始下标]开始复制，新数组的长度 = 终止下标 -  起始下标。[起始下标，终止下标)

（4）void fill（数组， 值）：用这个值把数组填满

（5）boolean equals(数组1， 数组2)：只有两个数组的长度、元素都一致才会返回true

（6）void sort（数组）：默认按照升序排序

（7）String toString（数组）：把数组的元素的值拼接为一个字符串返回，形式：[元素1，元素2.。。]



### 6.7.3 java.lang.System类

这个类中的方法也都静态的，都是和系统最常用功能有关

（1）静态的常量对象：

System.in：默认情况，代表键盘输入

System.out：默认情况，代表输出到控制台，输出普通文本

System.err：：默认情况，代表输出到控制台，输出错误信息

（2）System.arraycopy(原数组， 原数组的起始下标， 目标数组， 目标数组的起始下标， 一共要复制或移动几个元素)

原数组和目录数组如果不是同一个数组，那么实现的是复制元素的功能；

原数组和目录数组如果是同一个数组，那么实现的是移动元素的功能，一般在数组需要删除、插入元素时使用。

```java
int[] arr1 = {1,2,3,4,5};
int[] arr2 = new int[10];

//把arr1的5个元素复制到arr2中，arr2从[3]开始存储
System.arraycopy(arr1, 0, arr2, 3, arr1.length);

//把arr1中[1]元素给删除掉
//需要移动：[2]->[1],[3]->[2],[4]->[3]
int total = arr1.length;元素的总个数
int index = 1;//被删除的下标
System.arraycopy(arr1, 2, arr1, 1, total-index-1);
arr1[total-1] = 0;

//在arr1的2和3数字之间插入10
//先扩容
arr1 = Arrays.copyOf(arr1, 新长度);
//移动元素
//total = 5, index = 2
//[2]->[3],[3]->[4],[4]->[5]
System.arraycopy(arr1,2,arr1,3, total-index);
arr[2] = 10;//插入新值
```

> 删除时，往左移，原数组的起始下标 > 目标数组的起始下标，因为删掉一个，要移动的元素会少一个，total-index-1
>
> 插入时，往右移，原数组的起始下标 < 目标数组的起始下标，移动元素个数：total-index

（3）long System.currentTimeMillis()：返回当前系统距离1970年1月1日凌晨的毫秒值

（4）void System.exit(0)：0表正常退出，非0表示异常终止



## 6.8 继承

1、继承的好处和目的：（1）代码复用（2）代码（功能）的扩展

2、什么情况下会考虑使用继承？

（1）当你新声明一个类，发现已有的某个类中的所有的特征，在你这个新的类型中都有，并且新的类型与已有类型是is-a的关系，那么可以考虑继承这个已有的类，避免编写重复的代码。在新的类型中增加已有的类没有的部分，实现功能的扩展。

（2）当你同时编写了多个类，发现这多个类中有共同的特性，那么此时可以考虑把这些共同特性抽取到父类中，实现代码的简化，从而也实现使用父类管理众多的子类对象。

3、继承的语法格式

```java
【修饰符】 class 子类 extends 父类{
    
}
```

4、继承的特点

（1）父类中所有的属性和方法都会被继承，但是私有的（如果跨包的话，缺省的）等这些在子类中不可见的，在子类中无法直接使用，我们可以间接使用它。

（2）父类中的构造器是不能被继承的。

（3）但是子类的构造器中必须调用父类的构造器，目的就是为从父类继承的属性进行初始化。

（4）Java中的继承有单继承的限制，即直接父类只有一个

（5）但是Java支持多层继承，父类还可以有父类

（6）一个父类可以同时拥有多个子类



5、方法的重写Override

当子类继承了父类，父类的某个方法的实现（方法体）不适用于子类，那么子类可以选择重写。

重写的要求：

（1）方法名称：必须相同

（2）形参列表：必须相同

（3）返回值类型：

​	基本数据类型和void：必须相同

​	引用数据类型：<=        （规定：子类的类型 < 父类的类型，例如：学生 < 人，学生的范围是人的范围的一部分）

（4）权限修饰符：>=

（5）不能被重写的方法

A：final

B：static

C：private

D：跨包还不能是缺省的



## 6.9 super

1、super：父类的

​	当前子类对象中，访问父类中声明的属性、方法、构造器

2、前提：

​	不管你是通过super去访问什么，都要保证这个成员在子类中是可见的

3、用法的形式有三种：

（1）super.属性

当子类声明了与父类同名的属性，并且父类这个属性在子类中可见，那么为了区别，super.属性代表的是从父类继承的。

（2）super.方法

当子类重写了父类的某个方法，又想要在子类中调用被重写的方法，那么可以使用super.方法代表父类中被重写的方法。

（3）super()或super(实参列表)

在子类的构造器的首行一定会调用父类的构造器。

super()：调用的是父类的无参构造器。

super(实参列表)：调用 的是父类的有参构造。



A：如果父类和子类都没有写构造器，那么都有默认的无参构造，编译器也会自动调用父类的无参构造。

B：如果父类有手动编写无参构造，那么子类可以选择声明构造器，也可以不声明构造器，因为此时编译器默认会去调用父类的无参构造。

C：如果父类没有无参构造，只写了有参构造，那么子类必须编写构造器，并且在构造器的首行必须使用super(实参列表)明确调用父类的有参构造，否则编译报错。

结论：编写类时，尽量的保留无参构造。



## 6.10 final

1、final：最终的

2、修饰类：表示这个类不能被继承，即不能有子类，是个太监类，例如：String,System,Math等

3、修饰方法：表示这个方法不能被重写

4、修饰变量：表示这个变量的值不能被修改，称为常量。

​	常量名的命名习惯：见名知意，每个单词都大写，单词之间使用_分割。



## 6.11 类初始化

1、类初始化的目的/作用：

​	为静态变量赋值。

2、类初始化的执行特点：

（1）第一次使用这个类的时候

（2）每一个类都只会初始化一次

（3）类初始化的过程其实就是在执行一个<clinit>()方法

（4）当子类初始化时，如果此时父类还没有初始化，那么会先初始化父类



3、<clinit>()方法：

（1）它不是程序员自己编写的方法，是有编译器帮我们组装而成

（2）它由：

A：静态变量的显式赋值部分

B：静态代码块中代码

它们从上往下按顺序组装到一起，构成一个完整的<clinit>()方法。



4、静态代码块

```java
【修饰符】 class 类{
    
    static{
        //....静态代码块
    }
}
```

## 6.12 实例初始化

1、作用：给非静态成员变量（实例变量）赋值

2、实例初始化过程其实是在执行实现初始化方法<init>(...)中一个。

3、实例初始化方法

（1）每一个类至少有一个实例初始化方法，也可能有多个，它的个数和你声明的构造器的个数是一样

（2）实例初始化方法的代码是由编译根据程序编写的某些代码组装而成，它由四个部分：

A：super()或super(实参列表)：表示执行父类对应的实例初始化方法

B：实例变量的显式赋值语句

C：非静态代码块

D：构造器中的其他代码

其中的B和C是按照代码的编写顺序组装，而A和D是固定的位置。

（3）实例初始化方法只有在new对象时执行，每次new对象都会有一个实例初始化方法被执行。

4、如果第一次使用某个类就是创建对象，那么会先完成类的初始化，然后完成实例初始化。

5、非静态代码

```java
【修饰符】 class 类{
    
    {
        //....非静态代码块
    }
}
```



# 第七章 面向对象基础（下）

## 7.1 抽象类

1、什么时候会用到抽象类？

（1）某个父类不想让你创建它的对象，哪怕它里面没有抽象方法，可以把这样的类声明为抽象类。

（2）某个父类中包含无法给出具体实现的抽象方法，那么这样的类必须声明为抽象类。

因为父类中需要体现它的子类们的共同的特征，有些特征在父类中无法给出具体代码实现，不得不声明为抽象方法。



2、抽象方法的语法格式：

```java
【其他修饰符】 abstract 返回值类型 方法名(【形参列表】);
```

抽象方法：（1）必须有abstract（2）没有方法体（3）包含抽象方法的类必须是抽象类



3、抽象类的语法格式

```java
【其他修饰符】 abstract class 类名 【extends 父类】{
    
}
```



4、抽象类的特点

（1）不能直接创建对象

（2）包含抽象方法的类必须是抽象类

（3）抽象类也可以没有抽象方法

（4）抽象类也可以有属性、构造器等其他的成员，它的构造器是给子类创建对象时调用

（5）抽象类是用来被继承的，子类继承抽象类必须重写所有的抽象方法，否则子类也得是抽象类



## 7.2 多态

1、形式

```java
//多态引用
父类类型  变量 = 子类的对象;
```

2、前提

（1）继承

（2）重写

（3）多态引用

3、现象

这个变量在编译时和运行时表现的类型不一致。

编译时按照父类编译，只能调用父类中拥有的方法，运行时执行的是子类重写的代码。

4、应用

（1）多态参数：方法的形参是父类的类型，调用时实参可以传入子类的对象

（2）多态数组：元素的类型是父类的类型，给元素赋值时可以是子类的对象

5、多态的好处：使得功能更强大，更灵活，可以使用父类的变量管理各种子类的对象

6、要注意

（1）属性没有多态：只看编译时类型

（2） 静态方法没有多态：只看编译时类型

## 7.3 向上转型与向下转型

1、向上转型

当把子类的对象赋值给父类的变量时，在编译时这个对象就被向上转型为父类的类型。

但是运行时，仍然是子类的类型。

2、向下转型

当把一个父类的变量的值赋值给子类的变量时，就需要强制类型转换，向下转为子类的类型。

这个转换是有风险的，可能会发生ClassCastException。

只有当这个父类的变量中实际存储的对象的类型 <= 被强转的子类类型，才能转换成功。

我们可以使用instanceof来判断这个父类的变量存储的对象是否属于被强转的子类的类型，来避免ClassCastException类型转换异常。

3、instanceof

```
if(父类的变量 instanceof 被强转的类型){
    
}
```

```java
Person p = new Man();//向上转型

Man m = (Man)p;//向下转型

if(p instanceof Woman){//判断
    Woman w = (Woman)P;
    //...
}
```

## 7.4 关键字native

1、native关键字的意思：本地的，原生的

2、native关键字的作用：用来修饰方法的

3、特点：

（1）它的方法体的实现不是由Java语言实现的，而是由C等语言实现的

（2）我们在Java程序中调用时，可以当做普通的Java方法一样调用，如果子类想要进行重写，只要这个native方法不是static, final的，子类也可以重写。

（3）native的方法运行时是在本地方法栈中开辟空间

![](imgs/1555119319865.png)

方法区：存储加载的类的信息、静态变量、常量等

堆：new出来的对象

虚拟机栈：存储Java实现方法的局部变量等

本地方法栈：存储native方法的局部变量等

程序计数器：存储每一个线程下一条指令



## 7.5 根父类

1、如何理解根父类？

（1）所有引用数据类型都将Object类视为我们的父类，可能是直接或间接的继承了Object。

（2）它的方法所有对象都拥有，包括数组对象

（3）Object类型的变量可以与任意类型的对象构成“多态引用”

（4）所有对象的创建都会最终调用Object的无参构造。

（5）Object类型的数组可以存储任意类型对象



2、根父类的常用方法：

（1）public String toString()：返回对象的信息

A：如果没有重写，默认返回的是“对象的运行时类型@对象的hashCode值的十六进制形式”

B：建议子类都要重写这个方法，并且用简单明了的形式来返回的对象的详细信息

C：如果我们打印一个对象或一个对象与String进行拼接时，就会自动调用一个对象的toString()

（2）public void finalize()：这个方法由GC垃圾回收器调用，再某个对象确定称为垃圾对象时调用，如果这个对象在finalize()方法又“复活”了，下次称为垃圾后就不会再调用了，直接回收。一般建议资源对象来重写这个方法用于彻底释放资源。

（3）public Class getClass()：获取某个对象的运行时类型

（4）public Object clone()：用于克隆一个和当前对象除了内存地址其他都一样的对象，但是要求这个类型必须实现java.lang.Cloneable接口

（5）public int hashCode()：返回某个对象的hashCode值，hashCode值使用用于在哈希表等存储结构中存储对象时使用的

（6）public boolean equals(Object obj)：判断当前对象this与指定对象obj是否相等。

A：如果没有重写，和==一样，比较的是内存的地址

B：我们可以选择进行重写



hashCode与equals方法重写的一些要求：

A：它俩必须一起重写，而且选择的比较的成员变量或者其他值要一样

B：如果两个对象equals方法比较返回true，要求hashCode值必须一样

C：如果两个对象的hashCode值一样，两个对象调用equals不要求一样

D：如果两个对象的hashCode值不一样，两个对象调用equals一定返回false



equals方法重写还有一些原则：

A：自反性：x.equals(x)一定返回true

B：传递性：x.equals(y)返回true,  y.equals(z)返回true,那么x.equals(z)一定true

C：对称性：x.equals(y)和y.equals(x)结果一致

D：一致性：只要参与计算和比较的属性的值没有修改，那么无论什么时候调用结果要一致

E：非空对象与null返回false



## 7.6 接口

1、接口：行为标准

2、如何声明一个接口？

```java
【修饰符】 interface 接口名{
    //...接口的成员
}
【修饰符】 interface 接口名 extends 接口们{
    //...接口的成员
}
```

3、一个类如何实现接口

```java
【修饰符】 class 类名 implements 接口们{
    //...类的成员
}
【修饰符】 class 子类名 extends 父类 implements 父接口们{
    //...类的成员
}
```

4、接口的特点

（1）接口也是不能直接实例化，即不能直接创建对象

（2）接口的成员

JDK1.8之前，只能有：

A：公共的静态的常量：public static final （可以省略三个修饰符）

B：公共的抽象方法：public abstract（可以省略两个修饰符）

C：公共的静态的内部接口：public static （可以省略两个修饰符）

JDK1.8之后，又增加了：

D：公共的静态的方法：public static （不能省略static）

E：公共的默认方法：public default（不能省略default）

JDK1.9之后，又增加了

F：私有的方法

（3）接口就是用来被实现的，实现类实现接口时，要求必须重写接口的所有的抽象方法，否则这个实现类就得是抽象类

（4）Java中类与类之间有单继承限制，但是类与接口之间是支持多实现的

（5）接口还可以继承接口，而且支持多继承



5、默认方法的冲突问题

（1）当一个类同时实现了多个接口，多个接口中存在了方法签名相同的默认方法，此时要求实现类必须做出选择

A：选择保留其中一个接口的实现，通过：接口名.super.方法

B：选择完全重写

（2）当一个类继承了父类，又实现了接口，父类中存在了方法签名与接口的默认方法相同的方法，此时这个子类有几个方案：

A：这个子类默认保留的是父类的实现

B：我们也可以选择保留接口的实现，通过：接口名.super.方法

C：选择完全重写



6、两个和对象的比较大小有关的接口：

（1）java.lang.Comparable接口：自然排序

​	它的抽象方法： int compareTo(Object obj)，

​	实现时，要求，当this对象“大于、小于、等于”指定对象obj时，返回正整数、负整数、0

​	调用这个方法，通过判断返回值的情况，来决定两个对象谁大谁小

（2）java.util.Comparator接口：定制排序

​	它的抽象方法：int compare(Object o1, Object o2)，

​	实现时，要求o1对象“大于、小于、等于”o2对象时，返回正整数、负整数、0

​	调用这个方法，通过判断返回值的情况，来决定两个对象谁大谁小

## 7.7 内部类

### 7.7.1 为什么需要内部类？

类：一类具有相同特性的事物的抽象描述。

当我们描述一个事物时，发现它的内部又有另一个完整的结构，这个完整的结构有自己的属性、方法等特征，而且这个内部类只为这个外部类服务，这个时候就可以把这种内部结构声明为内部类。在语法的角度，内部类是可以使用外部类的所有成员，包括私有的，但是关于静态和非静态有一些限制。

在JavaSE阶段，内部类的应用场景在集合部分最明显。

### 7.7.2 内部类的形式

1、成员内部类

（1）静态内部类

（2）非静态成员内部类

2、局部内部类

（1）有名字的局部内部类

（2）匿名的内部类



### 7.7.3  静态内部类

1、语法格式

```java
【修饰符】 class 外部类{
	【修饰符】 static class 内部类{
        
	}
}
```

静态内部类的修饰符：

（1）四种权限修饰符：public，protected，缺省，private

（2）abstract

（3）final

（4）static



2、静态内部类也是一个类

（1）可以继承自己的父类，实现自己的父接口们

（2）成员没有限制

（3）它有自己的字节码文件：外部类名$静态内部类名.class



3、静态内部类的使用问题

（1）在静态内部类中使用外部类的成员

有限制，只能用外部类的其他静态成员，不能使用外部类其他非静态成员

（2）在外部类中使用静态内部类

没有限制

（3）在外部类的外面使用静态内部类

没有限制

A：调用静态内部类的静态方法

​	外部类名.静态内部类名.静态方法

B：调用静态内部类的非静态方法

​	外部类名.静态内部类名  对象名 = new 外部类名.静态内部类名();//这里可以使用import简化类名的描述

​	对象名.非静态方法



### 7.7.4  非静态内部类

1、语法格式

```java
【修饰符】 class 外部类{
	【修饰符】 class 内部类{
        
	}
}
```

非静态内部类的修饰符：

（1）四种权限修饰符：public，protected，缺省，private

（2）abstract

（3）final



2、非静态内部类也是一个类

（1）可以继承自己的父类，实现自己的父接口们

（2）成员有限制，只能有：

A：静态的常量

B：非静态的其他成员

（3）它有自己的字节码文件：外部类名$非静态内部类名.class



3、非静态内部类的使用问题

（1）在非静态内部类中使用外部类的成员

没有限制

（2）在外部类中使用非静态内部类

有限制，在外部类的静态成员中不能使用非静态内部类



（3）在外部类的外面使用非静态内部类

调用非静态内部类的非静态方法

```
//第一步：需要外部类的对象
外部类名  out = new 外部类名(【实参列表】);

//第二步：创建非静态内部类的对象
外部类名.非静态内部类名  对象名 = out.new 非静态内部类名(【实参列表】);
//实际开发中，一般不这样写，在外部类中提供一个方法，来获取非静态内部类的对象
外部类名.非静态内部类名  对象名 = out.getInner(【实参列表】);//getInner()方法名称可以自己声明

//第三步：调用方法
对象名.非静态方法
```



### 7.7.5 局部内部类（了解）

1、语法格式

```
【修饰符】 class 外部类{
    【修饰符】 返回值类型  方法签名(【形参列表】){
    	【修饰符】 class 内部类{
            
    	}
    }
}
```

局部内部类的修饰符：

（1）abstract

（2）final

2、局部内部类也是一个类

（1）可以继承自己的父类，实现自己的父接口们

（2）成员有限制，只能有：

A：静态的常量

B：非静态的其他成员

（3）它有自己的字节码文件：外部类名$编号局部内部类名.class



3、局部内部类的使用问题

（1）在局部内部类中使用外部类的成员

有限制，看所在方法是否是静态的



（2）在外部类中使用局部内部类

有作用域限制



（3）在外部类的外面使用局部内部类的类型

绝对不行



（4）在外部类的外面获取局部内部类的对象

可以通过方法的返回值把这个对象返回去，用局部内部类的父类或父接口的变量接收这个对象。



（5）在局部内部类中使用外部类的局部变量

这个局部变量需要有final修饰，即常量。因为这个局部变量的值不能存在栈中。



### 7.7.6 匿名内部类

1、语法格式

```java
new 父类(){
    //方法等
}

new 父类(实参列表){
    //方法等
}

new 接口(){
    //方法等
}
```

（1）匿名内部类必须在声明类的同时就创建对象，它的对象是唯一的

（2）在声明类并创建对象的同时，指明它的父类或父接口，并且通过()中的参数来明确调用父类的哪个构造器。



2、匿名内部类也是一个类

（1）可以继承自己的父类，实现自己的父接口

有的区别，new后面写了父类，就不能写接口，如果写了接口，直接父类就是Object。

（2）成员有限制，只能有：

A：静态的常量

B：非静态的其他成员

（3）它有自己的字节码文件：外部类名$编号.class



3、匿名内部类的使用的三种形式

形式一：多态引用

通过这种形式只能调用重写的父类的方法

```java
父类/父接口 变量 = new 父类/父接口(【实参列表】){
    //方法等
};
变量.方法（【实参列表】）；//不接收返回值

接收返回值的变量 = 变量.方法（【实参列表】）；//接收返回值
```

形式二：用匿名内部类的匿名对象直接调用方法

```java
//没有接收方法的返回值
new 父类/父接口(【实参列表】){
    //方法等
}.方法(【实参列表】);  

//接收方法的返回值
接收返回值的变量 = new 父类/父接口(【实参列表】){
    //方法等
}.方法(【实参列表】);  
```

形式三：用匿名内部类的匿名对象作为方法的实参

```java
xxx.方法(new 父类/父接口(【实参列表】){
    //方法等
});
```

### 7.7.7  内部类的选择

1、思考：静态内部类与非静态成员内部类的选择？

优先考虑静态内部类。

但是当你在成员内部类中如果要用外部类的非静态成员时，那么就不能用静态内部类。



2、思考：什么情况下使用匿名内部类？

（1）当某个类仅在当前代码中使用一次

如果此时单独声明一个.java文件，声明一个有名字的类，就麻烦了。

（2） 这个类不会很复杂，代码比较简洁



# 第八章 枚举与注解

枚举与注解都是JDK1.5之后引入。

可变参数、静态导入也是JDK1.5引入。

## 8.1 枚举

1、当某个类型的对象是固定的有限的几个，而且这个类型的成员不会很复杂，这个时候考虑使用枚举。

2、JDK1.5之前如何实现枚举？

（1）构造器私有化

（2）在类的内部提前创建几个公共的静态的常量对象

3、JDK1.5之后如何实现枚举？

```java
【修饰符】 enum 枚举类名{
    常量对象列表
}
【修饰符】 enum 枚举类名{
    常量对象列表;
    其他成员列表
}
```

（1）常量对象列表必须在枚举类的首行

（2）如果后面有其他成员，在常量对象列表后面必须加;

（3）构造器一定是私有的，哪怕你省略修饰符，也是私有的

（4）枚举类不能在显式继承其他类了，因为它的直接父类固定是java.lang.Enum类

（5）java.lang.Enum类是一个抽象类，它只有唯一的有参构造，说明在枚举类型的构造器中是默认调用Enum类的有参构造的

（6）JDK1.5之后，switch增加了对枚举的支持，switch()中的表达式可以是枚举类型的变量，case后面写枚举常量对象名。



4、几个方法了解一些

（1）String name()：返回常量对象的名称

（2）int ordinal()：返回常量对象的下标

（3）String toString()：默认返回的是常量对象的名称，如果要重写只能手动重写

（4）枚举类型 valueOf(常量对象名)：根据常量对象名获取常量对象

（5）枚举类型[] values()



## 8.2 注解

1、注解：也是一种注释，它由另一段代码来读取它。

2、注解有三个部分组成：

（1）声明

（2）使用

（3）读取

3、系统预定义的三个最基本的注解

（1）@Override

用在：重写父类、父接口的方法上

作用：让编译器帮我们检查这个方法是否满足重写的格式要求

（2）@SuppressWarnings

用在：用在方法、类型、属性、形参、局部变量、构造器等

作用：抑制警告

（3）@Deprecated

用在：方法、类型、属性、构造器等

作用：标识xx已过时

4、文档注释相关的注解

```java
/**

文档注释

*/
```

@author：标记作者

@since：从xx版本开始

@version：当前版本是xx

@see：另请参考

@param：形参

@return：返回值类型

@throws或@exception：标记抛出的异常类型

当我们的源文件写了文档注释，可以用javadoc.exe工具生成对应的API文档。

5、JUnit单元测试相关的几个注解

在使用JUnit之前必须先引入JUnit的library。

@Test：标记某个方法是单元测试的方法，它标记的方法必须在public的类中，其次方法签名必须保证是public，void，()无参。方法名在JUnit4版本之后没有要求。

​	当你不选中其中某个方法时，所有的@Test标记的单元测试方法都会运行，如果只想运行其中一个，那么选中这个方法名，然后右键运行。

@Before/@After：标记在需要在每一个@Test单元测试方法之前/后运行的方法上。它标记的方法必须在public的类中，其次方法签名必须保证是public，void，()无参。方法名在JUnit4版本之后没有要求。

@BeforeClass/@AfterClass：标记在需要在所有的@Test单元测试方法之前/后运行，而且只运行一次。它标记的方法必须在public的类中，其次方法签名必须保证是public  static，void，()无参。方法名在JUnit4版本之后没有要求。

6、自定义注解

```java
@元注解
【修饰符】 @interface 注解名{
    
}

@元注解
【修饰符】 @interface 注解名{
    数据类型  参数名();
    数据类型  参数名() default 默认值;
}
```

7、元注解

@Target：标记某个注解的使用位置。这个位置由ElementType这个枚举类型的10个常量对象来指定。

@Retention：标记某个注解的生命周期。这个生命周期由RetentionPolicy这个枚举类型的3个常量对象来指定。

​	SOURCE,CLASS,RUNTIME

只有RUNTIME才能被反射读取。

@Documented：标记某个注解是否可以被javadoc.exe读取到API中

@Inhertied：标记某个注解是否可以被子类继承

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)
public @interface Override {
}
```

```java
@Target({TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE})
@Retention(RetentionPolicy.SOURCE)
public @interface SuppressWarnings {
    String[] value();
}
```

```java
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value={CONSTRUCTOR, FIELD, LOCAL_VARIABLE, METHOD, PACKAGE, PARAMETER, TYPE})
public @interface Deprecated {
}
```

8、配置参数

```java
@元注解
【修饰符】 @interface 注解名{
    数据类型  参数名();
    数据类型  参数名() default 默认值;
}
```

如果注解声明时声明了配置参数，那么在使用这个注解时，要给配置参数赋值，标准的赋值格式

```java
@注解名(参数名=参数值)
```

当我们配置的名称是value，可以省略“value="

配置参数的类型有要求：8种基本数据类型、String、Class、注解、枚举，以及他们组成的数组。



# 第九章 异常

异常：程序正常运行没问题，但是因为一些其他的因素：用户的非法输入，网络问题等因素导致程序运行不正常，如果发生异常不处理，程序就会中断，如果处理，程序还可能继续。

## 9.1 异常的类型体系结构

1、根类型：java.lang.Throwable

（1）它是所有错误和异常的根父类

（2）只有它或它子类的对象才能被“抛”出

​	A：JVM抛出

​	B：throw抛出

（3）只有它或它子类的类型才能拥有catch()中类型匹配，即才能“抓”



2、Throwable分为

（1）Error：合理的应用程序不应该捕获的严重的错误。

（2）Exception：合理的应用程序应该需要捕获和处理的异常。



面试题：编写一段代码，演示发生StackOverflowError，OutOfMemoryError？

```java
	//StackOverflowError
	public void method(){
		method();
	}
	
	//OutOfMemoryError
	public void largeArray(){
		int[] arr = new int[Integer.MAX_VALUE];
	}
```

3、Exception又分为

（1）编译时异常：

​	一旦某段代码有可能抛出编译时异常，那么编译器就会强制要求我们编写对应的处理代码，否则编译不通过。

（2）运行时异常：RuntimeException或它的子类

​	运行时异常编译器不检测。



4、异常的处理

形式一：

```
try{
    可能发生异常的代码
}catch(异常的类型1 异常名){
    处理异常的代码
}catch(异常的类型2 异常名){
    处理异常的代码
}catch(异常的类型3 异常名){
    处理异常的代码
}
。。。
```

执行的特点：

（1）如果try中没有异常，那么所有的catch都不执行

（2）如果try中有异常，try中发生异常后面的代码就不执行了，然后会抛出一个异常对象，与catch()的异常类型进行匹配，按照顺序进行匹配，如果有一个catch可以匹配，那么就进入这个catch，会执行整个try..catch下面的代码

（3）如果try中有异常，这个异常对象的类型与所有的catch都不匹配，会结束当前方法，往“上”抛，由调用者处理

形式二：

```
try{
    可能发生异常的代码
}catch(异常的类型1 异常名){
    处理异常的代码
}catch(异常的类型2 异常名){
    处理异常的代码
}catch(异常的类型3 异常名){
    处理异常的代码
}
。。。
finally{
    最终块
}
```

执行特点：

（1）try..catch与上面一样

（2）finally中的代码块

A：无论try中是否发生异常

B：无论catch是否可以捕获这个异常

C：无论try和catch中是否有return

finally中的代码都会执行。

唯一有一种情况不会执行，就是try或catch中有System.exit(0)语句

形式三：

```java
try{
    可能发生异常的代码
}finally{
    最终块
}
```

执行特点：如果try中发生异常，就抛出调用者，但是finally会执行。



5、throw

用于手动抛出异常。

```java
throw new 异常的类型(message);
```



6、throws

用于方法签名中，声明这个方法中没有处理xxx异常，一旦发生这些异常，由调用者来处理。

```java
【修饰符】 返回值类型  方法名(【形参列表】)throws 异常列表{
    
}
【修饰符】 abstract 返回值类型  方法名(【形参列表】)throws 异常列表;
```

方法的重写时：

（1）方法名称：必须相同

（2）形参列表：必须相同

（3）返回值类型：

​	基本数据类型和void：必须相同

​	引用数据类型：<=        （规定：子类的类型 < 父类的类型，例如：学生 < 人，学生的范围是人的范围的一部分）

（4）权限修饰符：>=

（5）不能被重写的方法

A：final

B：static

C：private

D：跨包还不能是缺省的

（6）throws的异常类型：<=

​	即子类重写方法抛出的异常，不能大于父类被重写方法抛出的异常

​	如果父类的方法没有抛出编译时异常，子类重写的方法也不能抛出编译时异常。



7、自定义异常

（1）必须继承Throwable或它子类

如果是继承RuntimeException或它的子类，那么就是运行时异常。

如果是继承Exception或它非RuntimeException子类，那就是编译时异常。

（2）建议大家保留两个构造器

A：无参构造

B：异常类型(String message)

（3）自定义异常必须通过throw语句抛出



# 第十章  常用类

## 10.1包装类（重点）

1、Java中一些API或特性只为对象设计，不适用基本数据类型，把这样的基本数据类型的数据用包装类包装起来。

2、包装类：

| 基本数据类型 | 包装类类型 |
| ------------ | ---------- |
| byte         | Byte       |
| short        | Short      |
| int          | Integer    |
| long         | Long       |
| float        | Float      |
| double       | Double     |
| char         | Character  |
| boolean      | Boolean    |
| void         | Void       |

3、一些常量对象或方法

（1）获取某个类型的最大最小值

Integer.MAX_VALUE

Integer.MIN_VALUE

（2）二进制相关的

```java
	@Test
	public void test1(){
		System.out.println(Integer.toBinaryString(10));
		System.out.println(Integer.toOctalString(10));
		System.out.println(Integer.toHexString(10));
	}
	
	@Test
	public void test2(){
		double d1 = 1.1;
		double d2 = 1.1;
		System.out.println(d1==d2);
		System.out.println(Double.doubleToLongBits(d1) == Double.doubleToLongBits(d2));
		System.out.println(Double.compare(d1, d2));
	}
```

（3）和字符串类型转换有关

A：基本数据类型==>字符串

​	基本数据类型的值 + ""

B：字符串==>基本数据类型

​	Integer.parseInt(字符串)，Integer.valueOf(字符串)

​	Double.parseDouble(字符串)，Double.valueOf(字符串)

4、包装类的对象的特点

（1）包装类的对象不可变

一旦修改就会产生新对象

（2）部分包装类对象有缓存对象

Byte：-128~127

Short：-128~127

Integer：-128~127

Long：-128~127

Float和Double：没有缓存对象

Character：0~127

Boolean：true,false

5、装箱与拆箱

装箱：把基本数据类型的值包装为对应包装类的对象。

拆箱：把包装类的对象的数据转为基本数据类型的值。

JDK1.5支持自动装箱与拆箱，自动只支持自己对应类型的转换。



## 10.2 和数学相关的

1、java.lang.Math类

（1）sqrt(x)：求平方根

（2）pow(x,y)：求x的y次方

（3）random()：返回[0,1)范围的小数

（4）max(x,y)：找x,y最大值

​	  min(x,y)：找最小值

（5）round(x)：四舍五入

​         ceil(x)：进一

​         floor(x)：退一

.....



2、java.math包

BigInteger：大整数

BigDecimal：大小数

运算通过方法完成：add(),subtract(),multiply(),divide()....





## 10.3 java.lang.String类（重点）

### 10.3.1 字符串类的特点

1、字符串String类型本身是final声明的，意味着我们不能继承String。

2、字符串的对象也是不可变对象，意味着一旦进行修改，就会产生新对象

> 我们修改了字符串后，如果想要获得新的内容，必须重新接受。
>
> 如果程序中涉及到大量的字符串的修改操作，那么此时的时空消耗比较高。可能需要考虑使用StringBuilder或StringBuffer。

3、String对象内部是用字符数组进行保存的

> JDK1.9之前有一个char[] value数组，JDK1.9之后byte[]数组

4、String类中这个char[] values数组也是final修饰的，意味着这个数组不可变，然后它是private修饰，外部不能直接操作它，String类型提供的所有的方法都是用新对象来表示修改后内容的，所以保证了String对象的不可变。

5、就因为字符串对象设计为不可变，那么所以字符串有常量池来保存很多常量对象

常量池在方法区。

如果细致的划分：

（1）JDK1.6及其之前：方法区

（2）JDK1.7：堆

（3）JDK1.8：元空间



### 10.3.2  字符串对象的比较

1、==：比较是对象的地址

> 只有两个字符串变量都是指向字符串的常量对象时，才会返回true

```java
String str1 = "hello";
String str2 = "hello";
str1 == str2//true
```

2、equals：比较是对象的内容，因为String类型重写equals，区分大小写

只要两个字符串的字符内容相同，就会返回true

```java
String str1 = new String("hello");
String str2 = new String("hello");
str1.equals(strs) //true
```

3、equalsIgnoreCase：比较的是对象的内容，不区分大小写

```java
String str1 = new String("hello");
String str2 = new String("HELLO");
str1.equalsIgnoreCase(strs) //true
```

4、compareTo：String类型重写了Comparable接口的抽象方法，自然排序，按照字符的Unicode编码值进行比较大小的，严格区分大小写

```java
String str1 = "hello";
String str2 = "world";
str1.compareTo(str2) //小于0的值
```

5、compareToIgnoreCase：不区分大小写，其他按照字符的Unicode编码值进行比较大小

```java
String str1 = new String("hello");
String str2 = new String("HELLO");
str1.compareToIgnoreCase(str2)  //等于0
```

6、java.text.Collator可以按照语言环境进行自然语言的排序。



### 10.3.3 空字符串的比较

1、哪些是空字符串

```java
String str1 = "";
String str2 = new String();
String str3 = new String("");
```

空字符串：长度为0

2、如何判断某个字符串是否是空字符串

```java
if("".equals(str))

if(str!=null  && str.isEmpty())

if(str!=null && str.equals(""))

if(str!=null && str.length()==0)
```



### 10.3.4 字符串的对象的个数

1、字符串常量对象

```java
String str1 = "hello";//1个，在常量池中
```

2、字符串的普通对象

```java
String str2 = new String();
//两个对象，一个是常量池中的空字符串对象，一个是堆中的空字符串对象
String str22 = new String("");
//两个对象，一个是常量池中的空字符串对象，一个是堆中的空字符串对象
```

3、字符串的普通对象和常量对象一起

```java
String str3 = new String("hello");
//str3首先指向堆中的一个字符串对象，然后堆中字符串的value数组指向常量池中常量对象的value数组
```

### 10.3.5 字符串拼接结果

原则：

（1）常量+常量：结果是常量池

（2）常量与变量 或 变量与变量：结果是堆

（3）拼接后调用intern方法：结果在常量池

```java
	@Test
	public void test06(){
		String s1 = "hello";
		String s2 = "world";
		String s3 = "helloworld";
		
		String s4 = (s1 + "world").intern();//把拼接的结果放到常量池中
		String s5 = (s1 + s2).intern();
		
		System.out.println(s3 == s4);//true
		System.out.println(s3 == s5);//true
	}
	
	@Test
	public void test05(){
		final String s1 = "hello";
		final String s2 = "world";
		String s3 = "helloworld";
		
		String s4 = s1 + "world";//s4字符串内容也helloworld，s1是常量，"world"常量，常量+ 常量 结果在常量池中
		String s5 = s1 + s2;//s5字符串内容也helloworld，s1和s2都是常量，常量+ 常量 结果在常量池中
		String s6 = "hello" + "world";//常量+ 常量 结果在常量池中，因为编译期间就可以确定结果
		
		System.out.println(s3 == s4);//true
		System.out.println(s3 == s5);//true
		System.out.println(s3 == s6);//true
	}
	
	@Test
	public void test04(){
		String s1 = "hello";
		String s2 = "world";
		String s3 = "helloworld";
		
		String s4 = s1 + "world";//s4字符串内容也helloworld，s1是变量，"world"常量，变量 + 常量的结果在堆中
		String s5 = s1 + s2;//s5字符串内容也helloworld，s1和s2都是变量，变量 + 变量的结果在堆中
		String s6 = "hello" + "world";//常量+ 常量 结果在常量池中，因为编译期间就可以确定结果
		
		System.out.println(s3 == s4);//false
		System.out.println(s3 == s5);//false
		System.out.println(s3 == s6);//true
	}
```



### 10.3.6 字符串的API

1、判断字符串是否为空：boolean isEmpty()

2、求字符串长度：int length()

3、字符串的拼接：String concat()  或 +

4、转大小写：toUpperCase()  和  toLowerCase()

5、比较字符串内容：boolean equals(xx)

​				  boolean equalsIgnoreCase(xx)

6、比较字符串大小：int compareTo(xx)

​					int compareToIgnoreCase(xx)

7、把字符串结果存储到常量池：String intern()

8、把字符串转为字符数组：char[] toCharArray()

9、取出某个下标位置的字符：char charAt(index)

10、把字符数组转为字符串：

​			String(char[] arr)

​			String(char[] arr, int offset, int count)

​			valueOf(char[] arr)

​			valueOf(char[] arr, int offset, int count)

11、把字符串编码为字节数组：

​	byte[] getBytes()：按照平台默认的编码方式进行编码

​	byte[] getBytes(编码方式)：按照指定的编码方式，常见的编码：UTF-8,GBK,GB2312,ISO8859-1等

12、把字节数组解码为字符串

​	String(byte[] bytes)

​	String(byte[] bytes,  编码方式)	

13、字符串的截取：String substring(int beginIndex)

​					String substring(int beginIndex, int endIndex)

14、判断当前字符串是否包含：boolean contains(xx)

15、字符串查找：int indexOf(xx)

​				int lastIndexOf(xx)

16、是否满足某种规则：boolean matches(正则)

17、是否以xx开头：boolean startsWith(xx)

​	是否以xx结尾：boolean endsWith(xx)

18、字符串替换：String replace(xx,yy)

​			      String replaceAll(正则,yy)

​			    String replaceFirst(正则，yy)

19、字符串拆分：String[] split(正则)

20、去掉前后空白符：String trim()



面试题：字符串的length和数组的length有什么不同？

字符串的length()，数组的length属性



## 10.4 可变字符序列（重点）

 可变字符序列：

java.lang.StringBuffer和java.lang.StringBuilder。



字符串：String，不可变

可变字符序列：可变。



StringBuffer和StringBuilder的API：

（1）append(xx)：字符串拼接

（2）insert(index,xx)：在字符序列中插入xx

（3）delete(int start, int end)：删除[start,end)范围的字符序列

（4）deleteCharAt(int index)：删除某个位置的字符

（5）reverse()：字符系列的反转

（6）replace(int start, int end, String str)：替换[start,end)范围的字符序列

（7）substring(int start) ：从[start]截取到最后

（8）substring(int start, int  end)：截取[start, end)字符序列

（9）indexOf(xx)：从左往右查找

（10）lastIndexOf(xx)：从右往左查找

（11）int length()：获取字符序列的长度

（12）setLength(int )：设置字符序列的长度

（13）charAt(index)：获取某个下标的字符

（14）String toString()：把可变字符序列的对象转为字符串类型的对象



StringBuffer和StringBuilder：它俩的API是完全兼容的，但是StringBuffer是线程安全的，StringBuilder线程不安全的，在单线程情况下使用效率更高。



## 10.5 java.lang.Runtime类

Runtime类型的对象是代表当前Java应用程序的JVM的运行时环境。

（1）获取Runtime类的对象：Runtime getRuntime()

（2）long totalMemory() ：获取JVM的总内存

（3）long freeMemory()：获取JVM的空闲内存

（4）void gc()：调用GC

（5）void exit(xx)：退出系统



## 10.6 日期时间API

### 10.6.1 JDK1.8之前

1、java.util.Date

new  Date()：当前系统时间

long  getTime()：返回该日期时间对象距离1970-1-1 0.0.0 0毫秒之间的毫秒值

new Date(long 毫秒)：把该毫秒值换算成日期时间对象

2、java.util.Calendar：

（1）getInstance()：得到Calendar的对象

（2）get(常量)

3、java.text.SimpleDateFormat：日期时间的格式化

y：表示年

M：月

d：天

H： 小时，24小时制

h：小时，12小时制

m：分

s：秒

S：毫秒

E：星期

D：年当中的天数

```java
	@Test
	public void test10() throws ParseException{
		String str = "2019年06月06日 16时03分14秒 545毫秒  星期四 +0800";
		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS毫秒  E Z");
		Date d = sf.parse(str);
		System.out.println(d);
	}
	
	@Test
	public void test9(){
		Date d = new Date();

		SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒 SSS毫秒  E Z");
		//把Date日期转成字符串，按照指定的格式转
		String str = sf.format(d);
		System.out.println(str);
	}
	
	@Test
	public void test8(){
		String[] all = TimeZone.getAvailableIDs();
		for (int i = 0; i < all.length; i++) {
			System.out.println(all[i]);
		}
	}
	
	@Test
	public void test7(){
		TimeZone t = TimeZone.getTimeZone("America/Los_Angeles");
		
		//getInstance(TimeZone zone)
		Calendar c = Calendar.getInstance(t);
		System.out.println(c);
	}
	
	@Test
	public void test6(){
		Calendar c = Calendar.getInstance();
		System.out.println(c);
		
		int year = c.get(Calendar.YEAR);
		System.out.println(year);
		
		int month = c.get(Calendar.MONTH)+1;
		System.out.println(month);
		
		//...
	}
	
	@Test
	public void test5(){
		long time = Long.MAX_VALUE;
		Date d = new Date(time);
		System.out.println(d);
	}
	
	@Test
	public void test4(){
		long time = 1559807047979L;
		Date d = new Date(time);
		System.out.println(d);
	}
	@Test
	public void test3(){
		Date d = new Date();
		long time = d.getTime();
		System.out.println(time);//1559807047979
	}
	
	@Test
	public void test2(){
		long time = System.currentTimeMillis();
		System.out.println(time);//1559806982971
		//当前系统时间距离1970-1-1 0:0:0 0毫秒的时间差，毫秒为单位
	}
	
	@Test
	public void test1(){
		Date d = new Date();
		System.out.println(d);
	}
```

### 10.6.2 JDK1.8之后

java.time及其子包中。

1、LocalDate、LocalTime、LocalDateTime

（1）now()：获取系统日期或时间

（2）of(xxx)：或者指定的日期或时间

（3）运算：运算后得到新对象，需要重新接受

plusXxx()：在当前日期或时间对象上加xx

minusXxx() ：在当前日期或时间对象上减xx

| 方法                                                         | **描述**                                                     |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| now() / now(ZoneId zone)                                     | 静态方法，根据当前时间创建对象/指定时区的对象                |
| of()                                                         | 静态方法，根据指定日期/时间创建对象                          |
| getDayOfMonth()/getDayOfYear()                               | 获得月份天数(1-31) /获得年份天数(1-366)                      |
| getDayOfWeek()                                               | 获得星期几(返回一个 DayOfWeek 枚举值)                        |
| getMonth()                                                   | 获得月份, 返回一个 Month 枚举值                              |
| getMonthValue() / getYear()                                  | 获得月份(1-12) /获得年份                                     |
| getHours()/getMinute()/getSecond()                           | 获得当前对象对应的小时、分钟、秒                             |
| withDayOfMonth()/withDayOfYear()/withMonth()/withYear()      | 将月份天数、年份天数、月份、年份修改为指定的值并返回新的对象 |
| with(TemporalAdjuster  t)                                    | 将当前日期时间设置为校对器指定的日期时间                     |
| plusDays(), plusWeeks(), plusMonths(), plusYears(),plusHours() | 向当前对象添加几天、几周、几个月、几年、几小时               |
| minusMonths() / minusWeeks()/minusDays()/minusYears()/minusHours() | 从当前对象减去几月、几周、几天、几年、几小时                 |
| plus(TemporalAmount t)/minus(TemporalAmount t)               | 添加或减少一个 Duration 或 Period                            |
| isBefore()/isAfter()                                         | 比较两个 LocalDate                                           |
| isLeapYear()                                                 | 判断是否是闰年（在LocalDate类中声明）                        |
| format(DateTimeFormatter  t)                                 | 格式化本地日期、时间，返回一个字符串                         |
| parse(Charsequence text)                                     | 将指定格式的字符串解析为日期、时间                           |

2、DateTimeFormatter：日期时间格式化

该类提供了三种格式化方法：

预定义的标准格式。如：ISO_DATE_TIME;ISO_DATE

本地化相关的格式。如：ofLocalizedDate(FormatStyle.MEDIUM)

自定义的格式。如：ofPattern(“yyyy-MM-dd hh:mm:ss”)

```java
	@Test
	public void test10(){
		LocalDateTime now = LocalDateTime.now();
		
//		DateTimeFormatter df = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG);//2019年6月6日 下午04时40分03秒
		DateTimeFormatter df = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);//19-6-6 下午4:40
		String str = df.format(now);
		System.out.println(str);
	}
	@Test
	public void test9(){
		LocalDateTime now = LocalDateTime.now();
		
		DateTimeFormatter df = DateTimeFormatter.ISO_DATE_TIME;//2019-06-06T16:38:23.756
		String str = df.format(now);
		System.out.println(str);
	}
	
	@Test
	public void test8(){
		LocalDateTime now = LocalDateTime.now();
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒  SSS毫秒  E 是这一年的D天");
		String str = df.format(now);
		System.out.println(str);
	}
	
	@Test
	public void test7(){
		LocalDate now = LocalDate.now();
		LocalDate before = now.minusDays(100);
		System.out.println(before);//2019-02-26
	}
	
	@Test
	public void test06(){
		LocalDate lai = LocalDate.of(2019, 5, 13);
		LocalDate go = lai.plusDays(160);
		System.out.println(go);//2019-10-20
	}
	
	@Test
	public void test05(){
		LocalDate lai = LocalDate.of(2019, 5, 13);
		System.out.println(lai.getDayOfYear());
	}
	
	
	@Test
	public void test04(){
		LocalDate lai = LocalDate.of(2019, 5, 13);
		System.out.println(lai);
	}
	
	@Test
	public void test03(){
		LocalDateTime now = LocalDateTime.now();
		System.out.println(now);
	}
	
	@Test
	public void test02(){
		LocalTime now = LocalTime.now();
		System.out.println(now);
	}
	
	@Test
	public void test01(){
		LocalDate now = LocalDate.now();
		System.out.println(now);
	}
```

3、Instant

提供计算机视图的时间瞬间，不考虑本地化，即获取“本初子午线”的时间。

```java
	@Test
	public void test(){
		Instant i = Instant.now();
		System.out.println(i);
		//2019-09-21T03:20:26.241Z
	}
```



4、Duration和Period

Duration：时间间隔

Period：日期间隔

```java
	@Test
	public void test2(){
		LocalTime now = LocalTime.now();
		LocalTime leave = LocalTime.of(12, 0, 0);
		
		Duration d = Duration.between(now, leave);
		System.out.println(d);
		//PT37M51.75S
	}
	
	@Test
	public void test3(){
		LocalDate today = LocalDate.now();
		LocalDate leave = LocalDate.of(2020, 3, 1);
		
		Period p = Period.between(today, leave);
		System.out.println(p);//P5M9D
	}
```

# 第十一章 多线程

## 11.1 概念

程序（program）：为了完成某个认为或功能而选择一种编程语言编写的一组指令的集合。

软件（software）：程序+程序运行期间需要的其他的素材等，一个软件至少包含一个程序。

进程（process）：一个程序的一次运行。操作系统是以进程为单位分配系统资源（例如：内存），进程之间的内存是独立，进程之间的切换，通信成本比较高。

线程（thread）：一个进程中其中一条执行路径。一个进程至少有一个线程。线程是CPU调度的最小单位。同一个进程中的多个线程是可以共享内存（方法区，堆），但是每一个线程的栈空间是独立的。



## 11.2 SE阶段启动线程的两种方式

1、继承java.lang.Thread类

步骤：

（1）编写线程类继承Thread类

（2）必须重写public void run()方法

（3）创建线程类的对象

（4）调用线程类对象的start()方法启动线程

```java
class MyThread extends Thread{
    public void run(){
        //....
    }
}
class Test{
    public static void main(String[] args){
        MyThread t = new MyThread();
        t.start();
    }
}
```

```java
//使用匿名内部类的方式启动一个线程
new Thread(){
    public void run(){
        //...
    }
}.start();
```



2、实现java.lang.Runnable接口

步骤：

（1）编程线程类实现Runnable接口

（2）必须重写public void run()方法

（3）创建Runnable接口的实现类的对象

（4）创建Thread类的对象，并且把Runnable接口的实现类的对象，作为Thread对象target

（5）调用Thread类的对象的start()启动线程

```java
class MyRunnable implements Runnable{
    public void run(){
        //...
    }
}
class Test{
    public static void main(String[] args){
        MyRunnable my = new MyRunnable();
        Thread t = new Thread(my);
        t.start();
    }
}
```

```java
//匿名内部类的方式实现
new Thread(new Runnable(){
    public void run(){
        //...
    }
}).start();

或
Runnable r = new Runnable(){
    public void run(){
        //...
    }
};
Thread t = new Thread(r);
t.start();
```

## 11.3 Thread类的常用方法

（1）构造器

new Thread()

new Thread(String name)

new Thread(Runnable target)

new Thread(Runnable target, String name)

（2）其他方法

void start()：启动线程

void run()：编写线程体用的，子类必须重写

static void sleep(时间)throws InterruptedException：线程休眠

static void yield()：暂停当前线程

void join()：线程加塞，被加塞线程必须等到加塞的线程执行完才能继续

void join(时间)：被加塞线程要等xx时间之后才能有机会被执行

void setName(xx)：设置线程名称

String getName()：获取线程名称

void setPriority(等级)：设置线程优先级，优先级的等级范围是[1,10]。Thread类中有三个常量：MAX_PRIORITY，MIN_PRIORITY，NORM_PRIORITY。

int getPriority()：获取线程优先级

void interrupt()：中断线程的休眠等

void setDaemon(true)：设置某个线程为守护线程。当其他被守护线程结束了，守护线程自己就会自动结束。

static Thread currentThread()：获取正在执行当前代码的线程对象



## 11.4 关键字volatile

volatile：修饰符，修饰成员变量。当这个成员变量被频繁的访问时，Java编译器会做优化，会在寄存器中缓存这个变量的值，而不去及时主存去读取，或者说主存的修改和缓存中的值没有及时同步，这个时候我们变量值已经修改了，但是程序可能仍然用之前缓存的值进行计算和判断，导致效果有问题。加了volatile，就会使得每一次使用这个变量的值的时候，都与主存保持一致。



## 11.5 线程安全

1、什么情况下会发生线程安全问题？或者说如何判断我们的某段代码是否存在线程安全问题？

（1）是否存在多个线程同时运行某段代码模板

（2）这几个线程是否有使用“共享”数据，这个共享数据可能是访问和修改同一个变量，访问和修改同一个文件，访问或修改同一个数据库的同一条记录

（3）多条语句操作这个共享数据。



2、SE阶段就是使用同步：synchronized

3、形式一：同步代码块

```java
synchronized(锁对象/监视器对象){
    需要同步的代码
}
```

锁对象：

（1）类型：没有限制，任意引用数据类型的对象都可以作为锁对象

（2）但是，必须保证使用共享数据的多个线程必须使用同一个“锁对象”



同步：协作的意味，使用同一个“锁”对象多个线程，同时只能有一个线程“持”有锁对象，只有持有锁对象的线程才能执行它的线程体，没有获取锁对象的线程只能等着。



4、形式二：同步方法

```java
【其他修饰符】 synchronized 返回值类型 方法名(【形参列表】)【throws 异常列表】{
    
}
```

锁对象：

（1）非静态方法：this

（2）静态方法：当前类的Class对象



## 11.6 线程通信：等待与唤醒机制

生产者与消费者问题（英语：Producer-consumer problem），也称有限缓冲问题（英语：Bounded-buffer problem），是一个多线程同步问题的经典案例。该问题描述了两个（多个）共享固定大小缓冲区的线程——即所谓的“生产者”和“消费者”——在实际运行时会发生的问题。生产者的主要作用是生成一定量的数据放到缓冲区中，然后重复此过程。与此同时，消费者也在缓冲区消耗这些数据。该问题的关键就是要保证生产者不会在缓冲区满时加入数据，消费者也不会在缓冲区中空时消耗数据。

生产者与消费者问题中其实隐含了两个问题：

* 线程安全问题：因为生产者与消费者共享数据缓冲区，不过这个问题可以使用同步解决。
* 线程的协调工作问题：
  * 要解决该问题，就必须让生产者线程在缓冲区满时等待(wait)，暂停进入阻塞状态，等到下次消费者消耗了缓冲区中的数据的时候，通知(notify)正在等待的线程恢复到就绪状态，重新开始往缓冲区添加数据。同样，也可以让消费者线程在缓冲区空时进入等待(wait)，暂停进入阻塞状态，等到生产者往缓冲区添加数据之后，再通知(notify)正在等待的线程恢复到就绪状态。通过这样的等待与唤醒通信机制来解决此类问题。

**调用wait和notify方法需要注意的细节**

1. wait方法与notify方法必须要由同一个锁对象调用。因为：对应的锁对象可以通过notify唤醒使用同一个锁对象调用的wait方法后的线程。

2. wait方法与notify方法是属于Object类的方法的。因为：锁对象可以是任意对象，而任意对象的所属类都是继承了Object类的。

3. wait方法与notify方法必须要在同步代码块或者是同步方法中使用。因为：必须要通过锁对象调用这2个方法。

   

### 11.6.1 一个生产者与一个消费者

```java
public class TestOneProducerOneConsumer {
	public static void main(String[] args) {
		WareHouse house = new WareHouse();//仓库
		Worker w = new Worker("xx工人" ,house);//工人，并告知工人仓库对象地址
		Saler s = new Saler("xx销售",house);
		w.start();
		s.start();
	}
}
//Worker：工人
class Worker extends Thread{
	private WareHouse house;
	
	public Worker(String name, WareHouse house) {
		super(name);
		this.house = house;
	}

	public Worker(WareHouse house) {
		super();
		this.house = house;
	}

	public void run(){
		while(true){
			house.add();
		}
	}
}
//Saler：销售
class Saler extends Thread{
	private WareHouse house;
	
	public Saler(String name, WareHouse house) {
		super(name);
		this.house = house;
	}

	public Saler(WareHouse house) {
		super();
		this.house = house;
	}

	public void run(){
		while(true){
			house.reduce();
		}
	}
}
//WareHouse：仓库
class WareHouse{
	private static final int MAX_SIZE = 5;
	private int total;//仓库中商品的数量
	
	//仓库的商品数量增加
	public synchronized void add(){
		if(total>=MAX_SIZE){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		total++;
		//因为这个方法由生产者线程调用，那么获取到线程对象就是生产者线程
		Thread workThread = Thread.currentThread();
		System.out.println(workThread.getName() + "生产了新的产品，数量：" + total);
		this.notify();
	}
	
	//仓库的商品数量减少
	public synchronized void reduce(){
		if(total<=0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		total--;
		//因为这个方法由消费者线程调用，那么获取到线程对象就是消费者线程
		Thread saleThread = Thread.currentThread();
		System.out.println(saleThread.getName() + "消耗了产品，数量：" + total);
		this.notify();
	}
}
```

### 11.6.2 多个生产者与多个消费者

```java
public class TestManyProducerManyConsumer {
	public static void main(String[] args) {
		WareHouse house = new WareHouse();//仓库
		Worker w1 = new Worker("工人1" ,house);//工人，并告知工人仓库对象地址
		Worker w2 = new Worker("工人2" ,house);//工人，并告知工人仓库对象地址
		Saler s1 = new Saler("销售1",house);
		Saler s2 = new Saler("销售2",house);
		w1.start();
		w2.start();
		s1.start();
		s2.start();
	}
}
//Worker：工人
class Worker extends Thread{
	private WareHouse house;
	
	public Worker(String name, WareHouse house) {
		super(name);
		this.house = house;
	}

	public Worker(WareHouse house) {
		super();
		this.house = house;
	}

	public void run(){
		while(true){
			house.add();
		}
	}
}
//Saler：销售
class Saler extends Thread{
	private WareHouse house;
	
	public Saler(String name, WareHouse house) {
		super(name);
		this.house = house;
	}

	public Saler(WareHouse house) {
		super();
		this.house = house;
	}

	public void run(){
		while(true){
			house.reduce();
		}
	}
}
//WareHouse：仓库
class WareHouse{
	private static final int MAX_SIZE = 5;
	private int total;//仓库中商品的数量
	
	//仓库的商品数量增加
	public synchronized void add(){
		while(total>=MAX_SIZE){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		total++;
		//因为这个方法由生产者线程调用，那么获取到线程对象就是生产者线程
		Thread workThread = Thread.currentThread();
		System.out.println(workThread.getName() + "生产了新的产品，数量：" + total);
		this.notifyAll();
	}
	
	//仓库的商品数量减少
	public synchronized void reduce(){
		while(total<=0){
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		total--;
		//因为这个方法由消费者线程调用，那么获取到线程对象就是消费者线程
		Thread saleThread = Thread.currentThread();
		System.out.println(saleThread.getName() + "消耗了产品，数量：" + total);
		this.notifyAll();
	}
}
```



## 11.7 线程的生命周期

简单来说，线程的生命周期有五种状态：新建（New）、就绪（Runnable）、运行（Running）、阻塞（Blocked）、死亡（Dead）。CPU需要在多条线程之间切换，于是线程状态会多次在运行、阻塞、就绪之间切换。

![](day18_note/imgs/1563282798989.png)

不过在java.lang.Thread.State的枚举类中这样定义：

```java
    public enum State {
        NEW,
        RUNNABLE,
        BLOCKED,
        WAITING,
        TIMED_WAITING,
        TERMINATED;
    }
```

* 首先它没有区分：就绪和运行状态，因为对于Java对象来说，只能标记为可运行，至于什么时候运行，不是JVM来控制的了，是OS来进行调度的，而且时间非常短暂，因此对于Java对象的状态来说，无法区分。只能我们人为的进行想象和理解。
* 其次根据Thread.State的定义，阻塞状态是分为三种的：BLOCKED、WAITING、TIMED_WAITING。

![1563381997032](day18_note/imgs/1563381997032.png)



## 11.8 面试题

1、sleep和wait的区别？

（1）sleep不释放锁，wait释放锁

（2）sleep是Thread类的静态方法，wait是Object类的方法，因为wait方法必须有锁对象调用，而锁对象可能是任意类型的对象，所以只能声明在Object中

（3）sleep一般指定休眠时间，到了恢复到就绪状态。wait可以设置时间也可以等着被notify唤醒



2、继承Thread和实现Runnable两种创建线程的方式的区别？

（1）选择共享资源

继承Thread：选择共享资源时，可能需要使用静态等方式，或者要考虑共享同一个对象的方式

实现Runnable：选择共享资源时，只要是同一个Runnable的实现类对象即可。

（2）继承Thread，选择锁对象时，this对象不一定是对的，this可能不是同一个锁对象，可以考虑当前类的Class对象，或者是共享的资源对象。

实现Runnable，选择锁对象时，this对象基本上没问题。

（3）继承Thread有单继承的限制

实现Runnable，没有单实现的限制



# 第十二章 泛型

## 12.1 泛型的相关的概念

1、泛型：参数化类型

<T>，<E>，<K,V>：泛型的形参，类型形参，类型变量

<String>，<Integer>：泛型的实参，类型实参，参数化的类型

> 形参：
>
> （1）方法前面中的形参：数据形参，代表某个未知的数据，例如：int max(int a, int b)
>
> （2）泛型的形参：类型形参，代表某个未知的类型，例如：Comparable<T>
>
> 实参：
>
> （1）调用方法时，在()中传入的参数：数据实参，代表某个具体的数据值，例如：max(3,5)
>
> （2）泛型的实参：类型实参，代表某个具体的类型，例如：<String>



2、使用泛型的好处？

（1）类型的检查

（2）避免使用者强制类型转换

```java
class Employee implements Comparable{
    private Stirng name;
    private double salary;
    
    //...
    
    public int compareTo(Object obj){
        Employee other = (Employee)obj;
        return Double.compare(this.salary, other.salary);
    }
}
```

```java
class MyArrayList{
    private Object[] arr;
    //.....
    public Object get(int index){
        return arr[index];
    }
}
class TestMyArrayList{
    public static void main(String[] args){
        MyArrayList my = new MyArrayList();
        my.add("hello");
        my.add(10);//无法阻止添加非String类型
        
        Object obj = my.get(0);
        String str = (String)obj;//又需要强制类型转换
    }
}
```

```java
class MyArrayList<E>{
    private Object[] arr;
    //.....
    public E get(int index){
        return (E)arr[index];
    }
}
class TestMyArrayList{
    public static void main(String[] args){
        MyArrayList<String> my = new MyArrayList<>();
        my.add("hello");
       // my.add(10);//可以阻止添加非String类型
        
        String obj = my.get(0);//不需要强制类型转换
    }
}
```

## 12.2 泛型的形式

### 12.1.1 泛型类与泛型接口

1、语法格式

```java
【修饰符】 class/interface 类名/接口名<泛型类型列表>{
    
}
```

2、<泛型类型列表>

（1）一般使用单个的大写字母，例如：<T>，<E>，<K,V>

（2）可以有多个，例如：<K,V>

（3）还可以指定上限

```java
<T extends 上限1 & 上限2 ....>
```

A：如果某个泛型没有指定上限，默认它的上限就是Object，代表所有引用数据类型都可以

B：如果有多个上限，那么其中的类只能有一个，并且在最左边，即类靠左接口靠右，如果有上限，那么这个T类型只能是<=所有的上限



3、使用泛型类与泛型接口

（1）创建泛型类的对象，可以指定具体的类型

```java
泛型类<具体的类型> 变量名 = new 泛型类<具体的类型>(...);

//JDK1.7之后，右边<>中的类型可以省略
泛型类<具体的类型> 变量名 = new 泛型类<>(...);
```

（2）继承泛型类，也可以指定具体的类型

```java
public class 泛型父类<T>{
}

//子类就不是泛型类
public class 子类 extends 泛型父类<具体的类型>{
    
}
```

如果子类在继承泛型父类时，仍然无法确定泛型的具体类型，又不想泛型被擦除，可以保留泛型

```java
public class 泛型父类<T>{
}

//这个子类仍然是泛型类
public class 泛型子类<T> extends 泛型父类<T>{
    
}
```

（3）实现泛型接口时，可以指定具体的类型

```java
public interface 泛型接口<T>{
    
}

public class 实现类 implements 泛型接口<具体的类型>{
    
}
```

如果在实现接口的时候，仍然无法确定泛型的具体类型，又不想泛型被擦除，可以保留泛型

```java
public interface 泛型接口<T>{
    
}

//实现类仍然是泛型类
public class 实现类<T> implements 泛型接口<T>{
    
}
```



4、泛型的擦除

在使用泛型类和泛型接口时，没有指定具体的类型，泛型被擦除。泛型的类型就按照第一个上限处理。

（1）如果<T extends 上限1 & 上限2 。。。>，有上限，按照上限1处理

（2）如果<T>没有上限，那么就是按照Object处理，因为它的默认上限就是Object

```java
//创建对象
泛型类 变量名 = new 泛型类(...);

//继承时
public class 泛型父类<T>{
}

public class 子类 extends 泛型父类{
    
}

//实现接口时
public interface 泛型接口<T>{
    
}
public class 实现类 implements 泛型接口{
    
}
```

5、强调

给泛型指定具体类型时，必须是引用数据类型，不能是基本数据类型。

### 12.2.2 泛型方法

1、语法格式

```java
【修饰符】 <泛型类型列表> 返回值类型 方法名(【形参列表】)【throws 异常列表】{
    
}
```

此处<泛型类型列表>的要求和上面一样。



2、使用泛型方法时，<泛型类型列表>的类型由调用方法时实参的类型来自动确定。



## 12.3 通配符

通配符是用于当我们在使用某个泛型类或泛型接口时，表示泛型实参的范围。不能用于声明一个泛型类或泛型接口，泛型方法的<泛型类型列表>。



1、<?>：代表任意引用数据类型

2、<? extends 上限>：代表<=上限的引用类型

3、<? super 下限>：代表>=下限的引用类型



## 12.4 问题

1、不支持这样的多态

```java
泛型类<Object> 变量 = new 泛型列<String>();//错误的
```

2、无法创建泛型数组

```
T[] arr = new T[长度];//错误的
```



顺便说一下：

```java
Object[] arr = new Object[4];
String[] strings = (String[])arr;//错误的

Object[] arr = new String[4];
String[] strings = (String[])arr;//正确的
```



# 第十三章 集合

## 13.1  数据结构

逻辑结构：

（1）同属于一个集合的松散的结构：元素之间没有关系

（2）线性结构：一对一的关系

（3）树型结构：一对多的关系

（4）图型结构：多对多的关系

物理结构：数组的顺序结构、链式结构、索引结构、哈希结构结构

在JAVA的核心类库中，提供了很多已经实现好的数据结构，主要分为：Collection系列、Map系列



## 13.2 Collection接口

Collection系列：存储一组对象。

Collection接口：是Collection系列的根接口。它没有直接的实现类，它是通过更具体的子接口的实现类来完成数据结构的设计。

![1569633278693](imgs/1569633278693.png)

Collection接口的API：

（1）添加

add(E e)：添加一个对象

addAll(Collection c)：添加多个对象

（2）删除

remove(Object ojb)：删除一个对象

removeAll(Collection c)：删除多个对象

clear()：清空集合

（3）查询

boolean contains(Object obj)：是否包含某个

boolean containsAll(Collection c)：是否包含多个对象

boolean isEmpty()：是否为空结合

（4）其他方法

int size()：获取有效元素的个数

retainAll(Collection c)：取交集

（5）和遍历相关

Object[] toArray()

Iterator iterator()



## 13.3 Iterable和Iterator接口

java.lang.Iterable：实现这个接口，才能称为foreach遍历的目标。它包含：Iterator iterator()

java.util.Iterator：迭代器的统一接口，它包含三个抽象方法：

（1）boolean hasNext()

（2）E next()

（3）void remove()



Collection接口的remove()和Iterator接口的remove()的区别？

（1）Collection接口的remove()用于直接删除目标

​         Iterator接口的remove()可以用于根据元素是否某个条件删除

（2）在用Iterator接口遍历过程中，只能使用Iterator接口的remove()方法，如果你在Iterator接口遍历过程中调用了Collection接口的remove()，可能会报ConcurrentModificationException。



Iterator接口的代码结构：

```java
Iterator<元素的数据类型> iter = Collection系列集合对象.iterator();
while(iter.hasNext()){
    元素的数据类型 element = iter.next();
    //....
}
```

使用foreach遍历的代码结构：

```java
for(元素的数据类型  element :  Collection系列集合/数组){
    //...
}
```



## 13.4 List接口

1、List系列集合：（1）有序的（2）可重复的

2、List接口在Collection接口的基础上增加了一些API：

（1）添加

add(int index,E e)：添加一个对象

addAll(int index, Collection c)：添加多个对象

（2）删除

remove(int index)：删除一个对象

（3）查询

E get(int index)：获取index位置的元素

int indexOf(Object obj)：从前往后找

int lastIndexOf(Object obj)：从后往前找

（4）修改

set(int index ,Object value)：修改替换

（5）其他方法

List subList(int start, int d)：截取

（6）和遍历相关

ListIterator listIterator()

ListIterator listIterator(int index)



3、ListIterator：是Iterator的子接口，增加了几个方法：

（1）boolean hasPrevious()：是否前面还有元素

（2）E previous()：取出前一个

（3）int nextIndex()：获取下一个元素的下标

（4）int previousIndex()：获取前一个元素的下标

（5）add(Object obj)：遍历过程中添加

（6）set(Object obj)：遍历过程中替换



4、List接口的实现类们

（1）Vector：底层实现是数组结构，我们称为动态数组

（2）ArrayList：底层实现是数组结构，我们称为动态数组

（3）LinkedList：底层实现是双向链表，

​	实现了List、Deque、Queue等，

​	在逻辑结构可以作为：栈、队列、链表等线性结构使用，通过调用一些特殊的方法来实现的。

​	栈：先进后出、后进先出

​	队列：先进先出



（4）Stack：它是Vector的子类，在Vector的基础上增加了几个方法，使得可以呈现先进后出的特征。（pop,push,peek等）



5、Vector和ArrayList的区别：

（1）Vector是线程安全，它的操作方法有synchronized修饰

​       ArrayList是线程不安全的

（2）Vector是扩容2倍，也可以根据指定的增量扩容

​	ArrayList是扩容1.5倍

（3）Vector初始化为10

​	ArrayList在JDK1.7之前也是10，JDK1.8之后，在创建ArrayList时先初始化为一个默认的空数组的常量，当第一次添加元素时，初始化为10.

（4）Vector支持旧版的迭代器Enumeration，也支持新版的Iterator和foreach

 ArrayList只支持新版的Iterator和foreach

### 13.4.5 源码分析

#### （1）Vector源码分析

```java
    public Vector() {
        this(10);//指定初始容量initialCapacity为10
    }
	public Vector(int initialCapacity) {
        this(initialCapacity, 0);//指定capacityIncrement增量为0
    }
    public Vector(int initialCapacity, int capacityIncrement增量为0) {
        super();
        //判断了形参初始容量initialCapacity的合法性
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        //创建了一个Object[]类型的数组
        this.elementData = new Object[initialCapacity];//默认是10
        //增量，默认是0，如果是0，后面就按照2倍增加，如果不是0，后面就按照你指定的增量进行增量
        this.capacityIncrement = capacityIncrement;
    }
```

```java
//synchronized意味着线程安全的   
	public synchronized boolean add(E e) {
        modCount++;
    	//看是否需要扩容
        ensureCapacityHelper(elementCount + 1);
    	//把新的元素存入[elementCount]，存入后，elementCount元素的个数增1
        elementData[elementCount++] = e;
        return true;
    }

    private void ensureCapacityHelper(int minCapacity) {
        // overflow-conscious code
        //看是否超过了当前数组的容量
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);//扩容
    }
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;//获取目前数组的长度
        //如果capacityIncrement增量是0，新容量 = oldCapacity的2倍
        //如果capacityIncrement增量是不是0，新容量 = oldCapacity + capacityIncrement增量;
        int newCapacity = oldCapacity + ((capacityIncrement > 0) ?
                                         capacityIncrement : oldCapacity);
        
        //如果按照上面计算的新容量还不够，就按照你指定的需要的最小容量来扩容minCapacity
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        
        //如果新容量超过了最大数组限制，那么单独处理
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        
        //把旧数组中的数据复制到新数组中，新数组的长度为newCapacity
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

```java
    public boolean remove(Object o) {
        return removeElement(o);
    }
    public synchronized boolean removeElement(Object obj) {
        modCount++;
        //查找obj在当前Vector中的下标
        int i = indexOf(obj);
        //如果i>=0，说明存在，删除[i]位置的元素
        if (i >= 0) {
            removeElementAt(i);
            return true;
        }
        return false;
    }
    public int indexOf(Object o) {
        return indexOf(o, 0);
    }
    public synchronized int indexOf(Object o, int index) {
        if (o == null) {//要查找的元素是null值
            for (int i = index ; i < elementCount ; i++)
                if (elementData[i]==null)//如果是null值，用==null判断
                    return i;
        } else {//要查找的元素是非null值
            for (int i = index ; i < elementCount ; i++)
                if (o.equals(elementData[i]))//如果是非null值，用equals判断
                    return i;
        }
        return -1;
    }
    public synchronized void removeElementAt(int index) {
        modCount++;
        //判断下标的合法性
        if (index >= elementCount) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " +
                                                     elementCount);
        }
        else if (index < 0) {
            throw new ArrayIndexOutOfBoundsException(index);
        }
        
        //j是要移动的元素的个数
        int j = elementCount - index - 1;
        //如果需要移动元素，就调用System.arraycopy进行移动
        if (j > 0) {
            //把index+1位置以及后面的元素往前移动
            //index+1的位置的元素移动到index位置，依次类推
            //一共移动j个
            System.arraycopy(elementData, index + 1, elementData, index, j);
        }
        //元素的总个数减少
        elementCount--;
        //将elementData[elementCount]这个位置置空，用来添加新元素，位置的元素等着被GC回收
        elementData[elementCount] = null; /* to let gc do its work */
    }
```

#### （2）ArrayList源码分析

JDK1.6：

```java
    public ArrayList() {
		this(10);//指定初始容量为10
    }
    public ArrayList(int initialCapacity) {
		super();
        //检查初始容量的合法性
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: "+
                                               initialCapacity);
        //数组初始化为长度为initialCapacity的数组
		this.elementData = new Object[initialCapacity];
    }
```

JDK1.7

```java
    private static final int DEFAULT_CAPACITY = 10;//默认初始容量10
	private static final Object[] EMPTY_ELEMENTDATA = {};
	public ArrayList() {
        super();
        this.elementData = EMPTY_ELEMENTDATA;//数组初始化为一个空数组
    }
    public boolean add(E e) {
        //查看当前数组是否够多存一个元素
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == EMPTY_ELEMENTDATA) {//如果当前数组还是空数组
            //minCapacity按照 默认初始容量和minCapacity中的的最大值处理
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
		//看是否需要扩容处理
        ensureExplicitCapacity(minCapacity);
    }
	//...
```

JDK1.8

```java
private static final int DEFAULT_CAPACITY = 10;
private static final Object[] EMPTY_ELEMENTDATA = {};
private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;//初始化为空数组
    }
    public boolean add(E e) {
        //查看当前数组是否够多存一个元素
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        
        //存入新元素到[size]位置，然后size自增1
        elementData[size++] = e;
        return true;
    }
    private void ensureCapacityInternal(int minCapacity) {
        //如果当前数组还是空数组
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            //那么minCapacity取DEFAULT_CAPACITY与minCapacity的最大值
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }
		//查看是否需要扩容
        ensureExplicitCapacity(minCapacity);
    }
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;//修改次数加1

        // 如果需要的最小容量  比  当前数组的长度  大，即当前数组不够存，就扩容
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;//当前数组容量
        int newCapacity = oldCapacity + (oldCapacity >> 1);//新数组容量是旧数组容量的1.5倍
        //看旧数组的1.5倍是否够
        if (newCapacity - minCapacity < 0)
            newCapacity = minCapacity;
        //看旧数组的1.5倍是否超过最大数组限制
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        
        //复制一个新数组
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
```

```java
    public boolean remove(Object o) {
        //先找到o在当前ArrayList的数组中的下标
        //分o是否为空两种情况讨论
        if (o == null) {
            for (int index = 0; index < size; index++)
                if (elementData[index] == null) {//null值用==比较
                    fastRemove(index);
                    return true;
                }
        } else {
            for (int index = 0; index < size; index++)
                if (o.equals(elementData[index])) {//非null值用equals比较
                    fastRemove(index);
                    return true;
                }
        }
        return false;
    }
    private void fastRemove(int index) {
        modCount++;//修改次数加1
        //需要移动的元素个数
        int numMoved = size - index - 1;
        
        //如果需要移动元素，就用System.arraycopy移动元素
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        
        //将elementData[size-1]位置置空，让GC回收空间，元素个数减少
        elementData[--size] = null; // clear to let GC do its work
    }
```

```java
    public E remove(int index) {
        rangeCheck(index);//检验index是否合法

        modCount++;//修改次数加1
        
        //取出[index]位置的元素，[index]位置的元素就是要被删除的元素，用于最后返回被删除的元素
        E oldValue = elementData(index);
        
		//需要移动的元素个数
        int numMoved = size - index - 1;
        
        //如果需要移动元素，就用System.arraycopy移动元素
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        //将elementData[size-1]位置置空，让GC回收空间，元素个数减少
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
```

```java
    public E set(int index, E element) {
        rangeCheck(index);//检验index是否合法

        //取出[index]位置的元素，[index]位置的元素就是要被替换的元素，用于最后返回被替换的元素
        E oldValue = elementData(index);
        //用element替换[index]位置的元素
        elementData[index] = element;
        return oldValue;
    }
    public E get(int index) {
        rangeCheck(index);//检验index是否合法

        return elementData(index);//返回[index]位置的元素
    }
```

```java
    public int indexOf(Object o) {
        //分为o是否为空两种情况
        if (o == null) {
            //从前往后找
            for (int i = 0; i < size; i++)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
    public int lastIndexOf(Object o) {
         //分为o是否为空两种情况
        if (o == null) {
            //从后往前找
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
```

#### （3）LinkedList源码分析

```java
int size = 0;
Node<E> first;//记录第一个结点的位置
Node<E> last;//记录最后一个结点的位置

    private static class Node<E> {
        E item;//元素数据
        Node<E> next;//下一个结点
        Node<E> prev;//前一个结点

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```

```java
    public boolean add(E e) {
        linkLast(e);//默认把新元素链接到链表尾部
        return true;
    }
    void linkLast(E e) {
        final Node<E> l = last;//用l 记录原来的最后一个结点
        
        //创建新结点
        final Node<E> newNode = new Node<>(l, e, null);
        //现在的新结点是最后一个结点了
        last = newNode;
        
        //如果l==null，说明原来的链表是空的
        if (l == null)
            //那么新结点同时也是第一个结点
            first = newNode;
        else
            //否则把新结点链接到原来的最后一个结点的next中
            l.next = newNode;
        //元素个数增加
        size++;
        //修改次数增加
        modCount++;
    }
```

```java
    public boolean remove(Object o) {
        //分o是否为空两种情况
        if (o == null) {
            //找到o对应的结点x
            for (Node<E> x = first; x != null; x = x.next) {
                if (x.item == null) {
                    unlink(x);//删除x结点
                    return true;
                }
            }
        } else {
            //找到o对应的结点x
            for (Node<E> x = first; x != null; x = x.next) {
                if (o.equals(x.item)) {
                    unlink(x);//删除x结点
                    return true;
                }
            }
        }
        return false;
    }
    E unlink(Node<E> x) {//x是要被删除的结点
        // assert x != null;
        final E element = x.item;//被删除结点的数据
        final Node<E> next = x.next;//被删除结点的下一个结点
        final Node<E> prev = x.prev;//被删除结点的上一个结点

        //如果被删除结点的前面没有结点，说明被删除结点是第一个结点
        if (prev == null) {
            //那么被删除结点的下一个结点变为第一个结点
            first = next;
        } else {//被删除结点不是第一个结点
            //被删除结点的上一个结点的next指向被删除结点的下一个结点
            prev.next = next;
            //断开被删除结点与上一个结点的链接
            x.prev = null;//使得GC回收
        }

        //如果被删除结点的后面没有结点，说明被删除结点是最后一个结点
        if (next == null) {
            //那么被删除结点的上一个结点变为最后一个结点
            last = prev;
        } else {//被删除结点不是最后一个结点
            //被删除结点的下一个结点的prev执行被删除结点的上一个结点
            next.prev = prev;
            //断开被删除结点与下一个结点的连接
            x.next = null;//使得GC回收
        }
		//把被删除结点的数据也置空，使得GC回收
        x.item = null;
        //元素个数减少
        size--;
        //修改次数增加
        modCount++;
        //返回被删除结点的数据
        return element;
    }
```



## 13.5  Set接口

1、Set系列的集合：元素不可重复

2、它的实现类们：

（1）HashSet：无序

​	内部实现是HashMap

（2）TreeSet：有大小顺序

​	A：new TreeSet()：要求元素实现Comparable接口

​	B：new TreeSet(Comparator)：单独指定Comparator的比较器对象

​	内部实现：TreeMap

（3）LinkedHashSet：有序，按照元素的添加顺序，它是HashSet的子类。

​	内部实现：LinkedHashMap

3、如何区分元素不可重复？

（1）HashSet和LinkedHashSet：依赖与元素的hashCode()和equals()方法

（2）TreeSet：依赖于Comparable接口compareTo()方法或Comparator接口的compare(x,x)



4、添加到set中的元素是单个对象，而内部实现是map，那么如何构建键值对？

以HashSet为例：

````java
    private static final Object PRESENT = new Object();
	public HashSet() {
        map = new HashMap<>();
    }
    public boolean add(E e) {
        return map.put(e, PRESENT)==null;
    }
````



## 13.6 Map

### 13.6.1 Map概述

用来存储键值对，映射关系的集合。所有的Map的key都不能重复，而且所有的key一旦添加到map中就不要修改。

键值对、映射关系的类型：Map.Entry类型

```
Entry接口是Map接口的内部接口。所有的Map的键值对的类型都实现了这个接口。
HashMap中的映射关系，是有一个内部类来实现Entry的接口，JDK1.7是一个叫做Entry的内部类实现Entry接口。
JDK1.8是一个叫做Node的内部类实现Entry接口，TreeNode又继承了Node类型。
TreeMap中的映射关系，是有一个内部类Entry来实现Entry的接口
```

### 13.6.2 API

（1）put(Object key, Object value)：添加一对映射关系

（2）putAll(Map m)：添加多对映射关系

（3）clear()：清空map

（4）remove(Object key)：根据key删除一对

（5）int size()：获取有效元素的对数

（6）containsKey(Object key)：是否包含某个key

（7）containsValue(Object value)：是否包含某个value

（8）Object  get(Object key)：根据key获取value

（9）遍历相关的几个方法

Collection  values()：获取所有的value进行遍历

Set keySet()：获取所有key进行遍历

Set entrySet()：获取所有映射关系进行遍历

```java
HashMap<key类型，value类型> map = new HashMap<>();

map.put(key,value);

//遍历所有的value
Collection<value的类型>  values = map.values();
for(value的类型  value : values){
    //...
}

//遍历所有的key
Set<key类型> keys = map.keySet();
Iterator<key类型> iter = keys.iterator();
while(iter.hasNext()){
    key的类型 key = iter.next();
    ///...
}

//遍历所有映射关系
Set<Map.Entry<key的类型,value的类型>> entrys = map.entrySet();
for(Map.Entry<key的类型,value的类型> entry : entrys){
    key的类型 key = entry.getKey();
    value的类型 value = entry.getValue();
    //...
}
```



### 13.6.3 Map的实现类们的区别

（1）HashMap：哈希表

​	依据key的hashCode()和equals()来保证key是否重复。

​	key如果重复，新的value会替换旧的value。

​	hashCode()决定了映射关系在table数组中的存储的位置，index = hash(key.hashCode()) & table.length-1 

​	HashMap的底层实现：JDK1.7是数组+链表；JDK1.8是数组+链表/红黑树

（2）TreeMap

​	依据key的大小来保证key是否重复。key如果重复，新的value会替换旧的value。

​	key的大小依赖于，java.lang.Comparable或java.util.Comparator。

（3）LinkedHashMap

​	依据key的hashCode()和equals()来保证key是否重复。key如果重复，新的value会替换旧的value。

​	LinkedHashMap是HashMap的子类，比HashMap多了添加顺序

（4）Hashtable：哈希表

Hashtable是线程安全的，不允许key和value是null。

HashMap是线程不安全的，允许key和value是null。



HashMap线程不安全，怎么办？

(A)自己使用加锁等方式

(B)使用Collections工具类中的 synchronizedMap()将不安全的map转一下

(C)使用ConcurrentHashMap

(D)Hashtable





### 13.6.4 HashMap源码分析

#### JDK1.6源码：

```java
    public HashMap() {
        //this.loadFactor加载因子，影响扩容的频率
        //DEFAULT_LOAD_FACTOR：默认加载因子0.75
        this.loadFactor = DEFAULT_LOAD_FACTOR;
        //threshold阈值 = 容量 * 加载因子
        //threshold阈值，当size达到threhold时，考虑扩容
        //扩容需要两个条件同时满足：（1）size >= threhold （2）table[index]！=null，即新映射关系要存入的位置非空
        threshold = (int)(DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        //table是数组，
        //DEFAULT_INITIAL_CAPACITY：默认是16
        table = new Entry[DEFAULT_INITIAL_CAPACITY];
        init();
    }
```

#### JDK1.7源码：

```java
    public HashMap() {
    	//DEFAULT_INITIAL_CAPACITY：默认初始容量16
    	//DEFAULT_LOAD_FACTOR：默认加载因子0.75
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }
    public HashMap(int initialCapacity, float loadFactor) {
        //校验initialCapacity合法性
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal initial capacity: " +
        //校验initialCapacity合法性                                       initialCapacity);
        if (initialCapacity > MAXIMUM_CAPACITY)
            initialCapacity = MAXIMUM_CAPACITY;
        //校验loadFactor合法性
        if (loadFactor <= 0 || Float.isNaN(loadFactor))
            throw new IllegalArgumentException("Illegal load factor: " +
                                               loadFactor);
		//加载因子，初始化为0.75
        this.loadFactor = loadFactor;
        // threshold 初始为初始容量                                  
        threshold = initialCapacity;
        init();
    }
```

```java
public V put(K key, V value) {
        //如果table数组是空的，那么先创建数组
        if (table == EMPTY_TABLE) {
            //threshold一开始是初始容量的值
            inflateTable(threshold);
        }
        //如果key是null，单独处理
        if (key == null)
            return putForNullKey(value);
        
        //对key的hashCode进行干扰，算出一个hash值
        int hash = hash(key);
        
        //计算新的映射关系应该存到table[i]位置，
        //i = hash & table.length-1，可以保证i在[0,table.length-1]范围内
        int i = indexFor(hash, table.length);
        
        //检查table[i]下面有没有key与我新的映射关系的key重复，如果重复替换value
        for (Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        //添加新的映射关系
        addEntry(hash, key, value, i);
        return null;
    }
    private void inflateTable(int toSize) {
        // Find a power of 2 >= toSize
        int capacity = roundUpToPowerOf2(toSize);//容量是等于toSize值的最接近的2的n次方
		//计算阈值 = 容量 * 加载因子
        threshold = (int) Math.min(capacity * loadFactor, MAXIMUM_CAPACITY + 1);
        //创建Entry[]数组，长度为capacity
        table = new Entry[capacity];
        initHashSeedAsNeeded(capacity);
    }
	//如果key是null，直接存入[0]的位置
    private V putForNullKey(V value) {
        //判断是否有重复的key，如果有重复的，就替换value
        for (Entry<K,V> e = table[0]; e != null; e = e.next) {
            if (e.key == null) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }
        modCount++;
        //把新的映射关系存入[0]的位置，而且key的hash值用0表示
        addEntry(0, null, value, 0);
        return null;
    }
    void addEntry(int hash, K key, V value, int bucketIndex) {
        //判断是否需要库容
        //扩容：（1）size达到阈值（2）table[i]正好非空
        if ((size >= threshold) && (null != table[bucketIndex])) {
            //table扩容为原来的2倍，并且扩容后，会重新调整所有映射关系的存储位置
            resize(2 * table.length);
            //新的映射关系的hash和index也会重新计算
            hash = (null != key) ? hash(key) : 0;
            bucketIndex = indexFor(hash, table.length);
        }
		//存入table中
        createEntry(hash, key, value, bucketIndex);
    }
    void createEntry(int hash, K key, V value, int bucketIndex) {
        Entry<K,V> e = table[bucketIndex];
        //原来table[i]下面的映射关系作为新的映射关系next
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        size++;//个数增加
    }
```

1、put(key,value)

（1）当第一次添加映射关系时，数组初始化为一个长度为**16**的**HashMap$Entry**的数组，这个HashMap$Entry类型是实现了java.util.**Map.Entry**接口

（2）特殊考虑：如果key为null，index直接是[0]

（3）在计算index之前，会对key的hashCode()值，做一个hash(key)再次哈希的运算，这样可以使得Entry对象更加散列的存储到table中

（4）计算index = table.length-1 & hash;

（5）如果table[index]下面，已经有映射关系的key与我要添加的新的映射关系的key相同了，会用新的value替换旧的value。

（6）如果没有相同的，会把新的映射关系添加到链表的头，原来table[index]下面的Entry对象连接到新的映射关系的next中。

（7）添加之前先判断if(size >= threshold  &&  table[index]!=null)如果该条件为true，会扩容

​	if(size >= threshold  &&  table[index]!=null){

​		①会扩容

​		②会重新计算key的hash

​		③会重新计算index

​	}

2、get(key)

（1）计算key的hash值，用这个方法hash(key)

（2）找index = table.length-1 & hash;

（3）如果table[index]不为空，那么就挨个比较哪个Entry的key与它相同，就返回它的value

3、remove(key)

（1）计算key的hash值，用这个方法hash(key)

（2）找index = table.length-1 & hash;

（3）如果table[index]不为空，那么就挨个比较哪个Entry的key与它相同，就删除它，把它前面的Entry的next的值修改为被删除Entry的next

#### JDK1.8源码

```java
几个常量和变量：
（1）DEFAULT_INITIAL_CAPACITY：默认的初始容量 16
（2）MAXIMUM_CAPACITY：最大容量  1 << 30
（3）DEFAULT_LOAD_FACTOR：默认加载因子 0.75
（4）TREEIFY_THRESHOLD：默认树化阈值8，当链表的长度达到这个值后，要考虑树化
（5）UNTREEIFY_THRESHOLD：默认反树化阈值6，当树中的结点的个数达到这个阈值后，要考虑变为链表
（6）MIN_TREEIFY_CAPACITY：最小树化容量64
		当单个的链表的结点个数达到8，并且table的长度达到64，才会树化。
		当单个的链表的结点个数达到8，但是table的长度未达到64，会先扩容
（7）Node<K,V>[] table：数组
（8）size：记录有效映射关系的对数，也是Entry对象的个数
（9）int threshold：阈值，当size达到阈值时，考虑扩容
（10）double loadFactor：加载因子，影响扩容的频率
```

```java
    public HashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR; 
        // all other fields defaulted，其他字段都是默认值
    }
```

```java
    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }
	//目的：干扰hashCode值
    static final int hash(Object key) {
        int h;
		//如果key是null，hash是0
		//如果key非null，用key的hashCode值 与 key的hashCode值高16进行异或
		//		即就是用key的hashCode值高16位与低16位进行了异或的干扰运算
		
		/*
		index = hash & table.length-1
		如果用key的原始的hashCode值  与 table.length-1 进行按位与，那么基本上高16没机会用上。
		这样就会增加冲突的概率，为了降低冲突的概率，把高16位加入到hash信息中。
		*/
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    final V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        Node<K,V>[] tab; //数组
		Node<K,V> p; //一个结点
		int n, i;//n是数组的长度   i是下标
		
		//tab和table等价
		//如果table是空的
        if ((tab = table) == null || (n = tab.length) == 0){
            n = (tab = resize()).length;
            /*
			tab = resize();
			n = tab.length;*/
			/*
			如果table是空的，resize()完成了①创建了一个长度为16的数组②threshold = 12
			n = 16
			*/
        }
		//i = (n - 1) & hash ，下标 = 数组长度-1 & hash
		//p = tab[i] 第1个结点
		//if(p==null) 条件满足的话说明 table[i]还没有元素
		if ((p = tab[i = (n - 1) & hash]) == null){
			//把新的映射关系直接放入table[i]
            tab[i] = newNode(hash, key, value, null);
			//newNode（）方法就创建了一个Node类型的新结点，新结点的next是null
        }else {
            Node<K,V> e; 
			K k;
			//p是table[i]中第一个结点
			//if(table[i]的第一个结点与新的映射关系的key重复)
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k)))){
                e = p;//用e记录这个table[i]的第一个结点
			}else if (p instanceof TreeNode){//如果table[i]第一个结点是一个树结点
                //单独处理树结点
                //如果树结点中，有key重复的，就返回那个重复的结点用e接收，即e!=null
                //如果树结点中，没有key重复的，就把新结点放到树中，并且返回null，即e=null
				e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            }else {
				//table[i]的第一个结点不是树结点，也与新的映射关系的key不重复
				//binCount记录了table[i]下面的结点的个数
                for (int binCount = 0; ; ++binCount) {
					//如果p的下一个结点是空的，说明当前的p是最后一个结点
                    if ((e = p.next) == null) {
						//把新的结点连接到table[i]的最后
                        p.next = newNode(hash, key, value, null);
						
						//如果binCount>=8-1，达到7个时，加上新添加的就是8个
                        if (binCount >= TREEIFY_THRESHOLD - 1){ // -1 for 1st
                            //要么扩容，要么树化
							treeifyBin(tab, hash);
						}
                        break;
                    }
					//如果key重复了，就跳出for循环，此时e结点记录的就是那个key重复的结点
            if (e.hash == hash &&((k = e.key) == key || (key != null && key.equals(k)))){
                        break;
					}
                    p = e;//下一次循环，e=p.next，就类似于e=e.next，往链表下移动
                }
            }
			//如果这个e不是null，说明有key重复，就考虑替换原来的value
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null){
                    e.value = value;
				}
                afterNodeAccess(e);//什么也没干
                return oldValue;
            }
        }
        ++modCount;
		
		//元素个数增加
		//size达到阈值
        if (++size > threshold){
            resize();//一旦扩容，重新调整所有映射关系的位置
		}
        afterNodeInsertion(evict);//什么也没干
        return null;
    }	
	
   final Node<K,V>[] resize() {
        Node<K,V>[] oldTab = table;//oldTab原来的table
		//oldCap：原来数组的长度
        int oldCap = (oldTab == null) ? 0 : oldTab.length;
		
		//oldThr：原来的阈值
        int oldThr = threshold;//最开始threshold是0
		
		//newCap，新容量
		//newThr：新阈值
        int newCap, newThr = 0;
        if (oldCap > 0) {//说明原来不是空数组
            if (oldCap >= MAXIMUM_CAPACITY) {//是否达到数组最大限制
                threshold = Integer.MAX_VALUE;
                return oldTab;
            }else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                     oldCap >= DEFAULT_INITIAL_CAPACITY){
				//newCap = 旧的容量*2 ，新容量<最大数组容量限制
				//新容量：32,64，...
				//oldCap >= 初始容量16
				//新阈值重新算 = 24，48 ....
                newThr = oldThr << 1; // double threshold
			}
        }else if (oldThr > 0){ // initial capacity was placed in threshold
            newCap = oldThr;
        }else {               // zero initial threshold signifies using defaults
            newCap = DEFAULT_INITIAL_CAPACITY;//新容量是默认初始化容量16
			//新阈值= 默认的加载因子 * 默认的初始化容量 = 0.75*16 = 12
            newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
        }
        if (newThr == 0) {
            float ft = (float)newCap * loadFactor;
            newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                      (int)ft : Integer.MAX_VALUE);
        }
        threshold = newThr;//阈值赋值为新阈值12，24.。。。
		
		//创建了一个新数组，长度为newCap，16，32,64.。。
        @SuppressWarnings({"rawtypes","unchecked"})
            Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
        table = newTab;
		
		
        if (oldTab != null) {//原来不是空数组
			//把原来的table中映射关系，倒腾到新的table中
            for (int j = 0; j < oldCap; ++j) {
                Node<K,V> e;
                if ((e = oldTab[j]) != null) {//e是table下面的结点
                    oldTab[j] = null;//把旧的table[j]位置清空
                    if (e.next == null)//如果是最后一个结点
                        newTab[e.hash & (newCap - 1)] = e;//重新计算e的在新table中的存储位置，然后放入
                    else if (e instanceof TreeNode)//如果e是树结点
						//把原来的树拆解，放到新的table
                        ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                    else { // preserve order
                        Node<K,V> loHead = null, loTail = null;
                        Node<K,V> hiHead = null, hiTail = null;
                        Node<K,V> next;
						/*
						把原来table[i]下面的整个链表，重新挪到了新的table中
						*/
                        do {
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                if (loTail == null)
                                    loHead = e;
                                else
                                    loTail.next = e;
                                loTail = e;
                            }
                            else {
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[j] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[j + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }	
	
    Node<K,V> newNode(int hash, K key, V value, Node<K,V> next) {
		//创建一个新结点
	   return new Node<>(hash, key, value, next);
    }

    final void treeifyBin(Node<K,V>[] tab, int hash) {
        int n, index; 
		Node<K,V> e;
		//MIN_TREEIFY_CAPACITY：最小树化容量64
		//如果table是空的，或者  table的长度没有达到64
        if (tab == null || (n = tab.length) < MIN_TREEIFY_CAPACITY)
            resize();//先扩容
        else if ((e = tab[index = (n - 1) & hash]) != null) {
			//用e记录table[index]的结点的地址
            TreeNode<K,V> hd = null, tl = null;
			/*
			do...while，把table[index]链表的Node结点变为TreeNode类型的结点
			*/
            do {
                TreeNode<K,V> p = replacementTreeNode(e, null);
                if (tl == null)
                    hd = p;//hd记录根结点
                else {
                    p.prev = tl;
                    tl.next = p;
                }
                tl = p;
            } while ((e = e.next) != null);
			
            //如果table[index]下面不是空
            if ((tab[index] = hd) != null)
                hd.treeify(tab);//将table[index]下面的链表进行树化
        }
    }	
```

1、添加过程

（1）当第一次添加映射关系时，数组初始化为一个长度为**16**的**HashMap$Node**的数组，这个HashMap$Node类型是实现了java.util.**Map.Entry**接口

（2）在计算index之前，会对key的hashCode()值，做一个hash(key)再次哈希的运算，这样可以使得Entry对象更加散列的存储到table中

> JDK1.8关于hash(key)方法的实现比JDK1.7要简洁。 key.hashCode() ^ key.Code()>>>16;

（3）计算index = table.length-1 & hash;

（4）如果table[index]下面，已经有映射关系的key与我要添加的新的映射关系的key相同了，会用新的value替换旧的value。

（5）如果没有相同的，

①table[index]链表的长度没有达到8个，会把新的映射关系添加到链表的尾

②table[index]链表的长度达到8个，但是table.length没有达到64，会先对table进行扩容，然后再添加

③table[index]链表的长度达到8个，并且table.length达到64，会先把该分支进行树化，结点的类型变为TreeNode，然后把链表转为一棵红黑树

④table[index]本来就已经是红黑树了，那么直接连接到树中，可能还会考虑考虑左旋右旋以保证树的平衡问题

（6）添加完成后判断if(size > threshold ){

​		①会扩容

​		②会重新计算key的hash

​		③会重新计算index

​	}



2、remove(key)

（1）计算key的hash值，用这个方法hash(key)

（2）找index = table.length-1 & hash;

（3）如果table[index]不为空，那么就挨个比较哪个Entry的key与它相同，就删除它，把它前面的Entry的next的值修改为被删除Entry的next

（4）如果table[index]下面原来是红黑树，结点删除后，个数小于等于6，会把红黑树变为链表

### 13.6.5 关于HashMap的面试问题

1、HashMap的底层实现

> 答：JDK1.7是数组+链表，JDK1.8是数组+链表/红黑树

2、HashMap的数组的元素类型

> 答：java.util.Map$Entry接口类型。
>
> JDK1.7的HashMap中有内部类Entry实现Entry接口
>
> JDK1.8的HashMap中有内部类Node和TreeNode类型实现Entry接口

3、为什么要使用数组？

> 答：因为数组的访问的效率高

4、为什么数组还需要链表？或问如何解决hash或[index]冲突问题？

> 答：为了解决hash和[index]冲突问题
>
> （1）两个不相同的key的hashCode值本身可能相同
>
> （2）两个hashCode不相同的key，通过hash(key)以及 hash & table.length-1运算得到的[index]可能相同
>
> 那么意味着table[index]下可能需要存储多个Entry的映射关系对象，所以需要链表

5、HashMap的数组的初始化长度

> 答：默认的初始容量值是16

6、HashMap的映射关系的存储索引index如何计算

> 答：hash & table.length-1

7、为什么要使用hashCode()? 空间换时间

> 答：因为hashCode()是一个整数值，可以用来直接计算index，效率比较高，用数组这种结构虽然会浪费一些空间，但是可以提高查询效率。

8、hash()函数的作用是什么

> 答：在计算index之前，会对key的hashCode()值，做一个hash(key)再次哈希的运算，这样可以使得Entry对象更加散列的存储到table中
>
> JDK1.8关于hash(key)方法的实现比JDK1.7要简洁。 key.hashCode() ^ key.Code()>>>16; 因为这样可以使得hashCode的高16位信息也能参与到运算中来

9、HashMap的数组长度为什么一定要是2的幂次方

> 答：因为2的n次方-1的二进制值是前面都0，后面几位都是1，这样的话，与hash进行&运算的结果就能保证在[0,table.length-1]范围内，而且是均匀的。

10、HashMap 为什么使用 &按位与运算代替%模运算？

> 答：因为&效率高

11、HashMap的数组什么时候扩容？

> 答：JDK1.7版：当要添加新Entry对象时发现（1）size达到threshold（2）table[index]!=null时，两个条件同时满足会扩容
>
> JDK1.8版：当要添加新Entry对象时发现（1）size达到threshold（2）当table[index]下的结点个数达到8个但是table.length又没有达到64。两种情况满足其一都会导致数组扩容
>
> 而且数组一旦扩容，不管哪个版本，都会导致所有映射关系重新调整存储位置。

12、如何计算扩容阈值(临界值)？

> 答：threshold = capacity * loadfactor

13、loadFactor为什么是0.75，如果是1或者0.1呢有什么不同？

> 答：1的话，会导致某个table[index]下面的结点个数可能很长
>
> 0.1的话，会导致数组扩容的频率太高

14、JDK1.8的HashMap什么时候树化？

> 答：当table[index]下的结点个数达到8个但是table.length已经达到64

15、JDK1.8的HashMap什么时候反树化？

> 答：当table[index]下的树结点个数少于6个

16、JDK1.8的HashMap为什么要树化？

> 答：因为当table[index]下的结点个数超过8个后，查询效率就低下了，修改为红黑树的话，可以提高查询效率

17、JDK1.8的HashMap为什么要反树化？

> 答：因为因为当table[index]下树的结点个数少于6个后，使用红黑树反而过于复杂了，此时使用链表既简洁又效率也不错

18、作为HashMap的key类型重写equals和hashCode方法有什么要求

​	（1）equals与hashCode一起重写

​	（2）重写equals()方法，但是有一些注意事项；

* 自反性：x.equals(x)必须返回true。
  对称性：x.equals(y)与y.equals(x)的返回值必须相等。
  传递性：x.equals(y)为true，y.equals(z)也为true，那么x.equals(z)必须为true。
  一致性：如果对象x和y在equals()中使用的信息都没有改变，那么x.equals(y)值始终不变。
  非null：x不是null，y为null，则x.equals(y)必须为false。

​	（3）重写hashCode（）的注意事项

* 如果equals返回true的两个对象，那么hashCode值一定相同，并且只要参与equals判断属性没有修改，hashCode值也不能修改；
  如果equals返回false的两个对象，那么hashCode值可以相同也可以不同；
  如果hashCode值不同的，equals一定要返回false；
  hashCode不宜过简单，太简单会导致冲突严重，hashCode也不宜过于复杂，会导致性能低下；

19、为什么大部分 hashcode 方法使用 31？

> 答：因为31是一个不大不小的素数
>
> i*31 ==》  i<<5-1 ，像31,127等这种特殊的素数，可以将乘法转为位运算符，效率更高。
>
> 素数：稳定，hashCode冲突的概率更低。

20、请问已经存储到HashMap中的key的对象属性是否可以修改？为什么？

> 答：如果该属性参与hashCode的计算，那么不要修改。因为一旦修改hashCode()已经不是原来的值。
> 而存储到HashMap中时，key的hashCode()-->hash()-->hash已经确定了，不会重新计算。用新的hashCode值再查询get(key)/删除remove(key)时，算的hash值与原来不一样就不找不到原来的映射关系了。

21、所以为什么，我们实际开发中，key的类型一般用String和Integer

> 答：因为他们不可变。

22、为什么HashMap中的Node的hash变量与key变量加final声明？JDK1.7Entry中key加final声明？

> 答：因为不希望你修改hash和key值

23、为什么HashMap中的Node或Entry类型要单独存储hash？

> 答：为了在添加、删除、查找过程中，比较hash效率更高，不用每次重新计算key的hash值

24、请问已经存储到HashMap中的value的对象属性是否可以修改？为什么？

> 答：可以。因为我们存储、删除等都是根据key，和value无关。

25、如果key是null是如何存储的？

> 答：会存在table[0]中

## 13.7 集合框架图

![1560348912361](../../JavaSE20190513/note/imgs/1560348912361.png)

## 13.8 Collections工具类

java.util.Collections：工具类，操作集合

（1）public static <T> boolean addAll(Collection<? super T> c, T... elements)

添加elements的几个对象到c集合中。T是elements对象的类型，要求Collection集合的元素类型必须是T或T的父类

（2）public static <T> int binarySearch(List<? extends Comparable<? super T>> list,T key)  

在list集合中用二分查找key的下标，如果存在返回的是合理的下标，如果不存在返回的是一个负数下标   

T是元素的类型，

<? extends Comparable<? super T>>，要求集合的元素必须实现Comparable接口

<? super T>，在实现Comparable接口，可以指定Comparable<类型实参>为T或T的父类。

（3）public static boolean disjoint(Collection<?> c1, Collection<?> c2)

判断c1和c2没有交集就为true

（4）public static <T extends Object & Comparable<? super T>> T max(Collection<? extends T> coll)

求coll集合中最大元素

<T extends Object & Comparable<? super T>>：要求T或T的父类实现Comparable接口

因为找最大值需要比较大小

（5）public static <T extends Comparable<? super T>> void sort(List<T> list) 给list集合排序   

<T extends Comparable<? super T>>：要求T或T的父类实现Comparable接口

（6）public static <T> Collection<T> synchronizedCollection(Collection<T> c)

以synchronizedXX开头的方法，表示把某种非线程安全集合转为一个线程安全的集合。

（7）public static <T> List<T> unmodifiableList(List<? extends T> list)

以unmodifiableXx开头的方法，表示返回一个“只读”的集合。

# 第十四章 IO流

## 14.1 java.io.File类

1、文件和目录路径名的抽象描述。即File类的对象是表示一个文件或一个目录，创建File类对象时需要指定这个文件或目录的路径名。

2、API

（1）new File(路径名)

> new出来的File对象仍然只是Java中的一个对象，存在堆中。如果这个File对象对应的文件或目录是真实存在的，那么会根据这个实际的文件或目录将相应的属性赋值，例如：大小、时间等。如果这个File对象对应文件或目录并不存在，那么除了name,path会赋值以外，其实的属性都是默认值。

（2）String getName()：获取文件名称

（3）long length()：获取文件大小，只能获取文件的大小，不能直接获取目录的大小

（4）long lastModified()：获取最后修改时间

（5）String getPath()：获取构造路径

（6）String getAbsolutePath()：获取绝对路径

​	File getAbsoluteFile()：获取绝对路径对应的File对象

（7）String getCanonicalPath()：获取规范路径

​	File getCanonicalFile()：获取规范路径对应的File对象

（8）boolean exists()：是否存在

（9）boolean isFile()：是否是文件，只有这个文件存在并且确实是一个文件才会返回true

（10）boolean isDirectory()：是否是目录，只有这个目录确实存在才会返回true

（11）boolean isHidden()：是否隐藏

（12）boolean canRead()：是否可读

（13）boolean canWrite()：是否可写

（14）createNewFile()：创建文件

（15）mkdir()：创建目录，如果父目录不存在，创建失败

（16）mkdirs()：创建目录，如果父目录不存在，一并创建

（17）delete()：删除文件，如果是目录只能删除空目录

（18）renameTo(新File)：重命名

（19）String getParent()：获取父目录

​	  File getParentFile()：获取父目录对象

（20）String[] list()：获取下一级

​	File[] listFiles()： 获取下一级的File对象

（21）File[] listFiles(FileFilter f)：根据过滤器筛选下一级

​	  String[] listFiles(FileFilter f)：根据过滤器筛选下一级

（22）static File createTempFile(前缀，后缀)：在操作系统用户的临时目录下创建临时文件

​	static File createTempFile(前缀，后缀，临时文件目录)：在指定目录下创建临时文件

（23）void deleteOnExit()：退出JVM时删除，一般用于删除临时文件

## 14.2 IO流

### 14.2.1 分类

1、按照方向分：输入流和输出流

输入流：InputStream和Reader系列

输入流：OutputStream和Writer系列

2、按照处理数据的方式不同：字节流和字符流

字节流：InputStream和OutputStream系列

字符流：Reader和Writer系列

3、按照角色功能不同：节点流和处理流

节点流：文件IO流

处理流：缓冲流、转换流、数据流、对象流、打印流...

### 14.2.2 四个抽象基类

1、InputStream：字节输入流

（1）int read()：读取一个字节，如果已经到达流末尾，没有数据可读了，返回-1.

（2）int read(byte[] data)：读取多个字节到data数组中，从data[0]开始存储，最多读取data.length个字节。返回的是实际读取的字节数。如果已经到达流末尾，没有数据可读了，返回-1.

（3）int read(byte[] data, int offset, int len)：读取多个字节到data数组中，从data[offset]开始存储，最多读取len个字节。返回的是实际读取的字节数。如果已经到达流末尾，没有数据可读了，返回-1.

（4）void close()；关闭IO流

2、OutputStream：字节输出流

（1）void write(int b)：输出一个字节

（2）void write(byte[] data)：输出一个字节数组的全部

（3）void write(byte[] data, int offset, int len)：输出一个字节数组的部分

（4）void flush()：刷新

（5）void close()：关闭

3、Reader：字符输入流

（1）int read()：读取一个字符，如果已经到达流末尾，没有数据可读了，返回-1.

（2）int read(char[] data)：读取多个字符到data数组中，从data[0]开始存储，最多读取data.length个字符。返回的是实际读取的字符数。如果已经到达流末尾，没有数据可读了，返回-1.

（3）int read(char[] data, int offset, int len)：读取多个字符到data数组中，从data[offset]开始存储，最多读取len个字符。返回的是实际读取的字符数。如果已经到达流末尾，没有数据可读了，返回-1.

（4）void close()；关闭IO流

4、Writer：字符输出流

（1）void write(int b)：输出一个字符

（2）void write(char[] data)：输出一个字符数组的全部

（3）void write(char[] data, int offset, int len)：输出一个字符数组的部分

（4）void flush()：刷新

（5）void close()：关闭

（6）void write(String str)：输出整个字符串

（7）void write(String str ,int offset, int count)：输出字符串的部分





操作IO流的步骤：

（1）创建合适的IO流的对象

（2）读、写

（3）关闭IO流

​	要么只关闭最外层的IO流，要么都关的话，注意顺序，先关外面的再关里面的。



### 14.2.3 文件IO流

FileInputStream：文件字节输入流，可以用于读取任意类型的文件

FileOutputStream：文件字节输出流，用于输出数据到任意类型的文件

FileReader：文件字符输入流，只能用于读取纯文本文件，并且只能按照平台默认的字符编码进行文件读取。所以如果文件的编码与程序的编码不一致，会出现乱码。

FileWriter：文件字符输出流，只能把数据输出到纯文本文件中，并且只能按照平台默认的字符编码进行输出。所以如果文件的编码与程序的编码不一致，会出现乱码。

### 14.2.4 缓冲流

BufferedInputStream：给InputStream系列的IO流增加缓冲功能，即只能包装InputStream的子类的IO流。

BufferedOutpuStream：给OutputStream....

BufferedReader：给Reader..

BufferedWriter：给Writer...



BufferedReader：

​	String readLine()

BufferedWriter:

​	String newLine()



### 14.2.5 转换流

InputStreamReader：用于解码的IO流

​	用来包装字节流，把字节输入流包装为字符输入流。

​	数据：从字节流中读取数据，按照指定的编码方式，解码为字符数据

OutputStreamWriter：用于编码的IO流

​	用来包装字节流，把字节输出流包装为字符输出流。

​	数据：先写入到OutputStreamWriter中，它再字符数据按照指定的编码，转为字节写出到它包装的字节输出流中，然后输出。



### 14.2.6 数据流

DataOutputStream：数据输出流

​	writeUTF(String)

​	writeInt(int)

​	writeDouble(double)

​	writeChar(char)

​	writeBoolean(boolean)

DataInputStream：数据输入流

​	String readUTF()

​	int readInt()

​	double readDouble()

​	char readChar()

​	boolean readBoolean()



### 14.2.7 对象流

ObjectOutpuStream：序列化

​	writeObject(obj)：输出对象

ObjectInputStream：反序列化

​	Object readObject()：读取对象



序列化的问题：

1、所有需要序列化的对象的类型必须实现java.io.Serializable接口，如果这个对象的属性也是引用数据类型，那么只要这个属性也参与序列化，也要实现Serializable接口

2、如果希望类作了修改之后，原来的序列化的数据仍然可以被反序列化，可以在一开始给类增加一个序列化版本ID：long serialVersionUID的常量值。

3、有static、transient修饰的属性是不能被序列化



补充：java.io.Externalizable接口

实现这个接口，也可以使得对象能够被序列化，但是要求程序员重写两个方法：

void writeExternal(ObjectOutput out) ：编写哪些属性需要序列化
void readExternal(ObjectInput in) ：编写哪些属性需要反序列化

# 第十五章 网络编程（了解）

学习它为了更好的理解：web（服务器端和客户端的通信）、数据库（服务器端和客户端的数据传输）等原理。

## 15.1 主机IP

在程序中表示：

（1）值的表示

IPV4：32位整数，8位一组，用.分割，例如：192.168.11.45

​	每个8位的范围[0,255]

IPV6：128位整数表示，16位一组，用:分割，例如：X:X:X:X:X:X:X:X

​	每个16位用十六进制值表示

 

（2）对象表示：InetAddress

此类表示互联网协议 (IP) 地址，它有两个子类Inet4Address和Inet6Address，分别对应IPV4和IPV6。InetAddress类没有提供公共的构造器，而是提供了如下几个静态方法来获取InetAddress实例。

* public static InetAddress getLocalHost()
* public static InetAddress getByAddress(byte[] addr)
* public static InetAddress getByName(String host)

InetAddress提供了如下几个常用的方法：

* public String getHostAddress()：返回 IP 地址字符串（以文本表现形式）。
* public String getHostName()：获取此 IP 地址的主机名
* public String getCanonicalHostName()：获取此 IP 地址的完全限定域名
* public boolean isReachable(int timeout)：测试是否可以达到该地址。

## 15.2 端口号

范围：[0,65535]

常见的端口号：

​	tomcat/JBoss：8080

​	http：80

​	mysql：3306

​	oracle：1521

​	sql server：1433



## 15.3 网络协议

![1560731383474](../../JavaSE20190513/note/imgs/1560731383474.png)

* 应用层：网络服务与最终用户的一个接口。协议有：HTTP、FTP、SMTP、DNS、TELNET、HTTPS、POP3等等。
* 表示层：数据的表示、安全、压缩。格式有：JPEG、ASCll、DECOIC、加密格式等。
* 会话层：建立、管理、终止会话。对应主机进程，指本地主机与远程主机正在进行的会话
* 传输层：定义传输数据的协议端口号，以及流控和差错校验。协议有：TCP、UDP。
* 网络层：进行逻辑地址寻址，实现不同网络之间的路径选择。协议有：ICMP、IGMP、IP（IPV4 IPV6）、ARP、RARP。
* 数据链路层：建立逻辑连接、进行硬件地址寻址、差错校验等功能。将比特组合成字节进而组合成帧，用MAC地址访问介质，错误发现但不能纠正。
* 物理层：建立、维护、断开物理连接。

而IP协议是一种非常重要的协议。IP（internet protocal）又称为互联网协议。IP的责任就是把数据从源传送到目的地。它在源地址和目的地址之间传送一种称之为数据包的东西，它还提供对数据大小的重新组装功能，以适应不同网络对包大小的要求。经常与IP协议放在一起的还有TCP（Transmission Control Protocol）协议，即传输控制协议，是一种面向连接的、可靠的、基于字节流的传输层通信协议。而通常我们说的TCP/IP协议，其实是指TCP/IP协议族，因为该协议家族的两个最核心协议：TCP（传输控制协议）和IP（网际协议），为该家族中最早通过的标准，所以简称为TCP/IP协议。

TCP：Transmission Control Protocol 传输控制协议
	（1）面向连接的：连接之前有三次握手，断开时四次挥手
	（2）可靠的
	（3）基于字节流
	（4）可以传输大量数据的
UDP：User Datagram Protocol 用户数据报协议
	（1）非面向连接的
	（2）不可靠的
	（3）基于数据报的
	（4）数据量大小有限制的64K



## 15.4 Socket编程

Socket：套接字，代表网络通信的一端，负责和网卡驱动程序沟通的对象。

![1560731601718](../../JavaSE20190513/note/imgs/1560731601718.png)

分为：
（1）流套接字：ServerSocket和Socket
（2）数据报套接字：DatagramSocket

ServerSocket的常用构造方法和其他方法：

* ServerSocket(int port) ：指定在某个端口号监听客户端的连接和通信
* Socket accept()  ：监听和接收客户端的连接
* void close() ：关闭

Socket类的常用构造方法：

* public Socket(InetAddress address,int port)：创建一个流套接字并将其连接到指定 IP 地址的指定端口号。
* public Socket(String host,int port)：创建一个流套接字并将其连接到指定主机上的指定端口号。

Socket类的常用方法：

* public InputStream getInputStream()：返回此套接字的输入流，可以用于接收消息
* public OutputStream getOutputStream()：返回此套接字的输出流，可以用于发送消息
* public InetAddress getInetAddress()：此套接字连接到的远程 IP 地址；如果套接字是未连接的，则返回 null。
* public void close()：关闭此套接字。套接字被关闭后，便不可在以后的网络连接中使用（即无法重新连接或重新绑定）。需要创建新的套接字对象。 关闭此套接字也将会关闭该套接字的 InputStream 和 OutputStream。 
* public void shutdownInput()：如果在套接字上调用 shutdownInput() 后从套接字输入流读取内容，则流将返回 EOF（文件结束符）。 即不能在从此套接字的输入流中接收任何数据。
* public void shutdownOutput()：禁用此套接字的输出流。对于 TCP 套接字，任何以前写入的数据都将被发送，并且后跟 TCP 的正常连接终止序列。 如果在套接字上调用 shutdownOutput() 后写入套接字输出流，则该流将抛出 IOException。 即不能通过此套接字的输出流发送任何数据。

注意：先后调用Socket的shutdownInput()和shutdownOutput()方法，仅仅关闭了输入流和输出流，并不等于调用Socket的close()方法。在通信结束后，仍然要调用Scoket的close()方法，因为只有该方法才会释放Socket占用的资源，比如占用的本地端口号等。

DatagramSocket：

* DatagramPacket(byte[] buf, int length)
* DatagramPacket(byte[] buf, int offset, int length, InetAddress address, int port)

## 15.5 代码示例

### 15.5.1 TCP协议编程示例一

一个客户端连接服务器，连接成功后，服务器给客户端发送“欢迎你登录"

```java
package com.atguigu.test10;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * TCP：面向连接，可靠的，基于字节流的
 * 		服务器：等待被连接的过程
 * 
 * ServerSocket：只负责接受和建立连接，不负责数据的传输
 * Socket：负责数据的传输
 * 
 * 步骤：
 * 1、开启服务器
 * 	  指定服务器监听的端口号
 * 2、等待客户端并接受客户端的连接
 * 
 * 3、接受/发送数据
 * 发送方：输出流
 * 接受方：输入流
 * 
 * 4、断开连接
 * 
 * 5、关闭服务器
 */
public class TestServer {
	public static void main(String[] args) throws IOException {
		//1、开启服务器：网卡驱动就监听9999端口号的数据
		ServerSocket server = new ServerSocket(9999);
		
		//2、等待客户端并接受客户端的连接
		Socket socket = server.accept();//这句代码执行一次，就接受一个客户端连接
		System.out.println("一个客户端连接成功!");
		//3、例如：发送数据
		//发送：欢迎你登录
		//字节流，输出流  OutputStream
		//(1)获取输出流
		OutputStream out = socket.getOutputStream();
		//(2)发送数据
		out.write("欢迎你登录".getBytes());
		
		//4、断开连接
		socket.close();
		
		//5、关闭服务器
		server.close();
	}
}

```

```java
package com.atguigu.test10;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * TCP：
 * 	 客户端，主动连接服务器
 * 
 * Socket(InetAddress address, int port) 
 * Socket(String host, int port)
 * 
 * 步骤：
 * 1、连接服务器
 * Socket socket = new Socket("192.168.30.142",9999);
 * 
 * 2、发送或接受数据
 * 
 * 3、断开连接
 */
public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
//		1、连接服务器
		Socket socket = new Socket("192.168.30.142",9999);
		
		//2、例如：接受数据
		//字节流，输入流，InputStream
		InputStream in = socket.getInputStream();
		byte[] data = new byte[1024];
		int len;
		while((len = in.read(data)) != -1){
			System.out.println(new String(data,0,len));
		}
		
		//3、断开
		socket.close();
	}
}

```



### 15.5.2 TCP协议编程示例二

一个客户端连接服务器，连接成功后，客户端给服务器先传一个“你好”，服务器给客户端返回“欢迎你登录"

```java
package com.atguigu.test11;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
	public static void main(String[] args) throws IOException {
		//1、开启服务器：网卡驱动就监听9999端口号的数据
		ServerSocket server = new ServerSocket(9999);
		
		//2、等待客户端并接受客户端的连接
		Socket socket = server.accept();//这句代码执行一次，就接受一个客户端连接
		System.out.println("一个客户端连接成功!");
		
		//3、接受数据
		InputStream in = socket.getInputStream();
		byte[] data = new byte[1024];
		int len;
		System.out.println("服务器收到：");
		while((len = in.read(data)) != -1){
			System.out.println(new String(data,0,len));
		}
		
		//4、例如：发送数据
		//发送：欢迎你登录
		//字节流，输出流  OutputStream
		//(1)获取输出流
		OutputStream out = socket.getOutputStream();
		//(2)发送数据
		out.write("欢迎你登录".getBytes());
		
		//4、断开连接
		socket.close();
		
		//5、关闭服务器
		server.close();
	}
}

```

```java
package com.atguigu.test11;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
//		1、连接服务器
		Socket socket = new Socket("192.168.30.142",9999);
		
		//2、例如：发送你好
		OutputStream out = socket.getOutputStream();
		out.write("你好".getBytes());
//		out.close();//错误的，如果调用out.close()会导致socket的close()
		//如果仅仅表示不发送了，还要接收，那么选择半关闭，只关闭输出通道
		socket.shutdownOutput();
		
		//3、例如：接受数据
		//字节流，输入流，InputStream
		System.out.println("客户端收到：");
		InputStream in = socket.getInputStream();
		byte[] data = new byte[1024];
		int len;
		while((len = in.read(data)) != -1){
			System.out.println(new String(data,0,len));
		}
		
		//3、断开
		socket.close();
	}
}
```

### 15.5.3 TCP协议编程示例三

一个客户端连接服务器，连接成功后：

（1）客户端从键盘输入词语，给服务器发送，直到bye为止；

（2）服务器每次手动词语，反转词语 ，然后返回给客户端，直到接收到bye为止

```java
package com.atguigu.test12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 服务器端：
 * 	(1)接收客户端的连接
 *  (2)接收客户端的词语
 *  (3)把词语“反转”返回给客户端
 *  (2)(3)多次，直到客户端发送"bye"为止
 */
public class TestServer {
	public static void main(String[] args) throws IOException {
		//1、开启服务器
		ServerSocket server = new ServerSocket(8989);
		
		//2、接收一个客户端的连接
		Socket socket = server.accept();
		
		//3、先获取输入流和输出流
		InputStream in = socket.getInputStream();
		/*
		 * 因为是接收一个词语，反转一个，返回一个
		 * 那么如果仅仅使用字节流，不好区分词语
		 * 需要用到字符流
		 * 那么就意味着需要把字节流转为字符流
		 */
		InputStreamReader isr = new InputStreamReader(in);//这里不涉及编码问题，仅仅为了转换流的类型
		/*
		 * 字符流中几个字符是一个词语
		 * 那么我们这里选择“换行符”来作为词语的分割
		 * 意味着我们可以按行读取Scanner或BufferedReader
		 */
		BufferedReader br = new BufferedReader(isr);
		
		OutputStream out = socket.getOutputStream();
		/*
		 * 客户端收到字节，同样不方便处理几个字节是一个词语，仍然要把字节输出流转为字符流
		 * 而且字符之间也不好区分，那么也选择“换行符”进行区别词语
		 * 我们现在需要把OutputStream转为一个可以按行写的字符流或其他的处理流
		 * 
		 * 可以按行写的：BufferedWriter(newLine())
		 * 		   PrintStream(println())
		 */
		PrintStream ps = new PrintStream(out);
		
		//从客户端接收词语
		String word;
		while((word = br.readLine()) != null){
			if("bye".equals(word)){
				break;
			}
			
			//如果不是bye，要反转，并且返回
			StringBuilder sb = new StringBuilder(word);
			sb.reverse();
			
			//返回给客户端
			ps.println(sb.toString());
		}
		
		//4、断开
		socket.close();
		
		//5、关闭服务器
		server.close();
	}
}

```

```java
package com.atguigu.test12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * 客户端：
 * （1）从键盘输入词语
 * （2）发送给服务器
 * （3）接收服务器返回的结果
 * （1）（2）（3）多次进行，直到键盘输入bye并发送给发服务器之后就结束
 */
public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、连接服务器
		Socket socket = new Socket("192.168.30.142",8989);
		
		/*
		 *  * （1）从键盘输入词语
		 * （2）发送给服务器
		 * （3）接收服务器返回的结果
		 * （1）（2）（3）多次进行，直到键盘输入bye并发送给发服务器之后就结束
		 */
		Scanner input = new Scanner(System.in);
		/*
		 * 同样考虑到发送词语，以及词语之间分割问题，那我们选择PrintStream和BufferedReader
		 */
		PrintStream ps = new PrintStream(socket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while(true){
			//从键盘输入词语
			System.out.print("请输入词语：");
			String word = input.next();
			
			//发送给服务器端
			ps.println(word);
			
			if("bye".equals(word)){
				break;
			}
			
			//接收服务器返回的结果
			String result = br.readLine();
			System.out.println("服务器返回的反转后的结果：" + result);
		}
		
		input.close();
		socket.close();
	}
}

```

### 15.5.4 TCP协议编程示例四

**多个客户端**同时连接服务器，连接成功后：

（1）客户端从键盘输入词语，给服务器发送，直到bye为止；

（2）服务器每次手动词语，反转词语 ，然后返回给客户端，直到接收到bye为止

```java
package com.atguigu.test13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 服务器端：
 * 	(1)接收客户端的连接
 *  (2)接收客户端的词语
 *  (3)把词语“反转”返回给客户端
 *  (2)(3)多次，直到客户端发送"bye"为止
 *  
 *  加一个条件，服务器端可以同时接收n个客户端连接
 *  服务器端得加多线程
 */
public class TestServer {
	public static void main(String[] args) throws IOException {
		//1、开启服务器
		ServerSocket server = new ServerSocket(8989);
		
		boolean flag = true;
		while(flag){
			//2、接收一个客户端的连接
			Socket socket = server.accept();//每个客户端的socket是独立的
			
			//为没一个客户端开启一个独立的线程维护它的通信
			MessageHandler mh = new MessageHandler(socket);
			mh.start();
		}
			
		//5、关闭服务器
		server.close();
	}
}
class MessageHandler extends Thread{
	private Socket socket;
	
	public MessageHandler(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run(){
		try {
			//3、先获取输入流和输出流
			InputStream in = socket.getInputStream();
			/*
			 * 因为是接收一个词语，反转一个，返回一个
			 * 那么如果仅仅使用字节流，不好区分词语
			 * 需要用到字符流
			 * 那么就意味着需要把字节流转为字符流
			 */
			InputStreamReader isr = new InputStreamReader(in);//这里不涉及编码问题，仅仅为了转换流的类型
			/*
			 * 字符流中几个字符是一个词语
			 * 那么我们这里选择“换行符”来作为词语的分割
			 * 意味着我们可以按行读取Scanner或BufferedReader
			 */
			BufferedReader br = new BufferedReader(isr);
			
			OutputStream out = socket.getOutputStream();
			/*
			 * 客户端收到字节，同样不方便处理几个字节是一个词语，仍然要把字节输出流转为字符流
			 * 而且字符之间也不好区分，那么也选择“换行符”进行区别词语
			 * 我们现在需要把OutputStream转为一个可以按行写的字符流或其他的处理流
			 * 
			 * 可以按行写的：BufferedWriter(newLine())
			 * 		   PrintStream(println())
			 */
			PrintStream ps = new PrintStream(out);
			
			//从客户端接收词语
			String word;
			while((word = br.readLine()) != null){
				if("bye".equals(word)){
					break;
				}
				
				//如果不是bye，要反转，并且返回
				StringBuilder sb = new StringBuilder(word);
				sb.reverse();
				
				//返回给客户端
				ps.println(sb.toString());
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				//4、断开
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
```

```java
package com.atguigu.test13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * 客户端：
 * （1）从键盘输入词语
 * （2）发送给服务器
 * （3）接收服务器返回的结果
 * （1）（2）（3）多次进行，直到键盘输入bye并发送给发服务器之后就结束
 * 
 * 加一个条件，服务器端可以同时接收n个客户端连接
 * 客户端代码不用修改
 */
public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、连接服务器
		Socket socket = new Socket("192.168.30.142",8989);
		
		/*
		 *  * （1）从键盘输入词语
		 * （2）发送给服务器
		 * （3）接收服务器返回的结果
		 * （1）（2）（3）多次进行，直到键盘输入bye并发送给发服务器之后就结束
		 */
		Scanner input = new Scanner(System.in);
		/*
		 * 同样考虑到发送词语，以及词语之间分割问题，那我们选择PrintStream和BufferedReader
		 */
		PrintStream ps = new PrintStream(socket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		while(true){
			//从键盘输入词语
			System.out.print("请输入词语：");
			String word = input.next();
			
			//发送给服务器端
			ps.println(word);
			
			if("bye".equals(word)){
				break;
			}
			
			//接收服务器返回的结果
			String result = br.readLine();
			System.out.println("服务器返回的反转后的结果：" + result);
		}
		
		input.close();
		socket.close();
	}
}


```

### 15.5.5 TCP协议编程示例五

一个客户端连接服务器，连接成功后，给服务器上传一个文件，服务器接收到文件后存到upload的文件夹中。

```java
package com.atguigu.test14;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

/*
 * 从客户端发送文件到服务器端
 * (1)接收客户端的连接
 * (2)接收文件名.后缀名
 * 思考：
 * 	 存哪里   ①在当前项目中找一个位置存储，例如：upload文件夹
 *  ②如何解决文件名重名的问题         文件名需要处理，加入时间戳或其他的唯一编码的UUID等值
 *  ③.后缀名需要保留，因为它代表文件的类型
 * (3)接收文件内容
 * (4)反馈结果
 * 
 * 思考：
 * 		这里既要接收文件名.后缀名，又要接收文件内容。
 * 		这里既有  文本信息“文件名.后缀名”，又有其他类型的数据“文件内容”，只能选择字节流。
 * 
 * 		如何区别，文件名.后缀名   与  文件内容呢
 * 		想哪种字节输入流，可以处理字符串，和字节类型的数据。
 * 
 * 		FileInputStream
 * 		BufferedInputStream
 * 		DataInputStream
 * 		ObjectInputStream
 * 
 * 		这些里面：DataInputStream：readUTF() 和  read(byte[])
 * 				ObjectInputStream也可以，但是麻烦，我这里选择DataInputStream
 * 
 */
public class TestServer {
	public static void main(String[] args) throws IOException {
		//1、开启服务器
		ServerSocket server = new ServerSocket(9999);
		
		//2、接收客户端的连接
		Socket socket = server.accept();
		
		//3、获取输入流
		InputStream in = socket.getInputStream();
		DataInputStream dis = new DataInputStream(in);
		
		//接收文件名.后缀名
		String fileName = dis.readUTF();
		
		//处理文件名
		/*
		 * 希望我要在服务器存储的文件名：   原来的文件名 + 时间戳
		 */
		long timestamp = System.currentTimeMillis();
		//.的下标
		int index = fileName.lastIndexOf(".");
		//后缀名
		String ext = fileName.substring(index);
		// 原来的文件名
		String name = fileName.substring(0, index);
		//新文件名
		String newFileName = "upload/" + name + timestamp + ext;
		
		//创建文件输出流，把接收到的文件内容，写入新文件
		FileOutputStream fos = new FileOutputStream(newFileName);
		
		//接收文件内容
		byte[] data = new byte[1024];
		int len;
		while((len = dis.read(data))!=-1){
			fos.write(data, 0, len);
		}
		
		//还可以给客户端反馈：文件接收完毕
		OutputStream out = socket.getOutputStream();
		PrintStream ps = new PrintStream(out);
		ps.println("文件接收完毕!");
		
		//断开
		fos.close();
		socket.close();
		server.close();
	}
}

```

```java
package com.atguigu.test14;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * 从客户端发送文件到服务器端
 * 
 * 
 * 客户端：
 * （1）从键盘输入文件的路径名，即选择要发送的文件
 * （2）给服务器先把“文件名.后缀名"
 * （3）发送文件内容
 * （4）接收服务器的反馈结果
 * 
 * 这里同样因为既要发送“文件名.后缀名"，又要发送“文件内容”，选择字节流，选择DataOutputStream
 * 
 */
public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、连接服务器
		Socket socket = new Socket("192.168.30.142",9999);
		
		//2、从键盘输入文件的路径名，即选择要发送的文件
		Scanner input = new Scanner(System.in);
		System.out.print("请选择你要发送的文件（例如：D:/尚硅谷_190513班_柴林燕_JavaSE/开学典礼所发资料.rar）：");
		String fileName = input.nextLine();
		File file = new File(fileName);
		
		//3、给服务器发送“文件名.后缀名"
		OutputStream out = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(out);
		
		//发送“文件名.后缀名"
		dos.writeUTF(file.getName());
		
		//4、发送文件内容
		//先从file文件读取内容
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[1024];
		int len;
		while((len = fis.read(data)) != -1){
			//一边读，一边给服务器发送
			dos.write(data,0,len);
		}
		socket.shutdownOutput();//表示发送完毕了
		
		//5、接收反馈
		InputStream in = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String result = br.readLine();
		System.out.println("结果：" + result);
		
		//6、关闭
		socket.close();
		fis.close();
	}
}

```

### 15.5.6 TCP协议编程示例六

多个客户端连接服务器，连接成功后，给服务器上传一个文件，服务器接收到文件后存到upload的文件夹中。

```java
package com.atguigu.test15;

import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestServer {
	public static void main(String[] args) throws IOException {
		//1、开启服务器
		ServerSocket server = new ServerSocket(9999);
		
		while(true){
			//2、接收客户端的连接
			Socket socket = server.accept();
			
			FileUploadThread ft = new FileUploadThread(socket);
			ft.start();
		
		}
//		server.close();//不关闭服务器
	}
}
class FileUploadThread extends Thread{
	private Socket socket;
	
	public FileUploadThread(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run(){
		try {
			//3、获取输入流
			InputStream in = socket.getInputStream();
			DataInputStream dis = new DataInputStream(in);
			
			//接收文件名.后缀名
			String fileName = dis.readUTF();
			
			//处理文件名
			/*
			 * 希望我要在服务器存储的文件名：   原来的文件名 + 时间戳
			 */
			long timestamp = System.currentTimeMillis();
			//.的下标
			int index = fileName.lastIndexOf(".");
			//后缀名
			String ext = fileName.substring(index);
			// 原来的文件名
			String name = fileName.substring(0, index);
			//新文件名
			String newFileName = "upload/" + name + timestamp + ext;
			
			//创建文件输出流，把接收到的文件内容，写入新文件
			FileOutputStream fos = new FileOutputStream(newFileName);
			
			//接收文件内容
			byte[] data = new byte[1024];
			int len;
			while((len = dis.read(data))!=-1){
				fos.write(data, 0, len);
			}
			
			//还可以给客户端反馈：文件接收完毕
			OutputStream out = socket.getOutputStream();
			PrintStream ps = new PrintStream(out);
			ps.println("文件接收完毕!");
			
			//断开
			fos.close();
			socket.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
```

```java
package com.atguigu.test15;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、连接服务器
		Socket socket = new Socket("192.168.30.142",9999);
		
		//2、从键盘输入文件的路径名，即选择要发送的文件
		Scanner input = new Scanner(System.in);
		System.out.print("请选择你要发送的文件（例如：D:/尚硅谷_190513班_柴林燕_JavaSE/开学典礼所发资料.rar）：");
		String fileName = input.nextLine();
		File file = new File(fileName);
		
		//3、给服务器发送“文件名.后缀名"
		OutputStream out = socket.getOutputStream();
		DataOutputStream dos = new DataOutputStream(out);
		
		//发送“文件名.后缀名"
		dos.writeUTF(file.getName());
		
		//4、发送文件内容
		//先从file文件读取内容
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[1024];
		int len;
		while((len = fis.read(data)) != -1){
			//一边读，一边给服务器发送
			dos.write(data,0,len);
		}
		socket.shutdownOutput();//表示发送完毕了
		
		//5、接收反馈
		InputStream in = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String result = br.readLine();
		System.out.println("结果：" + result);
		
		//6、关闭
		socket.close();
		fis.close();
		input.close();
	}
}

```

### 15.5.7 TCP协议编程示例七

群聊

```java
package com.atguigu.test16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

/*
 * 
 */
public class TestServer {
	private static ArrayList<Socket> online = new ArrayList<Socket>();
	
	public static void main(String[] args) throws IOException {
		//1、开启服务器
		ServerSocket server = new ServerSocket(9999);
		
		while(true){
			//2、接收客户端的连接
			Socket socket = server.accept();
			
			//把这个客户端加入到online中
			online.add(socket);
			
			//每一个客户端独立的线程
			MessageHandler mh = new MessageHandler(socket);
			mh.start();
		}
	}

	//私有的静态的内部类
	//这里用内部类的原因，是为了用上面的online集合
	private static class MessageHandler extends Thread{
		private Socket socket;
		private String ip;
		
		public MessageHandler(Socket socket) {
			super();
			this.socket = socket;
			this.ip = socket.getInetAddress().getHostAddress();
		}

		public void run(){
			//这个客户端的一连接成功，线程一启动，就可以告诉其他人我上线了
			sendToOthers(ip+"上线了");
			
			/*
			 * （1）接收当前的客户端发送的消息
			 * （2）给其他在线的客户端转发
			 */
			//（1）接收当前的客户端发送的消息
			try {
				InputStream in = socket.getInputStream();
				InputStreamReader isr = new InputStreamReader(in);
				BufferedReader br = new BufferedReader(isr);
				
				String content;
				while((content = br.readLine()) !=null){
					if("bye".equals(content)){
						//给自己发一句bye
						OutputStream out = socket.getOutputStream();
						PrintStream ps = new PrintStream(out);
						ps.println("bye");
						
						break;
					}
					
					//收到一句，转发一句
					sendToOthers(ip+"说:" + content);
				}
				
				sendToOthers(ip+"下线了");
			} catch (IOException e) {
				sendToOthers(ip+"掉线了");
			}
		}
		
		//因为转发的代码也很长，独立为一个方法
		public void sendToOthers(String str){
			//遍历所有online的客户端
			Iterator<Socket> iterator = online.iterator();
			while(iterator.hasNext()){
				Socket on = iterator.next();
				if(!on.equals(socket)){//只给其他客户端转发
					try {
						OutputStream out = on.getOutputStream();
						PrintStream ps = new PrintStream(out);
						
						ps.println(str);
					} catch (IOException e) {
						//说明on这个客户端要么下线了，要么掉线了
						iterator.remove();
					}
				}
			}
		}
	}
	
	
}

```

```java
package com.atguigu.test16;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/*
 * 群聊
 */
public class TestClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		//1、连接服务器
		Socket socket = new Socket("192.168.30.142",9999);
		
		//2、开启两个线程，一个收消息，一个发消息
		SendThread st = new SendThread(socket);
		ReceiveThread rt = new ReceiveThread(socket);
		
		st.start();
		rt.start();
		
		//等发送线程停下来再往下走
		try {
			st.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//让接收数据的线程停下
		rt.setFlag(false);
		
		//等接收线程停下来，再往下走，断开连接
		try {
			rt.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		socket.close();
	}
}
class SendThread extends Thread{
	private Socket socket;
	
	public SendThread(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run(){
		try {
			//键盘输入
			Scanner input = new Scanner(System.in);
			OutputStream out = socket.getOutputStream();
			PrintStream ps = new PrintStream(out);
			while(true){
				//从键盘输入
				System.out.print("请输入要发送的消息：");
				String content = input.nextLine();
				System.out.println("content:" + content);
				
				//给服务器发送
				ps.println(content);
				
				//如果bye，就结束发送
				if("bye".equals(content)){
					break;
				}
			}
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
class ReceiveThread extends Thread{
	private Socket socket;
	private boolean flag = true;
	
	public ReceiveThread(Socket socket) {
		super();
		this.socket = socket;
	}
	
	public void run(){
		try {
			InputStream in = socket.getInputStream();
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			
			while(flag){
				String line = br.readLine();
				System.out.println(line);
				if("bye".equals(line)){
					break;
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
}
```

### 15.5.8 UDP协议编程示例

```java
package com.atguigu.test17;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TestSend {
	public static void main(String[] args) throws IOException {
		//1、发送方，建立一个Socket
		//发送方的端口号和IP地址，自动获取
		DatagramSocket ds = new DatagramSocket();
		
		//2、准备把要发送的数据打包
		String str = "马上下课了";
		byte[] bytes = str.getBytes();
		InetAddress ip = InetAddress.getByName("192.168.30.142");
		DatagramPacket dp = new DatagramPacket(bytes,0,bytes.length, ip, 9999);
		
		//3、发送，通过socket发送
		ds.send(dp);
		System.out.println("发送完毕");
		
		//4、关闭
		ds.close();
		
	}
}

```

```java
package com.atguigu.test17;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/*
 * 接收方：
 * 	DatagramPacket(byte[] buf, int length) 
 * 	参数一：用来装数据的字节数组
 *  参数二：数组的长度
 */
public class TestReceive {
	public static void main(String[] args) throws IOException {
		//1、接收方也要socket
		//接收方的端口号，指定，IP自动获取
		DatagramSocket ds = new DatagramSocket(9999);
		
		//2、准备一个数据报，接收数据
		byte[] data = new byte[1024];
		DatagramPacket dp = new DatagramPacket(data,data.length);
		
		//3、接收数据
		ds.receive(dp);
		
		//4、把数据拆解出来
		byte[] bs = dp.getData();//接收的数据
		int len = dp.getLength();//接收的数据的实际长度
		System.out.println(new String(bs,0,len));
		
		//5、断开
		ds.close();
	}
}

```

# 















# 第十六章 反射

使得Java具有动态语言的特征，在运行期间再确定（1）你创建什么类型的对象（2）操作什么属性（3）调用什么方法。。。。

没有使用反射，在编译期间就要确定你创建什么类型的对象，为什么属性赋值，调用什么方法等

```java
Circle c = new Circle();
c.setRadius(1.2);
```

Java不是动态语言，先JavaScript，python等是动态语言，Java是静态语言，需要在编译时确定类型，并且Java是强类型语言，如果没有反射，Java的灵活性就受到了限制。但是在开发中，有的时候在编译时就是无法确定（1）你创建什么类型的对象（2）操作什么属性（3）调用什么方法。。。。那么Java引入了反射机制，使得我们可以模仿动态型。但是和真正的动态语言还是不同的。

反射的根源：Class对象，以及它里面存储的Field、Constructor、Method等对象来实现的。

Class对象是在类型被加载到内存时，在方法区创建的，不是由程序员创建的，而是由JVM帮我们创建的。

`Class` 没有公共构造方法。`Class` 对象是在加载类时由 Java 虚拟机以及通过调用类加载器中的 
`defineClass` 方法自动构造的。

## 16.1 类加载

1、类在内存中的生命周期：加载-->使用-->卸载

2、类的加载又分为三个阶段：

（1）加载：load

就是指将类型的clas字节码数据读入内存

（2）连接：link

①验证：校验合法性等

②准备：准备对应的内存（方法区），创建Class对象，为类变量（静态变量）赋默认值，为静态常量赋初始值。

③解析：把字节码中的符号引用替换为对应的直接地址引用

（3）初始化：initialize（类初始化）即执行<clinit>类初始化方法，大多数情况下，类的加载就完成了类的初始化，有些情况下，会延迟类的初始化。

![1560767438339](../../JavaSE20190513/note/imgs/1560767438339.png)

3、哪些会导致类的初始化？

（1）主方法（main方法）所在的类，要先初始化

（2）第一次使用某个类型就是在new它的对象，此时这个类没有初始化的话，先完成类初始化再做实例初始化

（3）调用某个类的静态成员（类变量和类方法），此时这个类没有初始化的话，先完成类初始化

（4）子类初始化时，发现它的父类还没有初始化的话，那么先初始化父类

（5）通过反射操作某个类时，如果这个类没有初始化，也会导致该类先初始化

> 类初始化执行的是<clinit>()，该方法由（1）类变量的显式赋值代码（2）静态代码块中的代码构成

```java
class Father{
	static{
		System.out.println("main方法所在的类的父类(1)");//初始化子类时，会初始化父类
	}
}

public class TestClinit1 extends Father{
	static{
		System.out.println("main方法所在的类(2)");//主方法所在的类会初始化
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		new A();//第一次使用A就是创建它的对象，会初始化A类
		
		B.test();//直接使用B类的静态成员会初始化B类
		
		Class clazz = Class.forName("com.atguigu.test02.C");//通过反射操作C类，会初始化C类
	}
}
class A{
	static{
		System.out.println("A类初始化");
	}
}
class B{
	static{
		System.out.println("B类初始化");
	}
	public static void test(){
		System.out.println("B类的静态方法");
	}
}
class C{
	static{
		System.out.println("C类初始化");
	}
}
```



4、哪些使用类，但是不会导致类的初始化？

（1）使用某个类的静态的常量（static  final）

（2）通过子类调用父类的静态变量，静态方法，只会导致父类初始化，不会导致子类初始化，即只有声明静态成员的类才会初始化

（3）用某个类型声明数组并创建数组对象时，不会导致这个类初始化

```java
public class TestClinit2 {
	public static void main(String[] args) {
		System.out.println(D.NUM);//D类不会初始化，因为NUM是final的
		
		System.out.println(F.num);
		F.test();//F类不会初始化，E类会初始化，因为num和test()是在E类中声明的
		
		//G类不会初始化，此时还没有正式用的G类
		G[] arr = new G[5];//没有创建G的对象，创建的是准备用来装G对象的数组对象
        //G[]是一种新的类型，是数组类想，动态编译生成的一种新的类型
        //G[].class
	}
}
class D{
	public static final int NUM = 10;
	static{
		System.out.println("D类的初始化");
	}
}
class E{
	static int num = 10;
	static{
		System.out.println("E父类的初始化");
	}
	public static void test(){
		System.out.println("父类的静态方法");
	}
}
class F extends E{
	static{
		System.out.println("F子类的初始化");
	}
}

class G{
	static{
		System.out.println("G类的初始化");
	}
}
```



5、类加载需要类加载器

（1）引导类加载器

​	它负责加载jre/rt.jar核心库

​	它本身不是Java代码实现的，也不是ClassLoader的子类，获取它的对象时往往返回null

（2）扩展类加载器

​	它负责加载jre/lib/ext扩展库

​	它是ClassLoader的子类

（3）应用程序类加载器

​	它负责加载项目的classpath路径下的类

​	它是ClassLoader的子类

（4）自定义类加载器

​	当你的程序需要加载“特定”目录下的类，可以自定义类加载器；

​	当你的程序的字节码文件需要加密时，那么往往会提供一个自定义类加载器对其进行解码

​	后面会见到的自定义类加载器：tomcat中



6、Java系统类加载器的双亲委托模式

简单描述：

​	下一级的类加载器，如果接到任务时，会先搜索是否加载过，如果没有，会先把任务往上传，如果都没有加载过，一直到根加载器，如果根加载器在它负责的路径下没有找到，会往回传，如果一路回传到最后一级都没有找到，那么会报ClassNotFoundException或NoClassDefError，如果在某一级找到了，就直接返回Class对象。

应用程序类加载器  把  扩展类加载器视为父加载器，

扩展类加载器 把 引导类加载器视为父加载器。

不是继承关系，是组合的方式实现的。

## 16.2  javalang.Class类

相关API（1）java.lang.Class（2）java.lang.reflect.*;

1、Class对象是反射的根源。

2、哪些类型可以获取Class对象，可以用代码示例

```java
//（1）基本数据类型和void
例如：int.class
	 void.class
//（2）类和接口
例如：String.class
	Comparable.class
//（3）枚举
例如：ElementType.class
//（4）注解
例如：Override.class
//（5）数组
例如：int[].class

```

3、获取Class对象的四种方式
（1）类型名.class

要求编译期间已知类型

（2）对象.getClass()

只能获取已经存在的对象的运行时类型

（3）Class.forName(类型全名称)

 可以获取编译期间未知的类型

（4）ClassLoader的类加载器对象.loadClass(类型全名称)

可以用自定义加载器对象加载指定路径下的类型

```java
public class TestClass {
	@Test
	public void test05() throws ClassNotFoundException{
		Class c = TestClass.class;
		ClassLoader loader = c.getClassLoader();
		
		Class c2 = loader.loadClass("com.atguigu.test05.Employee");
		Class c3 = Employee.class;
		System.out.println(c2 == c3);
	}
	
	@Test
	public void test03() throws ClassNotFoundException{
		Class c2 = String.class;
		Class c1 = "".getClass();
		Class c3 = Class.forName("java.lang.String");
		
		System.out.println(c1 == c2);
		System.out.println(c1 == c3);
	}
}
```



## 16.3 反射的应用

### 16.3.1 获取类型的详细信息

可以获取：包、修饰符、类型名、父类（包括泛型父类）、父接口（包括泛型父接口）、成员（属性、构造器、方法）、注解（类上的、方法上的、属性上的）

示例代码获取常规信息：

```java
public class TestClassInfo {
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		//1、先得到某个类型的Class对象
		Class clazz = String.class;
		//比喻clazz好比是镜子中的影子
		
		//2、获取类信息
		//（1）获取包对象，即所有java的包，都是Package的对象
		Package pkg = clazz.getPackage();
		System.out.println("包名：" + pkg.getName());
		
		//（2）获取修饰符
		//其实修饰符是Modifier，里面有很多常量值
		/*
		 * 0x是十六进制
		 * PUBLIC           = 0x00000001;  1    1
		 * PRIVATE          = 0x00000002;  2	10
		 * PROTECTED        = 0x00000004;  4	100
		 * STATIC           = 0x00000008;  8	1000
		 * FINAL            = 0x00000010;  16	10000
		 * ...
		 * 
		 * 设计的理念，就是用二进制的某一位是1，来代表一种修饰符，整个二进制中只有一位是1，其余都是0
		 * 
		 * mod = 17          0x00000011
		 * if ((mod & PUBLIC) != 0)  说明修饰符中有public
		 * if ((mod & FINAL) != 0)   说明修饰符中有final
		 */
		int mod = clazz.getModifiers();
		System.out.println(Modifier.toString(mod));
		
		//（3）类型名
		String name = clazz.getName();
		System.out.println(name);
		
		//（4）父类，父类也有父类对应的Class对象
		Class superclass = clazz.getSuperclass();
		System.out.println(superclass);
		
		//（5）父接口们
		Class[] interfaces = clazz.getInterfaces();
		for (Class class1 : interfaces) {
			System.out.println(class1);
		}
		
		//（6）类的属性，  你声明的一个属性，它是Field的对象
/*		Field clazz.getField(name)  根据属性名获取一个属性对象，但是只能得到公共的
		Field[] clazz.getFields();  获取所有公共的属性
		Field clazz.getDeclaredField(name)  根据属性名获取一个属性对象，可以获取已声明的
		Field[] clazz.getDeclaredFields()	获取所有已声明的属性
		*/
		Field valueField = clazz.getDeclaredField("value");
//		System.out.println("valueField = " +valueField);
		
		Field[] declaredFields = clazz.getDeclaredFields();
		for (Field field : declaredFields) {
			//修饰符、数据类型、属性名    
			int modifiers = field.getModifiers();
			System.out.println("属性的修饰符：" + Modifier.toString(modifiers));
			
			String name2 = field.getName();
			System.out.println("属性名：" + name2);
			
			Class<?> type = field.getType();
			System.out.println("属性的数据类型：" + type);
		}
		System.out.println("-------------------------");
		//（7）构造器们
		Constructor[] constructors = clazz.getDeclaredConstructors();
		for (Constructor constructor : constructors) {
			//修饰符、构造器名称、构造器形参列表  、抛出异常列表
			int modifiers = constructor.getModifiers();
			System.out.println("构造器的修饰符：" + Modifier.toString(modifiers));
			
			String name2 = constructor.getName();
			System.out.println("构造器名：" + name2);
			
			//形参列表
			System.out.println("形参列表：");
			Class[] parameterTypes = constructor.getParameterTypes();
			for (Class class1 : parameterTypes) {
				System.out.println(class1);
			}
		}
		System.out.println("=--------------------------------");
		//(8)方法们
		Method[] declaredMethods = clazz.getDeclaredMethods();
		for (Method method : declaredMethods) {
			//修饰符、返回值类型、方法名、形参列表 、异常列表 
			int modifiers = method.getModifiers();
			System.out.println("方法的修饰符：" + Modifier.toString(modifiers));
			
			Class<?> returnType = method.getReturnType();
			System.out.println("返回值类型:" + returnType);
			
			String name2 = method.getName();
			System.out.println("方法名：" + name2);
			
			//形参列表
			System.out.println("形参列表：");
			Class[] parameterTypes = method.getParameterTypes();
			for (Class class1 : parameterTypes) {
				System.out.println(class1);
			}
			
			//异常列表
			System.out.println("异常列表：");
			Class<?>[] exceptionTypes = method.getExceptionTypes();
			for (Class<?> class1 : exceptionTypes) {
				System.out.println(class1);
			}
		}
		
	}
}

```



### 16.3.2  创建任意引用类型的对象

两种方式：

1、直接通过Class对象来实例化（要求必须有无参构造）

2、通过获取构造器对象来进行实例化



方式一的步骤：

（1）获取该类型的Class对象（2）创建对象

方式二的步骤：

（1）获取该类型的Class对象（2）获取构造器对象（3）创建对象

> 如果构造器的权限修饰符修饰的范围不可见，也可以调用setAccessible(true)

示例代码：

```java
public class TestNewInstance {
	@Test
	public void test3()throws Exception{
		//(1)获取Class对象
		Class<?> clazz = Class.forName("com.atguigu.test.Student");
		/*
		 * 获取Student类型中的有参构造
		 * 如果构造器有多个，我们通常是根据形参【类型】列表来获取指定的一个构造器的
		 * 例如：public Student(int id, String name) 
		 */
		//(2)获取构造器对象
		Constructor<?> constructor = clazz.getDeclaredConstructor(int.class,String.class);
		
		//(3)创建实例对象
		// T newInstance(Object... initargs)  这个Object...是在创建对象时，给有参构造的实参列表
		Object obj = constructor.newInstance(2,"张三");
		System.out.println(obj);
	}
	
	@Test
	public void test2()throws Exception{
		Class<?> clazz = Class.forName("com.atguigu.test.Student");
		//Caused by: java.lang.NoSuchMethodException: com.atguigu.test.Student.<init>()
		//即说明Student没有无参构造，就没有无参实例初始化方法<init>
		Object stu = clazz.newInstance();
		System.out.println(stu);
	}
	
	@Test
	public void test1() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
//		AtGuigu obj = new AtGuigu();//编译期间无法创建
		
		Class<?> clazz = Class.forName("com.atguigu.test.AtGuigu");
		//clazz代表com.atguigu.test.AtGuigu类型
		//clazz.newInstance()创建的就是AtGuigu的对象
		Object obj = clazz.newInstance();
		System.out.println(obj);
	}
}
```



### 16.3.3 操作任意类型的属性

（1）获取该类型的Class对象
Class clazz = Class.forName("com.atguigu.bean.User");
（2）获取属性对象
Field field = clazz.getDeclaredField("username");
（3）创建实例对象
Object obj = clazz.newInstance();

（4）设置属性可访问

field.setAccessible(true);

（4）设置属性

field.set(obj,"chai");
（5）获取属性值
Object value = field.get(obj);

> 如果操作静态变量，那么实例对象可以省略，用null表示

示例代码：

```java
public class TestField {
	public static void main(String[] args)throws Exception {
		//1、获取Student的Class对象
		Class clazz = Class.forName("com.atguigu.test.Student");
		
		//2、获取属性对象，例如：id属性
		Field idField = clazz.getDeclaredField("id");
		
		//3、创建实例对象，即，创建Student对象
		Object stu = clazz.newInstance();
		
		//如果id是私有的等在当前类中不可访问access的，我们需要做如下操作
		idField.setAccessible(true);
		
		//4、获取属性值
		/*
		 * 以前：int 变量= 学生对象.getId()
		 * 现在：Object id属性对象.get(学生对象)
		 */
		Object value = idField.get(stu);
		System.out.println("id = "+ value);
		
		//5、设置属性值
		/*
		 * 以前：学生对象.setId(值)
		 * 现在：id属性对象.set(学生对象,值)
		 */
		idField.set(stu, 2);
		
		value = idField.get(stu);
		System.out.println("id = "+ value);
	}
}

```



### 16.3.4 调用任意类型的方法

（1）获取该类型的Class对象
Class clazz = Class.forName("com.atguigu.service.UserService");
（2）获取方法对象
Method method = clazz.getDeclaredMethod("login",String.class,String.class);
（3）创建实例对象
Object obj = clazz.newInstance();
（4）调用方法
Object result = method.invoke(obj,"chai","123);

> 如果方法的权限修饰符修饰的范围不可见，也可以调用setAccessible(true)
>
> 如果方法是静态方法，实例对象也可以省略，用null代替

示例代码：

```java
public class TestMethod {
	@Test
	public void test()throws Exception {
		// 1、获取Student的Class对象
		Class<?> clazz = Class.forName("com.atguigu.test.Student");
		
		//2、获取方法对象
		/*
		 * 在一个类中，唯一定位到一个方法，需要：（1）方法名（2）形参列表，因为方法可能重载
		 * 
		 * 例如：void setName(String name)
		 */
		Method method = clazz.getDeclaredMethod("setName", String.class);
		
		//3、创建实例对象
		Object stu = clazz.newInstance();
		
		//4、调用方法
		/*
		 * 以前：学生对象.setName(值)
		 * 现在：方法对象.invoke(学生对象，值)
		 */
		method.invoke(stu, "张三");
		
		System.out.println(stu);
	}
}
```

### 16.3.5 获取泛型父类信息

示例代码获取泛型父类信息：

1、获取子类的Class对象

2、调用getGenericSuperClass()获取泛型父类

3、强转为ParameterizedType类型

4、调用getActualTypeArguments()获取实际类型参数

```java
/* Type：
 * （1）Class
 * （2）ParameterizedType   
 * 		例如：Father<String,Integer>
 * 			ArrayList<String>
 * （3）TypeVariable
 * 		例如：T，U,E,K,V
 * （4）WildcardType
 * 		例如：
 * 		ArrayList<?>
 * 		ArrayList<? super 下限>
 * 		ArrayList<? extends 上限>
 * （5）GenericArrayType
 * 		例如：T[]
 * 	
 */
public class TestGeneric {
	public static void main(String[] args) {
		//需求：在运行时，获取Son类型的泛型父类的泛型实参<String,Integer>
		
		//（1）还是先获取Class对象
		Class clazz = Son.class;//四种形式任意一种都可以
		
		//（2）获取泛型父类
//		Class sc = clazz.getSuperclass();
//		System.out.println(sc);
		/*
		 * getSuperclass()只能得到父类名，无法得到父类的泛型实参列表
		 */
		Type type = clazz.getGenericSuperclass();
		
		// Father<String,Integer>属于ParameterizedType
		ParameterizedType pt = (ParameterizedType) type;
		
		//（3）获取泛型父类的泛型实参列表
		Type[] typeArray = pt.getActualTypeArguments();
		for (Type type2 : typeArray) {
			System.out.println(type2);
		}
	}
}
//泛型形参：<T,U>
class Father<T,U>{
	
}
//泛型实参：<String,Integer>
class Son extends Father<String,Integer>{
	
}
```

### 16.3.6 读取注解信息

示例代码读取注解信息：

获取类上的注解：

1、获取Class对象

2、调用getAnnot 注解ation（）方法得到注解对象

3、调用注解对象的配置参数的方法获取配置参数值

获取属性上的注解：

1、获取Class对象

2、获取某个属性Field对象

3、调用getAnnot 注解ation（）方法得到注解对象

4、调用注解对象的配置参数的方法获取配置参数值

获取方法上的注解：

1、获取Class对象

2、获取某个方法method对象

3、调用getAnnot 注解ation（）方法得到注解对象

4、调用注解对象的配置参数的方法获取配置参数值

```java
public class TestAnnotation {
	public static void main(String[] args) {
		//需求：可以获取MyClass类型上面配置的注解@MyAnnotation的value值
		
		//读取注解
//		（1）获取Class对象
		Class<MyClass> clazz = MyClass.class;
		
		//（2）获取注解对象
		//获取指定注解对象
		MyAnnotation my = clazz.getAnnotation(MyAnnotation.class);
		
		//（3）获取配置参数值
		String value = my.value();
		System.out.println(value);
	}
}
//声明
@Retention(RetentionPolicy.RUNTIME)  //说明这个注解可以保留到运行时
@Target(ElementType.TYPE) //说明这个注解只能用在类型上面，包括类，接口，枚举等
@interface MyAnnotation{
	//配置参数，如果只有一个配置参数，并且名称是value，在赋值时可以省略value=
	String value();
}

//使用注解
@MyAnnotation("/login")
class MyClass{
	
}
```





















# 第十八章 设计模式

## 18.1 模板设计模式（了解）

1、当解决某个问题，或者完成某个功能时，主体的算法结构（步骤）是确定的，只是其中的一个或者几个小的步骤不确定，要有使用者（子类）来确定时，就可以使用模板设计模式

2、示例代码：计算任意一段代码的运行时间

```java
//模板类
public abstract class CalTime{
    public long getTime(){
        //1、获取开始时间
        long start =  System.currentTimeMills();
        
        //2、运行xx代码：这个是不确定的
        doWork();
        
        //3、获取结束时间
        long end =  System.currentTimeMills();
        
        //4、计算时间差
        return end - start;
    }
    
    protected abstract void doWork();
}
```

使用模板类：

```java
public class MyCalTime extends CalTime{
    protected void doWork(){
        //....需要计算运行时间的代码
    }
}
```

测试类

```java
public class Test{
    public static void main(String[] args){
        MyCalTime my = new MyCalTime();
        System.out.println("运行时间：" + my.getTime());
    }
}
```

## 18.2 单例设计模式

单例：某个类型的对象只有一个唯一的对象

（1）饿汉式

A：新式枚举

```java
public enum Single{
    INSTANCE
}
```

B：老式枚举

```java
public class Single{
    public static 【final】 Single instance = new Single();
    private Single(){
        
    }
}
```

C：标准Javabean形式

```java
public class Single{
    private static 【final】 Single instance = new Single();
    private Single(){
        
    }
    public static Single getInstance(){
        return instance;
    }
}
```

（2）懒汉式

D：同步代码块的形式

```java
public class Single{
    private static Single instance;
    private Single(){
        
    }
    public static Single getInstance(){
        if(instance == null){
            synchronized(Single.class){
                if(instance == null){
                    instance = new Single();
                }
            }
        }
        return instance;
    }
}
```

E：静态内部类形式

```java
public class Single{
    private Single(){
        
    }
    public static  Single getInstance(){
        return Inner.instance;
    }
    
    private static class Inner{
        private static Single instance = new Single();
    }
}
```

## 18.3 工厂设计模式

解决的问题：把对象的创建者与对象的使用者分离，把对象的创建统一到一个地方（工厂）

### 18.3.1 简单工厂模式

示例代码：

```java
interface Car{
	void run();
}

class BMW implements Car{

	@Override
	public void run() {
		System.out.println("让你在宝马车里哭");
	}
	
}
class Benz implements Car{
	@Override
	public void run() {
		System.out.println("奔驰让你坐在引擎盖上哭");
	}
}

class SimpleFactory{
	public static Car getCar(String type){
		switch(type){
		case "宝马":
			return new BMW();
		case "奔驰":
			return new Benz();
		}
		return null;
	}
}
```

如果有反射：简单工厂模式的工厂类可以优化：

```java
class SimpleFactoryPlus{
	/*
	 * type：类型的全名称
	 */
	public static Car getCar(String type) throws Exception{
		//获取Car的实现类的Class对象
		Class clazz = Class.forName(type);
		//创建Car的实现类的实例对象
		Object obj = clazz.newInstance();
		//强转类并返回车的实例对象
		return (Car)obj;
	}
}
```



### 18.3.2 工厂方法模式

示例代码：

```java
interface Car{
	void run();
}

class BMW implements Car{

	@Override
	public void run() {
		System.out.println("让你在宝马车里哭");
	}
	
}
class Benz implements Car{
	@Override
	public void run() {
		System.out.println("奔驰让你坐在引擎盖上哭");
	}
}

//工厂的公共接口
interface Factory{
	Car getCar();
}
class BMWFactory implements Factory{

	@Override
	public BMW getCar() {
		return new BMW();
	}
	
}
class BenzFactory implements Factory{

	@Override
	public Benz getCar() {
		return new Benz();
	}
	
}
```

## 18.4 静态代理模式

静态代理类只能替一个主题接口进行代理工作。

如果主题接口不同，代理工作相同，也需要编写两个代理类。

如果方法不同，代理工作相同，也需要编写多次重复代码。

示例代码：

```java
public class TestProxy {
	@Test
	public void test1(){
		TimeProxy tp = new TimeProxy(new GoodsDAO());
		tp.add();
	}
	@Test
	public void test2(){
		TimeProxy tp = new TimeProxy(new UserDAO());
		tp.add();
	}
}
//主题接口
interface DAO{
	void add();
}
//被代理类
class GoodsDAO implements DAO{
	public void add(){
		System.out.println("商品的添加");
	}
}
class UserDAO implements DAO{
	public void add(){
		System.out.println("用户的添加");
	}
}
//代理类
class TimeProxy implements DAO{
	private DAO target;//target表示被代理者对象

	public TimeProxy(DAO target) {
		super();
		this.target = target;
	}

	@Override
	public void add() {
		long start = System.currentTimeMillis();
		target.add();//核心业务逻辑交还给被代理者
		long end = System.currentTimeMillis();
		System.out.println("时间差：" +(end-start));
	}
	
}
```

## 18.5 动态代理

步骤：

（1）主题接口

（2）被代理类

（3）动态代理的代理工作处理器

要求必须实现：java.lang.reflect.InvocationHandler接口，重写

Object invoke(Object proxy, Method method,Object[] args)

（4）创建代理类对象

java.lang.reflect.Proxy类型的静态方法

newProxyInstance(ClassLoader loader, Class[] interfaces，InvocationHandler h )

（5）调用对用的方法

示例代码：

```java
 /* 步骤：
 * 1、编写主题接口（和静态代理一样）
 * 2、编写被代理类（和静态代理一样）
 * 3、编写代理工作处理器：即代理类要替被代理类做什么事情
 * 要求：必须实现InvocationHandler，重写
 *   Object invoke(Object proxy, Method method, Object[] args)
 *   第一个参数：代理类对象
 *   第二个参数：被代理类和代理类   要执行的方法
 *   第三个参数：要执行方法的实参列表
 *   
 *   这个invoke方法不是程序员调用，当代理类对象执行对应的代理方法时，自动调用的
 *   
 * 4、创建代理类及其对象
 *   需要：Proxy：提供用于创建动态代理类和实例的静态方法，它还是由这些方法创建的所有动态代理类的超类。
 *   
 *    static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) 
 *    第一个参数：被代理类的类加载器，我们希望被代理和代理类使用同一个类加载器
 *    第二个参数：被代理类实现的接口们
 *    第三个参数：代理工作处理器对象
 *    
 * 5、调用被代理的方法   
 */
public class TestProxy2 {
	@Test
	public void test2(){
		//被代理对象
		YongHuDAO sd = new YongHuDAO();
		
		ClassLoader loader = sd.getClass().getClassLoader();//被代理者的类加载器对象
		Class<?>[] interfaces = sd.getClass().getInterfaces();//被代理者实现的接口们
		TimeInvocationHandler h = new TimeInvocationHandler(sd);//代理工作处理器对象
		
		//创建代理类及其对象
		//proxy是代理类的对象，代理类是编译器自动编译生成的一个类
		Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
		
		//这里强转的目的是为了调用增、删、改、查的方法
		//为什么这里强转可以成功了，因为代理类与被代理类实现了相同的主题接口
		DBDAO d = (DBDAO) proxy;
		d.add();
		d.update();
		d.delete();
		d.select();
	}
	
	@Test
	public void test1(){
		//被代理对象
		ShangPinDAO sd = new ShangPinDAO();
		
		ClassLoader loader = sd.getClass().getClassLoader();
		Class<?>[] interfaces = sd.getClass().getInterfaces();
		TimeInvocationHandler h = new TimeInvocationHandler(sd);
		
		//创建代理类及其对象
		//proxy是代理类的对象，代理类是编译器自动编译生成的一个类
		Object proxy = Proxy.newProxyInstance(loader, interfaces, h);
		
		//这里强转的目的是为了调用增、删、改、查的方法
		//为什么这里强转可以成功了，因为代理类与被代理类实现了相同的主题接口
		DBDAO d = (DBDAO) proxy;
		d.add();
		d.update();
		d.delete();
		d.select();
	}
}
//代理工作处理器
class TimeInvocationHandler implements InvocationHandler{
	private Object target;//被代理对象

	public TimeInvocationHandler(Object target) {
		super();
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		long start = System.currentTimeMillis();
		
		//被代理对象的xx方法被调用
		/*
		 * 没有反射：  被代理对象.xx方法(args实参列表)
		 * 有了反射：  方法对象.invoke(被代理对象，args实参列表)
		 */
		Object returnValue = method.invoke(target, args);
		
		long end = System.currentTimeMillis();
		System.out.println(method.getName() + "方法运行时间：" + (end-start));
		
		return returnValue;
	}
	
}


//主题接口
interface DBDAO{
	void add();
	void update();
	void delete();
	void select();
}
//被代理类1
class ShangPinDAO implements DBDAO{
	public void add(){
		System.out.println("添加商品");
	}

	@Override
	public void update() {
		System.out.println("修改商品");
	}

	@Override
	public void delete() {
		System.out.println("删除商品");
	}

	@Override
	public void select() {
		System.out.println("查询商品");
	}
}
//被代理类2
class YongHuDAO implements DBDAO{
	public void add(){
		System.out.println("添加用户");
	}

	@Override
	public void update() {
		System.out.println("修改用户");
	}

	@Override
	public void delete() {
		System.out.println("删除用户");
	}

	@Override
	public void select() {
		System.out.println("查询用户");
	}
}
```



