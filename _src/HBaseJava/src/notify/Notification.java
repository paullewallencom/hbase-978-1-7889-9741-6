package notify;

import com.sun.jdi.connect.spi.TransportService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Notification {

    public enum NotificationType{
        COMMENT,
        LIKE,
        FRIEND_REQUEST,
        PRIVATE_MESSAGE
    }

    public enum ResourceType {
        PHOTO,
        LINK
    }

    public List<Column> getColumns() {
        return columns;
    }

    List<Column> columns = new ArrayList<>();

    NotificationType type;

    public NotificationType getType() {
        return this.type;
    }

    public String toString(){
        return "General Notification";
    }

}



