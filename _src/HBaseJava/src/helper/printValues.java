package helper;


import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.NavigableMap;

public class printValues {


    public static void printAllValues(Result result) {

        NavigableMap<byte[],
                NavigableMap<byte[],
                        NavigableMap<Long, byte[]>>> resultMap = result.getMap();

        for (byte[] columnFamily : resultMap.keySet()) {
            String cf = Bytes.toString(columnFamily);
            NavigableMap<byte[], NavigableMap<Long, byte[]>> columnMap = resultMap.get(columnFamily);

            for (byte[] column : columnMap.keySet()) {
                String col = Bytes.toString(column);
                NavigableMap<Long, byte[]> timestampMap = columnMap.get(column);

                for (Long timestamp : timestampMap.keySet()) {
                    String ts = timestamp.toString();
                    String value = Bytes.toString(timestampMap.get(timestamp));
                    System.out.println("Column Family: " + cf
                            + " Column: " + col + " Value: " + value);
                }
            }
        }

    }

}
