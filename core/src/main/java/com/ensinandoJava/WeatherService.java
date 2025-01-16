package com.ensinandoJava;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class WeatherService {

    private static final String CITY_NAME = "Xique-Xique";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/";
    private static final String API_KEY;

    static {
        Dotenv dotenv = Dotenv.load();

        String apiKey = dotenv.get("API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException("A chave da API não foi encontrada no arquivo .env. Certifique-se de que a variável 'API_KEY' esteja configurada corretamente.");
        }

        API_KEY = apiKey;
    }

    public static boolean isRaining() {
        try {
            URL url = new URL(BASE_URL + "weather?q=" + CITY_NAME + "&appid=" + API_KEY + "&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                return false;
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject weatherData = new JSONObject(jsonResponse.toString());
            for (Object weather : weatherData.getJSONArray("weather")) {
                if (((JSONObject) weather).getString("main").equalsIgnoreCase("Rain")) {
                    System.out.println("Está chovendo em Xique-Xique BA");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Não está chovendo em Xique-Xique BA");
        return false;
    }

    public String getNextRainyDay() {
        try {
            URL url = new URL(BASE_URL + "forecast?q=" + CITY_NAME + "&appid=" + API_KEY + "&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            if (conn.getResponseCode() != 200) {
                return "Erro ao obter dados da previsão.";
            }

            Scanner scanner = new Scanner(url.openStream());
            StringBuilder jsonResponse = new StringBuilder();
            while (scanner.hasNext()) {
                jsonResponse.append(scanner.nextLine());
            }
            scanner.close();

            JSONObject weatherData = new JSONObject(jsonResponse.toString());
            for (Object forecast : weatherData.getJSONArray("list")) {
                JSONObject weatherDetails = (JSONObject) forecast;
                String dateTime = weatherDetails.getString("dt_txt");
                for (Object condition : weatherDetails.getJSONArray("weather")) {
                    JSONObject weatherCondition = (JSONObject) condition;
                    if (weatherCondition.getString("main").equalsIgnoreCase("Rain")) {
                        String dateString = dateTime.split(" ")[0];
                        LocalDate rainyDate = LocalDate.parse(dateString);
                        return rainyDate.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy"));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
