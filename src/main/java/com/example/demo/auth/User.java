package com.example.demo.auth;

public class User {
    private String username;
    private String password;
    private int streak;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.streak = 0;
    }

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

    @Override
    public String toString() {
        return username + "," + password  + "," + streak;
    }
}
