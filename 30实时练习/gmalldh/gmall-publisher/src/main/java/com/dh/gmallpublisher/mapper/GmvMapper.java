package com.dh.gmallpublisher.mapper;

import java.util.List;
import java.util.Map;

/**
 * @author dinghao
 * @create 2020-02-21 15:43
 * @message
 */
public interface GmvMapper {

    public Double selectOrderAmountTotal(String date);

    public List<Map> selectOrderAmountHourMap(String date);
}
