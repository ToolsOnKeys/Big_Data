# Callable接口
## FutureTask类是接口RunnableFuture<T>【是Runnable的子接口】的实现类，
```java
public FutureTask(Callable<V> callable){};
```
### Demo案列:
```java
 FutureTask ftask = new FutureTask(new Callable() {
     @Override
     public Object call() throws Exception { 
         return null;
     }
 });
 new Thread(ftask).start
```
### 关注返回值，切面优化=====>>>
```java
 FutureTask<Integer> ftask = new FutureTask(()->{return null;});
 new Thread(ftask).start;
 Integer returnData = ftask.get();//有异常，需要捕捉
```
### 不关注返回值，切面优化=====>>>
```java
 new Thread(new FutureTask<Integer>(()->{return null;})).start();
```
