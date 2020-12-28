package filters;

import helper.printValues;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.util.Bytes;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by swethakolalapudi on 6/29/16.
 */
public class rowFilter {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        Filter filter = new RowFilter(CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("1")));

        Scan userScan = new Scan();
        userScan.setFilter(filter);
        ResultScanner userScanResult = table.getScanner(userScan);

        for (Result res : userScanResult) {
            printValues.printAllValues(res);
        }

        userScanResult.close();


    }
}
