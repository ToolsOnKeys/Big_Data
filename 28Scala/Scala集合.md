# Scala集合基础

## 1、数据结构特点

### ①、Scala集合基本介绍

* Scala同时支持不可变集合和可变集合
* 两个主要的包：不可变集合（scala.collection.immutable）和可变集合（scala.collection.mutable）
* Scala默认采用不可变集合，对于几乎所有的集合类，Scala都同时提供了可变和（mutable）和不可变（immutable）
* Scala的集合有三大类：序列Seq、集Set、映射Map，所有的集合都扩展自Iterable特质，在Scala中集合有可变（mutable）和不可变（immutable）两种类型。

### ②、可变集合和不可变集合

* 不可变集合：Scala不可变集合，就是这个集合本身不能动态变化。（类似于Java的数组，是不可以动态增长的）
* 可变集合：集合本身可以动态变化的

### ③、Scala不可变集合说明

* Set、Map时Java中也有的集合
* Seq时Java没有的，我们发现List归属到Seq了，因此这里的List就和Java不是同一个概念了
* 我们前面的for循环有一个1to3，就是IndexedSeq下的Vector
* String也是属于IndexedSeq
* 我们发现经典的数据结构化如Queue和Stack被归刷到LinearSeq
* Scala中的Map体系有一个SortedMap，说明Scala的Map可以支持排序
* IndexSeq和LinearSeq的区别：IndexSeq时通过索引来查找和定位，因此速度快，比如String实时一个索引集合，通过索引即可定位；LineaSeq时线性的，即有头尾的概念，这种数据结构一般就是通过遍历来查找的，他的价值在于应用到一些业务场景。

### ④、Scala可变集合说明

* 可变集合和不可变集合体系大致相同
* Seq增加了Buffer体系，可以直接动态的修改 集合
* 增加了并发同步的集合体系 以Synchronized开头的。

## 2、数组-定长数组

* var arr1 = new Array\[Int](10)  //定义
* var arr2 = Array(1,2,3,4) //伴生对象形式定义
* arr1(1)=7 //赋值
* for(i<-arr1) println(i) //遍历

## 3、数组-变长数组

* var arr = ArrayBuffer\[Int]() //定义
* var arr = ArrayBuffer\[Int](1,2,3,4,5)//定义
* arr.append(7，4，5) //添加值（支持多元素）
* arr(0)=7 //重新赋值
* arr.remove(n)//删除第n+1个元素

## 4、定长数组和变长数组转换

* arr.toBuffer //把定长数组转成可变数组返回结果，arr本身无变化
* arr.toArray //把可变数组转成定长数组返回结果，arr本身无变化

## 5、数组-多维数组

* var arr = Array.ofDim\[Double](3,4) //创建一个arr的二维数组，有三个元素（一维数组），同时每个元素（一维数组）存放4个值
* arr(1)(1)=11 //赋值

## 6、数组-Scala数组和Java的List的互转

```Scala
// Scala集合和Java集合互相转换
val arr = ArrayBuffer("1", "2", "3")
import scala.collection.JavaConversions.bufferAsJavaList
val javaArr = new ProcessBuilder(arr) //为什么可以这样使用?  可以说明一下原理
val arrList = javaArr.command()
println(arrList) //输出 [1, 2, 3]
```

## 7、Java的List转Scala数组（mutable.Buffer）

```Scala
import scala.collection.JavaConversions.asScalaBuffer
import scala.collection.mutable
// java.util.List ==> Buffer 
val arrList = new util.ArrayList[String]()
val scalaArr: mutable.Buffer[String] = arrList
scalaArr.append("jack")
println(scalaArr)
```

## 8、元组 Tuple-元组

### ①、基本介绍

* 元组也是可以理解为一个容器，可以存放各种相同或不同类型的数据
* 说的简单点，就是将多个无关的数据封装为一个整体，称为元组，最大的特点灵活，对数据没有过多的约束
* 元组中最多只能有22个元素，放入的元素数据个数不同，就对应不同类型的元组

### ②、元组数据的访问

* 访问元组中的数据，可以采用顺序号（_顺序号），也可以通过索引（productElement）访问

  ```scala
  val t1 = (1, "a", "b", true, 2)
  println(t1._1) //访问元组的第一个元素 ，从1开始
  println(t1.productElement(0)) // 访问元组的第一个元素，从0开始
  ```

* 遍历

  ```scala
  for(item<-t1.productIterator){println(item)}
  ```

## 9、列表List-创建List

### ①、基本介绍

* Scala中的List和Java List不一样，在Java中List时一个接口，真正存放数据是ArrayList，而Scala的List可以直接存放数据，就是一个object，默认情况下Scala的List是不可变的，List属于Seq。

  ```scala
  val list01 = List(1, 2, 3) //创建时，直接分配元素
  println(list01)
  val list02 = Nil  //空集合
  println(list02)
  ```

