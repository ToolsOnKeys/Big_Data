package com.dh.gmallpublisher.service.Impl;

import com.dh.gmallpublisher.mapper.DauMapper;
import com.dh.gmallpublisher.service.DauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dinghao
 * @create 2020-02-19 14:23
 * @message
 */
@Service
public class DauServiceImpl implements DauService {

    @Autowired
    DauMapper dauMapper;

    @Override
    public int getTotal(String date) {
        return dauMapper.getTotal(date);
    }

    @Override
    public Map getRealTimeHours(String date) {
        List<Map> dauTotalHourlist = dauMapper.selectDauTotalHourMap(date);
        HashMap dauHourMap = new HashMap();
        for (Map map : dauTotalHourlist) {
            dauHourMap.put(map.get("LH"),map.get("CT"));
        }
        return dauHourMap;
    }

}
