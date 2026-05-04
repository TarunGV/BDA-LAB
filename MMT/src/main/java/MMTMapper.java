import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class MMTMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  private static final int MISSING = 9999;

  @Override
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {

    String line = value.toString();

    // Basic length check to ensure the line isn't malformed
    if (line.length() < 93)
      return;

    String month = line.substring(19, 21);
    int temperature;

    // Extract temperature: handle '+' sign or '-' sign
    if (line.charAt(87) == '+') {
      temperature = Integer.parseInt(line.substring(88, 92));
    } else {
      temperature = Integer.parseInt(line.substring(87, 92));
    }

    String quality = line.substring(92, 93);

    // Filter for valid data based on NCDC quality codes
    if (temperature != MISSING && quality.matches("[01459]")) {
      context.write(new Text(month), new IntWritable(temperature));
    }
  }
}
