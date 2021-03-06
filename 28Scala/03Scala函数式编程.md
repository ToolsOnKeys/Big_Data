# Scala函数式编程

## 1、简介

### ①、面向对象编程

* 解决问题，分解对象，行为，属性，然后通过该对象的关系以及行为的调用来解决问题。

* 对象：用户
* 行为：登陆，连接JDBC、读取数据库
* 属性：用户名，密码
* Scala语言式一个完全面向对象编程语言。万物皆对象。
* 对象的本质：对数据和行为的一个封装

### ②、函数式编程

* 解决问题时，件为体分解成一个一个的步骤，将每个步骤进行封装（函数），通过调用这些封装好的步骤，解决问题。

* eg：请求->用户名、密码->连接JDBC->读取数据库
* Scala语言是一个完全函数式编程语言
* 函数的本质：函数可以当做一个值进行传递

### ③、在Scala中函数式编程和面向对象编程完美的融合在一起了

## 2、函数基础

### ①、函数基本语法

```scala
def sum(x:Int,y:Double):Int = {
    函数体
}
```

### ②、函数和方法的区别

#### Ⅰ、核心概念

* 未完成某一个功能的程序语句的集合，称为函数
* 类中的函数称之为方法

#### Ⅱ、实例说明

* Scala语言可以在任意的语法结构中声明任何的语法
* 函数没有重载和重写的概念；方法可以进行重写和重载
* Scala中函数看可以嵌套定义（如何使用？？）

#### Ⅲ、函数定义

* 函数定义

  ```
  （1）函数1：无参，无返回值
  （2）函数2：无参，有返回值
  （3）函数3：有参，无返回值
  （4）函数4：有参，有返回值
  （5）函数5：多参，无返回值
  （6）函数6：多参，有返回值
  ```

* 实操

  ```scala
  package com.atguigu.chapter05
  object TestFunctionDeclare {
      def main(args: Array[String]): Unit = {
          // 函数1：无参，无返回值
          def test1(): Unit ={
              println("无参，无返回值")
          }
          test1()
          // 函数2：无参，有返回值
          def test2():String={
              return "无参，有返回值"
          }
          println(test2())
          // 函数3：有参，无返回值
          def test3(s:String):Unit={
              println(s)
          }
          test3("jinlian")
          // 函数4：有参，有返回值
          def test4(s:String):String={
              return s+"有参，有返回值"
          }
          println(test4("hello "))
          // 函数5：多参，无返回值
          def test5(name:String, age:Int):Unit={
              println(s"$name, $age")
          }
          test5("dalang",40)
      }
  }
  ```

#### Ⅳ、函数参数

* 实操

  > 1、可变参数
  >
  > 2、如果参数列表中存在多个参数，那么可变参数一般防止在最后
  >
  > 3、参数默认值，一般酱油默认值的参数放置在参数列表的后面
  >
  > 4、带参数名

#### Ⅴ、函数至简原则

* return可以省略，Scala会使用函数体的最后一行代码作为返回值
* 如果函数体只有一行代码，可以省略花括号
* 返回值类型如果能够推断出来，那么可以省略（冒号：和返回值类型都可以一起省略）
* 如果有return，则不能省略返回值类型，必须指定
* 如果函数明确声明unit，那么及时函数体中使用return关键字也不起作用
* Scala如果期望时无返回值类型，可以省略等号
* 如果函数无参，但是声明了参数列表，那么调用时，小括号，可加可不加
* 如果函数没有参数列表，那么小括号可以省略，调用时小括号必须省略
* 如果不关心名称，只关心逻辑处理，那么函数名（def）可以省略

## 3、函数高级

### ①、高阶函数

* 函数可以作为值进行传递
* 函数可以作为参数进行传递
* 函数可以作为含税返回值返回

### ②、匿名函数

* 没有名字的函数就是匿名函数

* (x:Int)=>{函数体}

* x:表示输入参数类型；Int：表示输入参数类型；函数体：表示具体代码逻辑

* 传递匿名函数至简原则：

  ```
  1、参数类型可以省略，会根据形参进行自动的推导
  2、类型省略之后，发现只有一个参数，则圆括号可以省略；其他情况：没有参数和参数超过1的永远不能省略圆括号。
  3、匿名函数如果只有一行，则大括号可以省略
  4、如果参数只出现一次，则参数省略且后面参数可以用_代替
  ```

### ③、高阶函数案例

### ④、函数柯里化&闭包

* 如果一个函数，访问到了他的外部（局部）变量的值，那么这个函数和他所处的环境称之为闭包
* 函数柯里化：把一个参数列表的多个参数变成多个参数列表

### ⑤、递归

* 一个函数/方法体内有调用了本身，我们称之为递归调用。

### ⑥、控制抽象

* 值调用：把计算后的值传递下去
* 名调用：把代码传递下去

### ⑦、惰性函数

* 当函数返回值被声明为lazy时，函数的执行将被推迟，直到我们首次对此取值，该函数才会执行。这种函数我们称之为惰性函数。