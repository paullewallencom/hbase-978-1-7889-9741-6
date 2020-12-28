package filters;


import batch.batchOp;
import helper.printValues;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.NavigableMap;

public class colValueFilter {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("for_user"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("Daniel")));

        filter.setFilterIfMissing(true);

        Scan userScan = new Scan();
        userScan.setFilter(filter);
        ResultScanner userScanResult = table.getScanner(userScan);

        for (Result res : userScanResult) {

            printValues.printAllValues(res);
        }

        userScanResult.close();
    }
}
