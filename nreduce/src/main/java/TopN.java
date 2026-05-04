import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

/**
 * Hello world!
 */
public class TopN {

  public static void main(String[] args)
      throws IOException, InterruptedException, ClassNotFoundException {
    Configuration configuration = new Configuration();
    args = (new GenericOptionsParser(configuration, args)).getRemainingArgs();
    if (args.length != 2) {
      System.err.println("Usage: TopN <in> <out>");
    }
    Job job = Job.getInstance();
    job.setJobName("Top N");
    job.setJarByClass(TopN.class);

    job.setMapperClass(TopNMapper.class);
    job.setReducerClass(TopNReducer.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
