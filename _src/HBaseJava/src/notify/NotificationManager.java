package notify;

import helper.printValues;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;


import java.io.IOException;
import java.util.*;

public class NotificationManager {

    public Notification createNotification(Notification.NotificationType type,Map<String,String> parameters){

        switch(type) {
            case LIKE:
                return new LikeNotification(
                        parameters.get("from_user"),
                        parameters.get("for_user"),
                        Notification.ResourceType.valueOf(parameters.get("liked")),
                        parameters.get("url"));

            case COMMENT:
                return new CommentNotification(
                        parameters.get("from_user"),
                        parameters.get("for_user"),
                        parameters.get("text"),
                        Notification.ResourceType.valueOf(parameters.get("commentedOn")),
                        parameters.get("url"));

            case PRIVATE_MESSAGE:
                return new PMNotification(
                        parameters.get("from_user"),
                        parameters.get("for_user"),
                        parameters.get("text"));

            case FRIEND_REQUEST:
                return new FriendRequestNotification(
                        parameters.get("from_user"),
                        parameters.get("for_user"));
            default:
                return new Notification();
        }
    }

    public void addNotification(Notification notification) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        String uniqueID = UUID.randomUUID().toString();

        Put put = new Put(Bytes.toBytes(uniqueID));

        List<Column> columns=notification.getColumns();

        for(Column column:columns){

            put.addColumn(Bytes.toBytes(column.getColumnFamily()),
                    Bytes.toBytes(column.getColumnName()),
                    Bytes.toBytes(column.getValue()));
        }

        table.put(put);
    }

    public List<Notification> getUserNotifications(String user) throws Exception{

        List<Notification> userNotifications = new ArrayList<>();

        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("notifications"));

        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                Bytes.toBytes("attributes"),
                Bytes.toBytes("for_user"),
                CompareFilter.CompareOp.EQUAL,
                new BinaryComparator(Bytes.toBytes(user)));

        filter.setFilterIfMissing(true);
        Scan userScan = new Scan();
        userScan.setFilter(filter);
        userScan.addFamily(Bytes.toBytes("attributes"));
        ResultScanner userScanResult = table.getScanner(userScan);
        for (Result res : userScanResult) {

            Notification.NotificationType type = Notification.NotificationType.valueOf(
                    Bytes.toString(res.getValue(Bytes.toBytes("attributes"), Bytes.toBytes("type"))));

            Map<String,String> parameters = parseResults(res);

            userNotifications.add(createNotification(type,parameters));
        }
        userScanResult.close();
        return  userNotifications;
    }

    private Map<String,String> parseResults(Result result){

        Map<String,String> parameters = new HashMap<>();

        NavigableMap<byte[],
                NavigableMap<byte[],byte[]>> resultMap = result.getNoVersionMap();

        for (byte[] columnFamily : resultMap.keySet()) {
            NavigableMap<byte[], byte[]> columnMap = resultMap.get(columnFamily);
            for (byte[] column : columnMap.keySet()) {
                String col = Bytes.toString(column);
                String value = Bytes.toString(columnMap.get(column));
                if(col!="type") {
                        parameters.put(col, value);
                    }
                }
            }

        return parameters;
    }
}




