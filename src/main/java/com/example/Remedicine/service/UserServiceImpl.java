package com.example.Remedicine.service;

import com.example.Remedicine.Entity.User;
import com.example.Remedicine.repositry.UsersRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    UsersRepositry usersRepositry;
    //Constructor Injection.

    public UserServiceImpl(UsersRepositry usersRepositry)
    {
        this.usersRepositry = usersRepositry;
    }


    @Override
    public User createUser(User user) {
        return usersRepositry.save(user);
    }

    @Override
    public User getUserById(int userid) {

        return usersRepositry.findByuserId(userid);
    }

    @Override
    public List<User> getAllUsers() {
        return usersRepositry.findAll();
    }

    @Override
    public void deleteUser(int userid) {
        if (usersRepositry.findByuserId(userid) != null) {
            usersRepositry.deleteById(userid);
        }
    }
}
