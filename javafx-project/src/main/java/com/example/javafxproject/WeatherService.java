package com.example.javafxproject;

import java.util.Random;

public class WeatherService {
    public WeatherData getWeather(String city, String unit) {
        // Здесь в реальном приложении вы бы делали запрос к сервису погоды,
        // чтобы получить актуальные данные. Но для примера, мы будем генерировать случайные данные.
        String temperature = generateRandomTemperature(unit);
        String iconUrl = generateRandomIconUrl();
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
}