package put;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class singlePut{

    public static void main(String[] args) throws IOException{

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = connection.getTable(TableName.valueOf("notifications"));
        //HTable table = new HTable(conf, "notifications");

        Put put =new Put(Bytes.toBytes("1"));

        put.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"), Bytes.toBytes("Comment"));
        put.addColumn(Bytes.toBytes("attributes"),Bytes.toBytes("for_user"),Bytes.toBytes("Chaz"));
        put.addColumn(Bytes.toBytes("metrics"),Bytes.toBytes("open"),Bytes.toBytes("0"));

        table.put(put);

    }
}
