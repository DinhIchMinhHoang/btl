package com.example.demo.dictionary;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.io.InputStream;
import java.io.InputStreamReader;



public class DictionaryManagement {
    /**
     * biến kiểu Dictionary chứa các word.
     */
    private Dictionary dictionary;

    /**
     * constructor khởi tạo 1 đối tượng Dictionary và trả về tham chiếu tới đối tượng đó.
     */
    public DictionaryManagement() {
        dictionary = new Dictionary();
    }

    /**
     * trả về tham chiếu tới đối tượng Dictionary;
     *
     * @return dictionary.
     */
    public Dictionary getDictionary() {
        return dictionary;
    }

    /**
     * trả về key của từ được tính bằng chữ cái đầu.
     *
     * @param str lưu từ tiếng anh.
     * @return key.
     */
    static int hash(String str) {
        return str.charAt(0) - 97;
    }

    /**
     * sắp xếp các từ trong mảng theo alphabet.
     *
     * @param arr chứa mảng các từ.
     * @param a   chứa chỉ số đầu của mảng.
     * @param b   chứa chỉ số cuối của mảng.
     */
    public static void quickSort(ArrayList arr, int a, int b) {
        if (a < b) {
            int left = a;
            int right = b - 1;
            int pivot = b;
            while (left <= right) {
                while ((left <= right)
                        && ((Word) arr.get(left)).getTarget().compareTo(((Word) arr.get(pivot)).getTarget()) < 0) {
                    left++;
                }
                while ((right >= left)
                        && ((Word) arr.get(right)).getTarget().compareTo(((Word) arr.get(pivot)).getTarget()) > 0) {
                    right--;
                }
                if (left >= right) {
                    break;
                }
                String temp1 = ((Word) arr.get(left)).getTarget();
                String temp2 = ((Word) arr.get(left)).getExplain();
                ((Word) arr.get(left)).setTarget(((Word) arr.get(right)).getTarget());
                ((Word) arr.get(left)).setExplain(((Word) arr.get(right)).getExplain());
                ((Word) arr.get(right)).setTarget(temp1);
                ((Word) arr.get(right)).setExplain(temp2);
                left++;
                right--;
            }
            String temp1 = ((Word) arr.get(left)).getTarget();
            String temp2 = ((Word) arr.get(left)).getExplain();
            ((Word) arr.get(left)).setTarget(((Word) arr.get(pivot)).getTarget());
            ((Word) arr.get(left)).setExplain(((Word) arr.get(pivot)).getExplain());
            ((Word) arr.get(pivot)).setTarget(temp1);
            ((Word) arr.get(pivot)).setExplain(temp2);

            quickSort(arr, a, left - 1);
            quickSort(arr, left + 1, b);
        }
    }

    /**
     * đọc dữ liêu từ file và lưu vào arraylist.
     *
     * @throws IOException để đẩy ngoại lệ ra ngoài.
     */
    public void insertFromFile() throws IOException {
        BufferedReader buff = null;
        try {
            InputStream inputStream = getClass().getResourceAsStream("/data/dtb.txt");
            if (inputStream == null) {
                throw new IOException("Dictionary file not found in resources");
            }

            buff = new BufferedReader(new InputStreamReader(inputStream));
            String str = null;
            String target = null;
            String explain = null;

            while ((str = buff.readLine()) != null) {
                if (str.charAt(0) == '@') {
                    if (target != null) {
                        dictionary.setLists(new Word(target, explain), hash(target));
                    }
                    int index = str.indexOf('/');
                    if (index == -1) {
                        int i = str.indexOf(' ');
                        if (i != -1) {
                            target = str.substring(1, i);
                            explain = str.substring(i + 1);
                        }
                    } else {
                        target = str.substring(1, index - 1);
                        explain = str.substring(index);
                    }
                } else if (explain != null) {
                    explain += "\n" + str;
                }
            }

            // Add the last word if exists
            if (target != null) {
                dictionary.setLists(new Word(target, explain), hash(target));
            }

        } catch (IOException e) {
            System.err.println("Error reading dictionary file: " + e.getMessage());
            throw e;
        } finally {
            if (buff != null) {
                buff.close();
            }
        }
    }


    /**
     * trả về phiên âm và nghĩa của từ tiếng anh tương ứng.
     * nếu không tìm thấy sẽ trả về null.
     *
     * @param str lưu từ tiếng anh cần tìm.
     * @return trả về phiên âm và nhĩa của từ đó.
     */
    public String dictionarySearch(String str) {
        for (int i = 0; i < dictionary.getLists().length; i++) {
            for (int j = 0; j < dictionary.getLists()[i].size(); j++) {
                if (((Word) dictionary.getLists()[i].get(j)).getTarget().equals(str)) {
                    return ((Word) dictionary.getLists()[i].get(j)).getExplain();
                }
            }
        }
        return null;
    }

    /**
     * trả về danh sách các từ gợi ý.
     *
     * @param str chứa chuỗi người dùng nhập vào.
     * @return arraylist.
     */
    public ArrayList dictionarySuggest(String str) {
        ArrayList arrayList = new ArrayList();
        int index = hash(str);
        int num = str.length();
        for (int i = 0; i < dictionary.getLists()[index].size(); i++) {
            if (((Word) dictionary.getLists()[index].get(i)).getTarget().length() >= num)
                if (((Word) dictionary.getLists()[index].get(i)).getTarget().substring(0, num).equals(str)) {
                    arrayList.add(((Word) dictionary.getLists()[index].get(i)).getTarget());
                }
        }
        return arrayList;
    }

