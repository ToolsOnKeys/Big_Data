# Scala总结

## 1、流程控制

### ①、for系列

```scala
//To 包含10
for(i<- 0 to 10){...}
//until 不包含10
for(i<- 0 until 10){...}
//循环守卫
for(i<- 0 to 10 if i != 3){...}
//循环步长
for(i<- 0 to 10 by 2){...}
//嵌套循环
for(i<- 0 to 10,j<- 0 to 10){...}
## 等价于
for(i<- 0 to 10){
    for(j<- 0 to 10){...}
}
//引入变量
for(i<- 0 to 10,j=4-i){..j可以被使用..}
//循环返回值,返回类型是Vector集合
for(i<- 0 to 10) yield i
//倒序打印
for(i<- 0 to 10 reverse){...}
```

### ②、While系列

```scala
//1
while(condition){...}
//2
do{...}while(condtion)
```

### ③、循环中断

```scala
//1、采用异常中断的方式退出循环
try{
    for(condition1){
        ....
        if(condition2) throw new Exception
        ....
    }
}catch{
    case ex：Exception => ...
}
//2、使用breakable+break()
breakable{
    for(condition1){
        ...
        if(condition2) break()
    }
}
```

## 2、函数式编程几个概念

### ①、过程

* 即无返回值，返回类型为Unit

### ②、惰性函数

```scala
def function(res){...}
//关键字lazy，对于function方法，只有再次调用f的时候，才会执行function的方法体内容
lazy val f=function(res)
```

### ③、异常

```scala
try{
    ...
}catch{
    case ex:..Exception=>...
    case ex:....
}finally{
    ....
}
```

## 3、面向对象编程

### ①、类的定义

```scala
//1、
class Cla{
    var a1:String=_
    //如果属性添加 @BeanProperty 修饰，则会为该属性生成get和set方法
    @BeanProperty var a2:Int=_
    def f1(..){...}
    def this(pa1:String){
        this()
        a1=pa1
    }
    ...
}
//2
class Cla(pa1:String,pa2:Int){
    var a1:String=pa1
    var a2:Int=pa2
    def this(){
        this("moren",1)
    }
    ...
}
```

### ②、包

```
//多层级包
package com{
....
    package dh{
  ....
       import com.zyh
       new zheng()
       
    }
    package zyh{
        .....
        class zheng{...}
    }
}
/*权限控制
   1) 默认的 就在任何位置可以使用， 通过提供 public 的方法实现 
   2) protected 在本类，子类和伴生对象可以使用 
   3) private 在本类和伴生对象可以使用 
*/
//包对象
package com{
    package object dh{//包对象
        var name:String="ding"
        def sayOk(){...}
    }
    package dh{
        class zyh{
            ...
            def love(){
                sayOK() //包对象中的属性、方法均可以使用
            }
        }
    }
}
```

### ③、伴生对象

```scala
class A(pname:String){//伴生类
    var name:String=pname
    ....
}
object A{//伴生对象，其中属性都是静态的
    ...
    def apply(pname:String):A={new A(pname)}//此方法可以使得创建对象时不需要new
}
```

### ④、单例模式

```scala
//1、懒汉式
class SingleTon private(){...}
object SingleTon{
    private var s:SingleTon=null
    def getInstance={
        if(s==null) s=new SingleTon 
        else s
    }
}
//2、饿汉式
class SingleTon private(){}
object SingleTon{
    private val s:SingleTon=new SingleTon
    def getInstance={s}
}
```

### ⑤、特质（trait）

```scala
trait tezhi{//特质中的方法可以时实现的，也可以是为实现的
    def fun1()
    def fun2(){...}
}
//没有父类
class Cla extend 特质1 with 特质2 with 特质3 ...
//有父类
class Cla extend 父类 with 特质1 with 特质2...
//特质叠加，如果有super.fun时，从右往左追溯
```

## 4、集合基础

### ①、数组Array

