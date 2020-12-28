package mapReduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

/**
 * Created by swethakolalapudi on 6/29/16.
 */
public class Main {

    public static void main(String[] args) throws Exception {

        Configuration conf = HBaseConfiguration.create();
        Job job = new Job(conf, "ExampleSummary");
        job.setJarByClass(Main.class);     // class that contains mapper and reducer


        String sourceTable = "notifications";
        String targetTable = "summary";

        Scan scan = new Scan();
        scan.setCaching(500);        // 1 is the default in Scan, which will be bad for MapReduce jobs
        scan.setCacheBlocks(false);  // don't set to true for MR jobs
        // set other scan attrs

        TableMapReduceUtil.initTableMapperJob(
                sourceTable,        // input table
                scan,               // Scan instance to control CF and attribute selection
                MyMapper.class,     // mapper class
                Text.class,         // mapper output key
                IntWritable.class,  // mapper output value
                job);
        TableMapReduceUtil.initTableReducerJob(
                targetTable,        // output table
                MyTableReducer.class,    // reducer class
                job);
        job.setNumReduceTasks(1);   // at least one, adjust as required

        boolean b = job.waitForCompletion(true);
        if (!b) {
            throw new IOException("error with job!");
        }

    }

}
