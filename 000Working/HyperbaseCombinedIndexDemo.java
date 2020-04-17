import com.google.protobuf.HBaseZeroCopyByteString;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.protobuf.generated.HyperbaseProtos;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hyperbase.client.HyperbaseAdmin;
import org.apache.hadoop.hyperbase.client.HyperbaseHTable;
import org.apache.hadoop.hyperbase.secondaryindex.CombineIndex;
import org.apache.hadoop.hyperbase.secondaryindex.IndexedColumn;
import org.apache.hadoop.hyperbase.secondaryindex.SecondaryIndex;
import org.apache.hadoop.hyperbase.secondaryindex.SecondaryIndexUtil;

import java.io.IOException;
import java.util.ArrayList;


public class HyperbaseCombinedIndexDemo {

  private static Configuration  conf = HBaseConfiguration.create();

  /**
   * Create table
   * @param tableName
   * @param families
   * @throws IOException
   */
  public static void createTable(String tableName, String[] families) throws IOException{
    Connection connection = ConnectionFactory.createConnection(conf);
    HBaseAdmin admin = (HBaseAdmin) connection.getAdmin();
//    HBaseAdmin admin = new HBaseAdmin(conf);
    if (admin.tableExists(tableName)){
      System.out.println(tableName+" already exists!");
    }
    else {
      HTableDescriptor descr = new HTableDescriptor(TableName.valueOf(tableName));
      for (String family:families) {
        descr.addFamily(new HColumnDescriptor(family)); //添加列族
      }
      admin.createTable(descr); //建表
      System.out.println(tableName+" created successfully!");
    }

  }


  /**
   *  add global index using IndexedColumn builder
   * @param tableName
   * @param name
   * @param family
   * @param qualifiers
   * @param len
   * @param keyLen
   * @throws IOException
   */
  public static void addGlobalIndex(String tableName, String name, String family,
                                    String[] qualifiers, int[] len, int keyLen)
          throws IOException {
    //split key used to index table
    byte[][] splitKeys = null;
    boolean withCompression = false;

    byte[] indexName = Bytes.toBytes(name);
    byte[] byteFamily = Bytes.toBytes(family);
    ArrayList<byte[]> bqulifiersList = new ArrayList<byte[]>();
    for(int i = 0; i < qualifiers.length; i++){
      bqulifiersList.add(Bytes.toBytes(qualifiers[i]));
    }

    // build index
    HyperbaseProtos.SecondaryIndex.Builder builder = HyperbaseProtos.SecondaryIndex.newBuilder();

    //set index type and properties
    builder.setClassName(CombineIndex.class.getName());
    builder.setUpdate(false);
    builder.setDcop(false);

    // index column
    for(int i = 0; i < qualifiers.length; i++) {
      builder.addColumns(HyperbaseProtos.IndexedColumn.newBuilder().
              setColumn(HyperbaseProtos.Column.newBuilder().
                      setFamily(HBaseZeroCopyByteString.wrap(byteFamily)).
                      setQualifier(HBaseZeroCopyByteString.wrap(bqulifiersList.get(i)))).
              setSegmentLength(len[i]));
    }



    // rowkey column
    builder.addColumns(HyperbaseProtos.IndexedColumn.newBuilder().
            setColumn(HyperbaseProtos.Column.newBuilder().
                    setFamily(HBaseZeroCopyByteString.wrap(IndexedColumn.ROWKEY_FAMILY)).
                    setQualifier(HBaseZeroCopyByteString.wrap(IndexedColumn.ROWKEY_QUALIFIER))).
            setSegmentLength(keyLen));

    // attaching column
    /*
    for(int i = 1; i < qualifiers.length; i++) {
      builder.addAttachings(HyperbaseProtos.Column.newBuilder().
              setFamily(HBaseZeroCopyByteString.wrap(byteFamily)).
              setQualifier(HBaseZeroCopyByteString.wrap(bqulifiersList.get(i))));
    }
    */
    CombineIndex index = (CombineIndex) SecondaryIndexUtil.constructSecondaryIndexFromPb(builder.build());

  //add global index
    HyperbaseAdmin admin = new HyperbaseAdmin(conf);
//    System.out.println(admin.toString());
    admin.addGlobalIndex(TableName.valueOf(tableName), index, indexName, splitKeys, withCompression);

  }


