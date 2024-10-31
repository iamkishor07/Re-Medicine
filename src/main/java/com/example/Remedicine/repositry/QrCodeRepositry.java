package com.example.Remedicine.repositry;

import com.example.Remedicine.Entity.QrCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepositry extends JpaRepository<QrCode, Integer> {
    QrCode findByqrcodeId(int id);
}
