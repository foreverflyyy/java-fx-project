package com.example.javafxproject;

public class WeatherData {
    private String city;
    private String temperature;
    private String iconUrl;
    private String backgroundUrl;

    public WeatherData(String city, String temperature, String iconUrl, String backgroundUrl) {
        this.city = city;
        this.temperature = temperature;
        this.iconUrl = iconUrl;
        this.backgroundUrl = backgroundUrl;
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

    public String getBackgroundUrl() {return backgroundUrl;}
}