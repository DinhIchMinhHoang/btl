
package com.example.demo.auth;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private static final String UserFile = "demo/src/main/resources/data/user.txt";
    private static User currentUser;

    public UserManager() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {
        try {
            File file = new File(UserFile);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
                System.out.println("Created new user file at: " + UserFile);
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            File file = new File(UserFile);
            if (!file.exists()) {
                System.out.println("File does not exist: " + UserFile);
                return users;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            System.out.println("Reading users from file: " + UserFile);
            while ((line = reader.readLine()) != null) {
                System.out.println("Line read: '" + line + "'");
                if (!line.trim().isEmpty()) {
                    String[] parts = line.split(",");
                    if (parts.length >= 2) {
                        if (parts.length == 2) {
                            users.add(new User(parts[0].trim(), parts[1].trim()));
                        } else if (parts.length >= 3) {
                            users.add(new User(parts[0].trim(), parts[1].trim(), Integer.parseInt(parts[2].trim())));
                        }
                        System.out.println("Added user: " + parts[0].trim());
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(User user) {
        createFileIfNotExists();

        List<User> existingUsers = getAllUsers();
        for (User existingUser : existingUsers) {
            if (existingUser.getUsername().equals(user.getUsername())) {
                System.out.println("Username already exists: " + user.getUsername());
                return false;
            }
        }

        try (FileWriter fw = new FileWriter(UserFile, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(user.toString());
            System.out.println("Added new user: " + user.getUsername());
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean verifyUser(String username, String password) {
        List<User> users = getAllUsers();
        System.out.println("\nVerifying user: " + username);
        System.out.println("Stored users:");
        for (User user : users) {
            System.out.println("User in file: '" + user.getUsername() + "' , '" + user.getPassword() + "'");
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Match found!");
                currentUser = user;
                return true;
            }
        }
        System.out.println("No match found.");
        return false;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        currentUser = user;
    }

    public void updateUserStats(User user, boolean won) {
        if (user == null) return;

        if (won) {
            user.incrementWins();
        } else {
            user.incrementLosses();
        }

        updateUserInFile(user);
    }

    public void updateUserInFile(User updatedUser) {
        List<User> users = getAllUsers();
        try (FileWriter fw = new FileWriter(UserFile);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (User user : users) {
                if (user.getUsername().equals(updatedUser.getUsername())) {
                    out.println(updatedUser.toString());
                } else {
                    out.println(user.toString());
                }
            }
        } catch (IOException e) {
            System.out.println("Error updating user stats: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<User> getTopUsers(int limit) {
        List<User> users = getAllUsers();
        users.sort((u1, u2) -> Integer.compare(u2.getStreak(), u1.getStreak()));

        if (users.size() <= limit) {
            return users;
        } else {
            return users.subList(0, limit);
        }
    }

    public boolean deleteUser(String username) {
        List<User> users = getAllUsers();
        boolean userFound = false;

        try (FileWriter fw = new FileWriter(UserFile);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {

            for (User user : users) {
                if (!user.getUsername().equals(username)) {
                    out.println(user.toString());
                } else {
                    userFound = true;
                    System.out.println("Deleted user: " + username);
                }
            }

            return userFound;
        } catch (IOException e) {
            System.out.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
