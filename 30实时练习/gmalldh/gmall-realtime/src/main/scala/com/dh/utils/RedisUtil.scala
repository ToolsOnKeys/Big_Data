package com.dh.utils

import redis.clients.jedis.{JedisPool, JedisPoolConfig}

/**
  * @author dinghao 
  * @create 2020-02-18 15:21 
  * @message
  */
object RedisUtil {

  def main(args: Array[String]): Unit = {
    val jedisClient = getJedisClient
    println(jedisClient.ping())
    jedisClient.close()
  }
  var jedisPool:JedisPool = null

  def getJedisClient ={
    if(jedisPool==null){
      val config = PropertiesUtil.load("config.properties")
      val host = config.getProperty("redis.host")
      val port = config.getProperty("redis.port")
      val jedisPoolConfig = new JedisPoolConfig()
      jedisPoolConfig.setMaxTotal(100)  //最大连接数
      jedisPoolConfig.setMaxIdle(20)    //最大空闲
      jedisPoolConfig.setMinIdle(20)    //最小空闲
      jedisPoolConfig.setBlockWhenExhausted(true)  //忙碌时是否等待
      jedisPoolConfig.setMaxWaitMillis(500) //忙碌时等待时长 毫秒
      jedisPoolConfig.setTestOnBorrow(true) //每次获得连接的进行测试
//      println(host+":"+port)
      jedisPool = new JedisPool(jedisPoolConfig,host,port.toInt)
    }
    jedisPool.getResource
  }
}
