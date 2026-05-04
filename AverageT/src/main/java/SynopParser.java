import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SynopParser {

  // Grabs Year(4), MonthDay(4), Time(4) starting from the date section
  private static final Pattern DATE_PATTERN =
      Pattern.compile("(\\d{4})(\\d{4})(\\d{4})");

  // Adjusted to be more flexible with the character between direction and speed
  private static final Pattern WIND_PATTERN =
      Pattern.compile("V020(\\d{3})1[NC](\\d{4})");

  // Grabs the sign and first 4 digits, treating the 5th digit as quality
  private static final Pattern TEMP_PATTERN =
      Pattern.compile("N9([-+]\\d{4})(\\d{1})");

  // Pressure is usually the 5 digits following the '1' after the temperature
  // quality
  private static final Pattern PRESSURE_PATTERN =
      Pattern.compile("([-+]\\d{5})1ADD");

  public static WeatherObservation parse(String line) {
    WeatherObservation obs = new WeatherObservation(line);

    // 1. Station ID: Always first 11-15 chars in this format
    obs.setStationId(line.substring(0, 11));

    // 2. Date + Time (Starts around index 15)
    Matcher dateMatcher = DATE_PATTERN.matcher(line.substring(15, 30));
    if (dateMatcher.find()) {
      obs.setDate(dateMatcher.group(1) + dateMatcher.group(2)); // YYYYMMDD
      obs.setTime(dateMatcher.group(3));                        // HHmm
    }

    // 3. Wind
    Matcher windMatcher = WIND_PATTERN.matcher(line);
    if (windMatcher.find()) {
      obs.setWindDirection(Integer.parseInt(windMatcher.group(1)));
      obs.setWindSpeed(Integer.parseInt(windMatcher.group(2)));
    }

    // 4. Temperature (scaled by 10)
    Matcher tempMatcher = TEMP_PATTERN.matcher(line);
    if (tempMatcher.find()) {
      // Group 1 is the value, Group 2 is the quality bit we ignore
      double temp = Integer.parseInt(tempMatcher.group(1)) / 10.0;
      obs.setTemperature(temp);
    }

    // 5. Pressure (scaled by 10)
    Matcher pressureMatcher = PRESSURE_PATTERN.matcher(line);
    if (pressureMatcher.find()) {
      obs.setPressure(Integer.parseInt(pressureMatcher.group(1)) / 10.0);
    }

    return obs;
  }
}