    /**
     * xóa từ tương ứng với từ tiếng anh.
     *
     * @param str chứa từ tiếng anh muốn xóa.
     */
    public void dictionaryDelete(String str) {
        int index = hash(str);
        for (int i = 0; i < dictionary.getLists()[index].size(); i++) {
            if (((Word) dictionary.getLists()[index].get(i)).getTarget().equals(str)) {
                dictionary.getLists()[index].remove(i);
            }
        }
    }

    /**
     * thêm từ mới.
     *
     * @param word chứa từ muốn thêm.
     */
    public void dictionaryAdd(Word word) {
        int index = hash(word.getTarget());
        dictionary.getLists()[index].add(word);
        quickSort((ArrayList) dictionary.getLists()[index], 0, dictionary.getLists()[index].size() - 1);
    }

    /**
     * sửa từ
     *
     * @param target  chứa từ tiếng anh
     * @param explain chứa phiên âm và nghĩa sau khi thay đổi của từ
     */
    public void dictionaryChange(String target, String explain) {
        int index = hash(target);
        for (int i = 0; i < dictionary.getLists()[index].size(); i++) {
            if (((Word) dictionary.getLists()[index].get(i)).getTarget().equals(target)) {
                ((Word) dictionary.getLists()[index].get(i)).setExplain(explain);
            }
        }
    }

    /**
     * phát âm.
     *
     * @param words chứa từ muốn phát âm
     */
    public static void dictionarySpeak(String words) {
        try {
            String[] command = new String[] {
                    "cmd.exe",
                    "/c",
                    "mshta vbscript:Execute(\"CreateObject(\"\"SAPI.SpVoice\"\").Speak(\"\"" + words + "\"\")(window.close)\")"
            };

            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            System.err.println("Error in text-to-speech: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * so sánh để tìm ra các từ gần đúng khi nhập từ không có trong file txt.
     *
     * @param s1 chứa từ so sánh.
     * @param s2 chứa từ cần tìm.
     * @return s1 có gần đúng vói s2 không.
     */
    public static boolean nearlyEqual(String s1, String s2) {
        int errorAllow = (int) Math.round(s2.length() * 0.3);
        if (s1.length() < (s2.length() - errorAllow) || s1.length() > (s2.length() + errorAllow)) {
            return false;
        }
        int indexS2 = 0;
        int indexS1 = 0;
        int error = 0;
        while (indexS2 < s2.length() && indexS1 < s1.length()) {
            if (s2.charAt(indexS2) != s1.charAt(indexS1)) {
                error++;
                for (int k = 1; k <= errorAllow; k++) {
                    if ((indexS2 + k < s2.length()) && s2.charAt(indexS2 + k) == s1.charAt(indexS1)) {
                        indexS2 += k;
                        error += k - 1;
                        break;
                    } else if ((indexS1 + k < s1.length()) && s2.charAt(indexS2) == s1.charAt(indexS1 + k)) {
                        indexS1 += k;
                        error += k - 1;
                        break;
                    }
                }
            }
            indexS1++;
            indexS2++;
        }
        error += s2.length() - indexS2 + s1.length() - indexS1;
        if (error <= errorAllow) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * trả về danh sách các từ gần đúng.
     *
     * @param target chứa từ nhập vào.
     * @return danh sách chứa từ gần đúng.
     */
    public String dictionarySearchNearly(String target) {
        String result = "";
        for (int i = 0; i < dictionary.getLists().length; i++) {
            for (int j = 0; j < dictionary.getLists()[i].size(); j++) {
                if (nearlyEqual(((Word) dictionary.getLists()[i].get(j)).getTarget(), target)) {
                    result += ((Word) dictionary.getLists()[i].get(j)).getTarget();
                    result += "\n";
                }
            }
        }
        return result;
    }

    /**
     * lưu dữ liệu mới vào file.
     *
     * @throws IOException để đẩy ngoại lệ ra ngoài.
     */
    public void dictionarySave() throws IOException {
        String resourcePath = "demo/src/main/resources/data/dtb.txt";

        try (FileWriter fileWriter = new FileWriter(resourcePath)) {
            for (int i = 0; i < dictionary.getLists().length; i++) {
                for (int j = 0; j < dictionary.getLists()[i].size(); j++) {
                    String str = dictionary.getLists()[i].get(j).toString();
                    fileWriter.write("@" + str + "\n");
                }
            }
        } catch (IOException exc) {
            System.err.println("Error saving dictionary: " + exc.getMessage());
            throw exc;
        }
    }


    /**
     * dịch đoạn văn từ 'langFrom' -> 'langTo'.
     *
     * @param langFrom là kiểu ngôn ngữ cũ.
     * @param langTo   là kiểu ngôn ngũ mới.
     * @param text     là đoạn cần dịch.
     * @return chuỗi sau khi dịch.
     */
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        String urlStr = null;
        try {
            urlStr = "https://script.google.com/macros/s/AKfycbzqt4HkQoDO-iwvT0jDJxrs0TlIshoa3SklBCZGnizNcAVXGA/exec" +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        URL url = new URL(urlStr);
        System.out.println(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}