* List默认是不可变集合
* List在Scala包对象声明的，因此不需要引入其他包也可以使用
* var List=scala.collection.immutable.List
* var Nil=scala.collection.immutable.Nil
* List中可以存放任意的数据类型，比如 类型为List[Any]
* 如果希望得到一个空列表，可以使用Nil对象，在Scala包对象声明的，因此不需要引入其他包就可以使用

#### ②、列表List-List元素

* 向列表中添加元素，会返回新的列表。集合对象。注意：Scala中List元素的追加形式非常独特，和Java不一样。

* 符号::表示想集合中添加元素（将添加的元素当作整体）；运算时，集合对象一定要放置在最右边；运算规则，从右到左；符号:::运算符事件集合中的每一个元素加入到空集合中去。

  ```scala
  var list1 = List(1, 2, 3, "abc")
  // :+运算符表示在列表的最后增加数据
  val list2 = list1 :+ 4
  // +: 运算符表示在列表的最前增加数据
  val list2 = 4 +: list1
  val list5 = 4 :: 5 :: 6 :: list1 :: Nil
  //[4,5,6,1，2，3，"abc"]
  val list7 = 4 :: 5 :: 6 :: list1 ::: Nil
  //[4,5,6,1，2，3，"abc"]
  ```

### ③、ListBuffer

```scala
val lst0 = ListBuffer[Int](1, 2, 3)//定义
println("lst0(2)=" + lst0(2))//取值
for (item <- lst0) {//遍历
println("item=" + item)
}
val lst1 = new ListBuffer[Int]//定义
lst1 += 4//添加数据
lst1.append(5)//末尾添加
lst0 ++= lst1//在lst0集合后面添加lst1返回给lit0
val lst2 = lst0 ++ lst1 //lst0集合加上lst1集合，结果返回给lst2
val lst3 = lst0 :+ 5 //lst0添加元素5，结果返回给lst3
println("=====删除=======")
println("lst1=" + lst1)
lst1.remove(1)//删除第一个值
for (item <- lst1) {
println("item=" + item)
}
```

## 10、队列Queue

### ①、队列的说明

* 队列是一个有序列表，在底层可以用数组或是链表实现

* 其输入和输出要准寻先入先出的元组。即：先存入队列的数据，要先取出，后存入的要后取出

* 在Scala中，有设计者直接给我们提供队列类型使用

* 在Scala中，有scala.collection.mutable.Queue和scala.collection.immutable.Queue，一般来说，我们在开发中通常使用可变集合中的队列

  ```scala
  val q1 = new Queue[Int]
  q1 += 20 //  
  println(q1)
  q1 ++= List(2,4,6) // 
  println(q1)
  //q1 += List(1,2,3) //类型为Any才ok
  println(q1)
  注意：++=  和 += 的区别
  val q1 = new mutable.Queue[Int]//
  q1 += 12
  q1 += 34
  q1 ++= List(2,9)
  q1.dequeue() //取出队列头的第一个元素 
  println(q1)
  q1.enqueue(20,60) //将20和60加入到队列尾
  println(q1)
  println(q1.head) //返回队列头的数据(元素),但是不从队列删除 
  println(q1.last) //对队列本身没有任何影响
  println(q1.tail) //仍然对队列本身没有影响
  println(q1.tail.tail)//即：返回除了第一个以外剩余的元素， 可以级联使用，这个在递归时使用较多。
  ```

## 11、映射-Map

### ①、Java中的Map回顾

* HashMap是一个散列表，他存储的内容是
* 键值队（k-v），Java中的HashMap是无序的，key不能重复

### ②、Scala中的Map介绍

* Scala中的Map和Java类似，也是一个散列表，他存储的内容也是键值对（k-v）映射，Scala中不可变的Map是有序的，可变的Map是无序的。
* Scala中，有可变的Map（scala.collection.mutable.Map）和不可变的Map(scala.collection.immutable.Map)

```scala
//#####不可变Map
val map1 = Map("Alice" -> 10, "Bob" -> 20, "Kotlin" -> "北京")
//1、输出顺序和声明顺序一致
//2、构建Map集合中，集合中的元素其实是Tuple2类型
//3、默认情况下（即没有引入其他包的情况下），Map是不可变的
//#####可变Map
val map2 = scala.collection.mutable.Map("Alice" -> 10, "Bob" -> 20, "Kotlin" -> 30)
//1、输出顺序和声明顺序不一致
//#####创建空映射
val map3 = new scala.collection.mutable.HashMap[String, Int]
//#####对偶元组
val map4 = mutable.Map( ("A", 1), ("B", 2), ("C", 3),("D", 30))
```

### ③、map取值

