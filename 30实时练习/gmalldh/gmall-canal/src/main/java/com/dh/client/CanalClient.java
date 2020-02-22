package com.dh.client;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.dh.constants.GmallConstants;
import com.dh.utils.MyKafkaSender;
import com.google.protobuf.InvalidProtocolBufferException;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author dinghao
 * @create 2020-02-21 11:13
 * @message
 */
//读取Canal数据，解析之后发送到Kafka
public class CanalClient {
    public static void main(String[] args) {
        //获取Canal连接器
        CanalConnector canalConnector = CanalConnectors.newSingleConnector(new InetSocketAddress("hadoop201", 11111), "example", "", "");

        //抓取数据并解析
        while(true){
            //连接Canal
            canalConnector.connect();
            //指定订阅的数据库
            canalConnector.subscribe("gmall.*");
            //抓取数据
            Message message = canalConnector.get(100);
            //判断当前抓取的数据是否为空
            if(message.getEntries().size()==0){
                System.out.println("No Data!!!Sleep 5 minintes");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                //有数据，取出Entry集合并遍历集合数据
                for (CanalEntry.Entry entry : message.getEntries()) {
                    //判断但钱操作的类型
                    if(CanalEntry.EntryType.ROWDATA.equals(entry.getEntryType())){
                        CanalEntry.RowChange rowChange =null;
                        try {
                            //反序列化数据
                            rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
                        } catch (InvalidProtocolBufferException e) {
                            e.printStackTrace();
                        }
                        //获取表名
                        String tableName = entry.getHeader().getTableName();
                        //取出事件类型
                        CanalEntry.EventType eventType = rowChange.getEventType();
                        //取出行集
                        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();

                        handler(tableName,eventType,rowChange);
                    }
                }
            }
        }
    }

    private static void handler(String tableName, CanalEntry.EventType eventType, CanalEntry.RowChange rowChange) {
        //订单表并且是下单数据
        if("order_info".equals(tableName)&&CanalEntry.EventType.INSERT.equals(eventType)){
            //遍历RowDatasList
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                //创建JSON对象，用于存放一行数据
                JSONObject jsonObject = new JSONObject();
                //获取变化后的数据并遍历
                for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                    jsonObject.put(column.getName(),column.getValue());
                }
                //发送至Kafka
                System.out.println(jsonObject.toString());
                MyKafkaSender.send(GmallConstants.KAFKA_TOPIC_ORDER_INFO,jsonObject.toString());
            }
        }
    }
}
