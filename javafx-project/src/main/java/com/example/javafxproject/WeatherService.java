package com.example.javafxproject;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.net.InetAddress;


public class WeatherService {
    public WeatherData getWeather(String city, String unit) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q="
                + city
                + "&appid=614674e366d17b482963b1fd4aef410b&units=metric";
        JSONObject out = new JSONObject(getDataByUrl(url));
        JSONObject obj = out.getJSONObject("main");
        JSONObject sun = out.getJSONObject("sys");
        long sunrise = sun.getLong("sunrise");
        long sunset = sun.getLong("sunset");
        int timezone = out.getInt("timezone");
        ZoneOffset zoneOffset = ZoneOffset.ofTotalSeconds(timezone);
        ZoneId zoneId = ZoneId.ofOffset("UTC", zoneOffset);
        LocalDateTime sunriseTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunrise), zoneId);
        LocalDateTime sunsetTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(sunset), zoneId);


        JSONArray needArr = out.getJSONArray("weather");
        JSONObject subObj = needArr.getJSONObject(0);

        Double tempInCelsius = obj.getDouble("temp");
        String backgroundUrl = getBackgroundByTemp(tempInCelsius);

        String iconId = subObj.getString("icon");
        String iconUrl = "http://openweathermap.org/img/w/" + iconId + ".png";

        String sunUrl = getSunImageByTime(sunriseTime, sunsetTime, zoneId);

        String temp;
        if ("imperial".equals(unit)) {
            tempInCelsius = transferToFahrenheit(tempInCelsius);
            temp = String.format("%.2f°F", tempInCelsius);
        } else {
            temp = tempInCelsius + " °C";
        }


        try {
            InetAddress localHost = InetAddress.getLocalHost();
            System.out.println("IP Address : " + localHost.getHostAddress());
            System.out.println("Host Name : " + localHost.getHostName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new WeatherData(city, temp, iconUrl, backgroundUrl, sunUrl);
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
            backgroundUrl = "https://i.postimg.cc/QtnWSGV9/hermit-nikolai-gromov.jpg";
        else if (temp < -5)
            backgroundUrl = "https://i.postimg.cc/VspCC832/orig.jpg";
        else if (temp < 5)
            backgroundUrl = "https://i.postimg.cc/jSQwmJLr/0ba3450bc4a6132d572d5f8d947368b359fe67d1.jpg";
        else if (temp < 15)
            backgroundUrl = "https://i.postimg.cc/PJQPLP0j/1645997370-49-vsegda-pomnim-com-p-priblizhenie-dozhdya-foto-53.jpg";
        else
            backgroundUrl = "https://i.postimg.cc/PqfshNQD/original.jpg";

        return backgroundUrl;
    }

    private Double transferToFahrenheit(Double temp) {

        return temp * 9.0 / 5.0 + 32.0;
    }

    private String getSunImageByTime(LocalDateTime sunriseTime, LocalDateTime sunsetTime, ZoneId zoneId)
    {
        LocalDateTime currentTime = LocalDateTime.now(zoneId);
        if (currentTime.isBefore(sunriseTime)) {
            return "https://i.postimg.cc/y6wZNvC0/sunrise-6744778.png";
        }
        else if (currentTime.isAfter(sunsetTime.minusHours(1)) && currentTime.isBefore(sunsetTime) ) {
            return "https://i.postimg.cc/Twtrnb7s/sunset-1622198.png";
        }
        else if(currentTime.isAfter(sunsetTime)) {
            return "https://i.postimg.cc/GtpBFhHh/moon-1812660.png";
        }
        else {
           return "https://i.postimg.cc/L6XxQ9jf/sun-4480566.png";
        }
    }
}