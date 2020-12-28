package delete;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

/**
 * Created by swethakolalapudi on 6/24/16.
 */
public class singleDelete {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        Delete delete =new Delete(Bytes.toBytes("2"));

        delete.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        delete.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("for_user"));

        table.delete(delete);

        table.close();

    }
}