```scala
//定长数组
var arr01 = new Array[Int](4)//长度为4的定长数组，默认都为0
var arr02 = Array(1,3,"ss")//定义定长数组的时候就赋值
arr01(1)=4//只允许修改值和遍历值
//变长数组
var arr11 = ArrayBuffer[Any](2,3,2)//apply伴生对象创建变长数组
arr11.append(elems:A*)//追加元素，一个或多个
arr11(1)=10//修改
arr11.remove(0)//删除第一个元素
//定长数组和变长数组之间的转换
arr01.toBuffer
arr11.toArray
//多维数组
var arr21 = Array.ofDim[Double](3,4)//定义个3行4列的二维数组
```

#### Scala数组和Java的List的互相转换

```scala
//Scala集合=》Java集合
var arr = ArrayBuffer(1,2,3)
import scala.collection.JavaConversions.bufferAsJavaList
var javaArr = new ProcessBuilder(arr)
var arrList = javaArr.command()
//Java集合=》Scala集合
var arrList = new java.util.ArrayList[String]()
import scala.collection.mutable
var scalaArr:mutable.Buffer[String] = arrList
```

### ②、元组Tuple

```scala
var tuple1 = (1,2,"dh",4)//元组中元素最多只有22个
tuple1._1//第一个元素
tuple1._2//第二个元素
tuple1.productElement(0)//第一个元素
for(item<-tuple1.productIterator){...}//元组的遍历
```

### ③、列表List

```scala
//定长List
var list01 = List(1,2,3)//创建定长列表时时直接列表元素
var list03 = Nil //创建空集合
list01(1)//列表的第二个值
var list04 = list1 :+ 4//后插值
var list05 = 5 +: list1//前插值
var list06 = 1 :: 2 :: list1
var list07 = 1 :: 2 :: list1 ::: Nil
//变长List
var list11 = ListBuffer[Int](1,2,3)//指定类型的变长列表
list11(1)=2//列表的第二个元素，直接修改
list11 += 3 //新增元素
list12 = list11 ++ list1//追加list
list11.append(4)//追加
var list13 = list11 :+ 5 //追加
list11.remove(1)//删除第二个元素
for(item<-list11){...}//遍历
```

### ④、队列Queue

```scala
var q1 = new mutable.Queue[Int]//创建一个空队列
q1+=20//队尾增加一个元素
q1 ++= List(1,2,3,4)//队尾添加列表中的所有元素
q1 += List(1,2,3,4)//队尾添加一个元素，此元素是一个有四个元素的列表
q1.dequeue()//取出队头的元素
q1.enqueue(2,3)//队尾添加两个元素
q1.head//返回队列头元素
q1.last//返回队列尾元素
q1.tail//返回除头元素外的队列
```

### ⑤、映射Map

```scala
//定义
var map1 = Map("dh"->24,"zyh"->18)//定义一个不可变的map映射，顺序即定义顺序
var map2 = scala.collection.mutable.Map("dh"->24,"zyh"->19)
var map3 = scala.collection.mutable.Mao[String,Int]//空映射
var map4 = mutable.Map(("dh",24),("zyh",18))//对偶元组方式，等价于map2
//取值
map1("dh")//key为dh的value，如果不存在，报出异常
map1.contains("dh")//判断dh是否存在，存在返回true，反之false
map1.get("dh")//存在返回一个Some结构，不存在返回None
map1.get("dh").get//存在返回值，不存在报出异常
map1.getOrElse("dh","默认值")//dh存在，返回dh对应的value，否则返回默认值
//修改
map1("dh")=25//修改dh的value
map2 += ("dz"->20,"zd"->16)//变长映射中添加元素
var map5 = map2 + ("dz"->20)
map2 -= ("dz","zd")//删除元素，有则删除，无则不影响
//遍历
for((k,v)<-map2){...}
for(k<-map2.keys){...}
for(v<-map2.values){...}
for(v<-map2){...}//v._1即key，v._2即value
```

### ⑥、集Set

```scala
//定义
var set01 = Set(1,2,3)//不可变
var set11 = mutable.Set(1,2,3)//可变
//添加元素,不存在则添加，存在则不添加
set11.add(4)
set11+=4
set11.+=4
//删除元素,有则删除，无则不影响
set11 -=4
set11.-=4
set11.remove(4)
//遍历
for(x<-set11){...}
```



