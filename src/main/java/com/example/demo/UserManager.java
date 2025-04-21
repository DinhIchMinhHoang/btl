package com.example.demo;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;


public class UserManager {
    private static final String UserFile = "src/main/resources/user.txt";


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            // Get the file from resources folder
            InputStream inputStream = getClass().getResourceAsStream("/user.txt");
            if (inputStream != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                System.out.println("Reading users from file:");
                while ((line = reader.readLine()) != null) {
                    System.out.println("Line read: '" + line + "'");
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split(",");
                        if (parts.length == 2) {
                            users.add(new User(parts[0].trim(), parts[1].trim()));
                            System.out.println("Added user: " + parts[0].trim());
                        }
                    }
                }
                reader.close();
            } else {
                System.out.println("Could not find user.txt in resources");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
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
        System.out.println("\nStored users:");
        for (User user : users) {
            System.out.println("User in file: '" + user.getUsername() + "' , '" + user.getPassword() + "'");
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Match found!");
                return true;
            }
        }
        System.out.println("No match found.");
        return false;
    }


}
