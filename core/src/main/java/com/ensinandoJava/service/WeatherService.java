package com.ensinandoJava.service;

import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
        API_KEY = getApiKey();
    }

    private static String getApiKey() {
        File envFile = new File(".env");
        if (!envFile.exists()) {
            try {
                envFile.createNewFile();
            } catch (IOException e) {
                throw new IllegalStateException("Erro ao criar o arquivo .env.", e);
            }
        }

        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = solicitarApiKeyUsuario();
            if (apiKey != null) {
                salvarApiKeyNoEnv(apiKey);
            } else {
                throw new IllegalStateException("Nenhuma chave de API foi fornecida.");
            }
        }
        return apiKey;
    }


    private static String solicitarApiKeyUsuario() {
        return JOptionPane.showInputDialog(
            null,
            "Insira a chave da API do OpenWeather:",
            "Configuração da API",
            JOptionPane.QUESTION_MESSAGE
        );
    }

    private static void salvarApiKeyNoEnv(String apiKey) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(".env", true))) {
            writer.println("API_KEY=" + apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isRaining() {
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
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                        LocalDate date = LocalDate.parse(dateTime.split(" ")[0]);
                        String time = dateTime.split(" ")[1].substring(0, 5);
                        return date.format(DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy")) + " às " + time;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
