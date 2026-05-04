import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AverageDriver {

  public static void main(String[] args) throws Exception {
    if (args.length != 2) {
      System.err.println("Usage: AverageDriver <input path> <output path>");
      System.exit(-1);
    }

    Job job = Job.getInstance();
    job.setJarByClass(AverageDriver.class);
    job.setJobName("Average Temperature");

    // Set Input and Output paths from arguments
    FileInputFormat.addInputPath(job, new Path(args[0]));
    FileOutputFormat.setOutputPath(job, new Path(args[1]));

    // Set Mapper and Reducer classes
    job.setMapperClass(AverageMapper.class);
    job.setReducerClass(AverageReducer.class);

    // Set Output Key/Value types
    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);
    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    // Terminate based on job success
    System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}