  /**
   * Add global index by using Pattern String like shell
   * @param tableName
   * @param indexName
   * @param pattern
   * @throws IOException
   */
  public static void addGlobalIndexByPattern(String tableName, String indexName, String pattern)
  throws IOException{
     String COMBINE_INDEX =  pattern;

    SecondaryIndex indexBuilder = SecondaryIndexUtil.createSecondaryIndexFromPattern(COMBINE_INDEX);
    HyperbaseAdmin admin = new HyperbaseAdmin(conf);
    admin.addGlobalIndex(TableName.valueOf(tableName),
            indexBuilder, Bytes.toBytes(indexName), null, false);
  }

  /***
   *
   * @param tableName
   * @param clientInsert
   */
  public static void updateClientInsert(String tableName, boolean clientInsert) throws IOException{
    HyperbaseAdmin hyperAdmin = new HyperbaseAdmin(conf);
    hyperAdmin.setClientInsert(TableName.valueOf(tableName), clientInsert);
    hyperAdmin.close();
  }

/*
  public deleteGlobalIndex(){

  }


  public rebuildGlobalIndex(){

  }
*/

  /**
   *
   * @param htable
   * @param rowKey
   * @param familyName
   * @param columnName
   * @param value
   * @return
   */
  public static Put addData(HyperbaseHTable htable, String rowKey, String familyName, String columnName, String value) {

    Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
    put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName), Bytes.toBytes(value));
    return put;
  }



  /**
   * hbase Shell: add_index 'demoTable','shellIdx','COMBINE_INDEX|INDEXED=f:q3:8|f:q4:8|rowKey:rowKey:10,UPDATE=true'
   * @param args
   */
  public static void main( String[] args){
//    String confDir = "C:/Users/chenxiaosuo/IdeaProjects/resources/conf_191/hyperbase1";
    String confDir = "C:/Users/ding1/IdeaProjects/fortest";
    String tableName = "demoTable";
    String[] family = {"f"};
    conf.addResource(new Path(confDir + "/hbase-site.xml"));


    try {

//      createTable(tableName, family);


//      add global index
//      String[] qualifiers = {"q"};
//      int[] lens =  {8};
//      addGlobalIndex(tableName, "index", family[0], qualifiers, lens, 8);
//      String[] qualifiers = {"q3", "q4"};
//      int[] lens =  {8, 8};
//      addGlobalIndex(tableName, "apiIdx", family[0], qualifiers, lens, 10);

      //add global index with pattern
//      addGlobalIndexByPattern(tableName, "apiPartternIdx",
//              "COMBINE_INDEX|INDEXED=f:q3:8|f:q4:8|rowKey:rowKey:10,UPDATE=true");

      //choose insert client
      updateClientInsert(tableName, true);

      //insert data-

//      HyperbaseHTable htable = new HyperbaseHTable(conf,  TableName.valueOf(tableName));// HTable负责跟记录相关的操作如增删改查等//
//      htable.setAutoFlushTo(false);
//      htable.put(addData(htable, "0000000002", family[0], "q1", "xiaoming"));
//      htable.put(addData(htable, "0000000002", family[0], "q2", "male"));
//      htable.put(addData(htable, "0000000002", family[0], "q3", "18652000000"));
//      htable.put(addData(htable, "0000000002", family[0], "q4", "19900101"));
//      htable.put(addData(htable, "0000000002", family[0], "q5", "xiaoming@transwarp.io"));
//      htable.flushCommits();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
