package com.example.javafxproject;

import java.util.Random;

public class WeatherData {
    private String city;
    private Double temperature;
    private String iconUrl;

    public WeatherData(String city, Double temperature, String iconUrl) {
        this.city = city;
        this.temperature = temperature;
        this.iconUrl = iconUrl;
    }

    public String getCity() {
        return city;
    }

    public Double getTemperature() {
        return temperature;
    }

    public String getIconUrl() {
        return iconUrl;
    }
}