```scala
#### 方式一-使用map(key)
var value1 = map("Alice")
//1)	如果key存在，则返回对应的值
//2)	如果key不存在，则抛出异常[java.util.NoSuchElementException]
//3)	在Java中,如果key不存在则返回null

#### 方式二-使用contains方法检查是否存在key
map2.contains("B")
//如果key存在，返回true，不存在返回false
//推荐和方式一组合使用

#### 方式三-使用map.get(key).get
var map4 = mutable.Map( ("A", 1), ("B", "北京"), ("C", 3) )
println(map4.get("A")) //返回?
println(map4.get("A").get) //得到Some在取出
//1、map.get方法会对数据进行包装
//2、如果map.get(key) key存在返回some，如果key不存在，则返回None
//3、如果map.get(key).get key存在，如果key对应的值，否则抛出异常java.util.NoSuchElementException

#### 方式四-map4.getOrElse()取值
map4.getOrElse("A","默认")
//1、key存在，返回值
//2、key不存在，返回默认值
```

### ④、映射 Map-map的修改、添加和删除

```scala
#### 更新map的元素
val map4 = mutable.Map( ("A", 1), ("B", "北京"), ("C", 3) )
map4("A") = 20 //修改和增加
println(map4)
//1、map是可变的，才能修改，否则报错
//2、如果key存在：则修改对应的值，key不存在，等价于添加一个k-v

#### 添加map的元素
val map4 = mutable.Map( ("A", 1), ("B", "北京"), ("C", 3) )
map4 += ( "D" -> 4 )
map4 += ( "B" -> 50 )
val map5 = map4 + ("E"->1, "F"->3)
map4 += ("EE"->1, "FF"->3)  
println(map4)

#### 删除map元素
val map4 = mutable.Map( ("A", 1), ("B", "北京"), ("C", 3) )
map4 -= ("A", "B")//删除key为”A“和”B“的键值对
```

### ⑤、Map-对map的遍历

* 对map元素（元组Tuple2对象）进行遍历的方式很多

```scala
val map1 = mutable.Map( ("A", 1), ("B", "北京"), ("C", 3) )
for ((k, v) <- map1) println(k + " is mapped to " + v)
for (v <- map1.keys) println(v)
for (v <- map1.values) println(v) 
for(v <- map1) println(v) 
//每遍历一次，返回的元素是Tuple2
//取出的时候，可以按照元组的方式来取
```

## 12、集 Set

### ①、Set的基本介绍

* 集是不重复元素的集合。即不保留顺序，默认是以哈希集的实现
* Java中的Set：HashSet是实现Set\<E>接口的一个实现类，数据以哈希表的形式存放的，里面的不能包含重复数据。Set接口是一种不包含重复元素的collection，HashSet中的数据也是没有顺序的。
* Scala中Set：默认情况下，Scala使用的是不可变集合，如果你想使用可变集合，需要引用Scala.collection.mutable.Set包

### ②、Set-创建

```scala
val set = Set(1, 2, 3) //不可变
import scala.collection.mutable.Set
val mutableSet = Set(1, 2, 3) //可变
```

### ③、Set-可变集合的元素的添加

```scala
mutableSet.add(4) //方式1
mutableSet += 6  //方式2
mutableSet.+=(5) //方式3
```

### ④、Set-元素的删除

```scala
val set02 = mutable.Set(1,2,4,"abc")
set02 -= 2 // 操作符形式
set02.-=(4) // 
set02.remove("abc") // 方法的形式，scala的Set可以直接删除值
println(set02)
//如果删除的对象不存在，则不生效，也不会报错
```

### ⑤、Set-遍历

```scala
val set02 = mutable.Set(1, 2, 4, "abc")
for(x <- set02) {
println(x)
}
```

### ⑥、更多操作

| 序号 | 方法                                       | 描述                                                 |
| ---- | ------------------------------------------ | ---------------------------------------------------- |
| 1    | def   +(elem: A): Set[A]                   | 为集合添加新元素，并创建一个新的集合，除非元素已存在 |
| 2    | def   -(elem: A): Set[A]                   | 移除集合中的元素，并创建一个新的集合                 |
| 3    | def   contains(elem: A): Boolean           | 如果元素在集合中存在，返回 true，否则返回 false。    |
| 4    | def   &(that: Set[A]): Set[A]              | 返回两个集合的交集                                   |
| 5    | def   &~(that: Set[A]): Set[A]             | 返回两个集合的差集                                   |
| 6    | def   ++(elems: A): Set[A]                 | 合并两个集合                                         |
| 7    | def   drop(n: Int): Set[A]]                | 返回丢弃前n个元素新集合                              |
| 8    | def   dropRight(n: Int): Set[A]            | 返回丢弃最后n个元素新集合                            |
| 9    | def   dropWhile(p: (A) => Boolean): Set[A] | 从左向右丢弃元素，直到条件p不成立                    |
| 10   | def   max: A //演示下 //比如(1,2,3的集)    | 查找最大元素                                         |
| 11   | def   min: A  //演示下                     | 查找最小元素                                         |
| 12   | def   take(n: Int): Set[A]                 | 返回前 n 个元素                                      |

# 集合引用

## 1、集合元素的映射-map映射操作

* 请将List(3,5,7) 中的所有元素都 * 2 ，将其结果放到一个新的集合中返回，即返回一个新的List(6,10,14), 请编写程序实现.