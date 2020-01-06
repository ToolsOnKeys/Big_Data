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

```scala
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

## 5、集合核心应用

### ①、映射map

```scala
def map[B](f:(A)=>B):HashSet[B]//即对一个集合中的每一个元素遍历，然后每个元素经过f函数的处理，记过返回一个新的集合
//eg：元素全部大写化
List("Alice", "Bob", "Nick").map(_.toUpperCase)//List(ALICE, BOB, NICK)
```

### ②、过滤filter

```scala
List("Alice", "Bob", "Nick").filter(_.startsWith("A"))//List(Alice)
```

### ③、化简reduce

```scala
def reduce(f:(A)=>B)//默认reduceleft，同时有对应的reduceright；对于reduce，即从集合左边开始两两经过f函数处理，将返回值作为一个值，同往右的另一个值开始传入f函数进行处理。
//eg：求一个list集合的所有元素的积
List(1, 2, 3, 4, 5).reduce(_*_)//120
```

### ④、折叠fold

```scala
List(1, 2, 3, 4, 5).reduce(_+_)//底层调用fold
//等价于=》
List(2,3,4,5).fold(1)(_+_)//15
```

### ⑤、扫描scan

```scala
(1 to 5).scan(0)(_+_)   //Vector(0, 1, 3, 6, 10, 15)
```

### ⑥、拉链zip

```scala
(1 to 5).zip(6 to 10)//Vector((1,6), (2,7), (3,8), (4,9), (5,10))
```

### ⑦、迭代器iterator

```scala
//遍历方式一,一次遍历后，需要重新赋值迭代器
val iterator=List(1,2,3,4).iterator
while(iterator.hasNext){... iterator.next() ...}
//遍历方式二，通过变量指向迭代器，通过改变变量的指向迭代器的位置可以实现多次遍历
val iterator1 = List(1,2,3,4).iterator
for(enum <- iterator1){... enum ...}
```

### ⑧、流Stream

```scala
def numsForm(n:BigInt):Stream[BigInt]=n #:: numsForm(n+1)
val stream1 = numsForm(1)//取出第一个元素
stream1.head//取出当前流的头元素
stream1.tail//生成下一个元素
```

### ⑨、视图View（？？？？）

```scala
//懒加载的集合
println((1 to 100).view.filter((a: Int) => a.toString.reverse == a.toString))//SeqViewF(...)
```

### ⑩、其他

#### 1、线程安全的集合

```scala
SynchronizedBuffer
SynchronizedMap
SynchronizedPriorityQueue
SynchronizedQueue
SynchronizedSet
SynchronizedStack
```

#### 2、并行集合parallel

```scala
(1 to 20).par.foreach(println _)//关键.par
```

## 6、隐式转换 implicit

```scala
def main(args: Array[String]): Unit = {
      implicit def f1(d: Double): Int = {
        d.toInt
      }
      implicit def f2(l: Long): Int = {
        l.toInt
      }
      val num: Int = 3.5
      println(num)
      val num2: Int = 4.5
      println(num2)
      val num3: Int = 20l
 } 
```

### ①、丰富类库

```scala
class MySQL{
  def insert(): Unit = {
    println("insert")
  }
}
class DB {
  def delete(): Unit = {
    println("delete")
  }
}
//隐式转换方法
implicit def addDelete(mysql:MySQL): DB = {
      new DB //
}
val mysql = new MySQL
mysql.insert()
mysql.delete() 
```

### ②、隐式值

```scala
implicit val str1: String = "jack"//定义隐式参数
def hello(implicit name: String): Unit = {//方法传参是一个隐式值
println(name + " hello")
}
hello//jack
```

### ③、隐式类

```scala
class MySQL1 { //普通类
  def sayOk(): Unit = {
    println("sayOk")
  }
}

def main(args: Array[String]): Unit = {
    //DB1会对应生成隐式类
    //构造器，可以接受 MySQL1 实例
    implicit class DB1(val m: MySQL1) {
      def addSuffix(): String = { //方法
        m + " scala"
      }
    }
    val mysql1 = new MySQL1 //创建一个 MySQL1
    mysql1.sayOk()
    println(mysql1.addSuffix()) /
  }
