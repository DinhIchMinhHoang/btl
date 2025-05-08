package com.example.demo.dictionary;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    // Sử dụng List<Word>[] để lưu trữ các danh sách từ
    private List<Word>[] lists = new ArrayList[26];

    // Getter cho lists
    public List<Word>[] getLists() {
        return this.lists;
    }

    // Setter cho lists, thêm một từ vào danh sách tại vị trí chỉ định
    public void setLists(Word word, int i) {
        this.lists[i].add(word);
    }

    // Constructor khởi tạo các danh sách từ
    public Dictionary() {
        // Khởi tạo mỗi phần tử trong lists là một ArrayList<Word>
        for (int i = 0; i < 26; ++i) {
            this.lists[i] = new ArrayList<>();
        }
    }

    // Một phương thức tiện ích để tìm từ theo chỉ mục (một chữ cái)
    public List<Word> getWordsByLetter(char letter) {
        // Xử lý chỉ mục từ ký tự của chữ cái (0 cho A, 1 cho B, v.v.)
        int index = Character.toLowerCase(letter) - 'a';
        if (index >= 0 && index < 26) {
            return this.lists[index];
        }
        return null; // Trả về null nếu không phải là ký tự hợp lệ
    }
}
