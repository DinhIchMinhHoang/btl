package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslateAPI {
    public String translateText(String text, String langFrom, String langTo) {
        try {
            return translate(langFrom, langTo, text);
        } catch (IOException e) {
            return "Something went wrong: " + e.getMessage();
        }
    }

    private static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbyhEscEOBHaAdHoz0l8mkbjktYj_0VXkcKKDdneJ5IKaqXf7xmoUksQRCr0ZZk9qFZTZw/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
        while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
        }
        return response.toString();
    }
}
