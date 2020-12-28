package mapReduce;


import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

public  class MyMapper extends TableMapper<Text, IntWritable> {
    public static final byte[] CF = "attributes".getBytes();
    public static final byte[] ATTR1 = "type".getBytes();

    private final IntWritable ONE = new IntWritable(1);
    private Text text = new Text();

    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {
        String val = new String(value.getValue(CF, ATTR1));
        text.set(val);     // we can only emit Writables...

        context.write(text, ONE);
    }
}
