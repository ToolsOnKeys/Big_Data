package com.dh.gmallpublisher.service;

import java.util.Map;

/**
 * @author dinghao
 * @create 2020-02-21 15:42
 * @message
 */
public interface GmvService {
    //获取GMV总额
    public Double getTotal(String date);
    //获取GMV分时统计结果
    public Map getHoursGmv(String date);
}
