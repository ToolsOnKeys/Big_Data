package com.dh.app

import com.alibaba.fastjson.JSON
import com.dh.bean.OrderInfo
import com.dh.constants.GmallConstants
import com.dh.utils.MyKafkaUtil
import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.phoenix.spark._

/**
  * @author dinghao 
  * @create 2020-02-21 15:21 
  * @message
  */
object OrderApp {
  def main(args: Array[String]): Unit = {
    //1、创建sparkStream
    val ssc = new StreamingContext(new SparkConf().setAppName("OrderApp").setMaster("local[*]"),Seconds(5))
    //读取Kafka数据创建DStream
    val KafkaDStream = MyKafkaUtil.getKafkaStream(ssc,Set(GmallConstants.KAFKA_TOPIC_ORDER_INFO)).map(_.value())
    val orderDStream = KafkaDStream.map(value => {
      val orderInfo = JSON.parseObject(value, classOf[OrderInfo])

      val createTime = orderInfo.create_time.split(" ")
      orderInfo.create_date = createTime(0);
      orderInfo.create_hour = createTime(1).split(":")(0);

      orderInfo.consignee_tel = orderInfo.consignee_tel.splitAt(4)._1 + "****";
      orderInfo;
    })
    orderDStream.foreachRDD(rdd=>{
      rdd.saveToPhoenix("GMALL_ORDER_INFO", Seq("ID","PROVINCE_ID", "CONSIGNEE", "ORDER_COMMENT", "CONSIGNEE_TEL", "ORDER_STATUS", "PAYMENT_WAY", "USER_ID","IMG_URL", "TOTAL_AMOUNT", "EXPIRE_TIME", "DELIVERY_ADDRESS", "CREATE_TIME","OPERATE_TIME","TRACKING_NO","PARENT_ORDER_ID","OUT_TRADE_NO", "TRADE_BODY", "CREATE_DATE", "CREATE_HOUR"), new Configuration(), Some("hadoop201,hadoop202,hadoop203:2181"))
    })

    println("==========")
    ssc.start()
    ssc.awaitTermination()
  }
}
