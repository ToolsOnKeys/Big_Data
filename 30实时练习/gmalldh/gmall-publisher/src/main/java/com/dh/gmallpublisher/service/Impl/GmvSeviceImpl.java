package com.dh.gmallpublisher.service.Impl;

import com.dh.gmallpublisher.mapper.GmvMapper;
import com.dh.gmallpublisher.service.GmvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dinghao
 * @create 2020-02-21 15:43
 * @message
 */
@Service
public class GmvSeviceImpl implements GmvService {
    @Autowired
    GmvMapper gmvMapper;

    @Override
    public Double getTotal(String date) {
        return gmvMapper.selectOrderAmountTotal(date);
    }

    @Override
    public Map getHoursGmv(String date) {
        List<Map> mapList = gmvMapper.selectOrderAmountHourMap(date);
        HashMap<String,Double> hashMap = new HashMap<>();
        for (Map map : mapList) {
            hashMap.put((String)map.get("CREATE_HOUR"),(Double)map.get("SUM_AMOUNT"));
        }
        return hashMap;
    }
}
