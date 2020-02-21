package com.dh.utils

import java.io.InputStreamReader
import java.util.Properties

/**
  * @author dinghao 
  * @create 2020-02-18 15:21 
  * @message
  */
object PropertiesUtil {
  def load(propertiesName:String)={
    val prop = new Properties()
    prop.load(new InputStreamReader(Thread.currentThread().getContextClassLoader.getResourceAsStream(propertiesName),"UTF-8"))
    prop
  }
}
