
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class TopNReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

  private TreeMap<Integer, String> topNMap = new TreeMap<>();

  private static final int N = 3;

  @Override
  protected void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {

    int sum = 0;
    for (IntWritable value : values) {
      sum += value.get();
    }

    // Insert into TreeMap
    topNMap.put(sum, key.toString());

    // Keep only top N (remove smallest)
    if (topNMap.size() > N) {
      topNMap.remove(topNMap.firstKey());
    }
  }

  @Override
  protected void cleanup(Context context)
      throws IOException, InterruptedException {

    // Output in descending order
    for (Map.Entry<Integer, String> entry :
         topNMap.descendingMap().entrySet()) {
      context.write(new Text(entry.getValue()),
                    new IntWritable(entry.getKey()));
    }
  }
}
