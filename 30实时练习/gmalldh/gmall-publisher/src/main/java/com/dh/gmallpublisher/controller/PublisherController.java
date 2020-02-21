package com.dh.gmallpublisher.controller;

import com.alibaba.fastjson.JSON;
import com.dh.gmallpublisher.service.DauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author dinghao
 * @create 2020-02-19 14:19
 * @message
 */
@RestController
public class PublisherController {

    @Autowired
    DauService dauService;

    @GetMapping("realtime-total")
    public String getRealTimeTotal(@RequestParam("date") String date){
        //获取总数
        int total = dauService.getTotal(date);
        //创建返回数据集
        ArrayList<HashMap<String,Object>> result = new ArrayList<>();
        HashMap<String,Object> dauMap = new HashMap<>();
        HashMap<String,Object> newDirve = new HashMap<>();
        //创建新增数据集
        dauMap.put("id","dau");
        dauMap.put("name","新增日活");
        dauMap.put("value",total);

        newDirve.put("id","new_mid");
        newDirve.put("name","新增设备");
        newDirve.put("value",233);
        //将日活数据集加入至集合
        result.add(dauMap);
        result.add(newDirve);
        return JSON.toJSONString(result);
    }

    @GetMapping("realtime-hours")
    public String realtimeHourDate(@RequestParam("id") String id,@RequestParam("date") String date){
        HashMap<String,Map> result = new HashMap<>();

        Map todayMap = dauService.getRealTimeHours(date);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try{
            calendar.setTime(dateFormat.parse(date));
        }catch(ParseException e){
            e.printStackTrace();
        }

        calendar.add(Calendar.DAY_OF_MONTH,-1);

        String yesterday = dateFormat.format(new Date(calendar.getTimeInMillis()));

        Map yesterdayMap = dauService.getRealTimeHours(yesterday);

        result.put("yesterday",yesterdayMap);
        result.put("today",todayMap);
        return JSON.toJSONString(result);
    }

}
