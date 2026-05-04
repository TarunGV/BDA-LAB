import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class AverageReducer
    extends Reducer<Text, IntWritable, Text, IntWritable> {

  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {

    int sumTemp = 0;
    int count = 0;

    for (IntWritable value : values) {
      sumTemp += value.get();
      count++;
    }

    // Basic average calculation (integer division)
    if (count > 0) {
      context.write(key, new IntWritable(sumTemp / count));
    }
  }
}
