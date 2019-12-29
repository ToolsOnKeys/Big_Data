# Scala基本语法

## 1、流程控制

### ①、分支控制if-else

* 让程序有选择地执行，分支控制有三种：单分支、双分支、多分支

#### Ⅰ、单分支

```scala
if(条件表达式){
    执行代码块
}
```

#### Ⅱ、双分支

```scala
if (条件表达式) {
	执行代码块1
} else {
    执行代码块2
}
//注意：scala没有三元运算符 ?:
//     但是可以使用if()""else""处理【不可跨行】
```

#### Ⅲ、多分支

```scala
if (条件表达式1) {
	执行代码块1
}
else if (条件表达式2) {
	执行代码块2
}
   ……
else {
	执行代码块n
}
```

#### Ⅳ、嵌套分支

```scala
if(条件表达式1){
    if(条件表达式2){
        执行代码块1
    }else{
        执行代码块2
    }	
}
```

### ②、Switch分支结构

* 在Scala中没有Switch，而是使用模式匹配来处理。
* 后面讲解

### ③、For循环控制

* Scala也为for循环这一常见地控制结构提供了非常多地特性，这些for循环地特性被称为for推导式或for表达式

#### Ⅰ、范围数据循环（To）

```scala
for(i <- 1 to 3){
    print(i + " ")
}
//i表示循环地变量，<- 规定 to
//i将会从1-3循环，前后闭合
```

#### Ⅱ、范围数据循环（Until）

```scala
for(i <- 1 until 3) {
    print(i + " ")
}
//这种方式和前面地区别在于i是从1到3-1
//即 前闭合后开地范围
```

#### Ⅲ、循环守卫

```scala
for(i <- 1 to 3 if i != 2) {
    print(i + " ")
}
//循环守卫，即循环保护式（也称为条件判断式，守卫）。保护式为true则进入循环体内部，为false则跳出，类似于continue
//以上代码等价于，如下：
for (i <- 1 to 3){
	if (i != 2) {
		print(i + " ")
	}
}
```

#### Ⅳ、循环步长

```scala
for (i <- 1 to 10 by 2) {
    println("i=" + i)
}
//by 表示步长
```

#### Ⅴ、循环嵌套

```scala
for(i <- 1 to 3; j <- 1 to 3) {
    println(" i =" + i + " j = " + j)
}
//等价于
for (i <- 1 to 3) {
    for (j <- 1 to 3) {
        println("i =" + i + " j=" + j)
    }
}
```

#### Ⅵ、引入变量

```scala
for(i <- 1 to 3; j = 4 - i) {
    println("i=" + i + " j=" + j)
}
//for推导式一行中有多个表达式时，所以要加分号；来隔断逻辑
//for推导式有一个不成文地规定：当for推导式仅包含单一表达式时使用圆括号，当包含多个表达式时，一般每行一个表达式，并用花括号代替圆括号，即如下：
for {
    i <- 1 to 3
j = 4 - i
} {
    println("i=" + i + " j=" + j)
}
//同时以上代码等价如下
for (i <- 1 to 3) {
    var j = 4 - i
    println("i=" + i + " j=" + j)
}
```

#### Ⅶ、循环返回值

```scala
var res = for(i <-1 to 10) yield {
            i * 2
        }
println(res)
//Vector(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//将遍历过程中处理地结果返回到一个新Vector集合中，使用yield关键字。开发中使用较少
```

#### Ⅷ、倒序打印

```scala
for(i <- 1 to 10 reverse){
    print(i)
}
//10987654321
//倒序打印
```

### ④、While和do...While循环控制

* While和do...While的使用和Java语言中用法相同

### ⑤、循环中断

* 基本说明：Scala内置控制结构特地去掉了break和continue，是为了更好的适应函数式编程，推荐使用函数式的风格解决break和continue的功能，而不是一个关键字。Scala中使用breakable在控制结构中实现break和continue功能。

### ⑥、多重循环

