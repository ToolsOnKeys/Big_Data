package com.dh.gmallpublisher.service;

import java.util.Map;

/**
 * @author dinghao
 * @create 2020-02-19 14:22
 * @message
 */
public interface DauService {
    //获取总数
    public int getTotal(String date);

    //获取分时
    public Map getRealTimeHours(String date);


}
