public class Main {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay(weatherData);

        weatherData.setMeasurements(20, 65, 30.4f);
        weatherData.setMeasurements(22, 70, 29.2f);
        weatherData.setMeasurements(28, 90, 29.2f);
    }
}
