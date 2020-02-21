package com.dh.bean

/**
  * @author dinghao 
  * @create 2020-02-18 15:30 
  * @message
  */
case class StartUpLog(mid: String,
                      uid: String,
                      appid: String,
                      area: String,
                      os: String,
                      ch: String,
                      `type`: String,
                      vs: String,
                      var logDate: String,
                      var logHour: String,
                      var ts: Long)

