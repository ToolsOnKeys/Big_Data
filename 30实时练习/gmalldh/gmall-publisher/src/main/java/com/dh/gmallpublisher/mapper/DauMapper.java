package com.dh.gmallpublisher.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dinghao
 * @create 2020-02-19 14:24
 * @message
 */
public interface DauMapper {

    public int getTotal(String date);

    //获取分时
    public List<Map> selectDauTotalHourMap(String date);
}
