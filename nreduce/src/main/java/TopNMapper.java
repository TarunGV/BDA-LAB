import java.io.IOException;
import java.util.Scanner;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TopNMapper extends Mapper<Object, Text, Text, IntWritable> {
  static final IntWritable ONE = new IntWritable(1);
  static String delims = "[_|$#<>^=\\[\\]*/\\\\,;,.\\-:()?!\"']";

  @Override
  public void map(Object key, Text value, Context context)
      throws IOException, InterruptedException {

    // Use delimiter correctly
    Scanner sc = new Scanner(value.toString());

    while (sc.hasNext()) {
      String word = sc.next().trim();
      if (!word.isEmpty()) {
        context.write(new Text(word), ONE);
      }
    }

    sc.close();
  }
}
