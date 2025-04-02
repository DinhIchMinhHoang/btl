package com.example.demo;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;


public class UserManager {
    private static final String UserFile = "demo\\src\\main\\resources\\user.txt";

    public List<User>  getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            if (Files.exists(Paths.get(UserFile))) {
                List<String> lines = Files.readAllLines(Paths.get(UserFile));
                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length == 2) {
                        users.add(new User(parts[0], parts[1]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }


    public boolean addUser(User user) {
        List<User> existingUsers = getAllUsers();
        for (User existingUser : existingUsers) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                return false;
            }
        }

        try (FileWriter fw = new FileWriter(UserFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
                 out.println(user.toString());
                 return true;
             } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyUser(String username, String password) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
