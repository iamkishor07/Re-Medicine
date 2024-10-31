package com.example.Remedicine.repositry;

import com.example.Remedicine.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepositry extends JpaRepository<User, Integer> {

    User findByuserId(int id);
}
