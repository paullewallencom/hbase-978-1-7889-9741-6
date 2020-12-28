
package counters;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class counter {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));


        Get get =new Get(Bytes.toBytes("2"));
        get.addColumn(Bytes.toBytes("metrics"), Bytes.toBytes("open"));
        Result result = table.get(get);
        byte[] val= result.getValue(Bytes.toBytes("metrics"), Bytes.toBytes("open"));


        long opencount=1;
        if (val!=null){
             opencount = Bytes.toLong(val)+1;
        }

        Put put =new Put(Bytes.toBytes("2"));
        put.addColumn(Bytes.toBytes("metrics"),Bytes.toBytes("open"),Bytes.toBytes(opencount));

        table.put(put);


         table.incrementColumnValue(Bytes.toBytes("2"),Bytes.toBytes("metrics"),Bytes.toBytes("views"),1);


          Increment increment =new Increment(Bytes.toBytes("2"));
          increment.addColumn(Bytes.toBytes("metrics"),Bytes.toBytes("clicks"),1);
          increment.addColumn(Bytes.toBytes("metrics"),Bytes.toBytes("views"),1);


         table.increment(increment);

    }
}
