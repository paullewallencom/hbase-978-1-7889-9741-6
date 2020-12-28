package get;

import batch.batchOp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import helper.printValues;
/**
 * Created by swethakolalapudi on 6/24/16.
 */
public class listGets {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        List<Get> gets = new ArrayList<Get>();


        Get get1 = new Get(Bytes.toBytes("2"));
        get1.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        get1.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("for_user"));

        Get get2 = new Get(Bytes.toBytes("3"));
        get2.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        get2.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("from_user"));

        gets.add(get1);
        gets.add(get2);


        Result[] results = table.get(gets);

        for (Result result : results) {
            printValues.printAllValues(result);

        }
    }




}
