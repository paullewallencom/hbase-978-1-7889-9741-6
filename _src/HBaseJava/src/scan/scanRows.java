package scan;


import batch.batchOp;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;


import java.io.IOException;
import java.util.NavigableMap;
import helper.printValues;
public class scanRows {


    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));


        Scan fullScan = new Scan();
        ResultScanner fullScanResult = table.getScanner(fullScan);
        for (Result res : fullScanResult) {
            printValues.printAllValues(res);
        }
        fullScanResult.close();

        Scan colScan = new Scan();
        colScan.addFamily(Bytes.toBytes("metrics"));
        ResultScanner colScanResult = table.getScanner(colScan);
        for (Result res : colScanResult) {
            printValues.printAllValues(res);
        }
        colScanResult.close();

        Scan rangeScan = new Scan();
        rangeScan.addColumn(Bytes.toBytes("attributes"), Bytes.toBytes("type"))
                .setStartRow(Bytes.toBytes("2"))
                .setStopRow(Bytes.toBytes("2"));


        ResultScanner rangeScanResult = table.getScanner(rangeScan);
        for (Result res : rangeScanResult) {
            printValues.printAllValues(res);
        }
        rangeScanResult.close();


    }



}