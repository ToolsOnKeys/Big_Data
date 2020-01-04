# Scala模式匹配

## 1、match

### ①、基本介绍

* Scala中的模式匹配类似于Java中的switch语法，但是更加强大
* 模式匹配中，采用match关键字声明，每个分支采用case关键字进行声明，但需要匹配时，会从第一个case分支开始，如果匹配成功，那么执行对应的逻辑代码，如果匹配不成功，继续执行下一个分支进行判断。如果所有case都不匹配，那么会执行case _ 分支，类似于Java中default语句

```scala
// 模式匹配，类似于Java的switch语法
val oper = '+'
val n1 = 20
val n2 = 10
var res = 0
oper match {
case '+' => res = n1 + n2
case '-' => res = n1 - n2
case '*' => res = n1 * n2
case '/' => res = n1 / n2
case _ => println("oper error")
}
println("res=" + res)
```

### ②、细节和注意事项

* 如果所有case都不匹配，那么会执行case _ 分支，类似于Java中default语句
* 如果所有case都不匹配，又没有写case _ 分支，那么会抛出MatchError
*  每个case中，不用break语句，自动执行对应代码后，然后退出match
* 可以在match中使用其它类型，而不仅仅是字符,可以是表达式

## 2、守卫

### ①、基本介绍

* 如果想要表达匹配某个范围的数据，就需要再模式匹配中增加条件守卫

```scala
for (ch <- "+-3!") { 
var sign = 0
var digit = 0
ch match {
case '+' => sign = 1
case '-' => sign = -1
// 这里可以增加一个if 的判断，这样就可以对某个范围数据进行匹配了.
//  匹配到一个 _ 就不会再匹配的，这个原则和普通的case 是一样的
//  模式匹配 守卫功能
case _ if ch.toString.equals("3") => digit = 3
case _ => sign = 2
}
println(ch + " " + sign + " " + digit)
}
```

* 如果中间存在默认的匹配，那么默认一下的匹配则无效

## 3、模式中的变量

```scala
val ch = 'V'
ch match {
case '+' => println("ok~")
//理解 【1. 把 mychar = ch 2. 执行 println. 3. 这里会匹配所有的字符】
case mychar => println("ok~" + mychar)
case _ => println ("ok~~")
}
```

## 4、类型匹配

```scala
// 类型匹配, obj 可能有如下的类型
val a = 7
val obj = if(a == 1) 1
else if(a == 2) "2"
else if(a == 3) BigInt(3)
else if(a == 4) Map("aa" -> 1)
else if(a == 5) Map(1 -> "aa")
else if(a == 6) Array(1, 2, 3)
else if(a == 7) Array("aa", 1)
else if(a == 8) Array("aa")

val result = obj match {
case a : Int => a
case b : Map[String, Int] => "对象是一个字符串-数字的Map集合"
case c : Map[Int, String] => "对象是一个数字-字符串的Map集合"
case d : Array[String] => "对象是一个字符串数组"
case e : Array[Int] => "对象是一个数字数组"
case f : BigInt => Int.MaxValue
case _ => "啥也不是"
}
println(result)
```

### ①、类型匹配注意事项

* 在Scala中Map[String,Int]和Map[Int,String]是认为是相同的底层
* Array[String]对应String[]而Array[Int]对应int[]，是不同的类型[底层]
* 在进行类型匹配时，编译器会预先检测是否有可能的匹配，如果没有则报错
* 如果 case _ 出现如下情况(忽略匹配的变量值)，则表示隐藏变量名，即不使用,而不是表示默认匹配。

