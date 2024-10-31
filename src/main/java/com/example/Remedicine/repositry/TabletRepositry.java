package com.example.Remedicine.repositry;

import com.example.Remedicine.Entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TabletRepositry extends JpaRepository<Medicine, Integer> {
    Medicine findBytabletId(int id);
}
