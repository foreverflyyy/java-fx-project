package com.example.javafxproject;
import java.util.Random;
import java.net.URL;
import java.net.URLConnection;
import javafx.fxml.FXML;
import org.json.JSONObject;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class WeatherService {
    public WeatherData getWeather(String city, String unit) {
        JSONObject out= new JSONObject(getData("http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=614674e366d17b482963b1fd4aef410b&units=metric"));
        String iconUrl = generateRandomIconUrl();
        JSONObject obj = out.getJSONObject("main");
        Double temperature = obj.getDouble("temp");
        return new WeatherData(city, temperature, iconUrl);
    }

    private String generateRandomTemperature(String unit) {
        Random random = new Random();
        int temperatureCelsius = random.nextInt(40) - 10; // Генерируем случайную температуру от -10 до 30 градусов Цельсия

        if ("imperial".equals(unit)) {
            // Если выбраны фаренгейты, преобразуем температуру
            double temperatureFahrenheit = temperatureCelsius * 9.0 / 5.0 + 32.0;
            return String.format("%.2f°F", temperatureFahrenheit);
        } else {
            return temperatureCelsius + "°C";
        }
    }

    private String generateRandomIconUrl() {
        // TODO: Сделать, что если меньше какого-то градуса, то зимняя картинка
        // Здесь вы бы подключались к API сервиса погоды для получения реальной иконки.
        // Для примера, мы будем использовать одну изображение.
        return "https://i.postimg.cc/C1VdnTJB/image.png";
    }

    private String getData(String dataUrl) {
        StringBuffer data = new StringBuffer();

        try {
            URL url = new URL(dataUrl);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                data.append(line + "\n");
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Такой город был не найден!");
        }
        return data.toString();
    }

}