```

## 7、模式匹配

### ①、match

```scala
pre match {
case '+' => res = n1 + n2
case '-' => res = n1 - n2
case '*' => res = n1 * n2
case '/' => res = n1 / n2
case _ => println("oper error")
}
```

### ②、守卫

```scala
// 这里可以增加一个if 的判断，这样就可以对某个范围数据进行匹配了.
//  匹配到一个 _ 就不会再匹配的，这个原则和普通的case 是一样的
//  模式匹配 守卫功能
//   这里的 _ 表示 忽略 ch
case _ if ch.toString.equals("3") => digit = 3
// 这里的 _ 表示 默认匹配
case _ => sign = 2
```

### ③、类型匹配

```scala
obj match {
case a : Int => a
case b : Map[String, Int] => "对象是一个字符串-数字的Map集合"
case c : Map[Int, String] => "对象是一个数字-字符串的Map集合"//同b
case d : Array[String] => "对象是一个字符串数组"
case e : Array[Int] => "对象是一个数字数组"
case f : BigInt => Int.MaxValue
case _ => "啥也不是"
```

### ④、数组匹配

```scala
arr match {
case Array(0) => "0"
case Array(x, y) => x + "=" + y
case Array(0, _*) => "以0开头和数组"
case _ => "什么集合都不是"
```

### ⑤、列表匹配

```scala
list match {
case 0 :: Nil => "0" // 匹配？ LIst(0)   【List(0)】
case x :: y :: Nil => x + " " + y //  匹配? 【有两个元素的List】t
case 0 :: tail => "0 ..." // 匹配? 【以0 开头的后面有任意多个 元素的List 】
case _ => "something else"
}
```

### ⑥、元组匹配

```scala
pair match { // 
case (0, _) => "0 ..." // 匹配? 【以0 开头的 对偶元组】
case (y, 0) => y // 匹配? 【以0 结尾的 对偶元组】
case(x, y) => (y,x)
case _ => "other" // 其它
}
```

### ⑦、对象匹配

```scala
object Square {
def unapply(z: Double): Option[Double] = Some(math.sqrt(z)) //对象提取器
def apply(z: Double): Double = z * z
}
// 模式匹配使用：
val number: Double = 36.0
number match {
case Square(n) => println(n)//6
case _ => println("nothing matched")
}
```

### ⑧、变量声明中的模式

```scala
val (x, y) = (1, 2)
val (q, r) = BigInt(10) /% 3  // q = BigInt(10) / 3 r = BigInt(10) % 3 
val arr = Array(1, 7, 2, 9)
val Array(first, second, _*) = arr //模式匹配
println(first, second) // 1, 7
```

### ⑨、样例类

```scala
abstract class Amount
case class Dollar(value: Double) extends Amount 
case class Currency(value: Double, unit: String) extends Amount
case object NoAmount extends Amount 
//说明: 这里的 Dollar，Currencry, NoAmount  是样例类。
```

### ⑩、密封类 type

```scala
type S=String
var v:S="abc"
def test():S="xyz"
```

## 8、函数式编程高级

### ①、偏函数

```scala
val list = List(1, 2, 3, 4, "abc")
val list3 = list.collect(addOne3)
println("list3=" + list3) 
//偏函数
val addOne3= new PartialFunction[Any, Int] {
def isDefinedAt(any: Any) = if (any.isInstanceOf[Int]) true else false
def apply(any: Any) = any.asInstanceOf[Int] + 1
}
//=》化简一：
def f2:PartialFunction[Any,Int]={
    case i:Int=>i+1
}
//=>   list3=list.collect(f2)
//=》化简二：
list3=list.collect({case i:Int=>i+1})
```

### ②、匿名函数

```scala
var f2 = (x:Double)=>3*x
f2(1)//3
```

### ③、高阶函数

```scala
//1
def test(f: Double => Double, n1: Double) = {
f(n1) //
}
//2
def minusxy(x: Int) = {
 (y: Int) => x – y // 匿名函数 
}
```

### ④、闭包

```scala
def makeSuffix(suffix: String) = { //接受字符串
(name: String) => {  //传入一个文件名[可能有后缀，可能没有]
if (name.endsWith(suffix)) name // 如果有，返回原文件名
else name + suffix //如果没有就，拼接该后缀名
}}
```

### ⑤、函数柯里化

```scala
//闭包
def mulCurry(x: Int) = (y: Int) => x * y
//=》函数柯里化
def mulCurry2(x: Int)(y:Int) = x * y
```

### ⑥、控制抽象

```scala
def myRunInThread(f1: () => Unit) = {
      new Thread {
        override def run(): Unit = {
          f1()
        }
      }.start()
    }
//使用方法一：
    myRunInThread {
      () => println("干活咯！5秒完成...")
        Thread.sleep(5000)
        println("干完咯！")
    }
//使用方法二（控制抽象）
    myRunInThread {

        println("干活咯！5秒完成~...")
        Thread.sleep(5000)
        println("干完咯！~")

    }
```

```scala
def until(condition: => Boolean)(block: => Unit): Unit = {
//类似while循环，递归
if (condition) {
block
until(condition)(block)
}
```

## 9、泛型

```scala
// Scala 枚举类型
object SeasonEm extends Enumeration {
  type SeasonEm = Value //自定义SeasonEm，是Value类型,这样才能使用
  val spring, summer, winter, autumn = Value
}
// 定义一个泛型类
class EnglishClass[A, B, C](val classSeason: A, val className: B, val classType: C)
```

## 10、类型约束

### ①、上界

```scala
[T <: A]  表示 【表示T 是A的子类型，或者就是A类型,即不超过 A】
//或用通配符:
[ _ <: A]
//传统方法
class CompareInt(n1: Int, n2: Int) { 
  def greater = if(n1 > n2) n1 else n2
}
//任意类型
class CompareComm[T <: Comparable[T]](obj1: T, obj2: T) {
    def greater = if(obj1.compareTo(obj2) > 0) obj1 else obj2
}
```

### ②、下界

```scala
[T >: A] 
//或用通配符:
[_ >: A]
//    1)对于下界，可以传入任意类型
//    2)传入和Animal直系的，是Animal父类的还是父类处理，是Animal子类的按照Animal处理(编译类型)， 仍然遵守 动态绑定机制
//    3)和Animal无关的，一律按照Object处理[编译类型], 遵守动态绑定机制
//    4)也就是下界，可以随便传，只是处理是方式不一样
//    5)不能使用上界的思路来类推下界的含义
```

### ③、类型约束-视图界定

```scala
def method [A <% B](参数): R = ... 等价于:
def method [A](参数)(implicit viewAB: A => B): R = ... 
或等价于:
implicit def conver(a:A): B = ...

//Int , Float  之间可以相互比较, 使用视图界定
class CompareComm[T <% Comparable[T]](obj1: T, obj2: T) {
  def greater = if(obj1.compareTo(obj2) > 0) obj1 else obj2
}
```

### ④、上下文界定

```scala
//方式1
class CompareComm4[T: Ordering](obj1: T, obj2: T)(implicit comparetor: Ordering[T]) {
    def geatter = if (comparetor.compare(obj1, obj2) > 0) obj1 else obj2
}
//方式2,将隐式参数放到方法内
class CompareComm5[T: Ordering](o1: T, o2: T) {
    def geatter = {
        def f1(implicit cmptor: Ordering[T]) = cmptor.compare(o1, o2)
        if (f1 > 0) o1 else o2
    }}
//方式3,使用implicitly语法糖，最简单(推荐使用)
class CompareComm6[T: Ordering](o1: T, o2: T) {
  def geatter = {
    //这句话就是会发生隐式转换，获取到隐式值 personComparetor
    val comparetor = implicitly[Ordering[T]]
    if(comparetor.compare(o1, o2) > 0) o1 else o2
  }}
```

### ⑤、协变、逆变、不变

```scala
C[+T]：如果A是B的子类，那么C[A]是C[B]的子类，称为协变 
C[-T]：如果A是B的子类，那么C[B]是C[A]的子类，称为逆变 
C[T]：无论A和B是什么关系，C[A]和C[B]没有从属关系。称为不变.

object CovariantDemo {
  def main(args: Array[String]): Unit = {
     val t1: Temp3[Super] =  new Temp3[Super]("hello")

    //当我们在定义 Temp3[+A]， 那么 在使用时，就会发生协变
    // Temp3[Sub] 也是 Temp3[Super] 子类型
    // val t3: Temp3[Super] =  new Temp3[Sub]("hello")

    //当我们在定义 Temp3[-A]， 那么 在使用时，就会发生逆变
    // Temp3[Super] 也是 Temp3[Sub] 子类型
    val t4: Temp3[Sub] =  new Temp3[Super]("hello")
    
    //当我们在定义 Temp3[A]， 那么 在使用时，就不会发生逆变，和协变，称为不变
  }
}


class Temp3[-A](title: String) { //Temp3[+A] //Temp[-A]
  override def toString: String = {
    title
  }
}
//支持协变
class Super // 父类
class Sub extends Super //Sbu 是 Super子类
```

