package com.dh.app

import java.text.SimpleDateFormat
import java.util.Date

import com.alibaba.fastjson.JSON
import com.dh.bean.StartUpLog
import com.dh.constants.GmallConstants
import com.dh.handler.DauHandler
import com.dh.utils.MyKafkaUtil
import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.phoenix.spark._
/**
  * @author dinghao 
  * @create 2020-02-18 15:32 
  * @message
  */
object DauApp {
  def main(args: Array[String]): Unit = {
    val ssc = new StreamingContext(new SparkConf().setMaster("local[*]").setAppName("DauApp"), Seconds(5))

    val dateFormat = new SimpleDateFormat("yyyy-MM-dd HH")
    val dateFormat2 = new SimpleDateFormat("yyyy-MM-dd")
    val kafkaDStream = MyKafkaUtil.getKafkaStream(ssc, Set(GmallConstants.KAFKA_TOPIC_STARTUP))


    val startLogDStream = kafkaDStream.map(a => {
      val startUpLog = JSON.parseObject(a.value(), classOf[StartUpLog])
      val strings = dateFormat.format(new Date(startUpLog.ts)).split(" ")
      startUpLog.logDate = strings(0)
      startUpLog.logHour = strings(1)
      startUpLog
    })
    //3、利用Redis中用户清单过滤
    val filterDStream = DauHandler.filterWithRedis(startLogDStream, ssc)

    //批次内去重
    val distinctDStream = DauHandler.filterByBatch(filterDStream)
    distinctDStream.cache()
    //    distinctDStream.count().print
    //写入redis
    DauHandler.saveMidToRedis(distinctDStream)

    distinctDStream.foreachRDD(rdd => {
      rdd.saveToPhoenix("GMALL_DAU", Seq("MID", "UID", "APPID", "AREA", "OS", "CH", "TYPE", "VS", "LOGDATE", "LOGHOUR", "TS"), new Configuration, Some("hadoop201,hadoop202,hadoop203:2181"))
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
