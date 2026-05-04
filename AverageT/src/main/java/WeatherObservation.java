public class WeatherObservation {

  private String raw;
  private String stationId;
  private String date;
  private String time;
  private Integer windDirection;
  private Integer windSpeed;
  private Double temperature;
  private Double pressure;

  public WeatherObservation(String raw) { this.raw = raw; }

  // --- Setters ---
  public void setStationId(String stationId) { this.stationId = stationId; }
  public void setDate(String date) { this.date = date; }
  public void setTime(String time) { this.time = time; }
  public void setWindDirection(Integer windDirection) {
    this.windDirection = windDirection;
  }
  public void setWindSpeed(Integer windSpeed) { this.windSpeed = windSpeed; }
  public void setTemperature(Double temperature) {
    this.temperature = temperature;
  }
  public void setPressure(Double pressure) { this.pressure = pressure; }

  // --- Getters ---
  public String getRaw() { return raw; }
  public String getStationId() { return stationId; }
  public String getDate() { return date; }
  public String getTime() { return time; }
  public Integer getWindDirection() { return windDirection; }
  public Integer getWindSpeed() { return windSpeed; }
  public Double getTemperature() { return temperature; }
  public Double getPressure() { return pressure; }

  @Override
  public String toString() {
    return "WeatherObservation{"
        + "stationId='" + stationId + '\'' + ", date='" + date + '\'' +
        ", time='" + time + '\'' + ", windDirection=" + windDirection +
        ", windSpeed=" + windSpeed + ", temperature=" + temperature +
        ", pressure=" + pressure + '}';
  }
}
