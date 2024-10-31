package com.example.Remedicine.service;

import com.example.Remedicine.Entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);

    User getUserById(int userid);

    List<User> getAllUsers();

    void deleteUser(int userid);
}
