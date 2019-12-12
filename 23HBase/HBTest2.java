package com.dinghao.homework;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.master.HMaster;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.phoenix.util.ByteUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghao
 * @create 2019-12-12 10:00
 * @message
 */
public class HBTest2 {
    public static Configuration configuration;
    public static Connection connection;
    public static HBaseAdmin hMaster;

    static{
        configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum","hadoop101,hadoop102,hadoop103");
        try {
            connection = ConnectionFactory.createConnection(configuration);
            hMaster = (HBaseAdmin) connection.getAdmin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop() throws IOException {
        connection.close();
        hMaster.close();
    }
    public void toPrint(Result result){
        for (Cell cell : result.rawCells()) {
            System.out.print("rowKey:"+ Bytes.toString(CellUtil.cloneRow(cell)));
            System.out.print(",family:"+ Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.print(",qualifier:"+ Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println(",value:"+ Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }
    //1、创建命名空间
    public void addNameSpace(String namespaceName) throws IOException {
        hMaster.createNamespace(NamespaceDescriptor.create(namespaceName).build());
    }
    @Test
    public void addNmaeSpaceTest() throws IOException {
        addNameSpace("zyh");
    }
    //2、判断表示否存在
    public boolean isTable(String tableName) throws IOException {
        return hMaster.tableExists(tableName);
    }
    @Test
    public void isTableTest() throws IOException {
        if(isTable("dd")){
            System.out.println("表存在");
        }else{
            System.out.println("表不存在");
        }
    }
    //3、创建表格
    public void createTable(String tableName,String... cfs) throws IOException {
        if(cfs.length<=0){
            System.out.println("组族不可为空");
            return;
        }
        if(isTable(tableName)){
            System.out.println("表已存在");
            return;
        }
        HTableDescriptor htable = new HTableDescriptor(TableName.valueOf(tableName));
        for (String cf : cfs) {
            HColumnDescriptor family = new HColumnDescriptor(Bytes.toBytes(cf));
            htable.addFamily(family);
        }
        hMaster.createTable(htable);
    }
    @Test
    public void createTableTest() throws IOException {
        createTable("dz","info1","info2");
    }
    //4、删除表格
    public void deleteTable1(String tableName) throws IOException {
        if(!isTable(tableName)){
            System.out.println("表不存在");
            return;
        }
        hMaster.disableTable(tableName);
        hMaster.deleteTable(tableName);
    }
    @Test
    public void deleteTableTest() throws IOException {
        deleteTable1("zheng");
    }
    //5、插入数据
    public void addData(String tableName,String rowKey,String cf,String cn,String value) throws IOException {
        if(!isTable(tableName)){
            System.out.println("表不存在");
            return;
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn),Bytes.toBytes(value));
        table.put(put);
    }
    @Test
    public void addDataTest() throws IOException {
        addData("dz","1003","info1","name","other");
    }
    //6、查询数据（get）[单笔数据]
    public void getData(String tableName,String rowKey,String cf,String cn) throws IOException {
        if(!isTable(tableName)){
            System.out.println("表不存在");
            return;
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(cf),Bytes.toBytes(cn));
        Result result = table.get(get);
        toPrint(result);
    }
    @Test
    public void getDataTest() throws IOException {
        getData("dz","1002","info1","name");
    }
    //7-1、查看数据（scan）【无拦截器】
    public void scanData(String tableName) throws IOException {
        if(!isTable(tableName)){
            System.out.println("表不存在");
            return;
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();//不指定范围
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            toPrint(result);
        }
    }
    @Test
    public void scanDataTest() throws IOException {
        scanData("dz");
    }
    //7-2、查看数据（scan）【有拦截器】
    public void scanDataWithLanjie(String tableName) throws IOException {
        if(!isTable(tableName)){
            System.out.println("表不存在");
            return;
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        Scan scan = new Scan();
        FilterList filterlist = new FilterList(FilterList.Operator.MUST_PASS_ALL);
        Filter filter1 = new RowFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("100"));
        Filter filter2 = new ValueFilter(CompareFilter.CompareOp.EQUAL,new SubstringComparator("h"));
        filterlist.addFilter(filter1);
        filterlist.addFilter(filter2);
        scan.setFilter(filterlist);
        ResultScanner scanner = table.getScanner(scan);
        for (Result result : scanner) {
            toPrint(result);
        }
    }
    @Test
    public void scanDataWithTest() throws IOException {
        scanDataWithLanjie("dz");
    }
    //8、删除表数据
    public void deleteData(String tablename,String rowKey,String cf,String cn) throws IOException {
        if(!isTable(tablename)){
            System.out.println("表不存在");
            return;
        }
        Table table = connection.getTable(TableName.valueOf(tablename));
        List<Delete> deletelist = new ArrayList<>();
        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addColumns(Bytes.toBytes(cf),Bytes.toBytes(cn));
        deletelist.add(delete);
        table.delete(deletelist);
    }
    @Test
    public void deleteDatatest() throws IOException {
        deleteData("dz","1003","info1","name");
    }
}
