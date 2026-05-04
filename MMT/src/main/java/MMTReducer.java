import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class MMTReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  @Override
  public void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {

    int currentMax = Integer.MIN_VALUE;
    int sumOfMaxes = 0;
    int observationCount = 0;
    int windowCount = 0;

    for (IntWritable value : values) {
      int temp = value.get();

      // Find max in the current group
      if (temp > currentMax) {
        currentMax = temp;
      }

      observationCount++;

      // Every 3 observations, we lock in the max and reset
      if (observationCount == 3) {
        sumOfMaxes += currentMax;
        windowCount++;

        // Reset for next window
        currentMax = Integer.MIN_VALUE;
        observationCount = 0;
      }
    }

    // Output the average of the maximums found
    if (windowCount > 0) {
      context.write(key, new IntWritable(sumOfMaxes / windowCount));
    }
  }
}
