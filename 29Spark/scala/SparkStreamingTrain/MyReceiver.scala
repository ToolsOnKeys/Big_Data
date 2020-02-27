package SparkStreamingTrain

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket
import java.nio.charset.StandardCharsets

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver


/**
  * @author dinghao 
  * @create 2020-02-10 9:31 
  * @message
  */
class MyReceiver(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY) {


  //接收数据的方法
  def receive()={
      //创建一个socket
      var socket = new Socket(host, port)
      //创建一个BufferReader用来读取端口传来的数据
      val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))
      //定义一个变量，用来接收端口传过来的数据
      var input = reader.readLine()
      //当receiver没有关闭并且输入数据不为空，则循环发送数据给Spark
      while (input != null && !isStopped()) {
        store(input)
        input = reader.readLine()
      }
      //跳出循环则关闭资源
      reader.close()
      socket.close()
      //重启任务
      restart("restart")
  }
  //启动时调用的方法
  override def onStart(): Unit = {
    new Thread(){
      override def run(): Unit ={
        receive()
      }
    }
  }

  //关闭时调用的方法
  override def onStop(): Unit = {}
}
