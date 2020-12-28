package filters;


import helper.printValues;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class filterList {
    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        SingleColumnValueFilter userFilter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("for_user"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("Daniel")));

        userFilter.setFilterIfMissing(true);

        SingleColumnValueFilter typeFilter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("type"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes("Friend Request")));

        typeFilter.setFilterIfMissing(true);

        List<Filter> listOfFilters = new ArrayList<>();
        listOfFilters.add(typeFilter);
        listOfFilters.add(userFilter);

        FilterList filters = new FilterList(listOfFilters);


        Date endDate = new Date();
        Date startDate = DateUtils.addDays(endDate, -3);
        Scan userTypeScan = new Scan();
        userTypeScan.setTimeRange(startDate.getTime(),endDate.getTime());
        userTypeScan.setFilter(filters);
        ResultScanner userTypeScanResult = table.getScanner(userTypeScan);

        for (Result res : userTypeScanResult) {

            printValues.printAllValues(res);
        }
        userTypeScanResult.close();
    }
}
