# Scala异常

```scala
  try {
        var n= 10 / 0
    }catch {
        case ex: ArithmeticException=>{
            // 发生算术异常
            println("发生算术异常")
        }
        case ex: Exception=>{
            // 对异常处理
            println("发生了异常1")
            println("发生了异常2")
        }
    }finally {
        println("finally")
    }
```

* 我们将可疑代码封装在try块中。在try块之后使用了一个catch处理程序来捕获异常。如果发生任何异常，catch处理程序将处理它，程序将不会异常终止
* Scala的异常的工作机制和Java一样，但是Scala没有“checked（编译器）”异常，即Scala没有编译异常这个概念，异常都是在运行的时候捕获处理
* 异常不做的机制于其他语言中的一样，如果有异常发生，catch子句是按次序不做的。因此，在catch子句中，月具体的异常越要考前，越普遍的异常越靠后，如果把月普遍的一场写在前，把具体的异常卸载后，在Scala中也不会报错，但这样是非常不好的变成风格。
* finally子句用于执行不管是正常处理还是有异常发生时都需要执行的步骤，一般用于对象的清理工作，这点和Java时一样的。
* 用throw关键字，抛出一个异常对象。所有异常都是Throwable的子类型。throw表达式是有类型的，就是Nothing，因为Nothing时所有类型的子类型，所以throw表达式可以用在需要类型的地方
* Java提供了throws关键字来声明异常。可以使用方法定义声明异常。他想调用者函数提供了此方法可能引发此异常的信息。它有助于调用函数处理并将该代码包含在try-catch块中，以避免次序异常终止。在Scala中，可以使用throws注解来声明异常