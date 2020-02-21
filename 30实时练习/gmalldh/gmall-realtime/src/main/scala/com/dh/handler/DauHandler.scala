package com.dh.handler

import java.text.SimpleDateFormat
import java.util.Date

import com.dh.bean.StartUpLog
import com.dh.utils.RedisUtil
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.dstream.DStream

/**
  * @author dinghao 
  * @create 2020-02-19 9:20 
  * @message
  */
object DauHandler {
//  同批次去重
  def filterByBatch(filterDStream: DStream[StartUpLog]) = {
    filterDStream.map(startUpLog=>((startUpLog.mid,startUpLog.logDate),startUpLog)).groupByKey().flatMap{ case ((mid,logDate), startupLogItr) =>
      startupLogItr.toList.take(1)
    }
  }

  //redis去重
  def filterWithRedis(startLogDStream: DStream[StartUpLog], ssc: StreamingContext) = {
    val dateFormat2 = new SimpleDateFormat("yyyy-MM-dd")
    startLogDStream.transform(
      rdd => {
        val jedisClient = RedisUtil.getJedisClient
        val key = "dau:" + dateFormat2.format(new Date())
        val dauMidSet = jedisClient.smembers(key)
        jedisClient.close()
        val dauMidBC = ssc.sparkContext.broadcast(dauMidSet)
        println("BCcount:" + dauMidBC.value.size())
        println("begin" + rdd.count())
        val filterRdd = rdd.filter(startupLog => {
          !dauMidBC.value.contains(startupLog.mid)
        })
        println("after" + filterRdd.count())
        filterRdd
      }
    )
  }


  def saveMidToRedis(distictDStream: DStream[StartUpLog]) = {
    distictDStream.foreachRDD(rdd=>{
      rdd.foreachPartition(startupLog=>{
        val jedisClient = RedisUtil.getJedisClient
        startupLog.foreach(startuplog=>{
          val key = "dau:"+startuplog.logDate
          jedisClient.sadd(key,startuplog.mid)
        })
        jedisClient.close()
      })
    })
  }
}
