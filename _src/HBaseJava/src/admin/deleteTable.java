package admin;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;

public class deleteTable {

    public static void main(String[] args) throws IOException {

        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);

        Admin admin = connection.getAdmin();

        HTableDescriptor tableName = new HTableDescriptor(TableName.valueOf("notifications"));

        if (admin.tableExists(tableName.getTableName())) {
            System.out.print("Table exists, Deleting.. ");
            admin.disableTable(tableName.getTableName());
            admin.deleteTable(tableName.getTableName());
            System.out.println(" Done.");
        }
    }
}
