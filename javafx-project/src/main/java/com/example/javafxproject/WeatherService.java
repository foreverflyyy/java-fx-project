package com.example.javafxproject;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public class WeatherService {
    public WeatherData getWeather(String city, String unit) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=614674e366d17b482963b1fd4aef410b&units=metric";
        JSONObject out = new JSONObject(getDataByUrl(url));
        JSONObject obj = out.getJSONObject("main");
        JSONArray needArr = out.getJSONArray("weather");
        JSONObject subObj = needArr.getJSONObject(0);

        Double tempInCelsius = obj.getDouble("temp");
        String backgroundUrl = getBackgroundByTemp(tempInCelsius);

        String iconId = subObj.getString("icon");
        String iconUrl = "http://openweathermap.org/img/w/" + iconId + ".png";

        String temp;
        if ("imperial".equals(unit)) {
            tempInCelsius = transferToFahrenheit(tempInCelsius);
            temp = String.format("%.2f°F", tempInCelsius);
        } else {
            temp = tempInCelsius + " °C";
        }

        return new WeatherData(city, temp, iconUrl, backgroundUrl);
    }

    private String getDataByUrl(String dataUrl) {
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
            System.out.println("Ошибка запроса!");
        }
        return data.toString();
    }

    private String getBackgroundByTemp(Double temp) {
        String backgroundUrl = "";

        if (temp < -20)
            backgroundUrl = "https://i.postimg.cc/GmDYtRJg/hermit-nikolai-gromov.jpg";
        else if (temp < -5)
            backgroundUrl = "https://i.postimg.cc/8PmgSJxZ/orig.jpg";
        else if (temp < 5)
            backgroundUrl = "https://i.postimg.cc/TwgHJB6t/0ba3450bc4a6132d572d5f8d947368b359fe67d1.jpg";
        else if (temp < 15)
            backgroundUrl = "https://i.postimg.cc/vH2FTFHn/1645997370-49-vsegda-pomnim-com-p-priblizhenie-dozhdya-foto-53.jpg";
        else
            backgroundUrl = "https://i.postimg.cc/fLDg5BRZ/original.jpg";

        return backgroundUrl;
    }

    private Double transferToFahrenheit(Double temp) {
        return temp * 9.0 / 5.0 + 32.0;
    }
}