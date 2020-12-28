package delete;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class listDeletes {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        //HTable table = new HTable(conf, "notifications");

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        List<Delete> deletes = new ArrayList<Delete>();

        Delete delete1 =new Delete(Bytes.toBytes("1"));

        delete1.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        deletes.add(delete1);

        Delete delete2 =new Delete(Bytes.toBytes("3"));
        delete2.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"));
        deletes.add(delete2);

        table.delete(deletes);

    }
}
