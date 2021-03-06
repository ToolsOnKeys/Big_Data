# Kafka零拷贝技术

## 1、传统的拷贝过程

![](D:\BigData\BigData\21Kafka\相关图片\传统文件拷贝流程.png)

* 当消息从发送到写入磁盘，Broker维护的消息日志本身就是文件目录形式，每个文件都是二进制保存，生产者和消费者使用相同的格式来处理。在消费者获取消息是，服务器先从磁盘读取数据到内存，然后把内存中的数据原封不动的通过socket的形式发送给消费者。虽然这个操作看起来简单，但是实际上中间经历了很多步骤。
* 传统的文件拷贝流程步骤：
  * 1、操作系统将数据从磁盘读入到内核空间的页缓冲区。
  * 2、应用程序将数据从内核空间读入到用户空间的应用层缓冲区。
  * 3、应用程序将数据协会到内核空间的Socket套接字缓冲区
  * 4、操作系统将数据从Socket缓冲区复制到网卡缓冲区，以便将数据经网络发出

* 上述过程设计到4次上下文切换以及4次数据的复制，并且有两次复制操作是由CPU完成。但是这个福哦初中，数据完全没有进行变化，仅仅时从磁盘复制到网卡缓冲区。

## 2、DMA 技术的出现

* DMA（直接内存存取）是所有现代电脑的重要特点，他的出现就是为了解决批量数据的输入输出问题。它允许不同速度的硬件装置来沟通，而不需要依赖于CPU的大量终端负载。否则，CPU需要从来源把每个片段的资料复制到暂存器，然后把他们再次写入到新的地方。这个事件中，CPU对于其他工作来说i就是无法使用的。
* 传统的内存访问，所有的请求都会发送到CPU，然后再由CPU来完成相关调度工作。
* 当DMA技术的出现，数据文件在各个层之间的传输，则可以直接绕过CPU，是的**外围设备可以通过DMA控制器直接访问内存**。与此同时，CPU可以继续执行程序
* 在现代电脑中，很多硬件都是支持FMA技术的，这里面其中就包括我们此处用到的网卡。还有其他硬件也都是支持DMA技术的。

## 3、零拷贝技术

* 有了DMA技术，通过网卡直接去访问系统的内存，就可以实现绝对的零拷贝了。这样就可以最大程度的提高传输性能。通过“零拷贝”技术，我们可以去掉那些没必要的数据复制操作，同时也会减少上下文切换次数。
* 现代的Unix操作系统提供了一个优化的代码路径，用于将数据从页缓存直接传输到Socket；在Linux中，是通过sendfile系统调用来完成的。Java提供了访问这个系统调用的方法：FileChannel.transferTo API。使用sendfile，只需要一次拷贝就行，允许操作系统将数据直接从页缓存发送到网络上。所以在这个优化的路径中，只有最后一次将数据拷贝到网卡缓冲中是需要的。

## 4、Java中零拷贝的实现

```java
File file = new File("demo.zip");
RandomAccessFile raf = new RandomAccessFile(file, "rw");
FileChannel fileChannel = raf.getChannel();
SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("", 1234));
// 直接使用了transferTo()进行通道间的数据传输
fileChannel.transferTo(0, fileChannel.size(), socketChannel);
```

## 5、零拷贝的使用场景

* 1、较大，读写较慢，追求速度
* 2、内存不足，不能加载太大数据
* 3、带宽不够，即存在其他程序或线程存在大量的IO操作，导致带宽不够。