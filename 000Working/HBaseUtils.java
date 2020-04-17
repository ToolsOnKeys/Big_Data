import com.google.protobuf.HBaseZeroCopyByteString;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.protobuf.generated.HyperbaseProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.Triple;
import org.apache.hadoop.hyperbase.client.HyperbaseAdmin;
import org.apache.hadoop.hyperbase.secondaryindex.CombineIndex;
import org.apache.hadoop.hyperbase.secondaryindex.IndexedColumn;
import org.apache.hadoop.hyperbase.secondaryindex.SecondaryIndex;
import org.apache.hadoop.hyperbase.secondaryindex.SecondaryIndexUtil;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dinghao
 * @create 2020-04-13 13:39
 * @message 代码Demo =》 HBase的基本操作的工具类，涉及封装的方法有命名空间的建立，表的建立删除，表数据的增删改查
 * ①、public void createNamespace(String namespace) throws IOException =》创建命名空间
 * ②、public void createTable(String tableName, String... columnFamily) throws IOException =》创建表
 * ③、public void deleteTable(String tableName) throws IOException =》删除表
 * ④、public void addData(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException =》添加表数据
 * ⑤、public Result getData(String tableName, String rowKey, String columnFamily, String column) throws IOException =》 获取表数据
 * ⑥、public ResultScanner scanData(String tableName) throws IOException =》获取表数据
 * ⑦、public void deleteData(String tableName, String rowKey, String columnFamily, _rea:wq:wqZZZZZZString column) throws IOException =》删除数据
 * ⑧、public void deleteData(String tableName, List<Triple<String, String, String>> list) throws IOException =》删除数据
 */
public class HBaseUtils {
    public static Configuration configuration;
    public static Connection connection;
    public static HBaseAdmin hMaster;
    public static HyperbaseAdmin hyperbaseAdmin;

    //初始化HMaster的对象
    static {
        try {
            configuration = HBaseConfiguration.create();
            configuration.addResource(new Path("C:/Users/ding1/IdeaProjects/fortest/hbase-site.xml"));
            connection = ConnectionFactory.createConnection(configuration);
            hMaster = (HBaseAdmin) connection.getAdmin();
            hyperbaseAdmin = new HyperbaseAdmin(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //资源关闭函数
    public void stop() {
        try {
            connection.close();
            hMaster.close();
            hyperbaseAdmin.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //数据打印
    public void toPrint(Result result) {
        for (Cell cell : result.rawCells()) {
            System.out.print("rowKey:" + Bytes.toString(CellUtil.cloneRow(cell)));
            System.out.print(",columnFamily:" + Bytes.toString(CellUtil.cloneFamily(cell)));
            System.out.print(",column:" + Bytes.toString(CellUtil.cloneQualifier(cell)));
            System.out.println(",value:" + Bytes.toString(CellUtil.cloneValue(cell)));
        }
    }

    //创建命名空间
    public void createNamespace(String namespace) throws IOException {
        hMaster.createNamespace(NamespaceDescriptor.create(namespace).build());
    }

    //判断表是否存在
    private boolean isTable(String name) throws IOException {
        return hMaster.tableExists(name);
    }

    //创建表格
    public void createTable(String tableName, String... columnFamily) throws IOException {
        if (columnFamily.length <= 0) {
            System.out.println("列族不可为空");
            return;
        }
        if (isTable(tableName)) {
            System.out.println("表已存在");
            return;
        }
        HTableDescriptor hTableDescriptor = new HTableDescriptor(TableName.valueOf(tableName));
        for (String column : columnFamily) {
            HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(Bytes.toBytes(column));
            hTableDescriptor.addFamily(hColumnDescriptor);
        }
        hMaster.createTable(hTableDescriptor);
        System.out.println("表：" + tableName + " 创建成功");
    }

    @Test
    public void createTable() {
        try {
            createTable("zyh", "info");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    //删除表格
    public void deleteTable(String tableName) throws IOException {
        if (!isTable(tableName)) {
            System.out.println("表：" + tableName + " 不存在");
            return;
        }
        hMaster.disableTable(tableName);
        hMaster.deleteTable(tableName);
        System.out.println("表：" + tableName + " 删除成功");
    }

    @Test
    public void deleteTable() {
        try {
            deleteTable("zyh");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    //插入数据
    public void addData(String tableName, String rowKey, String columnFamily, String column, String value) throws IOException {
        if (!isTable(tableName)) {
            System.out.println("表" + tableName + "不存在");
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        Put put = new Put(Bytes.toBytes(rowKey));
        put.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column), Bytes.toBytes(value));
        table.put(put);
        System.out.println("数据插入成功");
    }

    @Test
    public void addData() {
        try {
            addData("zyh", "002", "info", "name", "dh");
            addData("zyh", "002", "info", "sex", "man");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    //查询数据【单笔get】columnFamily 和 column 是可选添加的，这个可以加可以不加
    public Result getData(String tableName, String rowKey, String columnFamily, String column) throws IOException {
        if (!isTable(tableName)) {
            System.out.println("表格不存在");
            return null;
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addColumn(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        return table.get(get);
    }

    @Test
    public void getData() {
        try {
            toPrint(getData("zyh", "001", "info", "name"));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    //查询数据【无拦截器scan】
    public ResultScanner scanData(String tableName) throws IOException {
        if (!isTable(tableName)) {
            System.out.println("表格不存在");
            return null;
        }
        Table table = connection.getTable(TableName.valueOf(tableName));
        return table.getScanner(new Scan());
    }

    @Test
    public void scanData() {
        try {
            for (Result result : scanData("zyh")) {
                toPrint(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    //删除表数据，columnFamily 和 column 是可选添加的，这个可以加可以不加
    public void deleteData(String tableName, String rowKey, String columnFamily, String column) throws IOException {
        if (!isTable(tableName)) {
            System.out.println("表不存在");
        }

        Table table = connection.getTable(TableName.valueOf(tableName));

        ArrayList<Delete> deletes = new ArrayList<Delete>();

        Delete delete = new Delete(Bytes.toBytes(rowKey));
        delete.addColumns(Bytes.toBytes(columnFamily), Bytes.toBytes(column));
        deletes.add(delete);

        table.delete(deletes);

        System.out.println("删除成功");
    }

    //删除表数据
    public void deleteData(String tableName, List<Triple<String, String, String>> list) throws IOException {
        for (Triple<String, String, String> triple : list) {
            deleteData(tableName, triple.getFirst(), triple.getSecond(), triple.getThird());
        }
    }

    @Test
    public void deleteData() {
        try {
            ArrayList<Triple<String, String, String>> triples = new ArrayList<Triple<String, String, String>>();
            triples.add(new Triple<String, String, String>("002", "info", "sex"));
            triples.add(new Triple<String, String, String>("001", "info", "age"));

            deleteData("zyh", triples);
            for (Result result : scanData("zyh")) {
                toPrint(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }

    //增加全局索引-add global index using IndexedColumn builder
    public void addGlobalIndex(String tableName, String indexName, String columnFamily, String[] qualifiers, int[] len, int keyLen) throws IOException {
        byte[][] splitKeys = null;
        boolean withCompression = false;

        //创建索引对象
        HyperbaseProtos.SecondaryIndex.Builder builder = HyperbaseProtos.SecondaryIndex.newBuilder();

        //设置索引类型和配置信息
        builder.setClassName(CombineIndex.class.getName());
        builder.setUpdate(false);
        builder.setDcop(false);

        //索引列关联
        for (int i = 0; i < qualifiers.length; i++) {
            builder.addColumns(HyperbaseProtos.IndexedColumn.newBuilder().
                    setColumn(HyperbaseProtos.Column.newBuilder().
                            setFamily(HBaseZeroCopyByteString.wrap(Bytes.toBytes(columnFamily))).
                            setQualifier(HBaseZeroCopyByteString.wrap(Bytes.toBytes(qualifiers[i]))))
                    .setSegmentLength(len[i]));
            //attaching column附加列
//            builder.addAttachings(HyperbaseProtos.Column.newBuilder().
//                    setFamily(HBaseZeroCopyByteString.wrap(Bytes.toBytes(columnFamily))).
//                    setQualifier(HBaseZeroCopyByteString.wrap(Bytes.toBytes(qualifiers[i]))));
        }

        //rowKey
        builder.addColumns(HyperbaseProtos.IndexedColumn.newBuilder().
                setColumn(HyperbaseProtos.Column.newBuilder().
                        setFamily(HBaseZeroCopyByteString.wrap(IndexedColumn.ROWKEY_FAMILY)).
                        setQualifier(HBaseZeroCopyByteString.wrap(IndexedColumn.ROWKEY_QUALIFIER))).
                setSegmentLength(keyLen));

        CombineIndex combineIndex = (CombineIndex) SecondaryIndexUtil.constructSecondaryIndexFromPb(builder.build());

        hyperbaseAdmin.addGlobalIndex(TableName.valueOf(tableName), combineIndex, Bytes.toBytes(indexName), splitKeys, withCompression);
        System.out.println("索引创建成功");
    }

    //增加索引-Add global index by using Pattern String like shell
    //hbase Shell: add_index 'demoTable','shellIdx','COMBINE_INDEX|INDEXED=f:q3:8|f:q4:8|rowKey:rowKey:10,UPDATE=true'
    public void addGlobalIndexByPattern(String tableName, String indexName, String pattern) throws IOException {
        SecondaryIndex index = SecondaryIndexUtil.createSecondaryIndexFromPattern(pattern);
        hyperbaseAdmin.addGlobalIndex(TableName.valueOf(tableName), index, Bytes.toBytes(indexName), null, false);
        System.out.println("索引创建成功");
    }

    //删除全局索引
    public void deleteGlobalIndex(String tableName, String indexName) throws IOException {
        hyperbaseAdmin.deleteGlobalIndex(TableName.valueOf(tableName), Bytes.toBytes(indexName));
    }

    //rebulid 全局索引
    public void rebuildGlobalIndex(String tableName, String indexName) {
    }

    //是否允许索引表更新
    public void updateClientInsert(String tableName, boolean clientInsert) throws IOException {
        hyperbaseAdmin.setClientInsert(TableName.valueOf(tableName), clientInsert);
    }

    @Test
    public void indexTest() {
        String tableName = "dh";
        String columnFamily = "info";
        String indexName = "index";
        try {
            deleteTable(tableName);
            createTable(tableName, columnFamily);
            if (isTable(tableName + "_" + indexName)) {
                deleteGlobalIndex(tableName, indexName);
            }
            //方式一：add global index
            //addGlobalIndex(tableName, indexName, columnFamily, new String[]{"q"}, new int[]{8}, 8);
            //方式二：add global index with pattern
            addGlobalIndexByPattern(tableName, indexName, "COMBINE_INDEX|INDEXED=info:name:8|rowKey:rowKey:8,UPDATE=true");
            updateClientInsert(tableName, true);

            addData(tableName, "001", columnFamily, "name", "dinghao");

            System.out.println("=========" + tableName + "=========");
            for (Result result : scanData(tableName)) {
                toPrint(result);
            }
            System.out.println("=========" + tableName + "_" + indexName + "=========");
            for (Result result : scanData(tableName + "_" + indexName)) {
                toPrint(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            stop();
        }
    }
}
