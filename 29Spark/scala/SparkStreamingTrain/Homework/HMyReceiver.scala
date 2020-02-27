package SparkStreamingTrain.Homework

import java.io.{BufferedReader, InputStreamReader}
import java.net.Socket

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver

/**
  * @author dinghao 
  * @create 2020-02-10 18:39 
  * @message
  */
class HMyReceiver(host:String,port:Int) extends Receiver[String](StorageLevel.MEMORY_ONLY){

  def receive(): Unit = {

    try {
      //创建socket流对象
      val socket = new Socket(host, port)

      //创建流
      val reader = new BufferedReader(new InputStreamReader(socket.getInputStream))

      //读取数据
      var str = reader.readLine()

      if (str != null && !isStopped()) {
        //写入spark内存
        store(str)
        //读取新的数据
        str = reader.readLine()
      }

      //关闭流
      reader.close()
      socket.close()
      //重启
      restart("restart")
    } catch {
      //出现异常，重启
      case e:Exception => restart("restart")
    }
  }

  //启动时调用的方法
  override def onStart(): Unit = {
    //开启新的线程读取数据
    new Thread(){
      override def run(): Unit = {
        receive()
      }
    }.start()
  }

  override def onStop(): Unit = {}
}
