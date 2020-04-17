import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.zookeeper.ZooKeeperWatcher;
import org.apache.hadoop.hyperbase.client.HyperbaseAdmin;
import org.apache.hadoop.hyperbase.client.HyperbaseHTable;
import org.apache.hadoop.hyperbase.datatype.HDataType;
import org.apache.hadoop.hyperbase.datatype.IntegerDataType;
import org.apache.hadoop.hyperbase.datatype.StringHDataType;
import org.apache.hadoop.hyperbase.datatype.TypeException;
import org.apache.hadoop.hyperbase.metadata.HyperbaseMetadata;
import org.apache.hadoop.hyperbase.metadata.schema.HyperbaseTableSchema;
import org.apache.hadoop.hyperbase.metadata.zookeeper.HyperbaseMetadataZookeeper;
import org.apache.hadoop.hyperbase.secondaryindex.SecondaryIndex;
import org.apache.zookeeper.KeeperException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * @author dinghao
 * @create 2020-04-16 9:36
 * @message
 */
public class HyperBaseDemo {
    public static Configuration configuration;
    public static Connection connection;
    public static HyperbaseAdmin hyperbaseAdmin;
    public static HyperbaseMetadataZookeeper zk;
    static{
        try {
            configuration = HBaseConfiguration.create();
            configuration.addResource(new Path("C:/Users/ding1/IdeaProjects/fortest/hbase-site.xml"));
            hyperbaseAdmin = new HyperbaseAdmin(configuration);
            ZooKeeperWatcher zooKeeperWatcher = new ZooKeeperWatcher(configuration, "HyperbaseAdmin", null);
            zk = new HyperbaseMetadataZookeeper(zooKeeperWatcher);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }
    public void stop(){
        try {
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
    @Test
    public void createTable(){
        String tableName = "zyh";
        String columnFamily = "info";

        HTableDescriptor desc=new HTableDescriptor(TableName.valueOf(tableName));
        desc.addFamily(new HColumnDescriptor(Bytes.toBytes(columnFamily)));

        HyperbaseTableSchema schema = new HyperbaseTableSchema(new StringHDataType());
        try {
            schema.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes("name"),new StringHDataType());
            schema.addColumn(Bytes.toBytes(columnFamily),Bytes.toBytes("age"),new IntegerDataType());

            hyperbaseAdmin.createTable(desc,schema);
        } catch (TypeException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            stop();
        }
    }
    @Test
    public void scanTable(){
        try {
            HyperbaseHTable table = new HyperbaseHTable(configuration, TableName.valueOf("zyh"));

            HyperbaseMetadata metadata = zk.getMetadata(TableName.valueOf("zyh"));
            System.out.println("=================globalIndexes=================");
            Map<byte[], SecondaryIndex> globalIndexes = metadata.getGlobalIndexes();
            for (Map.Entry<byte[], SecondaryIndex> indexEntry : globalIndexes.entrySet()) {
                System.out.println(indexEntry.getKey()+"->"+indexEntry.getValue());
            }
            System.out.println("==================schema===============");
            HyperbaseTableSchema schema = metadata.getSchema();
            for (Map.Entry<byte[], Map<byte[], HDataType>> entry : schema.getSchema().entrySet()) {
                System.out.println(new String(entry.getKey())+":");
                for (Map.Entry<byte[], HDataType> typeEntry : entry.getValue().entrySet()) {
                    System.out.println(new String(typeEntry.getKey())+"->"+typeEntry.getValue());
                }
            }

//            for (Result result : table.getScanner(new Scan())) {
//                toPrint(result);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            stop();
        }
    }
}
