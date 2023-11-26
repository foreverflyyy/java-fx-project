package com.example.javafxproject;

import java.util.Random;

public class WeatherData {
    private String city;
    private String temperature;
    private String iconUrl;

    public WeatherData(String city, String temperature, String iconUrl) {
        this.city = city;
        this.temperature = temperature;
        this.iconUrl = iconUrl;
    }

    public String getCity() {
        return city;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}