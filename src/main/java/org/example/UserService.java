package org.example;

import java.util.HashMap;
import java.util.Map;

public class UserService {
    private Map<String, User> userDatabase = new HashMap<>();

    public boolean registerUser(User user) {
        if (user.getUsername() == null || userDatabase.containsKey(user.getUsername())) {
            return false; // User already exists or username is null
        }
        userDatabase.put(user.getUsername(), user);
        return true; // User registered successfully
    }

    public User loginUser(String username, String password) {
        User user = userDatabase.get(username);
        if (user == null || !user.getPassword().equals(password)) {
            return null; // User not found or password does not match
        }
        return user; // Login successful
    }

    public boolean updateUserProfile(User user, String newUsername, String newPassword, String newEmail) {
        if (newUsername == null || (userDatabase.containsKey(newUsername) && !user.getUsername().equals(newUsername))) {
            return false; // New username is null or already taken by another user
        }
        userDatabase.remove(user.getUsername());
        user.setUsername(newUsername);
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        userDatabase.put(newUsername, user);
        return true; // User profile updated successfully
    }
}

