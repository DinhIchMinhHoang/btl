package com.example.demo.translate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Translate API cung cap chuc nang dich van ban giua tieng Anh va tieng Viet.
 * Class nay su dung Google Script de thuc hien viec dich.
 * duong link dan den Script: https://script.google.com/home/projects/17R7VRSCeAs9Fs9dBDwRpT-7m3MBlISv-OOPWx61N9J-IzWehH-GvcMm6/edit
 * duoc tham khao boi user Maksym tren stackoverflow trong qua trinh nghien cuu va phat trien san pham nay.
 * Dich vu dich duoc truy cap thong qua yeu cau HTTP den mot diem cuoi API ben ngoai.
 */
public class TranslateAPI {

    /**
     * Dich van ban tu 1 ngon ngu sang 1 ngon ngu khac.
     * @param text: Van ban can dich
     * @param langFrom: ngon ngu can duoc dich (vd:"en" tieng Anh)
     * @param langTo: ngon ngu sau khi da dich (vd:"vi" tieng Viet)
     * @return Van ban duoc dich thanh cong, hoac tra ve thong bao errror neu viec dich that bai
     */
    public String translateText(String text, String langFrom, String langTo) {
        try {
            //Goi phuong thuc translate de gui yeu cau HTTP den API
            return translate(langFrom, langTo, text);
        } catch (IOException e) {
            //Tra ve thong bao loi khi ket noi API co van de
            return "Something went wrong: " + e.getMessage();
        }
    }

    /**
     * phuong thuc rieng xu ly yeu cau HTTP den Api
     * @param langFrom ngon ngu ban dau ("en" tieng Anh)
     * @param langTo ngon ngu dich den ("vi" tieng Viet)
     * @param text Van ban can dich
     * @return Van ban sau khi duoc dich
     * @throws IOException neu xay ra loi trong qua trinh gui yeu cau HTTP
     */
    private static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = "https://script.google.com/macros/s/AKfycbyhEscEOBHaAdHoz0l8mkbjktYj_0VXkcKKDdneJ5IKaqXf7xmoUksQRCr0ZZk9qFZTZw/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") + //Ma hoa van ban can dich
                "&target=" + langTo + //Ngon ngu dich den
                "&source=" + langFrom;//Ngon ngu ngon (vd:"en" tieng Anh)
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        // Thiet Lap ket noi HTTP
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0"); //Dat User de khong bi chan khi ket noi

        // Doc va xu ly phan hoi tu API tra ve
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
        while((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
        }
        // Tra ve ket qua dich
        return response.toString();
    }
}
