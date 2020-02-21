package com.dh.gmalllogger.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dh.constants.GmallConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dinghao
 * @create 2020-02-17 17:24
 * @message
 */
@RestController
@Slf4j
public class LoggerController {
//    @GetMapping("test")
//    public String test(){
//        return "success";
//    }
//
//    @GetMapping("test2")
//    public String test2(@RequestParam("aa") String a){
//        return a+"success";
//    }

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @PostMapping("log")
    public String loger(@RequestParam("logString") String a) {
//        System.out.println(a);

        //1、使用log4j打印日志到控制台及文件
        //添加事件戳
        JSONObject jsonObject = JSON.parseObject(a);
        jsonObject.put("ts",System.currentTimeMillis());
        String jsonString = jsonObject.toJSONString();
        //重新组合Json
        log.info(jsonString);
        //2、使用Kafka生产者将数据发送到Kafka集群
        if("startup".equals(jsonObject.getString("type"))){
            //发送到启动日志主题
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_STARTUP,jsonString);
        }else{
            //发送的事件日志主题
            kafkaTemplate.send(GmallConstants.KAFKA_TOPIC_EVENT,jsonString);
        }

        return "success";
    }
}
