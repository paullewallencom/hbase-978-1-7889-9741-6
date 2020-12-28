package put;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class listPuts {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));


        Put put1 =new Put(Bytes.toBytes("4"));

        put1.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"), Bytes.toBytes("Friend Request"));
        put1.addColumn(Bytes.toBytes("attributes"),Bytes.toBytes("for_user"),Bytes.toBytes("Daniel"));
        put1.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("from_user"), Bytes.toBytes("Ryan"));


        Put put2 =new Put(Bytes.toBytes("5"));
        put2.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"), Bytes.toBytes("Comment"));
        put2.addColumn(Bytes.toBytes("attributes"),Bytes.toBytes("for_user"),Bytes.toBytes("Brendan"));
        put2.addColumn(Bytes.toBytes("attributes"),Bytes.toBytes("from_user"),Bytes.toBytes("Rick"));
        put2.addColumn(Bytes.toBytes("attributes"),Bytes.toBytes("for_thing"),Bytes.toBytes("link"));
        put2.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("link"), Bytes.toBytes("link"));


        List<Put> puts = new ArrayList<Put>();
        puts.add(put1);
        puts.add(put2);

        table.put(puts);

    }
}
