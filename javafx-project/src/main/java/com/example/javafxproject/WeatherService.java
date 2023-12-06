package com.example.javafxproject;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;


public class WeatherService {
    public WeatherData getWeather(String city, String unit) {
        Double tempInCelsius = getTempByCity(city);
        String iconUrl = getIconUrlByCity(city);

        String temp;
        if ("imperial".equals(unit)) {
            tempInCelsius = transferToFahrenheit(tempInCelsius);
            temp = String.format("%.2f°F", tempInCelsius);
        } else {
            temp = tempInCelsius + " °C";
        }

        return new WeatherData(city, temp, iconUrl);
    }

    private String getIconUrlByCity(String city) {
        //return "https://i.postimg.cc/C1VdnTJB/image.png";
        /*String url = "https://www.googleapis.com/customsearch/v1?"
                + "cx=e42f66d78edf54903"
                + "key=AIzaSyCPKMJriwQQ4KqY7rfek8k5H0X2aSYWl-Y"
                + "q=" + city
                + "searchType=image";
        JSONObject out = new JSONObject(getDataByUrl(url));
        JSONObject obj = out.getJSONObject("main");
        System.out.print(obj);*/

        return "https://i.postimg.cc/C1VdnTJB/image.png";
    }

    private Double getTempByCity(String city) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=614674e366d17b482963b1fd4aef410b&units=metric";
        JSONObject out = new JSONObject(getDataByUrl(url));
        JSONObject obj = out.getJSONObject("main");

        return obj.getDouble("temp");
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

    private Double transferToFahrenheit(Double temp) {
        return temp * 9.0 / 5.0 + 32.0;
    }
}