package com.example.demo.auth;

/**
 * class dai dien cho nguoi dung trong he thong
 * luu tru thong tin co ban cua user
 */
public class User {
    private String username;
    private String password;
    private int streak;

    /**
     * khoi tao nguoi dung moi voi ten, mk, streak
     * @param username ten dang nhap
     * @param password mat khau dang nhap
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.streak = 0;//set streak default = 0
    }

    /**
     * khoi tao nguoi dung voi ten, mk, streak
     * @param username ten dnhap
     * @param password mat khau dnhap
     * @param streak so tran thang lien tiep
     */
    public User(String username, String password, int streak) {
        this.username = username;
        this.password = password;
        this.streak = streak;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getStreak() {
        return streak;
    }
    public void setStreak(int streak) {
        this.streak = streak;
    }

    public void incrementWins() {
        this.streak++;
    }

    public void incrementLosses() {
        this.streak = 0;
    }

    /**
     * chuyen doi thong tin thanh chuoi de luu tru
     * form: username,password,winstreak
     * @return
     */
    @Override
    public String toString() {
        return username + "," + password  + "," + streak;
    }
}
